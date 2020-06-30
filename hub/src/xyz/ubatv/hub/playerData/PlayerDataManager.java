package xyz.ubatv.hub.playerData;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

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
                getPlayersHidden(uuid)); // TODO time online
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

    public boolean getPlayersHidden(UUID uuid){
        if(playerDataExists(uuid)) return getPlayerData(uuid).isPlayersHidden();
        else return main.playerDataTable.getPlayersHidden(uuid);
    }

    public void setPlayersHidden(UUID uuid, boolean hidden){
        if(playerDataExists(uuid)){
            getPlayerData(uuid).setPlayersHidden(hidden);
        } else{
            main.playerDataTable.setPlayersHidden(uuid, hidden);
        }
    }

    public boolean hasPermission(UUID uuid, Ranks neededRank){
        Ranks rank = getRank(uuid);
        int r = main.rankManager.rankToId(rank), n = main.rankManager.rankToId(neededRank);
        return r >= n;
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
