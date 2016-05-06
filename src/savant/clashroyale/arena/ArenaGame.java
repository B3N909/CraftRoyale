package savant.clashroyale.arena;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaGame
{
	public static HashMap<Integer, ArenaGame> games = new HashMap<Integer, ArenaGame>();

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
	
	
	
	int id;
	boolean isActive = false;
	
	Player redPlayer;
	Player bluePlayer;
	
	Location redLeftTower;
	Location redRightTower;
	Location blueLeftTower;
	Location blueRightTower;
	
	public Location getRedLeftTower()
	{
		return redLeftTower;
	}
	public Location getRedRightTower()
	{
		return redRightTower;
	}
	public Location getBlueLeftTower()
	{
		return blueLeftTower;
	}
	public Location getBlueRightTower()
	{
		return blueRightTower;
	}
	
	ArenaNPCManager npcManager;
	ArenaDamageManager damageManager;
	ArenaLaneManager laneManager;
	
	public ArenaGame(int id)
	{
		this.id = id;
		this.npcManager = new ArenaNPCManager();
		this.laneManager = new ArenaLaneManager(this);
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
			
			laneManager.buildLanes();
			
			damageManager = new ArenaDamageManager(this);
			
			games.put(id, this);
		}
		else
		{
			redPlayer.sendMessage(ChatColor.RED + "Arena " + id + "ID has not been setup properly!");
			bluePlayer.sendMessage(ChatColor.RED + "Arena " + id + "ID has not been setup properly!");
		}
	}
	public void stop()
	{
		laneManager.destroyLanes();
		damageManager.destroy();
		games.remove(id, this);
		isActive = false;
	}
	public void forceStop()
	{
		redPlayer.sendMessage(ChatColor.DARK_PURPLE + "Game force stopped by Server");
		bluePlayer.sendMessage(ChatColor.DARK_PURPLE + "Game force stopped by Server");
		stop();
	}
	
	public ArenaNPCManager getNPCManager()
	{
		return npcManager;
	}
	public ArenaLaneManager getLaneManager()
	{
		return laneManager;
	}

	
	public boolean isActive()
	{
		return this.isActive;
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

}
