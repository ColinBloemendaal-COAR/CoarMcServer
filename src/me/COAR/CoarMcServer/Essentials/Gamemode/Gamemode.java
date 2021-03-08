package me.COAR.CoarMcServer.Essentials.Gamemode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class Gamemode implements CommandExecutor {
	private Main main;
	public Gamemode(Main plugin) {
		this.main = plugin;
	}
	String[] lbls = {"gms", "gmc", "gma", "gmsp"};
	String[] fullLbls = {"SURVIVAL", "CREATIVE", "ADVENTURE", "SPECTATOR"};
	
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("gms") || lbl.equalsIgnoreCase("gmc") || lbl.equalsIgnoreCase("gma") || lbl.equalsIgnoreCase("gmsp")) {
			String currentMode = "";
			for(int i = 0; i < lbls.length; i++)
				if(lbls[i].equalsIgnoreCase(lbl))
					currentMode = fullLbls[i];

			
			if(args.length == 0) {
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(player.hasPermission("ServerEssentials.Gamemode" + currentMode)) {
						if(currentMode.equalsIgnoreCase("CREATIVE"))
							player.setGameMode(GameMode.CREATIVE);
						if(currentMode.equalsIgnoreCase("SURVIVAL"))
							player.setGameMode(GameMode.SURVIVAL);
						if(currentMode.equalsIgnoreCase("ADVENTURE"))
							player.setGameMode(GameMode.ADVENTURE);
						if(currentMode.equalsIgnoreCase("SPECTATOR"))
							player.setGameMode(GameMode.SPECTATOR);
						
						player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.Set"), player));
					} else {
						return false;
					}
				} else if(sender instanceof ConsoleCommandSender) {
					sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.ErrorMessages.MustBePlayer")));
					return false;
				}
			} else if(args.length == 1) {
				if(sender.hasPermission("ServerEssentials.Gamemode.Others")) {
					if(Bukkit.getServer().getPlayer(args[0]) != null) {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						if(sender instanceof Player) {
							Player player = (Player) sender;
							if(p.hasPermission("ServerEssentials.Gamemode.Unable")) {
								player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.Unable"), player, p));
								return true;
							}
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.SetOther"), player, p));
							p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.SetByOther"), player, p));
						} else if(sender instanceof ConsoleCommandSender) {
							ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
							if(p.hasPermission("ServerEssentials.Gamemode.Unable")) {
								commandSender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.Unable"), commandSender, p));
								return true;
							}
							commandSender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.SetPlayer"), commandSender, p));
							p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.SetByPlayer"), commandSender, p));
						}
						if(currentMode.equalsIgnoreCase("creative"))
							p.setGameMode(GameMode.CREATIVE);
						if(currentMode.equalsIgnoreCase("survival"))
							p.setGameMode(GameMode.SURVIVAL);
						if(currentMode.equalsIgnoreCase("adventure"))
							p.setGameMode(GameMode.ADVENTURE);
						if(currentMode.equalsIgnoreCase("spectator"))
							p.setGameMode(GameMode.SPECTATOR);
						
						return true;
					} else if(Bukkit.getServer().getPlayer(args[0]) == null) {
						sender.sendMessage(main.functions.TCC("Messages.ErrorMessages.CantFindOnlinePlayer"));
					}
				}
			} else if(args.length >= 2) {
				if(sender.hasPermission("ServerEssentials.Gamemode.Others")) {
					sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.UsageOnOther")));
					return true;
				}
				if(!(currentMode.isEmpty())) {
					if(sender.hasPermission("ServerEssentials.Gamememode" + currentMode)) {
						sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.UsageOnSelf")));
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
}
