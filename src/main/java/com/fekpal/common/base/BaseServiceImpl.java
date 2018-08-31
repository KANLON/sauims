package com.fekpal.common.base;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by APone on 2018/1/31 15:59.
 * 实现服务层通用操作接口的抽象类
 */
public abstract class BaseServiceImpl<Mapper, Record> implements BaseService<Record> {

    @Autowired
    public Mapper mapper;

    @Override
    public Record selectByPrimaryKey(Integer id) {
        try {
            Method selectByPrimaryKey = mapper.getClass().getMethod("selectByPrimaryKey", id.getClass());
            Object object = selectByPrimaryKey.invoke(mapper, id);
            return (Record) object;
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public Record selectFirstByExample(ExampleWrapper<Record> example) {
        try {
            Method selectFirstByExample = mapper.getClass().getMethod("selectFirstByExample", example.getClass());
            Object object = selectFirstByExample.invoke(mapper, example);
            return (Record) object;
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }

    }

    @Override
    public List<Record> selectByExample(ExampleWrapper<Record> example, Integer offset, Integer limit) {
        try {
            Method selectByExample = mapper.getClass().getMethod("selectByExample", example.getClass(), offset.getClass(), limit.getClass());
            Object object = selectByExample.invoke(mapper, example, offset, limit);
            return (List<Record>) object;
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int countByExample(ExampleWrapper<Record> example) {
        try {
            Method countByExample = mapper.getClass().getMethod("countByExample", example.getClass());
            Object object = countByExample.invoke(mapper, example);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int updateByPrimaryKey(Record record) {
        try {
            Method updateByPrimaryKey = mapper.getClass().getDeclaredMethod("updateByPrimaryKey", Object.class);
            Object object = updateByPrimaryKey.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {
        try {
            Method updateByPrimaryKeySelective = mapper.getClass().getDeclaredMethod("updateByPrimaryKeySelective", Object.class);
            Object object = updateByPrimaryKeySelective.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int updateByExample(Record record, ExampleWrapper<Record> example) {
        try {
            Method updateByExample = mapper.getClass().getDeclaredMethod("updateByExample", Object.class, example.getClass());
            Object object = updateByExample.invoke(mapper, record, example);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int updateByExampleSelective(Record record, ExampleWrapper<Record> example) {
        try {
            Method updateByExampleSelective = mapper.getClass().getDeclaredMethod("updateByExampleSelective", Object.class, example.getClass());
            Object object = updateByExampleSelective.invoke(mapper, record, example);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
            Object object = deleteByPrimaryKey.invoke(mapper, id);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int deleteByExample(ExampleWrapper<Record> example) {
        try {
            Method deleteByExample = mapper.getClass().getDeclaredMethod("deleteByExample", example.getClass());
            Object object = deleteByExample.invoke(mapper, example);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int insert(Record record) {
        try {
            Method insert = mapper.getClass().getDeclaredMethod("insert", Object.class);
            Object object = insert.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public int insertLoop(List<Record> records) {
        try {
            Method insertLoop = mapper.getClass().getDeclaredMethod("insertLoop", List.class);
            Object object = insertLoop.invoke(mapper, records);
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }
}
