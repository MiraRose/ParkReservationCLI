package com.techelevator.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface CampsiteDAO {
	
	public List<Campsite> getAvailableCampsiteForCampground(long campgroundId, LocalDate startDate, LocalDate toDate);
	public double getCampsiteDailyPriceByCampsiteID (long campsiteID);
	
}
