package xyz.ubatv.pve.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.playerData.PlayerData;

import java.util.ArrayList;
import java.util.Random;

public class MobSpawning implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onMobKill(EntityDeathEvent event){
        if(event.getEntity() instanceof Monster){
            Monster entity = (Monster) event.getEntity();
            if(entity.getKiller() != null){
                if(main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)){
                    main.gameManager.mobsToKill--;
                    Player player = entity.getKiller();
                    PlayerData playerData = main.playerDataManager.getPlayerData(player.getUniqueId());
                    playerData.incrementMobsKilled();
                    main.playerBankManager.addGameCoins(player.getUniqueId(), getKillReward(entity.getType()));
                }
            }
        }
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event){
        if(!main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)){
            if(event.getEntity() instanceof Monster){
                event.setCancelled(true);
            }
        }
    }

    public void startMobSpawn(){
        Random random = new Random();
        int locationSize = main.gameManager.mobSpawn.size();
        new BukkitRunnable(){
            @Override
            public void run() {
                if(main.gameManager.mobsToKill <= 0) {
                    this.cancel();
                }else{
                    for(int i = 0; i < numberOfSpawnsPerTime(main.gameManager.currentRound); i++){
                        ArrayList<EntityType> roundMobs = mobsRound(main.gameManager.currentRound);
                        int randomLocation = random.nextInt(locationSize);
                        int mobToSpawn = random.nextInt(roundMobs.size());
                        Location loc = main.gameManager.mobSpawn.get(randomLocation);
                        loc.getWorld().spawnEntity(loc, roundMobs.get(mobToSpawn));
                    }
                }
            }
        }.runTaskTimer(main, 0, mobSpawnDelay(main.gameManager.currentRound) * 20);
    }

    public ArrayList<EntityType> mobsRound(int round){
        ArrayList<EntityType> mobs = new ArrayList<>();
        mobs.add(EntityType.ZOMBIE);
        mobs.add(EntityType.SKELETON);
        if(round >= 2){
            mobs.add(EntityType.CREEPER);
        }
        if(round >= 3){
            mobs.add(EntityType.WITCH);
        }
        if(round >= 4){
            mobs.add(EntityType.BLAZE);
        }

        if(round >= 5){
            mobs.add(EntityType.WITHER_SKELETON);
        }

        return mobs;
    }

    public int numberOfSpawnsPerTime(int round){
        if (round == 2) return 2;
        else if (round == 3) return 3;
        else if (round == 4) return 3;
        else if (round == 5) return 4;
        else return 2;
    }

    // return in seconds
    public int mobSpawnDelay(int round){
        if (round == 2) return 3;
        else if (round == 3) return 3;
        else if (round == 4) return 2;
        else if (round == 5) return 2;
        else return 4;
    }

    public static int mobsAtRound(int round){
        if (round == 2) return 75;
        else if (round == 3) return 150;
        else if (round == 4) return 300;
        else if (round == 5) return 500;
        else return 25;
    }

    public int getKillReward(EntityType entityType){
        if(entityType.equals(EntityType.ZOMBIE)
                || entityType.equals(EntityType.SKELETON)) return 5;
        else if(entityType.equals(EntityType.CREEPER)) return 10;
        else if(entityType.equals(EntityType.WITCH)) return 15;
        else if(entityType.equals(EntityType.BLAZE)) return 25;
        else if(entityType.equals(EntityType.WITHER_SKELETON)) return 50;
        else return 10;
    }
}
