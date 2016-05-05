package savant.clashroyale.ai;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.projectiles.ProjectileSource;

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
		if(npc.getEntity().getType() == EntityType.SKELETON)
		{
			if(npc.getEntity().getLocation().distance(enemy.getEntity().getLocation()) < 3)
			{
				npc.getNavigator().getLocalParameters().attackStrategy().handle((LivingEntity)npc.getEntity(), (LivingEntity)enemy.getEntity());
				npc.faceLocation(enemy.getEntity().getLocation());
				Location loc = npc.getEntity().getLocation();
//				Arrow arrow = loc.getWorld().spawn(loc.add(0, 2, 0), Arrow.class);
//				arrow.setShooter((ProjectileSource) npc.getEntity());
//				arrow.setVelocity(((LivingEntity)npc.getEntity()).getEyeLocation().getDirection().multiply(4));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean shouldExecute(GoalSelector paramGoalSelector)
	{
		npc.getNavigator().setPaused(true);
		npc.getNavigator().cancelNavigation();
		if(npc.getEntity().getType() == EntityType.SKELETON)
		{
			npc.getNavigator().getLocalParameters().attackStrategy().handle((LivingEntity)npc.getEntity(), (LivingEntity)enemy.getEntity());
		}
		else
		{
			npc.getNavigator().setTarget((LivingEntity) enemy.getEntity(), true);
		}
		npc.getNavigator().setPaused(false);
		return true;
	}

}
