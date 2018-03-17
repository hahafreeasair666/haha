package com.ch999.haha.admin.mapper;

import com.ch999.haha.admin.entity.Adoption;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ch999.haha.admin.vo.mappervo.AdoptionSuccessNewsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-14
 */
public interface AdoptionMapper extends BaseMapper<Adoption> {

    List<AdoptionSuccessNewsVO> selectHadSendNews(@Param("userId") Integer userId);
}
