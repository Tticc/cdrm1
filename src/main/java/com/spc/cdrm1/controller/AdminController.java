package com.spc.cdrm1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.vo.ResultVO;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/sync")
	public ResultVO syncronizeData() {
		return ResultVOUtil.success();
	}
}
