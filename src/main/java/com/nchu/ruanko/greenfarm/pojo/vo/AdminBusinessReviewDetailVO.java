package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessScope;
import java.util.List;


/**
 * “审核记录”详细信息的 VO
 *
 * @author Yuan Yueshun
 */
public class AdminBusinessReviewDetailVO {

    private BusinessReview businessReview;
    private List<BusinessScope> businessScopeList;
    private String userUsername;
    private String userRealName;
    private String userIdcard;
    private String userPhone;
    private String userMail;

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public BusinessReview getBusinessReview() {
        return businessReview;
    }

    public void setBusinessReview(BusinessReview businessReview) {
        this.businessReview = businessReview;
    }

    public List<BusinessScope> getBusinessScopeList() {
        return businessScopeList;
    }

    public void setBusinessScopeList(List<BusinessScope> businessScopeList) {
        this.businessScopeList = businessScopeList;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(String userIdcard) {
        this.userIdcard = userIdcard;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    @Override
    public String toString() {
        return "AdminBusinessReviewDetailVO{" +
                "businessReview=" + businessReview +
                ", businessScopeList=" + businessScopeList +
                ", userUsername='" + userUsername + '\'' +
                ", userRealName='" + userRealName + '\'' +
                ", userIdcard='" + userIdcard + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userMail='" + userMail + '\'' +
                '}';
    }

}