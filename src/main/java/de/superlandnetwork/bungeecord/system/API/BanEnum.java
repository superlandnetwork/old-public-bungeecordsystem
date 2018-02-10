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

public enum BanEnum {
	//BanEnum Version: 1.0
	
	SECOUND("Sekunde(n)", 1, "sec"),
	MINUTE("Minute(n)", 60, "min"),
	HOUR("Stunde(n)", 3600, "hour"),
	DAY("Tag(e)", 86400, "day"),
	WEEK("Woche(n)", 604800, "week"),
	MONTH("Monat(e)", 1814400, "month"),
	YEAHR("Jahr(e)", 29030400, "yeahr");
	
	private String name;
	private long toSecounde;
	private String kurz;
	
	private BanEnum(String Name, long toSecounde, String kurz){
		this.name = Name;
		this.toSecounde = toSecounde;
		this.kurz = kurz;
	}
	
	public long getToSecunde(){
		return toSecounde;
	}
	
	public String getName(){
		return name;
	}
	
	public String getKurz(){
		return kurz;
	}
	
	public static List<String> getUnitAsString(){
		List<String> units = new ArrayList<String>();
		for(BanEnum unit : BanEnum.values()){
			units.add(unit.getKurz().toLowerCase());
		}
		return units;
	}
	
	public static BanEnum getUnit(String Unit){
		for(BanEnum unit : BanEnum.values()){
			if(unit.getKurz().toLowerCase().equals(Unit.toLowerCase())){
				return unit;
			}
		}
		return null;
	}
}
