BEGIN ;

ALTER TABLE loan ADD COLUMN invest_begin_time DATETIME NULL;

COMMIT ;