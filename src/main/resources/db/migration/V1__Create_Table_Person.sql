CREATE TABLE IF NOT EXISTS `rest_with_spring_boot_java`.`person` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(80) NOT NULL,
  `last_name` VARCHAR(80) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `gender` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`id`));
