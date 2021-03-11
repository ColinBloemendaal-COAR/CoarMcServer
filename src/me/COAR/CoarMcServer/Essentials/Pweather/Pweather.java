package me.COAR.CoarMcServer.Essentials.Pweather;

import org.bukkit.Bukkit;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class Pweather implements CommandExecutor  {
	private Main main;
	public Pweather(Main plugin) {
		this.main = plugin;
	}
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("pweather")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(main.messages.Get("Messages.ErrorMessages.MustBePlayer") + lbl);
				return true;
			} else if(sender instanceof Player) {
				Player player = (Player) sender;
				if(player.hasPermission("CoarMcServer.Pweather")) { 
					if(args.length == 0) {
						if(player.hasPermission("CoarMcServer.Pweather.Others"))
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.UsageOnOther")));
						else if(player.hasPermission("CoarMcServer.Pweather"))
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.UsageOnSelf")));
						return true;
					} else if(args.length == 1) {
						if(Bukkit.getServer().getPlayer(args[0]) != null) {
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.UsageOnSelf")));
							return true;
						}
						if(!(args[0].equalsIgnoreCase("reset"))) {
							for(String key : main.messages.getConfig().getConfigurationSection("Messages").getConfigurationSection("Pweather").getConfigurationSection("SetPweather").getKeys(false)) {
								if(key.equalsIgnoreCase(args[0])) {
									WeatherType WeatherValue = null;
									if(args[0].equalsIgnoreCase("clear"))
										WeatherValue = WeatherType.CLEAR;
									if(args[0].equalsIgnoreCase("rain"))
										WeatherValue = WeatherType.DOWNFALL;
									player.setPlayerWeather(WeatherValue);
									player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.SetPweather." + key)));
									return true;
								}
							}
						} else if(args[0].equalsIgnoreCase("reset")) {
							player.resetPlayerWeather();
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.SetPweather.Reset")));
							return true;
						}
					} else if(args.length == 2) {
						if(player.hasPermission("CoarMcServer.Pweather.Others")) {
							if(Bukkit.getServer().getPlayer(args[0]) != null) {
								Player p = Bukkit.getServer().getPlayer(args[0]);
								if(p.hasPermission("CoarMcServer.Pweather.Others.Unable")) {
									if(p.getDisplayName().equals(player.getDisplayName())) {
										player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.UsageOnSelf")));
										return true;
									} else {
										player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.SetOtherPweather.Unable"), player, p));
										return true;
									}
								}
								if(!(args[1].equalsIgnoreCase("reset"))) {
									for(String key : main.messages.getConfig().getConfigurationSection("Messages").getConfigurationSection("Pweather").getConfigurationSection("SetOtherPweather").getKeys(false)) {
										if(key.contains("ByPlayer"))
											key = key.replace("ByPlayer", "");
										else if(key.contains("Player"))
											key = key.replace("Player", "");
										if(key.equalsIgnoreCase(args[1])) {
											WeatherType WeatherValue = null;
											if(args[1].equalsIgnoreCase("clear"))
												WeatherValue = WeatherType.CLEAR;
											if(args[1].equalsIgnoreCase("rain"))
												WeatherValue = WeatherType.DOWNFALL;
											p.setPlayerWeather(WeatherValue);
											p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.SetOtherPweather." + key + "ByPlayer"), player, p));
											player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.SetOtherPweather." + key + "Player"), player, p));
											return true;
										}
									}
								} else if(args[1].equals("reset")) {
									p.resetPlayerWeather();;
									p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.SetOtherPweather.ResetByPlayer"), player, p));
									player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.SetOtherPweather.ResetPlayer"), player, p));
									return true;
								}
							}
						} else if(!(player.hasPermission("CoarMcServer.Pweather.Others"))) {
							player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Pweather.UsageOnSelf")));
							return true;
						}
					} else if(args.length >= 3) {
						if(player.hasPermission("CoarMcServer.Pweather.Others"))
							player.sendMessage(main.messages.Get("Messages.Pweather.UsageOnOther"));
						else 
							player.sendMessage(main.messages.Get("Messages.Pweather.UsageOnSelf"));
						return true;
					}
				}
			}
		}
		return false;
	}
}
