package com.craftmin.bukkit.chest;

import java.util.ArrayList;
import java.util.List;

public class lcChestArgs {

	private List<String> userList = null;
	private boolean locked = false;
	
	public lcChestArgs() {
		userList = new ArrayList<String>();
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	public List<String> getUserList() {
		return userList;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}
	
	public String toString() {
		String users = "";
		for(String user : userList) {
			users += "," + user;
		}
		if(users.startsWith(",")) {
			users = users.substring(1, users.length());
		}
		users = "{" + users + "}";
		
		return String.valueOf(isLocked()) + "," + users;
	}
	
}
