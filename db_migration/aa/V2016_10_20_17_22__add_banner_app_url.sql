BEGIN;

ALTER TABLE `aa`.`banner`
ADD COLUMN `app_url` VARCHAR(200) NULL AFTER `url`;

COMMIT;