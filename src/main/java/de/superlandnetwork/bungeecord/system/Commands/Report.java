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

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.API.ReportReasons;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Report extends Command {

	public Report(Main m) {
		super("report");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 2) {
			if(ProxyServer.getInstance().getPlayer(args[0]) == null) {
				sender.sendMessage(new TextComponent("§7[§4Report§7] §cDieser §cSpieler §cist §cnicht §cOnline!"));
				return;
			}
			String string = args[1];
			List<String> units = ReportReasons.getUnitAsString();
			if(units.contains(string.toLowerCase())) {
				ReportReasons unit = ReportReasons.getUnit(string.toLowerCase());
				sender.sendMessage(new TextComponent("§7[§4Report§7] §eDu §ehast §6" + args[0] + " §eErfolgrreich §eReportet!"));
				for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
					if(all.hasPermission("ccl.Team")) {
						all.sendMessage(sendLayout("§7[§4Report§7] §c" + args[0] + " §e>> §5" + unit.getName() + " ","§a§l[KLICK HIER]", "jumpto " + args[0], "§e/jumpto " + args[0]));
					}
				}
			} else {
				sender.sendMessage(new TextComponent("§7[§4Report§7] §c/report §c<Name> §c<Grund>"));
				sender.sendMessage(new TextComponent("§cMögliche §cReport §cGründe: "));
				for(ReportReasons unit : ReportReasons.values()){
					sender.sendMessage(new TextComponent("§6- §c" + unit.getName()));
				}
			}
		} else {
			sender.sendMessage(new TextComponent("§7[§4Report§7] §c/report §c<Name> §c<Grund>"));
			sender.sendMessage(new TextComponent("§cMögliche §cReport §cGründe: "));
			for(ReportReasons unit : ReportReasons.values()){
				sender.sendMessage(new TextComponent("§6- §c" + unit.getName()));
			}
		}
	}

	public TextComponent sendLayout(String text, String text2, String command, String hover){
		TextComponent tc = new TextComponent(text);
		TextComponent accept = new TextComponent(text2);
		accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));
		accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
		tc.addExtra(accept);
		return tc;
	}
	
}
