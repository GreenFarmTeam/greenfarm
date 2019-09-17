package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Administrator;

public interface AdministratorService {

    Administrator getAdministratorByUsernameAndPassword(String username, String password);

}
