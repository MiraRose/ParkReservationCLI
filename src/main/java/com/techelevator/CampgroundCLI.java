package com.techelevator;

import java.text.ParseException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import com.techelevator.dao.CampgroundDAO;
import com.techelevator.dao.CampsiteDAO;
import com.techelevator.dao.JDBCCampgroundDAO;
import com.techelevator.dao.JDBCCampsiteDAO;
import com.techelevator.dao.JDBCParkDAO;
import com.techelevator.dao.JDBCReservationDAO;
import com.techelevator.dao.ParkDAO;
import com.techelevator.dao.ReservationDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private CampsiteDAO campsiteDAO;
	private ReservationDAO reservationDAO;

	public static void main(String[] args) throws ParseException {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();

	}

	public CampgroundCLI(DataSource datasource) {
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		campsiteDAO = new JDBCCampsiteDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
	}

	public void run() throws ParseException {
		boolean isOn = true;
		while (isOn) {
			Menu menu = new Menu(parkDAO, campgroundDAO, campsiteDAO, reservationDAO);
			menu.startMenu();

			isOn = !isOn;
		}
	}
}
