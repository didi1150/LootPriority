package de.craftjunkies.lootpriority.config;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigIO {

	private Plugin plugin;
	private File file;
	private FileConfiguration fileConfiguration;

	public ConfigIO(Plugin plugin) {
		this.plugin = plugin;

		file = new File(plugin.getDataFolder(), "killcount.yml");
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		fileConfiguration = YamlConfiguration.loadConfiguration(file);
	}

	public void saveKills(UUID uuid, int amount) {
		Bukkit.getAsyncScheduler().runNow(plugin, task -> {
			fileConfiguration.set(uuid.toString() + ".kills", amount);
			try {
				fileConfiguration.save(file);
			} catch (IOException e) {
				System.out.println("Error while trying to save kills");
			}
		});
	}

	public int loadKills(UUID uuid) {
		AtomicInteger kills = new AtomicInteger();
		Bukkit.getAsyncScheduler().runNow(plugin, task -> {
			kills.set(fileConfiguration.getInt(uuid.toString() + ".kills"));
		});
		return kills.get();
	}

}
