package com.nchu.ruanko.greenfarm;

import com.nchu.ruanko.greenfarm.dao.BusinessDAO;
import com.nchu.ruanko.greenfarm.dao.BusinessReviewDAO;
import com.nchu.ruanko.greenfarm.dao.BusinessScopeDAO;
import com.nchu.ruanko.greenfarm.dao.ProductTypeDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessScope;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import com.nchu.ruanko.greenfarm.service.MailService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreenfarmApplicationTests {


//    @Autowired
//    private UserDAO userDAO;
//


//    @Autowired
//    private MailService mailService;

//    @Autowired
//    private ProductTypeDAO productTypeDAO;
//
    @Autowired
    private BusinessScopeDAO businessScopeDAO;

    @Autowired
    private BusinessDAO businessDAO;

    @Autowired
    private BusinessReviewDAO businessReviewDAO;

    @Test
    public void contextLoads() {
//        User user1 = userDAO.getUserByUsernameAndPassword("2", "3");
//        System.out.println(user1);
//        User user2 = userDAO.getUserByPhoneAndPassword("51", "7");
//        System.out.println(user2);
//        User user3 = userDAO.getUserByMailAndPassword("6", "3");
//        System.out.println(user3);
//        User user4 = userDAO.getUserByUsernameAndPassword("a", "b");
//        System.out.println(user4);
//        user3.setUserUid("100");
//        user3.setUserIsBusiness(1);
//        userDAO.insertUser(user3);
//        List<ProductType> productTypes = productTypeDAO.listAllProductTypes();
//        System.out.println(productTypes);
//        List<BusinessScope> businessScopeList = businessScopeDAO.listBusinessScopesByBusinessUID("ff238f7eae8b4953b9ad923191e989f7");
//        System.out.println(businessScopeList);
//        System.out.println(businessDAO.getBusinessByUID("252b52398a594740b608cd618870c169"));
//        System.out.println(businessDAO.countBusinessByUserUID("252b52398a594740b608cd618870c169"));
//        System.out.println(businessDAO.countBusinessByUserUID("1d0602ec9316495182f6506a2dbec573"));
//        BusinessReview review = new BusinessReview();
//        review.setReviewUid(StringUtils.createUUID());
//        review.setReviewSubmitTime(new Date());
//        businessReviewDAO.insertBusinessReview(review, "ff238f7eae8b4953b9ad923191e989f7");
        List<BusinessReview> businessReviewList = businessReviewDAO.listBusinessReviewsByBusinessUID("aac58037374a4ba4afa8274c8256dff4");
        System.out.println(businessReviewList);

    }

}
