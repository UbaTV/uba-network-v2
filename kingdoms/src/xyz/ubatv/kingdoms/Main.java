package xyz.ubatv.kingdoms;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.kingdoms.mysql.MySQLConnection;
import xyz.ubatv.kingdoms.mysql.MySQLYML;

public class Main extends JavaPlugin {

    public static Main instance;

    public MySQLYML mySQLYML;
    public MySQLConnection mySQL;

    @Override
    public void onEnable() {
        setInstance(this);

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
    }

    private void registerEvents(){

    }

    private void registerCommands(){

    }

    public void preload(){
        mySQLYML = new MySQLYML();
        mySQLYML.loadConfig();
        mySQL = new MySQLConnection(
                mySQLYML.getConfig().getString("hostname"),
                mySQLYML.getConfig().getInt("port"),
                mySQLYML.getConfig().getString("username"),
                mySQLYML.getConfig().getString("password"),
                mySQLYML.getConfig().getString("database")
        );
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
