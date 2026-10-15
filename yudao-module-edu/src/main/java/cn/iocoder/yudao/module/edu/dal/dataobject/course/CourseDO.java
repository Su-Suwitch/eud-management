package cn.iocoder.yudao.module.edu.dal.dataobject.course;

import cn.iocoder.yudao.framework.mybatis.core.type.LongListTypeHandler;
import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 课程基础信息。
 */
@TableName(value = "edu_course", autoResultMap = true)
@KeySequence("edu_course_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String courseCode;
    private String courseName;
    private Long deptId;
    private Long majorId;
    private Integer courseType;
    private Integer assessmentType;
    private BigDecimal credit;
    private Integer totalHours;
    private Integer theoryHours;
    private Integer practiceHours;
    private String description;
    private String displayColor;
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> syllabusFileIds;
    private Integer status;
    private String remark;

}
