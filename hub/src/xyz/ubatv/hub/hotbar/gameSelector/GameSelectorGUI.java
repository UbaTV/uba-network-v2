package xyz.ubatv.hub.hotbar.gameSelector;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

import java.util.UUID;

public class GameSelectorGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    public final Inventory gameSelector;

    public GameSelectorGUI(){
        this.gameSelector = Bukkit.createInventory(this, 9*3, "§5§lGame §7Selector");
    }

    public void createGUI(){
        ItemStack pve = main.itemAPI.item(Material.DIAMOND_SWORD, "§5§lPvE", "§7Click to go to minigame.");

        gameSelector.setItem(13, pve);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5§lGame §7Selector")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if(slot == 13){
            String available = " ";
            for(String server : main.pveStatus.servers.keySet()){
                GameStatus gameStatus = main.pveStatus.servers.get(server);
                if(gameStatus.equals(GameStatus.WAITING)){
                    available = server;
                    player.sendMessage(main.textUtils.right + "Connecting to §5" + server);
                    main.bungeeUtils.connectToServer(player.getUniqueId(), server);
                    break;
                }
            }
            if(available.equalsIgnoreCase(" ")) {
                player.sendMessage(main.textUtils.error + "All PvE servers are full.");
            }
        }
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(gameSelector);
    }

    @Override
    public Inventory getInventory() {
        return gameSelector;
    }
}
