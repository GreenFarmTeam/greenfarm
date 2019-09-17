package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.ProductTypeDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import com.nchu.ruanko.greenfarm.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeDAO productTypeDAO;

    @Override
    public List<ProductType> listAllProductTypes() {
        return productTypeDAO.listAllProductTypes();
    }

}