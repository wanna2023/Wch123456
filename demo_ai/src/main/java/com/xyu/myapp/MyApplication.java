package com.xyu.myapp;

import com.coze.openapi.service.config.Consts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import io.reactivex.Flowable;


@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);

    }
}
