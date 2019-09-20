package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.ProvinceDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Province;
import com.nchu.ruanko.greenfarm.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ProvinceDAO provinceDAO;

    /**
     * 获取全部“省份”
     *
     * @return List
     */
    @Override
    public List<Province> listAllProvinces() {
        return provinceDAO.listAllProvinces();
    }

}
