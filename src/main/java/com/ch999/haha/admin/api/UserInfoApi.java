package com.ch999.haha.admin.api;

import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.component.UserComponent;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.entity.Imgs;
import com.ch999.haha.admin.entity.Phone;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import com.ch999.haha.admin.service.ImgService;
import com.ch999.haha.admin.service.UserFansService;
import com.ch999.haha.admin.service.UserInfoService;
import com.ch999.haha.admin.service.impl.ImgServiceImpl;
import com.ch999.haha.admin.vo.OtherCenterVO;
import com.ch999.haha.admin.vo.UserCenterVO;
import com.ch999.haha.admin.vo.UserInfoUpdateVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Resource
    private UserInfoBORepository userInfoBORepository;

    @Resource
    private UserFansService userFansService;

    /**
     * 获取用户主页的信息,包括自己主页和别人主页
     *
     * @return
     */
    @GetMapping("/getUserCenterInfo/v1")
    public Result<UserCenterVO> getUserCenterInfo(Integer userId) {
        UserInfoBO loginUser = userComponent.getLoginUser();
        if (loginUser.getId() == null) {
            return Result.unlogin("unLogin", "请登陆后再进行操作", null);
        }
        if (userId != null && !loginUser.getId().equals(userId)) {
            OtherCenterVO userCenterInfo = userInfoService.getUserCenterInfo(userId, loginUser.getId());
            if (userCenterInfo == null) {
                return Result.error("error", "用户不存在");
            }
            return Result.success(userCenterInfo);
        } else {
            return Result.success(userInfoService.getMyCenterInfo(loginUser));
        }
    }

    /**
     * 修改用户信息
     *
     * @param type             要修改的类型有{avatar,name,mobile,description,pwd}
     * @param userInfoUpdateVO
     * @return
     */
    @PostMapping("/updateUserInfo/{type}/v1")
    public Result<String> updateAvatar(@PathVariable("type") String type, UserInfoUpdateVO userInfoUpdateVO) {
        UserInfoBO loginUser = userComponent.getLoginUser();
        if (loginUser.getId() == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        }
        UserInfo userInfo = new UserInfo(loginUser.getId());
        userInfo.setPicPath(userInfoBORepository.findOne(loginUser.getId()).getUserInfo().getPicPath());
        switch (type) {
            case "avatar":
                if (userInfoUpdateVO.getFile() == null) {
                    return Result.error("null", "请选择图片");
                }
                if (userComponent.updateUserAvatar(loginUser.getId(), userInfoUpdateVO.getFile())) {
                    return Result.success();
                }
                return Result.error("error", "头像更改失败");
            case "name":
                if (StringUtils.isBlank(userInfoUpdateVO.getName())) {
                    return Result.error("null", "用户名不能为空");
                } else if (!userInfoUpdateVO.getName().equals(loginUser.getUserInfo().getUsername()) && !userComponent.checkCanUse(type, userInfoUpdateVO.getName())) {
                    return Result.error("null", "昵称修改失败该昵称已被占用");
                }
                userInfo.setUsername(userInfoUpdateVO.getName());
                break;
            case "description":
                if (StringUtils.isBlank(userInfoUpdateVO.getDescription())) {
                    return Result.error("null", "个性签名不能为空");
                }
                userInfo.setAutograph(userInfoUpdateVO.getDescription());
                break;
            case "mobile":
                if (StringUtils.isBlank(userInfoUpdateVO.getMobile())) {
                    return Result.error("null", "请输入要修改的手机号码");
                } else if (!userInfoUpdateVO.getMobile().matches(Phone.CK)) {
                    return Result.error("error", "请输入正确的的电话号码");
                } else if (!userInfoUpdateVO.getMobile().equals(loginUser.getUserInfo().getMobile()) && !userComponent.checkCanUse(type, userInfoUpdateVO.getMobile())) {
                    return Result.error("error", "手机号修改失败该号码已被占用");
                }
                userInfo.setMobile(userInfoUpdateVO.getMobile());
                break;
            case "pwd":
                if (StringUtils.isBlank(userInfoUpdateVO.getOldPwd())) {
                    return Result.error("null", "请输入原密码");
                } else if (!userInfoService.selectById(loginUser.getId()).getPwd().equals(userInfoUpdateVO.getOldPwd())) {
                    return Result.error("error", "原密码输入错误，密码修改失败");
                } else if (StringUtils.isBlank(userInfoUpdateVO.getNewPwd1()) || StringUtils.isBlank(userInfoUpdateVO.getNewPwd2()) || !userInfoUpdateVO.getNewPwd2().equals(userInfoUpdateVO.getNewPwd1())) {
                    return Result.error("error", "新密码未输入或两次密码输入不一致，密码修改失败");
                }
                userInfo.setPwd(userInfoUpdateVO.getNewPwd1());
                break;
            default:
                return Result.error("error", "不支持该类型的修改");
        }
        //修改数据库
        userInfoService.updateById(userInfo);
        UserInfo userInfo1 = userInfoService.selectById(loginUser.getId());
        //修改缓存
        UserInfoBO one = userInfoBORepository.findOne(loginUser.getId());
        if (one != null) {
            one.setUserInfo(userInfo1);
        } else {
            one = new UserInfoBO(loginUser.getId(), userInfo1);
        }
        userInfoBORepository.save(one);
        return Result.success();
    }

    @PostMapping("/test")
    public Result<Imgs> test(MultipartFile file) {
        if (file == null) {
            return Result.error("error", "请选择要上传的图片");
        } else if (!ImgServiceImpl.checkIsImg(file.getOriginalFilename())) {
            return Result.error("error", "图片格式不正确");
        }
        Imgs imgs = imgService.uploadImg(file);
        if (imgs == null) {
            return Result.error("error", "图片上传失败");
        }
        return Result.success(imgs);
    }

    /**
     * 查看用户的粉丝，关注的具体是谁
     *
     * @param userId     不传表示查看当前登录用户的
     * @param isFansInfo 是否是查看粉丝信息
     * @return
     */
    @GetMapping("/getFansOrFollowsDetail/v1")
    public Result<List<UserInfo>> getFansOrFollowsDetail(Integer userId, Boolean isFansInfo) {
        UserInfoBO loginUser = userComponent.getLoginUser();
        if (loginUser.getId() == null) {
            return Result.unlogin("unLogin", "请登陆后再操作", null);
        }
        return Result.success(userFansService.getUserFansOrFollows(userId != null ? userId : loginUser.getId(), isFansInfo));
    }

    /**
     * 关注和取关
     *
     * @param userId
     * @return
     */
    @PostMapping("/followOrCancel/v1")
    public Result<String> followOrCancel(Integer userId, Boolean isFollow) {
        UserInfoBO loginUser = userComponent.getLoginUser();
        if (loginUser.getId() == null) {
            return Result.unlogin("unLogin", "请登陆后再操作", null);
        }
        if (userId == null) {
            return Result.error("error", "请选择要关注或者取关的人");
        }
        if (userId.equals(loginUser.getId())) {
            return Result.error("error", "不能自己关注自己");
        }
        Boolean aBoolean = userFansService.followOrCancel(loginUser.getId(), userId, isFollow);
        if (aBoolean == null) {
            return Result.error("error", "要关注或取关的用户不存在");
        } else if (aBoolean) {
            return Result.success();
        }
        if (isFollow == null || isFollow) {
            return Result.error("error", "你已经关注他了无需重复关注");
        }
        return Result.error("error", "你未关注他无法取消关注");
    }
}
