package plugin.minecraftplugin.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

/*
Diese Datei ist nicht von @Mazedias, Sie ist ein Mitschrieb von einem Tutorial als Lernzweck
*/

public class FileConfig extends YamlConfiguration {

    private String path; // Pfad

    public FileConfig(String folder, String filename) {
        this.path = "plugins/" + folder + "/" + filename;
        try {
            load(this.path);
        }catch (InvalidConfigurationException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public FileConfig(String filename) {
        this("LobbySystem", filename);
    }

    public void saveConfig() {
        try {
            save(this.path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
