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

package de.superlandnetwork.bungeecord.system.Utils;

import java.util.UUID;

import de.superlandnetwork.bungeecord.system.API.PlayerAPI;

public class CanJoinServer {

	public static ServerType getServerType(String name) {
		if (name == "Lobby1")
			return ServerType.LOBBY;
		
		if (name == "BedWars1")
			return ServerType.BW;
		
		if (name == "KFFA1")
			return ServerType.KFFA;
		
		if (name == "OITC1")
			return ServerType.OITC;
		
		if (name == "Build")
			return ServerType.BUILD;
		
		return ServerType.PRIVATE;
	}

	public static boolean CanThisUserJoin(UUID uuid, ServerType type) {
		PlayerAPI api = new PlayerAPI(uuid);
		if (type == ServerType.PRIVATE)
			return false;
		
		if (type == ServerType.BUILD) {
			if (api.getPlayerGroup() == 1 || api.getPlayerGroup() == 2 || api.getPlayerGroup() == 3 || api.getPlayerGroup() == 4)
				return false;
			else 
				return true;
		}
			
		if (type == ServerType.LOBBY)
			return true;
		
		if (type == ServerType.BW)
			return true;
		
		if (type == ServerType.KFFA)
			return true;
		
		if (type == ServerType.OITC)
			return true;
		
		if (type == ServerType.CITYBUILD)
			return true;
		return false;
	}

}
