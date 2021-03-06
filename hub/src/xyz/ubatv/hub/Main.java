package xyz.ubatv.hub;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.hub.bank.BankCommand;
import xyz.ubatv.hub.bank.BankTable;
import xyz.ubatv.hub.bank.PlayerBankManager;
import xyz.ubatv.hub.commands.BuildMode;
import xyz.ubatv.hub.events.HealthFoodManager;
import xyz.ubatv.hub.events.JoinQuitEvent;
import xyz.ubatv.hub.hotbar.HotbarManager;
import xyz.ubatv.hub.hotbar.gameSelector.GameSelectorGUI;
import xyz.ubatv.hub.hotbar.gameSelector.PvEStatus;
import xyz.ubatv.hub.hotbar.gameSelector.ServersYML;
import xyz.ubatv.hub.hotbar.profile.ProfileManager;
import xyz.ubatv.hub.hotbar.profile.collectibles.CollectiblesManager;
import xyz.ubatv.hub.hotbar.profile.collectibles.CollectiblesTable;
import xyz.ubatv.hub.hotbar.profile.collectibles.trails.TrailsManager;
import xyz.ubatv.hub.hotbar.store.StoreManager;
import xyz.ubatv.hub.location.LocationManager;
import xyz.ubatv.hub.location.LocationYML;
import xyz.ubatv.hub.location.SetLocationCommand;
import xyz.ubatv.hub.mysql.MySQLConnection;
import xyz.ubatv.hub.mysql.MySQLYML;
import xyz.ubatv.hub.playerData.PlayerData;
import xyz.ubatv.hub.playerData.PlayerDataManager;
import xyz.ubatv.hub.playerData.PlayerDataTable;
import xyz.ubatv.hub.playerData.PvETable;
import xyz.ubatv.hub.rankSystem.ChatFormatter;
import xyz.ubatv.hub.rankSystem.RankCommand;
import xyz.ubatv.hub.rankSystem.RankManager;
import xyz.ubatv.hub.scoreboard.ScoreboardHelper;
import xyz.ubatv.hub.scoreboard.ScoreboardManager;
import xyz.ubatv.hub.utils.BungeeUtils;
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
    public PvEStatus pveStatus;
    public BungeeUtils bungeeUtils;
    public ServersYML serversYML;
    public PvETable pveTable;
    public CollectiblesTable collectiblesTable;
    public LocationYML locationYML;
    public LocationManager locationManager;
    public HotbarManager hotbarManager;

    @Override
    public void onEnable() {
        setInstance(this);

        registerEvents();
        registerCommands();

        bungeeChannels();

        preLoad();

        for(Player online : Bukkit.getOnlinePlayers()){
            playerDataManager.createPlayerData(online.getUniqueId());
        }

        updateScoreboards();
        pveStatus.checkServers();
    }

    @Override
    public void onDisable() {
        for(PlayerData online : playerDataManager.playerData.values()){
            online.update();
        }

        mySQLYML.unload();
        serversYML.unload();
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new JoinQuitEvent(), this);
        pluginManager.registerEvents(new PlayerDataManager(), this);
        pluginManager.registerEvents(new PlayerBankManager(), this);
        pluginManager.registerEvents(new ChatFormatter(), this);
        pluginManager.registerEvents(new HotbarManager(), this);
        pluginManager.registerEvents(new GameSelectorGUI(), this);
        pluginManager.registerEvents(new ScoreboardManager(), this);
        pluginManager.registerEvents(new HealthFoodManager(), this);
        pluginManager.registerEvents(new StoreManager(), this);
        pluginManager.registerEvents(new ProfileManager(), this);
        pluginManager.registerEvents(new CollectiblesManager(), this);
        pluginManager.registerEvents(new BuildMode(), this);
        pluginManager.registerEvents(new TrailsManager(), this);
    }

    private void registerCommands(){
        getCommand("test").setExecutor(new TestCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("bank").setExecutor(new BankCommand());
        getCommand("buildmode").setExecutor(new BuildMode());
        getCommand("setlocation").setExecutor(new SetLocationCommand());
    }

    private void preLoad(){
        World world = Bukkit.getWorld("world");
        assert world != null;
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof Player) && entity instanceof LivingEntity) {
                entity.remove();
            }
        }

        mySQLYML = new MySQLYML();
        mySQLYML.loadConfig();
        serversYML = new ServersYML();
        serversYML.loadConfig();
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
        pveStatus = new PvEStatus();
        bungeeUtils = new BungeeUtils();
        pveTable = new PvETable();
        collectiblesTable = new CollectiblesTable();
        locationYML = new LocationYML();
        locationYML.loadConfig();
        locationManager = new LocationManager();
        hotbarManager = new HotbarManager();
    }

    private void updateScoreboards(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(ScoreboardHelper.hasScoreboard(player)){
                        // Tab Header and Footer
                        int online = Bukkit.getServer().getOnlinePlayers().size();
                        int max = Bukkit.getServer().getMaxPlayers();
                        player.setPlayerListHeaderFooter(
                                "\n" + textUtils.serverName + "\n" +
                                        "§aOnline: §5" + online + "§7/§5" + max + "\n",
                                "\n§7Website: §5" + textUtils.website + "\n");

                        PlayerData playerData = playerDataManager.getPlayerData(player.getUniqueId());
                        ScoreboardHelper scoreboardHelper = ScoreboardHelper.getScoreboard(player);

                        // Sidebar Scoreboard
                        scoreboardHelper.setSlot(4, "§6| §7Coins: §5" + playerBankManager.getServerCoins(player.getUniqueId()) + textUtils.coinsSymbol);
                        scoreboardHelper.setSlot(3, "§a| §7Rank: " + rankManager.rankName(playerData.getRank()));
                    }
                }
            }
        }.runTaskTimer(this,  0, 20*3);
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
