/*
    1. qqfriends表的userId1和userId2应该有约束条件：必在qquser表中
    2. user表的phone_num应该加入unique约束
    3. mysql 不支持check约束
    4. mariadb 10.2 以上版本开始支持check约束，但是check约束内不能有 len()函数
*/

-- 使用 pan 数据库
use pan;


-- 创建 pan_user 表
create table if not exists `pan_user`(
    `uid` int(11) auto_increment,
    `username` varchar(255) not null unique,
    `password` varchar(255) not null,
    `sex` char(1) default 'u',   -- m 男 f 女 u 未知
    `email` varchar(255) not null unique,
    `phone_num` char(11) not null unique,
    `avatar` varchar(255) default 'default.png',     -- 头像
    `birthday` date,        -- 生日
    `register_time` timestamp default CURRENT_TIMESTAMP,
    `last_login_time` timestamp default CURRENT_TIMESTAMP,
    primary key(`uid`)
) ENGINE=InnoDB default charset=utf8;


-- 创建 pan_friend 好友表
-- uid1 < uid2 (好友只有双向，无单思)
create table if not exists `pan_friend`(
    `uid1` int(11),      
    `uid2` int(11),
    `relation_time` timestamp default CURRENT_TIMESTAMP,
    primary key(`uid1`, `uid2`),
    foreign key(`uid1`) references pan_user(`uid`)
    on delete cascade on update cascade,
    foreign key(`uid2`) references pan_user(`uid`)
    on delete cascade on update cascade
) ENGINE=InnoDB default charset=utf8;




-- 群组表
create table if not exists `pan_room`(
    `room_id` int(11),
    `room_name` varchar(255),
    `room_icon` varchar(255) default 'avatar/default_room.png',     -- 群组头像
    `room_introduction` varchar(255),  -- 群组简介
    `create_time` timestamp default CURRENT_TIMESTAMP, -- 创建时间
    `member_num` int(5),    -- 成员数量
    -- `member_list` text,      -- 群组成员id列表，分号;分隔
    -- `member_nick_list` text,    -- 群组成员群内昵称列表，中文顿号、分隔
    -- `member_role_list` text,    -- 群组角色列表，中文顿号、分隔
    `room_owner` int(11),      -- 群主id
    primary key(`room_id`),	  -- 可以为null，这样群主注销时，群可以保留
    foreign key(`room_owner`) references pan_user(`uid`)
    on delete set null on update cascade
) ENGINE=InnoDB default charset=utf8;



-- 群组成员表
create table if not exists `pan_room_member`(
    `room_id` int(11),     -- 群组id
    `uid` int(11),       -- 用户id
    `user_nick` varchar(255), -- 用户在群中昵称，默认是用户名
    `user_role` int(1),    -- 用户在群中角色(群主0/管理员1/普通成员2)
    primary key(`room_id`, `uid`),
    foreign key(`room_id`) references pan_room(`room_id`)
    on delete cascade on update cascade,
    foreign key(`uid`) references pan_user(`uid`)
    on delete cascade on update cascade
) ENGINE=InnoDB default charset=utf8;



-- 会话表
create table if not exists `pan_session`(
    `session_id` int(11) unique,       -- 会话id，标示双方聊天的会话是同一个，一个用户可以有多个会话，而同一个会话，对应了两个或多个用户
    `uid` int(11),       -- 所属消息，多用户登录时区分会话表
    `uid1` int(11),      -- 发送人id
    `uid2` int(11),      -- 接受人id
    `last_msg` varchar(255), -- 最后一条消息内容
    `last_msg_username` varchar(255), -- 最后一条消息
    `last_msg_time` timestamp,   -- 最后一条消息时间
    `last_msg_type` char(1),  -- 最后一条消息类型(文字'w'/图片'p'/文佳你'f'/音乐'a')
    `session_type` char(1),    -- 会话类型(群组'r'/好友'f')     
    -- `unread_count` int(8)  -- 该会话未读消息数，应该只存在于本地表中
    primary key(`uid1`, `uid2`),
    foreign key(`uid`) references pan_user(`uid`)
    on delete cascade on update cascade,
    foreign key(`uid1`) references pan_user(`uid`)
    on delete cascade on update cascade,
    foreign key(`uid2`) references pan_user(`uid`)
    on delete cascade on update cascade
) ENGINE=InnoDB default charset=utf8;





-- 聊天记录表
create table if not exists `pan_message`(
    -- `id` int(11) auto_increment primary key,
    `msg_id` int(11) primary key,       -- 可能是便于同步
    `uid` int(11),       -- 所属者，多用户登录
    `uid1` int(11),      -- 发送人id
    `username1` varchar(255), -- 发送人名称
    `avatar1` varchar(255), -- 发送人头像
    `receiver_id` int(11),      -- 接收者(uid/room_id)
    `session_type` char(1), 
    `msg` varchar(255),     -- 消息内容
    `msg_type` char(1),
    `msg_time` timestamp default CURRENT_TIMESTAMP,   -- 发送时间
    `msg_status` char(1),  -- 发送状态(发送中'i', 发送完成'h', 发送失败'e')
    foreign key(`uid`) references pan_user(`uid`)
    on delete cascade on update cascade,
    foreign key(`uid`) references pan_user(`uid`)
    on delete cascade on update cascade
) ENGINE=InnoDB default charset=utf8;




-- 获取好友列表
select u.uid, u.username, u.sex, u.phone_num, u.avatar, u.birthday, u.register_time, u.last_login_time, f.relation_time
from pan_user as u join pan_friend as f
on u.uid = f.uid2
where f.uid1 = ?
union all
select u2.uid, u2.username, u2.sex, u2.phone_num, u2.avatar, u2.birthday, u2.register_time, u2.last_login_time, f2.relation_time
from pan_user as u2 join pan_friend as f2
on u2.uid = f2.uid1
where f2.uid2 = ?;




-- 获取会话列表
select pan_session.uid, session_id, uid1, uid2, user1.username, user2.username, user1.avatar, user2.avatar, last_msg, last_msg_username, last_msg_time, last_msg_type, session_type 
from pan_session, pan_user user1, pan_user user2
where (uid1 = ? or uid2 = ?) and uid1 = user1.uid and uid2 = user2.uid;








--------------------
-----虚拟文件系统-----
--------------------


-- 文件实体表
create table if not exists `pan_file`(
    `file_id` int(11) not null,     
    `file_name` varchar(255) not null,
    `file_type` varchar(25),
    `file_size` int(15),     -- 文件大小，单位B
    `link_num` int(11) default 0,   -- 文件链接次数
    `create_time` timestamp default CURRENT_TIMESTAMP,
    primary key(`file_id`)
) ENGINE=InnoDB default charset=utf8;



-- 文件链接表
/* create table if not exists `pan_file_link`(
    `link_id` int(11) auto_increment primary key,    -- 仅用于主键，没有实际意义，7位随机整数
    `uid` int(11) not null,      -- 用户id
    `file_id` int(11),      -- 文件id，文件夹时为null
    `file_name` varchar(255),       -- 链接中需要保存文件名，因为不同用户可以重命名文件链接
    `file_type` varchar(25),                 -- 目前策略是文件类型根据文件名判断，所以不同链接也要保存
    `file_size` int(15),     -- 文件大小，单位B
    `is_folder` char(1) default 'n',    -- 是否是文件夹(y是/n否)
    `folder_name` varchar(255) ,        -- 若是文件夹，则存放此文件夹名
   	-- `file_list` varchar(255) default '',        -- 若是文件夹，存放此文件夹下子文件的linkId（如1000000;1000001;1000002;)
    -- `folder_list` varchar(1000) default '',     -- 若是文件夹，存放此文件夹下子文件夹的linkId（如 1000000;1000001;1000003;）
    -- `is_root` char(1) default 'n',      -- 是否是根文件夹(y是/n否)
    `parent` int(11),   -- 父文件夹id, 根文件夹为 null
    `create_link_time` datetime default CURRENT_TIMESTAMP,
    foreign key(`uid`) references pan_user(`uid`)
    on delete cascade on update cascade,
    foreign key(`file_id`) references pan_file(`file_id`)
    on delete cascade on update cascade,
    foreign key(`parent`) references pan_file_link(`link_id`)
    on delete cascade on update cascade
) ENGINE=InnoDB default charset=utf8; */


-- 文件夹链接
create table if not exists `pan_folder`(
	`folder_id` int(11) auto_increment primary key,
	`uid` int(11) not null,
	`folder_name` varchar(255),
	`parent` int(11),		-- 父文件夹id，根文件夹null
	`create_folder_time` timestamp default CURRENT_TIMESTAMP,
	foreign key(`uid`) references pan_user(`uid`)
	on delete cascade on update cascade,
	foreign key(`parent`) references pan_folder(`folder_id`)
	on delete cascade on update cascade
) ENGINE=InnoDB default charset=utf8;

-- 文件链接表
create table if not exists `pan_file_link`(
	`link_id` int(11) auto_increment primary key,
	`uid` int(11) not null,
	`file_id` int(11) not null,
	`file_name` varchar(255) not null,
	`parent` int(11) not null,		-- 父文件夹id
	`create_link_time` timestamp default CURRENT_TIMESTAMP,
	foreign key(`uid`) references pan_user(`uid`)
	on delete cascade on update cascade,
	foreign key(`file_id`) references pan_file(`file_id`)
	on delete cascade on update cascade,
	foreign key(`parent`) references pan_folder(`folder_id`)
	on delete cascade on update cascade
) ENGINE=InnoDB default charset=utf8;





-- 获取文件真实名字
select pan_file.file_name
from pan_file, pan_file_link
where pan_file_link.link_id = ? and pan_file.file_id = pan_file_link.file_id 






-- 分享链接表
create table if not exists `pan_file_share`(
    `share_id` int(11) auto_increment primary key,
    `source_id` int(11) not null,		-- 资源id, 可以是link_id，也可以是folder_id
    `share_type` int(1) default 0,		-- 分享类型，(0文件分享/1文件夹分享）
    `share_mask` varchar(255) not null,      -- 掩码
    `share_pass` char(4) not null,        -- 4位提取码
    `create_share_time` timestamp default CURRENT_TIMESTAMP,      -- 创建时间
    `end_share_time` timestamp        -- 分享结束时间
) ENGINE=InnoDB default charset=utf8;


select pan_file_share.share_mask, pan_file_share.share_pass
from pan_file_link, pan_file_share
where pan_file_link.uid = ? and pan_file_link.link_id = pan_file_share.link_id