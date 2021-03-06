package costumetrade.user.service;

import java.util.List;

import costumetrade.order.domain.ScLogisticFee;
import costumetrade.user.domain.SpCustProdPrice;
import costumetrade.user.domain.SpCustomerType;
import costumetrade.user.domain.SsDataDictionary;
import costumetrade.user.query.DataDictionaryQuery;
import costumetrade.user.query.SettingQuery;


public interface SsDataDictionaryService {

	/**
	 * 查询设置
	 * */
	public SettingQuery getDataDictionarys(String storeId);
	
	/**
	 * 
	 * 新增设置内容
	 * */
	public Integer saveDataDictionary(List<SsDataDictionary> dictionarys);
	
	/**
	 * 删除设置
	 * */
	
	public List<SsDataDictionary> deleteDataDictionary(Integer id);
	
	/**
	 * 查询设置
	 * */
	public List<SsDataDictionary> getDataDicts(SsDataDictionary dictionary);
	
	/**
	 * 新增客户 修改客户类型初始化
	 * */
	
	public DataDictionaryQuery initCustomType(DataDictionaryQuery query);
	
	public SpCustomerType saveCustomType(SpCustomerType customerType);
	
	public int saveTypeOrGradeRate(List<SpCustProdPrice> spCustProdPrices);
	
	public SpCustProdPrice getTypeOrGradeRate(SpCustProdPrice spCustProdPrice);
	
	public int updateLogistics(List<ScLogisticFee> list);
	
}
