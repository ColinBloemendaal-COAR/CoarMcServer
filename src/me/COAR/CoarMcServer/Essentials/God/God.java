package me.COAR.CoarMcServer.Essentials.God;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class God implements CommandExecutor {
	private Main main;
	public God(Main plugin) {
		this.main = plugin;
	}
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("god")) {
			if(sender.hasPermission("CoarMcServer.God")) {
				if(args.length == 0) {
					if(sender instanceof ConsoleCommandSender) {
						sender.sendMessage(main.messages.Get("Messages.ErrorMessages.MustBePlayer") + lbl);
						return true;
					}
					Player player = (Player) sender;
					player.setHealth(20); player.setFoodLevel(20); player.setSaturation(20);
					main.seplayer.setPlayerToggleData(player, "God");
					if(main.seplayer.getPlayerToggleData(player, "God") == true)
						player.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.Enabled")));
					if(main.seplayer.getPlayerToggleData(player, "God") == false)
						player.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.Disabled")));
					return true;
				} else if(args.length == 1) {
					if(sender.hasPermission("CoarMcServer.God.Others")) {
						if(Bukkit.getServer().getPlayer(args[0]) != null) {
							Player p = (Player) Bukkit.getServer().getPlayer(args[0]);
							main.seplayer.setPlayerToggleData(p, "God");
							if(main.seplayer.getPlayerToggleData(p, "God") == true) {
								p.setHealth(20); p.setFoodLevel(20); p.setSaturation(20);
								if(sender instanceof Player) {
									Player player = (Player) sender;
									p.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.SetOtherGod.EnableByPlayer"), player, p));
									sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.SetOtherGod.EnablePlayer"), player, p));
								}
								if(sender instanceof ConsoleCommandSender) {
									p.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.SetOtherGod.EnableByPlayer"), sender, p));
									sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.SetOtherGod.EnablePlayer"), sender, p));
								}
							}
							if(main.seplayer.getPlayerToggleData(p, "God") == false) {
								if(sender instanceof Player) {
									Player player = (Player) sender;
									p.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.SetOtherGod.DisableByPlayer"), player, p));
									player.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.SetOtherGod.DisablePlayer"), player, p));
								}
								if(sender instanceof ConsoleCommandSender) {
									p.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.SetOtherGod.DisableByPlayer"),sender, p));
									sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.SetOtherGod.DisablePlayer"), sender, p));
								}
								return true;
							}
						} else {
							if(sender instanceof Player)
								sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.ErrorMessages.CantFindOnlinePlayer")));
							if(sender instanceof ConsoleCommandSender)
								sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.ErrorMessages.CantFindOnlinePlayer")));
							return false;
						}
						return false;
					}

					return false;
				} else if(args.length >= 2) {
					Player player = (Player) sender;
					player.sendMessage(main.functions.TCC(main.messages.Get("Messages.God.Usage")));
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}
}

