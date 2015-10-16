BEGIN ;
ALTER TABLE `recharge` ADD INDEX INDEX_RECHARGE_SOURCE (`source`);

ALTER TABLE `user` ADD INDEX INDEX_USER_SOURCE (`source`);

ALTER TABLE `invest` ADD INDEX INDEX_INVEST_SOURCE (`source`);

ALTER TABLE `withdraw_cash` ADD INDEX INDEX_WITHDEAW_CASH_SOURCE (`source`);

COMMIT ;