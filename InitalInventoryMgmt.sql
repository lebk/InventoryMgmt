SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `InventoryMgmt`;
USE `InventoryMgmt` ;

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
-- Table `InventoryMgmt`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(80) NOT NULL ,
  `password` VARCHAR(80) NULL ,
  `lastLoginTime` TIMESTAMP NULL ,
  `createTime` TIMESTAMP NULL ,
  `type` INT NULL ,
  `isValid` TINYINT(1)  NULL DEFAULT true ,
  `email` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `u_refer2ut` (`type` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) ,
  CONSTRAINT `u_refer2ut`
    FOREIGN KEY (`type` )
    REFERENCES `InventoryMgmt`.`userType` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`ptType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`ptType` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `type` VARCHAR(120) NOT NULL ,
  `opUserId` INT NULL ,
  `createTime` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `pt_refer2user` (`opUserId` ASC) ,
  CONSTRAINT `pt_refer2user`
    FOREIGN KEY (`opUserId` )
    REFERENCES `InventoryMgmt`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`ptSize`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`ptSize` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `size` VARCHAR(120) NOT NULL ,
  `opUserId` INT NULL ,
  `createTime` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `ps_refer2user` (`opUserId` ASC) ,
  CONSTRAINT `ps_refer2user`
    FOREIGN KEY (`opUserId` )
    REFERENCES `InventoryMgmt`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`ptColor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`ptColor` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `color` VARCHAR(120) NOT NULL ,
  `opUserId` INT NULL ,
  `createTime` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `pc_refer2user` (`opUserId` ASC) ,
  CONSTRAINT `pc_refer2user`
    FOREIGN KEY (`opUserId` )
    REFERENCES `InventoryMgmt`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`product`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(300) NULL ,
  `ptTypeId` INT NULL ,
  `ptSizeId` INT NULL ,
  `ptColorId` INT NULL ,
  `ptNumber` INT NULL ,
  `lastUpdateTime` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `p_refer2ptcat` (`ptTypeId` ASC) ,
  INDEX `p_refer2ptsiz` (`ptSizeId` ASC) ,
  INDEX `p_refer2ptcol` (`ptColorId` ASC) ,
  CONSTRAINT `p_refer2ptcat`
    FOREIGN KEY (`ptTypeId` )
    REFERENCES `InventoryMgmt`.`ptType` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `p_refer2ptsiz`
    FOREIGN KEY (`ptSizeId` )
    REFERENCES `InventoryMgmt`.`ptSize` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `p_refer2ptcol`
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
  `opUserId` INT NULL ,
  `createTime` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InventoryMgmt`.`ptDetails`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `InventoryMgmt`.`ptDetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `poId` INT NOT NULL ,
  `btId` INT NOT NULL ,
  `num` INT NULL ,
  `opUserId` INT NULL ,
  `date` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `pd_refer2bt` (`btId` ASC) ,
  INDEX `pd_refer2po` (`poId` ASC) ,
  INDEX `pd_refer2user` (`opUserId` ASC) ,
  CONSTRAINT `pd_refer2bt`
    FOREIGN KEY (`btId` )
    REFERENCES `InventoryMgmt`.`businessType` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pd_refer2po`
    FOREIGN KEY (`poId` )
    REFERENCES `InventoryMgmt`.`product` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pd_refer2user`
    FOREIGN KEY (`opUserId` )
    REFERENCES `InventoryMgmt`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- meta data 
-- -----------------------------------------------------
insert into InventoryMgmt.UserType (name,description) values ("admin" ,"adminstrator user");
  
insert into InventoryMgmt.UserType (name,description) values ("regular", "normal user");
  
-- initial  default user (admin/password, regular/password: (regular/password).
insert into InventoryMgmt.User (name,password,email,type, createTime) values ("管理员","123456","lebk.lei3@gmail.com", "1",now());
insert into InventoryMgmt.User (name,password,email,type, createTime) values ("admin","password","lebk.lei@gmail.com","1" ,now());    
insert into InventoryMgmt.User (name,password,email,type, createTime) values ("regular","password","lebk.lei2@gmail.com", "2",now());

insert into InventoryMgmt.businesstype (type,opUserId,createTime)  values ("入库",1,now());
insert into InventoryMgmt.businesstype (type,opUserId,createTime)  values ("出库",1,now());


insert into inventorymgmt.PtType(type,opUserId,createTime) values("强化地板类",1,now());
insert into inventorymgmt.PtType(type,opUserId,createTime) values("多层实木地板类",1,now());
insert into inventorymgmt.PtType(type,opUserId,createTime) values("油漆地板类",1,now());
-- -----------------------------------------------------
-- meta data end 
-- -----------------------------------------------------
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
