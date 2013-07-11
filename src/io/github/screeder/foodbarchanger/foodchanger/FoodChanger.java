package io.github.screeder.foodbarchanger.foodchanger;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.screeder.foodbarchanger.FoodBarChanger;
import io.github.screeder.foodbarchanger.foodchanger.listener.EatListener;

public class FoodChanger {
	
	public FoodBarChanger plugin;
	
	File foodFile;	
	FileConfiguration foodConf;
	
	public boolean modifyFood = true;
	
	public HashMap<Integer, Food> foodDetails = new HashMap<Integer, Food>();
	
	public FoodChanger(FoodBarChanger instance)
	{
		plugin = instance;
		modifyFood = plugin.getConfig().getBoolean("main.ModifyFood");
		foodFile = new File(plugin.getDataFolder(), "food.yml");
		checkFile("food.yml");
		foodConf = YamlConfiguration.loadConfiguration(foodFile);
		for(String key : foodConf.getKeys(false))
		{
			String name = key;
			int health = foodConf.getInt(key+".Health");
			int food = foodConf.getInt(key+".Food");
			Food f = new Food(name, health, food);
			Material m = Material.matchMaterial(name);
			if(m == null)
				continue;
			int id = m.getId();
			foodDetails.put(id, f);	
		}
		if(foodDetails.size() != foodConf.getKeys(false).size())
			plugin.getLogger().info("No/Not all items are modified");
		new EatListener(this);
	}
	
	public void checkFile(String file)
	{
		if (!foodFile.exists()) 
		{
		      foodFile.getParentFile().mkdirs();
		      plugin.saveResource(file, false);
		}
	}
}
