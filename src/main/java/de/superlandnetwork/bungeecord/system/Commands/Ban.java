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

import java.util.List;
import java.util.UUID;

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.API.BanReasons;
import de.superlandnetwork.bungeecord.system.API.Blacklist;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Ban extends Command{

	private Integer value;

	public Ban(Main m) {
		super("Ban");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		// /Ban <Name> <BanID>
		if(sender.hasPermission("ccl.command.ban")) {
			if(args.length == 2) {
				String playername = args[0];
				UUID UUID;
				if(ProxyServer.getInstance().getPlayer(playername) != null) {
					UUID = ProxyServer.getInstance().getPlayer(playername).getUniqueId();
				} else {
					UUID = PlayerAPI.getUUIDFromDBName(playername);
				}
				
				try {  
					this.value = Integer.valueOf(args[1]); 
				} catch(NumberFormatException e) { 
					sender.sendMessage(new TextComponent("§7[§4Ban§7] §c/Ban §c<Name> §c<BanID>"));
					sender.sendMessage(new TextComponent("§cMögliche §cBanId's: "));
					for(BanReasons unit : BanReasons.values()){
						sender.sendMessage(new TextComponent("§c" + unit.getId() + " §c: §c" + unit.getName()));
					}
				}
				
				List<Integer> units = BanReasons.getUnitAsString();
				if(units.contains(value)) {
					BanReasons unit = BanReasons.getUnit(value);
					
					if (UUID == null) {
						sender.sendMessage(new TextComponent("§7[§4Ban§7] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
						return;
					}
					
					if(new PlayerAPI(UUID).GetBan() == 1) {
						sender.sendMessage(new TextComponent("§7[§4Ban§7] " + new PlayerAPI(UUID).getTabPrefix() + " §eist §eschon §egebannt!"));
					} else {
						String by;
						if(sender instanceof ProxiedPlayer) {
							ProxiedPlayer p = (ProxiedPlayer)sender;
							by = p.getName() + "/" + p.getUniqueId();
						} else {
							by = "UNKOWN/" + sender.getName();
						}
						
						long time;
						int bans = new PlayerAPI(UUID).getBansID(unit.getId());
						if(unit.getDefaultTime() != -1) {
							if(getBanTime(unit, bans) != -1)
								time = getBanTime(unit, bans)*86400;
							else
								time = -1;
						} else 
							time = -1;
						
						new PlayerAPI(UUID).updaeBanID(unit.getId(), bans+1);
						
						/* IP-Ban */
						if(value == 1) {
							if(ProxyServer.getInstance().getPlayer(playername) != null)
								Blacklist.ips.put(ProxyServer.getInstance().getPlayer(playername).getAddress().getAddress().toString(), "IP-Ban");
						}
						
						new PlayerAPI(UUID).Ban(unit.getName(), time, by);
						sender.sendMessage(new TextComponent("§7[§4Ban§7] §eDu §ehast " + new PlayerAPI(UUID).getTabPrefix() + " §efür §c" + unit.getName2() + " §eGebannt!"));
					}
				} else {
					sender.sendMessage(new TextComponent("§c/Ban §c<Name> §c<BanID>"));
					sender.sendMessage(new TextComponent("§cMögliche §cBanId's: "));
					for(BanReasons unit : BanReasons.values()){
						sender.sendMessage(new TextComponent("§c" + unit.getId() + " §c: §c" + unit.getName()));
					}
				}
			}else{
				sender.sendMessage(new TextComponent("§7[§4Ban§7] §c/Ban §c<Name> §c<BanID>"));
				sender.sendMessage(new TextComponent("§cMögliche §cBanId's: "));
				for(BanReasons unit : BanReasons.values()){
					sender.sendMessage(new TextComponent("§c" + unit.getId() + " §c: §c" + unit.getName()));
				}
			}
		} else {
			sender.sendMessage(new TextComponent("§7[§4Ban§7] §cDu §chast §chierzu §ckeine §cBerechtigung!"));
		}
	}

	private long getBanTime(BanReasons e, int bans) {		
		if(e.getMaxBans() == bans+1 || e.getMaxBans() < bans+1) {
			return -1;
		}
		
		if(e.getMaxBans() != bans+1) {
			return e.getDefaultTime()*(bans+1);
		}
		
		return 0;
	}

}
