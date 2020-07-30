package xyz.ubatv.kingdoms.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.kingdoms.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillsManager implements Listener {

    private Main main = Main.getInstance();

    public Map<UUID, PlayerSkills> skills = new HashMap<>();

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
}
