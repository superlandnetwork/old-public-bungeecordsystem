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

package de.superlandnetwork.bungeecord.system;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import de.superlandnetwork.bungeecord.system.API.Blacklist;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import de.superlandnetwork.bungeecord.system.Commands.AutoNick;
import de.superlandnetwork.bungeecord.system.Commands.Ban;
import de.superlandnetwork.bungeecord.system.Commands.Check;
import de.superlandnetwork.bungeecord.system.Commands.Friend;
import de.superlandnetwork.bungeecord.system.Commands.Jumpto;
import de.superlandnetwork.bungeecord.system.Commands.MSG;
import de.superlandnetwork.bungeecord.system.Commands.Money;
import de.superlandnetwork.bungeecord.system.Commands.Mute;
import de.superlandnetwork.bungeecord.system.Commands.Party;
import de.superlandnetwork.bungeecord.system.Commands.Perm;
import de.superlandnetwork.bungeecord.system.Commands.Ping;
import de.superlandnetwork.bungeecord.system.Commands.Report;
import de.superlandnetwork.bungeecord.system.Commands.TeamChat;
import de.superlandnetwork.bungeecord.system.Commands.TempBan;
import de.superlandnetwork.bungeecord.system.Commands.VerifyCommand;
import de.superlandnetwork.bungeecord.system.Commands.Wartung;
import de.superlandnetwork.bungeecord.system.Commands.eco;
import de.superlandnetwork.bungeecord.system.Commands.unBan;
import de.superlandnetwork.bungeecord.system.Commands.unBanIP;
import de.superlandnetwork.bungeecord.system.Commands.unMute;
import de.superlandnetwork.bungeecord.system.Listener.JoinListener;
import de.superlandnetwork.bungeecord.system.Listener.MOTD;
import de.superlandnetwork.bungeecord.system.Listener.MuteListener;
import de.superlandnetwork.bungeecord.system.Utils.PartyManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class Main extends Plugin {

	public static String System_Prefix = "§7[§aSystem§7]§f ";
	public static String Server_Prefix = "§7[§bServer§7]§f ";
	
	public PartyManager PartyManager;
	public static Main instance;
	
	public static HashMap<UUID, Long> onlineTime = new HashMap<>();
	
	//MySQL
	public static String MySQL_Host = "localhost";
	public static String MySQL_Username = "Minecraft";
	public static String MySQL_Database = "Minecraft";
	public static String MySQL_Password = "n85n4be8AvNwsAg6";
	public static String MySQL_Port = "3306";
	
	public static String permVersion = "1.2";
	public ScheduledTask WartungTimmer;
	public boolean isWartungTimmerRunning = false;
	public int WartungTimerCD = 300;
	
	@Override
	public void onEnable(){
		instance = this;
		PartyManager = new PartyManager();
		PluginManager bgip = getProxy().getPluginManager();
		getProxy().getConsole().sendMessage(new TextComponent(System_Prefix + "§aAktiviert!"));
		Blacklist.loadIpBlacklist();
		MOTD.setVersions();
		//Commands
		bgip.registerCommand(this, new Ping(this));//Ping
		bgip.registerCommand(this, new MSG(this));//Msg
		bgip.registerCommand(this, new TeamChat(this));//TeamChat
		bgip.registerCommand(this, new Money(this));//Money
		bgip.registerCommand(this, new VerifyCommand(this));//Ts3 Linker
		bgip.registerCommand(this, new Wartung(this));//Wartungs System
		bgip.registerCommand(this, new Report(this));//Report System
		bgip.registerCommand(this, new Jumpto(this));//Report System
		bgip.registerCommand(this, new AutoNick(this));//Nick System
		bgip.registerCommand(this, new Party(this));//Party System
		bgip.registerCommand(this, new Friend(this));//Freunde System
		//Spieler Verwalungs Commands
		bgip.registerCommand(this, new Ban(this));//Ban
		bgip.registerCommand(this, new TempBan(this));//Ban
		bgip.registerCommand(this, new unBan(this));//Ban
		bgip.registerCommand(this, new unBanIP(this));//Ban
		bgip.registerCommand(this, new Mute(this));//Mute
		bgip.registerCommand(this, new unMute(this));//Mute
		bgip.registerCommand(this, new Check(this));//Check
		bgip.registerCommand(this, new eco(this));//Coins & Money System
		bgip.registerCommand(this, new Perm(this));//Permission System
		//Listener
		bgip.registerListener(this, new MOTD());//MOTD
		bgip.registerListener(this, new JoinListener());//Join & Login
		bgip.registerListener(this, new MuteListener());//Chat
		bgip.registerListener(this, PartyManager);//Party
		MySQL.connect();
		startTimer();
		startUpdateTimer();
	}
	
	private void startUpdateTimer() {
		ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
			
			@Override
			public void run() {
				for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
					if(onlineTime.containsKey(players.getUniqueId())){
						PlayerAPI api = new PlayerAPI(players.getUniqueId());
						api.updateOnlineTime(onlineTime.get(players.getUniqueId()));
						if (api.IsPlayerInGroup(2)) {
							if (api.getRankTime() != -1) {
								if (api.getRemainingRankTime() == "ERROR") {
									api.SetPlayerGroup(1);
									players.sendMessage(new TextComponent("§7[§6System§7] §eDein §ePremium-Rang §eist §eabgelaufen."));
								}
							}
						}
					}
				}
			}
		}, 10,10, TimeUnit.MINUTES);
	}

	private void startTimer() {
		ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
			
			@Override
			public void run() {
				for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
					if (onlineTime.containsKey(players.getUniqueId())) {
						Long i = onlineTime.get(players.getUniqueId());
						i++;
						onlineTime.replace(players.getUniqueId(), i);
					} else {
						Long i = new PlayerAPI(players.getUniqueId()).getOnlineTime();
						onlineTime.put(players.getUniqueId(), i);
					}
				}
			}
		}, 1, 1, TimeUnit.MINUTES);
	}
	
	public void startWartungTimer() {
		WartungTimmer = ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
			
			@Override
			public void run() {
				if (WartungTimerCD == 300)
					ProxyServer.getInstance().broadcast(new TextComponent("§7[§6System§7] §4Wartungsarbeiten §4in §e5 §eminuten§4!"));
				if (WartungTimerCD == 240)
					ProxyServer.getInstance().broadcast(new TextComponent("§7[§6System§7] §4Wartungsarbeiten §4in §e4 §eminuten§4!"));
				if (WartungTimerCD == 180)
					ProxyServer.getInstance().broadcast(new TextComponent("§7[§6System§7] §4Wartungsarbeiten §4in §e3 §eminuten§4!"));
				if (WartungTimerCD == 120)
					ProxyServer.getInstance().broadcast(new TextComponent("§7[§6System§7] §4Wartungsarbeiten §4in §e2 §eminuten§4!"));
				if (WartungTimerCD == 60)
					ProxyServer.getInstance().broadcast(new TextComponent("§7[§6System§7] §4Wartungsarbeiten §4in §e60 §esekunden§4!"));
				if (WartungTimerCD == 30 || WartungTimerCD == 15 || WartungTimerCD == 10 || WartungTimerCD < 10)
					ProxyServer.getInstance().broadcast(new TextComponent("§7[§6System§7] §4Wartungsarbeiten §4in §e" + WartungTimerCD + " §esekunden§4!"));
				if (WartungTimerCD == 1)
					ProxyServer.getInstance().broadcast(new TextComponent("§7[§6System§7] §4Wartungsarbeiten §4in §e1 §esekunde§4!"));
				if (WartungTimerCD == 0) {
					ProxyServer.getInstance().broadcast(new TextComponent("§7[§6System§7] §4Wartungsarbeiten!"));
					for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
						PlayerAPI api = new PlayerAPI(all.getUniqueId());
						if(api.GetWartungJoin() != 1)
							all.disconnect(new TextComponent("§4Das SuperLandNetwork.de Server Netzwerk wird nun Gewartet!"));
					}
					isWartungTimmerRunning = false;
					WartungTimerCD = 301;
					ProxyServer.getInstance().getScheduler().cancel(WartungTimmer);
				}
				WartungTimerCD--;
			}
		}, 1, 1, TimeUnit.SECONDS);
	}

	@Override
	public void onDisable(){
		getProxy().getConsole().sendMessage(new TextComponent(System_Prefix + "§cDeaktiviert!"));
		MySQL.close();
	}
	
	/**
	 * @return the instance
	 */
	public static Main getInstance() {
		return instance;
	}
}
