package io.github.screeder.foodbarchanger.Listener;

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
		if(!plugin.bRegenerate)
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
		event.getPlayer().setExhaustion((float) (event.getPlayer().getExhaustion() + plugin.FoodDrain));
	}
}
