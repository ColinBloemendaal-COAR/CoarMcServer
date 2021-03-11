package me.COAR.CoarMcServer.Essentials.Feed;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;

public class Feed implements CommandExecutor{
	private Main main;
	public Feed(Main plugin) {
		this.main = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("feed")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(main.functions.TCC(main.messages.getConfig().getString("Messages.ErrorMessages.MustBePlayer")) + lbl);
				return true;
			} else if(sender instanceof Player) {
				
				Player player = (Player) sender;
				if(player.hasPermission("CoarMcServer.feed")) {
					if(args.length == 0) {
						player.setFoodLevel(20);
						player.setSaturation(20);
						player.sendMessage(main.functions.TCC(main.messages.getConfig().getString("Messages.Feed.Succesfull")));
						return true;
					} else if(args.length == 1) {
						if(player.hasPermission("CoarMcServer.Feed.Others")) {
							if(Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(args[0]))) {
								Player p = (Player) Bukkit.getServer().getPlayer(args[0]);
								p.setFoodLevel(20);
								p.setSaturation(20);
								if(p.getDisplayName().equals(player.getDisplayName())) {
									player.sendMessage(main.functions.TCC(main.messages.getConfig().getString("Messages.Feed.Succesfull")));
									return true;
								}
								p.sendMessage(main.functions.TCC(main.messages.getConfig().getString("Messages.Feed.FedByPlayer"), player, p));
								player.sendMessage(main.functions.TCC(main.messages.getConfig().getString("Messages.Feed.FedPlayer"), player, p));
								return true;
							} else if(!(Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(args[0])))) {
								player.sendMessage(main.functions.TCC(main.messages.getConfig().getString("Messages.Feed.Usage")));
							}
						}

					}
				}
				return true;
			}
		}
		return false;
	}
}
