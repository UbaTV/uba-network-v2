package xyz.ubatv.hub.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.ubatv.hub.Main;

import java.util.UUID;

public class BungeeUtils {

    private Main main = Main.getInstance();

    public void connectToServer(UUID uuid, String server){
        Player player = Bukkit.getPlayer(uuid);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        assert player != null;
        player.sendPluginMessage(main, "BungeeCord", out.toByteArray());
    }
}
