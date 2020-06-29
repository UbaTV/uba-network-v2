package xyz.ubatv.pve.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.pve.Main;
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

    public Location lobby = null;
    public Location game = null;

    public GameStatus gameStatus = GameStatus.WAITING;
    public int currentRound = 0;
    public int mobsToKill = 0;

    public ArrayList<UUID> waiting = new ArrayList<>();
    public ArrayList<UUID> alive = new ArrayList<>();
    public ArrayList<UUID> dead = new ArrayList<>();
    public ArrayList<UUID> spectating = new ArrayList<>();

    public void preloadGame(){
        lobby = main.locationManager.getLocation("lobby");
        game = main.locationManager.getLocation("game");
        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof Player) && entity instanceof LivingEntity) {
                entity.remove();
            }
        }
    }

    public void startLobby(){
        changeGameState(GameStatus.WAITING);

        new BukkitRunnable() {
            int countdown = 10;
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
        currentRound = 1;
        for(UUID uuid : alive){
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.sendTitle("", "§5Day §7Time", 10, 20, 10);
            TextUtils.sendActionBarMessage(player, "§7Gather resources to §5§nfight §7at night.");
            player.teleport(game);
        }
        for(UUID uuid : spectating) {
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(game);
        }
        Bukkit.broadcastMessage("  §8> §7Game Started");
        Bukkit.broadcastMessage("    §7Round §5§l1");

        new BukkitRunnable() {
            int dayTime = timeDay;
            @Override
            public void run() {
                if(alive.size() == 0){
                    this.cancel();
                    endGame(false);
                }else{
                    // DAY TIME
                    if(gameStatus.equals(GameStatus.ROUND_DAY)){
                        if(dayTime <= 0){
                            Bukkit.broadcastMessage("§7Day time ended");
                            changeGameState(GameStatus.ROUND_NIGHT);
                            mobsToKill = MobSpawning.mobsAtRound(currentRound);
                            sendDayNightTitle(false);
                        }else{
                            if(dayTime == 60 || dayTime == 30 || dayTime == 10 || dayTime <= 5){
                                Bukkit.broadcastMessage("§5§l" + dayTime + " §7seconds until night time");
                            }
                        }
                        dayTime--;
                    // NIGHT TIME
                    }else{
                        if(mobsToKill <= 0){
                            if(currentRound == 5){
                                this.cancel();
                                endGame(true);
                            }else{
                                changeGameState(GameStatus.ROUND_DAY);
                                currentRound++;
                                Bukkit.broadcastMessage("    §7Round §5§l" + currentRound);
                                sendDayNightTitle(true);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(main, 0, 20);
    }

    public void endGame(boolean victory){
        Bukkit.broadcastMessage("  §8> §7Game Ended");
        Bukkit.broadcastMessage("    §7Survived Rounds§8: §5§l" + (victory ? 5 : currentRound-1));

        for(Player player : Bukkit.getOnlinePlayers()){
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(game);
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
    }

    // day (true) night (false)
    public void sendDayNightTitle(boolean dayNight){
        if(dayNight){
            for(UUID uuid : alive){
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                player.sendTitle("", "§5Day §7Time", 10, 20, 10);
                TextUtils.sendActionBarMessage(player, "§7Gather resources to §5§nfight §7at night.");
            }
            for(UUID uuid : dead){
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                player.sendTitle("", "§5Day §7Time", 10, 20, 10);
                TextUtils.sendActionBarMessage(player, "§7Gather resources to §5§nfight §7at night.");
            }
        }else{
            for(UUID uuid : alive){
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                player.sendTitle("", "§5Night §7Time", 10, 20, 10);
                TextUtils.sendActionBarMessage(player, "§7Time to§5§nfight");
            }
            for(UUID uuid : dead){
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                player.sendTitle("", "§5Night §7Time", 10, 20, 10);
                TextUtils.sendActionBarMessage(player, "§7Time to§5§nfight");
            }
        }
    }

    public void changeGameState(GameStatus gameState){
        if(gameStatus != gameState){
            main.gameManager.gameStatus = gameState;
            // TODO Send game state change to bungee server
        }
    }

}
