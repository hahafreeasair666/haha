package com.ch999.haha.admin.document.mongo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
@Document(collection = "test")
public class MongoTestBO {

    @Id
    private String id;

    private String name;

}
