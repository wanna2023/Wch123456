package com.xyu.Vo;
import java.util.List;
import com.xyu.Vo.MessageVo;
// 代表整个消息列表的父类
public class MessageListVo {
    private List<MessageVo> messages;

    public List<MessageVo> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageVo> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessageListVo{" +
                "messages=" + messages +
                '}';
    }
}
