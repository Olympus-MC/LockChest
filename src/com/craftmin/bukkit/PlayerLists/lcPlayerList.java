package com.craftmin.bukkit.PlayerLists;

import java.util.HashMap;

import com.craftmin.bukkit.LockChest;

public class lcPlayerList extends HashMap<String, lcPlayerListArgs> {

	/**
	 * Idk wtf this is... *smiley face
	 */
	private static final long serialVersionUID = 1L;
	LockChest plugin = null;
	
	public void Initialize(LockChest Plugin) {
		plugin = Plugin;
	}
	
	public boolean containsPlayer(String playerName) {
		if(playerName == null) { return false; }
		for(String pName : this.keySet()) {
			if(pName.equalsIgnoreCase(playerName.trim())) {
				return true;
			}
		}
		return false;
	}
	
	public lcPlayerListArgs getPlayerArgs(String playerName) {
		if(containsPlayer(playerName)) {
			for(String pName : this.keySet()) {
				if(pName.equalsIgnoreCase(playerName.trim())) {
					return this.get(pName);
				}
			}
		}
		return null;
	}
	
	public String getPlayerNameCorrection(String playerName) {
		if(containsPlayer(playerName)) {
			for(String pName : this.keySet()) {
				if(pName.equalsIgnoreCase(playerName.trim())) {
					return pName;
				}
			}
		}
		return null;
	}
	
}
