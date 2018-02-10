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

import java.util.UUID;

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.API.API;
import de.superlandnetwork.bungeecord.system.API.Blacklist;
import de.superlandnetwork.bungeecord.system.API.IPLog;
import de.superlandnetwork.bungeecord.system.API.PermAPI;
import de.superlandnetwork.bungeecord.system.API.PermEnum;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

	@EventHandler(priority=64)
	public void Login(LoginEvent e) {
		e.registerIntent(Main.getInstance());
		UUID UUID = e.getConnection().getUniqueId();
		PlayerAPI api = new PlayerAPI(UUID);
		IPLog logAPI = new IPLog(UUID, e.getConnection().getAddress().getAddress().toString());
		logAPI.LogIP();
		api.updateIP(e.getConnection().getAddress().getAddress().toString());
		if(api.GetBan() == 1) {
			if(api.getReamaingingTime() == "ERROR") {
				api.unban();
			} else {
				e.setCancelReason(new TextComponent("§cDu wurdest vom SuperLandNetwork.de Netzwerk gebannt\n"
						+ "\n"
						+ "§7Grund: §c"+ api.getBanReason() + "\n"
						+ "\n"
						+ "§7Verbleibende Zeit: §c"+ api.getReamaingingTime() + "\n"
						+ "\n"
						+ "§7Wenn du einen Entbannungsantrag stellen möchtest,\n"
						+ "§7kannst du in unser Forum gehen\n"
						+ "§eForum.SuperLandNetwork.de"));
				e.setCancelled(true);
			}
		} else if(new API().isWartung()) {
			if(api.GetWartungJoin() != 1) {
				e.setCancelReason(new TextComponent("§4Das SuperLandNetwork.de Server Netzwerk wird gerade Gewartet!"));
				e.setCancelled(true);
			}
		} else if (api.isOnline()) {
			e.setCancelReason(new TextComponent("§cDu §cbist §cbereits §cauf §cdem §cNetzwerk"));
			e.setCancelled(true);
		}
		e.completeIntent(Main.getInstance());
	}
	
	@EventHandler
	public void ipBlock(LoginEvent e) {
		e.registerIntent(Main.getInstance());
		String ip = e.getConnection().getAddress().getAddress().toString();
		if (Blacklist.ips.containsKey(ip)) {
			e.setCancelReason(new TextComponent("§cDu wurdest permanent von SuperLandNetwok.de IP gebannt \n" + 
					"\n" + 
					"§7Wenn du einen Entbannungsantrag stellen möchtest, \n" + 
					"§7kannst du in unser Fourm gehen \n" + 
					"§eForum.SuperLandnetwork.de"));
			e.setCancelled(true);
		} /*else {
			String[] s = ip.split(".");
			String d = s[0] +  "." + s[1] + "." + s[2] + ".*";
			if (Blacklist.ips.containsKey(d)) {
				e.setCancelReason(new TextComponent("§cDu wurdest permanent von SuperLandNetwok.de IP gebannt \n" + 
						"\n" + 
						"§7Wenn du einen Entbannungsantrag stellen möchtest, \n" + 
						"§7kannst du in unser Fourm gehen \n" + 
						"§eForum.SuperLandnetwork.de"));
				e.setCancelled(true);
			}
		}*/
		e.completeIntent(Main.getInstance());
	}
	
	@EventHandler
	public void PlayerLogin(PostLoginEvent e) {
		ProxiedPlayer p = e.getPlayer();
		PlayerAPI api = new PlayerAPI(p.getUniqueId());
		api.updateName();
		if(api.IsUserInDB()) {
			int Group_ID = api.getPlayerGroup();
			for(String Perms : PermAPI.getGroupPermisonsOrdet(Group_ID)) {
				p.setPermission(Perms, true);
			}
			if(api.IsPlayerInGroup(PermEnum.ADMINISTRATOR.getId()) || api.IsPlayerInGroup(PermEnum.ADMINISTRATORIN.getId())) {
				p.addGroups("admin", "team");
			}
			if(api.IsPlayerInGroup(PermEnum.BUILDER.getId()) || api.IsPlayerInGroup(PermEnum.BUILDERIN.getId())  || api.IsPlayerInGroup(PermEnum.SUPPORTER.getId()) || api.IsPlayerInGroup(PermEnum.SUPPORTERIN.getId()) || api.IsPlayerInGroup(PermEnum.MODERATOR.getId()) || api.IsPlayerInGroup(PermEnum.MODERATORIN.getId()) || api.IsPlayerInGroup(PermEnum.SRMODERATOR.getId()) || api.IsPlayerInGroup(PermEnum.SRMODERATORIN.getId()) || api.IsPlayerInGroup(PermEnum.DEVELOPER.getId()) || api.IsPlayerInGroup(PermEnum.DEVELOPERIN.getId())) {
				p.addGroups("team");
			}
		} else {
			api.SetPlayerGroup(1);
			int Group_ID = api.getPlayerGroup();
			for(String Perms : PermAPI.getGroupPermisonsOrdet(Group_ID)) {
				p.setPermission(Perms, true);
			}
		}
	}
	
	@EventHandler
	public void Leave(PlayerDisconnectEvent e) {
		if(Main.onlineTime.containsKey(e.getPlayer().getUniqueId())) {
			PlayerAPI api = new PlayerAPI(e.getPlayer().getUniqueId());
			api.updateOnlineTime(Main.onlineTime.get(e.getPlayer().getUniqueId()));
			Main.onlineTime.remove(e.getPlayer().getUniqueId());
		}
		
		PlayerAPI api = new PlayerAPI(e.getPlayer().getUniqueId());
		api.UpdateOnline(0);
		api.updaeLastOnline();
		if (!api.getFriendsUUID().isEmpty()) {
			for (UUID uuid : api.getFriendsUUID()) {
				if(!new PlayerAPI(uuid).isShowingNotify())
					continue;
				if (ProxyServer.getInstance().getPlayer(uuid) != null)
					ProxyServer.getInstance().getPlayer(uuid).sendMessage(new TextComponent("§7[§4Freunde§7] " + api.getTabPrefix() + " §eist §enun §cOffline"));
			}
		}
	}
	
	@EventHandler
	public void onJoin(PostLoginEvent e) {
		Title title = ProxyServer.getInstance().createTitle();
		title.title(new TextComponent("§6Willkomen"));
		title.subTitle(new TextComponent("§eAuf dem SuperLandNetwork.de Netzwerk"));
		title.send(e.getPlayer());
		
		PlayerAPI api = new PlayerAPI(e.getPlayer().getUniqueId());
		api.UpdateOnline(1);
		if (!api.getFriendRequests().isEmpty()) {
			int i = api.getFriendRequests().size();
			if (i == 1)
				e.getPlayer().sendMessage(new TextComponent("§7[§4Freunde§7] §aDu §ahast §anoch §e1 §aAnfrage §aoffen. §aAuflisten §amit §e/friend §erequests"));
			else
				e.getPlayer().sendMessage(new TextComponent("§7[§4Freunde§7] §aDu §ahast §anoch §e" + i + " §aAnfragen §aoffen. §aAuflisten §amit §e/friend §erequests"));	
		}
		
		if (!api.getFriendsUUID().isEmpty()) {
			for (UUID uuid : api.getFriendsUUID()) {
				if(!new PlayerAPI(uuid).isShowingNotify())
					continue;
				if (ProxyServer.getInstance().getPlayer(uuid) != null)
					ProxyServer.getInstance().getPlayer(uuid).sendMessage(new TextComponent("§7[§4Freunde§7] " + api.getTabPrefix() + " §eist §enun §aOnline"));
			}
		}
		
		if (api.IsPlayerInGroup(2)) {
			if (api.getRankTime() != -1) {
				if (api.getRemainingRankTime() == "ERROR") {
					api.SetPlayerGroup(1);
					api.setRankTime(-1);
					e.getPlayer().sendMessage(new TextComponent("§7[§6System§7] §eDein §ePremium-Rang §eist §eabgelaufen."));
				} else {
					e.getPlayer().sendMessage(new TextComponent("§7[§6System§7] §eDein §ePremium-Rang §eläuft §ein §6" + api.getRemainingRankTime() + " §eab."));
				}
			}
		}
	}
}
