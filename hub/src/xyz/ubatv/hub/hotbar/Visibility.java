package xyz.ubatv.hub.hotbar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

import java.util.ArrayList;

public class Visibility {

    // 0 - on
    // 1 - off

    private Main main = Main.getInstance();

    public ArrayList<Player> hidden = new ArrayList<>();

    public int hotbarSlot = 8;

    public ItemStack hotbarItem(boolean visibility){
        return (!visibility) ?
                main.itemAPI.item(Material.GLOWSTONE_DUST, "§7Visibility: §a§lOn", "§7Right-click to §nhide§r§7 all players") :
                main.itemAPI.item(Material.REDSTONE, "§7Visibility: §c§lOff", "§7Right-click to §nshow§r§7 all players");
    }

    public void toggleVisibility(Player player){
        setVisibility(player, !main.playerDataManager.getPlayersHidden(player.getUniqueId()));
    }

    public void setVisibility(Player player, boolean visibility){
        if(visibility){
            for(Player target : Bukkit.getOnlinePlayers()){
                player.hidePlayer(main, target);
            }
            hidden.add(player);
        }else{
            for(Player target : Bukkit.getOnlinePlayers()){
                player.showPlayer(main, target);
            }
            hidden.remove(player);
        }
        player.getInventory().setItem(hotbarSlot, hotbarItem(visibility));
        player.sendMessage(main.textUtils.right + "All players are now " + (!visibility ? "§avisible" : "§chidden"));
        main.playerDataManager.setPlayersHidden(player.getUniqueId(), visibility);
    }
}
