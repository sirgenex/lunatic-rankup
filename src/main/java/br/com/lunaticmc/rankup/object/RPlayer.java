package br.com.lunaticmc.rankup.object;

import br.com.lunaticmc.rankup.RankUP;
import br.com.lunaticmc.rankup.event.RankChangeEvent;
import br.com.lunaticmc.rankup.manager.BlocksManager;
import br.com.lunaticmc.rankup.manager.CoinsManager;
import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

@Getter
@Setter
@AllArgsConstructor
public class RPlayer {

    private String name;

    private boolean tagEnabled;

    private Rank rank;

    public boolean can(){
        boolean coins = CoinsManager.getInstance().has(name, rank.getCoinsPrice());
        boolean blocks = BlocksManager.getInstance().has(name, rank.getBlocksPrice());
        return coins && blocks;
    }

    public void up(){
        if(can() && !rank.isLast()) {
            CoinsManager.getInstance().remove(name, rank.getCoinsPrice());
            BlocksManager.getInstance().remove(name, rank.getBlocksPrice());
            upPlayer();
        }
    }

    public void upPlayer(){
        if(rank.isFirst()) RPlayerController.getInstance().create(this);
        RankChangeEvent event = new RankChangeEvent(name, rank, rank.getNextRank());
        Bukkit.getPluginManager().callEvent(event);
        RankUP.getInstance().getDB().set("players."+name+".rank", rank.getName());
        RankUP.getInstance().saveDB();
        RankUP.getInstance().reloadDB();
    }

}
