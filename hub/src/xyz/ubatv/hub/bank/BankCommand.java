package xyz.ubatv.hub.bank;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.hub.Main;

public class BankCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                sendBankInfo(player, player);
            }else if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(main.textUtils.error + "Invalid player");
                    return false;
                }
                sendBankInfo(player, player);
            }else{
                player.sendMessage(main.textUtils.error + "/bank [player]");
            }
            return false;
        }else{
            sender.sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    public void sendBankInfo(Player player, Player target){
        int serverCoins = main.playerBankManager.getServerCoins(target.getUniqueId());
        int pveCoins = main.playerBankManager.getPvECoins(target.getUniqueId());

        if(player.equals(target)){
            player.sendMessage("   §8§l> §5§lYour §7Bank");
        }else{
            player.sendMessage("   §8§l> §5§l" + target.getName() + " §7Bank");
        }
        player.sendMessage(" §5Network §7Coins§8: §5§l" + serverCoins);
        player.sendMessage(" §5PvE §7Coins§8: §5§l" + pveCoins);
    }
}
