package com.example.mycafe30.Model;

public class User {
    private String id_user;
    private String nama;
    private String email;
    private String username;
    private String password;
    private String level;

    public User(){ }

    public User(String id_user, String nama, String email, String username, String password, String level){
        this.id_user = id_user;
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
        this.level = level;
    }
    public String getIdUser() {
        return id_user;
    }

    public void setIdUser(String id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
