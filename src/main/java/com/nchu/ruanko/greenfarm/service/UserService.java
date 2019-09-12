package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.User;

public interface UserService {

    void addUser(User user);

    User getUserByKeyAndPassword(String key, String password);

    boolean checkUniqueUserWithMail(String mail);

    boolean checkUniqueUserWithPhone(String phone);
}