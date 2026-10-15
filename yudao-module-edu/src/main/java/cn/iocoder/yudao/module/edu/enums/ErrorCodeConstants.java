package cn.iocoder.yudao.module.edu.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * 学院管理错误码常量。
 *
 * edu 模块使用 1-060-000-000 段。
 */
public interface ErrorCodeConstants {

    ErrorCode COURSE_NOT_EXISTS = new ErrorCode(1_060_000_000, "课程不存在");
    ErrorCode COURSE_CODE_DUPLICATE = new ErrorCode(1_060_000_001, "课程编码已存在");
    ErrorCode COURSE_HOURS_INVALID = new ErrorCode(1_060_000_002,
            "理论学时与实践学时之和不能超过总学时");

}
