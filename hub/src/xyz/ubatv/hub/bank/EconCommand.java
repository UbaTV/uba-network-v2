package xyz.ubatv.hub.bank;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Ranks;

import java.util.UUID;

public class EconCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    // TODO

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if(main.playerDataManager.hasPermission(uuid, Ranks.ADMIN)){
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("reset")){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target == null){
                            player.sendMessage(main.textUtils.error + "Invalid player.");
                            return false;
                        }

                    }
                }

                if(args.length == 3){
                    return false;
                }

                player.sendMessage(main.textUtils.error + "/econ <add/remove/reset> <player> [amount]");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            sender.sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
