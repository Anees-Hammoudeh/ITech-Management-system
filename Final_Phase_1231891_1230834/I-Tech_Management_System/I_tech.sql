CREATE DATABASE  IF NOT EXISTS `itech_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `itech_db`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: itech_db
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `Brand_ID` int NOT NULL AUTO_INCREMENT,
  `Brand_Name` varchar(100) NOT NULL,
  `Website` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Brand_ID`),
  UNIQUE KEY `Brand_Name` (`Brand_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'Apple','https://www.apple.com'),(2,'Samsung','https://www.samsung.com'),(3,'Dell','https://www.dell.com'),(4,'HP','https://www.hp.com'),(5,'Lenovo','https://www.lenovo.com'),(6,'Sony','https://www.sony.com'),(7,'Logitech','https://www.logitech.com'),(8,'Asus','https://www.asus.com');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `Category_ID` int NOT NULL AUTO_INCREMENT,
  `Category_Name` varchar(100) NOT NULL,
  `Unit_Size` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`Category_ID`),
  UNIQUE KEY `Category_Name` (`Category_Name`),
  CONSTRAINT `category_chk_1` CHECK ((`Unit_Size` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Smartphone',2),(2,'Laptop',5),(3,'Tablet',3),(4,'Accessory',1),(5,'Audio',1),(6,'Gaming',5);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `Customer_ID` int NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(50) NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  PRIMARY KEY (`Customer_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Walk-in','Customer'),(2,'Omar','Khaled'),(3,'Lina','Ahmad'),(4,'Samer','Nassar'),(5,'Hiba','Saleh'),(6,'Tariq','Mansour'),(7,'Noor','Zaid'),(8,'Yara','Hammad'),(9,'Adam','Qasem'),(10,'Khaled','Mansour'),(11,'Rania','Haddad'),(12,'Faris','Barakat'),(13,'Dina','Nabulsi'),(14,'Ziad','Awad'),(15,'Samar','Khalil'),(16,'Bilal','Issa'),(17,'Nadia','Freij'),(18,'Tamer','Salah'),(19,'Hala','Qasim'),(20,'Jawad','Jaber');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_phone`
--

DROP TABLE IF EXISTS `customer_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_phone` (
  `Customer_ID` int NOT NULL,
  `Phone` varchar(20) NOT NULL,
  PRIMARY KEY (`Customer_ID`,`Phone`),
  CONSTRAINT `customer_phone_ibfk_1` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`Customer_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_phone`
--

LOCK TABLES `customer_phone` WRITE;
/*!40000 ALTER TABLE `customer_phone` DISABLE KEYS */;
INSERT INTO `customer_phone` VALUES (1,'0000000000'),(2,'0597002222'),(3,'0597003333'),(4,'0597004444'),(5,'0597005555'),(6,'0597006666'),(7,'0597007777'),(8,'0597008888'),(9,'0597009999'),(10,'0597010000'),(11,'0597011111'),(12,'0597012222'),(13,'0597013333'),(14,'0597014444'),(15,'0597015555'),(16,'0597016666'),(17,'0597017777'),(18,'0597018888'),(19,'0597019999'),(20,'0597020000');
/*!40000 ALTER TABLE `customer_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `Employee_ID` int NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(50) NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  `Position` varchar(50) NOT NULL,
  `Salary` decimal(10,2) NOT NULL,
  `Supervisor_ID` int DEFAULT NULL,
  PRIMARY KEY (`Employee_ID`),
  KEY `Supervisor_ID` (`Supervisor_ID`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`Supervisor_ID`) REFERENCES `employee` (`Employee_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `employee_chk_1` CHECK ((`Salary` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Ahmad','Saleh','Manager',2500.00,NULL),(2,'Mona','Ali','Manager',2500.00,NULL),(3,'Rami','Hassan','Accountant',1400.00,1),(4,'Lina','Omar','Accountant',1200.00,1),(5,'Sami','Naser','Sales Employee',1300.00,1),(6,'Huda','Khaled','Sales Employee',1100.00,1),(7,'Omar','Taha','Warehouse Employee',1200.00,1),(8,'Noor','Yousef','Warehouse Employee',1000.00,1),(9,'Kareem','Said','Cashier',1100.00,1),(10,'Sara','Mansour','Cashier',900.00,1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_phone`
--

DROP TABLE IF EXISTS `employee_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_phone` (
  `Employee_ID` int NOT NULL,
  `Phone` varchar(20) NOT NULL,
  PRIMARY KEY (`Employee_ID`,`Phone`),
  CONSTRAINT `employee_phone_ibfk_1` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_phone`
--

LOCK TABLES `employee_phone` WRITE;
/*!40000 ALTER TABLE `employee_phone` DISABLE KEYS */;
INSERT INTO `employee_phone` VALUES (1,'022981234'),(1,'0598001111'),(2,'0598002222'),(2,'0598003333');
/*!40000 ALTER TABLE `employee_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `Payment_ID` int NOT NULL AUTO_INCREMENT,
  `Sale_ID` int NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `Payment_Method` varchar(50) NOT NULL,
  `Payment_Status` varchar(50) NOT NULL,
  `Payment_Date` date NOT NULL,
  PRIMARY KEY (`Payment_ID`),
  KEY `Sale_ID` (`Sale_ID`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`Sale_ID`) REFERENCES `sale` (`Sale_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `payment_chk_1` CHECK ((`Amount` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,975.00,'Cash','Paid','2026-04-18'),(2,2,500.00,'Installment','Partial','2026-04-18'),(3,2,375.00,'Installment','Paid','2026-05-18'),(4,3,686.00,'Credit Card','Paid','2026-04-19'),(5,4,152.00,'Cash','Paid','2026-04-20'),(6,5,800.00,'Installment','Partial','2026-04-21'),(7,5,685.00,'Installment','Paid','2026-05-21'),(8,6,420.00,'Cash','Paid','2026-04-22'),(9,7,520.00,'Credit Card','Paid','2026-04-23'),(10,8,229.00,'Cash','Paid','2026-04-24'),(11,9,400.00,'Installment','Partial','2026-04-25'),(12,9,330.00,'Installment','Paid','2026-05-25'),(13,10,93.00,'Cash','Paid','2026-04-26'),(14,11,1684.00,'Cash','Paid','2026-05-01'),(15,12,800.00,'Installment','Partial','2026-05-03'),(16,13,524.00,'Credit Card','Paid','2026-05-05'),(17,14,211.00,'Cash','Paid','2026-05-08'),(18,15,700.00,'Installment','Partial','2026-05-10'),(19,16,260.00,'Cash','Paid','2026-05-12'),(20,17,630.00,'Credit Card','Paid','2026-05-15'),(21,18,211.00,'Cash','Paid','2026-05-18'),(22,19,975.00,'Cash','Paid','2026-06-01'),(23,20,400.00,'Cash','Paid','2026-06-05'),(24,21,127.00,'Cash','Paid','2026-06-10');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `Product_ID` int NOT NULL AUTO_INCREMENT,
  `Product_Name` varchar(100) NOT NULL,
  `Model` varchar(100) DEFAULT NULL,
  `Price` decimal(10,2) NOT NULL,
  `Category_ID` int NOT NULL,
  `Brand_ID` int NOT NULL,
  PRIMARY KEY (`Product_ID`),
  KEY `Category_ID` (`Category_ID`),
  KEY `Brand_ID` (`Brand_ID`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`Category_ID`) REFERENCES `category` (`Category_ID`) ON UPDATE CASCADE,
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`Brand_ID`) REFERENCES `brand` (`Brand_ID`) ON UPDATE CASCADE,
  CONSTRAINT `product_chk_1` CHECK ((`Price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'iPhone 15','A3090',950.00,1,1),(2,'Samsung Galaxy S24','SM-S921',820.00,1,2),(3,'Samsung Galaxy A55','SM-A556',380.00,1,2),(4,'Dell Inspiron 15','3520',650.00,2,3),(5,'HP Pavilion 14','14-dv2000',720.00,2,4),(6,'Lenovo IdeaPad 3','15IAU7',580.00,2,5),(7,'iPad 10th Gen','A2696',470.00,3,1),(8,'Logitech Wireless Mouse','M185',18.00,4,7),(9,'Logitech Keyboard','K380',42.00,4,7),(10,'Sony WH-CH720N','WH-CH720N',120.00,5,6),(11,'Samsung Galaxy Buds FE','SM-R400',75.00,5,2),(12,'Asus ROG Gaming Laptop','G16',1450.00,6,8),(13,'Apple USB-C Charger','20W',25.00,4,1),(14,'Samsung Fast Charger','25W',20.00,4,2),(15,'Logitech Webcam','C920',85.00,4,7),(16,'Dell Gaming Mouse','GM500',35.00,4,3),(17,'HP USB Flash Drive','64GB',12.00,4,4),(18,'Lenovo Tab M10','TB328FU',210.00,3,5),(19,'Gaming Keyboard','GK500',120.00,4,2),(20,'Wireless Charger','WC100',35.00,4,3);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `Purchase_ID` int NOT NULL AUTO_INCREMENT,
  `Employee_ID` int NOT NULL,
  `Supplier_ID` int NOT NULL,
  `Purchase_Date` date NOT NULL,
  `Delivery_Date` date DEFAULT NULL,
  `Purchase_Status` varchar(20) DEFAULT 'Pending',
  PRIMARY KEY (`Purchase_ID`),
  KEY `Employee_ID` (`Employee_ID`),
  KEY `Supplier_ID` (`Supplier_ID`),
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`) ON UPDATE CASCADE,
  CONSTRAINT `purchase_ibfk_2` FOREIGN KEY (`Supplier_ID`) REFERENCES `supplier` (`Supplier_ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,2,1,'2026-04-01','2026-04-03','Received'),(2,2,2,'2026-04-05','2026-04-07','Received'),(3,5,3,'2026-04-10','2026-04-12','Received'),(4,2,4,'2026-04-15','2026-04-16','Received'),(5,5,2,'2026-04-20','2026-04-22','Received'),(6,2,3,'2026-04-25','2026-04-27','Received'),(7,2,1,'2026-05-01','2026-05-03','Received'),(8,5,2,'2026-05-05','2026-05-07','Received'),(9,2,3,'2026-05-10','2026-05-12','Received'),(10,5,4,'2026-05-15','2026-05-17','Received'),(11,2,1,'2026-06-01','2026-06-03','Received'),(12,5,3,'2026-06-10',NULL,'Pending');
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_details`
--

DROP TABLE IF EXISTS `purchase_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_details` (
  `Purchase_ID` int NOT NULL,
  `Product_ID` int NOT NULL,
  `Unit_Price` decimal(10,2) NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`Purchase_ID`,`Product_ID`),
  KEY `Product_ID` (`Product_ID`),
  CONSTRAINT `purchase_details_ibfk_1` FOREIGN KEY (`Purchase_ID`) REFERENCES `purchase` (`Purchase_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_details_ibfk_2` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`Product_ID`) ON UPDATE CASCADE,
  CONSTRAINT `purchase_details_chk_1` CHECK ((`Quantity` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_details`
--

LOCK TABLES `purchase_details` WRITE;
/*!40000 ALTER TABLE `purchase_details` DISABLE KEYS */;
INSERT INTO `purchase_details` VALUES (1,1,850.00,8),(1,7,400.00,6),(1,13,18.00,30),(2,2,730.00,7),(2,3,310.00,10),(2,11,55.00,20),(2,14,14.00,25),(3,4,560.00,6),(3,5,620.00,5),(3,6,500.00,7),(3,12,1280.00,3),(4,8,10.00,40),(4,9,30.00,25),(4,10,90.00,12),(4,15,62.00,10),(5,2,730.00,4),(5,11,55.00,15),(6,16,25.00,20),(6,17,7.00,50),(6,18,160.00,8),(7,1,850.00,5),(7,2,730.00,5),(7,13,18.00,50),(8,8,10.00,60),(8,9,30.00,40),(8,11,55.00,20),(8,14,14.00,30),(9,4,560.00,4),(9,5,620.00,4),(9,6,500.00,5),(10,10,90.00,10),(10,15,62.00,8),(10,16,25.00,15),(11,1,850.00,6),(11,3,310.00,8),(11,7,400.00,4),(12,12,1280.00,2),(12,18,160.00,5);
/*!40000 ALTER TABLE `purchase_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_record`
--

DROP TABLE IF EXISTS `return_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return_record` (
  `Return_ID` int NOT NULL AUTO_INCREMENT,
  `Sale_ID` int NOT NULL,
  `Product_ID` int NOT NULL,
  `Return_Date` date NOT NULL,
  `Quantity` int NOT NULL,
  `Return_Reason` varchar(200) DEFAULT NULL,
  `Return_Status` varchar(50) NOT NULL,
  PRIMARY KEY (`Return_ID`),
  KEY `Sale_ID` (`Sale_ID`,`Product_ID`),
  CONSTRAINT `return_record_ibfk_1` FOREIGN KEY (`Sale_ID`, `Product_ID`) REFERENCES `sale_details` (`Sale_ID`, `Product_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_record`
--

LOCK TABLES `return_record` WRITE;
/*!40000 ALTER TABLE `return_record` DISABLE KEYS */;
INSERT INTO `return_record` VALUES (1,6,14,'2026-04-24',1,'Wrong charger type','Approved'),(2,10,11,'2026-04-28',1,'Audio issue','Approved'),(3,11,9,'2026-05-04',1,'Changed mind','Approved'),(4,13,8,'2026-05-08',1,'Defective item','Approved'),(5,16,11,'2026-05-15',1,'Sound quality issue','Approved'),(6,14,17,'2026-05-10',2,'No valid reason','Rejected'),(7,20,14,'2026-06-07',1,'Opened package','Rejected'),(8,15,12,'2026-05-14',1,'Screen flickering','Pending'),(9,19,1,'2026-06-04',1,'Battery issue','Pending');
/*!40000 ALTER TABLE `return_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale` (
  `Sale_ID` int NOT NULL AUTO_INCREMENT,
  `Employee_ID` int NOT NULL,
  `Customer_ID` int NOT NULL,
  `Sale_Date` date NOT NULL,
  PRIMARY KEY (`Sale_ID`),
  KEY `Employee_ID` (`Employee_ID`),
  KEY `Customer_ID` (`Customer_ID`),
  CONSTRAINT `sale_ibfk_1` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`) ON UPDATE CASCADE,
  CONSTRAINT `sale_ibfk_2` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`Customer_ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES (1,2,1,'2026-04-18'),(2,2,3,'2026-04-18'),(3,4,1,'2026-04-19'),(4,2,5,'2026-04-20'),(5,4,6,'2026-04-21'),(6,2,1,'2026-04-22'),(7,4,2,'2026-04-23'),(8,2,8,'2026-04-24'),(9,4,9,'2026-04-25'),(10,2,4,'2026-04-26'),(11,4,10,'2026-05-01'),(12,2,11,'2026-05-03'),(13,4,12,'2026-05-05'),(14,2,1,'2026-05-08'),(15,4,13,'2026-05-10'),(16,2,14,'2026-05-12'),(17,4,15,'2026-05-15'),(18,2,16,'2026-05-18'),(19,4,2,'2026-06-01'),(20,2,17,'2026-06-05'),(21,4,18,'2026-06-10'),(22,2,1,'2026-06-18');
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_details`
--

DROP TABLE IF EXISTS `sale_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_details` (
  `Sale_ID` int NOT NULL,
  `Product_ID` int NOT NULL,
  `Unit_Price` decimal(10,2) NOT NULL,
  `Quantity` int NOT NULL,
  `Discount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`Sale_ID`,`Product_ID`),
  KEY `Product_ID` (`Product_ID`),
  CONSTRAINT `sale_details_ibfk_1` FOREIGN KEY (`Sale_ID`) REFERENCES `sale` (`Sale_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sale_details_ibfk_2` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`Product_ID`) ON UPDATE CASCADE,
  CONSTRAINT `sale_details_chk_1` CHECK ((`Unit_Price` >= 0)),
  CONSTRAINT `sale_details_chk_2` CHECK ((`Quantity` > 0)),
  CONSTRAINT `sale_details_chk_3` CHECK ((`Discount` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_details`
--

LOCK TABLES `sale_details` WRITE;
/*!40000 ALTER TABLE `sale_details` DISABLE KEYS */;
INSERT INTO `sale_details` VALUES (1,1,950.00,1,NULL),(1,13,25.00,1,0.00),(2,2,820.00,1,20.00),(2,11,75.00,1,NULL),(3,4,650.00,1,0.00),(3,8,18.00,2,NULL),(4,9,42.00,1,NULL),(4,10,120.00,1,10.00),(5,12,1450.00,1,50.00),(5,15,85.00,1,NULL),(6,3,380.00,1,NULL),(6,14,20.00,2,0.00),(7,7,470.00,1,NULL),(7,13,25.00,2,NULL),(8,17,12.00,2,NULL),(8,18,210.00,1,5.00),(9,5,720.00,1,25.00),(9,16,35.00,1,NULL),(10,8,18.00,1,0.00),(10,11,75.00,2,NULL),(11,2,820.00,2,20.00),(11,9,42.00,1,0.00),(12,4,650.00,1,0.00),(12,5,720.00,1,0.00),(13,7,470.00,1,0.00),(13,8,18.00,3,0.00),(14,8,18.00,5,0.00),(14,9,42.00,2,5.00),(14,17,12.00,3,0.00),(15,12,1450.00,1,100.00),(16,10,120.00,1,0.00),(16,11,75.00,2,10.00),(17,6,580.00,1,0.00),(17,13,25.00,2,0.00),(18,13,25.00,3,0.00),(18,14,20.00,2,0.00),(18,17,12.00,4,0.00),(19,1,950.00,1,0.00),(19,13,25.00,1,0.00),(20,3,380.00,1,0.00),(20,14,20.00,1,0.00),(21,9,42.00,1,0.00),(21,15,85.00,1,0.00),(22,4,650.00,1,0.00),(22,5,720.00,1,0.00);
/*!40000 ALTER TABLE `sale_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_movement_history`
--

DROP TABLE IF EXISTS `stock_movement_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_movement_history` (
  `Movement_ID` int NOT NULL AUTO_INCREMENT,
  `Product_ID` int NOT NULL,
  `Source_Section_ID` int DEFAULT NULL,
  `Destination_Section_ID` int DEFAULT NULL,
  `Movement_Type` varchar(50) NOT NULL,
  `Quantity` int NOT NULL,
  `Movement_Date` date NOT NULL,
  PRIMARY KEY (`Movement_ID`),
  KEY `Product_ID` (`Product_ID`),
  KEY `Source_Section_ID` (`Source_Section_ID`),
  KEY `Destination_Section_ID` (`Destination_Section_ID`),
  CONSTRAINT `stock_movement_history_ibfk_1` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`Product_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stock_movement_history_ibfk_2` FOREIGN KEY (`Source_Section_ID`) REFERENCES `storage_section` (`Section_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `stock_movement_history_ibfk_3` FOREIGN KEY (`Destination_Section_ID`) REFERENCES `storage_section` (`Section_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `stock_movement_history_chk_1` CHECK ((`Quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_movement_history`
--

LOCK TABLES `stock_movement_history` WRITE;
/*!40000 ALTER TABLE `stock_movement_history` DISABLE KEYS */;
INSERT INTO `stock_movement_history` VALUES (1,1,NULL,3,'Purchase',8,'2026-04-03'),(2,7,NULL,3,'Purchase',6,'2026-04-03'),(3,13,NULL,5,'Purchase',30,'2026-04-03'),(4,2,NULL,3,'Purchase',7,'2026-04-07'),(5,3,NULL,3,'Purchase',10,'2026-04-07'),(6,11,NULL,5,'Purchase',20,'2026-04-07'),(7,14,NULL,5,'Purchase',25,'2026-04-07'),(8,4,NULL,4,'Purchase',6,'2026-04-12'),(9,5,NULL,4,'Purchase',5,'2026-04-12'),(10,6,NULL,4,'Purchase',7,'2026-04-12'),(11,12,NULL,4,'Purchase',3,'2026-04-12'),(12,8,NULL,5,'Purchase',40,'2026-04-16'),(13,9,NULL,5,'Purchase',25,'2026-04-16'),(14,10,NULL,2,'Purchase',12,'2026-04-16'),(15,15,NULL,5,'Purchase',10,'2026-04-16'),(16,2,NULL,3,'Purchase',4,'2026-04-22'),(17,11,NULL,5,'Purchase',15,'2026-04-22'),(18,16,NULL,5,'Purchase',20,'2026-04-27'),(19,17,NULL,5,'Purchase',50,'2026-04-27'),(20,18,NULL,1,'Purchase',8,'2026-04-27'),(21,1,NULL,3,'Purchase',5,'2026-05-03'),(22,2,NULL,3,'Purchase',5,'2026-05-03'),(23,13,NULL,5,'Purchase',50,'2026-05-03'),(24,8,NULL,5,'Purchase',60,'2026-05-07'),(25,9,NULL,5,'Purchase',40,'2026-05-07'),(26,14,NULL,5,'Purchase',30,'2026-05-07'),(27,11,NULL,5,'Purchase',20,'2026-05-07'),(28,4,NULL,4,'Purchase',4,'2026-05-12'),(29,5,NULL,4,'Purchase',4,'2026-05-12'),(30,6,NULL,4,'Purchase',5,'2026-05-12'),(31,10,NULL,4,'Purchase',10,'2026-05-17'),(32,15,NULL,5,'Purchase',8,'2026-05-17'),(33,16,NULL,5,'Purchase',15,'2026-05-17'),(34,1,NULL,1,'Purchase',6,'2026-06-03'),(35,7,NULL,1,'Purchase',4,'2026-06-03'),(36,3,NULL,3,'Purchase',8,'2026-06-03'),(37,1,1,NULL,'Sale',1,'2026-04-18'),(38,13,5,NULL,'Sale',1,'2026-04-18'),(39,2,1,NULL,'Sale',1,'2026-04-18'),(40,11,5,NULL,'Sale',1,'2026-04-18'),(41,4,2,NULL,'Sale',1,'2026-04-19'),(42,8,5,NULL,'Sale',2,'2026-04-19'),(43,10,2,NULL,'Sale',1,'2026-04-20'),(44,9,5,NULL,'Sale',1,'2026-04-20'),(45,12,2,NULL,'Sale',1,'2026-04-21'),(46,15,5,NULL,'Sale',1,'2026-04-21'),(47,3,1,NULL,'Sale',1,'2026-04-22'),(48,14,5,NULL,'Sale',2,'2026-04-22'),(49,7,1,NULL,'Sale',1,'2026-04-23'),(50,13,5,NULL,'Sale',2,'2026-04-23'),(51,18,1,NULL,'Sale',1,'2026-04-24'),(52,17,5,NULL,'Sale',2,'2026-04-24'),(53,5,2,NULL,'Sale',1,'2026-04-25'),(54,16,5,NULL,'Sale',1,'2026-04-25'),(55,11,5,NULL,'Sale',2,'2026-04-26'),(56,8,5,NULL,'Sale',1,'2026-04-26'),(57,2,3,NULL,'Sale',2,'2026-05-01'),(58,9,5,NULL,'Sale',1,'2026-05-01'),(59,4,4,NULL,'Sale',1,'2026-05-03'),(60,5,4,NULL,'Sale',1,'2026-05-03'),(61,7,3,NULL,'Sale',1,'2026-05-05'),(62,8,5,NULL,'Sale',3,'2026-05-05'),(63,8,5,NULL,'Sale',5,'2026-05-08'),(64,9,5,NULL,'Sale',2,'2026-05-08'),(65,17,5,NULL,'Sale',3,'2026-05-08'),(66,12,4,NULL,'Sale',1,'2026-05-10'),(67,10,4,NULL,'Sale',1,'2026-05-12'),(68,11,5,NULL,'Sale',2,'2026-05-12'),(69,6,4,NULL,'Sale',1,'2026-05-15'),(70,13,5,NULL,'Sale',2,'2026-05-15'),(71,13,5,NULL,'Sale',3,'2026-05-18'),(72,14,5,NULL,'Sale',2,'2026-05-18'),(73,17,5,NULL,'Sale',4,'2026-05-18'),(74,1,1,NULL,'Sale',1,'2026-06-01'),(75,13,5,NULL,'Sale',1,'2026-06-01'),(76,3,3,NULL,'Sale',1,'2026-06-05'),(77,14,5,NULL,'Sale',1,'2026-06-05'),(78,15,5,NULL,'Sale',1,'2026-06-10'),(79,9,5,NULL,'Sale',1,'2026-06-10'),(80,4,4,NULL,'Sale',1,'2026-06-18'),(81,5,4,NULL,'Sale',1,'2026-06-18'),(82,8,NULL,5,'Return',1,'2026-04-21'),(83,14,NULL,5,'Return',1,'2026-04-24'),(84,11,NULL,5,'Return',1,'2026-04-28'),(85,9,NULL,5,'Return',1,'2026-05-04'),(86,8,NULL,5,'Return',1,'2026-05-08'),(87,11,NULL,5,'Return',1,'2026-05-15'),(88,1,3,1,'Transfer',2,'2026-05-20'),(89,9,5,2,'Transfer',3,'2026-05-22');
/*!40000 ALTER TABLE `stock_movement_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_details`
--

DROP TABLE IF EXISTS `storage_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_details` (
  `Product_ID` int NOT NULL,
  `Section_ID` int NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`Product_ID`,`Section_ID`),
  KEY `Section_ID` (`Section_ID`),
  CONSTRAINT `storage_details_ibfk_1` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`Product_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `storage_details_ibfk_2` FOREIGN KEY (`Section_ID`) REFERENCES `storage_section` (`Section_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `storage_details_chk_1` CHECK ((`Quantity` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_details`
--

LOCK TABLES `storage_details` WRITE;
/*!40000 ALTER TABLE `storage_details` DISABLE KEYS */;
INSERT INTO `storage_details` VALUES (1,1,8),(1,3,1),(2,1,3),(2,3,8),(3,1,4),(3,3,12),(4,2,4),(4,4,6),(5,2,4),(5,4,5),(6,2,3),(6,4,8),(7,1,4),(7,3,3),(8,5,79),(9,2,3),(9,5,57),(10,2,4),(10,4,16),(11,5,31),(12,2,0),(12,4,1),(13,5,65),(14,5,47),(15,5,16),(16,5,32),(17,5,36),(18,1,2),(18,3,5);
/*!40000 ALTER TABLE `storage_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_section`
--

DROP TABLE IF EXISTS `storage_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_section` (
  `Section_ID` int NOT NULL AUTO_INCREMENT,
  `Section_Name` varchar(100) NOT NULL,
  `Section_Type` varchar(50) NOT NULL,
  `Capacity` int NOT NULL,
  PRIMARY KEY (`Section_ID`),
  CONSTRAINT `storage_section_chk_1` CHECK ((`Capacity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_section`
--

LOCK TABLES `storage_section` WRITE;
/*!40000 ALTER TABLE `storage_section` DISABLE KEYS */;
INSERT INTO `storage_section` VALUES (1,'Front Showroom A','Showroom',100),(2,'Front Showroom B','Showroom',100),(3,'Backroom Storage 1','Backroom',200),(4,'Backroom Storage 2','Backroom',200),(5,'Accessories Shelf','Showroom',300),(6,'Repair Waiting Shelf','Service Area',50);
/*!40000 ALTER TABLE `storage_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `Supplier_ID` int NOT NULL AUTO_INCREMENT,
  `Contact_Name` varchar(100) NOT NULL,
  `Company_Name` varchar(100) NOT NULL,
  `Address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Supplier_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Ahmad Saleh','TechSource Palestine','Ramallah - Al Ersal'),(2,'Mona Khalil','Smart Import Co.','Al-Bireh'),(3,'Yousef Nasser','Digital World Supplies','Nablus'),(4,'Rana Odeh','Future Electronics','Hebron');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_phone`
--

DROP TABLE IF EXISTS `supplier_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_phone` (
  `Supplier_ID` int NOT NULL,
  `Phone` varchar(20) NOT NULL,
  PRIMARY KEY (`Supplier_ID`,`Phone`),
  CONSTRAINT `supplier_phone_ibfk_1` FOREIGN KEY (`Supplier_ID`) REFERENCES `supplier` (`Supplier_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_phone`
--

LOCK TABLES `supplier_phone` WRITE;
/*!40000 ALTER TABLE `supplier_phone` DISABLE KEYS */;
INSERT INTO `supplier_phone` VALUES (1,'022981111'),(1,'0599001122'),(2,'022982222'),(2,'0599112233'),(3,'0599223344'),(4,'0599334455');
/*!40000 ALTER TABLE `supplier_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_product`
--

DROP TABLE IF EXISTS `supplier_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_product` (
  `Supplier_ID` int NOT NULL,
  `Product_ID` int NOT NULL,
  `Supply_Price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`Supplier_ID`,`Product_ID`),
  KEY `Product_ID` (`Product_ID`),
  CONSTRAINT `supplier_product_ibfk_1` FOREIGN KEY (`Supplier_ID`) REFERENCES `supplier` (`Supplier_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `supplier_product_ibfk_2` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`Product_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_product`
--

LOCK TABLES `supplier_product` WRITE;
/*!40000 ALTER TABLE `supplier_product` DISABLE KEYS */;
INSERT INTO `supplier_product` VALUES (1,1,850.00),(1,7,400.00),(1,13,18.00),(2,2,730.00),(2,3,310.00),(2,11,55.00),(2,14,14.00),(3,4,560.00),(3,5,620.00),(3,6,500.00),(3,12,1280.00),(3,16,25.00),(3,17,7.00),(4,8,10.00),(4,9,30.00),(4,10,90.00),(4,15,62.00),(4,18,160.00);
/*!40000 ALTER TABLE `supplier_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warranty`
--

DROP TABLE IF EXISTS `warranty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warranty` (
  `Sale_ID` int NOT NULL,
  `Product_ID` int NOT NULL,
  `Warranty_Number` varchar(50) NOT NULL,
  `Warranty_Type` varchar(100) NOT NULL,
  `Warranty_Period` varchar(50) NOT NULL,
  `Start_Date` date NOT NULL,
  PRIMARY KEY (`Sale_ID`,`Product_ID`,`Warranty_Number`),
  CONSTRAINT `warranty_ibfk_1` FOREIGN KEY (`Sale_ID`, `Product_ID`) REFERENCES `sale_details` (`Sale_ID`, `Product_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warranty`
--

LOCK TABLES `warranty` WRITE;
/*!40000 ALTER TABLE `warranty` DISABLE KEYS */;
INSERT INTO `warranty` VALUES (2,2,'W-S24-001','Manufacturer Warranty','12 Months','2026-04-18'),(4,10,'W-SONY-001','Manufacturer Warranty','6 Months','2026-04-20'),(5,12,'W-ASUS-001','Manufacturer Warranty','24 Months','2026-04-21'),(7,7,'W-IPAD-001','Manufacturer Warranty','12 Months','2026-04-23'),(8,18,'W-LENTAB-001','Manufacturer Warranty','12 Months','2026-04-24'),(9,5,'W-HP-001','Manufacturer Warranty','24 Months','2026-04-25'),(11,2,'W-S24-002','Manufacturer Warranty','12 Months','2026-05-01'),(12,4,'W-DELL-002','Store Warranty','12 Months','2026-05-03'),(12,5,'W-HP-002','Manufacturer Warranty','24 Months','2026-05-03'),(13,7,'W-IPAD-002','Manufacturer Warranty','12 Months','2026-05-05'),(15,12,'W-ASUS-002','Manufacturer Warranty','24 Months','2026-05-10'),(17,6,'W-LENI-001','Manufacturer Warranty','12 Months','2026-05-15'),(19,1,'W-IPH15-002','Manufacturer Warranty','12 Months','2026-06-01');
/*!40000 ALTER TABLE `warranty` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-21 15:38:48
