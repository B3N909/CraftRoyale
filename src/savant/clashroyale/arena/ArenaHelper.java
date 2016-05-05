package savant.clashroyale.arena;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import savant.clashroyale.mobs.Archer;
import savant.clashroyale.mobs.Barbarian;

public class ArenaHelper implements Listener
{
	NPCRegistry registry;
	
	public ArenaHelper()
	{
		registry = CitizensAPI.getNPCRegistry();
	}
	
	@EventHandler
	public void npcAttack(EntityDamageByEntityEvent e)
	{
		Entity attacker = e.getDamager();
		if(registry.isNPC(attacker))
		{
			String name = registry.getNPC(attacker).getFullName();
			int level = Integer.parseInt(name.split("LV")[1]);
			if(name.contains("Archer"))
			{
				e.setDamage(Archer.getDamage(level));
			}
			else if(name.contains("Barbarian"))
			{
				e.setDamage(Barbarian.getDamage(level));
			}
			else
			{
				System.out.println("[ClashRoyale] [ArenaHelper] Attempted to process an unknown Troop: " + name);
			}
		}
	}
}
