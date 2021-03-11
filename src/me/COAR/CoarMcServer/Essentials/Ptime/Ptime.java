package me.COAR.CoarMcServer.Essentials.Ptime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class Ptime implements CommandExecutor {
	private Main main;
	public Ptime(Main plugin) {
		this.main = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("ptime")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(main.messages.Get("Messages.ErrorMessages.MustBePlayer") + lbl);
				return true;
			} else if(sender instanceof Player) {
				Player player = (Player) sender;
				if(player.hasPermission("CoarMcServer.Ptime")) { 
					if(args.length == 0) {
						if(player.hasPermission("CoarMcServer.Ptime.Others"))
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.UsageOnOther")));
						else if(player.hasPermission("CoarMcServer.ptime"))
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.UsageOnSelf")));
						return true;
					} else if(args.length == 1) {
						if(Bukkit.getServer().getPlayer(args[0]) != null) {
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.UsageOnSelf")));
							return true;
						}
						if(!(args[0].equals("reset")) && !(main.functions.isNum(args[0]))) {

							for(String key : main.messages.getConfig().getConfigurationSection("Messages").getConfigurationSection("Ptime").getConfigurationSection("SetPtime").getKeys(false)) {
								if(key.equalsIgnoreCase(args[0])) {
									Integer TimeValue = 0;
									if(args[0].toLowerCase().equals("day"))
										TimeValue = 1000;
									if(args[0].toLowerCase().equals("noon"))
										TimeValue = 6000;
									if(args[0].toLowerCase().equals("night"))
										TimeValue = 13000;
									if(args[0].toLowerCase().equals("midnight"))
										TimeValue = 18000;
									player.setPlayerTime(TimeValue, false);
									player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetPtime." + key)));
									return true;
								}
							}
						} else {
							if(args[0].equals("reset")) {
								player.setPlayerTime(0, true);
								player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetPtime.Reset")));
								return true;
							}
							if(main.functions.isNum(args[0])) {
								int newInt = Integer.parseInt(args[0]);
								player.setPlayerTime(newInt, false);
								player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetPtime.Custom"), newInt));
								return true;
							}
						}	
					} else if(args.length == 2) {
						if(player.hasPermission("CoarMcServer.Ptime.Others")) {
							if(Bukkit.getServer().getPlayer(args[0]) != null) {
								Player p = (Player) Bukkit.getServer().getPlayer(args[0]);
								if(p.hasPermission("CoarMcServer.Ptime.Others.Unable")) {
									if(p.getDisplayName().equals(player.getDisplayName())) {
										player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.UsageOnSelf")));
										return true;
									} else {
										player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetOtherPtime.Unable"), player, p));
										return true;
									}									
								}
								if(!(args[1].equals("reset")) && !(main.functions.isNum(args[1]))) {
									for(String key : main.messages.getConfig().getConfigurationSection("Messages").getConfigurationSection("Ptime").getConfigurationSection("SetOtherPtime").getKeys(false)) {
										if(key.contains("ByPlayer")) {
											key = key.replace("ByPlayer", "");
										} else if(key.contains("Player")) {
											key = key.replace("Player", "");
										}
										if(key.equalsIgnoreCase(args[1])) {
											Integer TimeValue = 0;
											if(args[1].toLowerCase().equals("day"))
												TimeValue = 1000;
											if(args[1].toLowerCase().equals("noon"))
												TimeValue = 6000;
											if(args[1].toLowerCase().equals("night"))
												TimeValue = 13000;
											if(args[1].toLowerCase().equals("midnight"))
												TimeValue = 18000;
											
											p.setPlayerTime(TimeValue, false);
											p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetOtherPtime." + key + "ByPlayer"), player, p));
											player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetOtherPtime." + key + "Player"), player, p));
											return true;
										}
									}
								} else {
									if(args[1].equals("reset")) {
										p.setPlayerTime(0, true);
										p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetOtherPtime.ResetByPlayer"), player, p));
										player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetOtherPtime.ResetPlayer"), player, p));
										return true;
									}
									if(main.functions.isNum(args[1])) {
										int newInt = Integer.parseInt(args[1]);
										player.setPlayerTime(newInt, false);										
										p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetOtherPtime.CustomByPlayer"), newInt));
										player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.SetOtherPtime.CustomPlayer"), newInt));
										return true;
									}
								}
							}
						} else if(!(player.hasPermission("CoarMcServer.Ptime.Others"))) {
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Ptime.UsageOnSelf")));
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}