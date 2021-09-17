package br.com.lunaticmc.rankup.hook;

import br.com.lunaticmc.rankup.object.RPlayer;
import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import com.nickuc.chat.api.events.PublicMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class nChatHook implements Listener {

    public nChatHook(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(PublicMessageEvent e){
        RPlayer player = RPlayerController.getInstance().get(e.getSender().getName());
        if(player.isTagEnabled()) e.setTag("rank", player.getRank().getPrefix());
    }

}
