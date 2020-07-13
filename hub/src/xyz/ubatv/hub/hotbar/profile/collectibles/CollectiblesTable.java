package xyz.ubatv.hub.hotbar.profile.collectibles;

import org.bukkit.entity.Player;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CollectiblesTable {

    private Main main = Main.getInstance();

    public boolean playerExists(UUID uuid){
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM collectibles WHERE uuid='" + uuid.toString() + "';");
            return result.next();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(Player player){
        UUID uuid = player.getUniqueId();
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM collectibles WHERE uuid='" + uuid.toString() + "';");
            if(!result.next())
                main.getMySQL().updateSQL("INSERT INTO collectibles (`uuid`,`trails_flame`,`trails_hearths`,`trails_magic`,`trails_smoke`,`trails_angry`) VALUES ('" + uuid.toString() + "','0','0','0','0','0')");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean getTrailsFlame(UUID uuid){
        if(!this.playerExists(uuid)){
            return false;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT trails_flame FROM collectibles WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getBoolean("trails_flame");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setTrailsFlame(UUID uuid, boolean trailsFlame){
        try{
            main.mySQL.updateSQL("UPDATE collectibles SET trails_flame='" + (trailsFlame ? "1" : "0") + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean getTrailsHearths(UUID uuid){
        if(!this.playerExists(uuid)){
            return false;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT trails_hearths FROM collectibles WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getBoolean("trails_hearths");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setTrailsHearths(UUID uuid, boolean trailsHearths){
        try{
            main.mySQL.updateSQL("UPDATE collectibles SET trails_hearths='" + (trailsHearths ? "1" : "0") + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean getTrailsMagic(UUID uuid){
        if(!this.playerExists(uuid)){
            return false;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT trails_magic FROM collectibles WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getBoolean("trails_magic");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setTrailsMagic(UUID uuid, boolean trailsMagic){
        try{
            main.mySQL.updateSQL("UPDATE collectibles SET trails_magic='" + (trailsMagic ? "1" : "0") + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean getTrailsSmoke(UUID uuid){
        if(!this.playerExists(uuid)){
            return false;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT trails_smoke FROM collectibles WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getBoolean("trails_smoke");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setTrailsSmoke(UUID uuid, boolean trailsSmoke){
        try{
            main.mySQL.updateSQL("UPDATE collectibles SET trails_smoke='" + (trailsSmoke ? "1" : "0") + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean getTrailsAngry(UUID uuid){
        if(!this.playerExists(uuid)){
            return false;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT trails_angry FROM collectibles WHERE uuid='" + uuid.toString() + "';");
            result.next();
            return result.getBoolean("trails_angry");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setTrailsAngry(UUID uuid, boolean trailsAngry){
        try{
            main.mySQL.updateSQL("UPDATE collectibles SET trails_angry='" + (trailsAngry ? "1" : "0") + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
