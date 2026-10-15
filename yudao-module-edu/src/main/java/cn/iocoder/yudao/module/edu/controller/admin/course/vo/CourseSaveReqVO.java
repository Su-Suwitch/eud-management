package cn.iocoder.yudao.module.edu.controller.admin.course.vo;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "管理后台 - 课程创建或修改 Request VO")
@Data
public class CourseSaveReqVO {

    @Schema(description = "课程编号", example = "1024")
    private Long id;

    @Schema(description = "课程编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "CS101")
    @NotBlank(message = "课程编码不能为空")
    @Size(max = 32, message = "课程编码不能超过 32 个字符")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "课程编码只能包含字母、数字、下划线和短横线")
    private String courseCode;

    @Schema(description = "课程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "数据结构")
    @NotBlank(message = "课程名称不能为空")
    @Size(max = 100, message = "课程名称不能超过 100 个字符")
    private String courseName;

    @Schema(description = "开课院系编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "开课院系不能为空")
    private Long deptId;

    @Schema(description = "所属专业编号；公共课可为空", example = "200")
    private Long majorId;

    @Schema(description = "课程类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "课程类型不能为空")
    private Integer courseType;

    @Schema(description = "考核方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "考核方式不能为空")
    private Integer assessmentType;

    @Schema(description = "学分", requiredMode = Schema.RequiredMode.REQUIRED, example = "3.0")
    @NotNull(message = "学分不能为空")
    @DecimalMin(value = "0.1", message = "学分必须大于 0")
    private BigDecimal credit;

    @Schema(description = "总学时", requiredMode = Schema.RequiredMode.REQUIRED, example = "48")
    @NotNull(message = "总学时不能为空")
    @Min(value = 1, message = "总学时必须大于 0")
    private Integer totalHours;

    @Schema(description = "理论学时", requiredMode = Schema.RequiredMode.REQUIRED, example = "32")
    @NotNull(message = "理论学时不能为空")
    @Min(value = 0, message = "理论学时不能小于 0")
    private Integer theoryHours;

    @Schema(description = "实践学时", requiredMode = Schema.RequiredMode.REQUIRED, example = "16")
    @NotNull(message = "实践学时不能为空")
    @Min(value = 0, message = "实践学时不能小于 0")
    private Integer practiceHours;

    @Schema(description = "课程简介")
    @Size(max = 1000, message = "课程简介不能超过 1000 个字符")
    private String description;

    @Schema(description = "课表显示颜色", example = "#409EFF")
    @Pattern(regexp = "^$|^#[0-9A-Fa-f]{6}$", message = "显示颜色必须使用 #RRGGBB 格式")
    private String displayColor;

    @Schema(description = "课程大纲文件编号")
    private List<Long> syllabusFileIds;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "状态不能为空")
    @InEnum(value = CommonStatusEnum.class, message = "状态必须是 {value}")
    private Integer status;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注不能超过 500 个字符")
    private String remark;

}
