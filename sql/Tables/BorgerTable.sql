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
-- Table structure for table `borger`
--

CREATE TABLE `borger` (
  `cpr` tinytext NOT NULL,
  `rsapublickey` varbinary(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borger`
--

INSERT INTO `borger` (`cpr`, `rsapublickey`) VALUES
('test', 0x68656c6c6f20776f726c64),
('123', 0x68656c6c6f20776f726c64),
('0011223344', 0x30820122300d06092a864886f70d01010105000382010f003082010a0282010100ca22fa2123dd5de8290cc6d6db583e71074a05c309e6532891ac02249abcf98fad61cc2f34645b584c39945fd7237219e3671c20f5e33a37e56dbed5e29be79e3a6ea4b0044daa69734aa1320d17fc19adfe5fd4a14a59fe538bbe704b0b62d269d56e1337a9a21a5d2292a6bf9423d204544dee65e53126ac2a2c4426514e4f067bac01a45d5500e8b21ff34c48387ebe858765ec9b549dc536f9a55e5fcb8730c73cdb238e074111b46585307c359577dc3cb9a68a422d4dba4caee0afc4bd5577dbb707c1e91fb61226587a916032cd7b29120b60ed7aba124f3b97aad097de90c016e837b791f1364d2520a2b2acdbe4412fe77a32843e8fad10af26c10b0203010001);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;