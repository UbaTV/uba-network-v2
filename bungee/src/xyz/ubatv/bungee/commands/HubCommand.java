package xyz.ubatv.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.ubatv.bungee.Main;

public class HubCommand extends Command {

    private Main main = Main.getInstance();

    public HubCommand() {
        super("hub");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(player.getServer().getInfo().getName().equalsIgnoreCase("hub")){
                player.sendMessage(new TextComponent(main.textUtils.error + "You are already connected to the §5§lHub"));
                return;
            }

            ServerInfo hub = main.getProxy().getServerInfo("hub");
            player.connect(hub);
        }else{
            main.getLogger().warning(main.textUtils.playerOnly);
        }
    }
}
