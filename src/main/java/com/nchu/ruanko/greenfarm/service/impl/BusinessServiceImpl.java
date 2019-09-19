package com.nchu.ruanko.greenfarm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.dao.BusinessDAO;
import com.nchu.ruanko.greenfarm.dao.BusinessReviewDAO;
import com.nchu.ruanko.greenfarm.dao.BusinessScopeDAO;
import com.nchu.ruanko.greenfarm.dao.UserDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminBusinessReviewDetailVO;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminBusinessReviewPageVO;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessDAO businessDAO;

    @Autowired
    private BusinessScopeDAO businessScopeDAO;

    @Autowired
    private BusinessReviewDAO businessReviewDAO;

    @Autowired
    private UserDAO userDAO;

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
        businessDAO.updateBusinessByBusinessUID(business, businessUid);
    }

    @Override
    public String agreeApply(String reviewUid) {
        businessReviewDAO.updateReviewTimeByReviewUID(new Date(System.currentTimeMillis()), reviewUid);
        businessReviewDAO.updateReviewResultByReviewUID(1, reviewUid);
        businessReviewDAO.updateReviewReasonByReviewUID(null, reviewUid);
        String businessUid = businessReviewDAO.getBusinessReviewByReviewUID(reviewUid).getBusiness().getBusinessUid();
        businessDAO.updateBusinessShopStateByBusinessUID(0, businessUid);
        String userUid = businessDAO.getBusinessByUID(businessUid).getUser().getUserUid();
        userDAO.updateUserIsBusinessByUID(1, userUid);
        return userUid;
    }

    @Override
    public void disagree(String reviewUid, String reason) {
        businessReviewDAO.updateReviewTimeByReviewUID(new Date(System.currentTimeMillis()), reviewUid);
        businessReviewDAO.updateReviewResultByReviewUID(0, reviewUid);
        businessReviewDAO.updateReviewReasonByReviewUID(reason, reviewUid);
        String businessUid = businessReviewDAO.getBusinessReviewByReviewUID(reviewUid).getBusiness().getBusinessUid();
        businessDAO.updateBusinessShopStateByBusinessUID(null, businessUid);
        businessScopeDAO.deleteBusinessScopeByBusinessUID(businessUid);
        String userUid = businessDAO.getBusinessByUID(businessUid).getUser().getUserUid();
        userDAO.updateUserIsBusinessByUID(0, userUid);
    }

    @Override
    public String getBusinessUIDByUserUID(String userId) {
        return businessDAO.getBusinessUIDByUserUID(userId);
    }

    @Override
    public Business getBusinessByUserUID(String userUid) {
        return businessDAO.getBusinessByUserUID(userUid);
    }

    @Override
    public List<BusinessReview> listBusinessReviewsByBusinessUID(String businessUid) {
        return businessReviewDAO.listBusinessReviewsByBusinessUID(businessUid);
    }

    @Override
    public AdminBusinessReviewPageVO listBusinessReviewsWithPage(int pageNum, int pageSize, int navigationSize) {
        AdminBusinessReviewPageVO vo = new AdminBusinessReviewPageVO();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessReview> businessReviewList = businessReviewDAO.listUnfinishedBusinessReviews();
        PageInfo<BusinessReview> pageInfo = new PageInfo<>(businessReviewList, navigationSize);
        vo.setBusinessReviewList(businessReviewList);
        vo.setPageInfo(pageInfo);
        return vo;
    }

    @Override
    public AdminBusinessReviewDetailVO getBusinessReviewDetailByReviewUID(String reviewUid) {
        AdminBusinessReviewDetailVO vo = new AdminBusinessReviewDetailVO();
        BusinessReview businessReview = businessReviewDAO.getBusinessReviewByReviewUID(reviewUid);
        User user = businessReview.getBusiness().getUser();
        vo.setBusinessReview(businessReview);
        vo.setBusinessScopeList(businessScopeDAO.listBusinessScopesByBusinessUID(businessReview.getBusiness().getBusinessUid()));
        vo.setUserUsername(user.getUserUsername());
        vo.setUserRealName(StringUtils.decodeBase64(user.getUserRealname()));
        vo.setUserIdcard(StringUtils.decodeBase64(user.getUserIdcard()));
        if (user.getUserMail() != null) {
            vo.setUserMail(StringUtils.decodeBase64(user.getUserMail()));
        }
        if (user.getUserPhone() != null) {
            vo.setUserPhone(StringUtils.decodeBase64(user.getUserPhone()));
        }
        return vo;
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