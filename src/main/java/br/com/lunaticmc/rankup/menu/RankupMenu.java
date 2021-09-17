package br.com.lunaticmc.rankup.menu;

import br.com.lunaticmc.rankup.RankUP;
import br.com.lunaticmc.rankup.builder.ConfigItemBuilder;
import br.com.lunaticmc.rankup.builder.InventoryBuilder;
import br.com.lunaticmc.rankup.builder.SkullBuilder;
import br.com.lunaticmc.rankup.manager.BlocksManager;
import br.com.lunaticmc.rankup.manager.CoinsManager;
import br.com.lunaticmc.rankup.manager.FormatManager;
import br.com.lunaticmc.rankup.object.RPlayer;
import br.com.lunaticmc.rankup.object.Rank;
import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RankupMenu {

    @Getter private static final RankupMenu instance = new RankupMenu();

    private final ItemStack ranks = new ConfigItemBuilder("menus.rankup.ranks").setName().setLore().get();

    private final ItemStack decoration = new ConfigItemBuilder("menus.rankup.decoration").setName().get();

    private final String skull_name = RankUP.getInstance().getConfig().getString("menus.rankup.info.name");
    private final List<String> skull_lore = new ArrayList<>(RankUP.getInstance().getConfig().getStringList("menus.rankup.info.lore"));
    private final String skull_nameLast = RankUP.getInstance().getConfig().getString("menus.rankup.info_last.name");
    private final List<String> skull_loreLast = new ArrayList<>(RankUP.getInstance().getConfig().getStringList("menus.rankup.info_last.lore"));

    private final ItemStack rankup_can = new ConfigItemBuilder("menus.rankup.rankup_can").setName().setLore().get();
    private final ItemStack rankup_cannot = new ConfigItemBuilder("menus.rankup.rankup_cannot").setName().setLore().get();
    private final ItemStack rankup_last = new ConfigItemBuilder("menus.rankup.rankup_last").setName().setLore().get();

    public final String title = RankUP.getInstance().getConfig().getString("menus.rankup.title");
    private final int hotbars = RankUP.getInstance().getConfig().getInt("menus.rankup.hotbars");

    public void open(Player p){

        RPlayer player = RPlayerController.getInstance().get(p.getName());

        Rank rank = player.getRank();
        Rank next_rank = rank.getNextRank();

        ChatColor coinColor = CoinsManager.getInstance().has(p.getName(), rank.getCoinsPrice()) ? ChatColor.GREEN : ChatColor.RED;
        ChatColor blockColor = BlocksManager.getInstance().has(p.getName(), rank.getBlocksPrice()) ? ChatColor.GREEN : ChatColor.RED;

        ItemStack skull = rank.isLast() ?
                new SkullBuilder(p.getName()).setName(skull_nameLast).setLore(skull_loreLast,
                "{rank} -> "+rank.getPrefix()).get() :
                new SkullBuilder(p.getName()).setName(skull_name).setLore(skull_lore,
                "{player_coins} -> §"+coinColor.getChar()+FormatManager.getInstance().format(CoinsManager.getInstance().get(p.getName())),
                "{coins} -> "+ FormatManager.getInstance().format(rank.getCoinsPrice()),
                "{player_blocks} -> §"+blockColor.getChar()+FormatManager.getInstance().format(BlocksManager.getInstance().get(p.getName())),
                "{blocks} -> "+FormatManager.getInstance().format(rank.getBlocksPrice()),
                "{rank} -> "+rank.getPrefix(),
                "{next_rank} -> "+next_rank.getPrefix()).get();

        ItemStack rankup = rank.isLast() ? rankup_last : player.can() ? rankup_can : rankup_cannot;

        new InventoryBuilder(hotbars, title).addItem(ranks, 10).addItem(skull, 13).addItem(rankup, 16).decorate(decoration).open(p);
    }

}
