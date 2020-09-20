package cn.qixqi.pan.proxy;

import java.util.List;
import java.util.Map;

import cn.qixqi.pan.entity.PanFile;
import cn.qixqi.pan.entity.PanFileLink;
import cn.qixqi.pan.entity.PanFileShare;
import cn.qixqi.pan.entity.PanFolder;
import cn.qixqi.pan.entity.PanFriend;
import cn.qixqi.pan.entity.PanMessage;
import cn.qixqi.pan.entity.PanSession;
import cn.qixqi.pan.entity.PanUser;

public class ProxyUser implements User {
	private int priority;		// 权限
	private RealUser realUser = new RealUser();
	
	/**
	 * 构造函数，赋予权限
	 * @param priority
	 */
	public ProxyUser(int priority) {
		this.priority = priority;
	}
	

	@Override
	public int userRegister(PanUser panUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PanUser userLogin(String key, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int userLogout(int uid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PanUser getUserInfo(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateAvatar(int uid, String avatar) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getAvatar(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateUserInfo(int uid, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int resetPass(int uid, String oldPass, String newPass) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PanUser getSimpleUser(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addFriend(int uid1, int uid2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFriend(int uid1, int uid2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PanFriend> getFriends(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addSession(PanSession panSession) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteSession(int sessionId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PanSession> getSessions(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int sendMessage(PanMessage panMessage) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PanMessage> getMessages(int uid1, int uid2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int withdrawMessage(int msgId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int uploadFile(PanFile panFile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int downloadFile(int linkId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int downloadFolder(int folderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFile(int linkId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int renameFile(int linkId, String fileName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveFile(int linkId, int parentId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int shareFile(PanFileShare panFileShare) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PanFileLink getShareFile(String shareMask, String sharePass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createFolder(PanFolder panFolder) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFolder(int folderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int renameFolder(int folderId, String folderName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveFolder(int folderId, int parentId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int shareFolder(PanFileShare panFileShare) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PanFolder getShareFolder(String shareMask, String sharePass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PanFileShare> getShares(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
