package xyz.ubatv.kingdoms.skills;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;

public class SkillsCommand implements CommandExecutor, Listener {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            SkillsGUI skillsGUI = new SkillsGUI(player);
            skillsGUI.openInventory(player);
        }else{
            Bukkit.getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    // SkillsGUI Listener
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§8Your Skills")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if(slot == 49){
            player.closeInventory();
        }
    }
}
