package com.ch999.haha.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.Imgs;
import com.ch999.haha.admin.service.ImgService;
import com.ch999.haha.admin.service.ImgsService;
import com.ch999.haha.common.HttpClientUtil;
import com.ch999.haha.common.ImageRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理图片的服务类
 *
 * @author hahalala
 */
@Service
@Slf4j
public class ImgServiceImpl implements ImgService {

    private static final String IMGSERVER = "http://120.79.160.214:9333";

    private static final String IMGUPLOAG_URL = "/submit";

    @Resource
    private ImgsService imgsService;

    @Override
    public Imgs uploadImg(MultipartFile file) {
        Imgs imgs = new Imgs();
        String s = HttpClientUtil.uploadFile(IMGSERVER, IMGUPLOAG_URL, file);
        ImageRes imageRes = JSON.parseObject(s, ImageRes.class);
        String fid = imageRes.getFid();
        String fileName = imageRes.getFileName();
        Integer size = imageRes.getSize();
        if (StringUtils.isBlank(fid) || size == null || size == 0) {
            return null;
        }
        imgs.setImgName(fileName);
        imgs.setImgUrl("http://" + imageRes.getFileUrl().replace("localhost", "120.79.160.214"));
        imgs.setIsdel(0);
        imgsService.insert(imgs);
        return imgs;
    }

    public static boolean checkIsImg(String name) {
        String[] split = name.split("\\.");
        if (split.length < 2) {
            return false;
        } else if (split[1].equals("jpg")) {
            return true;
        } else if (split[1].equals("png")) {
            return true;
        } else if (split[1].equals("gif")) {
            return true;
        } else if (split[1].equals("jpeg")) {
            return true;
        } else if (split[1].equals("bmp")) {
            return true;
        }
        return false;
    }

}
