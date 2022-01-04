package cn.tedu.sp25.base;


import cn.tedu.sp25.base.config.MongoServiceImpl;
import cn.tedu.sp25.base.config.RedisServiceImpl;
import cn.tedu.sp25.controller.SysUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

public class BaseController {


    @Autowired
    public cn.tedu.sp25.base.BaseMapper bizService;

    @Autowired
    protected RedisServiceImpl redisServiceImpl;

    @Autowired
    protected JdbcTemplate jdbcTemplate;//Spring的JdbcTemplate是自动配置的，可直接使用

    protected static final Logger log = LoggerFactory.getLogger(BaseController.class);

    protected cn.tedu.sp0ag4studio.common.util.WebUtils webUtils;

    @Autowired
    protected RestTemplate templateEureka;

    @Autowired
    protected MongoServiceImpl mongoServiceImpl;
}
