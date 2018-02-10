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

import java.util.UUID;

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.API.Blacklist;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class unBan extends Command {

	public unBan(Main m) {
		super("unban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// /unBan <Name>
		if(sender.hasPermission("ccl.command.unban")) {
			if (args.length == 1) {
				String playername = args[0];
				UUID UUID = PlayerAPI.getUUIDFromDBName(playername);
				PlayerAPI api = new PlayerAPI(UUID);
				
				if (UUID == null) {
					sender.sendMessage(new TextComponent("§7[§4Ban§7] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
					return;
				}
				
				if (api.GetBan() == 0) {
					sender.sendMessage(new TextComponent("§7[§4Ban§7] " + api.getTabPrefix() + " §eist §enicht §egebannt!"));
				} else {
					if (api.GetNoUnBan() == 1) {
						sender.sendMessage(new TextComponent("§7[§4Ban§7] §eError §e400 §eBad §eRequest"));
					} else {
						api.unban();
						sender.sendMessage(new TextComponent("§7[§4Ban§7] §eDu §ehast "+ new PlayerAPI(UUID).getTabPrefix() +" §eentbannt!"));
						String IP = api.getIP();
						if (IP != null) {
							if (Blacklist.ips.containsKey(IP)) {
								if (Blacklist.ips.get(IP) == "IP-Ban (Hacking)") {
									Blacklist.ips.remove(IP);
								}
							}
						}
					}
				}
			} else {
				sender.sendMessage(new TextComponent("§c/unBan §c<Name>"));
			}
		} else {
			sender.sendMessage(new TextComponent("§cDu §chast §chierzu §ckeine §cBerechtigung!"));
		}
	}

}
