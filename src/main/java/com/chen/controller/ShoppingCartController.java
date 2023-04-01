package com.chen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.common.BaseContext;
import com.chen.common.R;
import com.chen.entity.ShoppingCart;
import com.chen.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 展示购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        // 缓存的购物车key
        String key = "shopping_" + BaseContext.getCurrentId();
        // 先查询redis中是否存在
        List<ShoppingCart> shoppingCarts = (List<ShoppingCart>) redisTemplate.opsForValue().get(key);
        // 如果缓存中无数据，那么就查询数据库
        if (null == shoppingCarts) {
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

            // 最晚下单的 菜品或套餐在购物车中最先展示
            queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
            shoppingCarts = shoppingCartService.list(queryWrapper);
            // 存储到redis中
            redisTemplate.opsForValue().set(key, shoppingCarts, 60, TimeUnit.MINUTES);
        }
        return R.success(shoppingCarts);
    }


    @PostMapping("/add")
    public R<ShoppingCart> addToCart(@RequestBody ShoppingCart shoppingCart) {
        // 清除缓存
//        cleanCache();
        log.info("购物车中的数据:{}" + shoppingCart.toString());

        //设置用户id,指定当前是哪个用户的 购物车数据
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        // 查询当前菜品或套餐是否 在购物车中
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        // 根据登录用户的 userId去ShoppingCart表中查询该用户的购物车数据
        queryWrapper.eq(ShoppingCart::getUserId, userId);

        // 先判断添加进购物车的是菜品
        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }


        ShoppingCart oneCart = shoppingCartService.getOne(queryWrapper);
        //  如果购物车中 已经存在该菜品或套餐，其数量+1，不存在，就将该购物车数据保存到数据库中
        if (oneCart != null) {
            Integer number = oneCart.getNumber();
            oneCart.setNumber(number + 1);
            shoppingCartService.updateById(oneCart);
        } else {
            shoppingCart.setNumber(1);
            // 设置创建时间
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            oneCart = shoppingCart;
        }
        return R.success(oneCart);
    }
}
