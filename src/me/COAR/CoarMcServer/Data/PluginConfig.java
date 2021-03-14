package me.COAR.CoarMcServer.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;

import me.COAR.CoarMcServer.Main;

public class PluginConfig {
	private File serverDataFile = null;
	private FileConfiguration serverDataConfig = null;
	
	private Main main;
	public PluginConfig(Main plugin) {
		this.main = plugin;
	}

	
//	Create plugin config file
	public boolean createServerDataFile() {
		File f = new File(main.getDataFolder() + "/");
		if(!f.exists())
		    f.mkdir();
		
		this.serverDataFile = new File(main.getDataFolder(), "CoarMcServerConfig.yml");
		try {
			Bukkit.getConsoleSender().sendMessage(serverDataFile.getAbsolutePath());
			serverDataFile.createNewFile();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	get config file
	public File getServerDataFile() {
		this.serverDataFile = new File(this.main.getDataFolder(), "CoarMcServerConfig.yml");
		return this.serverDataFile;
	}
//	Reload, set and/or create ~PlayerData~ files
	public void reloadServerDataConfig() {
		if(this.serverDataFile == null)
			createServerDataFile();
		
		this.serverDataConfig = YamlConfiguration.loadConfiguration(getServerDataFile());
		InputStream defaultStreem = this.main.getResource("CoarMcServer.yml");
		if(defaultStreem != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStreem));
			this.serverDataConfig.setDefaults(defaultConfig);
		}
	}
	
//	Get server config file
	public FileConfiguration getConfig() {
		if(this.serverDataConfig == null)
			reloadServerDataConfig();
		return this.serverDataConfig;
	}  
	
	//	Save config file
	public Boolean saveConfig() {
		if(this.serverDataConfig == null || this.serverDataFile == null)
			return false;
		try {
			this.getConfig().save(this.serverDataFile);
			return true;
		} catch (IOException e) {
			this.main.getLogger().log(Level.SEVERE, "Could not save config to " + this.serverDataFile, e);
			return false;
		}
	}
	
//	Create player file & set config sections and nodes
	public void saveDefaultFile() {
		if(this.serverDataFile == null)
			createServerDataFile();
		
		if(this.serverDataFile != null) {
			getConfig().createSection("Config");
			getConfig().getConfigurationSection("Config").createSection("EnabledPluginSection");
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").set("Essentials", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").set("Permissions", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").set("Kits", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").set("LaggPrevention", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").set("ChestCommands", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").set("PersonalVaults", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginSection").set("HolographicDisplays", false);
			
			getConfig().getConfigurationSection("Config").createSection("EnabledPluginCommands");
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").createSection("Essentials");
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection("Essentials").set("Feed", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection("Essentials").set("Fly", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection("Essentials").set("Gamemode", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection("Essentials").set("God", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection("Essentials").set("Heal", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection("Essentials").set("Ptime", false);
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").getConfigurationSection("Essentials").set("Pweather", false);

			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").createSection("Permissions");
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").createSection("Kits");
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").createSection("LaggPrevention");
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").createSection("ChestCommands");
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").createSection("PersonalVaults");
			getConfig().getConfigurationSection("Config").getConfigurationSection("EnabledPluginCommands").createSection("HolographicDisplays");			
			
			getConfig().getConfigurationSection("Config").createSection("ToggleData");
			getConfig().getConfigurationSection("Config").getConfigurationSection("ToggleData");
			getConfig().getConfigurationSection("Config").getConfigurationSection("ToggleData").set("FlyGmcGmsp", false);

			saveConfig();
		}
	}
	
//	Get server config data
	public boolean getPluginSectionToggleData(String section, String value) {
		int tempi = StringUtils.countMatches(section, ".");
		String tempArgs[] = section.split(".", tempi);

		ConfigurationSection configSection = getConfig().getConfigurationSection("Config");
		for(String key : tempArgs) {
			configSection = configSection.getConfigurationSection(key);
		}
		return configSection.getBoolean(value);
	}
}
