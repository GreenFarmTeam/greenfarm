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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    /**农场的四个状态**/
    private static final int UP_state = 11;
    private static final int UNCHECKED_state = 00;
    private static final int ADMIN_DOWN_state = 10;
    private static final int BUSINESS_DOWN_state = 01;
    private static final int FAIL_CHECK_state = 3;
    /**预定农场记录定单的状态**/
    private static final int UN_Pay_State = 0;
    private static final int WAIT_State = 1;
    private static final int FINISHED_State = 2;

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

    @Autowired
    private OrderFarmDAO orderFarmDAO;


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

        List<AdminFarmVO> AdminFarmPageVOList = new ArrayList<>();

        for(Farm farm : farmList){
            AdminFarmVO adminFarmVo = new AdminFarmVO();
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
    public AdminFarmVO adminGetFarmByFarmUID(String farmUid) {
        AdminFarmVO vo = new AdminFarmVO();
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
    /**
     * 根据农场类别加载农场
     * @param classificationId
     * @param pageNum
     * @param pageSize
     * @param pageNavigationSize
     * @return
     */
    @Override
    public MemberFarmPageVo loadAllFarmsByClassificationID(String classificationId, int pageNum, int pageSize, int pageNavigationSize) {
        MemberFarmPageVo memberFarmPageVo = new MemberFarmPageVo();
        List<MemberFarmVo> memberFarmVoList = new ArrayList<>();
        List<Farm> farmList = farmDAO.selectAllFarmsByClassificationId(classificationId);

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Farm> pageInfo = new PageInfo<>(farmList, pageNavigationSize);
        for(Farm farm : farmList){
            MemberFarmVo memberFarmVo = new MemberFarmVo();
            System.out.println(farm.getFarmUid());
            memberFarmVo.setReview(farmReviewDAO.getFarmReviewByFarmUID(farm.getFarmUid()));
            memberFarmVo.setFarm(farm);

            memberFarmVo.setOtherImages(farmImageDAO.listFarmOtherImagesByFarmUID(farm.getFarmUid()));
            FarmImage mainfarmImage = farmImageDAO.getFarmMainImageByFarmUID(farm.getFarmUid());
            if(mainfarmImage==null){
                memberFarmVo.setMainImage(new FarmImage());
            }else
                memberFarmVo.setMainImage(mainfarmImage);

            memberFarmVoList.add(memberFarmVo);
        }
        memberFarmPageVo.setPageInfo(pageInfo);
        memberFarmPageVo.setMemberFarmVOList(memberFarmVoList);
        return memberFarmPageVo;
    }

    @Override
    public MemberFarmVo loadFarmByfarmID(String farmID) {
        MemberFarmVo vo = new MemberFarmVo();
        vo.setFarm(farmDAO.getFarmByFarmUID(farmID));
        FarmImage mainFarmImage = farmImageDAO.getFarmMainImageByFarmUID(farmID);
        if(mainFarmImage==null){
            vo.setMainImage(new FarmImage());
        }else
            vo.setMainImage(mainFarmImage);
        vo.setOtherImages(farmImageDAO.listFarmOtherImagesByFarmUID(farmID));
        vo.setReview(farmReviewDAO.getFarmReviewByFarmUID(farmID));
        return vo;

    }

    /**
     * 提交预约农场的信息
     * @param userUid
     * @param name
     * @param phone
     * @param orderDate
     * @param requireInfo
     * @return
     */
    @Override
    public boolean submitOrderFarm(String userUid,String farmId, String name, String phone, String orderDate, String requireInfo) {
        if(orderFarmDAO.insertOrderFarmInfo(StringUtils.createUUID(),userUid,farmId,name,phone,orderDate,requireInfo,UN_Pay_State)>=1){
            return true;
        }else

            return false;
    }


}