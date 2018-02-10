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
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class eco extends Command{
	
	public eco(Main m) {
		super("eco");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender.hasPermission("ccl.eco")){
			if(args.length == 4){
				String Type = args[0];
				String Type2 = args[1];
				String playername = args[2];
				int Amount = Integer.parseInt(args[3]);
				UUID UUID;
				boolean isOn;
				if(ProxyServer.getInstance().getPlayer(playername) != null){
					UUID = ProxyServer.getInstance().getPlayer(playername).getUniqueId();
					isOn = true;
				}else{
					UUID = PlayerAPI.getUUIDFromDBName(playername);
					isOn = false;
				}
				
				if (UUID == null) {
					sender.sendMessage(new TextComponent("§7[§6System§7] §eDieser §eSpieler §ewar §enoch §enie §eauf §ediesem §eNetzwerk!"));
					return;
				}
				
				PlayerAPI api = new PlayerAPI(UUID);
				
				if(Type.equalsIgnoreCase("Money")){
					if(Type2.equalsIgnoreCase("give")){
						api.addMoney(Amount);
						if(isOn){
							ProxyServer.getInstance().getPlayer(playername).sendMessage(new TextComponent("§7[§6System§7] §aDu §ahast §6" + Amount + " §aGeld §aBekomen"));
						}
						sender.sendMessage(new TextComponent("§7[§6System§7] §aDu §ahast " + api.getPrefix() + " §6" + Amount + " §aGeld §aGegeben"));
						return;
					}
					if(Type2.equalsIgnoreCase("set")){
						api.setMoney(Amount);
						ProxyServer.getInstance().getPlayer(playername).sendMessage(new TextComponent("§7[§6System§7] §aDein §aKontostand §awurde §aauf §6" + Amount + " §aGeld §aGesetzt"));
						sender.sendMessage(new TextComponent("§7[§6System§7] §aDu §ahast " + api.getPrefix() + " §a's Konststand §aauf §6" + Amount + " §aGeld §aGesetzt"));
						return;
					}
					if(Type2.equalsIgnoreCase("take")){
						api.setMoney(api.getMoney()-Amount);
						ProxyServer.getInstance().getPlayer(playername).sendMessage(new TextComponent("§7[§6System§7] §aDier §awurden §6" + Amount + " §aGeld §aweggenomen"));
						sender.sendMessage(new TextComponent("§7[§6System§7] §aDu §ahast " + api.getPrefix() + " §6" + Amount + " §aGeld §awegegenomen"));
						return;
					}
					sender.sendMessage(new TextComponent("§7[§6System§7] §c/eco §c<Money/Coins> §c<give/set/take> §c<Name> §c<Betrag>"));
					return;
				} else if(Type.equalsIgnoreCase("Coins")) {
					if(Type2.equalsIgnoreCase("give")) {
						api.addCoins(Amount);
						if(isOn){
							ProxyServer.getInstance().getPlayer(playername).sendMessage(new TextComponent("§7[§6System§7] §aDu §ahast §6" + Amount + " §aCoins §aBekomen"));
						}
						sender.sendMessage(new TextComponent("§7[§6System§7] §aDu §ahast " + api.getPrefix() + " §6" + Amount + " §aCoins §aGegeben"));
						return;
					}
					if(Type2.equalsIgnoreCase("set")){
						api.setCoins(Amount);
						if(isOn){
							ProxyServer.getInstance().getPlayer(playername).sendMessage(new TextComponent("§7[§6System§7] §aDeine §aCoins wurden auf §6" + Amount + " §aCoins §aGesetzt"));
						}
						sender.sendMessage(new TextComponent("§7[§6System§7] §aDu §ahast " + api.getPrefix() + " §a's §aCoins §aauf §6" + Amount + " §aCoins §aGesetzt"));
						return;
					}
					if(Type2.equalsIgnoreCase("take")){
						api.setCoins(api.getCoins()-Amount);
						if(isOn){
							ProxyServer.getInstance().getPlayer(playername).sendMessage(new TextComponent("§7[§6System§7] §aDier §awurden §6" + Amount + " §aCoins §aweggenomen"));
						}
						sender.sendMessage(new TextComponent("§7[§6System§7] §aDu §ahast §c"+playername+" §6" + Amount + "§a Coins wegegenomen"));
						return;
					}
					sender.sendMessage(new TextComponent("§7[§6System§7] §c/eco §c<Money/Coins> §c<give/set/take> §c<Name> §c<Betrag>"));
					return;
				}else{
					sender.sendMessage(new TextComponent("§7[§6System§7] §c/eco §c<Money/Coins> §c<give/set/take> §c<Name> §c<Betrag>"));
					return;
				}
			}else{
				sender.sendMessage(new TextComponent("§7[§6System§7] §c/eco §c<Money/Coins> §c<give/set/take> §c<Name> §c<Betrag>"));
				return;
			}
		}else{
			sender.sendMessage(new TextComponent("§7[§6System§7] §cDu §chast §chierzu §ckeine §cRechte!"));
		}
	}
}
