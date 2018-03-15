package com.ch999.haha.admin.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.AdoptionRequest;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ch999.haha.admin.vo.AdoptionRequestVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-15
 */
public interface AdoptionRequestMapper extends BaseMapper<AdoptionRequest> {

    List<AdoptionRequestVO> selectAdoptionRequestList(Page<AdoptionRequestVO> page,@Param("userId") Integer userId);
}
