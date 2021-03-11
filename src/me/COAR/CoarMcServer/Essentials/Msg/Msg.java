package me.COAR.CoarMcServer.Essentials.Msg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;

public class Msg implements CommandExecutor {
	private Main main;
	public Msg(Main plugin) {
		this.main = plugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("msg") || lbl.equalsIgnoreCase("tell")) {
			if(args.length <= 1) {
				// Add usage message for the /msg command
				sender.sendMessage(main.functions.TCC(main.messages.Get("")));
				return true;
			} else if(args.length >= 2) {
				String message = "";
				for(int i = 1; i <= args.length; i++) {
					message += args[i] + " ";
				}
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(Bukkit.getServer().getPlayer(args[0]) != null) {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						// Set and get /msg message formats
						p.sendMessage(main.functions.TCC(main.messages.Get(""), player, p));
						player.sendMessage(main.functions.TCC(main.messages.Get(""), player, p));
						main.seplayer.setPlayerDataString(p, p.getUniqueId().toString(), "PreviousMessage", player.getDisplayName().toString());
						main.seplayer.setPlayerDataString(player, p.getUniqueId().toString(), "PreviousMessage", p.getDisplayName().toString());
						// Check if there if a "Watcher" online
						for(Player watcher : Bukkit.getOnlinePlayers())
							if(watcher.hasPermission(""))
								if(main.seplayer.getPlayerToggleData(watcher, "MessageWatcher") == true)
									watcher.sendMessage(main.functions.TCC(main.messages.Get(""), player, p)); // set message
						
						return true;
					} else if(Bukkit.getServer().getPlayer(args[0]) == null) {
						sender.sendMessage(main.functions.TCC(main.messages.Get("")));
						return false;
					}
				} else if(sender instanceof ConsoleCommandSender) {
					ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;
					if(Bukkit.getServer().getPlayer(args[0]) != null) {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						// Set and get /msg message formats
						p.sendMessage(main.functions.TCC(main.messages.Get(""), consoleSender, p));
						consoleSender.sendMessage(main.functions.TCC(main.messages.Get(""), consoleSender, p));
						main.seplayer.setPlayerDataString(p, p.getUniqueId().toString(), "PreviousMessage", consoleSender.getName());
						// Check if there if a "Watcher" online
						for(Player watcher : Bukkit.getOnlinePlayers())
							if(watcher.hasPermission(""))
								if(main.seplayer.getPlayerToggleData(watcher, "MessageWatcher") == true)
									watcher.sendMessage(main.functions.TCC(main.messages.Get(""), consoleSender, p)); // set message
						
						return true;
					} else if(Bukkit.getServer().getPlayer(args[0]) == null) {
						sender.sendMessage(main.functions.TCC(main.messages.Get("")));
						return false;
					}
				}


			}
		}
		
		return false;
	}

	
}
