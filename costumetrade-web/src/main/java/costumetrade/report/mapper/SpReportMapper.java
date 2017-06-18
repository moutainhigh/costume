package costumetrade.report.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import costumetrade.common.page.Page;
import costumetrade.report.domain.FinanceReportQuery;
import costumetrade.report.domain.ProductReportQuery;
import costumetrade.report.domain.PurchaseReportQuery;
@Mapper
public interface SpReportMapper {
	
	FinanceReportQuery financeReport(FinanceReportQuery query);
	
	List<Map<String, Object>> purchaseSortReport(PurchaseReportQuery query);
	
	List<PurchaseReportQuery> purchaseReport3(@Param("query")PurchaseReportQuery query,@Param("page")Page page);
	
	List<ProductReportQuery> purchaseReport2(@Param("query")ProductReportQuery query,@Param("querys")List<ProductReportQuery> querys);
	
	List<PurchaseReportQuery> purchaseReport1(@Param("query")PurchaseReportQuery query);
}