package object;

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    public boolean opened;

    public OBJ_Chest(GamePanel gp, ArrayList<Entity> inventory, boolean opened) {
        super(gp);
        this.gp = gp;
        this.inventory = inventory;
        this.opened = opened;

        type = type_obstacle;
        name = "Chest";
        image = setup("/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/chest_closed", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
        updateSprites();
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact() {
        if (!opened) {
            gp.gameState = gp.dialogueState;
            gp.playSE(3);
            gp.ui.currentDialogue = "You need a key to open this.";
        } else {
            if (this.inventory.isEmpty()) {
                gp.gameState = gp.dialogueState;
                gp.ui.currentDialogue = "It's empty.";
            } else {
                gp.gameState = gp.exchangeState;
                gp.playSE(3);
                gp.ui.npc = this;
                down1 = image3;
            }
        }
    }

    @Override
    public void updateSprites() {
        if (this.inventory.isEmpty()) {
            down1 = image3;
            return;
        }
        if (opened) {
            down1 = image;
        } else {
            down1 = image2;
        }
    }
}
