package br.com.lunaticmc.rankup.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class CustomSkullBuilder {

    private final ItemStack item;

    public CustomSkullBuilder(String url){
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/"+url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try { profileField = meta.getClass().getDeclaredField("profile");
        } catch (Exception ignored) {}
        assert profileField != null;
        profileField.setAccessible(true);
        try { profileField.set(meta, profile); } catch (Exception ignored) { }
        itemStack.setItemMeta(meta);
        this.item = itemStack;
    }

    public CustomSkullBuilder setName(String name){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name.replace("&", "§"));
        this.item.setItemMeta(meta);
        return this;
    }

    public CustomSkullBuilder setLore(List<String> lines, String... replacement){
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
