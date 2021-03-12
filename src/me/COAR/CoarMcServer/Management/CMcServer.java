package me.COAR.CoarMcServer.Management;

import java.text.SimpleDateFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class CMcServer {
	private Main main;
	public CMcServer(Main plugin) {
		this.main = plugin;
	}
	
	// Plugin commands
	public boolean isNum(String num) {
		try {
			Integer.parseInt(num);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public void tellConsole(String message){
	    Bukkit.getConsoleSender().sendMessage(message);
	}
	
	public SimpleDateFormat getDateFormat() {
//		String format = main.config.getServerData("Config", "DateFormat");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return format;
	}
	
	// All TextChatColor parsings and macro management
		public String TCC(String txt) { 
			// Get all macros and loop through them
			for(String key : main.macros.getConfig().getConfigurationSection("macros").getKeys(false)) {	
				// Set the current macro to work with the parameters
				String macroName = "%" + key + "%";
				// get the macro value which is get from the key value
				String node = main.macros.getConfig().getString("macros." + key);
				// Replace the macro with the value
				if(txt.contains(macroName)) {
					txt = txt.replace(macroName, node);
				}
			}		
			String returnTxt = ChatColor.translateAlternateColorCodes('&', txt);
			return returnTxt;
		}
		public String TCC(String txt, Player player) {
			// Get all macros and loop through them
			for(String key : main.macros.getConfig().getConfigurationSection("macros").getKeys(false)) {
				// Set the current macro to work with the parameters
				String macroName = "%" + key + "%";
				// get the macro value which is get from the key value
				String node;
				node = main.macros.getConfig().getString("macros." + key);
				// check if macro is in the key and if so replace the node with the player or p name
				if(key.equals("AllToggleData")) {
					node = "";
					List<String> ToggleData = main.seplayer.getAllPlayerToggleData(player);
					for(int i = 0; i < ToggleData.size(); i++) {
						if(ToggleData.size() == i + 1) {
							node += ToggleData.get(i).toString();
							continue;
						}
						if(ToggleData.size() == i + 2) {
							node += ToggleData.get(i).toString() + " and ";
							continue;
						}
			            node += ToggleData.get(i) + ", ";
			            tellConsole(node);
					}
				}
				if(key.equalsIgnoreCase("Gamemode"))
					node = player.getGameMode().toString().toLowerCase();
				// Replace the macro with the value
				if(txt.contains(macroName)) 
					txt = txt.replace(macroName, node);
			}		
			// Give styling to the txt and return it
			String returnTxt = ChatColor.translateAlternateColorCodes('&', txt);
			return returnTxt;
		}
		public String TCC(String txt, Integer newInt) {
			// Get all macros and loop through them
			for(String key : main.macros.getConfig().getConfigurationSection("macros").getKeys(false)) {
				// Set the current macro to work with the parameters
				String macroName = "%" + key + "%";
				// get the macro value which is get from the key value
				String node;
				node = main.macros.getConfig().getString("macros." + key);
				// check if macro is in the key and if so replace the node with the player or p name
				if(key.equals("PtimeNew"))
					node = newInt.toString();
				
				// Replace the macro with the value
				if(txt.contains(macroName)) 
					txt = txt.replace(macroName, node);
			}		
			// Give styling to the txt and return it
			String returnTxt = ChatColor.translateAlternateColorCodes('&', txt);
			return returnTxt;
		}
		public String TCC(String txt, CommandSender console, Player p) {
			// Get all macros and loop through them
			for(String key : main.macros.getConfig().getConfigurationSection("macros").getKeys(false)) {
				// Set the current macro to work with the parameters
				String macroName = "%" + key + "%";
				// get the macro value which is get from the key value
				String node = main.macros.getConfig().getString("macros." + key);
				// check if macro is in the key and if so replace the node with the player or p name
				if(key.equals("CommandReciever"))
					node = p.getDisplayName();
				else if(key.equals("CommandExecutor")) 
					node = console.getName();
				if(key.equalsIgnoreCase("Gamemode"))
					node = p.getGameMode().toString().toLowerCase();
				
				// Replace the macro with the value
				if(txt.contains(macroName)) 
					txt = txt.replace(macroName, node);
			}		
			// Give styling to the txt and return it
			String returnTxt = ChatColor.translateAlternateColorCodes('&', txt);
			return returnTxt;
		}	
		public String TCC(String txt, Player player, Player p) {
			// Get all macros and loop through them
			for(String key : main.macros.getConfig().getConfigurationSection("macros").getKeys(false)) {
				// Set the current macro to work with the parameters
				String macroName = "%" + key + "%";
				// get the macro value which is get from the key value
				String node = main.macros.getConfig().getString("macros." + key);
				// check if macro is in the key and if so replace the node with the player or p name
				if(key.equals("CommandReciever"))
					node = p.getDisplayName();
				else if(key.equals("CommandExecutor")) 
					node = player.getName();
				
				// Replace the macro with the value
				if(txt.contains(macroName)) 
					txt = txt.replace(macroName, node);
			}		
			// Give styling to the txt and return it
			String returnTxt = ChatColor.translateAlternateColorCodes('&', txt);
			return returnTxt;
		}
		public String TCC(String txt, Player player, Player p, Integer newInt) {
			// Get all macros and loop through them
			for(String key : main.macros.getConfig().getConfigurationSection("macros").getKeys(false)) {
				// Set the current macro to work with the parameters
				String macroName = "%" + key + "%";
				// get the macro value which is get from the key value
				String node = main.macros.getConfig().getString("macros." + key);
				// check if macro is in the key and if so replace the node with the player or p name
				if(key.equals("CommandReciever"))
					node = p.getDisplayName();
				else if(key.equals("CommandExecutor")) 
					node = player.getName();
				else if(key.equals("PtimeNew"))
					node = newInt.toString();
				
				// Replace the macro with the value
				if(txt.contains(macroName)) 
					txt = txt.replace(macroName, node);
			}		
			// Give styling to the txt and return it
			String returnTxt = ChatColor.translateAlternateColorCodes('&', txt);
			return returnTxt;
		}


	public String testTCC(String format, CommandSender sender, CommandSender reciever, Object customValue) {
		// Get macros and replace them with a value
		for(String key : main.macros.getConfig().getConfigurationSection("macros").getKeys(false)) {
			String macroName = "%" + key + "%";
			String node = main.macros.getConfig().getString("macros." + key);
			
			// Dynamic node values
			if(key.equals("CommandReciever")) {
				if(reciever instanceof Player) {
					Player reciev = (Player) reciever;
					node = reciev.getDisplayName();
				} else if(reciever instanceof ConsoleCommandSender) {
					ConsoleCommandSender reciev = (ConsoleCommandSender) reciever;
					node = reciev.getName();
				}
			} else if(key.equals("CommandExecutor")) {
				if(sender instanceof Player) {
					Player send = (Player) sender;
					node = send.getDisplayName();
				} else if(sender instanceof ConsoleCommandSender) {
					ConsoleCommandSender send = (ConsoleCommandSender) sender;
					node = send.getName();
				}
			} else if(key.equals("PtimeNew")) {
				if(main.functions.isNum(customValue.toString())) {
					node = customValue.toString();
				}
			} else if(key.equals("Message")) {
				if(customValue instanceof String) {
					node = customValue.toString();
				}
			} else if(key.equals("Gamemode")) {
				if(reciever instanceof Player) {
					Player p = (Player) reciever;
					node = p.getGameMode().toString().toLowerCase();
				}
			} else if(key.equals("AllToggleData")) {
				if(sender instanceof Player) {
					Player send = (Player) sender;
					node = "";
					List<String> ToggleData = main.seplayer.getAllPlayerToggleData(send);
					for(int i = 0; i < ToggleData.size(); i++) {
						if(ToggleData.size() == i + 1) {
							node += ToggleData.get(i).toString();
							continue;
						}
						if(ToggleData.size() == i + 2) {
							node += ToggleData.get(i).toString() + " and ";
							continue;
						}
			            node += ToggleData.get(i) + ", ";
			            tellConsole(node);
					}
				}
			}

			// Replace the macro with the value
			if(format.contains(macroName)) 
				format = format.replace(macroName, node);
		}
		String returnTxt = ChatColor.translateAlternateColorCodes('&', format);
		return returnTxt;
	}
}
