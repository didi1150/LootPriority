package de.craftjunkies.lootpriority.listeners;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import de.craftjunkies.lootpriority.LootPriority;

public class DamageListener implements Listener {

	private Map<Entity, Map<Player, Double>> damageDealtToEntity;

	public DamageListener() {
		this.damageDealtToEntity = new HashMap<>();
	}

	@EventHandler
	public void onKill(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof Monster))
			return;

		if (!damageDealtToEntity.containsKey(entity))
			return;
		Map<Player, Double> damageMap = damageDealtToEntity.get(entity);
		Player player = Collections.max(damageMap.entrySet(), Map.Entry.comparingByValue()).getKey();
		if (LootPriority.KILL_COUNT.containsKey(player.getUniqueId())) {
			Integer existingKills = LootPriority.KILL_COUNT.get(player.getUniqueId());
			existingKills++;
			LootPriority.KILL_COUNT.put(player.getUniqueId(), existingKills);
		} else {
			// Player's first kill
			LootPriority.KILL_COUNT.put(player.getUniqueId(), 1);
		}
	}

	@EventHandler
	public void onDamageByPlayer(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player player))
			return;

		Entity entity = event.getEntity();
		if (!(entity instanceof Monster))
			return;
		double damageDealt = event.getDamage();
		if (damageDealtToEntity.containsKey(entity)) {
			// Entity has been damaged before
			if (damageDealtToEntity.get(entity).containsKey(player)) {
				// Player has damaged entity before
				double existingDamage = damageDealtToEntity.get(entity).get(player);
				existingDamage += damageDealt;
				damageDealtToEntity.get(entity).put(player, existingDamage);
			} else {
				// Player has NEVER attacked entity before
				damageDealtToEntity.get(entity).put(player, damageDealt);
			}

		} else {
			// Entity has NOT been damaged before
			Map<Player, Double> playerDamage = new HashMap<>();
			playerDamage.put(player, damageDealt);
			damageDealtToEntity.put(entity, playerDamage);
		}
	}

}
