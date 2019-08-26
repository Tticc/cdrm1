package com.spc.cdrm1.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spc.cdrm1.entity.Product;
import com.spc.cdrm1.service.ProductService;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.util.redisUtil.RedisUtil_Value;
import com.spc.cdrm1.vo.ResultVO;

@RestController
@RequestMapping("/vue")
public class VueController1 {
	
	@Autowired
	private RedisUtil_Value redis_val;
	@Autowired
	private ProductService productService;

	@GetMapping("/index")
	public ResultVO index(){
		Map map = new HashMap<String, String>();
		map.put("hello", "world");
		return ResultVOUtil.success(map);
	}
	
	@GetMapping("/saveaprod")
	public ResultVO saveAProd() {
		ResultVO result = productService.findByNameLike("mi pen");
		int count = 1;
		if(result.getCode() == 0) {
			count = ((List<Product>) result.getData()).size();
		}
		Product entity = new Product("mi pen"+(++count), "能写很长时间的笔", "pen", 100);
		return productService.save(entity);
	}
	/**
	 * <ul>
	 * <li>查看获取到的entity在前端是怎么显示的。json</li>
	 * <li>查看@Data生成的toString。生成的是key-value对应的字符串</li>
	 * </ul>
	 * @author Wen, Changying
	 * @param id
	 * @return
	 * @date 2019年7月20日
	 */
	@GetMapping("/get/{id}")
	public ResultVO getProdById(@PathVariable int id) {
		ResultVO result = productService.getProdById(id);
		//object类型吗？Product类型
		System.out.println(result.getData().toString());
		//前一个和这一个有区别吗？没有区别;
		System.out.println(((Product)result.getData()).toString());
		return result;
	}
	
	//--------------------------- for test ---------------------------------------
	/**
	 * @PathVariable  
	 * http://localhost:8090/cdrm/vue/save?key=oom&value=hello
	 * @author Wen, Changying
	 * @param key
	 * @param value
	 * @return
	 * @date 2019年7月19日
	 */
	@GetMapping("/save/{key}/{value}")
	public ResultVO saveToRedis_path(@PathVariable String key, @PathVariable String value) {
		if(!redis_val.setValue(key, value)) {
			return ResultVOUtil.error("save {key:"+key+"} failed");
		}
		return ResultVOUtil.success("{key:"+key+"} was save");
	}
	
	/**
	 * @RequestParam  
	 * http://localhost:8090/cdrm/vue/save?key=oom&value=hello
	 * @author Wen, Changying
	 * @param key
	 * @param value
	 * @return
	 * @date 2019年7月19日
	 */
	@GetMapping("/save")
	public ResultVO saveToRedis_params(@RequestParam String key, @RequestParam String value) {
		if(!redis_val.setValue(key, value)) {
			return ResultVOUtil.error("save {key:"+key+"} failed");
		}
		return ResultVOUtil.success("{key:"+key+"} was save");
	}
	
	/**
	 * get value  
	 * http://localhost:8090/cdrm/vue/getval/oom
	 * @author Wen, Changying
	 * @param key
	 * @return
	 * @date 2019年7月19日
	 */
	@GetMapping("/getval/{key}")
	public ResultVO getVal(@PathVariable String key) {
		return ResultVOUtil.success(redis_val.getValue(key).toString());
	}
	
}
