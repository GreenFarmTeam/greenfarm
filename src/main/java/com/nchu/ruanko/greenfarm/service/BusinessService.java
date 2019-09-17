package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;

import java.util.List;

public interface BusinessService {

    void addBusiness(Business business, String userUid);

    void addBusinessScope(String uid, String businessUid, String typeId);

    void addBusinessReviewRecord(BusinessReview review, String businessUid);

    void modifyBusinessReApplyByBusinessUID(Business business, String businessUid);

    String getBusinessUidByUserUID(String userId);

    List<BusinessReview> listBusinessReviewsByBusinessUID(String businessUid);

    boolean checkUniqueBusinessByUserUID(String userUid);

    boolean checkExistUnfinishedBusinessReviewByBusinessUID(String businessUid);

}