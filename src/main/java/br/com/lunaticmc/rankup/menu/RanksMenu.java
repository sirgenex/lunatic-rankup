package br.com.lunaticmc.rankup.menu;

import br.com.lunaticmc.rankup.RankUP;
import br.com.lunaticmc.rankup.builder.ConfigItemBuilder;
import br.com.lunaticmc.rankup.builder.CustomSkullBuilder;
import br.com.lunaticmc.rankup.builder.InventoryBuilder;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class RanksMenu {

    @Getter private static final RanksMenu instance = new RanksMenu();

    private final HashMap<Integer, ItemStack> ranks = new HashMap<>();

    private final ItemStack decoration = new ConfigItemBuilder("menus.rankup.decoration").setName().get();

    public final String title = RankUP.getInstance().getConfig().getString("menus.ranks.title");
    private final int hotbars = RankUP.getInstance().getConfig().getInt("menus.ranks.hotbars");

    public void load(ConfigurationSection section){
        section.getKeys(false).forEach(path -> {
            int slot = RankUP.getInstance().getConfig().getInt("menus.ranks.ranks."+path+".slot");
            String url = RankUP.getInstance().getConfig().getString("menus.ranks.ranks."+path+".skull-url");
            String name = RankUP.getInstance().getConfig().getString("menus.ranks.ranks."+path+".name");
            List<String> lore = RankUP.getInstance().getConfig().getStringList("menus.ranks.ranks."+path+".lore");
            ranks.put(slot, new CustomSkullBuilder(url).setName(name).setLore(lore).get());
        });
    }

    public void open(Player p){

        new InventoryBuilder(hotbars, title).addItems(ranks).decorate(decoration).open(p);

    }

}
