package xyz.ubatv.hub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.hub.bank.BankCommand;
import xyz.ubatv.hub.bank.BankTable;
import xyz.ubatv.hub.bank.PlayerBankManager;
import xyz.ubatv.hub.hotbar.HotbarManager;
import xyz.ubatv.hub.hotbar.SelectorGUI;
import xyz.ubatv.hub.mysql.MySQLConnection;
import xyz.ubatv.hub.mysql.MySQLYML;
import xyz.ubatv.hub.playerData.PlayerData;
import xyz.ubatv.hub.playerData.PlayerDataManager;
import xyz.ubatv.hub.playerData.PlayerDataTable;
import xyz.ubatv.hub.rankSystem.ChatFormatter;
import xyz.ubatv.hub.rankSystem.RankCommand;
import xyz.ubatv.hub.rankSystem.RankManager;
import xyz.ubatv.hub.utils.ItemAPI;
import xyz.ubatv.hub.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;

    public MySQLConnection mySQL;
    public MySQLYML mySQLYML;
    public RankManager rankManager;
    public PlayerDataTable playerDataTable;
    public PlayerDataManager playerDataManager;
    public TextUtils textUtils;
    public ItemAPI itemAPI;
    public PlayerBankManager playerBankManager;
    public BankTable bankTable;

    @Override
    public void onEnable() {
        setInstance(this);

        registerEvents();
        registerCommands();

        preLoad();

        for(Player online : Bukkit.getOnlinePlayers()){
            playerDataManager.createPlayerData(online.getUniqueId());
        }
    }

    @Override
    public void onDisable() {
        for(PlayerData online : playerDataManager.playerData.values()){
            online.update();
        }

        mySQLYML.unload();
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerDataManager(), this);
        pluginManager.registerEvents(new PlayerBankManager(), this);
        pluginManager.registerEvents(new ChatFormatter(), this);
        pluginManager.registerEvents(new HotbarManager(), this);
        pluginManager.registerEvents(new SelectorGUI(), this);
    }

    private void registerCommands(){
        getCommand("test").setExecutor(new TestCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("bank").setExecutor(new BankCommand());
    }

    private void preLoad(){
        mySQLYML = new MySQLYML();
        mySQLYML.loadConfig();

        bankTable = new BankTable();
        textUtils = new TextUtils();
        itemAPI = new ItemAPI();
        playerDataManager = new PlayerDataManager();
        rankManager = new RankManager();
        playerDataTable = new PlayerDataTable();
        playerBankManager = new PlayerBankManager();
        mySQL = new MySQLConnection(
                mySQLYML.getConfig().getString("hostname"),
                mySQLYML.getConfig().getInt("port"),
                mySQLYML.getConfig().getString("username"),
                mySQLYML.getConfig().getString("password"),
                mySQLYML.getConfig().getString("database")
        );
    }

    public MySQLConnection getMySQL() {
        return mySQL;
    }

    public void setMySQL(MySQLConnection mySQL) {
        this.mySQL = mySQL;
    }

    public MySQLYML getMySQLConfig() {
        return mySQLYML;
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
