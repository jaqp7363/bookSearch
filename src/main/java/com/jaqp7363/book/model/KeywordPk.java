package com.jaqp7363.book.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
class KeywordPk implements Serializable{
	private String userid;
	private LocalDateTime searchDt;

}
