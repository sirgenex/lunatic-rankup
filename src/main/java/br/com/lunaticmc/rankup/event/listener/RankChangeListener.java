package br.com.lunaticmc.rankup.event.listener;

import br.com.lunaticmc.rankup.event.RankChangeEvent;
import br.com.lunaticmc.rankup.object.RPlayer;
import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RankChangeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onRankChange(RankChangeEvent e){
        RPlayer player = RPlayerController.getInstance().get(e.getPlayer());
        player.setRank(e.getTo());
    }

}
