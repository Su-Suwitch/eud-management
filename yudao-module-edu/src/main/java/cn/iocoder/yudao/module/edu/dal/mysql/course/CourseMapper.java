package cn.iocoder.yudao.module.edu.dal.mysql.course;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.edu.controller.admin.course.vo.CoursePageReqVO;
import cn.iocoder.yudao.module.edu.dal.dataobject.course.CourseDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapperX<CourseDO> {

    default CourseDO selectByCourseCode(String courseCode) {
        return selectOne(CourseDO::getCourseCode, courseCode);
    }

    default PageResult<CourseDO> selectPage(CoursePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CourseDO>()
                .likeIfPresent(CourseDO::getCourseCode, reqVO.getCourseCode())
                .likeIfPresent(CourseDO::getCourseName, reqVO.getCourseName())
                .eqIfPresent(CourseDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(CourseDO::getMajorId, reqVO.getMajorId())
                .eqIfPresent(CourseDO::getCourseType, reqVO.getCourseType())
                .eqIfPresent(CourseDO::getAssessmentType, reqVO.getAssessmentType())
                .eqIfPresent(CourseDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CourseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CourseDO::getId));
    }

}
