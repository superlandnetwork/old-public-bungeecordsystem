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
import de.superlandnetwork.bungeecord.system.API.Blacklist;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class unBanIP extends Command {

	public unBanIP(Main m) {
		super("unbanip");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// /unBanIP <IP>
		if(!sender.hasPermission("ccl.command.unbanIP")) {
			return;
		}
		if(args.length == 1) {
			String IP = args[0];
			if(!Blacklist.ips.containsKey(IP)){
				sender.sendMessage(new TextComponent("§cFehler: §6"+IP+"§c §cIst §cnicht §cGebant!"));
			}else{
				String by = sender.getName();
				Blacklist.ips.remove(IP);
				for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
					if(players.hasPermission("ccl.Team")){
						players.sendMessage(new TextComponent("§3-------------- §7[ §aunBanIP §7] §3--------------"));
						players.sendMessage(new TextComponent("§c IP: §4" + IP));
						players.sendMessage(new TextComponent("§c By: §4" + by));
						players.sendMessage(new TextComponent("§3-------------- §7[ §aunBanIP §7] §3--------------"));
					}
				}
			}
		}else{
			sender.sendMessage(new TextComponent("§c/unBanIP §c<IP>"));
		}
	}

}
