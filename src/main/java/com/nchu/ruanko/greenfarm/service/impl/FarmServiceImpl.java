package com.nchu.ruanko.greenfarm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.dao.*;
import com.nchu.ruanko.greenfarm.pojo.entity.*;
import com.nchu.ruanko.greenfarm.pojo.vo.*;
import com.nchu.ruanko.greenfarm.service.FarmService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import sun.security.krb5.internal.PAData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    private static final int UP_state = 11;
    private static final int UNCHECKED_state = 00;
    private static final int ADMIN_DOWN_state = 10;
    private static final int BUSINESS_DOWN_state = 01;
    private static final int FAIL_CHECK_state = 3;


    @Autowired
    private BusinessScopeDAO businessScopeDAO;

    @Autowired
    private FarmTypeDAO farmTypeDAO;

    @Autowired
    private FarmDAO farmDAO;

    @Autowired
    private FarmImageDAO farmImageDAO;

    @Autowired
    private FarmReviewDAO farmReviewDAO;


    @Override
    public boolean businessCanFarm(String businessUid) {
        return businessScopeDAO.countBusinessScopeFarmByBusinessUID(businessUid) == 1;
    }

    @Override
    public List<FarmType> listFarmTypes() {
        return farmTypeDAO.listFarmTypes();
    }

    @Override
    public void businessAddFarm(Farm farm, FarmImage mainImage, List<FarmImage> otherImages, String farmTypeUid, String businessUid) {
        String farmUid = StringUtils.createUUID();
        farm.setFarmUid(farmUid);
        farm.setFarmUpDate(null);
        farmDAO.insertFarm(farm, farmTypeUid, businessUid);

        if (mainImage != null) {
            mainImage.setFarmImageIsMainImage(1);
            farmImageDAO.insertFarmImage(mainImage, farmUid);
        }

        if (otherImages.size() != 0) {
            for (FarmImage otherImage : otherImages) {
                otherImage.setFarmImageIsMainImage(0);
                farmImageDAO.insertFarmImage(otherImage, farmUid);
            }
        }

        FarmReview farmReview = new FarmReview();
        farmReview.setFarmReviewUid(StringUtils.createUUID());
        farmReview.setFarmReviewSubmitTime(new Date(System.currentTimeMillis()));
        farmReview.setFarmReviewReviewTime(null);
        farmReview.setFarmReviewResult(null);
        farmReview.setFarmReviewReason(null);
        farmReviewDAO.insertFarmReview(farmReview, farmUid);
    }

    /**
     * 商家加载农场审核记录
     * @param businessUid
     * @param pageNum
     * @param pageSize
     * @param navigationSize
     * @return
     */
    @Override
    public BusinessFarmReviewPageVO businessListFarmReviewRecords(String businessUid, int pageNum, int pageSize, int navigationSize) {
        BusinessFarmReviewPageVO vo = new BusinessFarmReviewPageVO();

        PageHelper.startPage(pageNum, pageSize);
        List<Farm> farmList = farmDAO.listFarmsByBusinessUID(businessUid);
        PageInfo<Farm> pageInfo = new PageInfo<>(farmList, navigationSize);

        List<BusinessFarmReviewVO> businessFarmReviewPageVOList = new ArrayList<>();
        for (Farm farm : farmList) {
            BusinessFarmReviewVO reviewVO = new BusinessFarmReviewVO();
            reviewVO.setFarm(farm);
            reviewVO.setMainImage(farmImageDAO.getFarmMainImageByFarmUID(farm.getFarmUid()));
            reviewVO.setOtherImageList(farmImageDAO.listFarmOtherImagesByFarmUID(farm.getFarmUid()));
            reviewVO.setFarmReview(farmReviewDAO.getFarmReviewByFarmUID(farm.getFarmUid()));
            businessFarmReviewPageVOList.add(reviewVO);
        }

        vo.setPageInfo(pageInfo);
        vo.setBusinessFarmReviewVOList(businessFarmReviewPageVOList);
        return vo;
    }

    /**
     * 管理员获取所有的农场
     * @param pageNum
     * @param pageSize
     * @param pageNavigationSize
     * @param state 0未审核状态  11上架状态 1农场主下架状态 10管理员下架状态 2已审核状态
     * @return
     */
    @Override
    public AdminFarmPageVO adminListFarm(int pageNum, int pageSize, int pageNavigationSize,int state) {
        AdminFarmPageVO vo = new AdminFarmPageVO();
        PageHelper.startPage(pageNum,pageSize);
        List<Farm> farmList=null;
        if(state==2)
            farmList = farmDAO.listAllCheckedFarm();
        else if(state==0)
            farmList = farmDAO.listAllNoCheckedFarm();
        else if(state==1)
            farmList = farmDAO.listAllBusinessDownFarm();
        else if(state==11)
            farmList = farmDAO.listAllUpFarm();
        else if(state==10)
            farmList = farmDAO.listAllAdminDownFarm();
        /**对farm信息进行分页展示**/
        PageInfo<Farm> farmInfo =new PageInfo<>(farmList,pageNavigationSize) ;

        List<AdminFarmVo> AdminFarmPageVOList = new ArrayList<>();

        for(Farm farm : farmList){
            AdminFarmVo adminFarmVo = new AdminFarmVo();
            adminFarmVo.setFarm(farm);
            adminFarmVo.setReview(farmReviewDAO.getFarmReviewByFarmUID(farm.getFarmUid()));
            adminFarmVo.setMainImage(farmImageDAO.getFarmMainImageByFarmUID(farm.getFarmUid()));
            adminFarmVo.setOtherImages(farmImageDAO.getFarmOtherImageByFarmUID(farm.getFarmUid()));
            AdminFarmPageVOList.add(adminFarmVo);
        }
        vo.setPageInfo(farmInfo);
        vo.setAdminFarmVOList(AdminFarmPageVOList);

        return vo;
    }


    /**
     * 商家获取所有的农场
     * @param pageNum
     * @param pageSize
     * @param pageNavigationSize
     * @param state 0未审核状态  11上架状态 1农场主下架状态 10管理员下架状态 2已审核状态
     * @return
     */
    @Override
    public BusinessFarmPageVO businessListFarm(String businessUid,int pageNum, int pageSize, int pageNavigationSize,int state) {
        BusinessFarmPageVO vo = new BusinessFarmPageVO();
        PageHelper.startPage(pageNum,pageSize);
        List<Farm> farmList=null;
        if(state==2)
            farmList = farmDAO.listAllCheckedFarmByBusinessUid(businessUid);
        else if(state==0)
            farmList = farmDAO.listAllNoCheckedFarmByBusinessUid(businessUid);
        else if(state==1)
            farmList = farmDAO.listAllBusinessDownFarmByBusinessUid(businessUid);
        else if(state==11)
            farmList = farmDAO.listAllUpFarmByBusinessUid(businessUid);
        else if(state==10)
            farmList = farmDAO.listAllAdminDownFarmByBusinessUid(businessUid);
        /**对farm信息进行分页展示**/
        PageInfo<Farm> farmInfo =new PageInfo<>(farmList,pageNavigationSize) ;

        List<BusinessFarmVO> businessFarmVOList = new ArrayList<>();

        for(Farm farm : farmList){
            BusinessFarmVO businessFarmVO = new BusinessFarmVO();
            businessFarmVO.setFarm(farm);
            businessFarmVO.setReview(farmReviewDAO.getFarmReviewByFarmUID(farm.getFarmUid()));
            businessFarmVO.setMainImage(farmImageDAO.getFarmMainImageByFarmUID(farm.getFarmUid()));
            businessFarmVO.setOtherImages(farmImageDAO.getFarmOtherImageByFarmUID(farm.getFarmUid()));
            businessFarmVOList.add(businessFarmVO);
        }
        vo.setPageInfo(farmInfo);
        vo.setBusinessFarmVOList(businessFarmVOList);

        return vo;
    }

    /**
     * 加载农场详情
     * @param farmUid
     * @return
     */
    @Override
    public AdminFarmVo adminGetFarmByFarmUID(String farmUid) {
        AdminFarmVo vo = new AdminFarmVo();
        vo.setFarm(farmDAO.getFarmByFarmUID(farmUid));
        vo.setMainImage(farmImageDAO.getFarmMainImageByFarmUID(farmUid));
        vo.setOtherImages(farmImageDAO.listFarmOtherImagesByFarmUID(farmUid));
        /*System.out.println("farmUid"+farmUid);*/

        vo.setReview(farmReviewDAO.getFarmReviewByFarmUID(farmUid));
        return vo;

    }

    /**
     * 管理员同意上架某农场
     * -- rev_result = NULL：申请还未被审核，审核中
     * -- rev_result = 0：申请审核未通过
     * -- rev_result = 1：申请审核通过
     * @param farmReviewUid
     */
    @Override
    public void adminAgreeFarmReview(String farmReviewUid) {
        String farmUid = farmReviewDAO.getFarmIdByFarmReviewId(farmReviewUid);
        Date date = new Date(System.currentTimeMillis());
        farmReviewDAO.updateFarmReviewReviewTimeByFarmReviewUID(date, farmReviewUid);
        farmDAO.updateFarmUpDateByFarmUID(date, farmUid);
        farmDAO.updateFarmState(UP_state,farmUid);
        farmReviewDAO.updateFarmReviewResultAndFarmReviewReasonByFarmReviewUID(1, null, farmReviewUid);

    }

    /**
     * 管理员下架某个农场
     * @param farmUid
     */
    @Override
    public void adminDownFarm(String farmUid) {
        farmDAO.updateFarmState(ADMIN_DOWN_state,farmUid);

    }
    /**
     * 商家下架某个农场
     * @param farmUid
     */
    @Override
    public void businessDownFarm(String farmUid) {

        farmDAO.updateFarmState(BUSINESS_DOWN_state,farmUid);

    }

    /**
     * 管理员恢复上架某农场
     * @param farmUid
     */
    @Override
    public void adminUpFarm(String farmUid) {
        farmDAO.updateFarmState(UP_state,farmUid);
    }
    /**
     * 商家恢复上架某农场
     * @param farmUid
     */
    @Override
    public void businessUpFarm(String farmUid) {
        farmDAO.updateFarmState(UP_state,farmUid);
    }

    /**
     * 管理员驳回用户农场的发布申请
     * @param reason
     * @param farmReviewUid
     */
    @Override
    public void adminDisagreeFarmReview(String reason, String farmReviewUid) {

        String farmUid = farmReviewDAO.getFarmIdByFarmReviewId(farmReviewUid);
        farmReviewDAO.updateFarmReviewReviewTimeByFarmReviewUID(new Date(System.currentTimeMillis()), farmReviewUid);
        farmReviewDAO.updateFarmReviewResultAndFarmReviewReasonByFarmReviewUID(0, reason, farmReviewUid);
        farmDAO.updateFarmState(FAIL_CHECK_state,farmUid);
    }




}