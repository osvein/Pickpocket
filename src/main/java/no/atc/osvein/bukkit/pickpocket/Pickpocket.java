/*
 * Copyright (c) 2015 Oskar Sveinsen
 */
package no.atc.osvein.bukkit.pickpocket;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author osvein <osvein@users.noreply.github.com>
 *
 */
public class Pickpocket extends JavaPlugin {
    public static final String COPYRIGHT = "%1$s, Copyright (C) 2015 Oskar Sveinsen";
    public static final String WARRANTY = "%1$s comes with ABSOLUTELY NO WARRANTY; for details see the LICENSE file.";
    
    static final String PREFIX = ChatColor.DARK_GRAY + "[PP] " + ChatColor.AQUA;
    
    @Override
    public void onEnable() {
	String identifier = this.getDescription().getFullName();
	this.getLogger().info(String.format(COPYRIGHT, identifier));
	this.getLogger().info(String.format(WARRANTY, identifier));
    }
    
    @Override
    public void onDisable() {
	
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
	if (command == this.getCommand("pickpocket")) {
	    InventoryHolder victim = null;
	    
	    if (args.length == 1) {
		@SuppressWarnings("deprecation")
		Player p = this.getServer().getPlayer(args[0]);
		
		if (p == null) {
		    sender.sendMessage(PREFIX + ChatColor.RED + "Player " + ChatColor.GOLD + args[0] + ChatColor.RED + " not found.");
		    return false;
		}
		else if (p.hasPermission("pickpocket.vulnerable")) {
		    this.getLogger().info(sender.getName() + " is picking " + p.getName() + "'s pocket.");
		    sender.sendMessage(PREFIX + "Picking player " + ChatColor.GOLD + p.getDisplayName() + ChatColor.AQUA + "'s pocket...");
		    victim = p;
		}
		else {
		    sender.sendMessage(PREFIX + ChatColor.RED + "Player " + ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " is invulnerable to pickpockets.");
		    return false;
		}
	    }
	    
	    if (victim == null)
		return false;
	    
	    Inventory pocket = victim.getInventory();
	    
	    if (sender instanceof HumanEntity) {
		HumanEntity pickpocket = (HumanEntity)sender;
		pickpocket.openInventory(pocket);
		return true;
	    }
	    else {
		sender.sendMessage(PREFIX + ChatColor.RED + "You must be human to use this command.");
		return false;
	    }
	}
	
	return false;
    }
}
