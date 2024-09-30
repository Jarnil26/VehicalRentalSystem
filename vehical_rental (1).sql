-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2024 at 06:25 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vehical_rental`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_login` (IN `p_username` VARCHAR(20), IN `p_password` VARCHAR(15), OUT `p_result` INT)   BEGIN
    DECLARE v_user_count INT;

    -- Check if the username and password match
    SELECT COUNT(*) INTO v_user_count
    FROM users
    WHERE user_name = p_username AND password = p_password;

    -- If a match is found, log the login and set the result to 1
    IF v_user_count = 1 THEN
        INSERT INTO login_logs (username, login_date_time)
        VALUES (p_username, NOW());
        SET p_result = 1;
    ELSE
        -- If no match, set the result to 0
        SET p_result = 0;
    END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `administrator`
--

CREATE TABLE `administrator` (
  `administrator_id` int(5) NOT NULL,
  `administrator_name` varchar(20) NOT NULL,
  `password` varchar(12) NOT NULL,
  `administrator_email` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `administrator`
--

INSERT INTO `administrator` (`administrator_id`, `administrator_name`, `password`, `administrator_email`) VALUES
(777, 'Jarnil', '2626', 'jaril@gmail.com'),
(1107, 'jarnil', '260110', 'jarnilp@gamil.com');

-- --------------------------------------------------------

--
-- Table structure for table `bike_list`
--

CREATE TABLE `bike_list` (
  `bike_no` int(5) NOT NULL,
  `bike_name` varchar(50) NOT NULL,
  `rent_prize_bike` float NOT NULL,
  `rating` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bike_list`
--

INSERT INTO `bike_list` (`bike_no`, `bike_name`, `rent_prize_bike`, `rating`) VALUES
(555, 'Ducati panigale V2', 1000, 8.5),
(1246, 'Suzuki Hayabusa', 800, 6),
(4632, 'Kawaski Ninja H2R', 1000, 9);

-- --------------------------------------------------------

--
-- Table structure for table `car_list`
--

CREATE TABLE `car_list` (
  `car_no` int(5) NOT NULL,
  `car_name` varchar(20) NOT NULL,
  `rent_prize` float NOT NULL,
  `rating` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='All Car List';

--
-- Dumping data for table `car_list`
--

INSERT INTO `car_list` (`car_no`, `car_name`, `rent_prize`, `rating`) VALUES
(9999, 'Ferrari 296gtb', 2000, 8.5),
(49678, 'Mahindra Scorpio', 1000, 8),
(63214, 'Mustang GT', 1200, 8),
(66457, 'Ferrari SF90', 1000, 8.5),
(78912, 'rollos roys', 2500, 9);

-- --------------------------------------------------------

--
-- Table structure for table `login_logs`
--

CREATE TABLE `login_logs` (
  `log_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `login_date_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_logs`
--

INSERT INTO `login_logs` (`log_id`, `username`, `login_date_time`) VALUES
(1, 'jarnil', '2024-08-22 07:35:11'),
(2, 'jarnil', '2024-08-22 07:39:31'),
(3, 'jarnil', '2024-08-23 06:10:27'),
(4, 'jarnil', '2024-08-23 09:01:45'),
(5, 'jarnil', '2024-08-23 09:03:24'),
(6, 'jarnil', '2024-08-23 09:09:05'),
(7, 'jarnil', '2024-08-23 09:16:10'),
(8, 'jarnil', '2024-08-23 09:18:59'),
(9, 'jarnil', '2024-08-23 09:22:32'),
(10, 'jarnil', '2024-08-23 13:07:29'),
(11, 'jarnil', '2024-08-24 04:02:01'),
(12, 'jarnil', '2024-08-24 04:08:27'),
(13, 'jarnil', '2024-08-24 04:09:26');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `vehical_no` int(5) NOT NULL,
  `username` varchar(12) NOT NULL,
  `rent_date_from` date NOT NULL,
  `rent_date_to` date NOT NULL,
  `total_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`vehical_no`, `username`, `rent_date_from`, `rent_date_to`, `total_amount`) VALUES
(78912, 'jarnil', '2024-07-14', '2024-07-20', 15000),
(56489, 'jarnil', '2024-07-14', '2024-07-19', 2500),
(66457, 'jarnil', '2024-08-22', '2024-09-22', 32000),
(9999, 'jarnil', '2024-08-22', '2024-09-15', 50000),
(49678, 'jarnil', '2024-08-24', '2024-08-25', 2000),
(9999, 'jarnil', '2024-10-23', '2024-10-24', 4000),
(9999, 'jarnil', '2024-11-11', '2024-11-12', 4000);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_name` varchar(12) NOT NULL,
  `password` varchar(12) NOT NULL,
  `email` varchar(20) NOT NULL,
  `phone_no` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='this is table of user of vehical rental web';

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_name`, `password`, `email`, `phone_no`) VALUES
('jarnil23', '26026', 'jarnil@gmail.com', '9574357983');

--
-- Triggers `users`
--
DELIMITER $$
CREATE TRIGGER `check_user_update` BEFORE UPDATE ON `users` FOR EACH ROW BEGIN
    IF OLD.user_name = NEW.user_name  THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Update failed: no changes detected';
    END IF;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrator`
--
ALTER TABLE `administrator`
  ADD PRIMARY KEY (`administrator_id`);

--
-- Indexes for table `bike_list`
--
ALTER TABLE `bike_list`
  ADD PRIMARY KEY (`bike_no`);

--
-- Indexes for table `car_list`
--
ALTER TABLE `car_list`
  ADD PRIMARY KEY (`car_no`);

--
-- Indexes for table `login_logs`
--
ALTER TABLE `login_logs`
  ADD PRIMARY KEY (`log_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_name`),
  ADD UNIQUE KEY `phone-no` (`phone_no`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `login_logs`
--
ALTER TABLE `login_logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
