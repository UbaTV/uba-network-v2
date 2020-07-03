package xyz.ubatv.hub.hotbar.gameSelector;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class GameSelector {

    private Main main = Main.getInstance();

    private GameSelectorGUI gameSelectorGUI = new GameSelectorGUI();

    public int hotbarSlot = 0;

    public ItemStack hotbarItem(){
        return main.itemAPI.item(Material.COMPASS, "§5§lGame §7Selector", "§7Right-click to open this menu.");
    }

    public void openGUI(Player player){
        gameSelectorGUI.openInventory(player);
    }
}
