package br.com.lunaticmc.rankup.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(int type, int amount, int data){
        this.item = new ItemStack(Material.getMaterial(type), amount, (short)data);
    }

    public ItemBuilder setName(String name){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name.replace("&", "§"));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lines, String... replacement){
        ArrayList<String> lore = new ArrayList<>();
        lines.forEach(line -> {
            for (String s : replacement) line = line.replace(s.split(" -> ")[0], s.split(" -> ")[1]);
            lore.add(line.replace("&", "§"));
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
