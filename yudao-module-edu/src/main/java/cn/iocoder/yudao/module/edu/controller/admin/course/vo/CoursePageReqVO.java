package cn.iocoder.yudao.module.edu.controller.admin.course.vo;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 课程分页 Request VO")
@Data
public class CoursePageReqVO extends PageParam {

    @Schema(description = "课程编码，模糊匹配", example = "CS")
    private String courseCode;

    @Schema(description = "课程名称，模糊匹配", example = "数据结构")
    private String courseName;

    @Schema(description = "开课院系编号", example = "100")
    private Long deptId;

    @Schema(description = "专业编号", example = "200")
    private Long majorId;

    @Schema(description = "课程类型", example = "1")
    private Integer courseType;

    @Schema(description = "考核方式", example = "1")
    private Integer assessmentType;

    @Schema(description = "状态", example = "0")
    @InEnum(value = CommonStatusEnum.class, message = "状态必须是 {value}")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
