package com.code.community.common.model.form;

import com.code.community.common.model.po.BasePo;
import com.code.community.common.model.vo.BaseVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * TODO 是否可以减少重复代码
 * @param <T>
 */
@Data
public class BaseForm<T extends BasePo> {

    public T toPo(Class<T> clazz) {
        //TODO test
//       ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
//        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }

    public <T extends BaseVo> T toVo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }

}
