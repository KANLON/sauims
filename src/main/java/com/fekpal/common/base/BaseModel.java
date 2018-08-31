package com.fekpal.common.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by APone on 2017/8/14.
 * POJO基类，实现Serializable接口
 */
public class BaseModel implements Serializable{

    private static final long serialVersionUID = 2764427107524917616L;

    public String toString(){

        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
