package com.craftmin.bukkit.PlayerLists;

import org.bukkit.Location;

public class lcPlayerListArgs {

	/*
	 * Arg 1:
	 * 		LockChest Enabled - true/false
	 * Arg 2:
	 * 		Block Location - Bukkit Location
	 */

	private boolean enabled = false;
	private Location blockLocation = null;
	
	public lcPlayerListArgs() {}
	public lcPlayerListArgs(boolean Enabled, Location bLocation) {
		setEnabled(Enabled);
		setBlockLocation(bLocation);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setBlockLocation(Location blockLocation) {
		this.blockLocation = blockLocation;
	}
	public Location getBlockLocation() {
		return blockLocation;
	}
	
}
