package xyz.ubatv.hub.hotbar.store;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class StoreManager {

    private Main main = Main.getInstance();

    public int hotbarSlot = 5;

    public ItemStack hotbarItem(){
        return main.itemAPI.item(Material.GOLD_BLOCK, "§5§lStore", "§7Right-click to open the store");
    }
}
