CREATE TABLE `passwordManager`.`user` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `salt`  VARCHAR(255) NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB;
CREATE TABLE `passwordManager`.`record` (
    `record_id` INT NOT NULL AUTO_INCREMENT,
    `user_id_fk` INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NULL,
    `username` VARCHAR(255) NULL DEFAULT NULL,
    `password` VARCHAR(255) NOT NULL,
    `url` VARCHAR(255) NULL DEFAULT NULL,
    `note` TEXT NULL DEFAULT NULL,
    PRIMARY KEY (`record_id`),
    FOREIGN KEY (`user_id_fk`) REFERENCES `user`(`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;
