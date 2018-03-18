package com.ch999.haha.admin.component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.entity.Adoption;
import com.ch999.haha.admin.entity.AdoptionFeedBack;
import com.ch999.haha.admin.entity.News;
import com.ch999.haha.admin.mapper.AdoptionMapper;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import com.ch999.haha.admin.service.AdoptionFeedBackService;
import com.ch999.haha.admin.service.AdoptionService;
import com.ch999.haha.admin.service.NewsService;
import com.ch999.haha.admin.vo.mappervo.AdoptionSuccessNewsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户信用分管理控制类
 * @author hahalala
 */
@Component
@Slf4j
public class CreditComponent {

    @Resource
    private AdoptionFeedBackService adoptionFeedBackService;

    @Resource
    private UserInfoBORepository userInfoBORepository;

    @Resource
    private AdoptionService adoptionService;

    @Resource
    private AdoptionMapper adoptionMapper;

    @Resource
    private NewsService newsService;


    /**
     * 对用户信用积分进行增减
     * @param userId
     * @param number
     * @param isIncrease
     */
    private void updateUserCredit(Integer userId,Integer number,Boolean isIncrease){
        UserInfoBO one = userInfoBORepository.findOne(userId);
        Integer creditNum;
        if(isIncrease){
             creditNum = (int)one.getCreditInfo().get("creditNum") + number;
        }else {
             creditNum = (int)one.getCreditInfo().get("creditNum") - number;
        }
        one.getCreditInfo().put("creditNum",creditNum);
        one.getCreditInfo().put("updateTime",new Date());
        userInfoBORepository.save(one);
    }

    /*@Scheduled(fixedRate = 5000*1)
    public void testTimedTask(){
        System.out.println("哈哈自在如风");
    }*/

    /**
     * 对反馈公告的赞进行每日赞数量检测进行发布者信用积分的修改
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void timedTaskOfZan(){
        try{
            //对收养反馈公告的赞数量进行发布者信用积分改变
            Wrapper<AdoptionFeedBack> wrapper = new EntityWrapper<>();
            adoptionFeedBackService.selectList(wrapper).forEach(li->{
                News news = newsService.selectById(li.getNewsId());
                if(news.getZan() > li.getLastTimeZan()){
                    updateUserCredit(news.getCreateUserId(),getNumber(news.getZan() - li.getLastTimeZan()),true);
                    li.setLastTimeZan(news.getZan());
                    adoptionFeedBackService.updateById(li);
                }
            });
            //对成功收养的反馈公告进行检查对信用积分改变
            Wrapper<Adoption> wrapper1 = new EntityWrapper<>();
            wrapper1.eq("isadoption",1);
            adoptionService.selectList(wrapper1).forEach(li->{
                AdoptionSuccessNewsVO adoptionSuccessNewsVO = adoptionMapper.checkUserIsSendNews(li.getId());
                //第一次
                if(!li.getFirstHandle()){
                    newsHandle(li,adoptionSuccessNewsVO,1);
                //第二次
                }else if(!li.getSecondHandle()){
                    newsHandle(li,adoptionSuccessNewsVO,2);
                //第三次
                }else if(!li.getThirdHandle()){
                    newsHandle(li,adoptionSuccessNewsVO,3);
                }
            });
            log.info("定时任务处理完毕 " + new Date());
        }catch (Exception e){
            log.error("拉闸，定时任务异常..............");
        }
    }



    private Integer getNumber(Integer zan){
        if(zan < 5){
            return 0;
        }else if(zan >= 5 && zan < 10){
            return 1;
        }else if(zan >= 10 && zan < 20){
            return 2;
        }else if(zan >= 20 && zan < 50){
            return 3;
        }else if(zan >= 50){
            return 5;
        }
        return 0;
    }

    private void newsHandle(Adoption li,AdoptionSuccessNewsVO adoptionSuccessNewsVO,Integer times){
        long date = new Date().getTime();
        switch (times){
            case 1:
                if(date > adoptionSuccessNewsVO.getTime1().getTime()){
                    Wrapper<News> newsWrapper = new EntityWrapper<>();
                    newsWrapper.eq("parentid",li.getAdoptionId()).lt("createtime",adoptionSuccessNewsVO.getTime1());
                    List<News> news = newsService.selectList(newsWrapper);
                    //到时间没写就减10分信誉分
                    if(CollectionUtils.isEmpty(news)){
                        updateUserCredit(li.getUserId(),10,false);
                    }else {
                        updateUserCredit(li.getUserId(),5,true);
                    }
                }
                break;
            case 2:
                if(date > adoptionSuccessNewsVO.getTime2().getTime()){
                    Wrapper<News> newsWrapper = new EntityWrapper<>();
                    newsWrapper.eq("parentid",li.getAdoptionId()).lt("createtime",adoptionSuccessNewsVO.getTime2());
                    List<News> news = newsService.selectList(newsWrapper);
                    //到时间没写就减10分信誉分
                    if(CollectionUtils.isEmpty(news)){
                        updateUserCredit(li.getUserId(),10,false);
                    }else {
                        updateUserCredit(li.getUserId(),5,true);
                    }
                }
                break;
            case 3:
                if(date > adoptionSuccessNewsVO.getTime3().getTime()){
                    Wrapper<News> newsWrapper = new EntityWrapper<>();
                    newsWrapper.eq("parentid",li.getAdoptionId()).lt("createtime",adoptionSuccessNewsVO.getTime3());
                    List<News> news = newsService.selectList(newsWrapper);
                    //到时间没写就减10分信誉分
                    if(CollectionUtils.isEmpty(news)){
                        updateUserCredit(li.getUserId(),10,false);
                    }else {
                        updateUserCredit(li.getUserId(),5,true);
                    }
                }
                break;
            default:
                break;
        }
    }
}
