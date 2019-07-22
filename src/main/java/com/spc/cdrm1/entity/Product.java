package com.spc.cdrm1.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Entity
// mysql
// @Table(name="GGFW.WCY_SPC_PRODUCT")

// sql server
// @table(name="表名",schema="用户名",catalog="数据库名")

// oracle
// @table(name="表名",schema="用户名",catalog="")

@Table(name="WCY_SPC_PRODUCT", schema="gdggfw",catalog="")
@Data
public class Product implements Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//id
	
	private String name;//名字
	private String info;//描述
	private String type;//类型
	private int num;//数量
	private Timestamp create_time;	
	private Timestamp update_time;	
	@Version
	private int version;//版本号
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public Product() {}
	/**
	 * 有参构造器
	 * @param name name(String)
	 * @param info info(String)
	 * @param type type(String)
	 * @param num num(int)
	 */
	public Product(String name, String info, String type, int num) {
		this.name = name;
		this.info = info;
		this.type = type;
		this.num = num;
		this.create_time = this.update_time = new Timestamp(new Date().getTime());
		this.version = 1;
	}

}
