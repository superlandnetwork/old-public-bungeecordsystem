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
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeamChat extends Command {

	public TeamChat(Main m) {
		super("TeamChat", "", "tc");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer))
			return;
		
		ProxiedPlayer p = (ProxiedPlayer)sender;
		if(!p.hasPermission("ccl.Team")) {
			sender.sendMessage(new TextComponent("§7[§6System§7] §cDu §chast §chierzu §ckeine §cRechte!"));
			return;
		}
		
		String Msg = "";
		
		for(int i = 0;i<args.length;i++){
			if(i >= 1){
				Msg = Msg + " §e" + args[i];
			} else {
				Msg = args[i];
			}
		}
		
		for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
			if(players.hasPermission("ccl.Team")){
				players.sendMessage(new TextComponent("§7[§5§lTeam§7] " + new PlayerAPI(p.getUniqueId()).getPrefix() + " §7» §e" + Msg));
			}
		}
	}

}
