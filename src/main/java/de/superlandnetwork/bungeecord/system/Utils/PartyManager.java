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

package de.superlandnetwork.bungeecord.system.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyManager implements Listener {

	private ArrayList<Party> Partys;
	private HashMap<ProxiedPlayer, Party> PartyPlayer;
	private HashMap<ProxiedPlayer, HashMap<ProxiedPlayer, Party>> invites;
	
	public PartyManager() {
		Partys = new ArrayList<Party>();
		PartyPlayer = new HashMap<ProxiedPlayer, Party>();
		invites = new HashMap<ProxiedPlayer, HashMap<ProxiedPlayer, Party>>();
	}
	
	/**
	 * @return the partys
	 */
	public ArrayList<Party> getPartys() {
		return Partys;
	}
	
	/**
	 * @return the partyPlayer
	 */
	public HashMap<ProxiedPlayer, Party> getPartyPlayer() {
		return PartyPlayer;
	}
	
	/**
	 * @return the invites
	 */
	public HashMap<ProxiedPlayer, HashMap<ProxiedPlayer, Party>> getInvites() {
		return invites;
	}
	
	/**
	 * @param Player
	 * @return true or false
	 */
	public boolean isInParty(ProxiedPlayer Player) {
		return getPartyPlayer().containsKey(Player);
	}
	
	/**
	 * @param Player
	 * @return true or false
	 */
	public boolean isPartyLeader(ProxiedPlayer Player) {
		if(isInParty(Player)) {
			if(getPartyPlayer().get(Player).isOwner(Player)) {
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void Switch(ServerSwitchEvent e) {
		ProxiedPlayer Player = e.getPlayer();
		if(isPartyLeader(Player)) {
			for(ProxiedPlayer all : PartyPlayer.get(Player).getSpieler()) {
				if(all != Player)
					all.connect(Player.getServer().getInfo());
			}
			PartyPlayer.get(Player).sayToParty("§eDie §eParty §eist §eauf §eden §eServer §6" + Player.getServer().getInfo().getName() + " §eGejoint");
		}
	}
	
	@EventHandler
	public void Disconnect(PlayerDisconnectEvent e) {
		ProxiedPlayer Player = e.getPlayer();
		if(isInParty(Player)) {
			getPartyPlayer().get(Player).removePlayer(Player);
		}
	}
	
}
