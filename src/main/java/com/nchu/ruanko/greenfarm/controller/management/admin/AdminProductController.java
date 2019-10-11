package com.nchu.ruanko.greenfarm.controller.management.admin;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.constant.PageConstant;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminProductVO;
import com.nchu.ruanko.greenfarm.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Api(tags = "management.admin.AdminProductController", description = "“管理员商品管理”功能控制器")
@Controller
public class AdminProductController {

    @Autowired
    private ProductService productService;

    private static final int PAGE_NAVIGATION_SIZE = 10;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 跳转至“管理员商品审核”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "adminReviewProductPage", notes = "跳转至“管理员商品审核”的页面")
    @GetMapping(value = "/greenfarm/admin/management/product/review")
    public ModelAndView adminReviewProductPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product-review");
        modelAndView.addObject("vo", productService.adminListProductReview(pageNum, pageSize, PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

    /**
     * 管理员同意商品上架申请
     *
     * @param productReviewUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminReviewProductAgreeOperation", notes = "管理员同意商品上架申请")
    @GetMapping(value = "/greenfarm/admin/management/product/review/agree/{uid}/operation")
    @ResponseBody
    public String adminReviewProductAgreeOperation(@PathVariable(name = "uid") String productReviewUid) {
        JSONObject json = new JSONObject();
        productService.adminAgreeProductReview(productReviewUid);
        json.put("flag", true);
        return json.toString();
    }


    /**
     * 管理员驳回商品上架申请
     *
     * @param productReviewUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminReviewProductDisagreeOperation", notes = "管理员驳回商品上架申请")
    @PostMapping(value = "/greenfarm/admin/management/product/review/disagree/{uid}/operation")
    @ResponseBody
    public String adminReviewProductDisagreeOperation(@RequestParam(name = "reason") String reason ,@PathVariable(name = "uid") String productReviewUid) {
        JSONObject json = new JSONObject();
        productService.adminDisagreeProductReview(reason, productReviewUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 跳转至“管理员查看所有成功上架的商品”的界面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "adminProductPage", notes = "跳转至“管理员查看所有成功上架的商品”的界面")
    @GetMapping(value = "/greenfarm/admin/management/product")
    public ModelAndView adminProductPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/product-checked");
        modelAndView.addObject("vo", productService.adminListProducts(pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }


    /**
     * 管理员查看某一个商品的详细信息
     *
     * @param productUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminProductDetailOperation", notes = "管理员查看某一个商品的详细信息")
    @GetMapping(value = "/greenfarm/admin/management/product/detail/operation")
    @ResponseBody
    public String adminProductDetailOperation(@RequestParam(name = "uid") String productUid) {
        JSONObject json = new JSONObject();
        AdminProductVO vo = productService.adminGetProductByProductUID(productUid);
        json.put("uid", vo.getProduct().getProductUid());
        json.put("name", vo.getProduct().getProductName());
        json.put("upDate", dateFormat.format(vo.getReview().getProductReviewReviewTime()));
        json.put("type", vo.getProduct().getProductType().getTypeName());
        json.put("submitDate", dateFormat.format(vo.getReview().getProductReviewSubmitTime()));
        if (vo.getProduct().getProductStock() == null) {
            json.put("stock", "库存充足");
        } else {
            if (vo.getProduct().getProductUnit() == null) {
                json.put("stock", vo.getProduct().getProductStock());
            } else {
                json.put("stock", vo.getProduct().getProductStock() + vo.getProduct().getProductUnit());
            }
        }
        if (vo.getProduct().getProductDescription() == null) {
            json.put("description", "暂无描述");
        } else {
            json.put("description", vo.getProduct().getProductDescription());
        }
        if (vo.getProduct().getProductUnit() == null) {
            json.put("price", vo.getProduct().getProductPrice() + "元");
        } else {
            json.put("price", vo.getProduct().getProductPrice() + "元/" + vo.getProduct().getProductUnit());
        }
        if (vo.getMainImage() == null) {
            json.put("mainImage", "商品暂无主图片");
        } else {
            json.put("mainImage", vo.getMainImage().getProductImagePath());
        }
        List<String> pathList = new ArrayList<>();
        if (vo.getOtherImages().size() == 0) {
            json.put("otherImages", pathList);
        } else {
            for (ProductImage image : vo.getOtherImages()) {
                pathList.add(image.getProductImagePath());
            }
            json.put("otherImages", pathList);
        }
        json.put("businessUid", vo.getProduct().getBusiness().getBusinessUid());
        json.put("businessName", vo.getProduct().getBusiness().getBusinessShopName());
        json.put("businessDescription", vo.getProduct().getBusiness().getBusinessShopDescription());
        json.put("username", vo.getProduct().getBusiness().getUser().getUserUsername());
        json.put("nickname", vo.getProduct().getBusiness().getUser().getUserNickname());

        json.put("flag", true);
        return json.toString();
    }

    /**
     * 管理员下架商品操作
     *
     * @param productUid
     * @return
     */
    @ApiOperation(value = "adminProductDownOperation", notes = "管理员下架商品操作")
    @GetMapping(value = "/greenfarm/admin/management/product/down/operation")
    @ResponseBody
    public String adminProductDownOperation(@RequestParam(name = "uid") String productUid) {
        JSONObject json = new JSONObject();
        productService.adminDownProduct(productUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 管理员恢复上架商品操作
     *
     * @param productUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminProductUpOperation", notes = "管理员恢复上架商品操作")
    @GetMapping(value = "/greenfarm/admin/management/product/up/operation")
    @ResponseBody
    public String adminProductUpOperation(@RequestParam(name = "uid") String productUid) {
        JSONObject json = new JSONObject();
        productService.adminUpProduct(productUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 跳转至“管理员查看当前所有下架商品（客户主动下架/管理员下架）信息”页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "adminProductDownPage", notes = "跳转至“管理员查看当前所有下架商品（客户主动下架/管理员下架）信息”页面")
    @GetMapping(value = "/greenfarm/admin/management/product/down")
    public ModelAndView adminProductDownPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/product-down");
        modelAndView.addObject("vo", productService.adminListDownProducts(pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }


}