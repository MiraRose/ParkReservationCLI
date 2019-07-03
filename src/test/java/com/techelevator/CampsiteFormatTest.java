package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import processes.CampsiteFormat;



public class CampsiteFormatTest {

private CampsiteFormat campsiteFormat;
	
	@Before
	public void beforeTests() {
		campsiteFormat = new CampsiteFormat();
	}
	
	@Test
	public void format_accessibility_returns_correct_string() {
		String actual1 = campsiteFormat.formatAccessibility(true);
		String actual2 = campsiteFormat.formatAccessibility(false);
		
		Assert.assertEquals("Yes, it is accessible - true", "Yes", actual1);
		Assert.assertEquals("No, it isn't accessible - false", "No", actual2);
		
	}
	
	@Test
	public void format_max_rv_length_returns_correct_string() {
		String actual1 = campsiteFormat.formatMaxRVLength(0);
		String actual2 = campsiteFormat.formatMaxRVLength(20);
		
		Assert.assertEquals("0 returns n/a", "N/A", actual1);
		Assert.assertEquals("more than 0 returns number as string", "20", actual2);
		
	}
	
	@Test
	public void format_ultilities_returns_correct_string() {
		String actual1 = campsiteFormat.formatUtilities(true);
		String actual2 = campsiteFormat.formatUtilities(false);
		
		Assert.assertEquals("Yes, has utilities - true", "Yes", actual1);
		Assert.assertEquals("N/A doesn't have ultilities - false", "N/A", actual2);
		
	}
	
}
