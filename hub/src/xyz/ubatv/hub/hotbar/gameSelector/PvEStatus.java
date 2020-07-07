package xyz.ubatv.hub.hotbar.gameSelector;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.hub.Main;

import java.util.HashMap;

public class PvEStatus {

    private Main main = Main.getInstance();

    public HashMap<String, GameStatus> servers = new HashMap<>();

    PingServer pve1 = new PingServer(main.serversYML.getConfig().getString("pve1.ip"),
            main.serversYML.getConfig().getInt("pve1.port"));
    PingServer pve2 = new PingServer(main.serversYML.getConfig().getString("pve2.ip"),
            main.serversYML.getConfig().getInt("pve2.port"));

    public void checkServers(){
        checkPvE1();
        checkPvE2();
    }

    public void checkPvE1(){
        new BukkitRunnable(){
            @Override
            public void run() {
                pve1.update();
                if(pve1.getMotd().equalsIgnoreCase("waiting")){
                    main.pveStatus.servers.put("pve1", GameStatus.WAITING);
                }else{
                    main.pveStatus.servers.put("pve1", GameStatus.INGAME);
                }
            }
        }.runTaskTimer(main, 0, 20*2);
    }

    public void checkPvE2(){
        new BukkitRunnable(){
            @Override
            public void run() {
                pve2.update();
                if(pve2.getMotd().equalsIgnoreCase("waiting")){
                    main.pveStatus.servers.put("pve2", GameStatus.WAITING);
                }else{
                    main.pveStatus.servers.put("pve2", GameStatus.INGAME);
                }
            }
        }.runTaskTimer(main, 0, 20*5);
    }
}
