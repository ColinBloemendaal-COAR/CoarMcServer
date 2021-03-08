package me.COAR.CoarMcServer.Essentials.Pweather;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;

public class PweatherTabCompletion implements TabCompleter {
	private Main main;
	public PweatherTabCompletion(Main plugin) {
		this.main = plugin;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command c, String lbl, String[] args) {
		Player player = (Player) sender;
		List<String> tabResults = new ArrayList<String>();
		List<String> tabArguments = new ArrayList<String>();
		if(player.hasPermission("ServerEssentials.Pweather")) {
			if(player.hasPermission("ServerEssentials.Pweather.Others")) {
				if(args.length == 1) { 
					tabArguments.add("rain"); tabArguments.add("clear");
					tabArguments.add("reset");
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						if(p.hasPermission("Messages.Pweather.SetOtherPtime.Unable")) {
							continue;
						}
						if(p.getDisplayName().equals(player.getDisplayName())) {
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
					if(Bukkit.getPlayer(args[0]) != null) {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						if(p.hasPermission("Messages.Pweather.SetOtherPtime.Unable")) {
							if(p.getDisplayName().equals(player.getDisplayName()))
								tabArguments.add("Use /pweather <weather-type> for setting your personal weather");
							else 
								tabArguments.add("Unable to execute the command /pweather on the player " + Bukkit.getServer().getPlayer(args[0]).getDisplayName());
							for(String tA : tabArguments) {
								if(tA.toLowerCase().startsWith(args[1].toLowerCase())) {
									tabResults.add(tA);
								}
							}
							return tabResults;
						
						}
						tabArguments.add("rain"); tabArguments.add("clear");
						tabArguments.add("reset");
						for(String tA : tabArguments) {
							if(tA.toLowerCase().startsWith(args[1].toLowerCase())) {
								tabResults.add(tA);
							}
						}
						return tabResults;
					}
					return tabResults;
				}
				return tabResults;
			} else if(player.hasPermission("ServerEssentials.Pweather")) {
				if(args.length == 1) {
					tabArguments.add("rain"); tabArguments.add("clear");
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
