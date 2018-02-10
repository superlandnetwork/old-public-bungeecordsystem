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
import java.util.UUID;

import de.superlandnetwork.bungeecord.system.MySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerAPI {

	private UUID UUID;
	private ProxiedPlayer player;
	
	public PlayerAPI(UUID UUID) {
		this.UUID = UUID;
		this.player = ProxyServer.getInstance().getPlayer(UUID);
	}
	
	public int GetBan(){
		ResultSet rs = MySQL.getResult("SELECT Baned From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()){
				return rs.getInt("Baned");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int GetWartungJoin(){
		ResultSet rs = MySQL.getResult("SELECT WartungJoin From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()){
				return rs.getInt("WartungJoin");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getBanReason() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Ban_Grund From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()){
				return rs.getString("Ban_Grund");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "ERROR";
	}
	
	public long getBanEnd(){
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Ban_Ende From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()){
				return rs.getLong("Ban_Ende");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int GetNoUnBan(){
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Ban_NotUnban From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()){
				return rs.getInt("Ban_NotUnban");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	public String getReamaingingTime() {
		if(getBanEnd() == -1){
			return "§6Permanent";
		}
		long current = System.currentTimeMillis();
		long end = getBanEnd();
		long different = end - current;
		
		long secounds = 0;
		long minutes = 0;
		long hours = 0;
		long days = 0;
		while(different > 1000){
			different -=1000;
			secounds ++;
		}
		while(secounds > 60){
			secounds -=60;
			minutes ++;
		}
		while(minutes > 60){
			minutes -=60;
			hours ++;
		}
		while(hours > 24){
			hours -=24;
			days ++;
		}
		if(days >= 1){
			return "§c" + days + " §8Tage, §c" + hours + " §8Stunden, §c" + minutes + " §8Minuten, §c" + secounds + " §8 Sekunden";
		}
		if(hours >= 1){
			return "§c" + hours + " §8Stunden, §c" + minutes + " §8Minuten, §c" + secounds + " §8 Sekunden";
		}
		if(minutes >= 1){
			return "§c" + minutes + " §8Minuten, §c" + secounds + " §8 Sekunden";
		}
		if(secounds >= 1){
			return "§c" + secounds + " §8 Sekunden";
		}
		return "ERROR";
	}
	
	public boolean IsUserInDB(){
		ResultSet rs = MySQL.getResult("SELECT id From Users WHERE UUID='"+this.UUID+"'");
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void Ban(String reason, long second, String byUUID) {
		long end = 0;
		if(second == -1){
			end = -1;
		}else{
			long current = System.currentTimeMillis();
			end = current + (second*1000);
		}
		MySQL.update("UPDATE Users SET Ban_Ende='"+end+"', Ban_By='"+byUUID+"', Ban_Grund='"+reason+"' WHERE UUID='"+this.UUID+"'");
		if(this.player != null){
			this.player.disconnect(new TextComponent("§cDu wurdest vom SuperLandNetwork.de Netzwerk gebannt\n"
					+ "\n"
					+ "§6Grund: §c"+ reason + "\n"
					+ "\n"
					+ "§7Verbleibende Zeit: §c"+ getReamaingingTime() + "\n"
					+ "\n"
					+ "§7Wenn du einen Entbannungsantrag stellen möchtest,\n"
					+ "§7kannst du in unser Forum gehen\n"
					+ "§eForum.SuperLandNetwork.de"));
		}
		if (getReamaingingTime() == "ERROR") {
			System.err.println("Error Ban Time Error");
		}
		UpdateBan(true);
		UpdateBans(getBans() + 1);
	}
	
	public void UpdateBan(boolean baned) {
		InsertIntoDB();
		int s = 0;
		if(baned == true){
			s = 1;
		}
		
		MySQL.update("UPDATE Users SET Baned='"+s+"' WHERE UUID='"+this.UUID+"'");
	}

	public void InsertIntoDB() {
		if(!IsUserInDB()) {
			MySQL.update("INSERT INTO Users (UUID) VALUES ('"+this.UUID+"')");
		}
	}

	public String getBanner(){
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Ban_By From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()){
				return rs.getString("Ban_By");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void unban(){
		UpdateBan(false);
	}
	
	public void SetPlayerGroup(int Group_ID) {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET rank='"+Group_ID+"' WHERE UUID='"+this.UUID+"'");		
	}
	
	public int getPlayerGroup(){
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT rank From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()){
				return rs.getInt("rank");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public boolean IsPlayerInGroup(int GroupID){
		if(getPlayerGroup() == GroupID){
			return true;
		}
		return false;
	}
	
	public static UUID getUUIDFromDBName(String Name){
		if(IsUserInDB(Name)){
			ResultSet rs = MySQL.getResult("SELECT UUID From Users WHERE usernanme='"+Name+"'");
			try {
				while(rs.next()){
					return java.util.UUID.fromString(rs.getString("UUID"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

	private static boolean IsUserInDB(String Name) {
		ResultSet rs = MySQL.getResult("SELECT UUID From Users WHERE usernanme='"+Name+"'");
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void UpdateBans(long l) {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET Bans='"+l+"' WHERE UUID='"+this.UUID+"'");
	}
	
	public int getBans() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Bans From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()) {
				return rs.getInt("Bans");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void updateName() {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET usernanme='"+ProxyServer.getInstance().getPlayer(UUID).getName()+"' WHERE UUID='"+this.UUID+"'");
		MySQL.update("UPDATE Users SET timestap='"+System.currentTimeMillis()+"' WHERE UUID='"+this.UUID+"'");
	}
	
	public void updateName(String name) {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET usernanme='"+name+"' WHERE UUID='"+this.UUID+"'");
		MySQL.update("UPDATE Users SET timestap='"+System.currentTimeMillis()+"' WHERE UUID='"+this.UUID+"'");
	}

	public String getUsername() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT usernanme From Users WHERE UUID='"+this.UUID+"'");
		try {
			if (rs.next()) {
				return rs.getString("usernanme");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateOnlineTime(Long i) {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET OnlineTime='"+i+"' WHERE UUID='"+this.UUID+"'");
	}
	
	public int getCoins() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Coins From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()) {
				return rs.getInt("Coins");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addCoins(int Coins) {
		InsertIntoDB();
		int coin = getCoins();
		int Coins2 = coin + Coins;
		MySQL.update("UPDATE Users SET Coins='"+Coins2+"' WHERE UUID='"+this.UUID+"'");
	}
	
	public void setMoney(int Money){
		InsertIntoDB();
		MySQL.update("UPDATE Users SET Money='"+Money+"' WHERE UUID='"+this.UUID+"'");
	}
	
	public int getMoney() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Money From Users WHERE UUID='"+this.UUID+"'");
		try {
			while(rs.next()) {
				return rs.getInt("Money");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addMoney(int Money) {
		InsertIntoDB();
		int coin = getMoney();
		int Coins2 = coin + Money;
		MySQL.update("UPDATE Users SET Money='"+Coins2+"' WHERE UUID='"+this.UUID+"'");
	}
	
	public String getPrefix() {
		for(PermEnum e : PermEnum.values()) {
			if (IsPlayerInGroup(e.getId())) 
				return e.getPrefix()+getUsername()+"§f";
		}
		return PermEnum.SPIELER.getPrefix()+getUsername()+"§f";
	}
	
	public String getTabPrefix() {
		for(PermEnum e : PermEnum.values()) {
			if (IsPlayerInGroup(e.getId())) 
				return e.getTabList()+getUsername()+"§f";
		}
		return PermEnum.SPIELER.getTabList()+getUsername()+"§f";
	}
	
	public void setCoins(int Coins) {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET Coins='"+Coins+"' WHERE UUID='"+this.UUID+"'");
	}
	
	public boolean AutoNick() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT AutoNick From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				if (rs.getInt("AutoNick") == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setAutoNick(int i) {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET AutoNick='" + i + "' WHERE UUID='" + this.UUID + "'");
	}

	/**
	 * @return
	 */
	public boolean isMuted() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Mute From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				if (rs.getInt("Mute") == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @return
	 */
	public String getMuteReason() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Mute_reason From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				return rs.getString("Mute_reason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	public String getReamaingingMuteTime() {
		if(getMuteEnde() == -1){
			return "§6Permanent";
		}
		long current = System.currentTimeMillis();
		long end = getMuteEnde();
		long different = end - current;
		
		long secounds = 0;
		long minutes = 0;
		long hours = 0;
		long days = 0;
		while(different > 1000){
			different -=1000;
			secounds ++;
		}
		while(secounds > 60){
			secounds -=60;
			minutes ++;
		}
		while(minutes > 60){
			minutes -=60;
			hours ++;
		}
		while(hours > 24){
			hours -=24;
			days ++;
		}
		if(days >= 1){
			return "§c" + days + " §7Tage, §c" + hours + " §7Stunden, §c" + minutes + " §7Minuten, §c" + secounds + " §7 Sekunden";
		}
		if(hours >= 1){
			return "§c" + hours + " §7Stunden, §c" + minutes + " §7Minuten, §c" + secounds + " §7 Sekunden";
		}
		if(minutes >= 1){
			return "§c" + minutes + " §7Minuten, §c" + secounds + " §7 Sekunden";
		}
		if(secounds >= 1){
			return "§c" + secounds + " §7 Sekunden";
		}
		return "ERROR";
	}
	
	private long getMuteEnde() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Mute_Ende From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				return rs.getLong("Mute_Ende");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void Mute(String reason, long second, String byUUID) {
		InsertIntoDB();
		UpdateMute(1);
		long end = 0;
		if(second == -1){
			end = -1;
		}else{
			long current = System.currentTimeMillis();
			long millis = second*1000;
			end = current + millis;
		}
		MySQL.update("UPDATE Users SET Mute_Ende='"+end+"', Mute_by='"+byUUID+"', Mute_reason='"+reason+"' WHERE UUID='"+this.UUID+"'");
		
	}

	public void UpdateMute(int i) {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET Mute='"+i+"' WHERE UUID='"+this.UUID+"'");
		
	}

	public String getMuter() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Mute_by From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				return rs.getString("Mute_by");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	public String getIP() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT IP From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				return rs.getString("IP");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void UpdateOnline(int i) {
		InsertIntoDB();
		MySQL.update("UPDATE Users SET Online='"+i+"' WHERE UUID='"+this.UUID+"'");
	}
	
	public boolean isOnline() {
		InsertIntoDB();
		ResultSet rs = MySQL.getResult("SELECT Online From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				if(rs.getInt("Online") == 1)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("static-access")
	public ArrayList<UUID> getFriendsUUID() {
		ArrayList<UUID> list = new ArrayList<>();
		ResultSet rs = MySQL.getResult("SELECT Friend_UUID From Friends WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				list.add(UUID.fromString(rs.getString("Friend_UUID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<UUID> getFriendsUUIDOrdet() {
		ArrayList<UUID> list = getFriendsUUID();
		ArrayList<UUID> list2 = new ArrayList<>();
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.ADMINISTRATOR.getId()) || api.IsPlayerInGroup(PermEnum.ADMINISTRATORIN.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.DEVELOPER.getId()) || api.IsPlayerInGroup(PermEnum.DEVELOPERIN.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.SRMODERATOR.getId()) || api.IsPlayerInGroup(PermEnum.SRMODERATORIN.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.MODERATOR.getId()) || api.IsPlayerInGroup(PermEnum.MODERATORIN.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.SUPPORTER.getId()) || api.IsPlayerInGroup(PermEnum.SUPPORTERIN.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.BUILDER.getId()) || api.IsPlayerInGroup(PermEnum.BUILDERIN.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.YOUTUBER.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.PREMIUMPLUS.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (int i=0; i<list.size(); i++) {
			PlayerAPI api = new PlayerAPI(list.get(i));
			if (api.IsPlayerInGroup(PermEnum.PREMIUM.getId())) {
				list2.add(list.get(i));
				list.remove(i);
			} else continue;
		}
		for (UUID s : list) {
			list2.add(s);
		}
		return list2;
	}
	
	@SuppressWarnings("static-access")
	public ArrayList<UUID> getFriendRequests() {
		ArrayList<UUID> list = new ArrayList<>();
		ResultSet rs = MySQL.getResult("SELECT Requester_UUID From FriendRequests WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				list.add(UUID.fromString(rs.getString("Requester_UUID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void addFriend (UUID Friend_UUID) {
		MySQL.update("INSERT INTO Friends (`UUID`, `Friend_UUID`) VALUES ('" + this.UUID + "', '" + Friend_UUID + "')");
		MySQL.update("INSERT INTO Friends (`Friend_UUID`, `UUID`) VALUES ('" + this.UUID + "', '" + Friend_UUID + "')");
		return;
	}
	
	public void addFriendRequest (UUID Requester_UUID) {
		MySQL.update("INSERT INTO FriendRequests (`UUID`, `Requester_UUID`) VALUES ('" + this.UUID + "', '" + Requester_UUID + "')");
		return;
	}
	
	public void deleteFreindReuest (UUID Requester_UUID) {
		MySQL.update("DELETE FROM FriendRequests WHERE UUID='" + this.UUID + "' AND Requester_UUID='" + Requester_UUID + "'");
		return;
	} 
	
	public void deleteFreind (UUID Friend_UUID) {
		MySQL.update("DELETE FROM Friends WHERE UUID='" + this.UUID + "' AND Friend_UUID='" + Friend_UUID + "'");
		MySQL.update("DELETE FROM Friends WHERE Friend_UUID='" + this.UUID + "' AND UUID='" + Friend_UUID + "'");
		return;
	}

	public boolean isAllowJump() {
		ResultSet rs = MySQL.getResult("SELECT Friend_AllowJump From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				if(rs.getInt("Friend_AllowJump") == 1)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void setAllowJump(boolean b) {
		if(b)
			MySQL.update("UPDATE Users SET Friend_AllowJump='1' WHERE UUID='"+this.UUID+"'");
		else 
			MySQL.update("UPDATE Users SET Friend_AllowJump='0' WHERE UUID='"+this.UUID+"'");
	}

	public boolean isAllowMSG() {
		ResultSet rs = MySQL.getResult("SELECT Friend_AllowMSG From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				if(rs.getInt("Friend_AllowMSG") == 1)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void setAllowMSG(boolean b) {
		if(b)
			MySQL.update("UPDATE Users SET Friend_AllowMSG='1' WHERE UUID='"+this.UUID+"'");
		else 
			MySQL.update("UPDATE Users SET Friend_AllowMSG='0' WHERE UUID='"+this.UUID+"'");
	}

	public boolean isShowingNotify() {
		ResultSet rs = MySQL.getResult("SELECT Friend_ShowNotify From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				if(rs.getInt("Friend_ShowNotify") == 1)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void setShowingNotifys(boolean b) {
		if(b)
			MySQL.update("UPDATE Users SET Friend_ShowNotify='1' WHERE UUID='"+this.UUID+"'");
		else 
			MySQL.update("UPDATE Users SET Friend_ShowNotify='0' WHERE UUID='"+this.UUID+"'");
	}

	public boolean isAcceptingFriends() {
		ResultSet rs = MySQL.getResult("SELECT Friend_Accept From Users WHERE UUID='" + this.UUID + "'");
		try {
			while (rs.next()) {
				if(rs.getInt("Friend_Accept") == 1)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void setAcceptingFriends(boolean b) {
		if(b)
			MySQL.update("UPDATE Users SET Friend_Accept='1' WHERE UUID='"+this.UUID+"'");
		else 
			MySQL.update("UPDATE Users SET Friend_Accept='0' WHERE UUID='"+this.UUID+"'");
	}

	public void updateIP(String string) {
		MySQL.update("UPDATE Users SET IP='"+string+"' WHERE UUID='"+this.UUID+"'");
	}

	public int getBansID(int id) {
		if(isInBansDB()) {
			ResultSet rs2 = MySQL.getResult("SELECT "+getBanID(id)+" From SLN_Ban_Bans WHERE UUID='"+this.UUID+"'");
			try {
				if (rs2.next()) {
					return rs2.getInt(getBanID(id));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else 
			MySQL.update("INSERT INTO SLN_Ban_Bans (`UUID`) VALUES ('" + this.UUID + "')");
		return 0;
	}

	private boolean isInBansDB() {
		ResultSet rs = MySQL.getResult("SELECT id From SLN_Ban_Bans WHERE UUID='"+this.UUID+"'");
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getBanID(int id) {
		return "BanID"+id;
	}

	public void updaeBanID(int id, int i) {
		String s = getBanID(id);
		if(isInBansDB()) {
			MySQL.update("UPDATE SLN_Ban_Bans SET "+s+"='"+i+"' WHERE UUID='"+this.UUID+"'");
		} else {
			MySQL.update("INSERT INTO SLN_Ban_Bans (`UUID`, `"+s+"`) VALUES ('" + this.UUID + "', '"+i+"')");
		}
	}
	
	public boolean isAllowOnlyFriendMSG() {
		ResultSet rs = MySQL.getResult("SELECT Friend_AllowOnlyFriendMSG From Users WHERE UUID='"+this.UUID+"'");
		try {
			if(rs.next()) {
				if(rs.getInt("Friend_AllowOnlyFriendMSG") == 0)
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void updaeLastOnline() {
		MySQL.update("UPDATE Users SET Friend_LastOnline='"+System.currentTimeMillis()+"' WHERE UUID='"+this.UUID+"'");
	}

	public long getOnlineTime() {
		ResultSet rs = MySQL.getResult("SELECT OnlineTime From Users WHERE UUID='"+this.UUID+"'");
		try {
			if(rs.next()) {
				return rs.getLong("OnlineTime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getRemainingRankTime() {
		long current = System.currentTimeMillis();
		long end = getRankTime();
		long different = end - current;
		
		if (end == 0) {
			return "null";
		}
		
		long secounds = 0;
		long minutes = 0;
		long hours = 0;
		long days = 0;
		while(different > 1000){
			different -=1000;
			secounds ++;
		}
		while(secounds > 60){
			secounds -=60;
			minutes ++;
		}
		while(minutes > 60){
			minutes -=60;
			hours ++;
		}
		while(hours > 24){
			hours -=24;
			days ++;
		}
		if(days >= 1){
			return "§6" + days + " §6Tage, §6" + hours + " §6Stunden, §6" + minutes + " §6Minuten, §6" + secounds + " §6Sekunden";
		}
		if(hours >= 1){
			return "§6" + hours + " §eStunden, §6" + minutes + " §6Minuten, §6" + secounds + " §6Sekunden";
		}
		if(minutes >= 1){
			return "§6" + minutes + " §6Minuten, §e" + secounds + " §6Sekunden";
		}
		if(secounds >= 1){
			return "§6" + secounds + " §6Sekunden";
		}
		return "ERROR";
	}

	public long getRankTime() {
		ResultSet rs = MySQL.getResult("SELECT RankTime From Users WHERE UUID='"+this.UUID+"'");
		try {
			if(rs.next()) {
				return rs.getLong("RankTime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void setRankTime(int days) {
		if (days != -1) {
			long d1 = days * BanEnum.DAY.getToSecunde();
			long d = d1 * 1000;
			
			long time = System.currentTimeMillis() + d;
			MySQL.update("UPDATE Users SET RankTime='"+time+"' WHERE UUID='"+this.UUID+"'");
			return;
		}
		long time = -1;
		MySQL.update("UPDATE Users SET RankTime='"+time+"' WHERE UUID='"+this.UUID+"'");
	}

}
