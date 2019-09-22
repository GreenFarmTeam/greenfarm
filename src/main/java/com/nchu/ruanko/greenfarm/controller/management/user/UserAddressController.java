package com.nchu.ruanko.greenfarm.controller.management.user;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.pojo.entity.Address;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * “会员/用户收货地址”功能控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "management.user.UserAddressController", description = "“会员/用户收货地址”功能控制器")
@Controller
public class UserAddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 跳转至“用户/会员添加收货地址”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userAddAddressPage", notes = "跳转至“用户/会员添加收货地址”的页面")
    @GetMapping(value = "/user/management/address/add")
    public ModelAndView userAddAddressPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/add-address");
        modelAndView.addObject("provinceList", addressService.listAllProvinces());
        return modelAndView;
    }

    /**
     * 用户/会员添加收货地址
     *
     * @param province
     * @param city
     * @param district
     * @param name
     * @param address
     * @param request
     * @return JSON
     */
    @ApiOperation(value = "userAddAddressOperation", notes = "用户/会员添加收货地址")
    @PostMapping(value = "/user/management/address/add/operation")
    @ResponseBody
    public String userAddAddressOperation(@RequestParam(name = "province") String province, @RequestParam(name = "city") String city, @RequestParam(name = "district", required = false) String district, @RequestParam(name = "name") String name, @RequestParam(name = "address") String address, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Address addr = new Address();
        addr.setAddressProvince(province);
        addr.setAddressCity(city);
        addr.setAddressDistrict(district);
        addr.setAddressName(name);
        addr.setAddressDetail(address);
        addressService.addAddress(addr, user.getUserUid());
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 跳转至“用户/会员收货地址”页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userAddressPage", notes = "跳转至“用户/会员收货地址”页面")
    @GetMapping(value = "/user/management/address")
    public ModelAndView userAddressPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        modelAndView.setViewName("management/user/address-list");
        modelAndView.addObject("addressList", addressService.listAddressesByUserUID(user.getUserUid()));
        return modelAndView;
    }

    /**
     * 用户/会员删除收货地址
     *
     * @param addressUid uid
     * @return JSON
     */
    @ApiOperation(value = "userRemoveAddressOperation", notes = "用户/会员删除收货地址")
    @GetMapping(value = "/user/management/address/remove/operation")
    @ResponseBody
    public String userRemoveAddressOperation(@RequestParam(name = "uid") String addressUid) {
        JSONObject json = new JSONObject();
        addressService.removeAddressByAddressUID(addressUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 跳转至“用户/会员修改收货地址”的页面
     *
     * @param addressUid uid
     * @return ModelAndView
     */
    @ApiOperation(value = "userModifyAddressPage", notes = "跳转至“用户/会员修改收货地址”的页面")
    @GetMapping(value = "/user/management/address/modify/{addressUid}")
    public ModelAndView userModifyAddressPage(@PathVariable(name = "addressUid") String addressUid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/modify-address");
        modelAndView.addObject("provinceList", addressService.listAllProvinces());
        modelAndView.addObject("address", addressService.getAddressByAddressUID(addressUid));
        return modelAndView;
    }


    /**
     * 用户/会员修改收货地址
     *
     * @param addressUid uid
     * @return JSON
     */
    @ApiOperation(value = "userModifyAddressOperation", notes = "用户/会员修改收货地址")
    @PostMapping(value = "/user/management/address/modify/{addressUid}/operation")
    @ResponseBody
    public String userModifyAddressOperation(@RequestParam(name = "name") String name, @RequestParam(name = "province") String province, @RequestParam(name = "city") String city, @RequestParam(name = "district", required = false) String district, @RequestParam(name = "detail") String detail, @PathVariable(name = "addressUid") String addressUid) {
        JSONObject json = new JSONObject();
        Address address = new Address();
        address.setAddressName(name);
        address.setAddressProvince(province);
        address.setAddressCity(city);
        address.setAddressDistrict(district);
        address.setAddressDetail(detail);
        addressService.modifyAddressByAddressUID(address, addressUid);
        json.put("flag", true);
        return json.toString();
    }

}