package me.COAR.CoarMcServer.Essentials.R;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;

public class R implements CommandExecutor{
	private Main main;
	public R(Main plugin) {
		this.main = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("r")) {
			if(sender.hasPermission("CoarMcServer.Msg")) {
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(Bukkit.getServer().getPlayer(main.seplayer.getPlayerDataString(player, player.getUniqueId().toString(), "PreviousMessage")) != null) {
						Player p = Bukkit.getServer().getPlayer(main.seplayer.getPlayerDataString(player, player.getUniqueId().toString(), "PreviousMessage"));
						if(main.seplayer.getPlayerToggleData(player, "MessageToggle") == true) {
							// return message that CommandExecutor has this toggled ans it has been turned of so that he can can start sending and recieving messages again
							player.sendMessage(main.functions.TCC(main.messages.Get(""), player, p));
						}
						if(main.seplayer.getPlayerToggleData(p, "MessageToggle") == true) {
							// return that the commandReciever has this toggled and so wishes not to recieve /msg or /r commands
							player.sendMessage(main.functions.TCC(main.messages.Get(""), player, p));
							return true;
						}
						
						if(args.length >= 0) {
							String message = "";
							for(int i = 0; i <= args.length; i++) {
								message += args[i] + " ";
							}
							// Set and get /msg message formats
							p.sendMessage(main.functions.TCC(main.messages.Get(""), player, p));
							player.sendMessage(main.functions.TCC(main.messages.Get(""), player, p));
							main.seplayer.setPlayerDataString(p, p.getUniqueId().toString(), "PreviousMessage", player.getDisplayName().toString());
							main.seplayer.setPlayerDataString(player, player.getUniqueId().toString(), "PreviousMessage", p.getDisplayName().toString());
							// Check if there if a "Watcher" online
							for(Player watcher : Bukkit.getOnlinePlayers())
								if(watcher.hasPermission("CoarMcServer.Msg.Watcher"))
									if(main.seplayer.getPlayerToggleData(watcher, "MessageWatcher") == true)
										watcher.sendMessage(main.functions.TCC(main.messages.Get(""), player, p)); // set message						
							return true;
						}
					}
				} else if(sender instanceof ConsoleCommandSender) {
					// set message to console cant execute this command
					sender.sendMessage(main.functions.TCC(main.messages.Get("")));
					return false;
				}
			}
		}
		return false;
	}
}
