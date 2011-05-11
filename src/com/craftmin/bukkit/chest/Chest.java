package com.craftmin.bukkit.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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
	
	public boolean isLockedForPlayer(String plyName) {
		if(isLocked()) {
			if(!containsPlayer(plyName)) {
				return true;
			}
		}
		return false;
	}
	
	/*public boolean isDoubleChest(World world) {
		if(this.getLocation() != null) {
			if(world.getBlockTypeIdAt(getLocation()) == 54) {
				//Get 1 block infront
				Location bLocNorth = this.getLocation();
				bLocNorth.setX(bLocNorth.getX() - 1);
				
				//Get 1 Block behind
				Location bLocSouth = this.getLocation();
				bLocSouth.setX(bLocSouth.getX() + 1);
				
				//Get 1 block beside -> right
				Location bLocEast = this.getLocation();
				bLocEast.setZ(bLocEast.getZ() - 1);
				
				//Get 1 Block besidew <- left
				Location bLocWest = this.getLocation();
				bLocWest.setZ(bLocWest.getZ() + 1);
				
				boolean isDouble = false;
				if(world.getBlockTypeIdAt(bLocNorth) == 54) {
					isDouble = true;
				} else if(world.getBlockTypeIdAt(bLocSouth) == 54) {
					isDouble = true;
				} else if(world.getBlockTypeIdAt(bLocEast) == 54) {
					isDouble = true;
				} else if(world.getBlockTypeIdAt(bLocWest) == 54) {
					isDouble = true;
				}
				
				return isDouble;
			} //else wtf bro
		}		
		return false;
	}*/
	
	public Location isDoubleChest(World world) {
		if(this.getLocation() != null) {
			Block chest = world.getBlockAt(getLocation());
			if(chest.getTypeId() == 54) {
				if(chest.getRelative(BlockFace.NORTH).getTypeId() == 54) {
					return chest.getRelative(BlockFace.NORTH).getLocation();
				} else if(chest.getRelative(BlockFace.SOUTH).getTypeId() == 54) {
					return chest.getRelative(BlockFace.SOUTH).getLocation();
				} else if(chest.getRelative(BlockFace.EAST).getTypeId() == 54) {
					return chest.getRelative(BlockFace.EAST).getLocation();
				} else if(chest.getRelative(BlockFace.WEST).getTypeId() == 54) {
					return chest.getRelative(BlockFace.WEST).getLocation();
				}
			}
		}
		return null;
	}
	
	/*public Location isDoubleChest(World world) {
				//Get 1 block infront
				Location bLocNorth = this.getLocation();
				bLocNorth.setX(bLocNorth.getX() - 1);
				
				//Get 1 Block behind
				Location bLocSouth = this.getLocation();
				bLocSouth.setX(bLocSouth.getX() + 1);
				
				//Get 1 block beside -> right
				Location bLocEast = this.getLocation();
				bLocEast.setZ(bLocEast.getZ() - 1);
				
				//Get 1 Block besidew <- left
				Location bLocWest = this.getLocation();
				bLocWest.setZ(bLocWest.getZ() + 1);
				
				Location chest = null;
				if(world.getBlockTypeIdAt(bLocNorth) == 54) {
					chest = bLocNorth;
				} else if(world.getBlockTypeIdAt(bLocSouth) == 54) {
					chest = bLocSouth;
				} else if(world.getBlockTypeIdAt(bLocEast) == 54) {
					chest = bLocEast;
				} else if(world.getBlockTypeIdAt(bLocWest) == 54) {
					chest = bLocWest;
				}
				
				return chest;
			} //else wtf bro, Don't call me if I aint initialized!
		}		
		return null;
	}*/
	
	public boolean isDouble(World world) {
		if(isDoubleChest(world) != null) {
			return true;
		}
		return false;
	}
}
