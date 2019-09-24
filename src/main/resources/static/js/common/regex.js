// 密码正则：8-16位字母和数字组成，不能是纯数字或纯英文
var passwordRegex = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/;

// 邮箱正则
var mailRegex = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;

// 手机号正则 正常手机号
var phoneRegex = /^(?:\+?86)?1(?:3\d{3}|5[^4\D]\d{2}|8\d{3}|7(?:[01356789]\d{2}|4(?:0\d|1[0-2]|9\d))|9[189]\d{2}|6[567]\d{2}|4[579]\d{2})\d{6}$/;

// 昵称正则 数字、字母 汉字1-25位
var nicknameRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{1,25}$/;

// 身份证号正则 正常身份证号
var idcardRegex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;

// 商店名称正则  数字、字母 汉字6-15位
var shopNameRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{6,15}$/;

// 收货地址姓名正则
var addressNameRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{2,10}$/;

// 收货详细地址正则
var addressRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{5,25}$/;

// 商品名称正则
var productNameRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{2,25}$/;

// 商品单价正则
var productPriceRegex = /^([1-9]\d{1,7})(\.\d{2})$/;

// 商品单位正则
var productUnitRegex = /^[\u4E00-\u9FA5]{0,10}$/;

// 商品描述正则
var productDescriptionRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{0,125}$/;
