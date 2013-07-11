package io.github.screeder.foodbarchanger.foodchanger;

public class Food {
	public String name = "";
	public int health = 0;
	public int food = 0;
	public double saturation = 0;
	
	Food(String name, int health, int food)
	{
		this.name = name;
		this.health = health;
		this.food = food;
	}
}
