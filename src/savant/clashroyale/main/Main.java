package savant.clashroyale.main;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import savant.clashroyale.ai.RoyaleAI;
import savant.clashroyale.arena.ArenaEditMode;
import savant.clashroyale.arena.ArenaGame;
import savant.clashroyale.arena.ArenaHelper;
import savant.clashroyale.arena.ArenaManager;
import savant.clashroyale.util.Config;

public class Main extends JavaPlugin
{
	public void onEnable()
	{
		PluginManager pm = Bukkit.getPluginManager();
		
		Config arenaConfig = new Config("arenas", this);
		ArenaEditMode arenaEditMode = new ArenaEditMode();
		
		pm.registerEvents(arenaEditMode, this);
		pm.registerEvents(new ArenaHelper(), this);
		
		getCommand("cr").setExecutor(new Commands(arenaConfig, arenaEditMode));
		
		ArenaManager.init(arenaConfig);
		
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(RoyaleAI.class));
	}
	
	public void onDisable()
	{
		for(ArenaGame game : ArenaGame.games.values())
		{
			game.forceStop();
		}
	}
}
