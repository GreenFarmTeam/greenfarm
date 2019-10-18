package com.nchu.ruanko.greenfarm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.dao.FarmDAO;
import com.nchu.ruanko.greenfarm.dao.FarmImageDAO;
import com.nchu.ruanko.greenfarm.dao.FarmReviewDAO;
import com.nchu.ruanko.greenfarm.dao.OrderFarmDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.*;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessOrderPageVo;
import com.nchu.ruanko.greenfarm.pojo.vo.MemberFarmVo;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderFarmPageVo;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderFarmVo;
import com.nchu.ruanko.greenfarm.service.OrderFarmService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderFarmServiceImpl implements OrderFarmService {
    @Autowired
    private OrderFarmDAO orderFarmDAO;
    @Autowired
    private FarmDAO farmDAO;
    @Autowired
    private FarmImageDAO farmImageDAO;
    @Autowired
    private FarmReviewDAO farmReviewDAO;

    /**
     * 加载所有的预约农场信息
     * @param userUid
     * @return
     */
    @Override
    public List<OrderFarm> listAllOrderFarmsByUserId(String userUid) {
        return orderFarmDAO.selectAllOrderFarmsByUserId(userUid);
    }

    @Override
    public MemberFarmVo getFarmDetailInfoByOrderFarm(String orderFarmUid) {
        MemberFarmVo vo = new MemberFarmVo();
        Farm farm = farmDAO.getFarmByOrderFarmUid(orderFarmUid);
        User user =  farm.getBusiness().getUser();
        user.setUserRealname(StringUtils.decodeBase64(farm.getBusiness().getUser().getUserRealname()));
        Business business = farm.getBusiness();
        business.setUser(user);
        farm.setBusiness(business);
        vo.setFarm(farm);

        FarmImage mainFarmImage = farmImageDAO.getFarmMainImageByFarmUID(farm.getFarmUid());
        if(mainFarmImage==null){
            vo.setMainImage(new FarmImage());
        }else
            vo.setMainImage(mainFarmImage);
        vo.setOtherImages(farmImageDAO.listFarmOtherImagesByFarmUID(farm.getFarmUid()));
        vo.setReview(farmReviewDAO.getFarmReviewByFarmUID(farm.getFarmUid()));
        return vo;
    }

    /**
     * 商家加载农场预约相关的信息
     * @param userUid
     * @param pageNum
     * @param pageSize
     * @param pageNavigationSize
     * @return
     */
    @Override
    public OrderFarmPageVo loadAllOrderFarmsOfBusiness(String userUid, int pageNum, int pageSize, int pageNavigationSize) {
        List<OrderFarm> orderFarmList = orderFarmDAO.loadAllOrderFarmsOfBusinessByUserId(userUid);
        OrderFarmPageVo orderFarmPageVo = new OrderFarmPageVo();
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<OrderFarm> pageInfo = new PageInfo<>(orderFarmList, pageNavigationSize);
        List<OrderFarmVo> orderFarmVoList = new ArrayList<>();
        for(OrderFarm orderFarm : orderFarmList){
            OrderFarmVo orderFarmVo = new OrderFarmVo();
            orderFarmVo.setOrderFarm(orderFarm);
            FarmImage mainProductImage = farmImageDAO.getFarmMainImageByFarmUID(orderFarm.getOrderFarmFarm().getFarmUid());
            if(mainProductImage==null){
                orderFarmVo.setMainFarmImage(new FarmImage());
            }else
                orderFarmVo.setMainFarmImage(mainProductImage);
            orderFarmVoList.add(orderFarmVo);
        }
        orderFarmPageVo.setOrderFarmVoList(orderFarmVoList);
        orderFarmPageVo.setOrderFarmPageInfo(pageInfo);
        return orderFarmPageVo;
    }
}
