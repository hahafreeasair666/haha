package com.ch999.haha.admin.component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.entity.Animal;
import com.ch999.haha.admin.service.AnimalService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hahalala
 */
@Component
public class DogAndCatComponent {


    /**
     * 猫狗种类做懒加载
     */
    private static List<Map<String,Object>> catList;

    private static List<Map<String,Object>> dogList;

    @Resource
    private AnimalService animalService;


    /**
     * 获取猫或狗的种类和id
     * @param type
     * @return
     */
    public List<Map<String,Object>> getAnimalsName(Integer type){
        switch (type){
            case 1:
                if(CollectionUtils.isNotEmpty(dogList)){
                    return dogList;
                }else {
                    List<Map<String,Object>> list = new ArrayList<>();
                    getAnimalList(1).forEach(li->{
                        Map<String,Object> liMap = new HashMap<>();
                        liMap.put("id",li.getId());
                        liMap.put("name",li.getName());
                        list.add(liMap);
                    });
                    dogList = list;
                    return dogList;
                }
            case 2:
                if(CollectionUtils.isNotEmpty(catList)){
                    return catList;
                }else {
                    List<Map<String,Object>> list = new ArrayList<>();
                    getAnimalList(2).forEach(li->{
                        Map<String,Object> liMap = new HashMap<>();
                        liMap.put("id",li.getId());
                        liMap.put("name",li.getName());
                        list.add(liMap);
                    });
                    catList = list;
                    return catList;
                }
            default:
                return null;
        }
    }


    public List<Animal> getAnimalList(Integer type){
        Wrapper<Animal> wrapper = new EntityWrapper<>();
        wrapper.eq("type",type);
        List<Animal> animals = animalService.selectList(wrapper);
        return animals;
    }

}
