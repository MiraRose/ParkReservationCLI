package com.techelevator;

import java.sql.SQLException;
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
import com.techelevator.dao.CampgroundDAO;
import com.techelevator.dao.JDBCCampgroundDAO;


public class JDBCCampgroundDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private CampgroundDAO dao;
	private JdbcTemplate jdbcTemplate; 
	private long testParkId;
//	private long testDepartmentId;

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

//		truncateTables();
		String sql = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (DEFAULT, 'TestPark', 'Ohio', '12-12-2012', '100', '100', 'This is a park') RETURNING park_id";
		jdbcTemplate = new JdbcTemplate(dataSource);
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		result.next();
		testParkId = result.getInt(1);
//		
//		String sql2 = "INSERT INTO department (department_id, name) VALUES (DEFAULT, 'Department of What') RETURNING department_id";
//		SqlRowSet result2 = jdbcTemplate.queryForRowSet(sql2);
//		result2.next();
//		testDepartmentId = result2.getInt(1);

		dao = new JDBCCampgroundDAO(dataSource);

	}

	@Test
	public void find_campgrounds_by_park_returns_campgrounds_in_park() {
		List<Campground> campgroundList = dao.searchCampgroundsByPark(testParkId);

		int campgroundListOriginalSize = campgroundList.size();

		String sql = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (DEFAULT, ?, 'TestCampground', '01', '12', '$35.00')";
		jdbcTemplate.update(sql, testParkId);

		List<Campground> campgroundListTwo = dao.searchCampgroundsByPark(testParkId);
		int campgroundListNewSize = campgroundListTwo.size();

		Assert.assertEquals(campgroundListOriginalSize + 1, campgroundListNewSize);
	}

}
