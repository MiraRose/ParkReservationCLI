package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import com.techelevator.dao.CampsiteDAO;
import com.techelevator.dao.JDBCCampsiteDAO;
import com.techelevator.dao.JDBCReservationDAO;
import com.techelevator.dao.Reservation;
import com.techelevator.dao.ReservationDAO;

public class JDBCReservationDAOIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private ReservationDAO dao;
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

		jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sql2 = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (DEFAULT, 1, 'TestPark', '12', '03', '$35.00') RETURNING campground_id";
		SqlRowSet result2 = jdbcTemplate.queryForRowSet(sql2);
		result2.next();
		testCampgroundID = result2.getInt(1);
//		
		String sql = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) VALUES (DEFAULT, ?, 1, 6, true, 0, false) RETURNING site_id";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, testCampgroundID);
		result.next();
		testCampsiteID = result.getInt(1);

		dao = new JDBCReservationDAO(dataSource);

	}
	
	@Test
	public void save_new_reservation_and_read_it_back() throws SQLException {
		Reservation reservation = getReversation();

		Reservation savedReservation = dao.createReservation(reservation);
		String sql = "SELECT * FROM reservation WHERE reservation_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, savedReservation.getReservationId());
		result.next();
		long actual = result.getLong("reservation_id");

		Assert.assertNotEquals(null, actual);
//		assertCitiesAreEqual(theCity, savedCity);
	}
	
	private Reservation getReversation() {
		Reservation reservation = new Reservation();
		reservation.setName("TestPerson");
		reservation.setSiteId(testCampsiteID);
		reservation.setFromDate(LocalDate.of(2020, 12, 20));
		reservation.setToDate(LocalDate.of(2020, 12, 25));
		reservation.setCreateDate(LocalDate.now());
		
		return reservation;
	}

	private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		assertEquals(expected.getReservationId(), actual.getReservationId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getCreateDate(), actual.getCreateDate());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getToDate(), actual.getToDate());
	}

}
