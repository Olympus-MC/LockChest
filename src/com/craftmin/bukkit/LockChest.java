package com.craftmin.bukkit;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.craftmin.bukkit.DataSource.DataSource;
import com.craftmin.bukkit.Listeners.lcBlockListener;
import com.craftmin.bukkit.Listeners.lcPlayerListener;
import com.craftmin.bukkit.PlayerLists.lcPlayerList;

public class LockChest extends JavaPlugin {

	public boolean isEnabled = false;
	public com.nijikokun.bukkit.Permissions.Permissions Manager = null;
	public Configuration configuration;
	static final Logger log = Logger.getLogger("Minecraft");
	public DataSource dataSource = null;
	public Settings mySettings = null;
	public Permissions permissions = null;
	
	public lcPlayerList playerList;

	lcPlayerListener playerListener = new lcPlayerListener(this);
	lcBlockListener blockListener = new lcBlockListener(this);
	
	@Override
	public void onDisable() {
		Manager = null;
		isEnabled = false;
		writeConsole("Disabled", true);
	}

	@Override
	public void onEnable() {
		Plugin gmp = this.getServer().getPluginManager().getPlugin("Permissions");
		if (gmp != null) {
			if (!this.getServer().getPluginManager().isPluginEnabled(gmp)) {
				this.getServer().getPluginManager().enablePlugin(gmp);
			}
			Manager = (com.nijikokun.bukkit.Permissions.Permissions) gmp;
		} else {
			//writeConsole("Permissions Plugin Not Found!", true);
			//this.getPluginLoader().disablePlugin(this);
			//return;
		}
		
		String strDirectory = "plugins/LockChest";
	    new File(strDirectory).mkdir();
		
		addHooks();
		
		//Apparently the Config didn't create itself. >:(
		File config = new File("plugins/LockChest/configuration.txt");
		if(!config.exists()) {
			try {
				config.createNewFile();
			} catch (IOException e) {
				writeConsole("Error Creating Config: " + e.getMessage());
			}
		}
		configuration = new Configuration(config);
		configuration.load();
		
		mySettings = new Settings();
		mySettings.setAllowOps(configuration.getBoolean("allowops", true));
		mySettings.setAllowPicking(configuration.getBoolean("allowpicking", true));
		mySettings.setWoodenHoe(configuration.getInt("woodenhoe", 290));
		mySettings.setStoneHoe(configuration.getInt("stonehoe", 291));
		mySettings.setIronHoe(configuration.getInt("ironhoe", 292));
		mySettings.setGoldHoe(configuration.getInt("goldhoe", 294));
		mySettings.setDiamondHoe(configuration.getInt("diamondhoe", 293));
		mySettings.setBaseRate(configuration.getInt("baserate", 40));
		mySettings.setWoodenRate(configuration.getInt("woodenrate", 1));
		mySettings.setStoneRate(configuration.getInt("stonerate", 5));
		mySettings.setIronRate(configuration.getInt("ironrate", 8));
		mySettings.setGoldRate(configuration.getInt("goldrate", 7));
		mySettings.setDiamondRate(configuration.getInt("diamondrate", 20));
		
		if(Manager != null) {
			mySettings.setUsingPermissions(true);
			writeConsole("Using Permissions!");
		} else {
			mySettings.setUsingPermissions(false);
		}
		
		//dataSource = new DataSource(!configuration.getBoolean("sql", false)); //Sql support...
		dataSource = new DataSource(true, this);
		
		playerList = new lcPlayerList();
		playerList.Initialize(this);
		
		permissions = new Permissions(this);
		
		isEnabled = true;
		
		configuration.save(); //Want to have it save it's generated Settings at one point...
		
		writeConsole("Enabled");
		
	}
	
	void addHooks() {
		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Monitor, this);
	}
	
	public void writeConsole(String msg) {
		writeConsole(msg, isEnabled());
	}
	
	public void writeConsole(String msg, boolean force) {
		if(force) {
			log.info("[LockChest] " + msg);
		}
	}
	
	
	
}
