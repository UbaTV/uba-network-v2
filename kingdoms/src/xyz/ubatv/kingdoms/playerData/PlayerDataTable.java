package xyz.ubatv.kingdoms.playerData;

import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Ranks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerDataTable {

    private Main main = Main.getInstance();

    public boolean playerExists(UUID uuid){
        try{
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM userData WHERE uuid='" + uuid.toString() + "';");
            return result.next();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public Ranks getRank(UUID uuid){
        if(!this.playerExists(uuid)){
            return Ranks.DEFAULT;
        }

        try {
            ResultSet result = main.getMySQL().querySQL("SELECT * FROM userData WHERE uuid='" + uuid.toString() + "';");
            result.next();
            int rankId = result.getInt("rank");
            return main.rankManager.idToRank(rankId);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return Ranks.DEFAULT;
    }

    public void setRank(UUID uuid, Ranks rank){
        int rankId = main.rankManager.rankToId(rank);
        try{
            main.mySQL.updateSQL("UPDATE userData SET rank='" + rankId + "' WHERE uuid='" + uuid.toString() + "';");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
