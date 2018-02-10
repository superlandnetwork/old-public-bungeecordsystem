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
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Perm extends Command {

	public Perm(Main m) {
		super("Perm");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer || sender instanceof CommandSender){
			if(sender.hasPermission("ccl.Permisons")){
				if(args.length == 0){
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					sender.sendMessage(new TextComponent("§6Permisons Plugin by §cursinn"));
					sender.sendMessage(new TextComponent("§6Version: §c"+ Main.permVersion));
					sender.sendMessage(new TextComponent("§6Für Hilfe: §c/Perm help"));
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					return;
				}
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("help")){
						sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
						sender.sendMessage(new TextComponent("§6/Perm Help §7: §cPermissions Help"));
						sender.sendMessage(new TextComponent("§6/Perm Group Set <Spieler> <Gruppe> §7: §c Spieler <Spieler> In Gruppe <Gruppe> Setzen"));
						sender.sendMessage(new TextComponent("§6/Perm Group Set <Spieler> <Gruppe> (Time) §7: §c Spieler <Spieler> In Gruppe <Gruppe> Setzen für (Time) Tage"));
						sender.sendMessage(new TextComponent("§6/Perm Group list §7: §cListe Aller Grupen Anzeigen"));
						sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
						return;
					}
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					sender.sendMessage(new TextComponent("§6Für Hilfe: §c/Perm help"));
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					return;
				}
				if(args.length == 2){
					if(args[0].equalsIgnoreCase("Group") && args[1].equalsIgnoreCase("list")){
						sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
						sender.sendMessage(new TextComponent("§cConsole Only Set Groups: §4Owner§c, §4CoOwner§c"));
						sender.sendMessage(new TextComponent("§cOther Groups: §4Admin§c, §4Developer§c, §4Developerin§c, §4Builder§c, §4Builderin§c, §4SrModerator§c, §4SrModeratorin§c, §4Moderator§c, §4Moderatorin§c, §4Supporter§c, §4Supporterin§c, §4YouTube§c, §4PremiumPlus§c, §4Premium§c, §4Spieler§c"));
						sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
						return;
					}
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					sender.sendMessage(new TextComponent("§6Für Hilfe: §c/Perm help"));
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					return;
				}
				if(args.length == 4){
					if(args[0].equalsIgnoreCase("Group")){
						if(args[1].equalsIgnoreCase("set")){
							String Group = args[3];
							String playername = args[2];
							UUID UUID;
							boolean isOn;
							String KICKMSG = "§cBitte Neu Joinen - Rang änderung";
							ProxiedPlayer Player = ProxyServer.getInstance().getPlayer(playername);
							if(ProxyServer.getInstance().getPlayer(playername) != null){
								UUID = ProxyServer.getInstance().getPlayer(playername).getUniqueId();
								isOn = true;
							}else{
								UUID = PlayerAPI.getUUIDFromDBName(playername);
								isOn = false;
							}
							int Group_ID = 0;
							String NOPERMS = "§cDu hast hierzu Keine Berechtigung!";
							
							if(UUID == null) {
								sender.sendMessage(new TextComponent("§8[§6System§8] §eDieser §eSpieler §ewar §enoch §enicht §eauf §edem §eNetzwerk!"));
								return;
							}
							//Owner - Gruppe
							if(Group.equalsIgnoreCase("Owner")){
								if(!(sender instanceof CommandSender)){
									sender.sendMessage(new TextComponent(NOPERMS));
									return;
								}
								Group_ID = PermEnum.OWNER.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+" §aIn die Gruppe Owner Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Ownerin - Gruppe
							if(Group.equalsIgnoreCase("Ownerin")){
								if(!(sender instanceof CommandSender)){
									sender.sendMessage(new TextComponent(NOPERMS));
									return;
								}
								Group_ID = PermEnum.OWNERIN.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+" §aIn die Gruppe Ownerin Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Administrator - Gruppe
							if(Group.equalsIgnoreCase("Administrator")){
								if(!(sender instanceof CommandSender)){
									sender.sendMessage(new TextComponent(NOPERMS));
									return;
								}
								Group_ID = PermEnum.ADMINISTRATOR.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+" §aIn die Gruppe Administrator Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Administratorin - Gruppe
							if(Group.equalsIgnoreCase("Administratorin")){
								if(!(sender instanceof CommandSender)){
									sender.sendMessage(new TextComponent(NOPERMS));
									return;
								}
								Group_ID = PermEnum.ADMINISTRATORIN.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Administratorin Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Developer - Gruppe
							if(Group.equalsIgnoreCase("Developer")){
								Group_ID = PermEnum.DEVELOPER.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Developer Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Developerin - Gruppe
							if(Group.equalsIgnoreCase("Developerin")){
								Group_ID = PermEnum.DEVELOPERIN.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Developerin Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//SrModerator - Gruppe
							if(Group.equalsIgnoreCase("SrModerator")){
								Group_ID = PermEnum.SRMODERATOR.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe SrModerator Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//SrModeratorin - Gruppe
							if(Group.equalsIgnoreCase("SrModeratorin")){
								Group_ID = PermEnum.SRMODERATORIN.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe SrModeratorin Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Moderator - Gruppe
							if(Group.equalsIgnoreCase("Moderator")){
								Group_ID = PermEnum.MODERATOR.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Moderator Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Moderatorin - Gruppe
							if(Group.equalsIgnoreCase("Moderatorin")){
								Group_ID = PermEnum.MODERATORIN.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Moderatorin Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Supporter - Gruppe
							if(Group.equalsIgnoreCase("Supporter")){
								Group_ID = PermEnum.SUPPORTER.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Supporter Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Supporterin - Gruppe
							if(Group.equalsIgnoreCase("Supporterin")){
								Group_ID = PermEnum.SUPPORTERIN.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Supporterin Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Builder - Gruppe
							if(Group.equalsIgnoreCase("Builder")){
								Group_ID = PermEnum.BUILDER.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Builder Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Builderin - Gruppe
							if(Group.equalsIgnoreCase("Builderin")){
								Group_ID = PermEnum.BUILDERIN.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Builderin Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Helfer - Gruppe
							if(Group.equalsIgnoreCase("Helfer")){
								if(!(sender instanceof CommandSender)){
									sender.sendMessage(new TextComponent(NOPERMS));
									return;
								}
								Group_ID = PermEnum.HELFER.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+" §aIn die Gruppe Helfer Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Helferin - Gruppe
							if(Group.equalsIgnoreCase("Helferin")){
								if(!(sender instanceof CommandSender)){
									sender.sendMessage(new TextComponent(NOPERMS));
									return;
								}
								Group_ID = PermEnum.HELFERIN.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+" §aIn die Gruppe Helferin Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//YouTube - Gruppe
							if(Group.equalsIgnoreCase("YouTube")){
								Group_ID = PermEnum.YOUTUBER.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe YouTube Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//PremiumPlus - Gruppe
							if(Group.equalsIgnoreCase("PremiumPlus")){
								Group_ID = PermEnum.PREMIUMPLUS.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe PremiumPlus Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Premium - Gruppe
							if(Group.equalsIgnoreCase("Premium")){ 
								Group_ID = PermEnum.PREMIUM.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Premium Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setAutoNick(0);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							//Spieler - Gruppe
							if(Group.equalsIgnoreCase("spieler")){ 
								Group_ID = PermEnum.SPIELER.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Spieler Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setAutoNick(0);
								new PlayerAPI(UUID).setRankTime(-1);
								return;
							}
							return;
						}
						sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
						sender.sendMessage(new TextComponent("§6Für Hilfe: §c/Perm help"));
						sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
						return;
					}
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					sender.sendMessage(new TextComponent("§6Für Hilfe: §c/Perm help"));
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					return;
				}
				if (args.length == 5) {
					if(args[0].equalsIgnoreCase("Group")) {
						if(args[1].equalsIgnoreCase("set")) {
							String Group = args[3];
							String playername = args[2];
							String Time = args[4];
							UUID UUID;
							boolean isOn;
							String KICKMSG = "§cBitte Neu Joinen - Rang änderung";
							ProxiedPlayer Player = ProxyServer.getInstance().getPlayer(playername);
							if(ProxyServer.getInstance().getPlayer(playername) != null){
								UUID = ProxyServer.getInstance().getPlayer(playername).getUniqueId();
								isOn = true;
							}else{
								UUID = PlayerAPI.getUUIDFromDBName(playername);
								isOn = false;
							}
							int Group_ID = 0;
							int days;							
							if(UUID == null) {
								sender.sendMessage(new TextComponent("§8[§6System§8] §eDieser §eSpieler §ewar §enoch §enicht §eauf §edem §eNetzwerk!"));
								return;
							}
							
							try {
								days = Integer.parseInt(Time);
							} catch (NumberFormatException e) {
								sender.sendMessage(new TextComponent("§cError!"));
								return;
							}
							
							//Premium - Gruppe
							if(Group.equalsIgnoreCase("Premium")){ 
								Group_ID = PermEnum.PREMIUM.getId();
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								sender.sendMessage(new TextComponent("§aDu hast §c"+playername+"§a In die Gruppe Premium Gesetzt!"));
								sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
								if(isOn){
									Player.disconnect(new TextComponent(KICKMSG));
								}
								new PlayerAPI(UUID).SetPlayerGroup(Group_ID);
								new PlayerAPI(UUID).setAutoNick(0);
								new PlayerAPI(UUID).setRankTime(days);
								return;
							}
						}
						return;
					}
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					sender.sendMessage(new TextComponent("§6Für Hilfe: §c/Perm help"));
					sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
					return;
				}								
				sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
				sender.sendMessage(new TextComponent("§6Für Hilfe: §c/Perm help"));
				sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
				return;
			}
			sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
			sender.sendMessage(new TextComponent("§6Für Hilfe: §c/Perm help"));
			sender.sendMessage(new TextComponent("§b=== - Permisons Plugin - ==="));
			return;
		}
	}

}
