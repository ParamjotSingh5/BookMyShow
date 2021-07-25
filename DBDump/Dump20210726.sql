-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: bookmyshow
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `idMovies` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Genre` varchar(45) DEFAULT NULL,
  `ScreeningStartDate` date NOT NULL,
  `ScreeningEndDate` date NOT NULL,
  `TheaterId` int DEFAULT NULL,
  `ShowtimeId` int DEFAULT NULL,
  PRIMARY KEY (`idMovies`),
  KEY `TheaterId_idx` (`TheaterId`),
  KEY `ShowtimeId_idx` (`ShowtimeId`),
  CONSTRAINT `ShowtimeId` FOREIGN KEY (`ShowtimeId`) REFERENCES `showtimes` (`idshowtimes`),
  CONSTRAINT `TheaterId` FOREIGN KEY (`TheaterId`) REFERENCES `theatre` (`idTheatre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'Inception','Thriller','2021-07-24','2021-08-03',1,1),(2,'Batman Begins','Action','2021-07-19','2021-07-29',1,2),(3,'Following','suspense thriller ','2021-07-04','2021-07-23',1,1),(4,'Dunkirk','History','2021-07-29','2021-08-08',1,2),(5,'Hera Pheri','Comedy','2021-07-04','2021-07-14',2,1),(6,'Pulp Fiction','Dark Comedy','2021-07-19','2021-07-29',2,2),(7,'Angrej','Comedy','2021-07-29','2021-08-08',2,2);
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `idOrder` int NOT NULL AUTO_INCREMENT,
  `OrderDate` datetime NOT NULL,
  `UserId` int NOT NULL,
  `OrdersummaryId` int NOT NULL,
  PRIMARY KEY (`idOrder`),
  KEY `UserId_idx` (`UserId`),
  KEY `OrdersummaryId_idx` (`OrdersummaryId`),
  CONSTRAINT `OrdersummaryId` FOREIGN KEY (`OrdersummaryId`) REFERENCES `ordersummary` (`idOrderSummary`),
  CONSTRAINT `UserId` FOREIGN KEY (`UserId`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (30,'2021-07-26 00:00:00',1,32);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordersummary`
--

DROP TABLE IF EXISTS `ordersummary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordersummary` (
  `idOrderSummary` int NOT NULL AUTO_INCREMENT,
  `SlotsBooked` int NOT NULL,
  `MovieId` int NOT NULL,
  `ReservationDate` date NOT NULL,
  PRIMARY KEY (`idOrderSummary`),
  KEY `MovieId_idx` (`MovieId`),
  CONSTRAINT `MovieId` FOREIGN KEY (`MovieId`) REFERENCES `movies` (`idMovies`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordersummary`
--

LOCK TABLES `ordersummary` WRITE;
/*!40000 ALTER TABLE `ordersummary` DISABLE KEYS */;
INSERT INTO `ordersummary` VALUES (32,5,1,'2021-07-27');
/*!40000 ALTER TABLE `ordersummary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `idRoles` int NOT NULL AUTO_INCREMENT,
  `Role` varchar(45) NOT NULL,
  PRIMARY KEY (`idRoles`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Admin'),(2,'Customer');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `showtimes`
--

DROP TABLE IF EXISTS `showtimes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `showtimes` (
  `idshowtimes` int NOT NULL AUTO_INCREMENT,
  `Shift` varchar(45) NOT NULL,
  PRIMARY KEY (`idshowtimes`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `showtimes`
--

LOCK TABLES `showtimes` WRITE;
/*!40000 ALTER TABLE `showtimes` DISABLE KEYS */;
INSERT INTO `showtimes` VALUES (1,'Morning'),(2,'Evening');
/*!40000 ALTER TABLE `showtimes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `theatre`
--

DROP TABLE IF EXISTS `theatre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `theatre` (
  `idTheatre` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Slots` int NOT NULL,
  `Location` varchar(45) NOT NULL,
  PRIMARY KEY (`idTheatre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `theatre`
--

LOCK TABLES `theatre` WRITE;
/*!40000 ALTER TABLE `theatre` DISABLE KEYS */;
INSERT INTO `theatre` VALUES (1,'Mohini_Theater',60,'Kharrar'),(2,'Star_Fishries',55,'Sector 67 CHD'),(3,'Golden',15,'Shimla'),(4,'Silver',70,'Russia'),(5,'Copper',54,'Sweden');
/*!40000 ALTER TABLE `theatre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `CreatedOn` datetime NOT NULL,
  `UpdatedOn` datetime NOT NULL,
  `Name` varchar(45) NOT NULL,
  `RoleId` int NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  KEY `RoleId_idx` (`RoleId`),
  CONSTRAINT `RoleId` FOREIGN KEY (`RoleId`) REFERENCES `roles` (`idRoles`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'paramjot.dhiman@gmail.com','12345','2021-07-14 00:00:00','2021-07-14 00:00:00','Paramjot',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-26  2:36:28
