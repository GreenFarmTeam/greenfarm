package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.User;

public interface UserService {

    /**
     * 增加 User
     *
     * 注册时候使用
     *
     * @param user User
     */
    void addUser(User user);

    /**
     * 修改昵称
     *
     * @param nickname 昵称
     * @param uid UID
     */
    void modifyUserNicknameByUID(String nickname, String uid);

    /**
     * 修改真实姓名和身份证号
     *
     * 实名认证时候使用
     *
     * @param realname 真实姓名
     * @param idcard 身份证号
     * @param uid UID
     */
    void modifyUserRealnameAndIdcardByUID(String realname, String idcard, String uid);

    /**
     * 修改邮箱
     *
     * 绑定（含修改绑定）邮箱时候使用
     *
     * @param mail 邮箱
     * @param uid UID
     */
    void modifyUserMailByUID(String mail, String uid);

    /**
     * 修改手机号码
     *
     * 绑定（含修改绑定）手机号码时候使用
     *
     * @param phone 手机号码
     * @param uid UID
     */
    void modifyUserPhoneByUID(String phone, String uid);

    /**
     * 修改密码
     *
     * @param password 密码
     * @param uid UID
     */
    void modifyUserPasswordByUID(String password, String uid);

    /**
     * 获取 User，依据是 UID
     *
     * @param uid UID
     * @return User
     */
    User getUserByUID(String uid);

    /**
     * 获取 User，依据是会员号/邮箱/手机号与密码
     *
     * @param key 会员号/邮箱/手机号
     * @param password 密码
     * @return User
     */
    User getUserByKeyAndPassword(String key, String password);

    /**
     * 获取 User，依据是手机号
     *
     * @param phone 手机号码
     * @return User
     */
    User getUserByPhone(String phone);

    /**
     * 获取 User，依据是身份证号和真实姓名
     *
     * @param idcard 身份证号
     * @param realname 真实姓名
     * @return User
     */
    User getUserByIdcardAndRealname(String idcard, String realname);

    /**
     * 获取 User，依据是会员号/邮箱/手机号
     *
     * @param key 会员号/邮箱/手机号
     * @return User
     */
    User getUserByKey(String key);

    /**
     * 判断邮箱是否被使用过（被账号绑定过）
     *
     * @param mail 邮箱
     * @return 当前邮箱是否已经被使用过，true 未被使用过；false 被使用过
     */
    boolean checkUniqueUserWithMail(String mail);

    /**
     * 判断手机号码是否被使用过（被账号绑定过）
     *
     * @param phone 手机号码
     * @return 当前手机号码是否已经被使用，true 未被使用；false 被使用过
     */
    boolean checkUniqueUserWithPhone(String phone);

    /**
     * 判断身份证号是否被使用过（被账号绑定过）
     *
     * @param idcard 身份证号
     * @return 当前身份证号是否已经被使用，true 未被使用；false 被使用过
     */
    boolean checkUniqueUserWithIdcard(String idcard);

}