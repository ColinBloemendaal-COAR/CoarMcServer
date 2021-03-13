package me.COAR.CoarMcServer.Essentials.Top;

import javax.tools.DocumentationTool.Location;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import me.COAR.CoarMcServer.Main;

public class Top implements CommandExecutor {
	private Main main;
	public Top(Main plugin) {
		this.main = plugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("top")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(player.hasPermission("CoarMcServer.Top")) {
					if(args.length >= 1) {
						// set usage message for /top
						player.sendMessage(main.functions.TCC(main.messages.Get("")));
						return false;
					} else if(args.length == 0) {
						org.bukkit.Location loc = player.getEyeLocation().add(0,1,0);
						 
						for(int i = (int) loc.getY(); i < Bukkit.getServer().getWorlds().get(0).getMaxHeight(); i++) {
							org.bukkit.Location tempLoc = loc;
							if(tempLoc.getBlock().getType() != Material.AIR) {
								if(tempLoc.add(0, 1, 0).getBlock().getType() == Material.AIR) {
									if(tempLoc.add(0, 2, 0).getBlock().getType() == Material.AIR) {
										player.teleport(loc.add(0, 2, 0));
									}
								}
							}
						}
					}
					return false;
					
				} else if(!player.hasPermission("CoarMcServer.Top")) {
					return false;
				}
				return false;
			} else if(sender instanceof ConsoleCommandSender) {
				// set return message that this command must be performed by player
				sender.sendMessage(main.functions.TCC(main.messages.Get("")));
				return false;
			}
			return false;
		}
		return false;
	}
}
