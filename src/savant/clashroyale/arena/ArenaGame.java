package savant.clashroyale.arena;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import savant.clashroyale.schematic.Schematic;

public class ArenaGame
{
	public static HashMap<Integer, ArenaGame> games = new HashMap<Integer, ArenaGame>();
	
	int id;
	boolean isActive = false;
	
	Player redPlayer;
	Player bluePlayer;
	
	Location redLeftTower;
	Location redRightTower;
	Location blueLeftTower;
	Location blueRightTower;
	
	ArenaNPCManager npcManager;
	
	public ArenaGame(int id)
	{
		this.id = id;
	}
	
	public void start(Player redPlayer, Player bluePlayer)
	{
		if(ArenaManager.isSetup(id))
		{
			isActive = true;
			this.redPlayer = redPlayer;
			this.bluePlayer = bluePlayer;
			redPlayer.teleport(ArenaManager.getRedSpawn(id));
			bluePlayer.teleport(ArenaManager.getBlueSpawn(id));
			
			redLeftTower = ArenaManager.getRedLeftTower(id);
			redRightTower = ArenaManager.getRedRightTower(id);
			blueLeftTower = ArenaManager.getBlueLeftTower(id);
			blueRightTower = ArenaManager.getBlueRightTower(id);
			
			buildLanes();
			
			games.put(id, this);
			
			npcManager = new ArenaNPCManager();
		}
		else
		{
			redPlayer.sendMessage(ChatColor.RED + "Arena " + id + "ID has not been setup properly!");
			bluePlayer.sendMessage(ChatColor.RED + "Arena " + id + "ID has not been setup properly!");
		}
	}
	
	public ArenaNPCManager getNPCManager()
	{
		return npcManager;
	}
	
	public void stop()
	{
		destroyLanes();
		games.remove(id, this);
		isActive = false;
	}
	
	public boolean isActive()
	{
		return this.isActive;
	}
	
	private void buildLanes()
	{
		//TODO: Add Tower Tiers
		try
		{
			Schematic tower = Schematic.parseSchematic("tower_t1");
			Schematic.pasteCenteredSchematic(redLeftTower, tower, redPlayer);
			Schematic.pasteCenteredSchematic(redRightTower, tower, redPlayer);
			Schematic.pasteCenteredSchematic(blueLeftTower, tower, bluePlayer);
			Schematic.pasteCenteredSchematic(blueRightTower, tower, bluePlayer);
		}
		catch (IOException e)
		{
			isActive = false;
			redPlayer.sendMessage(ChatColor.RED + "Arena " + id + "ID has not been setup properly!");
			bluePlayer.sendMessage(ChatColor.RED + "Arena " + id + "ID has not been setup properly!");
			e.printStackTrace();
		}
	}
	
	private void destroyLanes()
	{
		try
		{
			Schematic tower = Schematic.parseSchematic("tower_t1");
			Schematic.airCenteredSchematic(redLeftTower, tower, redPlayer);
			Schematic.airCenteredSchematic(redRightTower, tower, redPlayer);
			Schematic.airCenteredSchematic(blueLeftTower, tower, bluePlayer);
			Schematic.airCenteredSchematic(blueRightTower, tower, bluePlayer);
		}
		catch (IOException e)
		{
			isActive = false;
			redPlayer.sendMessage(ChatColor.RED + "Arena " + id + "ID has not been setup properly!");
			bluePlayer.sendMessage(ChatColor.RED + "Arena " + id + "ID has not been setup properly!");
			e.printStackTrace();
		}
	}

	public void forceStop()
	{
		redPlayer.sendMessage(ChatColor.DARK_PURPLE + "Game force stopped by Server");
		bluePlayer.sendMessage(ChatColor.DARK_PURPLE + "Game force stopped by Server");
		stop();
	}
	
	public Player getBluePlayer()
	{
		return bluePlayer;
	}

	public Player getRedPlayer()
	{
		return redPlayer;
	}
	
	public ArenaTeam getTeam(Player p)
	{
		if(redPlayer.getName().equalsIgnoreCase(p.getName()))
		{
			return ArenaTeam.RED;
		}
		else
		{
			return ArenaTeam.BLUE;
		}
	}
	
	public Location getLeftLane(Player p)
	{
		ArenaTeam team = getTeam(p);
		if(team == ArenaTeam.RED)
		{
			return redLeftTower;
		}
		if(team == ArenaTeam.BLUE)
		{
			return blueLeftTower;
		}
		return null;
	}
	
	public Location getRightLane(Player p)
	{
		ArenaTeam team = getTeam(p);
		if(team == ArenaTeam.RED)
		{
			return redRightTower;
		}
		if(team == ArenaTeam.BLUE)
		{
			return blueRightTower;
		}
		return null;
	}
	
	public Location getEnemyLeftLane(Player p)
	{
		ArenaTeam team = getTeam(p);
		if(team == ArenaTeam.RED)
		{
			return blueLeftTower;
		}
		if(team == ArenaTeam.BLUE)
		{
			return redLeftTower;
		}
		return null;
	}
	
	public Location getEnemyRightLane(Player p)
	{
		ArenaTeam team = getTeam(p);
		if(team == ArenaTeam.RED)
		{
			return blueRightTower;
		}
		if(team == ArenaTeam.BLUE)
		{
			return redRightTower;
		}
		return null;
	}
	
	public static ArenaGame getGame(Player p)
	{
		for(ArenaGame game : games.values())
		{
			if(game.getBluePlayer().getName().equalsIgnoreCase(p.getName()) || game.getRedPlayer().getName().equalsIgnoreCase(p.getName()))
			{
				return game;
			}
		}
		return null;
	}
}
