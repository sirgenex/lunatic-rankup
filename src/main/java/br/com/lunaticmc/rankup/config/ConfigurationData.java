package br.com.lunaticmc.rankup.config;

import br.com.lunaticmc.rankup.RankUP;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigurationData {

    @Getter private static final ConfigurationData instance = new ConfigurationData();

    private final FileConfiguration c = RankUP.getInstance().getConfig();

    public String permission = c.getString("permission");

    public List<String> without_requisits = c.getStringList("messages.without_requisits");
    public List<String> rankup = c.getStringList("messages.rankup");
    public List<String> last_rank = c.getStringList("messages.last_rank");
    public List<String> help = c.getStringList("messages.help");
    public List<String> without_permission = c.getStringList("messages.without_permission");
    public List<String> inexistent_player = c.getStringList("messages.inexistent_player");
    public List<String> inexistent_rank = c.getStringList("messages.inexistent_rank");
    public List<String> rank_setted = c.getStringList("messages.rank_setted");
    public List<String> player_lastRank = c.getStringList("messages.player_lastRank");
    public List<String> player_firstRank = c.getStringList("messages.player_firstRank");
    public List<String> player_promote = c.getStringList("messages.player_promote");
    public List<String> player_demote = c.getStringList("messages.player_demote");
    public List<String> tag_enabled = c.getStringList("messages.tag_enabled");
    public List<String> tag_disabled = c.getStringList("messages.tag_disabled");

}
