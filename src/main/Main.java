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
//  либо увеличением размера карты, либо смещением интерфейса в центр) - нужно увеличить размер карты по ширине
//  2. Метод checkCollision устанавливает запрет движения если справа или снизу расстояние до непроходимого объекта
//  меньше скорости игрока, что не позволяет игроку приближаться к объектам, при большой скорости игрок не проходит
//  в узкие проходы и уперевшись в dryTree сверху не может пройти влево и вправо вдоль обычных тайлов

