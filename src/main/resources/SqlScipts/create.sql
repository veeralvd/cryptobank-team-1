-- MySQL Workbench Forward Engineering

-- SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
-- SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cryptobank
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cryptobank
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cryptobank` DEFAULT CHARACTER SET utf8 ;
USE `cryptobank` ;

-- -----------------------------------------------------
-- Table `cryptobank`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptobank`.`admin` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(512) NOT NULL,
  `salt` VARCHAR(16) NOT NULL,
  `token` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cryptobank`.`asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptobank`.`asset` (
  `abbreviation` VARCHAR(5) NOT NULL,
  `nameCrypto` VARCHAR(45) NOT NULL,
  `description` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`abbreviation`),
  UNIQUE INDEX `nameCrypto_UNIQUE` (`nameCrypto` ASC) VISIBLE,
  UNIQUE INDEX `abbreviation_UNIQUE` (`abbreviation` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cryptobank`.`bankaccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptobank`.`bankaccount` (
  `IBAN` VARCHAR(45) NOT NULL,
  `balance` DOUBLE NOT NULL,
  PRIMARY KEY (`IBAN`),
  UNIQUE INDEX `IBAN_UNIQUE` (`IBAN` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cryptobank`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptobank`.`customer` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(512) NOT NULL,
  `salt` VARCHAR(16) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `dateofbirth` DATE NOT NULL,
  `socialsecuritynumber` BIGINT NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(45) NOT NULL,
  `housenumber` INT NOT NULL,
  `addition` VARCHAR (5),
  `IBAN` VARCHAR(45) NOT NULL,
  `city` VARCHAR (45) NOT NULL,
  `token` VARCHAR(512) NOT NULL,
  UNIQUE INDEX `IBAN_UNIQUE` (`IBAN` ASC) VISIBLE,
  INDEX `fk_Customer_bankAccount1_idx` (`IBAN` ASC) VISIBLE,
  PRIMARY KEY (`username`),
  CONSTRAINT `fk_Customer_bankAccount1`
    FOREIGN KEY (`IBAN`)
    REFERENCES `cryptobank`.`bankaccount` (`IBAN`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cryptobank`.`ownedasset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptobank`.`ownedasset` (
  `IBAN` VARCHAR(45) NOT NULL,
  `abbreviation` VARCHAR(5) NOT NULL,
  `aantalEenheden` DOUBLE NOT NULL,
  PRIMARY KEY (`IBAN`, `abbreviation`),
  INDEX `fk_bankAccount_has_Asset_Asset1_idx` (`abbreviation` ASC) VISIBLE,
  INDEX `fk_bankAccount_has_Asset_bankAccount1_idx` (`IBAN` ASC) VISIBLE,
  CONSTRAINT `fk_bankAccount_has_Asset_Asset1`
    FOREIGN KEY (`abbreviation`)
    REFERENCES `cryptobank`.`asset` (`abbreviation`),
  CONSTRAINT `fk_bankAccount_has_Asset_bankAccount1`
    FOREIGN KEY (`IBAN`)
    REFERENCES `cryptobank`.`bankaccount` (`IBAN`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `cryptobank`.`crypto_currency_rate` (
    `abbreviation` VARCHAR(5) NOT NULL,
    `datetime` DATETIME NOT NULL,
    `value` DECIMAL(15,2) not null ,
    PRIMARY KEY (`abbreviation`, `datetime`),
    CONSTRAINT `fk_crypto_currency_rate_asset1`
    FOREIGN KEY (`abbreviation`)
    REFERENCES `cryptobank`.`asset` (`abbreviation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;

-- Gebruiker definiëren en toegang verlenen
CREATE USER 'cryptoUser'@'localhost' IDENTIFIED BY 'cryptoDB';
GRANT ALL PRIVILEGES ON cryptobank.* TO 'cryptoUser'@'localhost';

# SET SQL_MODE=@OLD_SQL_MODE;
# SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
# SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
