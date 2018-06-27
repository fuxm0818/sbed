DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(128) COMMENT '菜单名称',
  `url` varchar(256) COMMENT '菜单URL',
  `perms` varchar(500) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` tinyint COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(256) COMMENT '菜单图标',
  `order_num` int COMMENT '排序号',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `nickname` varchar(128) COMMENT '别名',
  `password` varchar(128) COMMENT '密码',
  `salt` varchar(32) COMMENT '盐',
  `email` varchar(64) COMMENT '邮箱',
  `mobile` varchar(32) COMMENT '手机号码',
  `status` tinyint COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) COMMENT '部门ID',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX (`username`),
  KEY `email` (`email`),
  KEY `mobile` (`mobile`),
  KEY `status` (`status`),
  KEY `dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `token` varchar(128) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COMMENT '角色名称',
  `remark` varchar(256) COMMENT '备注',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';

DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`key` varchar(128) COMMENT 'key',
	`value` text COMMENT 'value',
	`remark` varchar(256) COMMENT '备注',
	PRIMARY KEY (`id`),
	UNIQUE INDEX (`key`)
) ENGINE=`InnoDB` DEFAULT CHARSET=utf8mb4 COMMENT='系统配置信息表';

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) COMMENT '用户名',
  `operation` varchar(128) COMMENT '用户操作',
  `method` varchar(256) COMMENT '请求方法',
  `params` text COMMENT '请求参数',
  `ip` varchar(64) COMMENT 'IP地址',
  `time` bigint COMMENT '执行时长(毫秒)',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=`InnoDB` DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` text COMMENT '标题',
  `user_id` bigint(20) COMMENT '用户ID',
  `path` varchar(512) COMMENT '路径',
  `mime_type` varchar(128) COMMENT 'mime',
  `suffix` varchar(32) COMMENT '附件的后缀',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `mime_type` (`mime_type`)
) ENGINE=`InnoDB` DEFAULT CHARSET=utf8mb4 COMMENT='附件';

DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) COMMENT '上级部门ID，一级部门为0',
  `name` varchar(128) COMMENT '部门名称',
  `order_num` int COMMENT '排序',
  `del_flag` tinyint DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门管理';

DROP TABLE IF EXISTS `sys_content`;
CREATE TABLE `sys_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) COMMENT '标题',
  `text` longtext COMMENT '内容',
  `slug` varchar(128) COMMENT '别名',
  `thumbnail` varchar(128) COMMENT '缩略图',
  `amount` decimal(10,0) DEFAULT '0' COMMENT '价格',
  `status` tinyint DEFAULT 1 COMMENT '状态   0：草稿   1：正常   2：删除',
  `order_num` int DEFAULT 0 COMMENT '排序号',
  `user_id` bigint(20) COMMENT '用户ID',
  `object_id` bigint(20) COMMENT '关联的对象ID',
  `flag` varchar(128) COMMENT '标识',
  `remark` text COMMENT '备注',
  `meta_keywords` varchar(256) COMMENT 'SEO关键字',
  `meta_description` varchar(512) COMMENT 'SEO描述内容',
  `create_time` datetime COMMENT '创建时间',
  `modify_time` datetime COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `slug` (`slug`),
  KEY `status` (`status`),
  KEY `user_id` (`user_id`),
  KEY `object_id` (`object_id`),
  KEY `flag` (`flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章管理';

DROP TABLE IF EXISTS `sys_taxonomy`;
CREATE TABLE `sys_taxonomy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) COMMENT '名称',
  `remark` varchar(512) COMMENT '备注',
  `slug` varchar(128) COMMENT 'slug',
  `type` tinyint COMMENT '类型 0：分类 1：专题 2：标签',
  `icon` varchar(128) COMMENT '图标',
  `order_num` int DEFAULT 0 COMMENT '排序号',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父级的ID',
  `flag` varchar(128) COMMENT '标识',
  `meta_keywords` varchar(256) COMMENT 'SEO关键字',
  `meta_description` varchar(512) COMMENT 'SEO描述内容',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `slug` (`slug`),
  KEY `type` (`type`),
  KEY `parent_id` (`parent_id`),
  KEY `flag` (`flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类 专题 标签管理';

DROP TABLE IF EXISTS `sys_content_taxonomy`;
CREATE TABLE `sys_content_taxonomy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_id` bigint(20) NOT NULL COMMENT '内容ID',
  `taxonomy_id` bigint(20) NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`),
  KEY `taxonomy_id` (`taxonomy_id`),
  KEY `content_id` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容与分类对应关系';

insert into `sys_user` (`id`, `username`, `nickname`, `password`, `salt`, `email`, `mobile`, `status`, `dept_id`, `create_time`) values ('1', 'admin', NULL, '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', NULL, NUll, '1', NULL, '2016-11-11 11:11:11');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('1','0','系统管理',NULL,NULL,'0','fa fa-cogs','0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('2','1','用户管理','modules/sys/user.html',NULL,'1','fa fa-user','1');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('3','2','查看',NULL,'sys:user:list,sys:user:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('4','2','新增',NULL,'sys:user:save,sys:role:select,sys:dept:select,sys:dept:list','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('5','2','修改',NULL,'sys:user:update,sys:role:select,sys:dept:select,sys:dept:list','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('6','2','删除',NULL,'sys:user:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('7','1','部门管理','modules/sys/dept.html',NULL,'1','fa fa-sitemap','2');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('8','7','查看',NULL,'sys:dept:list,sys:dept:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('9','7','新增',NULL,'sys:dept:save,sys:dept:select,sys:dept:list','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('10','7','修改',NULL,'sys:dept:update,sys:dept:select,sys:dept:list','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('11','7','删除',NULL,'sys:dept:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('12','1','角色管理','modules/sys/role.html',NULL,'1','fa fa-user-secret','3');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('13','12','查看',NULL,'sys:role:list,sys:role:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('14','12','新增',NULL,'sys:role:save,sys:menu:list','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('15','12','修改',NULL,'sys:role:update,sys:menu:list','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('16','12','删除',NULL,'sys:role:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('17','1','菜单管理','modules/sys/menu.html',NULL,'1','fa fa-th-list','4');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('18','17','查看',NULL,'sys:menu:list,sys:menu:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('19','17','新增',NULL,'sys:menu:save,sys:menu:select','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('20','17','修改',NULL,'sys:menu:update,sys:menu:select','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('21','17','删除',NULL,'sys:menu:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('22','1','参数管理','modules/sys/config.html',NULL,'1','fa fa-sun-o','5');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('23','22','查看',NULL,'sys:config:list,sys:config:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('24','22','新增',NULL,'sys:config:save','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('25','22','修改',NULL,'sys:config:update','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('26','22','删除',NULL,'sys:config:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('27','1','附件管理','modules/sys/attachment.html','','1','fa fa-file-image-o','6');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('28','27','查看',NULL,'sys:attachment:list,sys:attachment:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('29','27','删除',NULL,'sys:attachment:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('30','27','上传文件',NULL,'sys:attachment:upload','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('31','27','下载文件',NULL,'sys:attachment:download','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('32','1','系统日志','modules/sys/log.html','sys:log:list','1','fa fa-pencil','7');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('33','1','SQL监控','druid/sql.html',NULL,'1','fa fa-bug','8');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('34','0','内容管理',NULL,NULL,'0','fa fa-files-o','1');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('35','34','分类管理','modules/sys/taxonomy.html?type=0',NULL,'1','fa fa-bars','1');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('36','35','查看',NULL,'sys:taxonomy:list,sys:taxonomy:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('37','35','新增',NULL,'sys:taxonomy:save,sys:taxonomy:select','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('38','35','修改',NULL,'sys:taxonomy:update,sys:taxonomy:select','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('39','35','删除',NULL,'sys:taxonomy:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('40','34','专题管理','modules/sys/taxonomy.html?type=1',NULL,'1','fa fa-list-alt','2');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('41','40','查看',NULL,'sys:taxonomy:list,sys:taxonomy:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('42','40','新增',NULL,'sys:taxonomy:save,sys:taxonomy:select','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('43','40','修改',NULL,'sys:taxonomy:update,sys:taxonomy:select','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('44','40','删除',NULL,'sys:taxonomy:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('45','34','标签管理','modules/sys/taxonomy.html?type=2','','1','fa fa-tags','3');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('46','45','查看',NULL,'sys:taxonomy:list,sys:taxonomy:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('47','45','新增',NULL,'sys:taxonomy:save','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('48','45','修改',NULL,'sys:taxonomy:update','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('49','45','删除',NULL,'sys:taxonomy:delete','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('50','0','代码生成器',NULL,NULL,'0','fa fa-support','2');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('51','50','代码生成','modules/sys/generator.html',NULL,'1','fa fa-rocket','1');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('52','51','查看',NULL,'sys:generator:list','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('53','51','生成代码',NULL,'sys:generator:code','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('54','34','文章管理','modules/sys/content.html',NULL,'1','fa fa-pencil-square-o','0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('55','54','查看',NULL,'sys:content:list,sys:content:info','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('56','54','新增',NULL,'sys:content:save,sys:attachment:list,sys:taxonomy:selectByType','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('57','54','修改',NULL,'sys:content:update,sys:attachment:list,sys:taxonomy:selectByType','2',NULL,'0');
insert into `sys_menu` (`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) values('58','54','删除',NULL,'sys:content:delete','2',NULL,'0');
