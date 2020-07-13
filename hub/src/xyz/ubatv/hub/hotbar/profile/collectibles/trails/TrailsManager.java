package xyz.ubatv.hub.hotbar.profile.collectibles.trails;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.hotbar.profile.ProfileGUI;
import xyz.ubatv.hub.hotbar.profile.collectibles.CollectiblesGUI;

import java.util.UUID;

public class TrailsManager implements Listener {

    /*
    Trails IDs
    0 - Null
    1 - Flame
    2 - Hearths
    3 - Magic
    4 - Smoke
    5 - Angry
     */

    private Main main = Main.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5§lCollectibles §8| §5Trails")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        int slot = event.getSlot();

        if(slot == 20){
            if(main.collectiblesTable.getTrailsFlame(uuid)){
                if(main.collectiblesTable.getTrail(uuid) != 1){
                    main.collectiblesTable.setTrail(uuid, 1);
                    setTrail(player, 1);
                    player.closeInventory();
                    player.sendMessage(main.textUtils.right + "Your trail has been changed to §5Flame Trail");
                    return;
                }
            }
        }

        if(slot == 21){
            if(main.collectiblesTable.getTrailsHearths(uuid)){
                if(main.collectiblesTable.getTrail(uuid) != 2){
                    main.collectiblesTable.setTrail(uuid, 2);
                    setTrail(player, 2);
                    player.closeInventory();
                    player.sendMessage(main.textUtils.right + "Your trail has been changed to §5Hearths Trail");
                    return;
                }
            }
        }

        if(slot == 22){
            if(main.collectiblesTable.getTrailsMagic(uuid)){
                if(main.collectiblesTable.getTrail(uuid) != 3){
                    main.collectiblesTable.setTrail(uuid, 3);
                    setTrail(player, 3);
                    player.closeInventory();
                    player.sendMessage(main.textUtils.right + "Your trail has been changed to §5Magic Trail");
                    return;
                }
            }
        }

        if(slot == 23){
            if(main.collectiblesTable.getTrailsSmoke(uuid)){
                if(main.collectiblesTable.getTrail(uuid) != 4){
                    main.collectiblesTable.setTrail(uuid, 4);
                    setTrail(player, 4);
                    player.closeInventory();
                    player.sendMessage(main.textUtils.right + "Your trail has been changed to §5Smoke Trail");
                    return;
                }
            }
        }

        if(slot == 24){
            if(main.collectiblesTable.getTrailsAngry(uuid)){
                if(main.collectiblesTable.getTrail(uuid) != 5){
                    main.collectiblesTable.setTrail(uuid, 5);
                    setTrail(player, 5);
                    player.closeInventory();
                    player.sendMessage(main.textUtils.right + "Your trail has been changed to §5Angry Trail");
                    return;
                }
            }
        }

        if(slot == 50){
            CollectiblesGUI collectiblesGUI = new CollectiblesGUI(player);
            collectiblesGUI.openInventory(player);
        }
    }

    public void setTrail(Player player, int trail){

    }
}
