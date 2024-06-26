package com.example.demo.finance.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MyLocalCacheTest {


    @Test
    void testCache() {
        MyLocalCache<String, Integer> cache = new MyLocalCache<>(5);

        // 测试 put 和 get
        cache.put("one", 1);

        Assertions.assertEquals(1, cache.get("one"));

        // 测试顺序和剔除
        cache.put("two", 2);
        cache.put("three", 3);
        cache.put("four", 4);
        cache.put("five", 5);
        cache.put("six", 6);
        // 超过容量 ，应该会移除最早的 one
        Assertions.assertNull(cache.get("one"));

        // 测试 pop
        // 定义一组用例key value
        String key="five";
        int value=5;
        //验证值存在并等于value
        Assertions.assertEquals(value, cache.get(key));
        //弹出刚刚使用的key
        cache.pop();
        //再获取必定为空
        Assertions.assertNull(cache.get(key));

        //再添加一组用例
        String key2="two";
        int value2=2;
        //验证值存在并等于value
        Assertions.assertEquals(value2, cache.get(key2));
        //弹出刚刚使用的key
        cache.pop();
        //再获取必定为空
        Assertions.assertNull(cache.get(key2));

        // 测试 compute
        cache.compute("three", 5, (k, v) -> v + 5);
        Assertions.assertEquals(8, cache.get("three"));



    }


}