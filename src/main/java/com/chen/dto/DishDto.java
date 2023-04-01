package com.chen.dto;

import com.chen.entity.Dish;
import com.chen.entity.DishFlavor;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Sire
 */
@Data
public class DishDto extends Dish {

    /**
     * 菜品口味
     */
    private List<DishFlavor> flavors = new ArrayList<>();

    /**
     * 菜品名称
     */
    private String categoryName;

    /**
     * 份数
     */
    private Integer copies;
}
