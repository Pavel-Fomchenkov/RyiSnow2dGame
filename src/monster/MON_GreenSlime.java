package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;
        type = type_monster;
        name = "Green Slime";
        level = 1;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 1;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        if (onPath) {
            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 10, 100);
            // Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            // Check if it shoots a projectile
            checkShootOrNot(200, 50);
        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 4, 100);
            // Get a random direction
            getRandomDirection();
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
//        direction = gp.player.direction;
        // Monster aggro when gets damage
        onPath = true;
    }

    @Override
    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(100) + 1;
        // SET THE MONSTER DROP
        if (i < 10) {
            dropItem(new OBJ_Coin_Bronze(gp));
        } else if (i < 23) {
            dropItem(new OBJ_Heart(gp));
        } else if (i < 36) {
            dropItem(new OBJ_ManaCrystal(gp));
        } else if (i < 37) {
            dropItem(new OBJ_Potion_Red(gp));
        }
    }
}
