CREATE TABLE `User` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `username` VARCHAR(255) NOT NULL ,
    `email` VARCHAR(255) NOT NULL ,
    `password` VARCHAR(255) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
CREATE TABLE `Record` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(255) NOT NULL ,
    `username` VARCHAR(255) NULL DEFAULT NULL ,
    `password` VARCHAR(255) NOT NULL ,
    `url` VARCHAR(255) NULL DEFAULT NULL ,
    `note` TEXT NULL DEFAULT NULL ,
    `folder_id` INT(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
CREATE TABLE `Folder` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(255) NOT NULL ,
    `parent_folder` INT NULL ,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;