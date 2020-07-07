package xyz.ubatv.hub.rankSystem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.hub.Main;

import java.util.UUID;

public class RankCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1){
            if(sender instanceof Player){
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();

                if(!main.playerDataManager.hasPermission(uuid, Ranks.CEO)){
                    player.sendMessage(main.textUtils.noPerms);
                    return false;
                }
                try{
                    Ranks rank = Ranks.valueOf(args[0].toUpperCase());
                    if(main.playerDataManager.getRank(uuid).equals(rank)) {
                        player.sendMessage(main.textUtils.warning + "You already have this rank.");
                        return false;
                    }
                    main.playerDataManager.setRank(uuid, rank);
                    player.sendMessage(main.textUtils.right + "Your rank has been changed to " + main.rankManager.rankName(rank));
                    main.playerDataManager.updateListName(player);
                }catch (IllegalArgumentException e){
                    player.sendMessage(main.textUtils.error + "Invalid rank.");
                }
            }else{
                sender.sendMessage(main.textUtils.error + "/rank <rank> <player>");
            }
            return false;
        }else if(args.length == 2){
            if(sender instanceof Player){
                if(!main.playerDataManager.hasPermission(((Player) sender).getUniqueId(), Ranks.CEO)){
                    sender.sendMessage(main.textUtils.noPerms);
                    return false;
                }
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null){
                sender.sendMessage(main.textUtils.error + "Invalid player.");
                return false;
            }

            UUID targetUUID = target.getUniqueId();
            try{
                Ranks rank = Ranks.valueOf(args[0].toUpperCase());
                if(main.playerDataManager.getRank(targetUUID).equals(rank)){
                    sender.sendMessage(main.textUtils.warning + "§5" + target.getName() + " §7already has this rank.");
                    return false;
                }
                main.playerDataManager.setRank(targetUUID, rank);
                sender.sendMessage(main.textUtils.right + "You changed §5" + target.getName() + "§7's rank to " + main.rankManager.rankName(rank));
                target.sendMessage(main.textUtils.warning + "Your rank has been changed " + main.rankManager.rankName(rank));
                main.playerDataManager.updateListName(target);
            }catch (IllegalArgumentException e){
                sender.sendMessage(main.textUtils.error + "Invalid rank.");
            }
            return false;
        }else{
            if(sender instanceof Player){
                sender.sendMessage(main.textUtils.error + "/rank <rank> [player]");
            }else{
                sender.sendMessage(main.textUtils.error + "/rank <rank> <player>");
            }
        }
        return false;
    }
}
