package booksys.application.domain;

import java.sql.Date;
import java.sql.Time;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReservationModel {
	
	private SimpleIntegerProperty tno;
	private SimpleStringProperty name;
	private SimpleStringProperty phone;
	private SimpleIntegerProperty covers;
	private SimpleStringProperty date;
	private SimpleStringProperty time;
	private SimpleStringProperty arrival;
	private SimpleStringProperty leave;
	private SimpleStringProperty staffId;
	private Booking b;
	
	public ReservationModel(int tno, int covers, String date, String time, 
			String leave, Booking b, String staffId) {
		this.tno = new SimpleIntegerProperty(tno);
		this.covers = new SimpleIntegerProperty(covers);
		this.date = new SimpleStringProperty(date);
		this.time = new SimpleStringProperty(time);
		this.leave = new SimpleStringProperty(leave);
		this.staffId = new SimpleStringProperty(staffId);
		this.b = b;
	}
	
	public ReservationModel(int tno, String name, String phone, int covers,
			String date, String time, String arrival, String leave, Booking b, String staffId) {
		this(tno,covers,date,time,leave,b,staffId);
		this.name = new SimpleStringProperty(name);
		this.phone = new SimpleStringProperty(phone);
		this.arrival = new SimpleStringProperty(arrival);
	}
	
	public int getTno() {
		return tno.get();
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getPhone() {
		return phone.get();
	}
	
	public int getCovers() {
		return covers.get();
	}
	
	public String getDate() {
		return date.get();
	}
	
	public String getTime() {
		return time.get();
	}
	
	public String getArrival() {
		return arrival.get();
	}
	
	public String getLeave() {
		return leave.get();
	}
	
	public String getStaffId() {
		return staffId.get();
	}
	
	public Booking getBooking() {
		return b;
	}
}
