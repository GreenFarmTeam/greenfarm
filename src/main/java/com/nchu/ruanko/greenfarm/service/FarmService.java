package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmType;
import com.nchu.ruanko.greenfarm.pojo.vo.*;

import java.util.List;

public interface FarmService {

    boolean businessCanFarm(String businessUid);

    List<FarmType> listFarmTypes();

    void businessAddFarm(Farm farm, FarmImage mainImage, List<FarmImage> otherImages, String farmTypeUid, String businessUid);

    BusinessFarmReviewPageVO businessListFarmReviewRecords(String businessUid, int pageNum, int pageSize, int navigationSize);

    AdminFarmPageVO adminListFarm(int pageNum, int pageSize, int pageNavigationSize,int state);

    AdminFarmVO adminGetFarmByFarmUID(String farmUid);

    void adminAgreeFarmReview(String farmReviewUid);

    void adminDownFarm(String farmUid);

    void businessDownFarm(String farmUid);

    void adminUpFarm(String farmUid);

    void businessUpFarm(String farmUid);

    void adminDisagreeFarmReview(String reason, String farmReviewUid);

    BusinessFarmPageVO businessListFarm(String businessUid, int pageNum, int pageSize, int pageNavigationSize, int i);


    MemberFarmPageVo loadAllFarmsByClassificationID(String classificationId, int pageNum, int pageSize, int pageNavigationSize);

    MemberFarmVo loadFarmByfarmID(String farmID);

    boolean submitOrderFarm(String userUid,String farmId, String name, String phone, String orderDate, String requireInfo);
}
