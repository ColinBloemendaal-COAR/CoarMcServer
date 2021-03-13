package me.COAR.CoarMcServer.Essentials.Mail;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.COAR.CoarMcServer.Main;

public class Mail implements CommandExecutor {
	private Main main;
	public Mail(Main plugin) {
		this.main = plugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("mail")) {
			if(sender.hasPermission("CoarMcServer.Mail")) {
				if(args.length <= 1) {
					// Add usage message for the /mail command
					sender.sendMessage(main.functions.TCC(main.messages.Get("")));
					return true;
				} else if(args.length >= 2) {
					String message = "";
					for(int i = 1; i <= args.length; i++) {
						message += args[i] + " ";
					}
					
					UUID OfflinePlayer = null;
					if(Bukkit.getServer().getOfflinePlayers() != null) {
						
					}
				}
			}
			return false;
		}
		return false;
	}
}
