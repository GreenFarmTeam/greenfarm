package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.FarmTypeDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmType;
import com.nchu.ruanko.greenfarm.service.FarmTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmTypeServiceImpl implements FarmTypeService {
    @Autowired
    private FarmTypeDAO farmTypeDAO;

    @Override
    public FarmType loadFarmTypeById(String classificationId) {
        return farmTypeDAO.getFarmTypeByFarmTypeUID(classificationId);
    }


}
