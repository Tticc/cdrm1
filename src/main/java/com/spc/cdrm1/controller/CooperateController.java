package com.spc.cdrm1.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.util.redisUtil.RedisUtil_Value;
import com.spc.cdrm1.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.This;

@Controller
@Slf4j
@RequestMapping("/coor")
public class CooperateController {

	private static final String COOPCONTENT = "coopContent";
	
	@Autowired
	private RedisUtil_Value redis_val;
	
	private StringBuffer sb = new StringBuffer("init");
	@GetMapping("/init")
	public String getPage(HttpServletRequest req) {
		String initContent = String.valueOf(redis_val.getValue(COOPCONTENT));
		this.sb.replace(0, this.sb.length(), initContent);
		req.setAttribute("content", initContent);
		return "coor";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public ResultVO updateContent(HttpServletRequest req, @RequestParam String content) {
		if(this.sb.toString().equals((content=String.valueOf(content)))) {
			return ResultVOUtil.error("no change");
		}
		this.sb.replace(0, this.sb.length(), content);		
		redis_val.setValue(COOPCONTENT, sb.toString());
		return ResultVOUtil.success(this.sb.toString());
	}
	
	@RequestMapping("get")
	@ResponseBody
	public ResultVO getContent(HttpServletRequest req, @RequestParam String content) {
		if(this.sb.toString().equals(String.valueOf(content))) {
			return ResultVOUtil.error("no change");
		}
		return ResultVOUtil.success(this.sb.toString());
	}
}
