begin;
alter table loan_details add column non_transferable tinyint(1) NOT NULL DEFAULT '0';
COMMIT;
