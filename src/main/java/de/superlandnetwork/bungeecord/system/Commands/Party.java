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

import java.util.HashMap;

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Party extends Command {

	public Party(Main m) {
		super("Party");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer))
			return;
		
		ProxiedPlayer p = (ProxiedPlayer)sender;
		PlayerAPI api = new PlayerAPI(p.getUniqueId());
		
		if (args.length == 0) {
			sendHelp(p);
			return;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("leave")) {
				if (Main.getInstance().PartyManager.isInParty(p)) {
					Main.getInstance().PartyManager.getPartyPlayer().get(p).removePlayer(p);
					return;
				} else {
					p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ebist §ezurzeit §ein §ekeiner §eParty!"));
					return;
				}
			} else {
				sendHelp(p);
				return;
			}
		} else if(args.length == 2) {
			if (args[0].equalsIgnoreCase("invite")) {
				if(Main.getInstance().PartyManager.isInParty(p)) {
					if(Main.getInstance().PartyManager.isPartyLeader(p)) {
						ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[1]);
						if(t == null) {
							p.sendMessage(new TextComponent("§7[§5Party§7] §cDer §cSpieler §cist §cnicht §cOnline"));
							return;
						}
						if(Main.getInstance().PartyManager.getInvites().containsKey(t)) {
							if(Main.getInstance().PartyManager.getInvites().get(t).containsKey(p)) {
								Main.getInstance().PartyManager.getInvites().get(t).replace(p, Main.getInstance().PartyManager.getPartyPlayer().get(p));
								p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ehast §6" + t.getName() + " §eEine §ePartyeinladung §egeschickt!"));
								Main.getInstance().PartyManager.getPartyPlayer().get(p).invitePlayer(t);
								return;
							} else {
								Main.getInstance().PartyManager.getInvites().get(t).put(p, Main.getInstance().PartyManager.getPartyPlayer().get(p));
								p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ehast §6" + t.getName() + " §eEine §ePartyeinladung §egeschickt!"));
								Main.getInstance().PartyManager.getPartyPlayer().get(p).invitePlayer(t);
								return;
							}
						} else {
							HashMap<ProxiedPlayer, de.superlandnetwork.bungeecord.system.Utils.Party> map = new HashMap<>();
							map.put(p, Main.getInstance().PartyManager.getPartyPlayer().get(p));
							Main.getInstance().PartyManager.getInvites().put(t, map);
							p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ehast §6" + t.getName() + " §eEine §ePartyeinladung §egeschickt!"));
							Main.getInstance().PartyManager.getPartyPlayer().get(p).invitePlayer(t);
							return;
						}
					} else {
						p.sendMessage(new TextComponent("§7[§5Party§7] §cDu §cbist §cnicht §cder §cLeiter §ceiner §cParty!"));
						return;
					}
				} else 	{
					ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[1]);
					if(t == null) {
						p.sendMessage(new TextComponent("§7[§5Party§7] §cDer §cSpieler §cist §cnicht §cOnline"));
						return;
					}
					if(Main.getInstance().PartyManager.getInvites().containsKey(t)) {
						if(Main.getInstance().PartyManager.getInvites().get(t).containsKey(p)) {
							Main.getInstance().PartyManager.getInvites().get(t).replace(p, Main.getInstance().PartyManager.getPartyPlayer().get(p));
							p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ehast §6" + t.getName() + " §eEine §ePartyeinladung §egeschickt!"));
							new de.superlandnetwork.bungeecord.system.Utils.Party(p).invitePlayer(t);
							return;
						} else {
							Main.getInstance().PartyManager.getInvites().get(t).put(p, Main.getInstance().PartyManager.getPartyPlayer().get(p));
							p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ehast §6" + t.getName() + " §eEine §ePartyeinladung §egeschickt!"));
							new de.superlandnetwork.bungeecord.system.Utils.Party(p).invitePlayer(t);
							return;
						}
					} else {
						HashMap<ProxiedPlayer, de.superlandnetwork.bungeecord.system.Utils.Party> map = new HashMap<>();
						map.put(p, Main.getInstance().PartyManager.getPartyPlayer().get(p));
						Main.getInstance().PartyManager.getInvites().put(t, map);
						p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ehast §6" + t.getName() + " §eEine §ePartyeinladung §egeschickt!"));
						Main.getInstance().PartyManager.getInvites().get(t).put(p, Main.getInstance().PartyManager.getPartyPlayer().get(p));
						new de.superlandnetwork.bungeecord.system.Utils.Party(p).invitePlayer(t);
						return;
					}
				}
			} else if(args[0].equalsIgnoreCase("promot")) {
				if(Main.getInstance().PartyManager.isPartyLeader(p)) {
					ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[1]);
					if(t == null) {
						p.sendMessage(new TextComponent("§7[§5Party§7] §cDer §cSpieler §cist §cnicht §cOnline"));
						return;
					}
					if(Main.getInstance().PartyManager.isInParty(t)) {
						if(Main.getInstance().PartyManager.getPartyPlayer().get(t) != Main.getInstance().PartyManager.getPartyPlayer().get(p)) {
							p.sendMessage(new TextComponent("§7[§5Party§7] §cDer §cSpieler §cist §cnicht §cin §cdeiner §cParty!"));
							return;
						}
						Main.getInstance().PartyManager.getPartyPlayer().get(p).setOwner(t);
						return;
					} else {
						p.sendMessage(new TextComponent("§7[§5Party§7] §cDer §cSpieler §cist §cnicht §cin §cdeiner §cParty!"));
						return;
					}
				} else {
					p.sendMessage(new TextComponent("§7[§5Party§7] §cDu §cbist §cnicht §cder §cLeiter §ceiner §cParty!"));
				}
			} else if(args[0].equalsIgnoreCase("chat")) {
				if(Main.getInstance().PartyManager.isInParty(p)) {
					String msg = "";
					for(int i = 1;i<args.length;i++){
						if(i > 1){
							msg = msg + " " + args[i];
						} else {
							msg = args[i];
						}
					}
					Main.getInstance().PartyManager.getPartyPlayer().get(p).sayToParty(api.getTabPrefix() + " §7: §e" + msg);
					return;
				} else {
					p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ebist §ezurzeit §ein §ekeiner §eParty!"));
					return;
				}
			} else if(args[0].equalsIgnoreCase("accept")) {
				if(Main.getInstance().PartyManager.isInParty(p)) {
					p.sendMessage(new TextComponent("§7[§5Party§7] §cDu §cbist §cbereits §cin §ceiner §cParty!"));
					return;
				} else {
					if(Main.getInstance().PartyManager.getInvites().containsKey(p)) {
						ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[1]);
						if(t == null) {
							p.sendMessage(new TextComponent("§7[§5Party§7] §cDer §cSpieler §cist §cnicht §cOnline!"));
							return;
						}
						if(Main.getInstance().PartyManager.getInvites().get(p).containsKey(t)) {
							de.superlandnetwork.bungeecord.system.Utils.Party party = Main.getInstance().PartyManager.getInvites().get(p).get(t);
							if(Main.getInstance().PartyManager.getPartys().contains(party)) {
								if(party.getMaxPlayers() == -1) {
									party.addPlayer(p);
									return;
								}
								if(party.getSpieler().size() >= party.getMaxPlayers()) {
									p.sendMessage(new TextComponent("§7[§5Party§7] §eDas §eParty §eLimit §ewurde §eereicht!"));
									return;
								} else {
									party.addPlayer(p);
									return;
								}
							} else {
								p.sendMessage(new TextComponent("§7[§5Party§7] §eDie §eParty §eExestiert §enicht §emehr!"));
								Main.getInstance().PartyManager.getInvites().get(p).remove(t);
								if(Main.getInstance().PartyManager.getInvites().get(p).isEmpty()) {
									Main.getInstance().PartyManager.getInvites().remove(p);
								}
								return;
							}
						} else {
							p.sendMessage(new TextComponent("§7[§5Party§7] §cDu §chast §ckeine §cEinladung §cfür §cdiese §cParty"));
							return;
						}
					} else {
						p.sendMessage(new TextComponent("§7[§5Party§7] §cDu §chast §ckeine §cEinladung §cfür §cdiese §cParty"));
						return;
					}
				}
			} else {
				sendHelp(p);
				return;
			}
		} else {
			if(args[0].equalsIgnoreCase("chat")) {
				if(Main.getInstance().PartyManager.isInParty(p)) {
					String msg = "";
					for(int i = 1;i<args.length;i++){
						if(i > 1){
							msg = msg + " " + args[i];
						} else {
							msg = args[i];
						}
					}
					Main.getInstance().PartyManager.getPartyPlayer().get(p).sayToParty(api.getTabPrefix() + " §7: §e" + msg);
					return;
				} else {
					p.sendMessage(new TextComponent("§7[§5Party§7] §eDu §ebist §ezurzeit §ein §ekeiner §eParty!"));
					return;
				}
			} else {
				sendHelp(p);
				return;
			}
		}
		return;
	}
	
	public void sendHelp(ProxiedPlayer p) {
		p.sendMessage(new TextComponent("§7----- §5§lParty §7-----"));
		p.sendMessage(new TextComponent("§c/party §cinvite §c<Player> §7| §cLade §ceinen §cSpieler §czu §cdeiner §cParty §cein"));
		p.sendMessage(new TextComponent("§c/party §cleave §7| §cVerlasse §cdeine §cParty"));
		p.sendMessage(new TextComponent("§c/party §cpromot §c<Player> §7| §cÄndere §cden §cPartyleiter"));
		p.sendMessage(new TextComponent("§c/party §cchat §c<Nachricht> §7| §cChate §cmit §cdeinen §cPartymitgliedern"));
		p.sendMessage(new TextComponent("§c/party §caccept §c<Player> §7| §cNehme §ceine §cPartyanfrage §can"));
		p.sendMessage(new TextComponent("§7----- §5§lParty §7-----"));
	}

}
