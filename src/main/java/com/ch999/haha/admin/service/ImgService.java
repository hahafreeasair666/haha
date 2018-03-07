package com.ch999.haha.admin.service;

import com.ch999.haha.admin.entity.Imgs;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hahalala
 */
public interface ImgService {

    Imgs uploadImg(MultipartFile file);
}
