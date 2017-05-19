package costumetrade.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.openapi.common.entity.MessageResp;
import com.sf.openapi.express.sample.route.dto.RouteReqDto;
import com.sf.openapi.express.sample.route.dto.RouteRespDto;

import costumetrade.common.util.OrderNoGenerator;
import costumetrade.order.domain.ScLogistics;
import costumetrade.order.domain.ScStoreAddr;
import costumetrade.order.domain.SpClient;
import costumetrade.order.domain.SpProduct;
import costumetrade.order.domain.SsFinancial;
import costumetrade.order.domain.SsStoDetail;
import costumetrade.order.domain.SsStoOrder;
import costumetrade.order.domain.SsStock;
import costumetrade.order.domain.SsStockTransfer;
import costumetrade.order.mapper.ScLogisticsMapper;
import costumetrade.order.mapper.ScStoreAddrMapper;
import costumetrade.order.mapper.SpCartMapper;
import costumetrade.order.mapper.SpClientMapper;
import costumetrade.order.mapper.SpProductMapper;
import costumetrade.order.mapper.SsFinancialMapper;
import costumetrade.order.mapper.SsStoDetailMapper;
import costumetrade.order.mapper.SsStoOrderMapper;
import costumetrade.order.mapper.SsStockMapper;
import costumetrade.order.mapper.SsStockTransferMapper;
import costumetrade.order.query.OrderDetailQuery;
import costumetrade.order.query.OrderQuery;
import costumetrade.order.service.SFLogisticsService;
import costumetrade.order.service.SpOrderService;
import costumetrade.order.service.SpProductService;

@Transactional
@Service
public class SpOrderServiceImpl implements SpOrderService{
	@Autowired
	private SsStoDetailMapper ssStoDetailMapper;
	@Autowired
	private SsStoOrderMapper ssStoOrderMapper;
	@Autowired
	private SpCartMapper spCartMapper;
	@Autowired
	private SsFinancialMapper ssFinancialMapper;
	@Autowired
	private SpClientMapper spClientMapper;
	@Autowired 
	private ScStoreAddrMapper scStoreAddrMapper;
	@Autowired
	private ScLogisticsMapper scLogisticsMapper;
	@Autowired
	private SsStockMapper ssStockMapper;
	@Autowired
	private SsStockTransferMapper ssStockTransferMapper;
	@Autowired
	private SpProductMapper spProductMapper;
	@Autowired
	private SpProductService spProductService;
	@Autowired
	private SFLogisticsService sFLogisticsService;
	@Override
	public SsStoOrder saveOrders(List<SsStoDetail> details,SsStoOrder order,Integer clientId) {
		
		String orderNo = OrderNoGenerator.generate("O");
		List<SsStoDetail> detail = new ArrayList<SsStoDetail>();
		int save = 0;
		BigDecimal count = new BigDecimal(0.0);
		BigDecimal cost =   new BigDecimal(0.0);
		
		//订单状态 1：新增  2、已付款 3、审核 4、发货 5、收货 6、已取消
		if(details.size() > 0){
			for(SsStoDetail d : details){
				SsStoDetail d1 = d;
				d1.setOrderid(orderNo);
				cost = cost.add((d.getCount()).multiply(d.getPrice()))  ;
				count = count.add(d.getCount());
				detail.add(d);
			}
			order.setPayorderno(orderNo+"");
			order.setTotalamt(cost);
			order.setTotalnum(count);
			order.setOrderstatus(1);
			order.setOrdertime(new Date());
		}
		if("2".equals(order.getOrdertype())){//线下单据，一旦提交，就是完成状态，库存会相应扣减
			order.setOrderstatus(5);
			OrderQuery param = new OrderQuery();
			param.setClientId(clientId);
			param.setBuyerstoreid(order.getBuyerstoreid());
			param.setSellerstoreid(order.getSellerstoreid());
			param.setOperate(5);
			orderStock(param,detail);
		}
		
		SpClient client = spClientMapper.selectByPrimaryKey(clientId);
		if(client.getStoreid() == null){ //普通会员
			ssStoDetailMapper.saveDetail(detail,order.getSellerstoreid());
			save = ssStoOrderMapper.insert(order,order.getSellerstoreid());
		}else{
			//买家保存采购单
			ssStoDetailMapper.saveDetailStore(detail,order.getBuyerstoreid());
			save = ssStoOrderMapper.insertStore(order,order.getBuyerstoreid());
			//卖家保存销售单
			ssStoDetailMapper.saveDetailStore(detail,order.getSellerstoreid());
			save = ssStoOrderMapper.insertStore(order,order.getSellerstoreid());
		}
		SsStoOrder o = null ;
		if(save >0){
			o = order(orderNo, order.getSellerstoreid());
		}
		return o;
	}
	@Override
	public OrderDetailQuery getOrder(String orderNo ,Integer orderType, Integer clientId) {
		OrderDetailQuery	query = new OrderDetailQuery();	
		SpClient client = spClientMapper.selectByPrimaryKey(clientId);
		
		SsStoOrder spStoOrder = new SsStoOrder();
		List<SsStoOrder> spStoOrders = new ArrayList<SsStoOrder>();
		List<SsStoDetail> ssStoDetails = new ArrayList<SsStoDetail>();
		if(client.getStoreid() == null){
			spStoOrder.setPayorderno(orderNo);
			spStoOrders = ssStoOrderMapper.selectByOrderMember(spStoOrder);
			ssStoDetails = ssStoDetailMapper.selectByOrderIdMember(orderNo);
		}else{
			spStoOrder.setStoreId(client.getStoreid());
			spStoOrders = ssStoOrderMapper.selectByOrderStore(spStoOrder);
			ssStoDetails = ssStoDetailMapper.selectByOrderId(orderNo,client.getStoreid());
		}
		query.setSsStoDetail(ssStoDetails);
		query.setSsStoOrder(spStoOrders.get(0));
		return query;
	}

	@Override
	public int orderOperate(OrderQuery param) {

		int operate = 0;
		SsStoOrder spStoOrder = new SsStoOrder();
		SpClient client = spClientMapper.selectByPrimaryKey(param.getClientId());
		spStoOrder.setSellerstoreid(param.getSellerstoreid());
		spStoOrder.setBuyerstoreid(param.getBuyerstoreid());
		spStoOrder.setPayorderno(param.getOrderNo());
		spStoOrder.setOrderstatus(param.getOperate());
		
		/**审核配货逻辑整理：
		1、确定有没有库存
		2、修改库存记录ss_stock  卖家
		3、新增对库存的交易记录   卖家
		 * 
		 * */
	
		if(param.getOperate() == 3){ //审核  配货
			orderStock(param,null);
		}
		if(client.getStoreid() == null){
			operate = ssStoOrderMapper.updateByPrimaryKeySelective(spStoOrder);
		}else{
			operate = ssStoOrderMapper.updateByPrimaryKeySelectiveStore(spStoOrder);
		}
	
		if(operate > 0){
			return 1;
		}else{
			return 0;
		}
		
	}
	public void orderStock(OrderQuery param,List<SsStoDetail> d){
		List<SsStoDetail> ssStoDetails = new ArrayList<SsStoDetail>();
		List<SsStock> ssStocks = new ArrayList<SsStock>();
		List<SsStock> ssStock = new ArrayList<SsStock>(); //对应店家查询的现有库存
		
		List<SsStockTransfer> transfers = new ArrayList<SsStockTransfer>();
		List<SsStockTransfer> ssStockTransferBuyyer = new ArrayList<SsStockTransfer>();//买家交易记录
		List<SsStockTransfer> ssStockTransferSeller = new ArrayList<SsStockTransfer>(); //卖家交易记录
		List<SpProduct> product = new ArrayList<SpProduct>(); //商品集合信息
		List<String> ids = new ArrayList<String>();//商品ID集合
		
		SpClient client = spClientMapper.selectByPrimaryKey(param.getClientId());
		boolean stockTag = true;
		if(d == null || d.size()==0){
			ssStoDetails = ssStoDetailMapper.selectByOrderId(param.getOrderNo(),param.getSellerstoreid());
		}else{
			ssStoDetails =d;
		}
		
		for(SsStoDetail detail : ssStoDetails){
			
			SsStock stock = new SsStock();
			stock.setProductid(detail.getProductid());
			stock.setProductcolor(detail.getProductcolor());
			stock.setProductsize(detail.getProductsize());
			stock.setStocknum(Double.parseDouble(detail.getCount().toString()));
			stock.setProductPrice(detail.getPrice());
			stock.setStoreid(param.getSellerstoreid());
			ssStocks.add(stock);
			ids.add(detail.getProductid());
		
			List<SsStock> s = ssStockMapper.select(stock);
			if(s == null || s.size()<=0){
				stockTag = false ; //不存在库存
				break;
			}else{
				stock = s.get(0);
				ssStock.add(stock);
			}
			SsStockTransfer transfer = new SsStockTransfer();
			transfer.setProductid(detail.getProductid());
			transfer.setProductcolor(detail.getProductcolor());
			transfer.setProductsize(detail.getProductsize());
			transfer.setCount(detail.getCount());
			transfer.setTransfertoid(param.getSellerstoreid());
			transfer.setAmount(detail.getCount().multiply(detail.getPrice()));
			transfers.add(transfer);
		}
		int updateSellerStock = 0;
		if(stockTag){
			/**审核配货逻辑整理：
			1、确定有没有库存
			2、修改库存记录ss_stock  卖家
			3、新增对库存的交易记录   卖家
			 * 
			 * */
			for(SsStockTransfer transfer : transfers){
				transfer.setTransferfromid(param.getClientId());
				transfer.setStockType(2);//调出
				ssStockTransferSeller.add(transfer);
			}
			if(param.getOperate() == 3||( param.getOperate() == 5 && param.getOrderType() == 2)){
				ssStockTransferMapper.insert(ssStockTransferSeller,param.getSellerstoreid());//新增卖家交易记录
				for(int i=0 ; i< ssStocks.size(); i++){
					if(ssStocks.get(i).getProductid().equals(ssStock.get(i).getProductid())
							&& ssStocks.get(i).getProductcolor().equals(ssStock.get(i).getProductcolor())
							&& ssStocks.get(i).getProductsize().equals(ssStock.get(i).getProductsize())){
						SsStock stock = new SsStock();
						stock = ssStocks.get(i);
						stock.setStoreid(param.getSellerstoreid());
						stock.setStocknum(ssStock.get(i).getStocknum()-ssStocks.get(i).getStocknum());
						updateSellerStock = ssStockMapper.updateByPrimaryKeySelective(stock); //更新卖家库存
						
						//更新卖家的商品销量
						SpProduct p = spProductMapper.selectByPrimaryKey(ids.get(i), param.getSellerstoreid());
						p.setSaleNum(p.getSaleNum().add(BigDecimal.valueOf(ssStocks.get(i).getStocknum())));
						spProductMapper.updateByPrimaryKeySelective(p);
					}
				}
			}
			/**审核配货逻辑整理：
			1、确定有没有库存
			2、修改库存记录ss_stock  买家
			3、新增对库存的交易记录   买家
			 * 
			 * */
			product = spProductMapper.selectById(ids, param.getSellerstoreid());
			if(client.getStoreid() != null && param.getOperate() == 5){//如果买家的角色是店家，那么当买家收货或者开采购单
				for(SsStockTransfer transfer : transfers){
					transfer.setStockType(1);//调ru
					ssStockTransferBuyyer.add(transfer);
				}
				ssStockTransferMapper.insert(ssStockTransferBuyyer,param.getBuyerstoreid());//  新增买家交易记录
				for(int i=0 ; i< ssStocks.size(); i++){
					SsStock stock = new SsStock();
					stock = ssStocks.get(i);
					stock.setStoreid(param.getBuyerstoreid());
					List<SsStock> stocks = ssStockMapper.select(stock);//买家库存，存在更新，不存在新增库存
					SsStock stockBuyyer = new SsStock();
					if(stocks != null ){
						stockBuyyer = stocks.get(0);
					}
					if(stockBuyyer == null ){
						for(SpProduct p : product){
							if(p.getId().equals(ssStocks.get(i).getProductid())){
								p.setPurchaseprice(ssStoDetails.get(i).getPrice());
								spProductService.insertSuspendingProduct(p, param.getBuyerstoreid());
							}
						}
						ssStockMapper.insertSelective(stock);
					}else{
						stock.setStocknum(stockBuyyer.getStocknum()+ssStocks.get(i).getStocknum());
						updateSellerStock = ssStockMapper.updateByPrimaryKeySelective(stock); //更新买家库存
					}
				}
			}
		
		}
	}
	
	@Override
	public int orderPay(SsFinancial ssFinancial) {
		int operate = 0 ;
		ssFinancial.setTradeno(OrderNoGenerator.generate("P"));
		ssFinancial.setPaydate(new Date());
		
		SsStoOrder spStoOrder = new SsStoOrder();
		spStoOrder.setSellerstoreid(ssFinancial.getSellerid());
		spStoOrder.setPayorderno(ssFinancial.getOrderno());
		spStoOrder.setBuyerstoreid(ssFinancial.getBuyerid());
		spStoOrder.setOrderstatus(ssFinancial.getOperate());
		SpClient client = spClientMapper.selectByPrimaryKey(ssFinancial.getClientId());
		if(client.getStoreid() == null){
			operate = ssStoOrderMapper.updateByPrimaryKeySelective(spStoOrder);
		}else{
			operate = ssStoOrderMapper.updateByPrimaryKeySelectiveStore(spStoOrder);
		}
		if(operate <= 0){
			return 0;
		}
		//if(ssFinancial.getPayType() == 1){ //1:现金支付，2：支付宝支付，3：银行卡支付，4：微信支付，5：票据支付
		operate = ssFinancialMapper.insertSelective(ssFinancial);
		//}	
		return operate;
	}
	@Override
	public ScStoreAddr orderInit(Integer clientId) {
		/*
		 * 调转下单界面，自动初始化收货地址
		 * 如果是会员则获取client中的地址，
		 * 如果是店家则获取store中的收货地址
		 * 返回地址信息ScStoreAddr
		 * */
		ScStoreAddr addr = new ScStoreAddr();
		SpClient client = spClientMapper.selectByPrimaryKey(clientId);
		if(client != null){
			if(client.getStoreid() == null){
				addr.setAddress(client.getAddress());
				addr.setContact(client.getContact());
				addr.setPhone(client.getPhone());
			}else{
				addr = scStoreAddrMapper.selectAddr(client.getStoreid());
			}
		}else{
			return addr;
		}
		return addr;
	}
	@Override
	public SsStoOrder order(String orderNo, Integer storeId) {
		return ssStoOrderMapper.selectByOrderNo(orderNo,storeId);
	}
	@Override
	public List<SsStoOrder> getOrders(Integer orderType, Integer orderStatus,
			Integer clientId) {
		SpClient client = spClientMapper.selectByPrimaryKey(clientId);
		SsStoOrder spStoOrder = new SsStoOrder();
		List<SsStoOrder> spStoOrders = new ArrayList<SsStoOrder>();
		/*
		 * 1、待付款  2、待发货   3、待收货  4、待审核 5、全部
			采购单列签：1
			orderStatus   值                    备注
			待付款                                   1                       新增
			待发货                                2，3                  付款和审核
			待收货                                  4                       已发货
			全部                                 所有状态                线上单据+线下单据
		 * 
		 * 销售单列签：2
			orderStatus   值                    备注
			待付款                                    1                    新增
			待审核                                    2                    付款
			待发货                        	  3                    已审核
			全部                         所有状态              线上单据+线下单据
		 * */
		List<Integer> status = new ArrayList<Integer>();
		if(orderType == 1){  //采购单列签
			if(orderStatus == 2){
				status.add(3);
				status.add(2);
				spStoOrder.setOrdertype(1+"");//线上订单
			}else if(orderStatus == 3){
				status.add(4);
				spStoOrder.setOrdertype(1+"");//线上订单
			}
		}else if(orderType == 2){ //销售单列签
			if(orderStatus == 4){
				status.add(2);
				spStoOrder.setOrdertype(1+"");//线上订单
			}else if(orderStatus == 2){
				status.add(3);
				spStoOrder.setOrdertype(1+"");//线上订单
			}
		}
		if(orderStatus == 5){
			status.add(1);
			status.add(2);
			status.add(3);
			status.add(4);
			status.add(5);
			status.add(6);
		}else if(orderStatus == 1){
			status.add(1);
			spStoOrder.setOrdertype(1+"");//线上订单
		}
		spStoOrder.setStatus(status);
		if(orderType == 1){  //采购单列签
			if(client.getStoreid() == null){
				spStoOrders = ssStoOrderMapper.selectByOrderMember(spStoOrder);
			}else{
				spStoOrder.setStoreId(client.getStoreid());
				spStoOrder.setBuyerstoreid(client.getStoreid());
				spStoOrders = ssStoOrderMapper.selectByOrderStore(spStoOrder);
			}
		}else if(orderType == 2){//销售单列签
			if(client.getStoreid() != null){
				spStoOrder.setStoreId(client.getStoreid());
				spStoOrder.setSellerstoreid(client.getStoreid());
				spStoOrders = ssStoOrderMapper.selectByOrderStore(spStoOrder);
			}
		}
		return spStoOrders;
	}
	@Override
	public int confirmLogistic(ScLogistics scLogistics) {
		scLogistics.setCreatetime(new Date());
		ScLogistics Logistics =scLogisticsMapper.selectByLogistic(scLogistics);
		int save = 0;
		if(Logistics !=null){
			scLogistics.setId(Logistics.getId());
			save =scLogisticsMapper.updateByPrimaryKeySelective(scLogistics);
		}else {
			save =scLogisticsMapper.insertSelective(scLogistics);
		}
		return save ;
	}
	@Override
	public MessageResp<List<RouteRespDto>> queryLogistic(SsStoOrder ssStoOrder) {
		ScLogistics Logistics = new ScLogistics();
		Logistics.setOrderno(ssStoOrder.getPayorderno());
		Logistics.setStoreid(ssStoOrder.getSellerstoreid());
		Logistics = scLogisticsMapper.selectByLogistic(Logistics);
		if(Logistics != null && "顺丰".equals(Logistics.getLogisticsname())){
			RouteReqDto routeReqDto = new RouteReqDto();
			routeReqDto.setMethodType(1);
			routeReqDto.setTrackingNumber(Logistics.getLogisticsno());
			routeReqDto.setTrackingType(1);
			return sFLogisticsService.queryRouteSF(routeReqDto);
		}else{
			return null;
		}
		
	}
	
	

}
