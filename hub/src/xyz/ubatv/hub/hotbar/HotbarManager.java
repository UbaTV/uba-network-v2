package xyz.ubatv.hub.hotbar;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

import java.util.UUID;

public class HotbarManager implements Listener {

    private Main main = Main.getInstance();

    private SelectorGUI selectorGUI = new SelectorGUI();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        player.getInventory().clear();
        if(!main.playerDataManager.hasPermission(uuid, Ranks.ADMIN)) player.setGameMode(GameMode.ADVENTURE);

        ItemStack gameSelector = gameSelector();
        player.getInventory().setItem(4, gameSelector);
        ItemStack hidePlayers = hidePlayers(main.playerDataManager.getPlayersHidden(uuid));
        player.getInventory().setItem(8, hidePlayers);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getItem() == null) return;

        if(event.getItem().getType() == Material.COMPASS){
            Player player = event.getPlayer();
            selectorGUI.openInventory(player);
        }

        if(event.getItem().getType() == Material.REDSTONE){
            Player player = event.getPlayer();
            boolean visibility = main.playerDataManager.getPlayersHidden(player.getUniqueId());
            if(visibility){
                for(Player target : Bukkit.getOnlinePlayers()){
                    player.showPlayer(main, target);
                }
            }else{
                for(Player target : Bukkit.getOnlinePlayers()){
                    player.hidePlayer(main, target);
                }
            }
            player.sendMessage(main.textUtils.right + "All players are now " + (visibility ? "§avisible" : "§chidden"));
            main.playerDataManager.setPlayersHidden(player.getUniqueId(), !visibility);
            player.getInventory().setItem(8, hidePlayers(!visibility));
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();
        if(item.getType() == Material.COMPASS) event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();
    }

    public ItemStack gameSelector(){
        return main.itemAPI.item(Material.COMPASS, "§5§lGame §7Selector", "§7Right-click to open this menu.");
    }

    public ItemStack hidePlayers(boolean hidden){
        ItemStack vanish = main.itemAPI.item(Material.REDSTONE, "§7Visibility: §c§lOff", "§7Right-click to §nshow§r§7 all players");
        ItemStack show = main.itemAPI.item(Material.GLOWSTONE_DUST, "§7Visibility: §a§lOn", "§7Right-click to §nhide§r§7 all players");
        return hidden ? vanish : show;
    }
}
