package com.soft.backapp.model;

public class MyUserBuilder {
    private Long id = null;
    private String username = null;
    private String password = null;
    private String firstName = null;
    private String lastName = null;
    private String email = null;

    public MyUserBuilder Id(Long id) {
        this.id = id;
        return this;
    }
    public MyUserBuilder username(String username) {
        this.username = username;
        return this;
    }
    public MyUserBuilder password(String password) {
        this.password = password;
        return this;
    }
    public MyUserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    public MyUserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    public MyUserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public MyUser build() {
        MyUser user = new MyUser(id, username, password, firstName, lastName, email);
        return user;
    }
}
