/*
    1. qqfriends表的userId1和userId2应该有约束条件：必在qquser表中
    2. user表的phone_num应该加入unique约束
*/

-- 使用 android 数据库
use android;

-- phone_num 应该唯一
-- 创建 qquser 表
create table if not exists `qquser`(
    `id` int(11) not null check(len(`id`) > 5),
    `username` varchar(255) not null,
    -- `email` varchar(255) not null,
    `password` varchar(255) not null check(len(`password`) between 6 and 20),
    `sex` char(1) not null default 'u',   -- m 男 f 女 u 未知
    `phone_num` char(11) unique,
    `icon` varchar(255) default 'icon_default.png',     -- 头像
    `birthday` date default '1999-12-14',        -- 生日
    `register_time` datetime not null,
    `last_login_time` datetime,
    primary key(`id`)
) ENGINE=InnoDB default charset=utf8;


-- 创建 qqfriends 好友表
-- userId1 < userId2 (好友只有双向，无单思)
create table if not exists `qqfriends`(
    `userId1` int(11),      
    `userId2` int(11),
    `relation_time` datetime not null,
    primary key(`userId1`, `userId2`)
) ENGINE=InnoDB default charset=utf8;




-- 群组表
create table if not exists `qqroom`(
    `roomId` int(11),
    `roomName` varchar(255),
    `roomIcon` int(11),     -- 群组头像
    `create_time` datetime, -- 创建时间
    `room_introduce` varchar(255),  -- 群组简介
    `member_num` int(5),    -- 成员数量
    -- `member_list` text,      -- 群组成员id列表，分号;分隔
    -- `member_nick_list` text,    -- 群组成员群内昵称列表，中文顿号、分隔
    -- `member_role_list` text,    -- 群组角色列表，中文顿号、分隔
    `room_owner` int(11),      -- 群主id
    primary key(`roomId`)
) ENGINE=InnoDB default charset=utf8;



-- 群组成员表
create table if not exists `qqroom_member`(
    `roomId` int(11) auto_increment,     -- 群组id
    `userId` int(11),       -- 用户id
    `user_nick` varchar(255), -- 用户在群中昵称，默认是用户名
    `user_role` char(1),    -- 用户在群中角色(群主0/管理员1/普通成员2)
    primary key(`roomId`, `userId`)
) ENGINE=InnoDB default charset=utf8;



-- 会话表
create table if not exists `qqsession`(
    -- `sessionId` int(11) auto_increment primary key,
    `userId` int(11),       -- 所属消息，多用户登录时区分会话表
    `chatId` int(11) unique,       -- 会话id，标示双方聊天的会话是同一个，一个用户可以有多个会话，而同一个会话，对应了两个或多个用户
    `userId1` int(11),      -- 发送人id
    `userId2` int(11),      -- 接受人id
    `last_msg` varchar(255), -- 最后一条消息
    `last_username` varchar(255), -- 最后一条消息内容
    `last_time` datetime,   -- 最后一条消息时间
    `last_msg_type` char(1),  -- 最后一条消息类型(文字'w'/图片'p'/文佳你'f'/音乐'a')
    `chat_type` char(1),    -- 会话类型(群组'r'/好友'f')     
    -- `unread_count` int(8)  -- 该会话未读消息数，应该只存在于本地表中
    primary key(`userId1`, `userId2`)
) ENGINE=InnoDB default charset=utf8;





-- 聊天记录表
create table if not exists `qqmessage`(
    `id` int(11) auto_increment primary key,
    `msg_id` int(11),       -- 可能是便于同步
    `userId` int(11),       -- 所属者，多用户登录
    `userId1` int(11),      -- 发送人id
    `username1` varchar(255), -- 发送人名称
    `userIcon1` varchar(255), -- 发送人头像
    `toId` int(11), -- 接收者(userId/roomId)
    `chat_type` char(1), 
    `msg_type` char(1),
    `msg` varchar(255),     -- 消息内容
    `send_time` datetime,   -- 发送时间
    `send_status` char(1)  -- 发送状态(发送中'i', 发送完成'h', 发送失败'e')
) ENGINE=InnoDB default charset=utf8;




-- 获取好友列表
select u.id, u.username, u.sex, u.phone_num, u.icon, u.birthday, u.register_time, u.last_login_time, f.relation_time
from qquser as u join qqfriends as f
on u.id = f.userId2
where f.userId1 = ?
union
select u2.id, u2.username, u2.sex, u2.phone_num, u2.icon, u2.birthday, u2.register_time, u2.last_login_time, f2.relation_time
from qquser as u2 join qqfriends as f2
on u2.id = f2.userId1
where f2.userId2 = ?;




-- 获取会话列表
select userId, chatId, userId1, userId2, user1.username, user2.username, user1.icon, user2.icon, last_msg, last_username, last_time, last_msg_type, chat_type 
from qqsession, qquser user1, qquser user2
where (userId1 = ? or userId2 = ?) and userId1 = user1.id and userId2 = user2.id;








--------------------
-----虚拟文件系统-----
--------------------


-- 文件实体表
create table if not exists `qqfile`(
    `fileId` int(11) not null check(len(`fileId`) > 5),     -- 7位随机整数
    `fileName` varchar(255) not null,
    `fileType` varchar(25),
    `fileSize` int(15),     -- 文件大小，单位B
    `linkNum` int(11) default 0,   -- 文件链接次数
    `createTime` datetime not null,
    `lastUseTime` datetime default '1999-04-22 00:00:00',    -- 最后一次使用(下载、预览)时间
    primary key(`fileId`)
) ENGINE=InnoDB default charset=utf8;



-- 文件链接表
create table if not exists `qqfile_link`(
    `linkId` int(11) auto_increment primary key,    -- 仅用于主键，没有实际意义，7位随机整数
    `userId` int(11) not null,      -- 用户id
    `fileId` int(11),      -- 文件id，文件夹时-1
    `fileName` varchar(255),       -- 链接中需要保存文件名，因为不同用户可以重命名文件链接
    `fileType` varchar(25),                 -- 目前策略是文件类型根据文件名判断，所以不同链接也要保存
    `fileSize` int(15),     -- 文件大小，单位B
    `isFolder` char(1) default 'n',    -- 是否是文件夹(y是/n否)
    `folderName` varchar(255) ,        -- 若是文件夹，则存放此文件夹名
    `fileList` varchar(255) default '',        -- 若是文件夹，存放此文件夹下子文件的linkId（如1000000;1000001;1000002;)
    `folderList` varchar(1000) default '',     -- 若是文件夹，存放此文件夹下子文件夹的linkId（如 1000000;1000001;1000003;）
    `isRoot` char(1) default 'n',      -- 是否是根文件夹(y是/n否)
    `parent` int(11),   -- 父文件夹id, 根文件夹为 -1
    `createLinkTime` datetime
) ENGINE=InnoDB default charset=utf8;





-- 获取文件真实名字
select qqfile.fileName
from qqfile, qqfile_link
where qqfile_link.linkId = ? and qqfile.fileId = qqfile_link.fileId 