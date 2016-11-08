CREATE TABLE `anxin_operations`.`anxin_send_captcha_request` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tx_time` VARCHAR(14) NOT NULL,
  `user_id` VARCHAR(32) NOT NULL,
  `project_code` VARCHAR(32) NOT NULL,
  `is_send_voice` VARCHAR(1) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8;


CREATE TABLE `anxin_operations`.`anxin_send_captcha_response` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tx_time` VARCHAR(14),
  `ret_code` VARCHAR(20) NOT NULL,
  `ret_message` VARCHAR(100) NOT NULL,
  `user_id` VARCHAR(32),
  `project_code` VARCHAR(32),
  `is_send_voice` VARCHAR(1),
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8;

