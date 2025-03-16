package com.xyu.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyu.mapper.UserMapper;
import com.xyu.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public User queryData(String username) {
        // 假设从数据库查询到的 String 是 JSON 格式的用户信息
        String user = userMapper.selectPasswordByUsername(username);
        String jsonData = user;
        try {
            // 将 jsonData 字符串转换为 User 对象
            User user_object = objectMapper.readValue(jsonData, User.class);
            return user_object;
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        }
        return null; // 出现异常时返回 null
    }
}
