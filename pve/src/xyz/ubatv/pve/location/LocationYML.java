package xyz.ubatv.pve.location;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocationYML {

    private final Main main = Main.getInstance();

    private File file;
    private FileConfiguration config;

    public void loadConfig(){
        file = new File(main.getDataFolder(), "location.yml");

        if(!file.exists()){
            file.getParentFile().mkdirs();
            FileUtils.copy(main.getResource("location.yml"), file);
        }

        config = new YamlConfiguration();

        try{
            config.load(file);
        }catch (InvalidConfigurationException | IOException e){
            e.printStackTrace();
        }
    }

    public void unload(){
        try{
            config.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
