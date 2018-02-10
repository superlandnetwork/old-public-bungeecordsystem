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
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Jumpto extends Command {

	public Jumpto(Main m) {
		super("jumpto");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer))
			return;
		if(!sender.hasPermission("ccl.Team"))
			return;
		if(args.length == 1) {
			if(ProxyServer.getInstance().getPlayer(args[0]) == null) {
				sender.sendMessage(new TextComponent("§cFeheler: Dieser Spieler ist nicht Online!"));
				return;
			}
			String info = ProxyServer.getInstance().getPlayer(args[0]).getServer().getInfo().getName();
			sender.sendMessage(new TextComponent("§eVerbinde mit Server §6" + info));
			ProxyServer.getInstance().getPlayer(sender.getName()).connect(ProxyServer.getInstance().getServerInfo(info));
		} else {
			sender.sendMessage(new TextComponent("§c/jumpto <Name>"));
		}
	}
	
}
