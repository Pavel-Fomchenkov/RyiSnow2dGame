package main;

import java.io.*;
import java.nio.file.Path;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        Path saveData = Path.of("gameConfig.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(saveData.toFile()))) {
            // Full screen
            if (gp.fullScreenOn) {
                bw.write("On");
            }
            if (!gp.fullScreenOn) {
                bw.write("Off");
            }
            bw.newLine();
            // Music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();
            // SE volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try (BufferedReader br = new BufferedReader(new FileReader("gameConfig.txt"))) {
            // Full screen
            String s = br.readLine();
            if (s.equals("On")) {
                gp.fullScreenOn = true;
            }
            if (s.equals("Off")) {
                gp.fullScreenOn = false;
            }
            // Music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);
            // SE volume
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
