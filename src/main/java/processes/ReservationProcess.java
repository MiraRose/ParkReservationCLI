package processes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cglib.core.Local;

import com.techelevator.dao.Campsite;

public class ReservationProcess {

	public boolean hasAvailableCampsites(List<Campsite> listOfAvailableCampsites) {
		
		if (listOfAvailableCampsites.size() == 0) {
			return false;
		}
		return true;
	}
	
	public boolean isReservationDateValid(LocalDate startDate, LocalDate endDate) {
		
		if (startDate.compareTo(LocalDate.now()) < 0 || endDate.compareTo(LocalDate.now()) < 0) {
			return false;
		}
		
		if (startDate.compareTo(endDate) > 0) {
			return false;
		}
		return true;
	}
	
}
