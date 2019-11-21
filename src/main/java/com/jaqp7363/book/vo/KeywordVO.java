package com.jaqp7363.book.vo;

import java.util.Date;

import lombok.Data;

@Data
public class KeywordVO {
	private Date search_dt;
	private String keyword;
	
	public KeywordVO(Date search_dt, String keyword) {
		this.search_dt = search_dt;
		this.keyword = keyword;
	}
}
