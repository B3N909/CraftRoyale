package savant.clashroyale.arena;

import org.bukkit.Location;
import org.bukkit.Material;

import savant.clashroyale.util.ProgressBar;
import savant.clashroyale.util.ProgressBar.BlockColor;

public class ArenaDamageManager
{
	public int redLeftTower;
	public int redRightTower;
	public int blueLeftTower;
	public int blueRightTower;
	
	ProgressBar redLeft;
	ProgressBar redRight;
	ProgressBar blueLeft;
	ProgressBar blueRight;
	
	int barHeight = 10;
	
	int blueLeftHealth;
	int blueRightHealth;
	int redLeftHealth;
	int redRightHealth;
	
	int maxHealth = 2000;
	
	public ArenaDamageManager(ArenaGame arena)
	{
		redLeft = createBar("RedLeft", arena.getLeftLane(arena.getRedPlayer()));
		redRight = createBar("RedRight", arena.getRightLane(arena.getRedPlayer()));
		blueLeft = createBar("BlueLeft", arena.getLeftLane(arena.getBluePlayer()));
		blueRight = createBar("BlueRight", arena.getRightLane(arena.getBluePlayer()));
		
		blueLeftHealth = maxHealth;
		blueRightHealth = maxHealth;
		redLeftHealth = maxHealth;
		redRightHealth = maxHealth;
		
		update();
	}
	
	public void damage(ArenaTower tower, int damage)
	{
		if(tower == ArenaTower.BLUE_LEFT)
		{
			blueLeftHealth = blueLeftHealth - damage;
			
			if(blueLeftHealth <= 0)
			{
				blueLeftHealth = 1;
			}
		}
		else if(tower == ArenaTower.BLUE_RIGHT)
		{
			blueRightHealth = blueRightHealth - damage;
			
			if(blueRightHealth <= 0)
			{
				blueRightHealth = 1;
			}
		}
		else if(tower == ArenaTower.RED_LEFT)
		{
			redLeftHealth = redLeftHealth - damage;
			
			if(redLeftHealth <= 0)
			{
				redLeftHealth = 1;
			}
		}
		else if(tower == ArenaTower.RED_RIGHT)
		{
			redRightHealth = redRightHealth - damage;
			
			if(redRightHealth <= 0)
			{
				redRightHealth = 1;
			}
		}
		update();
	}
	
	public void update()
	{
		blueLeft.setDirect((float)(((float)blueLeftHealth) / ((float) maxHealth)));
		blueRight.setDirect((float)(((float)blueRightHealth) / ((float) maxHealth)));
		redLeft.setDirect((float)(((float)redLeftHealth) / ((float) maxHealth)));
		redRight.setDirect((float)(((float)redRightHealth) / ((float) maxHealth)));
	}
	
	public void destroy()
	{
		blueLeft.clear();
		blueRight.clear();
		redLeft.clear();
		redRight.clear();
	}
	
	public ProgressBar createBar(String name, Location center)
	{
		return new ProgressBar(name, center.clone().add(-4, barHeight, 0).getBlock(), center.clone().add(4, barHeight + 3, 0).getBlock(), center.clone().add(4, barHeight, 0).getBlock(), Material.WOOL, BlockColor.LIME, Material.WOOL, BlockColor.GRAY);
	}
}
