ALTER TABLE `aa`.`loan_details`
ADD COLUMN `introduce` TEXT NULL AFTER loan_id;


ALTER TABLE `aa`.`loaner_details`
ADD COLUMN `source` VARCHAR(100) NULL AFTER region;
