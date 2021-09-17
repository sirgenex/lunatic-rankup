package br.com.lunaticmc.rankup.object.controller;

import br.com.lunaticmc.rankup.object.Rank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

public class RankController {

    @Getter private static final RankController instance = new RankController();

    @Getter @Setter public Rank firstRank;
    @Getter @Setter public Rank lastRank;

    public ArrayList<String> ranks = new ArrayList<>();

    private final HashMap<String, Rank> cache = new HashMap<>();

    public void create(Rank model){
        ranks.add(model.getName());
        cache.put(model.getName(), model);
    }

    public Rank get(String name){ return cache.getOrDefault(name, firstRank); }

}
