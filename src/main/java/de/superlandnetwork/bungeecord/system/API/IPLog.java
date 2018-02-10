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
import java.util.Base64;
import java.util.UUID;

import de.superlandnetwork.bungeecord.system.MySQL;

public class IPLog {

	private UUID uuid;
	private String IP;
	
	public IPLog(UUID uuid, String IP) {
		this.uuid = uuid;
		this.IP = IP;
	}
	
	public void LogIP(){
		byte[] bytesEncoded = Base64.getEncoder().encode(IP.getBytes());
		String IP2 = new String(bytesEncoded);
		long Time = System.currentTimeMillis();
		if (!GetIP()) {
			MySQL.update("INSERT INTO SLN_Log_IP (UUID, IP, Time) VALUES ('"+this.uuid+"', '"+IP2+"', '"+Time+"')");
		} else {
			MySQL.update("UPDATE `SLN_Log_IP` SET `IP`='"+IP2+"',`Time`='"+Time+"' WHERE `UUID`='"+this.uuid+"'");
		}
	}
	
	private boolean GetIP(){
		String IP2 = GetIPL();
		if (IP2 == null)
			return false;
		byte[] bytesEncoded = Base64.getEncoder().encode(IP.getBytes());
		String IP3 = new String(bytesEncoded);
		if (IP2.equals(IP3))
			return true;
		return false;
	}

	private String GetIPL() {
		ResultSet rs = MySQL.getResult("SELECT IP From SLN_Log_IP WHERE UUID='"+this.uuid+"'");
		try {
			while(rs.next()){
				return rs.getString("IP");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
