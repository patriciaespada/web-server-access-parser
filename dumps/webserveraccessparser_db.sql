CREATE DATABASE  IF NOT EXISTS `webserveraccessparser` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `webserveraccessparser`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: localhost    Database: webserveraccessparser
-- ------------------------------------------------------
-- Server version	5.6.24

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
-- Table structure for table `import_log_file`
--

DROP TABLE IF EXISTS `import_log_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_log_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `import_date` datetime NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `md5sum` varchar(35) NOT NULL,
  `status` enum('PROGRESS','FAIL','SUCCESS') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_log_file`
--

LOCK TABLES `import_log_file` WRITE;
/*!40000 ALTER TABLE `import_log_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_log_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server_access`
--

DROP TABLE IF EXISTS `server_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `import_log_file_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `ip` varchar(50) NOT NULL,
  `request` varchar(50) NOT NULL,
  `status` smallint(6) NOT NULL,
  `user_agent` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_server_access_import_log_file` (`import_log_file_id`),
  CONSTRAINT `fk_server_access_import_log_file` FOREIGN KEY (`import_log_file_id`) REFERENCES `import_log_file` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server_access`
--

LOCK TABLES `server_access` WRITE;
/*!40000 ALTER TABLE `server_access` DISABLE KEYS */;
/*!40000 ALTER TABLE `server_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server_blocked`
--

DROP TABLE IF EXISTS `server_blocked`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_blocked` (
  `ip` varchar(50) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `duration` enum('HOURLY','DAILY') NOT NULL,
  `threshold` smallint(6) NOT NULL,
  `number_requests` int(11) NOT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server_blocked`
--

LOCK TABLES `server_blocked` WRITE;
/*!40000 ALTER TABLE `server_blocked` DISABLE KEYS */;
/*!40000 ALTER TABLE `server_blocked` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'webserveraccessparser'
--

--
-- Dumping routines for database 'webserveraccessparser'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-28 15:56:26
