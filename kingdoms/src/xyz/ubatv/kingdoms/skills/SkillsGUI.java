package xyz.ubatv.kingdoms.skills;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;

public class SkillsGUI implements InventoryHolder {

    private Main main = Main.getInstance();

    public final Inventory skills;
    private Player player;

    public SkillsGUI(Player player){
        this.player = player;
        this.skills = Bukkit.createInventory(player, 9*6, "§8Your Skills");
    }

    public void createGUI(){
        ItemStack inDev = main.itemAPI.item(Material.BLACK_STAINED_GLASS_PANE, "§cComing soon...", "§7Skill still in development");
        ItemStack mining = main.itemAPI.item(Material.STONE_PICKAXE, "§aMining", "§6Level: §a" + main.skillsManager.xpToLevel(main.skillsManager.getMining(player.getUniqueId())), "", "§7Spend some time on the Mines", "§7to gain Mining XP");
        ItemStack combat = main.itemAPI.item(Material.STONE_SWORD, "§aCombat", "§6Level: §a" + main.skillsManager.xpToLevel(main.skillsManager.getCombat(player.getUniqueId())), "", "§7Fight monsters and players to", "§7gain Combat XP");
        ItemStack farming = main.itemAPI.item(Material.WHEAT_SEEDS, "§aFarming", "§6Level: §a" + main.skillsManager.xpToLevel(main.skillsManager.getFarming(player.getUniqueId())), "", "§7Harvest crops and kill", "§7animals to gain Farming XP");
        ItemStack close = main.itemAPI.item(Material.BARRIER, "§cClose", "");

        skills.setItem(19, inDev);
        skills.setItem(20, inDev);
        skills.setItem(21, mining);
        skills.setItem(22, combat);
        skills.setItem(23, farming);
        skills.setItem(24, inDev);
        skills.setItem(25, inDev);
        skills.setItem(28, inDev);
        skills.setItem(34, inDev);
        skills.setItem(49, close);
    }

    public void openInventory(Player player){
        createGUI();
        player.openInventory(skills);
    }

    @Override
    public Inventory getInventory() {
        return skills;
    }
}
