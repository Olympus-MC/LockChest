package com.craftmin.bukkit.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import com.craftmin.bukkit.LockChest;
import com.craftmin.bukkit.lockDecoder;
import com.craftmin.bukkit.chest.ChestDefinition;

public class lcPlayerListener extends PlayerListener {

	LockChest plugin = null;
	
	public lcPlayerListener(LockChest Plugin) {
		plugin = Plugin;
	}
	
	//public void onPlayerChat(PlayerChatEvent event) {
	//	Command.processCommand(event);
	//}///
	
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if(!plugin.isEnabled) { return; }
		Command.processCommand(event, plugin);
	}
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!plugin.isEnabled) { return; }
		if(!event.hasBlock()) { return; }
		if(plugin.mySettings.isUsingPermissions()) {
			String world = event.getPlayer().getWorld().getName();
			String grp = plugin.Manager.getHandler().getGroup(world, event.getPlayer().getName());
			if(!plugin.Manager.getHandler().canGroupBuild(world, grp)) {
				event.setCancelled(true);
				//event.getPlayer().sendMessage(ChatColor.DARK_RED + "You have insufficient permissions to perform that action.");
				//event.getPlayer().sendMessage(ChatColor.DARK_RED + "Please contact a Staff Member.");
				return;
			}			
		}
		if(event.getClickedBlock().getTypeId() == 54) {
			//if(event.getPlayer()() instanceof Player) {
				Player ply = event.getPlayer();

				
				boolean locked = ChestDefinition.isLocked(event.getClickedBlock(), ply, plugin);
				
				if(!lockDecoder.isHoldingHoe(ply)) {
					if(ply.getItemInHand().getTypeId() == 0) {
						if(ChestDefinition.chestSelectionToggle(ply.getName(), plugin.playerList)) {
							Location bLoc = event.getClickedBlock().getLocation();
							Command.LockChest_ArmDisable(plugin, ply);
							double X = bLoc.getX();
							double Y = bLoc.getY();
							double Z = bLoc.getZ();
							String loc = String.valueOf(X) + "," + String.valueOf(Y) + "," + String.valueOf(Z);
							ChestDefinition.selectChest(ply.getName(), plugin.playerList, bLoc);
							ply.sendMessage(ChatColor.DARK_GREEN + "Chest Location: " + ChatColor.WHITE + loc);

							event.setCancelled(true);
							return;
						}
					} 
					event.setCancelled(locked);
				} else {
					if(locked) {
						boolean ret = lockDecoder.processChest(ply, event.getClickedBlock());
						event.setCancelled(ret);
					}
				}
			}
		//}
	}
}
