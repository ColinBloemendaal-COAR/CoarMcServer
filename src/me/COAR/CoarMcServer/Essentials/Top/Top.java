package me.COAR.CoarMcServer.Essentials.Top;

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
						player.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Top.Usage"), sender, sender, ""));
						return false;
					} else if(args.length == 0) {
						org.bukkit.Location loc = player.getEyeLocation().add(0,1,0);
						for(int i = (int) loc.getY(); i < Bukkit.getServer().getWorlds().get(0).getMaxHeight(); i++) {
							org.bukkit.Location tempLoc = loc;
							tempLoc.setY(i);
							if(tempLoc.getBlock().getType() != Material.AIR) {
								if(tempLoc.add(0, 1, 0).getBlock().getType() == Material.AIR) {
									if(tempLoc.add(0, 1, 0).getBlock().getType() == Material.AIR) {
										tempLoc.setX((int) tempLoc.getX());
										tempLoc.setY((int) tempLoc.getY());
										tempLoc.setZ((int) tempLoc.getZ());
										player.teleport(tempLoc.add(0.5, -1, -0.5));
										break;
									}
								}
							}
							continue;
						}
						player.sendMessage(main.functions.testTCC(main.messages.Get("Messages.Top.Succesfull"), sender, sender, ""));
						return true;
					}
					return false;
					
				} else if(!player.hasPermission("CoarMcServer.Top")) {
					return false;
				}
				return false;
			} else if(sender instanceof ConsoleCommandSender) {
				sender.sendMessage(main.functions.testTCC(main.messages.Get("Messages.ErrorMessages.MustBePlayer"), sender, sender, lbl));
				return false;
			}
			return false;
		}
		return false;
	}
}
