package com.ch999.haha.admin.service;

import com.ch999.haha.admin.entity.Phone;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-13
 */
public interface PhoneService extends IService<Phone> {

    Boolean checkPhoneNum(String mobile);

}
