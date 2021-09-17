package br.com.lunaticmc.rankup.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Rank {

    private String name, prefix;

    private double coinsPrice, blocksPrice;

    private Rank previousRank, nextRank;

    private int position;

    public boolean isLast(){
        return nextRank == null;
    }

    public boolean isFirst(){
        return previousRank == null;
    }

}
