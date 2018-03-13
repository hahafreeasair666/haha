package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.entity.Phone;
import com.ch999.haha.admin.mapper.PhoneMapper;
import com.ch999.haha.admin.service.PhoneService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-13
 */
@Service
public class PhoneServiceImpl extends ServiceImpl<PhoneMapper, Phone> implements PhoneService {

    @Override
    public Boolean checkPhoneNum(String mobile) {
        Wrapper<Phone> wrapper = new EntityWrapper<>();
        wrapper.eq("phone_num",mobile);
        Integer count = this.selectCount(wrapper);
        if(count != 0){
            return false;
        }else {
            Phone phone = new Phone(mobile);
            this.insert(phone);
            return true;
        }
    }
}
