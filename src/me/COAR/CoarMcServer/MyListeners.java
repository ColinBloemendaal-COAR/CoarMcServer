package me.COAR.CoarMcServer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.COAR.CoarMcServer.Management.NegEffects;

public class MyListeners implements Listener {
	private Main main;
	public MyListeners(Main plugin) {
		this.main = plugin;
	}
	
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			if(!(player.hasPlayedBefore())) {
				main.seplayer.saveDefault(player); 
			}
			if(!main.seplayer.getConfig(player).contains(player.getUniqueId().toString())) {
				main.seplayer.saveDefault(player); 
			}
			main.functions.tellConsole("2");
			main.seplayer.onLoginEvent(player);
		}
	}
	
	@EventHandler
	public void PlayerDamageEvent(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(main.seplayer.getPlayerToggleData(player, "God") == true) {
				event.setCancelled(true);
			}
		}
		
	}
	@EventHandler
	public void PlayerHungerEvent(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(main.seplayer.getPlayerToggleData(player, "God") == true) {
				event.setCancelled(true);
			}
		}
		
	}
	@EventHandler
	public void PlayerPotionEvent(EntityPotionEffectEvent event) {
		main.functions.tellConsole("haiii");
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(main.seplayer.getPlayerToggleData(player, "God") == true) {
				main.functions.tellConsole("1");
				
		        for(PotionEffect effect : event.getNewEffect()){
		        	main.functions.tellConsole("in loop");
		            for(NegEffects bad: NegEffects.values()){
		            	main.functions.tellConsole(bad.toString());
			            if(effect.getType().getName().equalsIgnoreCase(bad.name())){
			            	main.functions.tellConsole("haiii");
//			                event.setCancelled(true);     
//			                player.removePotionEffect(effect.getType());
			            }
		            }           
		        }
			}
		}
	}
	
	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		main.seplayer.onLogoutEvent(p);
	}
}
