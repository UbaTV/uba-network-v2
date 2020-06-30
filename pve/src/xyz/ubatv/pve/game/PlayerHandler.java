package xyz.ubatv.pve.game;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
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

    public void removeFromLists(UUID uuid){
        main.gameManager.waiting.remove(uuid);
        main.gameManager.alive.remove(uuid);
        main.gameManager.dead.remove(uuid);
        main.gameManager.spectating.remove(uuid);
    }

    public void connectToHub(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("hub");
        assert player != null;
        player.sendPluginMessage(main, "BungeeCord", out.toByteArray());
    }
}
