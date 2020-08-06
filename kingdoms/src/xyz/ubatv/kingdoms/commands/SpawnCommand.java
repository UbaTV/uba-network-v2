package xyz.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Ranks;

public class SpawnCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    // TODO add teleport cooldown

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(main.locationManager.spawn == null){
                player.sendMessage(main.textUtils.error + "Spawn location is not defined.");
                return false;
            }

            if(args.length == 1){
                if(main.playerDataManager.hasPermission(player.getUniqueId(), Ranks.ADMIN)){
                    Player target = Bukkit.getPlayer(args[0]);

                    if(target == null){
                        player.sendMessage(main.textUtils.error + "Invalid player.");
                        return false;
                    }

                    target.teleport(main.locationManager.spawn);
                    player.sendMessage(main.textUtils.right + "Teleported §a" + target.getName() + "§7to §aspawn");
                    target.sendMessage(main.textUtils.warning + "You have been teleported to §aspawn");
                }else{
                    player.sendMessage(main.textUtils.noPerms);
                }
            }else{
                player.teleport(main.locationManager.spawn);
                player.sendMessage(main.textUtils.right + "Teleported to §aspawn");
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
