/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.33-community : Database - sauims
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sauims` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sauims`;

/*Table structure for table `anniversary_audit` */

DROP TABLE IF EXISTS `anniversary_audit`;

CREATE TABLE `anniversary_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) NOT NULL,
  `audit_title` varchar(50) NOT NULL,
  `file_name` varchar(50) NOT NULL,
  `audit_state` int(11) NOT NULL DEFAULT '2',
  `audit_result` varchar(100) NOT NULL DEFAULT '无',
  `submit_time` datetime NOT NULL,
  `submit_description` varchar(200) DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `anniversary_audit` */

insert  into `anniversary_audit`(`id`,`org_id`,`audit_title`,`file_name`,`audit_state`,`audit_result`,`submit_time`,`submit_description`,`audit_time`) values (1,12,'2018精英年度审核修改的','AnnRegisterTemplate.docx',2,'无','2018-08-20 23:51:36','2017精英年度审核的修改的描述2','2018-08-19 22:05:29'),(2,17,'2017乒乓球协会','AnnRegisterTemplate.docx',2,'这是审核的驳回理由','2018-04-07 18:05:04','乒乓球协会年度审核描述','2018-04-07 11:12:14'),(3,12,'2016scheduleIdea精英汇','AnnRegisterTemplate.docx',2,'','2017-03-11 18:05:04','scheduleIdea精英汇年度审核描述',NULL),(4,17,'2016乒乓球协会','AnnRegisterTemplate.docx',2,'','2017-03-11 18:05:04','乒乓球协会年度审核描述',NULL),(5,12,'2015scheduleIdea精英汇','AnnRegisterTemplate.docx',2,'','2016-03-11 18:05:04','scheduleIdea精英汇年度审核描述',NULL),(6,17,'2015乒乓球协会','AnnRegisterTemplate.docx',2,'','2016-03-11 18:05:04','乒乓球协会年度审核描述',NULL),(7,12,'2018精英','AnnRegisterTemplate.docx',2,'','2018-04-07 14:45:39','2018精英年度审核的描述',NULL),(8,12,'2017精英年度审核2','AnnRegisterTemplate.docx',2,'无','2018-08-21 08:44:22','2017精英年度审核的修改的描述2',NULL);

/*Table structure for table `authority` */

DROP TABLE IF EXISTS `authority`;

CREATE TABLE `authority` (
  `authority_id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_name` varchar(40) NOT NULL,
  `authority_available` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `authority` */

/*Table structure for table `authority_resource` */

DROP TABLE IF EXISTS `authority_resource`;

CREATE TABLE `authority_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `available` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `authority_resource` */

/*Table structure for table `club_audit` */

DROP TABLE IF EXISTS `club_audit`;

CREATE TABLE `club_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) NOT NULL,
  `register_time` datetime NOT NULL,
  `audit_time` datetime DEFAULT NULL,
  `audit_result` varchar(100) DEFAULT NULL,
  `file` varchar(50) NOT NULL,
  `audit_description` varchar(100) NOT NULL DEFAULT '无',
  `audit_title` varchar(100) NOT NULL,
  `apply_name` varchar(50) NOT NULL,
  `audit_state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `club_audit` */

insert  into `club_audit`(`id`,`org_id`,`register_time`,`audit_time`,`audit_result`,`file`,`audit_description`,`audit_title`,`apply_name`,`audit_state`) values (9,18,'2014-03-11 18:05:04','2015-03-11 18:05:04','','ClubRegisterTemplate.docx','篮球审核描述','篮球协会 注册申请审核','篮球协会负责人姓名',2),(10,19,'2016-03-11 18:05:04','2016-03-11 18:05:04','','ClubRegisterTemplate.docx','台球协会审核描述','台球协会 注册申请审核','台球球协会负责人姓名',0),(11,20,'2013-04-11 18:05:04',NULL,'','ClubRegisterTemplate.docx','社交审核描述','社交协会 注册申请审核','社交协会负责人姓名',2),(12,21,'2018-02-11 18:05:04',NULL,'','ClubRegisterTemplate.docx','桌游协会审核描述','桌游协会 注册申请审核','桌游协会负责人姓名',1),(13,24,'2013-05-11 18:05:04',NULL,'','ClubRegisterTemplate.docx','羽毛球协会审核描述','羽毛球协会 注册申请审核','羽毛球协会负责人姓名',1),(14,25,'2018-07-11 18:05:04',NULL,'','ClubRegisterTemplate.docx','乒乓球协会2审核描述','乒乓球协会2 注册申请审核','乒乓球协会2负责人姓名',1),(15,26,'2018-04-16 21:21:15','2018-04-16 21:21:15','','ClubRegisterTemplate.docx','无','apone协会 注册申请审核','张三san',1),(16,27,'2018-07-06 15:26:53','2018-07-06 15:26:53','','ClubRegisterTemplate.docx','无','apone2协会 注册申请审核','张三san',1);

/*Table structure for table `deparment` */

DROP TABLE IF EXISTS `deparment`;

CREATE TABLE `deparment` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(50) NOT NULL,
  `department_available` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `deparment` */

/*Table structure for table `like_org` */

DROP TABLE IF EXISTS `like_org`;

CREATE TABLE `like_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `available` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `like_org` */

insert  into `like_org`(`id`,`person_id`,`org_id`,`available`) values (1,7,12,1),(2,7,17,0),(3,8,12,0),(4,12,11,0),(5,12,12,1),(6,12,17,1),(7,12,24,0),(8,12,25,0),(9,12,26,0),(10,17,11,0),(11,17,12,0),(12,17,17,0),(13,17,24,0),(14,17,25,0),(15,17,26,0),(16,12,21,0),(17,12,27,0),(18,12,19,0);

/*Table structure for table `major` */

DROP TABLE IF EXISTS `major`;

CREATE TABLE `major` (
  `major_id` int(11) NOT NULL AUTO_INCREMENT,
  `major_name` varchar(50) NOT NULL,
  `department_id` int(11) NOT NULL,
  `major_available` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`major_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `major` */

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) NOT NULL,
  `person_id` int(11) NOT NULL,
  `member_duty` int(11) NOT NULL DEFAULT '0',
  `member_state` int(11) NOT NULL DEFAULT '1',
  `join_time` datetime NOT NULL,
  `leave_time` datetime DEFAULT NULL,
  `org_department` varchar(100) DEFAULT NULL,
  `available` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `member` */

insert  into `member`(`id`,`org_id`,`person_id`,`member_duty`,`member_state`,`join_time`,`leave_time`,`org_department`,`available`) values (8,11,12,0,1,'2007-10-10 12:00:00','2007-10-10 12:00:00','无',1),(9,11,13,0,1,'2008-10-10 12:00:00','2007-10-10 12:00:00','无',1),(10,12,12,0,1,'2007-10-10 12:00:00','2007-10-10 12:00:00','无',1),(11,12,13,0,1,'2008-10-10 12:00:00','2007-10-10 12:00:00','无',1),(12,12,14,0,2,'2009-10-10 12:00:00','2007-10-10 12:00:00','无',1),(13,12,15,0,1,'2006-10-10 12:00:00','2007-10-10 12:00:00','无',1),(14,26,12,0,1,'2018-04-16 23:35:19',NULL,'无',1),(17,27,12,0,1,'2018-08-21 22:41:54',NULL,'无',2),(18,17,12,0,1,'2018-08-21 23:10:59',NULL,'无',2),(19,16,12,0,1,'2018-08-21 23:14:08',NULL,'无',2);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `message_title` varchar(100) NOT NULL,
  `message_content` varchar(300) NOT NULL,
  `release_time` datetime NOT NULL,
  `org_id` int(11) NOT NULL,
  `message_type` int(11) NOT NULL,
  `message_annex` varchar(50) DEFAULT NULL,
  `message_state` int(11) NOT NULL DEFAULT '1',
  `release_name` varchar(50) NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

insert  into `message`(`message_id`,`message_title`,`message_content`,`release_time`,`org_id`,`message_type`,`message_annex`,`message_state`,`release_name`) values (1,'测试的第一条消息标题','测试id第一条消息内容 ','2017-10-10 12:00:00',0,1,NULL,1,'张三'),(2,'测试的第二条消息标题','测试id第二条消息内容 ','2017-10-10 12:00:00',0,1,NULL,1,'李四'),(3,'测试的第三条消息标题','测试id第3条消息内容 ','2017-10-10 12:00:00',0,1,NULL,1,'李四'),(4,'测试的第4条消息标题','测试id第4条消息内容 ','2017-10-10 12:00:00',0,1,NULL,1,'李四'),(5,'测试的第5条消息标题','测试id第5条消息内容 ','2017-10-10 12:00:00',0,1,NULL,1,'李四'),(6,'乒乓球活动','在星期天有一个乒乓球活动','2018-01-05 20:54:07',11,0,NULL,1,'schedule校社联'),(10,'乒乓球活动','在星期天有一个乒乓球活动','2012-04-05 21:32:17',11,0,NULL,1,'schedule校社联'),(11,'羽毛球活动','在星期天有一个羽毛球活动','2018-02-05 21:34:51',11,0,NULL,1,'schedule校社联'),(12,'乒乓球活动','在星期天有一个乒乓球活动','2018-05-05 21:36:18',11,0,NULL,1,'schedule校社联'),(16,'乒乓球活动','在星期天有一个乒乓球活动','2013-04-05 22:04:38',11,0,NULL,1,'schedule校社联'),(17,'足球活动全体消息','在星期天有一个足球活动','2017-04-06 10:28:35',11,0,NULL,1,'schedule校社联'),(18,'足球社团内部消息','在星期天有一个足球活动','2018-04-06 10:30:29',11,0,NULL,1,'schedule校社联'),(19,'自定义发布消息','在星期天有一个指定社团的活动','2014-04-06 10:33:00',11,0,NULL,1,'schedule校社联'),(20,'精英社的活动活动','在星期天有一个精英社的活动','2014-04-09 17:33:24',12,0,NULL,1,'scheduleIdea精英汇协会'),(22,'乒乓球活动','在星期天有一个乒乓球活动','2018-04-09 17:39:14',12,0,NULL,1,'scheduleIdea精英汇协会'),(23,'精英社自定义发布对象','精英社自定义发布对象的消息的内容','2016-04-09 18:52:23',12,0,NULL,1,'scheduleIdea精英汇协会'),(24,'用户id为58,59的消息','精英社自定义发布对象用户id为58,59的消息的内容','2015-04-09 18:54:38',12,0,NULL,1,'scheduleIdea精英汇协会'),(25,'精英社的活动活动2','在星期天有一个精英社的活动2','2017-04-09 19:03:19',12,0,NULL,1,'scheduleIdea精英汇协会'),(26,'篮球活动，全体消息','在星期天有一个蓝球活动','2018-08-19 15:46:14',11,0,NULL,1,'schedule校社联'),(27,'下雨了活动取消，全体消息','在星期天有一个蓝球活动','2018-08-19 15:47:41',11,0,NULL,1,'schedule校社联'),(28,'羽毛球233活动','在星期天有一个羽毛球2333活动','2018-08-19 15:53:13',11,0,NULL,1,'schedule校社联'),(29,'羽毛球223456活动','在星期天有一个羽毛球22345活动','2018-08-19 16:00:50',11,0,NULL,1,'schedule校社联'),(30,'发送给精英社的消息，发布给指定社团','在星期天有一个精英社的指定社团的活动，好好安排','2018-08-19 16:16:57',11,0,NULL,1,'schedule校社联');

/*Table structure for table `message_receive` */

DROP TABLE IF EXISTS `message_receive`;

CREATE TABLE `message_receive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) NOT NULL,
  `receive_id` int(11) NOT NULL,
  `read_flag` int(11) NOT NULL DEFAULT '0',
  `available` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8;

/*Data for the table `message_receive` */

insert  into `message_receive`(`id`,`message_id`,`receive_id`,`read_flag`,`available`) values (1,1,48,1,0),(2,1,47,0,0),(3,2,48,1,1),(4,2,54,0,1),(15,17,47,0,0),(16,17,48,1,1),(17,17,53,0,1),(18,17,54,0,1),(19,17,55,0,1),(20,18,54,1,1),(21,18,47,1,0),(22,18,48,1,1),(23,19,47,1,0),(24,19,48,1,1),(25,20,47,0,0),(26,20,48,0,0),(27,20,53,0,1),(28,20,54,0,1),(29,20,55,0,1),(30,20,58,1,1),(31,20,59,1,1),(32,22,58,1,0),(33,22,59,1,1),(34,23,58,1,1),(35,24,58,1,1),(36,24,59,1,1),(37,25,47,1,1),(38,25,48,0,0),(39,25,53,0,1),(40,25,54,0,1),(41,25,55,0,1),(42,25,58,0,0),(43,25,59,1,1),(44,26,48,1,1),(45,26,53,0,1),(46,26,54,0,1),(47,26,55,0,1),(48,26,58,0,1),(49,26,59,1,1),(50,26,60,0,1),(51,26,61,0,1),(52,26,62,0,1),(53,26,63,0,1),(54,26,64,0,1),(55,26,65,0,1),(56,26,66,0,1),(57,26,67,0,1),(58,26,68,0,1),(59,26,69,0,1),(60,27,48,1,1),(61,27,53,0,1),(62,27,54,0,1),(63,27,55,0,1),(64,27,58,0,1),(65,27,59,1,1),(66,27,60,0,1),(67,27,61,0,1),(68,27,62,0,1),(69,27,63,0,1),(70,27,64,0,1),(71,27,65,0,1),(72,27,66,0,1),(73,27,67,0,1),(74,27,68,0,1),(75,27,69,0,1),(76,28,59,1,1),(77,29,59,1,1),(78,30,48,1,1);

/*Table structure for table `org_info` */

DROP TABLE IF EXISTS `org_info`;

CREATE TABLE `org_info` (
  `org_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `org_name` varchar(40) NOT NULL,
  `found_time` datetime NOT NULL,
  `description` varchar(400) DEFAULT NULL,
  `org_type` varchar(50) DEFAULT NULL,
  `admin_name` varchar(20) NOT NULL,
  `org_logo` varchar(50) NOT NULL,
  `org_view` varchar(50) NOT NULL,
  `like_click` int(11) NOT NULL DEFAULT '0',
  `members` int(11) NOT NULL DEFAULT '0',
  `contact_email` varchar(50) DEFAULT NULL,
  `contact_number` varchar(13) DEFAULT NULL,
  `org_state` int(11) NOT NULL,
  `org_auth` int(11) NOT NULL,
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `club_name_UNIQUE` (`org_name`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `org_info` */

insert  into `org_info`(`org_id`,`user_id`,`org_name`,`found_time`,`description`,`org_type`,`admin_name`,`org_logo`,`org_view`,`like_click`,`members`,`contact_email`,`contact_number`,`org_state`,`org_auth`) values (11,47,'校社联','2018-04-06 14:34:48','我是更新后的校社联描述2','管理类','张三','default_logo.jpg','default_overview.png',1,0,'Canlong2015@126.com','18316821383',1,2),(12,48,'精英汇协会','2017-12-14 04:09:11','修改精英汇协会描述1','科技类','精英','default_logo.jpg','default_overview.png',2,2,'Canlong2015@126.com','12345678901',1,1),(17,53,'乒乓球协会','2015-03-10 14:01:27','这是乒乓球协会的描述','体育','张三q1','default_logo.jpg','default_overview.png',1,0,'s199612346@126.com','18316821839',1,1),(18,54,'篮球协会','2014-03-11 18:05:04','热爱篮球的人的聚合体','体育','篮球协会负责人姓名','default_logo.jpg','default_overview.png',1,1,'Canlong2015@126.com','1234567890',2,1),(19,55,'台球协会','2013-03-11 18:05:04','热爱台球球的人的聚合体','体育','台球协会负责人姓名','default_logo.jpg','default_overview.png',1,1,'gdufxsc106@163.com','1234567891',0,1),(20,62,'社交协会','2015-02-11 18:05:04','热爱社交的人的聚合体','体育','社交协会负责人姓名','default_logo.jpg','default_overview.png',1,1,'shejiao@126.com','1234567893',2,1),(21,63,'桌游协会','2017-04-11 18:05:04','热爱桌游的人的聚合体','体育','桌游协会负责人姓名','default_logo.jpg','default_overview.png',1,1,'zhuoyou@163.com','1234567894',1,1),(24,64,'羽毛球协会','2015-02-11 18:05:04','热爱羽毛球的人的聚合体','体育','羽毛球协会负责人姓名','default_logo.jpg','default_overview.png',1,1,'yumaoqiu@126.com','1234567893',1,1),(25,65,'乒乓球协会2','2017-04-11 18:05:04','热爱乒乓球的人的聚合体','体育','乒乓球协会负责人姓名','default_logo.jpg','default_overview.png',1,1,'pingpangqiu@163.com','1234567894',1,1),(26,67,'apone协会2','2018-04-08 21:55:51','修改apone协会描述2','体育','apone1','default_logo.jpg','default_overview.png',0,1,'s19961234@126.com','18316821839',1,1),(27,69,'apone2协会','2018-07-06 15:26:53','这是apone2协会的描述','体育','张三san','default_logo.jpg','default_overview.png',0,0,'s19961234@126.com','18316821839',1,1);

/*Table structure for table `person_info` */

DROP TABLE IF EXISTS `person_info`;

CREATE TABLE `person_info` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `real_name` varchar(20) DEFAULT NULL,
  `nickname` varchar(20) NOT NULL,
  `gender` int(11) NOT NULL,
  `birthday` datetime NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `enrollment_year` int(11) DEFAULT NULL,
  `address` varchar(40) DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `major` varchar(50) DEFAULT NULL,
  `student_id` varchar(50) DEFAULT NULL,
  `person_logo` varchar(50) NOT NULL,
  `person_state` int(11) NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `person_info` */

insert  into `person_info`(`person_id`,`user_id`,`real_name`,`nickname`,`gender`,`birthday`,`description`,`enrollment_year`,`address`,`department`,`major`,`student_id`,`person_logo`,`person_state`) values (12,58,'王五','wangwu',0,'2018-03-11 18:05:04','hahaha',2012,'8#111','数统学院','信息与计算科学','151612222','default_logo.jpg',1),(13,59,'个人用户2','个人用户2的昵称',0,'2007-10-10 12:00:00','个人用户2的描述,是男',2015,'个人用户2的地址','会计学院','会计学','151612291','default_logo.jpg',1),(14,60,'个人用户3','个人用户3的昵称',0,'2006-10-10 12:00:00','个人用户3的描述',2016,'个人用户3的地址','金融数学与统计学院','会计学','151612290','default_logo.jpg',2),(15,61,'个人用户4','个人用户4的昵称',1,'2005-10-10 12:00:00','个人用户4的描述,是女生',2017,'个人用户4的地址','金融数学与统计学院','会计学','151612291','default_logo.jpg',2),(16,66,NULL,'sauims_66',0,'1970-01-01 08:00:00',NULL,NULL,NULL,NULL,NULL,NULL,'default_logo.jpg',1),(17,68,NULL,'sauims_68',0,'1970-01-01 08:00:00',NULL,NULL,NULL,NULL,NULL,NULL,'default_logo.jpg',1);

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_url` varchar(200) NOT NULL,
  `resource_name` varchar(100) NOT NULL,
  `resource_available` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `resource` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(40) NOT NULL,
  `role_available` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

/*Table structure for table `role_authority` */

DROP TABLE IF EXISTS `role_authority`;

CREATE TABLE `role_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `authority_id` int(11) NOT NULL,
  `available` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_authority` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(40) NOT NULL,
  `phone` varchar(13) NOT NULL,
  `email` varchar(50) NOT NULL,
  `user_key` varchar(40) NOT NULL,
  `login_ip` varchar(40) NOT NULL,
  `login_time` datetime NOT NULL,
  `register_ip` varchar(40) NOT NULL,
  `register_time` datetime NOT NULL,
  `authority` int(11) NOT NULL,
  `user_state` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_name`,`password`,`phone`,`email`,`user_key`,`login_ip`,`login_time`,`register_ip`,`register_time`,`authority`,`user_state`) values (47,'sauims_admin','1fe41c99f47af4fbc9a0ede37e2ca153','18316821383','Canlong2015@126.com','12345','127.0.0.1','2018-08-20 22:33:53','0.0.0.0','2017-03-09 18:30:00',2,1),(48,'abc','8cfa2282b17de0a598c010f5f0109e7d','12345678901','Canlong2015@qq.com','12345','127.0.0.1','2018-08-22 09:27:27','0.0.0.0','2017-03-09 18:30:00',1,1),(53,'admin','774e9c6d12380d300258fdfee932e85b','18316821839','s199612346@126.com','nv9cKjIG34HvZkCAGRmY7g==','127.0.0.1','2018-08-21 23:09:32','127.0.0.1','2018-03-10 14:01:27',1,1),(54,'lanqiu','8cfa2282b17de0a598c010f5f0109e7d','','Canlong20156@126.com','12345','127.0.0.1','2018-04-07 14:14:56','127.0.0.1','2018-03-10 14:25:48',1,2),(55,'taiqiu','a512eac93b40e7d53c69063987e3acbd','','gdufxsc106@163.com','crSweuudfnz1wv3GNFgtlA==','127.0.0.1','2018-03-19 23:13:17','127.0.0.1','2018-03-19 23:13:17',1,0),(58,'person1@126.com','8cfa2282b17de0a598c010f5f0109e7d','1234567892','person1@126.com','12345','127.0.0.1','2018-08-21 22:41:45','127.0.0.1','2017-03-09 18:30:00',0,1),(59,'person2@126.com','8cfa2282b17de0a598c010f5f0109e7d','1234567892','person2@126.com','12345','127.0.0.1','2018-08-20 11:56:39','127.0.0.1','2017-03-09 18:30:00',0,1),(60,'person3@126.com','8cfa2282b17de0a598c010f5f0109e7d','1234567892','person3@126.com','12345','127.0.0.1','2016-04-07 09:50:35','127.0.0.1','2016-03-09 18:30:00',0,2),(61,'person4@126.com','8cfa2282b17de0a598c010f5f0109e7d','1234567892','person4@126.com','12345','127.0.0.1','2015-04-07 09:50:35','127.0.0.1','2015-03-09 18:30:00',0,2),(62,'shejiao','8cfa2282b17de0a598c010f5f0109e7d','1234567893','shejiao@126.com','12345','127.0.0.1','2013-04-07 09:50:35','127.0.0.1','2013-03-09 18:30:00',0,2),(63,'zhuoyou','8cfa2282b17de0a598c010f5f0109e7d','1234567894','zhuoyou@126.com','12345','127.0.0.1','2018-04-07 09:50:35','127.0.0.1','2018-03-09 18:30:00',0,1),(64,'yumaoqiu','8cfa2282b17de0a598c010f5f0109e7d','1234567893','yumaoqiu@126.com','12345','127.0.0.1','2013-05-07 09:50:35','127.0.0.1','2013-04-09 18:30:00',0,1),(65,'pingpangqiu','8cfa2282b17de0a598c010f5f0109e7d','1234567894','pingpangqiu@126.com','12345','127.0.0.1','2018-07-07 09:50:35','127.0.0.1','2018-07-09 18:30:00',0,1),(66,'2078673367@qq.com','cde7f551f3ba21118a1d1eab70ea7411','','2078673367@qq.com','MLNggwQgL6KIe5RIfoXKCA==','127.0.0.1','2018-04-16 21:15:03','127.0.0.1','2018-04-16 21:12:06',0,1),(67,'apone','8531af7b93029a83bb27d390c1178bc8','18316821839','s12344@126.com','sj48NIIZy4yOHpJXemdcRQ==','127.0.0.1','2018-04-25 10:36:44','127.0.0.1','2018-04-16 21:21:15',1,1),(68,'2078697336@qq.com','ebdb485a885fc0e2a6ef414da5a8d810','','2078697336@qq.com','vdLNHryaIBK+hRfsfx4s2g==','127.0.0.1','2018-07-07 11:37:02','127.0.0.1','2018-07-06 15:06:03',0,1),(69,'apone2','b6762db4df39895f2b56d5388f25feec','18316821839','s19961234@126.com','oC7Zmw2aeTem2ePGP4f1Tw==','127.0.0.1','2018-07-06 15:31:44','127.0.0.1','2018-07-06 15:26:53',1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
