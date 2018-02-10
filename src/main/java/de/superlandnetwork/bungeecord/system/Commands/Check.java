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
import de.superlandnetwork.bungeecord.system.API.PermEnum;
import de.superlandnetwork.bungeecord.system.API.PlayerAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Check extends Command {

	public Check(Main m) {
		super("check");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!sender.hasPermission("ccl.command.check")) {
			sender.sendMessage(new TextComponent("§7[§6System§7] §cDu §chast §chierzu §cKeine §cBerechtigung!"));
			return;
		}
		if(args.length == 1) {
			String playername = args[0];
			UUID UUID;
			if(ProxyServer.getInstance().getPlayer(playername) != null) {
				UUID = ProxyServer.getInstance().getPlayer(playername).getUniqueId();
			} else {
				UUID = PlayerAPI.getUUIDFromDBName(playername);
			}
			
			if (UUID == null) {
				sender.sendMessage(new TextComponent("§7[§6System§7] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
				return;
			}
			
			PlayerAPI a = new PlayerAPI(UUID);
			boolean isBanned;
			boolean NoUnBan;
			String BanReason = "";
			String BanEnde = "";
			String Banby = "";
			int Bans = a.getBans();
			if (a.GetBan() == 1) {
				isBanned = true;
				BanReason = a.getBanReason();
				Banby = a.getBanner();
				BanEnde = a.getReamaingingTime();
			} else {
				isBanned = false;
			}
			
			if (a.GetNoUnBan() == 1) {
				NoUnBan = true;
			} else {
				NoUnBan = false;
			}
			
			boolean isMuted = a.isMuted();
			String Muteby = "";
			String Mutereason = "";
			if(isMuted) {
				Muteby = a.getMuter();
				Mutereason = a.getMuteReason();
			}
			String IP = a.getIP();
			
			String Rang = null;
			for(PermEnum e : PermEnum.values()) {
				if(a.getPlayerGroup() == e.getId())
					Rang = e.getName();
				else continue;
			}
			
			if(Rang == null)
				Rang = PermEnum.SPIELER.getName();
			
			sender.sendMessage(new TextComponent("§e------------------------------"));
			sender.sendMessage(new TextComponent("§3Name §a» §c" + playername));
			sender.sendMessage(new TextComponent("§3UUID §a» §c" + UUID.toString()));
			sender.sendMessage(new TextComponent("§3Bans §a» §c" + Bans));
			sender.sendMessage(new TextComponent("§3Rang §a» §c" + Rang));
			sender.sendMessage(new TextComponent("§3IP §a» §c" + IP));
			if (isBanned) {
				sender.sendMessage(new TextComponent("§3Ban Status §a» §cGebannt"));
			    sender.sendMessage(new TextComponent("§3Gebannt von §a» §c" + Banby));
				sender.sendMessage(new TextComponent("§3Ban Ende §a» §c" + BanEnde));
				sender.sendMessage(new TextComponent("§3Ban Grund §a» §c" + BanReason));
				if (NoUnBan)
					sender.sendMessage(new TextComponent("§3NoUnban §a» §cYes"));
			} else
				sender.sendMessage(new TextComponent("§3Ban Status §a» §cNicht Gebannt"));
			if (isMuted) {
				sender.sendMessage(new TextComponent("§3Mute Status §a» §cGemutet"));
				sender.sendMessage(new TextComponent("§3Gemuted von §a» §c" + Muteby));
				sender.sendMessage(new TextComponent("§3Mute Grund §a» §c" + Mutereason));
			} else
				sender.sendMessage(new TextComponent("§3Mute Status §a» §cNicht Gemutet"));
			sender.sendMessage(new TextComponent("§e------------------------------"));
		
		} else {
			sender.sendMessage(new TextComponent("§7[§6System§7] §c/check §c<Name>"));
		}
	}
}
