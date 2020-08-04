package xyz.ubatv.hub.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                    main.hotbarManager.giveHotbar(player);
                }else{
                    buildMode.add(player);
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(main.textUtils.right + "You just §ajoined §5Build Mode");
                    player.getInventory().clear();
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

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(!buildMode.contains(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupArrowEvent event){
        if(!buildMode.contains(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event){
        if(event.getEntity() instanceof Player){
            if(!buildMode.contains(event.getEntity()))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        HumanEntity player = event.getWhoClicked();
        if(buildMode.contains(event.getWhoClicked())) return;
        if(Objects.equals(event.getClickedInventory(), player.getInventory())) event.setCancelled(true);
    }

}
