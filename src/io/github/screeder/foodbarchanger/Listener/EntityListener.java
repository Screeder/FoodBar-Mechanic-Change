package io.github.screeder.foodbarchanger.Listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.screeder.foodbarchanger.FoodBarChanger;

public class EntityListener implements Listener {
	
	FoodBarChanger plugin;
	
	public EntityListener(FoodBarChanger instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onRegainHealth(EntityRegainHealthEvent event)
	{
		if(!plugin.bRegenerate && event.getEntityType() == EntityType.PLAYER && ((Player) event.getEntity()).hasPermission("foodbarchanger.change"))
		{
			if(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if(event.getPlayer().hasPermission("foodbarchanger.change"))
			event.getPlayer().setExhaustion((float) (event.getPlayer().getExhaustion() + plugin.FoodDrain));
	}
}
