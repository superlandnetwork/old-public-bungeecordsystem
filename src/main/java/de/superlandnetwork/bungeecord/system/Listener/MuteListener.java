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

package de.superlandnetwork.bungeecord.system.Listener;

import de.superlandnetwork.bungeecord.system.API.ChatLog;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MuteListener implements Listener {

	@EventHandler
	public void onChat(ChatEvent e){
		if(e.getSender() instanceof ProxiedPlayer) {
			ProxiedPlayer p =(ProxiedPlayer)e.getSender();
			String string = e.getMessage().replaceAll("ä", "&auml;").replaceAll("ö", "&ouml;").replaceAll("ü", "&ouml;").replaceAll("ß", "ss");
			new ChatLog(p.getUniqueId(), p.getServer().getInfo().getName()).Log(string);
			if (e.getMessage().equalsIgnoreCase("/help")) {
				p.sendMessage(new TextComponent("Help-Command"));//TODO
				e.setCancelled(true);
				return;
			}
			
			if (e.getMessage().equalsIgnoreCase("/pl") || e.getMessage().equalsIgnoreCase("/plugins")  || e.getMessage().equalsIgnoreCase("/version") || e.getMessage().equalsIgnoreCase("/ver") || e.getMessage().equalsIgnoreCase("/about") || e.getMessage().equalsIgnoreCase("/?")) {
				if (p.hasPermission("ccl.command.plugin"))
					return;
				else {
					e.setCancelled(true);
					return;
				}
			}
			
			if (e.getMessage().equalsIgnoreCase("/me")) {
				if (p.hasPermission("ccl.command.me"))
					return;
				else {
					e.setCancelled(true);
					return;
				}
			}
			
			if(e.getMessage().equalsIgnoreCase("/hub") || e.getMessage().equalsIgnoreCase("/lobby") || e.getMessage().equalsIgnoreCase("/l")) {
				if(isOnLobby(p)) {
					return;
				} else {
					p.connect(ProxyServer.getInstance().getServerInfo(getRandomLobby()));
					p.sendMessage(new TextComponent("§7[§bServer§7]§c Du bist nun auf dem Lobby Server!"));
					e.setCancelled(true);
					return;
				}
			}
			
			if(e.getMessage().startsWith("/hub") || e.getMessage().startsWith("/lobby") || e.getMessage().startsWith("/l")) {
				String[] s = e.getMessage().split(" ");
				if(s[0].equalsIgnoreCase("/hub") || s[0].equalsIgnoreCase("/lobby") || s[0].equalsIgnoreCase("/l")) {
					if(isOnLobby(p)) {
						return;
					} else {
						p.connect(ProxyServer.getInstance().getServerInfo(getRandomLobby()));
						p.sendMessage(new TextComponent("§7[§bServer§7]§c Du bist nun auf dem Lobby Server!"));
						e.setCancelled(true);
						return;
					}
				}
			}
			
			if(e.getMessage().startsWith("/")) {
				return;
			}
			
			if(new PlayerAPI(p.getUniqueId()).isMuted()) {
				p.sendMessage(new TextComponent("§cDu §cwurdest §cgemutet"));
				p.sendMessage(new TextComponent("§cGrund: " + new PlayerAPI(p.getUniqueId()).getMuteReason()));
				p.sendMessage(new TextComponent("§7Verbleibende §7Zeit: §c" + new PlayerAPI(p.getUniqueId()).getReamaingingMuteTime()));
				e.setCancelled(true);
			}
		}
	}

	/**
	 * @param p
	 * @return
	 */
	private boolean isOnLobby(ProxiedPlayer p) {
		for(String server : ProxyServer.getInstance().getServers().keySet()) {
			ServerInfo info = ProxyServer.getInstance().getServers().get(server);
			if(info.getPlayers().isEmpty())
				continue;
			if(!info.getPlayers().contains(p))
				continue;
			if(info.getName().contains("Lobby"))
				return true;
			else
				return false;
		}
		return false;
	}

	private String getRandomLobby() {
		return "Lobby1";
	}
	
}
