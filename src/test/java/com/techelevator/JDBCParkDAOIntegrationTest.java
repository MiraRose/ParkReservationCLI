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

import com.techelevator.dao.JDBCParkDAO;
import com.techelevator.dao.Park;
import com.techelevator.dao.ParkDAO;




public class JDBCParkDAOIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private ParkDAO dao;
	private JdbcTemplate jdbcTemplate;
//	private long testEmployeeId;
//	private long testDepartmentId;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
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
//		String sql = "INSERT INTO employee (employee_id, department_id, first_name, last_name, birth_date, gender, hire_date) VALUES (DEFAULT, 1, 'TestFirst', 'TestLast', '1990-04-23', 'F', '2011-08-02') RETURNING employee_id";
		jdbcTemplate = new JdbcTemplate(dataSource);
//		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
//		result.next();
//		testEmployeeId = result.getInt(1);
//		
//		String sql2 = "INSERT INTO department (department_id, name) VALUES (DEFAULT, 'Department of What') RETURNING department_id";
//		SqlRowSet result2 = jdbcTemplate.queryForRowSet(sql2);
//		result2.next();
//		testDepartmentId = result2.getInt(1);
		
		dao = new JDBCParkDAO(dataSource);
		
	}
	
	@Test
	public void get_all_parks_returns_all_parks() {
		List<Park> parkList = dao.getAllParks();
		
		int parkListOriginalSize = parkList.size();
		
		String sql = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (DEFAULT, 'TestPark', 'TestLocation', '12-12-2012', 300, 300, 'a description')";
		jdbcTemplate.update(sql);
		
		List<Park> parkListTwo = dao.getAllParks();
		int parkListNewSize = parkListTwo.size();
		
		Assert.assertEquals(parkListOriginalSize + 1, parkListNewSize);
	}

}
