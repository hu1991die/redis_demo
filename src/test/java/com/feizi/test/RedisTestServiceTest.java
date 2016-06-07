/*
* Copyright (c) 2014 Javaranger.com. All Rights Reserved.
*/
package com.feizi.test;

import com.feizi.service.RedisTestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Desc 测试Redis缓存
 * @Author feizi
 * @Date 2016/6/7 9:59
 * @Package_name com.feizi.test
 */
public class RedisTestServiceTest extends SpringTestCase{

    @Autowired
    private RedisTestService redisTestService;

    @Test
    public void getTimestampTest() throws InterruptedException{
        System.out.println("第一次调用：" + redisTestService.getTimestamp("param"));
        Thread.sleep(2000);

        System.out.println("2秒之后调用：" + redisTestService.getTimestamp("param"));
        Thread.sleep(9000);

        System.out.println("再过11秒之后调用：" + redisTestService.getTimestamp("param"));
    }
}
