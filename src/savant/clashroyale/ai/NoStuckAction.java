package savant.clashroyale.ai;

import net.citizensnpcs.api.ai.Navigator;
import net.citizensnpcs.api.ai.StuckAction;
import net.citizensnpcs.api.npc.NPC;

public class NoStuckAction implements StuckAction
{
	@Override
	public boolean run(NPC paramNPC, Navigator paramNavigator)
	{
		//TODO: Tower Damage
		return false;
	}
}
