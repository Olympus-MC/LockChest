package com.craftmin.bukkit.DataSource;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

import com.craftmin.bukkit.LockChest;
import com.craftmin.bukkit.chest.Chest;
import com.craftmin.bukkit.chest.lcChestArgs;

public class DataSource {

	//public boolean isFlatFile = true;
	
	LockChest plugin = null;
	
	//public Configuration configuration = null;
	private HashMap<String, Configuration> worldChests = null;
	
	public DataSource(boolean fFile, LockChest Plugin) {
		//isFlatFile = fFile;
		plugin = Plugin;
		if(fFile) {
			worldChests = new HashMap<String, Configuration>();
			File worldDir = new File("plugins/LockChest/Worlds");
			if(!worldDir.exists()) {
				if(!worldDir.mkdir()) {
					plugin.writeConsole("Issue Creating Worlds Folder (1)");
					if(!worldDir.mkdirs()) {
						plugin.writeConsole("Issue Creating Worlds Folder (2)");
					}
				}
			}
			Configuration configuration = null;
			for(World world : plugin.getServer().getWorlds()) {
				configuration = new Configuration(new File("plugins/LockChest/Worlds/" + world.getName() + ".txt"));
				configuration.load();
				configuration.save();
				worldChests.put(world.getName(), configuration);
			}
			//configuration = new Configuration(new File("plugins/LockChest/chests.txt"));
			//configuration.load();
		}
	}
	
	public boolean addChest(Chest chest, String worldName) {
		lcChestArgs ret = chest.tolcChestArgs();
		if(ret != null) {
			if(chest.locationToString() != null) {
				Configuration configuration = getWorldChestData(worldName);
				if(configuration != null) {
					configuration.setProperty(chest.locationToString(), ret.toString());
					configuration.save();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean unlockChest(Chest chest, String worldName) {
		lcChestArgs ret = chest.tolcChestArgs();
		if(ret != null) {
			if(chest.locationToString() != null) {
				Configuration configuration = getWorldChestData(worldName);
				if(configuration != null) {
					chest.UnLock();
					configuration.setProperty(chest.locationToString(), ret.toString());
					configuration.save();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean chestLocked(Location Axis, String playerName, String worldName) {
		Chest chest = new Chest();
		chest.setLocation(Axis);
		String location = chest.locationToString();
		if(location != "null") {
			Configuration configuration = getWorldChestData(worldName);
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
										boolean found = false;
										for(String ply : Args) {
											if(ply.equalsIgnoreCase(playerName.trim())) {
												found = true;
											}
										}
										if(found) {
											isLocked = false;
										}
									} else if(Users.length() > 0) {
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
	
	public Chest getChest(Location Axis, String worldName) {
		Chest chest = new Chest();
		chest.setLocation(Axis);
		String location = chest.locationToString();
		if(location != "null") {
			Configuration configuration = getWorldChestData(worldName);
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

	public void setWorldChests(HashMap<String, Configuration> worldChests) {
		this.worldChests = worldChests;
	}

	public HashMap<String, Configuration> getWorldChests() {
		return worldChests;
	}
	
	public Configuration getWorldChestData(String worldName) {
		Configuration configuration = null;
		for(String world : this.worldChests.keySet()) {
			if(world.trim().equalsIgnoreCase(worldName.trim())) {
				configuration = this.worldChests.get(world);
			}
		}
		return configuration;
	}
	
}
