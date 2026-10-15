package cn.iocoder.yudao.module.edu.service.course;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CoursePageReqVO;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CourseSaveReqVO;
import cn.iocoder.yudao.module.edu.dal.dataobject.course.CourseDO;
import cn.iocoder.yudao.module.edu.dal.mysql.course.CourseMapper;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.edu.enums.ErrorCodeConstants.COURSE_CODE_DUPLICATE;
import static cn.iocoder.yudao.module.edu.enums.ErrorCodeConstants.COURSE_HOURS_INVALID;
import static cn.iocoder.yudao.module.edu.enums.ErrorCodeConstants.COURSE_NOT_EXISTS;

@Service
@Validated
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private DeptApi deptApi;

    @Override
    public Long createCourse(CourseSaveReqVO createReqVO) {
        validateCourseCodeUnique(null, createReqVO.getCourseCode());
        validateCourse(createReqVO);
        CourseDO course = BeanUtils.toBean(createReqVO, CourseDO.class);
        courseMapper.insert(course);
        return course.getId();
    }

    @Override
    public void updateCourse(CourseSaveReqVO updateReqVO) {
        validateCourseExists(updateReqVO.getId());
        validateCourseCodeUnique(updateReqVO.getId(), updateReqVO.getCourseCode());
        validateCourse(updateReqVO);
        courseMapper.updateById(BeanUtils.toBean(updateReqVO, CourseDO.class));
    }

    @Override
    public void deleteCourse(Long id) {
        validateCourseExists(id);
        courseMapper.deleteById(id);
    }

    @Override
    public void deleteCourseList(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        List<CourseDO> courses = courseMapper.selectByIds(ids);
        if (courses.size() != ids.size()) {
            throw exception(COURSE_NOT_EXISTS);
        }
        courseMapper.deleteByIds(ids);
    }

    @Override
    public CourseDO getCourse(Long id) {
        return courseMapper.selectById(id);
    }

    @Override
    public PageResult<CourseDO> getCoursePage(CoursePageReqVO pageReqVO) {
        return courseMapper.selectPage(pageReqVO);
    }

    private void validateCourse(CourseSaveReqVO reqVO) {
        deptApi.validateDeptList(Collections.singleton(reqVO.getDeptId()));
        if (reqVO.getTheoryHours() + reqVO.getPracticeHours() > reqVO.getTotalHours()) {
            throw exception(COURSE_HOURS_INVALID);
        }
    }

    @VisibleForTesting
    void validateCourseExists(Long id) {
        if (id == null || courseMapper.selectById(id) == null) {
            throw exception(COURSE_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    void validateCourseCodeUnique(Long id, String courseCode) {
        CourseDO course = courseMapper.selectByCourseCode(courseCode);
        if (course != null && !course.getId().equals(id)) {
            throw exception(COURSE_CODE_DUPLICATE);
        }
    }

}
