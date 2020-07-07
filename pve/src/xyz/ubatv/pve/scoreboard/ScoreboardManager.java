package xyz.ubatv.pve.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.game.GameStatus;

public class ScoreboardManager implements Listener {

    private Main main = Main.getInstance();

    public static int dayTime = 60*5;

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
        else if(main.gameManager.gameStatus.equals(GameStatus.ROUND_DAY)) gameState = "§7Time Left§l: §5" + dayTime / 60 + "m" + dayTime % 60 + "s";
        else gameState = "This is a bug. Report to staff";

        scoreboardHelper.setTitle(main.textUtils.serverName);
        scoreboardHelper.setSlot(6, "  ");
        scoreboardHelper.setSlot(5, gameState);
        //scoreboardHelper.setSlot(3, "§7Coins: §5" + main.playerBankManager.playerBank.get(player.getUniqueId()).getGameCoins());
        scoreboardHelper.setSlot(4, "§7Self Revives§8: §5" + main.playerDataManager.getSelfRevives(player.getUniqueId()));
        scoreboardHelper.setSlot(3, "§7Team Revives§8: §5" + main.playerDataManager.getTeamRevives(player.getUniqueId()));
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
