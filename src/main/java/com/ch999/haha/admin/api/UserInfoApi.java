package com.ch999.haha.admin.api;

import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.component.UserComponent;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.entity.Imgs;
import com.ch999.haha.admin.service.ImgService;
import com.ch999.haha.admin.service.UserInfoService;
import com.ch999.haha.admin.service.impl.ImgServiceImpl;
import com.ch999.haha.admin.vo.UserCenterVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author hahalala
 */
@RestController
@RequestMapping("/user/api")
public class UserInfoApi {

    @Resource
    private UserComponent userComponent;

    @Resource
    private ImgService imgService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取用户主页的信息
     * @return
     */
    @GetMapping("/getUserCenterInfo/v1")
    public Result<UserCenterVO> getUserCenterInfo(){
        UserInfoBO loginUser = userComponent.getLoginUser();
        if(loginUser.getId() == null){
            return Result.unlogin("unLogin","请登陆后再查看个人主页",null);
        }
        return  Result.success(userInfoService.getUserCenterInfo(loginUser));
    }

    @PostMapping("/test")
    public Result test(MultipartFile file){
        if(file == null){
            return Result.error("error","请选择要上传的图片");
        }else if(!ImgServiceImpl.checkIsImg(file.getOriginalFilename())){
            return Result.error("error","图片格式不正确");
        }
        Imgs imgs = imgService.uploadImg(file);
        if(imgs == null){
            return Result.error("error","图片上传失败");
        }
        return Result.success(imgs);
    }

}
