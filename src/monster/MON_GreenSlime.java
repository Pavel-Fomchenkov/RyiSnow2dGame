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
        speed = 1;
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
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && !projectile.alive && shotAvailableCounter == 50) {
            projectile.set(worldX, worldY, direction, true, this);
            projectile.speed = speed + 2;
            projectile.maxLife = projectile.life = 100;
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
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
