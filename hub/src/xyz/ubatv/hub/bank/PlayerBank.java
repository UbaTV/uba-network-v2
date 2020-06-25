package xyz.ubatv.hub.bank;

import xyz.ubatv.hub.Main;

import java.util.UUID;

public class PlayerBank {

    private Main main = Main.getInstance();

    UUID uuid;
    public int serverCoins;
    public int pveCoins;

    public PlayerBank(UUID uuid, int serverCoins, int pveCoins){
        this.uuid = uuid;
        this.serverCoins = serverCoins;
        this.pveCoins = pveCoins;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getServerCoins() {
        return serverCoins;
    }

    public void setServerCoins(int serverCoins) {
        this.serverCoins = serverCoins;
    }

    public int getPveCoins() {
        return pveCoins;
    }

    public void setPveCoins(int pveCoins) {
        this.pveCoins = pveCoins;
    }

    public void update(){
        main.playerBankManager.setServerCoins(uuid, this.serverCoins);
        main.playerBankManager.setPvECoins(uuid, this.pveCoins);
    }
}
