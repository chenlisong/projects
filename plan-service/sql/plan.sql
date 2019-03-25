/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.1.73 : Database - plan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`plan` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `plan`;

/*Table structure for table `t_day` */

DROP TABLE IF EXISTS `t_day`;

CREATE TABLE `t_day` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '时间，标记是哪一天',
  `score` tinyint(3) NOT NULL DEFAULT '0' COMMENT '得分1A,2B,3C,4D',
  `check` varchar(255) NOT NULL DEFAULT '' COMMENT '检查内容',
  `summary` varchar(255) NOT NULL DEFAULT '' COMMENT '总结',
  PRIMARY KEY (`pk_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_day` */

/*Table structure for table `t_hour` */

DROP TABLE IF EXISTS `t_hour`;

CREATE TABLE `t_hour` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `begin_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '结束时间',
  `content` varchar(127) NOT NULL DEFAULT '' COMMENT '内容',
  `complete` tinyint(3) NOT NULL DEFAULT '0' COMMENT '1未完成，2完成',
  `fk_day_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联的天的数据',
  PRIMARY KEY (`pk_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_hour` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(63) NOT NULL DEFAULT '' COMMENT '姓名',
  `password` varchar(63) NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` varchar(63) NOT NULL DEFAULT '',
  `qq` int(11) NOT NULL DEFAULT '0',
  `weixin` varchar(63) NOT NULL DEFAULT '',
  `birthday` datetime NOT NULL DEFAULT '1970-01-01 08:00:00',
  `sex` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00',
  PRIMARY KEY (`pk_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
