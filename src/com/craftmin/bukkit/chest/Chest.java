package com.craftmin.bukkit.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class Chest {

	private List<String> userList = null;
	private Location location = null;
	private boolean lock = false;
	
	public Chest() {
		userList = new ArrayList<String>();
	}
	
	public void Lock() {
		lock = true;
	}
	public void UnLock() {
		lock = false;
	}
	
	public boolean isLocked() {
		return lock;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	public Location getLocation() {
		return location;
	}
	
	public void addUser(String playerName) {
		if(playerName == null) { return; }
		if(playerName.trim().length() < 1) { return; }
		userList.add(playerName);
	}
	
	@Deprecated
	public boolean removeUser(String playerName) {
		if(containsPlayer(playerName)) {
			return removeUserPlain(playerName);
		}
		return false;
	}
	
	/*public boolean containsPlayer(String playerName) {
		if(playerName.trim().length() >= 1) {
			for(String ply : userList) {
				if(ply.equalsIgnoreCase(playerName.trim())) {
					return true;
				}
			}
		}
		return false;
	}*/
	
	public boolean removeUserPlain(String playerName) {
		for(String player : userList) {
			if(player.equalsIgnoreCase(playerName.trim())) {
				userList.remove(player);
				return true;
			}
		}
		return false;
	}
	
	public boolean containsPlayer(String playerName) {
		for(String player : userList) {
			if(player.equalsIgnoreCase(playerName.trim())) {
				return true;
			}
		}
		return false;
	}
	
	public String locationToString() {
		if(getLocation() == null) {
			return "null";
		}
		double X = location.getX();
		double Y = location.getY();
		double Z = location.getZ();
		
		return String.valueOf(X) + "," + String.valueOf(Y) + "," + String.valueOf(Z);
	}
	
	public lcChestArgs tolcChestArgs() {
		lcChestArgs ReT = new lcChestArgs();
		ReT.setUserList(userList);
		ReT.setLocked(lock);
		return ReT;
	}
	
	public List<String> getUserList() {
		return userList;
	}
}
