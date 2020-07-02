package xyz.ubatv.hub.hotbar;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

import java.util.ArrayList;
import java.util.UUID;

public class HotbarManager implements Listener {

    // TODO Add cooldown to player visibility - redo

    private Main main = Main.getInstance();

    private SelectorGUI selectorGUI = new SelectorGUI();
    private ArrayList<Player> hidden = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        player.getInventory().clear();
        if(!main.playerDataManager.hasPermission(uuid, Ranks.ADMIN)) player.setGameMode(GameMode.ADVENTURE);

        ItemStack serverInfo = serverInfo();
        ItemStack gameSelector = gameSelector();
        ItemStack hidePlayers = hidePlayers(!main.playerDataManager.getPlayersHidden(uuid));

        player.getInventory().setItem(0, serverInfo);
        player.getInventory().setItem(4, gameSelector);
        player.getInventory().setItem(8, hidePlayers);

        toggleVisibility(player);
        if(!hidden.isEmpty()){
            for(Player target : hidden){
                target.hidePlayer(main, player);
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;

        if(event.getItem().getType() == serverInfo().getType()){
            sendServerInfo(event.getPlayer());
            return;
        }

        if(event.getItem().getType() == gameSelector().getType()){
            selectorGUI.openInventory(event.getPlayer());
            return;
        }

        if(event.getItem().getType() == Material.REDSTONE || event.getItem().getType() == Material.GLOWSTONE_DUST){
            Player player = event.getPlayer();
            boolean visibility = main.playerDataManager.getPlayersHidden(player.getUniqueId());
            toggleVisibility(player);
            player.sendMessage(main.textUtils.right + "All players are now " + (visibility ? "§avisible" : "§chidden"));
            if(!visibility) hidden.add(player);
            main.playerDataManager.setPlayersHidden(player.getUniqueId(), !visibility);
            player.getInventory().setItem(8, hidePlayers(!visibility));
        }
    }

    private void toggleVisibility(Player player) {
        if(main.playerDataManager.getPlayersHidden(player.getUniqueId())){
            for(Player target : Bukkit.getOnlinePlayers()){
                player.showPlayer(main, target);
            }
        }else{
            for(Player target : Bukkit.getOnlinePlayers()){
                player.hidePlayer(main, target);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        HumanEntity player = event.getWhoClicked();
        if(event.getClickedInventory().equals(player.getInventory())) event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();
        if(item.getType() == Material.COMPASS) event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        hidden.remove(player);
    }

    private ItemStack serverInfo() {
        return main.itemAPI.item(Material.BOOK, "§5§lServer §7Info", "§7Right-click to show the server info.");
    }


    public ItemStack gameSelector(){
        return main.itemAPI.item(Material.COMPASS, "§5§lGame §7Selector", "§7Right-click to open this menu.");
    }

    public ItemStack hidePlayers(boolean hidden){
        ItemStack vanish = main.itemAPI.item(Material.REDSTONE, "§7Visibility: §c§lOff", "§7Right-click to §nshow§r§7 all players");
        ItemStack show = main.itemAPI.item(Material.GLOWSTONE_DUST, "§7Visibility: §a§lOn", "§7Right-click to §nhide§r§7 all players");
        return hidden ? vanish : show;
    }

    public void sendServerInfo(Player player){
        player.sendMessage(" ");
        main.textUtils.sendCenteredMessage(player, main.textUtils.serverName);
        player.sendMessage(" ");
        player.sendMessage("   §7Website: §5https://ubatv.xyz");
        player.sendMessage("   §7Discord: §5https://discord.gg/AJxFu2C");
        player.sendMessage("   §7Twitter: §5https://twitter.com/ubatvoficial");
        player.sendMessage(" ");
    }
}
