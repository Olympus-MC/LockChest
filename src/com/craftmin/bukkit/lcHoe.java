package com.craftmin.bukkit;

import org.bukkit.inventory.ItemStack;

public class lcHoe {

	public static int getHoeChanceVal(ItemStack item, Settings settings) {
		if(item.getTypeId() == settings.getWoodenHoe()) { //Wooden Hoe
			return settings.getWoodenRate();
		} else if(item.getTypeId() == settings.getStoneHoe()) { //Stone Hoe
			return settings.getStoneRate();
		} else if(item.getTypeId() == settings.getIronHoe()) { //Iron Hoe
			return settings.getIronRate();
		} else if(item.getTypeId() == settings.getGoldHoe()) { //Gold Hoe
			return settings.getGoldRate();
		} else if(item.getTypeId() == settings.getDiamondHoe()) { //Diamond Hoe
			return settings.getDiamondRate();
		}	
		return 0;
	}
	
}
