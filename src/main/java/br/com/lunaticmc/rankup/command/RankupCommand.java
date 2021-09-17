package br.com.lunaticmc.rankup.command;

import br.com.lunaticmc.rankup.config.ConfigurationData;
import br.com.lunaticmc.rankup.event.RankChangeEvent;
import br.com.lunaticmc.rankup.menu.RankupMenu;
import br.com.lunaticmc.rankup.object.RPlayer;
import br.com.lunaticmc.rankup.object.Rank;
import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import br.com.lunaticmc.rankup.object.controller.RankController;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class RankupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        final ConfigurationData c = ConfigurationData.getInstance();

        if(args.length == 0){
            if(sender instanceof Player) RankupMenu.getInstance().open((Player)sender);
            else sender.sendMessage("§cComando apenas para jogadores.");
        }else {
            if (sender.hasPermission(c.permission)) {
                if (args.length >= 2) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                    if (p.hasPlayedBefore()) {
                        RPlayer player = RPlayerController.getInstance().get(p.getName());
                        Rank rank = player.getRank();
                        if (args[0].equalsIgnoreCase("setar")) {
                            if (args.length >= 3) {
                                ArrayList<String> ranks = RankController.getInstance().ranks;
                                if(ranks.contains(args[2])) {
                                    Rank nextRank = RankController.getInstance().get(args[2]);
                                    RankChangeEvent event = new RankChangeEvent(p.getName(), rank, nextRank);
                                    Bukkit.getPluginManager().callEvent(event);
                                    c.rank_setted.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName()).replace("{rank}", nextRank.getPrefix())));
                                } else {
                                    c.inexistent_rank.forEach(msg -> {
                                        if (msg.contains("@showRankList"))
                                            ranks.forEach(name -> sender.sendMessage("§7- " + name));
                                        else sender.sendMessage(msg.replace("&", "§"));
                                    });
                                }
                                return true;
                            }
                        }
                        if (args[0].equalsIgnoreCase("promover")) {
                            if (!rank.isLast()) {
                                RankChangeEvent event = new RankChangeEvent(p.getName(), rank, rank.getNextRank());
                                Bukkit.getPluginManager().callEvent(event);
                                c.player_promote.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName()).replace("{rank}", rank.getNextRank().getPrefix())));
                                return true;
                            } else {
                                c.player_lastRank.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName())));
                                return true;
                            }
                        }
                        if (args[0].equalsIgnoreCase("rebaixar")) {
                            if (!rank.isFirst()) {
                                RankChangeEvent event = new RankChangeEvent(p.getName(), rank, rank.getPreviousRank());
                                Bukkit.getPluginManager().callEvent(event);
                                c.player_demote.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName()).replace("{rank}", rank.getPreviousRank().getPrefix())));
                                return true;
                            } else {
                                c.player_firstRank.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName())));
                                return true;
                            }
                        }
                        if (args[0].equalsIgnoreCase("first")) {
                            Rank nextRank = RankController.getInstance().getFirstRank();
                            RankChangeEvent event = new RankChangeEvent(p.getName(), rank, nextRank);
                            Bukkit.getPluginManager().callEvent(event);
                            c.rank_setted.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName()).replace("{rank}", nextRank.getPrefix())));
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("tag")) {
                            if (player.isTagEnabled()) {
                                player.setTagEnabled(false);
                                c.tag_disabled.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName())));
                            } else {
                                player.setTagEnabled(true);
                                c.tag_enabled.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName())));
                            }
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("last")) {
                            Rank nextRank = RankController.getInstance().getLastRank();
                            RankChangeEvent event = new RankChangeEvent(p.getName(), rank, nextRank);
                            Bukkit.getPluginManager().callEvent(event);
                            c.rank_setted.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", p.getName()).replace("{rank}", nextRank.getPrefix())));
                            return true;
                        }
                    } else {
                        c.inexistent_player.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
                        return true;
                    }
                }
                c.help.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
            } else c.without_permission.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
        }
        return false;
    }
}
