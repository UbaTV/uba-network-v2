package xyz.ubatv.pve.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class GameManager {

    public World world = Bukkit.getServer().getWorld("world");

    public final int minPlayer = 2;
    public final int maxPlayer = 4;

    public Location lobby;
    public Location game;

    public GameStatus gameStatus = GameStatus.WAITING;
    public int currentRound = 0;

    public void preloadGame(){

    }
}
