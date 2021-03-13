package me.COAR.CoarMcServer.Essentials.R;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.COAR.CoarMcServer.Main;

public class RTabCompletion implements TabCompleter {
	private Main main;
	public RTabCompletion(Main plugin) {
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
