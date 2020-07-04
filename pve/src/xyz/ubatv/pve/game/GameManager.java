package xyz.ubatv.pve.game;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.playerData.PlayerData;
import xyz.ubatv.pve.scoreboard.ScoreboardManager;
import xyz.ubatv.pve.utils.TextUtils;

import java.util.ArrayList;
import java.util.UUID;

public class GameManager {

    private Main main = Main.getInstance();

    public World world = Bukkit.getServer().getWorld("world");

    public final int minPlayer = 2;
    public final int maxPlayer = 4;
    public final int totalRounds = 5;
    public final int timeDay = 60*5; // 5 minutes
    public final long dayTicks = 6000;
    public final long nightTicks = 18000;

    public Location lobby = null;
    public Location game = null;
    public ArrayList<Location> mobSpawn = new ArrayList<>();

    public GameStatus gameStatus = GameStatus.WAITING;
    public int currentRound = 0;
    public int mobsToKill = 0;

    public ArrayList<UUID> waiting = new ArrayList<>();
    public ArrayList<UUID> alive = new ArrayList<>();
    public ArrayList<UUID> dead = new ArrayList<>();
    public ArrayList<UUID> spectating = new ArrayList<>();

    public void preloadGame(){
        // Define required locations
        main.gameManager.lobby = main.locationManager.getLocation("lobby");
        main.gameManager.game = main.locationManager.getLocation("game");
        for(String name : main.locationYML.getConfig().getConfigurationSection("").getKeys(false)){
            if(name.length() >= 8){
                if(name.substring(0, 8).equalsIgnoreCase("mobspawn")){
                    Location loc = main.locationManager.getLocation(name);
                    main.gameManager.mobSpawn.add(loc);
                }
            }
        }

        // World settings
        clearMobs();
        main.gameManager.world = main.gameManager.lobby.getWorld();
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    public void startLobby(){
        changeGameState(GameStatus.WAITING);
        main.gameManager.world.setTime(dayTicks);

        new BukkitRunnable() {
            int countdown = 5;
            @Override
            public void run() {
                if(main.gameManager.waiting.size() >= minPlayer){
                    if(countdown <= 0){
                        this.cancel();
                        startGame();
                    }else{
                        changeGameState(GameStatus.STARTING);
                        if(countdown == 30 || countdown == 20 || countdown == 10 || countdown <= 5){
                            Bukkit.broadcastMessage("§7Game will start in §5§l" + countdown + " §7seconds.");
                        }
                    }
                    countdown--;
                }else{
                    changeGameState(GameStatus.WAITING);
                    countdown = 10;
                }
            }
        }.runTaskTimer(main, 0, 20);
    }

    public void startGame(){
        changeGameState(GameStatus.ROUND_DAY);
        main.gameManager.currentRound = 1;
        for(int i = 0; i < main.gameManager.waiting.size(); i++){
            UUID uuid = main.gameManager.waiting.get(i);
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.sendTitle("", "§5Day §7Time", 10, 20*2, 10);
            TextUtils.sendActionBarMessage(player, "§7Gather resources to §5§nfight §7at night.");
            main.gameManager.alive.add(uuid);
            player.teleport(game);
        }
        main.gameManager.waiting.clear();

        for(UUID uuid : spectating) {
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(game);
        }

        Bukkit.broadcastMessage("  §8> §7Game Started");
        Bukkit.broadcastMessage("    §7Round §5§l1");

        new BukkitRunnable(){
            @Override
            public void run() {
                new BukkitRunnable() {
                    int dayTime = timeDay;
                    @Override
                    public void run() {
                        if(main.gameManager.alive.size() == 0){
                            cancel();
                            endGame(false);
                        }else{
                            // DAY TIME
                            if(gameStatus.equals(GameStatus.ROUND_DAY)){
                                if(dayTime <= 0){
                                    Bukkit.broadcastMessage(" §5§l> §7Day time ended");
                                    changeGameState(GameStatus.ROUND_NIGHT);
                                    main.gameManager.mobsToKill = MobSpawning.mobsAtRound(currentRound);
                                    sendDayNightTitle(false);
                                    main.gameManager.world.setTime(nightTicks);
                                    dayTime = timeDay;
                                    main.mobSpawning.startMobSpawn();
                                }else{
                                    if(dayTime == 60 || dayTime == 30 || dayTime == 10 || dayTime <= 5){
                                        Bukkit.broadcastMessage("§5§l" + dayTime + " §7seconds until night time");
                                    }
                                }
                                dayTime--;
                                ScoreboardManager.dayTime--;
                                // NIGHT TIME
                            }else{
                                if(main.gameManager.mobsToKill <= 0){
                                    main.gameManager.world.setTime(dayTicks);
                                    if(main.gameManager.currentRound == totalRounds){
                                        this.cancel();
                                        endGame(true);
                                    }else{
                                        changeGameState(GameStatus.ROUND_DAY);
                                        main.gameManager.currentRound++;
                                        ScoreboardManager.dayTime = timeDay;
                                        Bukkit.broadcastMessage("    §7Round §5§l" + main.gameManager.currentRound);
                                        sendDayNightTitle(true);
                                        clearMobs();
                                    }
                                }
                            }
                        }
                    }
                }.runTaskTimer(main, 0, 20);
            }
        }.runTaskLater(main, 20);
    }

    public void clearMobs(){
        for (Entity entity : main.gameManager.world.getEntities()) {
            if (!(entity instanceof Player) && entity instanceof LivingEntity) {
                entity.remove();
            }
        }
    }

    public void endGame(boolean victory){
        Bukkit.broadcastMessage("  §8> §7Game Ended");
        Bukkit.broadcastMessage("    §7Survived Rounds§8: §5§l" + (victory ? 5 : main.gameManager.currentRound-1));

        for(Player player : Bukkit.getOnlinePlayers()){
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(main.gameManager.game);
            PlayerData playerData = main.playerDataManager.getPlayerData(player.getUniqueId());
            int pveCoinsReward = (main.gameManager.currentRound - 1) * 20 + ((int) (playerData.getMobsKilled() * 0.9));
            player.sendMessage(main.textUtils.right + "You won §5" + pveCoinsReward + " PvE§7 coins.");
            main.bankTable.addPvECoins(player.getUniqueId(), pveCoinsReward);
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                resetGame();
            }
        }.runTaskLater(main, 20*5);
    }

    public void resetGame(){
        for(Player player : Bukkit.getOnlinePlayers()){
            main.playerHandler.connectToHub(player.getUniqueId());
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                // builtin:restart is a command built-in MultiCraft
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "builtin:restart");
            }
        }.runTaskLater(main, 20*3);
    }

    // day (true) night (false)
    public void sendDayNightTitle(boolean dayNight){
        if(dayNight){
            for(UUID uuid : alive){
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                player.sendTitle("", "§5Day §7Time", 10, 20, 10);
                TextUtils.sendActionBarMessage(player, "§7Gather resources to §5§nfight§7 at night.");
            }
            for(UUID uuid : dead){
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                player.sendTitle("", "§5Day §7Time", 10, 20, 10);
                TextUtils.sendActionBarMessage(player, "§7Gather resources to §5§nfight§7 at night.");
            }
        }else{
            for(UUID uuid : alive){
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                player.sendTitle("", "§5Night §7Time", 10, 20, 10);
                TextUtils.sendActionBarMessage(player, "§7Time to §5§nfight");
            }
            for(UUID uuid : dead){
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                player.sendTitle("", "§5Night §7Time", 10, 20, 10);
                TextUtils.sendActionBarMessage(player, "§7Time to §5§nfight");
            }
        }
    }

    public void changeGameState(GameStatus gameState){
        if(gameStatus != gameState){
            main.gameManager.gameStatus = gameState;
        }
    }
}
