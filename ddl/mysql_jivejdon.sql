CREATE TABLE jiveForum (
  forumID BIGINT NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL,
  description TEXT,
  modDefaultThreadVal BIGINT NOT NULL,
  modMinThreadVal BIGINT NOT NULL,
  modDefaultMsgVal BIGINT NOT NULL,
  modMinMsgVal BIGINT NOT NULL,
  modifiedDate VARCHAR(15) NOT NULL,
  creationDate VARCHAR(15) NOT NULL,
  PRIMARY KEY (forumID),
  INDEX jiveForum_name_idx (name(10))
);
CREATE TABLE jiveForumProp (
  forumID BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  propValue TEXT NOT NULL,
  PRIMARY KEY (forumID, name)
);
CREATE TABLE jiveThread (
  threadID BIGINT NOT NULL,
  forumID BIGINT NOT NULL,
  rootMessageID BIGINT NOT NULL,
  modValue BIGINT NOT NULL,
  rewardPoints INT NOT NULL,
  creationDate VARCHAR(15) NOT NULL,
  modifiedDate VARCHAR(15) NOT NULL,
  PRIMARY KEY (threadID),
  INDEX jiveThread_forumID_idx (forumID),
  INDEX jiveThread_modValue_idx (modValue),
  INDEX jiveThread_cDate_idx (creationDate),
  INDEX jiveThread_mDate_idx (modifiedDate)
);
CREATE TABLE jiveThreadProp (
  threadID BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  propValue TEXT NOT NULL,
  PRIMARY KEY (threadID, name)
);
CREATE TABLE jiveMessage (
  messageID BIGINT NOT NULL,
  parentMessageID BIGINT NULL,
  #defaul is null 
  threadID BIGINT NOT NULL,
  forumID BIGINT NOT NULL,
  userID BIGINT NULL,
  subject VARCHAR(255),
  body TEXT,
  modValue BIGINT NOT NULL,
  rewardPoints INT NOT NULL,
  creationDate VARCHAR(15) NOT NULL,
  modifiedDate VARCHAR(15) NOT NULL,
  PRIMARY KEY (messageID),
  INDEX jiveMessage_threadID_idx (threadID),
  INDEX jiveMessage_forumID_idx (forumID),
  INDEX jiveMessage_userID_idx (userID),
  INDEX jiveMessage_modValue_idx (modValue),
  INDEX jiveMessage_cDate_idx (creationDate),
  INDEX jiveMessage_mDate_idx (modifiedDate)
);
CREATE TABLE jiveMessageProp (
  messageID BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  propValue TEXT NOT NULL,
  PRIMARY KEY (messageID, name)
);
CREATE TABLE reblog (
  reblogFromID BIGINT NOT NULL,
  reblogToID BIGINT NOT NULL,
  PRIMARY KEY (reblogFromID, reblogToID)
);
CREATE TABLE jiveID (
  idType INT NOT NULL,
  id BIGINT NOT NULL,
  PRIMARY KEY (idType)
);
insert into jiveID
values (1, 100);
insert into jiveID
values (2, 100);
insert into jiveID
values (3, 200);
insert into jiveID
values (4, 100);
insert into jiveID
values (5, 100);
insert into jiveID
values (0, 100);
CREATE TABLE jiveshortmsg (
  msgId bigint(20) NOT NULL default '0',
  userId bigint(20) default NULL,
  messageTitle varchar(50) default NULL,
  messageBody text,
  messageFrom varchar(20) default NULL,
  messageTo varchar(20) default NULL,
  sendTime varchar(50) default NULL,
  hasRead int(1) default NULL,
  hasSent int(1) default NULL,
  PRIMARY KEY (msgId)
);
#shortmessage added by GeXinying
CREATE TABLE setup (
  name varchar(50) NOT NULL default '',
  value text NOT NULL,
  PRIMARY KEY name (name)
);
CREATE TABLE upload (
  objectId char(50) NOT NULL default '',
  name varchar(50) default '',
  description varchar(200) default '',
  datas LONGBLOB,
  size int(20) NOT NULL default '0',
  messageId varchar(20) NOT NULL default '0',
  creationDate VARCHAR(15) NOT NULL,
  contentType varchar(50) default '',
  PRIMARY KEY (objectId),
  KEY messageId (messageId)
);
CREATE TABLE tag (
  tagID BIGINT NOT NULL,
  title varchar(50) default '',
  assonum int(20) NOT NULL default '0',
  PRIMARY KEY (tagID)
);
CREATE TABLE threadTag (
  threadTagID BIGINT NOT NULL,
  threadID BIGINT NOT NULL,
  tagID BIGINT NOT NULL,
  PRIMARY KEY (threadTagID),
  INDEX jiveThread_tagID_idx (tagID),
  INDEX tagID_jiveThread_idx (threadID)
);
CREATE TABLE token (
  threadID BIGINT NOT NULL,
  token varchar(50) default '', 
  PRIMARY KEY (threadID)
);
CREATE TABLE jiveUser (
  userID BIGINT NOT NULL,
  username VARCHAR(30) UNIQUE NOT NULL,
  passwordHash VARCHAR(32) NOT NULL,
  name VARCHAR(100),
  nameVisible INT NOT NULL,
  email VARCHAR(100) NOT NULL,
  emailVisible INT NOT NULL,
  rewardPoints INT NOT NULL,
  creationDate VARCHAR(15) NOT NULL,
  modifiedDate VARCHAR(15) NOT NULL,
  PRIMARY KEY (userID),
  INDEX jiveUser_username_idx (username),
  INDEX jiveUser_cDate_idx (creationDate)
);
insert into jiveUser (
    userID,
    name,
    username,
    passwordHash,
    email,
    emailVisible,
    nameVisible,
    creationDate,
    modifiedDate,
    rewardPoints
  )
values (
    1,
    'Administrator',
    'admin',
    '21232f297a57a5a743894a0e4a801fc3',
    'admin@yoursite.com',
    1,
    1,
    '0',
    '0',
    0
  );
CREATE TABLE jiveUserProp (
  userID BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  propValue TEXT NOT NULL,
  PRIMARY KEY (userID, name)
);
CREATE TABLE jiveReward (
  userID char(50) NOT NULL,
  creationDate VARCHAR(15) NOT NULL,
  rewardPoints BIGINT NOT NULL,
  messageID BIGINT NULL,
  threadID BIGINT NULL,
  INDEX jiveReward_userID_idx (userID),
  INDEX jiveReward_creationDate_idx (creationDate),
  INDEX jiveReward_messageID_idx (messageID),
  INDEX jiveReward_threadID_idx (threadID)
);
CREATE TABLE jiveModeration (
  objectID BIGINT NOT NULL,
  objectType BIGINT NOT NULL,
  userID char(50) NULL,
  modDate VARCHAR(15) NOT NULL,
  modValue BIGINT NOT NULL,
  INDEX jiveModeration_objectID_idx (objectID),
  INDEX jiveModeration_objectType_idx (objectType),
  INDEX jiveModeration_userID_idx (userID)
);
CREATE TABLE subscription (
  subscriptionID bigint(20) NOT NULL,
  userId char(50) default NULL,
  subscribedtype int(1) default NULL,
  subscribedID bigint(20) default NULL,
  creationDate varchar(15) default NULL,
  sendmsg bit(1) default NULL,
  sendemail bit(1) default NULL,
  notifymode varchar (100),
  PRIMARY KEY (subscriptionID),
  KEY userId (userId),
  KEY userIdType (userId, subscribedtype),
  KEY userIdsubscribedID (userId, subscribedID),
  KEY subscribedID (subscribedID)
);
insert into jiveID
values (6, 100);
create table userconnector (
  userId varchar (100) not null,
  conntype varchar (100) not null,
  connuser varchar (100),
  connpasswd varchar (100),
  datas LONGBLOB,
  expiredate VARCHAR(15) NOT NULL,
  PRIMARY KEY userIdtype (userId, conntype)
);
create table oauthuser (
  weiboUserId varchar (100) not null,
  userId varchar (100) not null,
  nickname varchar (100) not null,
  description varchar (200),
  url varchar (100),
  profileImageUrl varchar (100),
  PRIMARY KEY weiboUserId (weiboUserId),
  KEY userId (userId)
);
create table sitemap (
  id BIGINT NOT NULL,
  url varchar (255) not null,
  name varchar (255) not null,
  creationDate varchar (15) not null,
  PRIMARY KEY id (id)
);
INSERT INTO `jiveForum`
VALUES (
    '101',
    '1',
    '11',
    '0',
    '0',
    '0',
    '0',
    '001537193648828',
    '001537193648828'
  );
INSERT INTO `jiveMessage`
VALUES (
    '101',
    '101',
    '1',
    '101',
    '1',
    'aa',
    'bb',
    '1',
    '1',
    '1537195463805',
    '1537195463805'
  );
INSERT INTO `jiveThread`
VALUES (
    '1',
    '101',
    '101',
    '1',
    '1',
    '1537195463805',
    '1537195463805'
  );