package com.jaqp7363;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class H2CreateTable implements ApplicationRunner {
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		try(Connection con = datasource.getConnection()) {
            // table 생성
            /*Statement statement = con.createStatement();
            String sql = "CREATE TABLE USER(id INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
            statement.executeUpdate(sql);*/
		}
		jdbcTemplate.execute("INSERT INTO users(userid, email, password_hash, role) VALUES ('guest', 'guest@daum.net', '366603b443ad09bd9b8bf9455d13da73bcfd84fd9d3def2597e5cdc6797bf0cd','USER')");
	}
}
