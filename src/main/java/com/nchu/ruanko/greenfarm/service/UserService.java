package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.User;

public interface UserService {

    void addUser(User user);

    void modifyUserNicknameByUID(String nickname, String uid);

    void modifyUserRealnameAndIdcardByUID(String realname, String idcard, String uid);

    void modifyUserMailByUID(String mail, String uid);

    void modifyUserPhoneByUID(String phone, String uid);

    void modifyUserPasswordByUID(String password, String uid);

    User getUserByUID(String uid);

    User getUserByKeyAndPassword(String key, String password);

    User getUserByPhone(String phone);

    User getUserByIdcardAndRealname(String idcard, String realname);

    User getUserByKey(String key);

    boolean checkUniqueUserWithMail(String mail);

    boolean checkUniqueUserWithPhone(String phone);

    boolean checkUniqueUserWithIdcard(String idcard);
}