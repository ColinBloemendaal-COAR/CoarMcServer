package me.COAR.CoarMcServer.Essentials.Gms;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class Gms implements CommandExecutor {
	private Main main;
	public Gms(Main plugin) {
		this.main = plugin;
	}
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		String[] lbls = {"gms", "gmc", "gma", "gmsp"};
		String[] fullLbls = {"SURVIVAL", "CREATIVE", "ADVENTURE", "SPECTATOR"};
		if(lbl.equalsIgnoreCase("gms") || lbl.equalsIgnoreCase("gmc") || lbl.equalsIgnoreCase("gma") || lbl.equalsIgnoreCase("gmsp")) {
			String currentMode = "";
			for(int i = 0; i < lbls.length; i++)
				if(lbls[i].equalsIgnoreCase(lbl))
					currentMode = fullLbls[i];
			
			if(args.length == 0) {
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(player.hasPermission("CoarMcServer.Gamemode" + currentMode)) {
						// Set gamemode to args
						for(int i = 0; i < fullLbls.length; i++)
							if(currentMode.equalsIgnoreCase(fullLbls[i]))
								player.setGameMode(GameMode.valueOf(fullLbls[i]));
						// Check if the Fly command is enabled and if true check if player flight is enabled
						if(main.config.getPluginSectionToggleData("EnabledPluginCommands.Essentials", "Fly") == true)
							if(main.seplayer.getPlayerToggleData(player, "Fly") == true)
								player.setAllowFlight(true);

						
						player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.Set"), player));
					} else {
						return false;
					}
				} else if(sender instanceof ConsoleCommandSender) {
					sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.ErrorMessages.MustBePlayer")));
					return false;
				}
			} else if(args.length == 1) {
				if(sender.hasPermission("CoarMcServer.Gamemode.Others")) {
					if(Bukkit.getServer().getPlayer(args[0]) != null) {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						if(sender instanceof Player) {
							Player player = (Player) sender;
							if(p.hasPermission("CoarMcServer.Gamemode.Unable")) {
								player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.Unable"), player, p));
								return true;
							}
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.SetOther"), player, p));
							p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.SetByOther"), player, p));
						} else if(sender instanceof ConsoleCommandSender) {
							ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
							if(p.hasPermission("CoarMcServer.Gamemode.Unable")) {
								commandSender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.Unable"), commandSender, p));
								return true;
							}
							commandSender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.SetPlayer"), commandSender, p));
							p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.SetByPlayer"), commandSender, p));
						}
						// Set gamemode to the args
						for(int i = 0; i < fullLbls.length; i++)
							if(currentMode.equalsIgnoreCase(fullLbls[i]))
								p.setGameMode(GameMode.valueOf(fullLbls[i]));
						// Check if the Fly command is enabled and if true check if player flight is enabled
						if(main.config.getPluginSectionToggleData("EnabledPluginCommands.Essentials", "Fly") == true)
							if(main.seplayer.getPlayerToggleData(p, "Fly") == true)
								p.setAllowFlight(true);
						
						return true;
					} else if(Bukkit.getServer().getPlayer(args[0]) == null) {
						sender.sendMessage(main.functions.TCC("Messages.ErrorMessages.CantFindOnlinePlayer"));
					}
				}
			} else if(args.length >= 2) {
				if(sender.hasPermission("CoarMcServer.Gamemode.Others")) {
					sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Gamemode.UsageOnOther")));
					return true;
				}
				if(!(currentMode.isEmpty())) {
					if(sender.hasPermission("CoarMcServer.Gamememode" + currentMode)) {
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
