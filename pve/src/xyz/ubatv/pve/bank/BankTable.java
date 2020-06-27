package xyz.ubatv.pve.bank;

import org.bukkit.entity.Player;
import xyz.ubatv.pve.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BankTable {

    private Main main = Main.getInstance();

    public boolean playerExists(UUID uuid){
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM bank WHERE uuid='" + uuid.toString() + "';");
            return result.next();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(Player player){
        UUID uuid = player.getUniqueId();
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM bank WHERE uuid='" + uuid.toString() + "';");
            if(!result.next())
                main.getMySQL().updateSQL("INSERT INTO bank (`uuid`,`serverCoins`,`pve`) VALUES ('" + uuid.toString() + "','0','0')");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getPvECoins(UUID uuid){
        if(!this.playerExists(uuid)){
            return 0;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM bank WHERE uuid='" + uuid.toString() + "';");
            result.next();
            int coins = result.getInt("pve");
            return coins;
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setPvECoins(UUID uuid, int coins){
        try{
            main.mySQL.updateSQL("UPDATE bank SET pve='" + coins + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
