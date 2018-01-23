package com.ch999.haha;

import com.ch999.haha.admin.document.mongo.MongoTestBO;
import com.ch999.haha.admin.repository.mongo.MongoTestBoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HahaapiApplicationTests {

	@Resource
	private MongoTestBoRepository mongoTestBoRepository;

	@Test
	public void contextLoads() {
		MongoTestBO one = mongoTestBoRepository.findOne("eb0fb842-0513-44e7-9968-cf3078833ca0");
		System.out.println(one);
	}



}
