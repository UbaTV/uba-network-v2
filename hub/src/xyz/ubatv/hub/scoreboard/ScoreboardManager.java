package xyz.ubatv.hub.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.playerData.PlayerData;

public class ScoreboardManager implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = main.playerDataManager.getPlayerData(player.getUniqueId());
        ScoreboardHelper scoreboardHelper = ScoreboardHelper.createScoreboard(player);
        scoreboardHelper.setTitle(main.textUtils.serverName);
        scoreboardHelper.setSlot(5, "  ");
        scoreboardHelper.setSlot(4, "§6| §7Coins: §5" + main.playerBankManager.getServerCoins(player.getUniqueId()) + main.textUtils.coinsSymbol);
        scoreboardHelper.setSlot(3, "§a| §7Rank: " + main.rankManager.rankName(playerData.getRank()));
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
