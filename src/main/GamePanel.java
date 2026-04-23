package main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import entity.Projectile;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 19;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1008 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 624 pixels
    // FOR FULL SCREEN
    public boolean fullScreenOn = false;
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;


    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FPS
    int FPS = 60;
    int currentFPS = 0;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Thread gameThread;
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity[][] projectile = new Projectile[maxMap][50];
    public List<Entity> particleList = new ArrayList<>();
    List<Entity> entityList = new ArrayList<>();
    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        gameState = titleState;

        // tempImage to draw everything instead of drawing into JPanel
        // will be resized to fullscreen once
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void retry() {
        player.setDefaultPosition(currentMap);
        player.restoreLifeAndMana();
        aSetter.setNPC();
    }

    public void restart() {
        currentMap = 0;
        ui.message.clear();
        // Clear projectiles
        for (int i = 0; i < projectile[currentMap].length; i++) {
            if (projectile[currentMap][i] != null) {
                projectile[currentMap][i].life = 0;
            }
        }
        particleList.clear();
        player.setDefaultValues();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }

    public void setFullScreen() {
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    public void run() {
//
//        double drawInterval = 1_000_000_000 / FPS;
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while (gameThread != null) {
//
//            // 1 UPDATE: update information such as character position
//            update();
//            // 2 DRAW: draw the screen with the updated information
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime /= 1000000;
//                if (remainingTime < 0) {
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
    public void run() {     // Alternative way to control refresh rate
        double drawInterval = (double) 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen(); // instead of repaint method
                drawToScreen(); // draw the buffered image to the screen
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                currentFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            // PLAYER
            player.update();
            // NPC
            for (Entity entity : npc[currentMap]) {
                if (entity != null) {
                    entity.update();
                }
            }
            for (int i = 0; i < monster[currentMap].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < projectile[currentMap].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }
                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            for (int i = particleList.size() - 1; i >= 0; i--) {
                Entity p = particleList.get(i);
                if (p != null) {
                    if (p.alive) {
                        p.update();
                    }
                    if (!p.alive) {
                        particleList.remove(i);
                    }
                }
            }

            for (InteractiveTile tile : iTile[currentMap]) {
                if (tile != null) {
                    tile.update();
                }
            }
        }
        if (gameState == pauseState) {
            //nothing
        }
    }

    public void drawToTempScreen() {
        // DEBUG
        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            // TILE
            tileM.draw(g2);
            // INTERACTIVE TILE
            for (InteractiveTile interactiveTile : iTile[currentMap]) {
                if (interactiveTile != null) {
                    interactiveTile.draw(g2);
                }
            }
            // ENTITY
            entityList.add(player);
            for (Entity entity : npc[currentMap]) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : obj[currentMap]) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : monster[currentMap]) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : projectile[currentMap]) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : particleList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));
            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // DEBUG
            if (keyH.showDebugText) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
                g2.setColor(Color.white);
                int x = tileSize / 3;
                int y = tileSize * 3;
                int lineHeight = tileSize / 3;

                g2.drawString("Draw Time: " + passed, x, y);
                g2.drawString("FPS: " + currentFPS, x, y += lineHeight);
                g2.drawString("WorldX: " + player.worldX, x, y += lineHeight);
                g2.drawString("WorldY: " + player.worldY, x, y += lineHeight);
                g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y += lineHeight);
                g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y += lineHeight);
                g2.drawString("CollisionOn: " + player.collisionOn, x, y + lineHeight);
                System.out.println("Draw Time: " + passed);
            }
            // UI
            ui.draw(g2);
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
