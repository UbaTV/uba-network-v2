package xyz.ubatv.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import xyz.ubatv.bungee.commands.HubCommand;
import xyz.ubatv.bungee.utils.TextUtils;

public class Main extends Plugin {

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
        getProxy().getPluginManager().registerCommand(this, new HubCommand());
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
