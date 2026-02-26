package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;
    int currentFPS = 0;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[] obj = new Entity[20];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    public InteractiveTile[] iTile = new InteractiveTile[50];
    public List<Entity> projectileList = new ArrayList<>();
    List<Entity> entityList = new ArrayList<>();
    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        // playMusic(0);
        gameState = titleState;
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
                repaint();
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
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    if (!monster[i].alive) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }

            for (int i = projectileList.size() - 1; i >= 0; i--) {
                Entity p = projectileList.get(i);
                if (p != null) {
                    if (p.alive) {
                        p.update();
                    }
                    if (!p.alive) {
                        projectileList.remove(i);
                    }
                }
            }

            for (InteractiveTile tile : iTile) {
                if (tile != null) {
                    tile.update();
                }
            }
        }
        if (gameState == pauseState) {
            //nothing
        }
    }

    public void paintComponent(Graphics g) {    // Standard name, invoked by Event Dispatch Thread from Java Swing component
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

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
            for (InteractiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.draw(g2);
                }
            }
            // ENTITY
            entityList.add(player);
            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : monster) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : projectileList) {
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

            // EMPTY ENTITYLIST
            entityList.clear();

            // DEBUG
            if (keyH.showDebugText) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
//                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.white);
                int x = tileSize / 3;
                int y = tileSize * 3;
                int lineHeight = tileSize / 2;

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
        // Releases resources
        g2.dispose();
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
