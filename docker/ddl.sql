CREATE TABLE `User` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `username` VARCHAR(255) NOT NULL ,
    `email` VARCHAR(255) NOT NULL ,
    `password` VARCHAR(255) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
CREATE TABLE `Record` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `user_id` INT NOT NULL ,
    `name` VARCHAR(255) NOT NULL ,
    `username` VARCHAR(255) NULL DEFAULT NULL ,
    `password` VARCHAR(255) NOT NULL ,
    `url` VARCHAR(255) NULL DEFAULT NULL ,
    `note` TEXT NULL DEFAULT NULL ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES User(`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;
CREATE UNIQUE INDEX unique_username ON User(username);