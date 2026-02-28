package main;

import javax.swing.*;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");
        window.setUndecorated(true); // remove window header, gp.setFullScreen method can be bypassed to run in windowed mode

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}

// TODO
//  1. обзор может превышать размер карты из-за чего возникают артефакты интерфейса (чинится уменьшением maxScreenCol,
//  либо увеличением размера карты, либо смещением интерфейса в центр)
//  2. DryTree имеет меньше solidArea из за чего персонах проходя рядом с dryTree упирается в соседние tile
//  3. Некорректно работает titleScreen0 и titleScreen1
