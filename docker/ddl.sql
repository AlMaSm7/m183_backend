CREATE TABLE `passwordManager`.`user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `salt`  VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
CREATE TABLE `passwordManager`.`record` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id_fk` INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `username` VARCHAR(255) NULL DEFAULT NULL,
    `password` VARCHAR(255) NOT NULL,
    `url` VARCHAR(255) NULL DEFAULT NULL,
    `note` TEXT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id_fk`) REFERENCES `user`(`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;
