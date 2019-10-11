package com.nchu.ruanko.greenfarm.controller.management.admin;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.constant.PageConstant;
import com.nchu.ruanko.greenfarm.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "management.admin.AdminMemberController", description = "")
@Controller
public class AdminMemberController {

    @Autowired
    private MemberService memberService;

    private static final int PAGE_NAVIGATION_SIZE = 10;

    /**
     * 加载所有的已审核的合法商家
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="loadAllLegalMember", notes="加载所有的合法的会员")
    @GetMapping(value="/greenfarm/admin/management/member/loadAll")
    public ModelAndView loadAllLegalBusiness(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("vo", memberService.listAllLegalMemberWithPage(pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE));
        modelAndView.setViewName("management/admin/member-list");
        return modelAndView;
    }

    /**
     * 加载所有的不合法的会员
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="loadAllNotLegalMember", notes="加载所有的不合法的会员")
    @GetMapping(value="/greenfarm/admin/management/illegal/member/loadAll")
    public ModelAndView loadAllNotLegalMember(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize){
        System.out.println("加载所有不合法会员");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("vo", memberService.listAllIllegalMemberWithPage(pageNum, pageSize, PAGE_NAVIGATION_SIZE));
        modelAndView.setViewName("member-illegal-list");
        return modelAndView;
    }

    /**
     *
     */
    @ApiOperation(value="loadMemberDetailInfo", notes="加载会员的详细信息")

    @GetMapping(value="/greenfarm/admin/management/member/detail/{memberID}")
    public ModelAndView loadMemberDetailInfo(@PathVariable(name="memberID") String memberID){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("vo", memberService.loadMemberDetailInfoByMemberID(memberID));
        modelAndView.setViewName("management/admin/member-detail");
        return modelAndView;

    }

    /**
     * 删除某个会员
     * @param memberID
     * @return
     */
    @ApiOperation(value="deleteMemberInfo", notes="删除会员的详细信息")
    @GetMapping(value="/greenfarm/admin/management/member/delete/{memberID}")
    @ResponseBody
    public String deleteMemberInfo(@PathVariable(name="memberID") String memberID){
        JSONObject json = new JSONObject();
        if(memberService.deleteMemberByID(memberID)){
            json.put("flags",1);

        }else{
            json.put("flags",0);
            json.put("reason","数据库更新失败，请稍后重试");
        }
        return json.toString();

    }



    /**
     * 删除某个会员
     * @param memberID
     * @return
     */
    @ApiOperation(value="undoDeleteMemberInfo", notes="撤销删除会员的详细信息")
    @GetMapping(value="/greenfarm/admin/management/member/undo/delete/{memberID}")
    @ResponseBody
    public String undoDeleteMemberInfo(@PathVariable(name="memberID") String memberID){
        JSONObject json = new JSONObject();
        if(memberService.undoDeleteMemberByID(memberID)){
            json.put("flags",1);

        }else{
            json.put("flags",0);
            json.put("reason","数据库更新失败，请稍后重试");
        }
        return json.toString();

    }
}
