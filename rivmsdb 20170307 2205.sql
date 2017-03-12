-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.41-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema rivmsdb
--

CREATE DATABASE IF NOT EXISTS rivmsdb;
USE rivmsdb;

--
-- Definition of table `acl_class`
--

DROP TABLE IF EXISTS `acl_class`;
CREATE TABLE `acl_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acl_class`
--

/*!40000 ALTER TABLE `acl_class` DISABLE KEYS */;
INSERT INTO `acl_class` (`id`,`class`) VALUES 
 (1,'org.gaixie.micrite.beans.Role');
/*!40000 ALTER TABLE `acl_class` ENABLE KEYS */;


--
-- Definition of table `acl_entry`
--

DROP TABLE IF EXISTS `acl_entry`;
CREATE TABLE `acl_entry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ace_order` int(11) NOT NULL,
  `audit_failure` bit(1) NOT NULL,
  `audit_success` bit(1) NOT NULL,
  `granting` bit(1) NOT NULL,
  `mask` int(11) NOT NULL,
  `acl_object_identity` bigint(20) NOT NULL,
  `sid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5302D47DD753D41B` (`sid`),
  KEY `FK5302D47DB467E947` (`acl_object_identity`),
  CONSTRAINT `FK5302D47DB467E947` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identity` (`id`),
  CONSTRAINT `FK5302D47DD753D41B` FOREIGN KEY (`sid`) REFERENCES `acl_sid` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acl_entry`
--

/*!40000 ALTER TABLE `acl_entry` DISABLE KEYS */;
INSERT INTO `acl_entry` (`id`,`ace_order`,`audit_failure`,`audit_success`,`granting`,`mask`,`acl_object_identity`,`sid`) VALUES 
 (1,1,0x00,0x00,0x01,16,1,1),
 (2,1,0x00,0x00,0x01,16,2,1),
 (3,2,0x00,0x00,0x01,16,2,2),
 (4,1,0x00,0x00,0x01,16,3,1);
/*!40000 ALTER TABLE `acl_entry` ENABLE KEYS */;


--
-- Definition of table `acl_object_identity`
--

DROP TABLE IF EXISTS `acl_object_identity`;
CREATE TABLE `acl_object_identity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `entries_inheriting` bit(1) NOT NULL,
  `object_id_identity` bigint(20) NOT NULL,
  `object_id_class` bigint(20) NOT NULL,
  `owner_sid` bigint(20) DEFAULT NULL,
  `parent_object` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2A2BB009A8909DB2` (`parent_object`),
  KEY `FK2A2BB009BAD4BE8B` (`object_id_class`),
  KEY `FK2A2BB009D864648F` (`owner_sid`),
  CONSTRAINT `FK2A2BB009A8909DB2` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identity` (`id`),
  CONSTRAINT `FK2A2BB009BAD4BE8B` FOREIGN KEY (`object_id_class`) REFERENCES `acl_class` (`id`),
  CONSTRAINT `FK2A2BB009D864648F` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sid` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acl_object_identity`
--

/*!40000 ALTER TABLE `acl_object_identity` DISABLE KEYS */;
INSERT INTO `acl_object_identity` (`id`,`entries_inheriting`,`object_id_identity`,`object_id_class`,`owner_sid`,`parent_object`) VALUES 
 (1,0x01,1,1,1,NULL),
 (2,0x01,2,1,1,NULL),
 (3,0x01,3,1,1,NULL);
/*!40000 ALTER TABLE `acl_object_identity` ENABLE KEYS */;


--
-- Definition of table `acl_sid`
--

DROP TABLE IF EXISTS `acl_sid`;
CREATE TABLE `acl_sid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` bit(1) NOT NULL,
  `sid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acl_sid`
--

/*!40000 ALTER TABLE `acl_sid` DISABLE KEYS */;
INSERT INTO `acl_sid` (`id`,`principal`,`sid`) VALUES 
 (1,0x00,'ROLE_ADMIN'),
 (2,0x00,'ROLE_USER');
/*!40000 ALTER TABLE `acl_sid` ENABLE KEYS */;


--
-- Definition of table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `order1` int(10) unsigned NOT NULL DEFAULT '0',
  `order2` int(10) unsigned NOT NULL DEFAULT '0',
  `iconCls1` varchar(255) DEFAULT NULL,
  `iconCls2` varchar(255) DEFAULT NULL,
  `state` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `authorities`
--

/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` (`id`,`name`,`type`,`value`,`order1`,`order2`,`iconCls1`,`iconCls2`,`state`) VALUES 
 (1,'All URL','URL','/**',0,0,NULL,NULL,0),
 (2,'/系统管理/用户列表','URL','/security/userList.js*',4,13,NULL,'icon_m2010_user',0),
 (3,'/系统管理/资源列表','URL','/security/authorityList.js*',4,15,NULL,'icon_m2010_auth',0),
 (4,'/系统管理/角色列表','URL','/security/roleList.js*',4,14,NULL,'icon_m2010_role',0),
 (5,'/基础数据/车主列表','URL','/crm/carownerList.js*',1,2,NULL,'icon_m2010_owner',1),
 (6,'查询角色列表的权力','METHOD','* org.gaixie.micrite.security.service.IRoleService.*PerPage(..)',0,0,NULL,NULL,0),
 (7,'为用户绑定角色的权力','METHOD','* org.gaixie.micrite.security.service.I*Service.*bind*(..)',0,0,NULL,NULL,0),
 (8,'为用户解除角色绑定的权力','METHOD','* org.gaixie.micrite.security.service.I*Service.*unBind*(..)',0,0,NULL,NULL,0),
 (9,'security模块中的新增记录的权力','METHOD','* org.gaixie.micrite.security.service.I*Service.add*(..)',0,0,NULL,NULL,0),
 (10,'security模块中的记录修改的权力','METHOD','* org.gaixie.micrite.security.service.I*Service.update*(..)',0,0,NULL,NULL,0),
 (11,'security模块中的删除记录的权力','METHOD','* org.gaixie.micrite.security.service.I*Service.delete*(..)',0,0,NULL,NULL,0),
 (12,'更新自己资料的权力','METHOD','* org.gaixie.micrite.security.service.IUserService.updateMe(..)',0,0,NULL,NULL,0),
 (13,'/基础数据/车辆列表','URL','/car/carfileList.js*',1,1,NULL,'icon_m2010_car',1),
 (14,'/基础数据/企业列表','URL','/enterprise/enterpriseList.js*',1,3,NULL,'icon_m2010_ent',1),
 (15,'/巡河汇总管理/巡河汇总列表','URL','/patrolRiverSummary/patrolRiverSummaryList.js*',3,1,NULL,'icon_m2010_sta',0),
 (17,'/系统管理/数据导入','URL','/security/uploadForm.js*',4,2,NULL,'icon_m2010_upl',1),
 (18,'/二级维护/二级签证','URL','/car/maintainList.js*',2,1,NULL,'icon_m2010_mnt',1),
 (19,'/系统管理/系统参数','URL','/dic/dicList.js*',4,1,NULL,'icon_m2010_dic',0),
 (20,'/二级维护/处罚处理','URL','/car/punishOpt.js*',2,2,NULL,'icon_m2010_pun',1),
 (21,'行政审批不通过','METHOD','* org.gaixie.micrite.car.service.ICarfileService.ridofPunishment(..)',0,0,NULL,NULL,0),
 (22,'行政审批通过','METHOD','* org.gaixie.micrite.car.service.ICarfileService.exemptPunishment(..)',0,0,NULL,NULL,0),
 (23,'/系统管理/操作日志','URL','/car/carhistory.js*',4,26,NULL,'icon_m2010_carhis',0),
 (25,'/巡河汇总管理/行政审批','URL','/patrolRiverSummary/patrolRiverSummaryexemptOpt.js*',3,2,NULL,'icon_m2010_xmpt',0),
 (26,'/二级维护/检测站数据','URL','/check/checkList.js*',2,3,NULL,'icon_m2010_chk',1);
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;


--
-- Definition of table `car_dictionary`
--

DROP TABLE IF EXISTS `car_dictionary`;
CREATE TABLE `car_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `car_dictionary`
--

/*!40000 ALTER TABLE `car_dictionary` DISABLE KEYS */;
/*!40000 ALTER TABLE `car_dictionary` ENABLE KEYS */;


--
-- Definition of table `carfile`
--

DROP TABLE IF EXISTS `carfile`;
CREATE TABLE `carfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brandType` varchar(255) DEFAULT NULL COMMENT '品牌型号',
  `carRemark` varchar(255) DEFAULT NULL COMMENT '车辆备注',
  `createDate` datetime DEFAULT NULL COMMENT '记录创建时间',
  `evaluateCycle` int(11) NOT NULL DEFAULT '0' COMMENT '技术等级评定周期',
  `evaluateDate` datetime DEFAULT NULL COMMENT '技术等级评定有效期止',
  `fuelRank` int(11) NOT NULL DEFAULT '0' COMMENT '燃料类型',
  `licenseNumber` varchar(255) DEFAULT NULL COMMENT '车牌号码',
  `licenseType` int(11) NOT NULL DEFAULT '0' COMMENT '车牌类型',
  `loadTon` varchar(255) DEFAULT NULL COMMENT '载重载客',
  `maintainCycle` int(11) NOT NULL DEFAULT '0' COMMENT '二维周期',
  `maintainDate` datetime DEFAULT NULL COMMENT '二维日期',
  `paiSe` int(11) NOT NULL DEFAULT '0' COMMENT '车牌颜色',
  `skillRank` int(11) NOT NULL DEFAULT '0' COMMENT '技术等级',
  `carType` int(11) NOT NULL DEFAULT '0' COMMENT '车辆类型',
  `yingyunNo` varchar(255) DEFAULT NULL COMMENT '营运证号',
  `maintainDateEnd` datetime DEFAULT NULL COMMENT '二维有效期止',
  `printIndex1` int(11) NOT NULL DEFAULT '0' COMMENT '技术等级打印指针',
  `printIndex2` int(11) NOT NULL DEFAULT '0' COMMENT '二级维护打印指针',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '车辆状态',
  `notepad` varchar(255) DEFAULT NULL COMMENT '临时备注',
  `carStatus` int(10) NOT NULL DEFAULT '0' COMMENT '车辆状态',
  `createrId` int(11) NOT NULL DEFAULT '0' COMMENT '创建者',
  `editDate` datetime DEFAULT NULL COMMENT '修改时间',
  `editorId` int(11) NOT NULL DEFAULT '0' COMMENT '修改者',
  `expired` int(10) NOT NULL DEFAULT '0' COMMENT '二维超期',
  `approval` int(10) NOT NULL DEFAULT '0' COMMENT '审批',
  `owner` int(10) NOT NULL DEFAULT '0' COMMENT '车主',
  `daysToExpired` int(10) DEFAULT NULL COMMENT '离超期还有多少天',
  PRIMARY KEY (`id`),
  KEY `FK21056B5087DC110F` (`carType`),
  KEY `FK21056B50DD8C91FE` (`skillRank`),
  KEY `FK21056B50E768AE3C` (`maintainCycle`),
  KEY `FK21056B504FF7EFEE` (`evaluateCycle`),
  KEY `FK21056B5040A916FC` (`licenseType`),
  KEY `FK21056B50AA41AEE3` (`fuelRank`),
  KEY `FK21056B50A7DD9748` (`paiSe`),
  KEY `FK21056B501F70A97C` (`skillRank`),
  KEY `FK21056B50828D2E7A` (`licenseType`),
  KEY `FK21056B50294CC5BA` (`maintainCycle`),
  KEY `FK21056B50AF0C1C69` (`paiSe`),
  KEY `FK21056B50EC25C661` (`fuelRank`),
  KEY `FK21056B5091DC076C` (`evaluateCycle`),
  KEY `FK21056B50C9C0288D` (`carType`),
  KEY `FK_carfile_15` (`createrId`),
  KEY `FK_carfile_9` (`editorId`),
  KEY `FK_carfile_10` (`owner`),
  KEY `FK21056B50C341833B` (`owner`),
  KEY `Index_20` (`editDate`),
  CONSTRAINT `FK21056B501F70A97C` FOREIGN KEY (`skillRank`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK21056B50294CC5BA` FOREIGN KEY (`maintainCycle`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK21056B504FF7EFEE` FOREIGN KEY (`evaluateCycle`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK21056B50828D2E7A` FOREIGN KEY (`licenseType`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK21056B5087DC110F` FOREIGN KEY (`carType`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK21056B50A7DD9748` FOREIGN KEY (`paiSe`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK21056B50C341833B` FOREIGN KEY (`owner`) REFERENCES `carowner` (`id`),
  CONSTRAINT `FK21056B50EC25C661` FOREIGN KEY (`fuelRank`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK_carfile_10` FOREIGN KEY (`owner`) REFERENCES `carowner` (`id`),
  CONSTRAINT `FK_carfile_15` FOREIGN KEY (`createrId`) REFERENCES `userbase` (`id`),
  CONSTRAINT `FK_carfile_9` FOREIGN KEY (`editorId`) REFERENCES `userbase` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `carfile`
--

/*!40000 ALTER TABLE `carfile` DISABLE KEYS */;
INSERT INTO `carfile` (`id`,`brandType`,`carRemark`,`createDate`,`evaluateCycle`,`evaluateDate`,`fuelRank`,`licenseNumber`,`licenseType`,`loadTon`,`maintainCycle`,`maintainDate`,`paiSe`,`skillRank`,`carType`,`yingyunNo`,`maintainDateEnd`,`printIndex1`,`printIndex2`,`status`,`notepad`,`carStatus`,`createrId`,`editDate`,`editorId`,`expired`,`approval`,`owner`,`daysToExpired`) VALUES 
 (1,'这型号','erererefgfgfgfgfgf','2011-01-26 10:15:12',59,'2011-01-04 00:00:00',50,'浙B.12346',26,'12',60,'2011-01-25 00:00:00',65,23,14,'12345','2011-04-25 00:00:00',0,5,0,NULL,0,1,'2011-03-04 06:13:24',1,2,0,4,-2142),
 (2,'','','2011-01-26 16:24:15',58,'2011-01-05 00:00:00',50,'浙B.ER45',46,'',63,'2011-01-18 00:00:00',64,23,14,'','2011-05-18 00:00:00',0,0,0,NULL,2,1,'2011-01-29 15:17:25',1,2,0,1,109),
 (3,'','','2011-03-04 06:28:35',59,'2011-03-04 00:00:00',49,'浙B.',47,'5',60,'2011-03-04 00:00:00',64,23,12,'1234','2011-06-04 00:00:00',0,0,0,NULL,0,1,'2011-03-04 06:28:35',1,2,0,2,-2102),
 (4,'','','2011-03-04 06:29:47',58,'2011-03-04 00:00:00',49,'浙B.',46,'1234',60,'2011-03-04 00:00:00',65,22,14,'1234','2011-06-04 00:00:00',0,0,0,NULL,0,1,'2011-03-04 06:29:47',1,2,0,2,-2102),
 (5,'','','2011-03-04 09:41:47',58,'2011-09-04 00:00:00',50,'浙B.123',44,'12',63,'2011-03-09 00:00:00',64,24,13,'','2011-07-09 00:00:00',0,0,0,NULL,0,1,'2011-03-04 10:22:33',1,2,0,2,-2067),
 (6,'','','2011-03-04 10:23:11',59,'2011-03-04 00:00:00',50,'浙B.233',47,'',63,'2011-03-04 00:00:00',64,23,14,'','2011-07-04 00:00:00',0,0,0,NULL,0,1,'2011-03-04 10:23:11',1,2,0,2,-2072),
 (7,'','','2011-03-04 10:27:48',59,'2012-03-04 00:00:00',49,'浙B.1234',46,'12',63,'2011-03-16 00:00:00',64,23,14,'','2011-07-16 00:00:00',1,1,0,NULL,0,1,'2011-03-04 10:27:48',1,2,0,2,-2060),
 (8,'','xince','2011-03-07 15:43:55',59,'2011-03-07 00:00:00',50,'浙B.B1234',47,'1',63,'2011-03-07 00:00:00',65,22,14,'9876','2011-07-07 00:00:00',0,0,0,NULL,0,1,'2011-03-07 16:09:56',1,2,0,3,-2069),
 (9,'','','2011-03-07 16:41:48',58,'2011-03-07 00:00:00',50,'浙B.',45,'',63,'2011-03-07 00:00:00',66,22,14,'','2011-07-07 00:00:00',0,0,0,NULL,0,1,'2011-03-07 16:41:48',1,2,0,3,-2069),
 (10,'','','2011-03-10 08:45:34',59,'2011-03-10 00:00:00',49,'浙B.J0123',47,'12',63,'2011-04-10 00:00:00',65,22,14,'','2011-08-10 00:00:00',0,1,1,NULL,0,1,'2011-03-17 11:25:39',1,0,0,3,146),
 (11,'','','2011-03-10 09:03:50',58,'2012-03-12 00:00:00',50,'浙B.01234',46,'12',60,'2011-03-15 00:00:00',65,23,14,'12345','2011-06-15 00:00:00',1,3,0,NULL,0,1,'2011-03-15 15:15:10',1,2,0,3,-2091),
 (12,'','','2011-03-10 09:11:16',58,'2012-03-12 00:00:00',49,'浙B.01234',46,'12',60,'2011-03-16 00:00:00',64,23,14,'','2011-06-16 00:00:00',0,0,0,NULL,0,1,'2011-03-15 15:11:09',1,2,0,3,-2090),
 (13,'123','而 ','2011-03-14 07:29:12',58,'2011-03-14 00:00:00',49,'浙B.SU876',26,'5',60,'2011-03-14 00:00:00',66,22,162,'1234','2011-06-14 00:00:00',1,1,0,NULL,2,1,'2011-03-14 07:39:12',1,2,0,6,92),
 (14,'2345','','2011-03-14 07:33:20',58,'2011-03-14 00:00:00',49,'浙B.IZ755',47,'5',60,'2011-03-14 00:00:00',64,22,12,'1234567','2010-06-14 00:00:00',1,1,0,NULL,1,1,'2011-03-14 07:39:25',1,2,0,6,92),
 (15,'','本车不存在，仅作演示用。','2011-03-17 08:55:41',59,'2012-03-17 00:00:00',49,'浙B.12340',26,'5',60,'2011-03-17 00:00:00',66,22,1,'3456789012','2011-06-17 00:00:00',0,0,0,NULL,0,1,'2011-03-17 09:04:22',1,2,0,3,-2089);
/*!40000 ALTER TABLE `carfile` ENABLE KEYS */;


--
-- Definition of table `carowner`
--

DROP TABLE IF EXISTS `carowner`;
CREATE TABLE `carowner` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `py` varchar(45) DEFAULT NULL,
  `status` int(10) NOT NULL DEFAULT '0',
  `remark` varchar(450) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `createrId` int(11) NOT NULL DEFAULT '0',
  `editDate` datetime NOT NULL,
  `editorId` int(11) NOT NULL DEFAULT '0',
  `license` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_carowner_1` (`createrId`),
  KEY `FK_carowner_2` (`editorId`),
  KEY `Index_4` (`license`),
  CONSTRAINT `FK_carowner_1` FOREIGN KEY (`createrId`) REFERENCES `userbase` (`id`),
  CONSTRAINT `FK_carowner_2` FOREIGN KEY (`editorId`) REFERENCES `userbase` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='车主表';

--
-- Dumping data for table `carowner`
--

/*!40000 ALTER TABLE `carowner` DISABLE KEYS */;
INSERT INTO `carowner` (`id`,`name`,`address`,`telephone`,`mobile`,`py`,`status`,`remark`,`createDate`,`createrId`,`editDate`,`editorId`,`license`) VALUES 
 (0,'系统车主',NULL,NULL,NULL,'xt',0,'0','2010-01-01 00:00:00',0,'2010-01-01 00:00:00',1,''),
 (1,'张三','慈溪df','13456789','13211111111','zs',0,NULL,'2011-01-26 10:15:12',1,'2011-01-26 23:32:27',1,'123'),
 (2,'李四','你好','1234560','13211122222','ls',0,NULL,'2011-01-27 23:57:05',1,'2011-03-04 10:43:38',1,'123423'),
 (3,'李四四','的','12345676','13211111112','lss',0,NULL,'2011-01-30 13:37:30',1,'2011-03-15 15:06:49',1,'12345'),
 (4,'dsds','dsfsf','1234567678','13211111111','DSDS',0,NULL,'2011-03-04 06:13:08',1,'2011-03-04 06:13:08',1,'12343'),
 (5,'王五','浒山','65556666','13565556666','ww',0,NULL,'2011-03-14 07:29:12',1,'2011-03-14 07:29:12',1,'123456'),
 (6,'赵六','横河','12345678','13211111111','zl',0,NULL,'2011-03-14 07:33:20',1,'2011-03-14 07:33:20',1,'1234567');
/*!40000 ALTER TABLE `carowner` ENABLE KEYS */;


--
-- Definition of table `checknote`
--

DROP TABLE IF EXISTS `checknote`;
CREATE TABLE `checknote` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cheLei` varchar(255) DEFAULT NULL,
  `cheXiu` varchar(255) DEFAULT NULL,
  `cheZhu` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `heGe` int(11) DEFAULT NULL,
  `jianTime` datetime DEFAULT NULL,
  `testNo` int(11) DEFAULT NULL,
  `notepad` varchar(255) DEFAULT NULL,
  `paiSe` int(11) DEFAULT NULL,
  `paiHao` varchar(255) DEFAULT NULL,
  `printed` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `carId` int(11) NOT NULL,
  `isPost` int(11) NOT NULL,
  `testKind` int(11) NOT NULL,
  `endTime` datetime DEFAULT NULL,
  `cheDengji` int(10) unsigned NOT NULL DEFAULT '0',
  `createrId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK17CDD0FA2B773119` (`paiSe`),
  KEY `FK17CDD0FAAF0C1C69` (`paiSe`),
  KEY `FK17CDD0FAC406E805` (`createrId`),
  CONSTRAINT `FK17CDD0FA2B773119` FOREIGN KEY (`paiSe`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK17CDD0FAAF0C1C69` FOREIGN KEY (`paiSe`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK17CDD0FAC406E805` FOREIGN KEY (`createrId`) REFERENCES `userbase` (`id`),
  CONSTRAINT `FK_checknote_3` FOREIGN KEY (`createrId`) REFERENCES `userbase` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=568 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `checknote`
--

/*!40000 ALTER TABLE `checknote` DISABLE KEYS */;
INSERT INTO `checknote` (`id`,`cheLei`,`cheXiu`,`cheZhu`,`createDate`,`heGe`,`jianTime`,`testNo`,`notepad`,`paiSe`,`paiHao`,`printed`,`status`,`carId`,`isPost`,`testKind`,`endTime`,`cheDengji`,`createrId`) VALUES 
 (483,NULL,NULL,'李四','2011-03-04 06:28:35',NULL,NULL,0,'',64,'浙B.',0,3,0,2,94,NULL,0,1),
 (484,NULL,NULL,NULL,'2011-03-04 06:28:35',NULL,NULL,NULL,NULL,64,'浙B.',0,0,3,0,1,'2011-03-04 00:00:00',23,1),
 (485,NULL,NULL,NULL,'2011-03-04 06:28:35',NULL,'2011-03-04 00:00:00',NULL,NULL,64,'浙B.',0,0,3,1,2,'2011-06-04 00:00:00',0,1),
 (486,NULL,NULL,'李四','2011-03-04 06:29:47',NULL,NULL,0,'',65,'浙B.',0,4,0,2,94,NULL,0,1),
 (487,NULL,NULL,NULL,'2011-03-04 06:29:47',NULL,NULL,NULL,NULL,65,'浙B.',0,0,4,1,1,'2011-03-04 00:00:00',22,1),
 (488,NULL,NULL,NULL,'2011-03-04 06:29:47',NULL,'2011-03-04 00:00:00',NULL,NULL,65,'浙B.',0,0,4,0,2,'2011-06-04 00:00:00',0,1),
 (489,'大货','众鑫','新城建筑公司','2011-03-04 09:37:43',544,'2010-12-27 00:00:00',-26141,NULL,65,'BB7571',0,1,0,0,1,NULL,0,0),
 (490,NULL,NULL,'李四','2011-03-04 09:41:47',NULL,NULL,0,'',64,'浙B.123',0,5,0,2,94,NULL,0,1),
 (491,NULL,NULL,NULL,'2011-03-04 09:41:47',NULL,NULL,NULL,NULL,64,'浙B.123',0,0,5,1,1,'2011-03-04 00:00:00',22,1),
 (492,NULL,NULL,NULL,'2011-03-04 09:41:47',NULL,'2011-03-04 00:00:00',NULL,NULL,64,'浙B.123',0,0,5,1,2,'2011-07-04 00:00:00',0,1),
 (493,NULL,NULL,'李四','2011-03-04 09:43:14',NULL,NULL,NULL,'一级->二级',64,'浙B.123',0,5,0,2,11,NULL,0,1),
 (494,NULL,NULL,'李四','2011-03-04 10:22:33',NULL,NULL,NULL,'二级->三级',64,'浙B.123',0,5,0,2,11,NULL,0,1),
 (495,NULL,NULL,'李四','2011-03-04 10:23:11',NULL,NULL,0,'',64,'浙B.233',0,6,0,2,94,NULL,0,1),
 (496,NULL,NULL,NULL,'2011-03-04 10:23:11',NULL,NULL,NULL,NULL,64,'浙B.233',0,0,6,1,1,'2011-03-04 00:00:00',0,1),
 (497,NULL,NULL,NULL,'2011-03-04 10:23:11',NULL,'2011-03-04 00:00:00',NULL,NULL,64,'浙B.233',0,0,6,1,2,'2011-07-04 00:00:00',0,1),
 (498,NULL,NULL,'李四','2011-03-04 10:27:48',NULL,NULL,0,'',64,'浙B.1234',0,7,0,2,94,NULL,0,1),
 (499,NULL,NULL,NULL,'2011-03-04 10:27:48',NULL,NULL,NULL,NULL,64,'浙B.1234',1,0,7,1,1,'2011-03-04 00:00:00',2,1),
 (500,NULL,NULL,NULL,'2011-03-04 10:27:48',NULL,'2011-03-04 00:00:00',NULL,NULL,64,'浙B.1234',1,0,7,1,2,'2011-07-04 00:00:00',0,1),
 (501,NULL,'你好','李四','2011-03-04 10:43:38',NULL,NULL,NULL,'1234->123423',NULL,NULL,0,2,0,4,4,NULL,0,1),
 (502,'大货','众鑫','新城建筑公司','2011-03-07 10:06:26',544,'2011-12-02 00:00:00',-26141,NULL,65,'B12346',0,0,1,0,1,NULL,0,0),
 (503,'大货','众鑫','新城建筑公司','2011-03-07 10:07:10',544,'2011-03-27 00:00:00',-26141,NULL,65,'B12346',0,0,1,0,1,NULL,0,0),
 (504,'大货','众鑫','新城建筑公司','2011-03-07 10:17:38',544,'2011-03-21 00:00:00',-26141,NULL,65,'B1234689',0,0,0,0,1,NULL,0,0),
 (505,NULL,NULL,'李四四','2011-03-07 15:43:55',NULL,NULL,0,'',65,'浙B.B1234',0,8,0,2,94,NULL,0,1),
 (506,NULL,NULL,NULL,'2011-03-07 15:43:55',NULL,NULL,NULL,NULL,65,'浙B.B1234',0,0,8,1,1,'2011-03-07 00:00:00',1,1),
 (507,NULL,NULL,NULL,'2011-03-07 15:43:55',NULL,'2011-03-07 00:00:00',NULL,NULL,65,'浙B.B1234',0,0,8,1,2,'2011-07-07 00:00:00',0,1),
 (508,NULL,NULL,'李四四','2011-03-07 16:09:56',NULL,NULL,NULL,'->xince',65,'浙B.B1234',0,8,0,2,9,NULL,0,1),
 (509,NULL,NULL,'李四四','2011-03-07 16:09:56',NULL,NULL,NULL,'->9876',65,'浙B.B1234',0,8,0,2,14,NULL,0,1),
 (510,NULL,NULL,'李四四','2011-03-07 16:41:48',NULL,NULL,0,'',66,'浙B.',0,9,0,2,94,NULL,0,1),
 (511,NULL,NULL,NULL,'2011-03-07 16:41:48',NULL,NULL,NULL,NULL,66,'浙B.',0,0,9,1,1,'2011-03-07 00:00:00',1,1),
 (512,NULL,NULL,NULL,'2011-03-07 16:41:48',NULL,'2011-03-07 00:00:00',NULL,NULL,66,'浙B.',0,0,9,1,2,'2011-07-07 00:00:00',0,1),
 (513,NULL,NULL,'李四四','2011-03-10 08:45:34',NULL,NULL,0,'',65,'浙B.J0123',0,10,0,2,94,NULL,0,1),
 (514,NULL,NULL,NULL,'2011-03-10 08:45:34',NULL,NULL,NULL,NULL,65,'浙B.J0123',0,0,10,1,1,'2011-03-10 00:00:00',1,1),
 (515,NULL,NULL,NULL,'2011-03-10 08:45:34',NULL,'2011-03-10 00:00:00',NULL,NULL,65,'浙B.J0123',1,0,10,1,2,'2011-07-10 00:00:00',0,1),
 (516,NULL,NULL,'李四四','2011-03-10 09:03:50',NULL,NULL,0,'',65,'浙B.01234',0,11,0,2,94,NULL,0,1),
 (517,NULL,NULL,NULL,'2011-03-10 09:03:50',NULL,'2011-03-10 00:00:00',NULL,NULL,65,'浙B.01234',1,0,11,1,1,'2011-03-10 00:00:00',1,1),
 (518,NULL,NULL,NULL,'2011-03-10 09:03:50',NULL,'2011-03-10 00:00:00',NULL,NULL,65,'浙B.01234',1,0,11,1,2,'2011-06-10 00:00:00',0,1),
 (519,NULL,NULL,'李四四','2011-03-10 09:11:16',NULL,NULL,0,'',64,'浙B.01234',0,12,0,2,94,NULL,0,1),
 (520,NULL,NULL,NULL,'2011-03-10 09:11:16',NULL,'2011-03-10 00:00:00',NULL,NULL,64,'浙B.01234',0,0,12,1,1,'2011-09-10 00:00:00',2,1),
 (521,NULL,NULL,NULL,'2011-03-10 09:11:16',NULL,'2011-03-10 00:00:00',NULL,NULL,64,'浙B.01234',0,0,12,1,2,'2011-06-10 00:00:00',0,1),
 (522,NULL,'浒山','王五','2011-03-14 07:29:12',NULL,NULL,NULL,'',NULL,NULL,0,5,0,4,94,NULL,0,1),
 (523,NULL,NULL,'王五','2011-03-14 07:29:12',NULL,NULL,0,'',66,'浙B.SU875',0,13,0,2,94,NULL,0,1),
 (524,NULL,NULL,NULL,'2011-03-14 07:29:12',NULL,'2011-03-14 00:00:00',NULL,NULL,66,'浙B.SU875',1,0,13,1,1,'2011-09-14 00:00:00',1,1),
 (525,NULL,NULL,NULL,'2011-03-14 07:29:12',NULL,'2011-03-14 00:00:00',NULL,NULL,66,'浙B.SU875',1,0,13,1,2,'2011-06-14 00:00:00',0,1),
 (526,NULL,'横河','赵六','2011-03-14 07:33:20',NULL,NULL,NULL,'',NULL,NULL,0,6,0,4,94,NULL,0,1),
 (527,NULL,NULL,'赵六','2011-03-14 07:33:20',NULL,NULL,0,'',64,'浙B.IZ753',0,14,0,2,94,NULL,0,1),
 (528,NULL,NULL,NULL,'2011-03-14 07:33:20',NULL,'2011-03-14 00:00:00',NULL,NULL,64,'浙B.IZ753',1,0,14,1,1,'2011-09-14 00:00:00',1,1),
 (529,NULL,NULL,NULL,'2011-03-14 07:33:20',NULL,'2011-03-14 00:00:00',NULL,NULL,64,'浙B.IZ753',1,0,14,1,2,'2011-06-14 00:00:00',0,1),
 (530,NULL,NULL,'王五','2011-03-14 07:38:31',NULL,NULL,5,'王五->赵六',66,'浙B.SU875',6,13,0,2,91,NULL,0,1),
 (531,NULL,NULL,'王五','2011-03-14 07:38:31',NULL,NULL,NULL,'浙B.SU875->浙B.SU876',66,'浙B.SU875',0,13,0,2,4,NULL,0,1),
 (532,NULL,NULL,'赵六','2011-03-14 07:39:12',NULL,NULL,0,'',66,'浙B.SU876',0,13,0,2,92,NULL,0,1),
 (533,NULL,NULL,'赵六','2011-03-14 07:39:25',NULL,NULL,0,'',64,'浙B.IZ753',0,14,0,2,93,NULL,0,1),
 (535,NULL,NULL,'李四四','2011-03-14 09:42:34',NULL,NULL,NULL,'回单:123456789',64,'浙B.01234',0,12,0,2,101,'2011-02-10 00:00:00',0,1),
 (536,NULL,NULL,'李四四','2011-03-15 15:06:20',NULL,NULL,NULL,'->12345',65,'浙B.01234',0,11,0,2,14,NULL,0,1),
 (537,NULL,'的','李四四','2011-03-15 15:06:49',NULL,NULL,NULL,'13211111111->13211111112',NULL,NULL,0,3,0,4,16,NULL,0,1),
 (538,NULL,NULL,'李四四','2011-03-15 15:07:09',NULL,NULL,NULL,'一级->二级',65,'浙B.01234',0,11,0,2,11,NULL,0,1),
 (539,NULL,NULL,'李四四','2011-03-15 15:07:43',NULL,NULL,NULL,'2011-03-10->2011-04-10',65,'浙B.J0123',0,10,0,2,16,NULL,0,1),
 (540,NULL,NULL,'李四四','2011-03-15 15:08:45',NULL,NULL,NULL,'2011-02-10->2011-03-10',64,'浙B.01234',0,12,0,2,16,NULL,0,1),
 (541,NULL,NULL,'李四四','2011-03-15 15:10:14',NULL,NULL,NULL,'2011-03-10->2011-02-10',64,'浙B.01234',0,12,0,2,16,NULL,0,1),
 (542,NULL,NULL,'李四四','2011-03-15 15:11:08',NULL,NULL,NULL,'2011-02-10->2010-02-10',64,'浙B.01234',0,12,0,2,16,NULL,0,1),
 (543,NULL,NULL,'李四四','2011-03-15 15:15:10',NULL,NULL,NULL,'经行政审批同意免除该车本次处罚。',65,'浙B.01234',0,11,0,2,102,'2010-06-10 00:00:00',0,1),
 (544,NULL,'众鑫',NULL,'2011-03-15 15:17:38',544,'2011-03-15 00:00:00',0,NULL,65,'浙B.01234',1,0,11,1,1,'2011-09-15 00:00:00',2,1),
 (545,NULL,'众鑫',NULL,'2011-03-15 15:17:38',544,'2011-03-15 00:00:00',NULL,NULL,65,'浙B.01234',1,0,11,1,2,'2011-06-15 00:00:00',0,1),
 (546,NULL,'众鑫',NULL,'2011-03-15 15:34:44',544,'2011-03-15 00:00:00',0,NULL,65,'浙B.01234',0,0,11,1,1,'2011-09-15 00:00:00',1,1),
 (547,NULL,'众鑫',NULL,'2011-03-15 15:34:44',544,'2011-03-15 00:00:00',NULL,NULL,65,'浙B.01234',0,0,11,1,2,'2011-06-15 00:00:00',0,1),
 (548,NULL,'慈溪市进口汽车修理厂',NULL,'2011-03-15 15:58:39',1024242,'2011-03-15 00:00:00',NULL,NULL,65,'浙B.01234',0,0,11,1,2,'2011-06-15 00:00:00',0,1),
 (549,NULL,'众鑫',NULL,'2011-03-16 08:40:51',544,'2011-03-16 00:00:00',0,NULL,64,'浙B.1234',0,0,7,1,1,'2012-03-16 00:00:00',2,1),
 (550,NULL,'众鑫',NULL,'2011-03-16 08:40:51',544,'2011-03-16 00:00:00',NULL,NULL,64,'浙B.1234',0,0,7,1,2,'2011-07-16 00:00:00',0,1),
 (551,NULL,'众鑫',NULL,'2011-03-16 08:41:38',544,'2010-12-27 00:00:00',0,NULL,64,'浙B.01234',0,489,12,1,1,'2011-06-27 00:00:00',2,1),
 (552,NULL,'众鑫',NULL,'2011-03-16 08:41:38',544,'2010-12-27 00:00:00',NULL,NULL,64,'浙B.01234',0,489,12,1,2,'2011-03-27 00:00:00',0,1),
 (553,NULL,'众鑫',NULL,'2011-03-16 08:42:07',544,'2011-03-16 00:00:00',0,NULL,64,'浙B.01234',0,0,12,1,1,'2011-09-16 00:00:00',2,1),
 (554,NULL,'众鑫',NULL,'2011-03-16 08:42:07',544,'2011-03-16 00:00:00',NULL,NULL,64,'浙B.01234',0,0,12,1,2,'2011-06-16 00:00:00',0,1),
 (555,NULL,'众鑫',NULL,'2011-03-16 08:42:50',544,'2011-03-09 00:00:00',0,NULL,64,'浙B.123',0,0,5,1,1,'2011-09-09 00:00:00',3,1),
 (556,NULL,'众鑫',NULL,'2011-03-16 08:42:50',544,'2011-03-09 00:00:00',NULL,NULL,64,'浙B.123',0,0,5,1,2,'2011-07-09 00:00:00',0,1),
 (557,NULL,NULL,'李四四','2011-03-17 08:55:41',NULL,NULL,0,'',66,'浙B.12345',0,15,0,2,94,NULL,0,1),
 (558,NULL,NULL,NULL,'2011-03-17 08:55:41',NULL,'2011-03-17 00:00:00',NULL,NULL,66,'浙B.12345',0,0,15,1,1,'2012-03-17 00:00:00',1,1),
 (559,NULL,NULL,NULL,'2011-03-17 08:55:41',NULL,'2011-03-17 00:00:00',NULL,NULL,66,'浙B.12345',0,0,15,1,2,'2011-06-17 00:00:00',0,1),
 (560,NULL,NULL,'李四四','2011-03-17 09:04:22',NULL,NULL,NULL,'5->4',66,'浙B.12345',0,15,0,2,7,NULL,0,1),
 (561,NULL,NULL,'李四四','2011-03-17 09:38:13',NULL,NULL,0,'',66,'浙B.12345',0,16,0,2,94,NULL,0,1),
 (562,NULL,NULL,NULL,'2011-03-17 09:38:13',NULL,'2011-03-17 00:00:00',NULL,NULL,66,'浙B.12345',0,0,16,1,1,'2012-03-17 00:00:00',1,1),
 (563,NULL,NULL,NULL,'2011-03-17 09:38:13',NULL,'2011-03-17 00:00:00',NULL,NULL,66,'浙B.12345',0,0,16,1,2,'2011-06-17 00:00:00',0,1),
 (564,NULL,NULL,'李四四','2011-03-17 11:25:39',NULL,NULL,0,'',65,'浙B.J0123',0,10,0,2,95,NULL,0,1),
 (565,NULL,NULL,'慈溪市美鹰汽车维修服务中心','2017-03-03 20:51:45',NULL,NULL,NULL,'未选->观城',NULL,NULL,0,885,0,3,19,NULL,0,1),
 (566,NULL,NULL,'慈溪市美鹰汽车维修服务中心','2017-03-03 20:51:45',NULL,NULL,NULL,'->12312321312',NULL,NULL,0,885,0,3,3,NULL,0,1),
 (567,NULL,NULL,'慈溪市美鹰汽车维修服务中心','2017-03-03 20:51:45',NULL,NULL,NULL,'   ->测试',NULL,NULL,0,885,0,3,17,NULL,0,1);
/*!40000 ALTER TABLE `checknote` ENABLE KEYS */;


--
-- Definition of table `customer_source`
--

DROP TABLE IF EXISTS `customer_source`;
CREATE TABLE `customer_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customer_source`
--

/*!40000 ALTER TABLE `customer_source` DISABLE KEYS */;
INSERT INTO `customer_source` (`id`,`name`) VALUES 
 (1,'游客'),
 (2,'朋友');
/*!40000 ALTER TABLE `customer_source` ENABLE KEYS */;


--
-- Definition of table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_ts` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `customer_source_id` int(11) DEFAULT NULL,
  `testDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK600E7C55268318A0` (`customer_source_id`),
  CONSTRAINT `FK600E7C55268318A0` FOREIGN KEY (`customer_source_id`) REFERENCES `customer_source` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customers`
--

/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` (`id`,`creation_ts`,`name`,`telephone`,`customer_source_id`,`testDate`) VALUES 
 (1,'2010-11-09 10:41:06','Bob Yu','139000000',1,'2010-11-09 10:41:06');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;


--
-- Definition of table `dictionary`
--

DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` int(10) unsigned NOT NULL DEFAULT '0',
  `value` int(10) unsigned NOT NULL DEFAULT '0',
  `iSshow` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`type`,`value`),
  UNIQUE KEY `Index_3` (`name`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dictionary`
--

/*!40000 ALTER TABLE `dictionary` DISABLE KEYS */;
INSERT INTO `dictionary` (`id`,`name`,`type`,`value`,`iSshow`) VALUES 
 (0,'未选',0,0,0),
 (1,'普通货车',1,1,0),
 (2,'自卸车',1,2,0),
 (3,'工程自卸车',1,3,0),
 (4,'厢式货车',1,4,0),
 (5,'专用货车',1,5,0),
 (6,'保温冷藏车',1,6,0),
 (7,'商品车运输专用车辆',1,7,0),
 (8,'牵引车',1,8,0),
 (9,'集装箱牵引车',1,9,0),
 (10,'挂车',1,10,0),
 (11,'集装箱挂车',1,11,0),
 (12,'栏板式挂车',1,12,0),
 (13,'厢式挂车',1,13,0),
 (14,'罐式挂车',1,14,0),
 (15,'平板挂车',1,15,0),
 (16,'农用车',1,16,0),
 (17,'拖拉机',1,17,0),
 (18,'变型式拖拉机',1,18,0),
 (19,'手扶拖拉机',1,19,0),
 (20,'大型拖拉机',1,20,0),
 (21,'特种车',1,21,0),
 (22,'一级',2,1,0),
 (23,'二级',2,2,0),
 (24,'三级',2,3,0),
 (25,'大型汽车',3,1,0),
 (26,'小型汽车',3,2,0),
 (27,'挂车',3,3,0),
 (28,'领馆汽车',3,4,0),
 (29,'境外汽车',3,5,0),
 (30,'外籍汽车',3,6,0),
 (31,'两、三轮摩托车',3,7,1),
 (32,'轻便摩托车',3,8,1),
 (33,'使馆摩托车',3,9,1),
 (34,'领馆摩托车',3,10,0),
 (35,'境外摩托车',3,11,1),
 (36,'外籍摩托车',3,12,1),
 (37,'农用运输车',3,13,1),
 (38,'拖拉机',3,14,0),
 (39,'使馆汽车',3,15,0),
 (40,'教练汽车',3,16,0),
 (41,'教练摩托车',3,17,1),
 (42,'试验汽车',3,18,1),
 (43,'试验摩托车',3,19,1),
 (44,'临时入境汽车',3,20,0),
 (45,'临时入境摩托车',3,21,0),
 (46,'临时行使车',3,22,0),
 (47,'警用汽车',3,23,0),
 (48,'其他',3,24,0),
 (49,'汽油',4,1,0),
 (50,'柴油',4,2,0),
 (51,'电动',4,3,0),
 (52,'液化石油气（LPG）',4,4,0),
 (53,'液化天然气（LNG）',4,5,0),
 (54,'双燃料(柴加气)',4,6,0),
 (55,'双燃料(汽加气)',4,7,0),
 (56,'双燃料(电加气)',4,8,0),
 (57,'其它',4,9,0),
 (58,'六个月',5,6,0),
 (59,'一年',5,12,0),
 (60,'三个月',6,3,0),
 (61,'六个月',6,6,0),
 (62,'一年',6,12,0),
 (63,'四个月',6,4,0),
 (64,'黑色',7,1,0),
 (65,'黄色',7,2,0),
 (66,'蓝色',7,3,0),
 (67,'白色',7,4,0),
 (68,'其它',7,5,0),
 (69,'维修一类',8,1,0),
 (70,'维修二类',8,2,0),
 (71,'维修三类',8,3,0),
 (72,'内资',9,1,0),
 (73,'国有全资',9,2,0),
 (74,'集体全资',9,3,0),
 (75,'股份合作',9,4,0),
 (76,'联营',9,5,0),
 (77,'国有联营',9,6,0),
 (78,'集体联营',9,7,0),
 (79,'国有和集体联营',9,8,0),
 (80,'其他联营',9,9,0),
 (81,'有限责任（公司）',9,10,0),
 (82,'国有独资（公司）',9,11,0),
 (83,'其他有限责任（公司）',9,12,0),
 (84,'股份有限（公司）',9,13,0),
 (85,'私有',9,14,0),
 (86,'私有独资',9,15,0),
 (87,'私有合伙',9,16,0),
 (88,'私营有限责任（公司）',9,17,0),
 (89,'私营股份有限（公司）',9,18,0),
 (90,'个体经营',9,19,0),
 (91,'其他私有',9,20,0),
 (92,'其他内资',9,21,0),
 (93,'港、澳、台投资',9,22,0),
 (94,'内地和港、澳、台合资',9,23,0),
 (95,'内地和港、澳、台合作',9,24,0),
 (96,'港、澳、台独资',9,25,0),
 (97,'港、澳、台投资股份有限公司\r\n',9,26,0),
 (98,'其他港、澳、台投资',9,27,0),
 (99,'国外投资',9,28,0),
 (100,'中外合资',9,29,0),
 (101,'中外合作',9,30,0),
 (102,'外资',9,31,0),
 (103,'国外投资股份有限（公司 ）',9,32,0),
 (104,'其他国外投资',9,33,0),
 (105,'营业',10,1,0),
 (107,'龙山',11,1,0),
 (108,'观城',11,2,0),
 (109,'浒山',11,3,0),
 (110,'周巷',11,4,0),
 (111,'二级维护允许超期天数',12,10,0),
 (112,'技术等级',13,1,0),
 (113,'二级维护',13,2,0),
 (114,'停业',10,2,0),
 (115,'整改',10,3,0),
 (116,'停业整顿',10,4,0),
 (117,'歇业',10,5,0),
 (118,'注销',10,6,0),
 (119,'营运',14,0,0),
 (120,'报废',14,1,0),
 (121,'转籍',14,2,0),
 (123,'车牌所属业户',17,1,0),
 (124,'业户联系电话',17,2,0),
 (125,'业户地址',17,3,0),
 (126,'车牌号码',15,4,0),
 (127,'车辆类型',15,5,0),
 (128,'车牌型号',15,6,0),
 (129,'核载载客',15,7,0),
 (130,'品牌型号',15,8,0),
 (131,'车辆备注',15,9,0),
 (132,'燃油型号',15,10,0),
 (133,'技术等级',15,11,0),
 (134,'技术评定周期',15,12,0),
 (135,'二维周期',15,13,0),
 (136,'营运证号',15,14,0),
 (137,'车牌颜色',15,15,0),
 (138,'车主手机',17,16,0),
 (139,'营运状态',15,17,0),
 (140,'单位名称',16,1,0),
 (141,'法人代表',16,2,0),
 (142,'法人手机 ',16,3,0),
 (143,'许可证号',16,4,0),
 (144,'经营资质 ',16,5,0),
 (145,'经办人',16,6,0),
 (146,'经办人电话 ',16,7,0),
 (147,'负责人手机',16,8,0),
 (148,'委托人',16,9,0),
 (149,'委托人电话',16,10,0),
 (150,'经济性质',16,11,0),
 (151,'许可证发放日期',16,12,0),
 (152,'有效日期开始',16,13,0),
 (153,'有效日期结束',16,14,0),
 (154,'注册地址',16,15,0),
 (155,'经营范围',16,16,0),
 (156,'经营范围备注',16,17,0),
 (157,'经营状态',16,18,0),
 (158,'所属管辖站',16,19,0),
 (159,'超期处罚',15,101,0),
 (160,'行政审批',15,102,0),
 (161,'过户',15,91,0),
 (162,'教练车',1,22,0),
 (163,'经营许可证号',17,4,0),
 (164,'四级',2,4,1),
 (165,'转籍',15,92,0),
 (166,'报废',15,93,0),
 (167,'二维日期',15,16,0),
 (168,'技术等级有效期止',15,18,0),
 (169,'新增',15,94,0),
 (170,'删除',15,95,0),
 (171,'新增',16,94,0),
 (172,'删除',16,95,0),
 (173,'新增',17,94,0),
 (174,'删除',17,95,0);
/*!40000 ALTER TABLE `dictionary` ENABLE KEYS */;


--
-- Definition of table `enterprises`
--

DROP TABLE IF EXISTS `enterprises`;
CREATE TABLE `enterprises` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL COMMENT '注册地址',
  `commission` varchar(255) DEFAULT NULL COMMENT '委托人',
  `createDate` datetime DEFAULT NULL COMMENT '记录产生时间-设计需要',
  `dateBegin` datetime DEFAULT NULL COMMENT '有效日期开始',
  `editDate` datetime DEFAULT NULL COMMENT '修改日期',
  `dateEnd` datetime DEFAULT NULL COMMENT '有效日期结束',
  `handleMan` varchar(255) DEFAULT NULL COMMENT '经办人',
  `kind` int(11) DEFAULT NULL COMMENT '经济性质',
  `legalPerson` varchar(255) DEFAULT NULL COMMENT '法人代表',
  `license` varchar(255) DEFAULT NULL COMMENT '许可证号',
  `licenseDate` datetime DEFAULT NULL COMMENT '许可证发放日期',
  `qualification` int(11) DEFAULT NULL COMMENT '经营资质',
  `telephone1` varchar(255) DEFAULT NULL COMMENT '法人手机',
  `telephone2` varchar(255) DEFAULT NULL COMMENT '经办人联系电话',
  `telephone3` varchar(255) DEFAULT NULL COMMENT '负责人手机',
  `telephone4` varchar(255) DEFAULT NULL COMMENT '委托人电话',
  `unitName` varchar(255) DEFAULT NULL COMMENT '单位名称\r\n单位名称',
  `workArea` varchar(255) DEFAULT NULL COMMENT '经营范围',
  `workRemark` varchar(255) DEFAULT NULL COMMENT '经营范围备注',
  `workType` int(11) NOT NULL DEFAULT '0' COMMENT '经营状态',
  `py` varchar(255) DEFAULT NULL COMMENT '企业名称拼音-设计需要增加',
  `station` int(11) NOT NULL DEFAULT '0' COMMENT '所属管辖站-要求增加',
  `status` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '记录状态',
  `createrId` int(11) NOT NULL DEFAULT '0' COMMENT '记录产生人-设计需要增加',
  `editorId` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人-设计需要增加',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_8` (`license`),
  KEY `FK34BF393268F271CC` (`workType`),
  KEY `FK34BF393267024795` (`kind`),
  KEY `FK34BF39328312C16E` (`qualification`),
  KEY `FK34BF393270C71213` (`station`) USING BTREE,
  KEY `FK34BF3932A8E65F13` (`kind`),
  KEY `FK34BF3932AAD6894A` (`workType`),
  CONSTRAINT `FK34BF393237A024B3` FOREIGN KEY (`station`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK34BF393267024795` FOREIGN KEY (`kind`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK34BF393268F271CC` FOREIGN KEY (`workType`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK34BF39328312C16E` FOREIGN KEY (`qualification`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK34BF3932A8E65F13` FOREIGN KEY (`kind`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK34BF3932AAD6894A` FOREIGN KEY (`workType`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `FK_enterprises_4` FOREIGN KEY (`station`) REFERENCES `dictionary` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=908 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `enterprises`
--

/*!40000 ALTER TABLE `enterprises` DISABLE KEYS */;
INSERT INTO `enterprises` (`id`,`address`,`commission`,`createDate`,`dateBegin`,`editDate`,`dateEnd`,`handleMan`,`kind`,`legalPerson`,`license`,`licenseDate`,`qualification`,`telephone1`,`telephone2`,`telephone3`,`telephone4`,`unitName`,`workArea`,`workRemark`,`workType`,`py`,`station`,`status`,`createrId`,`editorId`) VALUES 
 (0,'',NULL,'2010-11-23 20:37:21','2010-11-23 16:40:30','2010-11-23 16:40:30','2010-11-23 16:40:30',NULL,81,'局长','0','2010-11-23 16:40:30',69,'13506789999','13506789999','13506789999',NULL,'慈溪市公路管理所','公路管理',NULL,105,NULL,107,0,0,0),
 (1,'慈溪','','2011-01-26 14:14:39','2008-09-18 00:00:00','2011-01-26 14:14:39','2011-09-18 00:00:00','',74,'04','04','2009-02-26 00:00:00',69,'','','','','慈溪市机动车维修行业管理协会','维修(小型车维修)','',105,'cxsjdcwxxyglxh',0,0,1,1),
 (2,'浒山街道孙塘路与实验路交叉口','','2011-01-26 14:14:40','2008-07-14 00:00:00','2011-01-26 14:14:40','2011-06-30 00:00:00','',90,'茹红霞','330282003414','2009-02-26 00:00:00',71,'','','','','慈溪市浒山红霞洗车场','维修(车身清洁维护)','   ',105,'cxszshxxcc',0,0,1,1),
 (3,'慈溪市匡堰镇樟树村泗水桥126号','','2011-01-26 14:14:40','2008-07-14 00:00:00','2011-01-26 14:14:40','2011-06-30 00:00:00','',90,'叶如中','330282006711','2009-02-26 00:00:00',71,'','','','','慈溪市匡堰如中汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxskyrzqcxld',0,0,1,1),
 (4,'慈溪','','2011-01-26 14:14:40','2008-11-24 00:00:00','2011-01-26 14:14:40','2011-11-24 00:00:00','',90,'99','330282006901','2009-02-26 00:00:00',0,'','','','','慈溪市周巷镇兴业摩托车配件商店','维修(汽车快修,车身维修)','   ',105,'cxszxzxymtcpjsd',0,0,1,1),
 (5,'慈溪','','2011-01-26 14:14:40','2008-11-24 00:00:00','2011-01-26 14:14:40','2011-11-24 00:00:00','',90,'郑先杰','330282006914','2009-02-26 00:00:00',0,'','','','','慈溪市周巷安兴汽配经营部','维修(小型车维修,发动机维修)','   ',105,'cxszxaxqpjyb',0,0,1,1),
 (6,'慈溪','','2011-01-26 14:14:41','2008-11-26 00:00:00','2011-01-26 14:14:41','2011-11-26 00:00:00','',90,'123','330282006924','2009-02-26 00:00:00',71,'','','','','慈溪市新浦胡锋摩托车维修店','维修(摩托车修理)','   ',105,'cxsxphfmtcwxd',0,0,1,1),
 (7,'慈溪','','2011-01-26 14:14:41','2008-11-27 00:00:00','2011-01-26 14:14:41','2011-11-27 00:00:00','',90,'35','330282006926','2009-02-26 00:00:00',69,'','','','','慈溪飞翔修理厂','维修(小型车维修)','   ',105,'cxfxxlc',0,0,1,1),
 (8,'浒山群丰村','','2011-01-26 14:14:41','2008-07-14 00:00:00','2011-01-26 14:14:41','2011-06-30 00:00:00','',90,'孙长其','330282006978','2009-02-26 00:00:00',71,'','13566371666','','','慈溪市浒山慈通汽车修理厂','维修(车身维修)','   ',105,'cxszsctqcxlc',0,0,1,1),
 (9,'慈溪市浒山浒崇公路178号','','2011-01-26 14:14:41','2008-10-20 00:00:00','2011-01-26 14:14:41','2011-06-30 00:00:00','',90,'张秧儿','330282007706','2009-02-26 00:00:00',71,'','63225918','','','慈溪市浒山大兴洗车厂','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszsdxxcc',0,0,1,1),
 (10,'慈溪市周巷副食品批发市场停车场','','2011-01-26 14:14:41','2010-08-23 00:00:00','2011-01-26 14:14:41','2011-06-30 00:00:00','',90,'楼春芳','330282007751','2010-08-23 00:00:00',71,'','63305556','','','慈溪市周巷佳洁洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszxjjxcc',0,0,1,1),
 (11,'慈溪市杭州湾新区海南村','','2011-01-26 14:14:41','2008-12-03 00:00:00','2011-01-26 14:14:41','2011-06-30 00:00:00','',90,'贺孝兵','330282007777','2010-08-23 00:00:00',71,'','','','','慈溪经济开发区阿龙摩托车维修店','维修(二类摩托车维修)','   ',105,'cxjjkfqalmtcwxd',0,0,1,1),
 (12,'慈溪市浒山经济开发区担山北侧','','2011-01-26 14:14:41','1989-09-10 00:00:00','2011-01-26 14:14:41','2003-12-30 00:00:00','',75,'洪放明','330282023008','2010-08-23 00:00:00',0,'13306744316','','','','慈溪市汽车综合性能检测站','维修(机动车维修质量检验经营)','   ',105,'cxsqczhxnjcz',0,0,1,1),
 (13,'慈溪市浒山街道海关北路236—238-1号','','2011-01-26 14:14:41','2007-06-06 00:00:00','2011-01-26 14:14:41','2010-06-06 00:00:00','',90,'劳立波','330282030057','2010-08-23 00:00:00',71,'','','','','慈溪市浒山海北汽车修理部','维修(供油系统维护及油品更换)','',105,'cxszshbqcxlb',0,0,1,1),
 (14,'慈溪市区329国道匡堰段南侧','','2011-01-26 14:14:41','2010-08-10 00:00:00','2011-01-26 14:14:41','2011-07-01 00:00:00','',75,'胡一唯','330282031001','2010-08-10 00:00:00',69,'13906748777','63531100','13805829822','','上海大众汽车慈溪特约维修站','维修(一类机动车维修:小型车辆维修)','',105,'shdzqccxtywxz',0,0,1,1),
 (15,'慈溪市浒山镇峙山路50号','','2011-01-26 14:14:41','2010-08-10 00:00:00','2011-01-26 14:14:41','2011-07-01 00:00:00','',73,'方国平','330282031003','2010-08-10 00:00:00',69,'13505847962','63802418','13706748438','','慈溪市公路运输总公司汽车大修厂','维修(危险品运输车辆维修,一类机动车维修:大中型客车维修、大中型货车维修;危险货物运输车辆维修)','',105,'cxsglyszgsqcdxc',0,0,1,1),
 (16,'慈溪市天元镇潭南村（余庵公路208号）','','2011-01-26 14:14:41','2010-12-21 00:00:00','2011-01-26 14:14:41','2016-06-30 00:00:00','',81,'屠英仙','330282031004','2010-12-21 00:00:00',69,'','63459966','13805785688','','浙江康桥科奥汽车有限公司','维修(一类机动车维修:小型车辆维修)','',105,'zjkqkaqcyxgs',0,0,1,1),
 (17,'慈溪市浒山街道浒崇公路689号','','2011-01-26 14:14:41','2010-08-10 00:00:00','2011-01-26 14:14:41','2011-07-01 00:00:00','',85,'陆庆华','330282031005','2010-08-10 00:00:00',69,'13906743288','63039385  66313233','13805811086','','慈溪市大申进口汽车服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'cxsdsjkqcfwyxgs',0,0,1,1),
 (18,'慈溪市浒山镇眉山村（小岭墩西侧）','','2011-01-26 14:14:42','2010-08-10 00:00:00','2011-01-26 14:14:42','2011-07-01 00:00:00','',85,'史汉张','330282031006','2010-08-10 00:00:00',69,'','63816158','13777248777','','慈溪市飞跃汽车维修厂','维修(一类机动车维修:小型车辆维修)','',105,'cxsfyqcwxc',0,0,1,1),
 (19,'慈溪市浒山眉山村慈百路南侧','','2011-01-26 14:14:42','2010-08-10 00:00:00','2011-01-26 14:14:42','2011-07-01 00:00:00','',84,'陈铁轮','330282031007','2010-08-10 00:00:00',69,'13968209635','63804766','13968209635','','慈溪市东风汽车销售技术服务有限公司','维修(一类机动车维修:大中型货车维修)','   ',105,'cxsdfqcxsjsfwyxgs',0,0,1,1),
 (20,'慈溪市青少年宫北路508号','','2011-01-26 14:14:42','2010-08-10 00:00:00','2011-01-26 14:14:42','2011-07-01 00:00:00','',81,'毕幼平','330282031008','2010-08-10 00:00:00',69,'13805871037','63039304','13957810396','','慈溪市兴欣汽车销售服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'cxsxxqcxsfwyxgs',0,0,1,1),
 (21,'慈溪市浒山前应路北','','2011-01-26 14:14:42','2010-08-10 00:00:00','2011-01-26 14:14:42','2011-07-01 00:00:00','',81,'冯强','330282031009','2010-08-10 00:00:00',69,'13777989999','63199722','13867855159','','宁波盈通汽车销售服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'nbytqcxsfwyxgs',0,0,1,1),
 (22,'慈溪市浒山镇慈百路163号南','','2011-01-26 14:14:42','2010-08-10 00:00:00','2011-01-26 14:14:42','2011-07-01 00:00:00','',85,'吴春林','330282031010','2010-08-10 00:00:00',69,'','63897299','13805829700','','慈溪市园林汽车维修厂','维修(一类机动车维修:小型车辆维修)','',105,'cxsylqcwxc',0,0,1,1),
 (23,'桥头镇工业开发区（329国道旁）','','2011-01-26 14:14:42','2010-08-10 00:00:00','2011-01-26 14:14:42','2011-07-01 00:00:00','',81,'李喜善','330282031011','2010-08-10 00:00:00',69,'13906740662','13567808188  13906740620','','','慈溪金瑞汽车销售服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'cxjrqcxsfwyxgs',0,0,1,1),
 (24,'浒山眉山村','','2011-01-26 14:14:42','2010-08-10 00:00:00','2011-01-26 14:14:42','2011-07-01 00:00:00','',81,'史雪权','330282031012','2010-08-10 00:00:00',69,'13506789999','13906740373','13906740373','','慈溪市新亚汽车销售服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'cxsxyqcxsfwyxgs',0,0,1,1),
 (25,'慈溪市浒祟公路西侧','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2011-07-01 00:00:00','',81,'华建锋','330282031013','2010-08-10 00:00:00',69,'13819883888','63892099','13362468800','','慈溪市华泰汽车服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'cxshtqcfwyxgs',0,0,1,1),
 (26,'天元镇潭南村','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2011-07-01 00:00:00','',85,'杜维益','330282031014','2010-08-10 00:00:00',69,'13906742668','13606883260','','','慈溪科海汽车大修厂','维修(一类机动车维修:大中型货车维修)','',105,'cxkhqcdxc',0,0,1,1),
 (27,'慈溪市古塘街道海通路155号','','2011-01-26 14:14:43','2005-07-01 00:00:00','2011-01-26 14:14:43','2011-07-01 00:00:00','',81,'黄曙红','330282031015','2010-04-23 00:00:00',69,'13505847888','','13805811086','','慈溪市正大进口汽车服务有限公司','维修(一类机动车维修:小型车辆维修)','',105,'cxszdjkqcfwyxgs',0,0,1,1),
 (28,'慈溪市古塘街道浒崇公路689号','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2011-07-01 00:00:00','',81,'陈志连','330282031016','2010-08-10 00:00:00',69,'','','13968296789','','慈溪市一得汽车服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'cxsydqcfwyxgs',0,0,1,1),
 (29,'慈溪市匡堰王家埭','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2011-07-01 00:00:00','',81,'史汉张','330282031017','2010-08-10 00:00:00',69,'13777248777','13958383128','63531111','','宁波远顺行汽车销售服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'nbysxqcxsfwyxgs',0,0,1,1),
 (30,'浒山街道东山村','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2011-07-01 00:00:00','',81,'章祖鸣','330282031018','2010-08-10 00:00:00',69,'13336899797','63989456  13396748432','13386623353','','慈溪市京通汽车销售服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'cxsjtqcxsfwyxgs',0,0,1,1),
 (31,'慈溪市横河镇','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2011-07-01 00:00:00','',81,'汪剑英','330282031019','2010-08-10 00:00:00',69,'63155663','23631122','13355991215','','慈溪金港汽车销售服务有限公司','维修(一类机动车维修:小型车辆维修)','   ',105,'cxjgqcxsfwyxgs',0,0,1,1),
 (32,'慈溪市逍林镇新园村','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2012-08-29 00:00:00','',81,'史雪权','330282031021','2010-08-10 00:00:00',69,'13506789999','13805829822','','','慈溪市奥可汽车销售有限公司','维修(一类机动车维修:小型车辆维修)','',105,'cxsakqcxsyxgs',0,0,1,1),
 (33,'慈溪市横河镇杨梅大道北路333号','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2013-07-06 00:00:00','',81,'周长军','330282031022','2010-08-10 00:00:00',69,'','13906740620','','','慈溪宝恒汽车销售服务有限公司','维修(小型车维修)','',105,'cxbhqcxsfwyxgs',0,0,1,1),
 (34,'慈溪市横河镇马堰村','','2011-01-26 14:14:43','2010-08-26 00:00:00','2011-01-26 14:14:43','2016-06-30 00:00:00','',81,'黄坤达','330282031023','2010-08-26 00:00:00',69,'','','','','慈溪常隆丰田汽车销售服务有限公司','维修(小型车维修)','   ',105,'cxclftqcxsfwyxgs',0,0,1,1),
 (35,'慈溪市横河镇石堰村','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2015-02-10 00:00:00','',81,'史雪权','330282031024','2010-08-10 00:00:00',69,'','13805815682','','','慈溪市驰奥汽车销售有限公司','维修(一类机动车维修:小型车辆维修)','',105,'cxscaqcxsyxgs',0,0,1,1),
 (36,'慈溪市横河镇杨梅大道北路518号','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2014-12-19 00:00:00','',81,'徐娣珍','330282031025','2010-08-10 00:00:00',69,'','13858321151','','','浙江慈吉之星汽车有限公司','维修(一类机动车维修:小型车辆维修)','',105,'zjcjzxqcyxgs',0,0,1,1),
 (37,'慈溪市古塘街道海关路88号','同星亮','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2015-08-07 00:00:00','',81,'董顺岳','330282031026','2010-08-10 00:00:00',69,'13906621758','','','13968296789','慈溪市锦堂汽车服务有限公司','机动车维修：一类机动车维修（小型车辆维修）。','',105,'cxsjtqcfwyxgs',0,0,1,1),
 (38,'慈溪市古塘街道海关路88号','','2011-01-26 14:14:43','2010-08-10 00:00:00','2011-01-26 14:14:43','2015-08-10 00:00:00','',85,'陈金贞','330282031027','2010-08-10 00:00:00',69,'','13906745121','','','慈溪市金丰汽车服务有限公司','机动车维修：一类机动车维修（小型车辆维修）。','',105,'cxsjfqcfwyxgs',0,0,1,1),
 (39,'慈溪市逍林镇新园村','张洪文','2011-01-26 14:14:44','2010-08-10 00:00:00','2011-01-26 14:14:44','2016-02-25 00:00:00','张洪文',81,'叶晓峰','330282031028','2010-08-10 00:00:00',69,'13906683748','58587012','13567810646','13867810646','慈溪市腾峰昊达汽车销售服务有限公司','机动车维修：一类机动车维修（小型车辆维修）。','',105,'cxstfzdqcxsfwyxgs',0,0,1,1),
 (40,'慈溪市坎墩街道浒崇公路998号','张京','2011-01-26 14:14:44','2010-04-16 00:00:00','2011-01-26 14:14:44','2016-06-30 00:00:00','',81,'麦庆龙','330282031029','2010-04-16 00:00:00',69,'','58589979','','13586787832','宁波慈溪骏佳雷克萨斯汽车销售服务有限公司','机动车维修：一类机动车维修（小型车辆维修）。','',105,'nbcxjjlkssqcxsfwyxgs',0,0,1,1),
 (41,'慈溪市客运西站内','','2011-01-26 14:14:44','2005-07-01 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',73,'邵蓓舜','330282032001','2010-04-16 00:00:00',70,'13606889858','63806999','13706742393','','慈溪市车辆急救维修中心','维修(二类机动车维修:大中型货车维修)','   ',105,'cxscljjwxzx',0,0,1,1),
 (42,'慈溪浒山北二环中路海关大厦西侧301','','2011-01-26 14:14:44','2010-08-10 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',85,'戚一帆','330282032002','2010-08-10 00:00:00',70,'','63018111','13805826831','','慈溪市凌云汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxslyqcwxc',0,0,1,1),
 (43,'慈溪市横河镇上剑山村','','2011-01-26 14:14:44','2010-08-10 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',90,'龚建权','330282032003','2010-08-10 00:00:00',70,'','63196218','13805815682','','慈溪市横河镇万盛汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxshhzwsqcwxc',0,0,1,1),
 (44,'慈溪市浒山南二环251号','','2011-01-26 14:14:44','2010-08-10 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',85,'潘剑','330282032004','2010-08-10 00:00:00',70,'','66301988','13805811218','','慈溪市桑普汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsspqcwxc',0,0,1,1),
 (45,'慈溪市三北镇甸山方马','','2011-01-26 14:14:44','2010-08-10 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',90,'陈坚煜','330282032005','2010-08-10 00:00:00',70,'','63733590','13306627687','','慈溪市三北镇汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxssbzqcwxc',0,0,1,1),
 (46,'慈溪市客运中心东站内','','2011-01-26 14:14:44','2010-08-09 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',73,'王超波','330282032006','2010-08-10 00:00:00',70,'','63825961','13355977170','','慈溪市城乡公共交通有限公司维修厂','维修(二类机动车维修:大中型客车维修)','   ',105,'cxscxggjtyxgswxc',0,0,1,1),
 (47,'慈溪市周巷镇环城东路','','2011-01-26 14:14:44','2010-12-09 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',90,'徐斌','330282032007','2010-12-09 00:00:00',70,'13905845809','63307390','13905845809','','慈溪市周巷新环汽车修理厂','维修(二类机动车维修:小型车辆维修)','',105,'cxszxxhqcxlc',0,0,1,1),
 (48,'慈溪市观海卫镇五里村','','2011-01-26 14:14:44','2010-08-10 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',85,'裘亚君','330282032008','2010-08-10 00:00:00',70,'','13606743723','','','慈溪市观海卫镇烨鑫汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxsghwzzzqcwxc',0,0,1,1),
 (49,'慈溪市浒山镇新江路121号','','2011-01-26 14:14:44','2010-08-10 00:00:00','2011-01-26 14:14:44','2011-07-01 00:00:00','',85,'雷汉荣','330282032009','2010-08-10 00:00:00',70,'','63245483','63182960','','慈溪市宗汉新新汽车维修厂','维修(二类机动车维修:小型车辆维修)','',105,'cxszhxxqcwxc',0,0,1,1),
 (50,'慈溪市浒山镇南二环东路','','2011-01-26 14:14:45','2010-08-10 00:00:00','2011-01-26 14:14:45','2011-07-01 00:00:00','',81,'朱紫阳','330282032010','2010-08-10 00:00:00',70,'13805821423','63829555','','','慈溪市粤华汽车维修服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsyhqcwxfwyxgs',0,0,1,1),
 (51,'宗汉金轮工业开发区','','2011-01-26 14:14:45','2010-08-10 00:00:00','2011-01-26 14:14:45','2011-07-01 00:00:00','',85,'黄兵华','330282032011','2010-08-10 00:00:00',70,'','13056744547','81368711','','慈溪市华兴汽车修理厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxshxqcxlc',0,0,1,1),
 (52,'慈溪市桥头镇五姓村','','2011-01-26 14:14:45','2010-09-14 00:00:00','2011-01-26 14:14:45','2011-07-01 00:00:00','',85,'孙仕国','330282032012','2010-09-14 00:00:00',70,'','63551915','13606742333','','慈溪市慈东汽车修配厂（普通合伙）','维修(二类机动车维修:大中型货车维修)','',105,'cxscdqcxpc（pthh）',0,0,1,1),
 (53,'慈溪市庵东镇工业园区纬一路','','2011-01-26 14:14:45','2010-08-10 00:00:00','2011-01-26 14:14:45','2012-07-11 00:00:00','',85,'陆成胡','330282032013','2010-08-10 00:00:00',70,'','63477108','13968263120','','慈溪市桥南汽车维修厂(普通合伙）','维修(二类机动车维修:大中型货车维修、小型车辆维修)','   ',105,'cxsqnqcwxc(pthh）',0,0,1,1),
 (54,'慈溪市附海镇四界村','','2011-01-26 14:14:45','2008-07-22 00:00:00','2011-01-26 14:14:45','2012-07-22 00:00:00','',101,'孙庆达','330282032014','2010-08-10 00:00:00',70,'','13301517676','','','宁波威尔斯汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'nbwesqcfwyxgs',0,0,1,1),
 (55,'慈溪市宗汉镇东周塘村','','2011-01-26 14:14:45','2010-08-10 00:00:00','2011-01-26 14:14:45','2011-07-01 00:00:00','',85,'胡利波','330282032015','2010-08-10 00:00:00',70,'','63225730','13805819248','','慈溪市宗汉飞雁汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxszhfyqcwxc',0,0,1,1),
 (56,'慈溪市庵东镇海星村','','2011-01-26 14:14:45','2010-08-10 00:00:00','2011-01-26 14:14:45','2015-12-29 00:00:00','',90,'丁小萍','330282032016','2010-08-10 00:00:00',70,'13221996238','','','','慈溪市庵东小萍汽车维修厂','机动车维修：二类机动车维修（大中型货车维修）。','',105,'cxszdxpqcwxc',0,0,1,1),
 (57,'慈溪市桥头镇上林湖村（吴山）','','2011-01-26 14:14:45','2010-08-10 00:00:00','2011-01-26 14:14:45','2011-07-01 00:00:00','',85,'陈国庆','330282032018','2010-08-10 00:00:00',70,'','63551709','13805826637','','慈溪市桥头镇万里行汽车修理厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxsqtzwlxqcxlc',0,0,1,1),
 (58,'慈溪市浒山西二环线','','2011-01-26 14:14:45','2010-08-10 00:00:00','2011-01-26 14:14:45','2011-07-01 00:00:00','',90,'钟达','330282032019','2010-08-10 00:00:00',70,'','63801748','13906622104','','慈溪市浒山创达汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxszscdqcwxc',0,0,1,1),
 (59,'慈溪市古塘街道海关北路365号','','2011-01-26 14:14:45','2010-08-10 00:00:00','2011-01-26 14:14:45','2015-06-30 00:00:00','',81,'马建听','330282032020','2010-08-10 00:00:00',70,'13586649242','63999060','','','慈溪市尚品汽车服务有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsspqcfwyxgs',0,0,1,1),
 (60,'慈溪市观海卫镇新泽村','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2011-07-01 00:00:00','',90,'胡国文','330282032022','2010-08-10 00:00:00',70,'','63618008','13906743919','','慈溪市观海卫镇海威汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxsghwzhwqcwxc',0,0,1,1),
 (61,'慈溪市周巷镇环城南路1190号','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2014-06-30 00:00:00','',85,'陆兰芬','330282032023','2010-08-10 00:00:00',70,'','62197697','13306655117','','慈溪市亚兴汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsyxqcwxc',0,0,1,1),
 (62,'慈溪市观城镇杜家桥村','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2011-07-01 00:00:00','',90,'叶新国','330282032024','2010-08-10 00:00:00',70,'','63636891','13706743806','','慈溪市鸣鹤跃兴汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxsmhyxqcwxc',0,0,1,1),
 (63,'慈溪市新浦镇开发区','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2011-07-01 00:00:00','',85,'张建训','330282032026','2010-08-10 00:00:00',70,'','63577877','13906742525','','慈溪市新浦汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxsxpqcwxc',0,0,1,1),
 (64,'慈溪市逍林镇择乐路村','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2011-07-01 00:00:00','',85,'赵伟林','330282032027','2010-08-10 00:00:00',70,'','63502074','13806644212','','慈溪市众鑫汽车修配厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxszzqcxpc',0,0,1,1),
 (65,'慈溪市庵东镇新建村','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2012-06-30 00:00:00','',85,'沈居权','330282032028','2010-08-10 00:00:00',70,'','63475868','','','慈溪市庵东镇中汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxszdzzqcwxc',0,0,1,1),
 (66,'慈溪市周巷镇周邵村塘南','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2015-08-14 00:00:00','',85,'杨立波','330282032029','2010-08-10 00:00:00',70,'13105553748','','','','慈溪市周巷立波汽车维修厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxszxlbqcwxc',0,0,1,1),
 (67,'慈溪市古塘街道华胜路1-9号','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2015-06-30 00:00:00','',81,'胡建江','330282032030','2010-08-10 00:00:00',70,'13968232588','','','','慈溪凯驰吉豪汽车销售服务有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxkcjhqcxsfwyxgs',0,0,1,1),
 (68,'慈溪市浒山街道孙塘南路66号','','2011-01-26 14:14:46','2010-05-19 00:00:00','2011-01-26 14:14:46','2015-06-30 00:00:00','',90,'罗群','330282032031','2010-05-19 00:00:00',70,'13858318855','','','','慈溪市一顺汽车维修厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsysqcwxc',0,0,1,1),
 (69,'慈溪市浒山街道浒崇公路48-68号','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2011-07-01 00:00:00','',95,'宋斐韬','330282032032','2010-08-10 00:00:00',70,'13606881710','63013276','13805821526','','宁波锦兴汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'nbjxqcfwyxgs',0,0,1,1),
 (70,'慈溪市白沙路街道后油车体育馆旁（综合4号楼后）','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2015-06-30 00:00:00','',90,'吴立洪','330282032033','2010-08-10 00:00:00',70,'','','13506848833','','慈溪市浒山立洪汽车维修服务部','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxszslhqcwxfwb',0,0,1,1),
 (71,'慈溪市三北镇东渡村','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2011-07-01 00:00:00','',85,'黄建侃','330282032034','2010-08-10 00:00:00',70,'','63731938','','','慈溪市三北镇甸山汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxssbzdsqcwxc',0,0,1,1),
 (72,'慈溪市长河沦北村','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2011-07-01 00:00:00','',85,'莫先明','330282032036','2010-08-10 00:00:00',70,'','63404881','13506784291','','慈溪市长河镇先明汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxschzxmqcwxc',0,0,1,1),
 (73,'慈溪市观海卫镇蒋家桥村','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2011-07-01 00:00:00','',85,'张海平','330282032037','2010-08-10 00:00:00',70,'','63601218','13805826729','','慈溪市观海卫路达汽车修配厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxsghwldqcxpc',0,0,1,1),
 (74,'慈溪市浒山街道南二环中路437号','','2011-01-26 14:14:46','2010-08-10 00:00:00','2011-01-26 14:14:46','2013-06-30 00:00:00','',90,'董登富','330282032039','2010-08-10 00:00:00',70,'','63100608','13646654541','','慈溪市浒山安及汽车修理厂','维修(小型车维修)','',105,'cxszsajqcxlc',0,0,1,1),
 (75,'慈溪市逍林镇新园村','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2013-05-18 00:00:00','',81,'华建峰','330282032040','2010-08-10 00:00:00',70,'','63728888','','','慈溪森美宝顺汽车销售有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsmbsqcxsyxgs',0,0,1,1),
 (76,'慈溪市古塘街道孙塘北路826号','','2011-01-26 14:14:47','2010-05-06 00:00:00','2011-01-26 14:14:47','2016-06-30 00:00:00','',90,'陈建萍','330282032041','2010-05-06 00:00:00',0,'13805815688','63108888','','','慈溪市古塘金腾汽车修理厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsgtjtqcxlc',0,0,1,1),
 (77,'慈溪市周巷镇镇北路（香苑大酒店对面）','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',81,'周吉祥','330282032042','2010-08-10 00:00:00',70,'13362469736','63323086','13306748746','','慈溪市吉祥汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsjxqcfwyxgs',0,0,1,1),
 (78,'慈溪市观海卫镇工业园西区','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',81,'赵恩平','330282032043','2010-08-10 00:00:00',70,'','','13506783057','','慈溪市车博士汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxscbsqcfwyxgs',0,0,1,1),
 (79,'慈溪市观海卫镇三北西路319-321号','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',90,'徐克','330282032044','2010-08-10 00:00:00',70,'','','13805827770','','慈溪市观海卫镇五里汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxsghwzwlqcwxc',0,0,1,1),
 (80,'慈溪市经济开发区科技路555号','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',84,'范华表','330282032045','2010-08-10 00:00:00',70,'13906743118','63032269','13566601002','','慈溪市华畅汽修服务有限公司','维修(二类机动车维修:大中型货车维修)','   ',105,'cxshcqxfwyxgs',0,0,1,1),
 (81,'浒山街道南二环中路197号','','2011-01-26 14:14:47','2006-03-29 00:00:00','2011-01-26 14:14:47','2012-07-01 00:00:00','',81,'沈仙华','330282032046','2010-08-10 00:00:00',70,'','','13805825577','','慈溪市一得奔宝汽车维修服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsydbbqcwxfwyxgs',0,0,1,1),
 (82,'慈溪市浒山街道后河陈路12号','','2011-01-26 14:14:47','2010-07-13 00:00:00','2011-01-26 14:14:47','2016-06-30 00:00:00','',81,'孙利平','330282032047','2010-12-08 00:00:00',0,'','63532166','','','宁波远顺行汽车销售服务有限公司慈溪分公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'nbysxqcxsfwyxgscxfgs',0,0,1,1),
 (83,'观城广义路23－43号','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',81,'张连锋','330282032048','2010-08-10 00:00:00',70,'13586618877','63602068','13806649068','','慈溪市天一汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxstyqcfwyxgs',0,0,1,1),
 (84,'浒山南二环线东段','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',81,'孙孟杰','330282032049','2010-08-10 00:00:00',70,'13805828710','63838999','66138976','','慈溪市恒通汽车有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxshtqcyxgs',0,0,1,1),
 (85,'慈溪市周巷镇环城南路','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',81,'姚君飞','330282032050','2010-08-10 00:00:00',70,'','63300678','13906620020','','慈溪市宏达汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxshdqcfwyxgs',0,0,1,1),
 (86,'慈溪市浒山镇南二环线东段','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',81,'华雪峰','330282032051','2010-08-10 00:00:00',70,'13355958888','63828886','13805824555','','慈溪市车之家汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsczjqcfwyxgs',0,0,1,1),
 (87,'浒山湾底村','','2011-01-26 14:14:47','2010-10-08 00:00:00','2011-01-26 14:14:47','2012-03-31 00:00:00','',90,'孙华君','330282032053','2010-10-08 00:00:00',70,'','23617611','13506783845','','慈溪市帆丰汽车修理厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsffqcxlc',0,0,1,1),
 (88,'浒崇公路489号','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2014-06-30 00:00:00','',85,'余沪军','330282032055','2010-08-10 00:00:00',70,'','13805810952','','','慈溪诚信汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxcxqcfwyxgs',0,0,1,1),
 (89,'慈溪市坎墩后孙方村','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',85,'孙国华','330282032056','2010-08-10 00:00:00',70,'','63034429','13506782514','','慈溪市坎墩汽车维修厂','维修(二类机动车维修:大中型货车维修)','',105,'cxskdqcwxc',0,0,1,1),
 (90,'慈溪市观海卫镇工业西区','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',90,'周国良','330282032057','2010-08-10 00:00:00',70,'','63611997','13906747997','','慈溪市观海卫镇海鹰汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsghwzhyqcwxc',0,0,1,1),
 (91,'慈溪市浒山海关路与开发大道交叉口（朝南）','','2011-01-26 14:14:47','2008-07-16 00:00:00','2011-01-26 14:14:47','2012-07-16 00:00:00','',85,'楼哲南','330282032061','2010-08-10 00:00:00',70,'','13362469238','','','慈溪市浒山大正汽修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxszsdzqxc',0,0,1,1),
 (92,'宗汉百两桥村木材市场','','2011-01-26 14:14:47','2005-07-01 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',90,'严祖荣','330282032062','2010-08-10 00:00:00',70,'','63208206','13606747856','','慈溪市众兴汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxszxqcwxc',0,0,1,1),
 (93,'慈溪市孙塘北路898号','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2011-07-01 00:00:00','',90,'陆欢清','330282032063','2010-08-10 00:00:00',70,'','63808133','13805824444','','慈溪市佳诚汽车修理厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsjcqcxlc',0,0,1,1),
 (94,'慈溪市长河镇大云村（长七公路旁）','','2011-01-26 14:14:47','2010-08-10 00:00:00','2011-01-26 14:14:47','2012-07-31 00:00:00','',85,'林红如','330282032064','2010-08-10 00:00:00',70,'','63338856','13968233728','','慈溪市长河镇鑫达汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxschzzdqcwxc',0,0,1,1),
 (95,'慈溪市周巷镇城中村','','2011-01-26 14:14:48','2010-08-10 00:00:00','2011-01-26 14:14:48','2011-07-01 00:00:00','',85,'叶常青','330282032065','2010-08-10 00:00:00',70,'13355977768','63306769','13355977768','','慈溪市周巷镇宏安汽车维修厂','维修(二类机动车维修:大中型货车维修)','   ',105,'cxszxzhaqcwxc',0,0,1,1),
 (96,'慈溪市掌起镇下叶村（工业开发区）','','2011-01-26 14:14:48','2010-08-10 00:00:00','2011-01-26 14:14:48','2011-07-01 00:00:00','',85,'陈镇芳','330282032067','2010-08-10 00:00:00',70,'','63740669','13777027333','','慈溪市掌起镇慈鑫汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxszqzczqcwxc',0,0,1,1),
 (97,'慈溪市周巷镇环城南路208号','','2011-01-26 14:14:48','2010-08-10 00:00:00','2011-01-26 14:14:48','2011-07-01 00:00:00','',85,'傅政辉','330282032068','2010-08-10 00:00:00',70,'','63301229','13906745000','','慈溪市周巷镇天帅汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxszxztsqcwxc',0,0,1,1),
 (98,'慈溪市浒山街道南二环中路223-237号','','2011-01-26 14:14:48','2010-08-10 00:00:00','2011-01-26 14:14:48','2011-07-01 00:00:00','',81,'华建峰','330282032069','2010-08-10 00:00:00',70,'13819883888','','13306620040','','慈溪市亚太汽车服务有限公司浒山分公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsytqcfwyxgszsfgs',0,0,1,1),
 (99,'慈溪市附海镇西舍村','','2011-01-26 14:14:48','2010-08-10 00:00:00','2011-01-26 14:14:48','2011-07-01 00:00:00','',85,'王科君','330282032070','2010-08-10 00:00:00',70,'','63568346','13306625518','','慈溪市附海镇东海汽车修理厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsfhzdhqcxlc',0,0,1,1),
 (100,'浒山街道新城大道北路781号','','2011-01-26 14:14:48','2010-08-10 00:00:00','2011-01-26 14:14:48','2012-06-30 00:00:00','',90,'李万富','330282032071','2010-08-10 00:00:00',70,'','13968212222','','','慈溪宝德汽车服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxbdqcfwyxgs',0,0,1,1),
 (101,'浒山青少年宫北路669号','','2011-01-26 14:14:48','2010-08-10 00:00:00','2011-01-26 14:14:48','2012-07-01 00:00:00','',81,'吴家齐','330282032072','2010-08-10 00:00:00',70,'13094833288','63037555','13095989788','','慈溪旅行者汽车销售服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxlxzqcxsfwyxgs',0,0,1,1),
 (102,'慈溪市古塘街道担山北路58号','','2011-01-26 14:14:48','2008-09-03 00:00:00','2011-01-26 14:14:48','2014-06-30 00:00:00','',75,'郑一春','330282032073','2010-08-10 00:00:00',70,'','13906740310','','','慈溪市进口汽车修理厂','机动车维修：二类机动车维修（大中型客车维修、大中型货车维修、小型车辆维修）。','   ',105,'cxsjkqcxlc',0,0,1,1),
 (103,'慈溪市匡堰镇高家村','','2011-01-26 14:14:48','2010-08-26 00:00:00','2011-01-26 14:14:48','2016-06-30 00:00:00','',90,'丁慧军','330282032074','2010-08-26 00:00:00',70,'','81346178','','','慈溪市鑫军汽车维修厂','维修(小型车维修)','   ',105,'cxszjqcwxc',0,0,1,1),
 (104,'崇寿镇六塘村','','2011-01-26 14:14:48','2007-10-29 00:00:00','2011-01-26 14:14:48','2013-10-29 00:00:00','',85,'陈彩娟','330282032075','2010-06-24 00:00:00',70,'','63293902','','','慈溪市新盛汽车维修厂','维修(小型车维修)','   ',105,'cxsxsqcwxc',0,0,1,1),
 (105,'慈溪市宗汉大道146号','','2011-01-26 14:14:48','2007-09-28 00:00:00','2011-01-26 14:14:48','2013-09-28 00:00:00','',85,'马利纳','330282032076','2010-08-10 00:00:00',70,'','','13906741727','','慈溪市宇亚汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsyyqcwxc',0,0,1,1),
 (106,'慈溪市逍林镇新园村','','2011-01-26 14:14:48','2008-03-10 00:00:00','2011-01-26 14:14:48','2012-03-10 00:00:00','',81,'孙孟杰','330282032077','2010-08-10 00:00:00',70,'','13805828710','','','慈溪市恒升汽车销售服务有限公司','维修(小型车维修)','   ',105,'cxshsqcxsfwyxgs',0,0,1,1),
 (107,'慈溪市三北镇田央村','','2011-01-26 14:14:48','2007-12-29 00:00:00','2011-01-26 14:14:48','2011-12-29 00:00:00','',85,'华飞忠','330282032078','2010-08-10 00:00:00',70,'','135067462589','135067462589','','慈溪市慈鹰汽车维修厂','维修(二类机动车维修:小型车辆维修)','   ',105,'cxscyqcwxc',0,0,1,1),
 (108,'慈溪市逍林镇新园村','','2011-01-26 14:14:49','2008-02-18 00:00:00','2011-01-26 14:14:49','2012-02-18 00:00:00','',81,'赵奕挺','330282032079','2010-08-10 00:00:00',70,'','63515282','13396627151','','慈溪市广顺汽车销售服务有限公司','维修(二类机动车维修:小型车辆维修)','   ',105,'cxsgsqcxsfwyxgs',0,0,1,1),
 (109,'慈溪市观城镇蒋家桥村','江央婷','2011-01-26 14:14:49','2009-05-06 00:00:00','2011-01-26 14:14:49','2015-05-06 00:00:00','',81,'张连锋','330282032080','2010-08-10 00:00:00',70,'13586618877','','','15869376008','慈溪市张三汽车服务有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxszsqcfwyxgs',0,0,1,1),
 (110,'逍林镇新园村','','2011-01-26 14:14:49','2010-08-10 00:00:00','2011-01-26 14:14:49','2015-03-10 00:00:00','陆松泽',81,'徐周明','330282032081','2010-08-10 00:00:00',70,'','','15957886008','','慈溪市友铃汽车服务有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsylqcfwyxgs',0,0,1,1),
 (111,'慈溪市浒山街道慈百路17号','','2011-01-26 14:14:49','2010-08-10 00:00:00','2011-01-26 14:14:49','2015-06-30 00:00:00','',85,'张学军','330282032082','2010-08-10 00:00:00',70,'','','','','慈溪市桥城汽车维修厂（普通合伙）','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsqcqcwxc（pthh）',0,0,1,1),
 (112,'逍林镇新园村','胡琴琴','2011-01-26 14:14:49','2010-08-10 00:00:00','2011-01-26 14:14:49','2015-05-11 00:00:00','',81,'朱紫阳','330282032083','2010-08-10 00:00:00',70,'13008981865','','','','慈溪市中驰汽车有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxszcqcyxgs',0,0,1,1),
 (113,'慈溪市浒山街道海关北路与开发大道交叉口','','2011-01-26 14:14:49','2010-08-10 00:00:00','2011-01-26 14:14:49','2015-05-26 00:00:00','',85,'王茂卿','330282032084','2010-08-10 00:00:00',70,'18906621888','','','','慈溪市浒山新大正汽修部（普通合伙）','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxszsxdzqxb（pthh）',0,0,1,1),
 (114,'慈溪市坎墩街道坎墩大道771号','','2011-01-26 14:14:49','2010-08-10 00:00:00','2011-01-26 14:14:49','2015-06-30 00:00:00','王磊',81,'王磊','330282032085','2010-08-10 00:00:00',70,'13362469238','63090288','','','慈溪市运达汽车服务有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsydqcfwyxgs',0,0,1,1),
 (115,'慈溪市附海镇海晏庙村市场路5-7号','','2011-01-26 14:14:49','2010-08-10 00:00:00','2011-01-26 14:14:49','2015-06-30 00:00:00','',90,'方钧','330282032086','2010-08-10 00:00:00',70,'13506747525','','','','慈溪市晨茜汽车维修厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsczqcwxc',0,0,1,1),
 (116,'慈溪市古塘街道浒崇公路北三环四叉路口','','2011-01-26 14:14:49','2010-08-10 00:00:00','2011-01-26 14:14:49','2015-06-30 00:00:00','罗岳高',90,'黄小萍','330282032087','2010-08-10 00:00:00',70,'13566608111','','8137000','','慈溪市浒山大洋汽车维修厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxszsdyqcwxc',0,0,1,1),
 (117,'慈溪市观海卫镇环城西路167号','','2011-01-26 14:14:49','2010-08-10 00:00:00','2011-01-26 14:14:49','2015-06-30 00:00:00','',90,'杨亚军','330282032088','2010-08-10 00:00:00',70,'','13567803059','','','慈溪市观海卫泰安汽车维修厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsghwtaqcwxc',0,0,1,1),
 (118,'慈溪市宗汉街道百两村北三环工业开发区','','2011-01-26 14:14:50','2010-08-10 00:00:00','2011-01-26 14:14:50','2015-10-20 00:00:00','',90,'黄建苗','330282032089','2010-08-10 00:00:00',70,'13606742272','','','','慈溪市宗汉佳苗汽车修理厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxszhjmqcxlc',0,0,1,1),
 (119,'慈溪市掌起镇陈家村','','2011-01-26 14:14:50','2010-08-10 00:00:00','2011-01-26 14:14:50','2015-11-05 00:00:00','',90,'沈亦青','330282032090','2010-08-10 00:00:00',70,'18906741422','','','','慈溪市立诚汽车修理厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxslcqcxlc',0,0,1,1),
 (120,'慈溪市观海卫镇城隍庙村329国道边','','2011-01-26 14:14:50','2010-08-10 00:00:00','2011-01-26 14:14:50','2015-11-06 00:00:00','',85,'朱治伟','330282032091','2010-08-10 00:00:00',70,'13968295823','','','','慈溪市观海卫兴诚汽车修理厂（普通合伙）','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsghwxcqcxlc（pthh）',0,0,1,1),
 (121,'慈溪市龙山镇施公山村','','2011-01-26 14:14:50','2010-09-30 00:00:00','2011-01-26 14:14:50','2016-06-30 00:00:00','',81,'桂雷明','330282032092','2010-09-30 00:00:00',0,'13486600999','','','','慈溪市雷明汽车服务有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxslmqcfwyxgs',0,0,1,1),
 (122,'慈溪市古塘街道北二环中路338号','','2011-01-26 14:14:50','2010-10-21 00:00:00','2011-01-26 14:14:50','2016-06-30 00:00:00','',85,'赵平波','330282032093','2010-12-20 00:00:00',0,'13906747112','','','','慈溪市古塘新美特汽车服务中心（普通合伙）','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsgtxmtqcfwzx（pthh）',0,0,1,1),
 (123,'慈溪市龙山镇慈龙中路437号','','2011-01-26 14:14:50','2010-10-08 00:00:00','2011-01-26 14:14:50','2016-06-30 00:00:00','',90,'龚志浩','330282032094','2010-10-08 00:00:00',0,'','','','','慈溪市龙山豪东汽车维修厂','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxslshdqcwxc',0,0,1,1),
 (124,'慈溪市逍林镇新园村','胡金锦','2011-01-26 14:14:50','2010-11-12 00:00:00','2011-01-26 14:14:50','2016-06-30 00:00:00','',81,'孙浩洪','330282032095','2010-11-12 00:00:00',0,'13105560000','','','','慈溪市瑞丰汽车销售服务有限公司','机动车维修：二类机动车维修（小型车辆维修）。','',105,'cxsrfqcxsfwyxgs',0,0,1,1),
 (125,'慈溪市浒山街道白彭路','','2011-01-26 14:14:50','2010-08-11 00:00:00','2011-01-26 14:14:50','2011-06-30 00:00:00','',85,'周立波','330282032801','2010-08-11 00:00:00',71,'','63821048','13806647636','','慈溪市浒山驰兴汽车维修厂','维修(发动机维修)','   ',105,'cxszscxqcwxc',0,0,1,1),
 (126,'慈溪市慈溪大道坎墩西路336号','','2011-01-26 14:14:50','2010-08-11 00:00:00','2011-01-26 14:14:50','2011-06-30 00:00:00','',85,'马军杰','330282032802','2010-08-11 00:00:00',71,'','','','','慈溪市坎墩军杰汽车修理厂','维修(发动机维修)','   ',105,'cxskdjjqcxlc',0,0,1,1),
 (127,'南三环前应路（热电厂旁）','','2011-01-26 14:14:50','2008-07-14 00:00:00','2011-01-26 14:14:50','2011-06-30 00:00:00','',85,'吴小忠','330282032803','2010-08-11 00:00:00',71,'','63111947','13706742278','','慈溪市浒山飞达汽车维修厂','维修(发动机维修)','   ',105,'cxszsfdqcwxc',0,0,1,1),
 (128,'慈溪市横河镇龙泉村虹梅路40号','','2011-01-26 14:14:50','2010-08-11 00:00:00','2011-01-26 14:14:50','2011-06-30 00:00:00','',90,'','330282032804','2010-08-11 00:00:00',71,'','','13355999088','','慈溪市横河乾权汽车维修厂','维修(发动机维修)','   ',105,'cxshhqqqcwxc',0,0,1,1),
 (129,'慈溪市周巷镇周东329国道旁','','2011-01-26 14:14:51','2010-08-23 00:00:00','2011-01-26 14:14:51','2011-06-30 00:00:00','',85,'景建飞','330282032805','2010-08-23 00:00:00',71,'','63301633','13606883505','','慈溪市周巷大发汽车维修厂','维修(发动机维修)','   ',105,'cxszxdfqcwxc',0,0,1,1),
 (130,'慈溪市周巷镇兴业北路598号','','2011-01-26 14:14:51','2010-08-23 00:00:00','2011-01-26 14:14:51','2011-06-30 00:00:00','',85,'王仪男','330282032806','2010-08-23 00:00:00',71,'','63304137','','','慈溪市周巷信义汽车维修厂','维修(发动机维修)','   ',105,'cxszxxyqcwxc',0,0,1,1),
 (131,'慈溪市浒山镇教场山东路','','2011-01-26 14:14:51','2008-07-14 00:00:00','2011-01-26 14:14:51','2011-06-30 00:00:00','',74,'杨子江','330282032808','2010-08-23 00:00:00',71,'','63814718','','','慈溪市浒山环卫所汽车维修厂','维修(发动机维修)','   ',105,'cxszshwsqcwxc',0,0,1,1),
 (132,'慈溪市龙山镇西门外公路边','','2011-01-26 14:14:51','2008-07-14 00:00:00','2011-01-26 14:14:51','2011-06-30 00:00:00','',85,'郑利波','330282032809','2010-08-23 00:00:00',71,'','63780102','','','慈溪市龙山利波汽车维修厂','维修(发动机维修)','   ',105,'cxslslbqcwxc',0,0,1,1),
 (133,'慈溪市范市镇王家路村','','2011-01-26 14:14:51','2008-07-14 00:00:00','2011-01-26 14:14:51','2011-06-30 00:00:00','',85,'陈国良','330282032810','2010-08-23 00:00:00',71,'','63705714','','','慈溪市范市镇汽车维修厂','维修(发动机维修)','   ',105,'cxsfszqcwxc',0,0,1,1),
 (134,'浒山街道孙塘北路816号','','2011-01-26 14:14:51','2008-07-14 00:00:00','2011-01-26 14:14:51','2011-06-30 00:00:00','',90,'陈建平','330282032901','2010-08-23 00:00:00',71,'','13806646391','63034909','','慈溪市爱赛德科汽车快修店','维修(汽车快修)','   ',105,'cxsasdkqckxd',0,0,1,1),
 (135,'浒山东三环路','','2011-01-26 14:14:51','2010-08-11 00:00:00','2011-01-26 14:14:51','2012-06-30 00:00:00','',90,'邹树军','330282032902','2010-08-11 00:00:00',71,'','63820528','','','慈溪市汇欣汽车快修店','机动车维修：三类机动车维修、汽车快修）。','   ',105,'cxshxqckxd',0,0,1,1),
 (136,'慈溪市庵东镇杭州湾跨海大桥南岸服务区','','2011-01-26 14:14:51','2008-05-04 00:00:00','2011-01-26 14:14:51','2012-05-04 00:00:00','',90,'陈高琪','330282032903','2010-08-11 00:00:00',71,'','13905844518','13905844518','','慈溪市庵东路达汽车维修服务部','维修(汽车快修)','   ',105,'cxszdldqcwxfwb',0,0,1,1),
 (137,'慈溪市古塘街道开发大道1217号','','2011-01-26 14:14:51','2010-08-11 00:00:00','2011-01-26 14:14:51','2011-06-30 00:00:00','',81,'朱冰','330282032905','2010-08-11 00:00:00',71,'','63930050','','','浙江元瑞汽车有限公司慈溪分公司','维修(汽车快修)','   ',105,'zjyrqcyxgscxfgs',0,0,1,1),
 (138,'慈溪市胜山镇胜山头村胜山大道869号','','2011-01-26 14:14:51','2010-08-11 00:00:00','2011-01-26 14:14:51','2011-06-30 00:00:00','',90,'娄建国','330282033000','2010-08-11 00:00:00',71,'','66382521','','','慈溪市胜山鸿源汽车维修店','维修(涂漆,轮胎动平衡及修补)','   ',105,'cxssshyqcwxd',0,0,1,1),
 (139,'慈溪市新城大道Ａ41-45号','','2011-01-26 14:14:51','2006-06-06 00:00:00','2011-01-26 14:14:51','2009-07-01 00:00:00','',90,'王新岳','330282033001','2010-08-11 00:00:00',71,'','','13805819829','','慈溪市车伙伴汽车装璜经营部','维修(三类机动车维修:四轮定位检测调整;车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxschbqczzjyb',0,0,1,1),
 (140,'慈溪市逍林镇逍林大道2号','','2011-01-26 14:14:51','2006-09-11 00:00:00','2011-01-26 14:14:51','2009-09-11 00:00:00','',90,'赵春玉','330282033003','2010-08-11 00:00:00',71,'','13968267930','13968267930','','慈溪市逍林红日汽车装璜店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszlhrqczzd',0,0,1,1),
 (141,'龙山镇龙头场村国道旁边芳龙路慈客隆东面','','2011-01-26 14:14:51','2006-09-30 00:00:00','2011-01-26 14:14:51','2009-07-01 00:00:00','',90,'李强','330282033004','2010-08-11 00:00:00',71,'','13325940069','13325940069','','慈溪市龙山实力汽车装璜店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxslsslqczzd',0,0,1,1),
 (142,'浒山南二环中路173号','','2011-01-26 14:14:52','2006-10-24 00:00:00','2011-01-26 14:14:52','2009-07-01 00:00:00','',90,'罗立生','330282033005','2010-08-11 00:00:00',71,'','13777993735','13777993735','','慈溪市浒山路路通汽车装璜服务部','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszslltqczzfwb',0,0,1,1),
 (143,'慈溪市浒山街道八字桥村','','2011-01-26 14:14:52','2010-08-11 00:00:00','2011-01-26 14:14:52','2012-05-27 00:00:00','',90,'刁荣振','330282033006','2010-08-24 00:00:00',71,'','','','','慈溪市浒山中基汽车维修厂','机动车维修：三类机动车维修（发动机修理、供油系统维护及油品更换）。','   ',105,'cxszszjqcwxc',0,0,1,1),
 (144,'慈溪市天元镇界塘村','','2011-01-26 14:14:52','2010-08-23 00:00:00','2011-01-26 14:14:52','2012-06-30 00:00:00','',90,'陈兴锋','330282033007','2010-08-23 00:00:00',71,'','63440090','13958370157','','慈溪市天元镇兴锋汽车美容店','维修(三类机动车维修:涂漆、车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxstyzxfqcmrd',0,0,1,1),
 (145,'慈溪市体育中心东二区','','2011-01-26 14:14:52','2010-08-11 00:00:00','2011-01-26 14:14:52','2012-05-16 00:00:00','',90,'苗志高','330282033009','2010-08-11 00:00:00',71,'','13968280008','13968280008','','慈溪市浒山无限车友俱乐部','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszswxcyjlb',0,0,1,1),
 (146,'慈溪市浒山街道孙塘北路656号','','2011-01-26 14:14:52','2010-08-11 00:00:00','2011-01-26 14:14:52','2012-12-31 00:00:00','',90,'姚叶婷','330282033010','2010-08-11 00:00:00',71,'','63033922','','','慈溪市浒山焦点汽车装璜服务部','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszsjdqczzfwb',0,0,1,1),
 (147,'慈溪市逍林镇逍林大道','','2011-01-26 14:14:52','2010-08-11 00:00:00','2011-01-26 14:14:52','2012-06-30 00:00:00','',90,'陈希敏','330282033011','2010-08-11 00:00:00',71,'','15967830796','','','慈溪市逍林小平汽车喷涂漆店','维修(三类机动车维修:涂漆)','   ',105,'cxszlxpqcptqd',0,0,1,1),
 (148,'天元镇潭北路北93号','','2011-01-26 14:14:52','2010-08-23 00:00:00','2011-01-26 14:14:52','2011-06-30 00:00:00','',90,'许红娣','330282033012','2010-08-23 00:00:00',71,'','13008902574','','','慈溪市宏泰汽车装潢店','维修(汽车装璜)','   ',105,'cxshtqczzd',0,0,1,1),
 (149,'慈溪市横河镇孙家境村杨梅大道361号','','2011-01-26 14:14:52','2008-06-04 00:00:00','2011-01-26 14:14:52','2012-06-04 00:00:00','',90,'胡渭竹','330282033013','2010-08-23 00:00:00',71,'','13252258185','','','慈溪市横河孙境洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxshhsjxcc',0,0,1,1),
 (150,'慈溪市庵东镇庵宗公路164号','','2011-01-26 14:14:52','2008-06-18 00:00:00','2011-01-26 14:14:52','2012-06-18 00:00:00','',90,'诸梁波','330282033014','2010-08-23 00:00:00',71,'','63476540','','','慈溪市庵东颖颖洗车店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszdyyxcd',0,0,1,1),
 (151,'慈溪市周家巷精忠','','2011-01-26 14:14:52','2010-08-23 00:00:00','2011-01-26 14:14:52','2011-06-30 00:00:00','',85,'付焕忠','330282033015','2010-08-23 00:00:00',71,'','63310833','13968282505','','慈溪市周巷镇精忠汽车急救修理店','维修(发动机维修)','   ',105,'cxszxzjzqcjjxld',0,0,1,1),
 (152,'浒山街道开发大道石桥村','','2011-01-26 14:14:52','2010-08-11 00:00:00','2011-01-26 14:14:52','2012-06-30 00:00:00','',90,'严越贵','330282033016','2010-08-24 00:00:00',71,'','63023611','','','慈溪市浒山富隆汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszsflqcxld',0,0,1,1),
 (153,'慈溪市浒山孙塘南路2号楼19-20号','','2011-01-26 14:14:52','2010-08-11 00:00:00','2011-01-26 14:14:52','2011-06-19 00:00:00','',90,'陆建冲','330282033017','2010-08-11 00:00:00',71,'','13884486208','','','慈溪市浒山晨宇汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszscyqcxld',0,0,1,1),
 (154,'慈溪市坎墩镇坎中村','','2011-01-26 14:14:52','2010-08-11 00:00:00','2011-01-26 14:14:52','2012-12-28 00:00:00','',90,'张卫平','3302820330177','2010-08-11 00:00:00',0,'13858348190','','','','慈溪市坎墩卫平汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxskdwpqcxld',0,0,1,1),
 (155,'慈溪市长河镇东路120号','','2011-01-26 14:14:52','2010-08-23 00:00:00','2011-01-26 14:14:52','2009-07-01 00:00:00','',90,'吴家卫','330282033018','2010-08-23 00:00:00',71,'','13706748633','13706748633','','慈溪市长河家卫汽车修理厂','维修(三类机动车维修:轮胎动平衡及修补、供油系统维护及油品更换)','   ',105,'cxschjwqcxlc',0,0,1,1),
 (156,'浒山新江路371-373号','','2011-01-26 14:14:53','2010-08-11 00:00:00','2011-01-26 14:14:53','2012-06-30 00:00:00','',90,'陈明磊','330282033019','2010-08-11 00:00:00',71,'','13586614280','13586614280','','慈溪市浒山小余汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszsxyqcxld',0,0,1,1),
 (157,'慈溪市坎墩街道三群村','','2011-01-26 14:14:53','2010-08-11 00:00:00','2011-01-26 14:14:53','2012-06-30 00:00:00','',90,'邹乃军','330282033020','2010-08-11 00:00:00',71,'','15825561000','','','慈溪市坎墩保洁利洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxskdbjlxcc',0,0,1,1),
 (158,'慈溪市周巷镇海江村','','2011-01-26 14:14:53','2008-07-10 00:00:00','2011-01-26 14:14:53','2011-07-10 00:00:00','',90,'严军辉','330282033021','2010-08-11 00:00:00',0,'','13768262917','','','慈溪市周巷阿煊摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxazmtcxld',0,0,1,1),
 (159,'慈溪市天元镇镇北路86号','','2011-01-26 14:14:53','2007-10-26 00:00:00','2011-01-26 14:14:53','2011-10-26 00:00:00','',90,'钱利东','330282033022','2010-08-11 00:00:00',71,'','13958372937','13958372937','','慈溪市天元车之行轮胎修理店','维修(三类机动车维修:轮胎动平衡及修补)','   ',105,'cxstyczxltxld',0,0,1,1),
 (160,'慈溪市坎墩街道孙方村坎墩大道953号','','2011-01-26 14:14:53','2010-08-11 00:00:00','2011-01-26 14:14:53','2011-12-29 00:00:00','',90,'叶建达','330282033023','2010-08-11 00:00:00',71,'','13805817134','13805817134','',' 慈溪市坎墩建达轮胎修理店 ','机动车维修：三类机动车维修（轮胎动平衡及修补、四轮定位检测调整）。','   ',105,' cxskdjdltxld ',0,0,1,1),
 (161,'慈溪市坎墩大道731号','','2011-01-26 14:14:53','2010-08-11 00:00:00','2011-01-26 14:14:53','2011-06-30 00:00:00','',90,'苗志权','330282033024','2010-08-11 00:00:00',71,'','63283640','','','慈溪市坎墩志权车辆修理店','维修(三类机动车维修:轮胎动平衡及修补)','   ',105,'cxskdzqclxld',0,0,1,1),
 (162,'慈溪市周巷镇万寿村万寿','','2011-01-26 14:14:53','2008-07-17 00:00:00','2011-01-26 14:14:53','2011-07-17 00:00:00','',90,'邢劳邦','330282033025','2010-08-11 00:00:00',0,'','13600614980','','','慈溪市周巷阿邦摩托车配件店','维修(一类摩托车维修)','   ',105,'cxszxabmtcpjd',0,0,1,1),
 (163,'慈溪市掌起镇厉家村慈掌路490号','','2011-01-26 14:14:53','2010-08-11 00:00:00','2011-01-26 14:14:53','2011-07-28 00:00:00','',90,'柴成杰','330282033026','2010-08-11 00:00:00',71,'','13805824049','','','慈溪市掌起飞驰汽车修理厂','机动车维修：三类机动车维修（车身维修）。','   ',105,'cxszqfcqcxlc',0,0,1,1),
 (164,'慈溪市横河镇彭桥村庙后','','2011-01-26 14:14:53','2008-07-29 00:00:00','2011-01-26 14:14:53','2011-07-29 00:00:00','',90,'孙定益','330282033027','2010-08-11 00:00:00',71,'','13484267150','','','慈溪市横河欣诚汽车维修店','维修(三类机动车维修:车身维修、涂漆)','   ',105,'cxshhxcqcwxd',0,0,1,1),
 (165,'慈溪市周巷镇杭湾路8号','','2011-01-26 14:14:54','2008-08-08 00:00:00','2011-01-26 14:14:54','2011-06-30 00:00:00','',90,'吴建江','330282033028','2010-08-11 00:00:00',0,'','63491298','','','慈溪市周巷福江摩托车维修店','维修(摩托车修理)','   ',105,'cxszxfjmtcwxd',0,0,1,1),
 (166,'慈溪市宗汉街道新界村','','2011-01-26 14:14:54','2008-08-18 00:00:00','2011-01-26 14:14:54','2011-06-30 00:00:00','',90,'胡月意','330282033029','2010-08-11 00:00:00',0,'','15968027258','','','慈溪市宗汉仙潭洗车场','维修(车身清洁维护)','   ',105,'cxszhxtxcc',0,0,1,1),
 (167,'慈溪市长河镇沧北村','','2011-01-26 14:14:54','2010-08-23 00:00:00','2011-01-26 14:14:54','2011-06-30 00:00:00','',90,'王黎明','330282033030','2010-08-23 00:00:00',71,'','','','','慈溪市长河安达汽车维修店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxschadqcwxd',0,0,1,1),
 (168,'慈溪市龙山镇三北田央村','','2011-01-26 14:14:54','2008-09-11 00:00:00','2011-01-26 14:14:54','2011-06-30 00:00:00','',90,'刘兵舰','330282033031','2010-08-23 00:00:00',0,'','82673171','','','慈溪市三北百年摩托车修理店','维修(摩托车修理)','   ',105,'cxssbbnmtcxld',0,0,1,1),
 (169,'慈溪市浒山街道浒山泥桥路4-6号','','2011-01-26 14:14:54','2010-08-11 00:00:00','2011-01-26 14:14:54','2012-01-22 00:00:00','',90,'祖信春','330282033032','2010-08-11 00:00:00',71,'','82308806','','','慈溪市浒山宝兴汽车电器维修店','维修(三类机动车维修:电气系统维修)','   ',105,'cxszsbxqcdqwxd',0,0,1,1),
 (170,'慈溪市周巷镇环城东路878号','','2011-01-26 14:14:54','2010-08-23 00:00:00','2011-01-26 14:14:54','2011-06-30 00:00:00','',90,'陈华生','330282033033','2010-08-23 00:00:00',0,'','23717826','','','慈溪市周巷创佳车身清洁维护店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszxcjcsqjwhd',0,0,1,1),
 (171,'慈溪市周巷镇环城南路1057号','','2011-01-26 14:14:54','2008-09-12 00:00:00','2011-01-26 14:14:54','2011-06-30 00:00:00','',90,'赵双明','330282033034','2010-08-23 00:00:00',0,'','13505787253','','','慈溪市周巷双明摩托车修理店','维修(摩托车修理)','   ',105,'cxszxsmmtcxld',0,0,1,1),
 (172,'慈溪市周巷镇劳家村','','2011-01-26 14:14:54','2010-08-23 00:00:00','2011-01-26 14:14:54','2011-06-30 00:00:00','',90,'赖国强','330282033035','2010-08-23 00:00:00',0,'','15858431040','','','慈溪市周巷杰文洗车场','维修(车身清洁维护)','   ',105,'cxszxjwxcc',0,0,1,1),
 (173,'慈溪市周巷镇环城西路379号','','2011-01-26 14:14:55','2010-08-23 00:00:00','2011-01-26 14:14:55','2011-06-30 00:00:00','',90,'马炯杰','330282033036','2010-08-23 00:00:00',71,'','13857457451','','','慈溪市周巷锦新汽车装潢店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszxjxqczzd',0,0,1,1),
 (174,'慈溪市匡堰镇高家村后张埭','','2011-01-26 14:14:55','2010-09-15 00:00:00','2011-01-26 14:14:55','2011-06-30 00:00:00','',90,'胡建丽','330282033037','2010-09-15 00:00:00',0,'13486611203','','','','慈溪市匡堰镇保洁洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxskyzbjxcc',0,0,1,1),
 (175,'慈溪市宗汉街道新塘村新江路口','','2011-01-26 14:14:55','2009-03-27 00:00:00','2011-01-26 14:14:55','2011-06-30 00:00:00','',90,'郑金宏','330282033038','2009-03-27 00:00:00',0,'13857451188','','','','慈溪市宗汉怡心冼车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszhzxzcc',0,0,1,1),
 (176,'慈溪市周巷镇海莫社区开发路','','2011-01-26 14:14:55','2010-08-23 00:00:00','2011-01-26 14:14:55','2011-06-30 00:00:00','',90,'黄浩亮','330282033039','2010-08-23 00:00:00',71,'','13506783150','','','慈溪市周巷龙天汽车装潢店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszxltqczzd',0,0,1,1),
 (177,'慈溪市宗汉街道百两村','','2011-01-26 14:14:56','2008-09-24 00:00:00','2011-01-26 14:14:56','2011-06-30 00:00:00','',90,'黄建苗','330282033040','2010-08-23 00:00:00',71,'','13606742272','','','慈溪市宗汉建苗汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszhjmqcxld',0,0,1,1),
 (178,'慈溪市周巷镇小安村杭湾路31号','','2011-01-26 14:14:56','2010-08-23 00:00:00','2011-01-26 14:14:56','2011-06-30 00:00:00','',90,'罗万焕','330282033041','2010-08-23 00:00:00',71,'','13606743822','','','慈溪市周巷万焕修理店','维修(三类机动车维修:轮胎动平衡及修补)','   ',105,'cxszxwhxld',0,0,1,1),
 (179,'慈溪市周巷镇环城南路1109号','','2011-01-26 14:14:56','2010-08-23 00:00:00','2011-01-26 14:14:56','2011-06-30 00:00:00','',90,'张德昌','330282033042','2010-08-23 00:00:00',71,'','13858324363','','','慈溪市周巷张德昌汽车喷油泵维护店','维修(三类机动车维修:喷油泵和喷油嘴维修)','',105,'cxszxzdcqcpybwhd',0,0,1,1),
 (180,'慈溪市明州路南侧，新安江东侧','','2011-01-26 14:14:56','2010-08-11 00:00:00','2011-01-26 14:14:56','2011-06-30 00:00:00','',90,'胡成强','330282033043','2010-08-11 00:00:00',71,'','13095976615','','','慈溪市古塘乘翔汽车装璜店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxsgtcxqczzd',0,0,1,1),
 (181,'慈溪市周巷镇环城南路208号','','2011-01-26 14:14:56','2010-08-23 00:00:00','2011-01-26 14:14:56','2011-06-30 00:00:00','',90,'傅政辉','330282033044','2010-08-23 00:00:00',0,'13906745000','','','','慈溪市周巷腾帅汽车修理店','机动车维修：三类机动车维修（车身维修）。','',105,'cxszxtsqcxld',0,0,1,1),
 (182,'慈溪市周巷镇登州街村','','2011-01-26 14:14:56','2008-10-09 00:00:00','2011-01-26 14:14:56','2011-06-30 00:00:00','',90,'张乃军','330282033045','2010-08-23 00:00:00',0,'','13819888450','','','慈溪市周巷军琳摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxjlmtcxld',0,0,1,1),
 (183,'慈溪市古塘街道海关北路222号楼228号','','2011-01-26 14:14:56','2010-08-11 00:00:00','2011-01-26 14:14:56','2012-06-30 00:00:00','',90,'王红卫','330282033046','2010-08-24 00:00:00',0,'','13566071365','','','慈溪市古塘宏伟汽车维修服务部','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxsgthwqcwxfwb',0,0,1,1),
 (184,'慈溪市龙山镇王家路村','','2011-01-26 14:14:57','2008-10-23 00:00:00','2011-01-26 14:14:57','2011-06-30 00:00:00','',90,'邱叶挺','330282033047','2010-08-24 00:00:00',0,'','13566600400','','','慈溪市范市叶挺摩托车修理店','维修(二类摩托车维修)','   ',105,'cxsfsytmtcxld',0,0,1,1);
INSERT INTO `enterprises` (`id`,`address`,`commission`,`createDate`,`dateBegin`,`editDate`,`dateEnd`,`handleMan`,`kind`,`legalPerson`,`license`,`licenseDate`,`qualification`,`telephone1`,`telephone2`,`telephone3`,`telephone4`,`unitName`,`workArea`,`workRemark`,`workType`,`py`,`station`,`status`,`createrId`,`editorId`) VALUES 
 (185,'慈溪市龙山镇伏龙路23号','','2011-01-26 14:14:57','2008-10-23 00:00:00','2011-01-26 14:14:57','2011-06-30 00:00:00','',90,'何维杰','330282033048','2010-08-24 00:00:00',0,'','13858303843','','','慈溪市龙山龙腾汽车洗车店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxslsltqcxcd',0,0,1,1),
 (186,'慈溪市古塘街道北二环中路42号','','2011-01-26 14:14:57','2010-08-11 00:00:00','2011-01-26 14:14:57','2011-06-30 00:00:00','',90,'胡雄杰','330282033049','2010-08-11 00:00:00',71,'','13967846826','','','慈溪市古塘凯浪汽车装潢店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxsgtklqczzd',0,0,1,1),
 (187,'掌起镇东埠头','','2011-01-26 14:14:57','2010-08-11 00:00:00','2011-01-26 14:14:57','2011-06-30 00:00:00','',90,'乐柱宏','330282033050','2010-08-11 00:00:00',71,'','63772386','13506744810','','慈溪市掌起镇东埠头汽车维修店','维修(三类机动车维修:车身维修、轮胎动平衡及修补)','　',105,'cxszqzdbtqcwxd',0,0,1,1),
 (188,'浒山实验公寓4201-1','','2011-01-26 14:14:57','2006-03-22 00:00:00','2011-01-26 14:14:57','2009-07-01 00:00:00','',90,'戚吕常','330282033051','2010-08-11 00:00:00',71,'','','13957455789','','慈溪市浒山阿常汽车维修服务部','维修(供油系统维护及油品更换,空调维修)','   ',105,'cxszsacqcwxfwb',0,0,1,1),
 (189,'逍林镇福合院村','','2011-01-26 14:14:57','2006-04-29 00:00:00','2011-01-26 14:14:57','2009-07-01 00:00:00','',90,'刘坤贵','330282033052','2010-08-11 00:00:00',71,'','','13034668070','','慈溪市逍林阿贵汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxszlagqcxld',0,0,1,1),
 (190,'慈溪市长河镇沧北村','','2011-01-26 14:14:57','2009-08-14 00:00:00','2011-01-26 14:14:57','2012-06-30 00:00:00','',90,'王永利','330282033053','2009-08-14 00:00:00',71,'','63407074','','','慈溪市长河永利汽车发动机免拆清洗店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxschylqcfdjmcqxd',0,0,1,1),
 (191,'慈溪市胜山镇蔡丁村','','2011-01-26 14:14:57','2010-08-11 00:00:00','2011-01-26 14:14:57','2012-06-30 00:00:00','',90,'曾华云','330282033054','2010-08-11 00:00:00',71,'','','13777931973','','慈溪市胜山华云汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxssshyqcxld',0,0,1,1),
 (192,'慈溪市周巷镇万寿寺村周西公路939号','','2011-01-26 14:14:57','2010-08-23 00:00:00','2011-01-26 14:14:57','2012-12-31 00:00:00','',90,'郑勇','330282033055','2010-08-23 00:00:00',71,'','','13486613224','','慈溪市周巷双吴汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','',105,'cxszxswqcxld',0,0,1,1),
 (193,'慈溪市横河镇龙泉村梅川西路108号','','2011-01-26 14:14:57','2008-11-03 00:00:00','2011-01-26 14:14:57','2011-06-30 00:00:00','',90,'张佰强','330282033056','2010-08-23 00:00:00',0,'','13968204035','','','慈溪市横河路过发洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxshhlgfxcc',0,0,1,1),
 (194,'慈溪市龙山镇王家路村官塘路','','2011-01-26 14:14:58','2008-11-03 00:00:00','2011-01-26 14:14:58','2011-06-30 00:00:00','',90,'王林耀','330282033057','2010-08-23 00:00:00',0,'','63702816','','','慈溪市范市林耀摩托车修理店','维修(二类摩托车维修)','   ',105,'cxsfslymtcxld',0,0,1,1),
 (195,'崇寿镇六塘亭村浒崇公路西侧','','2011-01-26 14:14:58','2010-08-11 00:00:00','2011-01-26 14:14:58','2011-06-30 00:00:00','',90,'胡建立','330282033058','2010-08-11 00:00:00',71,'','','','','慈溪市崇寿镇建立热胶五金修理店','维修(三类机动车维修:轮胎动平衡及修补)','   ',105,'cxscszjlrjwjxld',0,0,1,1),
 (196,'慈溪市周巷镇兴业北路36号','','2011-01-26 14:14:58','2008-11-13 00:00:00','2011-01-26 14:14:58','2011-06-30 00:00:00','',90,'潘斌','330282033059','2010-08-11 00:00:00',71,'','66357209','','','慈溪市周巷大象车身清洁维护店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszxdxcsqjwhd',0,0,1,1),
 (197,'慈溪市周巷镇劳家埭村义四91号','','2011-01-26 14:14:58','2008-11-24 00:00:00','2011-01-26 14:14:58','2011-06-30 00:00:00','',90,'劳建岳','330282033060','2010-08-11 00:00:00',0,'','63499808','','','慈溪市周巷建岳摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxjymtcxld',0,0,1,1),
 (198,'慈溪市浒山街道大河口路1号','','2011-01-26 14:14:58','2010-08-11 00:00:00','2011-01-26 14:14:58','2012-06-30 00:00:00','',90,'梁智华','330282033061','2010-08-24 00:00:00',71,'','13429332840','13429332840','','慈溪市浒山超粤汽车维修服务部','维修(三类机动车维修:车身维修、车身清洁维护)','   ',105,'cxszscyqcwxfwb',0,0,1,1),
 (199,'慈溪市浒山新江路143号','','2011-01-26 14:14:58','2006-12-26 00:00:00','2011-01-26 14:14:58','2009-07-01 00:00:00','',90,'葛益振','330282033063','2010-08-24 00:00:00',71,'','13429258678','','','慈溪市车骑士汽车维修店','维修(三类机动车维修:车身维修)','   ',105,'cxscqsqcwxd',0,0,1,1),
 (200,'慈溪市周巷镇环城北路607号','','2011-01-26 14:14:58','2008-11-17 00:00:00','2011-01-26 14:14:58','2011-06-30 00:00:00','',90,'杨剑云','330282033064','2010-08-24 00:00:00',0,'','13968265140','','','慈溪市周巷剑云摩托车维修店','维修(二类摩托车维修)','   ',105,'cxszxjymtcwxd',0,0,1,1),
 (201,'慈溪市周巷镇云城村新周塘路281号','','2011-01-26 14:14:59','2010-08-23 00:00:00','2011-01-26 14:14:59','2011-06-30 00:00:00','',90,'叶明','330282033065','2010-08-23 00:00:00',0,'','13486041329','','','慈溪市周巷阿明汽车装潢店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszxamqczzd',0,0,1,1),
 (202,'慈溪市周巷镇环城东路688号','','2011-01-26 14:14:59','2010-08-23 00:00:00','2011-01-26 14:14:59','2011-06-30 00:00:00','',90,'华金权','330282033066','2010-08-23 00:00:00',71,'','13819442877','','','慈溪市周巷众诚汽车维修店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszxzcqcwxd',0,0,1,1),
 (203,'慈溪市周巷镇周西社区庙后路2号','','2011-01-26 14:14:59','2008-09-27 00:00:00','2011-01-26 14:14:59','2011-06-30 00:00:00','',90,'赵长君','330282033067','2010-08-23 00:00:00',0,'','15058058064','','','慈溪市周巷长君摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxcjmtcxld',0,0,1,1),
 (204,'慈溪市周巷镇开发路681号','','2011-01-26 14:14:59','2008-09-26 00:00:00','2011-01-26 14:14:59','2011-06-30 00:00:00','',90,'范志强','330282033068','2010-08-23 00:00:00',0,'','13454756194','','','慈溪市周巷志强摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxzqmtcxld',0,0,1,1),
 (205,'慈溪市周巷镇小安村景苑路145号','','2011-01-26 14:14:59','2008-09-23 00:00:00','2011-01-26 14:14:59','2011-06-30 00:00:00','',90,'施红立','330282033069','2010-08-23 00:00:00',0,'','13819442199','','','慈溪市周巷红立摩托车维修店','维修(二类摩托车维修)','   ',105,'cxszxhlmtcwxd',0,0,1,1),
 (206,'慈溪市周巷镇双潭村泥墩潭路A53－22号','','2011-01-26 14:14:59','2008-09-23 00:00:00','2011-01-26 14:14:59','2011-06-30 00:00:00','',90,'苗华桥','330282033070','2010-08-23 00:00:00',71,'','13968237552','','','慈溪市周巷华桥汽车维修店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszxhqqcwxd',0,0,1,1),
 (207,'慈溪市浒山街道南二环线中段245号','','2011-01-26 14:15:00','2010-08-11 00:00:00','2011-01-26 14:15:00','2011-06-30 00:00:00','',81,'陈凌云','330282033071','2010-10-28 00:00:00',71,'','66310853','','','慈溪提比汽车服务有限公司','机动车维修：三类机动车维修（轮胎动平衡及修补、车辆装潢（蓬布、坐垫及内饰））。','   ',105,'cxtbqcfwyxgs',0,0,1,1),
 (208,'慈溪宗汉大道85号','','2011-01-26 14:15:00','2005-12-15 00:00:00','2011-01-26 14:15:00','2008-07-01 00:00:00','',90,'曹建明','330282033072','2010-10-28 00:00:00',71,'','13857452416','','','慈溪肯诺汽车装璜店','维修(汽车装璜)','   ',105,'cxknqczzd',0,0,1,1),
 (209,'慈溪市古塘西洋寺村','','2011-01-26 14:15:00','2010-08-11 00:00:00','2011-01-26 14:15:00','2012-06-30 00:00:00','',90,'胡雪丹','330282033073','2010-08-24 00:00:00',71,'','','','','慈溪市浒山车卫士汽车修理装璜店','机动车维修：三类机动车维修（供油系统维护及油品更换、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszscwsqcxlzzd',0,0,1,1),
 (210,'慈溪市浒山街道孙塘南路东山村洗车场内','','2011-01-26 14:15:00','2010-08-11 00:00:00','2011-01-26 14:15:00','2012-12-31 00:00:00','',90,'季建华','330282033074','2010-08-11 00:00:00',71,'','13454731228','13454731228','','慈溪市万发汽车装潢店','维修(汽车装璜,三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxswfqczzd',0,0,1,1),
 (211,'浒山新城大道A-37.38.39','','2011-01-26 14:15:00','2005-10-18 00:00:00','2011-01-26 14:15:00','2008-07-01 00:00:00','',90,'孙鹏桥','330282033075','2010-08-11 00:00:00',71,'','13456189006','','','慈溪市浒山孙鹏桥汽车装饰服务部','维修(汽车装璜)','   ',105,'cxszsspqqczsfwb',0,0,1,1),
 (212,'慈溪市浒山街道南二环线（二棉南门）','','2011-01-26 14:15:00','2005-12-30 00:00:00','2011-01-26 14:15:00','2008-07-01 00:00:00','',90,'褚红艳','330282033076','2010-08-11 00:00:00',71,'','13566609576','','','慈溪市浒山好友汽车装璜店','维修(汽车装璜)','   ',105,'cxszshyqczzd',0,0,1,1),
 (213,'浒山镇南二环线28-32号','','2011-01-26 14:15:00','2006-03-22 00:00:00','2011-01-26 14:15:00','2009-07-01 00:00:00','',90,'沈建华','330282033077','2010-08-11 00:00:00',71,'','','13777182313','','慈溪市浒山腾飞汽车装璜店','维修(汽车装璜)','   ',105,'cxszstfqczzd',0,0,1,1),
 (214,'慈溪市周巷登州村南区路53号','','2011-01-26 14:15:00','2008-09-22 00:00:00','2011-01-26 14:15:00','2011-06-30 00:00:00','',90,'施姚锡','330282033078','2010-08-11 00:00:00',0,'','63340530','','','慈溪市周巷姚锡摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxyxmtcxld',0,0,1,1),
 (215,'慈溪市周巷镇泥墩潭路B64－11号','','2011-01-26 14:15:00','2008-09-22 00:00:00','2011-01-26 14:15:00','2011-06-30 00:00:00','',90,'姚建挺','330282033079','2010-08-11 00:00:00',0,'','','','','慈溪市周巷建挺摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxjtmtcxld',0,0,1,1),
 (216,'浒山南二环线177号','','2011-01-26 14:15:01','2006-06-16 00:00:00','2011-01-26 14:15:01','2009-07-01 00:00:00','',90,'沈仙华','330282033080','2010-08-11 00:00:00',71,'','63129037','','','慈溪市一得行汽车服务有限公司','维修(三类机动车维修:轮胎动平衡及修补、车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxsydxqcfwyxgs',0,0,1,1),
 (217,'慈溪市周巷镇三江口村草楼','','2011-01-26 14:15:01','2008-09-19 00:00:00','2011-01-26 14:15:01','2011-06-30 00:00:00','',90,'何明','330282033081','2010-08-11 00:00:00',0,'','15957888681','','','慈溪市周巷何明摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxhmmtcxld',0,0,1,1),
 (218,'慈溪市天元镇镇北路口余庵公路旁','','2011-01-26 14:15:01','2010-08-23 00:00:00','2011-01-26 14:15:01','2013-06-30 00:00:00','',90,'计商商','330282033082','2010-09-25 00:00:00',71,'','63451752','','','慈溪市天元龟博士汽车美容维修中心','机动车维修：三类机动车维修（涂漆、车辆装潢（蓬布、坐垫及内饰））。','   ',105,'cxstygbsqcmrwxzx',0,0,1,1),
 (219,'慈溪市周巷镇西三村','','2011-01-26 14:15:01','2008-09-19 00:00:00','2011-01-26 14:15:01','2011-06-30 00:00:00','',90,'姚惠军','330282033083','2010-09-25 00:00:00',0,'','13586720672','','','慈溪市周巷惠军摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxhjmtcxld',0,0,1,1),
 (220,'慈溪市宗汉街道西二环北路421号','','2011-01-26 14:15:01','2007-08-30 00:00:00','2011-01-26 14:15:01','2010-08-30 00:00:00','',90,'翁丹峰','330282033084','2010-09-25 00:00:00',71,'','','','','慈溪迦南汽车装璜店','维修(汽车装璜)','   ',105,'cxznqczzd',0,0,1,1),
 (221,'慈溪市周巷镇三江口村牛角尖南区','','2011-01-26 14:15:01','2008-09-19 00:00:00','2011-01-26 14:15:01','2011-06-30 00:00:00','',90,'郎渭章','330282033085','2010-09-25 00:00:00',0,'','13116689312','','','慈溪市周巷渭章摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxwzmtcxld',0,0,1,1),
 (222,'慈溪市周巷镇长胜市村','','2011-01-26 14:15:01','2008-09-18 00:00:00','2011-01-26 14:15:01','2011-06-30 00:00:00','',90,'蔡治发','330282033086','2010-09-25 00:00:00',0,'','13486451496','','','慈溪市周巷阿发摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxafmtcxld',0,0,1,1),
 (223,'浒山开发大道大发路138号（新海家私城内）','','2011-01-26 14:15:01','2010-08-11 00:00:00','2011-01-26 14:15:01','2011-10-12 00:00:00','',90,'宋学东','330282033087','2010-08-11 00:00:00',71,'','13958268103','13958268103','','慈溪市浒山雨泉汽车装潢服务部','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszsyqqczzfwb',0,0,1,1),
 (224,'慈溪市浒山镇上周塘村杨家103号','','2011-01-26 14:15:01','2010-08-11 00:00:00','2011-01-26 14:15:01','2012-01-07 00:00:00','',90,'孙仕焕','330282033088','2010-08-11 00:00:00',71,'','63020596','','','慈溪市浒山勤丰汽车装璜店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszsqfqczzd',0,0,1,1),
 (225,'慈溪市浒山街道南二环货配中心对面','','2011-01-26 14:15:01','2008-01-09 00:00:00','2011-01-26 14:15:01','2012-01-09 00:00:00','',90,'施建萍','330282033089','2010-08-11 00:00:00',71,'','13706746368','13706746368','','慈溪市浒山建萍洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszsjpxcc',0,0,1,1),
 (226,'慈溪市周巷镇西三车站十字路口','','2011-01-26 14:15:01','2008-09-18 00:00:00','2011-01-26 14:15:01','2011-06-30 00:00:00','',90,'王雪东','330282033090','2010-08-11 00:00:00',0,'','63480268','','','慈溪市周巷阿东摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxadmtcxld',0,0,1,1),
 (227,'观海卫镇新泽村','','2011-01-26 14:15:01','2010-09-15 00:00:00','2011-01-26 14:15:01','2010-07-12 00:00:00','',90,'方天立','330282033091','2010-09-15 00:00:00',71,'','13008955434','','','慈溪市观海卫镇天立汽车电器修理店','维修(电气系统维修)','   ',105,'cxsghwztlqcdqxld',0,0,1,1),
 (228,'慈溪市周巷镇周西居委开发路南','','2011-01-26 14:15:02','2010-08-23 00:00:00','2011-01-26 14:15:02','2011-06-30 00:00:00','',90,'曹建奇','330282033092','2010-08-23 00:00:00',71,'','13958297832','','','慈溪市周巷奇银汽车车身清洁维护店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszxqyqccsqjwhd',0,0,1,1),
 (229,'慈溪市周巷镇上庵东周塘路沿81号','','2011-01-26 14:15:02','2008-09-17 00:00:00','2011-01-26 14:15:02','2011-06-30 00:00:00','',90,'唐小兵','330282033093','2010-08-23 00:00:00',71,'','13645844961','','','慈溪市周巷佳云汽车车身清洁维护店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszxjyqccsqjwhd',0,0,1,1),
 (230,'慈溪市浒山街道南二环线251号','','2011-01-26 14:15:02','2010-08-11 00:00:00','2011-01-26 14:15:02','2011-12-24 00:00:00','',81,'贾正艳','330282033094','2010-08-11 00:00:00',71,'','13567936765','13567936765','','慈溪车至洁汽车服务有限公司','维修(三类机动车维修:车身清洁维护、车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxczjqcfwyxgs',0,0,1,1),
 (231,'慈溪市周巷镇周西社区井亭庵','','2011-01-26 14:15:02','2010-08-23 00:00:00','2011-01-26 14:15:02','2011-06-30 00:00:00','',90,'何国文','330282033095','2010-08-23 00:00:00',71,'','13625840944','','','慈溪市周巷广鸿汽车车身清洁维护店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszxghqccsqjwhd',0,0,1,1),
 (232,'慈溪市新浦镇水湘村','','2011-01-26 14:15:02','2007-12-07 00:00:00','2011-01-26 14:15:02','2011-12-07 00:00:00','',90,'岑义清','330282033096','2010-08-23 00:00:00',71,'','13989335255','13989335255','','慈溪市新浦宏诚洗车店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxsxphcxcd',0,0,1,1),
 (233,'慈溪市匡堰镇高家村后张埭','','2011-01-26 14:15:02','2008-03-10 00:00:00','2011-01-26 14:15:02','2012-03-10 00:00:00','',90,'胡雪南','330282033097','2010-08-23 00:00:00',71,'','15306612357','15306612357','','慈溪市匡堰雪南洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxskyxnxcc',0,0,1,1),
 (234,'浒山三北大街与东三环北路交叉口东南','','2011-01-26 14:15:02','2008-03-18 00:00:00','2011-01-26 14:15:02','2012-03-18 00:00:00','',90,'华志芳','330282033098','2010-08-23 00:00:00',71,'','13429392175','13429392175','','慈溪市浒山梦蕾洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszsmlxcc',0,0,1,1),
 (235,'掌起镇周家段村慈掌路715号','','2011-01-26 14:15:02','2008-03-28 00:00:00','2011-01-26 14:15:02','2011-03-28 00:00:00','',90,'王慈波','330282033099','2010-08-23 00:00:00',71,'','','','','慈溪市掌起镇超洁汽车店','维修(车身清洁维护)','   ',105,'cxszqzcjqcd',0,0,1,1),
 (236,'慈溪市周巷镇环城西路380号','','2011-01-26 14:15:02','2010-08-23 00:00:00','2011-01-26 14:15:02','2011-06-30 00:00:00','',90,'曹建明','330282033100','2010-08-23 00:00:00',71,'','13252283530','','','慈溪市周巷旺达车身清洁维护店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszxwdcsqjwhd',0,0,1,1),
 (237,'慈溪市西二环线湾底村','','2011-01-26 14:15:02','2010-08-11 00:00:00','2011-01-26 14:15:02','2011-06-30 00:00:00','',85,'周启展','330282033101','2010-08-11 00:00:00',71,'','63895125','13306625729','','慈溪市浒山小周车身维修店','维修(车身维修,电气系统维修)','   ',105,'cxszsxzcswxd',0,0,1,1),
 (238,'慈溪市白沙路街道孙塘南路218号','','2011-01-26 14:15:02','2010-08-11 00:00:00','2011-01-26 14:15:02','2011-06-30 00:00:00','',90,'徐旭波','330282033102','2010-08-11 00:00:00',71,'','66367185','13968263833','','慈溪市浒山阿三汽车维修店','维修(车身维修)','   ',105,'cxszsasqcwxd',0,0,1,1),
 (239,'慈溪市匡堰镇高家村','','2011-01-26 14:15:02','2010-09-15 00:00:00','2011-01-26 14:15:02','2011-06-30 00:00:00','',90,'徐国范','330282033103','2010-09-15 00:00:00',71,'','13606746773','','','慈溪市万国汽车维修店','维修(车身维修)','   ',105,'cxswgqcwxd',0,0,1,1),
 (240,'慈溪市宗汉镇徐家村','','2011-01-26 14:15:02','2005-07-01 00:00:00','2011-01-26 14:15:02','2008-07-01 00:00:00','',90,'徐正权','330282033104','2010-09-15 00:00:00',71,'','63223075','13777107120','','慈溪市宗汉镇正权汽车车身维修店','维修(车身维修)','   ',105,'cxszhzzqqccswxd',0,0,1,1),
 (241,'慈溪市白沙镇','','2011-01-26 14:15:02','2005-07-01 00:00:00','2011-01-26 14:15:02','2008-07-01 00:00:00','',90,'庄鸿型','330282033105','2010-09-15 00:00:00',71,'','63020033','','','慈溪市浒山鸿型汽车车身维修店','维修(车身维修)','   ',105,'cxszshxqccswxd',0,0,1,1),
 (242,'浒山街道杨家路桥西','','2011-01-26 14:15:02','2010-08-11 00:00:00','2011-01-26 14:15:02','2012-06-30 00:00:00','',90,'胡松群','330282033106','2010-08-11 00:00:00',71,'','13586618281','63820248','','慈溪市浒山车韵车身修理店','维修(车身维修)','   ',105,'cxszscycsxld',0,0,1,1),
 (243,'慈溪市崇身寿镇六塘','','2011-01-26 14:15:02','2005-07-01 00:00:00','2011-01-26 14:15:02','2008-07-01 00:00:00','',90,'孙孟义','330282033107','2010-08-11 00:00:00',71,'','13065625015','','','慈溪市崇寿镇孟义汽车车身维修店','维修(车身维修)','   ',105,'cxscszmyqccswxd',0,0,1,1),
 (244,'慈溪市范市溪浦村','','2011-01-26 14:15:03','2002-07-01 00:00:00','2011-01-26 14:15:03','2005-07-01 00:00:00','',90,'阮忠仟','330282033108','2010-08-11 00:00:00',71,'','63702528','','','慈溪市范市忠仟汽车车身维修店','维修(车身维修)','   ',105,'cxsfszqqccswxd',0,0,1,1),
 (245,'慈溪市古塘街道浒崇公路132-136号','','2011-01-26 14:15:03','2010-08-11 00:00:00','2011-01-26 14:15:03','2012-06-30 00:00:00','',90,'陈利军','330282033109','2010-08-24 00:00:00',0,'13586790521','','','','慈溪市古塘乐皓汽车修理店','机动车维修：三类机动车维修（轮胎动平衡及修补、四轮定位检测调整）。','',105,'cxsgtlzqcxld',0,0,1,1),
 (246,'慈溪浒山鸣山村','','2011-01-26 14:15:03','2010-08-11 00:00:00','2011-01-26 14:15:03','2011-06-30 00:00:00','',90,'陈利权','330282033110','2010-08-11 00:00:00',71,'','13606743824','','','慈溪市浒山凯达汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxszskdqcxld',0,0,1,1),
 (247,'慈溪市横河乌山村综合楼455号','','2011-01-26 14:15:03','2010-08-11 00:00:00','2011-01-26 14:15:03','2011-06-30 00:00:00','',90,'邹建君','330282033111','2010-08-11 00:00:00',71,'','13805820586','63107345','','慈溪市横河镇三鑫汽车维修店','维修(车身维修)','   ',105,'cxshhzszqcwxd',0,0,1,1),
 (248,'慈溪市龙山镇镇淞浦村329国道27号','','2011-01-26 14:15:03','2010-08-11 00:00:00','2011-01-26 14:15:03','2011-06-30 00:00:00','',90,'朱海江','330282033112','2010-08-11 00:00:00',71,'','63700665','13306742752','','慈溪市龙山镇范市海江车身维修店','维修(三类机动车维修:车身维修、涂漆)','   ',105,'cxslszfshjcswxd',0,0,1,1),
 (249,'慈溪市庵东镇南村庵余路','','2011-01-26 14:15:03','2005-07-01 00:00:00','2011-01-26 14:15:03','2008-07-01 00:00:00','',90,'庄士坚','330282033113','2010-08-11 00:00:00',71,'','63477111','13336657610','','慈溪市庵东镇鸿达汽车车身维修店','维修(车身维修)','   ',105,'cxszdzhdqccswxd',0,0,1,1),
 (250,'慈溪附海镇海晏庙村','','2011-01-26 14:15:03','2008-10-21 00:00:00','2011-01-26 14:15:03','2011-06-30 00:00:00','',90,'方均','330282033114','2010-08-11 00:00:00',71,'','63567898','13506747525','','慈溪市附海晨茜汽车车身维修店','维修(车身维修)','   ',105,'cxsfhczqccswxd',0,0,1,1),
 (251,'慈溪市青少年宫北路443－449号','','2011-01-26 14:15:03','2010-08-11 00:00:00','2011-01-26 14:15:03','2012-12-31 00:00:00','',90,'许卫倩','330282033115','2010-08-11 00:00:00',71,'','13906745455','','','慈溪市浒山舒耐斯汽车修理部','维修(车身维修,汽车装璜)','   ',105,'cxszssnsqcxlb',0,0,1,1),
 (252,'慈溪市庵东镇新建村','','2011-01-26 14:15:03','2010-08-23 00:00:00','2011-01-26 14:15:03','2012-12-25 00:00:00','',90,'施国炳','330282033116','2010-08-23 00:00:00',0,'13484271937','','','','慈溪市庵东杭湾汽车修理厂','机动车维修：三类机动车维修（发动机修理、车身维修）。','',105,'cxszdhwqcxlc',0,0,1,1),
 (253,'慈溪市周巷镇万寿寺村周西公路229号','','2011-01-26 14:15:03','2008-11-28 00:00:00','2011-01-26 14:15:03','2011-06-30 00:00:00','',90,'马罗垒','330282033117','2010-08-23 00:00:00',0,'','13857876661','','','慈溪市周巷磊子摩托车维修店','维修(二类摩托车维修)','   ',105,'cxszxlzmtcwxd',0,0,1,1),
 (254,'慈溪市周巷镇镇西路114号','','2011-01-26 14:15:04','2008-12-03 00:00:00','2011-01-26 14:15:04','2011-06-30 00:00:00','',90,'熊岳明','330282033118','2010-08-23 00:00:00',0,'','13566338493','','','慈溪市周巷岳明摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxymmtcxld',0,0,1,1),
 (255,'慈溪市白沙路街道南二环路东向阳渔港对面','','2011-01-26 14:15:04','2010-08-11 00:00:00','2011-01-26 14:15:04','2012-06-30 00:00:00','',90,'米耀华','330282033120','2010-08-11 00:00:00',0,'15258263567','','','','慈溪市白沙路文文洗车场','维修(三类机动车维修:车身清洁维护)','',105,'cxsbslwwxcc',0,0,1,1),
 (256,'慈溪市西二环线北路459号','','2011-01-26 14:15:04','2010-08-11 00:00:00','2011-01-26 14:15:04','2011-06-16 00:00:00','',90,'陆建锋','330282033121','2010-08-11 00:00:00',71,'','63212623','13906622509','','慈溪市浒山永茂汽车车身修理店','维修(车身维修)','   ',105,'cxszsymqccsxld',0,0,1,1),
 (257,'慈溪市鸣鹤医院对面','','2011-01-26 14:15:04','2010-09-15 00:00:00','2011-01-26 14:15:04','2011-06-30 00:00:00','',90,'王增吉','330282033122','2010-09-15 00:00:00',71,'','63674978','13968228079','','慈溪市鸣鹤增吉汽车车身维修店','维修(车身维修)','   ',105,'cxsmhzjqccswxd',0,0,1,1),
 (258,'慈溪市匡堰镇高家村匡堰大道','','2011-01-26 14:15:04','2010-09-15 00:00:00','2011-01-26 14:15:04','2011-06-30 00:00:00','',90,'陈奇柯','330282033123','2010-09-15 00:00:00',0,'','','','','慈溪市匡堰亿胜汽车装潢店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxskyysqczzd',0,0,1,1),
 (259,'慈溪市横河镇上建山村１号桥旁','','2011-01-26 14:15:04','2010-08-11 00:00:00','2011-01-26 14:15:04','2011-06-30 00:00:00','',90,'胡理权','330282033124','2010-08-11 00:00:00',71,'','63198347','13506745425','','慈溪市横河镇理权汽车钣金维修店','维修(车身维修)','   ',105,'cxshhzlqqczjwxd',0,0,1,1),
 (260,'慈溪市范市镇小范方村','','2011-01-26 14:15:04','2005-07-01 00:00:00','2011-01-26 14:15:04','2008-07-01 00:00:00','',90,'胡志挺','330282033125','2010-08-11 00:00:00',71,'','13968399749','','','慈溪市范市镇志挺汽车钣金店','维修(车身维修)','   ',105,'cxsfszztqczjd',0,0,1,1),
 (261,'慈溪市周巷镇天灯舍村','','2011-01-26 14:15:04','2010-08-23 00:00:00','2011-01-26 14:15:04','2011-06-30 00:00:00','',90,'施伟建','330282033126','2010-08-23 00:00:00',71,'','','','','慈溪市周巷建伟汽车修理店','维修(车身维修)','',105,'cxszxjwqcxld',0,0,1,1),
 (262,'慈溪市浒山小岭墩西南侧','','2011-01-26 14:15:05','2002-07-01 00:00:00','2011-01-26 14:15:05','2005-07-01 00:00:00','',90,'季德平','330282033127','2010-08-23 00:00:00',71,'','63896566','','','慈溪市浒山镇德平汽车钣金维修店','维修(车身维修)','   ',105,'cxszszdpqczjwxd',0,0,1,1),
 (263,'周巷环成东路850号','','2011-01-26 14:15:05','2010-08-23 00:00:00','2011-01-26 14:15:05','2011-06-30 00:00:00','',90,'傅银杰','330282033128','2010-08-23 00:00:00',0,'','13586767137','','','慈溪市周巷银杰汽车装潢店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszxyjqczzd',0,0,1,1),
 (264,'慈溪市浒山街道施宿路5号','','2011-01-26 14:15:05','2010-08-11 00:00:00','2011-01-26 14:15:05','2011-06-30 00:00:00','',90,'朱菊洪','330282033130','2010-08-11 00:00:00',0,'','13968267798','','','慈溪市浒山洁亮洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszsjlxcc',0,0,1,1),
 (265,'慈溪浒山镇群丰村','','2011-01-26 14:15:05','2005-07-01 00:00:00','2011-01-26 14:15:05','2008-07-01 00:00:00','',90,'钱国平','330282033131','2010-08-11 00:00:00',71,'','13858314088','','','慈溪市浒山国平汽车五金维修店','维修(车身维修)','   ',105,'cxszsgpqcwjwxd',0,0,1,1),
 (266,'慈溪市浒山西二环城','','2011-01-26 14:15:05','2005-07-01 00:00:00','2011-01-26 14:15:05','2008-07-01 00:00:00','',90,'莫张夫','330282033133','2010-08-11 00:00:00',71,'','63892360','13616780858','','慈溪市浒山镇张夫汽车钣金维修店','维修(车身维修)','   ',105,'cxszszzfqczjwxd',0,0,1,1),
 (267,'慈溪市浒山镇北二环线西路口','','2011-01-26 14:15:05','2002-07-01 00:00:00','2011-01-26 14:15:05','2005-07-01 00:00:00','',90,'叶均南','330282033134','2010-08-11 00:00:00',71,'','63801938','','','慈溪市浒山镇美家亮汽车美容店','维修(车身维修)','   ',105,'cxszszmjlqcmrd',0,0,1,1),
 (268,'浒山镇孙塘路365号','','2011-01-26 14:15:05','2005-07-01 00:00:00','2011-01-26 14:15:05','2008-07-01 00:00:00','',90,'俞忠忠','330282033135','2010-08-11 00:00:00',71,'','63040177','13506744374','','慈溪市浒山俞忠油泵修理店','维修(喷油泵和喷油器维修)','   ',105,'cxszsyzybxld',0,0,1,1),
 (269,'慈溪市周巷镇车站7号','','2011-01-26 14:15:05','2002-07-01 00:00:00','2011-01-26 14:15:05','2005-07-01 00:00:00','',90,'徐冲','330282033137','2010-08-11 00:00:00',71,'','63305586','','','慈溪市周巷宝中汽车修理店','维修(车身维修)','   ',105,'cxszxbzqcxld',0,0,1,1),
 (270,'慈溪市古塘街道孙塘北路826号','','2011-01-26 14:15:05','2010-08-11 00:00:00','2011-01-26 14:15:05','2011-12-08 00:00:00','',90,'沈建平','330282033138','2010-08-11 00:00:00',71,'13805815688','63029699','63185688','','慈溪市浒山金田汽车车身修理店','维修(车身维修)','   ',105,'cxszsjtqccsxld',0,0,1,1),
 (271,'慈溪市天元镇火车跟村','','2011-01-26 14:15:06','2010-08-23 00:00:00','2011-01-26 14:15:06','2011-06-30 00:00:00','',90,'张汉军','330282033139','2010-08-23 00:00:00',71,'','63459545','13003766122','','慈溪市天元镇汉军车身修理店','维修(车身维修)','   ',105,'cxstyzhjcsxld',0,0,1,1),
 (272,'慈溪市长河镇沧北村芦庵公路1158号','','2011-01-26 14:15:06','2010-08-23 00:00:00','2011-01-26 14:15:06','2011-06-30 00:00:00','',90,'姚金苗','330282033140','2010-08-23 00:00:00',71,'','63473970','13034639576','','慈溪市长河建法汽车修理店','维修(三类机动车维修:车身维修)','大型货车维修（无烤漆房）',105,'cxschjfqcxld',0,0,1,1),
 (273,'慈溪市胜山镇胜南村老塘西路106-107号','','2011-01-26 14:15:06','2010-08-11 00:00:00','2011-01-26 14:15:06','2011-06-30 00:00:00','',90,'潘迪锋','330282033141','2010-08-11 00:00:00',71,'','','13706740932','','慈溪胜山镇迪锋喷油化油器修理店','维修(喷油泵和喷油器维修)','   ',105,'cxsszdfpyhyqxld',0,0,1,1),
 (274,'慈溪市周巷镇环城北路373号','','2011-01-26 14:15:06','2006-01-13 00:00:00','2011-01-26 14:15:06','2009-07-01 00:00:00','',90,'黄青芬','330282033142','2010-08-11 00:00:00',71,'','63323086','','','慈溪市周巷艳伶汽车修理店','维修(车身清洁维护)','   ',105,'cxszxylqcxld',0,0,1,1),
 (275,'慈溪市浒山镇吉祥新村56号','','2011-01-26 14:15:06','2003-05-22 00:00:00','2011-01-26 14:15:06','2006-05-22 00:00:00','',90,'孙君杰','330282033143','2010-08-11 00:00:00',71,'','13805826193','','','慈溪市浒山镇君杰车身清洁维护店','维修(车身清洁维护)','   ',105,'cxszszjjcsqjwhd',0,0,1,1),
 (276,'慈溪市浒山镇寺山路322号','','2011-01-26 14:15:06','2010-08-11 00:00:00','2011-01-26 14:15:06','2011-10-12 00:00:00','',90,'赵云','330282033147','2010-08-11 00:00:00',71,'','63102946','','','慈溪市浒山赵云清洗店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszszyqxd',0,0,1,1),
 (277,'慈溪市庵东镇庵余路347号','','2011-01-26 14:15:06','2007-11-07 00:00:00','2011-01-26 14:15:06','2011-11-07 00:00:00','',90,'黄伟潮','330282033148','2010-08-11 00:00:00',71,'','63475349','','','慈溪市庵东阿二洗车店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszdaexcd',0,0,1,1),
 (278,'慈溪市胜山镇镇前村胜地名苑A 区','','2011-01-26 14:15:06','2007-11-14 00:00:00','2011-01-26 14:15:06','2011-11-14 00:00:00','',90,'吴宝','330282033149','2010-08-11 00:00:00',71,'','13250958598','13250958598','','慈溪市胜山车爵仕清洗服务部','维修(三类机动车维修:车身清洁维护)','   ',105,'cxssscjsqxfwb',0,0,1,1),
 (279,'坎墩三四灶村','','2011-01-26 14:15:07','2008-04-28 00:00:00','2011-01-26 14:15:07','2011-04-28 00:00:00','',90,'胡群峰','330282033150','2010-08-11 00:00:00',71,'','','','','慈溪市坎墩群峰洗车场','维修(车身清洁维护)','   ',105,'cxskdqfxcc',0,0,1,1),
 (280,'慈溪市庵东镇华兴村新市东路','','2011-01-26 14:15:07','2008-04-30 00:00:00','2011-01-26 14:15:07','2012-04-30 00:00:00','',90,'阮登惠','330282033151','2010-08-11 00:00:00',71,'','13606883756','13606883756','','慈溪市庵东惠之洁洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszdhzjxcc',0,0,1,1),
 (281,'慈溪市三北镇双马村三北路','','2011-01-26 14:15:07','2008-05-14 00:00:00','2011-01-26 14:15:07','2012-05-14 00:00:00','',90,'龙伯刚','330282033152','2010-08-11 00:00:00',71,'','13065682208','','','慈溪市三北鹏程汽车店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxssbpcqcd',0,0,1,1),
 (282,'慈溪市庵东镇汽车站停车场','','2011-01-26 14:15:07','2008-05-22 00:00:00','2011-01-26 14:15:07','2011-05-22 00:00:00','',90,'陈利焕','330282033153','2010-08-11 00:00:00',71,'','','','','慈溪市庵东利焕洗车店','维修(车身清洁维护)','   ',105,'cxszdlhxcd',0,0,1,1),
 (283,'慈溪市附海镇观附公路1530＃--1534＃','','2011-01-26 14:15:07','2009-01-07 00:00:00','2011-01-26 14:15:07','2012-06-30 00:00:00','',90,'张惠儿','330282033154','2010-08-11 00:00:00',0,'','13484266294','','','慈溪市附海阿强洗车店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxsfhaqxcd',0,0,1,1),
 (284,'慈溪市周巷镇义让路村','','2011-01-26 14:15:07','2009-01-07 00:00:00','2011-01-26 14:15:07','2011-06-30 00:00:00','',90,'候海阳','330282033155','2010-08-11 00:00:00',0,'','13008992996','','','慈溪市周巷海阳摩托车维修店','维修(二类摩托车维修)','   ',105,'cxszxhymtcwxd',0,0,1,1),
 (285,'慈溪市宗汉街道新界村','','2011-01-26 14:15:07','2009-01-13 00:00:00','2011-01-26 14:15:07','2011-06-30 00:00:00','',90,'朱建芬','330282033156','2010-08-11 00:00:00',0,'','63229352','','','慈溪市宗汉建芬洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszhjfxcc',0,0,1,1),
 (286,'慈溪市周巷镇周邵村（塘南）','','2011-01-26 14:15:07','2010-08-23 00:00:00','2011-01-26 14:15:07','2013-06-30 00:00:00','',90,'鲁立权','330282033157','2010-08-23 00:00:00',71,'','63338579','','','慈溪市周巷镇恒水汽车维修店','维修(车身维修,涂漆)','   ',105,'cxszxzhsqcwxd',0,0,1,1),
 (287,'329国道周巷东修配厂旁','','2011-01-26 14:15:07','1996-11-29 00:00:00','2011-01-26 14:15:07','2006-09-29 00:00:00','',90,'周长林','330282033158','2010-08-23 00:00:00',71,'','','','','慈溪市周巷河南喷油泵修理店','维修(喷油泵和喷油器维修)','   ',105,'cxszxhnpybxld',0,0,1,1),
 (288,'慈溪市匡堰樟树村','','2011-01-26 14:15:07','2010-09-15 00:00:00','2011-01-26 14:15:07','2011-06-30 00:00:00','',90,'何伟敏','330282033159','2010-09-15 00:00:00',71,'','63531862','13806641796','','慈溪市匡堰镇伟敏挡风玻璃店','维修(门窗玻璃安装)','   ',105,'cxskyzwmdfbld',0,0,1,1),
 (289,'宗汉．宗兴路','','2011-01-26 14:15:07','2005-11-09 00:00:00','2011-01-26 14:15:07','2008-07-01 00:00:00','',90,'施国炳','330282033160','2010-09-15 00:00:00',71,'','66320537','','','慈溪市宗汉国炳汽车维修厂','维修(车身维修,涂漆)','   ',105,'cxszhgbqcwxc',0,0,1,1),
 (290,'慈溪市天元工业区（余庵公路203号）','','2011-01-26 14:15:08','2010-08-23 00:00:00','2011-01-26 14:15:08','2012-06-30 00:00:00','',90,'卢立军','330282033161','2010-08-23 00:00:00',71,'','13968229254','','','慈溪市天元兴光汽车维修店','维修(车身维修,供油系统维护及油品更换)','   ',105,'cxstyxgqcwxd',0,0,1,1),
 (291,'慈溪市观海卫镇下泽山村','','2011-01-26 14:15:08','2010-09-15 00:00:00','2011-01-26 14:15:08','2011-06-30 00:00:00','',90,'沈国林','330282033162','2010-09-15 00:00:00',71,'','63618930','','','慈溪市观海卫镇城东汽车电焊钣金店','维修(车身维修)','   ',105,'cxsghwzcdqcdhzjd',0,0,1,1),
 (292,'慈溪市龙山镇慈龙西路14号','','2011-01-26 14:15:08','2010-08-31 00:00:00','2011-01-26 14:15:08','2012-11-06 00:00:00','',90,'范雪孟','330282033163','2010-08-31 00:00:00',71,'13857452185','','','','慈溪市龙山雪孟汽车维修店','机动车维修：三类机动车维修、汽车快修）。','',105,'cxslsxmqcwxd',0,0,1,1),
 (293,'慈溪市周巷镇三江口村','','2011-01-26 14:15:08','2005-07-01 00:00:00','2011-01-26 14:15:08','2008-07-01 00:00:00','',90,'杨立波','330282033164','2010-08-31 00:00:00',71,'','63338003','13056743688','','慈溪市杭州湾镇立波汽车小修店','维修(发动机维修)','   ',105,'cxshzwzlbqcxxd',0,0,1,1),
 (294,'慈溪市长河沧南村四塘横路438号','','2011-01-26 14:15:08','2010-08-23 00:00:00','2011-01-26 14:15:08','2011-06-30 00:00:00','',90,'林菊如','330282033165','2010-08-23 00:00:00',71,'','63415339','','','慈溪市长河镇如荣汽车修理店','维修(车身维修)','   ',105,'cxschzrrqcxld',0,0,1,1),
 (295,'逍林樟南路','','2011-01-26 14:15:08','2002-07-01 00:00:00','2011-01-26 14:15:08','2005-07-01 00:00:00','',90,'岑志明','330282033166','2010-08-23 00:00:00',71,'','63516546','13806645206','','慈溪市兴裕汽车修理店','维修(发动机维修)','   ',105,'cxsxyqcxld',0,0,1,1),
 (296,'慈溪市胜山镇一灶村','','2011-01-26 14:15:08','2006-06-06 00:00:00','2011-01-26 14:15:08','2009-07-01 00:00:00','',90,'任裕','330282033167','2010-08-23 00:00:00',71,'','','13008935342','','慈溪市胜山任裕汽车修理店','维修(三类机动车维修:车身维修;涂漆)','   ',105,'cxsssryqcxld',0,0,1,1),
 (297,'天元镇火车跟村','','2011-01-26 14:15:08','2010-12-27 00:00:00','2011-01-26 14:15:08','2013-06-30 00:00:00','',90,'许立新','330282033168','2010-12-27 00:00:00',71,'','63458242','','','慈溪市天元镇立新汽车维修厂','维修(车身维修)','',105,'cxstyzlxqcwxc',0,0,1,1),
 (298,'慈溪市浒山明州路','','2011-01-26 14:15:09','2006-08-17 00:00:00','2011-01-26 14:15:09','2009-07-01 00:00:00','',90,'陈杰','330282033170','2010-12-27 00:00:00',71,'','66136796','','','慈溪市浒山萍杰汽配店','维修(车身维修,涂漆)','   ',105,'cxszspjqpd',0,0,1,1),
 (299,'慈溪市龙山镇邱洋村','','2011-01-26 14:15:09','2005-07-01 00:00:00','2011-01-26 14:15:09','2008-07-01 00:00:00','',90,'应根友','330282033171','2010-12-27 00:00:00',71,'','63788203','13606741091','','慈溪市龙山镇信誉车身维修店','维修(车身维修)','   ',105,'cxslszxycswxd',0,0,1,1),
 (300,'慈溪市慈百路17号','','2011-01-26 14:15:09','2008-04-16 00:00:00','2011-01-26 14:15:09','2012-04-16 00:00:00','',85,'柯炳杰','330282033172','2010-12-27 00:00:00',71,'13306621666','63881878','13968212222','','慈溪市万富汽车维修厂','维修(发动机维修,车身维修)','   ',105,'cxswfqcwxc',0,0,1,1),
 (301,'慈溪市观海卫镇小团浦村','','2011-01-26 14:15:09','2010-09-15 00:00:00','2011-01-26 14:15:09','2011-01-18 00:00:00','',90,'施志通','330282033173','2010-09-15 00:00:00',71,'','6365283','13806640477','','慈溪金鹰汽车维修厂','机动车维修：三类机动车维修（发动机修理、车身维修）。','   ',105,'cxjyqcwxc',0,0,1,1),
 (302,'慈溪市长河镇沧田村','','2011-01-26 14:15:09','2008-01-29 00:00:00','2011-01-26 14:15:09','2011-01-29 00:00:00','',90,'张华治','330282033174','2010-09-15 00:00:00',71,'','13606744404','13606744404','','慈溪市长河镇建银汽车维修厂','维修(三类机动车维修:发动机修理)','   ',105,'cxschzjyqcwxc',0,0,1,1),
 (303,'浒山镇乌山南路（市政公司内）','','2011-01-26 14:15:09','2005-07-01 00:00:00','2011-01-26 14:15:09','2008-07-01 00:00:00','',90,'任金苗','330282033175','2010-09-15 00:00:00',71,'','63111849','13008924061','','慈溪市浒山阿苗汽车维修店','维修(发动机维修)','   ',105,'cxszsamqcwxd',0,0,1,1),
 (304,'慈溪市白沙路街道慈甬路853号','','2011-01-26 14:15:09','2010-08-11 00:00:00','2011-01-26 14:15:09','2011-04-25 00:00:00','',85,'叶迪芳','330282033176','2010-08-11 00:00:00',71,'','63109333','13957458062','','慈溪市新迪汽车修理厂(普通合伙）','维修(三类机动车维修:发动机修理、车身维修)','   ',105,'cxsxdqcxlc(pthh）',0,0,1,1),
 (305,'慈溪市浒山街道担山跟村','','2011-01-26 14:15:09','2010-08-11 00:00:00','2011-01-26 14:15:09','2012-12-31 00:00:00','',90,'孙松岳','330282033177','2010-08-11 00:00:00',0,'13805826023','','','','慈溪市浒山担山洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszsdsxcc',0,0,1,1),
 (306,'慈溪市龙山镇东门外村','','2011-01-26 14:15:09','2010-08-11 00:00:00','2011-01-26 14:15:09','2011-06-30 00:00:00','',90,'王存立','330282033178','2010-08-11 00:00:00',71,'','63781373','13157411789','','慈溪市龙山华丽汽车修理部','维修(发动机维修)','   ',105,'cxslshlqcxlb',0,0,1,1),
 (307,'慈溪市庵东镇华兴村','','2011-01-26 14:15:09','2009-10-09 00:00:00','2011-01-26 14:15:09','2012-06-30 00:00:00','',90,'傅国华','330282033180','2009-10-09 00:00:00',71,'13336873699','63482198','13216742167','','慈溪市庵东本达汽车维修店','维修(发动机维修)','   ',105,'cxszdbdqcwxd',0,0,1,1),
 (308,'慈溪市周巷镇井亭庵村','','2011-01-26 14:15:10','2009-03-19 00:00:00','2011-01-26 14:15:10','2012-06-30 00:00:00','',90,'姚国昌','330282033181','2009-03-19 00:00:00',0,'','63323098','','','慈溪市周巷宏顺汽车维修店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszxhsqcwxd',0,0,1,1),
 (309,'慈溪市周巷镇环城东路688号','','2011-01-26 14:15:10','2010-08-23 00:00:00','2011-01-26 14:15:10','2011-06-30 00:00:00','',90,'王银燕','330282033182','2010-08-23 00:00:00',0,'13486617077','','','','慈溪市周巷飞腾汽车装潢店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszxftqczzd',0,0,1,1),
 (310,'慈溪市横河镇建山花坛旁','','2011-01-26 14:15:10','2005-07-01 00:00:00','2011-01-26 14:15:10','2008-07-01 00:00:00','',90,'胡阔柱','330282033183','2010-08-23 00:00:00',71,'','13968202115','','','慈溪市横河镇阔柱汽车电器维修店','维修(电气系统维修)','   ',105,'cxshhzkzqcdqwxd',0,0,1,1),
 (311,'慈溪市白沙路街道八字桥横河塍35号','','2011-01-26 14:15:10','2009-03-27 00:00:00','2011-01-26 14:15:10','2009-06-27 00:00:00','',90,'刘新起','330282033184','2009-03-27 00:00:00',0,'13567811729','','','','慈溪市白沙路老刘汽车修理店','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxsbslllqcxld',0,0,1,1),
 (312,'慈溪市胜山镇镇前村','','2011-01-26 14:15:10','2010-08-11 00:00:00','2011-01-26 14:15:10','2011-06-30 00:00:00','',90,'岑建杰','330282033185','2010-08-11 00:00:00',0,'','13958244081','','','慈溪市胜山瑞丰汽车修理店','机动车维修：三类机动车维修（车身维修）。','',105,'cxsssrfqcxld',0,0,1,1),
 (313,'慈溪市浒山街道剑山路195号','','2011-01-26 14:15:10','2010-08-11 00:00:00','2011-01-26 14:15:10','2012-06-30 00:00:00','',90,'张荣华','330282033186','2010-08-24 00:00:00',0,'','13958232588','','',' 慈溪市浒山富荣汽车维修店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,' cxszsfrqcwxd',0,0,1,1),
 (314,'慈溪市浒山街道界牌村','','2011-01-26 14:15:10','2009-04-09 00:00:00','2011-01-26 14:15:10','2011-06-30 00:00:00','',90,'孙君杰','330282033187','2010-05-13 00:00:00',0,'13003753705','','','','慈溪市古塘浒崇汽车维修厂','机动车维修：三类机动车维修（发动机修理）。','',105,'cxsgtzcqcwxc',0,0,1,1),
 (315,'慈溪市周巷镇陈家村','','2011-01-26 14:15:10','2009-04-15 00:00:00','2011-01-26 14:15:10','2011-06-30 00:00:00','',90,'江新生','330282033188','2009-04-15 00:00:00',0,'13291952532','','','','慈溪市周巷新生摩托车维修店','机动车维修：摩托车维修。','',105,'cxszxxsmtcwxd',0,0,1,1),
 (316,'慈溪市横河宜青桥村','','2011-01-26 14:15:10','2010-08-11 00:00:00','2011-01-26 14:15:10','2011-06-30 00:00:00','',90,'高春水','330282033189','2010-08-11 00:00:00',0,'13306661537','','','','慈溪市横河春水汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxshhcsqcxld',0,0,1,1),
 (317,'慈百路328号','','2011-01-26 14:15:10','2004-05-13 00:00:00','2011-01-26 14:15:10','2007-05-13 00:00:00','',90,'唐功法','330282033190','2010-08-11 00:00:00',71,'','','','','浒山龟博士汽车装璜店','维修(汽车装璜)','   ',105,'zsgbsqczzd',0,0,1,1),
 (318,'慈溪市浒山新城大道北段东侧','','2011-01-26 14:15:10','2005-07-01 00:00:00','2011-01-26 14:15:10','2008-07-01 00:00:00','',85,'郑丽达','330282033191','2010-08-11 00:00:00',71,'','63036666','13906745778','','宁波市新天地汽车销售服务有限公司浒山分公司','维修(汽车装璜)','   ',105,'nbsxtdqcxsfwyxgszsfgs',0,0,1,1),
 (319,'浒山镇南二环路176号','','2011-01-26 14:15:10','2005-07-01 00:00:00','2011-01-26 14:15:10','2008-07-01 00:00:00','',81,'徐周明','330282033192','2010-08-11 00:00:00',71,'','13306655555','','','慈溪市友邦汽车销售有限公司','维修(汽车装璜)','   ',105,'cxsybqcxsyxgs',0,0,1,1),
 (320,'浒山新江路38号','','2011-01-26 14:15:10','2005-07-01 00:00:00','2011-01-26 14:15:10','2008-07-01 00:00:00','',90,'徐国良','330282033193','2010-08-11 00:00:00',71,'','63804919','13586616777','','慈溪市浒山良品汽车服务店','维修(汽车装璜)','   ',105,'cxszslpqcfwd',0,0,1,1),
 (321,'慈溪市浒山施山村','','2011-01-26 14:15:11','2010-08-11 00:00:00','2011-01-26 14:15:11','2011-06-30 00:00:00','',90,'杨国武','330282033194','2010-08-11 00:00:00',71,'','13065628306','','','慈溪市浒山大众汽车装潢店','维修(汽车装璜)','   ',105,'cxszsdzqczzd',0,0,1,1),
 (322,'慈溪市天元镇潭南村','','2011-01-26 14:15:11','2010-08-23 00:00:00','2011-01-26 14:15:11','2011-06-30 00:00:00','',90,'韩张伟','330282033195','2010-08-23 00:00:00',71,'','','13396620088','','慈溪市天元创新汽车维修店','维修(汽车装璜)','   ',105,'cxstycxqcwxd',0,0,1,1),
 (323,'浒山西二环线104－108号','','2011-01-26 14:15:11','2003-08-14 00:00:00','2011-01-26 14:15:11','2006-08-14 00:00:00','',90,'周建华','330282033196','2010-08-23 00:00:00',71,'','63897511','13306691208','','浒山建华汽车装璜店','维修(汽车装璜)','   ',105,'zsjhqczzd',0,0,1,1),
 (324,'慈溪市孙塘北路656号','','2011-01-26 14:15:11','2005-07-01 00:00:00','2011-01-26 14:15:11','2008-07-01 00:00:00','',81,'徐林强','330282033197','2010-08-23 00:00:00',71,'','63033922','13805821572','','慈溪市鹏程汽车装璜服务部','维修(汽车装璜)','   ',105,'cxspcqczzfwb',0,0,1,1),
 (325,'慈溪市古塘街道浒崇公路144-152号','','2011-01-26 14:15:11','2010-08-11 00:00:00','2011-01-26 14:15:11','2011-06-30 00:00:00','',90,'许颖儿','330282033198','2010-08-11 00:00:00',71,'','','63245812','','慈溪市小拇指汽车修理服务部','维修(汽车装璜)','   ',105,'cxsxmzqcxlfwb',0,0,1,1),
 (326,'慈溪市横河镇埋马村','','2011-01-26 14:15:11','2010-08-17 00:00:00','2011-01-26 14:15:11','2012-12-31 00:00:00','',90,'杨浩杰','330282033200','2010-08-25 00:00:00',0,'13606744941','','','','慈溪市横河浩杰摩托车维修店','机动车维修：摩托车维修。','',105,'cxshhhjmtcwxd',0,0,1,1),
 (327,'慈溪市附海镇南圆村观附公路边','','2011-01-26 14:15:11','2010-09-15 00:00:00','2011-01-26 14:15:11','2011-06-30 00:00:00','',85,'','330282033201','2010-09-15 00:00:00',0,'','','','','慈溪市附海旭海汽车维修厂（普通合伙）','机动车维修：三类机动车维修（发动机修理）。','',105,'cxsfhxhqcwxc（pthh）',0,0,1,1),
 (328,'匡堰镇宋家漕村','','2011-01-26 14:15:11','2010-09-16 00:00:00','2011-01-26 14:15:11','2012-05-12 00:00:00','',85,'华淞炼','330282033202','2010-09-16 00:00:00',71,'13884497918','','','','慈溪市佳吉汽车维修部（普通合伙）','机动车维修：三类机动车维修（车身维修）。','',105,'cxsjjqcwxb（pthh）',0,0,1,1),
 (329,'慈溪市观海卫镇新泽村','','2011-01-26 14:15:11','2010-09-15 00:00:00','2011-01-26 14:15:11','2011-06-30 00:00:00','',90,'唐伟明','330282033203','2010-09-15 00:00:00',71,'','13906748224','','','慈溪市观城伟明汽车电器维修店','维修(电气系统维修)','   ',105,'cxsgcwmqcdqwxd',0,0,1,1),
 (330,'慈溪市长河镇沧北村芦庵公路2049号','','2011-01-26 14:15:11','2009-04-22 00:00:00','2011-01-26 14:15:11','2009-07-22 00:00:00','',90,'丁清','330282033204','2009-04-22 00:00:00',0,'','','','','慈溪市长河丁清补胎店','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxschdqbtd',0,0,1,1),
 (331,'慈溪市北二环线路钢整平厂','','2011-01-26 14:15:11','2002-07-01 00:00:00','2011-01-26 14:15:11','2005-07-01 00:00:00','',90,'诸建立','330282033205','2009-04-22 00:00:00',71,'','13505846586','','','慈溪市浒山建立汽车电器维修店','维修(电气系统维修)','   ',105,'cxszsjlqcdqwxd',0,0,1,1),
 (332,'慈溪市浒山镇教场山路206号','','2011-01-26 14:15:11','2002-07-01 00:00:00','2011-01-26 14:15:11','2005-07-01 00:00:00','',90,'陈连通','330282033206','2009-04-22 00:00:00',71,'','63801484','','','慈溪市浒山连通汽车电器维修店','维修(电气系统维修)','   ',105,'cxszsltqcdqwxd',0,0,1,1),
 (333,'慈溪市浒山镇南二环路823号','','2011-01-26 14:15:11','2010-08-11 00:00:00','2011-01-26 14:15:11','2011-06-30 00:00:00','',90,'陈杰','330282033207','2010-08-11 00:00:00',71,'','13805815533','63101826','','慈溪市浒山陈杰汽车电器修理站','维修(电气系统维修,空调维修)','   ',105,'cxszscjqcdqxlz',0,0,1,1),
 (334,'慈溪市周巷镇周西公路叉口','','2011-01-26 14:15:11','2010-08-23 00:00:00','2011-01-26 14:15:11','2011-06-30 00:00:00','',90,'赵佰员','330282033208','2010-08-23 00:00:00',71,'','63300260','','','慈溪市周巷镇通顺汽车电器修理店','维修(电气系统维修)','   ',105,'cxszxztsqcdqxld',0,0,1,1),
 (335,'慈溪市观海卫镇新泽村','','2011-01-26 14:15:11','2010-09-15 00:00:00','2011-01-26 14:15:11','2011-06-30 00:00:00','',90,'宓博跃','330282033209','2010-09-15 00:00:00',71,'','63618450','13506749647','','慈溪市观海卫北侠汽车电器修理店','机动车维修：三类机动车维修（车身维修、电气系统维修）。','',105,'cxsghwbxqcdqxld',0,0,1,1),
 (336,'慈溪市逍林镇新世纪电子城G座','','2011-01-26 14:15:12','2010-08-11 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'郁云强','330282033211','2010-08-11 00:00:00',71,'','63516065','13805813832','','慈溪市逍林永强汽车电器维修店','维修(电气系统维修,空调维修)','   ',105,'cxszlyqqcdqwxd',0,0,1,1),
 (337,'慈溪市观城镇五里','','2011-01-26 14:15:12','2010-09-15 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'宓沛新','330282033212','2010-09-15 00:00:00',71,'','63628455','13505847520','','慈溪市观海卫镇沛新汽车电器修理店','维修(电气系统维修)','   ',105,'cxsghwzpxqcdqxld',0,0,1,1),
 (338,'浒山街道新江路105号','','2011-01-26 14:15:12','2010-08-23 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'徐方林','330282033213','2010-08-23 00:00:00',0,'','63241923','','','慈溪市宗汉方林洗车场','维修(三类机动车维修:车身清洁维护)','   ',105,'cxszhflxcc',0,0,1,1),
 (339,'慈溪市庵东镇七二三大桥东100Ｍ','','2011-01-26 14:15:12','2008-08-29 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'丁成荣','330282033215','2010-08-23 00:00:00',71,'','63475930','13003715893','','慈溪市庵东镇成荣汽车电器修理店','维修(电气系统维修)','   ',105,'cxszdzcrqcdqxld',0,0,1,1),
 (340,'慈溪市浒山阳光花园综合7号楼','','2011-01-26 14:15:12','2010-08-11 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'唐家茂','330282033216','2010-11-25 00:00:00',71,'','63898003','13805816766','','慈溪市浒山新大唐汽车电器维修店','维修(车身维修,电气系统维修)','',105,'cxszsxdtqcdqwxd',0,0,1,1),
 (341,'慈溪市掌起镇周家段村','','2011-01-26 14:15:12','2008-10-06 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'刘金锋','330282033217','2010-02-22 00:00:00',71,'','63741994','13606889967','','慈溪市掌起镇信用汽车修理店','维修(电气系统维修)','   ',105,'cxszqzxyqcxld',0,0,1,1),
 (342,'慈溪市附海西舍','','2011-01-26 14:15:12','2010-09-15 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'罗国其','330282033218','2010-09-15 00:00:00',71,'','63566200','','','慈溪市附海国其汽车电气维修店','维修(电气系统维修)','   ',105,'cxsfhgqqcdqwxd',0,0,1,1),
 (343,'新浦双桥','','2011-01-26 14:15:12','2005-07-01 00:00:00','2011-01-26 14:15:12','2008-07-01 00:00:00','',90,'王烈俊','330282033219','2010-09-15 00:00:00',71,'','63575932','13003763358','','慈溪市新浦烈新汽车修配店','维修(供油系统维护及油品更换,喷油泵和喷油器维修)','   ',105,'cxsxplxqcxpd',0,0,1,1),
 (344,'浒山街道教场山路198号（天和家园）','','2011-01-26 14:15:12','2010-08-11 00:00:00','2011-01-26 14:15:12','2012-05-12 00:00:00','',90,'冯建洪','330282033220','2010-08-24 00:00:00',0,'13586616697','','','','慈溪市古塘莱卡兹汽车装璜店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsgtlkzqczzd',0,0,1,1),
 (345,'慈溪市周巷花墙门12号','','2011-01-26 14:15:12','2002-07-01 00:00:00','2011-01-26 14:15:12','2005-07-01 00:00:00','',90,'吴宏辉','330282033221','2010-08-24 00:00:00',71,'','62195031','','','慈溪市周巷宏辉汽车电器修理店','维修(电气系统维修)','   ',105,'cxszxhhqcdqxld',0,0,1,1),
 (346,'慈溪市浒山镇西二环线','','2011-01-26 14:15:12','2003-05-22 00:00:00','2011-01-26 14:15:12','2006-05-22 00:00:00','',90,'陈小明','330282033222','2010-08-24 00:00:00',71,'','13968216461','','','慈溪市浒山镇顺通汽车电器修理店','维修(电气系统维修)','   ',105,'cxszszstqcdqxld',0,0,1,1),
 (347,'慈溪市金穗公寓4号202室','','2011-01-26 14:15:12','2003-12-31 00:00:00','2011-01-26 14:15:12','2006-12-31 00:00:00','',90,'胡枫','330282033223','2010-08-24 00:00:00',71,'','','13566346425','','浒山开创汽车电器修理店','维修(电气系统维修)','   ',105,'zskcqcdqxld',0,0,1,1),
 (348,'慈溪市观海卫镇沈师桥村三北东路768号','','2011-01-26 14:15:12','2010-09-15 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'柴建辉','330282033224','2010-09-15 00:00:00',71,'','','13008995601','','慈溪市观海卫镇建辉汽车电器维修店','维修(电气系统维修)','   ',105,'cxsghwzjhqcdqwxd',0,0,1,1),
 (349,'慈溪市观城镇观方路口','','2011-01-26 14:15:12','2010-09-15 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'唐国能','330282033227','2010-09-15 00:00:00',71,'','13968276535','','','慈溪市观海卫镇国能汽车电器维修店','维修(电气系统维修)','   ',105,'cxsghwzgnqcdqwxd',0,0,1,1),
 (350,'慈溪市崇寿镇崇胜村','','2011-01-26 14:15:12','2005-07-01 00:00:00','2011-01-26 14:15:12','2008-07-01 00:00:00','',90,'王科','330282033229','2010-09-15 00:00:00',71,'','63297492','','','慈溪市崇寿镇王科汽车修理店','维修(电气系统维修)','   ',105,'cxscszwkqcxld',0,0,1,1),
 (351,'长河沧田村花潭旁','','2011-01-26 14:15:12','2010-08-23 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'潘红利','330282033230','2010-08-23 00:00:00',71,'','63406818','13216742055','','慈溪市长河镇红利汽车电器修理店','维修(电气系统维修)','   ',105,'cxschzhlqcdqxld',0,0,1,1),
 (352,'慈溪市范市镇王家路村329国道线南','','2011-01-26 14:15:12','2010-08-31 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'叶锋雷','330282033231','2010-08-31 00:00:00',71,'','','','','慈溪市宏峰汽车电器修理店','维修(电气系统维修)','   ',105,'cxshfqcdqxld',0,0,1,1),
 (353,'横河洋山岗村','','2011-01-26 14:15:12','2010-08-11 00:00:00','2011-01-26 14:15:12','2013-06-30 00:00:00','',90,'徐建柯','330282033233','2010-08-11 00:00:00',71,'','63252200','13355914958','','慈溪市横河阿科汽车电器修理店','机动车维修：三类机动车维修（车身维修、电气系统维修）。','   ',105,'cxshhakqcdqxld',0,0,1,1),
 (354,'新浦高桥村','','2011-01-26 14:15:12','2010-08-11 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'岑仲维','330282033234','2010-08-11 00:00:00',71,'','13003775537','','','慈溪市新浦仲维汽车电器维修店','维修(电气系统维修)','   ',105,'cxsxpzwqcdqwxd',0,0,1,1),
 (355,'慈溪市横河镇宜青桥村','','2011-01-26 14:15:12','2010-08-11 00:00:00','2011-01-26 14:15:12','2013-01-06 00:00:00','',90,'毛祥显','330282033235','2010-08-11 00:00:00',0,'18906742905','','','','慈溪市慈冠汽车修理店','机动车维修：汽车快修。','',105,'cxscgqcxld',0,0,1,1),
 (356,'慈溪市古塘街道泥桥路4号','','2011-01-26 14:15:12','2009-03-30 00:00:00','2011-01-26 14:15:12','2011-06-30 00:00:00','',90,'祖信春','330282033236','2009-06-04 00:00:00',71,'','','','','慈溪市古塘宝瑞汽车电器维修店','维修(电气系统维修)','   ',105,'cxsgtbrqcdqwxd',0,0,1,1),
 (357,'慈溪市观海卫镇五里村','','2011-01-26 14:15:13','2010-09-15 00:00:00','2011-01-26 14:15:13','2011-06-30 00:00:00','',90,'胡国海','330282033237','2010-09-15 00:00:00',71,'','63608407','13968200756','','慈溪市观海卫镇阿海汽车车身维修店','维修(车身维修)','   ',105,'cxsghwzahqccswxd',0,0,1,1),
 (358,'龙山淞浦村','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2011-06-30 00:00:00','',90,'陈鹿阳','330282033238','2010-08-11 00:00:00',71,'','63759617','13606742457','','慈溪市龙山乐阳汽车维修店','维修(电气系统维修)','   ',105,'cxslslyqcwxd',0,0,1,1),
 (359,'浒山八字桥村','','2011-01-26 14:15:13','2005-03-18 00:00:00','2011-01-26 14:15:13','2008-03-18 00:00:00','',90,'丁国云','330282033239','2010-08-11 00:00:00',71,'','13606880138','63838360','','慈溪市浒山街道国云电瓶店','维修(电气系统维修)','   ',105,'cxszsjdgydpd',0,0,1,1),
 (360,'慈溪市龙山镇龙山所村','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2011-06-30 00:00:00','',90,'王珂','330282033240','2010-08-11 00:00:00',71,'','63785631','66648968','','慈溪市龙山兄弟汽车维修店','维修(电气系统维修)','   ',105,'cxslsxdqcwxd',0,0,1,1),
 (361,'龙山邱王村329国道旁（老收费所旁）','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2011-06-30 00:00:00','',90,'王战胜','330282033241','2010-08-11 00:00:00',71,'','63788190','13967889514','','慈溪市龙山战胜汽车电瓶修理店','维修(电气系统维修)','   ',105,'cxslszsqcdpxld',0,0,1,1),
 (362,'宗汉新塘东村（新江路105号）','','2011-01-26 14:15:13','2005-04-30 00:00:00','2011-01-26 14:15:13','2008-04-30 00:00:00','',90,'周文明','330282033242','2010-08-11 00:00:00',71,'','13777126620','','','慈溪宗汉恒安电子电器修理店','维修(电气系统维修)','   ',105,'cxzhhadzdqxld',0,0,1,1),
 (363,'浒山街道担山跟村','','2011-01-26 14:15:13','2005-07-28 00:00:00','2011-01-26 14:15:13','2008-07-01 00:00:00','',90,'孙洪波','330282033243','2010-08-11 00:00:00',71,'','63014154','','','慈溪市浒山金晨汽车修理店','维修(电气系统维修)','   ',105,'cxszsjcqcxld',0,0,1,1),
 (364,'慈溪市浒山街道长春村','','2011-01-26 14:15:13','2006-01-13 00:00:00','2011-01-26 14:15:13','2009-07-01 00:00:00','',90,'徐珂','330282033244','2010-08-11 00:00:00',71,'','63825519','','','慈溪市浒山国钧汽车电器修理店','维修(电气系统维修)','   ',105,'cxszsgjqcdqxld',0,0,1,1),
 (365,'慈溪市附海镇东海村韩家路','','2011-01-26 14:15:13','2010-09-15 00:00:00','2011-01-26 14:15:13','2012-05-12 00:00:00','',90,'楼汉炯','330282033245','2010-09-15 00:00:00',0,'13606889739','','','','慈溪市小蚂蚁汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsxmyqcxld',0,0,1,1),
 (366,'掌起镇周家段村','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2011-06-30 00:00:00','',90,'刘远举','330282033246','2010-08-11 00:00:00',71,'','13250962322','','','慈溪市掌起远盛汽车修理店','维修(电气系统维修)','   ',105,'cxszqysqcxld',0,0,1,1),
 (367,'慈溪市新浦镇水湘村','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2012-03-24 00:00:00','',90,'章岳江','330282033247','2010-08-11 00:00:00',71,'','','13456187657','','慈溪市新浦阿江汽车维修店','维修(三类机动车维修:电气系统维修、车身清洁维护)','   ',105,'cxsxpajqcwxd',0,0,1,1),
 (368,'慈溪市横河镇梅川路16-18号','','2011-01-26 14:15:13','2009-05-18 00:00:00','2011-01-26 14:15:13','2012-05-18 00:00:00','',90,'刘发军','330282033248','2009-05-18 00:00:00',0,'13968204742','','','','慈溪市横河发军汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxshhfjqcxld',0,0,1,1),
 (369,'慈溪市横河镇孙家境村横筋线东侧','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2011-06-30 00:00:00','',90,'孙纪才','330282033249','2010-08-11 00:00:00',0,'63263609','','','','慈溪市横河镇孙家境洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxshhzsjjxcc',0,0,1,1),
 (370,'周巷镇小安村','','2011-01-26 14:15:13','2009-06-10 00:00:00','2011-01-26 14:15:13','2012-06-30 00:00:00','',90,'沈吉','330282033250','2009-06-10 00:00:00',0,'13645844058','','','','慈溪市周巷车友洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszxcyxcc',0,0,1,1),
 (371,'慈溪市浒山街道鸣山西二环塘南','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2012-06-29 00:00:00','',90,'陈正飞','330282033251','2010-08-24 00:00:00',0,'13805812043','','','','慈溪市浒山正飞轮胎店','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxszszfltd',0,0,1,1),
 (372,'慈溪市龙山镇新东村','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2012-06-30 00:00:00','',90,'王会照','330282033252','2010-08-11 00:00:00',0,'13251970973','','','','慈溪市范市力威洗车店','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxsfslwxcd',0,0,1,1),
 (373,'慈溪市观海卫镇城隍庙村','','2011-01-26 14:15:13','2010-09-15 00:00:00','2011-01-26 14:15:13','2013-01-15 00:00:00','',90,'黄立夫','330282033253','2010-09-15 00:00:00',0,'13396641376','','','','慈溪市观海卫盛通汽车修理厂','机动车维修：三类机动车维修（车身维修、供油系统维护及油品更换）。','',105,'cxsghwstqcxlc',0,0,1,1),
 (374,'慈溪市横河镇彭桥村庙后','','2011-01-26 14:15:13','2010-08-11 00:00:00','2011-01-26 14:15:13','2012-06-30 00:00:00','',90,'孙定益','330282033254','2010-08-11 00:00:00',0,'13484267150','','','','慈溪市横河佳益汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxshhjyqcxld',0,0,1,1),
 (375,'慈溪市宗汉街道马家路村','马银平','2011-01-26 14:15:13','2010-08-23 00:00:00','2011-01-26 14:15:13','2013-01-18 00:00:00','',90,'马成坤','330282033255','2010-08-23 00:00:00',0,'13008927980','','','82780311','慈溪市宗汉宗兴洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszhzxxcc',0,0,1,1);
INSERT INTO `enterprises` (`id`,`address`,`commission`,`createDate`,`dateBegin`,`editDate`,`dateEnd`,`handleMan`,`kind`,`legalPerson`,`license`,`licenseDate`,`qualification`,`telephone1`,`telephone2`,`telephone3`,`telephone4`,`unitName`,`workArea`,`workRemark`,`workType`,`py`,`station`,`status`,`createrId`,`editorId`) VALUES 
 (376,'慈溪市范市小范方淞浦','','2011-01-26 14:15:14','2010-08-11 00:00:00','2011-01-26 14:15:14','2012-07-17 00:00:00','',90,'叶有根','330282033256','2010-08-11 00:00:00',0,'13456191053','','','','慈溪市范市小叶汽车维修部','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsfsxyqcwxb',0,0,1,1),
 (377,'慈溪市古塘街道吉祥新村','','2011-01-26 14:15:14','2010-08-11 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'仲济科','330282033257','2010-08-24 00:00:00',0,'15968025900','','','','慈溪市古塘吉顺汽车修理店','机动车维修：三类机动车维修、汽车快修）。','',105,'cxsgtjsqcxld',0,0,1,1),
 (378,'慈溪市周巷镇西三公路北二环线东1号','','2011-01-26 14:15:14','2010-08-23 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'陈见龙','330282033258','2010-08-23 00:00:00',0,'','15067472302','','','慈溪市周巷蒙恩汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszxmeqcxld',0,0,1,1),
 (379,'慈溪市匡堰镇樟树村','','2011-01-26 14:15:14','2010-09-15 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',83,'钱中盛','330282033259','2010-09-15 00:00:00',0,'','63539922','','13071984259','宁波市祥安汽车营销服务有限公司慈溪分公司','机动车维修：三类机动车维修（车身维修）、汽车快修）。','',105,'nbsxaqcyxfwyxgscxfgs',0,0,1,1),
 (380,'慈溪市观海卫镇太平桥综合大楼底层','','2011-01-26 14:15:14','2009-07-31 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'严佰新','330282033260','2009-07-31 00:00:00',0,'15924348800','','','','慈溪市观海卫路顺汽车维修厂','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsghwlsqcwxc',0,0,1,1),
 (381,'慈溪市古塘街道新潮塘村坎墩大道355号','','2011-01-26 14:15:14','2010-08-11 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'胡威威','330282033261','2010-08-11 00:00:00',0,'13586788089','','','','慈溪市古塘新锐汽车装潢店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsgtxrqczzd',0,0,1,1),
 (382,'慈溪市周巷镇周西社区（井亭庵）','','2011-01-26 14:15:14','2010-08-23 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'田志江','330282033262','2010-08-23 00:00:00',0,'','','','','慈溪市周巷志江汽车油品更换店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszxzjqcypghd',0,0,1,1),
 (383,'慈溪市龙山镇龙头场村慈龙西路16号','','2011-01-26 14:15:14','2010-08-31 00:00:00','2011-01-26 14:15:14','2012-07-16 00:00:00','',90,'彭军华','330282033263','2010-08-31 00:00:00',0,'13566609786','','','','慈溪市龙山小彭汽车维修店','机动车维修：三类机动车维修（电气系统维修）。','',105,'cxslsxpqcwxd',0,0,1,1),
 (384,'慈溪市周巷镇环城西路389号','','2011-01-26 14:15:14','2010-08-23 00:00:00','2011-01-26 14:15:14','2011-06-30 00:00:00','',90,'倪立明','330282033264','2010-08-23 00:00:00',0,'81319778','','','','慈溪市周巷明靓车身清洁维护店','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszxmzcsqjwhd',0,0,1,1),
 (385,'慈溪市白沙路街道墙里村','','2011-01-26 14:15:14','2010-08-11 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'龚佰冲','330282033265','2010-08-24 00:00:00',0,'13606745758','','','','慈溪市白沙路达龙汽车装潢服务中心','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsbsldlqczzfwzx',0,0,1,1),
 (386,'慈溪市横河镇宜青桥村乌山路与前应路四岔口','','2011-01-26 14:15:14','2010-08-11 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'徐渭川','330282033266','2010-08-11 00:00:00',0,'13858303473','','','','慈溪市横河镇顺川汽车维修店','机动车维修：三类机动车维修（轮胎动平衡及修补、供油系统维护及油品更换）。','',105,'cxshhzscqcwxd',0,0,1,1),
 (387,'慈溪市观海卫镇小团浦村','','2011-01-26 14:15:14','2010-09-15 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'沈迪','330282033267','2010-09-15 00:00:00',0,'13065820445','','','','慈溪市观海卫镇塘角钳汽车电器修理厂','机动车维修：三类机动车维修（空调维修）。','',105,'cxsghwztjqqcdqxlc',0,0,1,1),
 (388,'慈溪市浒山孙塘南路','','2011-01-26 14:15:14','2010-08-11 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'陈爱萍','330282033269','2010-08-11 00:00:00',0,'13806642855','','','','慈溪市浒山阿萍冼车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszsapzcc',0,0,1,1),
 (389,'慈溪市庵东镇庵宗公路70号','','2011-01-26 14:15:14','2010-08-23 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'陈大猛','330282033270','2010-08-23 00:00:00',0,'13858335929','','','','慈溪市庵东小陈汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszdxcqcxld',0,0,1,1),
 (390,'慈溪市附海镇东海村','','2011-01-26 14:15:14','2010-09-15 00:00:00','2011-01-26 14:15:14','2012-06-30 00:00:00','',90,'陈营锋','330282033271','2010-09-15 00:00:00',0,'13858345934','','','','慈溪市附海慈奥汽车修理厂','机动车维修：三类机动车维修（四轮定位检测调整、供油系统维护及油品更换）。','',105,'cxsfhcaqcxlc',0,0,1,1),
 (391,'慈溪市古塘街道开发大道1221-1225','','2011-01-26 14:15:15','2010-08-16 00:00:00','2011-01-26 14:15:15','2012-06-30 00:00:00','',81,'胡跃华','330282033272','2010-08-24 00:00:00',0,'13738855667','','','','宁波元通机电实业有限公司慈溪分公司','机动车维修：汽车快修。','',105,'nbytjdsyyxgscxfgs',0,0,1,1),
 (392,'慈溪市浒山街道解放中街197号','符胜斌','2011-01-26 14:15:15','2010-08-11 00:00:00','2011-01-26 14:15:15','2013-01-21 00:00:00','',90,'王继勇','330282033273','2010-08-11 00:00:00',0,'013735589155','','','15869369316','慈溪市浒山麦捷斯汽车装潢服务部','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszsmjsqczzfwb',0,0,1,1),
 (393,'慈溪市周巷镇周邵村','','2011-01-26 14:15:15','2010-02-03 00:00:00','2011-01-26 14:15:15','2013-02-03 00:00:00','',90,'谢明华','330282033274','2010-02-03 00:00:00',0,'13586507713','','','','慈溪市周巷镇明华轮胎修配店','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxszxzmhltxpd',0,0,1,1),
 (394,'慈溪市白沙路街道三洞桥村','孙国云','2011-01-26 14:15:15','2010-08-11 00:00:00','2011-01-26 14:15:15','2012-12-31 00:00:00','',90,'孙东湖','330282033275','2010-08-11 00:00:00',0,'13506740970','','','','慈溪市白沙路东东汽车装饰服务部','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsbslddqczsfwb',0,0,1,1),
 (395,'慈溪市古塘街道浒崇公路84-100号','','2011-01-26 14:15:15','2010-08-11 00:00:00','2011-01-26 14:15:15','2013-03-04 00:00:00','',90,'康广川','330282033277','2010-08-11 00:00:00',0,'15058233870','','','','慈溪市兆鑫汽车空调修理商行','机动车维修：三类机动车维修（空调维修、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszzqckdxlsx',0,0,1,1),
 (396,'慈溪市浒山街道阳明山庄（英雄楼）1号楼101室','','2011-01-26 14:15:15','2010-03-26 00:00:00','2011-01-26 14:15:15','2010-06-26 00:00:00','',90,'冯剑','330282033278','2010-03-26 00:00:00',0,'13486410113','','','','慈溪益丰汽车装饰有限公司','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxyfqczsyxgs',0,0,1,1),
 (397,'慈溪市浒山街道慈百路326号','','2011-01-26 14:15:15','2010-08-11 00:00:00','2011-01-26 14:15:15','2013-06-30 00:00:00','',90,'雷江凤','330282033279','2010-08-11 00:00:00',0,'13777981133','63818189','','','慈溪市浒山大雷汽车装潢服务部','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszsdlqczzfwb',0,0,1,1),
 (398,'慈溪市浒山街道石桥头路9号','','2011-01-26 14:15:16','2010-08-11 00:00:00','2011-01-26 14:15:16','2011-06-30 00:00:00','',90,'吕代刚','330282033280','2010-08-11 00:00:00',71,'','63813809','13867860809','','慈溪市浒山蜀辉汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxszsshqcxld',0,0,1,1),
 (399,'慈溪市范市镇新西村','','2011-01-26 14:15:16','2010-08-11 00:00:00','2011-01-26 14:15:16','2012-06-30 00:00:00','',90,'邹能伟','330282033282','2010-08-11 00:00:00',0,'13081931111','23681111','','','慈溪市范市祥睿汽车维修厂','机动车维修：三类机动车维修（车身维修、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsfsxzqcwxc',0,0,1,1),
 (400,'慈溪市庵东镇振东村庵宗公路155号','','2011-01-26 14:15:16','2010-08-23 00:00:00','2011-01-26 14:15:16','2012-06-30 00:00:00','',90,'单华国','330282033283','2010-08-23 00:00:00',0,'13056704446','','','','慈溪市庵东振东汽车修理店','机动车维修：汽车快修。','',105,'cxszdzdqcxld',0,0,1,1),
 (401,'慈溪市附海镇海晏庙村海熙路201号','','2011-01-26 14:15:16','2010-09-15 00:00:00','2011-01-26 14:15:16','2010-09-20 00:00:00','',90,'郑雪锋','330282033284','2010-11-30 00:00:00',71,'','13506747771','13506747771','','慈溪市附海郑顺汽车修理厂','维修(三类机动车维修:电气系统维修)','   ',105,'cxsfhzsqcxlc',0,0,1,1),
 (402,'慈溪市浒山镇浒崇公路108号','','2011-01-26 14:15:16','2010-08-11 00:00:00','2011-01-26 14:15:16','2011-10-25 00:00:00','',90,'俞忠忠','330282033285','2010-08-11 00:00:00',71,'','63040177','13506744374','','慈溪市浒山顺航汽车修理部','维修(三类机动车维修:车身维修、涂漆)','   ',105,'cxszsshqcxlb',0,0,1,1),
 (403,'慈溪浒山街道虞北路7-13号','','2011-01-26 14:15:16','2007-10-26 00:00:00','2011-01-26 14:15:16','2011-10-26 00:00:00','',90,'俞烈','330282033286','2010-08-11 00:00:00',71,'','13003705440','13003705440','','慈溪市浒山金手指汽车修理服务部','维修(三类机动车维修:涂漆、车辆玻璃安装)','   ',105,'cxszsjszqcxlfwb',0,0,1,1),
 (404,'慈溪市附海镇东海村','','2011-01-26 14:15:16','2010-09-15 00:00:00','2011-01-26 14:15:16','2012-02-26 00:00:00','',90,'王有颂','330282033288','2010-09-15 00:00:00',71,'','13884482340','13884482340','','慈溪市附海特强汽车修理厂','维修(三类机动车维修:车身维修)','   ',105,'cxsfhtqqcxlc',0,0,1,1),
 (405,'慈溪市新浦镇老浦村新胜路9号','','2011-01-26 14:15:16','2010-08-11 00:00:00','2011-01-26 14:15:16','2011-06-30 00:00:00','',90,'苗高锋','330282033289','2010-08-11 00:00:00',71,'','13858301322','','','慈溪市新浦新胜汽车修理厂','维修(三类机动车维修:车身维修、涂漆)','   ',105,'cxsxpxsqcxlc',0,0,1,1),
 (406,'浒山西二环线108号','','2011-01-26 14:15:16','2005-07-01 00:00:00','2011-01-26 14:15:16','2008-07-01 00:00:00','',90,'罗云波','330282033290','2010-08-11 00:00:00',71,'','13706742303','','','慈溪市浒山－路行汽配维修店','维修(供油系统维护及油品更换)','   ',105,'cxszs－lxqpwxd',0,0,1,1),
 (407,'慈溪市长河镇镇东路198号','','2011-01-26 14:15:16','2010-10-18 00:00:00','2011-01-26 14:15:16','2013-06-30 00:00:00','',90,'杨忠义','330282033291','2010-10-19 00:00:00',71,'13567806356','63408339','','','慈溪市长河凯鑫汽车维修厂','机动车维修：三类机动车维修（车身清洁维护）、汽车快修）。','',105,'cxschkzqcwxc',0,0,1,1),
 (408,'慈溪市观海卫镇新泽村','','2011-01-26 14:15:16','2010-09-15 00:00:00','2011-01-26 14:15:16','2011-06-30 00:00:00','',90,'叶建江','330282033292','2010-09-15 00:00:00',71,'','63625436','13968205754','','慈溪市观海卫镇成男发动机免拆清洗店','维修(供油系统维护及油品更换)','   ',105,'cxsghwzcnfdjmcqxd',0,0,1,1),
 (409,'慈溪市崇寿镇周洋村','','2011-01-26 14:15:16','2005-07-01 00:00:00','2011-01-26 14:15:16','2008-07-01 00:00:00','',90,'程佰冲','330282033293','2010-09-15 00:00:00',71,'','63297641','13056744615','','慈溪市崇寿镇佰冲发动机免拆清洗店','维修(供油系统维护及油品更换)','   ',105,'cxscszbcfdjmcqxd',0,0,1,1),
 (410,'慈溪市周巷镇环城东路劳家','','2011-01-26 14:15:16','2003-05-23 00:00:00','2011-01-26 14:15:16','2006-05-23 00:00:00','',90,'张飞龙','330282033294','2010-09-15 00:00:00',71,'','13566081115','','','周巷镇飞龙汽车发动机免拆清洗店','维修(供油系统维护及油品更换)','   ',105,'zxzflqcfdjmcqxd',0,0,1,1),
 (411,'慈溪市龙山镇田央停车场','','2011-01-26 14:15:16','2010-08-11 00:00:00','2011-01-26 14:15:16','2011-06-30 00:00:00','',90,'李淑君','330282033295','2010-08-11 00:00:00',71,'','63734528','13656741449','','慈溪市龙山镇小李汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxslszxlqcxld',0,0,1,1),
 (412,'慈溪市观海卫镇城隍庙村329国道边','','2011-01-26 14:15:16','2008-10-06 00:00:00','2011-01-26 14:15:16','2011-06-30 00:00:00','',90,'朱治伟','330282033297','2010-08-11 00:00:00',71,'','63618984  81389039','13968295823','','慈溪市观海卫佳磊汽车供油系统维护店','维修(供油系统维护及油品更换)','   ',105,'cxsghwjlqcgyxtwhd',0,0,1,1),
 (413,'慈溪市新浦镇水湘村','','2011-01-26 14:15:16','2010-08-11 00:00:00','2011-01-26 14:15:16','2012-05-20 00:00:00','',90,'胡淼杰','330282033298','2010-08-24 00:00:00',71,'','63590670','13566312000','','慈溪市新浦琼海汽车修理店','维修(车身维修,供油系统维护及油品更换)','   ',105,'cxsxpqhqcxld',0,0,1,1),
 (414,'慈溪市胜山镇山头村','','2011-01-26 14:15:17','2010-08-11 00:00:00','2011-01-26 14:15:17','2011-06-30 00:00:00','',90,'王江山','330282033299','2010-08-11 00:00:00',71,'','63528400','13056890631','','慈溪市胜山江山发动机免拆清洗店','维修(供油系统维护及油品更换)','   ',105,'cxsssjsfdjmcqxd',0,0,1,1),
 (415,'慈溪市周巷镇登州街村东南区106号','','2011-01-26 14:15:17','2010-08-23 00:00:00','2011-01-26 14:15:17','2013-06-30 00:00:00','',90,'张立军','330282033300','2010-08-23 00:00:00',0,'13958296848','13958296848','','','慈溪市周巷立柯汽车维修店','机动车维修：三类机动车维修（空调维修）。','',105,'cxszxlkqcwxd',0,0,1,1),
 (416,'慈溪浒山镇北三环开发大道403－409号','','2011-01-26 14:15:17','2010-08-11 00:00:00','2011-01-26 14:15:17','2011-06-30 00:00:00','',90,'沈彩儿','330282033301','2010-08-11 00:00:00',71,'','66310853','','','慈溪市浒山慈欣提比轮胎店','维修(轮胎动平衡及修补)','   ',105,'cxszscxtbltd',0,0,1,1),
 (417,'慈溪市浒山镇西二环线112号','','2011-01-26 14:15:17','2010-08-11 00:00:00','2011-01-26 14:15:17','2012-06-30 00:00:00','',90,'马永义','330282033302','2010-08-24 00:00:00',71,'','13968233259','63810872','','慈溪市浒山永义补胎店','维修(轮胎动平衡及修补)','   ',105,'cxszsyybtd',0,0,1,1),
 (418,'慈溪市浒山街道西二环北路178号','','2011-01-26 14:15:17','2010-08-11 00:00:00','2011-01-26 14:15:17','2011-06-30 00:00:00','',90,'施迪夫','330282033303','2010-08-11 00:00:00',71,'','63807646','13906745165','','慈溪市浒山迪夫汽车补胎站','维修(轮胎动平衡及修补)','   ',105,'cxszsdfqcbtz',0,0,1,1),
 (419,'慈溪市浒山慈百路64号','','2011-01-26 14:15:17','2010-08-11 00:00:00','2011-01-26 14:15:17','2011-06-30 00:00:00','',90,'赵建国','330282033304','2010-08-11 00:00:00',71,'','63811190','13306741190','','慈溪市飞阳轮胎店','维修(轮胎动平衡及修补)','   ',105,'cxsfyltd',0,0,1,1),
 (420,'慈溪市横河镇乌山村','','2011-01-26 14:15:17','2010-08-11 00:00:00','2011-01-26 14:15:17','2011-06-30 00:00:00','',90,'郑文学','330282033305','2010-08-11 00:00:00',71,'','13606880460','','','慈溪市横河镇腾鑫车辆轮胎店','维修(轮胎动平衡及修补)','   ',105,'cxshhztzclltd',0,0,1,1),
 (421,'慈溪市宗汉镇马家路村','','2011-01-26 14:15:17','2008-08-18 00:00:00','2011-01-26 14:15:17','2011-06-30 00:00:00','',90,'赵建岳','330282033306','2010-08-11 00:00:00',71,'','63204747','13906743815','','慈溪市宗汉兴发轮胎店','维修(轮胎动平衡及修补)','   ',105,'cxszhxfltd',0,0,1,1),
 (422,'慈溪市周巷西片329国道边','','2011-01-26 14:15:17','2005-07-01 00:00:00','2011-01-26 14:15:17','2008-07-01 00:00:00','',90,'孙孝松','330282033307','2010-08-11 00:00:00',71,'','62195078','','','慈溪市周巷孝松轮胎维修店','维修(轮胎动平衡及修补)','   ',105,'cxszxxsltwxd',0,0,1,1),
 (423,'慈溪市庵东镇庵余路老汽车站南','','2011-01-26 14:15:17','2008-08-15 00:00:00','2011-01-26 14:15:17','2011-06-30 00:00:00','',90,'施炳涨','330282033310','2010-08-11 00:00:00',71,'','63474586','','','慈溪市庵东阿涨机动车补胎店','维修(轮胎动平衡及修补)','   ',105,'cxszdazjdcbtd',0,0,1,1),
 (424,'慈溪市庵东镇振东村庵宗公路（振东住宅3号楼）','','2011-01-26 14:15:18','2008-08-15 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'施炳畅','330282033311','2009-06-29 00:00:00',71,'','','','','慈溪市庵东炳畅补胎店','维修(轮胎动平衡及修补)','   ',105,'cxszdbcbtd',0,0,1,1),
 (425,'慈溪市逍林镇新园村五百步','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2013-06-30 00:00:00','',90,'陈立平','330282033312','2010-08-11 00:00:00',0,'13858307277','','','','慈溪市逍林五百步洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszlwbbxcc',0,0,1,1),
 (426,'慈溪市慈百路98号','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'马永孟','330282033313','2010-08-11 00:00:00',71,'','63814880','13906746666','','慈溪市浒山勇猛轮胎汽车用品店','维修(轮胎动平衡及修补)','   ',105,'cxszsymltqcypd',0,0,1,1),
 (427,'慈溪市庵东镇庵余路','','2011-01-26 14:15:18','2005-07-01 00:00:00','2011-01-26 14:15:18','2008-07-01 00:00:00','',90,'裘定军','330282033314','2010-08-11 00:00:00',71,'','63475585','13567801460','','慈溪市庵东镇定军轮胎维修店','维修(轮胎动平衡及修补)','   ',105,'cxszdzdjltwxd',0,0,1,1),
 (428,'慈溪市范市镇小范方村','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'范孟达','330282033315','2010-08-11 00:00:00',71,'','63700781','','','慈溪市龙山镇孟达汽车轮胎修补店','维修(轮胎动平衡及修补)','   ',105,'cxslszmdqcltxbd',0,0,1,1),
 (429,'慈溪市浒山石桥头村','','2011-01-26 14:15:18','2009-01-09 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'杨国波','330282033317','2010-08-11 00:00:00',71,'','13867878488','','','慈溪市古塘国波汽车轮胎修补站','维修(轮胎动平衡及修补)','   ',105,'cxsgtgbqcltxbz',0,0,1,1),
 (430,'古塘街道界牌村','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'沈乾龙','330282033318','2010-08-11 00:00:00',71,'','63010652','','','慈溪市古塘乾龙轮胎店','维修(轮胎动平衡及修补)','   ',105,'cxsgtqlltd',0,0,1,1),
 (431,'慈溪市桥头镇吴山村329国道旁','','2011-01-26 14:15:18','2010-09-15 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'顾夏林','330282033319','2010-09-15 00:00:00',71,'','63552322','13506786978','','慈溪市桥头镇夏林汽车轮胎修配店','维修(轮胎动平衡及修补)','   ',105,'cxsqtzxlqcltxpd',0,0,1,1),
 (432,'浒山镇阳明山庄富贵楼19号','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'陈菊英','330282033320','2010-08-11 00:00:00',71,'','63898848','','','慈溪市旭东汽车配件有限公司','维修(轮胎动平衡及修补)','   ',105,'cxsxdqcpjyxgs',0,0,1,1),
 (433,'慈溪市浒山西二环路378号','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2012-06-30 00:00:00','',90,'马永芳','330282033321','2010-08-24 00:00:00',71,'','13606880644','','','慈溪市浒山永芳轮胎修补店','维修(轮胎动平衡及修补)','   ',105,'cxszsyfltxbd',0,0,1,1),
 (434,'慈溪市白沙路街道八字桥村','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'候振斗','330282033322','2010-08-11 00:00:00',71,'','13095925752','','','慈溪市浒山候振补胎店','维修(轮胎动平衡及修补)','   ',105,'cxszshzbtd',0,0,1,1),
 (435,'龙山镇山下村','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'候东海','330282033323','2010-08-11 00:00:00',71,'','13867845726','','','慈溪市龙山东海补胎店','维修(轮胎动平衡及修补)','   ',105,'cxslsdhbtd',0,0,1,1),
 (436,'横河宜青桥村','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'郑建强','330282033324','2010-08-11 00:00:00',71,'','63105897','13968295459','','横河建强补胎店','维修(轮胎动平衡及修补)','   ',105,'hhjqbtd',0,0,1,1),
 (437,'坎墩街道孙方村','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','',90,'江家孝','330282033325','2010-08-11 00:00:00',71,'','63284583','','','慈溪市坎墩家孝轮胎修补店','维修(三类机动车维修:车身清洁维护、轮胎动平衡及修补)','   ',105,'cxskdjxltxbd',0,0,1,1),
 (438,'慈溪市浒山街道西二环北路191号','','2011-01-26 14:15:18','2010-08-11 00:00:00','2011-01-26 14:15:18','2011-06-30 00:00:00','候振斗',90,'候振和','330282033327','2010-08-11 00:00:00',71,'13175961366','','13858316118','','慈溪市浒山振和汽车轮胎修理店','维修(轮胎动平衡及修补)','',105,'cxszszhqcltxld',0,0,1,1),
 (439,'龙山镇邱王村','','2011-01-26 14:15:19','2005-07-01 00:00:00','2011-01-26 14:15:19','2008-07-01 00:00:00','',90,'叶玉林','330282033328','2010-08-11 00:00:00',71,'','63788830','','','龙山镇玉林汽车轮胎修补店','维修(轮胎动平衡及修补)','   ',105,'lszylqcltxbd',0,0,1,1),
 (440,'崇寿镇七塘公路同兴桥头','','2011-01-26 14:15:19','2010-08-11 00:00:00','2011-01-26 14:15:19','2011-06-30 00:00:00','',90,'许建林','330282033329','2010-08-11 00:00:00',71,'','63292043','','','慈溪市祟寿镇建林汽车轮胎修补店','维修(轮胎动平衡及修补)','   ',105,'cxssszjlqcltxbd',0,0,1,1),
 (441,'慈溪市宗汉街道玉字地村','','2011-01-26 14:15:19','2009-07-09 00:00:00','2011-01-26 14:15:19','2012-06-30 00:00:00','',90,'李双全','330282033331','2009-07-09 00:00:00',0,'','15924365258','','','慈溪市宗汉金顺汽车修理厂','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhjsqcxlc',0,0,1,1),
 (442,'浒山南二环846－848号','','2011-01-26 14:15:19','2010-08-11 00:00:00','2011-01-26 14:15:19','2011-06-30 00:00:00','',90,'胡汉杰','330282033333','2010-08-11 00:00:00',71,'','63103080','','','慈溪市浒山翔龙汽车配件轮胎修补店','维修(轮胎动平衡及修补)','   ',105,'cxszsxlqcpjltxbd',0,0,1,1),
 (443,'浒山新城中心19－5','','2011-01-26 14:15:19','2010-08-11 00:00:00','2011-01-26 14:15:19','2011-06-30 00:00:00','',90,'胡若能','330282033334','2010-08-11 00:00:00',71,'','13906746666','','','慈溪市浒山阿猛轮胎店','维修(轮胎动平衡及修补,四轮定位检测调整)','   ',105,'cxszsamltd',0,0,1,1),
 (444,'慈溪市白沙路街道慈甬路1230-1234号','','2011-01-26 14:15:19','2010-08-11 00:00:00','2011-01-26 14:15:19','2011-06-30 00:00:00','',90,'龚叶明','330282033335','2010-08-11 00:00:00',71,'','63826413','13625743374','','慈溪市浒山叶明轮胎店','维修(轮胎动平衡及修补)','   ',105,'cxszsymltd',0,0,1,1),
 (445,'慈溪市逍林镇林西村林西路647号','','2011-01-26 14:15:19','2010-08-11 00:00:00','2011-01-26 14:15:19','2013-06-30 00:00:00','',90,'魏强','330282033337','2010-08-11 00:00:00',0,'13484235950','','','','慈溪市逍林阿强汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszlaqqcxld',0,0,1,1),
 (446,'慈溪市横河镇乌山村','','2011-01-26 14:15:19','2010-08-11 00:00:00','2011-01-26 14:15:19','2013-06-30 00:00:00','',90,'郑泽华','330282033338','2010-08-11 00:00:00',0,'13216676699','','','','慈溪市横河郑杨汽车装潢服务部','机动车维修：三类机动车维修（车身清洁维护、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxshhzyqczzfwb',0,0,1,1),
 (447,'慈溪市庵宗公路388号','','2011-01-26 14:15:19','2010-07-02 00:00:00','2011-01-26 14:15:19','2013-06-30 00:00:00','',90,'沈映芬','330282033339','2010-07-13 00:00:00',0,'13567801283','63474273','','','慈溪市庵东映芬洗车店','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszdyfxcd',0,0,1,1),
 (448,'慈溪市坎墩街道沈五村镇中路345号(原沈五村)','','2011-01-26 14:15:19','2010-08-11 00:00:00','2011-01-26 14:15:19','2013-06-30 00:00:00','',90,'张军波','330282033340','2010-08-11 00:00:00',0,'13738873363','','','','慈溪市坎墩明辉汽车装潢店','机动车维修：三类机动车维修（涂漆、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxskdmhqczzd',0,0,1,1),
 (449,'慈溪市宗汉镇百两桥村','','2011-01-26 14:15:19','2010-08-23 00:00:00','2011-01-26 14:15:19','2012-05-31 00:00:00','',90,'沈桂勇','330282033341','2010-08-23 00:00:00',71,'','13968279152','','','慈溪市宗汉东升汽车车身维修店','维修(车身维修)','   ',105,'cxszhdsqccswxd',0,0,1,1),
 (450,'慈溪市宗汉街道北二环线131','','2011-01-26 14:15:20','2005-07-01 00:00:00','2011-01-26 14:15:20','2008-07-01 00:00:00','',90,'张金科','330282033343','2010-08-23 00:00:00',71,'','13516748878','','','慈溪市宗汉金科汽车车身清洁维修店','维修(车身清洁维护)','   ',105,'cxszhjkqccsqjwxd',0,0,1,1),
 (451,'慈溪市观海卫镇东桥头村','','2011-01-26 14:15:20','2010-09-15 00:00:00','2011-01-26 14:15:20','2012-04-08 00:00:00','',90,'沈立宜','330282033344','2010-09-15 00:00:00',71,'','81316986','13968271441','','慈溪市观海卫立宜汽车车身清洁维护店','维修(车身维修)','   ',105,'cxsghwlyqccsqjwhd',0,0,1,1),
 (452,'慈溪市浒山北二环中路167号','','2011-01-26 14:15:20','2010-08-11 00:00:00','2011-01-26 14:15:20','2011-06-30 00:00:00','',90,'裘士锋','330282033345','2010-08-11 00:00:00',71,'','81357771','','','慈溪市浒山街道士锋车身修理店','维修(车身维修)','   ',105,'cxszsjdsfcsxld',0,0,1,1),
 (453,'慈溪市浒山街道上周塘村','','2011-01-26 14:15:20','2010-08-11 00:00:00','2011-01-26 14:15:20','2011-06-30 00:00:00','',90,'孙新丰','330282033346','2010-08-11 00:00:00',71,'','63838909','66373480','','慈溪市浒山宏丰汽车车身维修厂','维修(车身维修,汽车装璜)','   ',105,'cxszshfqccswxc',0,0,1,1),
 (454,'慈溪市坎墩街道兴镇街291号','','2011-01-26 14:15:20','2002-07-01 00:00:00','2011-01-26 14:15:20','2005-07-01 00:00:00','',90,'王建华','330282033347','2010-08-11 00:00:00',71,'','63282455','','','慈溪市坎墩利华汽车车身修理店','维修(车身维修)','   ',105,'cxskdlhqccsxld',0,0,1,1),
 (455,'市体育中心','','2011-01-26 14:15:20','2004-03-22 00:00:00','2011-01-26 14:15:20','2007-03-22 00:00:00','',90,'卢文达','330282033348','2010-08-11 00:00:00',71,'','63025266','','','慈溪市新光汽车车身修理店','维修(车身维修)','   ',105,'cxsxgqccsxld',0,0,1,1),
 (456,'慈溪市坎墩穿镇中路10号','','2011-01-26 14:15:20','2002-07-01 00:00:00','2011-01-26 14:15:20','2005-07-01 00:00:00','',90,'胡小立','330282033349','2010-08-11 00:00:00',71,'','63284793','','','慈溪市坎墩晓立汽车车身修理店','维修(车身维修)','   ',105,'cxskdxlqccsxld',0,0,1,1),
 (457,'白沙八字桥村','','2011-01-26 14:15:20','2007-02-07 00:00:00','2011-01-26 14:15:20','2010-02-07 00:00:00','',90,'余爱平','330282033350','2010-08-11 00:00:00',71,'','63839551','13306657212','','慈溪市浒山新万里汽车修理店','维修(车身维修,车身清洁维护)','   ',105,'cxszsxwlqcxld',0,0,1,1),
 (458,'观海卫镇加油站斜对面','','2011-01-26 14:15:20','2010-09-15 00:00:00','2011-01-26 14:15:20','2011-06-30 00:00:00','',90,'蒋国远','330282033351','2010-09-15 00:00:00',71,'','63618234','13008945833','','慈溪市观海卫镇国远汽车钣金店','维修(车身维修)','   ',105,'cxsghwzgyqczjd',0,0,1,1),
 (459,'周巷镇环城南路','','2011-01-26 14:15:20','2010-08-23 00:00:00','2011-01-26 14:15:20','2011-06-30 00:00:00','',90,'郑先杰','330282033352','2010-08-23 00:00:00',71,'','63308399','','','周巷先杰车身修理店','维修(车身维修)','   ',105,'zxxjcsxld',0,0,1,1),
 (460,'天元镇天元村余庵公路东侧','','2011-01-26 14:15:21','2010-08-23 00:00:00','2011-01-26 14:15:21','2011-06-30 00:00:00','',90,'沈国会','330282033353','2010-08-23 00:00:00',71,'','63400448','13008938966','','慈溪市天元镇精诚汽车车身维修涂漆店','维修(车身维修,涂漆)','   ',105,'cxstyzjcqccswxtqd',0,0,1,1),
 (461,'慈溪市海关北路329号','','2011-01-26 14:15:21','2010-08-11 00:00:00','2011-01-26 14:15:21','2011-06-30 00:00:00','',90,'杨国明','330282033354','2010-08-11 00:00:00',71,'','13506781555','63040407','','慈溪市古塘明亿汽车修理厂','维修(车身维修)','   ',105,'cxsgtmyqcxlc',0,0,1,1),
 (462,'观海卫镇小团浦村','','2011-01-26 14:15:21','2010-09-15 00:00:00','2011-01-26 14:15:21','2011-06-30 00:00:00','',90,'张志杰','330282033355','2010-09-15 00:00:00',71,'','63661606','13185936363','','慈溪市观海卫镇庆丰汽车修理店','维修(车身维修)','   ',105,'cxsghwzqfqcxld',0,0,1,1),
 (463,'慈溪市坎墩镇坎东村','','2011-01-26 14:15:21','2005-07-01 00:00:00','2011-01-26 14:15:21','2008-07-01 00:00:00','',90,'胡小东','330282033356','2010-09-15 00:00:00',71,'','63283474','13566333386','','慈溪市坎墩小东汽车修理店','维修(车身维修)','   ',105,'cxskdxdqcxld',0,0,1,1),
 (464,'浒山街道鸣山村','','2011-01-26 14:15:21','2010-08-11 00:00:00','2011-01-26 14:15:21','2011-06-30 00:00:00','',90,'马锦涛','330282033357','2010-08-11 00:00:00',71,'','13306747950','63380011','','慈溪市浒山宏发汽车车身修理店','维修(车身维修)','   ',105,'cxszshfqccsxld',0,0,1,1),
 (465,'浒山西二环线路','','2011-01-26 14:15:21','2010-08-11 00:00:00','2011-01-26 14:15:21','2011-06-30 00:00:00','',90,'陈文强','330282033361','2010-08-11 00:00:00',71,'','63805758','13003705564','','慈溪市浒山彩虹车身修理店','维修(车身维修)','   ',105,'cxszschcsxld',0,0,1,1),
 (466,'周巷镇平王居委','','2011-01-26 14:15:22','2006-04-10 00:00:00','2011-01-26 14:15:22','2009-07-01 00:00:00','',90,'钟国壮','330282033362','2010-08-11 00:00:00',71,'','13065639810','','','慈溪市周巷国壮汽车修理店','维修(车身维修,涂漆)','   ',105,'cxszxgzqcxld',0,0,1,1),
 (467,'慈溪市横河镇彭南村湖清垫','','2011-01-26 14:15:22','2010-08-11 00:00:00','2011-01-26 14:15:22','2011-06-30 00:00:00','',90,'张建波','330282033363','2010-08-11 00:00:00',71,'','13586781118','','','慈溪市横河湖清垫汽车车身修理店','维修(车身维修)','   ',105,'cxshhhqdqccsxld',0,0,1,1),
 (468,'宗汉百两桥','','2011-01-26 14:15:22','2008-07-23 00:00:00','2011-01-26 14:15:22','2011-06-30 00:00:00','',90,'陆岳明','330282033365','2010-08-11 00:00:00',71,'','81351079','13008945074','','慈溪市宗汉伟民汽车车身修理店','维修(车身维修)','   ',105,'cxszhwmqccsxld',0,0,1,1),
 (469,'慈溪市白沙街道东三环北路299号','','2011-01-26 14:15:22','2009-03-03 00:00:00','2011-01-26 14:15:22','2011-06-30 00:00:00','',90,'沈杰','330282033366','2010-10-22 00:00:00',71,'','','','','慈溪市浒山惠明车身修理店','机动车维修：三类机动车维修〔车身维修（钣金）〕。','',105,'cxszshmcsxld',0,0,1,1),
 (470,'慈溪市崇寿镇六塘村','','2011-01-26 14:15:22','2010-12-27 00:00:00','2011-01-26 14:15:22','2013-06-30 00:00:00','',90,'沈树波','330282033367','2010-12-27 00:00:00',71,'','13858230680','','','慈溪市崇寿镇永兴车身修理店','维修(车身维修)','',105,'cxscszyxcsxld',0,0,1,1),
 (471,'慈溪市匡堰镇樟树村樟东','','2011-01-26 14:15:22','2010-09-15 00:00:00','2011-01-26 14:15:22','2011-06-30 00:00:00','',90,'毛庆锋','330282033368','2010-09-15 00:00:00',71,'','63862001','13008917219','','慈溪市匡堰庆锋汽车车身修理店','维修(车身维修)','   ',105,'cxskyqfqccsxld',0,0,1,1),
 (472,'浒山开发大道395－401','','2011-01-26 14:15:22','2005-07-01 00:00:00','2011-01-26 14:15:22','2008-07-01 00:00:00','',90,'邬瀛东','330282033369','2010-09-15 00:00:00',71,'','13116603333','63803691','','慈溪市浒山兴旺汽车车身清洁维护店','维修(车身维修)','   ',105,'cxszsxwqccsqjwhd',0,0,1,1),
 (473,'慈溪市坎墩街道担山北路1616号','','2011-01-26 14:15:22','2010-08-11 00:00:00','2011-01-26 14:15:22','2013-06-30 00:00:00','',90,'杨峰','330282033370','2010-08-11 00:00:00',71,'','15968911999','81373823','','慈溪市坎墩亚佳汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxskdyjqcxld',0,0,1,1),
 (474,'浒山街道西二环北路54-56号','','2011-01-26 14:15:22','2010-08-11 00:00:00','2011-01-26 14:15:22','2011-06-30 00:00:00','',90,'冯锡明','330282033371','2010-08-11 00:00:00',71,'','13606743214','','','慈溪天和汽车车身修理店','维修(车身维修)','   ',105,'cxthqccsxld',0,0,1,1),
 (475,'慈溪市逍林镇新园村机电市场对面','','2011-01-26 14:15:22','2010-08-11 00:00:00','2011-01-26 14:15:22','2011-06-30 00:00:00','',90,'岑映儿','330282033372','2010-08-11 00:00:00',71,'','63510388','13968299788','','慈溪市逍林镇清风车影汽车车身修理店','维修(车身维修,汽车装璜)','   ',105,'cxszlzqfcyqccsxld',0,0,1,1),
 (476,'杭州湾新区海南村','','2011-01-26 14:15:22','2006-06-06 00:00:00','2011-01-26 14:15:22','2009-07-01 00:00:00','',90,'宣光金','330282033373','2010-08-11 00:00:00',71,'','63290250','63016733','','慈溪市杭州湾金路冲汽车修理行','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxshzwjlcqcxlx',0,0,1,1),
 (477,'慈溪市新浦镇高桥村樟新公路','','2011-01-26 14:15:22','2010-08-11 00:00:00','2011-01-26 14:15:22','2011-06-30 00:00:00','',90,'岑利武','330282033374','2010-08-11 00:00:00',71,'','63572693','13566519051','','慈溪市新浦利武汽车修理店','维修(车身维修,供油系统维护及油品更换)','　',105,'cxsxplwqcxld',0,0,1,1),
 (478,'慈溪市浒山街道八字桥村','','2011-01-26 14:15:22','2010-08-11 00:00:00','2011-01-26 14:15:22','2011-06-30 00:00:00','',90,'龚百丰','330282033375','2010-08-11 00:00:00',71,'','66337895','13606747473','','慈溪市浒山悦达汽车维修服务部','维修(车身维修,涂漆)','   ',105,'cxszsydqcwxfwb',0,0,1,1),
 (479,'宗汉金轮二厂往东100米','','2011-01-26 14:15:23','2005-06-24 00:00:00','2011-01-26 14:15:23','2008-06-24 00:00:00','',85,'胡建柯','330282033376','2010-08-11 00:00:00',71,'','81336516','13805828665','','宗汉风影车身修理店','维修(车身维修)','   ',105,'zhfycsxld',0,0,1,1),
 (480,'慈溪市古塘街道担山跟村','','2011-01-26 14:15:23','2010-10-12 00:00:00','2011-01-26 14:15:23','2013-06-30 00:00:00','',85,'孙志根','330282033377','2010-10-21 00:00:00',71,'','13805828665','','','慈溪市浒山根大轿车喷漆修理厂','维修(车身维修,涂漆)','',105,'cxszsgdjcpqxlc',0,0,1,1),
 (481,'浒山镇彭民路八字桥村','','2011-01-26 14:15:23','2005-08-18 00:00:00','2011-01-26 14:15:23','2008-07-01 00:00:00','',90,'胡孟洪','330282033378','2010-10-21 00:00:00',71,'','13566606355','','','慈溪市浒山车先生汽车维修部','维修(车身维修,电气系统维修)','   ',105,'cxszscxsqcwxb',0,0,1,1),
 (482,'掌起镇厉家村下叶道口旁','','2011-01-26 14:15:23','2010-08-11 00:00:00','2011-01-26 14:15:23','2011-06-30 00:00:00','',90,'王宏杰','330282033379','2010-08-11 00:00:00',71,'','13906610682','','','慈溪市掌起浩翔汽车修理部','维修(车身维修,汽车装璜)','   ',105,'cxszqhxqcxlb',0,0,1,1),
 (483,'慈溪市庵东镇宏兴村','','2011-01-26 14:15:23','2010-08-23 00:00:00','2011-01-26 14:15:23','2012-06-30 00:00:00','',90,'陈建新','330282033380','2010-08-23 00:00:00',0,'','13957450592','','','慈溪市庵东慈泽轮胎修理店','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxszdczltxld',0,0,1,1),
 (484,'慈溪市周巷镇万寿寺村周西公路128号','','2011-01-26 14:15:23','2010-08-23 00:00:00','2011-01-26 14:15:23','2012-07-17 00:00:00','',90,'王红杰','330282033381','2010-08-23 00:00:00',0,'13968286628','','','','慈溪市周巷新城汽车修理店','机动车维修：三类机动车维修（车身维修）。','',105,'cxszxxcqcxld',0,0,1,1),
 (485,'慈溪市周巷镇周邵村周家','','2011-01-26 14:15:23','2010-08-25 00:00:00','2011-01-26 14:15:23','2011-06-30 00:00:00','',90,'王丁锋','330282033382','2010-08-25 00:00:00',0,'','15869563770','','','慈溪市周巷风迪汽车维修店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszxfdqcwxd',0,0,1,1),
 (486,'慈溪市白沙路街道新城大道北路500号','','2011-01-26 14:15:23','2010-08-25 00:00:00','2011-01-26 14:15:23','2013-06-30 00:00:00','',90,'王俊成','330282033383','2010-08-25 00:00:00',0,'15967888088','','','','慈溪市白沙路新世纪汽车服务中心','机动车维修：三类机动车维修（供油系统维护及油品更换、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsbslxsjqcfwzx',0,0,1,1),
 (487,'慈溪市附海镇南圆村','','2011-01-26 14:15:23','2010-09-15 00:00:00','2011-01-26 14:15:23','2013-06-30 00:00:00','',90,'宓学空','330282033384','2010-09-15 00:00:00',0,'13968268000','','','','慈溪市附海赛浪汽车装璜店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsfhslqczzd',0,0,1,1),
 (488,'慈溪市长河镇沧田村芦庵公路688号','','2011-01-26 14:15:23','2010-10-13 00:00:00','2011-01-26 14:15:23','2013-10-13 00:00:00','',90,'陈建','330282033387','2010-10-13 00:00:00',0,'15058081498','','','','慈溪市长河双意洗车店','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxschsyxcd',0,0,1,1),
 (489,'慈溪市白沙路街道彭民公路74号','','2011-01-26 14:15:23','2010-10-26 00:00:00','2011-01-26 14:15:23','2013-06-30 00:00:00','',90,'岑佰新','330282033388','2010-10-26 00:00:00',71,'13706746368','','','','慈溪市浒山绿色自动电脑洗车场','机动车维修：三类机动车维修（车身清洁维护、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszslszddnxcc',0,0,1,1),
 (490,'慈溪市浒山街道慈百路311-313号（和润公寓）','','2011-01-26 14:15:23','2010-11-01 00:00:00','2011-01-26 14:15:23','2013-06-30 00:00:00','',90,'周琪彬','330282033389','2010-11-01 00:00:00',0,'','13777067131','','','慈溪市浒山百援汽车装潢店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszsbyqczzd',0,0,1,1),
 (491,'慈溪市逍林镇林西村','','2011-01-26 14:15:23','2010-10-29 00:00:00','2011-01-26 14:15:23','2013-06-30 00:00:00','',90,'陈伟','330282033390','2010-11-19 00:00:00',0,'','','','','慈溪市逍林挽风汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszlwfqcxld',0,0,1,1),
 (492,'慈溪市周巷镇开发西路587号','','2011-01-26 14:15:23','2010-12-29 00:00:00','2011-01-26 14:15:23','2013-06-30 00:00:00','',90,'熊磊','330282033391','2010-12-29 00:00:00',0,'13858312567','','','','慈溪市周巷阿磊汽车装潢店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszxalqczzd',0,0,1,1),
 (493,'慈溪市浒山街道西二环线湾底路口','','2011-01-26 14:15:23','2010-08-11 00:00:00','2011-01-26 14:15:23','2011-06-30 00:00:00','',90,'黄加南','330282033401','2010-08-11 00:00:00',71,'13606748222','63890293','13958282110','','慈溪市浒山鹦鹉汽车维修店','维修(车身维修,汽车装璜)','   ',105,'cxszszzqcwxd',0,0,1,1),
 (494,'慈溪市新江路国南4号楼756-758号','','2011-01-26 14:15:23','2010-08-11 00:00:00','2011-01-26 14:15:23','2011-06-19 00:00:00','',90,'季建明','330282033403','2010-08-11 00:00:00',71,'','63788571','13506744701','','慈溪市浒山建明汽车装潢店','维修(汽车装璜)','   ',105,'cxszsjmqczzd',0,0,1,1),
 (495,'慈溪市浒山八字桥村','','2011-01-26 14:15:24','2005-07-01 00:00:00','2011-01-26 14:15:24','2008-07-01 00:00:00','',90,'洪福标','330282033404','2010-08-11 00:00:00',71,'','63820919','13306623666','','慈溪市浒山福标玻璃店','维修(门窗玻璃安装)','   ',105,'cxszsfbbld',0,0,1,1),
 (496,'慈溪市浒山街道明州路497-501号','','2011-01-26 14:15:24','2010-08-11 00:00:00','2011-01-26 14:15:24','2011-07-08 00:00:00','',90,'张永辉','330282033405','2010-08-11 00:00:00',71,'','63025277','13858305340','','慈溪市浒山华胜汽车配件店','维修(供油系统维护及油品更换)','   ',105,'cxszshsqcpjd',0,0,1,1),
 (497,'慈溪市浒山镇峙山路74号','','2011-01-26 14:15:24','2002-07-01 00:00:00','2011-01-26 14:15:24','2005-07-01 00:00:00','',90,'徐辉','330282033406','2010-08-11 00:00:00',71,'','63898555','','','慈溪市浒山恒辉汽车装潢护理店','维修(车身维修)','   ',105,'cxszshhqczzhld',0,0,1,1),
 (498,'慈溪市浒山八字桥村','','2011-01-26 14:15:24','2005-07-01 00:00:00','2011-01-26 14:15:24','2008-07-01 00:00:00','',90,'卢志友','330282033407','2010-08-11 00:00:00',71,'','66303651','13806646719','','慈溪市浒山志友水箱修理店','维修(散热器(水箱)维修)','   ',105,'cxszszysxxld',0,0,1,1),
 (499,'慈溪市浒山慈百路263号','','2011-01-26 14:15:24','2002-07-01 00:00:00','2011-01-26 14:15:24','2005-07-01 00:00:00','',90,'肖建华','330282033408','2010-08-11 00:00:00',71,'','63801621','','','慈溪市浒山建华汽车玻璃店','维修(门窗玻璃安装)','   ',105,'cxszsjhqcbld',0,0,1,1),
 (500,'慈溪市浒山西二环北路110号','','2011-01-26 14:15:24','2010-08-11 00:00:00','2011-01-26 14:15:24','2011-06-30 00:00:00','',90,'周民忠','330282033410','2010-08-11 00:00:00',71,'','63807083','1385817120','','慈溪市浒山民忠喷油泵修理店','维修(喷油泵和喷油器维修)','   ',105,'cxszsmzpybxld',0,0,1,1),
 (501,'慈溪市长河镇沧北村','','2011-01-26 14:15:24','2010-08-23 00:00:00','2011-01-26 14:15:24','2011-06-30 00:00:00','',90,'应园月','330282033411','2010-08-23 00:00:00',71,'','63406275','13957455725','','慈溪市长河园月汽车油泵维修店','维修(喷油泵和喷油器维修)','   ',105,'cxschyyqcybwxd',0,0,1,1),
 (502,'慈溪市掌起镇周家段村慈掌路661号','','2011-01-26 14:15:24','2010-08-11 00:00:00','2011-01-26 14:15:24','2013-06-30 00:00:00','',90,'徐明波','330282033415','2010-08-11 00:00:00',71,'','63777788','','','慈溪市掌起金手指洗车店','维修(车身清洁维护)','',105,'cxszqjszxcd',0,0,1,1),
 (503,'宗汉北二环西路79号','','2011-01-26 14:15:24','2003-09-18 00:00:00','2011-01-26 14:15:24','2006-09-18 00:00:00','',90,'张国新','330282033418','2010-08-11 00:00:00',71,'','13805817576','','','宗汉新月汽车美容装璜店','维修(汽车装璜)','   ',105,'zhxyqcmrzzd',0,0,1,1),
 (504,'慈溪市观海卫镇新泽村','','2011-01-26 14:15:24','2010-09-15 00:00:00','2011-01-26 14:15:24','2011-06-30 00:00:00','',90,'蒋立峰','330282033419','2010-09-15 00:00:00',71,'','63653878','13008945553','','慈溪市观海卫金海汽车清洁维护店','维修(车身清洁维护)','   ',105,'cxsghwjhqcqjwhd',0,0,1,1),
 (505,'慈溪市横河镇梅川路综合楼','','2011-01-26 14:15:24','2010-08-11 00:00:00','2011-01-26 14:15:24','2011-06-30 00:00:00','',90,'胡建青','330282033420','2010-08-11 00:00:00',71,'13906747661','63251198','66355537','','慈溪市横河建青汽车清洁维护店','维修(车身清洁维护)','   ',105,'cxshhjqqcqjwhd',0,0,1,1),
 (506,'慈溪市横河镇乌山村前应路边','','2011-01-26 14:15:24','2010-08-11 00:00:00','2011-01-26 14:15:24','2011-06-30 00:00:00','',90,'胡再康','330282033424','2010-08-11 00:00:00',71,'','13805827482','','','慈溪市横河镇车辆清洗店','维修(车身清洁维护)','   ',105,'cxshhzclqxd',0,0,1,1),
 (507,'浒山镇新城大道应莫陈村综合楼','','2011-01-26 14:15:24','2010-08-11 00:00:00','2011-01-26 14:15:24','2011-06-30 00:00:00','',90,'邹央君','330282033425','2010-08-11 00:00:00',71,'','63034217','13805829307','','慈溪市浒山镇利定车身清洁维护店','维修(车身维修,车身清洁维护)','   ',105,'cxszszldcsqjwhd',0,0,1,1),
 (508,'慈溪市桥头镇日旺工业园区烟墩村','','2011-01-26 14:15:24','2010-09-15 00:00:00','2011-01-26 14:15:24','2012-06-30 00:00:00','',90,'叶向阳','330282033426','2010-09-15 00:00:00',71,'','','','','慈溪市桥头向阳汽车修理店','维修(电气系统维修,车身清洁维护)','   ',105,'cxsqtxyqcxld',0,0,1,1),
 (509,'慈溪市浒山吉祥新村58号106室','','2011-01-26 14:15:24','2002-07-01 00:00:00','2011-01-26 14:15:24','2005-07-01 00:00:00','',90,'余沪军','330282033428','2010-09-15 00:00:00',71,'','','13805810952','','浒山镇诚信汽车清洁维护店','维修(车身清洁维护)','   ',105,'zszcxqcqjwhd',0,0,1,1),
 (510,'慈溪市浒山镇西二环线','','2011-01-26 14:15:24','2002-07-01 00:00:00','2011-01-26 14:15:24','2005-07-01 00:00:00','',90,'刘聂飞','330282033429','2010-09-15 00:00:00',71,'','63380011','','','慈溪市浒山长运汽车清洁维护店','维修(车身清洁维护)','   ',105,'cxszscyqcqjwhd',0,0,1,1),
 (511,'慈溪市新浦镇老浦村','','2011-01-26 14:15:24','2010-08-11 00:00:00','2011-01-26 14:15:24','2011-06-30 00:00:00','',90,'岑潭夫','330282033430','2010-08-11 00:00:00',71,'','63599170','13506747112','','慈溪新浦镇阿谭汽车车身清洁维护店','维修(车身维修,车身清洁维护)','   ',105,'cxxpzatqccsqjwhd',0,0,1,1),
 (512,'慈溪市宗汉街道西二环线与庙山路交叉口','','2011-01-26 14:15:25','2010-09-28 00:00:00','2011-01-26 14:15:25','2013-06-30 00:00:00','',90,'陆海泉','330282033431','2010-09-28 00:00:00',71,'','','13606741770','','慈溪市宗汉兴驰汽车维修店','维修(车身维修,喷油泵和喷油器维修)','',105,'cxszhxcqcwxd',0,0,1,1),
 (513,'浒山镇车站路南端','','2011-01-26 14:15:25','2005-07-01 00:00:00','2011-01-26 14:15:25','2008-07-01 00:00:00','',90,'段乐庆','330282033433','2010-09-28 00:00:00',71,'','13306747000','','','慈溪市浒山超越汽车装璜美容店','维修(汽车装璜)','   ',105,'cxszscyqczzmrd',0,0,1,1),
 (514,'慈溪市浒山街道南二环东路872-880号','','2011-01-26 14:15:25','2010-08-11 00:00:00','2011-01-26 14:15:25','2013-06-30 00:00:00','',90,'郑明','330282033434','2010-08-11 00:00:00',71,'','','13867814078','','慈溪市浒山大地汽车装璜店','维修(汽车装璜)','   ',105,'cxszsddqczzd',0,0,1,1),
 (515,'慈溪市桥头镇桥头路256-262号','','2011-01-26 14:15:25','2009-05-18 00:00:00','2011-01-26 14:15:25','2011-06-30 00:00:00','',90,'孙建朝','330282033435','2009-06-23 00:00:00',0,'','','','','慈溪市桥头车保姆汽车装璜店','机动车维修：三类机动车维修（车身清洁维护、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsqtcbmqczzd',0,0,1,1),
 (516,'慈溪市胜山镇一灶村','','2011-01-26 14:15:25','2010-08-11 00:00:00','2011-01-26 14:15:25','2012-12-31 00:00:00','',90,'裘听','330282033437','2010-08-11 00:00:00',71,'','13777075423','','','慈溪市胜山新荣汽车维修店','维修(车身清洁维护)','   ',105,'cxsssxrqcwxd',0,0,1,1),
 (517,'慈溪市崇寿镇海南村','','2011-01-26 14:15:25','2008-08-22 00:00:00','2011-01-26 14:15:25','2011-06-30 00:00:00','',90,'吴国荣','330282033438','2010-08-11 00:00:00',71,'','63290091','13567411676','','慈溪市崇寿镇国荣车身清洁维护店','维修(车身清洁维护)','   ',105,'cxscszgrcsqjwhd',0,0,1,1),
 (518,'慈溪市周巷镇周邵村113号','','2011-01-26 14:15:25','2010-08-24 00:00:00','2011-01-26 14:15:25','2012-12-30 00:00:00','',90,'沈银桥','330282033439','2010-08-24 00:00:00',71,'','','','','慈溪市周巷周书汽车维修店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','   ',105,'cxszxzsqcwxd',0,0,1,1),
 (519,'慈溪市客运中心东站','','2011-01-26 14:15:25','2003-05-23 00:00:00','2011-01-26 14:15:25','2006-05-23 00:00:00','',90,'王建钢','330282033441','2010-08-24 00:00:00',71,'','13806646052','','','浒山佳吉汽车车身清洁维护店','维修(车身清洁维护)','   ',105,'zsjjqccsqjwhd',0,0,1,1),
 (520,'慈溪市胜山镇二灶村村中心路南','','2011-01-26 14:15:25','2010-08-11 00:00:00','2011-01-26 14:15:25','2011-06-30 00:00:00','',90,'龚云飞','330282033442','2010-08-11 00:00:00',71,'','63526002','13606743139','','慈溪市胜山镇云飞汽车车身修理店','维修(供油系统维护及油品更换)','   ',105,'cxssszyfqccsxld',0,0,1,1),
 (521,'慈溪市浒山街道担山村','','2011-01-26 14:15:25','2003-05-23 00:00:00','2011-01-26 14:15:25','2006-05-23 00:00:00','',90,'孙洪波','330282033443','2010-08-11 00:00:00',71,'','13968283242','','','浒山华通车身清洁维护店','维修(车身清洁维护)','   ',105,'zshtcsqjwhd',0,0,1,1),
 (522,'浒山北二环中路415－417号','','2011-01-26 14:15:25','2010-08-11 00:00:00','2011-01-26 14:15:25','2011-06-30 00:00:00','',90,'戚立君','330282033444','2010-08-11 00:00:00',71,'','63895170','13586600886','','慈溪市浒山立君车身清洁维护店','维修(车身清洁维护)','   ',105,'cxszsljcsqjwhd',0,0,1,1),
 (523,'慈溪市范市镇329国道旁','','2011-01-26 14:15:25','2010-08-11 00:00:00','2011-01-26 14:15:25','2011-06-30 00:00:00','',90,'潘高宏','330282033445','2010-08-11 00:00:00',71,'','','','','慈溪市范市阿宏洗车店','维修(车身清洁维护,轮胎动平衡及修补)','   ',105,'cxsfsahxcd',0,0,1,1),
 (524,'慈溪市浒山街道宁兴家园1号楼1-2店面','','2011-01-26 14:15:25','2010-08-11 00:00:00','2011-01-26 14:15:25','2011-06-30 00:00:00','',90,'陈洲','330282033446','2010-08-11 00:00:00',71,'','13805825288','63026706','','慈溪市浒山新时代车身维护店','维修(车身清洁维护)','   ',105,'cxszsxsdcswhd',0,0,1,1),
 (525,'慈溪市宗汉街道周塘东村曙光路','','2011-01-26 14:15:25','2007-07-30 00:00:00','2011-01-26 14:15:25','2010-07-30 00:00:00','',90,'葛建华','330282033447','2010-08-11 00:00:00',71,'','','13325841781','','慈溪市宗汉曙光洗车场','维修(车身清洁维护)','   ',105,'cxszhsgxcc',0,0,1,1),
 (526,'浒山明州路1号','','2011-01-26 14:15:25','2010-08-11 00:00:00','2011-01-26 14:15:25','2010-05-08 00:00:00','',90,'邹金飞','330282033449','2010-08-11 00:00:00',71,'','13968294930','','','浒山阿东汽车车身清洁维护店','维修(车身清洁维护)','   ',105,'zsadqccsqjwhd',0,0,1,1),
 (527,'慈溪市浒山三北大街东延后油车村','','2011-01-26 14:15:25','2004-09-02 00:00:00','2011-01-26 14:15:25','2007-09-02 00:00:00','',90,'朱威振','330282033450','2010-08-11 00:00:00',71,'','13858393252','','','慈溪市浒山阿威汽车车身清洁维护店','维修(车身清洁维护)','   ',105,'cxszsawqccsqjwhd',0,0,1,1),
 (528,'慈溪市崇寿镇海南村','','2011-01-26 14:15:25','2009-06-29 00:00:00','2011-01-26 14:15:25','2012-06-30 00:00:00','',90,'应文杰','330282033451','2009-06-29 00:00:00',71,'','63296541','','','慈溪市崇寿镇文杰汽车油泵修理店','维修(喷油泵和喷油器维修)','   ',105,'cxscszwjqcybxld',0,0,1,1),
 (529,'慈溪市浒山街道西二环线华兴宾馆对面','','2011-01-26 14:15:25','2010-08-11 00:00:00','2011-01-26 14:15:25','2013-01-07 00:00:00','',90,'赵幼珍','330282033452','2010-08-11 00:00:00',0,'','81300926','','','慈溪市浒山珍珍洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszszzxcc',0,0,1,1),
 (530,'慈溪市范市镇新西村人民北路1 号','','2011-01-26 14:15:25','2005-07-01 00:00:00','2011-01-26 14:15:25','2008-07-01 00:00:00','',90,'高志旺','330282033453','2010-08-11 00:00:00',71,'','13606743303','13606743303','','慈溪市范市阿旺汽车维修店','维修(喷油泵和喷油器维修)','   ',105,'cxsfsawqcwxd',0,0,1,1),
 (531,'祟寿向天庵村','','2011-01-26 14:15:26','2005-09-30 00:00:00','2011-01-26 14:15:26','2008-07-01 00:00:00','',90,'陈冲冲','330282033455','2010-08-11 00:00:00',71,'','13586615521','','','慈溪市祟寿镇冲冲汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxssszccqcxld',0,0,1,1),
 (532,'观海卫蒋家桥村','','2011-01-26 14:15:26','2010-09-15 00:00:00','2011-01-26 14:15:26','2011-06-30 00:00:00','',90,'方凯','330282033456','2010-09-15 00:00:00',71,'','','13780035479','','慈溪市观海卫凯隆汽车修理厂','维修(供油系统维护及油品更换)','   ',105,'cxsghwklqcxlc',0,0,1,1),
 (533,'慈溪市浒山西二环北路158号','','2011-01-26 14:15:26','2006-11-17 00:00:00','2011-01-26 14:15:26','2009-07-01 00:00:00','',90,'张建辉','330282033457','2010-09-15 00:00:00',71,'','13065896463','13065896463','','慈溪市浒山小张油泵维修店','维修(三类机动车维修:喷油泵和喷油嘴维修)','   ',105,'cxszsxzybwxd',0,0,1,1),
 (534,'浒山新江路','','2011-01-26 14:15:26','2007-08-06 00:00:00','2011-01-26 14:15:26','2010-08-06 00:00:00','',90,'马婉芬','330282033458','2010-08-11 00:00:00',71,'','63381739','','','慈溪市浒山晶晶电脑洗车场','维修(车身清洁维护)','   ',105,'cxszsjjdnxcc',0,0,1,1),
 (535,'慈溪市龙山镇龙头场村','','2011-01-26 14:15:26','2010-08-11 00:00:00','2011-01-26 14:15:26','2013-01-20 00:00:00','',90,'陈峰','330282033459','2010-08-11 00:00:00',0,'13586602311','','','','慈溪市龙山大海汽车轮胎维修店','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxslsdhqcltwxd',0,0,1,1),
 (536,'慈溪市龙山镇龙头场村','','2011-01-26 14:15:26','2010-01-21 00:00:00','2011-01-26 14:15:26','2013-01-21 00:00:00','',90,'陈益峰','330282033460','2010-01-21 00:00:00',0,'15869564695','','','','慈溪市龙山子洋汽车维修店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxslszyqcwxd',0,0,1,1),
 (537,'慈溪市横河镇洋山岗村','庄养伟','2011-01-26 14:15:26','2010-08-11 00:00:00','2011-01-26 14:15:26','2013-02-01 00:00:00','',90,'周植伟','330282033461','2010-08-11 00:00:00',0,'13454745314','','','','慈溪市养伟汽车修理部','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsywqcxlb',0,0,1,1),
 (538,'慈溪市观海卫镇观海卫路544-550号','','2011-01-26 14:15:26','2010-09-15 00:00:00','2011-01-26 14:15:26','2012-06-30 00:00:00','',90,'姚凯','330282033463','2010-09-15 00:00:00',0,'13567946800','','','','慈溪市观海卫车道汽车装潢店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsghwcdqczzd',0,0,1,1),
 (539,'慈溪市浒山街道后二房路','','2011-01-26 14:15:26','2010-08-11 00:00:00','2011-01-26 14:15:26','2012-06-30 00:00:00','',90,'胡忠群','330282033464','2010-08-11 00:00:00',0,'13586641288','','','','慈溪市浒山迪群车辆清洗站','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxszsdqclqxz',0,0,1,1),
 (540,'慈溪市横河镇孙家境村','','2011-01-26 14:15:26','2010-08-11 00:00:00','2011-01-26 14:15:26','2013-03-15 00:00:00','',90,'孙立申','330282033465','2010-08-11 00:00:00',0,'13732166068','','','','慈溪市横河镇立申洗车行','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxshhzlsxcx',0,0,1,1),
 (541,'慈溪市长河镇东路','','2011-01-26 14:15:26','2010-08-23 00:00:00','2011-01-26 14:15:26','2013-03-17 00:00:00','',90,'周恩','330282033466','2010-08-23 00:00:00',0,'13806649970','','','','慈溪市长河迪奥汽车维修厂','机动车维修：三类机动车维修（发动机修理、车身维修）。','',105,'cxschdaqcwxc',0,0,1,1),
 (542,'慈溪市横河镇东上河村西跃马桥99-100号','','2011-01-26 14:15:26','2010-08-11 00:00:00','2011-01-26 14:15:26','2013-06-30 00:00:00','',90,'胡耐','330282033468','2010-08-11 00:00:00',0,'13968297979','63267850','','','慈溪市一佳汽车修理厂','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsyjqcxlc',0,0,1,1),
 (543,'慈溪市观海卫镇下泽山村','','2011-01-26 14:15:26','2010-05-24 00:00:00','2011-01-26 14:15:26','2013-06-30 00:00:00','',90,'朱文红','330282033469','2010-05-24 00:00:00',0,'13732178881','63532999','','','慈溪市车骑师汽车维修厂','机动车维修：三类机动车维修（发动机修理、车身维修）。','',105,'cxscqsqcwxc',0,0,1,1),
 (544,'慈溪市宗汉街道南池村','','2011-01-26 14:15:26','2010-08-23 00:00:00','2011-01-26 14:15:26','2013-06-30 00:00:00','',90,'李俊','330282033470','2010-08-23 00:00:00',0,'13567816291','','','','慈溪市宗汉俊帆汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhjfqcxld',0,0,1,1),
 (545,'慈溪市古塘街道工业小区担山路1号','','2011-01-26 14:15:26','2010-08-18 00:00:00','2011-01-26 14:15:26','2013-08-18 00:00:00','',90,'孟庆立','330282033471','2010-08-18 00:00:00',0,'','13484266616','','','慈溪市古塘博众汽车维修店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsgtbzqcwxd',0,0,1,1),
 (546,'慈溪市宗汉街道百兴村新界路112号','','2011-01-26 14:15:27','2010-08-02 00:00:00','2011-01-26 14:15:27','2013-08-02 00:00:00','',90,'公艳南','330282033472','2010-08-02 00:00:00',0,'13566312242','','','','慈溪市宗汉畅鑫汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhczqcxld',0,0,1,1),
 (547,'慈溪市古塘街道海关路161号','','2011-01-26 14:15:27','2010-08-19 00:00:00','2011-01-26 14:15:27','2013-08-19 00:00:00','',81,'张孟军','330282033473','2010-08-19 00:00:00',0,'','18906746680','','','慈溪市永振汽车销售有限公司','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsyzqcxsyxgs',0,0,1,1),
 (548,'慈溪市坎墩大道1199号','','2011-01-26 14:15:27','2010-09-06 00:00:00','2011-01-26 14:15:27','2013-09-06 00:00:00','',90,'陈小强','330282033475','2010-09-06 00:00:00',0,'13586602010','','','','慈溪市坎墩小强汽车修理店','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxskdxqqcxld',0,0,1,1),
 (549,'慈溪市崇寿镇永清北路190-196号','','2011-01-26 14:15:27','2010-08-28 00:00:00','2011-01-26 14:15:27','2013-06-30 00:00:00','',90,'陈福申','330282033476','2010-09-21 00:00:00',0,'13646666327','','','','慈溪市崇寿宇鑫汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxscsyzqcxld',0,0,1,1),
 (550,'慈溪市掌起镇周家段村慈掌路715号','','2011-01-26 14:15:27','2010-09-20 00:00:00','2011-01-26 14:15:27','2013-06-30 00:00:00','',90,'戴鹏羽','330282033477','2010-09-20 00:00:00',0,'13958278789','','','','慈溪市掌起凯羽汽车修理部','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszqkyqcxlb',0,0,1,1),
 (551,'慈溪市周巷镇城中村兴三弄路10号','','2011-01-26 14:15:27','2010-10-22 00:00:00','2011-01-26 14:15:27','2013-06-30 00:00:00','',90,'黄浩亮','330282033478','2010-10-22 00:00:00',0,'13805818531','','','','慈溪市周巷美亮汽车装潢店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszxmlqczzd',0,0,1,1),
 (552,'慈溪市宗汉街道马家路村','','2011-01-26 14:15:28','2010-11-04 00:00:00','2011-01-26 14:15:28','2013-06-30 00:00:00','',90,'付焱中','330282033479','2010-11-04 00:00:00',0,'13957458316','','','','慈溪市宗汉振发汽车养护店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhzfqcyhd',0,0,1,1),
 (553,'慈溪市宗汉街道百两村','','2011-01-26 14:15:28','2010-11-08 00:00:00','2011-01-26 14:15:28','2013-06-30 00:00:00','',90,'邹科群','330282033480','2010-11-08 00:00:00',0,'13957452493','','','','慈溪市宗汉路安汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhlaqcxld',0,0,1,1),
 (554,'慈溪市坎墩街道五灶一村镇中路东侧','','2011-01-26 14:15:28','2010-08-11 00:00:00','2011-01-26 14:15:28','2013-06-30 00:00:00','',90,'潘迪明','330282033491','2010-08-11 00:00:00',71,'','13968213279','63284381','','慈溪市坎墩明兴汽车维修店','维修(供油系统维护及油品更换)','',105,'cxskdmxqcwxd',0,0,1,1),
 (555,'慈溪市横河镇宜青桥村','','2011-01-26 14:15:28','2010-08-11 00:00:00','2011-01-26 14:15:28','2013-06-30 00:00:00','',81,'李梅','330282033497','2010-08-11 00:00:00',0,'13738862373','63028537','','','慈溪市汇峰汽车服务有限公司','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxshfqcfwyxgs',0,0,1,1),
 (556,'慈溪市龙山镇湖滨路134号','','2011-01-26 14:15:28','2010-08-11 00:00:00','2011-01-26 14:15:28','2013-03-31 00:00:00','',90,'金萍','330282033498','2010-08-11 00:00:00',0,'13567818107','','','','慈溪市范市速洁洗车店','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxsfssjxcd',0,0,1,1),
 (557,'慈溪市浒山街道西二环北路235号','','2011-01-26 14:15:28','2010-08-11 00:00:00','2011-01-26 14:15:28','2013-04-28 00:00:00','',81,'许焕央','330282033499','2010-08-11 00:00:00',0,'13805812006','','','','慈溪市旭东汽车配件有限公司浒山分公司','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxsxdqcpjyxgszsfgs',0,0,1,1),
 (558,'慈溪市新浦镇西面六甲村','','2011-01-26 14:15:29','2010-08-11 00:00:00','2011-01-26 14:15:29','2013-02-08 00:00:00','',90,'潘艮江','330282033500','2010-08-11 00:00:00',0,'','13906747795','','','慈溪市新浦啊江洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxsxpajxcc',0,0,1,1),
 (559,'慈溪市浒山镇石桥头村','','2011-01-26 14:15:29','2002-07-01 00:00:00','2011-01-26 14:15:29','2005-07-01 00:00:00','',90,'许志雪','330282033502','2010-08-11 00:00:00',0,'','63014405','','','慈溪市浒山志雪摩托车修理店','维修(摩托车修理)','   ',105,'cxszszxmtcxld',0,0,1,1),
 (560,'慈溪市白沙路街道轻纺村界堰路东区213号','','2011-01-26 14:15:29','2010-08-17 00:00:00','2011-01-26 14:15:29','2011-06-30 00:00:00','',90,'华鼎','330282033503','2010-08-25 00:00:00',0,'','63828669','81377571','','慈溪市白沙路华鼎摩托车修配店','维修(摩托车修理)','   ',105,'cxsbslhdmtcxpd',0,0,1,1);
INSERT INTO `enterprises` (`id`,`address`,`commission`,`createDate`,`dateBegin`,`editDate`,`dateEnd`,`handleMan`,`kind`,`legalPerson`,`license`,`licenseDate`,`qualification`,`telephone1`,`telephone2`,`telephone3`,`telephone4`,`unitName`,`workArea`,`workRemark`,`workType`,`py`,`station`,`status`,`createrId`,`editorId`) VALUES 
 (561,'慈溪市浒山街道白沙倪家路23号','','2011-01-26 14:15:30','2010-08-17 00:00:00','2011-01-26 14:15:30','2011-06-30 00:00:00','',90,'胡聪岳','330282033505','2010-08-25 00:00:00',0,'','','13505844032','','慈溪市浒山聪岳摩托车维修店','维修(摩托车修理)','   ',105,'cxszscymtcwxd',0,0,1,1),
 (562,'慈溪市浒山镇楼家村塘区','','2011-01-26 14:15:30','2002-07-01 00:00:00','2011-01-26 14:15:30','2005-07-01 00:00:00','',90,'孙建立','330282033506','2010-08-25 00:00:00',0,'','63012827','','','慈溪市浒山建立摩托车修理店','维修(摩托车修理)','   ',105,'cxszsjlmtcxld',0,0,1,1),
 (563,'慈溪市宗汉金堂村','','2011-01-26 14:15:30','2005-10-19 00:00:00','2011-01-26 14:15:30','2008-07-01 00:00:00','',90,'陈炎丰','330282033507','2010-08-25 00:00:00',0,'','63203487','','','慈溪市宗汉炎丰摩托修理店','维修(摩托车修理)','   ',105,'cxszhyfmtxld',0,0,1,1),
 (564,'慈溪市匡堰镇高家村','','2011-01-26 14:15:30','2008-09-02 00:00:00','2011-01-26 14:15:30','2011-06-30 00:00:00','',90,'王国涛','330282033508','2010-08-25 00:00:00',0,'','13586765856','','','慈溪市匡堰阿涛摩托车修理店','维修(摩托车修理)','   ',105,'cxskyatmtcxld',0,0,1,1),
 (565,'慈溪市横河镇龙南村','','2011-01-26 14:15:30','2010-08-17 00:00:00','2011-01-26 14:15:30','2011-06-30 00:00:00','',90,'孙立新','330282033509','2010-08-25 00:00:00',0,'','63262603','','','慈溪市横河镇立新摩托修配店','维修(摩托车修理)','   ',105,'cxshhzlxmtxpd',0,0,1,1),
 (566,'慈溪市浒山金一路416号','','2011-01-26 14:15:30','2005-07-01 00:00:00','2011-01-26 14:15:30','2008-07-01 00:00:00','',90,'胡家申','330282033510','2010-08-25 00:00:00',0,'','66393058','','','慈溪市浒山家申摩托车修理店','维修(摩托车修理)','   ',105,'cxszsjsmtcxld',0,0,1,1),
 (567,'慈溪市横河大桥南','','2011-01-26 14:15:30','2010-08-17 00:00:00','2011-01-26 14:15:30','2011-06-30 00:00:00','',90,'潘金苗','330282033511','2010-08-25 00:00:00',0,'','13056026775','13606740564','','慈溪市横河镇金苗摩托修配店','维修(摩托车修理)','   ',105,'cxshhzjmmtxpd',0,0,1,1),
 (568,'慈溪市浒山金一路311号','','2011-01-26 14:15:30','2005-07-01 00:00:00','2011-01-26 14:15:30','2008-07-01 00:00:00','',90,'姚万吨','330282033512','2010-08-25 00:00:00',0,'','63106182','13968282897','','慈溪市浒山万吨摩托车维修店','维修(摩托车修理)','   ',105,'cxszswdmtcwxd',0,0,1,1),
 (569,'慈溪市浒山金一路411号','','2011-01-26 14:15:30','2002-07-01 00:00:00','2011-01-26 14:15:30','2005-07-01 00:00:00','',90,'胡孟岳','330282033514','2010-08-25 00:00:00',0,'','','13616781912','','慈溪市浒山虞波摩托车维修店','维修(摩托车修理)','   ',105,'cxszsybmtcwxd',0,0,1,1),
 (570,'慈溪市逍林镇新南路900号（桥一村）','','2011-01-26 14:15:30','2010-08-17 00:00:00','2011-01-26 14:15:30','2011-06-30 00:00:00','',90,'徐晶晶','330282033515','2010-08-25 00:00:00',0,'','63504597','','','慈溪市逍林镇晶晶摩托车维修店','维修(摩托车修理)','   ',105,'cxszlzjjmtcwxd',0,0,1,1),
 (571,'慈溪市浒山镇杨家路村','','2011-01-26 14:15:30','2002-07-01 00:00:00','2011-01-26 14:15:30','2005-07-01 00:00:00','',90,'华听','330282033516','2010-08-25 00:00:00',0,'','63823942','','','慈溪市浒山镇华听摩托车维修店','维修(摩托车修理)','   ',105,'cxszszhtmtcwxd',0,0,1,1),
 (572,'浒山镇青少年宫路底','','2011-01-26 14:15:31','2005-07-01 00:00:00','2011-01-26 14:15:31','2008-07-01 00:00:00','',90,'沈正宏','330282033517','2010-08-25 00:00:00',0,'','沈正宏','1396744221','','慈溪市浒山正宏摩托车维修店','维修(摩托车修理)','   ',105,'cxszszhmtcwxd',0,0,1,1),
 (573,'慈溪市横河镇医院旁','','2011-01-26 14:15:31','2010-08-17 00:00:00','2011-01-26 14:15:31','2011-06-30 00:00:00','',90,'陆建银','330282033519','2010-08-25 00:00:00',0,'','13003773448','13003773448','','慈溪市横河镇建银摩托修理店','维修(摩托车修理)','   ',105,'cxshhzjymtxld',0,0,1,1),
 (574,'慈溪市横河镇','','2011-01-26 14:15:31','2010-08-17 00:00:00','2011-01-26 14:15:31','2011-06-30 00:00:00','',90,'戚佰勋','330282033520','2010-08-25 00:00:00',0,'','13008935373','63264657','','慈溪市横河镇佰勋摩托修理店','维修(摩托车修理)','   ',105,'cxshhzbxmtxld',0,0,1,1),
 (575,'慈溪市庵东镇市场路389号','','2011-01-26 14:15:31','2010-08-23 00:00:00','2011-01-26 14:15:31','2013-06-30 00:00:00','',90,'张涛','330282033521','2010-08-23 00:00:00',0,'13764444465','','','','慈溪市庵东车之杰洗车店','机动车维修：三类机动车维修（车身清洁维护、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszdczjxcd',0,0,1,1),
 (576,'天元镇钱王新村','','2011-01-26 14:15:31','2008-09-01 00:00:00','2011-01-26 14:15:31','2011-06-30 00:00:00','',90,'史济天','330282033522','2010-08-23 00:00:00',0,'','63457071','','','慈溪市天元齐天摩托车维修店','维修(摩托车修理)','   ',105,'cxstyqtmtcwxd',0,0,1,1),
 (577,'慈溪市胜山镇陈丁村','','2011-01-26 14:15:31','2005-07-01 00:00:00','2011-01-26 14:15:31','2008-07-01 00:00:00','',90,'阮柏训','330282033523','2010-08-23 00:00:00',0,'','13081962857','','','慈溪市胜山镇柏训摩托车维修店','维修(摩托车修理)','   ',105,'cxssszbxmtcwxd',0,0,1,1),
 (578,'慈溪市经济开发区三洋村','','2011-01-26 14:15:31','2010-07-19 00:00:00','2011-01-26 14:15:31','2013-06-30 00:00:00','',90,'郑欢明','330282033524','2010-07-19 00:00:00',0,'','13958264830','','','慈溪市杭州湾欢明摩托车修理部','维修(摩托车修理)','   ',105,'cxshzwhmmtcxlb',0,0,1,1),
 (579,'横河镇石堰村','','2011-01-26 14:15:31','2010-08-17 00:00:00','2011-01-26 14:15:31','2011-06-30 00:00:00','',90,'谢福汉','330282033525','2010-08-25 00:00:00',0,'','63257340','','','慈溪市横河镇福汉摩托修理店','维修(摩托车修理)','   ',105,'cxshhzfhmtxld',0,0,1,1),
 (580,'慈溪市宗汉镇新江路155号号','','2011-01-26 14:15:31','2010-05-19 00:00:00','2011-01-26 14:15:31','2013-05-19 00:00:00','',90,'朱英忠','330282033527','2010-05-19 00:00:00',0,'13989339919','','','','慈溪市景华汽车维修服务站','机动车维修：三类机动车维修（车身维修）。','',105,'cxsjhqcwxfwz',0,0,1,1),
 (581,'慈溪市浒山镇北二环中路180号','','2011-01-26 14:15:31','2005-07-01 00:00:00','2011-01-26 14:15:31','2008-07-01 00:00:00','',90,'胡坚定','330282033528','2010-05-19 00:00:00',0,'','63109616','','','慈溪市浒山镇坚定摩托车维修店','维修(摩托车修理)','   ',105,'cxszszjdmtcwxd',0,0,1,1),
 (582,'慈溪市浒山东山路金东花苑1号楼','','2011-01-26 14:15:31','2010-08-17 00:00:00','2011-01-26 14:15:31','2011-06-30 00:00:00','',90,'赵建孟','330282033529','2010-08-25 00:00:00',0,'','63893707','','','慈溪市浒山宏达摩托车维修店','维修(摩托车修理)','   ',105,'cxszshdmtcwxd',0,0,1,1),
 (583,'慈溪市浒山街道剑山路223号','','2011-01-26 14:15:31','2010-08-17 00:00:00','2011-01-26 14:15:31','2011-09-04 00:00:00','',90,'虞立峰','330282033530','2010-08-25 00:00:00',0,'','13706747173','','','慈溪市浒山程兴摩托车修配店','维修(摩托车修理)','   ',105,'cxszscxmtcxpd',0,0,1,1),
 (584,'慈溪市浒山孙塘南大楼','','2011-01-26 14:15:31','2002-07-01 00:00:00','2011-01-26 14:15:31','2005-07-01 00:00:00','',90,'胡达春','330282033531','2010-08-25 00:00:00',0,'','','1396621061','','慈溪市浒山达春摩托车维修店','维修(摩托车修理)','   ',105,'cxszsdcmtcwxd',0,0,1,1),
 (585,'浒山镇西洋寺路47号','','2011-01-26 14:15:31','2005-07-01 00:00:00','2011-01-26 14:15:31','2008-07-01 00:00:00','',90,'陈荣平','330282033532','2010-08-25 00:00:00',0,'1396622963','13806647299','','','慈溪市浒山奔驰摩托车维修店','维修(摩托车修理)','   ',105,'cxszsbcmtcwxd',0,0,1,1),
 (586,'慈溪市宗汉街道东小路29号美墅','','2011-01-26 14:15:32','2010-08-23 00:00:00','2011-01-26 14:15:32','2013-05-21 00:00:00','',90,'肖小涛','330282033533','2010-08-23 00:00:00',71,'13251960929','','','','慈溪市宗汉信众汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhxzqcxld',0,0,1,1),
 (587,'浒山镇眉山村','','2011-01-26 14:15:32','2002-07-01 00:00:00','2011-01-26 14:15:32','2005-07-01 00:00:00','',90,'陆春柏','330282033534','2010-08-23 00:00:00',0,'','','1385815186','','慈溪市浒山鸣山摩托车维修店','维修(摩托车修理)','   ',105,'cxszsmsmtcwxd',0,0,1,1),
 (588,'浒山镇剑山路65号','','2011-01-26 14:15:32','2005-07-01 00:00:00','2011-01-26 14:15:32','2008-07-01 00:00:00','',90,'胡长文','330282033535','2010-08-23 00:00:00',0,'','','81369020','','慈溪市浒山长文摩托车修理店','维修(摩托车修理)','   ',105,'cxszscwmtcxld',0,0,1,1),
 (589,'慈溪市掌起镇厉家村','','2011-01-26 14:15:32','2010-05-25 00:00:00','2011-01-26 14:15:32','2013-06-30 00:00:00','',90,'陈镇芳','330282033536','2010-05-28 00:00:00',0,'13777027333','','','','慈溪市荣鑫汽车维修厂','机动车维修：三类机动车维修（自动变速器维修）。','',105,'cxsrzqcwxc',0,0,1,1),
 (590,'慈溪市浒山新江路园丁新村238-240号','','2011-01-26 14:15:32','2005-07-01 00:00:00','2011-01-26 14:15:32','2008-07-01 00:00:00','',90,'徐监','330282033537','2010-05-28 00:00:00',0,'','63245321','','','慈溪市浒山徐监摩托车修理店','维修(摩托车修理)','   ',105,'cxszsxjmtcxld',0,0,1,1),
 (591,'慈溪市龙山镇双马村','','2011-01-26 14:15:32','2010-06-09 00:00:00','2011-01-26 14:15:32','2013-06-09 00:00:00','',90,'余亚芳','330282033538','2010-06-09 00:00:00',0,'13336676918','','','','慈溪市三北源丰汽车维修厂','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxssbyfqcwxc',0,0,1,1),
 (592,'慈溪市浒山南二环线862号','','2011-01-26 14:15:32','2002-07-01 00:00:00','2011-01-26 14:15:32','2005-07-01 00:00:00','',74,'许继业','330282033539','2010-06-09 00:00:00',0,'','','','','慈溪市浒山两、三轮摩托车维修店','维修(摩托车修理)','   ',105,'cxszsl、slmtcwxd',0,0,1,1),
 (593,'慈溪市仲寿六塘亭村','','2011-01-26 14:15:32','2005-07-01 00:00:00','2011-01-26 14:15:32','2008-07-01 00:00:00','',90,'章臣权','330282033540','2010-06-09 00:00:00',0,'','63298268','','','慈溪市仲寿六塘亭摩托车维修店','维修(摩托车修理)','   ',105,'cxszslttmtcwxd',0,0,1,1),
 (594,'慈溪市崇寿镇六塘','','2011-01-26 14:15:33','2010-08-17 00:00:00','2011-01-26 14:15:33','2011-06-30 00:00:00','',90,'戎家宝','330282033541','2010-08-25 00:00:00',0,'','63298960','','','慈溪市崇寿镇宝家摩托车维修店','维修(摩托车修理)','   ',105,'cxscszbjmtcwxd',0,0,1,1),
 (595,'浒山镇教场山路4号','','2011-01-26 14:15:33','2003-05-23 00:00:00','2011-01-26 14:15:33','2006-05-23 00:00:00','',90,'陆科杰','330282033542','2010-08-25 00:00:00',0,'','63814812','','','慈溪市浒山科杰摩托维修店','维修(摩托车修理)','   ',105,'cxszskjmtwxd',0,0,1,1),
 (596,'慈溪市浒山镇南二环线813号','','2011-01-26 14:15:33','2002-07-01 00:00:00','2011-01-26 14:15:33','2005-07-01 00:00:00','',90,'胡建国','330282033543','2010-08-25 00:00:00',0,'','','','','慈溪市浒山建国摩托车维修店','维修(摩托车修理)','   ',105,'cxszsjgmtcwxd',0,0,1,1),
 (597,'慈溪市浒山沙井头26号','','2011-01-26 14:15:33','2002-07-01 00:00:00','2011-01-26 14:15:33','2005-07-01 00:00:00','',90,'陈孟杰','330282033544','2010-08-25 00:00:00',0,'','','','','慈溪市浒山孟杰摩托车维修店','维修(摩托车修理)','   ',105,'cxszsmjmtcwxd',0,0,1,1),
 (598,'慈溪市浒山北二环线中路(电声厂)','','2011-01-26 14:15:33','2005-07-01 00:00:00','2011-01-26 14:15:33','2008-07-01 00:00:00','',90,'周丁军','330282033545','2010-08-25 00:00:00',0,'','66375885','','','慈溪市浒山镇军军摩托车修理店','维修(摩托车修理)','   ',105,'cxszszjjmtcxld',0,0,1,1),
 (599,'慈溪市浒山东山路241号','','2011-01-26 14:15:33','2005-07-01 00:00:00','2011-01-26 14:15:33','2008-07-01 00:00:00','',90,'杨剑武','330282033546','2010-08-25 00:00:00',0,'','66390801','1396621540','','慈溪市浒山剑武摩托车维修店','维修(摩托车修理)','   ',105,'cxszsjwmtcwxd',0,0,1,1),
 (600,'慈溪市浒山街道南二环线367号','','2011-01-26 14:15:33','2010-08-17 00:00:00','2011-01-26 14:15:33','2011-06-30 00:00:00','',90,'胡建林','330282033547','2010-08-25 00:00:00',0,'','63815520','','','慈溪市浒山建林摩托车修理店','维修(摩托车修理)','   ',105,'cxszsjlmtcxld',0,0,1,1),
 (601,'慈溪市浒山镇北二环中路196号','','2011-01-26 14:15:33','2005-07-01 00:00:00','2011-01-26 14:15:33','2008-07-01 00:00:00','',90,'钟永利','330282033548','2010-08-25 00:00:00',0,'','63816913','','','慈溪市浒山永利摩托车维修店','维修(摩托车修理)','   ',105,'cxszsylmtcwxd',0,0,1,1),
 (602,'慈溪市浒山工人路80号','','2011-01-26 14:15:33','2005-07-01 00:00:00','2011-01-26 14:15:33','2008-07-01 00:00:00','',90,'张旭翼','330282033549','2010-08-25 00:00:00',0,'','13805826964','','','慈溪市浒山飞阳摩托车维修部','维修(摩托车修理)','   ',105,'cxszsfymtcwxb',0,0,1,1),
 (603,'慈溪市浒山解放西路862号','','2011-01-26 14:15:33','2010-08-17 00:00:00','2011-01-26 14:15:33','2012-06-30 00:00:00','',90,'陆登如','330282033550','2010-08-25 00:00:00',0,'13806645929','','','','慈溪市浒山城西摩托车修理店','维修(摩托车修理)','',105,'cxszscxmtcxld',0,0,1,1),
 (604,'慈溪市浒山东山路口北7-8号','','2011-01-26 14:15:33','2008-08-25 00:00:00','2011-01-26 14:15:33','2011-06-30 00:00:00','',90,'张远帆','330282033551','2010-08-25 00:00:00',0,'','63109033','13906746964','','慈溪市浒山行万里摩托车维修店','维修(摩托车修理)','   ',105,'cxszsxwlmtcwxd',0,0,1,1),
 (605,'慈溪市浒山镇青少年宫路305号','','2011-01-26 14:15:33','2005-06-01 00:00:00','2011-01-26 14:15:33','2005-07-01 00:00:00','',90,'胡国荣','330282033552','2010-08-25 00:00:00',0,'','63890920','','','慈溪市浒山国荣摩托车维修店','维修(摩托车修理)','   ',105,'cxszsgrmtcwxd',0,0,1,1),
 (606,'慈溪市横河镇孙家境村','','2011-01-26 14:15:33','2010-08-17 00:00:00','2011-01-26 14:15:33','2011-06-30 00:00:00','',90,'孙立权','330282033553','2010-08-25 00:00:00',0,'','13616567750','13616567750','','慈溪市横河镇立权摩托车维修店','维修(摩托车修理)','   ',105,'cxshhzlqmtcwxd',0,0,1,1),
 (607,'慈溪市横河镇宜青桥村','','2011-01-26 14:15:33','2005-07-01 00:00:00','2011-01-26 14:15:33','2008-07-01 00:00:00','',90,'褚伟波','330282033554','2010-08-25 00:00:00',0,'','62505111','13008965534','','慈溪市横河镇伟波摩托车修理店','维修(摩托车修理)','   ',105,'cxshhzwbmtcxld',0,0,1,1),
 (608,'慈溪市宗汉南池','','2011-01-26 14:15:34','2005-07-01 00:00:00','2011-01-26 14:15:34','2008-07-01 00:00:00','',90,'杨勇','330282033555','2010-08-25 00:00:00',0,'','6320940','','','慈溪市浒山飞达摩托车维修店','维修(摩托车修理)','   ',105,'cxszsfdmtcwxd',0,0,1,1),
 (609,'周巷镇西三村','','2011-01-26 14:15:34','2008-02-19 00:00:00','2011-01-26 14:15:34','2012-02-19 00:00:00','',90,'严坚东','330282033556','2010-08-25 00:00:00',0,'','63480830','','','慈溪市周巷严坚东摩托车维修店','维修(摩托车修理)','   ',105,'cxszxyjdmtcwxd',0,0,1,1),
 (610,'浒山镇长春村','','2011-01-26 14:15:34','2006-06-06 00:00:00','2011-01-26 14:15:34','2009-07-01 00:00:00','',90,'龚建军','330282033557','2010-08-25 00:00:00',0,'','','13645841696','','慈溪市浒山小军摩托修理店','维修(二类摩托车维修)','   ',105,'cxszsxjmtxld',0,0,1,1),
 (611,'慈溪市横河镇彭桥村','','2011-01-26 14:15:34','2010-08-17 00:00:00','2011-01-26 14:15:34','2012-12-31 00:00:00','',90,'黄维辉','330282033559','2010-08-25 00:00:00',0,'','63832537','','','慈溪市横河镇维辉摩托修理店','维修(摩托车修理)','   ',105,'cxshhzwhmtxld',0,0,1,1),
 (612,'慈溪市胜山镇一灶村','','2011-01-26 14:15:34','2006-06-06 00:00:00','2011-01-26 14:15:34','2009-07-01 00:00:00','',90,'徐海杰','330282033560','2010-08-25 00:00:00',0,'','','13958254981','','慈溪市胜山海杰摩托车维修店','维修(二类摩托车维修)','   ',105,'cxssshjmtcwxd',0,0,1,1),
 (613,'慈溪市浒山阳明广场环城西路199号','','2011-01-26 14:15:34','2005-07-01 00:00:00','2011-01-26 14:15:34','2008-07-01 00:00:00','',90,'李文波','330282033561','2010-08-25 00:00:00',0,'','63893774','','','慈溪市浒山镇文波摩托车维修店','维修(摩托车修理)','   ',105,'cxszszwbmtcwxd',0,0,1,1),
 (614,'慈溪市崇寿镇永清北路269.271号','','2011-01-26 14:15:34','2010-08-11 00:00:00','2011-01-26 14:15:34','2013-06-30 00:00:00','',90,'胡烈锋','330282033562','2010-08-11 00:00:00',0,'','13600612048','','','慈溪市崇寿镇央娣汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxscszyzqcxld',0,0,1,1),
 (615,'慈溪市逍林镇新园村','','2011-01-26 14:15:34','2006-06-06 00:00:00','2011-01-26 14:15:34','2009-07-01 00:00:00','',90,'徐孟夫','330282033563','2010-08-11 00:00:00',0,'','','13008965057','','慈溪市逍林梦飞摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszlmfmtcxld',0,0,1,1),
 (616,'慈溪市坎墩东潮村','','2011-01-26 14:15:34','2010-08-17 00:00:00','2011-01-26 14:15:34','2011-06-30 00:00:00','',90,'叶群雷','330282033564','2010-08-25 00:00:00',0,'','63281529','','','慈溪市坎墩群雷摩托车配件店','维修(摩托车修理)','   ',105,'cxskdqlmtcpjd',0,0,1,1),
 (617,'慈溪市崇寿镇六塘','','2011-01-26 14:15:34','2005-07-01 00:00:00','2011-01-26 14:15:34','2008-07-01 00:00:00','',90,'陆卫良','330282033565','2010-08-25 00:00:00',0,'','63298785','','','慈溪市崇寿双轮摩托车修理店','维修(摩托车修理)','   ',105,'cxscsslmtcxld',0,0,1,1),
 (618,'慈溪市崇寿六塘村','','2011-01-26 14:15:34','2002-07-01 00:00:00','2011-01-26 14:15:34','2005-07-01 00:00:00','',90,'胡国明','330282033566','2010-08-25 00:00:00',0,'','63297845','','','慈溪市崇寿国明摩托车维修店','维修(摩托车修理)','   ',105,'cxscsgmmtcwxd',0,0,1,1),
 (619,'慈溪市崇寿镇同兴村','','2011-01-26 14:15:34','2005-07-01 00:00:00','2011-01-26 14:15:34','2008-07-01 00:00:00','',90,'许建军','330282033567','2010-08-25 00:00:00',0,'','63292225','13567820215','','慈溪市崇寿建军摩托车维修店','维修(摩托车修理)','   ',105,'cxscsjjmtcwxd',0,0,1,1),
 (620,'慈溪市浒山镇金一路193号','','2011-01-26 14:15:34','2010-08-17 00:00:00','2011-01-26 14:15:34','2005-07-01 00:00:00','',90,'陈建锋','330282033569','2010-08-17 00:00:00',0,'','63819717','','','慈溪市浒山金一摩托车维修店','维修(摩托车修理)','   ',105,'cxszsjymtcwxd',0,0,1,1),
 (621,'慈溪市浒山后二房村','','2011-01-26 14:15:34','2002-07-01 00:00:00','2011-01-26 14:15:34','2005-07-01 00:00:00','',90,'黄奇男','330282033570','2010-08-17 00:00:00',0,'','','139679593','','慈溪市浒山奇男摩托车维修店','维修(摩托车修理)','   ',105,'cxszsqnmtcwxd',0,0,1,1),
 (622,'慈溪市匡堰镇高家村','','2011-01-26 14:15:34','2006-09-30 00:00:00','2011-01-26 14:15:34','2009-07-01 00:00:00','',90,'罗建强','330282033571','2010-08-17 00:00:00',0,'','13958297732','13958297732','','慈溪市匡堰强仔摩托修理店','维修(二类摩托车维修)','   ',105,'cxskyqzmtxld',0,0,1,1),
 (623,'慈溪市宗汉街道周塘东村','','2011-01-26 14:15:35','2010-07-06 00:00:00','2011-01-26 14:15:35','2013-07-06 00:00:00','',90,'陈锋','330282033572','2010-07-09 00:00:00',0,'13738816602','','','','慈溪市宗汉起点汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhqdqcxld',0,0,1,1),
 (624,'慈溪市浒山三北大街','','2011-01-26 14:15:35','2005-07-01 00:00:00','2011-01-26 14:15:35','2008-07-01 00:00:00','',90,'童建锋','330282033573','2010-07-09 00:00:00',0,'','','13967831898','','慈溪市浒山建锋摩托车维修店','维修(摩托车修理)','   ',105,'cxszsjfmtcwxd',0,0,1,1),
 (625,'慈溪市周巷东溜场村','','2011-01-26 14:15:35','2002-07-01 00:00:00','2011-01-26 14:15:35','2005-07-01 00:00:00','',90,'陈洪君','330282033574','2010-07-09 00:00:00',0,'','','13958374125','','慈溪市周巷洪君摩托车维修店','维修(摩托车修理)','   ',105,'cxszxhjmtcwxd',0,0,1,1),
 (626,'慈溪市周巷镇大治桥南','','2011-01-26 14:15:35','2008-11-19 00:00:00','2011-01-26 14:15:35','2011-06-30 00:00:00','',90,'陈伟民','330282033575','2010-07-09 00:00:00',0,'','13606749781','63307760','','慈溪市周巷伟民摩托车维修店','维修(摩托车修理)','   ',105,'cxszxwmmtcwxd',0,0,1,1),
 (627,'慈溪市周巷精忠车站旁','','2011-01-26 14:15:35','2005-07-01 00:00:00','2011-01-26 14:15:35','2008-07-01 00:00:00','',90,'叶明杰','330282033576','2010-07-09 00:00:00',0,'','63310677','','','慈溪市周巷明杰摩托车维修店','维修(摩托车修理)','   ',105,'cxszxmjmtcwxd',0,0,1,1),
 (628,'慈溪市古塘街道西界南横弄22号','','2011-01-26 14:15:35','2010-08-13 00:00:00','2011-01-26 14:15:35','2013-06-30 00:00:00','',90,'沈爱德','330282033577','2010-08-13 00:00:00',0,'13003775028','','','','慈溪市古塘沈爱德摩托修理店','机动车维修：摩托车维修。','',105,'cxsgtsadmtxld',0,0,1,1),
 (629,'周巷镇周邵村(邵家路)','','2011-01-26 14:15:35','2008-07-01 00:00:00','2011-01-26 14:15:35','2011-06-30 00:00:00','',90,'吴建权','330282033578','2009-11-05 00:00:00',0,'','','63492255','','慈溪市小安兴隆摩托车修理店','维修(摩托车修理)','',105,'cxsxaxlmtcxld',0,0,1,1),
 (630,'慈溪市周巷镇兴业北路','','2011-01-26 14:15:35','2002-07-01 00:00:00','2011-01-26 14:15:35','2005-07-01 00:00:00','',90,'熊岳明','330282033579','2009-11-05 00:00:00',0,'13008995416','63307140','','','慈溪市周巷宏大摩托车维修店','维修(摩托车修理)','   ',105,'cxszxhdmtcwxd',0,0,1,1),
 (631,'慈溪市小安中学旁','','2011-01-26 14:15:35','2005-07-01 00:00:00','2011-01-26 14:15:35','2008-07-01 00:00:00','',90,'严孝权','330282033580','2009-11-05 00:00:00',0,'','63493277','','','慈溪市小安权摩托维修店','维修(摩托车修理)','   ',105,'cxsxaqmtwxd',0,0,1,1),
 (632,'慈溪市横河镇龙泉村育才路','','2011-01-26 14:15:35','2010-08-13 00:00:00','2011-01-26 14:15:35','2013-06-30 00:00:00','',90,'段宗杰','330282033581','2010-08-13 00:00:00',0,'13386622900','','','','慈溪市横河阿飞汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxshhafqcxld',0,0,1,1),
 (633,'慈溪市小安乡周西公路边','','2011-01-26 14:15:35','2002-07-01 00:00:00','2011-01-26 14:15:35','2005-07-01 00:00:00','',90,'何志见','330282033583','2010-08-13 00:00:00',0,'','63492225','','','慈溪市小安志见摩托车维修店','维修(摩托车修理)','   ',105,'cxsxazjmtcwxd',0,0,1,1),
 (634,'慈溪市小安乡劳家埭','','2011-01-26 14:15:35','2002-07-01 00:00:00','2011-01-26 14:15:35','2005-07-01 00:00:00','',90,'劳建平','330282033584','2010-08-13 00:00:00',0,'13065829489','','','','慈溪市劳家埭建平摩托车维修店','维修(摩托车修理)','   ',105,'cxsljzjpmtcwxd',0,0,1,1),
 (635,'慈溪周巷南周巷32号','','2011-01-26 14:15:35','2002-07-01 00:00:00','2011-01-26 14:15:35','2005-07-01 00:00:00','',90,'毛炎明','330282033586','2010-08-13 00:00:00',0,'','63304135','','','慈溪市周巷炎明摩托维修店','维修(摩托车修理)','   ',105,'cxszxymmtwxd',0,0,1,1),
 (636,'慈溪市小安乡小安街村','','2011-01-26 14:15:35','2008-10-21 00:00:00','2011-01-26 14:15:35','2011-06-30 00:00:00','',90,'姚安君','330282033587','2010-08-13 00:00:00',0,'','63490169','','','慈溪市小安安君摩托车维修店','维修(摩托车修理)','   ',105,'cxsxaajmtcwxd',0,0,1,1),
 (637,'慈溪市小安周陶村','','2011-01-26 14:15:36','2005-07-01 00:00:00','2011-01-26 14:15:36','2008-07-01 00:00:00','',90,'马志龙','330282033588','2010-08-13 00:00:00',0,'','13646673450','13646673450','','慈溪市杭州湾周陶摩托修理店','维修(摩托车修理)','   ',105,'cxshzwztmtxld',0,0,1,1),
 (638,'慈溪市杭州湾镇','','2011-01-26 14:15:36','2005-07-01 00:00:00','2011-01-26 14:15:36','2008-07-01 00:00:00','',90,'沈红正','330282033590','2010-08-13 00:00:00',0,'','63490954','','','慈溪市杭州湾镇红正摩托车维修店','维修(摩托车修理)','   ',105,'cxshzwzhzmtcwxd',0,0,1,1),
 (639,'慈溪市周巷镇公路边','','2011-01-26 14:15:36','2009-04-02 00:00:00','2011-01-26 14:15:36','2011-06-30 00:00:00','',90,'陈国银','330282033591','2009-04-02 00:00:00',0,'','63305364','13906621075','','慈溪市周巷国银摩托车维修店','维修(摩托车修理)','   ',105,'cxszxgymtcwxd',0,0,1,1),
 (640,'慈溪市长河镇','','2011-01-26 14:15:36','2008-09-26 00:00:00','2011-01-26 14:15:36','2011-06-30 00:00:00','',90,'干立军','330282033592','2009-04-02 00:00:00',0,'','63410948','13805817474','','慈溪市长河镇飞跃摩托车销售店','维修(摩托车修理)','   ',105,'cxschzfymtcxsd',0,0,1,1),
 (641,'慈溪市天元镇潭南村（新塘下）','','2011-01-26 14:15:36','2008-01-11 00:00:00','2011-01-26 14:15:36','2012-01-11 00:00:00','',90,'张春良','330282033594','2009-04-02 00:00:00',0,'','63458244','','','慈溪市天元啊三摩托车修理店','维修(二类摩托车维修)','   ',105,'cxstyasmtcxld',0,0,1,1),
 (642,'慈溪市范市人民路143号','','2011-01-26 14:15:36','2008-10-31 00:00:00','2011-01-26 14:15:36','2011-06-30 00:00:00','',90,'方何','330282033595','2009-09-04 00:00:00',0,'','63705843','13566064446','','慈溪市范市方何摩托车维修店','维修(摩托车修理)','   ',105,'cxsfsfhmtcwxd',0,0,1,1),
 (643,'慈溪市三北镇施公山村','','2011-01-26 14:15:36','2009-01-04 00:00:00','2011-01-26 14:15:36','2011-06-30 00:00:00','',90,'戎国聪','330282033596','2009-09-04 00:00:00',0,'','63732338','13968217728','','慈溪市三北国聪摩托车修理店','维修(摩托车修理)','   ',105,'cxssbgcmtcxld',0,0,1,1),
 (644,'慈溪市龙山镇邱王村门前山边','','2011-01-26 14:15:36','2005-07-01 00:00:00','2011-01-26 14:15:36','2008-07-01 00:00:00','',90,'胡忠杰','330282033597','2009-09-04 00:00:00',0,'','63785400','13185983188','','慈溪市龙山镇忠杰摩托车维修店','维修(摩托车修理)','   ',105,'cxslszzjmtcwxd',0,0,1,1),
 (645,'慈溪市龙山镇西村第三产业用房','','2011-01-26 14:15:36','2008-10-31 00:00:00','2011-01-26 14:15:36','2011-06-30 00:00:00','',90,'仇海明','330282033598','2009-09-04 00:00:00',0,'','63700306','','','慈溪市龙山镇海明摩托车农机维修店','维修(摩托车修理)','   ',105,'cxslszhmmtcnjwxd',0,0,1,1),
 (646,'慈溪市宗汉镇东水路54号','','2011-01-26 14:15:36','2006-10-30 00:00:00','2011-01-26 14:15:36','2009-07-01 00:00:00','',90,'叶金波','330282033599','2009-09-04 00:00:00',0,'','13968288402','13968288402','','慈溪市宗汉金波摩托修理店','维修(二类摩托车维修)','   ',105,'cxszhjbmtxld',0,0,1,1),
 (647,'周苍镇周邵村','','2011-01-26 14:15:37','2006-11-13 00:00:00','2011-01-26 14:15:37','2009-07-01 00:00:00','',90,'严小荣','330282033600','2009-09-04 00:00:00',0,'','63338016','13056744567','','慈溪市周巷孝荣摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszxxrmtcxld',0,0,1,1),
 (648,'慈溪市范市镇镇北路16号','','2011-01-26 14:15:37','2008-10-31 00:00:00','2011-01-26 14:15:37','2011-06-30 00:00:00','',90,'孙巍','330282033601','2009-03-11 00:00:00',0,'','63703706','','','慈溪市范市阿巍摩托维修店','维修(摩托车修理)','   ',105,'cxsfsawmtwxd',0,0,1,1),
 (649,'慈溪市龙山镇龙头场村','','2011-01-26 14:15:37','2008-10-31 00:00:00','2011-01-26 14:15:37','2011-06-30 00:00:00','',90,'周希汉','330282033602','2009-03-11 00:00:00',0,'','63787330','13008955512','','慈溪市龙山镇慈东摩托车修配店','维修(摩托车修理)','   ',105,'cxslszcdmtcxpd',0,0,1,1),
 (650,'慈溪市逍林镇宏跃村','','2011-01-26 14:15:37','2006-12-20 00:00:00','2011-01-26 14:15:37','2009-07-01 00:00:00','',90,'徐立峰','330282033605','2009-03-11 00:00:00',0,'','63517845','','','慈溪市逍林雪群摩托车修理店','维修(二类摩托车维修)','   ',105,'cxszlxqmtcxld',0,0,1,1),
 (651,'慈溪市庵东镇江南村','','2011-01-26 14:15:37','2008-08-29 00:00:00','2011-01-26 14:15:37','2011-06-30 00:00:00','',90,'丁建丰','330282033608','2009-03-11 00:00:00',0,'','63484666','13216742102','','慈溪市庵东建丰摩托车维修店','维修(摩托车修理)','   ',105,'cxszdjfmtcwxd',0,0,1,1),
 (652,'浒山镇青少年宫北路6号','','2011-01-26 14:15:37','2005-07-01 00:00:00','2011-01-26 14:15:37','2008-07-01 00:00:00','',90,'张定锋','330282033609','2009-03-11 00:00:00',0,'','13906744264','','','慈溪市浒山定锋摩托车修理店','维修(摩托车修理)','   ',105,'cxszsdfmtcxld',0,0,1,1),
 (653,'庵东镇庵余路','','2011-01-26 14:15:37','2008-11-19 00:00:00','2011-01-26 14:15:37','2011-06-30 00:00:00','',90,'陈张建','330282033613','2009-03-11 00:00:00',0,'','13003785336','','','慈溪市庵东张建摩托车修配店','维修(摩托车修理)','   ',105,'cxszdzjmtcxpd',0,0,1,1),
 (654,'观海卫镇鸣兴村','','2011-01-26 14:15:37','2008-10-22 00:00:00','2011-01-26 14:15:37','2011-06-30 00:00:00','',90,'施银炯','330282033614','2009-03-11 00:00:00',0,'','63672165','','','慈溪市鸣鹤阿炯摩托车修配店','维修(摩托车修理)','   ',105,'cxsmhajmtcxpd',0,0,1,1),
 (655,'掌起镇东埠头下街','','2011-01-26 14:15:37','2008-10-22 00:00:00','2011-01-26 14:15:37','2011-06-30 00:00:00','',90,'童兴忠','330282033618','2009-03-11 00:00:00',0,'','63772016','','','慈溪市掌起镇建忠摩托车修理店','维修(摩托车修理)','   ',105,'cxszqzjzmtcxld',0,0,1,1),
 (656,'慈溪市周巷镇云城村新周塘路152号','','2011-01-26 14:15:38','2010-09-20 00:00:00','2011-01-26 14:15:38','2013-06-30 00:00:00','',90,'叶佰金','330282033619','2010-09-20 00:00:00',0,'13989333373','','','','慈溪市周巷佰金汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszxbjqcxld',0,0,1,1),
 (657,'慈溪市掌起镇乐家村','','2011-01-26 14:15:38','2008-10-22 00:00:00','2011-01-26 14:15:38','2011-06-30 00:00:00','',90,'张宏裕','330282033621','2010-09-20 00:00:00',0,'','1386783548','','','慈溪市掌起镇宏裕摩托车修理店','维修(摩托车修理)','   ',105,'cxszqzhymtcxld',0,0,1,1),
 (658,'慈溪市古塘街道明州路411-415号','','2011-01-26 14:15:38','2010-09-25 00:00:00','2011-01-26 14:15:38','2013-06-30 00:00:00','',90,'胡育权','330282033622','2010-09-26 00:00:00',71,'15906513555','','','','慈溪市古塘阿权汽车美容服务部','机动车维修：三类机动车维修（供油系统维护及油品更换、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsgtaqqcmrfwb',0,0,1,1),
 (659,'慈溪市浒山北二环东路82号','','2011-01-26 14:15:38','2010-08-17 00:00:00','2011-01-26 14:15:38','2013-01-27 00:00:00','',90,'刘深义','330282033624','2010-08-25 00:00:00',0,'','','13819443625','','慈溪浒山刘深义摩托修理店','维修(二类摩托车维修)','   ',105,'cxzslsymtxld',0,0,1,1),
 (660,'慈溪市宗汉街道周塘东村宗兴路','','2011-01-26 14:15:38','2010-09-27 00:00:00','2011-01-26 14:15:38','2013-06-30 00:00:00','',90,'叶华波','330282033626','2010-09-27 00:00:00',0,'13857459248','','','','慈溪市宗汉飞燕汽车维修厂','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhfyqcwxc',0,0,1,1),
 (661,'慈溪市浒山街道天九街16号楼店17#','','2011-01-26 14:15:38','2010-08-17 00:00:00','2011-01-26 14:15:38','2013-06-30 00:00:00','',90,'胡迪锋','330282033627','2010-08-25 00:00:00',0,'','66310107','18958341600','','慈溪市浒山胡锋摩托修理店','维修(摩托车修理)','',105,'cxszshfmtxld',0,0,1,1),
 (662,'慈溪市观海镇双湖村','','2011-01-26 14:15:38','2010-11-08 00:00:00','2011-01-26 14:15:38','2013-06-30 00:00:00','',90,'张亚芳','330282033628','2010-11-08 00:00:00',0,'','','','','慈溪市观海卫路得汽车修理服务部','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsghwldqcxlfwb',0,0,1,1),
 (663,'慈溪市掌起镇329国道北侧陈家东侧','','2011-01-26 14:15:38','2010-11-08 00:00:00','2011-01-26 14:15:38','2013-06-30 00:00:00','',90,'陈维立','330282033630','2010-11-08 00:00:00',0,'13456197990','','','','慈溪市掌起立荣汽车维修服务部','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszqlrqcwxfwb',0,0,1,1),
 (664,'慈溪市浒山街道新江路659号','','2011-01-26 14:15:38','2010-12-20 00:00:00','2011-01-26 14:15:38','2013-06-30 00:00:00','',90,'曹阳','330282033632','2010-12-20 00:00:00',0,'13968223322','','','','慈溪市浒山美洁汽车装潢服务部','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszsmjqczzfwb',0,0,1,1),
 (665,'慈溪市观海卫镇城南村329国道旁(观海卫农村合作银行东侧)','高幼波','2011-01-26 14:15:38','2010-12-20 00:00:00','2011-01-26 14:15:38','2013-06-30 00:00:00','',90,'宓忠杰','330282033633','2010-12-20 00:00:00',0,'13958296889','','','','慈溪市观海卫奔跃汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsghwbyqcxld',0,0,1,1),
 (666,'慈溪市范市镇街河路34号','','2011-01-26 14:15:38','2005-07-01 00:00:00','2011-01-26 14:15:38','2008-07-01 00:00:00','',90,'杨文科','330282033636','2010-12-20 00:00:00',0,'','13605882597','','','慈溪市范市镇科鑫摩托车配件店','维修(摩托车修理)','   ',105,'cxsfszkzmtcpjd',0,0,1,1),
 (667,'新浦镇浦沿村','','2011-01-26 14:15:39','2010-08-17 00:00:00','2011-01-26 14:15:39','2011-06-30 00:00:00','',90,'毛如章','330282033637','2010-08-25 00:00:00',0,'','1396748705','63575973','','慈溪市新浦镇如章摩托车维修店','维修(摩托车修理)','   ',105,'cxsxpzrzmtcwxd',0,0,1,1),
 (668,'慈溪市逍林镇福合院村','','2011-01-26 14:15:39','2010-08-09 00:00:00','2011-01-26 14:15:39','2016-08-09 00:00:00','',90,'蔡大君','330282033639','2010-08-09 00:00:00',0,'','1396747235','','','慈溪市逍林大君摩托车维修店','维修(摩托车修理)','',105,'cxszldjmtcwxd',0,0,1,1),
 (669,'桥头五丰村','','2011-01-26 14:15:39','2005-07-01 00:00:00','2011-01-26 14:15:39','2008-07-01 00:00:00','',90,'毛焕迪','330282033641','2010-08-09 00:00:00',0,'','1385818437','','','慈溪市桥头镇焕迪摩托车修理店','维修(摩托车修理)','   ',105,'cxsqtzhdmtcxld',0,0,1,1),
 (670,'新浦胜东街','','2011-01-26 14:15:39','2010-08-17 00:00:00','2011-01-26 14:15:39','2011-06-30 00:00:00','',90,'岑建立','330282033642','2010-08-25 00:00:00',0,'','13606888479','','','慈溪市胜山镇立新摩托车修理店','维修(摩托车修理)','   ',105,'cxssszlxmtcxld',0,0,1,1),
 (671,'新浦镇浦沿村','','2011-01-26 14:15:39','2005-07-01 00:00:00','2011-01-26 14:15:39','2008-07-01 00:00:00','',90,'胡赫南','330282033645','2010-08-25 00:00:00',0,'','63599502','13906620630','','慈溪市新浦镇赫南摩托车修理店','维修(摩托车修理)','   ',105,'cxsxpzhnmtcxld',0,0,1,1),
 (672,'桥头镇后弄','','2011-01-26 14:15:39','2008-09-28 00:00:00','2011-01-26 14:15:39','2011-06-30 00:00:00','',90,'周欣捷','330282033647','2010-08-25 00:00:00',0,'','63559784','13757450030','','慈溪市桥头镇欣捷摩托车修理店','维修(摩托车修理)','   ',105,'cxsqtzxjmtcxld',0,0,1,1),
 (673,'慈溪市龙山镇东门外村','','2011-01-26 14:15:39','2008-10-31 00:00:00','2011-01-26 14:15:39','2011-06-30 00:00:00','',90,'邱雷琼','330282033650','2010-08-25 00:00:00',0,'','63782532','','','慈溪市龙山镇阿大摩托车维修店','维修(摩托车修理)','   ',105,'cxslszadmtcwxd',0,0,1,1),
 (674,'浒山新江路641','','2011-01-26 14:15:39','2005-07-01 00:00:00','2011-01-26 14:15:39','2008-07-01 00:00:00','',90,'陈建文','330282033652','2010-08-25 00:00:00',0,'','13906621267','63181267','','慈溪市浒山建文摩托车修理店','维修(摩托车修理)','   ',105,'cxszsjwmtcxld',0,0,1,1),
 (675,'横河镇梅川西路４３号','','2011-01-26 14:15:39','2010-08-17 00:00:00','2011-01-26 14:15:39','2011-06-30 00:00:00','',90,'杨仕卫','330282033657','2010-08-25 00:00:00',0,'','63266046','','','慈溪市横河镇仕卫摩托修配店','维修(摩托车修理)','   ',105,'cxshhzswmtxpd',0,0,1,1),
 (676,'横河镇梅川路口','','2011-01-26 14:15:39','2010-08-17 00:00:00','2011-01-26 14:15:39','2011-06-30 00:00:00','',90,'将纪权','330282033658','2010-08-25 00:00:00',0,'','63251720','13805816963','','慈溪市横河镇纪权摩托修理店','维修(摩托车修理)','   ',105,'cxshhzjqmtxld',0,0,1,1),
 (677,'横河镇龙南村','','2011-01-26 14:15:39','2010-08-17 00:00:00','2011-01-26 14:15:39','2011-06-30 00:00:00','',90,'孙杰波','330282033660','2010-08-25 00:00:00',0,'','63264833','','','慈溪市横河镇龙南摩托车修理店','维修(摩托车修理)','   ',105,'cxshhzlnmtcxld',0,0,1,1),
 (678,'周巷镇三江口村','','2011-01-26 14:15:39','2010-07-30 00:00:00','2011-01-26 14:15:39','2014-06-30 00:00:00','',90,'施金建','330282033661','2010-08-19 00:00:00',0,'','','','','慈溪市周巷阿建摩托车修理店','维修(一类摩托车维修)','',105,'cxszxajmtcxld',0,0,1,1),
 (679,'慈溪市周巷镇三江口村','','2011-01-26 14:15:40','2007-08-09 00:00:00','2011-01-26 14:15:40','2010-08-09 00:00:00','',90,'段怀仲','330282033663','2010-08-19 00:00:00',0,'','','13291952140','','慈溪市周巷镇阿平摩托车修理店','维修(摩托车修理)','   ',105,'cxszxzapmtcxld',0,0,1,1),
 (680,'横河镇石堰村','','2011-01-26 14:15:40','2005-07-01 00:00:00','2011-01-26 14:15:40','2008-07-01 00:00:00','',90,'徐孟波','330282033664','2010-08-19 00:00:00',0,'','13506843818','','','慈溪市横河镇阿波摩托车修配店','维修(摩托车修理)','   ',105,'cxshhzabmtcxpd',0,0,1,1),
 (681,'匡堰镇高家村','','2011-01-26 14:15:40','2007-03-19 00:00:00','2011-01-26 14:15:40','2010-03-19 00:00:00','',90,'蔡权','330282033665','2010-08-19 00:00:00',0,'','63535880','13396625785','','慈溪市匡堰镇蔡权摩托修配店','维修(摩托车修理)','   ',105,'cxskyzcqmtxpd',0,0,1,1),
 (682,'横河镇镇北路23号','','2011-01-26 14:15:40','2010-08-17 00:00:00','2011-01-26 14:15:40','2011-06-30 00:00:00','',90,'胡尔富','330282033666','2010-08-25 00:00:00',0,'','63252028','','','慈溪市横河镇尔富摩托车修理站','维修(摩托车修理)','   ',105,'cxshhzefmtcxlz',0,0,1,1),
 (683,'慈溪市横河镇孙家境村','','2011-01-26 14:15:40','2010-08-17 00:00:00','2011-01-26 14:15:40','2012-05-28 00:00:00','',90,'杨浙明','330282033667','2010-08-25 00:00:00',0,'','63266820','13056722584','','慈溪市横河镇阳阳摩托车修理店','维修(摩托车修理)','   ',105,'cxshhzyymtcxld',0,0,1,1),
 (684,'浒山镇慈甬路418','','2011-01-26 14:15:40','2002-07-01 00:00:00','2011-01-26 14:15:40','2005-07-01 00:00:00','',90,'韩晓庆','330282033669','2010-08-25 00:00:00',0,'','63015417','','','慈溪市浒山新城摩托车维修店','维修(摩托车修理)','   ',105,'cxszsxcmtcwxd',0,0,1,1),
 (685,'天元镇坛河村','','2011-01-26 14:15:40','2008-09-26 00:00:00','2011-01-26 14:15:40','2011-06-30 00:00:00','',90,'邵柯杰','330282033671','2010-08-25 00:00:00',0,'','13506784200','','','慈溪市天元柯杰摩托车维修店','维修(摩托车修理)','   ',105,'cxstykjmtcwxd',0,0,1,1),
 (686,'横河镇石堰村','','2011-01-26 14:15:40','2010-08-17 00:00:00','2011-01-26 14:15:40','2011-06-30 00:00:00','',90,'陈文权','330282033672','2010-08-25 00:00:00',0,'','63258000','','','慈溪市横河镇阿权摩托修配店','维修(摩托车修理)','   ',105,'cxshhzaqmtxpd',0,0,1,1),
 (687,'横河镇黄墙弄村（周家后）','','2011-01-26 14:15:40','2010-08-17 00:00:00','2011-01-26 14:15:40','2011-06-30 00:00:00','',90,'泮再峰','330282033673','2010-08-25 00:00:00',0,'','63833859','13805812252','','慈溪市横河镇阿峰摩托修理店','维修(摩托车修理)','   ',105,'cxshhzafmtxld',0,0,1,1),
 (688,'横河镇','','2011-01-26 14:15:40','2010-08-17 00:00:00','2011-01-26 14:15:40','2011-06-30 00:00:00','',90,'徐建军','330282033674','2010-08-25 00:00:00',0,'','13056928608','','','慈溪横河建军摩托车维修店','维修(摩托车修理)','   ',105,'cxhhjjmtcwxd',0,0,1,1),
 (689,'长河镇大牌头村','','2011-01-26 14:15:40','2002-07-01 00:00:00','2011-01-26 14:15:40','2005-07-01 00:00:00','',90,'鲁杰裕','330282033675','2010-08-25 00:00:00',0,'','','','','慈溪市长河杰裕摩托车维修店','维修(摩托车修理)','   ',105,'cxschjymtcwxd',0,0,1,1),
 (690,'长河镇南大路583','','2011-01-26 14:15:41','2008-09-26 00:00:00','2011-01-26 14:15:41','2011-06-30 00:00:00','',90,'周继业','330282033677','2010-08-25 00:00:00',0,'','3402918','','','慈溪长河晶鑫摩托车维修店','维修(摩托车修理)','   ',105,'cxchjzmtcwxd',0,0,1,1),
 (691,'慈溪市坎墩街道羊路头村','','2011-01-26 14:15:41','2010-08-17 00:00:00','2011-01-26 14:15:41','2011-06-30 00:00:00','',90,'施伟军','330282033678','2010-08-25 00:00:00',0,'','13003725300','','','慈溪市坎墩伟军摩托车维修店','维修(摩托车修理)','   ',105,'cxskdwjmtcwxd',0,0,1,1),
 (692,'长河公管对面','','2011-01-26 14:15:41','2008-09-08 00:00:00','2011-01-26 14:15:41','2011-06-30 00:00:00','',90,'卢香连','330282033679','2010-08-25 00:00:00',0,'','63457285','','','慈溪天元鸿达摩托车商店','维修(摩托车修理)','   ',105,'cxtyhdmtcsd',0,0,1,1),
 (693,'慈溪市宗汉街道新华村明州路','','2011-01-26 14:15:41','2010-08-23 00:00:00','2011-01-26 14:15:41','2013-06-30 00:00:00','',90,'窦春平','330282033681','2010-08-23 00:00:00',0,'','13777982712','','','慈溪市宗汉创烨汽车维护店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhczqcwhd',0,0,1,1),
 (694,'天元镇新塘下村','','2011-01-26 14:15:41','2005-07-01 00:00:00','2011-01-26 14:15:41','2008-07-01 00:00:00','',90,'张春良','330282033684','2010-08-23 00:00:00',0,'','63458244','13505845799','','慈溪市天元镇新塘下摩托车维修店','维修(摩托车修理)','   ',105,'cxstyzxtxmtcwxd',0,0,1,1),
 (695,'庵东镇桥南村','','2011-01-26 14:15:41','2008-06-16 00:00:00','2011-01-26 14:15:41','2011-06-15 00:00:00','',90,'丁岳清','330282033685','2010-08-23 00:00:00',0,'','63481185','13606746870','','慈溪市庵东镇岳清摩托车修理店','维修(摩托车修理)','   ',105,'cxszdzyqmtcxld',0,0,1,1),
 (696,'崇寿镇四灶浦村七塘','','2011-01-26 14:15:41','2010-08-11 00:00:00','2011-01-26 14:15:41','2013-06-30 00:00:00','',90,'王渭军','330282033689','2010-08-11 00:00:00',0,'15906534495','','','','慈溪市崇寿镇佳莹洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxscszjyxcc',0,0,1,1),
 (697,'慈溪市周巷镇驿亭村','','2011-01-26 14:15:41','2002-07-01 00:00:00','2011-01-26 14:15:41','2005-07-01 00:00:00','',90,'郑叶龙','330282033691','2010-08-11 00:00:00',0,'','63310725','','','慈溪市周巷镇叶龙摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzylmtcwxd',0,0,1,1),
 (698,'长河镇花坛东坛北路','','2011-01-26 14:15:41','2008-09-01 00:00:00','2011-01-26 14:15:41','2011-06-30 00:00:00','',90,'钱焕堂','330282033692','2010-08-11 00:00:00',0,'','63450308','','','慈溪市长河镇焕堂摩托配件商店','维修(摩托车修理)','   ',105,'cxschzhtmtpjsd',0,0,1,1),
 (699,'慈溪市浒山白沙慈甬路991-993号','','2011-01-26 14:15:41','2002-07-01 00:00:00','2011-01-26 14:15:41','2005-07-01 00:00:00','',90,'胡礼平','330282033693','2010-08-11 00:00:00',0,'','1396746030','','','慈溪市浒山阿大摩托车维修店','维修(摩托车修理)','   ',105,'cxszsadmtcwxd',0,0,1,1),
 (700,'慈溪市宗汉街道百两村','','2011-01-26 14:15:41','2010-11-01 00:00:00','2011-01-26 14:15:41','2013-06-30 00:00:00','',90,'黄建佳','330282033694','2010-11-23 00:00:00',0,'黄建佳','13486046798','','','慈溪市宗汉黄佳汽车维修店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszhhjqcwxd',0,0,1,1),
 (701,'慈溪市庵东镇七塘公路','','2011-01-26 14:15:42','2009-05-13 00:00:00','2011-01-26 14:15:42','2011-06-30 00:00:00','',90,'裘丹波','330282033695','2009-05-13 00:00:00',0,'','63476261','','','慈溪市庵东镇丹波摩托车维修店','维修(摩托车修理)','   ',105,'cxszdzdbmtcwxd',0,0,1,1),
 (702,'慈溪市崇寿镇六塘东','','2011-01-26 14:15:42','2010-08-17 00:00:00','2011-01-26 14:15:42','2011-06-30 00:00:00','',90,'鲁企钿','330282033696','2010-08-25 00:00:00',0,'','63299960','','','慈溪市崇寿企钿摩托车维修店','维修(摩托车修理)','   ',105,'cxscsqzmtcwxd',0,0,1,1),
 (703,'慈溪市胜山镇胜山头村马潭南路219-222号','','2011-01-26 14:15:42','2010-12-20 00:00:00','2011-01-26 14:15:42','2013-06-30 00:00:00','',90,'范东海','330282033697','2010-12-20 00:00:00',0,'13586643877','','','','慈溪市胜山凹凸洗车店','机动车维修：三类机动车维修（车身清洁维护、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsssatxcd',0,0,1,1),
 (704,'慈溪市坎墩街道兴镇街299号','','2011-01-26 14:15:42','2010-12-23 00:00:00','2011-01-26 14:15:42','2013-06-30 00:00:00','',90,'刘桂舟','330282033698','2010-12-24 00:00:00',0,'1590298159','','','','慈溪市坎墩路邦汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxskdlbqcxld',0,0,1,1),
 (705,'慈溪市横河镇王家畈','','2011-01-26 14:15:42','2010-08-17 00:00:00','2011-01-26 14:15:42','2011-06-30 00:00:00','',90,'王洪峰','330282033699','2010-08-25 00:00:00',0,'','63259496','13567811680','','慈溪市横河镇洪峰摩托车维修店','维修(摩托车修理)','   ',105,'cxshhzhfmtcwxd',0,0,1,1),
 (706,'慈溪市浒山镇环城北路75号','','2011-01-26 14:15:42','2005-07-01 00:00:00','2011-01-26 14:15:42','2008-07-01 00:00:00','',90,'宋林杰','330282033700','2010-08-25 00:00:00',0,'','63816637','','','慈溪市浒山林杰摩托车维修店','维修(摩托车修理)','   ',105,'cxszsljmtcwxd',0,0,1,1),
 (707,'慈溪市浒山慈甬路488号','','2011-01-26 14:15:42','2010-12-06 00:00:00','2011-01-26 14:15:42','2013-06-30 00:00:00','',90,'徐锋杰','330282033701','2010-12-06 00:00:00',0,'','13957458801','','','慈溪市浒山阿杰汽车装潢店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszsajqczzd',0,0,1,1),
 (708,'慈溪市匡堰镇王家埭村','','2011-01-26 14:15:42','2008-03-28 00:00:00','2011-01-26 14:15:42','2011-03-28 00:00:00','',90,'陈立柯','330282033705','2010-12-06 00:00:00',71,'','','','','慈溪市匡堰欧德洗车场','维修(车身清洁维护)','   ',105,'cxskyodxcc',0,0,1,1),
 (709,'慈溪市坎墩街道坎西村戴家弄15号','','2011-01-26 14:15:42','2010-08-11 00:00:00','2011-01-26 14:15:42','2012-04-24 00:00:00','',90,'卢茂泽','330282033706','2010-08-11 00:00:00',71,'','13968295675','13968295675','','慈溪市坎墩冠直车辆清洗店','维修(三类机动车维修:车身清洁维护)','   ',105,'cxskdgzclqxd',0,0,1,1),
 (710,'慈溪市浒山新城大道东侧上周塘村','','2011-01-26 14:15:42','2010-08-11 00:00:00','2011-01-26 14:15:42','2011-04-28 00:00:00','',90,'李斌','330282033707','2010-08-11 00:00:00',71,'','','','','慈溪市浒山阿斌洗车场','维修(车身清洁维护)','   ',105,'cxszsabxcc',0,0,1,1),
 (711,'慈溪市浒山南二环线228号','','2011-01-26 14:15:42','2005-07-01 00:00:00','2011-01-26 14:15:42','2008-07-01 00:00:00','',90,'黄佰华','330282033708','2010-08-11 00:00:00',0,'','13857804209','','','慈溪市浒山正大摩托车修理店','维修(摩托车修理)','   ',105,'cxszszdmtcxld',0,0,1,1),
 (712,'慈溪市观海卫镇鸣兴村鸣兴西路115号','','2011-01-26 14:15:42','2010-09-15 00:00:00','2011-01-26 14:15:42','2012-06-30 00:00:00','张海平',90,'罗酉田','330282033710','2010-09-15 00:00:00',0,'15158322300','','','','慈溪市观海卫速达汽车修理厂','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxsghwsdqcxlc',0,0,1,1),
 (713,'慈溪市浒山镇北二环中路71号','','2011-01-26 14:15:42','2005-07-01 00:00:00','2011-01-26 14:15:42','2008-07-01 00:00:00','',90,'周学鹏','330282033711','2010-09-15 00:00:00',0,'','13586929858','','','慈溪市浒山学鹂摩托车维修店','维修(摩托车修理)','   ',105,'cxszsxzmtcwxd',0,0,1,1),
 (714,'慈溪市浒山金山路193号','','2011-01-26 14:15:42','2002-07-01 00:00:00','2011-01-26 14:15:42','2005-07-01 00:00:00','',90,'沈亚杰','330282033712','2010-09-15 00:00:00',0,'','63811183','','','慈溪市浒山亚杰摩托车维修店','维修(摩托车修理)','   ',105,'cxszsyjmtcwxd',0,0,1,1),
 (715,'慈溪市崇寿殿北村','','2011-01-26 14:15:43','2005-07-01 00:00:00','2011-01-26 14:15:43','2008-07-01 00:00:00','',90,'胡国银','330282033713','2010-09-15 00:00:00',0,'','63290521','13968269512','','慈溪市崇寿国银摩托车维修店','维修(摩托车修理)','   ',105,'cxscsgymtcwxd',0,0,1,1),
 (716,'慈溪市崇寿镇殿北村','','2011-01-26 14:15:43','2008-08-29 00:00:00','2011-01-26 14:15:43','2011-06-30 00:00:00','',90,'胡国芳','330282033714','2010-09-15 00:00:00',0,'','63299125','','','慈溪市崇寿镇国芳摩托车修理店','维修(摩托车修理)','   ',105,'cxscszgfmtcxld',0,0,1,1),
 (717,'慈溪市崇寿镇六塘村','','2011-01-26 14:15:43','2005-07-01 00:00:00','2011-01-26 14:15:43','2008-07-01 00:00:00','',90,'郑建江','330282033715','2010-09-15 00:00:00',0,'','63297574','','','慈溪市崇寿建江摩托车维修店','维修(摩托车修理)','   ',105,'cxscsjjmtcwxd',0,0,1,1),
 (718,'慈溪市庵东镇七二三大街','','2011-01-26 14:15:43','2008-08-15 00:00:00','2011-01-26 14:15:43','2012-06-30 00:00:00','',90,'王炳洪','330282033716','2010-09-15 00:00:00',0,'','63474180','13505841030','','慈溪市庵东镇炳洪摩托修理店','维修(摩托车修理)','   ',105,'cxszdzbhmtxld',0,0,1,1),
 (719,'慈溪市胜山镇马潭村','','2011-01-26 14:15:43','2010-08-17 00:00:00','2011-01-26 14:15:43','2011-06-30 00:00:00','',90,'张方军','330282033718','2010-08-25 00:00:00',0,'','13706742972','','','慈溪市胜山镇方军摩托车维修店','维修(摩托车修理)','   ',105,'cxssszfjmtcwxd',0,0,1,1),
 (720,'慈溪市胜山镇陈丁村','','2011-01-26 14:15:43','2010-08-17 00:00:00','2011-01-26 14:15:43','2011-06-30 00:00:00','',90,'陈利法','330282033720','2010-08-25 00:00:00',0,'','13008987309','','','慈溪市胜山镇利法摩托车修理店','维修(摩托车修理)','   ',105,'cxssszlfmtcxld',0,0,1,1),
 (721,'慈溪市周巷镇云城界塘村','','2011-01-26 14:15:43','2005-07-01 00:00:00','2011-01-26 14:15:43','2008-07-01 00:00:00','',90,'徐建丰','330282033723','2010-08-25 00:00:00',0,'','63326335','','','慈溪市周巷镇建丰摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzjfmtcwxd',0,0,1,1),
 (722,'慈溪市浒山镇金一路443号','','2011-01-26 14:15:43','2002-07-01 00:00:00','2011-01-26 14:15:43','2005-07-01 00:00:00','',90,'叶华良','330282033724','2010-08-25 00:00:00',0,'','63108097','','','慈溪市浒山镇华良摩托车维修店','维修(摩托车修理)','   ',105,'cxszszhlmtcwxd',0,0,1,1),
 (723,'慈溪市周巷镇环城南路638','','2011-01-26 14:15:43','2008-10-21 00:00:00','2011-01-26 14:15:43','2011-06-30 00:00:00','',90,'毛炎正','330282033725','2010-08-25 00:00:00',0,'','63304155','','','慈溪市周巷镇炎正摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzyzmtcwxd',0,0,1,1),
 (724,'慈溪市庵东镇西二老堰头七塘公路','','2011-01-26 14:15:43','2008-08-22 00:00:00','2011-01-26 14:15:43','2011-06-30 00:00:00','',90,'俞月坚','330282033726','2010-08-25 00:00:00',0,'','63482867','13216742968','','慈溪市庵东镇月坚摩托车维修店','维修(摩托车修理)','   ',105,'cxszdzyjmtcwxd',0,0,1,1),
 (725,'慈溪市浒山镇下房路133号','','2011-01-26 14:15:44','2005-04-01 00:00:00','2011-01-26 14:15:44','2008-07-01 00:00:00','',90,'陈应翔','330282033728','2010-08-25 00:00:00',0,'','63803947','','','慈溪市浒山镇应翔摩托车维修店','维修(摩托车修理)','   ',105,'cxszszyxmtcwxd',0,0,1,1),
 (726,'慈溪市坎墩东孙方村','','2011-01-26 14:15:44','2005-07-01 00:00:00','2011-01-26 14:15:44','2008-07-01 00:00:00','',90,'叶建达','330282033730','2010-08-25 00:00:00',0,'','63283731','','','慈溪市坎墩建达摩托车维修店','维修(摩托车修理)','   ',105,'cxskdjdmtcwxd',0,0,1,1),
 (727,'慈溪市浒山镇武陵桥村','','2011-01-26 14:15:44','2010-08-17 00:00:00','2011-01-26 14:15:44','2011-06-30 00:00:00','',90,'史纪成','330282033731','2010-08-25 00:00:00',0,'','13906745973','','','慈溪市浒山纪成摩托维修店','维修(摩托车修理)','   ',105,'cxszsjcmtwxd',0,0,1,1),
 (728,'慈溪市浒山镇寺山路278号','','2011-01-26 14:15:44','2002-07-01 00:00:00','2011-01-26 14:15:44','2005-07-01 00:00:00','',90,'胡建东','330282033732','2010-08-25 00:00:00',0,'','63102407','','','慈溪市浒山镇建东摩托车维修店','维修(摩托车修理)','   ',105,'cxszszjdmtcwxd',0,0,1,1),
 (729,'慈溪市崇寿镇相公殿东站','','2011-01-26 14:15:44','2005-07-01 00:00:00','2011-01-26 14:15:44','2008-07-01 00:00:00','',90,'陈委委','330282033733','2010-08-25 00:00:00',0,'','63298615','','','慈溪市崇寿镇委委摩托车维修店','维修(摩托车修理)','   ',105,'cxscszwwmtcwxd',0,0,1,1),
 (730,'慈溪市杭州湾南村','','2011-01-26 14:15:44','2005-07-01 00:00:00','2011-01-26 14:15:44','2008-07-01 00:00:00','',90,'谢国庆','330282033734','2010-08-25 00:00:00',0,'','13967880720','63494680','','慈溪市杭州湾国庆摩托车维修店','维修(摩托车修理)','   ',105,'cxshzwgqmtcwxd',0,0,1,1),
 (731,'慈溪市浒山南二环线中路乌山综合1号楼','','2011-01-26 14:15:44','2010-08-17 00:00:00','2011-01-26 14:15:44','2012-02-27 00:00:00','',90,'陈侠锋','330282033735','2010-08-25 00:00:00',0,'','13806643815','66326620','','慈溪市浒山侠锋摩托车修理店','维修(摩托车修理)','   ',105,'cxszsxfmtcxld',0,0,1,1),
 (732,'慈溪市长河镇大云村','','2011-01-26 14:15:44','2008-09-01 00:00:00','2011-01-26 14:15:44','2011-06-30 00:00:00','',90,'丁吴勇','330282033736','2010-08-25 00:00:00',0,'','63413561','13968227158','','慈溪市长河镇丁勇摩托车维修店','维修(摩托车修理)','   ',105,'cxschzdymtcwxd',0,0,1,1),
 (733,'慈溪市新浦镇浦沿村','','2011-01-26 14:15:44','2010-08-17 00:00:00','2011-01-26 14:15:44','2011-06-30 00:00:00','',90,'胡峰','330282033737','2010-08-25 00:00:00',0,'','13906621714','63891714','','慈溪市新浦镇胡峰摩托车维修店','维修(摩托车修理)','   ',105,'cxsxpzhfmtcwxd',0,0,1,1),
 (734,'慈溪市崇寿镇大袁家村','','2011-01-26 14:15:44','2008-12-29 00:00:00','2011-01-26 14:15:44','2011-06-30 00:00:00','',90,'马杰','330282033739','2010-08-25 00:00:00',0,'','63296335','','','慈溪市崇寿镇阿杰摩托车修理店','维修(摩托车修理)','   ',105,'cxscszajmtcxld',0,0,1,1),
 (735,'慈溪市掌起镇叶家村叶东路89号','','2011-01-26 14:15:44','2008-04-01 00:00:00','2011-01-26 14:15:44','2012-04-01 00:00:00','',90,'陈辉','330282033740','2010-08-25 00:00:00',71,'','13336878689','','','慈溪市掌起天朗汽车修理店','维修(三类机动车维修:车身维修、涂漆)','   ',105,'cxszqtlqcxld',0,0,1,1),
 (736,'慈溪市浒山镇车站路','','2011-01-26 14:15:44','2010-08-11 00:00:00','2011-01-26 14:15:44','2012-05-08 00:00:00','',90,'张荣华','330282033741','2010-08-24 00:00:00',71,'','13958232588','13958232588','','慈溪浒山阿荣汽车维修店','维修(三类机动车维修:车身维修、涂漆)','   ',105,'cxzsarqcwxd',0,0,1,1),
 (737,'慈溪市掌起镇周家段村','','2011-01-26 14:15:45','2010-08-11 00:00:00','2011-01-26 14:15:45','2012-05-16 00:00:00','',90,'王凯','330282033742','2010-08-11 00:00:00',71,'','13858228351','13858228351','','慈溪市掌起凯嵩汽车修理店','维修(三类机动车维修:涂漆)','   ',105,'cxszqkzqcxld',0,0,1,1),
 (738,'慈溪市三北大街少年宫东侧','','2011-01-26 14:15:45','2005-07-01 00:00:00','2011-01-26 14:15:45','2008-07-01 00:00:00','',90,'岑红军','330282033743','2010-08-11 00:00:00',0,'','66364575','','','慈溪市浒山红军摩托车维修店','维修(摩托车修理)','   ',105,'cxszshjmtcwxd',0,0,1,1),
 (739,'慈溪市胜山镇一灶村','','2011-01-26 14:15:45','2010-08-17 00:00:00','2011-01-26 14:15:45','2011-06-30 00:00:00','',90,'岑岳水','330282033744','2010-08-25 00:00:00',0,'','13606889584','','','慈溪市胜山镇岳水摩托车修理店','维修(摩托车修理)','   ',105,'cxssszysmtcxld',0,0,1,1),
 (740,'慈溪市周巷镇兴业北路588号','','2011-01-26 14:15:45','2010-08-23 00:00:00','2011-01-26 14:15:45','2011-06-30 00:00:00','',90,'茅炯萍','330282033745','2010-08-23 00:00:00',0,'','','','','慈溪市周巷唯旺汽车装潢美容店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszxwwqczzmrd',0,0,1,1),
 (741,'慈溪市范市镇人民北路','','2011-01-26 14:15:45','2008-06-04 00:00:00','2011-01-26 14:15:45','2012-06-04 00:00:00','',90,'范俊英','330282033746','2010-08-23 00:00:00',71,'','13081991922','','','慈溪市范市祥泰汽车装潢店','维修(三类机动车维修:车身维修、涂漆;车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxsfsxtqczzd',0,0,1,1),
 (742,'慈溪市坎墩镇四塘南村','','2011-01-26 14:15:45','2003-05-23 00:00:00','2011-01-26 14:15:45','2006-05-23 00:00:00','',90,'孙岳飞','330282033747','2010-08-23 00:00:00',0,'','63288911','','','慈溪市坎墩镇岳飞摩托车维修店','维修(摩托车修理)','   ',105,'cxskdzyfmtcwxd',0,0,1,1),
 (743,'慈溪市龙山镇西门外村','','2011-01-26 14:15:45','2008-10-31 00:00:00','2011-01-26 14:15:45','2011-06-30 00:00:00','',90,'潘学祥','330282033748','2010-08-23 00:00:00',0,'','63783667','13003778568','','慈溪市龙山镇学祥摩托车维修店','维修(摩托车修理)','   ',105,'cxslszxxmtcwxd',0,0,1,1),
 (744,'慈溪市新浦镇六甲村','','2011-01-26 14:15:46','2010-08-17 00:00:00','2011-01-26 14:15:46','2011-06-30 00:00:00','',90,'龚浩孟','330282033749','2010-08-25 00:00:00',0,'','63582788','13003700820','','慈溪市新浦镇浩孟摩托车修理店','维修(摩托车修理)','   ',105,'cxsxpzhmmtcxld',0,0,1,1),
 (745,'慈溪市周巷镇开发路567号','','2011-01-26 14:15:46','2010-08-23 00:00:00','2011-01-26 14:15:46','2011-06-30 00:00:00','',90,'宾电','330282033750','2010-08-23 00:00:00',0,'','13082900619','','','慈溪市周巷宾电汽车维修店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszxbdqcwxd',0,0,1,1),
 (746,'慈溪市横河镇东上河村上三房','','2011-01-26 14:15:46','2010-08-11 00:00:00','2011-01-26 14:15:46','2011-12-10 00:00:00','',90,'郝传华','330282033751','2010-08-11 00:00:00',71,'','66350188','','','慈溪市横河阿华汽车维修店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxshhahqcwxd',0,0,1,1),
 (747,'慈溪市新浦镇东三汽车站东','','2011-01-26 14:15:46','2008-09-12 00:00:00','2011-01-26 14:15:46','2011-06-30 00:00:00','',90,'张方明','330282033752','2010-08-11 00:00:00',0,'','63587196','','','慈溪市新浦镇方明摩托车修理店','维修(摩托车修理)','   ',105,'cxsxpzfmmtcxld',0,0,1,1),
 (748,'慈溪市庵东七塘公路西二老堰头','','2011-01-26 14:15:46','2009-03-17 00:00:00','2011-01-26 14:15:46','2011-06-30 00:00:00','',90,'陈晓明','330282033753','2009-03-17 00:00:00',0,'','63475737','13216743805','','慈溪市庵东镇晓明摩托车维修店','维修(摩托车修理)','   ',105,'cxszdzxmmtcwxd',0,0,1,1),
 (749,'慈溪市慈甬路228-234号','','2011-01-26 14:15:46','2002-07-01 00:00:00','2011-01-26 14:15:46','2005-07-01 00:00:00','',90,'黄淑群','330282033754','2009-03-17 00:00:00',0,'','63015686','','','慈溪市浒山镇隆欣摩托车维修店','维修(摩托车修理)','   ',105,'cxszszlxmtcwxd',0,0,1,1),
 (750,'慈溪市古塘街道金桥路330号','','2011-01-26 14:15:46','2010-08-11 00:00:00','2011-01-26 14:15:46','2011-12-17 00:00:00','',90,'蔡军','330282033755','2010-08-11 00:00:00',71,'','13567815514','13567815514','','慈溪市古塘金桥汽车修理厂','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxsgtjqqcxlc',0,0,1,1),
 (751,'慈溪市周巷镇万寿村','','2011-01-26 14:15:46','2005-07-01 00:00:00','2011-01-26 14:15:46','2008-07-01 00:00:00','',90,'陈吉方','330282033757','2010-08-11 00:00:00',0,'','63310924','13858324293','','慈溪市周巷镇吉方摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzjfmtcwxd',0,0,1,1),
 (752,'慈溪市浒山镇群丰村','','2011-01-26 14:15:46','2003-05-23 00:00:00','2011-01-26 14:15:46','2006-05-23 00:00:00','',90,'励沛方','330282033758','2010-08-11 00:00:00',0,'','62830316','','','慈溪市浒山镇沛方摩托车维修店','维修(摩托车修理)','   ',105,'cxszszpfmtcwxd',0,0,1,1),
 (753,'慈溪市浒山镇新江路南端施山村楼','','2011-01-26 14:15:46','2002-07-01 00:00:00','2011-01-26 14:15:46','2005-07-01 00:00:00','',90,'施国权','330282033759','2010-08-11 00:00:00',0,'','13606743009','','','慈溪市浒山镇国权摩托车维修店','维修(摩托车修理)','   ',105,'cxszszgqmtcwxd',0,0,1,1),
 (754,'慈溪市周镇界塘村','','2011-01-26 14:15:46','2002-07-01 00:00:00','2011-01-26 14:15:46','2005-07-01 00:00:00','',90,'徐军杰','330282033760','2010-08-11 00:00:00',0,'','63305997','','','慈溪市周巷镇军杰摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzjjmtcwxd',0,0,1,1),
 (755,'慈溪市庵东镇邮电西路592-594号','','2011-01-26 14:15:47','2008-02-26 00:00:00','2011-01-26 14:15:47','2012-02-26 00:00:00','',90,'应建利','330282033761','2010-08-11 00:00:00',71,'','13175914538','13175914538','','慈溪市庵东立军汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszdljqcxld',0,0,1,1),
 (756,'慈溪市周巷周塘村','','2011-01-26 14:15:47','2005-07-01 00:00:00','2011-01-26 14:15:47','2008-07-01 00:00:00','',90,'赵魏丰','330282033762','2010-08-11 00:00:00',0,'','63329422','','','慈溪市周巷镇魏丰摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzwfmtcwxd',0,0,1,1),
 (757,'慈溪市周巷镇进亭庵村','','2011-01-26 14:15:47','2002-07-01 00:00:00','2011-01-26 14:15:47','2005-07-01 00:00:00','',90,'杜大伟','330282033763','2010-08-11 00:00:00',0,'','13805813763','','','慈溪市周巷镇大伟摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzdwmtcwxd',0,0,1,1),
 (758,'浒山镇峙山路116号','','2011-01-26 14:15:47','2002-07-01 00:00:00','2011-01-26 14:15:47','2005-07-01 00:00:00','',90,'杨维杰','330282033764','2010-08-11 00:00:00',0,'','63817220','','','浒山镇维杰摩托维修店','维修(摩托车修理)','   ',105,'zszwjmtwxd',0,0,1,1);
INSERT INTO `enterprises` (`id`,`address`,`commission`,`createDate`,`dateBegin`,`editDate`,`dateEnd`,`handleMan`,`kind`,`legalPerson`,`license`,`licenseDate`,`qualification`,`telephone1`,`telephone2`,`telephone3`,`telephone4`,`unitName`,`workArea`,`workRemark`,`workType`,`py`,`station`,`status`,`createrId`,`editorId`) VALUES 
 (759,'慈溪市周巷镇芦城横江村','','2011-01-26 14:15:47','2008-11-24 00:00:00','2011-01-26 14:15:47','2012-06-30 00:00:00','',90,'潘金松','330282033765','2010-08-11 00:00:00',0,'','13968275668','63308620','','慈溪市周巷镇金松摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzjsmtcwxd',0,0,1,1),
 (760,'浒山镇长春村','','2011-01-26 14:15:47','2005-07-01 00:00:00','2011-01-26 14:15:47','2008-07-01 00:00:00','',90,'余芳','330282033766','2010-08-11 00:00:00',0,'','13968279644','','','慈溪市浒山镇余芳摩托修理店','维修(摩托车修理)','   ',105,'cxszszyfmtxld',0,0,1,1),
 (761,'慈溪市浒山新城大道南路63弄－1号','','2011-01-26 14:15:47','2010-08-17 00:00:00','2011-01-26 14:15:47','2011-06-30 00:00:00','',90,'范秋菊','330282033767','2010-08-25 00:00:00',0,'','13805818890','','','慈溪市浒山风范摩托车修理店','维修(摩托车修理)','   ',105,'cxszsffmtcxld',0,0,1,1),
 (762,'浒山镇经济开发区开发大道南侧','','2011-01-26 14:15:47','2005-07-01 00:00:00','2011-01-26 14:15:47','2008-07-01 00:00:00','',90,'叶华军','330282033768','2010-08-25 00:00:00',0,'','63022534','','','慈溪市浒山华军摩托车修配店','维修(摩托车修理)','   ',105,'cxszshjmtcxpd',0,0,1,1),
 (763,'慈溪市浒山镇三北大街302号','','2011-01-26 14:15:47','2005-07-01 00:00:00','2011-01-26 14:15:47','2008-07-01 00:00:00','',90,'王光明','330282033771','2010-08-25 00:00:00',0,'','13005915377','','','慈溪市浒山光明摩托维修店','维修(摩托车修理)','   ',105,'cxszsgmmtwxd',0,0,1,1),
 (764,'慈溪市天元镇镇中路','','2011-01-26 14:15:47','2008-09-08 00:00:00','2011-01-26 14:15:47','2011-06-30 00:00:00','',90,'吴光辉','330282033772','2010-08-25 00:00:00',0,'','13606742402','','','慈溪市天元镇兴旺摩托维修店','维修(摩托车修理)','   ',105,'cxstyzxwmtwxd',0,0,1,1),
 (765,'慈溪市横河镇小洋山村杨梅大道北路8号','','2011-01-26 14:15:47','2010-08-11 00:00:00','2011-01-26 14:15:47','2012-12-31 00:00:00','',90,'罗岳军','330282033773','2010-08-11 00:00:00',0,'','13968249209','','','慈溪市横河越强汽车修理部','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxshhyqqcxlb',0,0,1,1),
 (766,'慈溪市浒山街道长春村下房','','2011-01-26 14:15:47','2004-11-10 00:00:00','2011-01-26 14:15:47','2007-11-10 00:00:00','',90,'龚孟军','330282033774','2010-08-11 00:00:00',0,'','','13645841696','','慈溪市浒山孟军摩托修理店','维修(摩托车修理)','   ',105,'cxszsmjmtxld',0,0,1,1),
 (767,'浒山镇金山路金东北苑5号','','2011-01-26 14:15:48','2005-06-01 00:00:00','2011-01-26 14:15:48','2005-07-01 00:00:00','',90,'郑勇','330282033776','2010-08-11 00:00:00',0,'','63802266','','','慈溪市浒山街道郑勇摩托维修店','维修(摩托车修理)','   ',105,'cxszsjdzymtwxd',0,0,1,1),
 (768,'庵东镇中片七塘公路边','','2011-01-26 14:15:48','2009-06-05 00:00:00','2011-01-26 14:15:48','2015-06-05 00:00:00','',90,'吴百焕','330282033777','2009-06-05 00:00:00',0,'','63475121','13505843133','','慈溪市庵东镇阿焕摩托修理店','维修(摩托车修理)','   ',105,'cxszdzahmtxld',0,0,1,1),
 (769,'横河镇彭南村','','2011-01-26 14:15:48','2010-08-17 00:00:00','2011-01-26 14:15:48','2011-06-30 00:00:00','',90,'孙金权','330282033779','2010-08-25 00:00:00',0,'','63831526','13819442442','','慈溪市横河镇金权摩托修配店','维修(摩托车修理)','',105,'cxshhzjqmtxpd',0,0,1,1),
 (770,'明州路东端-起东村','','2011-01-26 14:15:48','2010-08-17 00:00:00','2011-01-26 14:15:48','2011-06-30 00:00:00','',90,'罗迪辉','330282033780','2010-08-25 00:00:00',0,'','13858349920','','','慈溪市浒山迪辉摩托修理店','维修(摩托车修理)','   ',105,'cxszsdhmtxld',0,0,1,1),
 (771,'宗汉新华街265号','','2011-01-26 14:15:48','2004-04-14 00:00:00','2011-01-26 14:15:48','2007-04-14 00:00:00','',90,'徐森波','330282033781','2010-08-25 00:00:00',0,'','63240926','','','宗汉森波摩托修理店','维修(摩托车修理)','   ',105,'zhsbmtxld',0,0,1,1),
 (772,'慈溪市龙山镇西门外村伏龙路46号','','2011-01-26 14:15:48','2008-10-31 00:00:00','2011-01-26 14:15:48','2011-06-30 00:00:00','',90,'马杰江','330282033782','2010-08-25 00:00:00',0,'','63789665','81315315','','慈溪市龙山镇阿江摩托修配店','维修(摩托车修理)','   ',105,'cxslszajmtxpd',0,0,1,1),
 (773,'宗汉街道百兴村','','2011-01-26 14:15:48','2008-07-02 00:00:00','2011-01-26 14:15:48','2011-07-02 00:00:00','',90,'徐国庆','330282033783','2010-08-25 00:00:00',0,'','13805813103','63202253','','慈溪市宗汉国庆摩托车修理店','维修(摩托车修理)','   ',105,'cxszhgqmtcxld',0,0,1,1),
 (774,'慈溪市新浦镇高桥村','','2011-01-26 14:15:48','2010-08-11 00:00:00','2011-01-26 14:15:48','2013-02-23 00:00:00','',90,'陆军','330282033784','2010-08-11 00:00:00',0,'','13355924244','','','慈溪市新浦盛世汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxsxpssqcxld',0,0,1,1),
 (775,'周巷镇城中村（傅家）','','2011-01-26 14:15:48','2008-08-06 00:00:00','2011-01-26 14:15:48','2011-06-30 00:00:00','',90,'茅杰','330282033785','2010-08-11 00:00:00',0,'','13777991313','','','周巷镇迅达摩托修理店','维修(摩托车修理)','   ',105,'zxzxdmtxld',0,0,1,1),
 (776,'浒山南二环（慈溪车城78号）','','2011-01-26 14:15:48','2010-08-17 00:00:00','2011-01-26 14:15:48','2011-06-30 00:00:00','',90,'沈建张','330282033786','2010-08-25 00:00:00',0,'','63102793','81386895','','慈溪市浒山建张摩托修理部','维修(摩托车修理)','   ',105,'cxszsjzmtxlb',0,0,1,1),
 (777,'周巷镇小安村','','2011-01-26 14:15:48','2008-09-27 00:00:00','2011-01-26 14:15:48','2011-06-30 00:00:00','',90,'顾军','330282033787','2010-08-25 00:00:00',0,'','13967847049','','','慈溪市周巷顾军摩托车维修店','维修(摩托车修理)','   ',105,'cxszxgjmtcwxd',0,0,1,1),
 (778,'天元镇界塘村','','2011-01-26 14:15:49','2008-09-26 00:00:00','2011-01-26 14:15:49','2011-06-30 00:00:00','',90,'徐建波','330282033788','2010-08-25 00:00:00',0,'','13757457468','','','慈溪市天元腾达摩托车维修店','维修(摩托车修理)','   ',105,'cxstytdmtcwxd',0,0,1,1),
 (779,'慈溪市浒山镇金山乌山路202号','','2011-01-26 14:15:49','2002-07-01 00:00:00','2011-01-26 14:15:49','2005-07-01 00:00:00','',90,'严狄波','330282033789','2010-08-25 00:00:00',0,'','63104165','','','慈溪市浒山波摩托车修理店','维修(摩托车修理)','   ',105,'cxszsbmtcxld',0,0,1,1),
 (780,'慈溪市浒山镇新江路399号','','2011-01-26 14:15:49','2002-07-01 00:00:00','2011-01-26 14:15:49','2005-07-01 00:00:00','',90,'李青松','330282033790','2010-08-25 00:00:00',0,'','13505843603','','','慈溪市浒山镇青松摩托修理店','维修(摩托车修理)','   ',105,'cxszszqsmtxld',0,0,1,1),
 (781,'慈溪市横河镇秦堰村','','2011-01-26 14:15:49','2010-08-17 00:00:00','2011-01-26 14:15:49','2011-06-30 00:00:00','',90,'胡家通','330282033791','2010-08-25 00:00:00',0,'','63251644','13968708656','','慈溪市横河镇家通摩修理店','维修(摩托车修理)','   ',105,'cxshhzjtmxld',0,0,1,1),
 (782,'慈溪市浒山孙塘东村','','2011-01-26 14:15:49','2002-07-01 00:00:00','2011-01-26 14:15:49','2005-07-01 00:00:00','',90,'孙伟树','330282033793','2010-08-25 00:00:00',0,'','63011760','','','浒山镇伟书摩托车维修店','维修(摩托车修理)','   ',105,'zszwsmtcwxd',0,0,1,1),
 (783,'慈溪市浒山镇楼家大厦','','2011-01-26 14:15:49','2002-07-01 00:00:00','2011-01-26 14:15:49','2005-07-01 00:00:00','',90,'胡铁军','330282033794','2010-08-25 00:00:00',0,'','63016785','','','浒山镇铁军摩托车维修店','维修(摩托车修理)','   ',105,'zsztjmtcwxd',0,0,1,1),
 (784,'慈溪市天元镇火车跟村综合楼下','','2011-01-26 14:15:49','2008-09-08 00:00:00','2011-01-26 14:15:49','2011-06-30 00:00:00','',90,'周小苗','330282033795','2010-08-25 00:00:00',0,'','63459122','','','天元镇兴发摩托车修配商行','维修(摩托车修理)','   ',105,'tyzxfmtcxpsx',0,0,1,1),
 (785,'市青少年宫北路212号','','2011-01-26 14:15:49','2005-07-01 00:00:00','2011-01-26 14:15:49','2008-07-01 00:00:00','',90,'杨添侃','330282033796','2010-08-25 00:00:00',0,'','63017919','13819889788','','慈溪市浒山阿开摩托车修理店','维修(摩托车修理)','   ',105,'cxszsakmtcxld',0,0,1,1),
 (786,'浒山镇教场山路302号','','2011-01-26 14:15:50','2005-07-01 00:00:00','2011-01-26 14:15:50','2008-07-01 00:00:00','',90,'丁一','330282033797','2010-08-25 00:00:00',0,'','13506747040','','','浒山镇丁一摩托车维修店','维修(摩托车修理)','   ',105,'zszdymtcwxd',0,0,1,1),
 (787,'慈溪市长河大云村','','2011-01-26 14:15:50','2008-09-01 00:00:00','2011-01-26 14:15:50','2011-06-30 00:00:00','',90,'倪开华','330282033798','2010-08-25 00:00:00',0,'','13008975616','','','慈溪市长河镇开华摩托车维修店','维修(摩托车修理)','   ',105,'cxschzkhmtcwxd',0,0,1,1),
 (788,'慈溪市新浦镇水湘村','','2011-01-26 14:15:50','2010-08-17 00:00:00','2011-01-26 14:15:50','2011-06-30 00:00:00','',90,'陈沛丰','330282033799','2010-08-25 00:00:00',0,'','13506746678','','','慈溪市新浦沛丰摩托维修店','维修(摩托车修理)','   ',105,'cxsxppfmtwxd',0,0,1,1),
 (789,'浒山镇北二环中路238号西1号','','2011-01-26 14:15:50','2002-07-01 00:00:00','2011-01-26 14:15:50','2005-07-01 00:00:00','',90,'韩维','330282033800','2010-08-25 00:00:00',0,'','63817457','','','浒山镇韩维摩托车维修店','维修(摩托车修理)','   ',105,'zszhwmtcwxd',0,0,1,1),
 (790,'慈溪市庵东镇宏兴村','','2011-01-26 14:15:50','2008-08-29 00:00:00','2011-01-26 14:15:50','2011-06-30 00:00:00','',90,'陈建能','330282033801','2010-08-25 00:00:00',0,'','63473052','13056793356','','慈溪市庵东镇建能摩托车维修店','维修(摩托车修理)','   ',105,'cxszdzjnmtcwxd',0,0,1,1),
 (791,'慈溪市庵东镇宏兴村','','2011-01-26 14:15:50','2008-11-19 00:00:00','2011-01-26 14:15:50','2011-06-30 00:00:00','',90,'沈光明','330282033803','2010-08-25 00:00:00',0,'','13616589617','','','慈溪市庵东镇光明摩托车维修店','维修(摩托车修理)','   ',105,'cxszdzgmmtcwxd',0,0,1,1),
 (792,'慈溪市崇寿镇仁寿村','','2011-01-26 14:15:50','2010-08-17 00:00:00','2011-01-26 14:15:50','2011-06-30 00:00:00','',90,'阮世杰','330282033804','2010-08-25 00:00:00',0,'','63296702','','','慈溪市崇寿镇阿杰修理店','维修(摩托车修理)','   ',105,'cxscszajxld',0,0,1,1),
 (793,'慈溪市逍林镇樟新南路（横新塘）','','2011-01-26 14:15:50','2010-08-17 00:00:00','2011-01-26 14:15:50','2011-06-30 00:00:00','',90,'曹雪云','330282033806','2010-08-25 00:00:00',0,'','13706747005','','','慈溪市逍林镇雪云摩托车修理店','维修(摩托车修理)','   ',105,'cxszlzxymtcxld',0,0,1,1),
 (794,'慈溪市浒山镇阳明广场190-191号','','2011-01-26 14:15:50','2002-07-01 00:00:00','2011-01-26 14:15:50','2005-07-01 00:00:00','',90,'韩晓庆','330282033808','2010-08-25 00:00:00',0,'','653015417','','','慈溪市浒山镇新城摩托车维修店','维修(摩托车修理)','   ',105,'cxszszxcmtcwxd',0,0,1,1),
 (795,'慈溪市逍林镇青春路','','2011-01-26 14:15:50','2010-08-17 00:00:00','2011-01-26 14:15:50','2011-06-30 00:00:00','',90,'岑祥州','330282033809','2010-08-25 00:00:00',0,'','63505658','13857455946','','慈溪市逍林镇祥州摩托车修理店','维修(摩托车修理)','   ',105,'cxszlzxzmtcxld',0,0,1,1),
 (796,'慈溪市匡堰镇高家村','','2011-01-26 14:15:50','2010-09-15 00:00:00','2011-01-26 14:15:50','2012-06-30 00:00:00','',90,'冯毛伟','330282033810','2010-09-15 00:00:00',0,'13567407863','','','','慈溪市匡堰镇小伟汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxskyzxwqcxld',0,0,1,1),
 (797,'慈溪市浒山街道东山村','','2011-01-26 14:15:50','2005-07-01 00:00:00','2011-01-26 14:15:50','2008-07-01 00:00:00','',90,'胡红安','330282033811','2010-09-15 00:00:00',0,'','63102422','','','慈溪市浒山红宏摩托车维修店','维修(摩托车修理)','   ',105,'cxszshhmtcwxd',0,0,1,1),
 (798,'慈溪市庵东镇邮电西路口','','2011-01-26 14:15:50','2008-08-15 00:00:00','2011-01-26 14:15:50','2011-06-30 00:00:00','',90,'施光恩','330282033813','2010-09-15 00:00:00',0,'','63473169','13777033191','','慈溪市庵东镇光恩摩托车维修店','维修(摩托车修理)','   ',105,'cxszdzgemtcwxd',0,0,1,1),
 (799,'慈溪市横河镇乌山村','','2011-01-26 14:15:51','2010-08-17 00:00:00','2011-01-26 14:15:51','2011-06-30 00:00:00','',90,'郑华东','330282033816','2010-08-25 00:00:00',0,'','63197385','','','慈溪市横河镇华东摩托车修理店','维修(摩托车修理)','   ',105,'cxshhzhdmtcxld',0,0,1,1),
 (800,'慈溪市新浦镇浦东村','','2011-01-26 14:15:51','2008-09-12 00:00:00','2011-01-26 14:15:51','2011-06-30 00:00:00','',90,'李华乔','330282033818','2010-08-25 00:00:00',0,'','63589110','','','慈溪市新浦镇华乔摩托车维修店','维修(摩托车修理)','   ',105,'cxsxpzhqmtcwxd',0,0,1,1),
 (801,'慈溪市新浦镇浦东村','','2011-01-26 14:15:51','2008-08-29 00:00:00','2011-01-26 14:15:51','2011-06-30 00:00:00','',90,'俞江','330282033819','2009-05-08 00:00:00',0,'','63598923','13355935924','','慈溪市新浦镇四灶浦二桥摩托车修配店','维修(摩托车修理)','',105,'cxsxpzszpeqmtcxpd',0,0,1,1),
 (802,'慈溪市新浦镇胜北车站','','2011-01-26 14:15:51','2010-08-17 00:00:00','2011-01-26 14:15:51','2011-06-30 00:00:00','',90,'史迪','330282033820','2010-08-25 00:00:00',0,'','63586538','','','慈溪市新浦镇史迪摩托修理店','维修(摩托车修理)','   ',105,'cxsxpzsdmtxld',0,0,1,1),
 (803,'慈溪市新浦镇后一灶村','','2011-01-26 14:15:51','2008-09-12 00:00:00','2011-01-26 14:15:51','2011-06-30 00:00:00','',90,'应仕雨','330282033821','2010-08-25 00:00:00',0,'','63588354','','','慈溪市新浦镇仕雨摩托车修理店','维修(摩托车修理)','   ',105,'cxsxpzsymtcxld',0,0,1,1),
 (804,'慈溪市周巷镇劳家村','','2011-01-26 14:15:51','2005-07-01 00:00:00','2011-01-26 14:15:51','2008-07-01 00:00:00','',90,'吴洲','330282033823','2010-08-25 00:00:00',0,'','63322181','13505789783','','慈溪市周巷镇吴洲摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzwzmtcwxd',0,0,1,1),
 (805,'慈溪市龙山镇龙头场村（林家）','','2011-01-26 14:15:51','2008-10-31 00:00:00','2011-01-26 14:15:51','2011-06-30 00:00:00','',90,'余慈荣','330282033824','2010-08-25 00:00:00',0,'','63787113','13968270961','','慈溪市龙山镇慈荣维修店','维修(摩托车修理)','   ',105,'cxslszcrwxd',0,0,1,1),
 (806,'慈溪市范市王家路村','','2011-01-26 14:15:51','2008-10-31 00:00:00','2011-01-26 14:15:51','2011-06-30 00:00:00','',90,'王跃飞','330282033825','2010-08-25 00:00:00',0,'','13645741271','','','慈溪市龙山跃飞摩托车修配店','维修(摩托车修理)','   ',105,'cxslsyfmtcxpd',0,0,1,1),
 (807,'慈溪市龙山镇街河路1号','','2011-01-26 14:15:52','2008-10-31 00:00:00','2011-01-26 14:15:52','2011-06-30 00:00:00','',90,'范东晓','330282033826','2010-08-25 00:00:00',0,'','63701617','13656740606','','慈溪市龙山阿东摩托车维修店','维修(摩托车修理)','   ',105,'cxslsadmtcwxd',0,0,1,1),
 (808,'慈溪市范市镇人民路5号','','2011-01-26 14:15:52','2005-07-01 00:00:00','2011-01-26 14:15:52','2008-07-01 00:00:00','',90,'范维江','330282033827','2010-08-25 00:00:00',0,'','63703257','13958270158','','慈溪市范市镇旺旺摩托车维修店','维修(摩托车修理)','   ',105,'cxsfszwwmtcwxd',0,0,1,1),
 (809,'慈溪市龙山镇综合市场旁','','2011-01-26 14:15:52','2008-10-30 00:00:00','2011-01-26 14:15:52','2011-06-30 00:00:00','',90,'华宏波','330282033828','2010-08-25 00:00:00',0,'','13516749693','','','慈溪市龙山镇三北摩托维修店','维修(摩托车修理)','   ',105,'cxslszsbmtwxd',0,0,1,1),
 (810,'慈溪市三北镇田央村','','2011-01-26 14:15:52','2002-07-01 00:00:00','2011-01-26 14:15:52','2005-07-01 00:00:00','',90,'孙利国','330282033829','2010-08-25 00:00:00',0,'','13003796015','','','慈溪市三北镇利国摩托车维修店','维修(摩托车修理)','   ',105,'cxssbzlgmtcwxd',0,0,1,1),
 (811,'慈溪市龙山镇达蓬村（孙家）','','2011-01-26 14:15:52','2008-10-31 00:00:00','2011-01-26 14:15:52','2011-06-30 00:00:00','',90,'李建波','330282033830','2010-08-25 00:00:00',0,'','13968214127','','','慈溪市龙山镇建波摩托车维修店','维修(摩托车修理)','   ',105,'cxslszjbmtcwxd',0,0,1,1),
 (812,'龙山龙头场村','','2011-01-26 14:15:52','2005-01-21 00:00:00','2011-01-26 14:15:52','2008-07-01 00:00:00','',90,'林凯','330282033831','2010-08-25 00:00:00',0,'','63787860','','','慈溪市龙山伏龙摩托车修理店','维修(摩托车修理)','   ',105,'cxslsflmtcxld',0,0,1,1),
 (813,'慈溪市匡堰镇匡堰大道','','2011-01-26 14:15:52','2009-03-30 00:00:00','2011-01-26 14:15:52','2011-06-30 00:00:00','',90,'罗锋','330282033832','2009-03-30 00:00:00',0,'','63535362','13967813532','','慈溪市匡堰镇罗锋摩托修配店','维修(摩托车修理)','   ',105,'cxskyzlfmtxpd',0,0,1,1),
 (814,'慈溪市天元镇钱王村','','2011-01-26 14:15:52','2008-09-08 00:00:00','2011-01-26 14:15:52','2011-06-30 00:00:00','',90,'吴毛辉','330282033833','2009-03-30 00:00:00',0,'','13606742400','','','慈溪市天元镇阿二摩托车修理店','维修(摩托车修理)','   ',105,'cxstyzaemtcxld',0,0,1,1),
 (815,'慈溪市坎墩镇羊路头村兴镇中街','','2011-01-26 14:15:52','2002-07-01 00:00:00','2011-01-26 14:15:52','2005-07-01 00:00:00','',90,'陈志炳','330282033834','2009-03-30 00:00:00',0,'','','','','慈溪市坎墩阿炳摩托车维修店','维修(摩托车修理)','   ',105,'cxskdabmtcwxd',0,0,1,1),
 (816,'胜山镇上蔡村','','2011-01-26 14:15:52','2005-07-28 00:00:00','2011-01-26 14:15:52','2008-07-01 00:00:00','',90,'钱狄丰','330282033835','2009-03-30 00:00:00',0,'','13586606485','','','慈溪市胜山镇少波摩托车修理店','维修(摩托车修理)','   ',105,'cxssszsbmtcxld',0,0,1,1),
 (817,'慈溪市长河镇宁丰村','','2011-01-26 14:15:52','2008-09-01 00:00:00','2011-01-26 14:15:52','2011-06-30 00:00:00','',90,'陈锋','330282033836','2009-03-30 00:00:00',0,'','63403338','13606887341','','慈溪市长河镇陈锋摩托车维修店','维修(摩托车修理)','   ',105,'cxschzcfmtcwxd',0,0,1,1),
 (818,'慈溪市周巷万安村','','2011-01-26 14:15:52','2005-07-01 00:00:00','2011-01-26 14:15:52','2008-07-01 00:00:00','',90,'张建国','330282033837','2009-03-30 00:00:00',0,'','63310594','','','慈溪市周巷镇建国摩托车维修店','维修(摩托车修理)','   ',105,'cxszxzjgmtcwxd',0,0,1,1),
 (819,'慈溪市浒山镇三北大街515号','','2011-01-26 14:15:53','2005-07-01 00:00:00','2011-01-26 14:15:53','2008-07-01 00:00:00','',90,'韩琳','330282033838','2009-03-30 00:00:00',0,'','66302970','','','慈溪市浒山韩琳摩托车维修店','维修(摩托车修理)','   ',105,'cxszshlmtcwxd',0,0,1,1),
 (820,'慈溪市浒山镇金山路桥旁','','2011-01-26 14:15:53','2002-07-01 00:00:00','2011-01-26 14:15:53','2005-07-01 00:00:00','',90,'胡龙达','330282033840','2009-03-30 00:00:00',0,'','63894603','','','慈溪市浒山镇龙达摩托车维修店','维修(摩托车修理)','   ',105,'cxszszldmtcwxd',0,0,1,1),
 (821,'慈溪市三北大街799号','','2011-01-26 14:15:53','2005-07-01 00:00:00','2011-01-26 14:15:53','2008-07-01 00:00:00','',90,'华立君','330282033841','2009-03-30 00:00:00',0,'','63829807','13505846092','','慈溪市浒山华立君摩托车维修店','维修(摩托车修理)','   ',105,'cxszshljmtcwxd',0,0,1,1),
 (822,'慈溪市坎墩街道三四灶村','','2011-01-26 14:15:53','2010-08-17 00:00:00','2011-01-26 14:15:53','2011-06-30 00:00:00','',90,'胡群松','330282033845','2010-08-25 00:00:00',0,'','13065626763','','','慈溪市坎墩群松摩托车维修店','维修(摩托车修理)','   ',105,'cxskdqsmtcwxd',0,0,1,1),
 (823,'慈溪市横河宜青桥村','','2011-01-26 14:15:53','2010-08-17 00:00:00','2011-01-26 14:15:53','2011-06-30 00:00:00','',90,'宁发富','330282033848','2010-08-25 00:00:00',0,'','13567812120','','','慈溪市横河镇发富摩托车维修店','维修(摩托车修理)','   ',105,'cxshhzffmtcwxd',0,0,1,1),
 (824,'慈溪市桥头五丰村','','2011-01-26 14:15:54','2008-09-28 00:00:00','2011-01-26 14:15:54','2011-06-30 00:00:00','',90,'陈利冲','330282033849','2010-08-25 00:00:00',0,'','13967816718','','','慈溪市桥头镇利冲摩托修理店','维修(摩托车修理)','   ',105,'cxsqtzlcmtxld',0,0,1,1),
 (825,'慈溪市周巷镇环城东路','','2011-01-26 14:15:54','2002-07-01 00:00:00','2011-01-26 14:15:54','2005-07-01 00:00:00','',90,'钟辽宁','330282033850','2010-08-25 00:00:00',0,'','13003764425','','','慈溪市周巷镇东环摩托车修理店','维修(摩托车修理)','   ',105,'cxszxzdhmtcxld',0,0,1,1),
 (826,'慈溪市桥头镇日旺路枫林村','','2011-01-26 14:15:54','2008-09-28 00:00:00','2011-01-26 14:15:54','2011-06-30 00:00:00','',90,'黄成','330282033851','2010-08-25 00:00:00',0,'','13586600820','','','慈溪市桥头镇黄成摩托修理店','维修(摩托车修理)','   ',105,'cxsqtzhcmtxld',0,0,1,1),
 (827,'天元镇潭东村','','2011-01-26 14:15:54','2008-09-01 00:00:00','2011-01-26 14:15:54','2011-06-30 00:00:00','',90,'张浩波','330282033852','2010-08-25 00:00:00',0,'','63453451','','','慈溪市天元阿波摩托车修理店','维修(摩托车修理)','   ',105,'cxstyabmtcxld',0,0,1,1),
 (828,'观城镇蒋家村','','2011-01-26 14:15:54','2008-10-22 00:00:00','2011-01-26 14:15:54','2011-06-30 00:00:00','',90,'俞坚定','330282033853','2010-08-25 00:00:00',0,'','13806648460','','','慈溪市观海卫镇阿二摩托车修理店','维修(摩托车修理)','   ',105,'cxsghwzaemtcxld',0,0,1,1),
 (829,'慈溪市新浦镇余家路村','','2011-01-26 14:15:54','2005-07-01 00:00:00','2011-01-26 14:15:54','2008-07-01 00:00:00','',90,'冯高冲','330282033854','2010-08-25 00:00:00',0,'','63573842','13506748042','','慈溪市新浦高冲摩托车维修店','维修(摩托车修理)','   ',105,'cxsxpgcmtcwxd',0,0,1,1),
 (830,'慈溪天元镇潭南村','','2011-01-26 14:15:54','2008-09-01 00:00:00','2011-01-26 14:15:54','2011-06-30 00:00:00','',90,'卢奇挺','330282033856','2010-01-21 00:00:00',0,'','13806645429','','','慈溪市天元阿挺摩托修理店','维修(摩托车修理)','   ',105,'cxstyatmtxld',0,0,1,1),
 (831,'浒山镇三北大街473号','','2011-01-26 14:15:54','2005-07-01 00:00:00','2011-01-26 14:15:54','2008-07-01 00:00:00','',90,'张永启','330282033857','2010-01-21 00:00:00',0,'','13065827419','','','浒山永启摩托车修理店','维修(摩托车修理)','   ',105,'zsyqmtcxld',0,0,1,1),
 (832,'慈溪市崇寿镇六塘村','','2011-01-26 14:15:54','2002-07-01 00:00:00','2011-01-26 14:15:54','2005-07-01 00:00:00','',90,'孙华明','330282033858','2010-01-21 00:00:00',0,'','63294916','','','慈溪市崇寿华明摩托车维修店','维修(摩托车修理)','   ',105,'cxscshmmtcwxd',0,0,1,1),
 (833,'逍林镇福合院村','','2011-01-26 14:15:55','2005-07-01 00:00:00','2011-01-26 14:15:55','2008-07-01 00:00:00','',90,'岑益松','330282033859','2010-01-21 00:00:00',0,'','13567867911','','','慈溪市逍林镇益松摩托车维修店','维修(摩托车修理)','   ',105,'cxszlzysmtcwxd',0,0,1,1),
 (834,'慈溪市逍林镇桥一村','','2011-01-26 14:15:55','2006-04-20 00:00:00','2011-01-26 14:15:55','2009-07-01 00:00:00','',90,'舒茂华','330282033860','2010-01-21 00:00:00',0,'','13968247104','','','慈溪市逍林茂华摩托车修理店','维修(摩托车修理)','   ',105,'cxszlmhmtcxld',0,0,1,1),
 (835,'慈溪市崇寿镇六塘东街','','2011-01-26 14:15:55','2010-08-11 00:00:00','2011-01-26 14:15:55','2012-03-04 00:00:00','',90,'史俊杰','330282033861','2010-08-11 00:00:00',71,'','66307865','','','慈溪市崇寿俊杰汽车修理店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxscsjjqcxld',0,0,1,1),
 (836,'浒山太屺村','','2011-01-26 14:15:55','2005-07-01 00:00:00','2011-01-26 14:15:55','2008-07-01 00:00:00','',90,'裘烘杰','330282033862','2010-08-11 00:00:00',0,'','13506710004','63675152','','慈溪市浒山阿杰摩托修理店','维修(摩托车修理)','   ',105,'cxszsajmtxld',0,0,1,1),
 (837,'慈溪市新浦镇荣誉村','','2011-01-26 14:15:55','2005-07-01 00:00:00','2011-01-26 14:15:55','2008-07-01 00:00:00','',90,'应松利','330282033863','2010-08-11 00:00:00',0,'','63588403','13805827617','','慈溪市新浦镇松利摩托修理店','维修(摩托车修理)','   ',105,'cxsxpzslmtxld',0,0,1,1),
 (838,'慈溪市逍林镇福合院村樟新北路191号','','2011-01-26 14:15:55','2010-09-24 00:00:00','2011-01-26 14:15:55','2013-06-30 00:00:00','',90,'岑利军','330282033864','2010-10-08 00:00:00',0,'','63506113','','','慈溪市逍林镇凯伦摩托车修理店','维修(摩托车修理)','',105,'cxszlzklmtcxld',0,0,1,1),
 (839,'浒山明州路419-423号','','2011-01-26 14:15:55','2010-08-11 00:00:00','2011-01-26 14:15:55','2011-03-26 00:00:00','',90,'王丽珍','330282033866','2010-08-11 00:00:00',71,'','','','','慈溪市浒山明州汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxszsmzqcxld',0,0,1,1),
 (840,'慈溪市桥头烟墩村','','2011-01-26 14:15:55','2008-09-28 00:00:00','2011-01-26 14:15:55','2011-06-30 00:00:00','',90,'张长夫','330282033869','2010-08-11 00:00:00',0,'','63556656','','','慈溪市桥头镇长夫摩托修理店','维修(摩托车修理)','   ',105,'cxsqtzcfmtxld',0,0,1,1),
 (841,'慈溪市桥头镇小桥头村','','2011-01-26 14:15:55','2008-09-28 00:00:00','2011-01-26 14:15:55','2011-06-30 00:00:00','',90,'余引君','330282033870','2010-08-11 00:00:00',0,'','63556907','13566619386','','慈溪市桥头镇引君摩托修理店','维修(摩托车修理)','   ',105,'cxsqtzyjmtxld',0,0,1,1),
 (842,'周巷镇小安村（杭湾路）','','2011-01-26 14:15:56','2008-10-21 00:00:00','2011-01-26 14:15:56','2011-06-30 00:00:00','',90,'泮立桥','330282033871','2010-08-11 00:00:00',0,'','63492349','','','周巷镇立桥摩托修理店','维修(摩托车修理)','   ',105,'zxzlqmtxld',0,0,1,1),
 (843,'长河镇宁丰村（南大路段）','','2011-01-26 14:15:56','2008-10-31 00:00:00','2011-01-26 14:15:56','2011-06-30 00:00:00','',90,'陈建波','330282033872','2010-08-11 00:00:00',0,'','13567817766','','','慈溪市长河镇建波摩托车修理店','维修(摩托车修理)','   ',105,'cxschzjbmtcxld',0,0,1,1),
 (844,'周巷镇镇东村','','2011-01-26 14:15:56','2003-07-22 00:00:00','2011-01-26 14:15:56','2006-07-22 00:00:00','',90,'陈柯锋','330282033874','2010-08-11 00:00:00',0,'','63327138','','','周巷镇柯锋摩修店','维修(摩托车修理)','   ',105,'zxzkfmxd',0,0,1,1),
 (845,'观海卫桃源路','','2011-01-26 14:15:56','2008-10-22 00:00:00','2011-01-26 14:15:56','2011-06-30 00:00:00','',90,'戴建卫','330282033875','2010-08-11 00:00:00',0,'','63604581','','','慈溪市观海卫镇建卫摩托修理店','维修(摩托车修理)','   ',105,'cxsghwzjwmtxld',0,0,1,1),
 (846,'周巷镇小安街村','','2011-01-26 14:15:56','2003-09-04 00:00:00','2011-01-26 14:15:56','2006-09-04 00:00:00','',90,'姚利辉','330282033877','2010-08-11 00:00:00',0,'','63496360','','','周巷镇阿辉摩托车修理店','维修(摩托车修理)','   ',105,'zxzahmtcxld',0,0,1,1),
 (847,'慈溪市西二环北路393号','','2011-01-26 14:15:56','2003-09-16 00:00:00','2011-01-26 14:15:56','2006-09-16 00:00:00','',90,'陈迪宗','330282033878','2010-08-11 00:00:00',0,'','','','','宗汉迪宗摩托修理店','维修(摩托车修理)','   ',105,'zhdzmtxld',0,0,1,1),
 (848,'慈溪市周巷镇环城西路','','2011-01-26 14:15:56','2005-07-01 00:00:00','2011-01-26 14:15:56','2008-07-01 00:00:00','',90,'倪洲腾','330282033883','2010-08-11 00:00:00',0,'','63318088','13566089000','','周巷镇洲腾摩托修理店','维修(摩托车修理)','   ',105,'zxzztmtxld',0,0,1,1),
 (849,'慈溪市观海卫镇杜家桥村罗鸣南路260号','','2011-01-26 14:15:56','2009-09-17 00:00:00','2011-01-26 14:15:56','2012-06-30 00:00:00','',90,'包坚强','330282033885','2009-09-17 00:00:00',0,'13968249228','','','','慈溪市观海卫坚强洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxsghwjqxcc',0,0,1,1),
 (850,'宗汉镇南池村','','2011-01-26 14:15:56','2008-07-02 00:00:00','2011-01-26 14:15:56','2011-07-02 00:00:00','',90,'鲁南军','330282033886','2009-09-17 00:00:00',0,'','13606744213','','','慈溪市宗汉南军摩托修配店','维修(摩托车修理)','   ',105,'cxszhnjmtxpd',0,0,1,1),
 (851,'慈溪市新浦镇下一灶村','','2011-01-26 14:15:56','2008-08-29 00:00:00','2011-01-26 14:15:56','2011-06-30 00:00:00','',90,'王欢龙','330282033890','2009-09-17 00:00:00',0,'','63588894','13706747912','','慈溪市新浦欢龙摩托车修理店','维修(摩托车修理)','   ',105,'cxsxphlmtcxld',0,0,1,1),
 (852,'胜山上蔡村','','2011-01-26 14:15:56','2010-08-17 00:00:00','2011-01-26 14:15:56','2011-06-30 00:00:00','',90,'周日光','330282033891','2010-08-25 00:00:00',0,'','13566064296','','','慈溪市胜山日光摩托修理店','维修(摩托车修理)','   ',105,'cxsssrgmtxld',0,0,1,1),
 (853,'宗汉大道与金轮大道交叉口东','','2011-01-26 14:15:56','2004-12-31 00:00:00','2011-01-26 14:15:56','2007-12-31 00:00:00','',90,'阮岳军','330282033892','2010-08-25 00:00:00',0,'','13805821965','','','慈溪市宗汉南墩时代摩托车修理店','维修(摩托车修理)','   ',105,'cxszhndsdmtcxld',0,0,1,1),
 (854,'慈溪市胜山镇胜山大道1008号','','2011-01-26 14:15:56','2010-08-11 00:00:00','2011-01-26 14:15:56','2012-04-14 00:00:00','',90,'沈卫星','330282033893','2010-08-24 00:00:00',71,'','13566613064','','','慈溪市胜山申雪汽车维修部','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxssssxqcwxb',0,0,1,1),
 (855,'桥头五姓村','','2011-01-26 14:15:56','2005-07-01 00:00:00','2011-01-26 14:15:56','2008-07-01 00:00:00','',90,'孙立辉','330282033895','2010-08-24 00:00:00',0,'','13082925120','','','慈溪市桥头镇立辉摩托车修理店','维修(摩托车修理)','   ',105,'cxsqtzlhmtcxld',0,0,1,1),
 (856,'龙山金岙村','','2011-01-26 14:15:57','2005-04-22 00:00:00','2011-01-26 14:15:57','2008-07-01 00:00:00','',90,'金邦','330282033896','2010-08-24 00:00:00',0,'','81319404  63788465','','','慈溪市龙山宝田摩托修配店','维修(摩托车修理)','   ',105,'cxslsbtmtxpd',0,0,1,1),
 (857,'周巷大古塘村（横江）','','2011-01-26 14:15:57','2009-04-02 00:00:00','2011-01-26 14:15:57','2011-06-30 00:00:00','',90,'张启君','330282033897','2009-04-02 00:00:00',0,'','13738800185','63315831','','慈溪市周巷启君摩托车修理店','维修(摩托车修理)','   ',105,'cxszxqjmtcxld',0,0,1,1),
 (858,'慈溪市桥头镇丰潭村','','2011-01-26 14:15:57','2008-09-28 00:00:00','2011-01-26 14:15:57','2011-06-30 00:00:00','',90,'余迪','330282033898','2009-04-02 00:00:00',0,'','63690467','13968291265','','慈溪市桥头阿迪摩托维修店','维修(摩托车修理)','   ',105,'cxsqtadmtwxd',0,0,1,1),
 (859,'杭州湾新区三洋村','','2011-01-26 14:15:57','2008-11-26 00:00:00','2011-01-26 14:15:57','2011-06-30 00:00:00','',90,'寿青松','330282033899','2009-04-02 00:00:00',0,'','13968267186','','','慈溪市杭州湾科益摩托车维修部','维修(摩托车修理)','   ',105,'cxshzwkymtcwxb',0,0,1,1),
 (860,'慈溪市周巷镇兴业北路54号','','2011-01-26 14:15:57','2010-09-25 00:00:00','2011-01-26 14:15:57','2013-06-30 00:00:00','',90,'徐斌','330282033900','2010-09-25 00:00:00',0,'13905845809','','','','慈溪市周巷飞环汽车修理店','机动车维修：三类机动车维修（供油系统维护及油品更换）。','',105,'cxszxfhqcxld',0,0,1,1),
 (861,'慈溪市孙塘南路靠东边临时店面58号','','2011-01-26 14:15:57','2010-11-02 00:00:00','2011-01-26 14:15:57','2013-06-30 00:00:00','',90,'单红松','330282033902','2010-11-02 00:00:00',0,'13506742396','','','','慈溪市浒山益通汽车装潢商行','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszsytqczzsx',0,0,1,1),
 (862,'慈溪市百路163号','','2011-01-26 14:15:57','2002-07-01 00:00:00','2011-01-26 14:15:57','2005-07-01 00:00:00','',81,'房建立','330282033903','2010-11-02 00:00:00',71,'','63818917','','','慈溪市汽车运输公司门检服务站','维修(车辆检测)','   ',105,'cxsqcysgsmjfwz',0,0,1,1),
 (863,'慈溪市宗汉新江路105号','','2011-01-26 14:15:57','2010-08-23 00:00:00','2011-01-26 14:15:57','2010-07-19 00:00:00','',90,'房建可','330282033904','2010-08-23 00:00:00',71,'','63241597','13805823313','','慈溪市宗汉小胖汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxszhxpqcxld',0,0,1,1),
 (864,'慈溪市胜山镇上蔡村','','2011-01-26 14:15:57','2007-07-19 00:00:00','2011-01-26 14:15:57','2010-07-19 00:00:00','',90,'付克福','330282033905','2010-08-23 00:00:00',71,'','8131920','13291957670','','慈溪市胜山幸福汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxsssxfqcxld',0,0,1,1),
 (865,'慈溪市白沙路街道开发大道1879-1883号','','2011-01-26 14:15:57','2010-11-16 00:00:00','2011-01-26 14:15:57','2013-11-16 00:00:00','',90,'陈凯凯','330282033906','2010-11-16 00:00:00',0,'','','','','慈溪市白沙路阿丰汽车维修厂','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxsbslafqcwxc',0,0,1,1),
 (866,'慈溪市横河镇龙泉村育才路25号','','2011-01-26 14:15:57','2010-08-11 00:00:00','2011-01-26 14:15:57','2010-09-18 00:00:00','',90,'沈冠德','330282033907','2010-08-11 00:00:00',71,'','','','','慈溪市横河鸿宇汽车维修厂','维修(供油系统维护及油品更换)','   ',105,'cxshhhyqcwxc',0,0,1,1),
 (867,'慈溪市浒山街道开发大道北侧（海关北路东）','','2011-01-26 14:15:57','2007-09-27 00:00:00','2011-01-26 14:15:57','2010-09-27 00:00:00','',90,'韩杏先','330282033908','2010-08-11 00:00:00',71,'','13906746851','','','慈溪市浒山洁净车辆自动清洗服务部','维修(三类机动车维修:车身清洁维护、供油系统维护及油品更换)','   ',105,'cxszsjjclzdqxfwb',0,0,1,1),
 (868,'慈溪市坎墩街道潮塘严家路','','2011-01-26 14:15:58','2010-08-11 00:00:00','2011-01-26 14:15:58','2011-11-08 00:00:00','',90,'孙利银','330282033909','2010-08-11 00:00:00',71,'','66304288','','','慈溪市坎墩三山汽车修配店','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxskdssqcxpd',0,0,1,1),
 (869,'慈溪市浒山街道西二环南路100号','','2011-01-26 14:15:58','2010-08-11 00:00:00','2011-01-26 14:15:58','2012-04-25 00:00:00','',90,'叶胜松','330282033910','2010-08-24 00:00:00',71,'','13968288863','13968288863','','慈溪市浒山小小汽车维修部','维修(三类机动车维修:供油系统维护及油品更换)','   ',105,'cxszsxxqcwxb',0,0,1,1),
 (870,'慈溪市匡堰镇樟树村','','2011-01-26 14:15:58','2010-09-15 00:00:00','2011-01-26 14:15:58','2012-06-30 00:00:00','',90,'余孟胜','330282033911','2010-09-15 00:00:00',0,'13706746704','','','','慈溪市匡堰孟胜汽车快修服务部','机动车维修：汽车快修。','',105,'cxskymsqckxfwb',0,0,1,1),
 (871,'慈溪市龙山镇龙头场村','','2011-01-26 14:15:58','2009-11-26 00:00:00','2011-01-26 14:15:58','2012-06-30 00:00:00','',90,'岑丽君','330282033912','2009-11-26 00:00:00',0,'13906749588','','','','慈溪市龙山译钡洗车场','机动车维修：三类机动车维修（车身清洁维护）。','',105,'cxslsybxcc',0,0,1,1),
 (872,'慈溪市逍林镇宏跃村','','2011-01-26 14:15:58','2010-08-11 00:00:00','2011-01-26 14:15:58','2012-06-30 00:00:00','',90,'刘志江','330282033913','2010-08-11 00:00:00',0,'15869509898','','','','慈溪市逍林刘志汽车装潢店','机动车维修：三类机动车维修（车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszllzqczzd',0,0,1,1),
 (873,'慈溪市逍林镇新园村逍林大道2号','','2011-01-26 14:15:58','2010-08-11 00:00:00','2011-01-26 14:15:58','2012-06-30 00:00:00','',90,'赵春玉','330282033914','2010-08-11 00:00:00',0,'13968267930','','','','慈溪市追超汽车维修店','机动车维修：三类机动车维修（车身清洁维护、车辆装潢（蓬布、坐垫及内饰））。','',105,'cxszcqcwxd',0,0,1,1),
 (874,'慈溪市坎墩镇镇中路10号','','2011-01-26 14:15:58','2005-07-01 00:00:00','2011-01-26 14:15:58','2008-07-01 00:00:00','',90,'方孟君','330282037466','2010-08-11 00:00:00',0,'','63285309','','','慈溪市坎墩孟君摩托车修理店','维修(摩托车修理)','   ',105,'cxskdmjmtcxld',0,0,1,1),
 (875,'观海卫镇三北中路533号','','2011-01-26 14:15:58','2010-11-15 00:00:00','2011-01-26 14:15:58','2013-06-30 00:00:00','',90,'王海球','330282330013','2010-11-15 00:00:00',71,'','63618838','','','慈溪提比汽车服务有限公司观海卫营业部','机动车维修：三类机动车维修（轮胎动平衡及修补）。','',105,'cxtbqcfwyxgsghwyyb',0,0,1,1),
 (876,'浒山北二环中路238号','','2011-01-26 14:15:58','2007-05-11 00:00:00','2011-01-26 14:15:58','2010-05-11 00:00:00','',90,'钟永利','330282330021','2010-11-15 00:00:00',71,'','','','','慈溪市浒山阿利汽车轮胎用品店','维修(轮胎动平衡及修补)','   ',105,'cxszsalqcltypd',0,0,1,1),
 (877,'浒山宏坚村','','2011-01-26 14:15:58','2007-04-28 00:00:00','2011-01-26 14:15:58','2010-04-28 00:00:00','',90,'王意','330282330054','2010-11-15 00:00:00',71,'','','','','慈溪市浒山王意汽车维修店','维修(供油系统维护及油品更换)','   ',105,'cxszswyqcwxd',0,0,1,1),
 (878,'慈溪市观海卫镇五里道口北','','2011-01-26 14:15:58','2010-12-20 00:00:00','2011-01-26 14:15:58','2013-06-30 00:00:00','',90,'包冲惠','330282330055','2010-12-20 00:00:00',71,'13505843761','','','','慈溪市观海卫啊冲汽车修理厂','维修(供油系统维护及油品更换)','',105,'cxsghwacqcxlc',0,0,1,1),
 (879,'慈溪市新城大道625号','','2011-01-26 14:15:58','2007-06-01 00:00:00','2011-01-26 14:15:58','2010-06-01 00:00:00','',90,'刁荣振','330282330056','2010-12-20 00:00:00',71,'','','','','慈溪市浒山亿佳汽车维修服务部','维修(供油系统维护及油品更换)','   ',105,'cxszsyjqcwxfwb',0,0,1,1),
 (880,'观海卫镇淹浦村','','2011-01-26 14:15:58','2010-09-15 00:00:00','2011-01-26 14:15:58','2010-06-06 00:00:00','',90,'陈波能','330282330058','2010-09-15 00:00:00',71,'','','','','慈溪市观海卫波能汽车维修厂','维修(供油系统维护及油品更换)','   ',105,'cxsghwbnqcwxc',0,0,1,1),
 (881,'慈溪市浒山街道开发大道945-947号','','2011-01-26 14:15:59','2007-06-11 00:00:00','2011-01-26 14:15:59','2010-06-11 00:00:00','',90,'陆军','330282330059','2010-09-15 00:00:00',71,'','','','','慈溪市浒山顺运汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxszssyqcxld',0,0,1,1),
 (882,'慈溪市宗汉街道马家路村停车场内','','2011-01-26 14:15:59','2007-06-26 00:00:00','2011-01-26 14:15:59','2010-06-26 00:00:00','',90,'屠如忠','330282330060','2010-09-15 00:00:00',71,'','','','','慈溪宗汉宗兴汽车修理厂','维修(供油系统维护及油品更换)','   ',105,'cxzhzxqcxlc',0,0,1,1),
 (883,'宗汉街道宗兴东路205-213号','','2011-01-26 14:15:59','2007-03-12 00:00:00','2011-01-26 14:15:59','2010-03-12 00:00:00','',90,'裘劲松','330282330064','2010-09-15 00:00:00',71,'','13566340048','13566340048','','慈溪宗汉东洲汽车修理厂','维修(车身维修,涂漆)','   ',105,'cxzhdzqcxlc',0,0,1,1),
 (884,'浒山孙塘南路临时房15-22','','2011-01-26 14:15:59','2010-03-26 00:00:00','2011-01-26 14:15:59','2013-03-26 00:00:00','',90,'叶志钢','330282330065','2010-07-30 00:00:00',71,'','13805826066','13805826066','','慈溪市浒山一成汽车修理厂','维修(发动机维修,车身维修)','   ',105,'cxszsycqcxlc',0,0,1,1),
 (885,'新江路399号三江宾馆旁','','2011-01-26 14:15:59','2008-06-25 00:00:00','2017-03-03 20:51:45','2010-03-26 00:00:00','',90,'朱英忠','330282330066','2010-07-30 00:00:00',71,'12312321312','13989339919','13989339919','','慈溪市美鹰汽车维修服务中心','维修(车身维修,涂漆)','测试',105,'cxsmyqcwxfwzx',108,0,1,1),
 (886,'观海卫镇卫北村','','2011-01-26 14:15:59','2010-09-15 00:00:00','2011-01-26 14:15:59','2010-06-01 00:00:00','',90,'魏剑东','330282330067','2010-09-15 00:00:00',71,'','','','','慈溪市观海卫一泰汽车维修厂','维修(车身维修)','   ',105,'cxsghwytqcwxc',0,0,1,1),
 (887,'三北镇工业区','','2011-01-26 14:15:59','2007-06-18 00:00:00','2011-01-26 14:15:59','2010-06-18 00:00:00','',90,'桂雷明','330282330069','2010-09-15 00:00:00',71,'','','','','慈溪市三北飞翔汽车修理部','维修(车身维修)','   ',105,'cxssbfxqcxlb',0,0,1,1),
 (888,'慈溪市周巷镇万新寺村周西公路128号','','2011-01-26 14:15:59','2007-06-18 00:00:00','2011-01-26 14:15:59','2010-06-18 00:00:00','',90,'阮其尧','330282330070','2010-09-15 00:00:00',71,'','','','','慈溪市周巷阳光汽车修理店','维修(车身维修)','   ',105,'cxszxygqcxld',0,0,1,1),
 (889,'浒山新城大道东侧（新城新村朝南）','','2011-01-26 14:15:59','2010-08-11 00:00:00','2011-01-26 14:15:59','2013-06-30 00:00:00','',90,'陈波','330282330078','2010-08-11 00:00:00',71,'','66317333','','','慈溪市浒山全球锁汽车装璜养护店','维修(三类机动车维修:车辆装潢[篷布、坐垫及内装饰])','   ',105,'cxszsqqsqczzyhd',0,0,1,1),
 (890,'慈溪市三北镇施山村329国道边','','2011-01-26 14:15:59','2007-06-28 00:00:00','2011-01-26 14:15:59','2010-06-28 00:00:00','',90,'龚志浩','330282330081','2010-08-11 00:00:00',71,'','13186894258','','','慈溪市三北豪东汽车配件店','维修(汽车装璜)','   ',105,'cxssbhdqcpjd',0,0,1,1),
 (891,'慈溪市观海卫镇东山头村东山头南路77号','','2011-01-26 14:15:59','2007-05-21 00:00:00','2011-01-26 14:15:59','2010-05-21 00:00:00','',90,'方寅','330282330167','2010-08-11 00:00:00',71,'','','','','慈溪市观海卫卫通汽车维修厂','维修(车身清洁维护)','   ',105,'cxsghwwtqcwxc',0,0,1,1),
 (892,'慈溪市横河镇相士地村','','2011-01-26 14:15:59','2007-02-12 00:00:00','2011-01-26 14:15:59','2010-02-12 00:00:00','',90,'胡宏强','330282330409','2010-08-11 00:00:00',71,'','139682597','139682597','','慈溪市横河镇宏强洗车店','维修(车身清洁维护)','   ',105,'cxshhzhqxcd',0,0,1,1),
 (893,'新浦镇水湘村','','2011-01-26 14:16:00','2007-04-25 00:00:00','2011-01-26 14:16:00','2010-04-25 00:00:00','',90,'戚展飞','330282330412','2010-08-11 00:00:00',71,'','','','','慈溪市新浦宇航洗车场','维修(车身清洁维护)','   ',105,'cxsxpyhxcc',0,0,1,1),
 (894,'范市镇镇北路7号','','2011-01-26 14:16:00','2007-06-15 00:00:00','2011-01-26 14:16:00','2010-06-15 00:00:00','',90,'洪涛','330282330421','2010-08-11 00:00:00',71,'','','','','慈溪市范市镇创新洗车服务店','维修(车身清洁维护)','   ',105,'cxsfszcxxcfwd',0,0,1,1),
 (895,'慈溪市胜山镇镇前村','','2011-01-26 14:16:00','2007-06-26 00:00:00','2011-01-26 14:16:00','2010-06-26 00:00:00','',90,'龚佰赞','330282330423','2010-08-11 00:00:00',71,'','','','','慈溪市胜山清泉洗车场','维修(车身清洁维护)','   ',105,'cxsssqqxcc',0,0,1,1),
 (896,'慈溪市逍林镇大众路789号','','2011-01-26 14:16:00','2007-07-02 00:00:00','2011-01-26 14:16:00','2010-07-02 00:00:00','',90,'岑国范','330282330427','2010-08-11 00:00:00',71,'','63517571','','','慈溪市逍林大众路洗车店','维修(车身清洁维护)','   ',105,'cxszldzlxcd',0,0,1,1),
 (897,'浒山赖王村大楼东','','2011-01-26 14:16:00','2007-07-03 00:00:00','2011-01-26 14:16:00','2010-07-03 00:00:00','',90,'陈建明','330282330432','2010-08-11 00:00:00',71,'','13958275507','','','慈溪市浒山赖王洗车场','维修(车身清洁维护)','   ',105,'cxszslwxcc',0,0,1,1),
 (898,'慈溪市浒山街道乌山路255号','','2011-01-26 14:16:00','2007-07-05 00:00:00','2011-01-26 14:16:00','2010-07-05 00:00:00','',90,'张恩杰','330282330435','2010-08-11 00:00:00',71,'','13082959909','','','慈溪市浒山大世界洗车场','维修(车身清洁维护)','   ',105,'cxszsdsjxcc',0,0,1,1),
 (899,'慈溪市胜山镇上蔡村','','2011-01-26 14:16:00','2007-06-28 00:00:00','2011-01-26 14:16:00','2010-06-28 00:00:00','',90,'陈迪','330282330901','2010-08-11 00:00:00',71,'','13738454749','','','慈溪市胜山陈迪汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxssscdqcxld',0,0,1,1),
 (900,'慈溪市庵东镇振东村庵宗公路住宅店（1号楼140号）','','2011-01-26 14:16:00','2007-06-29 00:00:00','2011-01-26 14:16:00','2010-06-29 00:00:00','',90,'沈锋','330282330902','2010-08-11 00:00:00',71,'','13484296101','','','慈溪市庵东沈锋汽车修理店','维修(供油系统维护及油品更换)','   ',105,'cxszdsfqcxld',0,0,1,1),
 (901,'逍林镇林西路716号','','2011-01-26 14:16:00','2007-06-29 00:00:00','2011-01-26 14:16:00','2010-06-29 00:00:00','',90,'刘坤贵','330282330903','2010-08-11 00:00:00',71,'','13250977513','','','慈溪市逍林贵锋汽车维修店','维修(供油系统维护及油品更换)','   ',105,'cxszlgfqcwxd',0,0,1,1),
 (902,'明州路933号','','2011-01-26 14:16:01','2007-06-11 00:00:00','2011-01-26 14:16:01','2010-06-11 00:00:00','',90,'巩正波','33028233577','2010-08-11 00:00:00',0,'','','','','慈溪市浒山正波摩托修理店','维修(摩托车修理)','   ',105,'cxszszbmtxld',0,0,1,1),
 (903,'慈溪市坎墩街道新潮塘村','','2011-01-26 14:16:01','2007-06-15 00:00:00','2011-01-26 23:39:00','2010-06-15 00:00:00','',90,'陆建明','33028233581','2010-08-11 00:00:00',0,'1324567891','','','','慈溪市坎墩阿明摩托修配店','维修(摩托车修理)','',105,'cxskdammtxpd',0,0,1,1),
 (904,'新江路954号','','2011-01-26 14:16:01','2007-06-18 00:00:00','2011-01-26 14:16:01','2010-06-18 00:00:00','',90,'施百聪','33028233585','2010-08-11 00:00:00',0,'','','','','慈溪市浒山百聪摩托车修配店','维修(摩托车修理)','   ',105,'cxszsbcmtcxpd',0,0,1,1),
 (905,'浒山孙塘新村59号楼4号店面','','2011-01-26 14:16:01','2010-08-17 00:00:00','2011-01-26 14:16:01','2013-06-30 00:00:00','',90,'陈伟岳','33028233628','2010-08-25 00:00:00',0,'','66354800','','','慈溪市浒山追风摩托车维修店','维修(摩托车修理)','   ',105,'cxszszfmtcwxd',0,0,1,1),
 (906,'慈溪市逍林镇逍路路头村','','2011-01-26 14:16:01','2007-04-10 00:00:00','2011-01-27 23:22:25','2010-04-10 00:00:00','',90,'王立珍','33028233635','2010-08-25 00:00:00',69,'13732165707','13732165707','13732165707','','慈溪市逍林立珍摩托车修理店','维修(摩托车修理)','',116,'cxszllzmtcxld',108,0,1,1),
 (907,'担山','','2011-01-26 14:16:01','2009-12-22 00:00:00','2011-01-26 14:16:01','2012-12-22 00:00:00','',72,'陈一春','330282731818','2009-12-22 00:00:00',0,'','63014592','','','慈溪市车辆综合性能检测站','机动车维修：机动车维修质量检验经营（车辆维修竣工质量检测、营运车辆技术状况检测、委托检测）。','',105,'cxsclzhxnjcz',0,0,1,1);
/*!40000 ALTER TABLE `enterprises` ENABLE KEYS */;


--
-- Definition of table `patrolriversummary`
--

DROP TABLE IF EXISTS `patrolriversummary`;
CREATE TABLE `patrolriversummary` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Activity1` varchar(255) DEFAULT NULL,
  `Activity2` varchar(255) DEFAULT NULL,
  `Activity3` varchar(255) DEFAULT NULL,
  `Activity4` varchar(255) DEFAULT NULL,
  `Activity5` varchar(255) DEFAULT NULL,
  `FaFangZiLiao` int(11) DEFAULT NULL,
  `FloatGarbage` int(11) DEFAULT NULL,
  `GongYeZhiPai` int(11) DEFAULT NULL,
  `JianYiHanCe` int(11) DEFAULT NULL,
  `LuanDaJian` int(11) DEFAULT NULL,
  `NongYeZhiPai` int(11) DEFAULT NULL,
  `PaiWuBZWeiQuan` int(11) DEFAULT NULL,
  `QingJieGongShiPai` int(11) DEFAULT NULL,
  `QingLiLaJi` int(11) DEFAULT NULL,
  `QingLiLuanDuiFang` int(11) DEFAULT NULL,
  `RiverChief` int(11) DEFAULT NULL,
  `RiverChiefUnit` int(11) DEFAULT NULL,
  `ShangMenXC` int(11) DEFAULT NULL,
  `State` int(11) DEFAULT NULL,
  `ZaWuDuiFang` int(11) DEFAULT NULL,
  `approveDate` datetime DEFAULT NULL,
  `approveDemo` varchar(255) DEFAULT NULL,
  `approveId` int(11) DEFAULT NULL,
  `approveStatus` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `createrId` int(11) DEFAULT NULL,
  `editDate` datetime DEFAULT NULL,
  `editorId` int(11) DEFAULT NULL,
  `printIndex1` int(11) DEFAULT NULL,
  `publicSignDamage` int(11) DEFAULT NULL,
  `townRiverChief` int(11) DEFAULT NULL,
  `villageRiverChief` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `patrolriversummary`
--

/*!40000 ALTER TABLE `patrolriversummary` DISABLE KEYS */;
INSERT INTO `patrolriversummary` (`id`,`Activity1`,`Activity2`,`Activity3`,`Activity4`,`Activity5`,`FaFangZiLiao`,`FloatGarbage`,`GongYeZhiPai`,`JianYiHanCe`,`LuanDaJian`,`NongYeZhiPai`,`PaiWuBZWeiQuan`,`QingJieGongShiPai`,`QingLiLaJi`,`QingLiLuanDuiFang`,`RiverChief`,`RiverChiefUnit`,`ShangMenXC`,`State`,`ZaWuDuiFang`,`approveDate`,`approveDemo`,`approveId`,`approveStatus`,`createDate`,`createrId`,`editDate`,`editorId`,`printIndex1`,`publicSignDamage`,`townRiverChief`,`villageRiverChief`) VALUES 
 (1,'测试小马',NULL,NULL,NULL,NULL,3,1,0,1,1,0,1,0,0,2,1,1,2,0,1,'2017-03-06 21:04:09','测试待审批',1,0,'2017-03-06 21:04:09',1,'2017-03-06 21:04:09',1,0,1,1,1);
/*!40000 ALTER TABLE `patrolriversummary` ENABLE KEYS */;


--
-- Definition of table `role_authority_map`
--

DROP TABLE IF EXISTS `role_authority_map`;
CREATE TABLE `role_authority_map` (
  `authority_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`authority_id`,`role_id`),
  KEY `FK957BCE57F26C6BA3` (`role_id`),
  KEY `FK957BCE576A1952D1` (`authority_id`),
  CONSTRAINT `FK957BCE576A1952D1` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`),
  CONSTRAINT `FK957BCE57F26C6BA3` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role_authority_map`
--

/*!40000 ALTER TABLE `role_authority_map` DISABLE KEYS */;
INSERT INTO `role_authority_map` (`authority_id`,`role_id`) VALUES 
 (1,1),
 (2,1),
 (3,1),
 (4,1),
 (5,1),
 (6,1),
 (7,1),
 (8,1),
 (9,1),
 (10,1),
 (11,1),
 (12,1),
 (13,1),
 (14,1),
 (15,1),
 (17,1),
 (18,1),
 (19,1),
 (20,1),
 (21,1),
 (23,1),
 (25,1),
 (26,1),
 (1,2),
 (17,2),
 (18,2),
 (20,2),
 (1,4),
 (13,4),
 (14,4),
 (15,4),
 (18,4),
 (20,4),
 (21,4),
 (23,4),
 (26,4);
/*!40000 ALTER TABLE `role_authority_map` ENABLE KEYS */;


--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`,`description`,`name`) VALUES 
 (1,'系统管理员，拥有全部模块权限','ROLE_ADMIN'),
 (2,'基本用户，无管理权限','ROLE_USER'),
 (3,'如果此角色有绑定的方法，会对方法调用后返回的结果集进行ACL过滤','AFTER_ACL_COLLECTION_READ'),
 (4,'测试用最基本的用户','ROLE_TEST');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


--
-- Definition of table `setting`
--

DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sortindex` int(11) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `setting`
--

/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` (`id`,`enabled`,`name`,`sortindex`,`value`) VALUES 
 (1,0x01,'RowsPerPage',0,'20'),
 (2,0x01,'RowsPerPage',1,'100'),
 (3,0x01,'Skin',0,'Blue'),
 (4,0x01,'Skin',1,'Gray');
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;


--
-- Definition of table `standard`
--

DROP TABLE IF EXISTS `standard`;
CREATE TABLE `standard` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `numberEnd` int(11) DEFAULT NULL,
  `numberStart` int(11) DEFAULT NULL,
  `pay` int(11) DEFAULT NULL,
  `remain` int(11) DEFAULT NULL,
  `sum` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `enterprise` int(11) DEFAULT NULL,
  `createrId` int(11) DEFAULT NULL,
  `preSet` int(10) unsigned DEFAULT NULL,
  `nextSet` int(10) unsigned DEFAULT NULL,
  `father` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4E3D1EBD2E5D19AB` (`enterprise`),
  KEY `Index_5` (`createDate`),
  KEY `FK4E3D1EBDD0925AC5` (`preSet`),
  KEY `FK4E3D1EBD7915E075` (`nextSet`),
  KEY `FK_standard_2` (`father`),
  KEY `FK4E3D1EBDBE98FD42` (`father`),
  CONSTRAINT `FK4E3D1EBD7915E075` FOREIGN KEY (`nextSet`) REFERENCES `standard` (`id`),
  CONSTRAINT `FK4E3D1EBDBE98FD42` FOREIGN KEY (`father`) REFERENCES `standard` (`id`),
  CONSTRAINT `FK4E3D1EBDD0925AC5` FOREIGN KEY (`preSet`) REFERENCES `standard` (`id`),
  CONSTRAINT `FK_standard_1` FOREIGN KEY (`enterprise`) REFERENCES `enterprises` (`id`),
  CONSTRAINT `FK_standard_2` FOREIGN KEY (`father`) REFERENCES `standard` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `standard`
--

/*!40000 ALTER TABLE `standard` DISABLE KEYS */;
INSERT INTO `standard` (`id`,`createDate`,`numberEnd`,`numberStart`,`pay`,`remain`,`sum`,`type`,`state`,`enterprise`,`createrId`,`preSet`,`nextSet`,`father`) VALUES 
 (1,'2011-01-27 14:47:51',11,1,11,0,11,0,0,0,1,NULL,NULL,NULL),
 (2,'2011-01-28 03:21:07',11,10,0,2,2,1,0,903,1,6,NULL,1),
 (3,'2011-01-27 14:48:13',2,1,0,2,2,1,0,898,1,NULL,4,1),
 (4,'2011-01-27 14:52:39',5,3,0,3,3,1,0,904,1,3,5,1),
 (5,'2011-01-27 14:53:26',7,6,0,2,2,1,0,808,1,4,6,1),
 (6,'2011-01-27 14:54:21',9,8,0,2,2,1,0,903,1,5,2,1),
 (7,'2011-01-27 14:55:41',45,11,0,35,35,0,0,0,1,NULL,NULL,NULL),
 (8,'2011-01-27 14:55:42',45,11,0,35,35,2,0,0,1,NULL,NULL,7),
 (9,'2011-01-28 21:46:03',50,46,0,5,5,0,0,0,1,NULL,NULL,NULL),
 (10,'2011-01-28 21:46:03',50,46,0,5,5,2,0,0,1,NULL,NULL,9),
 (11,'2011-01-28 21:47:51',60,51,0,10,10,0,0,0,1,NULL,NULL,NULL),
 (12,'2011-01-28 21:47:51',60,51,0,10,10,2,0,0,1,NULL,NULL,11),
 (13,'2011-01-28 22:02:47',123,123,0,1,1,0,0,0,1,NULL,NULL,NULL),
 (14,'2011-01-28 22:02:47',123,123,0,1,1,2,0,0,1,NULL,NULL,13),
 (15,'2011-01-28 22:05:07',223,223,0,1,1,0,0,0,1,NULL,NULL,NULL),
 (16,'2011-01-28 22:05:07',223,223,0,1,1,2,0,0,1,NULL,NULL,15),
 (17,'2011-01-28 22:06:09',61,61,0,1,1,0,0,0,1,NULL,NULL,NULL),
 (18,'2011-01-28 22:06:09',61,61,0,1,1,2,0,0,1,NULL,NULL,17),
 (19,'2011-01-29 09:32:11',62,62,1,0,1,0,0,0,1,NULL,NULL,NULL),
 (20,'2011-01-30 15:04:55',62,62,0,1,1,1,0,906,1,NULL,NULL,19),
 (21,'2011-01-29 09:33:40',63,63,0,1,1,0,0,0,1,NULL,NULL,NULL),
 (22,'2011-01-29 09:33:40',63,63,0,1,1,2,0,0,1,NULL,NULL,21),
 (23,'2011-01-29 09:34:36',64,64,1,0,1,0,0,0,1,NULL,NULL,NULL),
 (24,'2011-01-30 15:04:32',64,64,0,1,1,1,0,906,1,NULL,NULL,23),
 (25,'2011-01-29 09:34:56',65,65,0,1,1,0,0,0,1,NULL,NULL,NULL),
 (26,'2011-01-29 09:34:56',65,65,0,1,1,2,0,0,1,NULL,NULL,25),
 (27,'2011-03-15 15:43:28',1030000,1020001,160,9840,10000,0,0,0,1,NULL,NULL,NULL),
 (28,'2011-03-15 15:43:28',1024240,1020001,0,4240,4240,2,0,0,1,NULL,31,27),
 (29,'2011-03-15 15:45:56',1024560,1024481,0,80,80,1,0,102,1,32,30,27),
 (30,'2011-03-15 15:45:56',1030000,1024561,0,5440,5440,2,0,NULL,1,29,NULL,27),
 (31,'2011-03-15 15:46:57',1024320,1024241,1,79,80,1,0,102,1,28,32,27),
 (32,'2011-03-15 15:46:57',1024480,1024321,0,160,160,2,0,NULL,1,31,29,27);
/*!40000 ALTER TABLE `standard` ENABLE KEYS */;


--
-- Definition of table `user_role_map`
--

DROP TABLE IF EXISTS `user_role_map`;
CREATE TABLE `user_role_map` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKF3248407F26C6BA3` (`role_id`),
  KEY `FKF324840797972F83` (`user_id`),
  CONSTRAINT `FKF324840797972F83` FOREIGN KEY (`user_id`) REFERENCES `userbase` (`id`),
  CONSTRAINT `FKF3248407F26C6BA3` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role_map`
--

/*!40000 ALTER TABLE `user_role_map` DISABLE KEYS */;
INSERT INTO `user_role_map` (`user_id`,`role_id`) VALUES 
 (1,1),
 (2,2),
 (0,4),
 (3,4);
/*!40000 ALTER TABLE `user_role_map` ENABLE KEYS */;


--
-- Definition of table `user_setting_map`
--

DROP TABLE IF EXISTS `user_setting_map`;
CREATE TABLE `user_setting_map` (
  `user_id` int(11) NOT NULL,
  `setting_id` int(11) NOT NULL,
  KEY `FK9BBD2C79CECE6D71` (`setting_id`),
  KEY `FK9BBD2C7997972F83` (`user_id`),
  CONSTRAINT `FK9BBD2C7997972F83` FOREIGN KEY (`user_id`) REFERENCES `userbase` (`id`),
  CONSTRAINT `FK9BBD2C79CECE6D71` FOREIGN KEY (`setting_id`) REFERENCES `setting` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_setting_map`
--

/*!40000 ALTER TABLE `user_setting_map` DISABLE KEYS */;
INSERT INTO `user_setting_map` (`user_id`,`setting_id`) VALUES 
 (2,1),
 (2,3),
 (2,3),
 (1,1),
 (1,3);
/*!40000 ALTER TABLE `user_setting_map` ENABLE KEYS */;


--
-- Definition of table `userbase`
--

DROP TABLE IF EXISTS `userbase`;
CREATE TABLE `userbase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cryptpassword` varchar(255) DEFAULT NULL,
  `emailaddress` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `unit` varchar(100) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `grade` varchar(100) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `userbase`
--

/*!40000 ALTER TABLE `userbase` DISABLE KEYS */;
INSERT INTO `userbase` (`id`,`cryptpassword`,`emailaddress`,`enabled`,`fullname`,`loginname`,`unit`,`position`,`grade`,`phone`) VALUES 
 (0,'21232f297a57a5a743894a0e4a801fc3','00@00.com',0x00,'sys','sys',NULL,NULL,NULL,NULL),
 (1,'21232f297a57a5a743894a0e4a801fc3','administrator@micrite.org',0x01,'系统管理员','admin',NULL,NULL,NULL,NULL),
 (2,'21232f297a57a5a743894a0e4a801fc3','tommywang@micrite.org',0x01,'老张','user','平湖市当湖街道','街道办计生主任','村(社区)河长','15912341234'),
 (3,'21232f297a57a5a743894a0e4a801fc3','a@a.com',0x01,'小杨','xiaoyang','平湖市当湖街道','妇女主任','镇级河长','13883312111'),
 (4,'21232f297a57a5a743894a0e4a801fc3','mnx@163.com',0x01,'小马','xiaoma','','','',''),
 (5,'21232f297a57a5a743894a0e4a801fc3','manx@163.com',0x01,'manx','manx','街道办主任','镇级河长','街道办事处','15968030111');
/*!40000 ALTER TABLE `userbase` ENABLE KEYS */;


--
-- Definition of procedure `renew_post_record_carid`
--

DROP PROCEDURE IF EXISTS `renew_post_record_carid`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `renew_post_record_carid`()
BEGIN


	declare _paiHao,_tmp varchar(50) default '';
	declare missed,norow bit(1) default 1;
	declare _paiSeId,_carId int(11) unsigned;
	declare _checkId int(10) unsigned;
	declare cur1 CURSOR FOR SELECT id,paiSe,paiHao FROM `checknote` where isPost=0;
	declare CONTINUE HANDLER FOR SQLSTATE '02000' SET norow=0;

   OPEN cur1;
   FETCH cur1 INTO _checkId,_paiSeId,_paiHao;
   WHILE ( norow=1 ) DO
    set _tmp=CONCAT('浙',SUBSTRING(_paiHao,1,1),'.',SUBSTRING(_paiHao,2));

    select id from carfile where paiSe=_paiSeId and licenseNumber=_tmp into _carId;
    

    if(norow=1)then
      update checknote set carId=_carId where id=_checkId;
    else
      set norow=1;
    end if;
    FETCH cur1 INTO _checkId,_paiSeId,_paiHao;

   END WHILE;
   CLOSE cur1;

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
