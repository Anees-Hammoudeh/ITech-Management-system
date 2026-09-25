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
  PRIMARY KEY (`Category_ID`),
  UNIQUE KEY `Category_Name` (`Category_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (4,'Accessory'),(5,'Audio'),(6,'Gaming'),(2,'Laptop'),(1,'Smartphone'),(3,'Tablet');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Omar','Khaled'),(2,'Lina','Ahmad'),(3,'Samer','Nassar'),(4,'Hiba','Saleh'),(5,'Tariq','Mansour'),(6,'Noor','Zaid'),(7,'Yara','Hammad'),(8,'Adam','Qasem');
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
INSERT INTO `customer_phone` VALUES (1,'022971111'),(1,'0597001111'),(2,'0597002222'),(3,'0597003333'),(4,'0597004444'),(5,'0597005555'),(6,'0597006666'),(7,'0597007777'),(8,'0597008888');
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Khaled','Darwish','Manager',2500.00,NULL),(2,'Aya','Hassan','Sales Employee',1300.00,1),(3,'Rami','Qasem','Warehouse Employee',1200.00,1),(4,'Dana','Ali','Cashier',1100.00,1),(5,'Mahmoud','Nimer','Sales Employee',1250.00,2),(6,'Nour','Saleh','Accountant',1400.00,1);
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
INSERT INTO `employee_phone` VALUES (1,'022981234'),(1,'0598001111'),(2,'0598002222'),(3,'0598003333'),(4,'0598004444'),(5,'0598005555'),(6,'0598006666');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,975.00,'Cash','Paid','2026-04-18'),(2,2,500.00,'Installment','Partial','2026-04-18'),(3,2,395.00,'Installment','Pending','2026-05-18'),(4,3,686.00,'Credit Card','Paid','2026-04-19'),(5,4,162.00,'Cash','Paid','2026-04-20'),(6,5,800.00,'Installment','Partial','2026-04-21'),(7,5,735.00,'Installment','Pending','2026-05-21'),(8,6,420.00,'Cash','Paid','2026-04-22'),(9,7,520.00,'Credit Card','Paid','2026-04-23'),(10,8,234.00,'Cash','Paid','2026-04-24'),(11,9,400.00,'Installment','Partial','2026-04-25'),(12,9,355.00,'Installment','Pending','2026-05-25'),(13,10,168.00,'Cash','Paid','2026-04-26');
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'iPhone 15','A3090',950.00,1,1),(2,'Samsung Galaxy S24','SM-S921',820.00,1,2),(3,'Samsung Galaxy A55','SM-A556',380.00,1,2),(4,'Dell Inspiron 15','3520',650.00,2,3),(5,'HP Pavilion 14','14-dv2000',720.00,2,4),(6,'Lenovo IdeaPad 3','15IAU7',580.00,2,5),(7,'iPad 10th Gen','A2696',470.00,3,1),(8,'Logitech Wireless Mouse','M185',18.00,4,7),(9,'Logitech Keyboard','K380',42.00,4,7),(10,'Sony WH-CH720N Headphones','WH-CH720N',120.00,5,6),(11,'Samsung Galaxy Buds FE','SM-R400',75.00,5,2),(12,'Asus ROG Gaming Laptop','G16',1450.00,6,8),(13,'Apple USB-C Charger','20W',25.00,4,1),(14,'Samsung Fast Charger','25W',20.00,4,2),(15,'Logitech Webcam','C920',85.00,4,7),(16,'Dell Gaming Mouse','GM500',35.00,4,3),(17,'HP USB Flash Drive','64GB',12.00,4,4),(18,'Lenovo Tab M10','TB328FU',210.00,3,5);
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
  `Supplier_ID` int NOT NULL,
  `Purchase_Date` date NOT NULL,
  `Delivery_Date` date DEFAULT NULL,
  PRIMARY KEY (`Purchase_ID`),
  KEY `Supplier_ID` (`Supplier_ID`),
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`Supplier_ID`) REFERENCES `supplier` (`Supplier_ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,1,'2026-04-01','2026-04-03'),(2,2,'2026-04-05','2026-04-07'),(3,3,'2026-04-10','2026-04-12'),(4,4,'2026-04-15','2026-04-16'),(5,2,'2026-04-20','2026-04-22'),(6,3,'2026-04-25','2026-04-27');
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
INSERT INTO `purchase_details` VALUES (1,1,850.00,8),(1,7,400.00,6),(1,13,18.00,30),(2,2,730.00,7),(2,3,310.00,10),(2,11,55.00,20),(2,14,14.00,25),(3,4,560.00,6),(3,5,620.00,5),(3,6,500.00,7),(3,12,1280.00,3),(4,8,10.00,40),(4,9,30.00,25),(4,10,90.00,12),(4,15,62.00,10),(5,2,730.00,4),(5,11,55.00,15),(6,16,25.00,20),(6,17,7.00,50),(6,18,160.00,8);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_record`
--

LOCK TABLES `return_record` WRITE;
/*!40000 ALTER TABLE `return_record` DISABLE KEYS */;
INSERT INTO `return_record` VALUES (1,3,8,'2026-04-21',1,'Customer reported connection issue','Approved'),(2,6,14,'2026-04-24',1,'Wrong charger type','Pending'),(3,10,11,'2026-04-28',1,'Audio issue','Approved');
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
  `Customer_ID` int NOT NULL,
  `Sale_Date` date NOT NULL,
  PRIMARY KEY (`Sale_ID`),
  KEY `Customer_ID` (`Customer_ID`),
  CONSTRAINT `sale_ibfk_1` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`Customer_ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES (1,1,'2026-04-18'),(2,2,'2026-04-18'),(3,3,'2026-04-19'),(4,4,'2026-04-20'),(5,5,'2026-04-21'),(6,6,'2026-04-22'),(7,1,'2026-04-23'),(8,7,'2026-04-24'),(9,8,'2026-04-25'),(10,3,'2026-04-26');
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
  PRIMARY KEY (`Sale_ID`,`Product_ID`),
  KEY `Product_ID` (`Product_ID`),
  CONSTRAINT `sale_details_ibfk_1` FOREIGN KEY (`Sale_ID`) REFERENCES `sale` (`Sale_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sale_details_ibfk_2` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`Product_ID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_details`
--

LOCK TABLES `sale_details` WRITE;
/*!40000 ALTER TABLE `sale_details` DISABLE KEYS */;
INSERT INTO `sale_details` VALUES (1,1,950.00,1),(1,13,25.00,1),(2,2,820.00,1),(2,11,75.00,1),(3,4,650.00,1),(3,8,18.00,2),(4,9,42.00,1),(4,10,120.00,1),(5,12,1450.00,1),(5,15,85.00,1),(6,3,380.00,1),(6,14,20.00,2),(7,7,470.00,1),(7,13,25.00,2),(8,17,12.00,2),(8,18,210.00,1),(9,5,720.00,1),(9,16,35.00,1),(10,8,18.00,1),(10,11,75.00,2);
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
  `Section_ID` int NOT NULL,
  `Movement_Type` varchar(50) NOT NULL,
  `Quantity` int NOT NULL,
  `Movement_Date` date NOT NULL,
  PRIMARY KEY (`Movement_ID`),
  KEY `Product_ID` (`Product_ID`),
  KEY `Section_ID` (`Section_ID`),
  CONSTRAINT `stock_movement_history_ibfk_1` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`Product_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stock_movement_history_ibfk_2` FOREIGN KEY (`Section_ID`) REFERENCES `storage_section` (`Section_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_movement_history`
--

LOCK TABLES `stock_movement_history` WRITE;
/*!40000 ALTER TABLE `stock_movement_history` DISABLE KEYS */;
INSERT INTO `stock_movement_history` VALUES (1,1,3,'Purchase',8,'2026-04-03'),(2,7,3,'Purchase',6,'2026-04-03'),(3,13,5,'Purchase',30,'2026-04-03'),(4,2,3,'Purchase',7,'2026-04-07'),(5,3,3,'Purchase',10,'2026-04-07'),(6,11,5,'Purchase',20,'2026-04-07'),(7,14,5,'Purchase',25,'2026-04-07'),(8,4,4,'Purchase',6,'2026-04-12'),(9,5,4,'Purchase',5,'2026-04-12'),(10,6,4,'Purchase',7,'2026-04-12'),(11,12,4,'Purchase',3,'2026-04-12'),(12,8,5,'Purchase',40,'2026-04-16'),(13,9,5,'Purchase',25,'2026-04-16'),(14,10,4,'Purchase',12,'2026-04-16'),(15,15,5,'Purchase',10,'2026-04-16'),(16,1,1,'Sale',1,'2026-04-18'),(17,13,5,'Sale',1,'2026-04-18'),(18,2,1,'Sale',1,'2026-04-18'),(19,11,5,'Sale',1,'2026-04-18'),(20,4,2,'Sale',1,'2026-04-19'),(21,8,5,'Sale',2,'2026-04-19'),(22,10,2,'Sale',1,'2026-04-20'),(23,9,5,'Sale',1,'2026-04-20'),(24,12,2,'Sale',1,'2026-04-21'),(25,15,5,'Sale',1,'2026-04-21'),(26,8,5,'Return',1,'2026-04-21'),(27,14,5,'Return',1,'2026-04-24'),(28,11,5,'Return',1,'2026-04-28'),(29,16,5,'Purchase',20,'2026-04-27'),(30,17,5,'Purchase',50,'2026-04-27'),(31,18,3,'Purchase',8,'2026-04-27');
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
  CONSTRAINT `storage_details_ibfk_2` FOREIGN KEY (`Section_ID`) REFERENCES `storage_section` (`Section_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_details`
--

LOCK TABLES `storage_details` WRITE;
/*!40000 ALTER TABLE `storage_details` DISABLE KEYS */;
INSERT INTO `storage_details` VALUES (1,1,3),(1,3,5),(2,1,4),(2,3,7),(3,1,5),(3,3,5),(4,2,2),(4,4,4),(5,2,2),(5,4,3),(6,2,3),(6,4,4),(7,1,2),(7,3,4),(8,5,30),(9,5,20),(10,2,5),(10,4,7),(11,5,25),(12,2,1),(12,4,2),(13,5,25),(14,5,22),(15,5,8),(16,5,18),(17,5,45),(18,1,3),(18,3,5);
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
  PRIMARY KEY (`Section_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_section`
--

LOCK TABLES `storage_section` WRITE;
/*!40000 ALTER TABLE `storage_section` DISABLE KEYS */;
INSERT INTO `storage_section` VALUES (1,'Front Showroom A','Showroom'),(2,'Front Showroom B','Showroom'),(3,'Backroom Storage 1','Backroom'),(4,'Backroom Storage 2','Backroom'),(5,'Accessories Shelf','Showroom'),(6,'Repair Waiting Shelf','Service Area');
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
  `Product_ID` int NOT NULL,
  `Warranty_Number` varchar(50) NOT NULL,
  `Warranty_Type` varchar(100) NOT NULL,
  `Warranty_Period` varchar(50) NOT NULL,
  `Start_Date` date NOT NULL,
  PRIMARY KEY (`Product_ID`,`Warranty_Number`),
  UNIQUE KEY `Warranty_Number` (`Warranty_Number`),
  CONSTRAINT `warranty_ibfk_1` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`Product_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warranty`
--

LOCK TABLES `warranty` WRITE;
/*!40000 ALTER TABLE `warranty` DISABLE KEYS */;
INSERT INTO `warranty` VALUES (1,'W-IPH15-001','Manufacturer Warranty','12 Months','2026-04-01'),(2,'W-S24-001','Manufacturer Warranty','12 Months','2026-04-05'),(3,'W-A55-001','Manufacturer Warranty','12 Months','2026-04-05'),(4,'W-DELL-001','Store + Manufacturer Warranty','24 Months','2026-04-10'),(5,'W-HP-001','Manufacturer Warranty','24 Months','2026-04-10'),(6,'W-LEN-001','Manufacturer Warranty','12 Months','2026-04-10'),(7,'W-IPAD-001','Manufacturer Warranty','12 Months','2026-04-01'),(10,'W-SONY-001','Manufacturer Warranty','6 Months','2026-04-15'),(12,'W-ASUS-001','Manufacturer Warranty','24 Months','2026-04-10'),(15,'W-CAM-001','Store Warranty','6 Months','2026-04-15'),(18,'W-LENTAB-001','Manufacturer Warranty','12 Months','2026-04-25');
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

-- Dump completed on 2026-05-23 17:31:20
