package xyz.ubatv.pve.bank;

import xyz.ubatv.pve.Main;

import java.util.UUID;

public class PlayerBank {

    private Main main = Main.getInstance();

    UUID uuid;
    public int pveCoins;
    public int gameCoins;

    public PlayerBank(UUID uuid, int pveCoins, int gameCoins){
        this.uuid = uuid;
        this.pveCoins = pveCoins;
        this.gameCoins = gameCoins;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getPveCoins() {
        return pveCoins;
    }

    public void setPveCoins(int pveCoins) {
        this.pveCoins = pveCoins;
    }

    public int getGameCoins() {
        return gameCoins;
    }

    public void setGameCoins(int gameCoins) {
        this.gameCoins = gameCoins;
    }

    public void update(){
        main.playerBankManager.setPvECoins(uuid, this.pveCoins);
    }
}
