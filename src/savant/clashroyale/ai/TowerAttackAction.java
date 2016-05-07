package savant.clashroyale.ai;

import net.citizensnpcs.api.ai.Navigator;
import net.citizensnpcs.api.ai.StuckAction;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;

import savant.clashroyale.arena.ArenaGame;
import savant.clashroyale.arena.ArenaTower;
import savant.clashroyale.mobs.Archer;
import savant.clashroyale.mobs.Barbarian;

public class TowerAttackAction implements StuckAction
{
	ArenaGame arena;
	public void init(ArenaGame arena)
	{
		this.arena = arena;
	}
	
	public void firstRun(NPC npc)
	{
		Location redLeftTower = arena.getRedLeftTower();
		Location redRightTower = arena.getRedRightTower();
		Location blueLeftTower = arena.getBlueLeftTower();
		Location blueRightTower = arena.getBlueRightTower();
		
		Location loc = npc.getEntity().getLocation();
		
		int minDistance = 1000;
		if(redLeftTower.distance(loc) < minDistance)
		{
			minDistance = (int) redLeftTower.distance(loc);
			tower = ArenaTower.RED_LEFT;
		}
		if(redRightTower.distance(loc) < minDistance)
		{
			minDistance = (int) redRightTower.distance(loc);
			tower = ArenaTower.RED_RIGHT;
		}
		if(blueLeftTower.distance(loc) < minDistance)
		{
			minDistance = (int) blueLeftTower.distance(loc);
			tower = ArenaTower.BLUE_LEFT;
		}
		if(blueRightTower.distance(loc) < minDistance)
		{
			minDistance = (int) blueRightTower.distance(loc);
			tower = ArenaTower.BLUE_RIGHT;
		}
	}
	
	ArenaTower tower;
	
	boolean firstRun = true;
	
	@Override
	public boolean run(NPC npc, Navigator paramNavigator)
	{
		if(firstRun)
		{
			firstRun(npc);
			firstRun = false;
		}
		
		int damage = 0;
		if(npc.getFullName().contains("Archer"))
		{
			damage = Archer.getDamage(1);
		}
		else if(npc.getFullName().contains("Barbarian"))
		{
			damage = Barbarian.getDamage(1);
		}
		arena.getTowerManager().damage(tower, damage);
		return false;
	}
}
