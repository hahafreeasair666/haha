package com.ch999.haha.admin.api;


import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.component.DogAndCatComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/DogAndCat/api")
public class DogAndCatApi {

    @Resource
    private DogAndCatComponent dogAndCatComponent;

    /**
     * 1狗2猫
     * @param type
     * @return
     */
    @GetMapping("/getAnimalInfo")
    public Result getAllDog(Integer type){
        return Result.success(dogAndCatComponent.getAnimalList(type));
    }

    @GetMapping("/getAnimalName")
    public Result getAllCat(Integer type){
        return Result.success(dogAndCatComponent.getAnimalsName(type));
    }
}
