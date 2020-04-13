/*
Navicat MySQL Data Transfer

Source Server         : home
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : work

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2020-04-13 16:50:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for carport
-- ----------------------------
DROP TABLE IF EXISTS `carport`;
CREATE TABLE `carport` (
  `carport_id` int(20) NOT NULL AUTO_INCREMENT,
  `carport_num` varchar(32) NOT NULL COMMENT '车位编号',
  `carport_status` int(2) NOT NULL DEFAULT '0' COMMENT '0为空闲  1为使用  2为预约中 3为禁用',
  `carport_pos` varchar(255) NOT NULL COMMENT '车位路径',
  `parking_id` int(20) NOT NULL COMMENT '停车场id',
  PRIMARY KEY (`carport_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of carport
-- ----------------------------

-- ----------------------------
-- Table structure for ordercarport
-- ----------------------------
DROP TABLE IF EXISTS `ordercarport`;
CREATE TABLE `ordercarport` (
  `orderCarport_id` int(20) NOT NULL AUTO_INCREMENT,
  `orderCarport_user_id` int(20) NOT NULL,
  `orderCarport_carport_id` int(20) NOT NULL,
  `orderCarport_time` bigint(20) NOT NULL,
  `orderCarport_status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`orderCarport_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ordercarport
-- ----------------------------
INSERT INTO `ordercarport` VALUES ('1', '1', '1', '1586431334368', '1');
INSERT INTO `ordercarport` VALUES ('4', '1', '1', '1586572958253', '2');
INSERT INTO `ordercarport` VALUES ('5', '1', '1', '1586573476400', '2');
INSERT INTO `ordercarport` VALUES ('6', '1', '1', '1586574049751', '0');

-- ----------------------------
-- Table structure for parking
-- ----------------------------
DROP TABLE IF EXISTS `parking`;
CREATE TABLE `parking` (
  `parking_id` int(20) NOT NULL AUTO_INCREMENT,
  `parking_name` varchar(32) NOT NULL COMMENT '停车场名称',
  `parking_status` int(2) NOT NULL DEFAULT '0' COMMENT '0 零为使用中  1为禁用  2为满员',
  `parking_carport_size` int(20) NOT NULL COMMENT '停车场车位数量',
  `parking_carport_used` int(20) NOT NULL DEFAULT '0' COMMENT '停车场使用车位数量',
  `parking_price` double(10,2) DEFAULT NULL COMMENT '每小时单价',
  PRIMARY KEY (`parking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parking
-- ----------------------------

-- ----------------------------
-- Table structure for parking_log
-- ----------------------------
DROP TABLE IF EXISTS `parking_log`;
CREATE TABLE `parking_log` (
  `parking_log_id` int(20) NOT NULL AUTO_INCREMENT,
  `parking_log_parking_id` int(20) NOT NULL COMMENT '停车场id',
  `parking_log_plate_id` int(20) NOT NULL COMMENT '车牌id',
  `parking_log_user_id` int(20) NOT NULL COMMENT '用户id',
  `parking_log_start_time` bigint(15) NOT NULL,
  `parking_log_end_time` bigint(15) DEFAULT NULL,
  `parking_log_payment` double(15,2) DEFAULT NULL,
  PRIMARY KEY (`parking_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parking_log
-- ----------------------------

-- ----------------------------
-- Table structure for payment_log
-- ----------------------------
DROP TABLE IF EXISTS `payment_log`;
CREATE TABLE `payment_log` (
  `
payment_id` int(20) NOT NULL AUTO_INCREMENT,
  `payment_time` bigint(20) NOT NULL,
  `payment_money` double(20,2) NOT NULL,
  `payment_user_id` int(20) NOT NULL,
  PRIMARY KEY (`
payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payment_log
-- ----------------------------

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `permissions_id` int(11) NOT NULL AUTO_INCREMENT,
  `permissions_name` varchar(50) NOT NULL,
  `permissions_code` varchar(255) NOT NULL,
  PRIMARY KEY (`permissions_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES ('1', '全部权限', '');

-- ----------------------------
-- Table structure for plate
-- ----------------------------
DROP TABLE IF EXISTS `plate`;
CREATE TABLE `plate` (
  `plate_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `plate_code` varchar(32) NOT NULL COMMENT '车牌号',
  `plate_status` int(2) NOT NULL DEFAULT '0' COMMENT '0为 使用  1为禁用',
  PRIMARY KEY (`plate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plate
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  `role_code` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', 'admin');
INSERT INTO `role` VALUES ('2', '测试1', 'test');
INSERT INTO `role` VALUES ('3', '普通用户', 'user');
INSERT INTO `role` VALUES ('4', 'VIP用户', 'vipuser');

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions` (
  `role_permissions_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permissions_id` int(11) NOT NULL,
  PRIMARY KEY (`role_permissions_id`),
  KEY `role_id` (`role_id`),
  KEY `permissions_id` (`permissions_id`),
  CONSTRAINT `role_permissions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `role_permissions_ibfk_2` FOREIGN KEY (`permissions_id`) REFERENCES `permissions` (`permissions_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------
INSERT INTO `role_permissions` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(200) NOT NULL,
  `role_Id` int(20) DEFAULT '3',
  `status` int(2) DEFAULT '0',
  `balance` double(20,2) DEFAULT '0.00',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', 'root', '1', '0', '0.00');
INSERT INTO `user` VALUES ('2', 'xiaobai', '9471a72dc69c72598c62980a8e2de3bc', '1', '0', '0.00');
INSERT INTO `user` VALUES ('4', 'xiaohei', '18f478deafa21dd9c794e1198590fdff', '3', '0', '0.00');
INSERT INTO `user` VALUES ('5', 'xiaohong', '90ef7b152bc9ff5a8df4e62abfa28f8f', '3', '0', '0.00');
INSERT INTO `user` VALUES ('6', 'test', '897f56e878a67bfa86a84a30d7b363c1', '3', '0', '0.00');
