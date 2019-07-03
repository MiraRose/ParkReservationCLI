package com.techelevator.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampsiteDAO implements CampsiteDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampsiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campsite> getAvailableCampsiteForCampground(long campgroundId, LocalDate startDate, LocalDate endDate) {
		String sql = "SELECT * FROM site JOIN campground ON site.campground_id = campground.campground_id "
				+ "WHERE site.campground_id = ? AND site.site_id NOT IN (SELECT s.site_id FROM site s "
				+ "JOIN reservation r ON s.site_id = r.site_id WHERE s.campground_id = ? "
				+ "AND (? > r.from_date AND ? < r.to_date OR ? > r.from_date "
				+ "AND ? < r.to_date) OR "
				+ "(r.from_date BETWEEN ? AND ? AND r.to_date BETWEEN ? AND ? )) LIMIT 5";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, campgroundId, campgroundId, startDate, startDate, endDate, endDate, startDate, endDate, startDate, endDate);
		
		List<Campsite> returnedCampsites = new ArrayList<Campsite>();
		
		while (result.next()) {
			returnedCampsites.add(mapRowToCampsite(result));
		}
		
		return returnedCampsites;
	}
	
	@Override
	public double getCampsiteDailyPriceByCampsiteID(long campsiteID) {
		String sql = "SELECT daily_fee FROM site JOIN campground ON site.campground_id = campground.campground_id WHERE site_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, campsiteID);
		result.next();
		String dailyFee = result.getString(1);
		dailyFee.replace("$", "");
		
		return Double.parseDouble(dailyFee);
	}

	private Campsite mapRowToCampsite(SqlRowSet result) {
		Campsite campsite = new Campsite();
		campsite.setSite_id(result.getLong("site_id"));
		campsite.setCampground_id(result.getLong("campground_id"));
		campsite.setSite_number(result.getInt("site_number"));
		campsite.setMax_occupancy(result.getInt("max_occupancy"));
		campsite.setAccessible(result.getBoolean("accessible"));
		campsite.setMax_rv_length(result.getInt("max_rv_length"));
		campsite.setUtilities(result.getBoolean("utilities"));
	
		return campsite;
		
	}

	
}
