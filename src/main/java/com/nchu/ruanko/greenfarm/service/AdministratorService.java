package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Administrator;

public interface AdministratorService {

    /**
     * 管理员登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return Administrator
     */
    Administrator getAdministratorByUsernameAndPassword(String username, String password);

}
