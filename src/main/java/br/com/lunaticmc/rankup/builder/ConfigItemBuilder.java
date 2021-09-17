package br.com.lunaticmc.rankup.builder;

import br.com.lunaticmc.rankup.RankUP;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class ConfigItemBuilder {

    private final ItemStack item;
    private final FileConfiguration c = RankUP.getInstance().getConfig();
    private final String path;

    public ConfigItemBuilder(String path){
        String[] split = c.getString(path+".item").split(";");
        int data = Integer.parseInt(split[1]);
        int type = Integer.parseInt(split[0]);
        this.path = path;
        this.item = new ItemStack(Material.getMaterial(type), 1, (short)data);
    }

    public ConfigItemBuilder setName(String name){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name.replace("&", "§"));
        this.item.setItemMeta(meta);
        return this;
    }

    public ConfigItemBuilder setName(){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(c.getString(this.path+".name").replace("&", "§"));
        this.item.setItemMeta(meta);
        return this;
    }

    public ConfigItemBuilder setLore(String... replacement){
        ArrayList<String> lore = new ArrayList<>();
        c.getStringList(this.path+".lore").forEach(line -> {
            String replacedLine = line;
            for (String s : replacement) {
                String[] split = s.split(" -> ");
                replacedLine = replacedLine.replace(split[0], split[1]);
            }
            lore.add(replacedLine.replace("&", "§"));
        });
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(lore);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack get(){
        return this.item;
    }

}
