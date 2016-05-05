package savant.clashroyale.util;

import java.util.Random;

import org.bukkit.Location;

public class Mathf
{
	public static int randInt(int min, int max)
	{
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}
	
	public static double randDouble(double min, double max)
	{
		Random random = new Random();
		return min + (max - min) * random.nextDouble();
	}
	
	public static double distance(Location one, Location two)
	{
		return Math.sqrt(Math.pow(one.getX()-two.getX(), 2) + Math.pow(one.getY()-two.getY(), 2) + Math.pow(one.getZ()-two.getZ(), 2));
	}
}
