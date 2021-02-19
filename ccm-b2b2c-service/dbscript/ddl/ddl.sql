-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: rm-wz967plgn5l2nmxlo2o.mysql.rds.aliyuncs.com    Database: b2b2cdb
-- ------------------------------------------------------
-- Server version	5.6.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ad`
--

DROP TABLE IF EXISTS `ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad` (
  `AD_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `URL` varchar(300) DEFAULT NULL COMMENT '广告图片地址',
  `CONTENT` longtext COMMENT '广告图点击后的内容',
  `LINK_TYPE` varchar(10) DEFAULT NULL COMMENT '广告图点击后跳转的类型(文章, 商品)',
  `PRODUCTCATE_UUID` varchar(36) DEFAULT NULL COMMENT '广告图关联的商品品类',
  `LOCATION` varchar(45) DEFAULT NULL COMMENT '广告图位置:首页和商品分类页',
  `ISACTIVE` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`AD_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='广告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `ADMIN_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0' COMMENT '是否删除',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `CREATE_BY` varchar(50) NOT NULL COMMENT '记录创建人员',
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次更新时间',
  `UPDATE_BY` varchar(50) NOT NULL COMMENT '最近一次更新人员',
  `EMAIL` varchar(40) DEFAULT NULL COMMENT '电子邮箱地址',
  `ID` varchar(30) NOT NULL COMMENT '用户ID',
  `ISACTIVE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码(MD5)',
  `LASTLOGINTIME` timestamp NULL DEFAULT NULL COMMENT '最近一次登录时间',
  `FAILCOUNT` int(11) DEFAULT '0' COMMENT '登录失败次数',
  `ISLOCKED` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  `SUCCESSCOUNT` int(11) DEFAULT NULL COMMENT '总登录次数',
  `MOBILENO` varchar(20) DEFAULT NULL COMMENT '手机号码',
  PRIMARY KEY (`ADMIN_UUID`),
  KEY `ADMIN_INDEX_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台管理人员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin_role`
--

DROP TABLE IF EXISTS `admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role` (
  `ADMINROLE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `ADMIN_UUID` varchar(36) NOT NULL COMMENT '后台管理人员ID',
  `ROLE_UUID` varchar(36) NOT NULL COMMENT '角色ID',
  `IS_DELETED` varchar(1) DEFAULT '0' COMMENT '是否删除',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `CREATE_BY` varchar(50) NOT NULL COMMENT '记录创建人员',
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后一次更新时间',
  `UPDATE_BY` varchar(50) NOT NULL COMMENT '记录最后一次更新人员',
  PRIMARY KEY (`ADMINROLE_UUID`),
  KEY `ADMINROLE_INDEX_1` (`ADMIN_UUID`),
  KEY `ADMINROLE_INDEX_2` (`ROLE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台管理人员和角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin_token`
--

DROP TABLE IF EXISTS `admin_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_token` (
  `ADMINTOKEN_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0' COMMENT '是否删除',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `CREATE_BY` varchar(50) NOT NULL COMMENT '记录创建人员',
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最近更新时间',
  `UPDATE_BY` varchar(50) NOT NULL COMMENT '记录最近更新人员',
  `ADMIN_UUID` varchar(36) NOT NULL COMMENT '后台管理人员ID',
  `TOKENID` varchar(36) NOT NULL COMMENT '登录token',
  `LOGINTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  `EXPIRETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'token过期时间',
  PRIMARY KEY (`ADMINTOKEN_UUID`),
  KEY `ADMINTOKEN_FOREIGN_1_idx` (`ADMIN_UUID`),
  KEY `ADMINTOKEN_INDEX_1` (`TOKENID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台管理人员登录token';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `APPLICATION_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `APPLICATION_NAME` varchar(60) NOT NULL COMMENT '功能名称',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `APPLICATION_CODE` varchar(20) NOT NULL COMMENT '功能编码',
  `APPLICATION_DESC` varchar(200) DEFAULT NULL COMMENT '功能描述',
  `ROOTAPPLICATION` varchar(45) DEFAULT NULL COMMENT '暂不使用',
  PRIMARY KEY (`APPLICATION_UUID`),
  UNIQUE KEY `APPLICATION_UNIQUE_1` (`APPLICATION_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='一级功能(用于权限控制)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `AREA_ID` varchar(32) NOT NULL COMMENT '主键ID',
  `PARENT_ID` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `DEPTH` int(11) DEFAULT NULL COMMENT '地区深度',
  `NAME` varchar(32) DEFAULT NULL COMMENT '地区名称',
  `POSTAL_CODE` varchar(45) DEFAULT NULL COMMENT '邮政编码',
  `SORT` int(11) DEFAULT '0' COMMENT '地区排序',
  `FIRST_CHARACTER` varchar(1) DEFAULT NULL,
  `ABBR_CHARACTER` varchar(45) DEFAULT NULL,
  `FULL_CHARACTER` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`AREA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地区表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `ARTICLE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TAG_UUID` varchar(36) DEFAULT NULL COMMENT '标签ID',
  `CONTENT` longtext COMMENT '内容',
  `TITLE` varchar(200) DEFAULT NULL,
  `COVERIMAGE_URL` varchar(500) DEFAULT NULL COMMENT '主图',
  `MEDIA_ID` varchar(200) DEFAULT NULL COMMENT '微信公众号文章media_id',
  `IS_FROM_OFFICIALACCOUNT` tinyint(1) DEFAULT NULL COMMENT '是否引用公众号文章',
  `LINK_TYPE` varchar(50) DEFAULT NULL COMMENT '链接类型',
  `PUBLISH_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  `DIGEST` varchar(200) DEFAULT NULL COMMENT '摘要',
  `NUMBER_OF_READING` int(11) DEFAULT '0' COMMENT '浏览次数',
  `ISPUBLISHED` tinyint(1) DEFAULT NULL COMMENT '是否发布',
  `ARTICLE_TYPE` varchar(10) DEFAULT NULL COMMENT '内容类型(1-文案,2-音频,3-视频,4-官方资讯,5-活动通知)',
  PRIMARY KEY (`ARTICLE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容(文章,音频,视频)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `COUPON_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NAME` varchar(45) DEFAULT NULL COMMENT '优惠券名称',
  `TYPE` varchar(45) DEFAULT NULL COMMENT '优惠现金或折扣(DISCOUNT, CASH)',
  `VALID_START_DATE` date DEFAULT NULL COMMENT '有效期开始日期',
  `VALID_END_DATE` date DEFAULT NULL COMMENT '有效期结束日期',
  `TOTALCOUNT` int(11) DEFAULT NULL COMMENT '发放数量',
  `AVAILABLECOUNT` int(11) DEFAULT NULL COMMENT '可用数量',
  `LIMITPERUSER` int(11) DEFAULT NULL COMMENT '每人限领数量',
  `COUPONCODE` varchar(45) DEFAULT NULL COMMENT '优惠券编号',
  `BENEFIT_CASH` decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
  `BENEFIT_DISCOUNT` decimal(10,2) DEFAULT NULL COMMENT '优惠折扣',
  `CONDITION_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '满多少可使用',
  `ISACTIVE` tinyint(1) DEFAULT NULL COMMENT '是否发放',
  `MERCHANT_UUID` varchar(45) DEFAULT NULL COMMENT '发放商家(B2C商城暂时未空)',
  `VALID_TYPE` varchar(45) DEFAULT NULL COMMENT '1-指定有效期, 2-领取后N天有效',
  `VALID_DAYS` int(11) DEFAULT NULL COMMENT '领取后N天有效',
  `BENEFIT_TYPE` tinyint(1) DEFAULT NULL COMMENT '0-所有商品可用,1-指定商品可用',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '描述',
  `IMAGE_URL` varchar(500) DEFAULT NULL COMMENT '优惠券图片',
  PRIMARY KEY (`COUPON_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon_order`
--

DROP TABLE IF EXISTS `coupon_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon_order` (
  `COUPONORDER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) NOT NULL COMMENT '用户ID',
  `COUPON_UUID` varchar(36) NOT NULL COMMENT '优惠券ID',
  `COUPON_ORDER_TIME` timestamp NULL DEFAULT NULL COMMENT '优惠券购买时间',
  `TYPE` varchar(45) DEFAULT NULL COMMENT '优惠券类型(满减, 折扣)',
  `VALID_START_DATE` timestamp NULL DEFAULT NULL COMMENT '有效开始时间',
  `VALID_END_DATE` timestamp NULL DEFAULT NULL COMMENT '有效结束时间',
  `COUPON_DESC` varchar(500) DEFAULT NULL COMMENT '优惠券描述',
  `COUPON_UNIT` int(11) DEFAULT NULL COMMENT '优惠券数量',
  `COUPON_POINT` int(11) DEFAULT NULL COMMENT '优惠券所需积分',
  `ACTUAL_POINT` int(11) DEFAULT NULL COMMENT '优惠券实际支付积分',
  `COUPON_IMAGE_URL` varchar(500) DEFAULT NULL COMMENT '主图地址',
  `COUPON_NAME` varchar(45) DEFAULT NULL COMMENT '优惠券名称',
  PRIMARY KEY (`COUPONORDER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券与订单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon_product`
--

DROP TABLE IF EXISTS `coupon_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon_product` (
  `COUPONPRODUCT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) NOT NULL COMMENT '商品ID',
  `COUPON_UUID` varchar(36) NOT NULL COMMENT '优惠券ID',
  PRIMARY KEY (`COUPONPRODUCT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券可使用商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon_writeoff`
--

DROP TABLE IF EXISTS `coupon_writeoff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon_writeoff` (
  `COUPONWRITEOFF_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USERCOUPON_UUID` varchar(36) NOT NULL COMMENT '用户优惠券关系ID',
  `MERCHANT_UUID` varchar(36) NOT NULL COMMENT '商家ID',
  `COUPON_WRITE_OFF_TIME` timestamp NULL DEFAULT NULL COMMENT '核销时间',
  PRIMARY KEY (`COUPONWRITEOFF_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券核销记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `FILE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `FILE_NAME` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `SIZE` int(11) DEFAULT NULL COMMENT '文件大小',
  `OSS_KEY` varchar(200) DEFAULT NULL COMMENT '暂不使用',
  `URL` varchar(500) NOT NULL COMMENT '文件地址',
  PRIMARY KEY (`FILE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件存储表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `function`
--

DROP TABLE IF EXISTS `function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `function` (
  `FUNCTION_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `FUNCTION_NAME` varchar(60) NOT NULL COMMENT '功能名称',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `APPLICATION_UUID` varchar(36) NOT NULL COMMENT '关联的一级功能ID',
  `FUNCTION_CODE` varchar(20) NOT NULL COMMENT '功能代码',
  `FUNCTION_DESC` varchar(200) DEFAULT NULL COMMENT '功能描述',
  PRIMARY KEY (`FUNCTION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='二级功能(用于权限控制)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `global_setting`
--

DROP TABLE IF EXISTS `global_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `global_setting` (
  `GLOBAL_SETTING_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `APPLICATION_LOGO` varchar(500) DEFAULT NULL COMMENT '应用Logo',
  `APPLICATION_NAME` varchar(100) DEFAULT NULL COMMENT '应用名称',
  `APPLICATION_DESC` varchar(400) DEFAULT NULL COMMENT '应用描述',
  `APPLICATION_DOMAIN_NAME` varchar(100) DEFAULT NULL,
  `APPLICATION_VERSION` varchar(45) DEFAULT NULL COMMENT '应用版本号',
  `APPLICATION_PUBLIC_REGISTER_ENABLED` tinyint(1) DEFAULT '1' COMMENT '是否开启公开注册(不需要邀请)',
  `APPLICATION_PRODUCT_ENABLED` tinyint(1) DEFAULT NULL COMMENT '商家上架商品是否需要审核',
  `APPLICATION_MERCHANT_ENABLED` tinyint(1) DEFAULT NULL,
  `APPLICATION_CITY_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否允许城市切换(如允许用户可以切换城市获取商家和商品)',
  `APPLICATION_DELIVERY_EXPRESS_ENABLED` tinyint(1) DEFAULT '1' COMMENT '是否开启发货方式-快递配送',
  `APPLICATION_DELIVERY_CITY_ENABLED` tinyint(1) DEFAULT '0' COMMENT '是否开启发货方式-同城配送',
  `APPLICATION_DELIVERY_PICK_ENABLED` tinyint(1) DEFAULT '0' COMMENT '是否开启发货方式-到店自提',
  `WECHAT_MERCHANT_ID` varchar(100) DEFAULT NULL COMMENT '微信商户号',
  `WECHAT_APP_ID_OFFICIAL_ACCOUNT` varchar(100) DEFAULT NULL COMMENT '微信公众号APPID',
  `WECHAT_APP_SECRET_OFFICIAL_ACCOUNT` varchar(100) DEFAULT NULL COMMENT '微信公众号APPSECRET',
  `WECHAT_APP_ID_MINI_PROGRAM` varchar(100) DEFAULT NULL COMMENT '微信小程序APPID',
  `WECHAT_APP_SECRET_MINI_PROGRAM` varchar(100) DEFAULT NULL COMMENT '微信小程序APPSECRET',
  `WECHAT_APP_ID_OPEN` varchar(100) DEFAULT NULL COMMENT '微信开放平台APPID',
  `WECHAT_APP_SECRET_OPEN` varchar(100) DEFAULT NULL COMMENT '微信开放平台APPSECRET',
  `WECHAT_API_KEY` varchar(100) DEFAULT NULL COMMENT '微信API秘钥',
  `WECHAT_NOTIFY_URL` varchar(200) DEFAULT NULL COMMENT '微信支付回调地址',
  `WECHAT_REFUND_NOTIFY_URL` varchar(200) DEFAULT NULL COMMENT '微信退款回调地址',
  `ALIPAY_APP_ID` varchar(100) DEFAULT NULL COMMENT '支付宝APPID',
  `ALIPAY_NOTIFY_URL` varchar(200) DEFAULT NULL COMMENT '支付宝支付回调地址',
  `ALIPAY_REFUND_NOTIFY_URL` varchar(200) DEFAULT NULL COMMENT '支付宝退款回调地址',
  `ALIPAY_RETURN_URL` varchar(200) DEFAULT NULL COMMENT '支付宝支付页面回调地址',
  `ALIPAY_PRIVATE_KEY` varchar(2000) DEFAULT NULL COMMENT '支付宝支付应用端私钥',
  `ALIPAY_PUBLIC_KEY` varchar(2000) DEFAULT NULL COMMENT '支付宝支付支付宝公钥',
  `OSS_ACCESS_ID` varchar(45) DEFAULT NULL COMMENT '阿里云OSS存储ACCESS ID',
  `OSS_ACCESS_KEY` varchar(45) DEFAULT NULL COMMENT '阿里云OSS存储ACCESS KEY',
  `OSS_END_POINT` varchar(100) DEFAULT NULL COMMENT '阿里云OSS存储END POINT',
  `OSS_BUCKET_NAME` varchar(100) DEFAULT NULL COMMENT '阿里云OSS存储BUCKET NAME',
  `SMS_ACCESSKEY_ID` varchar(45) DEFAULT NULL COMMENT '阿里云短信验证码Accesskey Id',
  `SMS_ACCESSKEY_SECRET` varchar(45) DEFAULT NULL COMMENT '阿里云短信验证码Accesskey Secret',
  `SMS_SIGN_NAME` varchar(45) DEFAULT NULL COMMENT '阿里云短信验证码签名',
  `SMS_TEMPLATE_CODE` varchar(45) DEFAULT NULL COMMENT '阿里云短信验证码模板ID',
  `SMS_TEMPLATE_CODE_APPLICATION_APPROVED` varchar(45) DEFAULT NULL COMMENT '商家入驻申请审核通过短信模板',
  `SMS_TEMPLATE_CODE_APPLICATION_REJECTED` varchar(45) DEFAULT NULL COMMENT '商家入驻申请审核未通过短信模板',
  `AMAP_WEB_JSAPI_KEY` varchar(45) DEFAULT NULL COMMENT '高德地图Web端JS API key',
  `AMAP_WEB_SERVICE_KEY` varchar(45) DEFAULT NULL COMMENT '高德地图Web服务key',
  `CREATE_BY` varchar(50) DEFAULT NULL,
  `DATE_UPDATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) DEFAULT NULL,
  `DATE_CREATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IS_DELETED` varchar(1) DEFAULT '0',
  PRIMARY KEY (`GLOBAL_SETTING_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用全局设置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `GROUP_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `GROUP_NAME` varchar(20) NOT NULL COMMENT '分组名称',
  `GROUP_DESCRIPTION` varchar(100) DEFAULT NULL COMMENT '分组描述',
  `IS_DISPLAYED_HOME` tinyint(1) DEFAULT '0' COMMENT '是否首页显示',
  `SORT_NUMBER` int(11) DEFAULT NULL COMMENT '排序',
  `BACKGROUND_URL` varchar(500) DEFAULT NULL COMMENT '显示在页面时的背景图',
  `ICON_URL` varchar(500) DEFAULT NULL COMMENT '显示在页面时的icon图标',
  `IS_REGISTER` tinyint(1) DEFAULT NULL COMMENT '是否会员注册专用',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`GROUP_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品组';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_buy`
--

DROP TABLE IF EXISTS `group_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_buy` (
  `GROUP_BUY_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `GROUP_BUY_PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '团购活动ID',
  `OWNER_UUID` varchar(36) DEFAULT NULL COMMENT '团购商品库存',
  `START_TIME` timestamp NULL DEFAULT NULL COMMENT '团购发起时间',
  `END_TIME` timestamp NULL DEFAULT NULL COMMENT '团购结束时间',
  `STATUS` varchar(10) DEFAULT NULL COMMENT '拼团状态',
  PRIMARY KEY (`GROUP_BUY_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团购表(用户发起拼团)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_buy_def`
--

DROP TABLE IF EXISTS `group_buy_def`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_buy_def` (
  `GROUP_BUY_DEF_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL COMMENT '活动 名称',
  `START_TIME` timestamp NULL DEFAULT NULL COMMENT '活动开始时间',
  `END_TIME` timestamp NULL DEFAULT NULL COMMENT '活动结束时间',
  `STATUS` varchar(36) DEFAULT NULL COMMENT '团购商品状态(0-团购商品已下线, 1-团购商品已上线)',
  `MIN_USER_COUNT` int(11) DEFAULT NULL COMMENT '最少成团人数',
  `MAX_TRAN_DAYS` int(11) DEFAULT NULL COMMENT '成团有效期(天)',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家',
  PRIMARY KEY (`GROUP_BUY_DEF_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团购活动表(引用团购商品基本表)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_buy_product`
--

DROP TABLE IF EXISTS `group_buy_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_buy_product` (
  `GROUP_BUY_PRODUCT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `GROUP_BUY_DEF_UUID` varchar(36) DEFAULT NULL COMMENT '团购活动表',
  `PRODUCT_UUID` varchar(46) DEFAULT NULL COMMENT '商品ID',
  `UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '团购价格',
  `STOCK` int(11) DEFAULT NULL COMMENT '团购商品库存',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家',
  `SOLD_UNIT` int(11) DEFAULT '0',
  PRIMARY KEY (`GROUP_BUY_PRODUCT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团购商品表(引用商品基本表)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_buy_user`
--

DROP TABLE IF EXISTS `group_buy_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_buy_user` (
  `GROUP_BUY_USER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `GROUP_BUY_UUID` varchar(36) DEFAULT NULL COMMENT '团购活动ID',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '参与拼团人员',
  `ORDER_UUID` varchar(36) DEFAULT NULL COMMENT '拼团对应的订单ID',
  `JOIN_TIME` timestamp NULL DEFAULT NULL COMMENT '参与拼团的时间',
  `IS_OWNER` tinyint(1) DEFAULT NULL COMMENT '是否团购发起人',
  PRIMARY KEY (`GROUP_BUY_USER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团购表(用户发起拼团)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logistics`
--

DROP TABLE IF EXISTS `logistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logistics` (
  `LOGISTICS_UUID` varchar(36) NOT NULL,
  `NAME` varchar(20) NOT NULL COMMENT '物流公司名称',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  PRIMARY KEY (`LOGISTICS_UUID`),
  KEY `LOGISTICS_INDEX_1` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流公司';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `materia` (
  `MATERIA_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TYPE` varchar(36) DEFAULT NULL,
  `URL` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MATERIA_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文案相关表(暂不使用)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant`
--

DROP TABLE IF EXISTS `merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant` (
  `MERCHANT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MOBILENO` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `MERCHANTNAME` varchar(100) DEFAULT NULL COMMENT '商家名称',
  `MERCHANTADDRESS` varchar(200) DEFAULT NULL COMMENT '商家地址',
  `LOGO` varchar(500) DEFAULT NULL COMMENT '商家logo',
  `CONTACTNAME` varchar(20) DEFAULT NULL COMMENT '联系人',
  `MERCHANTDESCRIPTION` varchar(200) DEFAULT NULL COMMENT '商家描述',
  `ISCLOSED` tinyint(1) DEFAULT '0' COMMENT '是否关闭',
  `REFERRER` varchar(45) DEFAULT NULL COMMENT '推荐人',
  `REGISTER_DATE` timestamp NULL DEFAULT NULL COMMENT '入驻时间',
  `MEMO` varchar(100) DEFAULT NULL COMMENT '备注',
  `EXCHANGE_RATE` int(11) DEFAULT NULL COMMENT '积分赠送比例(不含%)',
  `PROVINCE` varchar(45) DEFAULT NULL COMMENT '商家地址(省)',
  `CITY` varchar(45) DEFAULT NULL COMMENT '商家地址(市)',
  `DISTRICT` varchar(45) DEFAULT NULL COMMENT '商家地址(区)',
  `LATITUDE` decimal(20,10) DEFAULT NULL COMMENT '商家地址(纬度)',
  `LONGITUDE` decimal(20,10) DEFAULT NULL COMMENT '商家地址(经度)',
  `MERCHANT_ID` varchar(45) DEFAULT NULL COMMENT '商户号',
  `WEIGHT` decimal(12,2) DEFAULT '0.00' COMMENT '商家权重(用于搜索商家排序)',
  `SCORE` decimal(12,1) DEFAULT NULL COMMENT '商家评分',
  `SOLD_UNIT` int(11) DEFAULT '0' COMMENT '商家已销售商品数量',
  `SALE_UNIT` int(11) DEFAULT '0' COMMENT '商家上架商品数量',
  PRIMARY KEY (`MERCHANT_UUID`),
  UNIQUE KEY `MOBILENO_UNIQUE` (`MOBILENO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_account`
--

DROP TABLE IF EXISTS `merchant_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_account` (
  `MERCHANTACCOUNT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `ACCOUNT_TYPE` varchar(10) DEFAULT NULL COMMENT '账户类型(微信,支付宝,银行)',
  `BANKNAME` varchar(45) DEFAULT NULL COMMENT '银行名称',
  `BANKACCOUNTNO` varchar(20) DEFAULT NULL COMMENT '银行账号',
  `BANKACCOUNTNAME` varchar(45) DEFAULT NULL COMMENT '银行账户名',
  `ALIPAY_ID` varchar(80) DEFAULT NULL COMMENT '支付宝ID',
  `WECHAT_ID` varchar(80) DEFAULT NULL COMMENT '微信ID',
  `BANKCODE` varchar(20) DEFAULT NULL COMMENT '银行支行代码',
  `ALIPAY_QRCODE` varchar(500) DEFAULT NULL COMMENT '支付宝收款地址',
  `WECHAT_QRCODE` varchar(500) DEFAULT NULL COMMENT '微信收款地址',
  PRIMARY KEY (`MERCHANTACCOUNT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_application`
--

DROP TABLE IF EXISTS `merchant_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_application` (
  `MERCHANT_APPLICATION_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MOBILENO` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `MERCHANTNAME` varchar(100) DEFAULT NULL COMMENT '商家名称',
  `MERCHANTADDRESS` varchar(200) DEFAULT NULL COMMENT '商家地址',
  `CONTACTNAME` varchar(20) DEFAULT NULL COMMENT '联系人',
  `MERCHANTDESCRIPTION` varchar(200) DEFAULT NULL COMMENT '商家描述',
  `REFERRER` varchar(45) DEFAULT NULL COMMENT '推荐人',
  `APPLICATION_TIME` timestamp NULL DEFAULT NULL COMMENT '申请入驻时间',
  `VERIFY_TIME` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `MEMO` varchar(100) DEFAULT NULL COMMENT '备注',
  `PROVINCE` varchar(45) DEFAULT NULL COMMENT '商家地址(省)',
  `CITY` varchar(45) DEFAULT NULL COMMENT '商家地址(市)',
  `DISTRICT` varchar(45) DEFAULT NULL COMMENT '商家地址(区)',
  `LATITUDE` decimal(20,10) DEFAULT NULL COMMENT '商家地址(纬度)',
  `LONGITUDE` decimal(20,10) DEFAULT NULL COMMENT '商家地址(经度)',
  `APPLICATION_ID` varchar(45) DEFAULT NULL COMMENT '申请编号',
  `APPLICATION_STATUS` varchar(5) DEFAULT NULL COMMENT '申请状态',
  PRIMARY KEY (`MERCHANT_APPLICATION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家入驻申请';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_assign`
--

DROP TABLE IF EXISTS `merchant_assign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_assign` (
  `MERCHANTASSIGN_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `TRAN_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '会员消费金额',
  `ASSIGN_POINT` int(11) DEFAULT NULL COMMENT '划拨积分',
  `ASSIGN_TIME` timestamp NULL DEFAULT NULL COMMENT '划拨时间',
  `ASSIGN_NO` varchar(45) DEFAULT NULL COMMENT '划拨交易号',
  `MERCHANT_POINT_BEFORE` int(11) DEFAULT NULL COMMENT '商家划拨前积分',
  `MERCHANT_POINT_AFTER` int(11) DEFAULT NULL COMMENT '商家划拨后积分',
  `USER_POINT_BEFORE` int(11) DEFAULT NULL COMMENT '会员划拨前积分',
  `USER_POINT_AFTER` int(11) DEFAULT NULL COMMENT '会员划拨后积分',
  PRIMARY KEY (`MERCHANTASSIGN_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家积分划拨表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_auth`
--

DROP TABLE IF EXISTS `merchant_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_auth` (
  `MERCHANTAUTH_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `MERCHANT_UUID` varchar(36) NOT NULL COMMENT '商家ID',
  `PASSWORD` varchar(100) NOT NULL COMMENT '登录密码',
  `TRAN_PASSWORD` varchar(200) DEFAULT NULL COMMENT '交易密码',
  `LASTLOGINTIME` timestamp NULL DEFAULT NULL COMMENT '最近登录时间',
  `FAILCOUNT` int(11) DEFAULT '0' COMMENT '登录失败次数',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  PRIMARY KEY (`MERCHANTAUTH_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家登录验证表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_balance`
--

DROP TABLE IF EXISTS `merchant_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_balance` (
  `MERCHANTBALANCE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `AVAILABLE_BALANCE` decimal(12,2) DEFAULT NULL COMMENT '可提现余额',
  `TOTAL_BALANCE` decimal(12,2) DEFAULT NULL COMMENT '总余额, 可能有部分余额因为订单未结束而不能提现',
  `AVAILABLE_POINT` int(11) DEFAULT NULL COMMENT '可用积分',
  `TOTAL_POINT` int(11) DEFAULT NULL COMMENT '总积分, 某些积分可能因为订单状态暂不可用',
  PRIMARY KEY (`MERCHANTBALANCE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家余额';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_charge`
--

DROP TABLE IF EXISTS `merchant_charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_charge` (
  `MERCHANTCHARGE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `CHARGE_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '充值金额',
  `CHARGE_POINT` int(11) DEFAULT NULL COMMENT '充值获取积分',
  `CHARGE_STATUS` varchar(10) DEFAULT NULL COMMENT '充值状态',
  `FAIL_REASON` varchar(100) DEFAULT NULL COMMENT '充值失败原因',
  `PAYMENT_METHOD` varchar(10) DEFAULT NULL COMMENT '支付方式(微信,支付宝)',
  `BANKNAME` varchar(45) DEFAULT NULL COMMENT '银行名称',
  `BANKACCOUNTNO` varchar(20) DEFAULT NULL COMMENT '银行账号',
  `BANKACCOUNTNAME` varchar(45) DEFAULT NULL COMMENT '银行账户名',
  `ALIPAY_ID` varchar(80) DEFAULT NULL COMMENT '支付宝ID',
  `WECHAT_ID` varchar(80) DEFAULT NULL COMMENT '微信ID',
  `CHARGE_TIME` timestamp NULL DEFAULT NULL COMMENT '充值时间',
  `CHARGE_NO` varchar(16) DEFAULT NULL COMMENT '充值交易编号',
  `OUT_TRADE_NO` varchar(45) DEFAULT NULL COMMENT '微信或支付宝交易号',
  `POINT_BEFORE` int(11) DEFAULT NULL COMMENT '充值前积分',
  `POINT_AFTER` int(11) DEFAULT NULL COMMENT '充值后积分',
  PRIMARY KEY (`MERCHANTCHARGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家充值记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_coupon`
--

DROP TABLE IF EXISTS `merchant_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_coupon` (
  `MERCHANTCOUPON_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT NULL,
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) NOT NULL COMMENT '商家ID',
  `COUPON_UUID` varchar(36) NOT NULL COMMENT '优惠券ID',
  PRIMARY KEY (`MERCHANTCOUPON_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家优惠券关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_follow`
--

DROP TABLE IF EXISTS `merchant_follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_follow` (
  `MERCHANTFOLLOW_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  PRIMARY KEY (`MERCHANTFOLLOW_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家关注表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_shopper`
--

DROP TABLE IF EXISTS `merchant_shopper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_shopper` (
  `MERCHANTSHOPPER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `NAME` varchar(36) DEFAULT NULL COMMENT '姓名',
  `SEX` varchar(5) DEFAULT NULL COMMENT '性别',
  `MOBILENO` varchar(36) DEFAULT NULL COMMENT '手机号码',
  `PHOTO` varchar(500) DEFAULT NULL COMMENT '头像',
  `IS_ENABLED` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`MERCHANTSHOPPER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家配送员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_statement`
--

DROP TABLE IF EXISTS `merchant_statement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_statement` (
  `MERCHANTSTATEMENT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `TRANSACTION_TYPE` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `TRANSACTION_TIME` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `TRANSACTION_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '交易金额',
  `TRANSACTION_CODE` varchar(20) DEFAULT NULL COMMENT '交易编号',
  `TRANSACTION_POINT` int(11) DEFAULT NULL COMMENT '交易积分',
  `TRANSACTION_DESC` varchar(500) DEFAULT NULL COMMENT '交易描述',
  `BALANCEBEFORE` decimal(12,2) DEFAULT NULL COMMENT '交易前资金余额',
  `BALANCEAFTER` decimal(12,2) DEFAULT NULL COMMENT '交易后资金余额',
  `POINTBEFORE` int(11) DEFAULT NULL COMMENT '交易前积分余额',
  `POINTAFTER` int(11) DEFAULT NULL COMMENT '交易后积分余额',
  PRIMARY KEY (`MERCHANTSTATEMENT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分明细(含充值和划拨)表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_token`
--

DROP TABLE IF EXISTS `merchant_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_token` (
  `MERCHANTTOKEN_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) NOT NULL COMMENT '商家ID',
  `TOKENID` varchar(36) NOT NULL COMMENT '登录token',
  `LOGINTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`MERCHANTTOKEN_UUID`),
  KEY `ADMINTOKEN_FOREIGN_1_idx` (`MERCHANT_UUID`),
  KEY `ADMINTOKEN_INDEX_1` (`TOKENID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家登录Token表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_user`
--

DROP TABLE IF EXISTS `merchant_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_user` (
  `MERCHANTUSER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  PRIMARY KEY (`MERCHANTUSER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家会员关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_withdraw`
--

DROP TABLE IF EXISTS `merchant_withdraw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_withdraw` (
  `MERCHANTWITHDRAW_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `WITHDRAW_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '提现金额',
  `WITHDRAW_STATUS` varchar(10) DEFAULT NULL COMMENT '提现状态',
  `FAIL_REASON` varchar(100) DEFAULT NULL COMMENT '提现失败原因',
  `PAYMENT_METHOD` varchar(10) DEFAULT NULL COMMENT '提现支付方式',
  `BANKNAME` varchar(45) DEFAULT NULL COMMENT '银行名称',
  `BANKACCOUNTNO` varchar(20) DEFAULT NULL COMMENT '银行账号',
  `BANKACCOUNTNAME` varchar(45) DEFAULT NULL COMMENT '银行账户名',
  `ALIPAY_ID` varchar(80) DEFAULT NULL COMMENT '支付宝ID',
  `WECHAT_ID` varchar(80) DEFAULT NULL COMMENT '微信ID',
  `WITHDRAW_TIME` timestamp NULL DEFAULT NULL COMMENT '提现时间',
  `VERIFY_TIME` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `TRANSFER_TIME` timestamp NULL DEFAULT NULL COMMENT '打款时间',
  `WITHDRAW_NO` varchar(16) DEFAULT NULL COMMENT '提现交易编号',
  `REJECT_REASON` varchar(100) DEFAULT NULL COMMENT '审核拒绝理由',
  `COMPLETE_TIME` timestamp NULL DEFAULT NULL COMMENT '提现成功时间',
  `PAYMENT_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '提现金额',
  `TAX_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '手续费',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`MERCHANTWITHDRAW_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家提现表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `MESSAGE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `FROM_USER_UUID` varchar(36) DEFAULT NULL COMMENT '发送会员ID',
  `TO_USER_UUID` varchar(36) NOT NULL COMMENT '接收会员ID',
  `CONTENT` varchar(500) DEFAULT NULL COMMENT '内容',
  `SEND_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`MESSAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员站内信表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note` (
  `NOTE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `CONTENT` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `STATUS` varchar(15) NOT NULL COMMENT '消息状态(已读,未读)',
  `SEND_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '消息发送时间',
  `ACTION` varchar(45) DEFAULT NULL COMMENT '点击消息触发事件',
  `APPLICATION` varchar(60) NOT NULL COMMENT '消息类型',
  PRIMARY KEY (`NOTE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `note_channel`
--

DROP TABLE IF EXISTS `note_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note_channel` (
  `NOTECHANNEL_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ISSMSENABLED` tinyint(1) DEFAULT NULL COMMENT '是否发送短信消息',
  `NOTETYPE` varchar(45) NOT NULL COMMENT '消息类型',
  `ISEMAILENABLED` tinyint(1) DEFAULT NULL COMMENT '是否发送邮件消息',
  `ISWECHATENABLED` tinyint(1) DEFAULT NULL COMMENT '是否发送微信消息',
  PRIMARY KEY (`NOTECHANNEL_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息渠道表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `note_def`
--

DROP TABLE IF EXISTS `note_def`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note_def` (
  `NOTEDEF_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `NOTE_TYPE` varchar(45) NOT NULL COMMENT '消息类型',
  `NOTE_VALUE` varchar(300) DEFAULT NULL COMMENT '消息内容',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NOTE_ACTION` varchar(45) DEFAULT NULL COMMENT '消息点击触发事件',
  `NOTE_APPLICATION` varchar(60) NOT NULL COMMENT '消息类型',
  PRIMARY KEY (`NOTEDEF_UUID`),
  KEY `NOTEDEF_INDEX_1` (`NOTE_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `num`
--

DROP TABLE IF EXISTS `num`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `num` (
  `i` int(11) NOT NULL,
  PRIMARY KEY (`i`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用于图表数据产生辅助表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `opt_contact`
--

DROP TABLE IF EXISTS `opt_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opt_contact` (
  `OPTCONTACT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `CONTACTTYPE` varchar(10) NOT NULL COMMENT '通知类型(邮箱, SMS)',
  `CONTACTNO` varchar(50) NOT NULL COMMENT '通知联系号码(手机号码, 电子邮件地址)',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  PRIMARY KEY (`OPTCONTACT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台通知人员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `ORDER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `ORDER_NO` varchar(20) NOT NULL COMMENT '订单号',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ORDER_TYPE` varchar(10) DEFAULT NULL COMMENT '0-普通订单, 1-秒杀订单, 2-团购订单',
  `ORDER_STATUS` varchar(10) NOT NULL DEFAULT '1' COMMENT '订单状态',
  `PRODUCT_UNIT` int(11) DEFAULT NULL COMMENT '数量',
  `PRODUCT_POINT` int(11) DEFAULT NULL COMMENT '总积分',
  `PRODUCT_COST_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '成本总价',
  `PRODUCT_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '总金额',
  `DEDUCT_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '优惠金额',
  `FREIGHT_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '订单运费',
  `ACTUAL_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '实际支付金额',
  `ACTUAL_POINT` int(11) DEFAULT NULL COMMENT '实际支付积分',
  `ORDER_TIME` timestamp NULL DEFAULT NULL COMMENT '下单时间',
  `DELIVERY_TIME` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `DELIVERY_TYPE` varchar(10) DEFAULT NULL COMMENT '1-快递配送, 2-同城配送, 3-门店自提',
  `DELIVERY_ADDRESS_UUID` varchar(36) DEFAULT NULL COMMENT '收货地址ID',
  `DELIVERY_PROVINCE` varchar(45) DEFAULT NULL COMMENT '收货地址(省)',
  `DELIVERY_CITY` varchar(45) DEFAULT NULL COMMENT '收货地址(市)',
  `DELIVERY_AREA` varchar(45) DEFAULT NULL COMMENT '收货地址(区)',
  `DELIVERY_STREET` varchar(200) DEFAULT NULL COMMENT '收货地址(详细地址)',
  `DELIVERY_NAME` varchar(100) DEFAULT NULL COMMENT '收货人姓名',
  `DELIVERY_CONTACT_NO` varchar(20) DEFAULT NULL COMMENT '收货人电话',
  `DELIVERY_ZIPCODE` varchar(20) DEFAULT NULL COMMENT '收货地址邮编',
  `DELIVERY_LATITUDE` decimal(20,10) DEFAULT NULL COMMENT '配送地址经纬度(针对配送方式为同城配送)',
  `DELIVERY_LONGITUDE` decimal(20,10) DEFAULT NULL COMMENT '配送地址经纬度(针对配送方式为同城配送)',
  `MEMO` varchar(200) DEFAULT NULL COMMENT '订单备注(买家)',
  `FORM_ID` varchar(100) DEFAULT NULL COMMENT '小程序端提交时的Form ID',
  `COURIER_NAME` varchar(45) DEFAULT NULL COMMENT '物流公司',
  `COURIER_NO` varchar(45) DEFAULT NULL COMMENT '物流编号',
  `ORDER_COMMENT` varchar(500) DEFAULT NULL COMMENT '订单备注(卖家)',
  `PAYMENT_METHOD` varchar(10) DEFAULT NULL COMMENT '订单支付方式(1-微信,2-支付宝,3-银行卡,4-余额)',
  `ORDER_CHANNEL` varchar(45) DEFAULT NULL COMMENT '下单渠道(1-微信小程序,2-H5,3-APP)',
  `PAYMENT_TIME` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `CANCEL_TIME` timestamp NULL DEFAULT NULL COMMENT '订单取消时间',
  `CANCEL_REASON` varchar(500) DEFAULT NULL COMMENT '订单取消原因',
  `AFTER_SALE_DEADLINE_TIME` timestamp NULL DEFAULT NULL COMMENT '确认收货后申请售后的最晚时间',
  `IS_AFTER_SALE` tinyint(1) DEFAULT '0' COMMENT '订单是否已申请售后',
  `AFTER_SALE_NO` varchar(45) DEFAULT NULL COMMENT '售后单号',
  `USER_COUPON_UUID` varchar(36) DEFAULT NULL COMMENT '订单使用的优惠券',
  `CONFIRM_TIME` timestamp NULL DEFAULT NULL COMMENT '确认收货时间',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '门店ID',
  `P_ORDER_NO` varchar(45) DEFAULT NULL COMMENT '合并支付时产生的主订单号',
  `TRANSACTION_ID` varchar(45) DEFAULT NULL COMMENT '微信支付后生成的交易编号',
  `IS_ACCOUNTED` tinyint(1) DEFAULT NULL COMMENT '订单是否入账到商家',
  `REFUND_ID` varchar(45) DEFAULT NULL COMMENT '微信退款后生成的交易编号',
  `REFUND_MSG` varchar(450) DEFAULT NULL COMMENT '微信退款返回的消息',
  `SHOPPER_NAME` varchar(36) DEFAULT NULL COMMENT '配送员姓名',
  `SHOPPER_MOBILENO` varchar(36) DEFAULT NULL COMMENT '配送员手机号码',
  `SHOPPER_PHOTO` varchar(500) DEFAULT NULL COMMENT '配送员头像',
  `SHOPPER_SEX` varchar(5) DEFAULT NULL COMMENT '配送员性别',
  `GROUP_BUY_UUID` varchar(36) DEFAULT NULL COMMENT '订单对应的拼团单',
  PRIMARY KEY (`ORDER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_after_sale`
--

DROP TABLE IF EXISTS `order_after_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_after_sale` (
  `ORDER_AFTER_SALE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ORDER_UUID` varchar(36) DEFAULT NULL,
  `SALE_NO` varchar(45) DEFAULT NULL COMMENT '售后单号',
  `AFTER_SALE_TYPE` varchar(5) DEFAULT NULL COMMENT '售后类型(1-仅退款, 2-退款退货)',
  `AFTER_SALE_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '申请退款金额',
  `AFTER_SALE_DESCRIPTION` varchar(500) DEFAULT NULL COMMENT '售后描述',
  `TIME_APPLICATION` timestamp NULL DEFAULT NULL COMMENT '申请售后时间',
  `TIME_OPERATION` timestamp NULL DEFAULT NULL COMMENT '处理售后时间',
  `TIME_COURIER` timestamp NULL DEFAULT NULL COMMENT '退货时客户退货时间',
  `TIME_CONFIRM` timestamp NULL DEFAULT NULL COMMENT '退货时商家确认收到退还货物时间',
  `STATUS` varchar(5) DEFAULT NULL COMMENT '售后单状态(0-处理中,1-审核通过,2-审核拒绝)',
  `REJECT_REASON` varchar(500) DEFAULT NULL COMMENT '拒绝退款申请原因',
  `COURIER_NO` varchar(45) DEFAULT NULL COMMENT '退货物流单号',
  `COURIER_NAME` varchar(45) DEFAULT NULL COMMENT '退货物流公司',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL,
  `REFUND_ID` varchar(45) DEFAULT NULL COMMENT '微信退款后生成的交易编号',
  `REFUND_MSG` varchar(450) DEFAULT NULL COMMENT '微信退款返回的消息',
  PRIMARY KEY (`ORDER_AFTER_SALE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='售后单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_after_sale_image`
--

DROP TABLE IF EXISTS `order_after_sale_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_after_sale_image` (
  `ORDER_AFTER_SALE_IMAGE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ORDER_AFTER_SALE_UUID` varchar(36) DEFAULT NULL,
  `URL` varchar(500) DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`ORDER_AFTER_SALE_IMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='售后单凭据图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_image`
--

DROP TABLE IF EXISTS `order_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_image` (
  `ORDERIMAGE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ORDER_UUID` varchar(36) DEFAULT NULL,
  `IMAGEURL` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ORDERIMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='暂不使用';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_product`
--

DROP TABLE IF EXISTS `order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_product` (
  `ORDER_PRODUCT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `ORDER_UUID` varchar(36) NOT NULL COMMENT '订单ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ACTUAL_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '实际支付金额',
  `ACTUAL_POINT` int(11) DEFAULT NULL COMMENT '实际支付积分',
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `PRODUCTSKU_UUID` varchar(36) DEFAULT NULL COMMENT '商品规格ID',
  `PRODUCT_NAME` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `PRODUCT_CODE` varchar(45) DEFAULT NULL COMMENT '商品货号',
  `PRODUCT_IMAGE_URL` varchar(500) DEFAULT NULL COMMENT '商品主图',
  `PRODUCT_SKU_DESC` varchar(500) DEFAULT NULL COMMENT '商品规格描述',
  `PRODUCT_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '商品价格',
  `PRODUCT_POINT` int(11) DEFAULT NULL COMMENT '商品积分',
  `PRODUCT_UNIT` int(11) DEFAULT NULL COMMENT '商品数量',
  `PRODUCT_UNIT_POINT` int(11) DEFAULT NULL,
  `PRODUCT_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '商品单价',
  `POINT_PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '积分商品ID',
  `POINT_PRODUCT_SKU_UUID` varchar(36) DEFAULT NULL COMMENT '积分商品SKU',
  `GROUP_BUY_PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '团购商品ID',
  `SEC_KILL_PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '秒杀商品ID',
  PRIMARY KEY (`ORDER_PRODUCT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `org`
--

DROP TABLE IF EXISTS `org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `org` (
  `ORG_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NAME` varchar(60) NOT NULL COMMENT '部门名称',
  `MANAGER_UUID` varchar(36) DEFAULT NULL COMMENT '经理ID',
  `DESCRIPTION` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ORG_UUID`),
  KEY `ORG_index_1` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `org_rel`
--

DROP TABLE IF EXISTS `org_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `org_rel` (
  `ORGREL_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ORG_PARENT_UUID` varchar(36) NOT NULL COMMENT '上级部门ID',
  `ORG_CHILD_UUID` varchar(36) NOT NULL COMMENT '下级部门ID',
  PRIMARY KEY (`ORGREL_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pay_order`
--

DROP TABLE IF EXISTS `pay_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_order` (
  `PAYORDER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `ORDER_NO` varchar(20) DEFAULT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ORDER_STATUS` varchar(1) DEFAULT NULL COMMENT '订单状态',
  `PRODUCT_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '消费金额',
  `ACTUAL_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '实际支付金额',
  `OUT_TRADE_NO` varchar(45) DEFAULT NULL COMMENT '微信或支付宝交易编号',
  `PAYMENT_TIME` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `ORDER_TIME` timestamp NULL DEFAULT NULL COMMENT '下单时间',
  `DELIVERY_TIME` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家ID',
  `REWARD_POINT` int(11) DEFAULT NULL COMMENT '奖励积分',
  `IS_REWARD_POINT_RECEIVED` tinyint(1) DEFAULT NULL COMMENT '积分是否领取',
  `WECHATPAY_UUID` varchar(36) DEFAULT NULL COMMENT '微信交易编号',
  `ALIPAY_UUID` varchar(36) DEFAULT NULL COMMENT '支付宝交易编号',
  `PAYMENT_METHOD` varchar(10) DEFAULT NULL COMMENT '消费金额',
  `ASINFO_PLATFORM` decimal(12,2) DEFAULT NULL COMMENT '该笔订单平台分账金额',
  `ASINFO_MERCHANT` decimal(12,2) DEFAULT NULL COMMENT '该笔订单商家分账金额',
  PRIMARY KEY (`PAYORDER_UUID`),
  UNIQUE KEY `MOBILENO_UNIQUE` (`ORDER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线下消费订单表(如扫码支付)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `PERMISSION_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `ROLE_UUID` varchar(36) NOT NULL COMMENT '角色ID',
  `FUNCTION_UUID` varchar(36) NOT NULL COMMENT '功能ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  PRIMARY KEY (`PERMISSION_UUID`),
  KEY `PERMISSION_INDEX_1` (`ROLE_UUID`,`FUNCTION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台管理权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `platform_account`
--

DROP TABLE IF EXISTS `platform_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `platform_account` (
  `PLATFORMACCOUNT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ACCOUNT_TYPE` varchar(10) DEFAULT NULL COMMENT '账户类型(微信,支付宝,银行卡)',
  `BANKNAME` varchar(45) DEFAULT NULL COMMENT '银行名称',
  `BRANCHNAME` varchar(45) DEFAULT NULL COMMENT '支行名称',
  `BANKACCOUNTNO` varchar(20) DEFAULT NULL COMMENT '银行账号',
  `BANKACCOUNTNAME` varchar(45) DEFAULT NULL COMMENT '银行账户名称',
  `ALIPAY_ID` varchar(80) DEFAULT NULL COMMENT '支付宝ID',
  `WECHAT_ID` varchar(80) DEFAULT NULL COMMENT '微信ID',
  `BANKCODE` varchar(20) DEFAULT NULL COMMENT '支行编号',
  `ALIPAY_QRCODE` varchar(500) DEFAULT NULL COMMENT '支付宝收款码地址',
  `WECHAT_QRCODE` varchar(500) DEFAULT NULL COMMENT '微信收款码地址',
  PRIMARY KEY (`PLATFORMACCOUNT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司收款账号';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `platform_balance`
--

DROP TABLE IF EXISTS `platform_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `platform_balance` (
  `PLATFORMBALANCE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `AVAILABLE_BALANCE` decimal(12,2) DEFAULT NULL COMMENT '可提现余额',
  `TOTAL_BALANCE` decimal(12,2) DEFAULT NULL COMMENT '总余额, 可能有部分余额因为订单未结束而不能提现',
  `AVAILABLE_INTEGRAL` int(11) DEFAULT NULL COMMENT '可用积分',
  `TOTAL_INTEGRAL` int(11) DEFAULT NULL COMMENT '总积分, 某些积分可能因为订单状态暂不可用',
  PRIMARY KEY (`PLATFORMBALANCE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台方资金余额';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `platform_statement`
--

DROP TABLE IF EXISTS `platform_statement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `platform_statement` (
  `PLATFORMSTATEMENT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TRANSACTION_TYPE` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `TRANSACTION_TIME` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `TRANSACTION_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '交易金额',
  `TRANSACTION_CODE` varchar(20) DEFAULT NULL COMMENT '交易编号',
  `BALANCE` decimal(12,2) DEFAULT NULL COMMENT '该笔交易发生后余额',
  PRIMARY KEY (`PLATFORMSTATEMENT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台方资金对账单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product`
--

DROP TABLE IF EXISTS `point_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product` (
  `PRODUCT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_DESC` longtext COMMENT '商品描述',
  `PRODUCT_NAME` varchar(45) NOT NULL COMMENT '商品名称',
  `PRODUCT_CODE` varchar(45) DEFAULT NULL COMMENT '商品编码',
  `PRODUCT_BRIEF` varchar(100) DEFAULT NULL COMMENT '商品简短描述',
  `PRODUCT_WEIGHT` decimal(12,2) DEFAULT NULL COMMENT '商品重量',
  `UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '商品单价',
  `UNIT_PRICE_STANDARD` decimal(12,2) DEFAULT NULL COMMENT '标准价格',
  `TOTALUNIT` int(11) DEFAULT NULL COMMENT '商品总库存数量',
  `SOLDUNIT` int(11) DEFAULT NULL COMMENT '商品已售数量',
  `IS_SKU_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否多规格商品',
  `PRODUCTCATE_UUID` varchar(36) DEFAULT NULL COMMENT '商品品类',
  `PRODUCTBRAND_UUID` varchar(36) DEFAULT NULL COMMENT '品牌',
  `PRODUCT_MAIN_IMAGE_UUID` varchar(36) DEFAULT NULL COMMENT '商品主图',
  `PRODUCTFREIGHT_UUID` varchar(36) DEFAULT NULL COMMENT '运费模板',
  `PROMOTE_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '促销单价',
  `MEMBERSHIP_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '会员单价',
  `MARKET_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '市场价',
  `PROMOTE_START_DATE` timestamp NULL DEFAULT NULL COMMENT '促销开始时间',
  `PROMOTE_END_DATE` timestamp NULL DEFAULT NULL COMMENT '促销结束时间',
  `WARN_UNIT` int(11) DEFAULT NULL COMMENT '库存预警数量',
  `IS_NEW` tinyint(1) DEFAULT NULL COMMENT '是否新品',
  `IS_HOT` tinyint(1) DEFAULT NULL COMMENT '是否热销',
  `IS_PROMOTE` tinyint(1) DEFAULT NULL COMMENT '是否参与促销',
  `UNIT_POINT` int(11) DEFAULT NULL COMMENT '购买需要实际积分数',
  `UNIT_POINT_STANDARD` int(11) DEFAULT NULL COMMENT '购买需要划线积分数',
  `IS_RECOMMEND` tinyint(1) DEFAULT NULL COMMENT '是否推荐商品',
  `PRODUCT_TYPE` varchar(45) DEFAULT NULL COMMENT '商品类型, 实物或者优惠券',
  `IS_ON_SALE` tinyint(1) DEFAULT NULL COMMENT '上架,下架',
  `IS_SHIPPING` tinyint(1) DEFAULT NULL COMMENT '是否需要邮寄',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '商家备注',
  `PRODUCT_COST_PRICE` decimal(12,2) DEFAULT NULL COMMENT '商品成本价',
  `SUPPLIER_UUID` varchar(36) DEFAULT NULL COMMENT '商品所属供应商',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_attr_value`
--

DROP TABLE IF EXISTS `point_product_attr_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_attr_value` (
  `PRODUCTATTRVALUE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(46) DEFAULT NULL,
  `IS_CATE_ATTR` tinyint(1) DEFAULT NULL,
  `PRODUCTCATEATTR_UUID` varchar(36) DEFAULT NULL,
  `PRODUCT_ATTR_NAME` varchar(45) DEFAULT NULL COMMENT '属性名称',
  `PRODUCT_ATTR_VALUE` varchar(100) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`PRODUCTATTRVALUE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品属性和属性值';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_cate`
--

DROP TABLE IF EXISTS `point_product_cate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_cate` (
  `PRODUCTCATE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `CATE_NAME` varchar(45) NOT NULL COMMENT '品类名称',
  `CATE_PATH` varchar(500) DEFAULT NULL COMMENT '品类路径',
  `PARENTCATE_UUID` varchar(46) DEFAULT NULL COMMENT '上级品类ID',
  `SORTNUMBER` int(11) DEFAULT '0' COMMENT '排序',
  `CATE_PIC_URL` varchar(500) DEFAULT NULL COMMENT '主图地址',
  `IS_LEAF` tinyint(1) DEFAULT NULL COMMENT '是否叶子节点',
  `IS_DISPLAYED_HOME` tinyint(1) DEFAULT '0' COMMENT '是否首页显示',
  PRIMARY KEY (`PRODUCTCATE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品品类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_cate_attr`
--

DROP TABLE IF EXISTS `point_product_cate_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_cate_attr` (
  `PRODUCTCATEATTR_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCTCATE_UUID` varchar(46) DEFAULT NULL COMMENT '品类ID',
  `ATTR_NAME` varchar(50) NOT NULL DEFAULT '0' COMMENT '属性名',
  `ATTR_VALUE` varchar(500) DEFAULT NULL COMMENT '属性值',
  `IS_SPU_ATTR` tinyint(1) DEFAULT NULL,
  `IS_MANDATORY` tinyint(1) DEFAULT NULL,
  `IS_MULTIPLE` tinyint(1) DEFAULT NULL,
  `IS_INPUT` tinyint(1) DEFAULT NULL,
  `IS_SKU_ATTR` tinyint(1) DEFAULT NULL,
  `IS_SEARCH_ATTR` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PRODUCTCATEATTR_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品品类预定于属性及可选属性值(暂不使用)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_collect`
--

DROP TABLE IF EXISTS `point_product_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_collect` (
  `PRODUCT_COLLECT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  PRIMARY KEY (`PRODUCT_COLLECT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_comment`
--

DROP TABLE IF EXISTS `point_product_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_comment` (
  `PRODUCTCOMMENT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `COMMENT_CONTENT` varchar(500) DEFAULT NULL COMMENT '评论类容',
  `COMMENT_RANK` int(11) DEFAULT NULL COMMENT '评论打分',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '发表评论会员ID',
  `REPLAY_CONTENT` varchar(500) DEFAULT NULL COMMENT '回复内容',
  `EVALUATE_TIME` timestamp NULL DEFAULT NULL COMMENT '发表时间',
  `REPLAY_TIME` timestamp NULL DEFAULT NULL COMMENT '回复时间',
  `ORDER_UUID` varchar(36) DEFAULT NULL COMMENT '订单ID',
  `IS_SHOW` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`PRODUCTCOMMENT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_comment_image`
--

DROP TABLE IF EXISTS `point_product_comment_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_comment_image` (
  `PRODUCTCOMMENTIMAGE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCTCOMMENT_UUID` varchar(36) NOT NULL COMMENT '评论ID',
  `IMAGEURL` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`PRODUCTCOMMENTIMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品评论图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_desc_image`
--

DROP TABLE IF EXISTS `point_product_desc_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_desc_image` (
  `PRODUCTDESCIMAGE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `FILE_UUID` varchar(36) DEFAULT NULL COMMENT '文件地址ID',
  PRIMARY KEY (`PRODUCTDESCIMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品描述图片';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_image`
--

DROP TABLE IF EXISTS `point_product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_image` (
  `PRODUCTIMAGE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL,
  `FILE_UUID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`PRODUCTIMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品图片';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_sku`
--

DROP TABLE IF EXISTS `point_product_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_sku` (
  `PRODUCTSKU_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `SKU_UNIT_POINT` int(11) DEFAULT NULL COMMENT '规格单价(积分)',
  `SKU_UNIT_POINT_STANDARD` int(11) DEFAULT NULL COMMENT '规格划线单价(积分)',
  `SKU_CODE` varchar(45) DEFAULT NULL COMMENT '规格货号',
  `SKU_TOTAL_UNIT` int(11) DEFAULT NULL COMMENT '规格库存',
  `SKU_SOLD_UNIT` int(11) DEFAULT NULL COMMENT '规格已售数量',
  `SKU_WARN_UNIT` int(11) DEFAULT NULL COMMENT '库存预警数量',
  `PRODUCT_SKU_ATTR_VALUE_UUIDS` longtext COMMENT 'SKU对应的规格名和规格值',
  `SKU_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '规格单价',
  `SKU_UNIT_PRICE_STANDARD` decimal(12,2) DEFAULT NULL COMMENT '规格标准单价',
  `SKU_IMAGE_URL` varchar(500) DEFAULT NULL COMMENT 'SKU商品图片',
  PRIMARY KEY (`PRODUCTSKU_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品规格表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_sku_attr`
--

DROP TABLE IF EXISTS `point_product_sku_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_sku_attr` (
  `PRODUCTSKUATTR_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(46) DEFAULT NULL COMMENT '商品ID',
  `IS_CATE_ATTR` tinyint(1) DEFAULT NULL COMMENT '是否继承自品类属性',
  `PRODUCTCATEATTR_UUID` varchar(36) DEFAULT NULL COMMENT '商品品类属性ID',
  `PRODUCT_ATTR_NAME` varchar(45) DEFAULT NULL COMMENT '商品品类名称',
  `PRODUCT_ATTR_VALUE` varchar(100) DEFAULT NULL COMMENT '暂不使用',
  PRIMARY KEY (`PRODUCTSKUATTR_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品规格名表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_sku_attr_value`
--

DROP TABLE IF EXISTS `point_product_sku_attr_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_sku_attr_value` (
  `PRODUCTSKUATTRVALUE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(46) DEFAULT NULL COMMENT '商品ID',
  `PRODUCTSKUATTR_UUID` varchar(36) DEFAULT NULL COMMENT '商品规格名ID',
  `PRODUCT_SKU_ATTR_VALUE` varchar(45) DEFAULT NULL COMMENT '商品规格值',
  `IMAGE_URL` varchar(500) DEFAULT NULL COMMENT '规格主图',
  PRIMARY KEY (`PRODUCTSKUATTRVALUE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品规格值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_product_video`
--

DROP TABLE IF EXISTS `point_product_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_product_video` (
  `PRODUCTVIDEO_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '商品视频',
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `FILE_UUID` varchar(36) DEFAULT NULL COMMENT '视频文件ID',
  PRIMARY KEY (`PRODUCTVIDEO_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分商品视频表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_setting`
--

DROP TABLE IF EXISTS `point_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_setting` (
  `POINT_SETTING_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `IS_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `AMOUNT` decimal(12,2) DEFAULT NULL,
  `POINT` int(11) DEFAULT NULL,
  PRIMARY KEY (`POINT_SETTING_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `poster`
--

DROP TABLE IF EXISTS `poster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `poster` (
  `POSTER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TAG_UUID` varchar(36) DEFAULT NULL COMMENT '标签ID',
  `URL` varchar(500) DEFAULT NULL COMMENT '海报地址',
  PRIMARY KEY (`POSTER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='海报表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `print_setting`
--

DROP TABLE IF EXISTS `print_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_setting` (
  `PRINT_SETTING_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `FEIE_USER` varchar(36) DEFAULT NULL COMMENT '飞鹅云账号',
  `FEIE_SN` varchar(100) DEFAULT NULL COMMENT '飞鹅云打印机编号',
  `FEIE_KEY` varchar(45) DEFAULT NULL COMMENT '飞鹅云打印机的KEY',
  `FEIE_UKEY` varchar(45) DEFAULT NULL COMMENT '飞鹅云后台注册账号后生成的UKEY',
  `MERCHANT_UUID` varchar(45) DEFAULT NULL COMMENT '商家ID',
  PRIMARY KEY (`PRINT_SETTING_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='打印设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `PRODUCT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_DESC` longtext COMMENT '商品描述',
  `PRODUCT_TYPE` varchar(1) DEFAULT '1' COMMENT '商品类型(1-实物商品,2-虚拟商品,3-线下核销类商品)',
  `PRODUCT_NAME` varchar(45) NOT NULL COMMENT '商品名称',
  `PRODUCT_CODE` varchar(45) DEFAULT NULL COMMENT '商品编码',
  `PRODUCT_BRIEF` varchar(100) DEFAULT NULL COMMENT '商品简短描述',
  `PRODUCT_WEIGHT` decimal(12,2) DEFAULT NULL COMMENT '商品重量',
  `UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '商品单价',
  `UNIT_PRICE_STANDARD` decimal(12,2) DEFAULT NULL COMMENT '标准价格',
  `TOTALUNIT` int(11) DEFAULT NULL COMMENT '商品总库存数量',
  `SOLDUNIT` int(11) DEFAULT NULL COMMENT '商品已售数量',
  `IS_SKU_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否多规格商品',
  `PRODUCTCATE_UUID` varchar(36) DEFAULT NULL COMMENT '商品品类',
  `PRODUCTBRAND_UUID` varchar(36) DEFAULT NULL COMMENT '品牌',
  `PRODUCT_MAIN_IMAGE_UUID` varchar(36) DEFAULT NULL COMMENT '商品主图',
  `PRODUCTFREIGHT_UUID` varchar(36) DEFAULT NULL COMMENT '运费模板',
  `PRODUCTDELIVERY_UUID` varchar(36) DEFAULT NULL COMMENT '同城配送收费模板',
  `PROMOTE_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '促销单价',
  `MEMBERSHIP_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '会员单价',
  `MARKET_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '市场价',
  `PROMOTE_START_DATE` timestamp NULL DEFAULT NULL COMMENT '促销开始时间',
  `PROMOTE_END_DATE` timestamp NULL DEFAULT NULL COMMENT '促销结束时间',
  `WARN_UNIT` int(11) DEFAULT NULL COMMENT '库存预警数量',
  `IS_NEW` tinyint(1) DEFAULT NULL COMMENT '是否新品',
  `IS_HOT` tinyint(1) DEFAULT NULL COMMENT '是否热销',
  `IS_PROMOTE` tinyint(1) DEFAULT NULL COMMENT '是否参与促销',
  `UNIT_POINT` int(11) DEFAULT NULL COMMENT '购买需要实际积分数',
  `UNIT_POINT_STANDARD` int(11) DEFAULT NULL COMMENT '购买需要划线积分数',
  `IS_RECOMMEND` tinyint(1) DEFAULT NULL COMMENT '是否推荐商品',
  `IS_ON_SALE` tinyint(1) DEFAULT NULL COMMENT '上架,下架',
  `IS_SHIPPING` tinyint(1) DEFAULT NULL COMMENT '是否需要邮寄',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '商家备注',
  `PRODUCT_COST_PRICE` decimal(12,2) DEFAULT NULL COMMENT '商品成本价',
  `SUPPLIER_UUID` varchar(36) DEFAULT NULL COMMENT '商品所属供应商',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL,
  `VERIFY_STATUS` varchar(10) DEFAULT NULL COMMENT '商品审核状态(0-审核中, 1-审核通过， 2-审核未通过)',
  `VERIFY_MSG` varchar(500) DEFAULT NULL COMMENT '商品审核消息',
  `IS_DELIVERY_EXPRESS_ENABLED` tinyint(1) DEFAULT '1' COMMENT '商品是否支持发货方式-快递配送',
  `IS_DELIVERY_CITY_ENABLED` tinyint(1) DEFAULT '0' COMMENT '商品是否支持发货方式-同城配送',
  `IS_DELIVERY_PICK_ENABLED` tinyint(1) DEFAULT '0' COMMENT '商品是否支持发货方式-到店自提',
  `VALID_TYPE` varchar(45) DEFAULT NULL COMMENT '有效期类型(仅用于电子卡券商品)',
  `VALID_DAYS` int(11) DEFAULT '0' COMMENT '几天内有效(仅用于电子卡券商品)',
  `VALID_START_DATE` date DEFAULT NULL COMMENT '有效开始时间(仅用于电子卡券商品)',
  `VALID_END_DATE` date DEFAULT NULL COMMENT '有效结束时间(仅用于电子卡券商品)',
  PRIMARY KEY (`PRODUCT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_attr_value`
--

DROP TABLE IF EXISTS `product_attr_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_attr_value` (
  `PRODUCTATTRVALUE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(46) DEFAULT NULL,
  `IS_CATE_ATTR` tinyint(1) DEFAULT NULL,
  `PRODUCTCATEATTR_UUID` varchar(36) DEFAULT NULL,
  `PRODUCT_ATTR_NAME` varchar(45) DEFAULT NULL COMMENT '属性名称',
  `PRODUCT_ATTR_VALUE` varchar(100) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`PRODUCTATTRVALUE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性和属性值';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_brand`
--

DROP TABLE IF EXISTS `product_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_brand` (
  `PRODUCTBRAND_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NAME` varchar(40) NOT NULL COMMENT '品牌名称',
  `DESCRIPTION` varchar(100) DEFAULT NULL COMMENT '品牌描述',
  `LOGOURL` varchar(500) DEFAULT NULL COMMENT 'LOGO地址',
  `SORTNUMBER` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`PRODUCTBRAND_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品品牌';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_cate`
--

DROP TABLE IF EXISTS `product_cate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_cate` (
  `PRODUCTCATE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `CATE_NAME` varchar(45) NOT NULL COMMENT '品类名称',
  `CATE_PATH` varchar(500) DEFAULT NULL COMMENT '品类路径',
  `PARENTCATE_UUID` varchar(46) DEFAULT NULL COMMENT '上级品类ID',
  `SORTNUMBER` int(11) DEFAULT '0' COMMENT '排序',
  `CATE_PIC_URL` varchar(500) DEFAULT NULL COMMENT '主图地址',
  `IS_LEAF` tinyint(1) DEFAULT NULL COMMENT '是否叶子节点',
  `IS_DISPLAYED_HOME` tinyint(1) DEFAULT '0' COMMENT '是否首页显示',
  PRIMARY KEY (`PRODUCTCATE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品品类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_cate_attr`
--

DROP TABLE IF EXISTS `product_cate_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_cate_attr` (
  `PRODUCTCATEATTR_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCTCATE_UUID` varchar(46) DEFAULT NULL COMMENT '品类ID',
  `ATTR_NAME` varchar(50) NOT NULL DEFAULT '0' COMMENT '属性名',
  `ATTR_VALUE` varchar(500) DEFAULT NULL COMMENT '属性值',
  `IS_SPU_ATTR` tinyint(1) DEFAULT NULL,
  `IS_MANDATORY` tinyint(1) DEFAULT NULL,
  `IS_MULTIPLE` tinyint(1) DEFAULT NULL,
  `IS_INPUT` tinyint(1) DEFAULT NULL,
  `IS_SKU_ATTR` tinyint(1) DEFAULT NULL,
  `IS_SEARCH_ATTR` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PRODUCTCATEATTR_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品品类预定于属性及可选属性值(暂不使用)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_collect`
--

DROP TABLE IF EXISTS `product_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_collect` (
  `PRODUCT_COLLECT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  PRIMARY KEY (`PRODUCT_COLLECT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_comment`
--

DROP TABLE IF EXISTS `product_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_comment` (
  `PRODUCTCOMMENT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `COMMENT_CONTENT` varchar(500) DEFAULT NULL COMMENT '评论类容',
  `COMMENT_RANK` int(11) DEFAULT NULL COMMENT '评论打分',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '发表评论会员ID',
  `REPLAY_CONTENT` varchar(500) DEFAULT NULL COMMENT '回复内容',
  `EVALUATE_TIME` timestamp NULL DEFAULT NULL COMMENT '发表时间',
  `REPLAY_TIME` timestamp NULL DEFAULT NULL COMMENT '回复时间',
  `ORDER_UUID` varchar(36) DEFAULT NULL COMMENT '订单ID',
  `IS_SHOW` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`PRODUCTCOMMENT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_comment_image`
--

DROP TABLE IF EXISTS `product_comment_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_comment_image` (
  `PRODUCTCOMMENTIMAGE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCTCOMMENT_UUID` varchar(36) NOT NULL COMMENT '评论ID',
  `IMAGEURL` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`PRODUCTCOMMENTIMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评论图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_delivery`
--

DROP TABLE IF EXISTS `product_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_delivery` (
  `PRODUCTDELIVERY_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NAME` varchar(40) NOT NULL COMMENT '模板名称',
  `DESCRIPTION` varchar(200) DEFAULT NULL COMMENT '配送模板说明',
  `DELIVERY_CONDITION_DISTANCE` int(11) DEFAULT NULL COMMENT '起送金额(订单超过该金额才能配送)',
  `DELIVERY_CONDITION_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '最远配送距离(千米), 超过该距离不能配送',
  `FIRST_DISTANCE` int(11) DEFAULT NULL COMMENT '配送起步距离(该距离内配送免费)',
  `FIRST_PRICE` decimal(12,2) DEFAULT NULL COMMENT '配送起步价',
  `NEXT_DISTANCE` int(11) DEFAULT NULL COMMENT '超出起步距离每多少公里收费多少元',
  `NEXT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '超出起步距离每多少公里收费多少元',
  `IS_ACTIVE` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `IS_DEFAULT` tinyint(1) DEFAULT '0',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL,
  `DELIVERY_FROM_ADDRESS` varchar(200) DEFAULT NULL COMMENT '起送地址名称(如果1号仓库)',
  `DELIVERY_FROM_PROVINCE` varchar(45) DEFAULT NULL COMMENT '起送地址所在省',
  `DELIVERY_FROM_CITY` varchar(45) DEFAULT NULL COMMENT '起送地址所在城市',
  `DELIVERY_FROM_DISTRICT` varchar(45) DEFAULT NULL COMMENT '起送地址所在区',
  `DELIVERY_FROM_DETAIL` varchar(200) DEFAULT NULL COMMENT '起送地址详细地址',
  `DELIVERY_FROM_LONGITUDE` decimal(20,10) DEFAULT NULL,
  `DELIVERY_FROM_LATITUDE` decimal(20,10) DEFAULT NULL,
  PRIMARY KEY (`PRODUCTDELIVERY_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='同城配送收费模板表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_desc_image`
--

DROP TABLE IF EXISTS `product_desc_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_desc_image` (
  `PRODUCTDESCIMAGE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `FILE_UUID` varchar(36) DEFAULT NULL COMMENT '文件地址ID',
  PRIMARY KEY (`PRODUCTDESCIMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品描述图片';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_freight`
--

DROP TABLE IF EXISTS `product_freight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_freight` (
  `PRODUCTFREIGHT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NAME` varchar(40) NOT NULL COMMENT '模板名称',
  `CHARGETYPE` int(11) DEFAULT NULL COMMENT '计价方式(1-按件数, 2-按重量)',
  `COURIER_NAME` varchar(45) DEFAULT NULL COMMENT '物流公司',
  `IS_SHIPPING_FREE` tinyint(1) DEFAULT NULL COMMENT '是否包邮',
  `SHIPPING_FREE_CONDITION_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '包邮条件(满多少金额, 设置为0表示无限制)',
  `DEFAULT_FIRST_WEIGHT` decimal(12,2) DEFAULT NULL COMMENT '默认首重',
  `DEFAULT_NEXT_WEIGHT` decimal(12,2) DEFAULT NULL COMMENT '续重',
  `DEFAULT_FIRST_UNIT` int(11) DEFAULT NULL COMMENT '默认首件件数',
  `DEFAULT_NEXT_UNIT` int(11) DEFAULT NULL COMMENT '续件件数',
  `DEFAULT_FIRST_PRICE` decimal(12,2) DEFAULT NULL COMMENT '默认首重/件金额',
  `DEFAULT_NEXT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '默认续重/件金额',
  `IS_ACTIVE` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `IS_DEFAULT` tinyint(1) DEFAULT '0',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`PRODUCTFREIGHT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运费模板表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_freight_region`
--

DROP TABLE IF EXISTS `product_freight_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_freight_region` (
  `PRODUCTFREIGHTREGION_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCTGREIGHT_UUID` varchar(36) NOT NULL COMMENT '运费模板ID',
  `FIRST_UNIT` int(11) DEFAULT NULL COMMENT '首件',
  `NEXT_UNIT` int(11) DEFAULT NULL COMMENT '续件',
  `FIRST_WEIGHT` decimal(12,2) DEFAULT NULL COMMENT '首重',
  `NEXT_WEIGHT` decimal(12,2) DEFAULT NULL COMMENT '续重',
  `FIRST_PRICE` decimal(12,2) DEFAULT NULL COMMENT '前多少件价格',
  `NEXT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '接下来每多少件价格',
  `AREA_ID` varchar(100) DEFAULT NULL COMMENT '区域',
  PRIMARY KEY (`PRODUCTFREIGHTREGION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运费模板区域价格表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_group`
--

DROP TABLE IF EXISTS `product_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_group` (
  `PRODUCTGROUP_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品组对应商品',
  `GROUP_UUID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`PRODUCTGROUP_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品与组关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_image` (
  `PRODUCTIMAGE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL,
  `FILE_UUID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`PRODUCTIMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品图片';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_inventory_inbound`
--

DROP TABLE IF EXISTS `product_inventory_inbound`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_inventory_inbound` (
  `PRODUCT_INVENTORY_INBOUND_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `PRODUCT_SKU_UUID` varchar(36) DEFAULT NULL COMMENT '商品规格ID',
  `SKU_NAME` varchar(100) DEFAULT NULL,
  `TRAN_UNIT` int(11) DEFAULT NULL COMMENT '入库数量',
  `TRAN_TIME` timestamp NULL DEFAULT NULL COMMENT '入库时间',
  `TRAN_TYPE` varchar(45) DEFAULT NULL COMMENT '入库类型(如补货, 退货等)',
  `MEMO` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_INVENTORY_INBOUND_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品入库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_inventory_outbound`
--

DROP TABLE IF EXISTS `product_inventory_outbound`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_inventory_outbound` (
  `PRODUCT_INVENTORY_OUTBOUND_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `PRODUCT_SKU_UUID` varchar(36) DEFAULT NULL COMMENT '商品规格ID',
  `SKU_NAME` varchar(100) DEFAULT NULL,
  `TRAN_UNIT` int(11) DEFAULT NULL COMMENT '出库数量',
  `TRAN_TIME` timestamp NULL DEFAULT NULL COMMENT '出库时间',
  `TRAN_TYPE` varchar(45) DEFAULT NULL COMMENT '出库类型(如调仓, 发货等)',
  `MEMO` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_INVENTORY_OUTBOUND_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品出库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_inventory_taking`
--

DROP TABLE IF EXISTS `product_inventory_taking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_inventory_taking` (
  `PRODUCT_INVENTORY_TAKING_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `PRODUCT_SKU_UUID` varchar(36) DEFAULT NULL COMMENT '商品规格ID',
  `SKU_NAME` varchar(100) DEFAULT NULL COMMENT '规格名称',
  `TRAN_UNIT` int(11) DEFAULT NULL COMMENT '盘点数量',
  `PROFIT_UNIT` int(11) DEFAULT NULL COMMENT '盘亏(盘赢)',
  `TRAN_TIME` timestamp NULL DEFAULT NULL COMMENT '盘点时间',
  `MEMO` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_INVENTORY_TAKING_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品盘点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_promote`
--

DROP TABLE IF EXISTS `product_promote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_promote` (
  `PRODUCTPROMOTE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PROMOTE_TYPE` varchar(10) NOT NULL,
  `PROMOTE_UUID` varchar(36) DEFAULT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL,
  `SUPPLIER_UUID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`PRODUCTPROMOTE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品品牌';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_sku`
--

DROP TABLE IF EXISTS `product_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_sku` (
  `PRODUCTSKU_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `SKU_UNIT_POINT` int(11) DEFAULT NULL COMMENT '规格单价(积分)',
  `SKU_UNIT_POINT_STANDARD` int(11) DEFAULT NULL COMMENT '规格划线单价(积分)',
  `SKU_CODE` varchar(45) DEFAULT NULL COMMENT '规格货号',
  `SKU_TOTAL_UNIT` int(11) DEFAULT NULL COMMENT '规格库存',
  `SKU_SOLD_UNIT` int(11) DEFAULT NULL COMMENT '规格已售数量',
  `SKU_WARN_UNIT` int(11) DEFAULT NULL COMMENT '库存预警数量',
  `PRODUCT_SKU_ATTR_VALUE_UUIDS` longtext COMMENT 'SKU对应的规格名和规格值',
  `SKU_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '规格单价',
  `SKU_UNIT_PRICE_STANDARD` decimal(12,2) DEFAULT NULL COMMENT '规格标准单价',
  `SKU_IMAGE_URL` varchar(500) DEFAULT NULL COMMENT 'SKU商品图片',
  PRIMARY KEY (`PRODUCTSKU_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_sku_attr`
--

DROP TABLE IF EXISTS `product_sku_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_sku_attr` (
  `PRODUCTSKUATTR_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(46) DEFAULT NULL COMMENT '商品ID',
  `IS_CATE_ATTR` tinyint(1) DEFAULT NULL COMMENT '是否继承自品类属性',
  `PRODUCTCATEATTR_UUID` varchar(36) DEFAULT NULL COMMENT '商品品类属性ID',
  `PRODUCT_ATTR_NAME` varchar(45) DEFAULT NULL COMMENT '商品品类名称',
  `PRODUCT_ATTR_VALUE` varchar(100) DEFAULT NULL COMMENT '暂不使用',
  PRIMARY KEY (`PRODUCTSKUATTR_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格名表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_sku_attr_value`
--

DROP TABLE IF EXISTS `product_sku_attr_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_sku_attr_value` (
  `PRODUCTSKUATTRVALUE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(46) DEFAULT NULL COMMENT '商品ID',
  `PRODUCTSKUATTR_UUID` varchar(36) DEFAULT NULL COMMENT '商品规格名ID',
  `PRODUCT_SKU_ATTR_VALUE` varchar(45) DEFAULT NULL COMMENT '商品规格值',
  `IMAGE_URL` varchar(500) DEFAULT NULL COMMENT '规格主图',
  PRIMARY KEY (`PRODUCTSKUATTRVALUE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_video`
--

DROP TABLE IF EXISTS `product_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_video` (
  `PRODUCTVIDEO_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '商品视频',
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `FILE_UUID` varchar(36) DEFAULT NULL COMMENT '视频文件ID',
  PRIMARY KEY (`PRODUCTVIDEO_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品视频表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profit_distribution`
--

DROP TABLE IF EXISTS `profit_distribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profit_distribution` (
  `PROFIT_DISTRIBUTION_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NAME` varchar(45) DEFAULT NULL COMMENT '分销名称',
  `IS_DISTRIBUTION_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否开启分销',
  `IS_DISTRIBUTOR_PURCHASE_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否开启分销内购，分销商自己购买商品，享受一级佣金，上级享受二级佣金，上上级享受三级佣金',
  `DISTRIBUTION_LEVEL` int(11) DEFAULT NULL COMMENT '分销级数(支持1,2,3级分销)',
  `LEVEL1_RATE` decimal(12,2) DEFAULT NULL COMMENT '1级分佣比例(不含%, 如3%则存值为3)',
  `LEVEL2_RATE` decimal(12,2) DEFAULT NULL COMMENT '2级分佣比例(不含%, 如3%则存值为3)',
  `LEVEL3_RATE` decimal(12,2) DEFAULT NULL COMMENT '3级分佣比例(不含%, 如3%则存值为3)',
  `APPLICATION_CONTENT` longtext COMMENT '分销申请页面内容',
  `IS_SHARE_REQUIRED` tinyint(1) DEFAULT NULL COMMENT '是否需要分享',
  `IS_FORM_REQUIRED` tinyint(1) DEFAULT NULL COMMENT '是否需要填写表单',
  `IS_APPROVE_REQUIRED` tinyint(1) DEFAULT NULL COMMENT '是否需要审核',
  PRIMARY KEY (`PROFIT_DISTRIBUTION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profit_performance`
--

DROP TABLE IF EXISTS `profit_performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profit_performance` (
  `PROFIT_PERFORMANCE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PROFIT_USER_LEVEL_UUID` varchar(36) DEFAULT NULL COMMENT '获取团队收益的会员等级',
  `AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '团队销售金额',
  `RATING` decimal(12,2) DEFAULT NULL COMMENT '提成比例',
  PRIMARY KEY (`PROFIT_PERFORMANCE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队收益设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profit_recruit`
--

DROP TABLE IF EXISTS `profit_recruit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profit_recruit` (
  `PROFIT_RECRUIT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `RECRUIT_USER_LEVEL_UUID` varchar(36) DEFAULT NULL COMMENT '推广的会员等级',
  `PROFIT_USER_LEVEL_UUID` varchar(36) DEFAULT NULL COMMENT '获取推广收益的会员等级',
  `PROFIT` decimal(12,2) DEFAULT NULL COMMENT '收益金额',
  PRIMARY KEY (`PROFIT_RECRUIT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推广收益设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profit_sale`
--

DROP TABLE IF EXISTS `profit_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profit_sale` (
  `PROFIT_SALE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PROFIT_USER_LEVEL_UUID` varchar(36) DEFAULT NULL COMMENT '获取推广收益的会员等级',
  `PROFIT_RATE` decimal(12,2) DEFAULT NULL COMMENT '分润比例',
  PRIMARY KEY (`PROFIT_SALE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售收益设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profit_trainer`
--

DROP TABLE IF EXISTS `profit_trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profit_trainer` (
  `PROFIT_TRAINER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PROFIT_USER_LEVEL_UUID` varchar(36) DEFAULT NULL COMMENT '培训导师辅导的会员等级',
  `AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '团队销售金额',
  `RATING` decimal(12,2) DEFAULT NULL COMMENT '提成比例',
  PRIMARY KEY (`PROFIT_TRAINER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='培训导师收益设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profit_welfare`
--

DROP TABLE IF EXISTS `profit_welfare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profit_welfare` (
  `PROFIT_WELFARE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `POINT_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否赠送积分',
  `COUPON_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否赠送优惠券',
  `PRODUCT_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否开启专属商品',
  `WELFARE_TYPE` varchar(32) DEFAULT NULL COMMENT '福利类型(积分, 优惠券, 专属商品)',
  `POINT_GIVEN` int(11) DEFAULT NULL COMMENT '赠送积分数',
  `COUPON_UUID` varchar(36) DEFAULT NULL COMMENT '优惠券ID',
  `COUPON_COUNT` int(11) DEFAULT NULL COMMENT '赠送优惠券数量',
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `PRODUCT_UNIT_PRICE` decimal(12,2) DEFAULT NULL COMMENT '商品单价',
  PRIMARY KEY (`PROFIT_WELFARE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新人福利设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qa`
--

DROP TABLE IF EXISTS `qa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qa` (
  `QA_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TAG_UUID` varchar(36) DEFAULT NULL COMMENT '标签ID',
  `CONTENT` longtext COMMENT '回答',
  `TITLE` varchar(200) DEFAULT NULL COMMENT '问题',
  `COVERIMAGE_URL` varchar(500) DEFAULT NULL COMMENT '主图地址',
  `MEDIA_ID` varchar(200) DEFAULT NULL COMMENT '暂不使用',
  `IS_FROM_OFFICIALACCOUNT` tinyint(1) DEFAULT NULL COMMENT '暂不使用',
  `PREVIEW_URL` varchar(500) DEFAULT NULL COMMENT '暂不使用',
  `PUBLISH_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  `DIGEST` varchar(200) DEFAULT NULL COMMENT '暂不使用',
  `NUMBER_OF_READING` int(11) DEFAULT '0' COMMENT '浏览次数',
  `ISPUBLISHED` tinyint(1) DEFAULT NULL COMMENT '是否发布',
  `ISHOT` tinyint(1) DEFAULT '0' COMMENT '是否热门',
  PRIMARY KEY (`QA_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='常见问题表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `ROLE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `ROLE_NAME` varchar(20) NOT NULL COMMENT '角色名称',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ISACTIVE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `ROLE_DESC` varchar(200) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`ROLE_UUID`),
  KEY `ROLE_INDEX_1` (`ROLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scene`
--

DROP TABLE IF EXISTS `scene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scene` (
  `SCENE_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TAG_UUID` varchar(36) DEFAULT NULL,
  `CONTENT` longtext,
  `TITLE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SCENE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场景表(暂不使用)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sec_kill_product`
--

DROP TABLE IF EXISTS `sec_kill_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_kill_product` (
  `SEC_KILL_PRODUCT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `PRODUCT_UUID` varchar(46) DEFAULT NULL COMMENT '商品ID',
  `STOCK` int(11) DEFAULT NULL COMMENT '秒杀商品库存',
  `UNIT_PRICE` varchar(45) DEFAULT NULL COMMENT '秒杀价格',
  `STATUS` varchar(36) DEFAULT NULL COMMENT '秒杀商品状态(0-秒杀商品已下线, 1-秒杀商品已上线)',
  `START_TIME` timestamp NULL DEFAULT NULL COMMENT '秒杀开始时间',
  `END_TIME` timestamp NULL DEFAULT NULL COMMENT '秒杀结束时间',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '商家',
  `SOLD_UNIT` int(11) DEFAULT '0',
  PRIMARY KEY (`SEC_KILL_PRODUCT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀商品表(引用商品基本表)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sequence`
--

DROP TABLE IF EXISTS `sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence` (
  `SEQUENCE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `SEQNAME` varchar(30) NOT NULL COMMENT '业务名称',
  `NEXTNUM` int(11) NOT NULL COMMENT '下一个编号',
  `TRANDATE` date DEFAULT NULL COMMENT '交易编号时间',
  PRIMARY KEY (`SEQUENCE_UUID`),
  KEY `SEQUENCE_INDEX_1` (`SEQNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='各业务表的交易编号产生表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `SETTING_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `SETTING_NAME` varchar(45) NOT NULL COMMENT '配置项名称',
  `SETTING_VALUE` varchar(400) DEFAULT NULL COMMENT '配置项值',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `SETTING_DESC` varchar(200) DEFAULT NULL COMMENT '配置项描述',
  PRIMARY KEY (`SETTING_UUID`),
  UNIQUE KEY `SETTING_INDEX_2` (`SETTING_NAME`),
  KEY `SETTING_INDEX_1` (`SETTING_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share`
--

DROP TABLE IF EXISTS `share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share` (
  `SHARE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TAG_UUID` varchar(36) DEFAULT NULL COMMENT '标签ID',
  `CONTENT` longtext COMMENT '内容',
  `PUBLISH_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  `ISPUBLISHED` tinyint(1) DEFAULT NULL COMMENT '是否发布',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '发布会员ID',
  `LIKES` int(11) DEFAULT '0' COMMENT '集赞数量',
  PRIMARY KEY (`SHARE_UUID`),
  KEY `SHARE_FK_1_idx` (`USER_UUID`),
  CONSTRAINT `SHARE_FK_1` FOREIGN KEY (`USER_UUID`) REFERENCES `user` (`USER_UUID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='朋友圈分享';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_comment`
--

DROP TABLE IF EXISTS `share_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_comment` (
  `SHARECOMMENT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `SHARE_UUID` varchar(36) DEFAULT NULL COMMENT '朋友圈ID',
  `COMMENT` varchar(300) DEFAULT NULL COMMENT '评论内容',
  `COMMENT_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '评论会员',
  PRIMARY KEY (`SHARECOMMENT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='朋友圈评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_comment_at`
--

DROP TABLE IF EXISTS `share_comment_at`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_comment_at` (
  `SHARECOMMENTAT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `SHARECOMMENT_UUID` varchar(36) DEFAULT NULL COMMENT '评论ID',
  `COMMENT` varchar(300) DEFAULT NULL COMMENT '回复内容',
  `COMMENT_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '回复时间',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '回复会员ID',
  PRIMARY KEY (`SHARECOMMENTAT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='朋友圈评论回复';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_image`
--

DROP TABLE IF EXISTS `share_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_image` (
  `SHAREIMAGE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `SHARE_UUID` varchar(36) DEFAULT NULL COMMENT '朋友圈ID',
  `URL` varchar(300) DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`SHAREIMAGE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='朋友圈图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_like`
--

DROP TABLE IF EXISTS `share_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share_like` (
  `SHARELIKE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `SHARE_UUID` varchar(36) DEFAULT NULL COMMENT '朋友圈ID',
  `LIKE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '点赞时间',
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '点赞会员',
  PRIMARY KEY (`SHARELIKE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='朋友圈点赞';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_cart` (
  `SHOPPINGCART_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  `UNIT` int(11) NOT NULL COMMENT '商品数量',
  `PRODUCTSKU_UUID` varchar(36) DEFAULT NULL COMMENT '商品规格ID',
  PRIMARY KEY (`SHOPPINGCART_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sign_setting`
--

DROP TABLE IF EXISTS `sign_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sign_setting` (
  `SIGN_SETTING_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `IS_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `DAY1_POINT` int(11) DEFAULT NULL COMMENT '连续签到1天获得积分数',
  `DAY2_POINT` int(11) DEFAULT NULL COMMENT '连续签到2天获得积分数',
  `DAY3_POINT` int(11) DEFAULT NULL COMMENT '连续签到3天获得积分数',
  `DAY4_POINT` int(11) DEFAULT NULL COMMENT '连续签到4天获得积分数',
  `DAY5_POINT` int(11) DEFAULT NULL COMMENT '连续签到5天获得积分数',
  `DAY6_POINT` int(11) DEFAULT NULL COMMENT '连续签到6天获得积分数',
  `DAY7_POINT` int(11) DEFAULT NULL COMMENT '连续签到7天获得积分数',
  `BACKGROUND_URL` varchar(500) DEFAULT NULL COMMENT '领取积分成功显示背景图',
  `RULE_URL` varchar(500) DEFAULT NULL COMMENT '积分规则背景图',
  PRIMARY KEY (`SIGN_SETTING_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summary_day`
--

DROP TABLE IF EXISTS `summary_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summary_day` (
  `datelist` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用于图表数据产生辅助表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summary_month`
--

DROP TABLE IF EXISTS `summary_month`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summary_month` (
  `year` int(11) DEFAULT NULL,
  `monthlist` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用于图表数据产生辅助表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summary_quarter`
--

DROP TABLE IF EXISTS `summary_quarter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summary_quarter` (
  `year` int(11) DEFAULT NULL,
  `quarterlist` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用于图表数据产生辅助表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summary_week`
--

DROP TABLE IF EXISTS `summary_week`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summary_week` (
  `year` int(11) DEFAULT NULL,
  `weeklist` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summary_year`
--

DROP TABLE IF EXISTS `summary_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summary_year` (
  `yearlist` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用于图表数据产生辅助表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier` (
  `SUPPLIER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MOBILENO` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `SUPPLIERNAME` varchar(100) DEFAULT NULL COMMENT '供应商名称',
  `SUPPLIERADDRESS` varchar(200) DEFAULT NULL COMMENT '供应商地址',
  `CONTACTNAME` varchar(20) DEFAULT NULL COMMENT '联系人',
  `SUPPLIERDESCRIPTION` varchar(200) DEFAULT NULL COMMENT '供应商描述',
  `ISCLOSED` tinyint(1) DEFAULT '0' COMMENT '是否关闭',
  `REGISTER_DATE` timestamp NULL DEFAULT NULL COMMENT '入驻时间',
  `MEMO` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`SUPPLIER_UUID`),
  UNIQUE KEY `MOBILENO_UNIQUE` (`MOBILENO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `supplier_product`
--

DROP TABLE IF EXISTS `supplier_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier_product` (
  `SUPPLIERPRODUCT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `SUPPLIER_UUID` varchar(36) DEFAULT NULL COMMENT '供应商ID',
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`SUPPLIERPRODUCT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商产品关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `swiper`
--

DROP TABLE IF EXISTS `swiper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `swiper` (
  `SWIPER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `URL` varchar(300) DEFAULT NULL COMMENT '轮播图图片地址',
  `CONTENT` longtext COMMENT '轮播图点击后的内容',
  `LINK_TYPE` varchar(10) DEFAULT NULL COMMENT '轮播图点击后跳转的类型(文章, 商品)',
  `PRODUCTCATE_UUID` varchar(36) DEFAULT NULL COMMENT '轮播图关联的商品品类',
  `LOCATION` varchar(45) DEFAULT NULL COMMENT '轮播图位置:首页和商品分类页',
  PRIMARY KEY (`SWIPER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `SYSLOG_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `LOG_DATE` timestamp NULL DEFAULT NULL COMMENT '日志日期',
  `LOG_USER_ID` varchar(45) DEFAULT NULL COMMENT '涉及的会员ID',
  `LOG_TYPE` varchar(10) DEFAULT NULL COMMENT '人工或系统日志',
  `LOG_CATEGORY` varchar(45) DEFAULT NULL COMMENT '日志类型',
  `LOG_DETAIL` varchar(200) DEFAULT NULL COMMENT '日志详情',
  PRIMARY KEY (`SYSLOG_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `TAG_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TAG_TYPE` varchar(20) DEFAULT NULL COMMENT '标签类型',
  `TAG_NAME` varchar(45) DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`TAG_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trainer`
--

DROP TABLE IF EXISTS `trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainer` (
  `TRAINER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `TRAINER_LEVEL` varchar(20) DEFAULT NULL COMMENT '培训导师级别',
  `PARENT_TRAINER_UUID` varchar(36) DEFAULT NULL COMMENT '父级培训导师',
  `EFFECTIVE_DATE` timestamp NULL DEFAULT NULL COMMENT '有效开始日期',
  PRIMARY KEY (`TRAINER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='培训导师表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `USER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `ID` varchar(24) DEFAULT NULL COMMENT '会员编号',
  `PHOTOURL` varchar(500) DEFAULT NULL COMMENT '会员头像',
  `NAME` varchar(20) DEFAULT NULL COMMENT '姓名',
  `IDCARDNO` varchar(45) DEFAULT NULL COMMENT '身份证号码',
  `SEX` varchar(10) DEFAULT NULL COMMENT '性别',
  `PERSONALEMAIL` varchar(40) DEFAULT NULL COMMENT '电子邮箱',
  `PERSONALPHONE` varchar(17) DEFAULT NULL COMMENT '电话号码',
  `PERSONALPHONE_COUNTRYCODE` varchar(4) DEFAULT NULL COMMENT '电话号码国家代码',
  `OPENID` varchar(45) DEFAULT NULL COMMENT '小程序open id',
  `REGISTER_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册日期',
  `ISACTIVE` tinyint(1) DEFAULT '1' COMMENT '是否激活',
  `MEMO` varchar(100) DEFAULT NULL COMMENT '备注',
  `OPENID_OFC` varchar(45) DEFAULT NULL COMMENT '公众号OPENID',
  `UNIONID` varchar(45) DEFAULT NULL COMMENT '微信开放平台unionID',
  `IS_NOTIFICATION_ENABLED` tinyint(1) DEFAULT '1' COMMENT '是否开启消息通知',
  `SUPERVISOR_UUID` varchar(36) DEFAULT NULL COMMENT '上级会员(具有推广权并能获取收益)',
  `INVITATION_UUID` varchar(36) DEFAULT NULL COMMENT '邀请会员(直接邀请人)',
  `USER_LEVEL_UUID` varchar(36) DEFAULT NULL COMMENT '会员等级ID',
  `TRAINER_UUID` varchar(36) DEFAULT NULL COMMENT '培训导师',
  `IS_PROFIT_WELFARE_RECEIVED` tinyint(1) DEFAULT '0' COMMENT '是否已领取新人福利',
  PRIMARY KEY (`USER_UUID`),
  KEY `USER_INDEX_1` (`NAME`),
  KEY `USER_INDEX_2` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_auth`
--

DROP TABLE IF EXISTS `user_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_auth` (
  `USERAUTH_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `USER_UUID` varchar(36) NOT NULL COMMENT '会员ID',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码(MD5)',
  `LASTLOGINTIME` timestamp NULL DEFAULT NULL COMMENT '最近登录时间',
  `FAILCOUNT` int(11) DEFAULT '0' COMMENT '登录失败次数',
  `ISLOCKED` tinyint(1) DEFAULT '0',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `SUCCESSCOUNT` int(11) DEFAULT NULL COMMENT '总登录次数',
  `ISACTIVATED` tinyint(1) DEFAULT '0' COMMENT '是否激活',
  PRIMARY KEY (`USERAUTH_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员验证表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_award`
--

DROP TABLE IF EXISTS `user_award`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_award` (
  `USERAWARD_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `TRANSACTION_TYPE` varchar(10) DEFAULT NULL COMMENT '奖励类型',
  `TRANSACTION_TIME` timestamp NULL DEFAULT NULL COMMENT '奖励时间',
  `TRANSACTION_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '奖励金额',
  `TRANSACTION_CODE` varchar(20) DEFAULT NULL COMMENT '交易编号',
  `TRANSACTION_DESC` varchar(200) DEFAULT NULL COMMENT '交易描述',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`USERAWARD_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户奖励表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_balance`
--

DROP TABLE IF EXISTS `user_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_balance` (
  `USERBALANCE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `AVAILABLE_BALANCE` decimal(12,2) DEFAULT NULL COMMENT '可用余额',
  `LEDGER_BALANCE` decimal(12,2) DEFAULT NULL COMMENT '未到账金额',
  `AVAILABLE_POINT` int(11) DEFAULT NULL COMMENT '可用积分',
  `LEDGER_POINT` int(11) DEFAULT NULL COMMENT '未到账积分',
  PRIMARY KEY (`USERBALANCE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户余额表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_coupon`
--

DROP TABLE IF EXISTS `user_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_coupon` (
  `USERCOUPON_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) NOT NULL COMMENT '会员ID',
  `COUPON_UUID` varchar(36) NOT NULL COMMENT '优惠券ID',
  `IS_USED` tinyint(1) DEFAULT NULL COMMENT '是否已使用',
  `USE_TIME` timestamp NULL DEFAULT NULL COMMENT '使用时间',
  `USE_CHANNEL` varchar(45) DEFAULT NULL COMMENT '使用方式',
  `RECEIVE_CHANNEL` varchar(45) DEFAULT NULL COMMENT '领取方式',
  `RECEIVE_TIME` timestamp NULL DEFAULT NULL COMMENT '领取时间',
  `VALID_START_DATE` date DEFAULT NULL COMMENT '有效期开始日期',
  `VALID_END_DATE` date DEFAULT NULL COMMENT '有效期结束日期',
  PRIMARY KEY (`USERCOUPON_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员卡券表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_delivery`
--

DROP TABLE IF EXISTS `user_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_delivery` (
  `USERDELIVERY_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) NOT NULL COMMENT '会员ID',
  `DELIVERY_STATUS` varchar(10) NOT NULL COMMENT '提货状态',
  `DELIVERY_ADDRESS` varchar(100) DEFAULT NULL COMMENT '提货地址',
  `DELIVERY_TYPE` varchar(10) DEFAULT NULL COMMENT '邮寄或者自提',
  `APPLICATION_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '提货申请时间',
  `DELIVERY_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '实际提货时间',
  `DELIVERY_QUANTITY` int(11) DEFAULT NULL COMMENT '提货数量',
  `COURIER_NAME` varchar(45) DEFAULT NULL COMMENT '物流公司',
  `COURIER_NO` varchar(45) DEFAULT NULL COMMENT '物流编号',
  `DELIVERY_POSTCODE` varchar(6) DEFAULT NULL COMMENT '邮编',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '备注',
  `MERCHANT_UUID` varchar(36) DEFAULT NULL COMMENT '提货商家',
  `DELIVERY_NAME` varchar(45) DEFAULT NULL COMMENT '收货人姓名',
  `DELIVERY_CONTACTNO` varchar(45) DEFAULT NULL COMMENT '收货人联系电话',
  PRIMARY KEY (`USERDELIVERY_UUID`),
  KEY `USERDELIVERY_FOREIGN_1_idx` (`USER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员提货记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_delivery_address`
--

DROP TABLE IF EXISTS `user_delivery_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_delivery_address` (
  `USERDELIVERYADDRESS_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `USER_UUID` varchar(36) NOT NULL COMMENT '会员ID',
  `NAME` varchar(20) NOT NULL COMMENT '收货人姓名',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `TELEPHONE` varchar(12) DEFAULT NULL COMMENT '收货人电话',
  `TELEPHONE2` varchar(12) DEFAULT NULL COMMENT '收货人电话2',
  `COUNTRY` varchar(25) DEFAULT NULL COMMENT '国家',
  `PROVINCE` varchar(15) DEFAULT NULL COMMENT '省',
  `CITY` varchar(15) DEFAULT NULL COMMENT '市',
  `AREA` varchar(15) DEFAULT NULL COMMENT '区',
  `STREET` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `ZIPCODE` varchar(6) DEFAULT NULL COMMENT '邮编',
  `IS_DEFAULT` tinyint(1) DEFAULT NULL COMMENT '是否默认地址',
  PRIMARY KEY (`USERDELIVERYADDRESS_UUID`),
  KEY `USERGROUP_INDEX_1` (`USER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员收货地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_inventory`
--

DROP TABLE IF EXISTS `user_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_inventory` (
  `USERINVENTORY_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) NOT NULL COMMENT '会员ID',
  `BALANCE` int(11) NOT NULL COMMENT '库存箱',
  `BALANCE_POUCH` int(11) DEFAULT NULL COMMENT '库存袋',
  PRIMARY KEY (`USERINVENTORY_UUID`),
  KEY `USERINVENTORY_FOREIGN_1_idx` (`USER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员商品库存';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_inventory_history`
--

DROP TABLE IF EXISTS `user_inventory_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_inventory_history` (
  `USERINVENTORYHISTORY_UUID` varchar(36) NOT NULL,
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) NOT NULL,
  `BALANCEBEFORE` int(11) NOT NULL COMMENT '交易前库存数',
  `BALANCEAFTER` int(11) DEFAULT NULL COMMENT '交易后库存数',
  `TRANTYPE` varchar(45) DEFAULT NULL COMMENT '交易类型',
  `TRANUNIT` int(11) DEFAULT NULL COMMENT '交易数量',
  `ISINCREASE` tinyint(1) DEFAULT '1' COMMENT '交易使库存增加或减少',
  `TRANDESC` varchar(200) DEFAULT NULL,
  `BALANCEPOUCHBEFORE` int(11) DEFAULT NULL COMMENT '交易前产品袋数',
  `BALANCEPOUCHAFTER` int(11) DEFAULT NULL COMMENT '交易后产品袋数',
  `TRANUNITVALUE` varchar(45) DEFAULT NULL COMMENT '数量单位',
  PRIMARY KEY (`USERINVENTORYHISTORY_UUID`),
  KEY `USERINVENTORYHISTORY_FOREIGN_1_idx` (`USER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户库存历史记录表(暂不使用)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_level`
--

DROP TABLE IF EXISTS `user_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_level` (
  `USER_LEVEL_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `NAME` varchar(45) DEFAULT NULL COMMENT '等级名称',
  `PRODUCT_DISCOUNT` decimal(12,2) DEFAULT NULL COMMENT '商品折扣',
  `IS_RECRUIT_PROFIT_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否可招募会员',
  `IS_SALE_PROFIT_ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否可销售商品',
  `REQUIRED_USER_COUNT_DIRECT` int(11) DEFAULT NULL COMMENT '到达该等级所需直邀会员数',
  `REQUIRED_USER_COUNT_TEAM` int(11) DEFAULT NULL COMMENT '到达该等级所需团队会员数',
  `REQUIRED_PRODUCT_AMOUNT_DIRECT` decimal(12,2) DEFAULT NULL COMMENT '到达该等级需直接销售金额',
  `REQUIRED_PRODUCT_AMOUNT_TEAM` decimal(12,2) DEFAULT NULL COMMENT '到达该等级需团队销售金额',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '会员备注',
  `IS_REQUIRED_BY_USER` tinyint(1) DEFAULT NULL COMMENT '升级至该会员等级条件按会员数量',
  `REQUIRED_BY_USER_SYMBOL` varchar(10) DEFAULT NULL COMMENT '直邀, 团队会员数量(OR, AND)',
  `IS_REQUIRED_BY_AMOUNT` tinyint(1) DEFAULT NULL COMMENT '升级至该会员等级条件按销售金额',
  `REQUIRED_BY_AMOUNT_SYMBOL` varchar(10) DEFAULT NULL COMMENT '直邀, 团队会员数量(OR, AND)',
  `IS_DEFAULT` tinyint(1) DEFAULT '0',
  `DEPTH` int(11) DEFAULT NULL COMMENT '等级深度',
  PRIMARY KEY (`USER_LEVEL_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_performance`
--

DROP TABLE IF EXISTS `user_performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_performance` (
  `USER_PERFORMANCE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `PERFORMANCE_DATE` date DEFAULT NULL COMMENT '月',
  `PERFORMANCE_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '月销售金额',
  `PERFORMANCE_AWARD` decimal(12,2) DEFAULT NULL COMMENT '月业绩奖金',
  PRIMARY KEY (`USER_PERFORMANCE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队月销售额表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_point_statement`
--

DROP TABLE IF EXISTS `user_point_statement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_point_statement` (
  `USERPOINTSTATEMENT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `TRANSACTION_TYPE` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `TRANSACTION_TIME` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `TRANSACTION_CODE` varchar(20) DEFAULT NULL COMMENT '交易编号',
  `TRANSACTION_POINT` int(11) DEFAULT NULL COMMENT '交易积分',
  `TRANSACTION_DESC` varchar(500) DEFAULT NULL COMMENT '交易描述',
  `POINTBEFORE` int(11) DEFAULT NULL COMMENT '交易前积分余额',
  `POINTAFTER` int(11) DEFAULT NULL COMMENT '交易后积分余额',
  PRIMARY KEY (`USERPOINTSTATEMENT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员积分明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_sign`
--

DROP TABLE IF EXISTS `user_sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_sign` (
  `USERSIGN_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) NOT NULL COMMENT '会员ID',
  `SIGN_TIME` timestamp NULL DEFAULT NULL COMMENT '签到时间',
  `SIGN_POINT` int(11) DEFAULT NULL COMMENT '签到获得积分',
  PRIMARY KEY (`USERSIGN_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员签到表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_statement`
--

DROP TABLE IF EXISTS `user_statement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_statement` (
  `USERSTATEMENT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `TRANSACTION_TYPE` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `TRANSACTION_TIME` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `TRANSACTION_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '交易金额',
  `TRANSACTION_CODE` varchar(20) DEFAULT NULL COMMENT '交易编号',
  `TRANSACTION_POINT` int(11) DEFAULT NULL COMMENT '交易积分',
  `TRANSACTION_DESC` varchar(500) DEFAULT NULL COMMENT '交易描述',
  `BALANCEBEFORE` decimal(12,2) DEFAULT NULL COMMENT '交易前资金余额',
  `BALANCEAFTER` decimal(12,2) DEFAULT NULL COMMENT '交易后资金余额',
  `POINTBEFORE` int(11) DEFAULT NULL COMMENT '交易前积分余额',
  `POINTAFTER` int(11) DEFAULT NULL COMMENT '交易后积分余额',
  PRIMARY KEY (`USERSTATEMENT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员资金明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_token`
--

DROP TABLE IF EXISTS `user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_token` (
  `USERTOKEN_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) NOT NULL COMMENT '会员ID',
  `TOKENID` varchar(36) NOT NULL COMMENT 'token',
  `LOGINTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  `EXPIRETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`USERTOKEN_UUID`),
  KEY `USERTOKEN_FOREIGN_2_idx` (`USER_UUID`),
  KEY `USERTOKEN_INDEX_2` (`TOKENID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员登录Token表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_tree`
--

DROP TABLE IF EXISTS `user_tree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_tree` (
  `USER_TREE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) NOT NULL COMMENT '会员ID',
  `ANCESTOR_UUID` varchar(36) NOT NULL COMMENT '上级会员ID',
  `LEVEL` int(11) DEFAULT NULL COMMENT '上级深度(每个会员是自己的上级会员, 会员深度为0)',
  PRIMARY KEY (`USER_TREE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员树表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_voucher`
--

DROP TABLE IF EXISTS `user_voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_voucher` (
  `USER_VOUCHER_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(45) DEFAULT NULL COMMENT '会员',
  `MERCHANT_UUID` varchar(45) DEFAULT NULL COMMENT '商家',
  `ORDER_UUID` varchar(36) DEFAULT NULL COMMENT '购买电子卡券的订单',
  `PRODUCT_UUID` varchar(36) DEFAULT NULL COMMENT '电子卡券对应的商品',
  `NAME` varchar(45) DEFAULT NULL COMMENT '卡券名称',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '使用说明',
  `VOUCHER_CODE` varchar(45) DEFAULT NULL COMMENT '卡券码',
  `QR_CODE_URL` varchar(500) DEFAULT NULL COMMENT '卡券二维码地址',
  `BAR_CODE_URL` varchar(500) DEFAULT NULL COMMENT '卡券条形码码地址',
  `VALID_START_DATE` date DEFAULT NULL COMMENT '有效期开始日期',
  `VALID_END_DATE` date DEFAULT NULL COMMENT '有效期结束日期',
  `RECEIVE_TIME` timestamp NULL DEFAULT NULL COMMENT '卡券获取时间',
  `IS_USED` tinyint(1) DEFAULT NULL COMMENT '是否已使用',
  `USE_TIME` timestamp NULL DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`USER_VOUCHER_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员电子卡券';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_wechat`
--

DROP TABLE IF EXISTS `user_wechat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_wechat` (
  `USERWECHAT_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `USER_UUID` varchar(36) NOT NULL,
  `OPENID` varchar(50) NOT NULL COMMENT '微信OPEN ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  PRIMARY KEY (`USERWECHAT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员微信信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_withdraw`
--

DROP TABLE IF EXISTS `user_withdraw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_withdraw` (
  `USERWITHDRAW_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `IS_DELETED` varchar(1) DEFAULT '0',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `USER_UUID` varchar(36) DEFAULT NULL COMMENT '会员ID',
  `WITHDRAW_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '提现金额',
  `WITHDRAW_STATUS` varchar(10) DEFAULT NULL COMMENT '提现状态',
  `FAIL_REASON` varchar(100) DEFAULT NULL COMMENT '提现失败原因',
  `PAYMENT_METHOD` varchar(10) DEFAULT NULL COMMENT '提现支付方式',
  `BANKNAME` varchar(45) DEFAULT NULL COMMENT '银行名称',
  `BANKACCOUNTNO` varchar(20) DEFAULT NULL COMMENT '银行账号',
  `BANKACCOUNTNAME` varchar(45) DEFAULT NULL COMMENT '银行账户名',
  `ALIPAY_ID` varchar(80) DEFAULT NULL COMMENT '支付宝ID',
  `WECHAT_ID` varchar(80) DEFAULT NULL COMMENT '微信ID',
  `WITHDRAW_TIME` timestamp NULL DEFAULT NULL COMMENT '提现时间',
  `VERIFY_TIME` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `TRANSFER_TIME` timestamp NULL DEFAULT NULL COMMENT '打款时间',
  `WITHDRAW_NO` varchar(16) DEFAULT NULL COMMENT '提现交易编号',
  `REJECT_REASON` varchar(100) DEFAULT NULL COMMENT '审核拒绝理由',
  `COMPLETE_TIME` timestamp NULL DEFAULT NULL COMMENT '提现成功时间',
  `PAYMENT_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '提现金额',
  `TAX_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '手续费',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`USERWITHDRAW_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员提现表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `verification_code`
--

DROP TABLE IF EXISTS `verification_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verification_code` (
  `VERIFICATIONCODE_UUID` varchar(36) NOT NULL COMMENT '主键ID',
  `TYPE` varchar(1) NOT NULL COMMENT '验证码发放渠道类型(邮件,短信)',
  `MOBILE_COUNTRYCODE` varchar(4) DEFAULT '0' COMMENT '手机号国际代码',
  `DATE_CREATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(50) NOT NULL,
  `DATE_UPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(50) NOT NULL,
  `MOBILE_NUMBER` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `CODE` varchar(8) DEFAULT NULL COMMENT '验证码',
  `EMAIL` varchar(40) DEFAULT NULL COMMENT '邮件地址',
  `EXPIRE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '过期时间',
  `IS_DELETED` varchar(1) DEFAULT '0',
  PRIMARY KEY (`VERIFICATIONCODE_UUID`),
  KEY `VERIFICATIONCODE_INDEX_1` (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-08 17:54:01
