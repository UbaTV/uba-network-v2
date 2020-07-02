package xyz.ubatv.pve;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.pve.bank.BankTable;
import xyz.ubatv.pve.bank.PlayerBankManager;
import xyz.ubatv.pve.commands.SelfRevive;
import xyz.ubatv.pve.events.DeathEvent;
import xyz.ubatv.pve.events.EntityDamage;
import xyz.ubatv.pve.events.JoinQuitEvent;
import xyz.ubatv.pve.game.*;
import xyz.ubatv.pve.location.LocationManager;
import xyz.ubatv.pve.location.LocationYML;
import xyz.ubatv.pve.location.SetLocationCommand;
import xyz.ubatv.pve.mysql.MySQLConnection;
import xyz.ubatv.pve.mysql.MySQLYML;
import xyz.ubatv.pve.playerData.PlayerDataManager;
import xyz.ubatv.pve.playerData.PlayerDataTable;
import xyz.ubatv.pve.playerData.PvETable;
import xyz.ubatv.pve.rankSystem.ChatFormatter;
import xyz.ubatv.pve.rankSystem.RankManager;
import xyz.ubatv.pve.scoreboard.ScoreboardHelper;
import xyz.ubatv.pve.scoreboard.ScoreboardManager;
import xyz.ubatv.pve.utils.TextUtils;

import java.io.File;
import java.util.Objects;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;
    public MySQLYML mySQLYML;
    public MySQLConnection mySQL;
    public PlayerDataManager playerDataManager;
    public PlayerDataTable playerDataTable;
    public RankManager rankManager;
    public PvETable pveTable;
    public BankTable bankTable;
    public PlayerBankManager playerBankManager;
    public LocationYML locationYML;
    public LocationManager locationManager;
    public GameManager gameManager;
    public PlayerHandler playerHandler;
    public MobSpawning mobSpawning;
    public WorldReset worldReset;

    // TODO Buffers and Shop
    // TODO Self Revive and Team Revive

    @Override
    public void onEnable() {
        setInstance(this);

        registerCommands();
        registerEvents();

        preload();
        bungeeCommunication();

        gameManager.preloadGame();
        gameManager.startLobby();

        updateScoreboards();
    }

    @Override
    public void onDisable() {
        mySQLYML.unload();
        locationYML.unload();

        // Move players to HUB
        if(Bukkit.getOnlinePlayers().size() != 0){
            for(Player player : Bukkit.getOnlinePlayers()){
                playerHandler.connectToHub(player.getUniqueId());
            }
        }
        Bukkit.getServer().unloadWorld(Objects.requireNonNull(Bukkit.getWorld("world")), false);
    }

    private void registerCommands(){
        getCommand("setlocation").setExecutor(new SetLocationCommand());
        getCommand("selfrevive").setExecutor(new SelfRevive());
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatFormatter(), this);
        pluginManager.registerEvents(new PlayerDataManager(), this);
        pluginManager.registerEvents(new PlayerBankManager(), this);
        pluginManager.registerEvents(new JoinQuitEvent(), this);
        pluginManager.registerEvents(new MobSpawning(), this);
        pluginManager.registerEvents(new EntityDamage(), this);
        pluginManager.registerEvents(new DeathEvent(), this);
        pluginManager.registerEvents(new ScoreboardManager(), this);
    }

    private void preload(){

        worldReset = new WorldReset();
        File file = Bukkit.getWorld("world").getWorldFolder();
        WorldReset.unloadWorld(Bukkit.getWorld("world"));
        File template = new File("./template", ".");
        WorldReset.deleteWorld(file);
        WorldReset.copyFileStructure(template, file);
        new WorldCreator("world").createWorld();

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
        bankTable = new BankTable();
        playerBankManager = new PlayerBankManager();
        locationYML = new LocationYML();
        locationYML.loadConfig();
        locationManager = new LocationManager();
        gameManager = new GameManager();
        playerHandler = new PlayerHandler();
        mobSpawning = new MobSpawning();
    }

    private void updateScoreboards(){
        new BukkitRunnable(){
            @Override
            public void run() {
                String gameState;
                int minLeft = ScoreboardManager.dayTime / 60;
                int secLeft = ScoreboardManager.dayTime % 60;
                if(gameManager.gameStatus.equals(GameStatus.WAITING)) gameState = "§eWaiting for players";
                else if(gameManager.gameStatus.equals(GameStatus.STARTING)) gameState = "§aStarting";
                else if(gameManager.gameStatus.equals(GameStatus.ENDED)
                        || gameManager.gameStatus.equals(GameStatus.RESTARTING)) gameState = "§cEnded";
                else if(gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)) gameState = "§7Mobs Left§8: " + gameManager.mobsToKill;
                else if(gameManager.gameStatus.equals(GameStatus.ROUND_DAY)) gameState = "§7Time Left§8: " + minLeft + "m" + secLeft + "s";
                else gameState = "This is a bug. Report to staff";

                for(Player player : Bukkit.getOnlinePlayers()){
                    if(ScoreboardHelper.hasScoreboard(player)){
                        // Tab Header and Footer
                        int online = Bukkit.getServer().getOnlinePlayers().size();
                        int max = Bukkit.getServer().getMaxPlayers();
                        player.setPlayerListHeaderFooter(
                                "\n" + textUtils.serverName + "\n" +
                                        "§aOnline: §5" + online + "§7/§5" + max + "\n",
                                "\n§7Website: §5" + textUtils.website + "\n");

                        ScoreboardHelper scoreboardHelper = ScoreboardHelper.getScoreboard(player);

                        // Sidebar Scoreboard
                        scoreboardHelper.setSlot(4, gameState);
                        scoreboardHelper.setSlot(3, "§7Coins: §5" + playerBankManager.playerBank.get(player.getUniqueId()).getGameCoins());
                    }
                }
            }
        }.runTaskTimer(this,  0, 20*3);
    }

    private void bungeeCommunication(){
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
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
