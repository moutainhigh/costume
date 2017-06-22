package costumetrade.report.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import costumetrade.common.page.Page;
import costumetrade.order.query.Rules;
import costumetrade.report.domain.FinanceReportQuery;
import costumetrade.report.domain.ProductReportQuery;
import costumetrade.report.domain.PurchaseReportQuery;
import costumetrade.report.domain.ReportQuery;
import costumetrade.report.mapper.SpReportMapper;
import costumetrade.report.service.SpReportService;
import costumetrade.user.domain.ScWeChat;
import costumetrade.user.mapper.ScWeChatMapper;

@Transactional
@Service
public class SpReportServiceImpl implements SpReportService{
	@Autowired
	private SpReportMapper spReportMapper;
	@Autowired
	private ScWeChatMapper scWeChatMapper;
	@Override
	public FinanceReportQuery financeReport(FinanceReportQuery query) {
		if(query.getTimeFrom() ==null ){
			query.setTimeFrom(setTimeFrom());
		}
		if(query.getTimeTo() == null){
			query.setTimeTo(setTimeTo());
		}
		ScWeChat wechat = scWeChatMapper.selectByOpenId(query.getOpenid());//根据当前操作者的openid 获取当前店铺storeId
		if(wechat !=null){//客户对账
			if(wechat.getStoreid()!=null){
				query.setStoreId(wechat.getStoreid());
			}else{
				return null;
			}
		}
		FinanceReportQuery report = spReportMapper.financeReport(query);
		return report;
	}
	@Override
	public List<Map<String,Object>> purchaseSortReport(
			PurchaseReportQuery query) {
		if(query.getTimeFrom() ==null ){
			query.setTimeFrom(setTimeFrom());
		}
		if(query.getTimeTo() == null){
			query.setTimeTo(setTimeTo());
		}
		ScWeChat wechat = scWeChatMapper.selectByOpenId(query.getOpenid());//根据当前操作者的openid 获取当前店铺storeId
		
		if(wechat !=null){
			if(wechat.getStoreid()!=null){
				query.setStoreId(wechat.getStoreid());
			}else{
				return null;
			}
		}
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> maps =  spReportMapper.purchaseSortReport(query);
		if(maps!=null){
			for(int i=0;i<maps.size();i++){
				Map<String,Object> map = maps.get(i);
				Map<String,Object> m = new HashMap<String, Object>();
				m.put(query.getFilter().getField(), map.get(query.getFilter().getField()));
				m.put("quantity", map.get("quantity"));
				m.put("amount", map.get("amount"));
				mapList.add(m);
				
			}
		}else{
			return mapList;
		}
		return mapList;
	}
	
	
	public Date setTimeFrom(){
		//如果时间没有传就是默认当天的 零点 到 24点
		Calendar calendar1 = Calendar.getInstance(); 
		calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),  
                0, 0, 0);  
		return calendar1.getTime();
	}
	public Date setTimeTo(){
		//如果时间没有传就是默认当天的 零点 到 24点
		Calendar calendar1 = Calendar.getInstance(); 
		calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),  
       		 23, 59, 59); 
		return calendar1.getTime();
	}
	@SuppressWarnings("unused")
	@Override
	public ReportQuery purchaseReport(PurchaseReportQuery query) {
		ReportQuery reportQuery = new ReportQuery();
		if(query.getTimeFrom() ==null ){
//			Calendar cale = null;
//			  cale = Calendar.getInstance();  
//		        cale.add(Calendar.MONTH, -1);  
//		        cale.set(Calendar.DAY_OF_MONTH, 1);  
//		        cale.set(Calendar.HOUR_OF_DAY,0);
//		        cale.set(Calendar.MINUTE,0);
//		        cale.set(Calendar.SECOND, 0);
//		        cale.set(Calendar.MILLISECOND,0);
//		        query.setTimeFrom(cale.getTime());
			query.setTimeFrom(setTimeFrom());
		}
		if(query.getTimeTo() == null){
			query.setTimeTo(setTimeTo());
		}
		ScWeChat wechat = scWeChatMapper.selectByOpenId(query.getOpenid());//根据当前操作者的openid 获取当前店铺storeId
		
		if(wechat !=null){
			if(wechat.getStoreid()!=null){
				query.setStoreId(wechat.getStoreid());
			}else{
				return null;
			}
		}
	
		if(query.getSort() != null){
			if("quantityOp".equals(query.getSort().getValue())){
				query.setQuantityOp(query.getSort().getOp());
			}else if("amountOp".equals(query.getSort().getValue())){
				query.setAmountOp(query.getSort().getOp());
			}
		}
		List<Rules> rules = query.getRules();
		List<String> clientIdArray = new ArrayList<String>();
		if(rules != null && rules.size()>0){
			for(int i=0 ; i< rules.size() ;i++){
				if("productTypeArray".equals(rules.get(i).getFiled())&&(rules.get(i).getValue() !=null&&rules.get(i).getValue().size()>0)){
					query.setProductTypeArray(rules.get(i).getValue());
				}else if("productBrandArray".equals(rules.get(i).getFiled())&&(rules.get(i).getValue() !=null&&rules.get(i).getValue().size()>0)){
					query.setProductBrandArray(rules.get(i).getValue());
				}if("productSeasonArray".equals(rules.get(i).getFiled())&&(rules.get(i).getValue() !=null&&rules.get(i).getValue().size()>0)){
					query.setProductSeasonArray(rules.get(i).getValue());
				}if("productColorArray".equals(rules.get(i).getFiled())&&(rules.get(i).getValue() !=null&&rules.get(i).getValue().size()>0)){
					query.setProductColorArray(rules.get(i).getValue());
				}if("productSizeArray".equals(rules.get(i).getFiled())&&(rules.get(i).getValue() !=null&&rules.get(i).getValue().size()>0)){
					query.setProductSizeArray(rules.get(i).getValue());
				}if("operatorArray".equals(rules.get(i).getFiled())&&(rules.get(i).getValue() !=null&&rules.get(i).getValue().size()>0)){
					query.setOperatorArray(rules.get(i).getValue());
				}if("productClientCustomerArray".equals(rules.get(i).getFiled())&&(rules.get(i).getValue() !=null&&rules.get(i).getValue().size()>0)){
					query.setProductClientCustomerArray(rules.get(i).getValue());
				}if("productClientSuppierArray".equals(rules.get(i).getFiled())&&(rules.get(i).getValue() !=null&&rules.get(i).getValue().size()>0)){
					query.setProductClientSuppierArray(rules.get(i).getValue());
				}
			}
		}
		//供应商ID 和 客户ID  合并
		if(query.getProductClientCustomerArray()!=null && query.getProductClientCustomerArray().size()>0){
			for(String s: query.getProductClientCustomerArray()){
				clientIdArray.add(s);
			}
		}
		if(query.getProductClientSuppierArray()!=null && query.getProductClientSuppierArray().size()>0){
			for(String s: query.getProductClientSuppierArray()){
				clientIdArray.add(s);
			}
		}
		if(clientIdArray.size()>0){
			query.setClientIdArray(clientIdArray);
		}else{
			query.setClientIdArray(null);
		}
		Page page = new Page();
		page.setPageNum(query.getPageNum());
		//通过过滤查询条件，找到商品 商品列表默认十个
		List<PurchaseReportQuery> maps1 =  spReportMapper.purchaseReport3(query,page);
		List<String> productNames = new ArrayList<String>();
		if(maps1 !=null&& maps1.size()>0){
			for(PurchaseReportQuery q : maps1){
				productNames.add(q.getProductName());
			}
		}
		//通过商品，再根据商品分组汇总
		List<PurchaseReportQuery> maps2 = null;
		if(productNames.size()>0){
			query.setProductNameArray(productNames);
			maps2 =spReportMapper.purchaseReport1(query);
		}
		
		List<ProductReportQuery> maps3 =null;
		if(maps2 !=null){
			
			reportQuery.setPurchaseReportQuerys(maps2);
			
			ProductReportQuery q = new ProductReportQuery();
			q.setTimeFrom(query.getTimeFrom());
			q.setTimeTo(query.getTimeTo());
			q.setProductName(maps2.get(0).getProductName());
			q.setStoreId(query.getStoreId());
			ReportQuery r =  purchaseReportByProductName(q);
			
			reportQuery.setProductReportQuerys(r.getProductReportQuerys());
		}
		
		return reportQuery;
	}
	
	public  List<ProductReportQuery> getDatePoor(ProductReportQuery q) {
		 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = q.getTimeTo().getTime()-q.getTimeFrom().getTime();
	    // 计算差多少小时
	    long hour = diff / nh +1;
	    long timeInterval = hour / 8;
	    List<Date> timeFroms = new ArrayList<Date>();
	    timeFroms.add(q.getTimeFrom());
	    List<Date> timeTos = new ArrayList<Date>();
	    Date startDate = q.getTimeFrom();
	    for(int i=0;i<7; i++){
	    	Calendar c=Calendar.getInstance();
		    c.setTime(startDate);
		    c.add(Calendar.HOUR_OF_DAY, Integer.parseInt(timeInterval+""));
	    	if(c.getTime().getTime()<q.getTimeTo().getTime()){
	    		 timeFroms.add(c.getTime());
	    		 timeTos.add(c.getTime());
	    	}
	    	startDate = c.getTime();
	    }
	    timeTos.add(q.getTimeTo());
	    q.setTimeFroms(timeFroms);
	    q.setTimeTos(timeTos);
	    List<ProductReportQuery> querys = new ArrayList<ProductReportQuery>();
	    for(int i=0;i<timeFroms.size();i++){
	    	ProductReportQuery report = new ProductReportQuery();
	    	report.setProductName(q.getProductName());
	    	report.setTimeFrom(timeFroms.get(i));
	    	report.setTimeTo(timeTos.get(i));
	    	report.setStoreId(q.getStoreId());
	    	querys.add(report);
	    }
	    return querys;
	}
	@Override
	public ReportQuery purchaseReportByProductName(ProductReportQuery query) {
		ReportQuery reportQuery =new ReportQuery();
		
		//根据时间从，时间到获取8个时间区间，根据时间区间去汇总区间中的采购数量
		List<ProductReportQuery> querys = getDatePoor(query);
		//默认查询第一个商品所对应的趋势图
		if(querys.size()>0){
			query = querys.get(0);
			querys.remove(0);
			System.out.println(querys.size());
		}
		 List<ProductReportQuery> maps3 =null;
		maps3 =  spReportMapper.purchaseReport2(query,querys);
		reportQuery.setProductReportQuerys(maps3);
		return reportQuery;
	}

}