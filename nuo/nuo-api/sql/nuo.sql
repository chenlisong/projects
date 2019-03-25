CREATE DATABASE  IF NOT EXISTS `nuo` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `nuo`;
-- MySQL dump 10.13  Distrib 5.6.11, for osx10.6 (i386)
--
-- Host: 47.100.24.40    Database: nuo
-- ------------------------------------------------------
-- Server version	5.7.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_class`
--

DROP TABLE IF EXISTS `t_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_class` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_teacher_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '班主任ID',
  `grade` tinyint(4) NOT NULL DEFAULT '0' COMMENT '年纪1-6',
  `number` tinyint(4) NOT NULL DEFAULT '0' COMMENT '班号01-99',
  `fk_school_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '归属学校',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_course`
--

DROP TABLE IF EXISTS `t_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_course` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(31) NOT NULL DEFAULT '' COMMENT '课程介绍。例如“数学辅导课”，“名师数学辅导”',
  `begin_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '课程开始时间',
  `end_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '课程结束时间',
  `fk_teacher_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '老师ID',
  `fk_class_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '班级ID',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '放置一些课程介绍等',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_school`
--

DROP TABLE IF EXISTS `t_school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_school` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '学校名称',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_score`
--

DROP TABLE IF EXISTS `t_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_score` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1考试，2作业',
  `fk_student_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '学生',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '成绩分数',
  `pic` varchar(255) NOT NULL DEFAULT '’‘' COMMENT '成绩照片地址',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_student`
--

DROP TABLE IF EXISTS `t_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_student` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL DEFAULT '' COMMENT '学生性别，默认''创建者的孩子''',
  `birthday` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '学生生日',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别，1男，2女',
  `pic` varchar(255) NOT NULL DEFAULT '' COMMENT '头像地址',
  `fk_class_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '班级id',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态，1不在学习中，2在学习中',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wechat_openid` varchar(45) NOT NULL DEFAULT '' COMMENT '微信唯一账号',
  `wechat_nickname` varchar(63) NOT NULL DEFAULT '' COMMENT '微信名',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别，1男，2女',
  `birthday` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '生日',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  `avatar_url` varchar(255) NOT NULL DEFAULT '' COMMENT '微信头像',
  `city` varchar(15) NOT NULL DEFAULT '' COMMENT '城市',
  `province` varchar(15) NOT NULL DEFAULT '' COMMENT '省份',
  `country` varchar(15) NOT NULL DEFAULT '' COMMENT '国家',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_student`
--

DROP TABLE IF EXISTS `t_user_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_student` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `fk_student_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '学生id',
  `relationship` tinyint(4) NOT NULL DEFAULT '0' COMMENT '关系，1自己，2父亲，3母亲，4爷爷，5奶奶，6姑姑，7姑父，8姨，9姨父, 10创建者',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-02 20:14:22
