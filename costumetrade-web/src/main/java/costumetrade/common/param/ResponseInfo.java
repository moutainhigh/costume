package costumetrade.common.param;

/**
 * <p>
 * Title: ResponseCodes.java<／p>
 * <p>
 * Description: <／p>
 * 
 * @author yh.yu
 * @date 2015年8月26日
 */
public enum ResponseInfo {

	SUCCESS(0, "success"), 
	EXCEPTION(-1, "系统异常"),
	
	NOT_DATA(1000, "查询无结果"), 
	INVALID_PARAM(1001,"非法参数"), 
	LOGIN_FAILURE(1002,"登录失败"), 
	LOGIN_EXPIRED(1003,"登录失效"), 
	AUTHENTICATION_FAILURE(1004,"认证失败"), 
	UNAUTHORIZED(1005, "无权限"), 
	BUSI_NOT_OPEN(1006,"业务未开通或已暂停"),
	PASSWORD_ERROR(1007,"密码错误"),
	MOBILE_VERIFICATION_CODE_ERROR(1008,"手机验证码错误"),
	LACK_PARAM(1009,"缺少参数"),
	TASK_EXPIRED(2000,"任务失效"), 
	OPERATE_EXPIRED(1010,"操作失败"),
	FILE_FORMAT(1011,"文件格式错误"),
	UPLOAD_EXCEPTION(1012,"上传失败"),
	NO_STOCK(1013,"缺少库存"),
	DATA_EXCEPTION(1014,"数据已存在"),
	SCAN_CUSTOMER_EXCEPTION(1015,"未扫描二维码或者店员不能加客户"),
	SCAN_SUPPLIER_EXCEPTION(1016,"未扫描二维码或者普通用户和店员不能加供应商"),
	SCAN_FRIEND_EXCEPTION(1017,"未扫描二维码或者普通用户和店员不能朋友"),
	SCAN_EMP_EXCEPTION(1019,"未扫描二维码或者该员工已存在"),
	RETURN_EXCEPTION(1018,"不允许作废"),
	ORDER_EXCEPTION(1020,"自家店铺不能下单"),
	CHANGE_EXCEPTION(1021,"转换格式失败"),
	PRINT_EXCEPTION(1022,"该打印机未启用"),
	;

	public final int code;

	public final String msg;

	private ResponseInfo(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
