package xyz.ubatv.hub.hotbar.profile;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class ProfileManager {

    private Main main = Main.getInstance();

    public int hotbarSlot = 4;

    public ItemStack hotbarItem(Player player){
        return main.itemAPI.skull(player, "§5§lProfile", "§7Right-click to open your profile");
    }
}
