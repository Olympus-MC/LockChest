package com.craftmin.bukkit.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.craftmin.bukkit.LockChest;
import com.craftmin.bukkit.PlayerLists.lcPlayerListArgs;
import com.craftmin.bukkit.chest.Chest;
import com.craftmin.bukkit.chest.ChestDefinition;

public class Command {
	
	public static class CommandDefinition {
		public static final String[] Commands = {"lock", "unlock", "enable", "disable",
													"current", "adduser", "removeuser"};

		public static final int LOCKCHEST_COMMAND_LOCK = 0;
		public static final int LOCKCHEST_COMMAND_UNLOCK = 1;
		public static final int LOCKCHEST_COMMAND_ENABLE = 2;
		public static final int LOCKCHEST_COMMAND_DISABLE = 3;
		public static final int LOCKCHEST_COMMAND_CURRENT = 4;
		public static final int LOCKCHEST_COMMAND_ADDUSER = 5;
		public static final int LOCKCHEST_COMMAND_REMOVEUSER = 6;
	}
	
	public static String[] processInput(String[] input) {
		String[] ReT = new String[input.length];
		for(int i = 0; i < input.length; i++) {
			ReT[i] = input[i].trim();
		}
		return ReT;
	}
	
	public static void processCommand(PlayerCommandPreprocessEvent event, LockChest plugin) {
		if(event.getMessage() != null && event.getMessage().trim().startsWith("/")) {
			String Command = event.getMessage().trim();
			String[] Commands = Command.split(" ");
			if(Commands != null && Commands.length > 0) {
				if(Commands[0].equalsIgnoreCase("/lockchest")) {
					plugin.writeConsole("Player '" + event.getPlayer().getName() + "' " +
							"sent command: " + event.getMessage());
					event.setCancelled(true);
					
					if(Commands.length > 1) {
						switch(getCommandValue(Commands[1])) {
							case CommandDefinition.LOCKCHEST_COMMAND_LOCK: {
									if(Commands.length >= 2) {
										String[] Users = null;
										boolean config = false;
										if(Commands.length > 2 && event.getMessage().contains(" lock ")) {
											String UsersTrimmed = event.getMessage().substring(
													event.getMessage().indexOf(" lock ") + 7, 
													event.getMessage().length()).trim();
											if(UsersTrimmed.contains(",") && UsersTrimmed.length() > 0) {
												Users = UsersTrimmed.split(",");
												String[] newUsers = new String[Users.length + 1];
												int i = 1;
												for(String ply : Users) {
													newUsers[i] = ply;
													i++;
												}
												newUsers[0] = event.getPlayer().getName();
												Users = processInput(newUsers);
												config = true;
											} else {
												Users = new String[2];
												Users[0] = event.getPlayer().getName();
												Users[1] = Commands[2];
												config = true;
											}
										}
										
										if(!config) {
											Users = new String[1];
											Users[0] = event.getPlayer().getName();
										}
	
										ChestDefinition.Lock(event.getPlayer(), Users, plugin);
										break;
								}
								event.getPlayer().sendMessage(ChatColor.DARK_RED + "Usage:");
								event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest lock");
								event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest lock player1");
								event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest lock player1,player2");
								break;
							} case CommandDefinition.LOCKCHEST_COMMAND_UNLOCK: {
								ChestDefinition.unLock(event.getPlayer(), plugin);
								break;
							} case CommandDefinition.LOCKCHEST_COMMAND_DISABLE: {
								LockChest_ArmDisable(plugin, event.getPlayer());
								break;
							} case CommandDefinition.LOCKCHEST_COMMAND_ENABLE: {
								LockChest_ArmEnable(plugin, event.getPlayer());
								break;
							} case CommandDefinition.LOCKCHEST_COMMAND_CURRENT: {
								Location bLoc = ChestDefinition.getSelectedChest(event.getPlayer().getName(), plugin.playerList);
								String location = ChatColor.DARK_GREEN + "Current Selected Chest Location: " + ChatColor.WHITE;
								if(bLoc == null) {
									location += "None Selected";
								} else {
									double X = bLoc.getX();
									double Y = bLoc.getY();
									double Z = bLoc.getZ();
									location +=  String.valueOf(X) + "," + String.valueOf(Y) + "," + String.valueOf(Z);
								}
								event.getPlayer().sendMessage(location);
								break;
							} case CommandDefinition.LOCKCHEST_COMMAND_ADDUSER: {
								Location cLoc = ChestDefinition.getSelectedChest(event.getPlayer().getName(), plugin.playerList);
								if(cLoc == null) {
									event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "None Selected");
								} else {
									Chest chest = plugin.dataSource.getChest(cLoc);
									if(chest == null) {
										event.getPlayer().sendMessage(ChatColor.DARK_RED + "Error Reading Chest!");
									} else {
										//chest.addUser(playerName)
										if(Commands.length >= 3) {
											String ply = Commands[2];
											if(ply != null) {
												if(ply.contains(",") && event.getMessage().contains(" lock ")) {
													String UsersTrimmed = event.getMessage().substring(
															event.getMessage().indexOf(" lock ") + 7, 
															event.getMessage().length()).trim();
													String[] split = processInput(UsersTrimmed.split(","));
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
										if(plugin.dataSource.addChest(chest)) {
											event.getPlayer().sendMessage(ChatColor.DARK_GREEN + 
													"Saved Chest at: " + ChatColor.WHITE +
													chest.locationToString());
										} else {
											event.getPlayer().sendMessage(ChatColor.DARK_RED + 
													"Error Saving Chest at: " + ChatColor.WHITE +
													chest.locationToString());
										}
										break;
									}
								}
								event.getPlayer().sendMessage(ChatColor.DARK_RED + "Usage:");
								event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest adduser player1");
								event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest adduser player1,player2");
								break;
							} case CommandDefinition.LOCKCHEST_COMMAND_REMOVEUSER: {
								Location cLoc = ChestDefinition.getSelectedChest(event.getPlayer().getName(), plugin.playerList);
								if(cLoc == null) {
									event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "None Selected");
								} else {
									Chest chest = plugin.dataSource.getChest(cLoc);
									if(chest == null) {
										event.getPlayer().sendMessage(ChatColor.DARK_RED + "Error Reading Chest!");
									} else {
										//chest.addUser(playerName)
										if(Commands.length >= 3) {
											String ply = Commands[2];
											if(ply != null) {
												if(ply.contains(",") && event.getMessage().contains(" lock ")) {
													String UsersTrimmed = event.getMessage().substring(
															event.getMessage().indexOf(" lock ") + 7, 
															event.getMessage().length()).trim();
													String[] split = processInput(UsersTrimmed.split(","));
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
										if(plugin.dataSource.addChest(chest)) {
											event.getPlayer().sendMessage(ChatColor.DARK_GREEN + 
													"Saved Chest at: " + ChatColor.WHITE +
													chest.locationToString());
										} else {
											event.getPlayer().sendMessage(ChatColor.DARK_RED + 
													"Error Saving Chest at: " + ChatColor.WHITE +
													chest.locationToString());
										}
										break;
									}
								}
								event.getPlayer().sendMessage(ChatColor.DARK_RED + "Usage:");
								event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest removeuser player1");
								event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest removeuser player1,player2");
								break;
							}
						}
					}
				}
			}
		}
	}

	/*public static void processCommand(PlayerChatEvent event, LockChest plugin) {
		if(event.getMessage() != null && event.getMessage().contains(" ")) {
			String[] message = event.getMessage().split(" ");
			if(message != null && message.length > 0 && message[0] != null && message[0].trim() !=  "") {
				if(message[0].trim().equalsIgnoreCase("/lockchest")) {
					plugin.writeConsole("Player '" + event.getPlayer().getName() + "' " +
							"sent command: " + event.getMessage());
					event.setCancelled(true);
					
					if(message.length <= 1) { return; }
					if(message[1] == null) { return; }
					
					switch(getCommandValue(message[1])) {
						case CommandDefinition.LOCKCHEST_COMMAND_LOCK: {
								if(message.length >= 2) {
									//if(message[2].contains(",")) {
									//if(message[2].length() > 0) {
									//	Users = message[2].split(",");
										//Users[Users.length] = event.getPlayer().getName();
									//}
									
									String[] Users = null;
									boolean config = false;
									if(message.length > 2 && event.getMessage().contains(" lock ")) {
										String UsersTrimmed = event.getMessage().substring(
												event.getMessage().indexOf(" lock ") + 7, 
												event.getMessage().length()).trim();
										if(UsersTrimmed.contains(",") && UsersTrimmed.length() > 0) {
											Users = UsersTrimmed.split(",");
											String[] newUsers = new String[Users.length + 1];
											int i = 1;
											for(String ply : Users) {
												newUsers[i] = ply;
												i++;
											}
											newUsers[0] = event.getPlayer().getName();
											Users = processInput(newUsers);
											config = true;
										} else {
											Users = new String[2];
											Users[0] = event.getPlayer().getName();
											Users[1] = message[2];
											config = true;
										}
									}
									
									if(!config) {
										Users = new String[1];
										Users[0] = event.getPlayer().getName();
									}

									ChestDefinition.Lock(event.getPlayer(), Users, plugin);
									break;
							}
							event.getPlayer().sendMessage(ChatColor.DARK_RED + "Usage:");
							event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest lock");
							event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest lock player1");
							event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest lock player1,player2");
							break;
						} case CommandDefinition.LOCKCHEST_COMMAND_UNLOCK: {
							ChestDefinition.unLock(event.getPlayer(), plugin);
							break;
						} case CommandDefinition.LOCKCHEST_COMMAND_DISABLE: {
							LockChest_ArmDisable(plugin, event.getPlayer());
							break;
						} case CommandDefinition.LOCKCHEST_COMMAND_ENABLE: {
							LockChest_ArmEnable(plugin, event.getPlayer());
							break;
						} case CommandDefinition.LOCKCHEST_COMMAND_CURRENT: {
							Location bLoc = ChestDefinition.getSelectedChest(event.getPlayer().getName(), plugin.playerList);
							String location = ChatColor.DARK_GREEN + "Current Selected Chest Location: " + ChatColor.WHITE;
							if(bLoc == null) {
								location += "None Selected";
							} else {
								double X = bLoc.getX();
								double Y = bLoc.getY();
								double Z = bLoc.getZ();
								location +=  String.valueOf(X) + "," + String.valueOf(Y) + "," + String.valueOf(Z);
							}
							event.getPlayer().sendMessage(location);
							break;
						} case CommandDefinition.LOCKCHEST_COMMAND_ADDUSER: {
							Location cLoc = ChestDefinition.getSelectedChest(event.getPlayer().getName(), plugin.playerList);
							if(cLoc == null) {
								event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "None Selected");
							} else {
								Chest chest = plugin.dataSource.getChest(cLoc);
								if(chest == null) {
									event.getPlayer().sendMessage(ChatColor.DARK_RED + "Error Reading Chest!");
								} else {
									//chest.addUser(playerName)
									if(message.length >= 3) {
										String ply = message[2];
										if(ply != null) {
											if(ply.contains(",") && event.getMessage().contains(" lock ")) {
												String UsersTrimmed = event.getMessage().substring(
														event.getMessage().indexOf(" lock ") + 7, 
														event.getMessage().length()).trim();
												String[] split = processInput(UsersTrimmed.split(","));
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
									if(plugin.dataSource.addChest(chest)) {
										event.getPlayer().sendMessage(ChatColor.DARK_GREEN + 
												"Saved Chest at: " + ChatColor.WHITE +
												chest.locationToString());
									} else {
										event.getPlayer().sendMessage(ChatColor.DARK_RED + 
												"Error Saving Chest at: " + ChatColor.WHITE +
												chest.locationToString());
									}
									break;
								}
							}
							event.getPlayer().sendMessage(ChatColor.DARK_RED + "Usage:");
							event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest adduser player1");
							event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest adduser player1,player2");
							break;
						} case CommandDefinition.LOCKCHEST_COMMAND_REMOVEUSER: {
							Location cLoc = ChestDefinition.getSelectedChest(event.getPlayer().getName(), plugin.playerList);
							if(cLoc == null) {
								event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "None Selected");
							} else {
								Chest chest = plugin.dataSource.getChest(cLoc);
								if(chest == null) {
									event.getPlayer().sendMessage(ChatColor.DARK_RED + "Error Reading Chest!");
								} else {
									//chest.addUser(playerName)
									if(message.length >= 3) {
										String ply = message[2];
										if(ply != null) {
											if(ply.contains(",") && event.getMessage().contains(" lock ")) {
												String UsersTrimmed = event.getMessage().substring(
														event.getMessage().indexOf(" lock ") + 7, 
														event.getMessage().length()).trim();
												String[] split = processInput(UsersTrimmed.split(","));
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
									if(plugin.dataSource.addChest(chest)) {
										event.getPlayer().sendMessage(ChatColor.DARK_GREEN + 
												"Saved Chest at: " + ChatColor.WHITE +
												chest.locationToString());
									} else {
										event.getPlayer().sendMessage(ChatColor.DARK_RED + 
												"Error Saving Chest at: " + ChatColor.WHITE +
												chest.locationToString());
									}
									break;
								}
							}
							event.getPlayer().sendMessage(ChatColor.DARK_RED + "Usage:");
							event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest removeuser player1");
							event.getPlayer().sendMessage(ChatColor.GRAY + "    /lockchest removeuser player1,player2");
							break;
						}
					}
				}
			}
		}
	}*/
	
	public static int getCommandValue(String command) {
		int i = 0;
		for(String cmd : CommandDefinition.Commands) {
			if(cmd.equalsIgnoreCase(command.trim())) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public static void LockChest_ArmEnable(LockChest plugin, Player player) {
		if(plugin.playerList.containsPlayer(player.getName())) {
			lcPlayerListArgs args = plugin.playerList.getPlayerArgs(player.getName());
			if(args == null) {
				plugin.playerList.remove(plugin.playerList.getPlayerNameCorrection(player.getName()));
			} else {
				plugin.playerList.getPlayerArgs(player.getName()).setEnabled(true);
				player.sendMessage(ChatColor.DARK_GREEN + "You may now select a Chest by Right Clicking it.");
				return;
			}
		}
		lcPlayerListArgs args = new lcPlayerListArgs();
		args.setEnabled(true);
		plugin.playerList.put(player.getName(), args);
		player.sendMessage(ChatColor.DARK_GREEN + "You may now select a Chest by Right Clicking it.");
	}
	public static void LockChest_ArmDisable(LockChest plugin, Player player) {
		if(plugin.playerList.containsPlayer(player.getName())) {
			lcPlayerListArgs args = plugin.playerList.getPlayerArgs(player.getName());
			if(args == null) {
				plugin.playerList.remove(plugin.playerList.getPlayerNameCorrection(player.getName()));
			} else {
				plugin.playerList.getPlayerArgs(player.getName()).setEnabled(false);
				player.sendMessage(ChatColor.DARK_GREEN + "LockChest Selecting tool now Disabled.");
				return;
			}
		}
		lcPlayerListArgs args = new lcPlayerListArgs();
		args.setEnabled(false);
		plugin.playerList.put(player.getName(), args);
		player.sendMessage(ChatColor.DARK_GREEN + "LockChest Selecting tool now Disabled.");
	}
	
}
