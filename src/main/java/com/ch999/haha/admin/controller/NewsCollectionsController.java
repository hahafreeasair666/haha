package com.ch999.haha.admin.controller;



import com.ch999.common.util.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2018-01-22
 */
@RestController
@RequestMapping("/admin/newsCollections")
public class NewsCollectionsController {

    @GetMapping("/test")
    public Result test(){
        return new Result(0,"成功","成功",new ArrayList<>());
    }
	
}
