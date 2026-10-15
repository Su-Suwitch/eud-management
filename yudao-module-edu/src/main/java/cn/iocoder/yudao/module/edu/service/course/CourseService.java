package cn.iocoder.yudao.module.edu.service.course;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CoursePageReqVO;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CourseSaveReqVO;
import cn.iocoder.yudao.module.edu.dal.dataobject.course.CourseDO;

import javax.validation.Valid;
import java.util.List;

public interface CourseService {

    Long createCourse(@Valid CourseSaveReqVO createReqVO);

    void updateCourse(@Valid CourseSaveReqVO updateReqVO);

    void deleteCourse(Long id);

    void deleteCourseList(List<Long> ids);

    CourseDO getCourse(Long id);

    PageResult<CourseDO> getCoursePage(CoursePageReqVO pageReqVO);

}
