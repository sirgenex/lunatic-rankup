package br.com.lunaticmc.rankup.builder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryBuilder {

    private final Inventory inventory;

    public InventoryBuilder(int hotbars, String title) {
        this.inventory = Bukkit.createInventory(null, 9*hotbars, title.replace("&", "§"));
    }

    public InventoryBuilder addItem(ItemStack item, Integer slot){
        inventory.setItem(slot, item);
        return this;
    }

    public InventoryBuilder addItems(HashMap<Integer, ItemStack> items){
        items.forEach(this.inventory::setItem);
        return this;
    }

    public InventoryBuilder decorate(ItemStack item){
        for(int i = 0 ; i < this.inventory.getSize() ; i++){
            if(this.inventory.getItem(i) == null) this.inventory.setItem(i, item);
        }
        return this;
    }

    public void open(Player p){ p.openInventory(inventory); }

}
