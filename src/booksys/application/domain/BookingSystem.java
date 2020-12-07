/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import java.io.IOException;
import java.sql.Date ;
import java.sql.Time ;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import booksys.presentation.controller.CanvasUI;

public class BookingSystem<PersistentBooking>
{
	// Attributes:

	Date currentDate ;
	Date today ;

	// Associations:

	Restaurant restaurant = null ;
	Vector currentBookings ;
	Booking selectedBooking ;
	Customer customer;
	CanvasUI c;

	// Singleton:

	private static BookingSystem uniqueInstance ;

	public static BookingSystem getInstance()
	{
		if (uniqueInstance == null) {
			uniqueInstance = new BookingSystem() ;
		}
		return uniqueInstance ;
	}

	public BookingSystem()
	{
		today = new Date(Calendar.getInstance().getTimeInMillis()) ;
		restaurant = new Restaurant() ;
		System.out.println(today);
	}

	// Observer: this is `Subject/ConcreteSubject'

	Vector observers = new Vector() ;

	public void addObserver(BookingObserver o)
	{
		System.out.println("addObserver");
		observers.addElement(o) ;
	}

	public void notifyObservers()
	{
		Enumeration enums = observers.elements() ;
		while (enums.hasMoreElements()) {
			BookingObserver bo = (BookingObserver) enums.nextElement() ;
			bo.update() ;
		}
	}

	public boolean observerMessage(String message, boolean confirm, String what) throws IOException
	{
		BookingObserver bo = (BookingObserver) observers.elementAt(0) ;
		return bo.message(message, confirm, what) ;
	}

	// System messages:

	public Vector getBookings(Date date) {
		return restaurant.getBookings(date);
	}

	public void display(Date date)
	{
		currentDate = date ;
		currentBookings = restaurant.getBookings(currentDate) ;
		selectedBooking = null ;
		notifyObservers() ;
	}

	public void makeReservation(int covers, Date date, Time time, int tno,
			String name, String phone, int count, String staffId) throws IOException
	{
		if (!doubleBooked(time, tno, null)) {
			Booking b
			= restaurant.makeReservation(covers, date, time, tno, name, phone, count, staffId) ;
			currentBookings.addElement(b) ;
			customer = restaurant.getCustomer(name, phone);
			customer.upCount();
			notifyObservers() ;
			if(!overflow(tno, covers)) {
			}
		}
	}

	public void makeWalkIn(int covers, Date date, Time time, int tno, String staffId) throws IOException
	{
		if (!doubleBooked(time, tno, null)) {
			Booking b = restaurant.makeWalkIn(covers, date, time, tno, staffId) ;
			currentBookings.addElement(b) ;
			notifyObservers() ;
			if(!overflow(tno, covers)) {
			}
		}
	}

	public void selectBooking(int tno, Time time)
	{
		selectedBooking = null ;
		Enumeration enums = currentBookings.elements() ;
		while (enums.hasMoreElements()) {
			Booking b = (Booking) enums.nextElement() ;
			if (b.getTableNumber() == tno) {
				if (b.getTime().before(time)
						&& b.getEndTime().after(time)) {
					selectedBooking = b ;
				}
			}
		}
		notifyObservers() ;
	}



	public void cancel() throws IOException
	{
		if (selectedBooking != null) {
			observerMessage("Are you sure?", true, "cancel");
		}
	}

	public void cancelExec() {
		currentBookings.remove(selectedBooking) ;
		restaurant.removeBooking(selectedBooking) ;
		if(selectedBooking instanceof Reservation) {
			Reservation r = (Reservation) selectedBooking;
			customer = r.getCustomer();
			customer.downCount();
		}
		selectedBooking = null ;
		notifyObservers() ;
	}

	public void recordArrival(Time time) throws IOException
	{
		if (selectedBooking != null) {
			if (selectedBooking.getArrivalTime() != null) {
				observerMessage("Arrival already recorded", false, "record") ;
			}
			else {
				selectedBooking.setArrivalTime(time) ;
				restaurant.updateBooking(selectedBooking) ;
				notifyObservers() ;
			}
		}
	}


	public void recordLeave(Time time) throws IOException
	{
		if(time.getHours() < 0 || time.getHours() > 24 || time.getMinutes() < 0 || time.getMinutes() > 60
				|| time.getSeconds() < 0 || time.getSeconds() > 60) {
			observerMessage("유효한 시간이 아닙니다.", false, "leave") ;
		}
		if (selectedBooking != null) {
			if (selectedBooking.getLeaveTime() != null) {
				observerMessage("Leave already recorded", false, "leave") ;
			}
			else {
				selectedBooking.setLeaveTime(time) ;
				restaurant.updateBooking(selectedBooking) ;
				notifyObservers() ;
			}
		}
	}


	public void transfer(Time time, int tno, String staffId) throws IOException
	{
		if (selectedBooking != null) {
			if (selectedBooking.getTableNumber() != tno) {
				if (!doubleBooked(selectedBooking.getTime(), tno, selectedBooking)) {
					selectedBooking.setTable(restaurant.getTable(tno)) ;
					selectedBooking.setStaffId(staffId);
					restaurant.updateBooking(selectedBooking) ;
				}
			}
			notifyObservers() ;
			if(!overflow(tno, selectedBooking.getCovers())) {

			}
		}
	}

	private boolean doubleBooked(Time startTime, int tno, Booking ignore) throws IOException
	{
		boolean doubleBooked = false ;

		Time endTime = (Time) startTime.clone() ;
		endTime.setHours(endTime.getHours() + 2) ;

		Enumeration enums = currentBookings.elements() ;
		while (!doubleBooked && enums.hasMoreElements()) {
			Booking b = (Booking) enums.nextElement() ;
			if(b.getLeaveTime() == null) {
				if (b != ignore && b.getTableNumber() == tno
						&& startTime.before(b.getEndTime())
						&& endTime.after(b.getTime())) {
					doubleBooked = true ;
					observerMessage("Double booking!", false, "double") ;
				}
			}
			else {
				if (b != ignore && b.getTableNumber() == tno
						&& startTime.before(b.getLeaveTime())
						&& endTime.after(b.getTime())) {
					doubleBooked = true ;
					observerMessage("Double booking!", false, "double") ;
				}
			}
		}
		return doubleBooked ;
	}

	private boolean overflow(int tno, int covers) throws IOException
	{
		boolean overflow = false ;
		Table t = restaurant.getTable(tno) ;

		if (t.getPlaces() < covers)
			overflow = !observerMessage("Ok to overfill table?", true, "overflow") ;

		return overflow ;
	}

	// Other Operations:

	public Date getCurrentDate()
	{
		return currentDate ;
	}

	public Enumeration getBookings()
	{
		return currentBookings.elements() ;
	}

	public Booking getSelectedBooking()
	{
		return selectedBooking ;
	}

	public static Vector getTableNumbers()
	{
		return Restaurant.getTableNumbers() ;
	}

	public static Vector getTableCovers() {

		return Restaurant.getTableCovers();
	}

	public Vector getCurrentBooking() {
		return currentBookings;
	}

	public Vector getSpecificBooking(String booking, String subject, String object) {
		return Restaurant.getSpecificBookings(booking, subject, object);
	}
}
