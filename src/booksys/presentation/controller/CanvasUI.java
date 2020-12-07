package booksys.presentation.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

import booksys.application.domain.Booking;
import booksys.application.domain.BookingObserver;
import booksys.application.domain.BookingSystem;
import booksys.application.domain.Reservation;
import booksys.application.domain.Staff;
import booksys.application.domain.WalkIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CanvasUI implements BookingObserver, Initializable{

	@FXML
	private Canvas canvas;
	private BookingSystem bs;
	private Staff staff;
	private Window w;
	
	final static int LEFT_MARGIN   = 50 ;
	final static int TOP_MARGIN    = 50 ;
	final static int BOTTOM_MARGIN = 50 ;
	final static int ROW_HEIGHT    = 30 ;
	final static int COL_WIDTH     = 60 ;

	final static int PPM = 2 ;	    // Pixels per minute
	final static int PPH = 60 * PPM ; // Pixels per hours
	final static int TZERO = 18 ;     // Earliest time shown
	final static int SLOTS = 12 ;     // Number of booking slots shown

	public CanvasUI(Staff staff, Canvas canvas) {
		this.staff = staff;
		this.canvas = canvas;
		w = Window.getInstance();
	}
	
	private int timeToX(Time time) {
		return LEFT_MARGIN
				+ PPH * (time.getHours() - TZERO)
				+ PPM * time.getMinutes() ;
	}

	private Time xToTime(int x) {
		x -= LEFT_MARGIN ;
		int h = (x / PPH) + TZERO ;
		int m = (x % PPH) / PPM ;
		return new Time(h, m, 0) ;
	}

	private int tableToY(int table) {
		return TOP_MARGIN + (ROW_HEIGHT * (table - 1)) ;
	}

	private int yToTable(int y) {
		return ((y - TOP_MARGIN) / ROW_HEIGHT) + 1 ;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bs = BookingSystem.getInstance() ;
		bs.addObserver(this) ;
		Calendar now = Calendar.getInstance() ;
		bs.display(new Date(now.getTimeInMillis())) ;
	}

	@FXML
	void quit(ActionEvent event) {
		Stage stage = (Stage)canvas.getScene().getWindow();
		stage.close();
	}

	@FXML
	void logout(ActionEvent event) {
		Stage stage = (Stage)canvas.getScene().getWindow();
		w.loginUI(stage);
	}

	@FXML
	void cancel(ActionEvent event) throws IOException {
		bs.cancel() ;
	}

	@FXML
	void chkArrival(ActionEvent event) throws IOException {
		Calendar now = Calendar.getInstance() ;
		bs.recordArrival(new Time(now.getTimeInMillis())) ;
	}

	@FXML
	void chkLeave(ActionEvent event) throws IOException {
		insertTime();
	}

	@FXML
	void display(ActionEvent event) throws IOException {
		displayDate();
	}

	@FXML
	void newReservation(ActionEvent event) throws IOException {
		addReservation();
	}

	@FXML
	void newWalkIn(ActionEvent event) throws IOException {
		addwalkIn();
	}
	
	@FXML
	void showReservation(ActionEvent event) throws IOException {
		ShowBookingDialog d = new ShowBookingDialog();
		w.showBooking(d);
	}

	@FXML
	void mouseClicked(MouseEvent event) {
		if(event.getClickCount() > 1) {
			currentX = (int)event.getX() ;
			currentY = (int)event.getY() ;
			bs.selectBooking(yToTable(currentY), xToTime(currentX));
			Booking b = bs.getSelectedBooking();
			if(b instanceof Reservation) {
				Reservation r = (Reservation) b;
			}
			else {
				WalkIn w = (WalkIn) b;
			}
		}
	}

	@FXML
	void mouseDragged(MouseEvent event) {
		currentX = (int)event.getX() ;
		currentY = (int)event.getY() ;
		update() ;
	}

	@FXML
	void mousePressed(MouseEvent event) {
		currentX = firstX = (int) event.getX() ;
		currentY = firstY = (int) event.getY() ;
		if (event.isPrimaryButtonDown()) {
			mouseDown = true ;
			bs.selectBooking(yToTable(firstY), xToTime(firstX)) ;
		}
	}

	@FXML
	void mouseReleased(MouseEvent event) throws IOException {
		mouseDown = false ;
		bs.transfer(xToTime(currentX), yToTable(currentY), staff.getId()) ;
	}


	private Vector tableNumbers ;
	private Vector tableCovers ;
	private int firstX, firstY, currentX, currentY ;
	private boolean mouseDown ;
	private Font f = Font.font("System", FontWeight.THIN, FontPosture.REGULAR, 13);

	@Override
	public void update() {
		repaint();
	}


	public void repaint() {
		tableNumbers = BookingSystem.getTableNumbers();
		tableCovers = BookingSystem.getTableCovers();
		GraphicsContext gc;

		gc = canvas.getGraphicsContext2D();
		gc.setFont(f);
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);

		gc.fillText(staff.getName(), 5, 20);
		gc.strokeLine(LEFT_MARGIN, 0, LEFT_MARGIN, canvas.getHeight()) ;
		gc.strokeLine(0, TOP_MARGIN, canvas.getWidth(), TOP_MARGIN) ;

		for(int i = 0; i < tableNumbers.size(); i++) {
			int y1 = TOP_MARGIN + (i+1)*ROW_HEIGHT ;
			gc.fillText(Integer.toString(i+1), 2, y1-10);
			gc.fillText(Integer.toString((int) tableCovers.get(i)), 20, y1-10);
			gc.fillText("Έν", 30, y1-10);
			gc.strokeLine(LEFT_MARGIN, y1, canvas.getWidth(), y1);
		}

		for (int i = 0; i < SLOTS; i++) {
			String tmp = (TZERO+(i/2)) + (i%2 == 0? ":00" : ":30") ;
			int x1 = LEFT_MARGIN + i*COL_WIDTH ;
			gc.fillText(tmp, x1, 40) ;
			gc.strokeLine(x1, TOP_MARGIN, x1, canvas.getHeight()) ;
		}

		gc.fillText(((java.util.Date) bs.getCurrentDate()).toString(),
				LEFT_MARGIN, 20);

		Enumeration enums = bs.getBookings() ;
		while (enums.hasMoreElements()) {
			Booking b = (Booking) enums.nextElement() ;
			int x1 = timeToX(b.getTime()) ;
			int y1 = tableToY(b.getTable().getNumber()) ;
			if(b.getLeaveTime() != null) {
				int xl = timeToX(b.getLeaveTime());
				gc.setFill(Color.DARKSLATEGREY);
				gc.fillRect(x1, y1, xl-x1, ROW_HEIGHT);
				if (b == bs.getSelectedBooking()) {
					gc.setStroke(Color.RED) ;
					gc.strokeRect(x1, y1, xl-x1, ROW_HEIGHT) ;
				}
				gc.setFill(Color.WHITE);
				gc.fillText(b.getDetails(), x1, y1 + ROW_HEIGHT/2,xl-x1) ;
				if(b instanceof Reservation) {
					gc.fillText(((Reservation) b).getCount(), x1, y1+30,13);
				}
			}
			else {
				gc.setFill(Color.GRAY) ;
				gc.fillRect(x1, y1, 4*COL_WIDTH, ROW_HEIGHT) ;
				if (b == bs.getSelectedBooking()) {
					gc.setStroke(Color.RED) ;
					gc.strokeRect(x1, y1, 4*COL_WIDTH, ROW_HEIGHT) ;
				}
				gc.setFill(Color.WHITE);
				gc.fillText(b.getDetails(), x1, y1 + ROW_HEIGHT/2) ;
				if(b instanceof Reservation) {
					gc.fillText(((Reservation) b).getCount(), x1, y1+30,13);
				}
			}
		}

		Booking b = bs.getSelectedBooking() ;
		if (mouseDown && b != null) {
			int x1 = timeToX(b.getTime()) + currentX - firstX ;
			int y1 = tableToY(b.getTable().getNumber()) + currentY - firstY ;
			
			if(b.getLeaveTime() != null) {
				int leaveTime = timeToX(b.getLeaveTime());
				gc.setStroke(Color.RED) ;
				gc.strokeRect(x1, y1, leaveTime - x1, ROW_HEIGHT) ;
			}
			else {
				gc.setStroke(Color.RED) ;
				gc.strokeRect(x1, y1, 4*COL_WIDTH, ROW_HEIGHT) ;
			}
		}	
	}


	void displayDate() throws IOException {
		CanvasUI c = this;
		DateDialog d = new DateDialog(c);
		Stage stage = (Stage) canvas.getScene().getWindow();
		w.dateDialog(d,stage);
	}

	public void displayDateCheck(DateDialog d) {
		if (d.isConfirmed()) {
			Date date = d.getDate() ;
			bs.display(date) ;
		}
	}
	
	void insertTime() throws IOException {
		TimeDialog d = new TimeDialog(this);
		Stage stage = (Stage) canvas.getScene().getWindow();
		w.timeDialog(d, stage);
	}
	
	public void insertTimeCheck(Time time) throws IOException {
		bs.recordLeave(time);
	}

	public void addwalkIn() throws IOException {
		CanvasUI c = this;
		WalkInDialog d = new WalkInDialog(c);
		w.walkinDialog(d);
	}

	public void addWalkInCheck(WalkInDialog d) throws IOException {
		if (d.isConfirmed()) {
			bs.makeWalkIn(d.getCovers(),
					bs.getCurrentDate(),
					d.getTime(),
					d.getTableNumber(),
					staff.getId()) ;
		}
	}

	public void addReservation() throws IOException {
		CanvasUI c = this;
		ReservationDialog d = new ReservationDialog(c);
		w.reservationDialog(d);
	}

	public void addReservationCheck(ReservationDialog d) throws IOException {
		if (d.isConfirmed()) {
			bs.makeReservation(d.getCovers(),
					bs.getCurrentDate(),
					d.getTime(),
					d.getTableNumber(),
					d.getCustomerName(),
					d.getPhoneNumber(),
					d.getCount(),
					staff.getId());
		}
	}


	@Override
	public boolean message(String s, boolean confirm, String what) throws IOException {
		if(confirm) {
			ConfirmDialogChoice d = new ConfirmDialogChoice(s,what) ;
			w.confirmDialogChoice(d, canvas);
			return d.isConfirmed();
		}
		else {
			ConfirmDialogOk d = new ConfirmDialogOk(s);
			Stage stage = (Stage) canvas.getScene().getWindow();
			w.confirmDialogOk(d,stage);
			return d.isConfirmed();
		}
	}


}
