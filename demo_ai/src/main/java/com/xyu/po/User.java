package com.xyu.po;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    // 默认构造函数
    public User() {
    }
    // 带参数的构造函数
    public User(String username, String password, LocalDateTime createdAt) {
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();  // 设置创建时间为当前时间
        }
    }
}
