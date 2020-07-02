package xyz.ubatv.pve.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.game.GameStatus;
import xyz.ubatv.pve.playerData.PlayerData;

import java.util.UUID;

public class SelfRevive implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if(main.gameManager.gameStatus.equals(GameStatus.ROUND_DAY)
            || main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)){
                if(main.gameManager.dead.contains(uuid)){
                    PlayerData playerData = main.playerDataManager.getPlayerData(uuid);
                    if(playerData.getSelfRevives() > 0){
                        main.playerDataManager.setSelfRevives(uuid, playerData.getSelfRevives() - 1);
                        playerData.update();
                        player.sendMessage(main.textUtils.right + "§5Self Revive §7used.");
                        main.gameManager.alive.add(uuid);
                        main.gameManager.dead.remove(uuid);
                        player.setGameMode(GameMode.SURVIVAL);
                        player.teleport(main.gameManager.game);
                        Bukkit.broadcastMessage(main.textUtils.right + "§5andreubita§7 just used §5Self Revive");
                        for(Player target : Bukkit.getOnlinePlayers()){
                            target.playSound(target.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                        }
                    }else{
                        player.sendMessage(main.textUtils.error + "You don't have enough §5Self Revives");
                    }
                    return false;
                }
            }

            player.sendMessage(main.textUtils.error + "§5Self Revive §7is not available");
            return false;
        }else{
            sender.sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
