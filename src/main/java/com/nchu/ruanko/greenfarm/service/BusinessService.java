package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessScope;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminBusinessLegalPageVO;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminBusinessReviewDetailVO;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminBusinessReviewPageVO;
import java.util.List;

public interface BusinessService {

    void addBusiness(Business business, String userUid);

    void addBusinessScope(String uid, String businessUid, String typeId);

    void addBusinessReviewRecord(BusinessReview review, String businessUid);

    void modifyBusinessReApplyByBusinessUID(Business business, String businessUid);

    String agreeApply(String reviewUid);

    void disagree(String reviewUid, String reason);

    String getBusinessUIDByUserUID(String userId);

    Business getBusinessByUserUID(String userId);

    Business getBusinessDetailByBusinessUID(String businessUid);

    List<BusinessReview> listBusinessReviewsByBusinessUID(String businessUid);

    List<BusinessScope> listBusinessScopesByBusinessUID(String businessUid);

    AdminBusinessReviewPageVO listBusinessReviewsWithPage(int pageNum, int pageSize, int navigationSize);

    AdminBusinessReviewDetailVO getBusinessReviewDetailByReviewUID(String reviewUid);

    AdminBusinessLegalPageVO listAllLegalBusinessWithPage(int pageNum, int pageSize, int pageNavigationSize);

    boolean checkUniqueBusinessByUserUID(String userUid);

    boolean checkExistUnfinishedBusinessReviewByBusinessUID(String businessUid);


}