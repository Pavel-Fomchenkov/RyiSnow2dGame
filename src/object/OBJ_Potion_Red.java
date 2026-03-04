package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = "Red potion";
        value = 5;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals your life by " + value + ".";
        price = 10;
    }

    @Override
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        if (entity.maxLife - entity.life < value) {
            value = entity.maxLife - entity.life;
        }
        entity.life += value;
        if (value != 0) {
            gp.ui.currentDialogue = "You drink the " + name + "!\n"
                    + "Your life has been recovered by " + value + ".";
        } else {
            gp.ui.currentDialogue = "You feel normal.";
        }
        gp.playSE(2);
    }
}
