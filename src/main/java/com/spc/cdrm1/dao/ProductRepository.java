package com.spc.cdrm1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spc.cdrm1.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

	/**
	 * JPA的特性，能够根据方法名判断查询条件。这里就是根据name来查询
	 * @author Wen, Changying
	 * @param name
	 * @return
	 * @date 2019年7月19日
	 */
	List<Product> findByName(String name);
	/**
	 * like 模糊查询尝试
	 * @author Wen, Changying
	 * @param name
	 * @return
	 * @date 2019年7月22日
	 */
	List<Product> findByNameLike(String name);
}
