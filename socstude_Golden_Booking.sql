-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 05, 2019 at 04:49 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `socstude_Golden_Booking`
--

-- --------------------------------------------------------

--
-- Table structure for table `HomeStay`
--

CREATE TABLE `HomeStay` (
  `HomeStayID` varchar(100) NOT NULL,
  `HomeStayName` varchar(100) NOT NULL,
  `HomeStayPhone` varchar(20) NOT NULL,
  `HomeStayAddress` varchar(50) NOT NULL,
  `Location` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  `price` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `HomeStay`
--

INSERT INTO `HomeStay` (`HomeStayID`, `HomeStayName`, `HomeStayPhone`, `HomeStayAddress`, `Location`, `description`, `price`) VALUES
('11111', 'Sentosa Regency Hotel', '1125698745', 'Alor Setar', 'Alor Setar', 'Located in Changlun in the Kedah region, Dk Sun Homestay has a garden. ', 100),
('12122', 'Serambi Pelangi Chalet Changlun', '01124150825', 'Changlun', 'Changlun', 'Situated in Changlun, Serambi Pelangi Chalet Changlun provides 3-star accommodation with access to a', 90),
('99999', 'Serambi Pelangi Chalet Changlun', '01124150825', 'Changlun', 'Changlun', 'Situated in Changlun, Serambi Pelangi Chalet Changlun features 3-star accommodation with access to a garden and a barbecue. \r\n\r\nSome units have a seating area and/or a patio. \r\nA car rental service ', 90);

-- --------------------------------------------------------

--
-- Table structure for table `Ordered`
--

CREATE TABLE `Ordered` (
  `orderid` varchar(30) NOT NULL,
  `total` double NOT NULL,
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `Email` varchar(50) NOT NULL,
  `PhoneNumber` varchar(100) NOT NULL,
  `HomeStayID` varchar(60) NOT NULL,
  `HomeStayName` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Ordered`
--

INSERT INTO `Ordered` (`orderid`, `total`, `fromDate`, `toDate`, `Email`, `PhoneNumber`, `HomeStayID`, `HomeStayName`) VALUES
('XFMJPL2', 200, '2019-06-03', '2019-06-05', 'yeongshyhhaw@gmail.com', '01124150825', '11111', 'Sentosa Regency Hotel'),
('OIOPE53', 200, '2019-06-03', '2019-06-05', 'yeongshyhhaw@gmail.com', '01124150825', '11111', 'Sentosa Regency Hotel'),
('DVIF8K8', 100, '2019-06-04', '2019-06-05', 'yeongshyhhaw@gmail.com', '01124150825', '11111', 'Sentosa Regency Hotel'),
('QX367PM', 300, '2019-06-03', '2019-06-06', 'yeongshyhhaw@gmail.com', '01124150825', '11111', 'Sentosa Regency Hotel'),
('YWSLB6V', 100, '2019-06-09', '2019-06-10', 'yeongshyhhaw@gmail.com', '01124150825', '11111', 'Sentosa Regency Hotel'),
('Q9CG2IV', 200, '2019-06-04', '2019-06-06', 'yeongshyhhaw@gmail.com', '01124150825', '11111', 'Sentosa Regency Hotel');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `Email` varchar(50) NOT NULL,
  `Password` varchar(60) NOT NULL,
  `PhoneNumber` varchar(30) NOT NULL,
  `Name` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`Email`, `Password`, `PhoneNumber`, `Name`) VALUES
('lestle1997@hotmail.com', '78e27ccc66a5185ee1cde4fcf65b4df6a823d401', '01124150825', 'lestle yeong'),
('seesiang97@hotmail.com', '642791fb83af2842c6b64e65b1b9beb814dd7b2d', '01124150825', 'see siang'),
('lestleyeong@gmail.com', '642791fb83af2842c6b64e65b1b9beb814dd7b2d', '01124150825', 'lestle'),
('yeongshyhhaw@gmail.com', '78e27ccc66a5185ee1cde4fcf65b4df6a823d401', '01124150825', 'lestle yeong'),
('lestleyeong123@gmail.com', '7b52009b64fd0a2a49e6d8a939753077792b0554', '12', '12'),
('lestle199728@hotmail.com', '642791fb83af2842c6b64e65b1b9beb814dd7b2d', '7373', 'lestle199728'),
('lestle199728@gmail.com', '642791fb83af2842c6b64e65b1b9beb814dd7b2d', '12', '12'),
('lestle1997@gmail.com', '642791fb83af2842c6b64e65b1b9beb814dd7b2d', '01124150825', '121212'),
('hanis@gmail.com', '331a4f44a6a875b2ce139ae0c9ce5bb5e1ec0d97', '273723', 'asdsa'),
('hanis123@gmail.com', 'bd0c68db9bb48c9ff57a9051b7f7815ac9047b18', '01124150825', 'MR ahmad hanis'),
('MrAhmadHanis@gmail.com', 'ec26301c6758fb6590041c1020a831c4b064b0b2', '01124150825', 'Mr Ahmad Hanis'),
('MrAhmadHanis1@gmail.com', '93319badff13c923aa2589face96de973cdd4fbb', '01124150825', 'MrahmadHanis'),
('MrAhmadHanis@homail.com', '642791fb83af2842c6b64e65b1b9beb814dd7b2d', '01124150825', 'Mr Ahmad Hanis'),
('yeong@gmail.com', '642791fb83af2842c6b64e65b1b9beb814dd7b2d', '01124150825', 'lestle');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `HomeStay`
--
ALTER TABLE `HomeStay`
  ADD PRIMARY KEY (`HomeStayID`);

--
-- Indexes for table `Ordered`
--
ALTER TABLE `Ordered`
  ADD PRIMARY KEY (`orderid`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`Email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
