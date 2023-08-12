-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: mgdsn230809.mysql.database.azure.com    Database: pbl230801
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `my_row_id` bigint unsigned NOT NULL AUTO_INCREMENT /*!80023 INVISIBLE */,
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`my_row_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` (`my_row_id`, `ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES (1,'00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2023-08-11 05:59:06',1,'EXECUTED','8:25a42a33f11e537c81e58aad4cc84729','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, referencedTableName=jhi_authority; addForeig...','',NULL,'4.15.0',NULL,NULL,'1733545603'),(2,'20230811031229-1','jhipster','config/liquibase/changelog/20230811031229_added_entity_Team.xml','2023-08-11 05:59:06',2,'EXECUTED','8:d314f545b52aadebd6cb53371ed7d9e9','createTable tableName=team; dropDefaultValue columnName=lst_mtn_dt, tableName=team','',NULL,'4.15.0',NULL,NULL,'1733545603'),(3,'20230811031230-1','jhipster','config/liquibase/changelog/20230811031230_added_entity_Player.xml','2023-08-11 05:59:06',3,'EXECUTED','8:aebf43592ad0a92b6b65615749cb34ee','createTable tableName=player; dropDefaultValue columnName=lst_mtn_dt, tableName=player','',NULL,'4.15.0',NULL,NULL,'1733545603'),(4,'20230811031231-1','jhipster','config/liquibase/changelog/20230811031231_added_entity_TeamPlayer.xml','2023-08-11 05:59:07',4,'EXECUTED','8:8dea7d64e7b783a30ba34b434dc3bc98','createTable tableName=team_player; dropDefaultValue columnName=lst_mtn_dt, tableName=team_player','',NULL,'4.15.0',NULL,NULL,'1733545603'),(5,'20230811031232-1','jhipster','config/liquibase/changelog/20230811031232_added_entity_EventZ.xml','2023-08-11 05:59:07',5,'EXECUTED','8:9e4e79232602301a22b5ae21825a17e3','createTable tableName=event_z; dropDefaultValue columnName=evnt_dt, tableName=event_z; dropDefaultValue columnName=event_beg_time, tableName=event_z; dropDefaultValue columnName=event_end_time, tableName=event_z; dropDefaultValue columnName=lst_mt...','',NULL,'4.15.0',NULL,NULL,'1733545603'),(6,'20230811031233-1','jhipster','config/liquibase/changelog/20230811031233_added_entity_EventPlayer.xml','2023-08-11 05:59:08',6,'EXECUTED','8:65a1566444397b3b3276eedb0a74a4ac','createTable tableName=event_player; dropDefaultValue columnName=lst_mtn_dt, tableName=event_player','',NULL,'4.15.0',NULL,NULL,'1733545603'),(7,'20230811031234-1','jhipster','config/liquibase/changelog/20230811031234_added_entity_MatchZ.xml','2023-08-11 05:59:08',7,'EXECUTED','8:60d53cea3aee167db3b670a10a66dbf4','createTable tableName=match_z; dropDefaultValue columnName=mtch_end_time, tableName=match_z; dropDefaultValue columnName=lst_mtn_dt, tableName=match_z','',NULL,'4.15.0',NULL,NULL,'1733545603'),(8,'20230811031235-1','jhipster','config/liquibase/changelog/20230811031235_added_entity_MatchPlayer.xml','2023-08-11 05:59:09',8,'EXECUTED','8:d061a36a0a9620c2f9e29920789e956e','createTable tableName=match_player; dropDefaultValue columnName=mtch_end_time, tableName=match_player; dropDefaultValue columnName=lst_mtn_dt, tableName=match_player','',NULL,'4.15.0',NULL,NULL,'1733545603'),(9,'20230811031236-1','mgdsn-weichieh09','config/liquibase/changelog/20230811031236_added_entity_VwEventResult.xml','2023-08-11 05:59:09',9,'EXECUTED','8:0e1c21921d3b92688023969e81762ae8','createView viewName=vw_event_result','',NULL,'4.15.0',NULL,NULL,'1733545603');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_player`
--

DROP TABLE IF EXISTS `event_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_player` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `e_id` bigint DEFAULT NULL,
  `p_id` bigint DEFAULT NULL,
  `chk_fg` varchar(255) DEFAULT NULL,
  `lst_mtn_usr` varchar(255) DEFAULT NULL,
  `lst_mtn_dt` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_player`
--

LOCK TABLES `event_player` WRITE;
/*!40000 ALTER TABLE `event_player` DISABLE KEYS */;
INSERT INTO `event_player` VALUES (1,1,1,'N','MGDsn','2023-08-07 00:01:09.437000'),(2,1,2,'N','MGDsn','2023-08-07 00:01:09.437000'),(3,1,3,'N','MGDsn','2023-08-07 00:01:09.437000'),(4,1,4,'N','MGDsn','2023-08-07 00:01:09.437000'),(5,1,5,'N','MGDsn','2023-08-07 00:01:09.440000'),(6,1,6,'N','MGDsn','2023-08-07 00:01:09.440000'),(7,1,7,'N','MGDsn','2023-08-07 00:01:09.440000'),(8,1,8,'N','MGDsn','2023-08-07 00:01:09.440000');
/*!40000 ALTER TABLE `event_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_z`
--

DROP TABLE IF EXISTS `event_z`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_z` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `evnt_nm` varchar(255) DEFAULT NULL,
  `evnt_dt` datetime(6),
  `venue` varchar(255) DEFAULT NULL,
  `event_beg_time` datetime(6),
  `event_end_time` datetime(6),
  `lst_mtn_usr` varchar(255) DEFAULT NULL,
  `lst_mtn_dt` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_z`
--

LOCK TABLES `event_z` WRITE;
/*!40000 ALTER TABLE `event_z` DISABLE KEYS */;
INSERT INTO `event_z` VALUES (1,'爭分奪勝搶水果','2023-08-01 00:00:00.000000','台藝大','2023-08-01 09:00:00.000000','2023-08-01 12:00:00.000000','MGDsn','2023-08-10 08:13:50.000000'),(2,'棒打鴛鴦','2023-08-05 00:00:00.000000','羽協','2023-08-05 13:00:00.000000','2023-08-05 15:00:00.000000','MGDsn','2023-08-10 14:10:59.000000'),(3,'魔王挑戰晉級賽','2023-08-10 00:00:00.000000','鑫高手','2023-08-10 06:00:00.000000','2023-08-10 09:00:00.000000','MGDsn','2023-08-10 00:02:16.000000');
/*!40000 ALTER TABLE `event_z` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','',_binary '','zh-tw',NULL,NULL,'system',NULL,NULL,'system',NULL),(2,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',_binary '','zh-tw',NULL,NULL,'system',NULL,NULL,'system',NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user_authority` (
  `my_row_id` bigint unsigned NOT NULL AUTO_INCREMENT /*!80023 INVISIBLE */,
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`my_row_id`),
  KEY `fk_authority_name` (`authority_name`),
  KEY `fk_user_id` (`user_id`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` (`my_row_id`, `user_id`, `authority_name`) VALUES (1,1,'ROLE_ADMIN'),(2,1,'ROLE_USER'),(3,2,'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match_player`
--

DROP TABLE IF EXISTS `match_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `match_player` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `m_id` bigint DEFAULT NULL,
  `p_id` bigint DEFAULT NULL,
  `e_id` bigint DEFAULT NULL,
  `mtch_end_time` datetime(6),
  `score` varchar(255) DEFAULT NULL,
  `win_fg` varchar(255) DEFAULT NULL,
  `lst_mtn_usr` varchar(255) DEFAULT NULL,
  `lst_mtn_dt` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match_player`
--

LOCK TABLES `match_player` WRITE;
/*!40000 ALTER TABLE `match_player` DISABLE KEYS */;
INSERT INTO `match_player` VALUES (11,1,1,1,'2023-08-01 09:10:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(12,1,2,1,'2023-08-01 09:10:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(13,1,3,1,'2023-08-01 09:10:00.000000','16','N','MGDsn','2023-08-06 19:16:29.803000'),(14,1,4,1,'2023-08-01 09:10:00.000000','16','N','MGDsn','2023-08-06 19:16:29.803000'),(21,2,1,1,'2023-08-01 09:12:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(22,2,2,1,'2023-08-01 09:12:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(23,2,5,1,'2023-08-01 09:12:00.000000','17','N','MGDsn','2023-08-06 19:16:29.803000'),(24,2,6,1,'2023-08-01 09:12:00.000000','17','N','MGDsn','2023-08-06 19:16:29.803000'),(31,3,1,1,'2023-08-01 09:15:00.000000','15','N','MGDsn','2023-08-06 19:16:29.803000'),(32,3,2,1,'2023-08-01 09:15:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(33,3,3,1,'2023-08-01 09:15:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(34,3,4,1,'2023-08-01 09:15:00.000000','15','N','MGDsn','2023-08-06 19:16:29.803000'),(41,4,1,1,'2023-08-01 09:20:00.000000','23','Y','MGDsn','2023-08-06 19:16:29.803000'),(42,4,3,1,'2023-08-01 09:20:00.000000','23','Y','MGDsn','2023-08-06 19:16:29.803000'),(43,4,4,1,'2023-08-01 09:20:00.000000','21','N','MGDsn','2023-08-06 19:16:29.803000'),(44,4,7,1,'2023-08-01 09:20:00.000000','21','N','MGDsn','2023-08-06 19:16:29.803000'),(51,5,2,1,'2023-08-01 09:30:00.000000','12','N','MGDsn','2023-08-06 19:16:29.803000'),(52,5,5,1,'2023-08-01 09:30:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(53,5,7,1,'2023-08-01 09:30:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(54,5,8,1,'2023-08-01 09:30:00.000000','12','N','MGDsn','2023-08-06 19:16:29.803000'),(61,6,1,1,'2023-08-01 09:35:00.000000','23','N','MGDsn','2023-08-06 19:16:29.803000'),(62,6,3,1,'2023-08-01 09:35:00.000000','23','N','MGDsn','2023-08-06 19:16:29.803000'),(63,6,4,1,'2023-08-01 09:35:00.000000','25','Y','MGDsn','2023-08-06 19:16:29.803000'),(64,6,7,1,'2023-08-01 09:35:00.000000','25','Y','MGDsn','2023-08-06 19:16:29.803000'),(71,7,1,1,'2023-08-01 09:37:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(72,7,3,1,'2023-08-01 09:37:00.000000','11','N','MGDsn','2023-08-06 19:16:29.803000'),(73,7,7,1,'2023-08-01 09:37:00.000000','11','N','MGDsn','2023-08-06 19:16:29.803000'),(74,7,8,1,'2023-08-01 09:37:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(81,8,1,1,'2023-08-01 09:45:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000'),(82,8,2,1,'2023-08-01 09:45:00.000000','18','N','MGDsn','2023-08-06 19:16:29.803000'),(83,8,6,1,'2023-08-01 09:45:00.000000','18','N','MGDsn','2023-08-06 19:16:29.803000'),(84,8,7,1,'2023-08-01 09:45:00.000000','21','Y','MGDsn','2023-08-06 19:16:29.803000');
/*!40000 ALTER TABLE `match_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match_z`
--

DROP TABLE IF EXISTS `match_z`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `match_z` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `e_id` bigint DEFAULT NULL,
  `mtch_end_time` datetime(6),
  `w_plyr_1` varchar(255) DEFAULT NULL,
  `w_plyr_2` varchar(255) DEFAULT NULL,
  `w_scr` varchar(255) DEFAULT NULL,
  `l_plyr_1` varchar(255) DEFAULT NULL,
  `l_plyr_2` varchar(255) DEFAULT NULL,
  `l_scr` varchar(255) DEFAULT NULL,
  `lst_mtn_usr` varchar(255) DEFAULT NULL,
  `lst_mtn_dt` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match_z`
--

LOCK TABLES `match_z` WRITE;
/*!40000 ALTER TABLE `match_z` DISABLE KEYS */;
INSERT INTO `match_z` VALUES (1,1,'2023-08-01 09:10:00.000000','1','2','21','3','4','16','MGDsn','2023-08-06 19:05:50.843000'),(2,1,'2023-08-01 09:12:00.000000','1','2','21','5','6','17','MGDsn','2023-08-06 19:05:50.843000'),(3,1,'2023-08-01 09:15:00.000000','2','3','21','1','4','15','MGDsn','2023-08-06 19:05:50.843000'),(4,1,'2023-08-01 09:20:00.000000','3','1','23','4','7','21','MGDsn','2023-08-06 19:05:50.847000'),(5,1,'2023-08-01 09:30:00.000000','5','7','21','8','2','12','MGDsn','2023-08-06 19:05:50.847000'),(6,1,'2023-08-01 09:35:00.000000','4','7','25','1','3','23','MGDsn','2023-08-06 19:05:50.847000'),(7,1,'2023-08-01 09:37:00.000000','8','1','21','7','3','11','MGDsn','2023-08-06 19:05:50.847000'),(8,1,'2023-08-01 09:45:00.000000','1','7','21','6','2','18','MGDsn','2023-08-06 19:05:50.850000');
/*!40000 ALTER TABLE `match_z` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `plyr_nm` varchar(255) DEFAULT NULL,
  `plyr_lvl` varchar(255) DEFAULT NULL,
  `lst_mtn_usr` varchar(255) DEFAULT NULL,
  `lst_mtn_dt` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'戴姿穎','18','MGDsn','2023-08-10 19:23:56.647000'),(2,'大堀彩','16','MGDsn','2023-08-10 19:23:56.650000'),(3,'安洗瑩','12','MGDsn','2023-08-10 19:23:56.650000'),(4,'周天成','16','MGDsn','2023-08-10 19:23:56.650000'),(5,'王齊麟','15','MGDsn','2023-08-10 19:23:56.650000'),(6,'李洋','15','MGDsn','2023-08-10 19:23:56.650000'),(7,'山口茜','12','MGDsn','2023-08-10 19:23:56.653000'),(8,'陳雨菲','13','MGDsn','2023-08-10 19:23:56.653000');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `team_nm` varchar(255) DEFAULT NULL,
  `lst_mtn_usr` varchar(255) DEFAULT NULL,
  `lst_mtn_dt` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,'夢想號不用燃料隊','MGDsn','2023-08-06 19:05:50.837000'),(2,'大聯盟隊','MGDsn','2023-08-06 19:05:50.837000'),(3,'運動家羽球隊','MGDsn','2023-08-06 19:05:50.837000');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_player`
--

DROP TABLE IF EXISTS `team_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_player` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `t_id` bigint DEFAULT NULL,
  `p_id` bigint DEFAULT NULL,
  `lst_mtn_usr` varchar(255) DEFAULT NULL,
  `lst_mtn_dt` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_player`
--

LOCK TABLES `team_player` WRITE;
/*!40000 ALTER TABLE `team_player` DISABLE KEYS */;
INSERT INTO `team_player` VALUES (1,1,1,'MGDsn','2023-08-06 19:05:50.840000'),(2,1,2,'MGDsn','2023-08-06 19:05:50.840000'),(3,1,3,'MGDsn','2023-08-06 19:05:50.840000'),(4,2,4,'MGDsn','2023-08-06 19:05:50.840000'),(5,2,5,'MGDsn','2023-08-06 19:05:50.840000'),(6,2,6,'MGDsn','2023-08-06 19:05:50.840000');
/*!40000 ALTER TABLE `team_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_event_result`
--

DROP TABLE IF EXISTS `vw_event_result`;
/*!50001 DROP VIEW IF EXISTS `vw_event_result`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_event_result` AS SELECT 
 1 AS `id`,
 1 AS `e_id`,
 1 AS `p_id`,
 1 AS `m_id`,
 1 AS `win_fg`,
 1 AS `plyr_nm`,
 1 AS `plyr_lvl`,
 1 AS `mtch_end_time`,
 1 AS `tot_matchs`,
 1 AS `tot_wins`,
 1 AS `acml_wins`,
 1 AS `chk_fg`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'pbl230801'
--

--
-- Final view structure for view `vw_event_result`
--

/*!50001 DROP VIEW IF EXISTS `vw_event_result`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mgdsn230809`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_event_result` AS select row_number() OVER (ORDER BY `s1`.`e_id` )  AS `id`,`s1`.`e_id` AS `e_id`,`s1`.`p_id` AS `p_id`,`s1`.`m_id` AS `m_id`,`s1`.`win_fg` AS `win_fg`,`s2`.`plyr_nm` AS `plyr_nm`,`s2`.`plyr_lvl` AS `plyr_lvl`,`s1`.`mtch_end_time` AS `mtch_end_time`,count(`s1`.`m_id`) OVER (PARTITION BY `s1`.`e_id`,`s1`.`p_id` )  AS `tot_matchs`,sum((case when (`s1`.`win_fg` = 'Y') then 1 else 0 end)) OVER (PARTITION BY `s1`.`e_id`,`s1`.`p_id` )  AS `tot_wins`,(case when (`s1`.`win_fg` = 'Y') then row_number() OVER (PARTITION BY `s1`.`e_id`,`s1`.`p_id`,`s1`.`win_fg` ORDER BY `s1`.`mtch_end_time` )  end) AS `acml_wins`,`s3`.`chk_fg` AS `chk_fg` from ((`match_player` `s1` left join `player` `s2` on((`s2`.`id` = `s1`.`p_id`))) left join `event_player` `s3` on(((`s3`.`e_id` = `s1`.`e_id`) and (`s3`.`p_id` = `s1`.`p_id`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-12 12:46:56
