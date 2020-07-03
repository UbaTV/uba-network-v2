package xyz.ubatv.hub.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

import java.util.UUID;

public class JoinQuitEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        player.setHealth(20);
        player.setFoodLevel(20);

        event.setJoinMessage("§8[§a§l+§8] §5" + player.getName());

        if(!main.playerDataManager.hasPermission(uuid, Ranks.ADMIN)) player.setGameMode(GameMode.ADVENTURE);

        player.sendMessage(" ");
        main.textUtils.sendCenteredMessage(player, "§7Welcome to the " + main.textUtils.serverName + "§7!");
        player.sendMessage(" ");
        main.textUtils.sendCenteredMessage(player, "§7Website: §5ubatv.xyz");
        main.textUtils.sendCenteredMessage(player, "§7Discord: §5discord.gg/AJxFu2C");
        player.sendMessage(" ");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage("§8[§c§l-§8] §5" + player.getName());
    }
}
