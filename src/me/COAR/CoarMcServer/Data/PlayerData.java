package me.COAR.CoarMcServer.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class PlayerData {
//	Creating instances of files and formats
	private File subDir = null;
	private File playerDataFile = null;
	private FileConfiguration playerDataConfig = null;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
//	PlayerData constructor
	private Main main;
	public PlayerData(Main plugin) {
		this.main = plugin;
	}
	
//	Create ServerEssentials ~PlayerData~ folder
	public boolean createSubFolder() {
		this.subDir = new File(Bukkit.getServer().getPluginManager().getPlugin("ServerEssentials").getDataFolder().getPath() + System.getProperty("file.separator") + "PlayerData");
		return subDir.mkdir();
	}
	
//	Create (or set) ~PlayerData~ file in ~PlayerData~ folder
	public Boolean createPlayerDataFile(Player p) {
		this.playerDataFile = new File(this.subDir.getPath() + System.getProperty("file.separator") + (p.getUniqueId() + ".yml"));
		try {
			playerDataFile.createNewFile();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

//	Get ~PlayerData~ file from folder ~PlayerData~
	public File getPlayerDataFile(Player p) {
		this.playerDataFile = new File(this.subDir.getPath() + System.getProperty("file.separator") + (p.getUniqueId() + ".yml"));
		return this.playerDataFile;
	}
	
//	Reload, set and/or create ~PlayerData~ files
	public void reloadPlayerDataConfig(Player p) {
		if(this.subDir == null)
			createSubFolder();
		if(this.playerDataFile == null)
			createPlayerDataFile(p);
		
		this.playerDataConfig = YamlConfiguration.loadConfiguration(getPlayerDataFile(p));
		InputStream defaultStreem = this.main.getResource(this.subDir.getPath() + System.getProperty("file.separator") + (p.getUniqueId() + ".yml"));
		if(defaultStreem != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStreem));
			this.playerDataConfig.setDefaults(defaultConfig);
		}
	}
	
//	Get config
	public FileConfiguration getConfig(Player p) {
		if(this.playerDataConfig == null)
			reloadPlayerDataConfig(p);
		return this.playerDataConfig;
	}  

	//	Save config file
	public Boolean saveConfig(Player p) {
		if(this.playerDataConfig == null || this.playerDataFile == null)
			return false;
		try {
			this.getConfig(p).save(this.playerDataFile);
			return true;
		} catch (IOException e) {
			this.main.getLogger().log(Level.SEVERE, "Could not save config to " + this.playerDataFile, e);
			return false;
		}
	}
	
//	Create player file & set config sections and nodes
	public void saveDefaultFile(Player p) {
		main.functions.tellConsole("In savedefault");
		if(this.subDir == null);
			createSubFolder();
		if(this.subDir != null && this.playerDataFile == null)
			createPlayerDataFile(p);
		if(this.playerDataFile != null) {
			getConfig(p).createSection(p.getUniqueId().toString());
			saveConfig(p);
			setPlayerData(p, p.getUniqueId().toString(), "PlayerName", p.getDisplayName());
			if(!(p.hasPlayedBefore())) {
				Date dateFirstLogin = new Date();
				setPlayerData(p, p.getUniqueId().toString(), "FirstLogin", dateFormat.format(dateFirstLogin).toString());
			} else {
				setPlayerData(p, p.getUniqueId().toString(), "FirstLogin", "");
			}
			setPlayerData(p, p.getUniqueId().toString(), "LastLogin", "");
			setPlayerSection(p, p.getUniqueId().toString(), "ToggleData");
			setPlayerSection(p, p.getUniqueId().toString(), "DeathMessages");
			setPlayerSection(p, p.getUniqueId().toString(), "DeathMessages");
			setPlayerData(p, (p.getUniqueId().toString() + ".DeathMessages"), "Enabled", "false");
			saveConfig(p);

		}
	}

//	Get player data
	public String getPlayerData(Player p, String section, String value) {
		String[] tempArgs = section.split(".");
		ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
		for(String key : tempArgs) {
			configSection.getConfigurationSection(key);
		}
		return configSection.getString(value);
	}
	
//	Set player data
	public Boolean setPlayerData(Player p, String section, String node, String value) {		
		String[] tempArgs = section.split(".");
		ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
		for(String key : tempArgs) {
			configSection.getConfigurationSection(key);
		}
		configSection.set(node, value);
		try {
			playerDataConfig.save(playerDataFile);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	Get player section
	public ConfigurationSection getPlayerSection(Player p, String section) {
		String[] tempArgs = section.split(".");
		ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
		for(String key : tempArgs) {
			configSection.getConfigurationSection(key);
		}
		return configSection;
	}
//	Set player section
	public Boolean setPlayerSection(Player p, String parentSection, String childSection) {
		String[] tempParentSection = parentSection.split(".");
		ConfigurationSection configParentSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
		for(String key : tempParentSection) {
			configParentSection.getConfigurationSection(key);
		}
		configParentSection.createSection(childSection);
		if(saveConfig(p)) 
			return true;
		else 
			return false;
	}
	
//	Get player ToggleData
	public String getPlayerToggleData(Player p, String ToggleEvent) {
		String value = getPlayerData(p, "ToggleEvents", ToggleEvent);
		return value;
	}

//	Get all player ToggleData
	public List<String> getAllPlayerToggleData(Player p) {
		ConfigurationSection values = getPlayerSection(p, "ToggleData");
		List<String> allReturnValues = new ArrayList<String>();
		for(String toggleData : values.getKeys(false)) {
			if(getPlayerToggleData(p, toggleData).equalsIgnoreCase("true"))
				allReturnValues.add(toggleData);
		}
		return allReturnValues;
	}
	
//	Set player ToggleData
	public boolean setPlayerToggleData(Player p, String toggleEvent) {
		if(!(getPlayerSection(p, "ToggleData").contains(toggleEvent)))
			setPlayerData(p, "ToggleData", toggleEvent, "false");
		if(getPlayerData(p, "ToggleData", toggleEvent).equalsIgnoreCase("false")) {
			setPlayerData(p, "ToggleData", toggleEvent, "true");
			return true;
		}
		if(getPlayerData(p, "ToggleData", toggleEvent).equalsIgnoreCase("true")) {
			setPlayerData(p, "ToggleData", toggleEvent, "false");
			return true;
		}
		return false;
	}
	
	public void onLastLogin(Player p) {
		try {
			Date dateLastLogin = new Date();
			getConfig(p).getConfigurationSection(p.getUniqueId().toString()).set("LastLogin", dateFormat.format(dateLastLogin).toString());
			getConfig(p).save(playerDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	When player joins the server for the first time
	public void onFirstLoginEvent() {
		
	}
	
//	When player join server
	public void onLoginEvent(Player p) {
		if(!(getAllPlayerToggleData(p).isEmpty())) {
			if(getAllPlayerToggleData(p).size() == 1)
				p.sendMessage(main.functions.TCC(main.messages.Get("Messages.OnPlayerJoin.CurrentToggleData.Singular"), p));
			if(getAllPlayerToggleData(p).size() >= 2)
				p.sendMessage(main.functions.TCC(main.messages.Get("Messages.OnPlayerJoin.CurrentToggleData.Multiple"), p));
		}
	}
	
//	When player leaves server
	public void onLogoutEvent() {
	
	}
}
