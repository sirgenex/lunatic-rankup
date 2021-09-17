package br.com.lunaticmc.rankup.object.adapter;

import br.com.lunaticmc.rankup.config.controller.FileController;
import br.com.lunaticmc.rankup.object.Rank;
import br.com.lunaticmc.rankup.object.controller.RankController;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.atomic.AtomicReference;

public class RankAdapter {

    @Getter private static final RankAdapter instance = new RankAdapter();

    public void adapt(FileConfiguration c){

        int[] position = {1};
        AtomicReference<Rank> previous = new AtomicReference<>();
        AtomicReference<Rank> last = new AtomicReference<>();

        c.getConfigurationSection("ranks").getKeys(false).forEach(rank -> {

            double coins = FileController.getInstance().getOrDefault("ranks."+rank+".price.coins", 0);
            double blocks = FileController.getInstance().getOrDefault("ranks."+rank+".price.blocks", 0);
            String prefix = FileController.getInstance().getOrDefault("ranks."+rank+".prefix", "&7").replace("&", "§");
            Rank model = new Rank(rank, prefix, coins, blocks, previous.get(), null, position[0]);

            if(previous.get() != null){
                previous.get().setNextRank(model);
            }else{
                RankController.getInstance().setFirstRank(model);
            }

            previous.set(model);
            position[0]++;
            last.set(model);

            RankController.getInstance().create(model);

        });

        RankController.getInstance().setLastRank(last.get());

    }

}
