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
import de.superlandnetwork.bungeecord.system.API.BanEnum;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TempBan extends Command {

	public TempBan(Main m) {
		super("tempban");
	}
	
	private long value;

	@Override
	public void execute(CommandSender sender, String[] args) {
		// /tempban <Name> <Zeit> <Zeit Einheit> <Grund>
		if(sender.hasPermission("ccl.command.tempban")){
			if(args.length >= 4){
				String playername = args[0];
				UUID UUID;
				if(ProxyServer.getInstance().getPlayer(playername) != null){
					UUID = ProxyServer.getInstance().getPlayer(playername).getUniqueId();
				}else{
					UUID = PlayerAPI.getUUIDFromDBName(playername);
				}
				
				if (UUID == null) {
					sender.sendMessage(new TextComponent("§7[§4Ban§7] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
					return;
				}
				
				if(new PlayerAPI(UUID).GetBan() == 1){
					sender.sendMessage(new TextComponent("§7[§4Ban§7] " + new PlayerAPI(UUID).getPrefix() + " §eist §eschon §egebannt!"));
				}else{
					try {  this.value = Integer.valueOf(args[1]); } catch(NumberFormatException e){ sender.sendMessage(new TextComponent("§8[§4Ban§8] §c<Zeit> §cMuss §ceine §cZahl §csein!")); }
					String utile = args[2];
					String reason = "";
					String by = sender.getName();
					if(sender instanceof ProxiedPlayer) {
						ProxiedPlayer p = (ProxiedPlayer) sender;
						by = sender.getName() + "/" + p.getUniqueId();
					} else if(sender instanceof CommandSender)
						by = "CONSOLE/SERVER";
					else 
						by = "UNKNOWN/" + sender.getName();
					for(int i=3; i<args.length; i++){
						reason +=args[i] + " ";
					}
					List<String> units = BanEnum.getUnitAsString();
					if(units.contains(utile.toLowerCase())){
						BanEnum unit = BanEnum.getUnit(utile);
						long secounds = this.value*unit.getToSecunde();
						new PlayerAPI(UUID).Ban(reason, secounds, by);
						for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
							if(players.hasPermission("ccl.Team")){
								players.sendMessage(new TextComponent("§3-------------- §7[ §aBan §7] §3--------------"));
								players.sendMessage(new TextComponent("§c Ban: §4" + playername));
								players.sendMessage(new TextComponent("§c Grund: §4" + reason));
								players.sendMessage(new TextComponent("§c Time: §4 " + this.value + unit.getName()));
								players.sendMessage(new TextComponent("§c By: §4" + by));
								players.sendMessage(new TextComponent("§3-------------- §7[ §aBan §7] §3--------------"));
							}
						}
					}else{
						sender.sendMessage(new TextComponent("§7[§4Ban§7] §cDiese §c<Zeit §cEinheit> §cexestiert §cNicht!"));
					}
				}
			}else{
				sender.sendMessage(new TextComponent("§7[§4Ban§7] §c/tempban §c<Name> §c<Zeit> §c<Zeit §cEinheit> §c<Grund>"));
			}
		}else{
			sender.sendMessage(new TextComponent("§7[§4Ban§7] §cDu §chast §chierzu §ckeine §cBerechtigung!"));
		}
	}
}
