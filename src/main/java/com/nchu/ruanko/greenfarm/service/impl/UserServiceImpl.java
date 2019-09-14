package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.UserDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.UserService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    private static final String MAIL_CHAR = "@";

    private static final String USERNAME_CHAR1 = "m";

    private static final String USERNAME_CHAR2 = "p";

    /**
     * 将数据进行加密等操作处理后再交给持久层
     *
     * @param user User
     */
    @Override
    public void addUser(User user) {
        user.setUserPassword(StringUtils.encodeMd5(user.getUserPassword()));
        user.setUserTime(new Date(System.currentTimeMillis()));
        user.setUserIsBusiness(0);
        user.setUserRealname(null);
        user.setUserIdcard(null);
        user.setUserPhoto(null);
        // 注册时采用“邮箱”
        if (user.getUserMail() != null) {
            String username = generateDefaultUsername(user.getUserMail());
            user.setUserUsername(username);
            user.setUserNickname(username);
            user.setUserMail(StringUtils.encodeBase64(user.getUserMail()));
            user.setUserPhone(null);
        }
        // 注册时采用“手机号”
        if (user.getUserPhone() != null) {
            String username = generateDefaultUsername(user.getUserPhone());
            user.setUserUsername(username);
            user.setUserNickname(username);
            user.setUserPhone(StringUtils.encodeBase64(user.getUserPhone()));
            user.setUserMail(null);
        }
        userDAO.insertUser(user);
    }

    /**
     *
     * @param nickname
     * @param uid
     */
    @Override
    public void modifyUserNicknameByUID(String nickname, String uid) {
        userDAO.updateUserNicknameByUID(nickname, uid);
    }

    /**
     *
     * @param realname
     * @param idcard
     * @param uid
     */
    @Override
    public void modifyUserRealnameAndIdcardByUID(String realname, String idcard, String uid) {
        userDAO.updateUserRealnameAndIdcardByUID(StringUtils.encodeBase64(realname), StringUtils.encodeBase64(idcard), uid);
    }

    /**
     *
     * @param mail
     * @param uid
     */
    @Override
    public void modifyUserMailByUID(String mail, String uid) {
        userDAO.updateUserMailByUID(StringUtils.encodeBase64(mail), uid);
    }

    /**
     *
     * @param phone
     * @param uid
     */
    @Override
    public void modifyUserPhoneByUID(String phone, String uid) {
        userDAO.updateUserPhoneByUID(StringUtils.encodeBase64(phone), uid);
    }

    /**
     *
     * @param password
     * @param uid
     */
    @Override
    public void modifyUserPasswordByUID(String password, String uid) {
        userDAO.updateUserPasswordByUID(StringUtils.encodeMd5(password), uid);
    }

    /**
     * 密码登录验证
     *
     * @param key 登录依据 会员名/手机号/邮箱
     * @param password 密码
     * @return User
     */
    @Override
    public User getUserByKeyAndPassword(String key, String password) {
        User user;
        if (key.contains(MAIL_CHAR)) {
            user = userDAO.getUserByMailAndPassword(StringUtils.encodeBase64(key), StringUtils.encodeMd5(password));
        } else {
            if (key.contains(USERNAME_CHAR1) || key.contains(USERNAME_CHAR2)) {
                user = userDAO.getUserByUsernameAndPassword(key, StringUtils.encodeMd5(password));
            } else {
                user = userDAO.getUserByPhoneAndPassword(StringUtils.encodeBase64(key), StringUtils.encodeMd5(password));
            }
        }
        return user;
    }

    @Override
    public User getUserByUID(String uid) {
        return userDAO.getUserByUID(uid);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userDAO.getUserByPhone(StringUtils.encodeBase64(phone));
    }

    /**
     * 判断该邮箱是否已被注册
     *
     * @param mail 邮箱
     * @return 是否已被注册
     */
    @Override
    public boolean checkUniqueUserWithMail(String mail) {
        return userDAO.countUserByMail(StringUtils.encodeBase64(mail)) == 0;
    }

    /**
     * 判断该手机号是否已被注册
     *
     * @param phone 手机号
     * @return 是否已被注册
     */
    @Override
    public boolean checkUniqueUserWithPhone(String phone) {
        return userDAO.countUserByPhone(StringUtils.encodeBase64(phone)) == 0;
    }

    /**
     *
     * @param idcard
     * @return
     */
    @Override
    public boolean checkUniqueUserWithIdcard(String idcard) {
        return userDAO.countUserByIdcard(StringUtils.encodeBase64(idcard)) == 0;
    }

    /**
     * 基于注册时所用的“邮箱”或“手机”生成“会员号”
     *
     * @param str 邮箱或手机号
     * @return 会员号
     */
    private String generateDefaultUsername(String str) {
        String result;
        if (str.contains(MAIL_CHAR)) {
            result = str.substring(str.lastIndexOf("@") + 1, str.lastIndexOf(".")).replace(".", "");
            result = result + str.substring(0, str.lastIndexOf("@"));
            result = "m" + result;
        } else {
            result = "p" + str;
        }
        return result;
    }

}