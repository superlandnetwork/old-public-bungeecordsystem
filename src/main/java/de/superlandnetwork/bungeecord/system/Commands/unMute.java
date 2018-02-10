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
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class unMute extends Command {

	public unMute(Main m) {
		super("unmute");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender.hasPermission("ccl.command.mute")){
			if(args.length == 1){
				String playername = args[0];
				UUID UUID;
				if(ProxyServer.getInstance().getPlayer(playername) != null){
					UUID = ProxyServer.getInstance().getPlayer(playername).getUniqueId();
				}else{
					UUID = PlayerAPI.getUUIDFromDBName(playername);
				}
				
				if(UUID == null) {
					sender.sendMessage(new TextComponent("§8[§4Mute§8] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
					return;
				}
				
				if(new PlayerAPI(UUID).isMuted()) {
					new PlayerAPI(UUID).UpdateMute(0);
					sender.sendMessage(new TextComponent("§8[§4Mute§8] §eDu §ehast " + new PlayerAPI(UUID).getPrefix() + " §eentmutet!"));
				} else {
					sender.sendMessage(new TextComponent("§8[§4Mute§8] " + new PlayerAPI(UUID).getPrefix() + " §eist §enicht §egemutet!"));
				}
			}else{
				sender.sendMessage(new TextComponent("§c/unmute §c<Name>"));
			}
		} else {
			sender.sendMessage(new TextComponent("§cDu §chast §chierzu §ckeine §cBerechtigung!"));
		}
	}

}
