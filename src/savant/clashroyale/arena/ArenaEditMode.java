package savant.clashroyale.arena;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ArenaEditMode implements Listener
{
	private boolean isEnabled = false;
	
	private int ID;
	private Player p;
	
	public void turnOn(Player p, int ID)
	{
		isEnabled = true;
		this.p = p;
		this.ID = ID;
		type = ArenaEditorType.RED_SPAWN;
	}
	
	private ArenaEditorType type;
	
	private Location RED_SPAWN;
	private Location RED_TOWER_LEFT;
	private Location RED_TOWER_RIGHT;
	private Location BLUE_SPAWN;
	private Location BLUE_TOWER_LEFT;
	private Location BLUE_TOWER_RIGHT;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(p != null && isEnabled && e.getPlayer().getName().equalsIgnoreCase(p.getName()))
		{
			if(e.getClickedBlock() == null)
			{
				p.sendMessage(ChatColor.RED + "You are in Edit Mode!");
				e.setCancelled(true);
				return;
			}
			if(type == ArenaEditorType.RED_SPAWN)
			{
				RED_SPAWN = e.getClickedBlock().getLocation();
				type = ArenaEditorType.RED_TOWER_LEFT;
				p.sendMessage(ChatColor.GREEN + "  - Click Red Left Tower Block");
				e.setCancelled(true);
				return;
			}
			if(type == ArenaEditorType.RED_TOWER_LEFT)
			{
				RED_TOWER_LEFT = e.getClickedBlock().getLocation();
				type = ArenaEditorType.RED_TOWER_RIGHT;
				p.sendMessage(ChatColor.GREEN + "  - Click Red Right Tower Block");
				e.setCancelled(true);
				return;
			}
			if(type == ArenaEditorType.RED_TOWER_RIGHT)
			{
				RED_TOWER_RIGHT = e.getClickedBlock().getLocation();
				type = ArenaEditorType.BLUE_SPAWN;
				p.sendMessage(ChatColor.GREEN + "  - Click Blue Spawn Block");
				e.setCancelled(true);
				return;
			}
			if(type == ArenaEditorType.BLUE_SPAWN)
			{
				BLUE_SPAWN = e.getClickedBlock().getLocation();
				type = ArenaEditorType.BLUE_TOWER_LEFT;
				p.sendMessage(ChatColor.GREEN + "  - Click Blue Left Tower Block");
				e.setCancelled(true);
				return;
			}
			if(type == ArenaEditorType.BLUE_TOWER_LEFT)
			{
				BLUE_TOWER_LEFT = e.getClickedBlock().getLocation();
				type = ArenaEditorType.BLUE_TOWER_RIGHT;
				p.sendMessage(ChatColor.GREEN + "  - Click Blue Right Tower Block");
				e.setCancelled(true);
				return;
			}
			if(type == ArenaEditorType.BLUE_TOWER_RIGHT)
			{
				BLUE_TOWER_RIGHT = e.getClickedBlock().getLocation();
				type = null;
				isEnabled = false;
				p.sendMessage(ChatColor.GREEN + "Updated " + ID + " (" + ArenaManager.getNickname(ID) + ") with Block Locations!");
				ArenaManager.updateArena(ID, RED_SPAWN, BLUE_SPAWN, RED_TOWER_LEFT, RED_TOWER_RIGHT, BLUE_TOWER_LEFT, BLUE_TOWER_RIGHT);
				p = null;
				e.setCancelled(true);
				return;
			}
		}
	}
}
