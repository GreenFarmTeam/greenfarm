package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.*;
import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmReview;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmType;
import com.nchu.ruanko.greenfarm.service.FarmService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {

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
}