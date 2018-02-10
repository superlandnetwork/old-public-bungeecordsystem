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

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party {

	ProxiedPlayer Owner;
	ArrayList<ProxiedPlayer> Spieler;
	
	/**
	 * @param Owner
	 */
	public Party(ProxiedPlayer Owner) {
		setOwner(Owner);
		Main.getInstance().PartyManager.getPartys().add(this);
		Spieler = new ArrayList<ProxiedPlayer>();
		Spieler.add(Owner);
		Main.getInstance().PartyManager.getPartyPlayer().put(Owner, this);
	}
	
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(ProxiedPlayer owner) {
		Owner = owner;
	}
	
	/**
	 * @return the owner
	 */
	public ProxiedPlayer getOwner() {
		return Owner;
	}
	
	/**
	 * @return the spieler
	 */
	public ArrayList<ProxiedPlayer> getSpieler() {
		return Spieler;
	}
	
	/**
	 * @param Message
	 * @param Player
	 */
	public void sayToParty(String Message) {
		for(ProxiedPlayer all : Spieler) {
			all.sendMessage(new TextComponent("§7[§5Party§7] §f" + Message));
		}
	}
	
	public void delte() {
		Main.getInstance().PartyManager.getPartys().remove(this);
		sayToParty("§cDie §cParty §cwurde §caufgelöst!");
		for(ProxiedPlayer all : Spieler) {
			Main.getInstance().PartyManager.getPartyPlayer().remove(all);
		}
		Spieler.clear();
		setOwner(null);
	}
	
	/**
	 * @param Player
	 */
	public void addPlayer(ProxiedPlayer Player) {
		PlayerAPI api = new PlayerAPI(Player.getUniqueId());
		Spieler.add(Player);
		sayToParty(api.getTabPrefix() + " §eist §eder §eParty §ebeigetreten!");
		Main.getInstance().PartyManager.getPartyPlayer().put(Player, this);
	}
	
	/**
	 * @param Player
	 */
	public void removePlayer(ProxiedPlayer Player) {
		PlayerAPI api = new PlayerAPI(Player.getUniqueId());
		sayToParty(api.getTabPrefix() + " §ehat §edie §eParty §everlassen!");
		Spieler.remove(Player);
		Main.getInstance().PartyManager.getPartyPlayer().remove(Player);
		if(getSpieler().size() == 1 && getSpieler().size() == 0) {
			delte();
		}
		if(getSpieler().size() > 1) {
			if(isOwner(Player)) {
				setOwner(getSpieler().get(0));
				PlayerAPI api2 = new PlayerAPI(getOwner().getUniqueId());
				sayToParty(api2.getTabPrefix() + " §eist §enun §eder §eLeiter §eder §eParty!");
			}
		}
	}
	
	/**
	 * @param Player
	 * @return 
	 */
	public boolean isOwner(ProxiedPlayer Player) {
		if(this.Owner == Player)
			return true;
		return false;
	}
	
	/**
	 * @param Player
	 */
	public void invitePlayer(ProxiedPlayer Player) {
		PlayerAPI api = new PlayerAPI(getOwner().getUniqueId());
		Player.sendMessage(new TextComponent("§7[§5Party§7] " + api.getTabPrefix() + " §ehat §edich §ein §eseine §eParty §eeingeladen"));
		TextComponent msg = new TextComponent("");
		TextComponent accept = new TextComponent("§a[Annehmen]");
		accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/friend accept " + getOwner().getName()));
		TextComponent deny = new TextComponent("§c[Ablehnen]");
		deny.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/party deny " + getOwner().getName()));
		TextComponent txt = new TextComponent(" §e| ");
		
		msg.addExtra(accept);
		msg.addExtra(txt);
		msg.addExtra(deny);
		Player.sendMessage(msg);

		HashMap<ProxiedPlayer, Party> map = new HashMap<ProxiedPlayer, Party>();
		map.put(getOwner(), this);
		Main.getInstance().PartyManager.getInvites().put(Player, map);
	}
	
	public int getMaxPlayers() {
		PlayerAPI api = new PlayerAPI(getOwner().getUniqueId());
		if(api.IsPlayerInGroup(1))
			return 5;
		if(api.IsPlayerInGroup(2) || api.IsPlayerInGroup(3) || api.IsPlayerInGroup(4))
			return 10;
		return -1;
	}
	
}
