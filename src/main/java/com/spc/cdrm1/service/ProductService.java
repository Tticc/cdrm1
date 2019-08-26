package com.spc.cdrm1.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spc.cdrm1.dao.ProductRepository;
import com.spc.cdrm1.entity.Product;
import com.spc.cdrm1.util.CustomizeDataSource;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.util.enums.DataSourceKey;
import com.spc.cdrm1.util.enums.ResultEnum;
import com.spc.cdrm1.vo.ResultVO;

@Service("productService")
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	//@Transactional(transactionManager="transactionManagerMysql")
	//使用不同的事务会使之保存到不同的数据库吗？？？？？？？？？？？？？？？？？？？？？？？？？
	//@Transactional(transactionManager="transactionManagerOracle")
	@CustomizeDataSource("mysql")
	//@CustomizeDataSource("oracle")
	public ResultVO save(Product entity) {
		try {
			productRepository.save(entity);
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResultVOUtil.error(ex.toString());
		}
		return ResultVOUtil.success(ResultEnum.SUCCESS.getMessage());
	}

	public ResultVO getProdById(long id) {
		/*Product entity;
		try {
			entity = productRepository.findById((long) id).get();
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResultVOUtil.error(ResultEnum.ERROR.getCode() ,ex.getMessage());
		}
		return ResultVOUtil.success(entity);*/
		return ResultVOUtil.success(productRepository.findById(id).get());
		
	}
	
	public ResultVO getALL() {
		return ResultVOUtil.success(productRepository.findAll());
	}

	public ResultVO findByNameLike(String name) {
		List<Product> prodList = productRepository.findByNameLike("%"+name+"%");
		if(prodList == null || prodList.size() == 0) {
			return ResultVOUtil.error("no product");
		}
		return ResultVOUtil.success(prodList);
	}
}
