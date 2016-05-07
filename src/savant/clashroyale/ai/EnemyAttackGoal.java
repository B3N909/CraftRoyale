package savant.clashroyale.ai;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.ai.TargetType;
import net.citizensnpcs.api.npc.NPC;

public class EnemyAttackGoal implements Goal
{
	NPC npc;
	NPC enemy;
	
	public EnemyAttackGoal(NPC npc, NPC enemy)
	{
		this.npc = npc;
		this.enemy = enemy;
	}
	
	@Override
	public void reset()
	{
		
	}

	@Override
	public void run(GoalSelector goalSelector)
	{
		if(enemy == null || !enemy.isSpawned())
		{
			goalSelector.finishAndRemove();
		}
	}
	
	@Override
	public boolean shouldExecute(GoalSelector paramGoalSelector)
	{
		if(npc.isSpawned() && enemy.isSpawned() && npc.getNavigator().getTargetType() == TargetType.LOCATION && enemy.getNavigator().getTargetType() == TargetType.LOCATION)
		{
			npc.getNavigator().cancelNavigation();
			
			npc.getNavigator().setTarget(enemy.getEntity(), true);
			return true;
		}
		return false;
	}

}
