package costumetrade.order.service;

import java.util.List;

import costumetrade.order.domain.SpProduct;
import costumetrade.order.domain.SsProductFile;
import costumetrade.order.domain.SsProductReview;
import costumetrade.order.domain.SsStock;
import costumetrade.order.query.ProductQuery;


public interface SpProductService {
	/**
	 * 获取所有商品
	 * 
	 * */
	public List<ProductQuery> selectProducts(ProductQuery productQuery);
	/**
	 * 分享链接查询商品
	 * */
	public List<ProductQuery> getShareProduct(ProductQuery productQuery);
	/**
	 * 保存款式加价
	 * */
	public List<Object> patternAddPriceInit(ProductQuery productQuery);
	/**
	 * 保存款式加价
	 * */
	public int savePatternAddPrice(ProductQuery productQuery);
	/**
	 * 
	 * 商品加入推广
	 * */
	public int makePopularize(ProductQuery productQuery);
	/**
	 * 获取商品詳情
	 * 
	 * */
	public ProductQuery selectProduct(ProductQuery productParam);
	/**
	 * 获取商品詳情
	 * 
	 * */
	public String saveProduct(SpProduct product);
	/**
	 * 获取图片
	 * 
	 * */
	public List<SsProductFile> getImages(SsProductFile productFile);
	/**
	 * 获取商品库存
	 * 
	 * */
	public List<SsStock> takingStock(SpProduct product);
	/**
	 * 保存商品库存
	 * 
	 * */
	public 	Integer updateStock(List<SsStock> stocks);
	/**
	 * 新增商品初始化查询
	 * 
	 * */
	public ProductQuery productInit(ProductQuery query);
	/**
	 * 批量修改初始化
	 * */
	public ProductQuery updateProductInit(ProductQuery query);
	/**
	 * 查询货品
	 * 
	 * */
	public List<SpProduct> selectProductById(List<String> id,String storeId);
	/**
	 * 删除货品
	 * 
	 * */
	public int deleteProducts(List<String> id,String storeId);
	/**
	 * 删除货品
	 * 
	 * */
	public int updateProducts(ProductQuery  productQuery);
	
	public void insertSuspendingProduct(SpProduct product,String buyerStoreId);
	
	/**
	 * 查询商品评价
	 * */
	public List<SsProductReview> getReviews(ProductQuery query);

}
