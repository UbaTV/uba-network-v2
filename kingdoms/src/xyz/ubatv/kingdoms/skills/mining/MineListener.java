package xyz.ubatv.kingdoms.skills.mining;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.ubatv.kingdoms.Main;

public class MineListener implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().name().contains("_PICKAXE")){
            if(event.getBlock().getType().name().contains("_ORE")
            || event.getBlock().getType().equals(Material.STONE)
            || event.getBlock().getType().equals(Material.COBBLESTONE)
            || event.getBlock().getType().equals(Material.OBSIDIAN)){
                rewardXp(player, event.getBlock().getType());
            }
        }
    }

    public void rewardXp(Player player, Material material){
        int reward = 0;
        if(material.equals(Material.COBBLESTONE) || material.equals(Material.STONE)) reward = 1;
        else if(material.equals(Material.COAL_ORE)) reward = 3;
        else if(material.equals(Material.IRON_ORE)) reward = 7;
        else if(material.equals(Material.LAPIS_ORE)) reward = 9;
        else if(material.equals(Material.REDSTONE)) reward = 9;
        else if(material.equals(Material.NETHER_QUARTZ_ORE)) reward = 9;
        else if(material.equals(Material.GOLD_ORE)) reward = 12;
        else if(material.equals(Material.DIAMOND_ORE)) reward = 18;
        else if(material.equals(Material.OBSIDIAN)) reward = 27;

        main.skillsManager.setMining(player.getUniqueId(), main.skillsManager.getMining(player.getUniqueId()) + reward);


        if(reward != 0){
            int xp = main.skillsManager.getPlayerSkills(player.getUniqueId()).getMining();
            main.textUtils.sendActionBarMessage(player, "§7Mining: §a" + xp + "/" + main.skillsManager.levelXp(main.skillsManager.xpToLevel(xp) + 1));
        }
    }
}
