package xyz.ubatv.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.kingdoms.mysql.MySQLConnection;
import xyz.ubatv.kingdoms.mysql.MySQLYML;
import xyz.ubatv.kingdoms.playerData.PlayerDataManager;
import xyz.ubatv.kingdoms.playerData.PlayerDataTable;
import xyz.ubatv.kingdoms.rankSystem.RankManager;
import xyz.ubatv.kingdoms.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;
    public MySQLYML mySQLYML;
    public MySQLConnection mySQL;
    public PlayerDataTable playerDataTable;
    public PlayerDataManager playerDataManager;
    public RankManager rankManager;

    @Override
    public void onEnable() {
        setInstance(this);

        registerCommands();
        registerEvents();

        preload();
        bungeeChannels();
    }

    @Override
    public void onDisable() {
    }

    private void registerEvents(){

    }

    private void registerCommands(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerDataManager(), this);
    }

    public void preload(){
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
        playerDataTable = new PlayerDataTable();
        playerDataManager = new PlayerDataManager();
        rankManager = new RankManager();
    }

    private void bungeeChannels(){
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public MySQLConnection getMySQL() {
        return mySQL;
    }

    public void setMySQL(MySQLConnection mySQL) {
        this.mySQL = mySQL;
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
