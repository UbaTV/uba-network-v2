package xyz.ubatv.hub.utils;

public class TextUtils {

    public String serverName = "§5§lUba§7Network";
    public String website = "ubatv.xyz";
    public String serverIp = "play.ubatv.xyz";

    public String right = "§a✓ §7";
    public String warning = "§e◬ §7";
    public String error = "§c◉ §7";

    public char coinsSymbol = '⛃';

    public String playerOnly = "Only players can execute this command";
    public String noPerms = error + "You don't have permission to execute this command.";

    public String getFirstSplit(String str){
        return str.length() > 16 ? str.substring(0, 16) : str;
    }

    public String getSecondSplit(String str){
        if(str.length() > 32){
            str = str.substring(0, 32);
        }
        return str.length() > 16 ? str.substring(16) : "";
    }
}
