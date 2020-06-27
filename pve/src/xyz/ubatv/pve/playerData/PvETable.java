package xyz.ubatv.pve.playerData;

import org.bukkit.entity.Player;
import xyz.ubatv.pve.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PvETable {

    private Main main = Main.getInstance();

    // TODO SELECT * (optimize to only select the needed collum)

    public boolean playerExists(UUID uuid){
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            return result.next();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(Player player){
        UUID uuid = player.getUniqueId();
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            if(!result.next())
                main.getMySQL().updateSQL("INSERT INTO pve (`uuid`,`selfRevives`,`teamRevives`,`miningSpeed`,`jumpBoost`,`hasteBoost`,`regenBoost`,`damageBoost`) VALUES ('" + uuid.toString() + "','0','0','0','0','0','0','0')");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getSelfRevives(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("selfRevives");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setSelfRevives(UUID uuid, int selfRevives){
        try{
            main.mySQL.updateSQL("UPDATE pve SET selfRevives='" + selfRevives + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getTeamRevives(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("teamRevives");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setTeamRevives(UUID uuid, int teamRevives){
        try{
            main.mySQL.updateSQL("UPDATE pve SET teamRevives='" + teamRevives + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getMiningSpeed(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("miningSpeed");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setMiningSpeed(UUID uuid, int miningSpeed){
        try{
            main.mySQL.updateSQL("UPDATE pve SET miningSpeed='" + miningSpeed + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getJumpBoost(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("jumpBoost");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setJumpBoost(UUID uuid, int jumpBoost){
        try{
            main.mySQL.updateSQL("UPDATE pve SET jumpBoost='" + jumpBoost + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getHasteBoost(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("hasteBoost");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setHasteBoost(UUID uuid, int hasteBoost){
        try{
            main.mySQL.updateSQL("UPDATE pve SET hasteBoost='" + hasteBoost + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getRegenBoost(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("regenBoost");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setRegenBoost(UUID uuid, int regenBoost){
        try{
            main.mySQL.updateSQL("UPDATE pve SET regenBoost='" + regenBoost + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getDamageBoost(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM pve WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getInt("damageBoost");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setDamageBoost(UUID uuid, int damageBoost){
        try{
            main.mySQL.updateSQL("UPDATE pve SET damageBoost='" + damageBoost + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
