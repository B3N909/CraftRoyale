package savant.clashroyale.mobs;

import java.util.ArrayList;
import java.util.HashMap;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import savant.clashroyale.ai.NoStuckAction;
import savant.clashroyale.ai.RoyaleAI;
import savant.clashroyale.arena.ArenaGame;

public abstract class Mob
{
	private static int meleeRange = 2;
	public static int getMeleeRange()
	{
		return meleeRange;
	}
	
	Player owner;
	String mobName;
	int health;
	double hitspeed;
	int damage;
	int range; //Range to attack from
	EnemyTarget target;
	int cost;
	int summons;
	EntityType mobType;
	HashMap<EquipmentSlot, ItemStack> equipment;
	
	ArenaGame arena;
	
	ArrayList<NPC> npcs;
	
	public Mob(Player owner, String mobName, int health, double hitspeed, int damage, int range, EnemyTarget target, int cost, int summons, EntityType mobType, HashMap<EquipmentSlot, ItemStack> equipment)
	{
		this.owner = owner;
		this.mobName = mobName;
		this.health = health;
		this.hitspeed = hitspeed;
		this.damage = damage;
		this.range = range;
		this.target = target;
		this.cost = cost;
		this.summons = summons;
		this.mobType = mobType;
		this.equipment = equipment;
	}
	
	public void spawn(Location location, ArenaGame arena)
	{
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		
		for(int x = 1; x < summons; x++)
		{
			NPC npc = registry.createNPC(mobType, mobName);
			
			npc.addTrait(RoyaleAI.class);
			npc.getTrait(RoyaleAI.class).setup(arena, owner);
			
			npc.setProtected(false);
			npc.getNavigator().getLocalParameters().distanceMargin(range);
			
			npc.getNavigator().getLocalParameters().stuckAction(new NoStuckAction());
			
			npc.spawn(location);
			
			LivingEntity entity = (LivingEntity) npc.getEntity();
			entity.setMaxHealth(health);
			entity.setHealth(health);
			
			Equipment equipTrait = npc.getTrait(Equipment.class);
			for(EquipmentSlot slot : equipment.keySet())
			{
				ItemStack item = equipment.get(slot);
				if(slot == EquipmentSlot.HEAD)
					equipTrait.set(1, item);
				if(slot == EquipmentSlot.CHEST)
					equipTrait.set(2, item);
				if(slot == EquipmentSlot.LEGS)
					equipTrait.set(3, item);
				if(slot == EquipmentSlot.BOOTS)
					equipTrait.set(4, item);
				if(slot == EquipmentSlot.HAND)
					equipTrait.set(0, item);
			}
			
			arena.getNPCManager().add(npc, arena.getTeam(owner));
		}
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public double getHitSpeed()
	{
		return hitspeed;
	}
	
	public int getDamage()
	{
		return damage;
	}

	public double getRange() 
	{
		return range;
	}

	public EnemyTarget getTarget()
	{
		return target;
	}

	public int getCost()
	{
		return cost;
	}

	public int getSummons()
	{
		return summons;
	}
	
	public ArrayList<NPC> getInstances()
	{
		return npcs;
	}
}
