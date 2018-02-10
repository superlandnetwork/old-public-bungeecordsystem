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

public enum ReportReasons {

	HACKING("Hacking"),
	WERBUNG("Werbung"),
	SPAMMING("Spamming"),
	BELEIDIGUNG("Beleidigung"),
	DROHUNG("Drohung"),
	TROLLING("Trolling"),
	SPAWNTRAPPING("Spawntrapping");
	
	String name;
	
	private ReportReasons(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static List<String> getUnitAsString(){
		List<String> units = new ArrayList<String>();
		for(ReportReasons unit : ReportReasons.values()){
			units.add(unit.getName());
		}
		return units;
	}
	
	public static ReportReasons getUnit(String Unit){
		for(ReportReasons unit : ReportReasons.values()){
			if(unit.getName().equals(Unit)) {
				return unit;
			}
		}
		return null;
	}
	
}
