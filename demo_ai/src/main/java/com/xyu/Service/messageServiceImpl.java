package com.xyu.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyu.mapper.MessageMapper;
import com.xyu.po.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class messageServiceImpl implements messageService{
    @Autowired
    private MessageMapper messageMapper;
    @Override
    public String qurryall() {
        final String all_data;
        List<Message> messageList = messageMapper.queryData();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            all_data = objectMapper.writeValueAsString(messageList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(all_data);
        return all_data;
    }
}
