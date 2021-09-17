package br.com.lunaticmc.rankup.config.controller;

import br.com.lunaticmc.rankup.RankUP;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class FileController {

    @Getter private static final FileController instance = new FileController();

    private final FileConfiguration c = RankUP.getInstance().getConfig();

    public double getOrDefault(String path, double or){
        return c.getString(path) == null ? or : c.getDouble(path);
    }

    public String getOrDefault(String path, String or){
        return c.getString(path) == null ? or : c.getString(path);
    }

}
