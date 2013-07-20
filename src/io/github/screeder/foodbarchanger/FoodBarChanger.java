package io.github.screeder.foodbarchanger;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import io.github.screeder.foodbarchanger.Listener.EntityListener;
import io.github.screeder.foodbarchanger.foodchanger.*;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

//Todo: Multiworld ändern, aus config ausleen lassen welche Welten.


public class FoodBarChanger extends JavaPlugin {
		
	int FoodLevel = 10;
	public double FoodDrain = 0.001;
	public boolean bRegenerate = true;
	Collection<PotionEffect> Potions = new LinkedList<PotionEffect>();
	HashMap<String, Boolean> players = new HashMap<String, Boolean>();
	
	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		List<World> worlds = getServer().getWorlds();
		//World world = getServer().getWorld(worlds.get(0).getName());
		FoodLevel = getConfig().getInt("main.FoodLevel");
		FoodDrain = getConfig().getDouble("main.FoodDrain");
		bRegenerate = getConfig().getBoolean("main.Regenerate");
		readPotions();
		runCheckFood(worlds);
		new EntityListener(this);
		new FoodChanger(this);
	}

	@Override
	public void onDisable()
	{
		Potions.clear();
		players.clear();
	}
	
	private void readPotions()
	{
		for(int i = 1; ; i++)
		{
			String name = getConfig().getString("main.Potions.Potion" + i + ".Name");
			if(name == null)
				break;
			int amplifier = getConfig().getInt("main.Potions.Potion" + i + ".Amplifier");				
			PotionEffectType p = getPotionEffectType(name);
			if(p != null)
				Potions.add(new PotionEffect(getPotionEffectType(name), (int) (20L*2), amplifier, false));
		}
	}
	
	private PotionEffectType getPotionEffectType(String name)
	{
		if(name.equalsIgnoreCase("BLINDNESS"))
			return PotionEffectType.BLINDNESS;
		if(name.equalsIgnoreCase("CONFUSION"))
			return PotionEffectType.CONFUSION;
		if(name.equalsIgnoreCase("DAMAGE_RESISTANCE"))
			return PotionEffectType.DAMAGE_RESISTANCE;
		if(name.equalsIgnoreCase("FAST_DIGGING"))
			return PotionEffectType.FAST_DIGGING;
		if(name.equalsIgnoreCase("FIRE_RESISTANCE"))
			return PotionEffectType.FIRE_RESISTANCE;
		if(name.equalsIgnoreCase("HARM"))
			return PotionEffectType.HARM;
		if(name.equalsIgnoreCase("HEAL"))
			return PotionEffectType.HEAL;
		if(name.equalsIgnoreCase("HUNGER"))
			return PotionEffectType.HUNGER;
		if(name.equalsIgnoreCase("INCREASE_DAMAGE"))
			return PotionEffectType.INCREASE_DAMAGE;
		if(name.equalsIgnoreCase("INVISIBILITY"))
			return PotionEffectType.INVISIBILITY;
		if(name.equalsIgnoreCase("JUMP"))
			return PotionEffectType.JUMP;
		if(name.equalsIgnoreCase("NIGHT_VISION"))
			return PotionEffectType.NIGHT_VISION;
		if(name.equalsIgnoreCase("POISON"))
			return PotionEffectType.POISON;
		if(name.equalsIgnoreCase("REGENERATION"))
			return PotionEffectType.REGENERATION;
		if(name.equalsIgnoreCase("SLOW"))
			return PotionEffectType.SLOW;
		if(name.equalsIgnoreCase("SLOW_DIGGING"))
			return PotionEffectType.SLOW_DIGGING;
		if(name.equalsIgnoreCase("SPEED"))
			return PotionEffectType.SPEED;
		if(name.equalsIgnoreCase("WATER_BREATHING"))
			return PotionEffectType.WATER_BREATHING;
		if(name.equalsIgnoreCase("WEAKNESS"))
			return PotionEffectType.WEAKNESS;
		if(name.equalsIgnoreCase("WITHER"))
			return PotionEffectType.WITHER;
		return null;
	}
	
	private void runCheckFood(final List<World> worlds)
	{
		new BukkitRunnable()
		{
		    @Override
		    public void run()
		    {	
		    	checkFood(worlds);
		    }
		}.runTaskTimer(this,0L, 20L * 1);
	}
	
	private void checkFood(List<World> worlds)
	{
		for(World world : worlds)
		{
			for(Player player : world.getPlayers())
			{
				if(!player.hasPermission("foodbarchanger.change"))
					continue;
				if(player.getFoodLevel() <= FoodLevel)
				{
					addPotionEffects(player);
					players.put(player.getName(), true);
				}
				if(player.getFoodLevel() > FoodLevel && !players.isEmpty()  && players.get(player.getName()) != null)
				{
					removePotionEffects(player);
					players.put(player.getName(), false);
				}
			}
		}
	}
	
	private void addPotionEffects(Player player)
	{
		for(PotionEffect p : Potions)
		{
			player.addPotionEffect(p, true);
		}
	}
	
	private void removePotionEffects(Player player)
	{
		for(PotionEffect p : Potions)
		{
			player.removePotionEffect(p.getType());
		}
	}
}
