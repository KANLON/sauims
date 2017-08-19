package com.fekpal.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by APone on 2017/8/14.
 * POJO基类，实现Serializable接口,方便序列化到硬盘或缓存，不用于对象到Json序列化
 */
public class BasePOJO implements Serializable{

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

}
