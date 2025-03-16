package com.xyu.Vo;
public class MessageVo {
    private int id;
    private String message;
    private String answer;

    // 默认构造函数
    public MessageVo() {}

    // 带参构造函数
    public MessageVo(int id, String message, String answer) {
        this.id = id;
        this.message = message;
        this.answer = answer;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    // toString 方法
    @Override
    public String toString() {
        return "MessageVo{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
