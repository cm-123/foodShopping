package com.chen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper  extends BaseMapper<Dish> {
}
