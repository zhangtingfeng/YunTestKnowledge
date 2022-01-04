package cn.eggsoft.sp10.tuoke;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MongoDbService {
    private static final Logger logger = LoggerFactory.getLogger(MongoDbService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource
    private MongoUtil mongoUtil;

    /**
     * 查询所有
     *
     * @return
     */
    public void save(LogContent argLogContent) {
        String questionId = ObjectId.get().toString();
        //然后我们使用的时候直接使用即可
        ObjectId id = new ObjectId(questionId);
        //argLogContent.setId(questionId);
        logger.info("--------------------->[MongoDB find start]");
        mongoTemplate.save(argLogContent);
    }


    /**
     * 根据名称查询
     *
     * @1param nam1e
     * @return
     */
    public PageHelper getMongoBySearch(LogContent argLogContent,int start,int limit) {
        logger.info("--------------------->[MongoDB find start]");
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "updatetime"));


        if (!argLogContent.getTitle().isEmpty()){
            Pattern pattern=Pattern.compile("^.*"+argLogContent.getTitle()+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("title").regex(pattern));
        }
        if (!argLogContent.getSubtitle().isEmpty()){
            Pattern pattern=Pattern.compile("^.*"+argLogContent.getSubtitle()+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("subtitle").regex(pattern));
        }
        if (!argLogContent.getContent().isEmpty()){

            Pattern pattern=Pattern.compile("^.*"+argLogContent.getContent()+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("content").regex(pattern));
        }

        if (argLogContent.getStartTime()!=null && argLogContent.getEndTime()!=null){
            //Pattern pattern=Pattern.compile("^.*"+argLogContent.getLeft_updatetime().getTime()+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("updatetime").gte(argLogContent.getStartTime()).lte(argLogContent.getEndTime()));
        }
        else if (argLogContent.getStartTime()!=null ){
            //Pattern pattern=Pattern.compile("^.*"+argLogContent.getLeft_updatetime().getTime()+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("updatetime").gte(argLogContent.getStartTime()));
        }
        else if (argLogContent.getEndTime()!=null ){
            //Pattern pattern=Pattern.compile("^.*"+argLogContent.getLeft_updatetime().getTime()+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("updatetime").lte(argLogContent.getEndTime()));
        }
        long count = mongoTemplate.count(query, LogContent.class);

        mongoUtil.start(start, limit, query);

        List<LogContent> ListLogContent=mongoTemplate.find(query, LogContent.class);

        Integer startNum=limit*((start==0?1:start)-1)+1;
        Integer[] arr = {startNum};
        ////给list每个元素添加序号，可以试下这里如果换成 Integer i = 1; 下面set的时候传 i++ 试下看会发生什么？
        ListLogContent = ListLogContent.stream().peek(e->e.setNum(arr[0]++)).collect(Collectors.toList());

        PageHelper pageHelper = mongoUtil.pageHelper(count, ListLogContent);
        System.out.println(pageHelper);
        return pageHelper;

    }
}
