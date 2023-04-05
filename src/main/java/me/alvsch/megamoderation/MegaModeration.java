package me.alvsch.megamoderation;

import me.alvsch.megamoderation.commands.CommandManager;
import me.alvsch.megamoderation.commands.commands.chat.ClearChat;
import me.alvsch.megamoderation.commands.commands.chat.GlobalMute;
import me.alvsch.megamoderation.commands.commands.chat.StaffChat;
import me.alvsch.megamoderation.commands.commands.moderation.*;
import me.alvsch.megamoderation.commands.commands.utility.*;
import me.alvsch.megamoderation.commands.main.Main;
import me.alvsch.megamoderation.events.InventoryListener;
import me.alvsch.megamoderation.events.PlayerListener;
import me.alvsch.megamoderation.events.Runnable;
import me.alvsch.megamoderation.events.ServerListener;
import me.alvsch.megamoderation.items.ItemHandler;
import me.alvsch.megamoderation.utils.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class MegaModeration extends JavaPlugin {

    static {
        ConfigurationSerialization.registerClass(MuteEntry.class, "Mute");
    }

    public boolean globalmute = false;
    public List<UUID> staffchat = new ArrayList<>();
    public List<Player> vanish = new ArrayList<>();
    public List<Player> freeze = new ArrayList<>();
    public List<UUID> silentcontainer = new ArrayList<>();
    public List<UUID> disablepickup = new ArrayList<>();
    public HashMap<Player, ItemStack[]> modmode = new HashMap<>();

    public ItemHandler itemHandler;
    public CommandManager commandManager;

    public enum Version{LATEST, OUTDATED, UNSTABLE}
    private Version version;

    private final UUID author = UUID.fromString("23ccb8ea-8395-4260-9752-d34b634dd38d");

    @Override
    public void onEnable() {
        // Plugin startup logic

        Logger.log(Logger.LogLevel.OUTLINE, "********************");
        Logger.log(Logger.LogLevel.INFO, "Plugin is loading...");

        itemHandler = new ItemHandler(this);
        commandManager = new CommandManager(this);

        Logger.log(Logger.LogLevel.INFO, "Commands are loading...");
        registerCommand();
        Logger.log(Logger.LogLevel.INFO, "Commands are loaded!");
        Logger.log(Logger.LogLevel.INFO, "Events are loading...");
        registerEvents();
        Logger.log(Logger.LogLevel.INFO, "Events are loaded!");
        try {
            Logger.log(Logger.LogLevel.INFO, "Metrics are loading...");
            Metrics metrics = new Metrics(this, 16385);
            Logger.log(Logger.LogLevel.INFO, "Metrics are loaded!");
        } catch (Exception e){
            Logger.log(Logger.LogLevel.WARNING, "Could not load metrics");
        }
        Logger.log(Logger.LogLevel.INFO, "Files are loading!");
        createFiles();
        Logger.log(Logger.LogLevel.INFO, "Files are loaded!");
        new UpdateChecker(this, "Alvsch/MegaModeration").getLatestVersion(version -> {

            if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                this.version = Version.LATEST;
            } else {
                this.version = Version.OUTDATED;
            }
        });

        Logger.log(Logger.LogLevel.SUCCESS, "Plugin is loaded!");
        Logger.log(Logger.LogLevel.OUTLINE, "********************");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        try {
            usermap.saveData(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Player p : modmode.keySet()) {
            commandManager.get("modmode").onCommand(p, new String[0]);
        }

    }


    private void registerCommand() {

        // Main
        commandManager.addCommand(new Main(this));

        // Chat
        commandManager.addCommand(new ClearChat(this));
        commandManager.addCommand(new GlobalMute(this));
        commandManager.addCommand(new StaffChat(this));


        // Moderation
        commandManager.addCommand(new Ban(this));
        commandManager.addCommand(new Tempban(this));
        commandManager.addCommand(new Unban(this));

        commandManager.addCommand(new Mute(this));
        commandManager.addCommand(new Unmute(this));

        commandManager.addCommand(new Kick(this));

        commandManager.addCommand(new Freeze(this));


        // Utility
        commandManager.addCommand(new Invsee(this));
        commandManager.addCommand(new EnderSee(this));

        commandManager.addCommand(new Vanish(this));
        commandManager.addCommand(new SilentContainer(this));
        commandManager.addCommand(new TogglePickup(this));

        commandManager.addCommand(new ModMode(this));

        // Init
        commandManager.init();

    }

    private void registerEvents() {

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ServerListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);

        Runnable runnable = new Runnable(this);
        runnable.startVanishActionBarTask();
        runnable.startFrozenActionBarTask();

    }

    private void loadFile(File file, String resourcePath) {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource(resourcePath, false);
        }
    }

    private File configf, languagef, userdataf;
    public FileConfiguration config, language;
    public UserMap usermap = new UserMap();

    public boolean createFiles() {
        configf = new File(getDataFolder(), "config.yml");
        languagef = new File(getDataFolder(), "language.yml");

        userdataf = new File(getDataFolder(), "userdata");
        userdataf.mkdirs();

        loadFile(configf, "config.yml");
        loadFile(languagef, "language.yml");

        config = new YamlConfiguration();
        language = new YamlConfiguration();
        try {
            config.load(configf);
            language.load(languagef);

            usermap.loadData(this);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Version getVersion() {
        return version;
    }
    public UUID getAuthor() {
        return author;
    }

    public String getPhrase(String str, String path) {
        return str + getPhrase(path);
    }
    public String getPhrase(ChatColor color, String path) {
        return color + getPhrase(path);
    }
    public String getPhrase(String path) {
        String phrase = language.getString(path);
        if(phrase == null) {
            Logger.log(Logger.LogLevel.WARNING, "Missing phrase in language.yml: " + path);
        }
        return phrase;
    }


}
