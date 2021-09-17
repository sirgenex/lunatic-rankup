package br.com.lunaticmc.rankup.object.controller;

import br.com.lunaticmc.rankup.object.RPlayer;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class RPlayerController {

    @Getter private static final RPlayerController instance = new RPlayerController();

    private final HashMap<String, RPlayer> cache = new HashMap<>();

    public void create(RPlayer model){ cache.put(model.getName(), model); }

    public RPlayer get(String name){ return cache.containsKey(name) ? cache.get(name) : new RPlayer(name, true, RankController.getInstance().firstRank); }

    public void saveAll(FileConfiguration c){
        cache.forEach((player, value) -> {
            c.set("players." + player + ".rank", value.getRank().getName());
            c.set("players."+player+".tag", value.isTagEnabled());
        });
    }

}
