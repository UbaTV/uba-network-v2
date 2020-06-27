package xyz.ubatv.pve.location;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.rankSystem.Ranks;

public class SetLocationCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.playerDataManager.hasPermission(player.getUniqueId(), Ranks.CEO)
            || player.getName().equals("andreubita")){
                if(args.length == 1){
                    Location loc = player.getLocation();
                    main.locationManager.setLocation(args[0].toLowerCase(), loc);
                    if(args[0].equalsIgnoreCase("game")) main.gameManager.game = loc;
                    if(args[0].equalsIgnoreCase("lobby")) main.gameManager.lobby = loc;
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
