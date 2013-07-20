package io.github.screeder.foodbarchanger.foodchanger.listener;

import io.github.screeder.foodbarchanger.foodchanger.Food;
import io.github.screeder.foodbarchanger.foodchanger.FoodChanger;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EatListener implements Listener {

	FoodChanger plugin;
	
	final int MAXVALUE = 20;
	
	public EatListener(FoodChanger instance)
	{
		plugin = instance;
		plugin.plugin.getServer().getPluginManager().registerEvents(this, plugin.plugin);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(plugin.modifyFood && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CAKE_BLOCK && event.getPlayer().getFoodLevel() < 20 && event.getPlayer().hasPermission("foodbarchanger.change"))
		{
			Player player = event.getPlayer();
			Food food = plugin.foodDetails.get(Material.CAKE.getId());
			if(player == null || food == null)
				return;
			if(player.getFoodLevel()-2 + food.food > 20)
				player.setFoodLevel(MAXVALUE);
			else
				player.setFoodLevel(player.getFoodLevel()-2 + food.food);
			if(player.getHealth() + food.health > 20)
				player.setHealth(MAXVALUE);
			else
				player.setHealth(player.getHealth() + food.health);
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event)
	{
		Player player = (Player)event.getEntity();
		Food food = plugin.foodDetails.get(player.getItemInHand().getTypeId());
		if(player == null || food == null || player.getFoodLevel() >= event.getFoodLevel() || !plugin.modifyFood)
			return;
		if(!player.hasPermission("foodbarchanger.change"))
			return;
		if(player.getFoodLevel() + food.food > 20)
			event.setFoodLevel(MAXVALUE);
		else
			event.setFoodLevel(player.getFoodLevel() + food.food);
		if(player.getHealth() + food.health > 20)
			player.setHealth(MAXVALUE);
		else
			player.setHealth(player.getHealth() + food.health);
	}
}
