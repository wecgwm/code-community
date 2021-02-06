package com.code.community.common.factory;

import com.code.community.comment.base.exception.BaseException;
import com.code.community.common.model.po.BasePo;
import com.code.community.common.model.vo.BaseVo;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * vo工厂
 * 通过po获得对应属性的vo
 */
public class VoFactory {

    public static <T extends BaseVo,T2 extends BasePo> T createVo(Class<T> clazz, T2 po) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        //对比vo泛型与po
        if(po.getClass() == actualTypeArguments[0]){
            T t = BeanUtils.instantiateClass(clazz);
            BeanUtils.copyProperties(po,t);
            return t;
        }else {
            throw new BaseException("类型错误");
        }
    }

}
