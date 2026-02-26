package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
    }

    @Override
    public void update() {
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 30) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void playSE() {
    }

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }
}
