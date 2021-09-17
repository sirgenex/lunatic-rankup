package br.com.lunaticmc.rankup.hook;

import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderHook extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "rankup";
    }

    @Override
    public String getPlugin() {
        return "lunatic-rankup";
    }

    @Override
    public String getAuthor() {
        return "SrGeneX";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        if(player == null) return "";
        if(s.equalsIgnoreCase("rank")) return RPlayerController.getInstance().get(player.getName()).getRank().getPrefix();
        return "";
    }
}
