package br.com.lunaticmc.rankup.event.listener;

import br.com.lunaticmc.rankup.config.ConfigurationData;
import br.com.lunaticmc.rankup.menu.RanksMenu;
import br.com.lunaticmc.rankup.menu.RankupMenu;
import br.com.lunaticmc.rankup.object.RPlayer;
import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        String title = e.getInventory().getTitle();
        if(title.equalsIgnoreCase(RanksMenu.getInstance().title.replace("&", "§"))) e.setCancelled(true);
        if(!title.equalsIgnoreCase(RankupMenu.getInstance().title.replace("&", "§"))) return;
        e.setCancelled(true);
        Player p = (Player)e.getWhoClicked();
        RPlayer player = RPlayerController.getInstance().get(p.getName());
        if(e.getSlot() == 10) {
            p.closeInventory();
            RanksMenu.getInstance().open(p);
        }
        if(e.getSlot() == 16) {
            p.closeInventory();
            if(!player.getRank().isLast()) {
                if (player.can()) {
                    ConfigurationData.getInstance().rankup.forEach(msg -> p.sendMessage(msg.replace("&", "§").replace("{rank}", player.getRank().getNextRank().getPrefix())));
                    player.up();
                } else ConfigurationData.getInstance().without_requisits.forEach(msg -> p.sendMessage(msg.replace("&", "§")));
            }else ConfigurationData.getInstance().last_rank.forEach(msg -> p.sendMessage(msg.replace("&", "§")));
        }
    }

}
