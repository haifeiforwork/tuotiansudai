SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `point_prize`;
insert into `point_prize`(`id`,`name`,`description`,`coupon_id`,`cash`,`probability`,`active`) values
('100001','JapanKoreaTravel','�պ���',null,null,'0','1'),
('100002','HKMacaoTravel','�۰���',null,null,'0','0'),
('100003','Iphone6sPlus','iphone6s plus',null,null,'0','1'),
('100004','ThankYou','лл����',null,null,'44','0'),
('100005','InterestCoupon0.2','0.2%��Ϣȯ','301',null,'26','1'),
('100007','Cash5','�ֽ�5Ԫ',null,'500','3','0'),
('100008','Cash2','�ֽ�2Ԫ',null,'200','2','0'),
('100009','TtsdUDisk','�����ٴ�U��',null,null,'2','1'),
('100010','MangoTravel100','100Ԫâ�����ο�',null,null,'2','1'),
('100011','InsulationCup','�໨�ɱ��±�',null,null,'5','1'),
('100012','InterestCoupon2','2%��Ϣȯ','303',null,'5','1'),
('100013','RedEnvelope2','2Ԫ���','304',null,'60','1');
SET FOREIGN_KEY_CHECKS = 1;

