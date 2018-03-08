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
import java.util.stream.Collectors;

/**
 * @author hahalala
 */
@Component
public class DogAndCatComponent {


    /**
     * 猫狗种类做懒加载
     */
    private static List<Map<String, Object>> catList;

    private static List<Map<String, Object>> dogList;

    @Resource
    private AnimalService animalService;


    /**
     * 获取猫或狗的种类和id
     *
     * @param type
     * @return
     */
    public List<Map<String, Object>> getAnimalsName(Integer type, Boolean isNeedDetail) {
        switch (type) {
            case 1:
                if (CollectionUtils.isNotEmpty(dogList)) {
                    return dogList;
                } else {
                    List<Map<String, Object>> list = new ArrayList<>();
                    getAnimalList(1, isNeedDetail).forEach(li -> {
                        Map<String, Object> liMap = new HashMap<>();
                        liMap.put("id", li.getId());
                        liMap.put("name", li.getName());
                        list.add(liMap);
                    });
                    dogList = list;
                    return dogList;
                }
            case 2:
                if (CollectionUtils.isNotEmpty(catList)) {
                    return catList;
                } else {
                    List<Map<String, Object>> list = new ArrayList<>();
                    getAnimalList(2, isNeedDetail).forEach(li -> {
                        Map<String, Object> liMap = new HashMap<>();
                        liMap.put("id", li.getId());
                        liMap.put("name", li.getName());
                        list.add(liMap);
                    });
                    catList = list;
                    return catList;
                }
            default:
                return null;
        }
    }


    public List<Animal> getAnimalList(Integer type, Boolean isNeedDetail) {
        Wrapper<Animal> wrapper = new EntityWrapper<>();
        wrapper.eq("type", type);
        List<Animal> animals = animalService.selectList(wrapper);
        if (isNeedDetail == null || isNeedDetail) {
            return animals.stream().filter(li -> !"未知".equals(li.getName())).collect(Collectors.toList());
        }
        return animals;
    }

    public Map<String, Object> getAnimalDetail(Integer id) {
        Animal animal = animalService.selectById(id);
        if(animal == null){
            return new HashMap<>();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",animal.getId());
        map.put("name",animal.getName());
        map.put("body",animal.getBody());
        map.put("pic",animal.getPic());
        return map;
    }

}
