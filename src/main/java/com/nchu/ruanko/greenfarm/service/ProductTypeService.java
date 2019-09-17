package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import java.util.List;

public interface ProductTypeService {

    /**
     * 获取全部的经营范围/产品类型信息
     *
     * @return List
     */
    List<ProductType> listAllProductTypes();

}
