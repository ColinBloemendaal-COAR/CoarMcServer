package me.COAR.CoarMcServer.Management;


import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import me.COAR.CoarMcServer.Main;

public class CommandLoader {
	private Main main;
	public CommandLoader(Main plugin) {
		this.main = plugin;
	}

	public void loadCustomCommands() {
		for(String key : main.config.getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").getKeys(false)) {
			Bukkit.getConsoleSender().sendMessage(key);
			if(main.config.getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").getBoolean(key)) {
				for(String key2 : main.config.getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection(key).getKeys(false)) {					
					if(main.config.getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection(key).getBoolean(key2)) {						
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
							Object objectCommand = ctorCommand.newInstance(main);
							main.getCommand(key2.toLowerCase()).setExecutor((CommandExecutor) objectCommand);
							
							Object objectTabCompletion = ctorTabCompletion.newInstance(main);
							main.getCommand(key2.toLowerCase()).setTabCompleter((TabCompleter) objectTabCompletion);
							main.functions.tellConsole(key2);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
