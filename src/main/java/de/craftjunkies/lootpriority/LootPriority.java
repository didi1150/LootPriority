package de.craftjunkies.lootpriority;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.craftjunkies.lootpriority.config.ConfigIO;
import de.craftjunkies.lootpriority.listeners.DamageListener;
import de.craftjunkies.lootpriority.listeners.JoinQuitListener;

public final class LootPriority extends JavaPlugin {

	public static Map<UUID, Integer> KILL_COUNT = new HashMap<>();

	@Override
	public void onEnable() {
		ConfigIO configIO = new ConfigIO(this);

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new JoinQuitListener(configIO), this);
		pm.registerEvents(new DamageListener(), this);
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
	}
}
