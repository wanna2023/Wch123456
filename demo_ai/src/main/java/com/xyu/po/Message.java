package com.xyu.po;
public class Message {
    private Long id;
    private String message;
    private String answer;
    // 默认构造方法
    public Message() {}
    // 构造方法
    public Message(Long id, String message, String answer) {
        this.id = id;
        this.message = message;
        this.answer = answer;
    }
    // Getter 和 Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
