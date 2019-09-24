package com.nchu.ruanko.greenfarm.controller.management.business;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import com.nchu.ruanko.greenfarm.service.ProductService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "management.business.BusinessProductController", description = "“商家商品管理”功能控制器")
@Controller
public class BusinessProductController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ProductService productService;

    // 根据实际情况去修改
    private static final String FILE_UPLOAD_PATH = "E:\\greenfarm\\file";

    private static final String FILE_UPLOAD_VIRTUAL_PATH_PREFIX = "/file/upload/";

    private static final int PAGE_NAVIGATION_SIZE = 10;

    /**
     * 跳转至“商家上架商品”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "businessManagementAddProductPage", notes = "跳转至“商家上架商品”的页面")
    @GetMapping(value = "/business/management/product/add")
    public ModelAndView businessManagementAddProductPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        modelAndView.setViewName("management/business/add-product");
        modelAndView.addObject("typeList", businessService.listBusinessScopesByBusinessUID(business.getBusinessUid()));
        return modelAndView;
    }

    /**
     * 商家上架商品
     *
     * @param productName 商品名称
     * @param productPrice 单价
     * @param productType 类型
     * @param productUnit 单位
     * @param productDescription 描述
     * @param mainImage 主图片
     * @param otherImages 其他图片
     * @return JSON
     */
    @ApiOperation(value = "businessManagementAddProductOperation", notes = "商家上架商品")
    @PostMapping("/business/management/product/add/operation")
    @ResponseBody
    public String businessManagementAddProductOperation(@RequestParam(name = "name") String productName, @RequestParam(name = "price") String productPrice, @RequestParam(name = "type") String productType, @RequestParam(name = "unit", required = false) String productUnit, @RequestParam(name = "description", required = false) String productDescription, @RequestParam(name = "mainImage", required = false) MultipartFile mainImage, @RequestParam(name = "otherImages", required = false) MultipartFile[] otherImages, HttpServletRequest request) {
        JSONObject json = new JSONObject();

        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        String businessUid = business.getBusinessUid();

        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice(Float.parseFloat(productPrice));
        product.setProductUnit(productUnit);
        product.setProductDescription(productDescription);

        File folder = new File(FILE_UPLOAD_PATH);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }

        ProductImage productMainImage = null;
        if (mainImage != null && !mainImage.isEmpty()) {
            String uid = StringUtils.createUUID();
            String fileNewName = uid + mainImage.getOriginalFilename().substring(mainImage.getOriginalFilename().lastIndexOf("."));
            try {
                mainImage.transferTo(new File(folder, fileNewName));
                productMainImage = new ProductImage();
                productMainImage.setProductImageUid(uid);
                productMainImage.setProductImagePath(FILE_UPLOAD_VIRTUAL_PATH_PREFIX + fileNewName);
            } catch (IOException e) {
                json.put("flag", false);
                json.put("reason", "文件上传失败！system");
                e.printStackTrace();
                return json.toString();
            }
        }

        List<ProductImage> productOtherImages = new ArrayList<>();
        if (otherImages != null && otherImages.length != 0) {
            for (MultipartFile otherImage : otherImages) {
                String uid = StringUtils.createUUID();
                String fileNewName = uid + otherImage.getOriginalFilename().substring(otherImage.getOriginalFilename().lastIndexOf("."));
                try {
                    otherImage.transferTo(new File(folder, fileNewName));
                    ProductImage productOtherImage = new ProductImage();
                    productOtherImage.setProductImageUid(uid);
                    productOtherImage.setProductImagePath(FILE_UPLOAD_VIRTUAL_PATH_PREFIX + fileNewName);
                    productOtherImages.add(productOtherImage);
                } catch (IOException e) {
                    json.put("flag", false);
                    json.put("reason", "文件上传失败！system");
                    e.printStackTrace();
                    return json.toString();
                }
            }
        }

        productService.addProduct(product, productMainImage, productOtherImages, productType, businessUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 跳转至“商家的商品审核记录”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "businessManagementProductReviewPage", notes = "跳转至“商家的商品审核记录”的页面")
    @GetMapping(value = "/business/management/product/review")
    public ModelAndView businessManagementProductReviewPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "1") int pageSize, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        modelAndView.setViewName("management/business/product-review");
        modelAndView.addObject("vo", productService.listBusinessProductReviewRecords(business.getBusinessUid(), pageNum, pageSize, PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

}