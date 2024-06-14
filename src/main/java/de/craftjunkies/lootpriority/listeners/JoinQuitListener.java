package de.craftjunkies.lootpriority.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.craftjunkies.lootpriority.LootPriority;
import de.craftjunkies.lootpriority.config.ConfigIO;

public class JoinQuitListener implements Listener {

	private ConfigIO configIO;

	public JoinQuitListener(ConfigIO configIO) {
		this.configIO = configIO;
	}

	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent event) {
		LootPriority.KILL_COUNT.put(event.getPlayerProfile().getId(),
				configIO.loadKills(event.getPlayerProfile().getId()));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Integer kills = LootPriority.KILL_COUNT.remove(event.getPlayer().getUniqueId());
		configIO.saveKills(event.getPlayer().getUniqueId(), kills);
	}

}
