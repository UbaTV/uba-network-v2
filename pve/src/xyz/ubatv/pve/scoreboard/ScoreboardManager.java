package xyz.ubatv.pve.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.game.GameStatus;
import xyz.ubatv.pve.playerData.PlayerData;

public class ScoreboardManager implements Listener {

    private Main main = Main.getInstance();

    public static int dayTime = 0;

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ScoreboardHelper scoreboardHelper = ScoreboardHelper.createScoreboard(player);

        String gameState;
        if(main.gameManager.gameStatus.equals(GameStatus.WAITING)) gameState = "§eWaiting for players";
        else if(main.gameManager.gameStatus.equals(GameStatus.STARTING)) gameState = "§aStarting";
        else if(main.gameManager.gameStatus.equals(GameStatus.ENDED)
        || main.gameManager.gameStatus.equals(GameStatus.RESTARTING)) gameState = "§cEnded";
        else if(main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)) gameState = "§7Mobs Left§8: §5§l" + main.gameManager.mobsToKill;
        else if(main.gameManager.gameStatus.equals(GameStatus.ROUND_DAY)) gameState = "§7Day Time Left§l: §5§l" + dayTime / 60 + "§5min§5§l" + dayTime % 60 + "§5s";
        else gameState = "This is a bug. Report to staff";

        scoreboardHelper.setTitle(main.textUtils.serverName);
        scoreboardHelper.setSlot(5, "  ");
        scoreboardHelper.setSlot(4, gameState);
        scoreboardHelper.setSlot(3, "§7Coins: " + main.playerBankManager.playerBank.get(player.getUniqueId()).getGameCoins());
        scoreboardHelper.setSlot(2, " ");
        scoreboardHelper.setSlot(1, "§7" + main.textUtils.serverIp);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(ScoreboardHelper.hasScoreboard(player)){
            ScoreboardHelper.removeScoreboard(player);
        }
    }
}
