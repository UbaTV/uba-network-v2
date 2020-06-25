package xyz.ubatv.hub.bank;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.hub.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerBankManager implements Listener {

    private Main main = Main.getInstance();

    public Map<UUID, PlayerBank> playerBank = new HashMap<>();

    public boolean playerBankExists(UUID uuid){
        return main.playerBankManager.playerBank.containsKey(uuid);
    }

    public PlayerBank getPlayerBank(UUID uuid){
        return main.playerBankManager.playerBank.get(uuid);
    }

    public void createPlayerBank(UUID uuid){
        PlayerBank data = new PlayerBank(uuid,
                getServerCoins(uuid),
                getPvECoins(uuid));
        main.playerBankManager.playerBank.put(uuid, data);
    }

    public int getServerCoins(UUID uuid){
        if(playerBankExists(uuid)) return getPlayerBank(uuid).getServerCoins();
        else return main.bankTable.getServerCoins(uuid);
    }

    public void setServerCoins(UUID uuid, int coins){
        if(playerBankExists(uuid)){
            getPlayerBank(uuid).setServerCoins(coins);
        } else{
            main.bankTable.setServerCoins(uuid, coins);
        }
    }

    public int getPvECoins(UUID uuid){
        if(playerBankExists(uuid)) return getPlayerBank(uuid).getPveCoins();
        else return main.bankTable.getPvECoins(uuid);
    }

    public void setPvECoins(UUID uuid, int coins){
        if(playerBankExists(uuid)){
            getPlayerBank(uuid).setPveCoins(coins);
        } else{
            main.bankTable.setPvECoins(uuid, coins);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        main.playerDataTable.createPlayer(player);
        createPlayerBank(uuid);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(playerBankExists(uuid)){
            PlayerBank data = getPlayerBank(uuid);
            main.playerBankManager.playerBank.remove(uuid);
            data.update();
        }
    }
}
