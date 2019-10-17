package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmType;

public interface FarmTypeService {
    FarmType loadFarmTypeById(String classificationId);
}
