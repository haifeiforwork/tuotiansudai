BEGIN ;
REPLACE INTO `menu` VALUES ('n-intro','Navigation','��������','/website/intro',NULL,1,'0','','1','*','_self','');
REPLACE INTO `menu` VALUES ('n-contact','Navigation','��ϵ����','/website/contactus','n-intro',1,'4','','1','*','_self','');
REPLACE INTO `menu` VALUES ('n-cost','Navigation','��վ����','/website/cost','n-center',1,'2','','1','*','_self','');
REPLACE INTO `menu` VALUES ('n-guide','Navigation','����ָ��','/website/guide','n-center',1,'1','','1','*','_self','');
REPLACE INTO `menu` VALUES ('n-introduction','Navigation','��˾����','/website/intro','n-intro',1,'1','','1','*','_self','');
REPLACE INTO `menu` VALUES ('n-media','Navigation','ý�屨��','/news/mediareport','n-intro',1,'3','','1','*','_self','');
REPLACE INTO `menu` VALUES ('n-protocol','Navigation','��վ����Э��','/website/service','n-center',1,'3','','1','*','_self','');
REPLACE INTO `menu` VALUES ('n-security','Navigation','��ȫ����','/website/safe','n-intro',1,'2','','1','*','_self','');

REPLACE INTO `url_mapping` VALUES ('company','/website/company','themepath:website/company.htm','');
REPLACE INTO `url_mapping` VALUES ('contactus','/website/contactus','themepath:website/contactus.htm','');
REPLACE INTO `url_mapping` VALUES ('joinus','/website/joinus','themepath:website/joinus.htm','');
REPLACE INTO `url_mapping` VALUES ('manage','/website/manage','themepath:website/manage.htm','');
REPLACE INTO `url_mapping` VALUES ('problem','/website/problem','themepath:website/problem.htm','');
REPLACE INTO `url_mapping` VALUES ('safe','/website/safe','themepath:website/safe.htm','');
REPLACE INTO `url_mapping` VALUES ('service','/website/service','themepath:website/service.htm','');
REPLACE INTO `url_mapping` VALUES ('team','/website/team','themepath:website/team.htm','');

COMMIT ;