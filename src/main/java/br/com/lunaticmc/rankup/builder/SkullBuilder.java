package br.com.lunaticmc.rankup.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class SkullBuilder {

    private final ItemStack item;

    public SkullBuilder(String owner){
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
        meta.setOwner(owner);
        itemStack.setItemMeta(meta);
        this.item = itemStack;
    }

    public SkullBuilder setName(String name){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name.replace("&", "§"));
        this.item.setItemMeta(meta);
        return this;
    }

    public SkullBuilder setLore(List<String> lines, String... replacement){
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
