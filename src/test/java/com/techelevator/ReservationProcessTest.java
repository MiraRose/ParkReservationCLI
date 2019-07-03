package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.dao.Campsite;

import processes.ReservationProcess;



public class ReservationProcessTest {

private ReservationProcess reservationProcess;
	
	@Before
	public void beforeTests() {
		reservationProcess = new ReservationProcess();
	}
	
	@Test
	public void if_no_available_campsites_returns_false() {
		List<Campsite> listOfAvailableCampsites = new ArrayList<Campsite>();
		boolean result = reservationProcess.hasAvailableCampsites(listOfAvailableCampsites);
		Assert.assertFalse(result);
	}
	
	@Test
	public void if_date_invalid_return_false() {
		boolean actual1 = reservationProcess.isReservationDateValid(LocalDate.of(2013, 02, 02), LocalDate.of(2013, 03, 03));
		Assert.assertFalse("In past", actual1);
		boolean actual2 = reservationProcess.isReservationDateValid(LocalDate.of(2020, 02, 23), LocalDate.of(2020, 01, 01));
		Assert.assertFalse("From date not before to date", actual2);
	}
}
