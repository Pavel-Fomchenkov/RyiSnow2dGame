package main;

import entity.Entity;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;
import tile_interactive.IT_DryTree;

import java.util.Arrays;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        clear2dArray(gp.obj);
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = 23 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = 23 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 40 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = 36 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 40 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX = 38 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 9 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = 11 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 33 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 33;
        gp.obj[mapNum][i].worldY = gp.tileSize * 9;
        i++;
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 35;
        gp.obj[mapNum][i].worldY = gp.tileSize * 9;
        i++;
        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 34;
        gp.obj[mapNum][i].worldY = gp.tileSize * 9;

//        gp.obj[1] = new OBJ_Key(gp);
//        gp.obj[1].worldX = 23 * gp.tileSize;
//        gp.obj[1].worldY = 40 * gp.tileSize;
//
//        gp.obj[2] = new OBJ_Key(gp);
//        gp.obj[2].worldX = 38 * gp.tileSize;
//        gp.obj[2].worldY = 8 * gp.tileSize;
//
//        gp.obj[3] = new OBJ_Door(gp);
//        gp.obj[3].worldX = 10 * gp.tileSize;
//        gp.obj[3].worldY = 12 * gp.tileSize;
//
//        gp.obj[4] = new OBJ_Door(gp);
//        gp.obj[4].worldX = 8 * gp.tileSize;
//        gp.obj[4].worldY = 28 * gp.tileSize;
//
//        gp.obj[5] = new OBJ_Door(gp);
//        gp.obj[5].worldX = 12 * gp.tileSize;
//        gp.obj[5].worldY = 23 * gp.tileSize;
//
//        gp.obj[6] = new OBJ_Chest(gp);
//        gp.obj[6].worldX = 10 * gp.tileSize;
//        gp.obj[6].worldY = 8 * gp.tileSize;
//
//        gp.obj[7] = new OBJ_Boots(gp);
//        gp.obj[7].worldX = 37 * gp.tileSize;
//        gp.obj[7].worldY = 42 * gp.tileSize;
    }

    public void setNPC() {
        clear2dArray(gp.npc);
        int mapNum = 0;
        gp.npc[mapNum][0] = new NPC_OldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize * 21;
        gp.npc[mapNum][0].worldY = gp.tileSize * 21;
    }

    public void setMonster() {
        clear2dArray(gp.monster);
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 36;

        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].life = 5;
        gp.monster[mapNum][i].maxLife = 5;
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;

        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 11;
        gp.monster[mapNum][i].worldY = gp.tileSize * 10;

        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 11;
        gp.monster[mapNum][i].worldY = gp.tileSize * 11;

        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;

        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 39;

        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 40;

        i++;
        mapNum = 1;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].maxLife = 10;
        gp.monster[mapNum][i].life = gp.monster[mapNum][i].maxLife;
        gp.monster[mapNum][i].level = 3;
        gp.monster[mapNum][i].exp = 3;
        gp.monster[mapNum][i].attack = 5;
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 10;
        gp.monster[mapNum][i].projectile = new OBJ_Fireball(gp);
        gp.monster[mapNum][i].projectile.attack = 3;
    }

    public void setInteractiveTile() {
        clear2dArray(gp.iTile);
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 35);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 36);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 15, 36);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 16, 36);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 36);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 18, 36);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 22);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 21);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 34, 20);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 34, 22);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 21);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 18, 20);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 18, 22);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 21, 19);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 23);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 39);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 29);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 29);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 29);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 34, 29);
    }

    public void clear2dArray(Entity[][] arr) {
        for (Entity[] a : arr) {
            Arrays.fill(a, null);
        }
    }
}
