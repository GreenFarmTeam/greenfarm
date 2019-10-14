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

// 商店名称正则    数字、字母 汉字4-15位
var shopNameRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{4,15}$/;

// 商铺描述正则         数字、字母 汉字0-255位(含标点)
var businessShopDescriptionRegex = /^[0-9a-zA-Z\u4E00-\u9FA5\u3002\uff1f\uff01\uff0c\u3001\uff1b\uff1a\u201c\u201d\u2018\u2019\uff08\uff09\u300a\u300b\u3008\u3009\u3010\u3011\u300e\u300f\u300c\u300d\ufe43\ufe44\u3014\u3015\u2026\u2014\uff5e\ufe4f\uffe5]{0,125}$/;

// 收货地址姓名正则 数字、字母 汉字2-255位
var addressNameRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{2,255}$/;

// 收货详细地址正则 数字、字母 汉字2-255位(含标点)
var addressRegex = /^[0-9a-zA-Z\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b\u4e00-\u9fa5]{2,255}$/;

//收货地址电话  手机号码11位
var addressPhoneRegex = /^1[3456789]\d{9}$/

// 商品名称正则    数字、字母 汉字2-25位
var productNameRegex = /^[0-9a-zA-Z\u4E00-\u9FA5]{2,25}$/;

// 商品单价正则           大于0的且小数点后精确2位的数值
var productPriceRegex = /^([1-9]{1}\d{0,6}|0{1})(\.\d{2})$/;

// 商品单位正则         汉字1-2位
var productUnitRegex = /^[\u4E00-\u9FA5]{1,2}$/;

// 商品描述正则         数字、字母 汉字0-255位(含标点)
var productDescriptionRegex = /^[0-9a-zA-Z\u4E00-\u9FA5\u3002\uff1f\uff01\uff0c\u3001\uff1b\uff1a\u201c\u201d\u2018\u2019\uff08\uff09\u300a\u300b\u3008\u3009\u3010\u3011\u300e\u300f\u300c\u300d\ufe43\ufe44\u3014\u3015\u2026\u2014\uff5e\ufe4f\uffe5]{0,125}$/;

// 商品库存正则 大于零的整数，最大值9999999
var stockRegex = /^[1-9]{1}\d{0,6}$/;

// 农场租金正则      大于0的且小数点后精确2位的数值
var farmPriceRegex = /^([1-9]{1}\d{0,6}|0{1})(\.\d{2})$/;

// 农场租期年限正则 1-99年
var farmYearRegex = /^[1-9]{1}\d{0,2}$/;

// 农场面积正则 1-9999亩
var farmAreaRegex = /^[1-9]{1}\d{0,4}$/;

// 农场描述正则 0-9/a-z/A-Z/汉字(含标点)
var farmDescriptionRegex = /^[0-9a-zA-Z\u4E00-\u9FA5\u3002\uff1f\uff01\uff0c\u3001\uff1b\uff1a\u201c\u201d\u2018\u2019\uff08\uff09\u300a\u300b\u3008\u3009\u3010\u3011\u300e\u300f\u300c\u300d\ufe43\ufe44\u3014\u3015\u2026\u2014\uff5e\ufe4f\uffe5]{0,125}$/;