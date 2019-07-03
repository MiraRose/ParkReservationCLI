package com.techelevator.dao;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Reservation createReservation(Reservation reservation) {
		
		String sql = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date) VALUES (DEFAULT, ?, ?, ?, ?, ?) RETURNING reservation_id";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, reservation.getSiteId(), reservation.getName(), reservation.getFromDate(), reservation.getToDate(), LocalDate.now());
		result.next();
		long reservationId = result.getLong("reservation_id");
		reservation.setReservationId(reservationId);

		return reservation;
	}
	
}
