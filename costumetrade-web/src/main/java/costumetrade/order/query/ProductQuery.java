package costumetrade.order.query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import costumetrade.common.Entity;
import costumetrade.order.domain.ScLogisticFee;
import costumetrade.order.domain.SpPBrand;
import costumetrade.order.domain.SpPCate;
import costumetrade.order.domain.SpPColor;
import costumetrade.order.domain.SpPSize;
import costumetrade.order.domain.SpPSizeCustom;
import costumetrade.order.domain.SpUnit;
import costumetrade.order.domain.SsProductFile;
import costumetrade.order.domain.SsProductReview;
import costumetrade.user.domain.PatternPrice;
import costumetrade.user.domain.PriceJson;
import costumetrade.user.domain.SpCustProdPrice;
import costumetrade.user.domain.SpPrivilege;
import costumetrade.user.domain.SsDataDictionary;

public class ProductQuery extends Entity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *  商品编号
     */
    private String id;

    /**
     *  企业ID
     */
    private String storeId;
    /**
     *  实体状态 0:正常 1：待处理 2、作废
     */
    private Integer status;

    /**
     *  商品货号
     */
    private String code;

    /**
     *  商品品牌Id号
     */
    private String brandid;

    /**
     *  商品种类
     */
    private String producttype;

    /**
     *  商品名称
     */
    private String name;

  
    /**
     *  商品颜色
     */
    private String colors;

    /**
     *  商品尺码
     */
    private String sizes;

    /**
     *  商品手数
     */
    private Integer handcount;


    /**
     *  商品年份
     */
    private String year;

    /**
     *  商品季节
     */
    private String season;
   
    /**
     *  商品季节
     */
    private List<String> seasonList;
    
    private Integer popularize;

    
    /**
     *  商品图片
     */
    private String image;

    /**
     *  商品图片图片
     */
    private String image1;

    /**
     *  商品图片
     */
    private String image2;

    /**
     *  商品图片
     */
    private String image3;
    /**
     *  商品图片
     */
    private String image4;
    
    private String video1;
    
    private String video2;
    
    private String video3;

    /**
     *  商品描述
     */
    private String description;
    /**
     *  上架时间
     */
    private Date timeUp;

    /**
     *  下架时间
     */
    private Date timeDown;

    
    private String storeName;
    
    private String storeImage;
    
    private BigDecimal stockNum;
    
    private BigDecimal salePrice;
    
    private BigDecimal originalPrice;
    /**
     * 小程序平台微信用户ID号
     * */

	private Integer weChatId;
	
	private String openid;
    
	private String encryptedData;
	
	private String iv;
    /**
     *  进货价
     */
    private BigDecimal purchaseprice;

    /**
     *  吊牌价
     */
    private BigDecimal tagprice;

    /**
     * 第三销售价
     */
    private BigDecimal thirdPrice;

    /**
     * 第二销售价 
     */
    private BigDecimal secondPrice;

    /**
     * 第一销售价
     */
    private BigDecimal firsthPrice;
    /**
     *  第五销售价
     */
    private BigDecimal fifthPrice;
    
    /**
     *  第四销售价
     */
    private BigDecimal fourthPrice;
	
    /**
     *  预警下线
     */
    private BigDecimal warnLow;

    /**
     *  预警上线
     */
    private BigDecimal warnHigh;
    
    /**
     *  商品条码
     */
    private String barcode;

    /**
     *  
     */
    private String barcodes;
    
    /**
     *  商品级别
     */
    private String grade;

    /**
     *  商品单位
     */
    private String unit;
    
    /**
     *  是否手动修改价格 1、修改 2、不修改
     */
    private Integer isModify;
    
    /**
     *  是否参与打折 1、参与打折 2、不参与打折
     */
    private Integer isDiscount;
    
    private String raisePrice;
    
    /*
     * 款式加价
     * */
    private String isPattern;
    
    private String custOrDiscTag;
    
    private List<SpCustProdPrice> custProdPrice;
    
   // private List<SpCustProdPrice>  gradeList;
    
    private List<SpCustProdPrice>  customerTypeList;

    private List<String> idArray;
    
    private List<SsProductReview> productReviews;
    
    private List<SsDataDictionary> gradeList;
    
    private List<SsDataDictionary> payTypeList;
    
    private List<ScLogisticFee> logisticFees;
    
    private List<SsProductFile> fileList;
    
    private PatternPrice priceJsons;
	/**
	 * 货品列表查询  开始
	 */
	private Sort sort;
    private List<Rules>  rules;
    private Integer custTypeCode;
	private String timeUpOp; // 升序为asc，降序为desc
	private String priceOp; // 升序为asc，降序为desc
	private String hottestOp;// 升序为asc，降序为desc
	private List<String> productTypeArray; // 商品类别
	private List<String> productBrandArray; // 商品品牌
	private List<String> productSeasonArray; // 商品季节
	private String saleOp;
	private List<String> grades;
	private Integer productManagerQuery;
	/**
	 * 货品列表查询  结束
	 */
	private List<SpPSizeCustom> productSize;  //x商品尺碼組
	

	private List<SpPBrand> brandList ;
	private List<SpPCate> productTypeList;
	private List<SpPSizeCustom> sizeList;
	private List<SpUnit> unitList;
	/**
	 * 行数
	 */
	private Integer pageSize;
	/**
	 * 当前页码
	 */
	private Integer pageNum;
	
	private BigDecimal price ;
	
	private String appId;
	
	private String appSecret;
	
	private String codeAndName;
	
	private String fields;
	
	private BigDecimal count ;
	
	private List<SpPrivilege> privileges;
	
	private List<SpPSize> sizeLists;
	
	private List<SpPColor> colorLists;
	
	private Boolean checkAllTag;
	
	private Integer countReview;
	
	private String shareId;
	
	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public Integer getWeChatId() {
		return weChatId;
	}

	public void setWeChatId(Integer weChatId) {
		this.weChatId = weChatId;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code =code ;
	}

	public String getTimeUpOp() {
		return timeUpOp;
	}

	public void setTimeUpOp(String timeUpOp) {
		this.timeUpOp = timeUpOp;
	}

	public String getPriceOp() {
		return priceOp;
	}

	public void setPriceOp(String priceOp) {
		this.priceOp = priceOp;
	}

	

	public List<String> getProductTypeArray() {
		return productTypeArray;
	}

	public void setProductTypeArray(List<String> productTypeArray) {
		this.productTypeArray = productTypeArray;
	}

	public List<String> getProductBrandArray() {
		return productBrandArray;
	}

	public void setProductBrandArray(List<String> productBrandArray) {
		this.productBrandArray = productBrandArray;
	}

	public List<String> getProductSeasonArray() {
		return productSeasonArray;
	}

	public void setProductSeasonArray(List<String> productSeasonArray) {
		this.productSeasonArray = productSeasonArray;
	}

	public void setSaleOp(String saleOp) {
		this.saleOp = saleOp;
	}

	public List<SpPSizeCustom> getProductSize() {
		return productSize;
	}

	public void setProductSize(List<SpPSizeCustom> productSize) {
		this.productSize = productSize;
	}

	

	public String getBrandid() {
		return brandid;
	}

	public void setBrandId(String brandid) {
		this.brandid = brandid;
	}



	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getSizes() {
		return sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	public Integer getHandcount() {
		return handcount;
	}

	public void setHandcount(Integer handcount) {
		this.handcount = handcount;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimeUp() {
		return timeUp;
	}

	public void setTimeUp(Date timeUp) {
		this.timeUp = timeUp;
	}

	public Date getTimeDown() {
		return timeDown;
	}

	public void setTimeDown(Date timeDown) {
		this.timeDown = timeDown;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreImage() {
		return storeImage;
	}

	public void setStoreImage(String storeImage) {
		this.storeImage = storeImage;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public List<SpPBrand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<SpPBrand> brandList) {
		this.brandList = brandList;
	}

	public List<SpPCate> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<SpPCate> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public List<SpPSizeCustom> getSizeList() {
		return sizeList;
	}

	public void setSizeList(List<SpPSizeCustom> sizeList) {
		this.sizeList = sizeList;
	}

	public List<String> getSeasonList() {
		return seasonList;
	}

	public void setSeasonList(List<String> seasonList) {
		this.seasonList = seasonList;
	}





	public String getSaleOp() {
		return saleOp;
	}

	public void setSaleNum(String saleOp) {
		this.saleOp = saleOp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public BigDecimal getPurchaseprice() {
		return purchaseprice;
	}

	public void setPurchaseprice(BigDecimal purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public BigDecimal getTagprice() {
		return tagprice;
	}

	public void setTagprice(BigDecimal tagprice) {
		this.tagprice = tagprice;
	}




	public BigDecimal getWarnLow() {
		return warnLow;
	}

	public void setWarnLow(BigDecimal warnLow) {
		this.warnLow = warnLow;
	}

	public BigDecimal getWarnHigh() {
		return warnHigh;
	}

	public void setWarnHigh(BigDecimal warnHigh) {
		this.warnHigh = warnHigh;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(String barcodes) {
		this.barcodes = barcodes;
	}



	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public List<Rules> getRules() {
		return rules;
	}

	public void setRules(List<Rules> rules) {
		this.rules = rules;
	}

	public List<SpUnit> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<SpUnit> unitList) {
		this.unitList = unitList;
	}

	public BigDecimal getThirdPrice() {
		return thirdPrice;
	}

	public void setThirdPrice(BigDecimal thirdPrice) {
		this.thirdPrice = thirdPrice;
	}

	public BigDecimal getSecondPrice() {
		return secondPrice;
	}

	public void setSecondPrice(BigDecimal secondPrice) {
		this.secondPrice = secondPrice;
	}

	public BigDecimal getFirsthPrice() {
		return firsthPrice;
	}

	public void setFirsthPrice(BigDecimal firsthPrice) {
		this.firsthPrice = firsthPrice;
	}

	public BigDecimal getFifthPrice() {
		return fifthPrice;
	}

	public void setFifthPrice(BigDecimal fifthPrice) {
		this.fifthPrice = fifthPrice;
	}

	public BigDecimal getFourthPrice() {
		return fourthPrice;
	}

	public void setFourthPrice(BigDecimal fourthPrice) {
		this.fourthPrice = fourthPrice;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public List<SpCustProdPrice> getCustProdPrice() {
		return custProdPrice;
	}

	public void setCustProdPrice(List<SpCustProdPrice> custProdPrice) {
		this.custProdPrice = custProdPrice;
	}

	public String getCustOrDiscTag() {
		return custOrDiscTag;
	}

	public void setCustOrDiscTag(String custOrDiscTag) {
		this.custOrDiscTag = custOrDiscTag;
	}

	public Integer getIsModify() {
		return isModify;
	}

	public void setIsModify(Integer isModify) {
		this.isModify = isModify;
	}

	public Integer getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Integer isDiscount) {
		this.isDiscount = isDiscount;
	}

	public List<SsDataDictionary> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<SsDataDictionary> gradeList) {
		this.gradeList = gradeList;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public List<String> getGrades() {
		return grades;
	}

	public void setGrades(List<String> grades) {
		this.grades = grades;
	}

	public Integer getCustTypeCode() {
		return custTypeCode;
	}

	public void setCustTypeCode(Integer custTypeCode) {
		this.custTypeCode = custTypeCode;
	}

	public String getVideo1() {
		return video1;
	}

	public void setVideo1(String video1) {
		this.video1 = video1;
	}

	public String getVideo2() {
		return video2;
	}

	public void setVideo2(String video2) {
		this.video2 = video2;
	}

	public String getVideo3() {
		return video3;
	}

	public void setVideo3(String video3) {
		this.video3 = video3;
	}

	public List<String> getIdArray() {
		return idArray;
	}

	public void setIdArray(List<String> idArray) {
		this.idArray = idArray;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Integer getProductManagerQuery() {
		return productManagerQuery;
	}

	public void setProductManagerQuery(Integer productManagerQuery) {
		this.productManagerQuery = productManagerQuery;
	}

	public List<SsProductReview> getProductReviews() {
		return productReviews;
	}

	public void setProductReviews(List<SsProductReview> productReviews) {
		this.productReviews = productReviews;
	}

	public String getCodeAndName() {
		return codeAndName;
	}

	public void setCodeAndName(String codeAndName) {
		this.codeAndName = codeAndName;
	}

	public Integer getPopularize() {
		return popularize;
	}

	public void setPopularize(Integer popularize) {
		this.popularize = popularize;
	}

	public List<SpCustProdPrice> getCustomerTypeList() {
		return customerTypeList;
	}

	public void setCustomerTypeList(List<SpCustProdPrice> customerTypeList) {
		this.customerTypeList = customerTypeList;
	}

	public List<SpPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<SpPrivilege> privileges) {
		this.privileges = privileges;
	}

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public List<SsDataDictionary> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<SsDataDictionary> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<ScLogisticFee> getLogisticFees() {
		return logisticFees;
	}

	public void setLogisticFees(List<ScLogisticFee> logisticFees) {
		this.logisticFees = logisticFees;
	}

	public String getIsPattern() {
		return isPattern;
	}

	public void setIsPattern(String isPattern) {
		this.isPattern = isPattern;
	}

	public List<SpPSize> getSizeLists() {
		return sizeLists;
	}

	public void setSizeLists(List<SpPSize> sizeLists) {
		this.sizeLists = sizeLists;
	}

	public List<SpPColor> getColorLists() {
		return colorLists;
	}

	public void setColorLists(List<SpPColor> colorLists) {
		this.colorLists = colorLists;
	}

	public String getRaisePrice() {
		return raisePrice;
	}

	public void setRaisePrice(String raisePrice) {
		this.raisePrice = raisePrice;
	}

	public List<SsProductFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<SsProductFile> fileList) {
		this.fileList = fileList;
	}

	public PatternPrice getPriceJsons() {
		return priceJsons;
	}

	public void setPriceJsons(PatternPrice priceJsons) {
		this.priceJsons = priceJsons;
	}

	public Boolean getCheckAllTag() {
		return checkAllTag;
	}

	public void setCheckAllTag(Boolean checkAllTag) {
		this.checkAllTag = checkAllTag;
	}

	public Integer getCountReview() {
		return countReview;
	}

	public void setCountReview(Integer countReview) {
		this.countReview = countReview;
	}

	public BigDecimal getStockNum() {
		return stockNum;
	}

	public void setStockNum(BigDecimal stockNum) {
		this.stockNum = stockNum;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getHottestOp() {
		return hottestOp;
	}

	public void setHottestOp(String hottestOp) {
		this.hottestOp = hottestOp;
	}

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	
	
	
	
	

	

	
	
	
}
