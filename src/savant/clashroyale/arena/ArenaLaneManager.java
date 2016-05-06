package savant.clashroyale.arena;

import java.io.IOException;

import org.bukkit.ChatColor;

import savant.clashroyale.schematic.Schematic;

public class ArenaLaneManager
{
	ArenaGame arena;
	
	public ArenaLaneManager(ArenaGame arena)
	{
		this.arena = arena;
	}
	
	public void buildLanes()
	{
		//TODO: Add Tower Tiers
		try
		{
			Schematic tower = Schematic.parseSchematic("tower_t1");
			Schematic.pasteCenteredSchematic(arena.getRedLeftTower(), tower, arena.getRedPlayer());
			Schematic.pasteCenteredSchematic(arena.getRedRightTower(), tower, arena.getRedPlayer());
			Schematic.pasteCenteredSchematic(arena.getBlueLeftTower(), tower, arena.getBluePlayer());
			Schematic.pasteCenteredSchematic(arena.getBlueRightTower(), tower, arena.getBluePlayer());
		}
		catch (IOException e)
		{
			System.out.println(ChatColor.RED + "Arena " + arena.id + "ID has not been setup properly!");
			e.printStackTrace();
		}
	}
	public void destroyLanes()
	{
		try
		{
			Schematic tower = Schematic.parseSchematic("tower_t1");
			Schematic.airCenteredSchematic(arena.getRedLeftTower(), tower, arena.getRedPlayer());
			Schematic.airCenteredSchematic(arena.getRedRightTower(), tower, arena.getRedPlayer());
			Schematic.airCenteredSchematic(arena.getBlueLeftTower(), tower, arena.getBluePlayer());
			Schematic.airCenteredSchematic(arena.getBlueRightTower(), tower, arena.getBluePlayer());
		}
		catch (IOException e)
		{
			System.out.println(ChatColor.RED + "Arena " + arena.id + "ID has not been setup properly!");
			e.printStackTrace();
		}
	}
}
