package com.craftmin.bukkit.chest;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.craftmin.bukkit.LockChest;
import com.craftmin.bukkit.PlayerLists.lcPlayerList;
import com.craftmin.bukkit.PlayerLists.lcPlayerListArgs;

public class ChestDefinition {

	public static boolean Lock(Player player, String[] allowedPlayers, LockChest plugin) {
		String playerName = player.getName();
		if(plugin.playerList.containsPlayer(playerName)) {
			String realPlayer = plugin.playerList.getPlayerNameCorrection(playerName);
			lcPlayerListArgs ReT = plugin.playerList.getPlayerArgs(realPlayer);
			if(ReT != null) {
				//Oops :C
				Chest chest = plugin.dataSource.getChest(ReT.getBlockLocation());
				if(chest != null) {
					if(chest.isLockedForPlayer(player.getName())) {
						player.sendMessage(ChatColor.DARK_RED + "You cannot access that chest!");
						return false;
					}
				}
				chest = new Chest();
				chest.setLocation(ReT.getBlockLocation());
				chest.Lock();
				if(allowedPlayers != null) {
					for(String ply : allowedPlayers) {
						chest.addUser(ply);
					}
				}
				if(plugin.dataSource.addChest(chest)) {
					player.sendMessage(ChatColor.DARK_GREEN + "Chest Successfully Locked!");
				} else {
					player.sendMessage(ChatColor.DARK_RED + "Chest Failed To Lock!");
				}
			}
		}
		return false;
	}
	
	public static boolean unLock(Player player, LockChest plugin) {
		Location chestLoc = getSelectedChest(player.getName(), plugin.playerList);
		if(chestLoc != null) {

			Chest chest = plugin.dataSource.getChest(chestLoc);
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
				chest.UnLock();
				if(chest.getUserList() != null) {
					chest.getUserList().clear();
				}
				
				if(plugin.dataSource.addChest(chest)) {
					player.sendMessage(ChatColor.DARK_GREEN + "Chest Successfully UnLocked!");
				} else {
					player.sendMessage(ChatColor.DARK_RED + "Chest Failed To UnLock!");
				}
			}			
		}
		return false;
	}
	
	public static boolean isLocked(Block Chest, Player player, LockChest plugin) {
		if(!plugin.permissions.isRestricted(player, "lockchest.break")) {
			return false;
		}
		//Search DataBase
		if(plugin.dataSource.chestLocked(Chest.getLocation(), player.getName())) {
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
