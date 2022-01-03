/*
 Navicat Premium Data Transfer

 Source Server         : 服务器mysql
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 139.224.65.105:3306
 Source Schema         : software

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 03/01/2022 20:08:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `gender` char(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员姓名',
  `create_time` datetime NOT NULL COMMENT '账户创建时间',
  `update_time` datetime NOT NULL COMMENT '账户修改时间',
  `mailbox` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员邮箱',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员头像url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100032 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (100001, '123456', '男', '宁吱吱', '2021-11-14 13:59:41', '2021-11-15 10:52:37', NULL, NULL);
INSERT INTO `administrator` VALUES (100006, '123456', NULL, 'nzhnzh', '2021-11-14 14:29:26', '2021-11-15 10:52:37', NULL, NULL);
INSERT INTO `administrator` VALUES (100031, '45678', '女', 'nyw', '2021-11-17 10:59:35', '2021-11-17 10:59:38', NULL, NULL);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名称',
  `place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授课地点',
  `open_period` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程开课时间段',
  `create_time` datetime NOT NULL COMMENT '课程信息创建时间',
  `update_time` datetime NOT NULL COMMENT '课程信息修改时间',
  `teacher_id` int UNSIGNED NOT NULL COMMENT '课程创建人ID(即责任教师）',
  `score_ratio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '成绩得分比例',
  `credit` int NULL DEFAULT NULL COMMENT '课程学分',
  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程所属学院',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60022 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (60001, '离散数学', '广楼G307', ' 星期一5-6节 [1-17]  星期三7-8节 [1-17]', '2021-11-16 23:25:29', '2021-11-16 23:25:29', 300001, '0.4,0.5,0.2', 4, '软件学院');
INSERT INTO `course` VALUES (60002, '计算机组成原理', '济事楼419', '星期三10-11节 [1-17]', '2021-11-17 00:15:13', '2022-01-01 21:19:23', 300003, '0.2,0.4,0.4', 2, '软件学院');
INSERT INTO `course` VALUES (60003, '软件工程课程设计', '安楼A314', '星期一10-11节 [10-17]', '2021-11-17 00:17:53', '2021-11-17 00:17:53', 300004, '0,0,0', 1, '软件学院');
INSERT INTO `course` VALUES (60004, '计算机网络实验', '济事楼330', '星期二1-2节 [1-17]', '2021-11-17 00:19:35', '2021-11-17 00:19:35', 300006, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60005, '数据结构', '广楼G206', '星期一3-4节 [1-17]', '2021-11-17 00:21:28', '2021-11-17 00:21:28', 300009, '0,0,0', 4, '软件学院');
INSERT INTO `course` VALUES (60006, '汇编语言', '济事楼416', '星期三7-8节 [1-17]', '2021-11-17 00:22:41', '2021-11-17 00:22:41', 300010, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60007, '数据结构课程设计', '济事楼430', '星期一10-11节 [2-16双]', '2021-11-17 10:30:49', '2021-11-17 10:30:49', 300009, '0,0,0', 1, '软件学院');
INSERT INTO `course` VALUES (60008, '计算机网络', '安楼A321', '星期一1-2节 [2-16双]', '2021-11-17 10:32:14', '2021-11-17 10:32:14', 300006, '0,0,0', 3, '软件学院');
INSERT INTO `course` VALUES (60009, '云计算技术', '济事楼434', '星期二3-4节 [1-17]', '2021-11-17 10:33:15', '2021-11-17 10:33:15', 300012, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60010, '容错与安全计算', '济事楼434', '星期一5-6节 [1-17]', '2021-11-17 10:34:08', '2021-11-17 10:34:08', 300013, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60011, '地学大数据原理与应用', '北322', '星期一10-11节 [1-17]', '2021-11-17 10:36:11', '2021-11-17 10:36:11', 300012, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60012, 'Java EE程序设计', '济事楼430', '星期五2-4节 [1-17]', '2021-11-17 10:37:02', '2021-11-17 10:37:02', 300014, '0,0,0', 3, '软件学院');
INSERT INTO `course` VALUES (60013, '分布式系统', '博楼B312', '星期二3-4节 [1-17]', '2021-11-17 10:39:02', '2021-11-17 10:39:02', 300015, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60014, 'WEB服务与SOA', '济事楼518', '星期四10-11节 [1-17]', '2021-11-17 10:39:55', '2021-11-17 10:39:55', 300016, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60015, '物联网应用基础', '济事楼416', '星期一5-6节 [2-16双]', '2021-11-17 10:41:07', '2021-11-17 10:41:07', 300007, '0,0,0', 3, '软件学院');
INSERT INTO `course` VALUES (60016, '数字图像处理', '学院专用教室', '星期六5-7节 [3-13]', '2021-11-17 10:42:00', '2021-11-17 10:42:00', 300017, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60017, '计算机图形学', '济事楼426', '星期一5-6节 [1-17]', '2021-11-17 10:46:11', '2021-11-17 10:46:11', 300025, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60018, '语音识别', '博楼B213', '星期四1-2节 [1-17]', '2021-11-17 10:47:45', '2021-11-17 10:47:45', 300020, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60019, '最优化理论', '济事楼426', '星期二3-4节 [1-17]', '2021-11-17 10:48:47', '2021-11-17 10:48:47', 300017, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60020, 'test课程', '20号楼437', 'day1 to day7', '2021-11-19 09:28:53', '2021-11-19 09:28:55', 300026, '0,0,0', 5, '20号楼');
INSERT INTO `course` VALUES (60021, '软件工程', '安楼A404', '星期一1-2节 [1-17]', '2021-12-05 19:06:25', '2021-12-05 19:06:27', 300005, '0,0,0', 3, '软件学院');
INSERT INTO `course` VALUES (60022, '数据仓库', '济事楼430', '星期一5-6节 [1-17]', '2022-01-03 19:40:02', '2022-01-03 19:40:04', 300021, '0,0,0', 2, '软件学院');
INSERT INTO `course` VALUES (60023, '编译原理', '安楼A308', '星期四5-6节 [1-17]', '2022-01-03 20:03:49', '2022-01-03 20:03:52', 300011, '0,0,0', 2, '软件学院');

-- ----------------------------
-- Table structure for course_feedback
-- ----------------------------
DROP TABLE IF EXISTS `course_feedback`;
CREATE TABLE `course_feedback`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程反馈ID',
  `student_id` int UNSIGNED NOT NULL COMMENT '学生ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `course_feedback_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_feedback
-- ----------------------------
INSERT INTO `course_feedback` VALUES (1, 200000, 60020, '课程评价', '老师给分很好，讲课很清楚', '2021-11-19 23:12:56', '2021-11-19 23:12:56');
INSERT INTO `course_feedback` VALUES (3, 200000, 60020, '45612', '老师给分很好，讲课很清楚', '2021-11-19 23:23:44', '2021-11-19 23:23:44');
INSERT INTO `course_feedback` VALUES (4, 200001, 60020, '45611', '老师给分不好，讲课很清楚', '2021-11-19 23:25:57', '2021-11-19 23:26:46');
INSERT INTO `course_feedback` VALUES (10, 200000, 60002, '', '', '2022-01-02 22:04:43', '2022-01-03 18:36:32');
INSERT INTO `course_feedback` VALUES (14, 200000, 60002, '123', '123', '2022-01-03 18:30:34', '2022-01-03 18:30:34');
INSERT INTO `course_feedback` VALUES (15, 200000, 60002, '123', '123', '2022-01-03 18:32:59', '2022-01-03 18:32:59');

-- ----------------------------
-- Table structure for course_file
-- ----------------------------
DROP TABLE IF EXISTS `course_file`;
CREATE TABLE `course_file`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程资料ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程资料名称',
  `create_time` datetime NOT NULL COMMENT '文件创建时间',
  `update_time` datetime NOT NULL COMMENT '文件修改时间',
  `teacher_id` int UNSIGNED NOT NULL COMMENT '上传人ID',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件链接',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `course_file_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_file
-- ----------------------------
INSERT INTO `course_file` VALUES (32, 60020, '全国大学生数学建模竞赛系统使用手册-学生账号.pdf', '2021-11-21 20:50:05', '2021-11-21 20:50:05', 300026, 'http://139.224.65.105:666/course/60020/全国大学生数学建模竞赛系统使用手册-学生账号.pdf');
INSERT INTO `course_file` VALUES (33, 60020, '设计模式举例.pdf', '2021-11-21 20:50:05', '2021-11-21 20:50:05', 300026, 'http://139.224.65.105:666/course/60020/设计模式举例.pdf');
INSERT INTO `course_file` VALUES (34, 60020, '摩尔庄园.pptx', '2021-11-21 21:44:47', '2021-11-21 21:44:47', 300026, 'http://139.224.65.105:666/course/60020/摩尔庄园.pptx');
INSERT INTO `course_file` VALUES (35, 60020, '公告管理模块.png', '2021-11-26 11:39:22', '2021-11-26 11:39:22', 300026, 'http://139.224.65.105:666/course/60020/公告管理模块.png');

-- ----------------------------
-- Table structure for course_notice
-- ----------------------------
DROP TABLE IF EXISTS `course_notice`;
CREATE TABLE `course_notice`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程公告ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公告内容',
  `create_time` datetime NOT NULL COMMENT '公告发布时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '公告修改时间',
  `teacher_id` int UNSIGNED NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `course_notice_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_notice
-- ----------------------------
INSERT INTO `course_notice` VALUES (2, 60020, '47//88', 'testtest', '2021-11-19 09:33:13', '2021-11-19 10:03:42', 300026);
INSERT INTO `course_notice` VALUES (3, 60020, '47//88', 'testtest', '2021-11-19 09:33:59', '2021-11-19 10:03:42', 300026);
INSERT INTO `course_notice` VALUES (4, 60019, '关于印发国务院xxx的通知', '学生为什么要减负呢,后端人下人属于是了', '2021-11-19 09:40:45', '2021-11-19 10:08:49', 300025);

-- ----------------------------
-- Table structure for course_score
-- ----------------------------
DROP TABLE IF EXISTS `course_score`;
CREATE TABLE `course_score`  (
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `student_id` int UNSIGNED NOT NULL COMMENT '学生ID',
  `course_score` decimal(5, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '课程得分',
  `last_attendance_time` datetime NULL DEFAULT NULL COMMENT '上次考勤时间',
  `attendance_score` decimal(5, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '考勤得分',
  `is_active` int NULL DEFAULT NULL COMMENT '是否激活',
  PRIMARY KEY (`course_id`, `student_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_score
-- ----------------------------
INSERT INTO `course_score` VALUES (60002, 200000, 000.00, '2021-12-30 13:21:15', 000.00, 1);
INSERT INTO `course_score` VALUES (60002, 200002, 000.00, '2021-12-30 13:18:00', 000.00, 1);
INSERT INTO `course_score` VALUES (60003, 200000, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60003, 200002, 000.00, '2022-01-03 12:40:01', 000.00, 1);
INSERT INTO `course_score` VALUES (60004, 200001, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60008, 200000, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60008, 200002, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60009, 200000, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60018, 200002, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60019, 200002, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60021, 200000, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60021, 200002, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60021, 200003, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60022, 200000, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60023, 200000, NULL, NULL, NULL, 0);
INSERT INTO `course_score` VALUES (60023, 200002, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for event
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `student_id` int UNSIGNED NOT NULL COMMENT '学生Id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日历内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `do_time` datetime NULL DEFAULT NULL COMMENT '在某一天要做的事',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO `event` VALUES (1, 200000, '期末考试', '2021-12-24 13:54:45', '2021-12-24 13:54:47', '2021-12-25 21:54:49');

-- ----------------------------
-- Table structure for experiment
-- ----------------------------
DROP TABLE IF EXISTS `experiment`;
CREATE TABLE `experiment`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '实验项目ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '实验项目名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `teacher_id` int UNSIGNED NOT NULL COMMENT '创建人ID',
  `experiment_score` decimal(10, 3) NULL DEFAULT NULL COMMENT '实验项目分值',
  `experiment_purpose` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '实验项目目的',
  `experiment_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '实验项目内容',
  `experiment_deadline` datetime NULL DEFAULT NULL COMMENT '实验项目截至时间',
  `experiment_remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '实验项目备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `experiment_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of experiment
-- ----------------------------
INSERT INTO `experiment` VALUES (1, 60020, 'test实验', '2022-01-01 09:17:14', '2022-01-01 09:17:10', 300001, 50.000, '目的是项目概括的声明。目的其实是整个过程的“将要做什么”，换句话说，这个项目将要交付“什么”？项目可能有不止一个目的，但是每个目的都有其对于的交付目标。不要混淆目的和目标。', '1、 进度，计划进度曲线图及实际进度曲线图进行对比，总结出入原因。\n\n2、 风险，按各里程碑阶段出现的风险分类进行分析，如质量、时间、人力、沟通、需求变更。\n\n3、 需求，按个里程碑阶段计划需求数量、实际需求数量柱形图进行分析；描述需求变更给项目带来的影响。\n\n4、 质量，从功能测试阶段、回归测试阶段、验收测试阶段等测试阶段入手，分析各阶段有效BUG数，以及分析BUG分布情况；根据BUG情况分析千行代码缺陷率趋势。\n\n5、 成本，按里程碑阶段，计算各阶段投入的人日，以及计算开发和测试的人员比例，按市场人月价格可估算成本。', '2022-01-01 17:16:36', 'token标签防止重复提交\n\n提交后用模态窗口来提醒 验证');

-- ----------------------------
-- Table structure for experiment_file
-- ----------------------------
DROP TABLE IF EXISTS `experiment_file`;
CREATE TABLE `experiment_file`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '项目资料ID',
  `experiment_id` int UNSIGNED NOT NULL COMMENT '项目ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目资料名称',
  `teacher_id` int UNSIGNED NOT NULL COMMENT '教师ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件链接',
  PRIMARY KEY (`id`, `experiment_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  INDEX `experiment_id`(`experiment_id`) USING BTREE,
  CONSTRAINT `experiment_file_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `experiment_file_ibfk_2` FOREIGN KEY (`experiment_id`) REFERENCES `experiment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of experiment_file
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `create_time` datetime NOT NULL COMMENT '公告创建时间',
  `update_time` datetime NOT NULL COMMENT '公告修改时间',
  `notice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公告标题',
  `notice_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公告内容',
  `administrator_id` int UNSIGNED NULL DEFAULT NULL COMMENT '公告发布人ID（即管理员ID)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `administrator_id`(`administrator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '2021-12-15 16:52:32', '2021-12-15 16:52:35', '系统公告', '公告内容', 100001);
INSERT INTO `notice` VALUES (2, '2021-12-15 16:53:05', '2021-12-15 16:53:09', '系统公告2', '公告内容2', 100006);
INSERT INTO `notice` VALUES (3, '2021-12-15 16:53:51', '2021-12-15 16:53:53', '系统公告3', '公告内容3', 100001);
INSERT INTO `notice` VALUES (4, '2022-01-01 21:19:02', '2022-01-01 21:19:02', '系统公告4', '系统公告内容', 100001);

-- ----------------------------
-- Table structure for practice
-- ----------------------------
DROP TABLE IF EXISTS `practice`;
CREATE TABLE `practice`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `counter_practice_id` int UNSIGNED NOT NULL COMMENT '对抗练习ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `create_time` datetime NOT NULL COMMENT '练习创建时间',
  `update_time` datetime NOT NULL COMMENT '练习修改时间',
  `start_time` datetime NULL DEFAULT NULL COMMENT '练习开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '练习结束时间',
  `teacher_id` int UNSIGNED NOT NULL COMMENT '创建人ID',
  `score` decimal(10, 3) NULL DEFAULT NULL COMMENT '练习分值',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of practice
-- ----------------------------

-- ----------------------------
-- Table structure for practice_problem
-- ----------------------------
DROP TABLE IF EXISTS `practice_problem`;
CREATE TABLE `practice_problem`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `problem_id` int UNSIGNED NOT NULL COMMENT '对抗练习题目ID',
  `counter_practice_auto_id` int UNSIGNED NOT NULL COMMENT '对抗练习ID',
  `stem` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题干',
  `reference_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参考答案',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of practice_problem
-- ----------------------------

-- ----------------------------
-- Table structure for practice_problem_options
-- ----------------------------
DROP TABLE IF EXISTS `practice_problem_options`;
CREATE TABLE `practice_problem_options`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主码',
  `topic_options_id` int UNSIGNED NOT NULL COMMENT '选项ID',
  `problem_auto_id` int UNSIGNED NOT NULL COMMENT '对抗练习题目ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of practice_problem_options
-- ----------------------------

-- ----------------------------
-- Table structure for stu_experiment
-- ----------------------------
DROP TABLE IF EXISTS `stu_experiment`;
CREATE TABLE `stu_experiment`  (
  `student_id` int UNSIGNED NOT NULL COMMENT '学生ID',
  `experiment_id` int UNSIGNED NOT NULL COMMENT '实验项目ID',
  `experiment_score` decimal(10, 3) NULL DEFAULT NULL COMMENT '项目分数',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件url',
  `job_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作业内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`student_id`, `experiment_id`) USING BTREE,
  INDEX `experiment_id`(`experiment_id`) USING BTREE,
  CONSTRAINT `stu_experiment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stu_experiment_ibfk_2` FOREIGN KEY (`experiment_id`) REFERENCES `experiment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_experiment
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `administrator_id` int UNSIGNED NOT NULL COMMENT '管理员ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `gender` char(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `year` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '年级',
  `phone_num` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `grade` decimal(10, 3) NULL DEFAULT NULL COMMENT '总成绩',
  `create_time` datetime NOT NULL COMMENT '账户创建时间',
  `update_time` datetime NOT NULL COMMENT '账户修改时间',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `mailbox` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱账号',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像url',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `administrator_id`(`administrator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (200000, 100001, '宁之恒', '123456', '男', '大三', '18752426528', NULL, '2021-11-16 22:52:39', '2022-01-03 20:01:27', '软件工程', '599152700@qq.com', 'http://139.224.65.105:666/student/200000/头像2.jpg', '同济大学');
INSERT INTO `student` VALUES (200001, 100001, 'nzh', '123456', '男', '', '18007756601', NULL, '2021-11-17 08:26:31', '2021-12-11 21:01:59', '软件工程', NULL, 'http://139.224.65.105:666/student/200001/21.jpg', NULL);
INSERT INTO `student` VALUES (200002, 100001, '曹峰源', '123456', '男', '大三', NULL, NULL, '2021-11-17 09:51:44', '2021-11-17 09:51:47', '软件工程', 'dearcfy@126.com', NULL, NULL);
INSERT INTO `student` VALUES (200003, 100001, '邓泉', '123456', '男', '大三', NULL, NULL, '2021-11-17 09:52:28', '2021-11-17 09:52:30', '软件工程', '1953871@tongji.edu.cn', NULL, NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教师用户id',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名字',
  `gender` char(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `create_time` datetime NOT NULL COMMENT '账户创建时间',
  `update_time` datetime NOT NULL COMMENT '账户更新时间',
  `mailbox` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱账号',
  `administrator_id` int UNSIGNED NOT NULL COMMENT '管理员id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师头像url',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `administrator_id`(`administrator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 300029 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (300001, '123456', '唐剑锋', '男', '2021-11-16 23:24:46', '2021-11-16 23:24:46', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300002, '123456', '史扬', '男', '2021-11-16 23:45:53', '2021-11-16 23:45:53', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300003, '123456', '张晶', '男', '2021-11-16 23:46:33', '2021-11-16 23:46:33', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300004, '123456', '杜庆峰', '男', '2021-11-16 23:46:46', '2021-11-16 23:46:46', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300005, '123456', '黄杰', '男', '2021-11-16 23:46:56', '2021-11-16 23:46:56', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300006, '123456', '金伟祖', '男', '2021-11-16 23:47:07', '2021-11-16 23:47:07', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300007, '123456', '夏波涌', '男', '2021-11-16 23:47:24', '2021-11-16 23:47:24', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300008, '123456', '严海洲', '男', '2021-11-16 23:47:44', '2021-11-16 23:47:44', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300009, '123456', '张颖', '男', '2021-11-16 23:47:53', '2021-11-16 23:47:53', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300010, '123456', '王冬青', '女', '2021-11-16 23:48:06', '2021-11-16 23:48:06', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300011, '123456', '高珍', '女', '2021-11-16 23:48:23', '2021-11-16 23:48:23', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300012, '123456', '李江峰', '男', '2021-11-16 23:48:35', '2021-11-16 23:48:35', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300013, '123456', '江建慧', '男', '2021-11-16 23:48:53', '2021-11-16 23:48:53', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300014, '123456', '范鸿飞', '男', '2021-11-16 23:49:02', '2021-11-16 23:49:02', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300015, '123456', '饶卫雄', '男', '2021-11-16 23:49:17', '2021-11-16 23:49:17', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300016, '123456', '刘岩', '女', '2021-11-16 23:49:27', '2021-11-16 23:49:27', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300017, '123456', '史清江', '男', '2021-11-16 23:49:46', '2021-11-16 23:49:46', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300018, '123456', '贾金源', '男', '2021-11-16 23:50:03', '2021-11-16 23:50:03', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300019, '123456', '陈梁', '男', '2021-11-16 23:50:23', '2021-11-16 23:50:23', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300020, '123456', '沈莹', '女', '2021-11-16 23:50:37', '2021-11-16 23:50:37', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300021, '123456', '朱宏明', '男', '2021-11-16 23:50:55', '2021-11-16 23:50:55', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300022, '123456', '冯巾松', '女', '2021-11-16 23:51:15', '2021-11-16 23:51:15', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300023, '123456', '袁时金', '女', '2021-11-16 23:52:00', '2021-11-16 23:52:00', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300024, '123456', '张惠娟', '女', '2021-11-16 23:52:15', '2021-11-16 23:52:15', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300025, '123456', '贾金原', '男', '2021-11-17 10:44:25', '2021-11-17 10:44:27', NULL, 100001, NULL);
INSERT INTO `teacher` VALUES (300026, '12345', 'nzh', '男', '2021-11-17 11:01:31', '2021-11-19 09:29:34', NULL, 100001, NULL);

-- ----------------------------
-- Table structure for teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `teacher_course`;
CREATE TABLE `teacher_course`  (
  `teacher_id` int UNSIGNED NOT NULL COMMENT '教师ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `character_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`teacher_id`, `course_id`, `character_name`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `teacher_course_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_course
-- ----------------------------
INSERT INTO `teacher_course` VALUES (300001, 60001, '教师');
INSERT INTO `teacher_course` VALUES (300002, 60001, '教师');
INSERT INTO `teacher_course` VALUES (300002, 60001, '责任教师');
INSERT INTO `teacher_course` VALUES (300010, 60002, '教师');
INSERT INTO `teacher_course` VALUES (300024, 60002, '教师');
INSERT INTO `teacher_course` VALUES (300024, 60002, '责任教师');
INSERT INTO `teacher_course` VALUES (300004, 60003, '教师');
INSERT INTO `teacher_course` VALUES (300005, 60003, '教师');
INSERT INTO `teacher_course` VALUES (300005, 60003, '责任教师');
INSERT INTO `teacher_course` VALUES (300006, 60004, '教师');
INSERT INTO `teacher_course` VALUES (300006, 60004, '责任教师');
INSERT INTO `teacher_course` VALUES (300007, 60004, '教师');
INSERT INTO `teacher_course` VALUES (300009, 60005, '教师');
INSERT INTO `teacher_course` VALUES (300009, 60005, '责任教师');
INSERT INTO `teacher_course` VALUES (300010, 60006, '教师');
INSERT INTO `teacher_course` VALUES (300010, 60006, '责任教师');
INSERT INTO `teacher_course` VALUES (300009, 60007, '教师');
INSERT INTO `teacher_course` VALUES (300009, 60007, '责任教师');
INSERT INTO `teacher_course` VALUES (300006, 60008, '教师');
INSERT INTO `teacher_course` VALUES (300006, 60008, '责任教师');
INSERT INTO `teacher_course` VALUES (300007, 60008, '教师');
INSERT INTO `teacher_course` VALUES (300012, 60009, '教师');
INSERT INTO `teacher_course` VALUES (300012, 60009, '责任教师');
INSERT INTO `teacher_course` VALUES (300013, 60010, '教师');
INSERT INTO `teacher_course` VALUES (300013, 60010, '责任教师');
INSERT INTO `teacher_course` VALUES (300012, 60011, '教师');
INSERT INTO `teacher_course` VALUES (300012, 60011, '责任教师');
INSERT INTO `teacher_course` VALUES (300014, 60012, '教师');
INSERT INTO `teacher_course` VALUES (300014, 60012, '责任教师');
INSERT INTO `teacher_course` VALUES (300015, 60013, '教师');
INSERT INTO `teacher_course` VALUES (300015, 60013, '责任教师');
INSERT INTO `teacher_course` VALUES (300016, 60014, '教师');
INSERT INTO `teacher_course` VALUES (300016, 60014, '责任教师');
INSERT INTO `teacher_course` VALUES (300007, 60015, '教师');
INSERT INTO `teacher_course` VALUES (300007, 60015, '责任教师');
INSERT INTO `teacher_course` VALUES (300025, 60017, '教师');
INSERT INTO `teacher_course` VALUES (300025, 60017, '责任教师');
INSERT INTO `teacher_course` VALUES (300020, 60018, '教师');
INSERT INTO `teacher_course` VALUES (300020, 60018, '责任教师');
INSERT INTO `teacher_course` VALUES (300017, 60019, '教师');
INSERT INTO `teacher_course` VALUES (300017, 60019, '责任教师');
INSERT INTO `teacher_course` VALUES (300004, 60021, '教师');
INSERT INTO `teacher_course` VALUES (300004, 60021, '责任教师');
INSERT INTO `teacher_course` VALUES (300005, 60021, '教师');
INSERT INTO `teacher_course` VALUES (300021, 60022, '教师');
INSERT INTO `teacher_course` VALUES (300021, 60022, '责任教师');
INSERT INTO `teacher_course` VALUES (300011, 60023, '教师');
INSERT INTO `teacher_course` VALUES (300011, 60023, '责任教师');

SET FOREIGN_KEY_CHECKS = 1;
