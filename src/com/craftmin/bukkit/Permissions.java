package com.craftmin.bukkit;

import org.bukkit.entity.Player;

public class Permissions {

	private LockChest plugin = null;
	
	public Permissions(LockChest Plugin) {
		plugin = Plugin;
	}
	
	/*@Deprecated
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
	}*/

	public boolean isRestricted(Player player, String node) {
		if(player.isOp()) {
			if(plugin.mySettings.isAllowOps()) {
				return false;
			}
		}
		if(plugin.Manager != null && plugin.Manager.getHandler() != null) {
			if(plugin.Manager.getHandler().has(player, node)){
				return false;
			}
		}
		return true;
	}
	
	public boolean isCommandRestricted(Player player, String node) {
		if(plugin.mySettings.isUsingPermissions()) {
			return isRestricted(player, node);
		} else {
			return false;
		}
	}
	
}
