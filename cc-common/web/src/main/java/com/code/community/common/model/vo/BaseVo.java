package com.code.community.common.model.vo;

import com.code.community.common.model.po.BasePo;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseVo <T extends BasePo> implements Serializable {

    private static final long serialVersionUID = 5127070430573773205L;

}
