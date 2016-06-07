/*
* Copyright (c) 2014 Javaranger.com. All Rights Reserved.
*/
package com.feizi.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Desc SpringTestCase测试用例基类
 * @Author feizi
 * @Date 2016/6/7 9:54
 * @Package_name com.feizi.test
 */
/*指定bean注入的配置文件*/
@ContextConfiguration(locations = {"classpath:application.xml"})
/*使用标准的Junit @RunWith注释来告诉Junit使用Spring TestRunner*/
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTestCase extends AbstractJUnit4SpringContextTests{

}
