package xyz.ubatv.pve.bank;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.pve.Main;

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
                getPvECoins(uuid),
                getGameCoins(uuid));
        main.playerBankManager.playerBank.put(uuid, data);
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

    public int getGameCoins(UUID uuid){
        if(playerBankExists(uuid)) return getPlayerBank(uuid).getGameCoins();
        else return 0;
    }

    public void setGameCoins(UUID uuid, int coins){
        if(playerBankExists(uuid)){
            getPlayerBank(uuid).setGameCoins(coins);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        main.bankTable.createPlayer(player);
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
