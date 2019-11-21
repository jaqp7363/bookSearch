package com.jaqp7363.book.vo;

import lombok.Data;

@Data
public class MostKeywordVO {
	private Long keywordCnt;
	private String keyword;
	
	public MostKeywordVO(Long keywordCnt, String keyword) {
		this.keywordCnt = keywordCnt;
		this.keyword = keyword;
	}
}
