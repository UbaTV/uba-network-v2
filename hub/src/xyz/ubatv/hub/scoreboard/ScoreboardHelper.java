package xyz.ubatv.hub.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import xyz.ubatv.hub.Main;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardHelper {

    private Main main = Main.getInstance();

    static HashMap<UUID, ScoreboardHelper> scoreboards = new HashMap<>();

    private Scoreboard scoreboard;
    private Objective sidebar;

    public ScoreboardHelper(Player player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        sidebar = scoreboard.registerNewObjective("sidebar", "dummy", main.textUtils.serverName);

        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        // CREATE TEAMS
        for(int i = 1; i < 16; i++){
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }

        player.setScoreboard(scoreboard);
        scoreboards.put(player.getUniqueId(), this);
    }

    public void setTitle(String title){
        title = ChatColor.translateAlternateColorCodes('&', title);
        sidebar.setDisplayName(title.length() > 32 ? title.substring(0, 32) : title);
    }

    public void setSlot(int slot, String text){
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if(!scoreboard.getEntries().contains(entry)){
            sidebar.getScore(entry).setScore(slot);
        }

        text = ChatColor.translateAlternateColorCodes('&', text);
        String prefix = main.textUtils.getFirstSplit(text);
        String suffix = main.textUtils.getFirstSplit(ChatColor.getLastColors(prefix) + main.textUtils.getSecondSplit(text));
        team.setPrefix(prefix);
        team.setSuffix(suffix);
    }

    public void removeSlot(int slot){
        String entry = genEntry(slot);
        if(scoreboard.getEntries().contains(entry)){
            scoreboard.resetScores(entry);
        }
    }

    private String genEntry(int slot){
        return ChatColor.values()[slot].toString();
    }

    public static boolean hasScoreboard(Player player){
        return scoreboards.containsKey(player.getUniqueId());
    }

    public static ScoreboardHelper createScoreboard(Player player){
        return new ScoreboardHelper(player);
    }

    public static ScoreboardHelper getScoreboard(Player player){
        return scoreboards.get(player.getUniqueId());
    }

    public static ScoreboardHelper removeScoreboard(Player player){
        return scoreboards.remove(player.getUniqueId());
    }
}
