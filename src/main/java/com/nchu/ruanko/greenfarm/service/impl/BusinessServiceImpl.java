package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.BusinessDAO;
import com.nchu.ruanko.greenfarm.dao.BusinessReviewDAO;
import com.nchu.ruanko.greenfarm.dao.BusinessScopeDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessDAO businessDAO;

    @Autowired
    private BusinessScopeDAO businessScopeDAO;

    @Autowired
    private BusinessReviewDAO businessReviewDAO;

    @Override
    public void addBusiness(Business business, String userUid) {
        business.setBusinessIdcardBehind(null);
        business.setBusinessIdcardFront(null);
        business.setBusinessShopState(null);
        business.setUser(null);
        businessDAO.insertBusiness(business, userUid);
    }

    @Override
    public void addBusinessScope(String uid, String businessUid, String typeId) {
        businessScopeDAO.insertBusinessScope(StringUtils.createUUID(), businessUid, typeId);
    }

    @Override
    public void addBusinessReviewRecord(BusinessReview review, String businessUid) {
        businessReviewDAO.insertBusinessReview(review, businessUid);
    }

    @Override
    public void modifyBusinessReApplyByBusinessUID(Business business, String businessUid) {
        business.setBusinessIdcardFront(null);
        business.setBusinessIdcardBehind(null);
        business.setBusinessShopState(null);
        businessDAO.updateBusinessByBusinessUid(business, businessUid);
    }

    @Override
    public String getBusinessUidByUserUID(String userId) {
        return businessDAO.getBusinessUidByUserUID(userId);
    }

    @Override
    public List<BusinessReview> listBusinessReviewsByBusinessUID(String businessUid) {
        return businessReviewDAO.listBusinessReviewsByBusinessUID(businessUid);
    }

    @Override
    public boolean checkUniqueBusinessByUserUID(String userUid) {
        return businessDAO.countBusinessByUserUID(userUid) == 0;
    }

    @Override
    public boolean checkExistUnfinishedBusinessReviewByBusinessUID(String businessUid) {
        return businessReviewDAO.countBusinessReviewUnfinishedByBusinessUID(businessUid) != 0;
    }

}