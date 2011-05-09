package com.craftmin.bukkit;

public class Settings {

	private boolean allowOps = false;
	private boolean usingPermissions = false;
	
	public Settings() {
		
	}

	public void setAllowOps(boolean allowOps) {
		this.allowOps = allowOps;
	}

	public boolean isAllowOps() {
		return allowOps;
	}

	public void setUsingPermissions(boolean usingPermissions) {
		this.usingPermissions = usingPermissions;
	}

	public boolean isUsingPermissions() {
		return usingPermissions;
	}
	
}
