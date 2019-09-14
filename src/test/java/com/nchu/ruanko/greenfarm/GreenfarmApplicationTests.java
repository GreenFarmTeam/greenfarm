package com.nchu.ruanko.greenfarm;

import com.nchu.ruanko.greenfarm.service.MailService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreenfarmApplicationTests {


//    @Autowired
//    private UserDAO userDAO;
//
//    @Autowired
//    private BusinessDAO businessDAO;

    @Autowired
    private MailService mailService;

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

//        Business admin = businessDAO.getBusinessByUsernameAndPassword("2", "33");
//        System.out.println(admin);

    }

}
