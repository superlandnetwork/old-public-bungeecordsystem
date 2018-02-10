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
import java.util.ArrayList;
import java.util.List;

import de.superlandnetwork.bungeecord.system.MySQL;

public class PermAPI {

	/**
	 * 
	 * Diese Funktion gibt eine List mit den Gruppen rechten der GroupID zurück
	 * 
	 * @param GroupID
	 * @return list	 */
	public static List<String> getGroupPermisons(int GroupID){
		List<String> list = new ArrayList<String>();
		ResultSet rs = MySQL.getResult("SELECT * FROM `Permisson_Permissons`");
		try {
			while(rs.next()){
				if(rs.getInt("GroupID") == GroupID){
					list.add(rs.getString("Permissons"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * Diese Funktion gibt alle Permissons der Gruppe GroupID zurück
	 * und der der Verebten
	 * 
	 * @param GroupID
	 * @return list
	 */
	public static List<String> getGroupPermisonsOrdet(int GroupID){
		List<String> list = new ArrayList<String>();
		if(GroupID == 1){
			list.addAll(getGroupPermisons(1));
		}
		if(GroupID == 2){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
		}
		if(GroupID == 3){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
		}
		if(GroupID == 4){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
		}
		if(GroupID == 5){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
		}
		if(GroupID == 6){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
		}
		if(GroupID == 7){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
		}
		if(GroupID == 8){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
		}
		if(GroupID == 9){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
			list.addAll(getGroupPermisons(9));
		}
		if(GroupID == 10){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
			list.addAll(getGroupPermisons(9));
		}
		if(GroupID == 11){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(11));
		}
		if(GroupID == 12){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(11));
		}
		if(GroupID == 13){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(13));
		}
		if(GroupID == 14){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(13));
		}
		if(GroupID == 15){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
			list.addAll(getGroupPermisons(9));
			list.addAll(getGroupPermisons(15));
		}
		if(GroupID == 16){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
			list.addAll(getGroupPermisons(9));
			list.addAll(getGroupPermisons(15));
		}
		if(GroupID == 17){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
			list.addAll(getGroupPermisons(9));
			list.addAll(getGroupPermisons(11));
			list.addAll(getGroupPermisons(15));
			list.addAll(getGroupPermisons(17));
		}
		if(GroupID == 18){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
			list.addAll(getGroupPermisons(9));
			list.addAll(getGroupPermisons(11));
			list.addAll(getGroupPermisons(15));
			list.addAll(getGroupPermisons(17));
		}
		if(GroupID == 19){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
			list.addAll(getGroupPermisons(9));
			list.addAll(getGroupPermisons(11));
			list.addAll(getGroupPermisons(15));
			list.addAll(getGroupPermisons(17));
			list.addAll(getGroupPermisons(19));
		}
		if(GroupID == 20){
			list.addAll(getGroupPermisons(1));
			list.addAll(getGroupPermisons(2));
			list.addAll(getGroupPermisons(3));
			list.addAll(getGroupPermisons(4));
			list.addAll(getGroupPermisons(5));
			list.addAll(getGroupPermisons(7));
			list.addAll(getGroupPermisons(9));
			list.addAll(getGroupPermisons(11));
			list.addAll(getGroupPermisons(15));
			list.addAll(getGroupPermisons(17));
			list.addAll(getGroupPermisons(19));
		}
		return list;
	}
	
}
