package me.alvsch.megamoderation;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final String name;
    private final UUID uuid;
    private final String address;

    private MuteEntry muteEntry = null;
    private boolean invsee = false;
    private boolean modmode = false;
    private boolean online;

    // Only when creating new user
    public User(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.address = player.getAddress().getHostName();

        setOnline(player.isOnline());
    }

    // Only when loading users from disk
    public User(FileConfiguration config) {
        this.name = config.getString("name");
        this.uuid = UUID.fromString(config.getString("uuid"));
        this.address = config.getString("address");

        this.muteEntry = (MuteEntry) config.get("mute");
        setOnline(false);
    }

    public void setInvsee(boolean invsee) {
        this.invsee = invsee;
    }

    public void setModmode(boolean modmode) {
        this.modmode = modmode;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setMute(MuteEntry muteEntry) {
        this.muteEntry = muteEntry;
    }


    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isInvsee() {
        return invsee;
    }

    public boolean isModmode() {
        return modmode;
    }

    public MuteEntry getMute() {
        return muteEntry;
    }

    public String getAddress() {
        return address;
    }

    public FileConfiguration getFileConfiguration() {
        FileConfiguration config = new YamlConfiguration();
        config.set("name", getName());
        config.set("uuid", getUniqueId().toString());
        config.set("address", getAddress());
        config.set("mute", getMute());
        return config;
    }
}
