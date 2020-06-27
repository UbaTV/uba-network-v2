package xyz.ubatv.pve.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import xyz.ubatv.pve.Main;

import java.util.UUID;

public class PlayerHandler {

    private Main main = Main.getInstance();

    public void spectateGame(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        removeFromLists(uuid);
        main.gameManager.spectating.add(uuid);
        assert player != null;
        player.setGameMode(GameMode.SPECTATOR);
    }

    /* TODO Implement the rest of the GameStates
       (Currently just WAITING state) */
    public void joinGame(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        removeFromLists(uuid);
        main.gameManager.waiting.add(uuid);
        assert player != null;
        player.setGameMode(GameMode.ADVENTURE);
    }

    public void removeFromLists(UUID uuid){
        main.gameManager.waiting.remove(uuid);
        main.gameManager.alive.remove(uuid);
        main.gameManager.dead.remove(uuid);
        main.gameManager.spectating.remove(uuid);
    }

    public void connectToHub(UUID uuid){
        // TODO Send players to HUB server
    }
}
