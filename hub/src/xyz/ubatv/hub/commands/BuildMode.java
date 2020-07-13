package xyz.ubatv.hub.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

import java.util.ArrayList;
import java.util.List;

public class BuildMode implements CommandExecutor, Listener {

    private Main main = Main.getInstance();

    public static List<Player> buildMode = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.playerDataManager.hasPermission(player.getUniqueId(), Ranks.BUILDER)){
                if(buildMode.contains(player)){
                    buildMode.remove(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(main.textUtils.right + "You just §cleft §5Build Mode");
                }else{
                    buildMode.add(player);
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(main.textUtils.right + "You just §cleft §5Build Mode");
                }
            }else{
                player.sendMessage(main.textUtils.noPerms);
                return false;
            }
        }else{
            sender.sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        buildMode.remove(event.getPlayer());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(!buildMode.contains(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(!buildMode.contains(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void cancelExplode(BlockExplodeEvent event){
        event.setCancelled(true);
    }
}
