-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema car_rent
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `car_rent` ;

-- -----------------------------------------------------
-- Schema car_rent
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `car_rent` DEFAULT CHARACTER SET utf8 ;
USE `car_rent` ;

-- -----------------------------------------------------
-- Table `car_rent`.`quality_class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_rent`.`quality_class` ;

CREATE TABLE IF NOT EXISTS `car_rent`.`quality_class` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` ENUM('city_car', 'sports_car', 'family_car', 'luxury') NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rent`.`car`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_rent`.`car` ;

CREATE TABLE IF NOT EXISTS `car_rent`.`car` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(120) NOT NULL,
  `license_plate` VARCHAR(10) NOT NULL,
  `quality_class_id` INT UNSIGNED NOT NULL,
  `price` DOUBLE UNSIGNED NOT NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_car_quality_class1_idx` (`quality_class_id` ASC) VISIBLE,
  CONSTRAINT `fk_car_quality_class1`
    FOREIGN KEY (`quality_class_id`)
    REFERENCES `car_rent`.`quality_class` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rent`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_rent`.`role` ;

CREATE TABLE IF NOT EXISTS `car_rent`.`role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` ENUM('admin', 'manager', 'customer') NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rent`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_rent`.`account` ;

CREATE TABLE IF NOT EXISTS `car_rent`.`account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `role_id` INT UNSIGNED NOT NULL,
  `blocked` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `fk_account_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_account_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `car_rent`.`role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rent`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_rent`.`status` ;

CREATE TABLE IF NOT EXISTS `car_rent`.`status` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` ENUM('new', 'approved', 'rejected', 'paid', 'returned') NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rent`.`booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_rent`.`booking` ;

CREATE TABLE IF NOT EXISTS `car_rent`.`booking` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` INT UNSIGNED NOT NULL,
  `passport` VARCHAR(45) NOT NULL,
  `lease_term` INT UNSIGNED NOT NULL,
  `driver` TINYINT(1) NOT NULL,
  `status_id` INT UNSIGNED NOT NULL,
  `damage` TINYINT(1) NOT NULL DEFAULT 0,
  `damage_paid` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_booking_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_booking_account2_idx` (`account_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_booking_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `car_rent`.`status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_account2`
    FOREIGN KEY (`account_id`)
    REFERENCES `car_rent`.`account` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rent`.`booking_has_car`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_rent`.`booking_has_car` ;

CREATE TABLE IF NOT EXISTS `car_rent`.`booking_has_car` (
  `booking_id` INT UNSIGNED NOT NULL,
  `car_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`booking_id`, `car_id`),
  INDEX `fk_car_has_booking_booking1_idx` (`booking_id` ASC) VISIBLE,
  INDEX `fk_car_has_booking_car1_idx` (`car_id` ASC) VISIBLE,
  CONSTRAINT `fk_car_has_booking_car1`
    FOREIGN KEY (`car_id`)
    REFERENCES `car_rent`.`car` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_car_has_booking_booking1`
    FOREIGN KEY (`booking_id`)
    REFERENCES `car_rent`.`booking` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `car_rent`.`quality_class`
-- -----------------------------------------------------
START TRANSACTION;
USE `car_rent`;
INSERT INTO `car_rent`.`quality_class` (`id`, `name`) VALUES (1, 'city_car');
INSERT INTO `car_rent`.`quality_class` (`id`, `name`) VALUES (2, 'family_car');
INSERT INTO `car_rent`.`quality_class` (`id`, `name`) VALUES (3, 'sports_car');
INSERT INTO `car_rent`.`quality_class` (`id`, `name`) VALUES (4, 'luxury');

COMMIT;


-- -----------------------------------------------------
-- Data for table `car_rent`.`car`
-- -----------------------------------------------------
START TRANSACTION;
USE `car_rent`;
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (1, 'Ford Mondeo', 'AA4648IB', 2, 20, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (2, 'Porsche Panamera', 'AA2345EA', 4, 50, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (3, 'Fiat 500', 'AA7654IC', 1, 40, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (4, 'BMW M5', 'AA7654CI', 3, 30, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (5, 'Ford Focus', 'AA4656IA', 2, 25, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (6, 'Porsche Cayenne', 'AA2347AA', 4, 55, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (7, 'Ferrari 430', 'AA6754IK', 3, 35, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (8, 'Deo Lanos', 'AA1453CC', 1, 15, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (9, 'Tesla X', 'AA6532EE', 4, 65, 1);
INSERT INTO `car_rent`.`car` (`id`, `model`, `license_plate`, `quality_class_id`, `price`, `available`) VALUES (10, 'Tesla S', 'AA5423II', 4, 60, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `car_rent`.`role`
-- -----------------------------------------------------
START TRANSACTION;
USE `car_rent`;
INSERT INTO `car_rent`.`role` (`id`, `name`) VALUES (1, 'admin');
INSERT INTO `car_rent`.`role` (`id`, `name`) VALUES (2, 'manager');
INSERT INTO `car_rent`.`role` (`id`, `name`) VALUES (3, 'customer');

COMMIT;


-- -----------------------------------------------------
-- Data for table `car_rent`.`account`
-- -----------------------------------------------------
START TRANSACTION;
USE `car_rent`;
INSERT INTO `car_rent`.`account` (`id`, `login`, `password`, `email`, `role_id`, `blocked`) VALUES (1, 'admin', 'admin', 'admin@gmail.com', 1, 0);
INSERT INTO `car_rent`.`account` (`id`, `login`, `password`, `email`, `role_id`, `blocked`) VALUES (2, 'manager', 'manager', 'manager@gmail.com', 2, 0);
INSERT INTO `car_rent`.`account` (`id`, `login`, `password`, `email`, `role_id`, `blocked`) VALUES (3, 'customer', 'customer', 'customer@gmail.com', 3, 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `car_rent`.`status`
-- -----------------------------------------------------
START TRANSACTION;
USE `car_rent`;
INSERT INTO `car_rent`.`status` (`id`, `name`) VALUES (1, 'new');
INSERT INTO `car_rent`.`status` (`id`, `name`) VALUES (2, 'approved');
INSERT INTO `car_rent`.`status` (`id`, `name`) VALUES (3, 'rejected');
INSERT INTO `car_rent`.`status` (`id`, `name`) VALUES (4, 'paid');
INSERT INTO `car_rent`.`status` (`id`, `name`) VALUES (5, 'returned');

COMMIT;


-- -----------------------------------------------------
-- Data for table `car_rent`.`booking`
-- -----------------------------------------------------
START TRANSACTION;
USE `car_rent`;
INSERT INTO `car_rent`.`booking` (`id`, `account_id`, `passport`, `lease_term`, `driver`, `status_id`, `damage`, `damage_paid`) VALUES (1, 3, '3452', 10, 1, 1, 0, 0);
INSERT INTO `car_rent`.`booking` (`id`, `account_id`, `passport`, `lease_term`, `driver`, `status_id`, `damage`, `damage_paid`) VALUES (2, 3, '3478', 2, 0, 2, 0, 0);
INSERT INTO `car_rent`.`booking` (`id`, `account_id`, `passport`, `lease_term`, `driver`, `status_id`, `damage`, `damage_paid`) VALUES (3, 3, '9865', 3, 1, 4, 0, 0);
INSERT INTO `car_rent`.`booking` (`id`, `account_id`, `passport`, `lease_term`, `driver`, `status_id`, `damage`, `damage_paid`) VALUES (4, 3, '6789', 2, 1, 5, 1, 1);
INSERT INTO `car_rent`.`booking` (`id`, `account_id`, `passport`, `lease_term`, `driver`, `status_id`, `damage`, `damage_paid`) VALUES (5, 3, '6723', 5, 0, 5, 0, 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `car_rent`.`booking_has_car`
-- -----------------------------------------------------
START TRANSACTION;
USE `car_rent`;
INSERT INTO `car_rent`.`booking_has_car` (`booking_id`, `car_id`) VALUES (1, 1);
INSERT INTO `car_rent`.`booking_has_car` (`booking_id`, `car_id`) VALUES (1, 2);
INSERT INTO `car_rent`.`booking_has_car` (`booking_id`, `car_id`) VALUES (2, 3);
INSERT INTO `car_rent`.`booking_has_car` (`booking_id`, `car_id`) VALUES (3, 4);
INSERT INTO `car_rent`.`booking_has_car` (`booking_id`, `car_id`) VALUES (4, 1);
INSERT INTO `car_rent`.`booking_has_car` (`booking_id`, `car_id`) VALUES (5, 2);
INSERT INTO `car_rent`.`booking_has_car` (`booking_id`, `car_id`) VALUES (5, 4);

COMMIT;

