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

import java.sql.ResultSet;
import java.sql.SQLException;

import de.superlandnetwork.bungeecord.system.MySQL;

public class API {

	private int id = 1;
	
	public void setWartung(boolean b){
		int i = 0;
		if (b) 
			i = 1;
		MySQL.update("UPDATE Einstellungen SET Wartung='"+i+"' WHERE id='"+id+"'");
	}
	
	public boolean isWartung(){
		ResultSet rs = MySQL.getResult("SELECT Wartung From Einstellungen WHERE id='"+id+"'");
		try {
			while(rs.next()){
				if(rs.getInt("Wartung") == 1)
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getMOTD() {
		ResultSet rs = MySQL.getResult("SELECT MOTD From Einstellungen WHERE id='"+id+"'");
		try {
			while(rs.next()){
				return rs.getString("MOTD");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "" ;
	}

	public int getMaxSlots() {
		ResultSet rs = MySQL.getResult("SELECT Slots From Einstellungen WHERE id='"+id+"'");
		try {
			while(rs.next()){
				return rs.getInt("Slots");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
