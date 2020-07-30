package xyz.ubatv.kingdoms.skills;

import xyz.ubatv.kingdoms.Main;

import java.util.UUID;

public class PlayerSkills {

    private Main main = Main.getInstance();

    UUID uuid;
    public int mining;
    public int combat;
    public int farming;

    public PlayerSkills(UUID uuid, int mining, int combat, int farming){
        this.uuid = uuid;
        this.mining = mining;
        this.combat = combat;
        this.farming = farming;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getMining() {
        return mining;
    }

    public void setMining(int mining) {
        this.mining = mining;
    }

    public int getCombat() {
        return combat;
    }

    public void setCombat(int combat) {
        this.combat = combat;
    }

    public int getFarming() {
        return farming;
    }

    public void setFarming(int farming) {
        this.farming = farming;
    }

    public void update(){
        main.skillsTable.setMining(uuid, mining);
        main.skillsTable.setCombat(uuid, combat);
        main.skillsTable.setFarming(uuid, farming);
    }
}
