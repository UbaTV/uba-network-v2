package xyz.ubatv.pve.playerData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.rankSystem.Ranks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager implements Listener {

    private Main main = Main.getInstance();

    public Map<UUID, PlayerData> playerData = new HashMap<>();

    public boolean playerDataExists(UUID uuid){
        return main.playerDataManager.playerData.containsKey(uuid);
    }

    public PlayerData getPlayerData(UUID uuid){
        return main.playerDataManager.playerData.get(uuid);
    }

    public void createPlayerData(UUID uuid){
        PlayerData data = new PlayerData(uuid,
                getRank(uuid),
                0,
                getSelfRevives(uuid),
                getTeamRevives(uuid),
                getMiningSpeed(uuid),
                getJumpBoost(uuid),
                getHasteBoost(uuid),
                getRegenBoost(uuid),
                getDamageBoost(uuid)); // TODO time online
        main.playerDataManager.playerData.put(uuid, data);
    }

    public Ranks getRank(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).getRank();
        else return main.playerDataTable.getRank(uuid);
    }

    public void setRank(UUID uuid, Ranks rank){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setRank(rank);
        } else{
            main.playerDataTable.setRank(uuid, rank);
        }
    }

    public int getSelfRevives(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).getSelfRevives();
        else return main.pveTable.getSelfRevives(uuid);
    }

    public void setSelfRevives(UUID uuid, int selfRevives){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setSelfRevives(selfRevives);
        } else{
            main.pveTable.setSelfRevives(uuid, selfRevives);
        }
    }

    public int getTeamRevives(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).getTeamRevives();
        else return main.pveTable.getTeamRevives(uuid);
    }

    public void setTeamRevives(UUID uuid, int teamRevives){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setTeamRevives(teamRevives);
        } else{
            main.pveTable.setTeamRevives(uuid, teamRevives);
        }
    }

    public int getMiningSpeed(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).getMiningSpeed();
        else return main.pveTable.getMiningSpeed(uuid);
    }

    public void setMiningSpeed(UUID uuid, int miningSpeed){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setMiningSpeed(miningSpeed);
        } else{
            main.pveTable.setMiningSpeed(uuid, miningSpeed);
        }
    }

    public int getJumpBoost(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).getJumpBoost();
        else return main.pveTable.getJumpBoost(uuid);
    }

    public void setJumpBoost(UUID uuid, int jumpBoost){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setJumpBoost(jumpBoost);
        } else{
            main.pveTable.setJumpBoost(uuid, jumpBoost);
        }
    }

    public int getHasteBoost(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).getHasteBoost();
        else return main.pveTable.getHasteBoost(uuid);
    }

    public void setHasteBoost(UUID uuid, int hasteBoost){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setHasteBoost(hasteBoost);
        } else{
            main.pveTable.setHasteBoost(uuid, hasteBoost);
        }
    }

    public int getRegenBoost(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).getRegenBoost();
        else return main.pveTable.getRegenBoost(uuid);
    }

    public void setRegenBoost(UUID uuid, int regenBoost){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setRegenBoost(regenBoost);
        } else{
            main.pveTable.setRegenBoost(uuid, regenBoost);
        }
    }

    public int getDamageBoost(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).getDamageBoost();
        else return main.pveTable.getDamageBoost(uuid);
    }

    public void setDamageBoost(UUID uuid, int damageBoost){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setDamageBoost(damageBoost);
        } else{
            main.pveTable.setDamageBoost(uuid, damageBoost);
        }
    }

    public boolean hasPermission(UUID uuid, Ranks neededRank){
        Ranks rank = getRank(uuid);
        int r = main.rankManager.rankToId(rank), n = main.rankManager.rankToId(neededRank);
        return r >= n;
    }

    public void updateTablistName(Player player){
        String rankName = main.rankManager.rankName(getRank(player.getUniqueId()));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        main.playerDataTable.createPlayer(player);
        createPlayerData(uuid);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(playerDataExists(uuid)){
            PlayerData data = getPlayerData(uuid);
            main.playerDataManager.playerData.remove(uuid);
            data.update();
        }

    }
}
