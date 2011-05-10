package com.craftmin.bukkit;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class lockDecoder {

	static Random rand = new Random();
	static int dFail = 0;
	
	public static boolean processChest(Player player, Block block, Settings settings) {
		if(!settings.isAllowPicking()) {
			return false;
		}
		int Chance = lcHoe.getHoeChanceVal(player.getItemInHand(), settings);
		
		if(Chance > 0) {
			int cal = 40 - (Chance);
			int Success = rand.nextInt(cal);
			
			if(Success < Chance) {
				Success += Chance;
			}
			
			if(dFail >= 5 && Chance == 20) {
				int rate = rand.nextInt(2);
				if(rate == 1) {
					Success = Chance;
					dFail = 0;
				}
			}
			
			int Amount = player.getItemInHand().getAmount() - 1;
			if(Amount <= 0) {
				player.getInventory().setItemInHand(null);
			} else {
				player.getInventory().setItemInHand(new ItemStack(player.getItemInHand().getTypeId(), Amount));
			}

			if(Success == Chance) {
				player.sendMessage(ChatColor.DARK_GREEN + "You have successfully unlocked the Chest!");
				return false;
			}
			if(Chance == 20) { dFail += 1; }
			player.sendMessage(ChatColor.DARK_RED + "You have failed to unlock the Chest!");
			
			return true;
		}		
		return true;
	}
	
	public static boolean isHoldingHoe(Player player, Settings settings) {
		int Chance = lcHoe.getHoeChanceVal(player.getItemInHand(), settings);
		if(Chance > 0) {
			return true;
		}
		return false;
	}
	
}
