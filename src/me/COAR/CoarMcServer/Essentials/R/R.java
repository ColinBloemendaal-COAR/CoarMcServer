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
			if(sender instanceof Player) {
				Player player = (Player) sender;
				Player p = Bukkit.getServer().getPlayer(main.seplayer.getPlayerDataString(player, player.getUniqueId().toString(), "PreviousMessage"));
			} else if(sender instanceof ConsoleCommandSender) {
				
				sender.sendMessage(main.functions.TCC(main.messages.Get("")));
				return false;
			}
		}
		
		
		return false;
	}
}
