begin;
alter table user add column experience_gold BIGINT UNSIGNED default 0 NOT NULL;
COMMIT ;