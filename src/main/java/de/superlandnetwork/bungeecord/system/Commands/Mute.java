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
import de.superlandnetwork.bungeecord.system.API.MuteReasons;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Mute extends Command{
	
	private Integer value;
	
	public Mute(Main m) {
		super("Mute");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		// /Mute <Namer>
		if(sender.hasPermission("ccl.command.mute")) {
			if(args.length == 2) {
				try {  
					this.value = Integer.valueOf(args[1]); 
				} catch(NumberFormatException e) { 
					sender.sendMessage(new TextComponent("§c/Mute §c<Name> <§cMuteID>"));
					sender.sendMessage(new TextComponent("§cMögliche §cMuteId's: "));
					for(MuteReasons unit : MuteReasons.values()){
						sender.sendMessage(new TextComponent("§c" + unit.getId() + " §c: " + unit.getName()));
					}
					return;
				}
				List<Integer> units = MuteReasons.getUnitAsString();
				if(units.contains(value)) {
					MuteReasons unit = MuteReasons.getUnit(value);
					String playername = args[0];
					String by = sender.getName();
					if(sender instanceof ProxiedPlayer) {
						ProxiedPlayer p = (ProxiedPlayer) sender;
						by = sender.getName() + "/" + p.getUniqueId();
					} else if(sender instanceof CommandSender)
						by = "CONSOLE/SERVER";
					else 
						by = "UNKOWN/" + sender.getName();
					
					UUID UUID;
					if(ProxyServer.getInstance().getPlayer(playername) != null) {
						UUID = ProxyServer.getInstance().getPlayer(playername).getUniqueId();
					} else {
						UUID = PlayerAPI.getUUIDFromDBName(playername);
					}
					
					if(UUID == null) {
						sender.sendMessage(new TextComponent("§7[§4Mute§7] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
						return;
					}
					
					if(new PlayerAPI(UUID).isMuted()) {
						sender.sendMessage(new TextComponent("§7[§4Mute§7] " + new PlayerAPI(UUID).getPrefix() + " §eist §eschon §egemutet"));
						return;
					}
					
					String reason = unit.getName();
					long time = unit.getDefaultTime()*86400;
					new PlayerAPI(UUID).Mute(reason, time, by);
					sender.sendMessage(new TextComponent("§7[§4Mute§7] §eDu §ehast " + new PlayerAPI(UUID).getPrefix() + " §efür §c" + reason + " §eGemutet!"));
				} else {
					sender.sendMessage(new TextComponent("§c/Mute §c<Name> §c<MuteID>"));
					sender.sendMessage(new TextComponent("§cMögliche §cMuteId's: "));
					for(MuteReasons unit : MuteReasons.values()){
						sender.sendMessage(new TextComponent("§c" + unit.getId() + " §c: §c" + unit.getName()));
					}
				}
			} else {
				sender.sendMessage(new TextComponent("§c/Mute §c<Name> §c<MuteID>"));
				sender.sendMessage(new TextComponent("§cMögliche §cMuteId's: "));
				for(MuteReasons unit : MuteReasons.values()){
					sender.sendMessage(new TextComponent("§c" + unit.getId() + " §c: §c" + unit.getName()));
				}
			}
		}else{
			sender.sendMessage(new TextComponent("§cDu §chast §chierzu §ckeine §cBerechtigung!"));
		}
	}
	
}
