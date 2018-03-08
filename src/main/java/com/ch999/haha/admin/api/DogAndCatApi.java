package com.ch999.haha.admin.api;


import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.component.DogAndCatComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hahalala
 */
@RestController
@RequestMapping("/DogAndCat/api")
public class DogAndCatApi {

    @Resource
    private DogAndCatComponent dogAndCatComponent;

    /**
     * 1狗2猫  用于猫狗列表展示的
     * @param type
     * @return
     */
    @GetMapping("/getAnimalInfo/v1")
    public Result getAllDog(Integer type){
        if(type == null){
            return Result.error("error","请选择类型");
        }
        return Result.success(dogAndCatComponent.getAnimalList(type,true));
    }

    @GetMapping("/getAnimalDetailById/v1")
    public Result getAnimalDetailById(Integer id){
        if(id == null){
            return Result.error("error","请选择具体动物");
        }
        return Result.success(dogAndCatComponent.getAnimalDetail(id));
    }

    /**
     * 用于发布消息时的猫狗种类的下拉列表
     * @param type
     * @return
     */
    @GetMapping("/getAnimalName/v1")
    public Result getAllCat(Integer type){
        if(type == null){
            return Result.error("error","请选择类型");
        }
        return Result.success(dogAndCatComponent.getAnimalsName(type,false));
    }
}
