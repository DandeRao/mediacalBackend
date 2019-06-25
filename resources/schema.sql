CREATE TABLE `medicine` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `middle_name` VARCHAR(45) NULL,
  `occupation` VARCHAR(45) NULL,
  `age` INT NULL,
  `dob` DATE NULL,
  `created_on` TIMESTAMP NULL,
  `updated_on` TIMESTAMP NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `person_id` INT NULL,
  `address_1` VARCHAR(45) NULL,
  `address_2` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(2) NULL,
  `zip_code` VARCHAR(5) NULL,
  `created_on` TIMESTAMP NULL,
  `updated_on` TIMESTAMP NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `address` 
ADD CONSTRAINT `person_to_address`
  FOREIGN KEY (`person_id`)
  REFERENCES `medicine` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


