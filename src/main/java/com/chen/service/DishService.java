package com.chen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.dto.DishDto;
import com.chen.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品,同时插入菜品对应的口味数据,需要操作两张表dish,dish_flavor
    void saveWithFlavor(DishDto dishDto);

    //根据id查询dish和口味信息
    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息,同时更新对应的口味信息
    void updateWithFlavor(DishDto dishDto);
}
