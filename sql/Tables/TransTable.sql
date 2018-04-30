-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 30, 2018 at 11:06 PM
-- Server version: 5.7.21-0ubuntu0.16.04.1
-- PHP Version: 7.0.28-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `p2`
--

-- --------------------------------------------------------

--
-- Table structure for table `TransDB`
--

CREATE TABLE `TransDB` (
  `cpr` tinytext NOT NULL,
  `transid` text NOT NULL,
  `aeskey` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TransDB`
--

INSERT INTO `TransDB` (`cpr`, `transid`, `aeskey`) VALUES
('2502125742', '89384ece-9cfc-42b6-9e7a-34c01d59ee65\r\n', 'ZnOsYgP78Ue7CtcVu76BzNQLTrHi6Tszz6AtF1ZEHZA='),
('2102514362', 'd0dae001-d216-446f-83a9-72ab1b868505', '44WwLDERRHTubnN0zmORqZes3jZSjQHUEpPgoG5tP1w=	\r\n'),
('2502125742', '7e6e5410-470f-46f9-a120-6d1f37103005', 'pQHkvL+rJW7Ca8JK3pQq2YyC9AqSQi62b6Gv08cbwrg='),
('1503714143', 'f9777aae-ef68-44e7-bc9b-8758c20069a3', 'COuav5OugPpTuv39k77FtPacrNJZS4lxUPD7XLdUBqk=');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
