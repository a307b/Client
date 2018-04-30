-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.9-log - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for p2
CREATE DATABASE IF NOT EXISTS `p2` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `p2`;

-- Dumping structure for table p2.borger
CREATE TABLE IF NOT EXISTS `borger` (
  `cpr` tinytext NOT NULL,
  `rsapublickey` varbinary(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table p2.borger: ~3 rows (approximately)
/*!40000 ALTER TABLE `borger` DISABLE KEYS */;
INSERT INTO `borger` (`cpr`, `rsapublickey`) VALUES
	('test', 'hello world'),
	('123', 'hello world'),
	('0011223344', '0�"0\r	*�H��\r\0�\00�\n�\0�"�!#�]�)���X>qJ�	�S(��$�����a�/4d[XL9�_�#r�g ��:7�m����:n��M�isJ�2\r���_ԡJY�S��pKb�i�n7��]"����#�TM�e�1&�*,D&QNO{��]U\0��LH8~���e�T��6��^_ˇ0�<�#�A�e�0|5�w�<���B-M�L��ĽUw۷���&Xz�`2�{)`�z�O;��Зސ��7���6M% �����A/�z2�>���&�\0');
/*!40000 ALTER TABLE `borger` ENABLE KEYS */;

-- Dumping structure for table p2.transaction
CREATE TABLE IF NOT EXISTS `transaction` (
  `cpr` tinytext NOT NULL,
  `transid` text NOT NULL,
  `aeskey` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table p2.transaction: ~4 rows (approximately)
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` (`cpr`, `transid`, `aeskey`) VALUES
	('2502125742', '89384ece-9cfc-42b6-9e7a-34c01d59ee65\r\n', 'ZnOsYgP78Ue7CtcVu76BzNQLTrHi6Tszz6AtF1ZEHZA='),
	('2102514362', 'd0dae001-d216-446f-83a9-72ab1b868505', '44WwLDERRHTubnN0zmORqZes3jZSjQHUEpPgoG5tP1w=	\r\n'),
	('2502125742', '7e6e5410-470f-46f9-a120-6d1f37103005', 'pQHkvL+rJW7Ca8JK3pQq2YyC9AqSQi62b6Gv08cbwrg='),
	('1503714143', 'f9777aae-ef68-44e7-bc9b-8758c20069a3', 'COuav5OugPpTuv39k77FtPacrNJZS4lxUPD7XLdUBqk=');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
