CREATE TABLE `ump_operations`.`mer_recharge_person_request` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `service`      VARCHAR(32)     NOT NULL,
  `sign_type`    VARCHAR(8)      NOT NULL,
  `sign`         VARCHAR(256)    NOT NULL,
  `charset`      VARCHAR(16)     NOT NULL,
  `mer_id`       VARCHAR(8)      NOT NULL,
  `version`      VARCHAR(3)      NOT NULL,
  `ret_url`      VARCHAR(128)    NOT NULL,
  `notify_url`   VARCHAR(128)    NOT NULL,
  `order_id`     VARCHAR(32)     NOT NULL,
  `mer_date`     VARCHAR(8)      NOT NULL,
  `pay_type`     VARCHAR(16)     NOT NULL,
  `user_id`      VARCHAR(32)     NOT NULL,
  `amount`       VARCHAR(13)     NOT NULL,
  `gate_id`      VARCHAR(8)      NOT NULL,
  `request_time` DATETIME        NOT NULL,
  `request_url`  VARCHAR(2048)   NOT NULL,
  `request_data` TEXT            NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8;


CREATE TABLE `ump_operations`.`recharge_notify_request` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `service`        VARCHAR(32)     NOT NULL,
  `sign_type`      VARCHAR(8)      NOT NULL,
  `sign`           VARCHAR(256)    NOT NULL,
  `mer_id`         VARCHAR(8)      NOT NULL,
  `version`        VARCHAR(3)      NOT NULL,
  `order_id`       VARCHAR(32)     NOT NULL,
  `mer_date`       VARCHAR(8)      NOT NULL,
  `trade_no`       VARCHAR(16)     NOT NULL,
  `mer_check_date` VARCHAR(8),
  `balance`        VARCHAR(14),
  `com_amt`        VARCHAR(14),
  `ret_code`       VARCHAR(4),
  `ret_msg`        VARCHAR(128),
  `request_time`   DATETIME        NOT NULL,
  `response_time`  DATETIME,
  `request_data`   TEXT            NOT NULL,
  `response_data`  TEXT,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8;