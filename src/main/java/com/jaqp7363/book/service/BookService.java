package com.jaqp7363.book.service;

public interface BookService {

	String searchBook(String query, String page) throws Exception;
	
	void searchHist(String query);
	
	String mySearchHist() throws Exception;
	
	String mostSearchList() throws Exception;
}
