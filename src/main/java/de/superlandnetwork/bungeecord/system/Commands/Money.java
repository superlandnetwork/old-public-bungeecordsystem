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

public class Money extends Command {

	public Money(Main m) {
		super("Money");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer)sender;
			sender.sendMessage(new TextComponent(Main.Server_Prefix + "§aDein Kontostand: §6" + new PlayerAPI(p.getUniqueId()).getMoney()));
			return;
		}else{
			sender.sendMessage(new TextComponent("§cFehler: Nur ein Spieler kann diesen Befehl ausführen!"));
			return;
		}
	}

}
