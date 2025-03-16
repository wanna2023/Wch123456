package com.xyu.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.xyu.Vo.AjaxResult;
import com.xyu.Vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xyu.Service.messageService;
import com.xyu.Service.messageServiceImpl;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;
@RestController
@RequestMapping("/auth")
public class Data_Controller {
    @Autowired
    private messageServiceImpl messageServiceimpl;
    @PostMapping("/data")
    public Map<String, Object> data_response(HttpServletResponse response){
        String messageService1 = messageServiceimpl.qurryall();
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("tell", messageService1);
        System.out.println(result);
        return result;
    }
}

