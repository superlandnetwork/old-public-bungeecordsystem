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

package de.superlandnetwork.bungeecord.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	
	public static Connection con;
	
	public static void connect(){
		if(!isConnected()){
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + Main.MySQL_Host + ":" + Main.MySQL_Port + "/" + Main.MySQL_Database + "?autoReconnect=true", Main.MySQL_Username, Main.MySQL_Password);
				System.out.println(Main.System_Prefix + "§aMySQL Verbindung Aufgebaut");
			} catch (SQLException e) {
				e.printStackTrace();	
			}
		}
	}
	
	public static void close(){
		if(isConnected()){
			try {
				con.close();
				System.out.println(Main.System_Prefix + "§aMySQL Verbindung Geschlossen");
			} catch (SQLException e) {
				e.printStackTrace();	
			}
		}
	}
	
	public static boolean isConnected(){
		return con != null;
	}
	
	public static void update(String query){
		if(isConnected()){
			try {
				Statement st = con.createStatement();
				st.executeUpdate(query);
				st.close();
			} catch (SQLException e) {
				connect();
				e.printStackTrace();	
			}
		}
	}
	
	public static ResultSet getResult(String query){
		ResultSet rs = null;
		if(isConnected()){
			try {
				Statement st = con.createStatement();
				rs = st.executeQuery(query);
			} catch (SQLException e) {
				connect();
				e.printStackTrace();	
			}
		}
		return rs;
	}
	
}
