package com.craftmin.bukkit;

import org.bukkit.entity.Player;

public class Permissions {

	private LockChest plugin = null;
	
	public Permissions(LockChest Plugin) {
		plugin = Plugin;
	}
	
	public boolean canBreak(Player player) {
		if(player.isOp()) {
			if(plugin.mySettings.isAllowOps()) {
				return true;
			}
		}
		if(plugin.Manager != null && plugin.Manager.getHandler() != null) {
			if(plugin.Manager.getHandler().has(player, "lockchest.break")){
				return true;
			}
		}
		return false;
	}
	
}
