package com.nchu.ruanko.greenfarm.controller.management.admin;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import com.nchu.ruanko.greenfarm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "management.admin.AdminBusinessController", description = "")
@Controller
public class AdminBusinessController {


    @Autowired
    private BusinessService businessService;

    @Autowired
    private UserService userService;

    private static final int PAGE_NAVIGATION_SIZE = 10;

    @ApiOperation(value = "adminReviewBusinessPage", notes = "")
    @GetMapping(value = "/greenfarm/admin/management/business/review")
    public ModelAndView adminReviewBusinessPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "1") int pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/review-business");
        modelAndView.addObject("vo", businessService.listBusinessReviewsWithPage(pageNum, pageSize, PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

    @ApiOperation(value = "adminReviewBusinessDetailPage", notes = "")
    @GetMapping(value = "/greenfarm/admin/management/business/review/detail/{reviewUid}")
    public ModelAndView adminReviewBusinessDetailPage(@PathVariable(name = "reviewUid") String reviewUid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/review-business-detail");
        modelAndView.addObject("vo", businessService.getBusinessReviewDetailByReviewUID(reviewUid));
        return modelAndView;
    }

    @ApiOperation(value = "adminAgreeReview", notes = "")
    @GetMapping(value = "/greenfarm/admin/management/business/review/agree/{reviewUid}")
    @ResponseBody
    public String adminAgreeReview(@PathVariable(name = "reviewUid") String reviewUid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String userUid = businessService.agreeApply(reviewUid);
        HttpSession session = request.getSession();
        session.setAttribute("user", userService.getUserByUID(userUid));
        json.put("flag", true);
        return json.toString();
    }

    @ApiOperation(value = "disagreeReviewPage", notes = "")
    @GetMapping("/greenfarm/admin/management/business/review/disagree/{reviewUid}")
    public ModelAndView disagreeReviewPage(@PathVariable(name = "reviewUid") String reviewUid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/review-business-disagree-reason");
        modelAndView.addObject("reviewUid", reviewUid);
        return modelAndView;
    }

    @ApiOperation(value = "disagreeReviewOperation", notes = "")
    @PostMapping(value = "/greenfarm/admin/management/business/review/disagree/{reviewUid}/operation")
    @ResponseBody
    public String disagreeReviewOperation(@PathVariable(name = "reviewUid") String reviewUid, @RequestParam(name = "reason") String reason) {
        JSONObject json = new JSONObject();
        businessService.disagree(reviewUid, reason);
        json.put("flag", true);
        return json.toString();
    }




}
