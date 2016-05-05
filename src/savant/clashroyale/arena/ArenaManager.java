package savant.clashroyale.arena;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

import savant.clashroyale.util.Config;

public class ArenaManager
{
	static Config arenaConfig;
	public static void init(Config config)
	{
		arenaConfig = config;
	}
	
	public static boolean exists(int value)
	{
		return arenaConfig.getConfig().contains("arena." + value);
	}
	
	public static int getArena(String nickName)
	{
		for(String id : getArenaIDs())
		{
			if(arenaConfig.getConfig().getString("arena." + id + ".nickName").equalsIgnoreCase(nickName))
			{
				return Integer.parseInt(id);
			}
		}
		return 0;
	}
	
	public static String getNickname(int id)
	{
		return arenaConfig.getConfig().getString("arena." + id + ".nickName");
	}
	
	public static void createArena(String nickName)
	{
		int newID = getLatestValue();
		arenaConfig.getConfig().set("arena." + newID + ".nickName", String.valueOf(nickName));
		arenaConfig.Save();
	}
	
	public static Set<String> getArenaIDs()
	{
		if(!arenaConfig.getConfig().contains("arena"))
		{
			return new HashSet<String>();
		}
		return arenaConfig.getConfig().getConfigurationSection("arena").getKeys(false);
	}
	
	public static void updateArena(int id, Location redSpawn, Location blueSpawn, Location leftTowerRed, Location rightTowerRed, Location leftTowerBlue, Location rightTowerBlue)
	{
		arenaConfig.getConfig().set("arena." + id + ".red.spawn", Config.toString(redSpawn));
		arenaConfig.getConfig().set("arena." + id + ".red.leftTower", Config.toString(leftTowerRed));
		arenaConfig.getConfig().set("arena." + id + ".red.rightTower", Config.toString(rightTowerRed));
		arenaConfig.getConfig().set("arena." + id + ".blue.spawn", Config.toString(blueSpawn));
		arenaConfig.getConfig().set("arena." + id + ".blue.leftTower", Config.toString(leftTowerBlue));
		arenaConfig.getConfig().set("arena." + id + ".blue.rightTower", Config.toString(rightTowerBlue));
		arenaConfig.Save();
	}
	
	public static Location getRedSpawn(int id)
	{
		return Config.toLocation(arenaConfig.getConfig().getString("arena." + id + ".red.spawn"));
	}
	
	public static Location getBlueSpawn(int id)
	{
		return Config.toLocation(arenaConfig.getConfig().getString("arena." + id + ".blue.spawn"));
	}
	
	public static Location getRedLeftTower(int id)
	{
		return Config.toLocation(arenaConfig.getConfig().getString("arena." + id + ".red.leftTower"));
	}
	
	public static Location getRedRightTower(int id)
	{
		return Config.toLocation(arenaConfig.getConfig().getString("arena." + id + ".red.rightTower"));
	}
	
	public static Location getBlueLeftTower(int id)
	{
		return Config.toLocation(arenaConfig.getConfig().getString("arena." + id + ".blue.leftTower"));
	}
	
	public static Location getBlueRightTower(int id)
	{
		return Config.toLocation(arenaConfig.getConfig().getString("arena." + id + ".blue.rightTower"));
	}
	
	public static boolean isSetup(int id)
	{
		return arenaConfig.getConfig().contains("arena." + id + ".red");
	}
	
	private static int getLatestValue()
	{
		if(arenaConfig.getConfig().contains("arena"))
		{
			return getArenaIDs().size();
		}
		return 1;
	}
}
