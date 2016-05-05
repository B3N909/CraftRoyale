package savant.clashroyale.mobs;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import savant.clashroyale.util.ColorUtil;

public class Archer extends Mob
{
	public Archer(Player owner, int level)
	{
		super(owner, "Archer LV" + level, (level * 10) + 115, 1.2, getDamage(level), 5, EnemyTarget.BOTH, 3, 2 * level, EntityType.SKELETON, getContents());
	}
	
	public static int getDamage(int level)
	{
		return (level * 4) + 38;
	}
	
	public static HashMap<EquipmentSlot, ItemStack> getContents()
	{
		HashMap<EquipmentSlot, ItemStack> list = new HashMap<EquipmentSlot, ItemStack>();
		
		ItemStack helmet = ColorUtil.setColor(new ItemStack(Material.LEATHER_HELMET, 1), Color.RED); 
		ItemStack chestplate = ColorUtil.setColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.BLUE);
		ItemStack leggings = ColorUtil.setColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.GREEN);
		
		list.put(EquipmentSlot.HEAD, helmet);
		list.put(EquipmentSlot.CHEST, chestplate);
		list.put(EquipmentSlot.LEGS, leggings);
		list.put(EquipmentSlot.HAND, new ItemStack(Material.BOW, 1));
		
		return list;
	}
}
