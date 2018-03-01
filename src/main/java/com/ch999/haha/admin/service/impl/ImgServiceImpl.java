package com.ch999.haha.admin.service.impl;

import com.ch999.haha.admin.service.ImgService;
import com.ch999.haha.common.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hahalala
 */
@Service
@Slf4j
public class ImgServiceImpl implements ImgService{

    private static final String IMGSERVER = "http://120.79.160.214:9333";

    private static final String IMGUPLOAG_URL = "/submit?collection=web";

    @Override
    public Object uploadImg(MultipartFile file) {
        String s = HttpClientUtil.uploadFile(IMGSERVER, IMGUPLOAG_URL, file);
        System.out.println(s);
        System.out.println("mmp");
        return null;
    }
}
