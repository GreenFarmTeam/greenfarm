package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.OrderFarm;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessOrderPageVo;
import com.nchu.ruanko.greenfarm.pojo.vo.MemberFarmVo;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderFarmPageVo;

import java.util.List;

public interface OrderFarmService {
    List<OrderFarm> listAllOrderFarmsByUserId(String userUid);

    MemberFarmVo getFarmDetailInfoByOrderFarm(String orderFarmUid);

    OrderFarmPageVo loadAllOrderFarmsOfBusiness(String userUid, int pageNum, int pageSize, int pageNavigationSize);


}
