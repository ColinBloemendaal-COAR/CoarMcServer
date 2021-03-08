package me.COAR.CoarMcServer.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.COAR.CoarMcServer.Main;


public class PlayerData {
//	PlayerData constructor | get main class for OOP
	private Main main;
	public PlayerData(Main plugin) {
		this.main = plugin;
	}
	
//	Creating and setting instances of files 
	private File subDir;
	private File playerDataFile;
	private FileConfiguration playerDataConfig = null;

	
//	Reload, set and/or create ~PlayerData~ files
	public void reloadPlayerDataConfig(Player p) {
		if(this.subDir == null)
			createSubFolder();
		if(this.playerDataFile == null)
			createPlayerFile(p);
		
		this.playerDataConfig = YamlConfiguration.loadConfiguration(getPlayerFile(p));
		InputStream defaultStreem = this.main.getResource(this.subDir.getPath() + System.getProperty("file.separator") + (p.getUniqueId() + ".yml"));
		if(defaultStreem != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStreem));
			this.playerDataConfig.setDefaults(defaultConfig);
		}
	}
	
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
	
//	Create PlayerData sub folder
	public boolean createSubFolder() {
//		Create a file which equals the sub directory
		this.subDir = new File(main.getDataFolder().getPath() + System.getProperty("file.separator") + "PlayerData");
//		Create the sub directory and return if it is successful
		return subDir.mkdir();
	}
	
//	Set PlayerData sub folder
	public boolean setSubFolder() {
//		Check if the sub directory exists if it does not create it and set the var 
		File tempDir = new File(main.getDataFolder().getPath() + System.getProperty("file.separator") + "PlayerData");
		if(!(tempDir.exists()))
			createSubFolder();
		this.subDir = tempDir;
		return true;
	}
	
//	Get PlayerData sub folder
	public File getSubFolder() {
		if(setSubFolder())
			return subDir;
		return null;
	}
	
//	Create Player file bases on unique id | UUID
	public boolean createPlayerFile(Player p) {
		File tempFile = new File(this.subDir.getPath() + System.getProperty("file.separator") + (p.getUniqueId() + ".yml"));
		try {
			tempFile.createNewFile();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	set var private File playerDataFile
	public void setPlayerFile(Player p) {
		if(this.subDir == null)
			setSubFolder();
		File tempFile = new File(this.subDir.getPath() + System.getProperty("file.separator") + (p.getUniqueId() + ".yml"));
		if(!(tempFile.exists()))
			createPlayerFile(p);
		this.playerDataFile = tempFile;
	}
	
//	get var private File playerDataFile
	public File getPlayerFile(Player p) {
		setPlayerFile(p);
		return this.playerDataFile;
	}
	
//	Save the default values
	public void saveDefault(Player p) {
		if(this.subDir == null) {
			createSubFolder();
		}
			
		if(this.subDir != null && this.playerDataConfig.contains(p.getUniqueId().toString())) {
			setPlayerFile(p);
		}
			
		Date dateFirstLogin = new Date();
		String playerId = p.getUniqueId().toString();
		getConfig(p).createSection(playerId);
		getConfig(p).getConfigurationSection(playerId).set("PlayerName", p.getDisplayName().toString());
		
		if(p.hasPlayedBefore())
			getConfig(p).getConfigurationSection(playerId).set("FirstLogin", "");
		else
			getConfig(p).getConfigurationSection(playerId).set("FirstLogin", main.functions.getDateFormat().format(dateFirstLogin.toString()));
		getConfig(p).getConfigurationSection(playerId).set("LastLogin", "");
		getConfig(p).getConfigurationSection(playerId).createSection("ToggleData");
		
		saveConfig(p);
	}
	
    // Get player data which has type String
    public String getPlayerDataString(Player p, String section, String value) {
        String[] tempArgs = null;
        ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
        if(section.contains(".")) {
            tempArgs = section.split(".");
            for(String key : tempArgs) {
                configSection = configSection.getConfigurationSection(key);
            }
            return configSection.getString(value);
        }  
        else if(!section.contains(".")) {
            configSection = configSection.getConfigurationSection(section);
            return configSection.getString(value);
        }
        return "";
    }

    // Get player data which has type Boolean
    public Boolean getPlayerDataBoolean(Player p, String section, String value) {
        String[] tempArgs;
        ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
        if(section.contains(".")) {
            tempArgs = section.split(".");
            for(String key : tempArgs) {
                configSection = configSection.getConfigurationSection(key);
            }
            return configSection.getBoolean(value);
        }  
        else if(!section.contains(".")) {
            configSection = configSection.getConfigurationSection(section);
            return configSection.getBoolean(value);
        }
        return null;
    }
	
    // Set player data which has type String
    public Boolean setPlayerDataString(Player p, String section, String node, String value) {
        String[] tempArgs;
		ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
        if(section.contains(".")) {
            tempArgs = section.split(".");
            for(String key : tempArgs) {
                configSection = configSection.getConfigurationSection(key);
            }
            configSection.set(node, value);
            return saveConfig(p);
        } else if(!section.contains(".")) {
            configSection = configSection.getConfigurationSection(section);
            configSection.set(node, value);
            return saveConfig(p);
        }
        return null;
    }
    // Set player data which has type String
    public Boolean setPlayerDataBoolean(Player p, String section, String node, Boolean value) {
        String[] tempArgs;
        ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
        if(section.contains(".")) {
            tempArgs = section.split(".");
            for(String key : tempArgs) {
                configSection = configSection.getConfigurationSection(key);
            }
            configSection.set(node, value);
            return saveConfig(p);
        } else if(!section.contains(".")) {
            configSection.getConfigurationSection(section).set(node, value);
            return saveConfig(p);
        }
        return null;
    }
	
    
    
//	Get player section
	public ConfigurationSection getPlayerSection(Player p, String section) {
		if(section.contains(".")) {
			String[] tempArgs = section.split(".");
			ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString());
			for(String key : tempArgs) {
				configSection = configSection.getConfigurationSection(key);
			}
			return configSection;
		} else if(!section.contains(".")) {
			ConfigurationSection configSection = getConfig(p).getConfigurationSection(p.getUniqueId().toString()).getConfigurationSection(section);
			return configSection;
		}
		return null;
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
	public Boolean getPlayerToggleData(Player p, String ToggleEvent) {
		Boolean value = getPlayerDataBoolean(p, "ToggleData", ToggleEvent);
		return value;
	}

//	Get all player ToggleData
	public List<String> getAllPlayerToggleData(Player p) {
		ConfigurationSection values = getPlayerSection(p, "ToggleData");
		List<String> allReturnValues = new ArrayList<String>();
		for(String toggleData : values.getKeys(false)) {
			if(getPlayerToggleData(p, toggleData) == true)
				allReturnValues.add(toggleData);
		}
		return allReturnValues;
	}
	
//	Set player ToggleData
	public boolean setPlayerToggleData(Player p, String toggleEvent) {
		if(getPlayerToggleData(p, toggleEvent) == false) {
			setPlayerDataBoolean(p, "ToggleData", toggleEvent, true);
			return true;
		}
		if(getPlayerToggleData(p, toggleEvent) == true) {
			setPlayerDataBoolean(p, "ToggleData", toggleEvent, false);
			return true;
		}
		return false;
	}
	
//	When player join server
	public void onLoginEvent(Player p) {
		if(!getAllPlayerToggleData(p).isEmpty()) {			
			if(getAllPlayerToggleData(p).size() == 1)
				p.sendMessage(main.functions.TCC(main.messages.Get("Messages.OnPlayerJoin.CurrentToggleData.Singular"), p));
			if(getAllPlayerToggleData(p).size() >= 2)
				p.sendMessage(main.functions.TCC(main.messages.Get("Messages.OnPlayerJoin.CurrentToggleData.Multiple"), p));
		}
	}

	public void onLogoutEvent(Player p) {
		// TODO Auto-generated method stub
		
	}
}
