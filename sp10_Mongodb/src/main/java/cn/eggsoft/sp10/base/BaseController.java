package cn.eggsoft.sp10.base;


import cn.eggsoft.sp10.base.config.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseController {


    @Autowired
    public BaseMapper bizService;

    @Autowired
    protected RedisServiceImpl redisServiceImpl;

    @Autowired
    protected JdbcTemplate jdbcTemplate;//Spring的JdbcTemplate是自动配置的，可直接使用

    protected static final Logger log = LoggerFactory.getLogger(BaseController.class);

    protected cn.tedu.sp0ag4studio.common.util.WebUtils webUtils;

}
