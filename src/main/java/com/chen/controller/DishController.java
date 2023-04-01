package com.chen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.common.R;
import com.chen.dto.DishDto;
import com.chen.entity.Category;
import com.chen.entity.Dish;
import com.chen.entity.DishFlavor;
import com.chen.service.CategoryService;
import com.chen.service.DishFlavorService;
import com.chen.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);
        return  R.success("新增菜品成功");
    }


    @DeleteMapping
    public R<String> deleteByIds(Long ids){
        log.info("删除id: {}",ids);
        if (ids == null){
            return R.error("删除失败");
        }else {
            dishService.removeById(ids);
        }

        return R.success("删除成功");
    }

//    @GetMapping("/page")
//    public R<Page> page(int page, int pageSize, String name){
//        log.info("page = {},pageSize = {}, name = {}",page,pageSize,name);
//
//
//        //构造分页构造器
//        Page<Dish> pageInfo = new Page(page,pageSize);//第一页,十条
//        Page<DishDto> dishDtoPage = new Page<>();
//
//        //构造条件构造器
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
//        //添加一个过滤条件
//        queryWrapper.like(name != null, Dish::getName,name);
//
//        //添加排序条件
//        queryWrapper.orderByDesc(Dish::getUpdateTime);
//
//        //执行分页查询
//        dishService.page(pageInfo,queryWrapper);
//
//        //对象拷贝
//        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
//
//        List<Dish> records = pageInfo.getRecords();
//        List<DishDto> list = records.stream().map((item)->{
//            DishDto dishDto = new DishDto();
//            BeanUtils.copyProperties(item,dishDto);
//            Long categoryId = item.getCategoryId();//菜品类型id
//            //根据id查询分类对象
//            Category category = categoryService.getById(categoryId);
//            if (category != null){
//                String categoryName = category.getName();
//                dishDto.setCategoryName(categoryName);
//            }
//
//            return dishDto;
//        }).collect(Collectors.toList());
//
//        dishDtoPage.setRecords(list);
//        return R.success(dishDtoPage);
//    }

    /**
     * 菜品信息分页查询(管理员用的)
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo, queryWrapper);

        //对象拷贝 这里需要忽略 records（是所有的对象集合），因为泛型，全部拷贝的话会更改泛型
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        //获取记录对象集合
        List<Dish> records = pageInfo.getRecords();
        // 这里的操作主要是为了 给前端传递 categoryName【这也是DishDto中字段的由来】
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            //            //产品类型赋值
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){
        log.info(id.toString());
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);
        return  R.success("修改菜品成功");
    }


    /**
     * 根据条件查询对应的菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());

        queryWrapper.eq(Dish::getStatus,1);

        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);


        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            //            //产品类型赋值
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            Long dishId = item.getId();

            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());





        return R.success(dishDtoList);
    }
}
