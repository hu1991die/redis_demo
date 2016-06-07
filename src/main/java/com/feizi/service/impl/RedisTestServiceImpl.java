/*
* Copyright (c) 2014 Javaranger.com. All Rights Reserved.
*/
package com.feizi.service.impl;

import com.feizi.service.RedisTestService;
import org.springframework.stereotype.Service;

/**
 * @Desc Redis测试Service服务实现类
 * @Author feizi
 * @Date 2016/6/7 9:17
 * @Package_name com.feizi.service.impl
 */
@Service
public class RedisTestServiceImpl implements RedisTestService{

    public String getTimestamp(String param) {
        Long timeStamp = System.currentTimeMillis();
        return timeStamp.toString();
    }
}
