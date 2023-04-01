package com.chen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.dto.SetmealDto;
import com.chen.entity.Setmeal;

import java.util.List;

public interface SetmealService  extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    void removeWithDish(List<Long> ids);
}
