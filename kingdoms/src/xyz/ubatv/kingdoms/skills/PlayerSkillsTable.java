package xyz.ubatv.kingdoms.skills;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerSkillsTable {

    private Main main = Main.getInstance();

    public boolean playerExists(UUID uuid){
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM kingdomsSkills WHERE uuid='" + uuid.toString() + "';");
            return result.next();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(Player player){
        UUID uuid = player.getUniqueId();
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM kingdomsSkills WHERE uuid='" + uuid.toString() + "';");
            if(!result.next())
                main.getMySQL().updateSQL("INSERT INTO kingdomsSkills (`uuid`,`mining`,`combat`) VALUES ('" + uuid.toString() + "','0','0')");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getMining(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT mining,uuid FROM kingdomsSkills WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("mining");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setMining(UUID uuid, int xp){
        try{
            main.mySQL.updateSQL("UPDATE kingdomsSkills SET mining='" + Math.max(xp, 0) + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void addMining(UUID uuid, int xp){
        try{
            main.mySQL.updateSQL("UPDATE kingdomsSkills SET mining='" + Math.max(getMining(uuid) + xp, 0) + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getCombat(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT combat,uuid FROM kingdomsSkills WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("combat");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setCombat(UUID uuid, int xp){
        try{
            main.mySQL.updateSQL("UPDATE kingdomsSkills SET combat='" + Math.max(xp, 0) + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void addCombat(UUID uuid, int xp){
        try{
            main.mySQL.updateSQL("UPDATE kingdomsSkills SET combat='" + Math.max(getCombat(uuid) + xp, 0) + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
