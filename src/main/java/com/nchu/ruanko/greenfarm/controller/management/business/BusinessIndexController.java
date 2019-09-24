package com.nchu.ruanko.greenfarm.controller.management.business;

import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "management.business.BusinessIndexController", description = "“商家主页”的控制器")
@Controller
public class BusinessIndexController {

    @Autowired
    private BusinessService businessService;

    /**
     * 跳转至“商家中心”的主页
     *
     * @param request HTTP 请求
     * @return ModelAndView
     */
    @ApiOperation(value = "businessManageIndexPage", notes = "跳转至“商家中心”的主页")
    @GetMapping(value = "/business/management/index")
    public ModelAndView businessManageIndexPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Business business = businessService.getBusinessByUserUID(user.getUserUid());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/business/index");
        modelAndView.addObject("scopes", businessService.listBusinessScopesByBusinessUID(business.getBusinessUid()));
        session.setAttribute("business", business);
        return modelAndView;
    }

}