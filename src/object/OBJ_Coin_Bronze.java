package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
    GamePanel gp;

    public OBJ_Coin_Bronze(GamePanel gp) {
        this(gp, 1);
    }

    public OBJ_Coin_Bronze(GamePanel gp, int value) {
        super(gp);
        this.gp = gp;
        this.value = value;   // устанавливаем переданное значение
        type = type_pickupOnly;
        name = "Bronze Coin";
        description = "[" + name + "]\nValue: " + value;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }

    @Override
    public boolean use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coin += value;
        return true;
    }
}
