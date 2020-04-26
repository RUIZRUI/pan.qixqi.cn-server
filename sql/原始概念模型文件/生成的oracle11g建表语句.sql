/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2020/4/26 3:32:57                            */
/*==============================================================*/


alter table "User"
   drop constraint FK_USER_USER_USER_USER;

alter table "fileLink"
   drop constraint FK_FILELINK_FILE_LINK_FILE;

alter table "fileLink"
   drop constraint FK_FILELINK_FOLDER_FI_FOLDER;

alter table "folder"
   drop constraint FK_FOLDER_FOLDER_FO_FOLDER;

alter table "folder"
   drop constraint FK_FOLDER_USER_FOLD_USER;

alter table "message"
   drop constraint FK_MESSAGE_SESSION_M_SESSION;

alter table "user_room_r"
   drop constraint FK_USER_ROO_USER_ROOM_USER;

alter table "user_room_r"
   drop constraint FK_USER_ROO_USER_ROOM_ROOM;

alter table "user_session_r"
   drop constraint FK_USER_SES_USER_SESS_USER;

alter table "user_session_r"
   drop constraint FK_USER_SES_USER_SESS_SESSION;

drop index "user_user_r_FK";

drop table "User" cascade constraints;

drop table "file" cascade constraints;

drop index "folder_fileLink_r_FK";

drop index "file_link_r_FK";

drop table "fileLink" cascade constraints;

drop index 文件夹与文件夹之间的关系_FK;

drop index "user_folder_r_FK";

drop table "folder" cascade constraints;

drop index "session_message_r_FK";

drop table "message" cascade constraints;

drop table "room" cascade constraints;

drop table "session" cascade constraints;

drop index "user_room_r2_FK";

drop index "user_room_r_FK";

drop table "user_room_r" cascade constraints;

drop index "user_session_r2_FK";

drop index "user_session_r_FK";

drop table "user_session_r" cascade constraints;

/*==============================================================*/
/* Table: "User"                                                */
/*==============================================================*/
create table "User" 
(
   "userId"             VARCHAR2(20)         not null,
   "Use_userId"         VARCHAR2(20),
   "userName"           VARCHAR2(255)        not null,
   "password"           VARCHAR2(16)         not null,
   "sex"                CHAR(1)              default 'u',
   "phoneNumber"        CHAR(11)             not null,
   "icon"               VARCHAR2(255),
   "birthday"           DATE,
   "registerTime"       DATE                 not null,
   "lastLoginTime"      DATE                 not null,
   constraint PK_USER primary key ("userId")
);

comment on column "User"."sex" is
'''u'' 未知，''m‘ 男，''f'' 女';

/*==============================================================*/
/* Index: "user_user_r_FK"                                      */
/*==============================================================*/
create index "user_user_r_FK" on "User" (
   "Use_userId" ASC
);

/*==============================================================*/
/* Table: "file"                                                */
/*==============================================================*/
create table "file" 
(
   "fileId"             VARCHAR2(20)         not null,
   "fileName"           VARCHAR2(255)        not null,
   "fileType"           VARCHAR2(25),
   "fileSize"           NUMBER(15)           not null,
   "linkNum"            NUMBER(11)           not null,
   "fileCreateTime"     DATE                 not null,
   "lastUsedTime"       DATE                 not null,
   constraint PK_FILE primary key ("fileId")
);

/*==============================================================*/
/* Table: "fileLink"                                            */
/*==============================================================*/
create table "fileLink" 
(
   "linkId"             VARCHAR2(20)         not null,
   "folderId"           VARCHAR2(20),
   "fileId"             VARCHAR2(20),
   "linkFileName"       VARCHAR2(255)        not null,
   "linkFileType"       VARCHAR2(25),
   "linkCreateTime"     DATE,
   "linkParent"         VARCHAR2(20),
   constraint PK_FILELINK primary key ("linkId")
);

/*==============================================================*/
/* Index: "file_link_r_FK"                                      */
/*==============================================================*/
create index "file_link_r_FK" on "fileLink" (
   "fileId" ASC
);

/*==============================================================*/
/* Index: "folder_fileLink_r_FK"                                */
/*==============================================================*/
create index "folder_fileLink_r_FK" on "fileLink" (
   "folderId" ASC
);

/*==============================================================*/
/* Table: "folder"                                              */
/*==============================================================*/
create table "folder" 
(
   "folderId"           VARCHAR2(20)         not null,
   "fol_folderId"       VARCHAR2(20),
   "userId"             VARCHAR2(20),
   "folderName"         VARCHAR2(255)        not null,
   "fileLinkNumber"     NUMBER(10)           not null,
   "folderNumber"       NUMBER(10)           not null,
   "folderParent"       VARCHAR2(20),
   "folderCreateTime"   DATE                 not null,
   constraint PK_FOLDER primary key ("folderId")
);

/*==============================================================*/
/* Index: "user_folder_r_FK"                                    */
/*==============================================================*/
create index "user_folder_r_FK" on "folder" (
   "userId" ASC
);

/*==============================================================*/
/* Index: 文件夹与文件夹之间的关系_FK                                       */
/*==============================================================*/
create index 文件夹与文件夹之间的关系_FK on "folder" (
   "fol_folderId" ASC
);

/*==============================================================*/
/* Table: "message"                                             */
/*==============================================================*/
create table "message" 
(
   "messageId"          VARCHAR2(20)         not null,
   "sessionId"          VARCHAR2(40),
   "fromId"             VARCHAR2(20)         not null,
   "msgType"            CHAR(1)              not null,
   "msg"                VARCHAR2(1000)       not null,
   "sendTime"           DATE                 not null,
   "sendStatus"         CHAR(1)              not null,
   constraint PK_MESSAGE primary key ("messageId")
);

/*==============================================================*/
/* Index: "session_message_r_FK"                                */
/*==============================================================*/
create index "session_message_r_FK" on "message" (
   "sessionId" ASC
);

/*==============================================================*/
/* Table: "room"                                                */
/*==============================================================*/
create table "room" 
(
   "roomId"             VARCHAR2(20)         not null,
   "roomName"           VARCHAR2(255)        not null,
   "roomIcon"           VARCHAR2(255)        default 'icon_default.png',
   "roomCreateTime"     DATE                 not null,
   "roomIntroduce"      VARCHAR2(1000),
   "memberNum"          NUMBER(10)           not null,
   "roomOwner"          VARCHAR2(20),
   constraint PK_ROOM primary key ("roomId")
);

/*==============================================================*/
/* Table: "session"                                             */
/*==============================================================*/
create table "session" 
(
   "sessionId"          VARCHAR2(40)         not null,
   "lastMsg"            VARCHAR2(1000)       not null,
   "lastUserName"       VARCHAR2(255)        not null,
   "lastTime"           DATE                 not null,
   "lastMsgType"        CHAR(1)              not null,
   "chatType"           CHAR(1)              not null,
   constraint PK_SESSION primary key ("sessionId")
);

comment on table "session" is
'用户包含的会话列表';

/*==============================================================*/
/* Table: "user_room_r"                                         */
/*==============================================================*/
create table "user_room_r" 
(
   "userId"             VARCHAR2(20)         not null,
   "roomId"             VARCHAR2(20)         not null,
   constraint PK_USER_ROOM_R primary key ("userId", "roomId")
);

comment on table "user_room_r" is
'一个用户加入多个群组，一个群组也可以包含多个用户
';

/*==============================================================*/
/* Index: "user_room_r_FK"                                      */
/*==============================================================*/
create index "user_room_r_FK" on "user_room_r" (
   "userId" ASC
);

/*==============================================================*/
/* Index: "user_room_r2_FK"                                     */
/*==============================================================*/
create index "user_room_r2_FK" on "user_room_r" (
   "roomId" ASC
);

/*==============================================================*/
/* Table: "user_session_r"                                      */
/*==============================================================*/
create table "user_session_r" 
(
   "userId"             VARCHAR2(20)         not null,
   "sessionId"          VARCHAR2(40)         not null,
   constraint PK_USER_SESSION_R primary key ("userId", "sessionId")
);

comment on table "user_session_r" is
'一个会话对应两个或多个用户，而一个用户可以包含多个会话';

/*==============================================================*/
/* Index: "user_session_r_FK"                                   */
/*==============================================================*/
create index "user_session_r_FK" on "user_session_r" (
   "userId" ASC
);

/*==============================================================*/
/* Index: "user_session_r2_FK"                                  */
/*==============================================================*/
create index "user_session_r2_FK" on "user_session_r" (
   "sessionId" ASC
);

alter table "User"
   add constraint FK_USER_USER_USER_USER foreign key ("Use_userId")
      references "User" ("userId");

alter table "fileLink"
   add constraint FK_FILELINK_FILE_LINK_FILE foreign key ("fileId")
      references "file" ("fileId");

alter table "fileLink"
   add constraint FK_FILELINK_FOLDER_FI_FOLDER foreign key ("folderId")
      references "folder" ("folderId");

alter table "folder"
   add constraint FK_FOLDER_FOLDER_FO_FOLDER foreign key ("fol_folderId")
      references "folder" ("folderId");

alter table "folder"
   add constraint FK_FOLDER_USER_FOLD_USER foreign key ("userId")
      references "User" ("userId");

alter table "message"
   add constraint FK_MESSAGE_SESSION_M_SESSION foreign key ("sessionId")
      references "session" ("sessionId");

alter table "user_room_r"
   add constraint FK_USER_ROO_USER_ROOM_USER foreign key ("userId")
      references "User" ("userId");

alter table "user_room_r"
   add constraint FK_USER_ROO_USER_ROOM_ROOM foreign key ("roomId")
      references "room" ("roomId");

alter table "user_session_r"
   add constraint FK_USER_SES_USER_SESS_USER foreign key ("userId")
      references "User" ("userId");

alter table "user_session_r"
   add constraint FK_USER_SES_USER_SESS_SESSION foreign key ("sessionId")
      references "session" ("sessionId");

