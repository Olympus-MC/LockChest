package com.craftmin.bukkit;

public class Settings {

	private boolean allowOps = false;
	private boolean allowPicking = true;
	private boolean usingPermissions = false;

	private int woodenHoe = 0;
	private int stoneHoe = 0;
	private int ironHoe = 0;
	private int goldHoe = 0;
	private int diamondHoe = 0;

	private int baseRate = 0;
	private int woodenRate = 0;
	private int stoneRate = 0;
	private int ironRate = 0;
	private int goldRate = 0;
	private int diamondRate = 0;
	
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

	public void setWoodenHoe(int woodenHoe) {
		this.woodenHoe = woodenHoe;
	}

	public int getWoodenHoe() {
		return woodenHoe;
	}

	public void setStoneHoe(int stoneHoe) {
		this.stoneHoe = stoneHoe;
	}

	public int getStoneHoe() {
		return stoneHoe;
	}

	public void setIronHoe(int ironHoe) {
		this.ironHoe = ironHoe;
	}

	public int getIronHoe() {
		return ironHoe;
	}

	public void setGoldHoe(int goldHoe) {
		this.goldHoe = goldHoe;
	}

	public int getGoldHoe() {
		return goldHoe;
	}

	public void setDiamondHoe(int diamondHoe) {
		this.diamondHoe = diamondHoe;
	}

	public int getDiamondHoe() {
		return diamondHoe;
	}

	public void setAllowPicking(boolean allowPicking) {
		this.allowPicking = allowPicking;
	}

	public boolean isAllowPicking() {
		return allowPicking;
	}

	public void setBaseRate(int baseRate) {
		this.baseRate = baseRate;
	}

	public int getBaseRate() {
		return baseRate;
	}

	public void setWoodenRate(int woodenRate) {
		this.woodenRate = woodenRate;
	}

	public int getWoodenRate() {
		return woodenRate;
	}

	public void setStoneRate(int stoneRate) {
		this.stoneRate = stoneRate;
	}

	public int getStoneRate() {
		return stoneRate;
	}

	public void setIronRate(int ironRate) {
		this.ironRate = ironRate;
	}

	public int getIronRate() {
		return ironRate;
	}

	public void setGoldRate(int goldRate) {
		this.goldRate = goldRate;
	}

	public int getGoldRate() {
		return goldRate;
	}

	public void setDiamondRate(int diamondRate) {
		this.diamondRate = diamondRate;
	}

	public int getDiamondRate() {
		return diamondRate;
	}
	
	
	
}
