package com.craftmin.bukkit.Listeners;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;

import com.craftmin.bukkit.LockChest;
import com.craftmin.bukkit.chest.Chest;
import com.craftmin.bukkit.chest.ChestDefinition;

public class lcBlockListener extends BlockListener {

	LockChest plugin = null;
	
	public lcBlockListener(LockChest Plugin) {
		plugin = Plugin;
	}
	
	public void onBlockDamage(BlockDamageEvent event) {
		if(event.getBlock().getTypeId() != 54) { return; }
		if(plugin.isEnabled) { return; }
		if(!ChestDefinition.isLocked(event.getBlock(), event.getPlayer(), plugin)) {
			return;
		}
		event.setCancelled(true);
	}
	
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getTypeId() != 54) { return; }
		if(!plugin.isEnabled) { return; }
		if(!ChestDefinition.isLocked(event.getBlock(), event.getPlayer(), plugin)) {
			if(plugin.dataSource == null) { return; }
			Chest chest = plugin.dataSource.getChest(event.getBlock().getLocation());
			if(chest != null) {
				chest.UnLock();
				if(chest.getUserList() != null) {
					chest.getUserList().clear();
				}
				plugin.dataSource.addChest(chest);
			}
			return;
		}
		event.setCancelled(true);
	}
	
}
