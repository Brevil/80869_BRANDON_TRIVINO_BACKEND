/*M!999999\- enable the sandbox mode */ 

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `clinica` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `clinica`;
DROP TABLE IF EXISTS `anotacion_historia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `anotacion_historia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` text NOT NULL,
  `diagnostico` text NOT NULL,
  `fecha` datetime(6) NOT NULL,
  `observacion` text DEFAULT NULL,
  `historia_id` bigint(20) NOT NULL,
  `medico_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7llt4a6upbsl8yc1287e9u6ac` (`historia_id`),
  KEY `FK9x12ohery1hdgu8xkjxpw91cx` (`medico_id`),
  CONSTRAINT `FK7llt4a6upbsl8yc1287e9u6ac` FOREIGN KEY (`historia_id`) REFERENCES `historia_medica` (`id`),
  CONSTRAINT `FK9x12ohery1hdgu8xkjxpw91cx` FOREIGN KEY (`medico_id`) REFERENCES `medico` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` varchar(20) NOT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `motivo` text DEFAULT NULL,
  `cliente_id` bigint(20) NOT NULL,
  `mascota_id` int(11) NOT NULL,
  `medico_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd4bcx0srxa2870y731fa0yew2` (`cliente_id`),
  KEY `FKjjr9rbirfalfxoq1rndrc8sqq` (`mascota_id`),
  KEY `FKdfrd023l6bdxh9tb21isevvth` (`medico_id`),
  CONSTRAINT `FKd4bcx0srxa2870y731fa0yew2` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`cliente_id`),
  CONSTRAINT `FKdfrd023l6bdxh9tb21isevvth` FOREIGN KEY (`medico_id`) REFERENCES `medico` (`id`),
  CONSTRAINT `FKjjr9rbirfalfxoq1rndrc8sqq` FOREIGN KEY (`mascota_id`) REFERENCES `mascota` (`mascota_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cliente_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `direccion` text DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  `genero` varchar(1) DEFAULT NULL,
  `nombres` varchar(100) NOT NULL,
  `numero_documento` varchar(20) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `tipo_documento` varchar(10) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`cliente_id`),
  UNIQUE KEY `UKhx9w6tpma79yxjbk83xfiw7fo` (`numero_documento`),
  UNIQUE KEY `UKid7jmosqg8hkqiqw4vf50xipm` (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `formula_medica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `formula_medica` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dosis` text NOT NULL,
  `duracion_dias` int(11) NOT NULL,
  `fecha_actualizacion_registro` datetime(6) DEFAULT NULL,
  `fecha_creacion_registro` datetime(6) NOT NULL,
  `fecha_emision` date NOT NULL,
  `frecuencia` varchar(100) NOT NULL,
  `indicaciones` text DEFAULT NULL,
  `cita_id` bigint(20) NOT NULL,
  `mascota_id` int(11) NOT NULL,
  `medicamento_id` bigint(20) NOT NULL,
  `medico_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhdlh5hex0n89k8d5oyyp4olt5` (`cita_id`),
  KEY `FKspf1nnj9f4duwsujp0n1uq1n1` (`mascota_id`),
  KEY `FKt177noclq9k18xt3oslb9qabu` (`medicamento_id`),
  KEY `FKf1ftr9o49ybek5yd076pk6pn9` (`medico_id`),
  CONSTRAINT `FKf1ftr9o49ybek5yd076pk6pn9` FOREIGN KEY (`medico_id`) REFERENCES `medico` (`id`),
  CONSTRAINT `FKhdlh5hex0n89k8d5oyyp4olt5` FOREIGN KEY (`cita_id`) REFERENCES `cita` (`id`),
  CONSTRAINT `FKspf1nnj9f4duwsujp0n1uq1n1` FOREIGN KEY (`mascota_id`) REFERENCES `mascota` (`mascota_id`),
  CONSTRAINT `FKt177noclq9k18xt3oslb9qabu` FOREIGN KEY (`medicamento_id`) REFERENCES `medicamento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `historia_medica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `historia_medica` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `diagnostico` text NOT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `motivo` text DEFAULT NULL,
  `observaciones` text DEFAULT NULL,
  `cita_id` bigint(20) NOT NULL,
  `paciente_id` int(11) NOT NULL,
  `medico_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKki84svhl0n45f8wafdlfyyrhv` (`cita_id`),
  KEY `FKbpiqkm9vqknx3hylr6cb1bu3b` (`paciente_id`),
  KEY `FKoawaaj2gsjiyoynj61lvowki3` (`medico_id`),
  CONSTRAINT `FKbpiqkm9vqknx3hylr6cb1bu3b` FOREIGN KEY (`paciente_id`) REFERENCES `mascota` (`mascota_id`),
  CONSTRAINT `FKjxqq39xa80w6c8lavk983xdkf` FOREIGN KEY (`cita_id`) REFERENCES `cita` (`id`),
  CONSTRAINT `FKoawaaj2gsjiyoynj61lvowki3` FOREIGN KEY (`medico_id`) REFERENCES `medico` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `mascota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `mascota` (
  `mascota_id` int(11) NOT NULL AUTO_INCREMENT,
  `edad` int(11) DEFAULT NULL,
  `fecha_modificacion` datetime(6) DEFAULT NULL,
  `fecha_registro` datetime(6) DEFAULT NULL,
  `nombre_mascota` varchar(255) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `raza_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`mascota_id`),
  KEY `FK43ghhb7i1g4240ndbx7m7a6ji` (`cliente_id`),
  KEY `FKllkmqrwwv3oslxrav189b27b8` (`raza_id`),
  CONSTRAINT `FK43ghhb7i1g4240ndbx7m7a6ji` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`cliente_id`),
  CONSTRAINT `FKllkmqrwwv3oslxrav189b27b8` FOREIGN KEY (`raza_id`) REFERENCES `raza` (`raza_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicamento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` text DEFAULT NULL,
  `fecha_compra` date NOT NULL,
  `fecha_creacion_registro` datetime(6) NOT NULL,
  `fecha_modificacion_registro` datetime(6) DEFAULT NULL,
  `fecha_vence` date NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `presentacion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfy4qp46a0asx66i0igibhi1n8` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(100) NOT NULL,
  `especializacion_id` int(11) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `numero_documento` varchar(20) NOT NULL,
  `registro_profesional` varchar(50) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `tipo_documento` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK70rt8elxd41jmtof1ti3tr7ie` (`numero_documento`),
  UNIQUE KEY `UKd0cahrkn79q0i29g2b6kgak35` (`registro_profesional`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `raza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `raza` (
  `raza_id` int(11) NOT NULL AUTO_INCREMENT,
  `especie` varchar(100) NOT NULL,
  `fecha_creacion` datetime(6) NOT NULL,
  `fecha_modificacion` datetime(6) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`raza_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

