package com.ch999.haha.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * @author haha 2017/11/3
 */
public class PageableVo implements Serializable,Pageable {

    private static final long serialVersionUID = 5737186884878863905L;
    // 当前页

    private Integer page;

    // 当前页面条数

    private Integer size;

    //排序条件

    private Sort sort;

    public void setSort(Sort sort) {
        this.sort = sort;
    }
    // 当前页面
    @Override
    public int getPageNumber() {
        return getPage();
    }
    // 每一页显示的条数
    @Override
    public int getPageSize() {
        return getSize();
    }
    // 第二页所需要增加的数量
    @Override
    public int getOffset() {
        return (getPage() - 1) * getSize();
    }
    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    public Integer getPage() {
        if(page == null || page == 0){
            page = 1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        if(size == null){
            size = 5;
        }
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
