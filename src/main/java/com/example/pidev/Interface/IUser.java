package com.example.pidev.Interface;


import com.example.pidev.entity.User;

import java.util.List;

public interface IUser {

    User saveUser(User user);
    void  deleteUser(User user);
    User getUser(int id);
    List<User> getUsers();
}
