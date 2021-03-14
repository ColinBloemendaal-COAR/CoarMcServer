package me.COAR.CoarMcServer.Essentials.Top;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.COAR.CoarMcServer.Main;

public class TopTabCompletion implements TabCompleter {
	private Main main;
	public TopTabCompletion(Main plugin) {
		this.main = plugin;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command c, String lbl, String[] args) {
		List<String> tabResults = new ArrayList<String>();
		if(args.length >= 1) {
			return tabResults;
		}
		return tabResults;
	}
}
