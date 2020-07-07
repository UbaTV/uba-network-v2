package xyz.ubatv.hub.hotbar;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.hotbar.gameSelector.GameSelector;
import xyz.ubatv.hub.hotbar.profile.ProfileManager;
import xyz.ubatv.hub.hotbar.store.StoreManager;

import java.util.Objects;

public class HotbarManager implements Listener {

    private Main main = Main.getInstance();

    private GameSelector gameSelector = new GameSelector();
    private ServerInfo serverInfo = new ServerInfo();
    private Visibility visibility = new Visibility();
    private ProfileManager profile = new ProfileManager();
    private StoreManager store = new StoreManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.getInventory().clear();

        boolean visibilityValue = main.playerDataManager.getPlayersHidden(player.getUniqueId());

        player.getInventory().setItem(gameSelector.hotbarSlot, gameSelector.hotbarItem());
        player.getInventory().setItem(serverInfo.hotbarSlot, serverInfo.hotbarItem());
        player.getInventory().setItem(visibility.hotbarSlot, visibility.hotbarItem(visibilityValue));
        player.getInventory().setItem(profile.hotbarSlot, profile.hotbarItem(player));
        player.getInventory().setItem(store.hotbarSlot, store.hotbarItem());

        if(!visibility.hidden.isEmpty()){
            for(Player target : visibility.hidden){
                target.hidePlayer(main, player);
            }
        }

        visibility.setVisibility(player, visibilityValue);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        visibility.hidden.remove(player);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();
        if(event.getItem().getType().equals(gameSelector.hotbarItem().getType())){
            gameSelector.openGUI(player);
            return;
        }

        if(event.getItem().getType().equals(serverInfo.hotbarItem().getType())){
            serverInfo.sendServerInfo(player);
            return;
        }

        if(event.getItem().getType().equals(visibility.hotbarItem(false).getType())
        || event.getItem().getType().equals(visibility.hotbarItem(true).getType())){
            visibility.toggleVisibility(player);
            return;
        }

        if(event.getItem().getType().equals(profile.hotbarItem(player).getType())){
            player.sendMessage(main.textUtils.error + "This feature is still in development.");
            return;
        }

        if(event.getItem().getType().equals(store.hotbarItem().getType())){
            store.openGUI(player);
            return;
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupArrowEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event){
        if(event.getEntity() instanceof Player){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        HumanEntity player = event.getWhoClicked();
        if(Objects.equals(event.getClickedInventory(), player.getInventory())) event.setCancelled(true);
    }
}