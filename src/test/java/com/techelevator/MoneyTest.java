package com.techelevator;


import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import processes.Money;

public class MoneyTest {

private Money money;
	
	@Before
	public void beforeTests() {
		money = new Money();
	}
	
	@Test
	public void inputing_daily_price_returns_total_price_for_reservation() {
		BigDecimal actual = money.getTotalPriceForReservation(35.00, LocalDate.of(2006, 12, 01), LocalDate.of(2006, 12, 10));
		
		Assert.assertEquals(new BigDecimal(315.00), actual);
	}
	
}
