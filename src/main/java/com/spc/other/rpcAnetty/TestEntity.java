package com.spc.other.rpcAnetty;

import lombok.Data;

@Data
public class TestEntity {
	public TestEntity() {
		this.id=1;
		this.name="chad";
		this.desc="desc";
		this.brief="brief";
		this.version = 1l;
	}
	private int id;
	private String name;
	private String desc;
	private String brief;
	private long version;
}
