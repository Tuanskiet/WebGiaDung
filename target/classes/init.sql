CREATE DATABASE  IF NOT EXISTS `db_giadung` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_giadung`;

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
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt5ytj44vpktaa6t9o4906gi2d` (`product_id`),
  KEY `FKsy01evyog4nc8vgw1769u5tyg` (`user_id`),
  CONSTRAINT `FKsy01evyog4nc8vgw1769u5tyg` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKt5ytj44vpktaa6t9o4906gi2d` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` (`id`, `quantity`, `product_id`, `user_id`) VALUES (2,3,2,1),(3,1,3,1);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluate`
--

DROP TABLE IF EXISTS `evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text,
  `name_user` varchar(255) DEFAULT NULL,
  `num_star` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsqmn2cvgnxcqlmdt7f1eumq6e` (`product_id`),
  CONSTRAINT `FKsqmn2cvgnxcqlmdt7f1eumq6e` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluate`
--

LOCK TABLES `evaluate` WRITE;
/*!40000 ALTER TABLE `evaluate` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_categories`
--

DROP TABLE IF EXISTS `my_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` text,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `policy` text,
  `slug` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tnkxxbb4tbac3ben5t50ilgia` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_categories`
--

LOCK TABLES `my_categories` WRITE;
/*!40000 ALTER TABLE `my_categories` DISABLE KEYS */;
INSERT INTO `my_categories` (`id`, `image`, `is_active`, `name`, `policy`, `slug`, `type`) VALUES (1,'https://cdn.tgdd.vn/Products/Images/1942/309559/android-tivi-aqua-32-inch-le32aqt6600g-8-550x340.jpg',_binary '','Thang nhôm',NULL,'thang-nhom','product'),(2,'https://cdn.tgdd.vn/Products/Images/1943/220325/samsung-rt22m4032by-sv-300x300.jpg',_binary '','Máy lọc nước',NULL,'may-loc-nuoc','product'),(3,'https://cdn.tgdd.vn/Products/Images/1944/242732/aqua-88-kg-aqw-fr88gtbk-300x300.jpg',_binary '','Linh kiện máy lọc nước',NULL,'linh-kien-may-loc-nuoc','product'),(4,'https://cdn.tgdd.vn/Products/Images/2202/272143/may-say-thong-hoi-electrolux-75-kg-edv754h3wb-170223-031343-600x600.jpg',_binary '','Máy lọc không khí',NULL,'may-loc-khong-khi','product'),(5,'https://cdn.tgdd.vn/Products/Images/3385/309367/may-loc-nuoc-ro-hoa-phat-hws1b-1022-10-loi-230623-025945-600x600.jpg',_binary '','Dây thoát hiểm',NULL,'day-thoat-hiem','product'),(6,'https://cdn.tgdd.vn/Products/Images/1982/236629/bep-tu-doi-hafele-hc-i2712a-10-600x600.jpg',_binary '','Rèm cửa',NULL,'rem-cua','product'),(7,'https://cdn.tgdd.vn/Products/Images/10139/264374/samsung-vr05r5050wk-sv-100423-014048-600x600.jpg',_binary '','Máy lạnh',NULL,'may-lanh','product'),(8,'https://cdn.tgdd.vn/Products/Images/2162/306932/loa-keo-karaoke-dalton-ts-12g350n-100523-094658-600x600.jpg',_binary '','Máy hút ẩm',NULL,'may-hut-am','product'),(9,'https://cdn.tgdd.vn/Products/Images/2162/306932/loa-keo-karaoke-dalton-ts-12g350n-100523-094658-600x600.jpg',_binary '','Thi công công trình',NULL,'thi-cong-cong-trinh','service'),(10,'https://cdn.tgdd.vn/Products/Images/2162/306932/loa-keo-karaoke-dalton-ts-12g350n-100523-094658-600x600.jpg',_binary '','Sửa chữa - lắp đặt',NULL,'sua-chua---lap-at','service'),(11,'https://cdn.tgdd.vn/Products/Images/2162/306932/loa-keo-karaoke-dalton-ts-12g350n-100523-094658-600x600.jpg',_binary '','Vệ sinh - bảo dưỡng',NULL,'ve-sinh---bao-duong','service');
/*!40000 ALTER TABLE `my_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(1024) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_payment` decimal(19,2) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3b2ji759qk04mk7iiglb0rcq6` (`user_id`),
  CONSTRAINT `FK3b2ji759qk04mk7iiglb0rcq6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `address`, `create_at`, `email`, `name`, `note`, `phone_number`, `status`, `total_payment`, `user_id`) VALUES (1,'Luc Tuan Kiet ','2023-11-06 16:34:07.036000','admin','Luc Tuan Kiet ','','Luc Tuan Kiet ','Đang xử lí',1820.00,1);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `old_price` decimal(19,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7pv97udw3cbgddveqn6eoosng` (`order_id`),
  KEY `FK3u8xd63p6ryjcn6196ydm6tyb` (`product_id`),
  CONSTRAINT `FK3u8xd63p6ryjcn6196ydm6tyb` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FK7pv97udw3cbgddveqn6eoosng` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` (`id`, `old_price`, `quantity`, `order_id`, `product_id`) VALUES (1,910.00,2,1,1);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` text,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb4ct19237ff3iusxp73vsdcp2` (`product_id`),
  CONSTRAINT `FKb4ct19237ff3iusxp73vsdcp2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_info`
--

DROP TABLE IF EXISTS `product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKho51irerj6wqcu3qigw6jww2o` (`product_id`),
  CONSTRAINT `FKho51irerj6wqcu3qigw6jww2o` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_info`
--

LOCK TABLES `product_info` WRITE;
/*!40000 ALTER TABLE `product_info` DISABLE KEYS */;
INSERT INTO `product_info` (`id`, `key`, `value`, `product_id`) VALUES (1,'Xuất xứ','Thương hiệu: Nhật - Sản xuất tại: Việt Nam',1),(2,'Loại Gas lạnh ','R32',1),(3,'Loại máy ','Inverter - loại 1 chiều (chỉ làm lạnh)',3),(4,'Loại máy ','Inverter - loại 2 chiều (chỉ làm lạnh)',NULL);
/*!40000 ALTER TABLE `product_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand_app` varchar(255) DEFAULT NULL,
  `date_release` datetime(6) DEFAULT NULL,
  `full_description` text,
  `image` text,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percent_discount` double DEFAULT NULL,
  `price` decimal(12,3) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6keev632w4hq6g5dfm2rpw5mo` (`category_id`),
  CONSTRAINT `FK6keev632w4hq6g5dfm2rpw5mo` FOREIGN KEY (`category_id`) REFERENCES `my_categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`id`, `brand_app`, `date_release`, `full_description`, `image`, `is_active`, `name`, `percent_discount`, `price`, `slug`, `category_id`) VALUES (1,NULL,'2023-11-06 16:32:28.688000',NULL,'https://bizweb.dktcdn.net/100/488/001/products/maylanhdaikininverter25hp.jpg?v=1686045426487',_binary '','Máy lạnh Daikin Inverter',9,999.000,'may-lanh-daikin-inverter',1),(2,NULL,'2023-11-06 16:32:28.712000',NULL,'https://bizweb.dktcdn.net/100/488/001/products/may-xay-sinh-to-multi-0-77-1.jpg?v=1686119933390',_binary '','Máy xay đa năng Panasonic ',9,999.000,'may-xay-a-nang-panasonic-',1),(3,NULL,'2023-11-06 16:32:28.734000',NULL,'https://bizweb.dktcdn.net/thumb/large/100/488/001/products/may-lanh-tu-dung-funiki-fc50-5-1.jpg?v=1686062079340',_binary '','Máy lạnh tủ đứng ',9,999.000,'may-lanh-tu-ung-',1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`) VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `email`, `enabled`, `full_name`, `password`) VALUES (1,'admin',_binary '','Luc Tuan Kiet','$2a$10$VG.Z9CBRGywQeR5INI6H7een1xjYNTNXxj0yGGBJjXpckDL.tH.Uy'),(2,'stellaprimo99@gmail.com',_binary '','Duc Cong','$2a$10$/QaciGIwpT8813k6cyra9.YU//O.6c19.mcjmgcYA7y4eGliVjtSe');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKnp0dqq44iawoqn80pjdsul85i` (`role_id`),
  CONSTRAINT `FKdxdy3w4bd1qmgc4ei96as8q7m` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKnp0dqq44iawoqn80pjdsul85i` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-06 16:37:47