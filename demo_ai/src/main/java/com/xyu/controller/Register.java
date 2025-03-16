package com.xyu.controller;
import com.xyu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/auth")
public class Register{
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        String username = credentials.get("username");
        String password = credentials.get("password");
        System.out.println(username);
        System.out.println(password);
        String string_user = userMapper.selectPasswordByUsername(username);
        if (string_user==null){
            userMapper.creatUser(username,password);
            result.put("status", "success");
            System.out.println("成功");
            result.put("message", "Login successful");
            return result;
        }else {
            result.put("status", "fail");
            result.put("message", "Invalid username or password");
            System.out.println(result);
            return result;
        }
    }
}

