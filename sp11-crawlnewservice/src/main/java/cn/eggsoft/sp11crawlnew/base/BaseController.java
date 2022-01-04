package cn.eggsoft.sp11crawlnew.base;



import cn.eggsoft.sp11crawlnew.base.config.MongoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

public class BaseController {




    @Autowired
    protected JdbcTemplate jdbcTemplate;//Spring的JdbcTemplate是自动配置的，可直接使用

    protected static final Logger log = LoggerFactory.getLogger(BaseController.class);

    protected cn.tedu.sp0ag4studio.common.util.WebUtils webUtils;

    @Autowired
    protected RestTemplate templateEureka;


    @Autowired
    protected MongoServiceImpl mongoServiceImpl;
}
