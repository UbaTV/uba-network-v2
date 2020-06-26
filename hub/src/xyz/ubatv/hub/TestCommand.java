package xyz.ubatv.hub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.getName().equalsIgnoreCase("andreubita")){
                main.playerBankManager.setServerCoins(player.getUniqueId(), main.playerBankManager.getServerCoins(player.getUniqueId()) + 1);
            }
        }
        return false;
    }
}
