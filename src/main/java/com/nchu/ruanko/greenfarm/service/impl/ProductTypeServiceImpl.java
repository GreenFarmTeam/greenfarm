package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.FarmTypeDAO;
import com.nchu.ruanko.greenfarm.dao.ProductTypeDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmType;
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

    /**
     * 根据产品类型ID获取产品类型
     * @return
     */
    @Override
    public ProductType loadProuctTypeById(String productId) {
        return productTypeDAO.getProductTypeByUID(productId);
    }


}