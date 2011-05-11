package com.craftmin.bukkit.chest;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.craftmin.bukkit.LockChest;
import com.craftmin.bukkit.Listeners.Command;
import com.craftmin.bukkit.PlayerLists.lcPlayerList;
import com.craftmin.bukkit.PlayerLists.lcPlayerListArgs;

public class ChestDefinition {

	public static Chest prepareLockingChest(Chest chest, Player player, Location location, String[] allowedPlayers) {
		chest = new Chest();
		chest.setLocation(location);
		chest.Lock();
		if(allowedPlayers != null) {
			for(String ply : allowedPlayers) {
				chest.addUser(ply);
			}
		}
		return chest;
	}
	
	public static Chest prepareUnLockingChest(Chest chest, Location location) {
		chest = new Chest();
		chest.setLocation(location);
		chest.UnLock();
		if(chest.getUserList() != null) {
			chest.getUserList().clear();
		}
		return chest;
	}
	
	public static boolean Lock(Player player, String[] allowedPlayers, LockChest plugin) {
		String playerName = player.getName();
		if(plugin.playerList.containsPlayer(playerName)) {
			String realPlayer = plugin.playerList.getPlayerNameCorrection(playerName);
			lcPlayerListArgs ReT = plugin.playerList.getPlayerArgs(realPlayer);
			if(ReT != null) {
				//Oops :C
				Chest chest = plugin.dataSource.getChest(ReT.getBlockLocation(), player.getWorld().getName());
				if(chest != null) {
					if(chest.isLockedForPlayer(player.getName())) {
						player.sendMessage(ChatColor.DARK_RED + "You cannot access that chest!");
						return false;
					}
				}
				chest = prepareLockingChest(chest, player, ReT.getBlockLocation(), allowedPlayers);
				
				Location dChest = chest.isDoubleChest(player.getWorld());
				if(dChest != null) {
					//double
					int incre = 0;
					if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
						incre++;
					}
					chest = prepareLockingChest(chest, player, dChest, allowedPlayers);
					if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
						incre++;
					}
					if(incre == 2) {
						player.sendMessage(ChatColor.DARK_GREEN + "Double Chest Successfully Locked!");
					} else {
						player.sendMessage(ChatColor.DARK_RED + "Double Chest Failed To Lock!");
					}
				} else {
					if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
						player.sendMessage(ChatColor.DARK_GREEN + "Chest Successfully Locked!");
					} else {
						player.sendMessage(ChatColor.DARK_RED + "Chest Failed To Lock!");
					}
				}
			}
		}
		return false;
	}
	
	public static boolean unLock(Player player, LockChest plugin) {
		Location chestLoc = getSelectedChest(player.getName(), plugin.playerList);
		if(chestLoc != null) {

			Chest chest = plugin.dataSource.getChest(chestLoc, player.getWorld().getName());
			//if(!chest.isLocked()) {
			//	player.sendMessage(ChatColor.DARK_GREEN + "The selected Chest is already Unlocked!");
			///	return false;
			//}
			
			if(chest != null) {
				if(chest.isLockedForPlayer(player.getName())) {
					player.sendMessage(ChatColor.DARK_RED + "You cannot access that chest!");
					return false;
				}
				if(!chest.isLocked()) {
					player.sendMessage(ChatColor.DARK_GREEN + "The selected Chest is already Unlocked!");
					return false;
				}
				
				chest = prepareUnLockingChest(chest, chestLoc);
				
				Location dChest = chest.isDoubleChest(player.getWorld());
				if(dChest != null) {
					//double
					int incre = 0;
					if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
						incre++;
					}
					chest = prepareUnLockingChest(chest, dChest);
					if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
						incre++;
					}
					if(incre == 2) {
						player.sendMessage(ChatColor.DARK_GREEN + "Double Chest Successfully UnLocked!");
					} else {
						player.sendMessage(ChatColor.DARK_RED + "Double Chest Failed To UnLock!");
					}
				} else {
					if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
						player.sendMessage(ChatColor.DARK_GREEN + "Chest Successfully UnLocked!");
					} else {
						player.sendMessage(ChatColor.DARK_RED + "Chest Failed To UnLock!");
					}
				}
			}			
		}
		return false;
	}

	public static boolean addUsers(String[] Commands, String Message, Chest chest, Player player, LockChest plugin) {
		if(Commands.length >= 3) {
			String ply = Commands[2];
			if(ply != null) {
				if(ply.contains(",") && Message.contains(" lock ")) {
					String UsersTrimmed = Message.substring(
							Message.indexOf(" lock ") + 7, 
							Message.length()).trim();
					String[] split = Command.processInput(UsersTrimmed.split(","));
					for(String uName : split) {
						if(uName != null && uName.trim().length() > 0) {
							chest.addUser(uName);
						}
					}
				} else if(ply.trim().length() > 0) {
					chest.addUser(ply);
				}
			}
		}
		Location dChest = chest.isDoubleChest(player.getWorld());
		if(dChest != null) {
			//double
			int incre = 0;
			if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
				incre++;
			}
			Chest secChest = chest;
			secChest.setLocation(dChest);
			if(plugin.dataSource.addChest(secChest, player.getWorld().getName())) {
				incre++;
			}
			
			if(incre == 2) {
				player.sendMessage(ChatColor.DARK_GREEN + 
						"Saved Double Chest at: " + ChatColor.WHITE +
						chest.locationToString());
			} else {
				player.sendMessage(ChatColor.DARK_RED + 
						"Error Saving Double Chest at: " + ChatColor.WHITE +
						chest.locationToString());
			}
		} else {
			if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
				player.sendMessage(ChatColor.DARK_GREEN + 
						"Saved Chest at: " + ChatColor.WHITE +
						chest.locationToString());
			} else {
				player.sendMessage(ChatColor.DARK_RED + 
						"Error Saving Chest at: " + ChatColor.WHITE +
						chest.locationToString());
			}
		}
		
		return false;
	}
	
	public static boolean removeUsers(String[] Commands, String Message, Chest chest, Player player, LockChest plugin) {
		if(Commands.length >= 3) {
			String ply = Commands[2];
			if(ply != null) {
				if(ply.contains(",") && Message.contains(" lock ")) {
					String UsersTrimmed = Message.substring(
							Message.indexOf(" lock ") + 7, 
							Message.length()).trim();
					String[] split = Command.processInput(UsersTrimmed.split(","));
					for(String uName : split) {
						if(uName != null && uName.trim().length() > 0) {
							if(chest.containsPlayer(uName)) {
								chest.removeUserPlain(uName);
							}
						}
					}
				} else if(ply.trim().length() > 0) {
					if(chest.containsPlayer(ply)) {
						chest.removeUserPlain(ply);
					}
				}
			}
		}
		
		Location dChest = chest.isDoubleChest(player.getWorld());
		if(dChest != null) {
			//double
			int incre = 0;
			if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
				incre++;
			}
			Chest secChest = chest;
			secChest.setLocation(dChest);
			if(plugin.dataSource.addChest(secChest, player.getWorld().getName())) {
				incre++;
			}
			
			if(incre == 2) {
				player.sendMessage(ChatColor.DARK_GREEN + 
						"Saved Double Chest at: " + ChatColor.WHITE +
						chest.locationToString());
			} else {
				player.sendMessage(ChatColor.DARK_RED + 
						"Error Saving Double Chest at: " + ChatColor.WHITE +
						chest.locationToString());
			}
		} else {
			if(plugin.dataSource.addChest(chest, player.getWorld().getName())) {
				player.sendMessage(ChatColor.DARK_GREEN + 
						"Saved Chest at: " + ChatColor.WHITE +
						chest.locationToString());
			} else {
				player.sendMessage(ChatColor.DARK_RED + 
						"Error Saving Chest at: " + ChatColor.WHITE +
						chest.locationToString());
			}
		}
		return false;
	}
	
	public static boolean isLocked(Block Chest, Player player, LockChest plugin) {
		if(!plugin.permissions.isRestricted(player, "lockchest.break")) {
			return false;
		}
		//Search DataBase
		if(plugin.dataSource.chestLocked(Chest.getLocation(), player.getName(), player.getWorld().getName())) {
			return true;
		}
		return false; //Default, Allowed to open Chest or not (If above Failed)
	}
	
	public static boolean chestSelectionToggle(String playerName, lcPlayerList lcList) {
		if(lcList == null) { return false; }
		lcPlayerListArgs list = lcList.getPlayerArgs(playerName);
		if(list != null) {
			return list.isEnabled();
		}
		return false;
	}
	
	public static void selectChest(String playerName, lcPlayerList lcList, Location Chest) {
		lcPlayerListArgs list = lcList.getPlayerArgs(playerName);
		if(list != null) {
			list.setBlockLocation(Chest);
		}
	}
	
	public static Location getSelectedChest(String playerName, lcPlayerList lcList) {
		lcPlayerListArgs list = lcList.getPlayerArgs(lcList.getPlayerNameCorrection(playerName));
		if(list != null) {
			return list.getBlockLocation();
		}
		return null;
	}
	
}
