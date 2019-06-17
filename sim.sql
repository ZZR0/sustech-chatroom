CREATE DATABASE  IF NOT EXISTS `sim` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sim`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: sim
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chat_history`
--

DROP TABLE IF EXISTS `chat_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chat_history` (
  `msg_id` varchar(45) NOT NULL,
  `from_id` varchar(45) NOT NULL,
  `to_id` varchar(45) NOT NULL,
  `msg` varchar(256) NOT NULL,
  `send_time` datetime NOT NULL,
  `sign_flag` int(11) NOT NULL,
  `img_path` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`msg_id`),
  KEY `chat_history_fk1_idx` (`from_id`,`to_id`),
  KEY `chat_history_fk2_idx` (`to_id`),
  CONSTRAINT `chat_history_fk1` FOREIGN KEY (`from_id`) REFERENCES `users` (`id`),
  CONSTRAINT `chat_history_fk2` FOREIGN KEY (`to_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_history`
--

LOCK TABLES `chat_history` WRITE;
/*!40000 ALTER TABLE `chat_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_list`
--

DROP TABLE IF EXISTS `friend_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `friend_list` (
  `owner_id` varchar(45) NOT NULL,
  `friend_id` varchar(45) NOT NULL,
  `tag` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`owner_id`,`friend_id`),
  KEY `friend_list_fk2_idx` (`friend_id`),
  CONSTRAINT `friend_list_fk1` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`),
  CONSTRAINT `friend_list_fk2` FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_list`
--

LOCK TABLES `friend_list` WRITE;
/*!40000 ALTER TABLE `friend_list` DISABLE KEYS */;
INSERT INTO `friend_list` VALUES ('190523D8MFXS63R4','190523D8P5H1XGC0',NULL),('190523D8P5H1XGC0','190523D8MFXS63R4',NULL);
/*!40000 ALTER TABLE `friend_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_request`
--

DROP TABLE IF EXISTS `friend_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `friend_request` (
  `from_id` varchar(45) NOT NULL,
  `to_id` varchar(45) NOT NULL,
  `msg` varchar(64) DEFAULT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`from_id`,`to_id`),
  KEY `friend_request_fk2_idx` (`to_id`),
  CONSTRAINT `friend_request_fk1` FOREIGN KEY (`from_id`) REFERENCES `users` (`id`),
  CONSTRAINT `friend_request_fk2` FOREIGN KEY (`to_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_request`
--

LOCK TABLES `friend_request` WRITE;
/*!40000 ALTER TABLE `friend_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moment_comments`
--

DROP TABLE IF EXISTS `moment_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `moment_comments` (
  `moment_id` varchar(45) NOT NULL,
  `comment` varchar(256) NOT NULL,
  `from_id` varchar(45) NOT NULL,
  PRIMARY KEY (`moment_id`),
  KEY `moment_comments_fk1_idx` (`from_id`),
  CONSTRAINT `moment_comments_fk1` FOREIGN KEY (`from_id`) REFERENCES `users` (`id`),
  CONSTRAINT `moment_comments_fk2` FOREIGN KEY (`moment_id`) REFERENCES `moment_content` (`moment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment_comments`
--

LOCK TABLES `moment_comments` WRITE;
/*!40000 ALTER TABLE `moment_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `moment_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moment_content`
--

DROP TABLE IF EXISTS `moment_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `moment_content` (
  `moment_id` varchar(45) NOT NULL,
  `sender_id` varchar(45) NOT NULL,
  `content` varchar(256) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  PRIMARY KEY (`moment_id`),
  KEY `moment_content_fk1_idx` (`sender_id`),
  CONSTRAINT `moment_content_fk1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment_content`
--

LOCK TABLES `moment_content` WRITE;
/*!40000 ALTER TABLE `moment_content` DISABLE KEYS */;
INSERT INTO `moment_content` VALUES ('190523FT0PW8670H','190523D8MFXS63R4','cym cute','2019-05-23 12:46:06'),('190523G5NGDSHH94','190523D8MFXS63R4','cym cute','2019-05-23 13:18:08'),('190523G5PCWCW37C','190523D8MFXS63R4','cym cute!!!','2019-05-23 13:18:14'),('190523G641RYRR8H','190523D8P5H1XGC0','cym cute!!!!!!','2019-05-23 13:19:29'),('190523G68X6885WH','190523D8P5H1XGC0','handsome','2019-05-23 13:20:00');
/*!40000 ALTER TABLE `moment_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moment_img`
--

DROP TABLE IF EXISTS `moment_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `moment_img` (
  `moment_id` varchar(45) NOT NULL,
  `img_path` varchar(256) NOT NULL,
  `rank` int(11) NOT NULL,
  PRIMARY KEY (`moment_id`),
  CONSTRAINT `moment_img_fk1` FOREIGN KEY (`moment_id`) REFERENCES `moment_content` (`moment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment_img`
--

LOCK TABLES `moment_img` WRITE;
/*!40000 ALTER TABLE `moment_img` DISABLE KEYS */;
/*!40000 ALTER TABLE `moment_img` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moment_thumb_up`
--

DROP TABLE IF EXISTS `moment_thumb_up`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `moment_thumb_up` (
  `moment_id` varchar(45) NOT NULL,
  `from_id` varchar(45) NOT NULL,
  PRIMARY KEY (`moment_id`,`from_id`),
  KEY `moment_thumb_up_fk1_idx` (`from_id`),
  CONSTRAINT `moment_thumb_up_fk1` FOREIGN KEY (`from_id`) REFERENCES `users` (`id`),
  CONSTRAINT `moment_thumb_up_fk2` FOREIGN KEY (`moment_id`) REFERENCES `moment_content` (`moment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment_thumb_up`
--

LOCK TABLES `moment_thumb_up` WRITE;
/*!40000 ALTER TABLE `moment_thumb_up` DISABLE KEYS */;
INSERT INTO `moment_thumb_up` VALUES ('190523G641RYRR8H','190523D8P5H1XGC0');
/*!40000 ALTER TABLE `moment_thumb_up` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` varchar(45) NOT NULL,
  `username` varchar(16) NOT NULL,
  `password` varchar(45) NOT NULL,
  `nickname` varchar(16) DEFAULT NULL,
  `gpa` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('190523D8MFXS63R4','123','123','123',NULL),('190523D8P5H1XGC0','uhdiwei','uhihii','uhdiwei',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'sim'
--

--
-- Dumping routines for database 'sim'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-24 13:03:03
