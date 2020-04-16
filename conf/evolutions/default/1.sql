# Todo schema

# --- !Ups
CREATE TABLE IF NOT EXISTS `scalatestdb1`.`todo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `isComplete` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8

# --- !Downs
drop table 'todo'