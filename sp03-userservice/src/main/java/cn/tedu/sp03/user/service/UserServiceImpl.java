package cn.tedu.sp03.user.service;

import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.UserService;
import cn.tedu.sp01.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${sp.user-service.users}")
    private String userJson;

    @Override
    public User getUser(Integer id) {
        List<User> users = JsonUtil.from(userJson, new TypeReference<List<User>>() {
        });
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return new User(id, "name-" + id, "pwd-" + id);
    }

    @Override
    public void addScore(Integer id, Integer score) {
        log.info("增加用户积分，userId=" + id + "，score=" + score);
    }
}