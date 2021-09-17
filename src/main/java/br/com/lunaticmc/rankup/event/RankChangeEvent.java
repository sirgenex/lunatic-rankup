package br.com.lunaticmc.rankup.event;

import br.com.lunaticmc.rankup.object.Rank;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class RankChangeEvent extends Event implements Cancellable {

    private final String player;
    @Setter private boolean cancelled;
    private final Rank from;
    private final Rank to;

    public RankChangeEvent(String player, Rank from, Rank to) {
        this.player = player;
        setCancelled(false);
        this.from = from;
        this.to = to;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}