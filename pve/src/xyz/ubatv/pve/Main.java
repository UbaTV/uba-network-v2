package xyz.ubatv.pve;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.pve.mysql.MySQLConnection;
import xyz.ubatv.pve.mysql.MySQLYML;
import xyz.ubatv.pve.playerData.PlayerDataManager;
import xyz.ubatv.pve.playerData.PlayerDataTable;
import xyz.ubatv.pve.playerData.PvETable;
import xyz.ubatv.pve.rankSystem.ChatFormatter;
import xyz.ubatv.pve.rankSystem.RankManager;
import xyz.ubatv.pve.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;
    public MySQLYML mySQLYML;
    public MySQLConnection mySQL;
    public PlayerDataManager playerDataManager;
    public PlayerDataTable playerDataTable;
    public RankManager rankManager;
    public PvETable pveTable;

    @Override
    public void onEnable() {
        setInstance(this);

        registerCommands();
        registerEvents();

        preload();
    }

    @Override
    public void onDisable() {
        mySQLYML.unload();
    }

    private void registerCommands(){

    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatFormatter(), this);
    }

    private void preload(){
        textUtils = new TextUtils();
        mySQLYML = new MySQLYML();
        mySQLYML.loadConfig();
        mySQL = new MySQLConnection(
                mySQLYML.getConfig().getString("hostname"),
                mySQLYML.getConfig().getInt("port"),
                mySQLYML.getConfig().getString("username"),
                mySQLYML.getConfig().getString("password"),
                mySQLYML.getConfig().getString("database")
        );
        playerDataManager = new PlayerDataManager();
        playerDataTable = new PlayerDataTable();
        pveTable = new PvETable();
        rankManager = new RankManager();
    }

    public MySQLConnection getMySQL() {
        return mySQL;
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
