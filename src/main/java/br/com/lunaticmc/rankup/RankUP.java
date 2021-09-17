package br.com.lunaticmc.rankup;

import br.com.lunaticmc.rankup.event.listener.RankChangeListener;
import br.com.lunaticmc.rankup.object.adapter.RPlayerAdapter;
import br.com.lunaticmc.rankup.object.adapter.RankAdapter;
import br.com.lunaticmc.rankup.command.RanksCommand;
import br.com.lunaticmc.rankup.command.RankupCommand;
import br.com.lunaticmc.rankup.hook.PlaceholderHook;
import br.com.lunaticmc.rankup.hook.nChatHook;
import br.com.lunaticmc.rankup.event.listener.MenuListener;
import br.com.lunaticmc.rankup.menu.RanksMenu;
import br.com.lunaticmc.rankup.object.controller.RPlayerController;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class RankUP extends JavaPlugin {

    @Getter private static RankUP instance;

    public Economy econ = null;

    private File file = null;
    private FileConfiguration fileConfiguration = null;

    @Override
    public void onEnable() {
        getLogger().info("Starting plugin...");
        instance = this;

        getLogger().info("Loading config...");
        saveDefaultConfig();
        getLogger().info("Config loaded!");

        getLogger().info("Hooking into Vault...");
        if (!setupEconomy() ) {
            getLogger().severe("Vault not found, disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Vault hooked successfully!");

        getLogger().info("Loading database...");
        File verifier = new File(getDataFolder(), "db.yml");
        if (!verifier.exists()) saveResource("db.yml", false);
        RankAdapter.getInstance().adapt(getConfig());
        RPlayerAdapter.getInstance().adapt(getDB());
        getLogger().info("Database loaded!");

        getLogger().info("Registering commands...");
        getCommand("ranks").setExecutor(new RanksCommand());
        getCommand("rankup").setExecutor(new RankupCommand());
        getLogger().info("Commands registered!");

        getLogger().info("Registering listeners...");
        Bukkit.getPluginManager().registerEvents(new RankChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        getLogger().info("Listeners registered!");

        getLogger().info("Loading menus...");
        RanksMenu.getInstance().load(getConfig().getConfigurationSection("menus.ranks.ranks"));
        getLogger().info("Menus loaded!");

        getLogger().info("Trying to hook into PlaceholderAPI...");
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook().register();
            getLogger().info("PlaceholderAPI hooked successfully.");
        } else getLogger().warning("PlaceholderAPI can't be found.");

        getLogger().info("Trying to hook into nChat...");
        if(Bukkit.getPluginManager().getPlugin("nChat") != null) {
            new nChatHook(this);
            getLogger().info("nChat hooked successfully.");
        } else getLogger().warning("nChat can't be found.");

        getLogger().info("Plugin started!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling plugin...");

        getLogger().info("Saving data...");
        RPlayerController.getInstance().saveAll(getDB());
        saveDB();
        reloadDB();
        getLogger().info("Data saved successfully!");

        getLogger().info("Plugin disabled!");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    public FileConfiguration getDB() {
        if (this.fileConfiguration == null) {
            this.file = new File(getDataFolder(), "db.yml");
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        }
        return this.fileConfiguration;
    }

    public void saveDB() {
        try { getDB().save(this.file); } catch (Exception ignored) {}
    }

    public void reloadDB() {
        if (this.file == null) this.file = new File(getDataFolder(), "db.yml");
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        YamlConfiguration db = YamlConfiguration.loadConfiguration(this.file);
        this.fileConfiguration.setDefaults(db);
    }

}