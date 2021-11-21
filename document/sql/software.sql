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

 Date: 21/11/2021 22:02:15
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
) ENGINE = InnoDB AUTO_INCREMENT = 100000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for counter_practice
-- ----------------------------
DROP TABLE IF EXISTS `counter_practice`;
CREATE TABLE `counter_practice`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对抗练习ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `create_time` datetime NOT NULL COMMENT '练习创建时间',
  `update_time` datetime NOT NULL COMMENT '练习修改时间',
  `start_time` datetime NULL DEFAULT NULL COMMENT '练习开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '练习结束时间',
  `teacher_id` int UNSIGNED NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`, `course_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `counter_practice_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  `credit` int NULL DEFAULT NULL COMMENT '课程学分',
  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程所属学院',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`id`, `student_id`, `course_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `course_feedback_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `course_feedback_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`id`, `course_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `course_file_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `course_file_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`id`, `course_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `course_notice_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `course_notice_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_score
-- ----------------------------
DROP TABLE IF EXISTS `course_score`;
CREATE TABLE `course_score`  (
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `student_id` int UNSIGNED NOT NULL COMMENT '学生ID',
  `course_score` decimal(10, 3) NULL DEFAULT NULL COMMENT '课程得分',
  `this_attendance_time` datetime NULL DEFAULT NULL COMMENT '这次考勤时间',
  `last_attendance_time` datetime NULL DEFAULT NULL COMMENT '上次考勤时间',
  `attendance_score` decimal(10, 3) NULL DEFAULT NULL COMMENT '考勤得分',
  `is_active` int NULL DEFAULT NULL COMMENT '是否激活',
  PRIMARY KEY (`course_id`, `student_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`id`, `course_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `experiment_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `experiment_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对抗练习题目ID',
  `counter_practice_id` int UNSIGNED NOT NULL COMMENT '对抗练习ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `stem` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题干',
  `reference_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参考答案',
  PRIMARY KEY (`id`, `counter_practice_id`, `course_id`) USING BTREE,
  INDEX `counter_practice_id`(`counter_practice_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `problem_ibfk_2`(`course_id`) USING BTREE,
  CONSTRAINT `problem_ibfk_1` FOREIGN KEY (`counter_practice_id`) REFERENCES `counter_practice` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `problem_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `counter_practice` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int UNSIGNED NOT NULL COMMENT '角色ID',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stu_experiment
-- ----------------------------
DROP TABLE IF EXISTS `stu_experiment`;
CREATE TABLE `stu_experiment`  (
  `student_id` int UNSIGNED NOT NULL COMMENT '学生ID',
  `experiment_id` int UNSIGNED NOT NULL COMMENT '实验项目ID',
  `experiment_score` decimal(10, 3) NULL DEFAULT NULL COMMENT '项目分数',
  `file_id` int UNSIGNED NULL DEFAULT NULL COMMENT '文件id',
  `job_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作业内容',
  PRIMARY KEY (`student_id`, `experiment_id`) USING BTREE,
  INDEX `experiment_id`(`experiment_id`) USING BTREE,
  CONSTRAINT `stu_experiment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stu_experiment_ibfk_2` FOREIGN KEY (`experiment_id`) REFERENCES `experiment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `administrator_id`(`administrator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_upload_file
-- ----------------------------
DROP TABLE IF EXISTS `student_upload_file`;
CREATE TABLE `student_upload_file`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生上传文件ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名字',
  `create_time` datetime NOT NULL COMMENT '文件上传时间',
  `update_time` datetime NOT NULL COMMENT '文件修改时间',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 300000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `teacher_course`;
CREATE TABLE `teacher_course`  (
  `teacher_id` int UNSIGNED NOT NULL COMMENT '教师ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `role_id` int UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`teacher_id`, `course_id`, `role_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `teacher_course_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teacher_course_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teacher_course_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for topic_options
-- ----------------------------
DROP TABLE IF EXISTS `topic_options`;
CREATE TABLE `topic_options`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '选项ID',
  `problem_id` int UNSIGNED NOT NULL COMMENT '题目ID',
  `counter_practice_id` int UNSIGNED NOT NULL COMMENT '对抗练习ID',
  `course_id` int UNSIGNED NOT NULL COMMENT '课程ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目内容',
  PRIMARY KEY (`id`, `problem_id`, `counter_practice_id`, `course_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `topic_options_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `problem` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
