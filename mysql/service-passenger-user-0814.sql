/*
 Navicat Premium Data Transfer

 Source Server         : AliCloud_120.26.4.95
 Source Server Type    : MySQL
 Source Server Version : 50739
 Source Host           : 120.26.4.95:3306
 Source Schema         : service-passenger-user

 Target Server Type    : MySQL
 Target Server Version : 50739
 File Encoding         : 65001

 Date: 14/08/2022 22:21:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for passenger_user
-- ----------------------------
DROP TABLE IF EXISTS `passenger_user`;
CREATE TABLE `passenger_user` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `passenger_phone` varchar(16) DEFAULT NULL,
  `passenger_name` varchar(16) DEFAULT NULL,
  `passenger_gender` tinyint(1) DEFAULT NULL COMMENT '0:女 1:男',
  `state` tinyint(1) DEFAULT NULL COMMENT '0:有效 1:无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
