package xyz.ubatv.kingdoms.mysql;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class MySQLYML {

    private Main main = Main.getInstance();

    private File file;
    private FileConfiguration config;

    public void loadConfig(){
        file = new File(main.getDataFolder(), "mysql.yml");

        if(!file.exists()){
            file.getParentFile().mkdirs();
            FileUtils.copy(main.getResource("mysql.yml"), file);
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
