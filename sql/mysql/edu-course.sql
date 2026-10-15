-- 学院管理课程基础表
-- 适用 MySQL 8.0+；升级已有环境时请使用独立迁移脚本。

CREATE TABLE IF NOT EXISTS `edu_course` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '课程编号',
  `course_code` varchar(32) NOT NULL COMMENT '课程编码',
  `course_name` varchar(100) NOT NULL COMMENT '课程名称',
  `dept_id` bigint NOT NULL COMMENT '开课院系编号，关联 system_dept.id',
  `major_id` bigint DEFAULT NULL COMMENT '专业编号；公共课可为空',
  `course_type` tinyint NOT NULL COMMENT '课程类型，字典 edu_course_type',
  `assessment_type` tinyint NOT NULL DEFAULT 1 COMMENT '考核方式，字典 edu_course_assessment_type',
  `credit` decimal(4,1) NOT NULL COMMENT '学分',
  `total_hours` smallint NOT NULL COMMENT '总学时',
  `theory_hours` smallint NOT NULL DEFAULT 0 COMMENT '理论学时',
  `practice_hours` smallint NOT NULL DEFAULT 0 COMMENT '实践学时',
  `description` varchar(1000) DEFAULT NULL COMMENT '课程简介',
  `display_color` varchar(20) DEFAULT NULL COMMENT '课表显示颜色',
  `syllabus_file_ids` varchar(1000) DEFAULT NULL COMMENT '课程大纲文件编号，逗号分隔',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0 启用，1 停用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`),
  KEY `idx_edu_course_tenant_code` (`tenant_id`, `course_code`, `deleted`),
  KEY `idx_edu_course_dept_type` (`tenant_id`, `dept_id`, `course_type`, `status`, `deleted`),
  KEY `idx_edu_course_major` (`tenant_id`, `major_id`, `deleted`),
  CONSTRAINT `chk_edu_course_credit` CHECK (`credit` > 0),
  CONSTRAINT `chk_edu_course_hours` CHECK (`total_hours` > 0 AND `theory_hours` >= 0
    AND `practice_hours` >= 0 AND `theory_hours` + `practice_hours` <= `total_hours`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程基础信息表';

-- 课程管理使用的字典。通过 type/value 判重，可以重复执行本脚本。
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`)
SELECT '课程类型', 'edu_course_type', 0, '课程基础信息的课程类型', 'system'
WHERE NOT EXISTS (
  SELECT 1 FROM `system_dict_type` WHERE `type` = 'edu_course_type' AND `deleted` = b'0'
);

INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`)
SELECT '课程考核方式', 'edu_course_assessment_type', 0, '课程基础信息的考核方式', 'system'
WHERE NOT EXISTS (
  SELECT 1 FROM `system_dict_type` WHERE `type` = 'edu_course_assessment_type' AND `deleted` = b'0'
);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`)
SELECT d.sort, d.label, d.value, d.dict_type, 0, d.color_type, 'system'
FROM (
  SELECT 1 sort, '公共必修' label, '1' value, 'edu_course_type' dict_type, 'danger' color_type
  UNION ALL SELECT 2, '专业必修', '2', 'edu_course_type', 'warning'
  UNION ALL SELECT 3, '专业选修', '3', 'edu_course_type', 'success'
  UNION ALL SELECT 4, '公共选修', '4', 'edu_course_type', 'primary'
  UNION ALL SELECT 5, '实践课', '5', 'edu_course_type', 'info'
  UNION ALL SELECT 1, '考试', '1', 'edu_course_assessment_type', 'danger'
  UNION ALL SELECT 2, '考查', '2', 'edu_course_assessment_type', 'primary'
  UNION ALL SELECT 3, '论文', '3', 'edu_course_assessment_type', 'warning'
  UNION ALL SELECT 4, '实践', '4', 'edu_course_assessment_type', 'success'
) d
WHERE NOT EXISTS (
  SELECT 1 FROM `system_dict_data` old
  WHERE old.`dict_type` = d.dict_type AND old.`value` = d.value AND old.`deleted` = b'0'
);

-- 学院管理目录与课程菜单。
INSERT INTO `system_menu`
  (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`,
   `status`, `visible`, `keep_alive`, `always_show`, `creator`)
SELECT '学院管理', '', 1, 30, 0, '/edu', 'ep:school', NULL, NULL,
       0, b'1', b'1', b'1', 'system'
WHERE NOT EXISTS (
  SELECT 1 FROM `system_menu` WHERE `parent_id` = 0 AND `path` = '/edu' AND `deleted` = b'0'
);

INSERT INTO `system_menu`
  (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`,
   `status`, `visible`, `keep_alive`, `always_show`, `creator`)
SELECT '课程管理', 'edu:course:query', 2, 10, root.id, 'course', 'ep:reading',
       'edu/course/index', 'EduCourse', 0, b'1', b'1', b'1', 'system'
FROM `system_menu` root
WHERE root.`parent_id` = 0 AND root.`path` = '/edu' AND root.`deleted` = b'0'
  AND NOT EXISTS (
    SELECT 1 FROM `system_menu` child
    WHERE child.`parent_id` = root.id AND child.`path` = 'course' AND child.`deleted` = b'0'
  );

INSERT INTO `system_menu`
  (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`,
   `status`, `visible`, `keep_alive`, `always_show`, `creator`)
SELECT p.name, p.permission, 3, p.sort, course_menu.id, '', '', NULL, NULL,
       0, b'1', b'1', b'1', 'system'
FROM (
  SELECT '课程查询' name, 'edu:course:query' permission, 1 sort
  UNION ALL SELECT '课程新增', 'edu:course:create', 2
  UNION ALL SELECT '课程修改', 'edu:course:update', 3
  UNION ALL SELECT '课程删除', 'edu:course:delete', 4
  UNION ALL SELECT '课程导出', 'edu:course:export', 5
) p
JOIN `system_menu` course_menu
  ON course_menu.`component` = 'edu/course/index' AND course_menu.`deleted` = b'0'
WHERE NOT EXISTS (
  SELECT 1 FROM `system_menu` old
  WHERE old.`permission` = p.permission AND old.`deleted` = b'0'
);
