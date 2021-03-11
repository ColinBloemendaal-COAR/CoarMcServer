package me.COAR.CoarMcServer.Essentials.Heal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;

public class HealTabCompletion implements TabCompleter {
	private Main main;
	public HealTabCompletion(Main plugin) {
		this.main = plugin;
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command c, String lbl, String[] args) {
		Player player = (Player) sender;
		List<String> tabArguments = new ArrayList<String>();
		List<String> tabResults = new ArrayList<String>();
		if(args.length == 1) {
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(p.getName().equals(player.getDisplayName()))
					continue;
				if(p.hasPermission("CoarMcServer.Heal.Others.Unable"))
					continue;
				tabArguments.add(p.getDisplayName());
			}
			for(String tA : tabArguments) {
				if(tA.toLowerCase().startsWith(args[0].toLowerCase())) {
					tabResults.add(tA);
				}
			}
			return tabResults;
		}	else if(args.length >= 2) {
			return tabResults;
		}
		return tabResults;
	}
}