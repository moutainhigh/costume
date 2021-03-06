package costumetrade.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import costumetrade.common.page.Page;
import costumetrade.report.domain.GeneralReportQuery;
import costumetrade.user.domain.SsPayment;
@Mapper
public interface SsPaymentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsPayment record);

    int insertSelective(SsPayment record);

    SsPayment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsPayment record);

    int updateByPrimaryKey(SsPayment record);
    
    SsPayment countRepay(SsPayment record);
    
    List<SsPayment> selects(@Param("query")GeneralReportQuery query,@Param("page")Page page);
}