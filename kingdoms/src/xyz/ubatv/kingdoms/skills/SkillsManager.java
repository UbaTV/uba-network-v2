package xyz.ubatv.kingdoms.skills;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.skills.combat.CombatGUI;
import xyz.ubatv.kingdoms.skills.farming.FarmingGUI;
import xyz.ubatv.kingdoms.skills.mining.MiningGUI;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillsManager implements Listener {

    private Main main = Main.getInstance();

    public final int maxLevel = 30;

    public Map<UUID, PlayerSkills> skills = new HashMap<>();

    public int levelXp(int level){
        return level >= maxLevel ? -1 : (level ^ 2) * 75;
    }

    public int xpToLevel(int xp){
        return xp >= ((maxLevel ^ 2) * 75) ? 30 : (int) Math.floor(Math.sqrt(Math.floorDiv(xp, 75)));
    }

    public boolean playerExists(UUID uuid){
        return main.skillsManager.skills.containsKey(uuid);
    }

    public PlayerSkills getPlayerSkills(UUID uuid){
        return main.skillsManager.skills.get(uuid);
    }

    public void createPlayerData(UUID uuid){
        PlayerSkills data = new PlayerSkills(uuid,
                getMining(uuid),
                getCombat(uuid),
                getFarming(uuid));
        main.skillsManager.skills.put(uuid, data);
    }

    public int getMining(UUID uuid){
        if(playerExists(uuid)) return getPlayerSkills(uuid).getMining();
        else return main.skillsTable.getMining(uuid);
    }

    public void setMining(UUID uuid, int mining){
        if(playerExists(uuid)){
            getPlayerSkills(uuid).setMining(mining);
        } else{
            main.skillsTable.setMining(uuid, mining);
        }
    }

    public int getCombat(UUID uuid){
        if(playerExists(uuid)) return getPlayerSkills(uuid).getCombat();
        else return main.skillsTable.getCombat(uuid);
    }

    public void setCombat(UUID uuid, int combat){
        if(playerExists(uuid)){
            getPlayerSkills(uuid).setCombat(combat);
        } else{
            main.skillsTable.setCombat(uuid, combat);
        }
    }

    public int getFarming(UUID uuid){
        if(playerExists(uuid)) return getPlayerSkills(uuid).getFarming();
        else return main.skillsTable.getFarming(uuid);
    }

    public void setFarming(UUID uuid, int farming){
        if(playerExists(uuid)){
            getPlayerSkills(uuid).setFarming(farming);
        } else{
            main.skillsTable.setFarming(uuid, farming);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        main.skillsTable.createPlayer(player);
        createPlayerData(uuid);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(playerExists(uuid)){
            PlayerSkills data = getPlayerSkills(uuid);
            main.skillsManager.skills.remove(uuid);
            data.update();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§8Your Skills")) return;
        event.setCancelled(true);
        if(event.getClick().equals(ClickType.NUMBER_KEY)) return;

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if(slot == 21){
            MiningGUI miningGUI = new MiningGUI(player);
            miningGUI.openInventory(player);
        }

        if(slot == 22){
            CombatGUI combatGUI = new CombatGUI(player);
            combatGUI.openInventory(player);
        }

        if(slot == 23){
            FarmingGUI farmingGUI = new FarmingGUI(player);
            farmingGUI.openInventory(player);
        }

        if(slot == 49){
            player.closeInventory();
        }
    }
}
