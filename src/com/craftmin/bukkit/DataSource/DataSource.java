package com.craftmin.bukkit.DataSource;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.util.config.Configuration;

import com.craftmin.bukkit.LockChest;
import com.craftmin.bukkit.chest.Chest;
import com.craftmin.bukkit.chest.lcChestArgs;

public class DataSource {

	//public boolean isFlatFile = true;
	
	LockChest plugin = null;
	
	public Configuration configuration = null;
	
	public DataSource(boolean fFile, LockChest Plugin) {
		//isFlatFile = fFile;
		plugin = Plugin;
		if(fFile) {
			configuration = new Configuration(new File("plugins/LockChest/chests.txt"));
			configuration.load();
		}
	}
	
	public boolean addChest(Chest chest) {
		lcChestArgs ret = chest.tolcChestArgs();
		if(ret != null) {
			if(chest.locationToString() != null) {
				configuration.setProperty(chest.locationToString(), ret.toString());
				configuration.save();
				return true;
			}
		}
		return false;
	}
	
	public boolean unlockChest(Chest chest) {
		lcChestArgs ret = chest.tolcChestArgs();
		if(ret != null) {
			if(chest.locationToString() != null) {
				chest.UnLock();
				configuration.setProperty(chest.locationToString(), ret.toString());
				configuration.save();
				return true;
			}
		}
		return false;
	}
	
	public boolean chestLocked(Location Axis, String playerName) {
		Chest chest = new Chest();
		chest.setLocation(Axis);
		String location = chest.locationToString();
		if(location != "null") {
			if(configuration != null) {
				String ret = configuration.getString(location);
				if(ret != null) {
					boolean isLocked = false;
					String Enabled = ret;
					if(Enabled.contains(",")) {
						String Args[] = Enabled.split(",");
						isLocked = Boolean.valueOf(Args[0]);
					}
					String UsersArray = configuration.getString(location);
					if(UsersArray.contains("{")) {
						//plugin.writeConsole("2");
								String Users = UsersArray.substring(UsersArray.indexOf("{"), UsersArray.length());
								if(Users.endsWith("}")) {
									if(Users.startsWith("{")) {
										Users = Users.substring(1, Users.length());
									}
									Users = Users.substring(0, Users.length() - 1);
									if(Users.contains(",")) {
										String Args[] = Users.split(",");
										boolean found = false;
										for(String ply : Args) {
											if(ply.equalsIgnoreCase(playerName.trim())) {
												//plugin.writeConsole("Found");
												found = true;
											}
										}
										if(found) {
											isLocked = false;
										}
									} else if(Users.length() > 0) {

										//plugin.writeConsole("3");
										if(Users.trim().equalsIgnoreCase(playerName.trim())) {
											isLocked = false;
										}
									}
						}
					}
					return isLocked;
				}
			}
		}
		return false;
	}
	
	public Chest getChest(Location Axis) {
		Chest chest = new Chest();
		chest.setLocation(Axis);
		String location = chest.locationToString();
		if(location != "null") {
			if(configuration != null) {
				String ret = configuration.getString(location);
				if(ret != null) {
					boolean isLocked = false;
					String Enabled = ret;
					if(Enabled.contains(",")) {
						String Args[] = Enabled.split(",");
						isLocked = Boolean.valueOf(Args[0]);
					}
					String UsersArray = configuration.getString(location);
					if(UsersArray.contains("{")) {
								String Users = UsersArray.substring(UsersArray.indexOf("{"), UsersArray.length());
								if(Users.endsWith("}")) {
									if(Users.startsWith("{")) {
										Users = Users.substring(1, Users.length());
									}
									Users = Users.substring(0, Users.length() - 1);
									if(Users.contains(",")) {
										String Args[] = Users.split(",");
										for(String ply : Args) {
											chest.addUser(ply);
										}
									} else if(Users.length() > 0) {
										chest.addUser(Users.trim());
									}
						}
					}
					if(isLocked) {
						chest.Lock();
					} else {
						chest.UnLock();
					}
					return chest;
				}
			}
		}
		return null;
	}
	
}
