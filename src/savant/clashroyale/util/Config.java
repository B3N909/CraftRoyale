package savant.clashroyale.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import savant.clashroyale.main.Main;

public class Config
{
	private FileConfiguration fc;
	private File file;
	
	Main plugin;
	
	public FileConfiguration getConfig()
	{
		if(plugin == null)
		{
			System.out.println("[ClashRoyale] Cannot find Main Class in Config#getConfig()");
			return fc;
		}
		return fc;
	}
	
	public Config(String n, Main plugin)
	{
		this.plugin = plugin;
		File f = new File(n + ".yml");
		fc = YamlConfiguration.loadConfiguration(f);
		this.file = f;
	}
	
	public void Save()
	{
		try
		{
			fc.save(file);
			plugin.saveConfig();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String toString(Location loc)
	{
		return loc.getWorld().getName() + "=" + fixInteger(loc.getBlockX()) + "=" + fixInteger(loc.getBlockY()) + "=" + fixInteger(loc.getBlockZ());
	}
	
	public static Location toLocation(String s)
	{
		int x = Integer.parseInt(s.split("=")[1]);
		int y = Integer.parseInt(s.split("=")[2]);
		int z = Integer.parseInt(s.split("=")[3]);
		return new Location(Bukkit.getWorld(s.split("=")[0]), x, y, z);
	}
	
	private static String fixInteger(int integer)
	{
		return integer + "".split(".")[0];
	}
}