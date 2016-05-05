package savant.clashroyale.arena;

import java.util.ArrayList;

import net.citizensnpcs.api.npc.NPC;

public class ArenaNPCManager
{
	ArrayList<NPC> blueNPCs;
	ArrayList<NPC> redNPCs;
	
	public ArenaNPCManager()
	{
		blueNPCs = new ArrayList<NPC>();
		redNPCs = new ArrayList<NPC>();
	}
	
	public ArrayList<NPC> getBlueNPCs()
	{
		return blueNPCs;
	}
	
	public ArrayList<NPC> getRedNPCs()
	{
		return redNPCs;
	}
	
	public void add(NPC npc, ArenaTeam team)
	{
		if(team == ArenaTeam.BLUE)
		{
			blueNPCs.add(npc);
		}
		else
		{
			redNPCs.add(npc);
		}
	}
	
	public void destroy()
	{
		for(NPC npc : blueNPCs)
		{
			npc.despawn();
			npc.destroy();
		}
		blueNPCs.clear();
		
		for(NPC npc : redNPCs)
		{
			npc.despawn();
			npc.destroy();
		}
		redNPCs.clear();
	}
}
