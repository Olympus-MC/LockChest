package com.craftmin.bukkit;

import org.bukkit.inventory.ItemStack;

public class lcHoe {

	public static int getHoeChanceVal(ItemStack item) {
		if(item.getTypeId() == 290) { //Wooden Hoe
			return 1;
		} else if(item.getTypeId() == 291) { //Stone Hoe
			return 5;
		} else if(item.getTypeId() == 292) { //Iron Hoe
			return 8;
		} else if(item.getTypeId() == 294) { //Gold Hoe
			return 7;
		} else if(item.getTypeId() == 293) { //Diamond Hoe
			return 20;
		}	
		return 0;
	}
	
}
