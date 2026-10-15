package cn.iocoder.yudao.module.edu.controller.admin.course.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 课程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CourseRespVO {

    @Schema(description = "课程编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("课程编号")
    private Long id;
    @Schema(description = "课程编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("课程编码")
    private String courseCode;
    @Schema(description = "课程名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("课程名称")
    private String courseName;
    @Schema(description = "开课院系编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开课院系编号")
    private Long deptId;
    @Schema(description = "专业编号")
    @ExcelProperty("专业编号")
    private Long majorId;
    @Schema(description = "课程类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("课程类型")
    private Integer courseType;
    @Schema(description = "考核方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("考核方式")
    private Integer assessmentType;
    @Schema(description = "学分", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("学分")
    private BigDecimal credit;
    @Schema(description = "总学时", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总学时")
    private Integer totalHours;
    @Schema(description = "理论学时", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("理论学时")
    private Integer theoryHours;
    @Schema(description = "实践学时", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("实践学时")
    private Integer practiceHours;
    @Schema(description = "课程简介")
    private String description;
    @Schema(description = "课表显示颜色")
    private String displayColor;
    @Schema(description = "课程大纲文件编号")
    private List<Long> syllabusFileIds;
    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("状态")
    private Integer status;
    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
