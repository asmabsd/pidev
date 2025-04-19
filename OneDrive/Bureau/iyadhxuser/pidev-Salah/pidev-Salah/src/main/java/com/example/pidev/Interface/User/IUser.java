package com.example.pidev.Interface.User;

import com.example.pidev.entity.User.User;

import java.util.List;

public interface IUser {

    User saveUser(User user);
    void  deleteUser(User user);
    User getUser(int id);

    User getUser(Long id);

    List<User> getUsers();
}
