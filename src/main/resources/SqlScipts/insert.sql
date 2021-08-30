-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: cryptobank
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

Use cryptobank;

-- customer
-- Dumping data for table `admin`
--

LOCK TABLES admin WRITE;
INSERT INTO admin VALUES ('admin','admin','b853e2a0', 'firstToken');
UNLOCK TABLES;

--
-- Dumping data for table `asset`
--

LOCK TABLES asset WRITE;
INSERT INTO asset VALUES ('ADA','Cardano','ADA'),('BCH','Bitcoin Cash','BCH'),('BNB','Binance Coin','BNB'),('BTC','Bitcoin','BTC'),('BUSD','Binance USD','BUSD'),('CAKE','PancakeSwap','pannekoek, goed idee voor lunch'),('DOGE','Dogecoin','DOGE'),('DOT','Polkadot','polka klinkt wel lekker pools en gezellig'),('ETH','Ethereum','ETH'),('HEX','HEX','HEX'),('LINK','Chainlink','LINK'),('LTC','Litecoin','LTC'),('LUNA','Terra','LUNA'),('MATIC','Polygon','MATIC'),('SOL','Solana','SOL is een prima zomerbiertje'),('UNI','Uniswap','UNI'),('USDC','USDC','USDC'),('USDT','Tether USD','USDT'),('WBTC','Wrapped BTC','een wrap is altijd lekker'),('XRP','XRP','XRP');
UNLOCK TABLES;

--
-- Dumping data for table `bankaccount`
--

LOCK TABLES bankaccount WRITE;
INSERT INTO bankaccount VALUES ('NL69COKI5000000003',0),('NL13COKI8974196092',0),('NL87COKI9583557878',0),('NL49COKI7517641892',0);
UNLOCK TABLES;

--
-- Dumping data for table `crypto_currency_rate`
--

LOCK TABLES crypto_currency_rate WRITE;
INSERT INTO crypto_currency_rate VALUES ('ADA','2021-08-25 11:57:00',2.65),('BCH','2021-08-25 11:57:12',639.50),('BNB','2021-08-25 11:57:01',478.97),('BTC','2021-08-25 11:56:53',47638.40),('BUSD','2021-08-25 11:57:11',1.00),('CAKE','2021-08-25 11:57:15',24.44),('DOGE','2021-08-25 11:57:05',0.29),('DOT','2021-08-25 11:57:08',25.28),('ETH','2021-08-25 11:56:58',3130.00),('HEX','2021-08-25 11:57:04',0.18),('LINK','2021-08-25 11:57:14',25.92),('LTC','2021-08-25 11:57:13',173.34),('LUNA','2021-08-25 11:57:07',29.35),('MATIC','2021-08-25 11:57:16',1.46),('SOL','2021-08-25 11:57:10',67.12),('UNI','2021-08-25 11:57:10',26.20),('USDC','2021-08-25 11:57:09',1.00),('USDT','2021-08-25 11:57:02',1.00),('WBTC','2021-08-25 11:57:17',47700.17),('XRP','2021-08-25 11:57:03',1.14);
UNLOCK TABLES;

--
-- Dumping data for table `customer`
--

LOCK TABLES customer WRITE;
INSERT INTO customer VALUES ('sarah','4a31a7bf19a029ac766d1e9486216c579495022c7b12e41509a92ba7e245bc72','b1b63d09','sarah','van Norgaerde','1992-08-22',123456789,'cryptostreet','1234KB',12,'bis','NL69COKI5000000003', 'Vlaardingen','firstToken');
UNLOCK TABLES;

--
-- Dumping data for table `ownedasset`
--

LOCK TABLES ownedasset WRITE;
UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
