package com.techelevator;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.dao.Campground;
import com.techelevator.dao.Campsite;
import com.techelevator.dao.CampsiteDAO;

import com.techelevator.dao.JDBCCampsiteDAO;

public class JDBCCampsiteDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private CampsiteDAO dao;
	private JdbcTemplate jdbcTemplate; 
	private long testCampsiteID;
	private long testCampgroundID;
	

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/*
		 * The following line disables autocommit for connections returned by this
		 * DataSource. This allows us to rollback any changes after each test
		 */
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Before
	public void setup() {

		String sql2 = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (DEFAULT, 1, 'TestPark', '12', '03', '$35.00') RETURNING campground_id";
		jdbcTemplate = new JdbcTemplate(dataSource);
		SqlRowSet result2 = jdbcTemplate.queryForRowSet(sql2);
		result2.next();
		testCampgroundID = result2.getInt(1);
		
		String sql = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) VALUES (DEFAULT, ?, 1, 6, true, 0, false) RETURNING site_id";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, testCampgroundID);
		result.next();
		testCampsiteID = result.getInt(1);

		dao = new JDBCCampsiteDAO(dataSource);

	}
	
	@Test
	public void find_available_campsites_by_campground_returns_available_campsites() {
		
		List<Campsite> campsiteList = dao.getAvailableCampsiteForCampground(testCampgroundID, LocalDate.of(2012, 04, 04), LocalDate.of(2012, 05, 04));

		int campsiteListOriginalSize = campsiteList.size();

		String sql = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) VALUES (DEFAULT, ?, 1, 6, true, 0, false)";
		jdbcTemplate.update(sql, testCampgroundID);

		List<Campsite> campsiteListTwo = dao.getAvailableCampsiteForCampground(testCampgroundID, LocalDate.of(2012, 04, 04), LocalDate.of(2012, 05, 04));
		int campsiteListNewSize = campsiteListTwo.size();

		Assert.assertEquals(campsiteListOriginalSize + 1, campsiteListNewSize);
	}
	
	@Test
	public void get_campsite_daily_price_returns_daily_price() {
		double actual = dao.getCampsiteDailyPriceByCampsiteID(testCampsiteID);
		
		Assert.assertEquals(35.00, actual, 2);
	}
	
}
