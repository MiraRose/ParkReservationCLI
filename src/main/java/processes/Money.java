package processes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Money {

	public BigDecimal getTotalPriceForReservation(double daily_price, LocalDate startDate, LocalDate endDate) {
		BigDecimal bigDailyPrice = new BigDecimal(daily_price);
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		return bigDailyPrice.multiply(new BigDecimal(daysBetween)).setScale(2, RoundingMode.HALF_UP);
	}
	
}
