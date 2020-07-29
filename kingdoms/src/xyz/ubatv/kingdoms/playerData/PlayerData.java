package xyz.ubatv.kingdoms.playerData;

import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Ranks;

import java.util.UUID;

public class PlayerData {

    private Main main = Main.getInstance();

    UUID uuid;
    public Ranks rank;
    public int timeOnline;

    public PlayerData(UUID uuid, Ranks rank, int timeOnline){
        this.uuid = uuid;
        this.rank = rank;
        this.timeOnline = timeOnline;
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

    public void update(){
        main.playerDataManager.setRank(uuid, this.rank);
        // TODO Set time online
    }
}
