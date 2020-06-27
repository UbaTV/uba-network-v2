package xyz.ubatv.pve.playerData;

import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.rankSystem.Ranks;

import java.util.UUID;

public class PlayerData {

    private Main main = Main.getInstance();

    // TODO Optimize update() to dont change if the data did not change

    UUID uuid;
    public Ranks rank;
    public int timeOnline;
    public int selfRevives;
    public int teamRevives;
    public int miningSpeed;
    public int jumpBoost;
    public int hasteBoost;
    public int regenBoost;
    public int damageBoost;

    public PlayerData(UUID uuid, Ranks rank, int timeOnline,
                      int selfRevives, int teamRevives, int miningSpeed,
                      int jumpBoost, int hasteBoost, int regenBoost,
                      int damageBoost){
        this.uuid = uuid;
        this.rank = rank;
        this.timeOnline = timeOnline;
        this.selfRevives = selfRevives;
        this.teamRevives = teamRevives;
        this.miningSpeed = miningSpeed;
        this.jumpBoost = jumpBoost;
        this.hasteBoost = hasteBoost;
        this.regenBoost = regenBoost;
        this.damageBoost = damageBoost;
    }

    public void update(){
        main.playerDataManager.setRank(uuid, this.rank);
        main.pveTable.setSelfRevives(uuid, this.selfRevives);
        main.pveTable.setTeamRevives(uuid, this.teamRevives);
        main.pveTable.setMiningSpeed(uuid, this.miningSpeed);
        main.pveTable.setJumpBoost(uuid, this.jumpBoost);
        main.pveTable.setHasteBoost(uuid, this.hasteBoost);
        main.pveTable.setRegenBoost(uuid, this.regenBoost);
        main.pveTable.setDamageBoost(uuid, this.damageBoost);
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

    public int getSelfRevives() {
        return selfRevives;
    }

    public void setSelfRevives(int selfRevives) {
        this.selfRevives = selfRevives;
    }

    public int getTeamRevives() {
        return teamRevives;
    }

    public void setTeamRevives(int teamRevives) {
        this.teamRevives = teamRevives;
    }

    public int getMiningSpeed() {
        return miningSpeed;
    }

    public void setMiningSpeed(int miningSpeed) {
        this.miningSpeed = miningSpeed;
    }

    public int getJumpBoost() {
        return jumpBoost;
    }

    public void setJumpBoost(int jumpBoost) {
        this.jumpBoost = jumpBoost;
    }

    public int getHasteBoost() {
        return hasteBoost;
    }

    public void setHasteBoost(int hasteBoost) {
        this.hasteBoost = hasteBoost;
    }

    public int getRegenBoost() {
        return regenBoost;
    }

    public void setRegenBoost(int regenBoost) {
        this.regenBoost = regenBoost;
    }

    public int getDamageBoost() {
        return damageBoost;
    }

    public void setDamageBoost(int damageBoost) {
        this.damageBoost = damageBoost;
    }
}
