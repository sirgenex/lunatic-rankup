package br.com.lunaticmc.rankup.object.adapter;

import br.com.lunaticmc.rankup.object.RPlayer;
import br.com.lunaticmc.rankup.object.Rank;
import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import br.com.lunaticmc.rankup.object.controller.RankController;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class RPlayerAdapter {

    @Getter private static final RPlayerAdapter instance = new RPlayerAdapter();

    public void adapt(FileConfiguration c){

        c.getConfigurationSection("players").getKeys(false).forEach(player -> {

            String name = c.getString("players."+player+".rank");
            Rank rank = RankController.getInstance().get(name);
            boolean tagEnabled = c.getBoolean("players."+player+".tag");
            RPlayer model = new RPlayer(player, tagEnabled, rank);

            RPlayerController.getInstance().create(model);

        });

    }

}
