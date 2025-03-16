package com.xyu.controller;
import com.xyu.mapper.MessageMapper;
import com.xyu.myapp.Ai_bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/auth")
public class ai_Controller {
    @Autowired
    private MessageMapper messageMapper;
    @PostMapping("/ai")
    public Map<String, Object> ai_response(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        String message = credentials.get("message"); // 获取请求体中的"message"字段
        System.out.println(message);
        Ai_bot bot = new Ai_bot();
        String answer =bot.aiBot(message +"不要回复表情,直接回复文本");
        Date date = new Date();
        // 格式化日期为 "yyyyMMdd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = sdf.format(date);
        // 将格式化后的日期转换为 int
        int dateInt = Integer.parseInt(formattedDate);
        messageMapper.creatMessage(dateInt,message,answer);
        result.put("status", "success");
        System.out.println("成功");
        result.put("status", "success");
        result.put("message", "Login successful");
        result.put("answer", answer);
        System.out.println(result);
        return result;
    }
}










