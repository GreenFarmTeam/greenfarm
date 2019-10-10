package com.nchu.ruanko.greenfarm;

import com.nchu.ruanko.greenfarm.dao.*;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessScope;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminBusinessReviewPageVO;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminFarmVo;
import com.nchu.ruanko.greenfarm.service.*;
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


    @Autowired
    private FarmDAO farmDAO;
    @Autowired
    private FarmService service;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BusinessService businessService;


    @Test
    public void contextLoads() {
        userDAO.updateUserNicknameByBusinessUID("王慎瑜先生","b312b63cef28406c8dfe1217565811d8");

       /* System.out.println(farmDAO.listAllNoCheckedFarm());*/
    }

}
