package me.COAR.CoarMcServer.Essentials.Gamemode;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.COAR.CoarMcServer.Main;

public class GamemodeTabCompletion implements TabCompleter {
	private Main main;
	public GamemodeTabCompletion(Main plugin) {
		this.main = plugin;
	}
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		return null;
	}
}
