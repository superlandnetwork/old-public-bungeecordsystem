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

import java.sql.ResultSet;
import java.sql.SQLException;

import de.superlandnetwork.bungeecord.system.Main;
import de.superlandnetwork.bungeecord.system.MySQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class VerifyCommand extends Command {

	/**
	 * @param name
	 */
	public VerifyCommand(Main m) {
		super("teamspeak", "", "ts");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) return;
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("show")) {
				if (isLinked(p)) {
					p.sendMessage(new TextComponent("§7[§3TS§7] §eVerknüpfte §eIdentität: §7" + getLink(p)));
					return;
				} else {
					p.sendMessage(new TextComponent("§7[§3TS§7] §cDu §chast §caktuell §ckeine §cIdentität §cverknüpft!"));
					return;
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (isLinked(p)) {
					sendRemoveTS(p);
					return;
				} else {
					p.sendMessage(new TextComponent("§7[§3TS§7] §cDu §chast §ckeine §cIdentität §cverknüpft!"));
					return;
				}
			} else {
				sendHelp(p);
				return;
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("set")) {
				if(args[1].length() == 28 && args[1].endsWith("=")) {
					if(!isLinked(p)) {
						sendVerifyTS(p, args[1]);
						return;
					} else {
						MySQL.update("INSERT INTO `SLN_TS_Verfiy` (`TS_UID`, `MC_Username`, `MC_UUID`, `Remove`, `Send`) VALUES ('"+getLink(p)+"', '"+p.getName()+"', '"+p.getUniqueId().toString()+"', '1', '1')");
						sendVerifyTS(p, args[1]);
						return;
					}
				} else {
					p.sendMessage(new TextComponent("§7[§3TS§7] §cDie §cIdentität §cmuss §c28 §cZeichen §clang §csein §cund §cmit §ceinem §c'=' §cenden."));
					return;
				}
			} else {
				sendHelp(p);
				return;
			}
		} else {
			sendHelp(p);
			return;
		}
	}

	public void sendRemoveTS(ProxiedPlayer p) {
		p.sendMessage(new TextComponent("§7[§3TS§7] §aDeine §aIdentität §awurde §aentfernt!"));
		MySQL.update("INSERT INTO `SLN_TS_Verfiy` (`TS_UID`, `MC_Username`, `MC_UUID`, `Remove`, `Send`) VALUES ('"+getLink(p)+"', '"+p.getName()+"', '"+p.getUniqueId().toString()+"', '1', '1')");
	}

	public void sendVerifyTS(ProxiedPlayer p, String UID) {
		p.sendMessage(new TextComponent("§7[§3TS§7] §aDeine §aIdentität §awurde §agesetzt. §aBestätige §adiese §anun §abitte §aauf §adem §aTeamspeak!"));
		if(!isLinkProcesRunning(UID))
			MySQL.update("INSERT INTO `SLN_TS_Verfiy` (`TS_UID`, `MC_Username`, `MC_UUID`, `Remove`) VALUES ('"+UID+"', '"+p.getName()+"', '"+p.getUniqueId().toString()+"', '0')");
		MySQL.update("UPDATE `SLN_TS_Verfiy` SET `Send`='1' WHERE `TS_UID`='"+UID+"'");
	}

	public boolean isLinked(ProxiedPlayer p) {
		ResultSet rs = MySQL.getResult("SELECT UID_TS FROM `Users` WHERE `UUID`='" + p.getUniqueId() + "'");
		try {
			if (rs.next()) {
				if(!(rs.getString("UID_TS").equals("null"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	public boolean isLinkProcesRunning(String UID) {
		ResultSet rs = MySQL.getResult("SELECT id FROM `SLN_TS_Verfiy` WHERE `TS_UID`='" + UID + "'");
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	public String getLink(ProxiedPlayer p) {
		ResultSet rs = MySQL.getResult("SELECT UID_TS FROM `Users` WHERE `UUID`='" + p.getUniqueId() + "'");
		try {
			if (rs.next()) {
				return rs.getString("UID_TS");
			}
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		return null;
	}
	
	public void sendHelp(ProxiedPlayer p) {
		p.sendMessage(new TextComponent("§7[§3TS§7] §6Teamspeak §6Verwaltung"));
		p.sendMessage(new TextComponent("§e/teamspeak §eshow §7Zeigt §7die §7aktuell §7verknüpfte §7Identität"));
		p.sendMessage(new TextComponent("§e/teamspeak §eset §e<Identität> §7Verknüpft §7neue §7Identität §7mit §7deinem §7Minecraft-Account"));
		p.sendMessage(new TextComponent("§e/teamspeak §eremove §7Entfernt §7die §7aktuell §7verknüpfte §7Identität"));
	}

}
