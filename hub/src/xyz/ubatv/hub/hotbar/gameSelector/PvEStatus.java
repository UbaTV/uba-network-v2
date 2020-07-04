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
    PingServer pve3 = new PingServer(main.serversYML.getConfig().getString("pve3.ip"),
            main.serversYML.getConfig().getInt("pve3.port"));

    public void checkServers(){
        new BukkitRunnable(){
            @Override
            public void run() {
                pve1.update();
                if(pve1.getMotd().equalsIgnoreCase("waiting")){
                    main.pveStatus.servers.put("pve1", GameStatus.WAITING);
                }else{
                    main.pveStatus.servers.put("pve1", GameStatus.INGAME);
                }

                pve2.update();
                if(pve2.getMotd().equalsIgnoreCase("waiting")){
                    main.pveStatus.servers.put("pve2", GameStatus.WAITING);
                }else{
                    main.pveStatus.servers.put("pve2", GameStatus.INGAME);
                }

                pve3.update();
                if(pve3.getMotd().equalsIgnoreCase("waiting")){
                    main.pveStatus.servers.put("pve3", GameStatus.WAITING);
                }else{
                    main.pveStatus.servers.put("pve3", GameStatus.INGAME);
                }
            }
        }.runTaskTimer(main, 0, 20);
    }

}
