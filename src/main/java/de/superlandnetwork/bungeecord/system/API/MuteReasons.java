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

public enum MuteReasons {
	
	Beleidigung("Beleidigung", 1, 5, 7),
	RespektlosesVerhalten("Respektloses Verhalten", 2, 5, 7),
	ProvokantesVerhalten("Provokantes Verhalten", 3, 5, 7),
	Spamming("Spamming", 4, 5, 7),
	Werbung("Werbung", 5, 5, 1),
	Drohung("Drohung", 6, 10, 5),
	Wortwahl("Wortwahl", 7, 5, 7);
	
	private String name;
	private int id;
	private int defaultTime;
	private int MaxMutes;
	
	private MuteReasons(String Name, int id, int defaultTime, int MaxMutes){
		this.name = Name;
		this.id = id;
		this.defaultTime = defaultTime;
		this.MaxMutes = MaxMutes;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public static List<Integer> getUnitAsString(){
		List<Integer> units = new ArrayList<Integer>();
		for(MuteReasons unit : MuteReasons.values()){
			units.add(unit.getId());
		}
		return units;
	}
	
	public static MuteReasons getUnit(int Unit){
		for(MuteReasons unit : MuteReasons.values()){
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

	public int getMaxMutes() {
		return MaxMutes;
	}
	
}
