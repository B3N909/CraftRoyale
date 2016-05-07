package savant.clashroyale.ai;

import net.citizensnpcs.api.ai.Navigator;
import net.citizensnpcs.api.ai.TargetType;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;

import org.bukkit.entity.Player;

import savant.clashroyale.arena.ArenaGame;
import savant.clashroyale.arena.ArenaNPCManager;

public class RoyaleAI extends Trait
{
	public RoyaleAI()
	{
		super("RoyaleAI");
	}
	
	ArenaGame arena;
	ArenaNPCManager npcManager;
	Player p;
	
	public void setup(ArenaGame arena, Player p)
	{
		this.arena = arena;
		this.npcManager = arena.getNPCManager();
		this.p = p;
	}

	@Persist
	private boolean data = false;
	
	@Override
	public void run()
	{
		if(npcManager == null)
		{
			return;
		}
		for(NPC blue : npcManager.getBlueNPCs())
		{
			for(NPC red : npcManager.getRedNPCs())
			{
				Navigator blueNav = blue.getNavigator();
				Navigator redNav = red.getNavigator();
				if(blueNav.getTargetType() != TargetType.ENTITY && redNav.getTargetType() != TargetType.ENTITY)
				{
					if(blue.getEntity().getLocation().distance(red.getEntity().getLocation()) < 8)
					{
						blue.getDefaultGoalController().addGoal(new EnemyAttackGoal(blue, red), 5);
						red.getDefaultGoalController().addGoal(new EnemyAttackGoal(red, blue), 5);
					}
				}
			}
		}
	}
	
	@Override
	public void onAttach()
	{
	}
	
	@Override
	public void onSpawn()
	{
		npc.getNavigator().getLocalParameters().useNewPathfinder(false);
		npc.getNavigator().getLocalParameters().avoidWater(true);
		npc.getNavigator().getLocalParameters().range(200);
		
		((TowerAttackAction)npc.getNavigator().getLocalParameters().stuckAction()).init(arena);
		
		npc.getDefaultGoalController().addGoal(new LaneGoal(npc, arena, p), 2);
	}
}
