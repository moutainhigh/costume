package costumetrade.user.control;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import costumetrade.common.param.ApiResponse;
import costumetrade.common.param.ResponseInfo;
import costumetrade.common.util.StringUtil;
import costumetrade.order.domain.ScStoreAddr;
import costumetrade.order.service.WeChatService;
import costumetrade.user.domain.ScWeChat;
import costumetrade.user.domain.SpEmployee;
import costumetrade.user.domain.SpStore;
import costumetrade.user.query.ScUserQuery;
import costumetrade.user.query.StoreQuery;
import costumetrade.user.service.SpEmployeeService;
import costumetrade.user.service.SpUserService;



/**
 *
 * 
 * @author fancy
 * @Date 2017年4月21日
 */
@RequestMapping("/user")
@Controller
public class SpUserController {
	@Autowired
	private SpUserService spUserService;
	@Autowired
	private WeChatService weChatService;
	@Autowired
	private SpEmployeeService spEmployeeService;
	
	@RequestMapping("/login")
	@ResponseBody
	public ApiResponse login(String code,String appId,String appSecret) throws Exception {
		ApiResponse result = new ApiResponse();
		System.out.println("欢迎进入小程序");
		result.setCode(ResponseInfo.SUCCESS.code);
		result.setMsg(ResponseInfo.SUCCESS.msg);
		if(code == null){
			result.setCode(ResponseInfo.LACK_PARAM.code);
			result.setMsg(ResponseInfo.LACK_PARAM.name());
			return result;
		}
		String openIdAndKey = weChatService.getOpenIdAndKey(code,appId,appSecret);
		JSONObject json = JSON.parseObject(openIdAndKey);
		String openid = json.getString("openid");
		System.out.println("欢迎进入小程序"+openIdAndKey);
	
		ScUserQuery resultQuery = new ScUserQuery();
		resultQuery.setSessionKey(json.getString("session_key"));
		resultQuery.setOpenid(openid);
		if(resultQuery == null){
			result.setData(ResponseInfo.NOT_DATA.code);
			result.setMsg(ResponseInfo.NOT_DATA.msg);
			return result;
		}else{
			result.setData(resultQuery);
		}
		return  result;
	}
	@RequestMapping("/saveUserOrStore")
	@ResponseBody
	public ApiResponse saveUserOrStore(@RequestBody SpStore store) {
		ApiResponse result = new ApiResponse();
		result.setCode(ResponseInfo.SUCCESS.code);
		result.setMsg(ResponseInfo.SUCCESS.msg);
		if(store == null){
			result.setCode(ResponseInfo.LACK_PARAM.code);
			result.setMsg(ResponseInfo.LACK_PARAM.name());
			return result;
		}
		Integer obj = spUserService.saveUserOrStore(store);
		return  ApiResponse.getInstance(obj);
	}
	
	@RequestMapping("/getAddressList")
	@ResponseBody
	public ApiResponse getAddressList(String openid) {
		ApiResponse result = new ApiResponse();
		result.setCode(ResponseInfo.SUCCESS.code);
		result.setMsg(ResponseInfo.SUCCESS.msg);
		if(openid == null){
			result.setCode(ResponseInfo.LACK_PARAM.code);
			result.setMsg(ResponseInfo.LACK_PARAM.name());
			return result;
		}
		List<ScStoreAddr> addrs = spUserService.getAddressList(openid);
		return  ApiResponse.getInstance(addrs);
	}
	
	@RequestMapping("/getUnionId")
	@ResponseBody
	public ApiResponse getUnionId(String encryptedData,String iv,String sessionKey) {
		ApiResponse result = new ApiResponse();
		result.setCode(ResponseInfo.SUCCESS.code);
		result.setMsg(ResponseInfo.SUCCESS.msg);
		if(StringUtil.isBlank(encryptedData)){
			result.setCode(ResponseInfo.LACK_PARAM.code);
			result.setMsg(ResponseInfo.LACK_PARAM.name());
			return result;
		}
		ScWeChat wechat = spUserService.getUnionId(encryptedData,iv,sessionKey);
		ScUserQuery resultQuery = new ScUserQuery();
		StoreQuery query = new StoreQuery();
		if(wechat != null && StringUtil.isNotBlank(wechat.getOpenid())){
			query.setOpenid(wechat.getOpenid());
			query.setStoreId(wechat.getStoreid());
			if(StringUtil.isNotBlank(wechat.getStoreid())){
				if(wechat.getEmpid()!=null){
					resultQuery.setUserIdentity(3);//员工身份
					resultQuery.setEmpId(wechat.getEmpid());
				}else{
					resultQuery.setUserIdentity(1);//店家身份
				}
				resultQuery.setStoreId(wechat.getStoreid());
			}else{
				resultQuery.setUserIdentity(2);//普通消费者
				resultQuery.setUserid(wechat.getUserid());
			}
		}
		query = spUserService.getStores(query);
		resultQuery.setQuery(query);
		SpEmployee employee = spEmployeeService.getEmployeePrivilege(wechat.getOpenid());
		SpEmployee e = new SpEmployee();
		if(employee!=null){
			e.setPrivilegeEmployees(employee.getPrivilegeEmployees());
			e.setZeroPrice(employee.getZeroPrice());
			e.setDiscount(employee.getDiscount());
			e.setModifyPrice(employee.getModifyPrice());
		}
		resultQuery.setEmployee(e);
			
		
		return  ApiResponse.getInstance(resultQuery);
	}
	@RequestMapping("/saveAddress")
	@ResponseBody
	public ApiResponse saveAddress(ScStoreAddr addr) {
		ApiResponse result = new ApiResponse();
		result.setCode(ResponseInfo.SUCCESS.code);
		result.setMsg(ResponseInfo.SUCCESS.msg);
		if(StringUtils.isBlank(addr.getOpenid())){
			result.setCode(ResponseInfo.LACK_PARAM.code);
			result.setMsg(ResponseInfo.LACK_PARAM.name());
			return result;
		}
		int save = spUserService.saveAddress(addr);
		if(save <=0){
			result.setCode(ResponseInfo.OPERATE_EXPIRED.code);
			result.setMsg(ResponseInfo.OPERATE_EXPIRED.name());
			return result;
		}
		return  result;
	}

	
}
