package me.alvsch.megamoderation.utils;

import com.google.gson.JsonObject;
import me.alvsch.megamoderation.MegaModeration;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.function.Consumer;

public class UpdateChecker {

    private final MegaModeration plugin;
    private final String repo;

    public UpdateChecker(MegaModeration plugin, String repo) {
        this.plugin = plugin;
        this.repo = repo;
    }

    public void getLatestVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            JsonObject jsonObject = null;
            try {
                jsonObject = JsonUtils.readJsonFromUrl("https://api.github.com/repos/" + repo + "/releases/latest");
            } catch (IOException e) {
                Bukkit.getLogger().info("Unable To Get New Updates! " + e.getMessage());
            }

            assert jsonObject != null;
            String version = jsonObject.get("tag_name").getAsString();
            consumer.accept(version);

        });
    }

}
