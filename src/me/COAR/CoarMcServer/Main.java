package me.COAR.CoarMcServer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.COAR.CoarMcServer.Data.PlayerData;
import me.COAR.CoarMcServer.Data.PluginConfig;
import me.COAR.CoarMcServer.Management.CMcServer;
import me.COAR.CoarMcServer.Management.Macros;
import me.COAR.CoarMcServer.Management.Messages;


public class Main extends JavaPlugin implements Listener {
	public PluginConfig config;
	public CMcServer functions;
	public Macros macros;
	public Messages messages;
	public PlayerData seplayer;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this); 
		this.functions = new CMcServer(this);
		this.config = new PluginConfig(this);
		this.macros = new Macros(this);
		this.messages = new Messages(this);
		this.seplayer = new PlayerData(this);
		
		macros.saveDefaultConfig();
		messages.saveDefaultConfig();

//		config.saveDefaultFile();
		for(String key : config.getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").getKeys(false)) {
			Bukkit.getConsoleSender().sendMessage(key);
			if(config.getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").getBoolean(key)) {
				for(String key2 : config.getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection(key).getKeys(false)) {					
					if(config.getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection(key).getBoolean(key2)) {						
						Bukkit.getConsoleSender().sendMessage(" - " + key2);

						Class<?> clazzCommand = null;
						Class<?> clazzTabCompletion = null;
						try {
							clazzCommand = Class.forName("me.COAR.CoarMcServer." + key + "." + key2 + "." + key2);
							clazzTabCompletion = Class.forName("me.COAR.CoarMcServer." + key + "." + key2 + "." + key2 + "TabCompletion");
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						Constructor<?> ctorCommand = null;
						Constructor<?> ctorTabCompletion = null;
						try {
							ctorCommand = clazzCommand.getConstructor(Main.class);
							ctorTabCompletion = clazzTabCompletion.getConstructor(Main.class);
						} catch (NoSuchMethodException | SecurityException e) {
							e.printStackTrace();
						}
						try {
							
							Object objectCommand = ctorCommand.newInstance(this);
							this.getCommand(key2.toLowerCase()).setExecutor((CommandExecutor) objectCommand);
							
							Object objectTabCompletion = ctorTabCompletion.newInstance(this);
							this.getCommand(key2).setTabCompleter((TabCompleter) objectTabCompletion);
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void onDisable() {

	}
}
