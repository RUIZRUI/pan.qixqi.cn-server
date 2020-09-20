package cn.qixqi.pan.proxy;

import java.util.List;
import java.util.Map;

import cn.qixqi.pan.entity.PanUser;
import cn.qixqi.pan.entity.PanFriend;
import cn.qixqi.pan.entity.PanSession;
import cn.qixqi.pan.entity.PanMessage;
import cn.qixqi.pan.entity.PanFile;
import cn.qixqi.pan.entity.PanFileShare;
import cn.qixqi.pan.entity.PanFileLink;
import cn.qixqi.pan.entity.PanFolder;

public interface User {
	// ***********************
	// 用户
	// 用户注册
	public int userRegister(PanUser panUser);
	
	// 用户登录
	public PanUser userLogin(String key, String password);
	
	// 用户注销
	public int userLogout(int uid);
	
	// 获取用户信息
	public PanUser getUserInfo(int uid);
	
	// 更新用户头像
	public int updateAvatar(int uid, String avatar);
	
	// 获取用户头像
	public String getAvatar(int uid);
	
	// 更新用户个人信息
	public int updateUserInfo(int uid, Map<String, Object> map);
	
	// 更新密码
	public int resetPass(int uid, String oldPass, String newPass);
	
	// 获取用户简要信息
	public PanUser getSimpleUser(int uid);
	
	
	// **********************
	// 聊天系统
	// 添加好友
	public int addFriend(int uid1, int uid2);
	
	// 删除好友
	public int deleteFriend(int uid1, int uid2);
	
	// 获取好友列表
	public List<PanFriend> getFriends(int uid);
	
	// 添加会话
	public int addSession(PanSession panSession);
	
	// 删除会话
	public int deleteSession(int sessionId);
	
	// 获取会话列表
	public List<PanSession> getSessions(int uid);
	
	// 发送消息
	public int sendMessage(PanMessage panMessage);
	
	// 获取消息列表
	public List<PanMessage> getMessages(int uid1, int uid2);
	
	// 撤回消息
	public int withdrawMessage(int msgId);
	
	
	// **********************
	// 文件系统
	// 上传文件
	public int uploadFile(PanFile panFile);

	// TODO: 上传文件夹
	
	// 下载文件
	public int downloadFile(int linkId);
	
	// 下载文件夹
	public int downloadFolder(int folderId);
	
	// 删除文件
	public int deleteFile(int linkId);
	
	// 修改文件名
	public int renameFile(int linkId, String fileName);
	
	// 移动文件
	public int moveFile(int linkId, int parentId);
	
	// 分享文件
	public int shareFile(PanFileShare panFileShare);
	
	// 获取分享文件
	public PanFileLink getShareFile(String shareMask, String sharePass);
	
	// 新建文件夹
	public int createFolder(PanFolder panFolder);
	
	// 删除文件夹
	public int deleteFolder(int folderId);
	
	// 修改文件夹名
	public int renameFolder(int folderId, String folderName);
	
	// 移动文件夹
	public int moveFolder(int folderId, int parentId);
	
	// 分享文件夹
	public int shareFolder(PanFileShare panFileShare);
	
	// 获取分享文件夹
	public PanFolder getShareFolder(String shareMask, String sharePass);
	
	// 用户获取自己的分享列表
	public List<PanFileShare> getShares(int uid);
}
