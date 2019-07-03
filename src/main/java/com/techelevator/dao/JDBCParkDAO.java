package com.techelevator.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;





public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		String sql = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park ORDER BY name;";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		List<Park> allParks = new ArrayList<Park>();
		
		while (result.next()) {
			allParks.add(mapRowToPark(result));
		}
		
		
		return allParks;
	}
	
	private Park mapRowToPark(SqlRowSet result) {
		Park park = new Park();
		park.setPark_id(result.getLong("park_id"));
		park.setName(result.getString("name"));
		park.setLocation(result.getString("location"));
		park.setDateEstablished(result.getDate("establish_date"));
		park.setArea(result.getInt("area"));
		park.setNumOfVisitors(result.getInt("visitors"));
		park.setDescription(result.getString("description"));
		
		
		
		return park;
		
//		private long park_id;
//		private String name;
//		private String location;
//		private Date dateEstablished;
//		private int area;
//		private int numOfVisitors;
//		private String description;
	}

}
