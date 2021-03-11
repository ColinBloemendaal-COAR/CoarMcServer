package me.COAR.CoarMcServer.Essentials.Ptime;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;

public class PtimeTabCompletion implements TabCompleter {
	private Main main;
	public PtimeTabCompletion(Main plugin) {
		this.main = plugin;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command c, String lbl, String[] args) {
		Player player = (Player) sender;
		List<String> tabResults = new ArrayList<String>();
		List<String> tabArguments = new ArrayList<String>();
		if(player.hasPermission("CoarMcServer.Ptime")) {
			if(player.hasPermission("CoarMcServer.Ptime.Others")) {
				if(args.length == 1) { 
					tabArguments.add("day"); tabArguments.add("noon");
					tabArguments.add("night"); tabArguments.add("midnight");
					tabArguments.add("reset");
					
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						if(p.hasPermission("Messages.Ptime.SetOtherPtime.Unable")) {
							continue;
						}
						if(p.getName().equals(player.getDisplayName())) {
							continue;
						}
						tabArguments.add(p.getDisplayName());
					}
					for(String tA : tabArguments) {
						if(tA.toLowerCase().startsWith(args[0].toLowerCase())) {
							tabResults.add(tA);
						}
					}
					return tabResults;
				} else if(args.length == 2) {
					if(Bukkit.getServer().getPlayer(args[0]) != null) {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						if(p.hasPermission("Messages.Ptime.SetOtherPtime.Unable")) {
							if(p.getDisplayName().equals(player.getDisplayName())) {
								tabArguments.add("Use /ptime <time> for setting your personal time");
							} else {
								tabArguments.add("Unable to execute the command /ptime on the player " + Bukkit.getServer().getPlayer(args[0]).getDisplayName());
							}
							for(String tA : tabArguments) {
								if(tA.toLowerCase().startsWith(args[1].toLowerCase())) {
									tabResults.add(tA);
								} 			 
							}
							return tabResults;
						}
						tabArguments.add("day"); tabArguments.add("noon");
						tabArguments.add("night"); tabArguments.add("midnight");
						tabArguments.add("reset");						
						for(String tA : tabArguments) {
							if(tA.toLowerCase().startsWith(args[1].toLowerCase())) {
								tabResults.add(tA);
							} 			 
						}
						return tabResults;
					} else {
						return tabResults;
					}
				} else {
					return tabResults;
				}
			} else if(player.hasPermission("CoarMcServer.Ptime")) {
				if(args.length == 1) {
					tabArguments.add("day"); tabArguments.add("noon");
					tabArguments.add("night"); tabArguments.add("midnight");
					tabArguments.add("reset");
					for(String tA : tabArguments) {
						if(tA.toLowerCase().startsWith(args[0].toLowerCase())) {
							tabResults.add(tA);
						}
					}
					return tabResults;
				} else if(args.length >= 2) {
					return tabResults;
				}
				return tabResults;
			}
			return tabResults;
		}
		return tabResults;
	}
}