//  ___                                     ___                  _   ___               _                 
// | _ )  _  _   _ _    __ _   ___   ___   / __|  ___   _ _   __| | / __|  _  _   ___ | |_   ___   _ __  
// | _ \ | || | | ' \  / _` | / -_) / -_) | (__  / _ \ | '_| / _` | \__ \ | || | (_-< |  _| / -_) | '  \ 
// |___/  \_,_| |_||_| \__, | \___| \___|  \___| \___/ |_|   \__,_| |___/  \_, | /__/  \__| \___| |_|_|_|
//                     |___/                                               |__/                          
//
// Copyright (C) Filli-IT (Einzelunternehmen) & Ursin Filli - All Rights Reserverd
// Unauthorized copying of the this file, via any medium is strictly prohibited
// Proprietary and confidential
// Written by Ursin Filli <ursin.filli@Filli-IT.ch>

package de.superlandnetwork.bungeecord.system.Commands;

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AutoNick extends Command {

	public AutoNick(Main m) {
		super("autonick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			if(!sender.hasPermission("ccl.nick")) {
				sender.sendMessage(new TextComponent("§cDu hast hierzu Keine Berechtigung!"));
				return;
			}
			PlayerAPI api = new PlayerAPI(((ProxiedPlayer) sender).getUniqueId());
			if(api.AutoNick()) {
				api.setAutoNick(0);
				sender.sendMessage(new TextComponent("§7[§5NICK§7] §cAutonick Deaktiviert!"));
			} else {
				api.setAutoNick(1);
				sender.sendMessage(new TextComponent("§7[§5NICK§7] §aAutonick Aktiviert!"));
			}
		}
	}
	
}
