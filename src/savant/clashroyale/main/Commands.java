package savant.clashroyale.main;

import java.util.HashSet;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import savant.clashroyale.arena.ArenaEditMode;
import savant.clashroyale.arena.ArenaGame;
import savant.clashroyale.arena.ArenaManager;
import savant.clashroyale.mobs.Archer;
import savant.clashroyale.mobs.Barbarian;
import savant.clashroyale.util.Config;

public class Commands implements CommandExecutor, Listener
{
	
	Config arenaConfig;
	ArenaEditMode arenaEditMode;
	
	public Commands(Config arenaConfig, ArenaEditMode arenaEditMode)
	{
		this.arenaConfig = arenaConfig;
		this.arenaEditMode = arenaEditMode;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("cr"))
		{
			Player p = (Player) sender;
			if(args.length == 0)
			{
				p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "> Avaliable Commands <");
				p.sendMessage(ChatColor.GREEN + "  - spawn");
				p.sendMessage(ChatColor.GREEN + "  - killall");
				p.sendMessage(ChatColor.GREEN + "  - fakestart");
				p.sendMessage(ChatColor.GREEN + "  - fakestop");
				p.sendMessage(ChatColor.GREEN + "  - arena");
				p.sendMessage(ChatColor.GREEN + "  - forcestart");
				return true;
			}
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("forcestart"))
				{
					p.sendMessage(ChatColor.RED + "/cr forcestart ID OpponentPlayer");
				}
				if(args[0].equalsIgnoreCase("fakestart"))
				{
					p.sendMessage(ChatColor.RED + "/cr fakestart ID");
					return true;
				}
				if(args[0].equalsIgnoreCase("fakestop"))
				{
					p.sendMessage(ChatColor.RED + "/cr fakestop ID");
					return true;
				}
				if(args[0].equalsIgnoreCase("arena"))
				{
					p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "> Avaliable Arena Modifications <");
					p.sendMessage(ChatColor.GREEN + "  - create");
					p.sendMessage(ChatColor.GREEN + "  - update");
					p.sendMessage(ChatColor.GREEN + "  - list");
					return true;
				}
				if(args[0].equalsIgnoreCase("spawn"))
				{
					p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "> Avaliable Spawnables <");
					p.sendMessage(ChatColor.GREEN + "  - Troop");
					return true;
				}
				if(args[0].equalsIgnoreCase("killall"))
				{
					NPCRegistry registry = CitizensAPI.getNPCRegistry();
					int count = 0;
					for(NPC npc : registry.sorted())
					{
						if(npc.isSpawned())
							npc.despawn();
						npc.destroy();
						count++;
					}
					p.sendMessage(ChatColor.GREEN + "Killed " + count + " NPCs");
					return true;
				}
			}
			if(args.length == 2)
			{
				if(args[0].equalsIgnoreCase("arena"))
				{
					if(args[1].equalsIgnoreCase("create"))
					{
						p.sendMessage(ChatColor.RED + "/cr arena create NICKNAME");
						return true;
					}
					if(args[1].equalsIgnoreCase("update"))
					{
						p.sendMessage(ChatColor.RED + "/cr arena update ID (/cr arena list)");
						return true;
					}
					if(args[1].equalsIgnoreCase("list"))
					{
						p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "> Avaliable Arenas <");
						int index = 0;
						for(String id : ArenaManager.getArenaIDs())
						{
							p.sendMessage(ChatColor.GREEN + "  - " + id + " (" + ArenaManager.getNickname(Integer.parseInt(id)) + ")");
							index++;
						}
						if(index == 0)
						{
							p.sendMessage(ChatColor.GREEN + "  - No Arenas Created (/cr arena create");
						}
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("fakestart"))
				{
					int arena;
					try
					{
						arena = Integer.parseInt(args[1]);
					}
					catch (NumberFormatException e)
					{
						p.sendMessage(ChatColor.RED + "Invalid Arena Number");
						return true;
					}
					ArenaGame game = new ArenaGame(arena);
					game.start(p, p);
					p.sendMessage(ChatColor.GREEN + "Started " + arena + "ID Game");
					return true;
				}
				if(args[0].equalsIgnoreCase("fakestop"))
				{
					int arena;
					try
					{
						arena = Integer.parseInt(args[1]);
					}
					catch (NumberFormatException e)
					{
						p.sendMessage(ChatColor.RED + "Invalid Arena Number");
						return true;
					}
					ArenaGame game = ArenaGame.games.get(arena);
					if(game == null || !game.isActive())
					{
						p.sendMessage(ChatColor.RED + "That Arena is not currently active.");
					}
					else
					{
						game.stop();
						p.sendMessage(ChatColor.GREEN + "Stopped " + arena + "ID Game");
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("spawn"))
				{
					if(args[1].equalsIgnoreCase("troop"))
					{
						p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "> Avaliable Mobs <");
						p.sendMessage(ChatColor.GREEN + "  - Archer");
						p.sendMessage(ChatColor.GREEN + "  - Barbarian");
						return true;
					}
				}
			}
			if(args.length == 3)
			{
				if(args[0].equalsIgnoreCase("forcestart"))
				{
					try
					{
						int id = Integer.parseInt(args[1]);
						if(ArenaManager.exists(id))
						{
							String opponent = args[2];
							if(Bukkit.getPlayer(opponent) != null)
							{
								Player opponentPlayer = Bukkit.getPlayer(opponent);
								ArenaGame game = new ArenaGame(id);
								game.start(p, opponentPlayer);
								p.sendMessage(ChatColor.GREEN + "Forced " + id + "ID to Start with " + opponentPlayer.getName() + "!");
							}
							else
							{
								p.sendMessage(ChatColor.RED + "Cannot find Player");
							}
						}
						else
						{
							p.sendMessage(ChatColor.RED + "Arena does not exist");
						}
					}
					catch (NumberFormatException e)
					{
						p.sendMessage(ChatColor.RED + "/cr forcestart ID OpponentPlayer");
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("arena"))
				{
					if(args[1].equalsIgnoreCase("create"))
					{
						String name = args[2];
						ArenaManager.createArena(name);
						p.sendMessage(ChatColor.GREEN + "Created Arena " + ArenaManager.getArena(name) + "ID (" + name + ")");
						p.sendMessage(ChatColor.GREEN + "Setup the Arena with /cr arena update");
						return true;
					}
					if(args[1].equalsIgnoreCase("update"))
					{
						String name = args[2];
						try
						{
							int id = Integer.parseInt(name);
							if(ArenaManager.exists(id))
							{
								arenaEditMode.turnOn(p, id);
								p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "> Arena Edit Mode <");
								p.sendMessage(ChatColor.GREEN + "  - Click Red Spawn Block");
							}
							else
							{
								p.sendMessage(ChatColor.RED + "Arena does not exist");
							}
						}
						catch (NumberFormatException e)
						{
							p.sendMessage(ChatColor.RED + "/cr arena update ID");
						}
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("spawn"))
				{
					if(args[1].equalsIgnoreCase("troop"))
					{
						String mob = args[2];
						if(mob.contains(":"))
						{
							int mobLevel = Integer.parseInt(mob.split(":")[1]);
							String mobName = mob.split(":")[0];
							if(ArenaGame.getGame(p) == null)
							{
								//TODO: Add Troop Demo Showcase
								p.sendMessage(ChatColor.RED + "You are not in a game");
								return true;
							}
							if(mobName.equalsIgnoreCase("archer"))
							{
								Archer archer = new Archer(p, mobLevel);
								archer.spawn(p.getTargetBlock((HashSet<Byte>)null, 25).getLocation().add(0, 2, 0), ArenaGame.getGame(p));
								p.sendMessage(ChatColor.GREEN + "Summoned 1 Archer Unit");
							}
							else if(mobName.equalsIgnoreCase("barbarian"))
							{
								Barbarian barbarian = new Barbarian(p, mobLevel);
								barbarian.spawn(p.getTargetBlock((HashSet<Byte>)null, 25).getLocation().add(0, 2, 0), ArenaGame.getGame(p));
								p.sendMessage(ChatColor.GREEN + "Summoned 1 Barbarian Unit");
							}
							else
							{
								p.sendMessage(ChatColor.RED + "Mob does not exist!");
							}
							return true;
						}
						else
						{
							p.sendMessage(ChatColor.RED + "Specify troop by TROOPNAME:TROOPLEVEL");
							return true;
						}
					}
				}
			}
			error(p);
			return false;
		}
		return false;
	}
	
	private void error(Player p)
	{
		p.sendMessage(ChatColor.RED + "You have made a error, try again!");
	}
}
