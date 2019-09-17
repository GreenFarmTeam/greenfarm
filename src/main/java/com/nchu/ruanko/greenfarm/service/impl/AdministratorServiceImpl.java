package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.AdministratorDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Administrator;
import com.nchu.ruanko.greenfarm.service.AdministratorService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorDAO administratorDAO;

    @Override
    public Administrator getAdministratorByUsernameAndPassword(String username, String password) {
        return administratorDAO.getAdministratorByUsernameAndPassword(StringUtils.encodeMd5(username), StringUtils.encodeMd5(password));
    }

}