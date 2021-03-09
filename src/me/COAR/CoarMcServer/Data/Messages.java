package me.COAR.CoarMcServer.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.COAR.CoarMcServer.Main;


public class Messages {

	private Main plugin;
	private FileConfiguration dataConfig = null;
	private File configFile = null;
	
	public Messages(Main plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}
	public Boolean createMessagesDataFile() {
		this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
		try {
			configFile.createNewFile();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void reloadConfig() {
		if(this.configFile == null) 
			this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
		
		this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
		
		InputStream defaultStreem = this.plugin.getResource("messages.yml");
		if(defaultStreem != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStreem));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
	
	public FileConfiguration getConfig() {
		if(this.dataConfig == null)
			reloadConfig();
		return this.dataConfig;
	}
	
	public void saveConfig() {
		if(this.dataConfig == null || this.configFile == null)
			return;
		try {
			this.getConfig().save(this.configFile);
		} catch (IOException e) {
			this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
		}
			
	}
	
	public void saveDefaultConfig() {
		if(this.configFile == null)
			createMessagesDataFile();
		
		if(!this.configFile.exists()) {
			this.plugin.saveResource("messages.yml", false);
		}
	}
	
	public String Get(String args) {
		String message = getConfig().getString(args);
		return message;
	}
	
}
