package com.example.model;

public class User {
    private final String login;  // unique
    private final String nickname;
    private final String passwordHash;   // BCrypt hash

    public User(String login, String nickname, String passwordHash) {
        this.login = login;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
    }

    public String getLogin()      { return login; }
    public String getNickname()   { return nickname; }
    public String getPasswordHash() { return passwordHash; }
}