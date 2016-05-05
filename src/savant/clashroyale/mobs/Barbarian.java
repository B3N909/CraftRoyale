package savant.clashroyale.mobs;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import savant.clashroyale.util.ColorUtil;

public class Barbarian extends Mob
{
	static ItemStack[] contents = new ItemStack[] {new ItemStack(Material.LEATHER_CHESTPLATE, 1)};
	
	public Barbarian(Player owner, int level)
	{
		//TODO: Change to 4 * Level Summons Rate
		super(owner, "Barbarian LV" + level, (level * 30) + 270, 1.5, getDamage(level), Mob.getMeleeRange(), EnemyTarget.GROUND, 5, 4 * level, EntityType.ZOMBIE, getContents());
	}
	
	public static int getDamage(int level)
	{
		return (level * 10) + 65;
	}
	
	public static HashMap<EquipmentSlot, ItemStack> getContents()
	{
		HashMap<EquipmentSlot, ItemStack> list = new HashMap<EquipmentSlot, ItemStack>();
		
		ItemStack helmet = ColorUtil.setColor(new ItemStack(Material.LEATHER_HELMET, 1), Color.YELLOW); 
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		
		list.put(EquipmentSlot.HEAD, helmet);
		list.put(EquipmentSlot.LEGS, leggings);
		list.put(EquipmentSlot.BOOTS, boots);
		list.put(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD, 1));
		
		return list;
	}
}
