package xyz.ubatv.hub.hotbar.store;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.hotbar.store.pve.PvEGUI;

public class StoreManager implements Listener {

    private int selfRevivePrice = 100;
    private int teamRevivePrice = 500;

    private Main main = Main.getInstance();

    public int hotbarSlot = 5;

    public ItemStack hotbarItem(){
        return main.itemAPI.item(Material.GOLD_BLOCK, "§5§lStore", "§7Right-click to open the store");
    }

    public void openGUI(Player player){
        StoreGUI storeGUI = new StoreGUI(player);
        storeGUI.openInventory(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5§lStore")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if(slot == 22){
            PvEGUI pveGUI = new PvEGUI(player);
            pveGUI.openInventory(player);
            return;
        }

        if(slot == 50){
            player.closeInventory();
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5§lPvE §7Store")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if(slot == 20){
            int balance = main.playerBankManager.getPvECoins(player.getUniqueId());
            if(balance >= selfRevivePrice){
                main.playerBankManager.setPvECoins(player.getUniqueId(), balance - selfRevivePrice);
                main.pveTable.setSelfRevives(player.getUniqueId(), main.pveTable.getSelfRevives(player.getUniqueId()) + 1);
                player.closeInventory();
                player.sendMessage(main.textUtils.right + "You just bought a §5Self Revive");
                player.sendMessage(main.textUtils.warning + "Now you have §5" + main.pveTable.getSelfRevives(player.getUniqueId()));
            }else{
                player.sendMessage(main.textUtils.error + "You don't have sufficient funds.");
            }
            return;
        }

        if(slot == 24){
            int balance = main.playerBankManager.getPvECoins(player.getUniqueId());
            if(balance >= teamRevivePrice){
                main.playerBankManager.setPvECoins(player.getUniqueId(), balance - teamRevivePrice);
                main.pveTable.setTeamRevives(player.getUniqueId(), main.pveTable.getTeamRevives(player.getUniqueId()) + 1);
                player.closeInventory();
                player.sendMessage(main.textUtils.right + "You just bought a §5Self Revive");
                player.sendMessage(main.textUtils.warning + "Now you have §5" + main.pveTable.getTeamRevives(player.getUniqueId()));
            }else{
                player.sendMessage(main.textUtils.error + "You don't have sufficient funds.");
            }
            return;
        }

        if(slot == 48){
            StoreGUI storeGUI = new StoreGUI(player);
            storeGUI.openInventory(player);
            return;
        }

        if(slot == 50){
            player.closeInventory();
        }
    }
}
