package com.nchu.ruanko.greenfarm.controller.management.business;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.constant.FileConstant;
import com.nchu.ruanko.greenfarm.constant.PageConstant;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessProductVO;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "management.business.BusinessProductController", description = "“商家商品管理”功能控制器")
@Controller
public class BusinessProductController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ProductService productService;

    private static final int PAGE_NAVIGATION_SIZE = 10;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        modelAndView.setViewName("management/business/product-add");
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

        File folder = new File(FileConstant.FILE_UPLOAD_PATH);
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
                productMainImage.setProductImagePath(FileConstant.FILE_UPLOAD_VIRTUAL_PATH_PREFIX + fileNewName);
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
                    productOtherImage.setProductImagePath(FileConstant.FILE_UPLOAD_VIRTUAL_PATH_PREFIX + fileNewName);
                    productOtherImages.add(productOtherImage);
                } catch (IOException e) {
                    json.put("flag", false);
                    json.put("reason", "文件上传失败！system");
                    e.printStackTrace();
                    return json.toString();
                }
            }
        }

        productService.businessAddProduct(product, productMainImage, productOtherImages, productType, businessUid);
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
    public ModelAndView businessManagementProductReviewPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        modelAndView.setViewName("management/business/product-review");
        modelAndView.addObject("vo", productService.businessListProductReviewRecords(business.getBusinessUid(), pageNum, pageSize, PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

    /**
     * 商家删除全部未审核通过的商品的信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "businessManagementClearNoPassProductOperation", notes = "商家删除未审核通过的商品的信息")
    @GetMapping(value = "/business/management/product/remove/nopass/operation")
    @ResponseBody
    public String businessManagementClearNoPassProductOperation(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        productService.businessClearNoPassProductReview(business.getBusinessUid());
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 跳转至“商家的所有成功上架的商品”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "businessManagementProductPage", notes = "跳转至“商家的所有成功上架的商品”的页面")
    @GetMapping(value = "/business/management/product")
    public ModelAndView businessManagementProductPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        modelAndView.setViewName("management/business/product-checked");
        modelAndView.addObject("vo", productService.businessListProducts(business.getBusinessUid(), pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

    /**
     * 商家获取某一个商品的详细信息
     *
     * @param productUid uid
     * @return JSON
     */
    @ApiOperation(value = "businessManagementProductDetailOperation", notes = "商家获取某一个商品的详细信息")
    @GetMapping(value = "/business/management/product/detail")
    @ResponseBody
    public String businessManagementProductDetailOperation(@RequestParam(name = "uid") String productUid) {
        JSONObject json = new JSONObject();

        BusinessProductVO vo = productService.businessGetProductByProductUID(productUid);
        json.put("name", vo.getProduct().getProductName());
        json.put("upDate", dateFormat.format(vo.getProduct().getProductUpDate()));
        json.put("type", vo.getProduct().getProductType().getTypeName());

        if (vo.getProduct().getProductStock() == null) {
            json.put("stock", "库存充足");
        } else {
            if (vo.getProduct().getProductUnit() == null) {
                json.put("stock", vo.getProduct().getProductStock());
            } else {
                json.put("stock", vo.getProduct().getProductStock() + vo.getProduct().getProductUnit());
            }
        }
        if (vo.getProduct().getProductUnit() == null) {
            json.put("price", vo.getProduct().getProductPrice() + "元");
        } else {
            json.put("price", vo.getProduct().getProductPrice() + "元/" + vo.getProduct().getProductUnit());
        }

        if (vo.getProduct().getProductDescription() == null) {
            json.put("description", "暂无描述");
        } else {
            json.put("description", vo.getProduct().getProductDescription());
        }

        if (vo.getMainImage() == null) {
            json.put("mainImage", "暂无主图片");
        } else {
            json.put("mainImage", vo.getMainImage().getProductImagePath());
        }

        List<String> pathList = new ArrayList<>();
        if (vo.getOtherImages().size() == 0) {
            json.put("otherImages", pathList);
        } else {
            for (ProductImage productImage : vo.getOtherImages()) {
                pathList.add(productImage.getProductImagePath());
            }
            json.put("otherImages", pathList);
        }

        json.put("flag", true);

        return json.toString();
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param request
     * @return ModelAndView
     */
    @ApiOperation(value = "businessManagementProductDownPage", notes = "")
    @GetMapping(value = "/business/management/product/down")
    public ModelAndView businessManagementProductDownPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        modelAndView.setViewName("management/business/product-down");
        modelAndView.addObject("vo", productService.businessListDownProducts(business.getBusinessUid(), pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

    /**
     * 商家下架自己某一商品
     *
     * @param productUid uid
     * @return JSON
     */
    @ApiOperation(value = "businessManagementProductDownOperation", notes = "商家下架自己某一商品")
    @GetMapping(value = "/business/management/product/down/operation")
    @ResponseBody
    public String businessManagementProductDownOperation(@RequestParam(name = "uid") String productUid) {
        JSONObject json = new JSONObject();
        productService.businessDownProduct(productUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 商家恢复上架商品
     *
     * @param productUid uid
     * @return JSON
     */
    @ApiOperation(value = "businessManagementProductUpOperation", notes = "商家恢复上架商品")
    @GetMapping(value = "/business/management/product/up/operation")
    @ResponseBody
    public String businessManagementProductUpOperation(@RequestParam(name = "uid") String productUid) {
        JSONObject json = new JSONObject();
        productService.businessUpProduct(productUid);
        json.put("flag", true);
        return json.toString();
    }


    /**
     * 商家修改库存
     *
     * @param stock
     * @param productUid
     * @return
     */
    @ApiOperation(value = "businessManagementProductModifyStock", notes = "商家修改库存")
    @PostMapping(value = "/business/management/product/stock/operation")
    @ResponseBody
    public String businessManagementProductModifyStock(@RequestParam(name = "stock") Integer stock, @RequestParam(name = "uid") String productUid) {
        System.out.println(stock);
        System.out.println(productUid);
        JSONObject json = new JSONObject();
        productService.businessSetStock(stock, productUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 商家修改库存为无上限
     *
     * @param productUid uid
     * @return JSON
     */
    @ApiOperation(value = "businessManagementProductModifyStockNoLimit", notes = "商家修改库存为无上限")
    @GetMapping(value = "/business/management/product/stock/nolimit/operation")
    @ResponseBody
    public String businessManagementProductModifyStockNoLimit(@RequestParam(value = "uid") String productUid) {
        JSONObject json = new JSONObject();
        productService.businessSetStockNoLimit(productUid);
        json.put("flag", true);
        return json.toString();
    }


    /**
     * 商家修改库存为零
     *
     * @param productUid uid
     * @return JSON
     */
    @ApiOperation(value = "businessManagementProductModifyStockNull", notes = "商家修改库存为零")
    @GetMapping(value = "/business/management/product/stock/null/operation")
    @ResponseBody
    public String businessManagementProductModifyStockNull(@RequestParam(value = "uid") String productUid) {
        JSONObject json = new JSONObject();
        productService.businessSetStockNull(productUid);
        json.put("flag", true);
        return json.toString();
    }



}