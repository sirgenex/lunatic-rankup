package br.com.lunaticmc.rankup.manager;

import br.com.lunaticmc.rankup.RankUP;
import lombok.Getter;

@SuppressWarnings("deprecation")
public class CoinsManager {

    @Getter private static final CoinsManager instance = new CoinsManager();

    public double get(String p){
        return RankUP.getInstance().econ.getBalance(p);
    }

    public boolean has(String p, double amount){
        return RankUP.getInstance().econ.has(p, amount);
    }

    public void remove(String p, double amount){
        RankUP.getInstance().econ.withdrawPlayer(p, amount);
    }

}
