package cn.iocoder.yudao.module.edu.controller.admin.course;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CoursePageReqVO;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CourseRespVO;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CourseSaveReqVO;
import cn.iocoder.yudao.module.edu.dal.dataobject.course.CourseDO;
import cn.iocoder.yudao.module.edu.service.course.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 课程管理")
@RestController
@RequestMapping("/edu/course")
@Validated
public class CourseController {

    @Resource
    private CourseService courseService;

    @PostMapping("/create")
    @Operation(summary = "创建课程")
    @PreAuthorize("@ss.hasPermission('edu:course:create')")
    public CommonResult<Long> createCourse(@Valid @RequestBody CourseSaveReqVO createReqVO) {
        return success(courseService.createCourse(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "修改课程")
    @PreAuthorize("@ss.hasPermission('edu:course:update')")
    public CommonResult<Boolean> updateCourse(@Valid @RequestBody CourseSaveReqVO updateReqVO) {
        courseService.updateCourse(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除课程")
    @Parameter(name = "id", description = "课程编号", required = true)
    @PreAuthorize("@ss.hasPermission('edu:course:delete')")
    public CommonResult<Boolean> deleteCourse(@RequestParam("id") Long id) {
        courseService.deleteCourse(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除课程")
    @Parameter(name = "ids", description = "课程编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('edu:course:delete')")
    public CommonResult<Boolean> deleteCourseList(@RequestParam("ids") List<Long> ids) {
        courseService.deleteCourseList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得课程")
    @Parameter(name = "id", description = "课程编号", required = true)
    @PreAuthorize("@ss.hasPermission('edu:course:query')")
    public CommonResult<CourseRespVO> getCourse(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(courseService.getCourse(id), CourseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得课程分页")
    @PreAuthorize("@ss.hasPermission('edu:course:query')")
    public CommonResult<PageResult<CourseRespVO>> getCoursePage(@Valid CoursePageReqVO pageReqVO) {
        return success(BeanUtils.toBean(courseService.getCoursePage(pageReqVO), CourseRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出课程 Excel")
    @PreAuthorize("@ss.hasPermission('edu:course:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCourseExcel(@Valid CoursePageReqVO pageReqVO, HttpServletResponse response)
            throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CourseDO> list = courseService.getCoursePage(pageReqVO).getList();
        ExcelUtils.write(response, "课程数据.xls", "课程", CourseRespVO.class,
                BeanUtils.toBean(list, CourseRespVO.class));
    }

}
