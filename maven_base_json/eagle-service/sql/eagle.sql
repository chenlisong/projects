/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.24 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`eagle` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `eagle`;

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
  `username` varchar(15) DEFAULT '' COMMENT '昵称',
  `password` varchar(16) DEFAULT '' COMMENT '加密后的密码',
  `create_time` datetime DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
   PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;