package br.com.lunaticmc.rankup.command;

import br.com.lunaticmc.rankup.menu.RanksMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RanksCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) RanksMenu.getInstance().open((Player)sender);
        else sender.sendMessage("§cComando apenas para jogadores.");

        return false;
    }

}
