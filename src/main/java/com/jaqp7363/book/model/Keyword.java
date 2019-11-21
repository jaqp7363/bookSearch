package com.jaqp7363.book.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.jaqp7363.book.vo.KeywordVO;

import lombok.Data;
@SqlResultSetMapping(
	name="mySearchHist",
	classes = @ConstructorResult(
		targetClass = KeywordVO.class,
		columns = {
			@ColumnResult(name="search_dt", type=Date.class),
			@ColumnResult(name="keyword", type=String.class)
		}
	)
)

@Data
@Table(name = "keyword_hist")
@Entity
@IdClass(KeywordPk.class)
public class Keyword {
	
	@Id
	@Column(name="userid")
	private String userid;

	@Id
	@Column(name="search_dt", nullable = false)
	private LocalDateTime  searchDt;
	
	private String keyword;
	
	public Keyword() {
		this.searchDt = LocalDateTime.now();
	}
	
}
