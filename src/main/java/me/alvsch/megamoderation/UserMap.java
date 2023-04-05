package me.alvsch.megamoderation;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserMap {

    private final HashMap<String, UUID> uuidMap = new HashMap<>();
    private final HashMap<UUID, User> userMap = new HashMap<>();

    public UserMap() {

    }

    public void loadData(final MegaModeration plugin) throws IOException, InvalidConfigurationException {
        loadUserMap(plugin);
        loadUUIDMap(plugin);
    }
    public void saveData(final MegaModeration plugin) throws IOException {
        saveUserMap(plugin);
        saveUUIDMap(plugin);
    }

    private void loadUUIDMap(final MegaModeration plugin) throws IOException, InvalidConfigurationException {
        File file = new File(plugin.getDataFolder(), "uuidmap.yml");
        file.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        config.load(file);
        for(Map.Entry<String, Object> entry : config.getValues(false).entrySet()) {
            addUUID(entry.getKey(), UUID.fromString((String) entry.getValue()));
        }
    }
    private void loadUserMap(final MegaModeration plugin) throws IOException, InvalidConfigurationException {
        File file = new File(plugin.getDataFolder(), "userdata");
        file.mkdirs();
        for(File f : file.listFiles()) {
            FileConfiguration config = new YamlConfiguration();
            config.load(f);
            addUser(new User(config));
        }
    }

    private void saveUserMap(final MegaModeration plugin) throws IOException {
        new File(plugin.getDataFolder(), "userdata").mkdirs();
        for(User user : userMap.values()) {
            File file = new File(plugin.getDataFolder() + "/userdata", user.getUniqueId().toString() + ".yml");
            user.getFileConfiguration().save(file);
        }
    }
    private void saveUUIDMap(final MegaModeration plugin) throws IOException {
        File file = new File(plugin.getDataFolder(), "uuidmap.yml");
        file.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        for(Map.Entry<String, UUID> entry : uuidMap.entrySet()) {
            config.set(entry.getKey(), entry.getValue().toString());
        }
        config.save(file);
    }

    private void addUser(User user) {
        userMap.put(user.getUniqueId(), user);
    }
    private void addUUID(String name, UUID uuid) {
        uuidMap.put(name.toLowerCase(), uuid);
    }

    public User registerPlayer(Player player) {
        addUUID(player.getName(), player.getUniqueId());
        return getUser(player);
    }

    public User getUser(Player player) {
        User user = userMap.get(player.getUniqueId());
        if(user == null) {
            user = new User(player);
            addUser(user);
        }
        return user;
    }
    public User getUser(UUID uuid) {
        return userMap.get(uuid);
    }

    public UUID getUUID(String name) {
        return uuidMap.get(name.toLowerCase());
    }

}
