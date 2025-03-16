package com.xyu.controller;
import com.xyu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        String username = credentials.get("username");
        String password = credentials.get("password");
        System.out.println(password);
        // 1. 验证用户名和密码
        String string_user = userMapper.selectPasswordByUsername(username);
        System.out.println(string_user);
        if (Objects.equals(string_user, password)) {
            result.put("status", "success");
            System.out.println("成功");
            result.put("message", "Login successful");
            return result;
        }
        // 3. 如果登录成功，将一些必要的信息（如 token）返回给前端
        result.put("status", "fail");
        result.put("message", "Invalid username or password");
        return result;
    }
}
