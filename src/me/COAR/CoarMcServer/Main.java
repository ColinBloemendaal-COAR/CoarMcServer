package me.COAR.CoarMcServer;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.COAR.CoarMcServer.Data.Macros;
import me.COAR.CoarMcServer.Data.Messages;
import me.COAR.CoarMcServer.Data.PlayerData;
import me.COAR.CoarMcServer.Data.PluginConfig;
import me.COAR.CoarMcServer.Management.CMcServer;
import me.COAR.CoarMcServer.Management.CommandLoader;

public class Main extends JavaPlugin implements Listener {
	public PluginConfig config;
	public CMcServer functions;
	public Macros macros;
	public Messages messages;
	public PlayerData seplayer;
	private CommandLoader cmLoader;

	@Override
	public void onEnable() {
		// Set listeners
		getServer().getPluginManager().registerEvents(new MyListeners(this), this);
		
		// Set instances
		this.functions = new CMcServer(this);
		this.config = new PluginConfig(this);
		this.macros = new Macros(this);
		this.messages = new Messages(this);
		this.seplayer = new PlayerData(this);
		this.cmLoader = new CommandLoader(this);
		
		
		// Must run functions on startup or reload
		cmLoader.loadCustomCommands();
		
		
		// Optional default file saves - Only enable while testing
//		config.saveDefaultFile();
//		macros.saveDefaultConfig();
//		messages.saveDefaultConfig();
	}

	public void onDisable() {

	}
}
