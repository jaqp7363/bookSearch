package com.jaqp7363.book.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaqp7363.book.service.BookService;

@RestController("BookController")
@RequestMapping("/book")
public class BookController {

	@Resource(name = "bookService")
	BookService bookService;
	
	@GetMapping("/searchHist")
	public @ResponseBody String searchHist(@RequestParam("query") String query) throws Exception {
		bookService.searchHist(query);
		Map<String, Object> returnJsonMap = new HashMap<String, Object>();
		returnJsonMap.put("message", "OK");
		returnJsonMap.put("status", "200");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(returnJsonMap);
	}
	
	@PostMapping("/keywordSearchBook")
	public String keywordSearchBook(@RequestParam("query") String query, @RequestParam("currentPage") String page) throws Exception {
		return bookService.searchBook(query, page);
	}
	
	@PostMapping("/mySearchHist")
	public @ResponseBody String mySearchHist() throws Exception {
		Map<String, Object> returnJsonMap = new HashMap<String, Object>();
		returnJsonMap.put("message", "OK");
		returnJsonMap.put("status", "200");
		returnJsonMap.put("data", bookService.mySearchHist());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(returnJsonMap);
	}
	
	@PostMapping("/mostSearchList")
	public @ResponseBody String mostSearchList() throws Exception {
		Map<String, Object> returnJsonMap = new HashMap<String, Object>();
		returnJsonMap.put("message", "OK");
		returnJsonMap.put("status", "200");
		returnJsonMap.put("data", bookService.mostSearchList());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(returnJsonMap);
	}
}
