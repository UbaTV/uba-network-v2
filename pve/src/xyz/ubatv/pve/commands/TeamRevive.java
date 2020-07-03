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

public class TeamRevive implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if(main.gameManager.gameStatus.equals(GameStatus.ROUND_DAY)
                    || main.gameManager.gameStatus.equals(GameStatus.ROUND_NIGHT)){
                if(!main.gameManager.dead.isEmpty()){
                    PlayerData playerData = main.playerDataManager.getPlayerData(uuid);
                    if(playerData.getTeamRevives() > 0){
                        main.playerDataManager.setTeamRevives(uuid, playerData.getTeamRevives() - 1);
                        playerData.update();
                        for(UUID targetUUID : main.gameManager.dead){
                            Player target = Bukkit.getPlayer(targetUUID);
                            main.gameManager.alive.add(targetUUID);
                            main.gameManager.dead.remove(targetUUID);
                            assert target != null;
                            target.setGameMode(GameMode.SURVIVAL);
                            target.teleport(main.gameManager.game);
                        }
                        Bukkit.broadcastMessage(main.textUtils.right + "§5" + player.getName() + "§7 just used §5Team Revive");
                        for(Player target : Bukkit.getOnlinePlayers()){
                            target.playSound(target.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                        }
                    }else{
                        player.sendMessage(main.textUtils.error + "You don't have enough §5Team Revives");
                    }
                    return false;
                }
            }

            player.sendMessage(main.textUtils.error + "§5Team Revive §7is not available");
            return false;
        }else{
            sender.sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
