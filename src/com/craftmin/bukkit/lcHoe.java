package com.craftmin.bukkit;

import org.bukkit.inventory.ItemStack;

public class lcHoe {

	public static int getHoeChanceVal(ItemStack item, Settings settings) {
		if(item.getTypeId() == settings.getWoodenHoe()) { //Wooden Hoe
			return 1;
		} else if(item.getTypeId() == settings.getStoneHoe()) { //Stone Hoe
			return 5;
		} else if(item.getTypeId() == settings.getIronHoe()) { //Iron Hoe
			return 8;
		} else if(item.getTypeId() == settings.getGoldHoe()) { //Gold Hoe
			return 7;
		} else if(item.getTypeId() == settings.getDiamondHoe()) { //Diamond Hoe
			return 20;
		}	
		return 0;
	}
	
}
