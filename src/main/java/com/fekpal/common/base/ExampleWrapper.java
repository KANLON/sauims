package com.fekpal.common.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 条件构造器标准，用于数据库查询的标准条件
 */
public class ExampleWrapper<Record> extends EntityWrapper<Record> implements Serializable {

    //参数别名常量，用于mybatis
    public static final String PARAM_NAME = "ex";

    private static final long serialVersionUID = 1612189027479401986L;

    public ExampleWrapper() {
        super.setParamAlias(PARAM_NAME);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
