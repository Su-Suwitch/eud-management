package cn.iocoder.yudao.module.edu.service.course;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CourseSaveReqVO;
import cn.iocoder.yudao.module.edu.dal.dataobject.course.CourseDO;
import cn.iocoder.yudao.module.edu.dal.mysql.course.CourseMapper;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.module.edu.enums.ErrorCodeConstants.COURSE_CODE_DUPLICATE;
import static cn.iocoder.yudao.module.edu.enums.ErrorCodeConstants.COURSE_HOURS_INVALID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private DeptApi deptApi;

    private CourseSaveReqVO reqVO;

    @BeforeEach
    void setUp() {
        reqVO = new CourseSaveReqVO();
        reqVO.setCourseCode("CS101");
        reqVO.setCourseName("数据结构");
        reqVO.setDeptId(100L);
        reqVO.setCourseType(2);
        reqVO.setAssessmentType(1);
        reqVO.setCredit(new BigDecimal("3.0"));
        reqVO.setTotalHours(48);
        reqVO.setTheoryHours(32);
        reqVO.setPracticeHours(16);
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
    }

    @Test
    void createCourse_rejectsDuplicateCode() {
        CourseDO existing = new CourseDO();
        existing.setId(1L);
        existing.setCourseCode(reqVO.getCourseCode());
        when(courseMapper.selectByCourseCode(reqVO.getCourseCode())).thenReturn(existing);

        assertServiceException(() -> courseService.createCourse(reqVO), COURSE_CODE_DUPLICATE);

        verify(courseMapper, never()).insert(any(CourseDO.class));
    }

    @Test
    void createCourse_rejectsHoursOverTotal() {
        reqVO.setPracticeHours(17);

        assertServiceException(() -> courseService.createCourse(reqVO), COURSE_HOURS_INVALID);

        verify(courseMapper, never()).insert(any(CourseDO.class));
    }

    @Test
    void updateCourse_allowsKeepingOwnCode() {
        reqVO.setId(1L);
        CourseDO existing = new CourseDO();
        existing.setId(1L);
        existing.setCourseCode(reqVO.getCourseCode());
        when(courseMapper.selectById(1L)).thenReturn(existing);
        when(courseMapper.selectByCourseCode(reqVO.getCourseCode())).thenReturn(existing);

        courseService.updateCourse(reqVO);

        verify(courseMapper).updateById(any(CourseDO.class));
    }

}
