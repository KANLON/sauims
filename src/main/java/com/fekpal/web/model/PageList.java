package com.fekpal.web.model;

import com.fekpal.common.base.BaseModel;
import org.springframework.stereotype.Component;

/**
 * Created by APone on 2018/2/28 19:00.
 */
@Component
public class PageList extends BaseModel {

    private static final long serialVersionUID = -5864235926106437627L;

    /**
     * 实际setter时为页码，得到时，将其转化为offset，即从0开始
     */
    private Integer offset;

    private Integer limit;

    /**
     * 默认获取全部数据
     */
    public PageList() {
        this.offset = 1;
        this.limit = 1000000;
    }

    /**
     * 将其转化为offset，即变成从0开始
     * @return
     */
    public Integer getOffset() {
        return (offset-1)*(this.limit);
    }

    /**
     * 设置offset时，默认传进页码，在get方法时，将其转化为offset，即变成从0开始
     * @param offset
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
