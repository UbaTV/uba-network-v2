package xyz.ubatv.hub.hotbar.gameSelector;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.hub.Main;

import java.util.HashMap;

public class PvEStatus {

    private Main main = Main.getInstance();

    public HashMap<String, GameStatus> servers = new HashMap<>();

    PingServer pve1 = new PingServer("localhost", 25567);

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
            }
        }.runTaskTimer(main, 0, 20);
    }

}
