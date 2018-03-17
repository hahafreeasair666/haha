package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.entity.Adoption;
import com.ch999.haha.admin.mapper.AdoptionMapper;
import com.ch999.haha.admin.service.AdoptionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch999.haha.admin.vo.NeedSendAdoptionFeedBackVO;
import com.ch999.haha.admin.vo.mappervo.AdoptionSuccessNewsVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2018-03-14
 */
@Service
public class AdoptionServiceImpl extends ServiceImpl<AdoptionMapper, Adoption> implements AdoptionService {

    /**
     * 成功收养一个宠物需要发布的反馈公告的总次数
     */
    private static final Integer TIMES = 4;

    @Resource
    private AdoptionMapper adoptionMapper;

    @Override
    public List<NeedSendAdoptionFeedBackVO> getNeedSendAdoptionFeedBack(Integer userId) {
        Wrapper<Adoption> wrapper = new EntityWrapper<>();
        wrapper.eq("userid", userId);
        List<Adoption> adoptions = this.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(adoptions)) {
            List<NeedSendAdoptionFeedBackVO> list = new ArrayList<>();
            //初始化个公告需要发送的次数
            adoptions.forEach(li -> list.add(new NeedSendAdoptionFeedBackVO(li.getAdoptionId(), li.getNewsTitle(), TIMES)));
            //按pid分组
            Map<Integer, List<AdoptionSuccessNewsVO>> collect = adoptionMapper.selectHadSendNews(userId).stream()
                    .collect(Collectors.groupingBy(AdoptionSuccessNewsVO::getPid));
            collect.entrySet().forEach(li -> {
                //假设每次都没完成
                List<Integer> collect1 = IntStream.of(0, 0, 0, 0).boxed().collect(Collectors.toList());
                List<AdoptionSuccessNewsVO> value = li.getValue();
                //检查完成情况，
                for (int i = 0; i < value.size(); i++) {
                    //满足第一次反馈情况
                    if (checkTimeIsOnTime(value.get(i).getTime1(),value.get(i).getCreateTime()) ||
                            (checkTimeIsOnTime(value.get(i).getCreateTime(),value.get(i).getTime1()) && collect1.get(0) !=1)) {
                        collect1.set(0, 1);
                        //满足第二次反馈情况
                    }else if (checkTimeIsOnTime(value.get(i).getTime2(),value.get(i).getCreateTime()) ||
                            (checkTimeIsOnTime(value.get(i).getCreateTime(),value.get(i).getTime2()) && collect1.get(1) !=1 )) {
                        collect1.set(1, 1);
                        //满足第三次反馈情况
                    }else if (checkTimeIsOnTime(value.get(i).getTime3(),value.get(i).getCreateTime()) ||
                            ((value.get(i).getCreateTime().getTime() - value.get(i).getTime3().getTime() > 0) && collect1.get(2) !=1 )){
                        collect1.set(2, 1);
                    }
                    //满足最后一次反馈情况
                    if (i == value.size()) {
                        if (collect1.stream().filter(vo -> vo == 0).count() == 1 && (value.size() > 4)) {
                            collect1.set(3, 1);
                        }
                    }
                }
                list.forEach(ls -> {
                    if (ls.getId() == li.getKey()) {
                        ls.setTimes((int) collect1.stream().filter(ti -> ti == 0).count());
                    }
                });
            });
            return list;
        }
        return null;
    }

    private Boolean checkTimeIsOnTime(Date time1, Date time2){
        long l = time1.getTime() - time2.getTime();
        return l < 604800000 &&  l > 0;
    }
}
