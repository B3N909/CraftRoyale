package savant.clashroyale.ai;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import savant.clashroyale.arena.ArenaGame;

public class LaneGoal implements Goal
{
	NPC npc;
	ArenaGame arena;
	Player p;
	
	public LaneGoal(NPC npc, ArenaGame arena, Player p)
	{
		this.npc = npc;
		this.arena = arena;
		this.p = p;
	}
	
	@Override
	public void reset()
	{
		
	}

	@Override
	public void run(GoalSelector goalSelector)
	{
		Location enemyLeftLane = arena.getEnemyLeftLane(p);
		Location enemyRightLane = arena.getEnemyRightLane(p);
		
		int leftDistance = (int) npc.getEntity().getLocation().distance(enemyLeftLane);
		int rightDistance = (int) npc.getEntity().getLocation().distance(enemyRightLane);
		
		npc.getNavigator().getLocalParameters().avoidWater(true);
		npc.getNavigator().getDefaultParameters().avoidWater(true);
		if(leftDistance > rightDistance)
		{
			npc.getNavigator().setTarget(enemyRightLane);
		}
		else if(rightDistance > leftDistance)
		{
			npc.getNavigator().setTarget(enemyLeftLane);
		}
		else
		{
			npc.getNavigator().setTarget(enemyRightLane);
		}
	}
	
	@Override
	public boolean shouldExecute(GoalSelector paramGoalSelector)
	{
		npc.getNavigator().setPaused(true);
		npc.getNavigator().cancelNavigation();
		npc.getNavigator().setTarget(arena.getEnemyLeftLane(p));
		npc.getNavigator().setPaused(false);
		return true;
	}

}
