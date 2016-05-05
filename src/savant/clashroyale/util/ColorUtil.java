package savant.clashroyale.util;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ColorUtil
{
	public static ItemStack setColor(ItemStack item, Color color)
	{
		LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
		lam.setColor(color);
		item.setItemMeta(lam);
		return item;
	}
}
