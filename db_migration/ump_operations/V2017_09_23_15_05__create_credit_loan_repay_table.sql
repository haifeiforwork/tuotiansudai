CREATE TABLE `ump_operations`.`credit_loan_repay_project_transfer_request` (
  `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `service`         VARCHAR(32)     NOT NULL,
  `sign_type`       VARCHAR(8)      NOT NULL,
  `sign`            VARCHAR(256)    NOT NULL,
  `charset`         VARCHAR(16)     NOT NULL,
  `mer_id`          VARCHAR(8)      NOT NULL,
  `version`         VARCHAR(3)      NOT NULL,
  `ret_url`         VARCHAR(128)    NULL,
  `notify_url`      VARCHAR(128)    NULL,
  `order_id`        VARCHAR(32)     NOT NULL,
  `mer_date`        VARCHAR(8)      NOT NULL,
  `project_id`      VARCHAR(32)     NOT NULL,
  `serv_type`       VARCHAR(2)      NOT NULL,
  `trans_action`    VARCHAR(2)      NOT NULL,
  `partic_type`     VARCHAR(2)      NOT NULL,
  `partic_acc_type` VARCHAR(2)      NOT NULL,
  `partic_user_id`  VARCHAR(32)     NOT NULL,
  `amount`          VARCHAR(13)     NOT NULL,
  `request_time`    DATETIME        NOT NULL,
  `request_url`     VARCHAR(2048)   NOT NULL,
  `request_data`    TEXT            NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8;


CREATE TABLE `ump_operations`.`credit_loan_repay_project_transfer_response` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `request_id`     BIGINT UNSIGNED NOT NULL,
  `sign_type`      VARCHAR(16)              DEFAULT NULL,
  `sign`           VARCHAR(512)             DEFAULT NULL,
  `mer_id`         VARCHAR(16)              DEFAULT NULL,
  `version`        VARCHAR(6)               DEFAULT NULL,
  `mer_date`       VARCHAR(16)              DEFAULT NULL,
  `mer_check_date` VARCHAR(16)              DEFAULT NULL,
  `trade_no`       VARCHAR(32)              DEFAULT NULL,
  `ret_code`       VARCHAR(16)              DEFAULT NULL,
  `ret_msg`        VARCHAR(256)             DEFAULT NULL,
  `response_data`  TEXT            NOT NULL,
  `response_time`  DATETIME        NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT FK_CREDIT_LOAN_REPAY_RESPONSE_REQUEST_ID_REF_REQUEST_ID FOREIGN KEY (`request_id`) REFERENCES `ump_operations`.`credit_loan_repay_project_transfer_request` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8;


CREATE TABLE `ump_operations`.`credit_loan_repay_notify_request` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `service`        VARCHAR(64)     NOT NULL,
  `sign_type`      VARCHAR(16)     NOT NULL,
  `sign`           VARCHAR(512)    NOT NULL,
  `mer_id`         VARCHAR(16)     NOT NULL,
  `version`        VARCHAR(6)      NOT NULL,
  `order_id`       VARCHAR(64)     NOT NULL,
  `mer_date`       VARCHAR(16)     NOT NULL,
  `trade_no`       VARCHAR(32)     NOT NULL,
  `mer_check_date` VARCHAR(16),
  `ret_code`       VARCHAR(16),
  `ret_msg`        VARCHAR(256),
  `request_time`   DATETIME        NOT NULL,
  `response_time`  DATETIME,
  `request_data`   TEXT            NOT NULL,
  `response_data`  TEXT,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8;
