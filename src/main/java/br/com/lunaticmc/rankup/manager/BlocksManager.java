package br.com.lunaticmc.rankup.manager;

import br.com.lunaticmc.blocks.object.controller.BlockPlayerController;
import lombok.Getter;

public class BlocksManager {

    @Getter private static final BlocksManager instance = new BlocksManager();

    public double get(String p){
        return BlockPlayerController.getInstance().get(p).getBalance();
    }

    public boolean has(String p, double amount){
        return BlockPlayerController.getInstance().get(p).has(amount);
    }

    public void remove(String p, double amount){
        BlockPlayerController.getInstance().get(p).remove(amount);
    }

}
