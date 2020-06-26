package xyz.ubatv.pve;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.pve.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;

    @Override
    public void onEnable() {
        setInstance(this);

        registerCommands();
        registerEvents();

        preload();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands(){

    }

    private void registerEvents(){

    }

    private void preload(){
        textUtils = new TextUtils();
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
