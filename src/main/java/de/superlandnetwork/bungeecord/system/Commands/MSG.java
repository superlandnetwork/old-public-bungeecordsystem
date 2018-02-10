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
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MSG extends Command {

	public MSG(Main m) {
		super("msg");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer))
			return;
		if (args.length > 1) {
			if (new PlayerAPI(((ProxiedPlayer) sender).getUniqueId()).isMuted()) {
				sender.sendMessage(new TextComponent("§cDu §cwurdest §cgemutet"));
				sender.sendMessage(new TextComponent("§cGrund: " + new PlayerAPI(((ProxiedPlayer) sender).getUniqueId()).getMuteReason()));
				sender.sendMessage(new TextComponent("§7Verbleibende §7Zeit: §c" + new PlayerAPI(((ProxiedPlayer) sender).getUniqueId()).getReamaingingMuteTime()));
				return;	
			}
			
			String name = args[0];
			String Msg = "";
			
			UUID uuid = null;
			if(ProxyServer.getInstance().getPlayer(name) != null)
				uuid = ProxyServer.getInstance().getPlayer(name).getUniqueId();
			else
				uuid = PlayerAPI.getUUIDFromDBName(name);
			
			if (uuid == null) {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
				return;
			}
			
			PlayerAPI api = new PlayerAPI(((ProxiedPlayer) sender).getUniqueId());
			
			if(api.IsPlayerInGroup(1) || api.IsPlayerInGroup(2) || api.IsPlayerInGroup(3)) {
			
				if(!(new PlayerAPI(uuid).isAllowMSG())) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §ckannst §cdem §cSpieler §ckeine §cPrivate §cNachricht §csenden"));
					return;
				}
			
				if(new PlayerAPI(uuid).isAllowOnlyFriendMSG()) {
					if(!(new PlayerAPI(((ProxiedPlayer) sender).getUniqueId()).getFriendsUUID().contains(uuid))) {
						sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §cbist §cnicht §cmit " + new PlayerAPI(uuid).getTabPrefix() + " §cbefreundet"));
						return;
					}
				}
			}
			
			for (int i = 1;i<args.length;i++) {
				if (i > 1) {
					Msg = Msg + " §e" + args[i];
				} else {
					Msg = args[i];
				}
			}
			if (ProxyServer.getInstance().getPlayer(name) != null) {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §7Du §7» " + new PlayerAPI(uuid).getTabPrefix() + "§7: §e" + Msg));
				ProxyServer.getInstance().getPlayer(name).sendMessage(new TextComponent("§7[§4Freunde§7] " + new PlayerAPI(((ProxiedPlayer) sender).getUniqueId()).getTabPrefix() + " §7» §7Dir§7: §e" + Msg));
			} else {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDer §cSpieler §cist §cnicht §conline!"));
			}
		} else {
			sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cBenutzte: §c/msg §c<Spieler> §c<Nachricht>"));
		}
	}

}
