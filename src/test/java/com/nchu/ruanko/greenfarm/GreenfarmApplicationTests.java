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

import java.text.DecimalFormat;
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
   @Autowired
   private OrderDAO orderDAO;

    @Test
    public void contextLoads() {
        DecimalFormat decimalFormat=new DecimalFormat(".00");
        System.out.println(decimalFormat.format(180.0));

    }

}
