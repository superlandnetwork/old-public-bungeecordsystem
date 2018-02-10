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
import de.superlandnetwork.bungeecord.system.Utils.CanJoinServer;
import de.superlandnetwork.bungeecord.system.Utils.ServerType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Friend extends Command {

	/**
	 * @param name
	 */
	public Friend(Main m) {
		super("friend","", "f", "freunde");
	}

	@Override 
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer))
			return;
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if(args.length == 0) {
			sender.sendMessage(new TextComponent("§7[§4Freunde§7] §6Freundschaftsverwaltung"));
			sender.sendMessage(new TextComponent("§e/friend add <Name> §7Fügt §7einen §7neuen §7Freund §7hinzuh"));
			sender.sendMessage(new TextComponent("§e/friend list §7Zeigt §7eine §7Liste §7aller §7Freunde"));
			sender.sendMessage(new TextComponent("§e/friend remove <Name> §7Entfernt §7einen §7Freund"));
			sender.sendMessage(new TextComponent("§e/friend jump <Name> §7Auf §7des §7Freundes §7Server §7springen"));
			sender.sendMessage(new TextComponent("§e/friend clear §7Leert §7die §7Freundesliste"));
			sender.sendMessage(new TextComponent("§e/friend requests §7Zeigt §7alle §7Anfragen §7an"));
			sender.sendMessage(new TextComponent("§e/friend accept <Name> §7Nimmt §7eine §7Anfrage §7an"));
			sender.sendMessage(new TextComponent("§e/friend acceptall §7Nimmt §7alle §7Freundschaftsanfragen §7an"));
			sender.sendMessage(new TextComponent("§e/friend deny <Name> §7Lehnt §7eine §7Anfrage §7ab"));
			sender.sendMessage(new TextComponent("§e/friend denyall §7Lehnt §7alle §7Freundschaftsanfragen §7ab"));
			sender.sendMessage(new TextComponent("§e/friend toggle §7Anfragen §7erlauben §7& §7verbieten"));
			sender.sendMessage(new TextComponent("§e/friend togglenotify §7Online/Offline §7Nachrichten §7erlauben §7& §7verbieten"));
			sender.sendMessage(new TextComponent("§e/friend togglemessage §7Private §7Nachrichten §7erlauben §7& §7verbieten"));
			sender.sendMessage(new TextComponent("§e/friend togglejump §7Erlaube §7& §7verbiete §7Spielern §7zu §7dir §7zu §7springen"));
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("togglejump")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.isAllowJump()) {
					api.setAllowJump(false);
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eFreunde §ekönnen §enun §enicht §emehr §ezu §edir §espringen"));
				} else {
					api.setAllowJump(true);
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eFreunde §ekönnen §enun §ewieder §ezu §edir §espringen"));
				}
			} else if(args[0].equalsIgnoreCase("togglemessage")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.isAllowMSG()) {
					api.setAllowMSG(false);
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eFreunde §ekönnen §edir §enun §ekeine §ePrivaten §eNachrichten §emehr §esenden"));
				} else {
					api.setAllowMSG(true);
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eFreunde §ekönnen §edir §enun §ewieder §ePrivaten §eNachrichten §esenden"));
				}
			} else if(args[0].equalsIgnoreCase("togglenotify")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.isShowingNotify()) {
					api.setShowingNotifys(false);
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §eerhälst §enun §ekeine §eOnline/Offline §eNachrichten §emehr"));
				} else {
					api.setShowingNotifys(true);
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §eerhälst §enun §ewieder §eOnline/Offline §eNachrichten"));
				}
			} else if(args[0].equalsIgnoreCase("toggle")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.isAcceptingFriends()) {
					api.setAcceptingFriends(false);
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §eerhälst §enun §ekeine §eFreundschaftsanfragen §emehr"));
				} else {
					api.setAcceptingFriends(true);
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDir §ekönnen §enun §ewieder §eFreundschaftsanfragen §egeschickt §ewerden"));
				}
			} else if(args[0].equalsIgnoreCase("denyall")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.getFriendRequests().isEmpty()) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §chast §cgerade §ckeine §cFreundschaftsanfragen"));
					return;
				}
				for(UUID s : api.getFriendRequests()) {
					api.deleteFreindReuest(s);
					PlayerAPI api2 = new PlayerAPI(s);
					if (ProxyServer.getInstance().getPlayer(s) != null)
						ProxyServer.getInstance().getPlayer(s).sendMessage(new TextComponent("§7[§4Freunde§7] " + api.getTabPrefix() + " §ehat §edeine §eFreundschaftsanfage §eabgelehnt"));
					p.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ehast §edie §eFreundschaftsanfrage §evon " + api2.getTabPrefix() + " §eabgelehnt"));
				}
			} else if(args[0].equalsIgnoreCase("acceptall")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.getFriendRequests().isEmpty()) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §chast §cgerade §ckeine §cFreundschaftsanfragen"));
					return;
				}
				for(UUID s : api.getFriendRequests()) {
					
					if(api.getFriendsUUID().size() >= getMax(p.getUniqueId())) {
						sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §chast §cdein §cFreundelimit §cerreicht"));
						return;
					}
					
					if(new PlayerAPI(s).getFriendsUUID().size() >= getMax(s)) {
						sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDer §cSpieler §chat §csein §cFreundelimit §cerreicht"));
						continue;
					}
					
					api.addFriend(s);
					api.deleteFreindReuest(s);
					PlayerAPI api2 = new PlayerAPI(s);
					if(ProxyServer.getInstance().getPlayer(s) != null)
						ProxyServer.getInstance().getPlayer(s).sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ebist §enun §emit " + api.getTabPrefix() + " §ebefreundet"));
					p.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ebist §enun §emit " + api2.getTabPrefix() + " §ebefreundet"));
				}
			} else if(args[0].equalsIgnoreCase("requests")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.getFriendRequests().isEmpty()) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §chast §cgerade §ckeine §cFreundschaftsanfragen"));
					return;
				}
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §6Freundschaftsanfragen"));
				for(UUID s : api.getFriendRequests()) {
					PlayerAPI api2 = new PlayerAPI(s);
					sender.sendMessage(new TextComponent("§6- " + api2.getTabPrefix()));
				}
			} else if(args[0].equalsIgnoreCase("clear")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.getFriendsUUID().isEmpty()) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDeine §cFreundesliste §cist §cleer"));
					return;
				}
				for(UUID s : api.getFriendsUUID()) {
					if(ProxyServer.getInstance().getPlayer(s) != null) {
						ProxyServer.getInstance().getPlayer(s).sendMessage(new TextComponent("§7[§4Freunde§7] " + api.getTabPrefix() + " §ehat §edich §evon §eseiner §eFreundesliste §eentfernt"));
					}
					api.deleteFreind(s);
				}
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ehast §edeine §eFreundesliste §egecleart"));
			} else if(args[0].equalsIgnoreCase("list")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				if(api.getFriendsUUID().isEmpty()) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDeine §cFreundesliste §cist §cleer"));
					return;
				}
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §6Freundschaftsliste"));
				for(UUID s : api.getFriendsUUIDOrdet()) {
					PlayerAPI api2 = new PlayerAPI(s);
					if(ProxyServer.getInstance().getPlayer(s) != null)
						sender.sendMessage(new TextComponent("§e- §r" + api2.getTabPrefix() + " §7(§aOnline§7)"));
					else
						sender.sendMessage(new TextComponent("§e- §r" + api2.getTabPrefix() + " §7(§cOffline§7)"));
				}
			} else if(args[0].equalsIgnoreCase("deny")) {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §c/friend deny <Name>"));
			} else if(args[0].equalsIgnoreCase("accept")) {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §c/friend accept <Name>"));
			} else if(args[0].equalsIgnoreCase("jump")) {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §c/friend jump <Name>"));
			} else if(args[0].equalsIgnoreCase("remove")) {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §c/friend remove <Name>"));
			} else {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDieser §cBefehl §cexistiert §cnicht"));
			}
		} else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("deny")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				String name = args[1];
				UUID uuid = null;
				if (ProxyServer.getInstance().getPlayer(name) != null)
					uuid = ProxyServer.getInstance().getPlayer(name).getUniqueId();
				else
					uuid = PlayerAPI.getUUIDFromDBName(name);
				
				if (uuid == null) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDieser §eSpieler §ewar §enoch §enicht §eauf §edem §eNetzwerk!"));
					return;
				}
				
				if (api.getFriendsUUID().contains(uuid)) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §cbist §cbereits §cmit §cdiesem §cSpieler §cbefreundet"));
					return;
				}
				
				if (api.getFriendRequests().contains(uuid)) {
					api.deleteFreindReuest(uuid);
					PlayerAPI api2 = new PlayerAPI(uuid);
					if (ProxyServer.getInstance().getPlayer(uuid) != null)
						ProxyServer.getInstance().getPlayer(uuid).sendMessage(new TextComponent("§7[§4Freunde§7] " + api.getTabPrefix() + " §ehat §edeine §eFreundschaftsanfage §eabgelehnt"));
					p.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ehast §edie §eFreundschaftsanfrage §evon " + api2.getTabPrefix() + " §eabgelehnt"));
					return;
				} else {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDer §cSpieler §chat §cdir §ckeine §cFreundschaftsanfage §cgeschickt"));
					return;
				}
			} else if(args[0].equalsIgnoreCase("accept")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				String name = args[1];
				UUID uuid = null;
				if (ProxyServer.getInstance().getPlayer(name) != null)
					uuid = ProxyServer.getInstance().getPlayer(name).getUniqueId();
				else
					uuid = PlayerAPI.getUUIDFromDBName(name);
				
				if (uuid == null) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDieser §eSpieler §ewar §enoch §enicht §eauf §edem §eNetzwerk!"));
					return;
				}
				
				if (api.getFriendsUUID().contains(uuid)) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §cbist §cbereits §cmit §cdiesem §cSpieler §cbefreundet"));
					return;
				}
				
				if(api.getFriendsUUID().size() >= getMax(p.getUniqueId())) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §chast §cdein §cFreundelimit §cerreicht"));
					return;
				}
				
				if(new PlayerAPI(uuid).getFriendsUUID().size() >= getMax(uuid)) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDer §cSpieler §chat §csein §cFreundelimit §cerreicht"));
					return;
				}
				
				if (api.getFriendRequests().contains(uuid)) {
					api.addFriend(uuid);
					api.deleteFreindReuest(uuid);
					PlayerAPI api2 = new PlayerAPI(uuid);
					if(ProxyServer.getInstance().getPlayer(name) != null)
						ProxyServer.getInstance().getPlayer(name).sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ebist §enun §emit " + api.getTabPrefix() + " §ebefreundet"));
					p.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ebist §enun §emit " + api2.getTabPrefix() + " §ebefreundet"));
					return;
				} else {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDer §cSpieler §chat §cdir §ckeine §cFreundschaftsanfage §cgeschickt"));
					return;
				}
			} else if(args[0].equalsIgnoreCase("jump")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				String name = args[1];
				UUID uuid = null;
				if(ProxyServer.getInstance().getPlayer(name) != null)
					uuid = ProxyServer.getInstance().getPlayer(name).getUniqueId();
				else
					uuid = PlayerAPI.getUUIDFromDBName(name);
				PlayerAPI api2 = new PlayerAPI(uuid);
				
				if (uuid == null) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
					return;
				}
				
				if (p.getUniqueId() == uuid) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §ckannst §cnicht §czu §cdir §cselber §cSpringen"));
					return;
				}
				
				if (!api.getFriendsUUID().contains(uuid)) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §cbist §cnicht §cmit " + api2.getTabPrefix() + " §cbefreundet"));
					return;
				}
				
				if (!api2.isAllowJump()) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDer §cSpieler §chat §cdas §cJump-Feature §cdeaktiviert"));
					return;
				}
					
				if(ProxyServer.getInstance().getPlayer(uuid) == null) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDer Spieler ist nicht Online"));
					return;
				}
				
				ServerType type = CanJoinServer.getServerType(ProxyServer.getInstance().getPlayer(uuid).getServer().getInfo().getName());
				if(CanJoinServer.CanThisUserJoin(ProxyServer.getInstance().getPlayer(uuid).getUniqueId(), type)) {
					ProxyServer.getInstance().getPlayer(sender.getName()).connect(ProxyServer.getInstance().getPlayer(uuid).getServer().getInfo());
					return;
				} else {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §ckonntest §cnicht §czu " + api2.getTabPrefix() + " §cspringen"));
					return;
				}
				
			} else if(args[0].equalsIgnoreCase("remove")) {
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				String name = args[1];
				UUID uuid = null;
				if(ProxyServer.getInstance().getPlayer(name) != null)
					uuid = ProxyServer.getInstance().getPlayer(name).getUniqueId();
				else
					uuid = PlayerAPI.getUUIDFromDBName(name);
				
				if (uuid == null) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDieser §eSpieler §ewar §enoch §enicht §eauf §edem §eNetzwerk!"));
					return;
				}
				
				if(!api.getFriendsUUID().contains(uuid)) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §cbist §cnicht §cmit " + new PlayerAPI(uuid).getTabPrefix() + " §cbefreundet"));
					return;
				}
				if (ProxyServer.getInstance().getPlayer(uuid) != null) {
					ProxyServer.getInstance().getPlayer(uuid).sendMessage(new TextComponent("§7[§4Freunde§7] " + api.getTabPrefix() + " §ehat §edich §evon §eseiner §eFreundesliste §eentfernt"));
				}
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ehast "+ new PlayerAPI(uuid).getTabPrefix() + " §evon §edeiner §eFreundesliste §eentfernt"));
				api.deleteFreind(uuid);
			} else if(args[0].equalsIgnoreCase("add")) {
				String name = args[1];
				PlayerAPI api = new PlayerAPI(p.getUniqueId());
				UUID uuid = null;
				if(ProxyServer.getInstance().getPlayer(name) != null)
					uuid = ProxyServer.getInstance().getPlayer(name).getUniqueId();
				else
					uuid = PlayerAPI.getUUIDFromDBName(name);
				PlayerAPI api2 = new PlayerAPI(uuid);
				
				if (uuid == null) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDieser §eSpieler §ewar §enoch §enicht §eauf §edem §eNetzwerk!"));
					return;
				}
				
				if(api.getFriendsUUID().contains(uuid)) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §cbist §cbereits §cmit §cdiesem §cSpieler §cbefreundet"));
					return;
				}
				
				if (uuid == p.getUniqueId()) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §ckannst §cdich §cnicht §cselber §cals §cFreund §chinzufügen"));
					return;
				}
				
				if (api2.getFriendRequests().contains(p.getUniqueId())) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ehast §ediesem §eSpieler §ebereits §eeine §eFreundschaftsanfrage §egeschickt"));
					return;
				}
				
				if(!api2.isAcceptingFriends()) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §ckannst §cdiesem §cSpieler §ckeine §cFreundschaftsanfragen §cschicken"));
					return;
				}
				
				if(api2.getFriendsUUID().size() >= getMax(uuid)) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDer §cSpieler §chat §csein §cFreundelimit §cerreicht"));
					return;
				}
				
				if(api.getFriendsUUID().size() >= getMax(p.getUniqueId())) {
					sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDu §chast §cdein §cFreundelimit §cerreicht"));
					return;
				}
				
				if(api.getFriendRequests().contains(uuid)) {
					api.addFriend(uuid);
					api.deleteFreindReuest(uuid);
					if(ProxyServer.getInstance().getPlayer(uuid) != null)
						ProxyServer.getInstance().getPlayer(uuid).sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ebist §enun §emit " + api.getTabPrefix() + " §ebefreundet"));
					p.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ebist §enun §emit " + api2.getTabPrefix() + " §ebefreundet"));
					return;
				}
				
				if(ProxyServer.getInstance().getPlayer(uuid) != null) {
					ProxyServer.getInstance().getPlayer(uuid).sendMessage(new TextComponent("§7[§4Freunde§7] " + api.getTabPrefix() + " §ehat §edir §eeine §eFreundschaftsanfrage §egeschickt."));
					TextComponent msg = new TextComponent("");
					TextComponent accept = new TextComponent("§a[Annehmen]");
					accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/friend accept " + sender.getName()));
					TextComponent deny = new TextComponent("§c[Ablehnen]");
					deny.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/friend deny " + sender.getName()));
					TextComponent txt = new TextComponent(" §e| ");
					
					msg.addExtra(accept);
					msg.addExtra(txt);
					msg.addExtra(deny);
					ProxyServer.getInstance().getPlayer(name).sendMessage(msg);
				}
				api2.addFriendRequest(p.getUniqueId());
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §eDu §ehast " + api2.getTabPrefix() + " §eeine §eFreundschaftsanfrage §egesendet"));
			} else {
				sender.sendMessage(new TextComponent("§7[§4Freunde§7] §cDieser §cBefehl §cexistiert §cnicht"));
			}
		}
	}
	
	private static int getMax(UUID uuid) {
		PlayerAPI api = new PlayerAPI(uuid);
		if(api.getPlayerGroup() == 1)
			return 500;
		if(api.getPlayerGroup() == 2)
			return 750;
		if(api.getPlayerGroup() == 3)
			return 750;
		if(api.getPlayerGroup() == 4)
			return 1000;
		if(api.getPlayerGroup() == 5)
			return 1000;
		if(api.getPlayerGroup() == 6)
			return 1000;
		if(api.getPlayerGroup() == 7)
			return 1000;
		if(api.getPlayerGroup() == 8)
			return 1000;
		if(api.getPlayerGroup() == 9)
			return 1000;
		if(api.getPlayerGroup() == 10)
			return 1000;
		if(api.getPlayerGroup() == 11)
			return 1000;
		if(api.getPlayerGroup() == 12)
			return 1000;
		if(api.getPlayerGroup() == 13)
			return 1000;
		if(api.getPlayerGroup() == 14)
			return 1000;
		if(api.getPlayerGroup() == 15)
			return 1000;
		if(api.getPlayerGroup() == 16)
			return 1000;
		if(api.getPlayerGroup() == 17)
			return 1000;
		if(api.getPlayerGroup() == 18)
			return 1000;
		return 500;	
	}
		
}
