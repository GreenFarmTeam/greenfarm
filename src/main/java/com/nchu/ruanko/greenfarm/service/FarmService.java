package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmType;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessFarmReviewPageVO;

import java.util.List;

public interface FarmService {

    boolean businessCanFarm(String businessUid);

    List<FarmType> listFarmTypes();

    void businessAddFarm(Farm farm, FarmImage mainImage, List<FarmImage> otherImages, String farmTypeUid, String businessUid);

    BusinessFarmReviewPageVO businessListFarmReviewRecords(String businessUid, int pageNum, int pageSize, int navigationSize);

    

}