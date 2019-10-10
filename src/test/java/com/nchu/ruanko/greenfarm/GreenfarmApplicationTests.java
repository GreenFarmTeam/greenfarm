package com.nchu.ruanko.greenfarm;

import com.nchu.ruanko.greenfarm.dao.*;
import com.nchu.ruanko.greenfarm.pojo.entity.*;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminBusinessReviewPageVO;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessProductReviewPageVO;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import com.nchu.ruanko.greenfarm.service.MailService;
import com.nchu.ruanko.greenfarm.service.ProductService;
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

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ProvinceDAO provinceDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductImageDAO productImageDAO;

    @Autowired
    private ProductReviewDAO productReviewDAO;

    @Autowired
    private ProductService productService;

    @Autowired
    private FarmTypeDAO farmTypeDAO;

    @Autowired
    private FarmDAO farmDAO;

    @Autowired
    private FarmImageDAO farmImageDAO;

    @Autowired
    private FarmReviewDAO farmReviewDAO;

    @Test
    public void contextLoads() {
//        List<ProductImage> images = productImageDAO.listProductImagesByProductUID("bb6670070a4e4307b79a0a04674c5499");
//        for (ProductImage image : images) {
//            System.out.println(image);
//        }
//        ProductReview review = productReviewDAO.getProductReviewByProductUID("bb6670070a4e4307b79a0a04674c5499");
//        System.out.println(review);
//        BusinessProductReviewPageVO vo = productService.listBusinessProductReviewRecords("176672c18082419caf15213d364a8ca6", 1, 1, 10);
//        System.out.println(vo.getBusinessProductReviewVOList().isEmpty());
//        List<Product> productList = productDAO.listProductsByBusinessUID("176672c18082419caf15213d364a8ca6");
//        System.out.println(productList);
//        List<Product> productList = productDAO.listUnreviewedProduct();
//        System.out.println(productList);
//        List<FarmType> typeList = farmTypeDAO.listFarmTypes();
//        System.out.println(typeList);
//        System.out.println(farmDAO.listFarmsByBusinessUID("176672c18082419caf15213d364a8ca6"));
//        System.out.println(farmImageDAO.getFarmMainImageByFarmUID("06fe449db1d54d1db55cc27212d5db49"));
//        System.out.println(farmImageDAO.listFarmOtherImagesByFarmUID("06fe449db1d54d1db55cc27212d5db49"));
//        System.out.println(farmReviewDAO.getFarmReviewByFarmUID("06fe449db1d54d1db55cc27212d5db49"));
    }

}
