/*
 在线拍卖系统（开源教学版）数据库初始化脚本
 MySQL 8.x / 5.7 兼容
 说明：所有用户均为虚构示例数据，身份证、手机号、邮箱均为演示用假数据。
 默认账号（密码均为 BCrypt 哈希存储）：
   管理员  abo / abo
   用户    demo / demo123
   用户    test / test123
*/

/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`gavel` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `gavel`;

/* ------------------------------ 配置文件 ------------------------------ */

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '配置参数名称',
  `value` varchar(100) DEFAULT NULL COMMENT '配置参数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='配置文件';

insert  into `config`(`id`,`name`,`value`) values
(1,'picture1','/upload/picture1.jpg'),
(2,'picture2','/upload/picture2.jpg'),
(3,'picture3','/upload/picture3.jpg'),
(6,'homepage',NULL);

/* ------------------------------ 竞拍订单 ------------------------------ */

DROP TABLE IF EXISTS `jingpaidingdan`;

CREATE TABLE `jingpaidingdan` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `dingdanbianhao` varchar(200) NOT NULL COMMENT '订单编号',
  `shangpinmingcheng` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `shangpinleixing` varchar(200) DEFAULT NULL COMMENT '商品类型',
  `chengjiaojiage` int NOT NULL COMMENT '成交价格',
  `faburiqi` date DEFAULT NULL COMMENT '发布日期',
  `yonghuming` varchar(200) DEFAULT NULL COMMENT '用户名',
  `xingming` varchar(200) DEFAULT NULL COMMENT '姓名',
  `shouji` varchar(200) DEFAULT NULL COMMENT '手机',
  `youxiang` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `dizhi` varchar(200) DEFAULT NULL COMMENT '地址',
  `ispay` varchar(200) DEFAULT '未支付' COMMENT '是否支付',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dingdanbianhao` (`dingdanbianhao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='竞拍订单';

insert  into `jingpaidingdan`(`id`,`addtime`,`dingdanbianhao`,`shangpinmingcheng`,`shangpinleixing`,`chengjiaojiage`,`faburiqi`,`yonghuming`,`xingming`,`shouji`,`youxiang`,`dizhi`,`ispay`) values
(1612340689795,'2024-01-15 10:24:49','1612340681215','示例商品A','数码',2000,'2024-01-10','demo','张三','13800000001','demo@example.com','示例省示例市','已支付');

/* ------------------------------ 历史竞拍 ------------------------------ */

DROP TABLE IF EXISTS `lishijingpai`;

CREATE TABLE `lishijingpai` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `shangpinmingcheng` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `shangpinleixing` varchar(200) DEFAULT NULL COMMENT '商品类型',
  `riqi` datetime DEFAULT NULL COMMENT '日期',
  `jiage` int NOT NULL COMMENT '价格',
  `yonghuming` varchar(200) DEFAULT NULL COMMENT '用户名',
  `xingming` varchar(200) DEFAULT NULL COMMENT '姓名',
  `shouji` varchar(200) DEFAULT NULL COMMENT '手机',
  `dizhi` varchar(200) DEFAULT NULL COMMENT '地址',
  `sfsh` varchar(200) DEFAULT '否' COMMENT '是否审核',
  `shhf` longtext COMMENT '审核回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='历史竞拍';

insert  into `lishijingpai`(`id`,`addtime`,`shangpinmingcheng`,`shangpinleixing`,`riqi`,`jiage`,`yonghuming`,`xingming`,`shouji`,`dizhi`,`sfsh`,`shhf`) values
(1612340577249,'2024-01-12 16:22:56','示例商品B','书籍','2024-01-11 16:00:00',500,'demo','张三','13800000001','示例省示例市','是','有效');

/* ------------------------------ 留言板 ------------------------------ */

DROP TABLE IF EXISTS `messages`;

CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` bigint NOT NULL COMMENT '留言人id',
  `username` varchar(200) DEFAULT NULL COMMENT '用户名',
  `content` longtext NOT NULL COMMENT '留言内容',
  `reply` longtext COMMENT '回复内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言板';

insert  into `messages`(`id`,`addtime`,`userid`,`username`,`content`,`reply`) values
(71,'2024-01-10 16:07:40',1,'demo','请问竞拍成功后多久发货？','您好，竞拍成功后 3 个工作日内发货。');

/* ------------------------------ 竞拍公告 ------------------------------ */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `introduction` longtext COMMENT '简介',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `content` longtext NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='竞拍公告';

insert  into `news`(`id`,`addtime`,`title`,`introduction`,`picture`,`content`) values
(61,'2024-01-10 16:07:40','欢迎使用在线拍卖系统','这是系统演示公告','/upload/news_picture1.jpg','<p>欢迎使用在线拍卖系统，本系统为开源教学项目。</p>'),
(62,'2024-01-11 16:07:40','竞拍须知','参与竞拍前请阅读','/upload/news_picture2.jpg','<p>竞拍出价即为承诺，竞拍成功后请按时完成支付。</p>');

/* ------------------------------ 拍卖商品 ------------------------------ */

DROP TABLE IF EXISTS `paimaishangpin`;

CREATE TABLE `paimaishangpin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `shangpinmingcheng` varchar(200) NOT NULL COMMENT '商品名称',
  `shangpinleixing` varchar(200) NOT NULL COMMENT '商品类型',
  `tupian` varchar(200) DEFAULT NULL COMMENT '图片',
  `jiage` int NOT NULL COMMENT '价格',
  `shangpinxiangqing` longtext COMMENT '商品详情',
  `huodongshijian` varchar(200) DEFAULT NULL COMMENT '活动时间',
  `huodongzhuangtai` varchar(200) DEFAULT NULL COMMENT '活动状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拍卖商品';

insert  into `paimaishangpin`(`id`,`addtime`,`shangpinmingcheng`,`shangpinleixing`,`tupian`,`jiage`,`shangpinxiangqing`,`huodongshijian`,`huodongzhuangtai`) values
(31,'2024-01-10 16:07:40','示例数码相机','数码','/upload/paimaishangpin_tupian1.jpg',2500,'<p>演示商品详情</p>','1-10号','竞拍中'),
(32,'2024-01-10 16:07:40','示例无线耳机','数码','/upload/paimaishangpin_tupian2.jpg',200,'<p>演示商品详情</p>','活动时间','竞拍中'),
(33,'2024-01-10 16:07:40','示例智能手机','手机','/upload/1612340132053.png',2000,'<p>演示商品详情，竞拍起始价 1500。</p>','1-10号','竞拍中'),
(34,'2024-01-10 16:07:40','示例图书','书籍','/upload/1612340185007.jpg',500,'<p>演示商品详情</p>','活动时间','竞拍中');

/* ------------------------------ 商品类型 ------------------------------ */

DROP TABLE IF EXISTS `shangpinleixing`;

CREATE TABLE `shangpinleixing` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `shangpinleixing` varchar(200) NOT NULL COMMENT '商品类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品类型';

insert  into `shangpinleixing`(`id`,`addtime`,`shangpinleixing`) values
(21,'2024-01-10 16:07:40','手机'),
(22,'2024-01-10 16:07:40','数码'),
(23,'2024-01-10 16:07:40','电器'),
(24,'2024-01-10 16:07:40','书籍');

/* ------------------------------ token 表 ------------------------------ */

DROP TABLE IF EXISTS `token`;

CREATE TABLE `token` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` bigint NOT NULL COMMENT '用户id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `tablename` varchar(100) DEFAULT NULL COMMENT '表名',
  `role` varchar(100) DEFAULT NULL COMMENT '角色',
  `token` varchar(200) NOT NULL COMMENT 'token',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='token表';

/* ------------------------------ 管理员 ------------------------------ */
/* abo 的 BCrypt 哈希 */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码（BCrypt 哈希）',
  `role` varchar(100) DEFAULT '管理员' COMMENT '角色',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

insert  into `users`(`id`,`username`,`password`,`role`,`addtime`) values
(1,'abo','$2b$10$ruaE4mRdqQ9uiSwKCWwSf.sffyGOqUchkfLG5lrZFl2NZordCKWFO','管理员','2024-01-10 16:07:40');

/* ------------------------------ 前台用户 ------------------------------ */
/* demo/demo123 与 test/test123 的 BCrypt 哈希；身份证手机号均为虚构 */

DROP TABLE IF EXISTS `yonghu`;

CREATE TABLE `yonghu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yonghuming` varchar(200) NOT NULL COMMENT '用户名',
  `mima` varchar(200) NOT NULL COMMENT '密码（BCrypt 哈希）',
  `xingming` varchar(200) DEFAULT NULL COMMENT '姓名',
  `xingbie` varchar(200) DEFAULT NULL COMMENT '性别',
  `touxiang` varchar(200) DEFAULT NULL COMMENT '头像',
  `shenfenzheng` varchar(200) DEFAULT NULL COMMENT '身份证',
  `shouji` varchar(200) DEFAULT NULL COMMENT '手机',
  `youxiang` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `dizhi` varchar(200) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `yonghuming` (`yonghuming`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

insert  into `yonghu`(`id`,`addtime`,`yonghuming`,`mima`,`xingming`,`xingbie`,`touxiang`,`shenfenzheng`,`shouji`,`youxiang`,`dizhi`) values
(11,'2024-01-10 16:07:40','demo','$2b$10$BrVtC/P8HbGiRVyJuNVit.ZOgAUna4ISwNgvWUIZ8rIfa2J6rHL/G','张三','男','/upload/yonghu_touxiang1.jpg','110101199001010011','13800000001','demo@example.com','示例省示例市'),
(12,'2024-01-10 16:07:40','test','$2b$10$HPPdoXR0os/STFwzmVgqR.84I2v0PKAL/FpaIRe/pfClH5WSFT1He','李四','女','/upload/yonghu_touxiang2.jpg','110101199202020022','13800000002','test@example.com','示例省示例市');

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
