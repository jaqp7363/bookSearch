package com.jaqp7363.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jaqp7363.book.model.Keyword;
import com.jaqp7363.book.vo.KeywordVO;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
	
	@Query(value = "SELECT search_dt, keyword FROM (SELECT "
			+ "MAX(search_dt) AS search_dt, keyword "
			+ "FROM keyword_hist t "
			+ "WHERE t.userid = :userid "
			+ "GROUP BY t.keyword ) A ORDER BY search_dt DESC", nativeQuery=true)
	public List<Object[]> mySearchHist(@Param("userid") String userid);
	
	@Query(value = "SELECT COUNT(keyword) AS keywordCnt, keyword " + 
			"FROM keyword_hist " + 
			"WHERE search_dt > sysdate-1 " + 
			"GROUP BY keyword ORDER BY keywordCnt DESC LIMIT 10", nativeQuery=true)
	public List<Object[]> mostSearchList();
}
