package com.techelevator.dao;

import java.util.List;

public interface CampgroundDAO {
	
	public List<Campground> searchCampgroundsByPark(long park_id);
	
}
