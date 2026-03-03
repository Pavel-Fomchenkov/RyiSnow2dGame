package main;

import javax.swing.*;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true); // remove window header, gp.setFullScreen method can be bypassed to run in windowed mode
        }

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}

// TODO
//  1. обзор может превышать размер карты из-за чего возникают артефакты интерфейса (чинится уменьшением maxScreenCol,
//  либо увеличением размера карты, либо смещением интерфейса в центр) - нужно увеличить размер карты по ширине или
//  сделать движение игрока от центра к краю
//  2. Метод checkCollision устанавливает запрет движения если справа или снизу расстояние до непроходимого объекта
//  меньше скорости игрока, что не позволяет игроку приближаться к объектам, при большой скорости игрок не проходит
//  в узкие проходы и уперевшись в dryTree сверху не может пройти влево и вправо вдоль обычных тайлов.
//  Вероятно, нужно полностью менять методы коллизии, например для методов движения, чтобы они также возвращали
//  расстояние до препятствия, если оно меньше значения скорости персонажа и использовать это значение при перемещении
//  персонажа
//  3. Пути к файлам карт дублируются в KeyHandler и TileManager
