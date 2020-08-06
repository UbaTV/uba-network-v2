package xyz.ubatv.kingdoms.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Ranks;

public class TeleportLocationCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.playerDataManager.hasPermission(player.getUniqueId(), Ranks.BUILDER)){
                if(args.length == 1){
                    Location loc = main.locationManager.getLocation(String.valueOf(args[0]));
                    if(loc == null){
                        player.sendMessage(main.textUtils.error + "Invalid location name.");
                    }else{
                        player.teleport(loc);
                        player.sendMessage(main.textUtils.right + "You have been teleported to §a" + args[0].toLowerCase());
                    }
                }else{
                    player.sendMessage(main.textUtils.error + "/tploc <location>");
                }
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
