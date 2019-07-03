package com.techelevator.view;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.techelevator.dao.Campground;
import com.techelevator.dao.CampgroundDAO;
import com.techelevator.dao.Campsite;
import com.techelevator.dao.CampsiteDAO;
import com.techelevator.dao.Park;
import com.techelevator.dao.ParkDAO;
import com.techelevator.dao.Reservation;
import com.techelevator.dao.ReservationDAO;

import processes.CampsiteFormat;
import processes.Money;
import processes.ReservationProcess;

public class Menu {

	private Scanner in = new Scanner(System.in);
	private List<Park> parks;
	private Park selectedPark;
	List<Campground> listOfCampgrounds;
	private Campground selectedCampground;

	private Money money;
	List<Campsite> listOfAvailableCampsites;
	LocalDate startDate;
	LocalDate endDate;

	boolean inParkInterface = true;
	boolean inSecondMenuParkInformationScreen = false;
	boolean inThirdMenuCampgroundMenu = false;
	boolean inFourthMenuSearchForReservation = false;
	boolean inFifthMenuMakeReservation = false;

	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private CampsiteDAO campsiteDAO;
	private ReservationDAO reservationDAO;

	ReservationProcess reservationProcess = new ReservationProcess();

	int secondMenuNumberSelection;

	public Menu(ParkDAO parkDAO, CampgroundDAO campgroundDAO, CampsiteDAO campsiteDAO, ReservationDAO reservationDAO) {

		this.parkDAO = parkDAO;
		this.campgroundDAO = campgroundDAO;
		this.campsiteDAO = campsiteDAO;
		this.reservationDAO = reservationDAO;

	}

	public void startMenu() throws ParseException {
		while (true) {

			firstMenuViewParksInterface();

			while (inSecondMenuParkInformationScreen) {
				secondMenuParkInformationScreen();

				while (inThirdMenuCampgroundMenu) {
					thirdMenuCampgroundMenu();

					while (inFourthMenuSearchForReservation) {
						fourthMenuSearchForReservation();

						while (inFifthMenuMakeReservation) {
							fifthMenuMakeReservation();
						}
					}
				}
			}

		}

	}

	private void firstMenuViewParksInterface() {

		parks = parkDAO.getAllParks();
		int number = 1;

		System.out.println("Select a Park for Further Details");
		// prints out parks with numbers
		for (Park park : parks) {
			System.out.println(number + ") " + park.getName());
			number++;
		}
		System.out.println("Q) quit");

		String selection = in.nextLine();

		if (selection.equalsIgnoreCase("Q")) {
			System.exit(0);
		}

		try {
			secondMenuNumberSelection = Integer.parseInt(selection);
		} catch (Exception e) {
			System.out.println("Invalid selection! Please try again.");
		}

		if (secondMenuNumberSelection < parks.size()) {
			inSecondMenuParkInformationScreen = true;

		} else {
			System.out.println("Invalid selection! Please try again.");
		}

	}

	private void secondMenuParkInformationScreen() {

		selectedPark = parks.get(secondMenuNumberSelection - 1);
		System.out.println(selectedPark.getName());
		System.out.println("Location: " + selectedPark.getLocation());
		System.out.println("Established: " + selectedPark.getDateEstablished());
		System.out.println("Area: " + selectedPark.getArea() + " sq km");
		System.out.println("Annual Visitors: " + selectedPark.getNumOfVisitors());
		System.out.println(wrappedDescription(selectedPark));
		System.out.println();

		parkSelectACommand();

	}

	private void parkSelectACommand() {

		System.out.println("Select a Command");
		System.out.println("1) View Campgrounds");
		System.out.println("2) Search for Reservation");
		System.out.println("3) Return to Previous Screen");

		int selection = in.nextInt();
		in.nextLine();

		if (selection == 1) {
			inThirdMenuCampgroundMenu = true;
		} else if (selection == 2) {
			System.out.println("Feature currently unavailable. Please choose another option.");

		} else if (selection == 3) {
			inSecondMenuParkInformationScreen = false;
		} else {
			System.out.println("Please choose a valid option");

		}

	}

	private void thirdMenuCampgroundMenu() throws ParseException {

		listOfCampgrounds = campgroundDAO.searchCampgroundsByPark(selectedPark.getPark_id());
		int number = 1;
		System.out.printf("%-5s %-25s %-10s %-10s %-5s", " ", "Name", "Open", "Close", "Daily Fee");
		System.out.println();
		for (Campground campground : listOfCampgrounds) {
			System.out.println();

			System.out.printf("%-5s %-25s %-10s %-10s $%.2f", number + ")", campground.getName(),
					changeNumeralMonthToString(campground.getOpenFromMonth()),
					changeNumeralMonthToString(campground.getOpenToMonth()),
					Double.parseDouble(campground.getDailyFee()));

			number++;
		}
		System.out.println();
		campgroundSelectACommand();

	}

	private String changeNumeralMonthToString(String month) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
		SimpleDateFormat simpleDateParse = new SimpleDateFormat("MM");

		return simpleDateFormat.format(simpleDateParse.parse(month));
	}

	private void campgroundSelectACommand() {
		System.out.println();
		System.out.println("Select A Command");
		System.out.println("1) Search for Available Reservation");
		System.out.println("2) Return to Previous Screen");
		int selection = in.nextInt();
		in.nextLine();
		if (selection == 1) {
			inFourthMenuSearchForReservation = true;
		} else if (selection == 2) {
			inThirdMenuCampgroundMenu = false;
		} else {
			System.out.println("Invalid selection! Please try again.");
		}
	}

	private void fourthMenuSearchForReservation() {

		System.out.println();
		System.out.println("Which campground (enter 0 to cancel)? ");
		String campgroundSelection = in.nextLine();

		int thirdMenuCampgroundSelection = 0;

		try {
			thirdMenuCampgroundSelection = Integer.parseInt(campgroundSelection);
		} catch (Exception e) {
			System.out.println("Invalid selection! Please try again.");
		}

		if (thirdMenuCampgroundSelection == 0) {
			inFourthMenuSearchForReservation = false;
		} else if (thirdMenuCampgroundSelection > listOfCampgrounds.size()) {
			System.out.println("Invalid selection! Please try again.");
		} else {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			
			System.out.println("What is the arrival date? (ie: 12/12/2012) ");
			String arrivalSelection = in.nextLine();
			
			try {
			startDate = LocalDate.parse(arrivalSelection, formatter);
			
			System.out.println("What is the departure date? (ie: 12/20/2012) ");
			String departureSelection = in.nextLine();
			endDate = LocalDate.parse(departureSelection, formatter);
			
			if (reservationProcess.isReservationDateValid(startDate, endDate)) {
				selectedCampground = listOfCampgrounds.get(thirdMenuCampgroundSelection - 1);
				listOfAvailableCampsites = campsiteDAO
						.getAvailableCampsiteForCampground(selectedCampground.getCampground_id(), startDate, endDate);

				if (reservationProcess.hasAvailableCampsites(listOfAvailableCampsites) == false) {
					System.out.println("No Available Campsites! Would you like to enter a different date? Y/N");
					String tryAgainSelection = in.nextLine();
					
					if (tryAgainSelection.equalsIgnoreCase("Y")) {
						
					}
					else if (tryAgainSelection.equalsIgnoreCase("N")) {
						inFourthMenuSearchForReservation = false;
					}
					else {
						System.out.println("Invalid selection! Please try again.");
					}
					
				} else {
					System.out.printf("%-10s %-10s %-10s %-20s %-10s %-10s", "Site no.", "Max Occup.", "Accessible?",
							"Max RV Length", "Utility", "Cost");
					System.out.println();
					money = new Money();
					CampsiteFormat campsiteFormat = new CampsiteFormat();
					for (Campsite campsite : listOfAvailableCampsites) {
						BigDecimal totalPrice = money.getTotalPriceForReservation(
								campsiteDAO.getCampsiteDailyPriceByCampsiteID(campsite.getSite_id()), startDate,
								endDate);
						System.out.printf("%-10s %-10s %-10s %-20s %-10s $%-10s", campsite.getSite_number(),
								campsite.getMax_occupancy(), campsiteFormat.formatAccessibility(campsite.isAccessible()), 
								campsiteFormat.formatMaxRVLength(campsite.getMax_rv_length()),
								campsiteFormat.formatUtilities(campsite.isUtilities()), totalPrice);
						System.out.println();
					}
					inFifthMenuMakeReservation = true;

				}
			} else {
				System.out.println("Invalid date! Please try again.");
			}
		}
		catch (DateTimeParseException e) {
			System.out.println("Invalid entry! Please try again.");
		}
		}
	}
	
	

	private void fifthMenuMakeReservation() {

		System.out.println("Which site should be reserved (enter 0 to cancel)?");
		String siteSelection = in.nextLine();

		int fifthMenuSiteSelection = 0;

		try {
			fifthMenuSiteSelection = Integer.parseInt(siteSelection);
		} catch (Exception e) {
			System.out.println("Invalid selection! Please try again.");
		}

		if (fifthMenuSiteSelection == 0) {
			inFourthMenuSearchForReservation = false;
			inFifthMenuMakeReservation = false;
		} else if (fifthMenuSiteSelection > listOfAvailableCampsites.size()) {
			System.out.println("Invalid selection! Please try again.");
		} else {
			System.out.println("What name should the reservation be made under?");
			String nameSelection = in.nextLine();
			Campsite selectedCampsite = null;
			for (Campsite campsite : listOfAvailableCampsites) {
				if (campsite.getSite_number() == fifthMenuSiteSelection) {
					selectedCampsite = campsite;
				}
			}

			Reservation newReservation = new Reservation();
			newReservation.setName(nameSelection);
			newReservation.setSiteId(selectedCampsite.getSite_id());
			newReservation.setFromDate(startDate);
			newReservation.setToDate(endDate);

			Reservation createdReservation = reservationDAO.createReservation(newReservation);
			System.out.println("The reservation has been made and the confirmation id is "
					+ createdReservation.getReservationId());
			System.exit(0);
		}
	}

	private StringBuilder wrappedDescription(Park selectedPark) {

		StringBuilder stringBuilder = new StringBuilder(selectedPark.getDescription());

		for (int i = 75; i < stringBuilder.length(); i = i + 75) {
			stringBuilder.insert(i, "\n");
		}
		return stringBuilder;
	}

}
