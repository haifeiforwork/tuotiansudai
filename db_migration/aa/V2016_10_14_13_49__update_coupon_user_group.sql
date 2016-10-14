BEGIN;

update coupon set deleted = '1' where id in (100028, 100029, 100030, 100031, 100032);

insert into `coupon`(`id`,`amount`,`rate`,`birthday_benefit`,`multiple`,`start_time`,`end_time`,`deadline`,`used_count`,`total_count`,`issued_count`,`active`,`shared`,`created_by`,`created_time`,`activated_by`,`activated_time`,`updated_by`,`updated_time`,`invest_lower_limit`,`product_types`,`coupon_type`,`user_group`,`sms_alert`,`deleted`,`coupon_source`,`comment`)
values('315','2800','0','0','0','2016-10-14 00:00:00','2016-12-31 23:59:59','30','0','9999999','0','1','0','sidneygao','2016-10-14 19:13:08','sidneygao','2016-10-14 18:40:38',null,'2016-10-14 21:25:44','50000','_30,_90,_180,_360','RED_ENVELOPE','EXPERIENCE_INVEST_SUCCESS','0','0','拓天速贷平台赠送','');
insert into `coupon`(`id`,`amount`,`rate`,`birthday_benefit`,`multiple`,`start_time`,`end_time`,`deadline`,`used_count`,`total_count`,`issued_count`,`active`,`shared`,`created_by`,`created_time`,`activated_by`,`activated_time`,`updated_by`,`updated_time`,`invest_lower_limit`,`product_types`,`coupon_type`,`user_group`,`sms_alert`,`deleted`,`coupon_source`,`comment`)
values('316','5000','0','0','0','2016-10-14 00:00:00','2016-12-31 23:59:59','30','0','999999','0','1','0','sidneygao','2016-10-14 17:36:18','sidneygao','2016-10-14 18:40:28',null,'2016-10-14 21:25:44','1999900','_190,_360','RED_ENVELOPE','EXPERIENCE_INVEST_SUCCESS','0','0','拓天速贷平台赠送','');
insert into `coupon`(`id`,`amount`,`rate`,`birthday_benefit`,`multiple`,`start_time`,`end_time`,`deadline`,`used_count`,`total_count`,`issued_count`,`active`,`shared`,`created_by`,`created_time`,`activated_by`,`activated_time`,`updated_by`,`updated_time`,`invest_lower_limit`,`product_types`,`coupon_type`,`user_group`,`sms_alert`,`deleted`,`coupon_source`,`comment`)
values('317','15000','0','0','0','2016-10-14 00:00:00','2016-12-31 23:59:59','30','0','999999','0','1','0','sidneygao','2016-10-14 17:37:39','sidneygao','2016-10-14 18:40:21',null,'2016-10-14 21:03:27','4999900','_180,_360','RED_ENVELOPE','EXPERIENCE_INVEST_SUCCESS','0','0','拓天速贷平台赠送','');
insert into `coupon`(`id`,`amount`,`rate`,`birthday_benefit`,`multiple`,`start_time`,`end_time`,`deadline`,`used_count`,`total_count`,`issued_count`,`active`,`shared`,`created_by`,`created_time`,`activated_by`,`activated_time`,`updated_by`,`updated_time`,`invest_lower_limit`,`product_types`,`coupon_type`,`user_group`,`sms_alert`,`deleted`,`coupon_source`,`comment`)
values('318','15000','0','0','0','2016-10-14 00:00:00','2016-12-31 23:59:59','30','0','999999','0','1','0','sidneygao','2016-10-14 17:38:49','sidneygao','2016-10-14 18:40:19','sidneygao','2016-10-14 17:40:57','1999900','_180,_360','RED_ENVELOPE','EXPERIENCE_INVEST_SUCCESS','0','0','拓天速贷平台赠送','');
insert into `coupon`(`id`,`amount`,`rate`,`birthday_benefit`,`multiple`,`start_time`,`end_time`,`deadline`,`used_count`,`total_count`,`issued_count`,`active`,`shared`,`created_by`,`created_time`,`activated_by`,`activated_time`,`updated_by`,`updated_time`,`invest_lower_limit`,`product_types`,`coupon_type`,`user_group`,`sms_alert`,`deleted`,`coupon_source`,`comment`)
values('319','21000','0','0','0','2016-10-14 00:00:00','2016-12-31 23:59:59','30','0','999999','0','1','0','sidneygao','2016-10-14 17:39:37','sidneygao','2016-10-14 18:40:16',null,'2016-10-14 21:25:44','5999900','_180,_360','RED_ENVELOPE','EXPERIENCE_INVEST_SUCCESS','0','0','拓天速贷平台赠送','');

COMMIT;
