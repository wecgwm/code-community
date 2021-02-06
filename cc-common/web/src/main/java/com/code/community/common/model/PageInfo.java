package com.code.community.common.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageInfo {
    @Min(1)
    @NotNull
    private Integer currentPage;
    @Min(1)
    @NotNull
    private Integer pageSize;

    public Page getPage(){
        return new Page<>(this.currentPage,this.pageSize);
    }

}
