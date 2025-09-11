-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           11.4.2-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win64
-- HeidiSQL Version:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Listage de la structure de table location. materiel
CREATE TABLE IF NOT EXISTS `materiel` (
  `is_rented` bit(1) NOT NULL,
  `weight` double NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location_date` datetime(6) DEFAULT NULL,
  `people_id` bigint(20) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `material_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs3t4pbr2ytvoikha1s4fiakv3` (`people_id`),
  CONSTRAINT `FKs3t4pbr2ytvoikha1s4fiakv3` FOREIGN KEY (`people_id`) REFERENCES `people` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Listage des données de la table location.materiel : ~30 rows (environ)
INSERT INTO `materiel` (`is_rented`, `weight`, `id`, `location_date`, `people_id`, `category`, `material_name`) VALUES
	(b'1', 12.5, 1, '2025-09-01 10:30:00.000000', 1, 'musculation', 'Haltère réglable'),
	(b'0', 80, 2, NULL, NULL, 'cardio', 'Tapis de course'),
	(b'0', 7, 3, NULL, NULL, 'musculation', 'Kettlebell 7 kg'),
	(b'1', 0.4, 4, '2025-09-05 15:00:00.000000', 2, 'cardio', 'Corde à sauter'),
	(b'1', 15, 5, '2025-09-06 09:20:00.000000', 3, 'musculation', 'Barre droite 15 kg'),
	(b'0', 60, 6, NULL, NULL, 'team', 'But de football portable'),
	(b'1', 5, 7, '2025-09-03 13:45:00.000000', 4, 'accessoire', 'Médecine ball 5 kg'),
	(b'0', 20, 8, NULL, NULL, 'musculation', 'Banc de musculation'),
	(b'1', 25, 9, '2025-09-02 11:15:00.000000', 5, 'cardio', 'Vélo elliptique'),
	(b'0', 0.3, 10, NULL, NULL, 'accessoire', 'Tapis de yoga'),
	(b'1', 18, 11, '2025-09-04 16:10:00.000000', 6, 'musculation', 'Kettlebell 18 kg'),
	(b'0', 110, 12, NULL, NULL, 'cardio', 'Rameur à eau'),
	(b'1', 10, 13, '2025-09-07 08:50:00.000000', 7, 'musculation', 'Haltères 2×5 kg'),
	(b'0', 35, 14, NULL, NULL, 'cardio', 'Stepper pro'),
	(b'1', 14, 15, '2025-08-31 17:25:00.000000', 8, 'musculation', 'Barre EZ 14 kg'),
	(b'0', 9, 16, NULL, NULL, 'accessoire', 'Sac de frappe junior'),
	(b'1', 2, 17, '2025-09-01 12:05:00.000000', 9, 'accessoire', 'Élastiques de résistance'),
	(b'0', 95, 18, NULL, NULL, 'cardio', 'Home trainer vélo'),
	(b'1', 32, 19, '2025-09-03 10:10:00.000000', 10, 'musculation', 'Disques fonte 4×8 kg'),
	(b'0', 1.2, 20, NULL, NULL, 'accessoire', 'Corde ondulatoire courte'),
	(b'1', 24, 21, '2025-09-02 09:00:00.000000', 11, 'musculation', 'Kettlebell 24 kg'),
	(b'0', 45, 22, NULL, NULL, 'team', 'Filet de badminton'),
	(b'1', 70, 23, '2025-09-05 18:40:00.000000', 12, 'cardio', 'Vélo spinning'),
	(b'0', 0.5, 24, NULL, NULL, 'accessoire', 'Roue abdominale'),
	(b'1', 6, 25, '2025-09-06 14:30:00.000000', 13, 'musculation', 'Haltères 2×3 kg'),
	(b'0', 55, 26, NULL, NULL, 'cardio', 'Ski de fond indoor'),
	(b'1', 22, 27, '2025-09-04 19:15:00.000000', 14, 'musculation', 'Disques caoutchouc 2×11 kg'),
	(b'1', 0.8, 28, '2025-09-07 11:20:00.000000', 15, 'accessoire', 'Sangle de suspension'),
	(b'0', 0.9, 29, NULL, NULL, 'accessoire', 'Cônes d’agilité'),
	(b'1', 75, 30, '2025-09-03 20:05:00.000000', 16, 'cardio', 'Tapis roulant compact');

-- Listage de la structure de table location. people
CREATE TABLE IF NOT EXISTS `people` (
  `age` int(11) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gender` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Listage des données de la table location.people : ~30 rows (environ)
INSERT INTO `people` (`age`, `id`, `gender`, `name`) VALUES
	(22, 1, 'F', 'Alice Martin'),
	(28, 2, 'H', 'Karim Benali'),
	(35, 3, 'H', 'Jean Dupuis'),
	(19, 4, 'F', 'Sophie Tremblay'),
	(42, 5, 'H', 'Marc Gagnon'),
	(31, 6, 'F', 'Nadia Belkacem'),
	(26, 7, 'H', 'David Wong'),
	(24, 8, 'F', 'Élodie Laurent'),
	(30, 9, 'Autre', 'Alex Robin'),
	(38, 10, 'H', 'Mohamed Chérif'),
	(21, 11, 'F', 'Camille Bouchard'),
	(27, 12, 'H', 'Yassine Ait'),
	(33, 13, 'F', 'Maya Fortin'),
	(29, 14, 'H', 'Loïc Desjardins'),
	(25, 15, 'F', 'Inès Bensaïd'),
	(40, 16, 'H', 'Thomas Lavoie'),
	(23, 17, 'F', 'Clara Petit'),
	(34, 18, 'H', 'Bachir Rahmani'),
	(32, 19, 'F', 'Mélanie Girard'),
	(20, 20, 'H', 'Imen Saidi'),
	(36, 21, 'F', 'Laura Pelletier'),
	(28, 22, 'H', 'Anis Haddad'),
	(22, 23, 'F', 'Océane Leblanc'),
	(31, 24, 'H', 'Adam Meziane'),
	(29, 25, 'F', 'Sarah Lemieux'),
	(41, 26, 'H', 'Julien Caron'),
	(24, 27, 'F', 'Noémie Gervais'),
	(37, 28, 'H', 'Khalil Amrani'),
	(27, 29, 'F', 'Lina Moreau'),
	(35, 30, 'Autre', 'Sam Beaulieu');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
