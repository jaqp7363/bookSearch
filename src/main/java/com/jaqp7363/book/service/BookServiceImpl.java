package com.jaqp7363.book.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaqp7363.book.model.Keyword;
import com.jaqp7363.book.repository.KeywordRepository;
import com.jaqp7363.book.vo.KeywordVO;
import com.jaqp7363.book.vo.MostKeywordVO;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
	final int API_KAKAO = 0;
	final int API_NAVER = 1;
	
	@Autowired
	KeywordRepository keywordRepository;
	
	@SuppressWarnings("unchecked")
	private StringBuffer connetion(int type, String query, String page) throws Exception{
		StringBuffer response = null;
		try {
			BufferedReader br = null;
			HttpURLConnection conn = null;
			URL url = null;
			String urlStr = "";
			if(type == API_KAKAO) {
				urlStr = "https://dapi.kakao.com/v3/search/book?query="+URLEncoder.encode(query, "UTF-8");
				if(page != null) urlStr += "&page="+page;
				url = new URL(urlStr);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("Authorization", "KakaoAK 9b5806fa967119e477b8ed26bb296681");
			}else {
				urlStr = "https://openapi.naver.com/v1/search/book.json?query="+URLEncoder.encode(query, "UTF-8");
				if(page != null) {
					int start = (Integer.parseInt(page)-1)*10+1;
					urlStr += "&start="+start;
				}
				url = new URL(urlStr);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("X-Naver-Client-Id", "4aVdNx5wbYt9J0vPzDUm");
				conn.setRequestProperty("X-Naver-Client-Secret", "95QGq4K5Uf");
				
			}
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(2000);
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) { 
				response.append(inputLine);
			}
			if(type == API_NAVER) {
				JSONParser jsonParser = new JSONParser();
				JSONObject nObj = (JSONObject) jsonParser.parse(response.toString());
				JSONArray nList = (JSONArray) nObj.get("items");

				JSONObject metaObj = new JSONObject();
				JSONArray bookList = new JSONArray();
				JSONObject jsonObj = new JSONObject();

				metaObj.put("total_count", nObj.get("total"));

				nList.forEach(n -> {
					JSONObject temp = new JSONObject();
					JSONObject obj = (JSONObject)n;
					temp.put("authors", obj.get("author"));
					temp.put("contents", obj.get("description"));
					temp.put("datetime", obj.get("pubdate"));
					temp.put("isbn", obj.get("isbn"));
					temp.put("price", obj.get("price"));
					temp.put("publisher", obj.get("publisher"));
					temp.put("sale_price", obj.get("discount"));
					temp.put("thumbmail", obj.get("image"));
					temp.put("title", obj.get("title"));
					bookList.add(temp);
				});
				jsonObj.put("documents", bookList);
				jsonObj.put("meta", metaObj);
				response = null;
				response = new StringBuffer();
				response.append(jsonObj.toJSONString());
			}
			br.close();
		}catch(SocketTimeoutException e) {
			if(type == API_KAKAO) return connetion(API_NAVER, query, page);
			else {
				e.printStackTrace();
				throw new SocketTimeoutException();
			}
		}catch(UnknownHostException e) {
			if(type == API_KAKAO) return connetion(API_NAVER, query, page);
			else {
				e.printStackTrace();
				throw new UnknownHostException();
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		
		return response;
	}
	
	@Override
	public String searchBook(String query, String page) throws Exception {
		StringBuffer response = connetion(API_KAKAO, query, page);
		return response.toString();
	}

	@Override
	public void searchHist(String query) {
		Keyword keyword = new Keyword();
		keyword.setKeyword(query);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userid = principal.toString();
		keyword.setUserid(principal.toString());
		keywordRepository.save(keyword);
	}

	@Override
	@Transactional(readOnly=true)
	public String mySearchHist() throws Exception {
		StringBuffer sb = new StringBuffer();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Object[]> objList = keywordRepository.mySearchHist(principal.toString());
		sb.append("<tr style=\"margin:5px 0 10px 0;\"><th>검색어</th><th>검색일시</th></tr>");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(objList == null || objList.size()==0) {
			sb.append("<td colspan=\"4\" style=\"text-align: center;\">검색이력이 없습니다.</td>");
		}else {
			List<KeywordVO> list = objList.stream().map(
					obj -> new KeywordVO(
						(Date) obj[0],
						(String) obj[1]
					)).collect(Collectors.toList());
			list.forEach(keyword -> {
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(keyword.getKeyword());
				sb.append("</td>");
				sb.append("<td>");
				sb.append(format.format(keyword.getSearch_dt()));
				sb.append("</td>");
				sb.append("</tr>");
			});
		}
		return sb.toString();
	}

	@Override
	@Transactional(readOnly=true)//, isolation=Isolation.READ_UNCOMMITTED)
	public String mostSearchList() throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object[]> objList = keywordRepository.mostSearchList();
		sb.append("<tr style=\"margin:5px 0 10px 0;\"><th>검색어</th><th>검색횟수</th></tr>");
		if(objList == null || objList.size()==0) {
			sb.append("<td colspan=\"2\" style=\"text-align: center;\">검색이력이 없습니다.</td>");
		}else {
			List<MostKeywordVO> list = objList.stream().map(
					obj -> new MostKeywordVO(
						((BigInteger) obj[0]).longValue(),
						(String) obj[1]
					)).collect(Collectors.toList());
			list.forEach(keyword -> {
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(keyword.getKeyword());
				sb.append("</td>");
				sb.append("<td>");
				sb.append(keyword.getKeywordCnt()+" 번");
				sb.append("</td>");
				sb.append("</tr>");
			});
		}
		return sb.toString();
	}
}
