SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `InventoryMgmt` ;
USE `InventoryMgmt` ;

-- -----------------------------------------------------
-- Table `InventoryMgmt`.`UserType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`UserType` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`UserType` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(20) NOT NULL ,
  `description` VARCHAR(80) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `level_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`User` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`User` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(40) NOT NULL ,
  `password` VARCHAR(40) NULL ,
  `email` VARCHAR(50) NOT NULL ,
  `type` INT UNSIGNED NOT NULL ,
  `createTime` DATETIME NOT NULL ,
  `lastLoginTime` DATETIME NULL ,
  `etrackId` INT NULL ,
  `isValid` TINYINT(1) NULL DEFAULT true ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  INDEX `uRefer2UserType` (`type` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) ,
  CONSTRAINT `uRefer2UserType`
    FOREIGN KEY (`type` )
    REFERENCES `InventoryMgmt`.`UserType` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`ProductType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`ProductType` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`ProductType` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(80) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`Product` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`Product` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `pVersion` VARCHAR(45) NULL ,
  `pKey` VARCHAR(100) NULL ,
  `ptId` INT UNSIGNED NOT NULL ,
  `description` VARCHAR(150) NULL ,
  `location` VARCHAR(200) NOT NULL ,
  `supportedOSList` VARCHAR(200) NULL ,
  `addTime` DATETIME NULL ,
  `uploadUser` VARCHAR(40) NOT NULL ,
  `isValid` TINYINT(1) NULL DEFAULT true ,
  PRIMARY KEY (`id`) ,
  INDEX `prefer2ProductType` (`ptId` ASC) ,
  
  UNIQUE INDEX `name_UNIQUE` (`name`) ,
  UNIQUE INDEX `prodType_version_UNIQUE` (`ptId`,`pVersion`),
  UNIQUE INDEX `location_UNIQUE` (`location` ASC) ,
  CONSTRAINT `prefer2ProductType`
    FOREIGN KEY (`ptId` )
    REFERENCES `InventoryMgmt`.`ProductType` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`Os`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`Os` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`Os` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `adminname` VARCHAR(45) NULL ,
  `adminpassword` VARCHAR(45) NULL ,
  `addTime` DATETIME NULL ,
  `location` VARCHAR(300) NULL ,
  `isValid` TINYINT(1) NULL DEFAULT true ,
  `isRBCS` TINYINT(1) NULL DEFAULT false ,
  PRIMARY KEY (`id`),
UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`JobStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`JobStatus` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`JobStatus` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(80) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`Job`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`Job` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`Job` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `osId` INT UNSIGNED NOT NULL ,
  `prodId` INT UNSIGNED NOT NULL ,
  `jobStatusId` INT UNSIGNED NOT NULL ,
  `userId` INT UNSIGNED NOT NULL ,
  `targetVMLocation` VARCHAR(300) NULL ,
  `startTime` DATETIME NULL ,
  `completeTime` DATETIME NULL ,
  `errorTime` DATETIME NULL ,
  `isRequestToStop` TINYINT(1) NOT NULL DEFAULT false ,
  `isProdSetting` TINYINT(1) NOT NULL DEFAULT false ,
  `isProdActivate` TINYINT(1) NOT NULL DEFAULT false ,
  `stopBy` INT UNSIGNED NULL ,
  `stopStartTime` DATETIME NULL ,
  `stopCompleteTime` DATETIME NULL ,
  `stopErrorTime` DATETIME NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `Jrefer2OS` (`osId` ASC) ,
  INDEX `Jrefer2JobStatus` (`jobStatusId` ASC) ,
  INDEX `Jrefer2User` (`userId` ASC) ,
  INDEX `StopByRefer2Use` (`stopBy` ASC) ,
  CONSTRAINT `Jrefer2OS`
    FOREIGN KEY (`osId` )
    REFERENCES `InventoryMgmt`.`Os` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Jrefer2product`
    FOREIGN KEY (`prodId` )
    REFERENCES `InventoryMgmt`.`Product` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Jrefer2JobStatus`
    FOREIGN KEY (`jobStatusId` )
    REFERENCES `InventoryMgmt`.`JobStatus` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Jrefer2User`
    FOREIGN KEY (`userId` )
    REFERENCES `InventoryMgmt`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `StopByRefer2Use`
    FOREIGN KEY (`stopBy` )
    REFERENCES `InventoryMgmt`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`LocationType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`LocationType` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`LocationType` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `description` VARCHAR(80) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`Location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`Location` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`Location` (
  `id` INT UNSIGNED NULL AUTO_INCREMENT ,
  `type` VARCHAR(45) NULL ,
  `url` VARCHAR(200) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `refer2LocationType` (`type` ASC) ,
  CONSTRAINT `refer2LocationType`
    FOREIGN KEY (`type` )
    REFERENCES `InventoryMgmt`.`LocationType` (`name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`JobProgress`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InventoryMgmt`.`JobProgress` ;

CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`JobProgress` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `jobId` INT UNSIGNED NOT NULL ,
  `jobStatusId` INT UNSIGNED NOT NULL ,
  `startTime` DATETIME NOT NULL ,
  `endTime` DATETIME NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `jobProgressRefer2Job` (`jobId` ASC) ,
  INDEX `jobPogressRefer2JobStatus` (`jobStatusId` ASC) ,
  CONSTRAINT `jobProgressRefer2Job`
    FOREIGN KEY (`jobId` )
    REFERENCES `InventoryMgmt`.`Job` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `jobPogressRefer2JobStatus`
    FOREIGN KEY (`jobStatusId` )
    REFERENCES `InventoryMgmt`.`JobStatus` (`id` )
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

-- Initial JobStatus
  
  insert into InventoryMgmt.JobStatus (name, description) values ("Start", "Job is starting");
  insert into InventoryMgmt.JobStatus (name, description) values ("CloneVM", "Clone the image from base image to a new image");
  insert into InventoryMgmt.JobStatus (name, description) values ("StartupVM", "start up the cloned VM");
  insert into InventoryMgmt.JobStatus (name, description) values ("UploadFile", "Upload the file to the guest OS where the sofware will be installed");
  insert into InventoryMgmt.JobStatus (name, description) values ("InstallFile", "Install the software on the guest OS");
  insert into InventoryMgmt.JobStatus (name, description) values ("SettingProduct", "Configuration the product just installed!");
  insert into InventoryMgmt.JobStatus (name, description) values ("ActivateProduct", "Activate the Product!");
  insert into InventoryMgmt.JobStatus (name, description) values ("ConfigVM", "Configure the VM like remove the user/password, enable auto login, etc!");
  insert into InventoryMgmt.JobStatus (name, description) values ("ShutdownVM", "Shutdown the cloned VM (after install the software)");
  insert into InventoryMgmt.JobStatus (name, description) values ("RemoveClonedVM", "Shutdown the cloned VM (after install the software)");
  insert into InventoryMgmt.JobStatus (name, description) values ("ExportToOVF", "Export the image to OVF format");
  insert into InventoryMgmt.JobStatus (name, description) values ("ZipOVF", "compress the exported OVF files");
  insert into InventoryMgmt.JobStatus (name, description) values ("MoveToFileServer", "Move the configed OVF file to target location");
  insert into InventoryMgmt.JobStatus (name, description) values ("RemoveOVF", "Remove the converted OVF Files (and the zipped one)");
  insert into InventoryMgmt.JobStatus (name, description) values ("Complete", "Job is complete");
  insert into InventoryMgmt.JobStatus (name, description) values ("Stop", "Job is stoped by the user");
  insert into InventoryMgmt.JobStatus (name, description) values ("Error", "Job is not finished correctly");
  insert into InventoryMgmt.JobStatus (name, description) values ("RequestToStop", "Job is requested to stop");
  
  -- Initial the location type table.
  insert into InventoryMgmt.LocationType(name, description) values ("targetVmbaseLocation","Where the configred VM will be copied to");
  insert into InventoryMgmt.LocationType(name, description) values ("ovftoolConvertLocation","Where the ovf tool existed");
  insert into InventoryMgmt.LocationType(name, description) values ("osBaseLocation","where the OS is stored");
  insert into InventoryMgmt.LocationType(name, description) values ("productBaseLocation","where the product is stored after uploaded");
  insert into InventoryMgmt.LocationType(name, description) values ("productInstallLocation","where the product will be installed");
  -- Initial the product type table.
  insert into InventoryMgmt.ProductType(name) values ("NIS2012");
  insert into InventoryMgmt.ProductType(name) values ("NIS2013");
  insert into InventoryMgmt.ProductType(name) values ("SEP_Amber");
  insert into InventoryMgmt.ProductType(name) values ("SEP_Jaguar");
  insert into InventoryMgmt.ProductType(name) values ("N360");

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
