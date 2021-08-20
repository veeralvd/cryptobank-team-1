-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cryptoOptieEen
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cryptoOptieEen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cryptoOptieEen` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema cryptooptieeen
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cryptooptieeen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cryptooptieeen` DEFAULT CHARACTER SET utf8 ;
USE `cryptoOptieEen` ;

-- -----------------------------------------------------
-- Table `cryptoOptieEen`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptoOptieEen`.`User` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `salt` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptoOptieEen`.`bankAccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptoOptieEen`.`bankAccount` (
  `IBAN` VARCHAR(45) NOT NULL,
  `balance` FLOAT NOT NULL,
  PRIMARY KEY (`IBAN`),
  UNIQUE INDEX `IBAN_UNIQUE` (`IBAN` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptoOptieEen`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptoOptieEen`.`Customer` (
  `username` VARCHAR(45) NOT NULL,
  `voornaam` VARCHAR(45) NOT NULL,
  `achternaam` VARCHAR(45) NOT NULL,
  `geboortedatum` DATE NOT NULL,
  `bsn` BIGINT(12) NOT NULL,
  `straat` VARCHAR(45) NOT NULL,
  `postcode` VARCHAR(45) NOT NULL,
  `huisnummer` INT NOT NULL,
  `toevoeging` DECIMAL(5) NULL,
  `IBAN` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`),
  INDEX `fk_Customer_bankAccount1_idx` (`IBAN` ASC) VISIBLE,
  UNIQUE INDEX `IBAN_UNIQUE` (`IBAN` ASC) VISIBLE,
  CONSTRAINT `fk_Customer_User1`
    FOREIGN KEY (`username`)
    REFERENCES `cryptoOptieEen`.`User` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Customer_bankAccount1`
    FOREIGN KEY (`IBAN`)
    REFERENCES `cryptoOptieEen`.`bankAccount` (`IBAN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptoOptieEen`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptoOptieEen`.`admin` (
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `fk_admin_User`
    FOREIGN KEY (`username`)
    REFERENCES `cryptoOptieEen`.`User` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptoOptieEen`.`Asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptoOptieEen`.`Asset` (
  `abbreviation` VARCHAR(5) NOT NULL,
  `nameCrypto` VARCHAR(45) NOT NULL,
  `description` LONGTEXT NULL,
  UNIQUE INDEX `nameCrypto_UNIQUE` (`nameCrypto` ASC) VISIBLE,
  PRIMARY KEY (`abbreviation`),
  UNIQUE INDEX `abbreviation_UNIQUE` (`abbreviation` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptoOptieEen`.`ownedAsset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptoOptieEen`.`ownedAsset` (
  `IBAN` VARCHAR(45) NOT NULL,
  `abbreviation` VARCHAR(5) NOT NULL,
  `aantalEenheden` FLOAT NOT NULL,
  PRIMARY KEY (`IBAN`, `abbreviation`),
  INDEX `fk_bankAccount_has_Asset_Asset1_idx` (`abbreviation` ASC) VISIBLE,
  INDEX `fk_bankAccount_has_Asset_bankAccount1_idx` (`IBAN` ASC) VISIBLE,
  CONSTRAINT `fk_bankAccount_has_Asset_bankAccount1`
    FOREIGN KEY (`IBAN`)
    REFERENCES `cryptoOptieEen`.`bankAccount` (`IBAN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bankAccount_has_Asset_Asset1`
    FOREIGN KEY (`abbreviation`)
    REFERENCES `cryptoOptieEen`.`Asset` (`abbreviation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `cryptooptieeen` ;

-- -----------------------------------------------------
-- Table `cryptooptieeen`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptooptieeen`.`admin` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(512) NOT NULL,
  `salt` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cryptooptieeen`.`asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptooptieeen`.`asset` (
  `abbreviation` VARCHAR(5) NOT NULL,
  `nameCrypto` VARCHAR(45) NOT NULL,
  `description` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`abbreviation`),
  UNIQUE INDEX `nameCrypto_UNIQUE` (`nameCrypto` ASC) VISIBLE,
  UNIQUE INDEX `abbreviation_UNIQUE` (`abbreviation` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cryptooptieeen`.`bankaccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptooptieeen`.`bankaccount` (
  `IBAN` VARCHAR(45) NOT NULL,
  `balance` DOUBLE NOT NULL,
  PRIMARY KEY (`IBAN`),
  UNIQUE INDEX `IBAN_UNIQUE` (`IBAN` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cryptooptieeen`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptooptieeen`.`customer` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(512) NOT NULL,
  `salt` VARCHAR(16) NOT NULL,
  `voornaam` VARCHAR(45) NOT NULL,
  `achternaam` VARCHAR(45) NOT NULL,
  `geboortedatum` DATE NOT NULL,
  `bsn` BIGINT NOT NULL,
  `straat` VARCHAR(45) NOT NULL,
  `postcode` VARCHAR(45) NOT NULL,
  `huisnummer` INT NOT NULL,
  `toevoeging` DECIMAL(5,0) NULL DEFAULT NULL,
  `IBAN` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `IBAN_UNIQUE` (`IBAN` ASC) VISIBLE,
  INDEX `fk_Customer_bankAccount1_idx` (`IBAN` ASC) VISIBLE,
  PRIMARY KEY (`username`),
  CONSTRAINT `fk_Customer_bankAccount1`
    FOREIGN KEY (`IBAN`)
    REFERENCES `cryptooptieeen`.`bankaccount` (`IBAN`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cryptooptieeen`.`ownedasset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptooptieeen`.`ownedasset` (
  `IBAN` VARCHAR(45) NOT NULL,
  `abbreviation` VARCHAR(5) NOT NULL,
  `aantalEenheden` DOUBLE NOT NULL,
  PRIMARY KEY (`IBAN`, `abbreviation`),
  INDEX `fk_bankAccount_has_Asset_Asset1_idx` (`abbreviation` ASC) VISIBLE,
  INDEX `fk_bankAccount_has_Asset_bankAccount1_idx` (`IBAN` ASC) VISIBLE,
  CONSTRAINT `fk_bankAccount_has_Asset_Asset1`
    FOREIGN KEY (`abbreviation`)
    REFERENCES `cryptooptieeen`.`asset` (`abbreviation`),
  CONSTRAINT `fk_bankAccount_has_Asset_bankAccount1`
    FOREIGN KEY (`IBAN`)
    REFERENCES `cryptooptieeen`.`bankaccount` (`IBAN`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
