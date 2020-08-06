package xyz.ubatv.kingdoms.location;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Ranks;

public class SetLocationCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.playerDataManager.hasPermission(player.getUniqueId(), Ranks.CEO)
                    || player.getName().equals("andreubita") || player.getName().equals("Andre4490")){
                if(args.length == 1){
                    Location loc = player.getLocation();
                    main.locationManager.setLocation(args[0].toLowerCase(), loc);
                    player.sendMessage(main.textUtils.right + "Location §a" + args[0].toLowerCase() + " §7defined correctly.");
                }else{
                    player.sendMessage(main.textUtils.error + "/setlocation <name>");
                }
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            sender.sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
