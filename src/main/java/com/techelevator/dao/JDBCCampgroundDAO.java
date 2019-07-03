package com.techelevator.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;



public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> searchCampgroundsByPark(long park_id) {
		String sql = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee FROM campground WHERE park_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, park_id);
		
		List<Campground> returnedCampgrounds = new ArrayList<Campground>();
		
		while (result.next()) {
			returnedCampgrounds.add(mapRowToCampground(result));
		}
		
		return returnedCampgrounds;

	}

	private Campground mapRowToCampground(SqlRowSet result) {
		Campground campground = new Campground();
		campground.setCampground_id(result.getLong("campground_id"));
		campground.setPark_id(result.getLong("park_id"));
		campground.setName(result.getString("name"));
		campground.setOpenFromMonth(result.getString("open_from_mm"));
		campground.setOpenToMonth(result.getString("open_to_mm"));
		campground.setDailyFee(result.getString("daily_fee"));
		
		
		
		return campground;
		

	}
	
}
