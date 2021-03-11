package me.COAR.CoarMcServer.Essentials.Fly;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class Fly implements CommandExecutor {

	private Main main;
	public Fly(Main plugin) {
		this.main = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("fly")) { 
			if(sender.hasPermission("CoarMcServer.Fly")) {
				if(args.length == 0) {
					if(sender instanceof ConsoleCommandSender) {
						sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.ErrorMessages.MustBePlayer")));
						return true;
					} else if(sender instanceof Player) {
					    Player player = (Player) sender;
					    if(main.config.getPluginSectionToggleData("ToggleData", "Flygmcgmsp") == true) {
					        if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
					            main.seplayer.setPlayerToggleData(player, "Fly");
					            if(main.seplayer.getPlayerToggleData(player, "Fly") == true) {
					                player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.Enabled")));
					                player.setAllowFlight(true);
					                return true;
					            } else if(main.seplayer.getPlayerToggleData(player, "Fly") == false) {
					                player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.Disabled")));
					                player.setAllowFlight(false);
					                return true;
					            }
					        }
					        else if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
					            player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.gmcgmsp")));
					            return true;
					        }
					    } else if(main.config.getPluginSectionToggleData("ToggleData", "Flygmcgmsp") == false) {
					        main.seplayer.setPlayerToggleData(player, "Fly");
					        if(main.seplayer.getPlayerToggleData(player, "Fly") == true) {
					            player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.Enabled")));
					            player.setAllowFlight(true);
					            return true;
					        } else if(main.seplayer.getPlayerToggleData(player, "Fly") == false) {
					            player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.Disabled")));
					            player.setAllowFlight(false);
					            return true;
					        }
					    }
					    return true;
					}
				} else if(args.length == 1) {
					if(sender.hasPermission("CoarMcServer.Fly.Other")) {
						if(Bukkit.getServer().getPlayer(args[0]) != null) {
							Player p = (Player) Bukkit.getServer().getPlayer(args[0]);
							if(main.config.getPluginSectionToggleData("ToggleData", "Flygmcgmsp") == true) {
								if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
									main.seplayer.setPlayerToggleData(p, "Fly");
									if(main.seplayer.getPlayerToggleData(p, "Fly") == true) {
										if(sender instanceof Player) {
											Player player = (Player) sender;
											sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.EnablePlayer"), player, p));
											p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.EnableByPlayer"), player, p));
										} else if(sender instanceof ConsoleCommandSender) {
											ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;
											sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.EnablePlayer"), consoleSender, p));
											p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.EnableByPlayer"), consoleSender, p));
										}
										p.setAllowFlight(true);
										return true;
									} else if(main.seplayer.getPlayerToggleData(p, "Fly") == false) {
										if(sender instanceof Player) {
											Player player = (Player) sender;
											sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.DisablePlayer"), player, p));	
											p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.DisableByPlayer"), player, p));
										} else if(sender instanceof ConsoleCommandSender) {
											ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;
											sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.DisablePlayer"), consoleSender, p));
											p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.DisableByPlayer"), consoleSender, p));

										}
										p.setAllowFlight(false);
										return true;
									}
								}
								else if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
									sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.gmcgmsp")));
									return true;
								}
							} else if(main.config.getPluginSectionToggleData("ToggleData", "Flygmcgmsp") == false) {
								main.seplayer.setPlayerToggleData(p, "Fly");
								if(main.seplayer.getPlayerToggleData(p, "Fly") == true) {
									if(sender instanceof Player) {
										Player player = (Player) sender;
										sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.EnablePlayer"), player, p));
										p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.EnableByPlayer"), player, p));
									} else if(sender instanceof ConsoleCommandSender) {
										ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;
										sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.EnablePlayer"), consoleSender, p));
										p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.EnableByPlayer"), consoleSender, p));

									}
									p.setAllowFlight(true);
									return true;
								} else if(main.seplayer.getPlayerToggleData(p, "Fly") == false) {
									if(sender instanceof Player) {
										Player player = (Player) sender;
										sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.DisablePlayer"), player, p));	
										p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.DisableByPlayer"), player, p));
									} else if(sender instanceof ConsoleCommandSender) {
										ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;
										sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.DisablePlayer"), consoleSender, p));	
										p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.SetOtherFly.DisableByPlayer"), consoleSender, p));
									}
									p.setAllowFlight(false);
									return true;
								}
							}
							return true;
							}
						} else if(Bukkit.getServer().getPlayer(args[0]) == null) {
							if(sender instanceof Player)
								sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.ErrorMessages.CantFindOnlinePlayer")));
							if(sender instanceof ConsoleCommandSender)
								sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.ErrorMessages.CantFindOnlinePlayer")));
							return true;
						}
					}
				} else if(args.length >= 2) {
					Player player = (Player) sender;
					player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Fly.Usage")));
					return true;
				}
			}
			
			if(sender instanceof Player) {
				Player player = (Player) sender;
				player.setAllowFlight(true);;
			}
		return false;
	}

}
