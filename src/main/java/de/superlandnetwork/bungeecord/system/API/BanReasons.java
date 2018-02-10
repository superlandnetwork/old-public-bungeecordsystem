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

package de.superlandnetwork.bungeecord.system.API;

import java.util.ArrayList;
import java.util.List;

public enum BanReasons {
	
	Hack("Hacks / Hackclient", "§cHacks §c/ §cHackclient", 1, 30, 2),
	ReportMissbrauch("Report Missbrauch", "§cReport §cMissbrauch", 2, 10, 5),
	UnangebrachterSkin("Unangebrachter Skin", "§cUnangebrachter §cSkin", 3, -1, 1),
	UnangebrachterName("Unangebrachter Name", "§cUnangebrachter §cName", 4, -1, 1),
	Teaming("Teaming", "§cTeaming", 5, 10, 6),
	Bugusing("Bugusing", "§cBugusing", 6, 5, 6),
	Spawntrapping("Spawntrapping", "§cSpawntrapping", 7, 15, 7),
	AltAccount("Alt Account", "§cAlt §cAccount", 8, -1, 1),
	Extrem("Extrem", "§cExtrem",  9, -1, 1);
	
	private String name;
	private String name2;
	private int id;
	private int defaultTime;
	private int MaxBans;
	
	private BanReasons(String Name, String name2, int id, int defaultTime, int MaxBans){
		this.name = Name;
		this.name2 = name2;
		this.id = id;
		this.defaultTime = defaultTime;
		this.MaxBans = MaxBans;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getName2() {
		return name2;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public static List<Integer> getUnitAsString(){
		List<Integer> units = new ArrayList<Integer>();
		for(BanReasons unit : BanReasons.values()){
			units.add(unit.getId());
		}
		return units;
	}
	
	public static BanReasons getUnit(int Unit){
		for(BanReasons unit : BanReasons.values()){
			if(unit.getId() == Unit) {
				return unit;
			}
		}
		return null;
	}
	
	/**
	 * @return the defaultTime
	 */
	public int getDefaultTime() {
		return defaultTime;
	}
	
	public int getMaxBans() {
		return MaxBans;
	}
	
}
