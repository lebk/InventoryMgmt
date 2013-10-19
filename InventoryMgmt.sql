SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `InventoryMgmt`;
USE `InventoryMgmt` ;

-- -----------------------------------------------------
-- Table `InventoryMgmt`.`PtCategory`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`PtCategory` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(120) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`PtSize`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`PtSize` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `size` VARCHAR(120) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`ptColor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`ptColor` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `color` VARCHAR(120) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`Product`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`Product` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(300) NULL ,
  `ptcatetoryId` INT NULL ,
  `ptSizeId` INT NULL ,
  `ptColorId` INT NULL ,
  `ptNumber` INT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `refer2ptcat` (`ptcatetoryId` ASC) ,
  INDEX `refer2ptsiz` (`ptSizeId` ASC) ,
  INDEX `refer2ptcol` (`ptColorId` ASC) ,
  CONSTRAINT `refer2ptcat`
    FOREIGN KEY (`ptcatetoryId` )
    REFERENCES `InventoryMgmt`.`PtCategory` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `refer2ptsiz`
    FOREIGN KEY (`ptSizeId` )
    REFERENCES `InventoryMgmt`.`PtSize` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `refer2ptcol`
    FOREIGN KEY (`ptColorId` )
    REFERENCES `InventoryMgmt`.`ptColor` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`businessType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`businessType` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `type` VARCHAR(40) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`ptDetails`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`ptDetails` (
  `id` INT NOT NULL ,
  `productId` INT NULL ,
  `btId` INT NULL ,
  `date` DATE NULL ,
  `opUser` VARCHAR(80) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `refer2bt` (`btId` ASC) ,
  CONSTRAINT `refer2bt`
    FOREIGN KEY (`btId` )
    REFERENCES `InventoryMgmt`.`businessType` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`userType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`userType` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `description` VARCHAR(80) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`User`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(80) NOT NULL ,
  `password` VARCHAR(80) NULL ,
  `lastLoginTime` TIMESTAMP NULL ,
  `createTime` TIMESTAMP NULL ,
  `type` INT NULL ,
  `isValid` TINYINT(1)  NULL DEFAULT true ,
  PRIMARY KEY (`id`) ,
  INDEX `refer2ut` (`type` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  CONSTRAINT `refer2ut`
    FOREIGN KEY (`type` )
    REFERENCES `InventoryMgmt`.`userType` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Inital the MetaData:
-- -----------------------------------------------------

insert into InventoryMgmt.UserType (name,description) values ("admin" ,"adminstrator user");
  
insert into InventoryMgmt.UserType (name,description) values ("regular", "normal user which can any start/stop his own jobs");
  
-- initial  default user (admin/password, regular/password: (regular/password).

insert into InventoryMgmt.User (name,password,email,type, createTime) values ("admin","password","lebk.lei@gmail.com","1" ,now());
    
insert into InventoryMgmt.User (name,password,email,type, createTime) values ("regular","password","lebk.lei2@gmail.com", "2",now());
insert into InventoryMgmt.User (name,password,email,type, createTime) values ("管理员","123456","lebk.lei3@gmail.com", "2",now());

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
