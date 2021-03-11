package me.COAR.CoarMcServer.Essentials.Heal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.COAR.CoarMcServer.Main;


public class Heal implements CommandExecutor {
	private Main main;
	public Heal(Main plugin) {
		this.main = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("heal")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(main.functions.TCC(main.messages.Get("Messages.ErrorMessages.MustBePlayer") + lbl));
				return true;
			} else if(sender instanceof Player) {
				Player player = (Player) sender;
				if(player.hasPermission("CoarMcServer.Heal")) {
					if(args.length == 0) {
						player.setFoodLevel(20);
						player.setSaturation(20);
						player.setHealth(20);
						for(PotionEffect effect : player.getActivePotionEffects()) {
							player.removePotionEffect(effect.getType());
						}
						player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Heal.Succesfull")));
						return true;
					} else if(args.length == 1) {
						if(player.hasPermission("CoarMcServer.Heal.Others")) {
							if(Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(args[0]))) {
								Player p = (Player) Bukkit.getServer().getPlayer(args[0]);
								p.setFoodLevel(20);
								p.setSaturation(20);
								p.setHealth(20);
								for(PotionEffect effect : p.getActivePotionEffects()) {
									p.removePotionEffect(effect.getType());
								}
								if(p.getDisplayName().equals(player.getDisplayName())) {
									p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Heal.Succesfull")));
									return true;
								}
								p.sendMessage(main.functions.TCC(main.messages.Get("Messages.Heal.HealByPlayer"), player, p));
								player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Heal.HealPlayer"), player, p));
								return true;
							} else if(!(Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(args[0])))) {
								player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Heal.Usage")));
							}
						}

					} else if(args.length >= 2) {
						player.sendMessage(main.functions.TCC(main.messages.Get("Messages.Heal.Usage")));
						return true;
					}
				}
				return true;
			}
		}
		return false;
	}
}