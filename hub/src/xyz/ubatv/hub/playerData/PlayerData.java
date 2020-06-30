package xyz.ubatv.hub.playerData;

import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

import java.util.UUID;

public class PlayerData {

    private Main main = Main.getInstance();

    UUID uuid;
    public Ranks rank;
    public int timeOnline;
    public boolean playersHidden;

    public PlayerData(UUID uuid, Ranks rank, int timeOnline, boolean playersHidden){
        this.uuid = uuid;
        this.rank = rank;
        this.timeOnline = timeOnline;
        this.playersHidden = playersHidden;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Ranks getRank() {
        return rank;
    }

    public void setRank(Ranks rank) {
        this.rank = rank;
    }

    public int getTimeOnline() {
        return timeOnline;
    }

    public void setTimeOnline(int timeOnline) {
        this.timeOnline = timeOnline;
    }

    public void addTimeOnline() {
        this.timeOnline++;
    }

    public void addTimeOnline(int timeOnline) {
        this.timeOnline += timeOnline;
    }

    public boolean isPlayersHidden() {
        return playersHidden;
    }

    public void setPlayersHidden(boolean playersHidden) {
        this.playersHidden = playersHidden;
    }

    public void togglePlayersHidden(){
        this.playersHidden = !this.playersHidden;
    }

    public void update(){
        main.playerDataManager.setRank(uuid, this.rank);
        // TODO Set time online
        main.playerDataManager.setPlayersHidden(uuid, this.playersHidden);
    }
}
