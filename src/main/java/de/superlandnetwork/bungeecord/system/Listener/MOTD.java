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

import java.util.ArrayList;
import java.util.UUID;

import de.superlandnetwork.bungeecord.system.API.API;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MOTD implements Listener {
	
	public static ArrayList<Integer> versions = new ArrayList<>();
	
	public static void setVersions() {
		versions.add(340); // 1.12
		versions.add(338); // 1.12.1
		versions.add(335); // 1.12
	}

	
	@EventHandler(priority=64)
	public void onPing(ProxyPingEvent e){
		ServerPing conn = e.getResponse();
		ServerPing.Players players = conn.getPlayers();
		API api = new API();
		if (api.isWartung()) {
			//Wartung
			conn.setVersion(new Protocol("Wartungsarbeiten", 2));
			conn.setDescriptionComponent(new TextComponent("§cSuperLandNetwork.de §8» §eServer Netzwerk §8[§c1.12§8]  §4WARTUNGSARBEITEN!"));
			players.setSample(new ServerPing.PlayerInfo[] {
				new ServerPing.PlayerInfo("§a===========================", UUID.randomUUID()),
				new ServerPing.PlayerInfo("§2SuperLandNetwork", UUID.randomUUID()),
				new ServerPing.PlayerInfo("§4Wartungsarbeiten", UUID.randomUUID()),
				new ServerPing.PlayerInfo("§bwww.SuperLandNetwork.de", UUID.randomUUID()),
				new ServerPing.PlayerInfo("§a===========================", UUID.randomUUID())});
			players.setMax(api.getMaxSlots());
			conn.setPlayers(players);
		} else {
			if (!versions.contains(e.getConnection().getVersion())) {
				//Nicht 1.12
				conn.setVersion(new Protocol("SuperLandNetwork 1.12", 2));
			}
			conn.setDescriptionComponent(new TextComponent(api.getMOTD()));
			players.setSample(new ServerPing.PlayerInfo[] {
				new ServerPing.PlayerInfo("§a===========================", UUID.randomUUID()),
				new ServerPing.PlayerInfo("§2SuperLandNetwork", UUID.randomUUID()),
				new ServerPing.PlayerInfo("§4Closed Alpha", UUID.randomUUID()),
				new ServerPing.PlayerInfo("§bwww.SuperLandNetwork.de", UUID.randomUUID()),
				new ServerPing.PlayerInfo("§a===========================", UUID.randomUUID())});
			players.setMax(api.getMaxSlots());
			conn.setPlayers(players);
		}
		
		e.setResponse(conn);
	}
	
}
