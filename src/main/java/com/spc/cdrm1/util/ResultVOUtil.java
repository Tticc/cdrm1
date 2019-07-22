package com.spc.cdrm1.util;

import com.spc.cdrm1.util.enums.MyEnum;
import com.spc.cdrm1.util.enums.ResultEnum;
import com.spc.cdrm1.vo.ResultVO;

/**
 * ResultVO 辅助类
 * @author Wen, Changying
 * 2019年7月18日
 */
public class ResultVOUtil {

	/**
	 * 成功
	 * @author Wen, Changying
	 * @return
	 * @date 2019年7月20日
	 */
	public static ResultVO success() {
		return success(null);
	}
	/**
	 * 成功，设置数据
	 * @author Wen, Changying
	 * @param object
	 * @return
	 * @date 2019年7月20日
	 */
	public static ResultVO success(Object object) {
		return setVO(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),object);
	}
	
	/**
	 * 失败
	 * @author Wen, Changying
	 * @param enu MyEnum
	 * @return
	 * @date 2019年7月20日
	 */
	public static ResultVO error(MyEnum enu) {
		return setVO(enu.getCode(),enu.getMessage(),null);
	}
	/**
	 * 失败
	 * @author Wen, Changying
	 * @param code
	 * @param msg
	 * @return
	 * @date 2019年7月20日
	 */
	public static ResultVO error(int code, String msg) {
		return setVO(code,msg,null);
	}
	
	/**
	 * 构造 ResultVO
	 * @author Wen, Changying
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 * @date 2019年7月20日
	 */
	private static ResultVO setVO(int code, String msg, Object data) {
		ResultVO resultVo = new ResultVO();
		resultVo.setData(data);
		resultVo.setCode(code);
		resultVo.setMessage(msg);
		return resultVo;
	}
}
