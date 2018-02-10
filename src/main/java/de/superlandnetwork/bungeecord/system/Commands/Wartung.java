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

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.API.API;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Wartung extends Command{

	public Wartung(Main m) {
		super("Wartung");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender.hasPermission("ccl.Wartung")){
			if(args.length == 1){
				API api = new API();
				if(args[0].equalsIgnoreCase("on")) {
					api.setWartung(true);
					sender.sendMessage(new TextComponent(Main.Server_Prefix + "§cWartungsmodus §eTimer §2Aktiviert!"));
					if(!Main.getInstance().isWartungTimmerRunning) {
						Main.getInstance().startWartungTimer();
						Main.getInstance().isWartungTimmerRunning = true;
					}
				}else if(args[0].equalsIgnoreCase("off")) {
					api.setWartung(false);
					sender.sendMessage(new TextComponent(Main.Server_Prefix + "§cWartungsmodus §4Deaktiviert!"));
					if(Main.getInstance().isWartungTimmerRunning)
						ProxyServer.getInstance().getScheduler().cancel(Main.getInstance().WartungTimmer);
				}else if(args[0].equalsIgnoreCase("Status")){
					if(api.isWartung()){
						sender.sendMessage(new TextComponent(Main.Server_Prefix + "§aWartungsmodus §2An!"));
					}else{
						sender.sendMessage(new TextComponent(Main.Server_Prefix + "§cWartungsmodus §4Aus!"));
					}
				}else{
					sender.sendMessage(new TextComponent(Main.Server_Prefix + "§cFehler: §4/Wartung <On/Off/Status>"));
				}
				return;
			}
			sender.sendMessage(new TextComponent(Main.Server_Prefix + "§cFehler: §4/Wartung <On/Off/Status>"));
			return;
		}else{
			sender.sendMessage(new TextComponent("§cDu hast hierzu Keine Berechtigung!"));
		}
	}

}
