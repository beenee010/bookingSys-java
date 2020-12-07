package booksys.presentation.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import booksys.application.domain.Booking;
import booksys.application.domain.BookingSystem;
import booksys.application.domain.Customer;
import booksys.application.domain.Reservation;
import booksys.application.domain.ReservationModel;
import booksys.application.domain.WalkIn;
import booksys.application.persistency.CustomerMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowBookingDialog implements Initializable {

	@FXML private DatePicker Calender;
	@FXML private Button undoBtn;
	@FXML private ComboBox<String> bookingBox;
	@FXML private ComboBox<String> subjectBox;
	@FXML private TextField searchTf;
	@FXML private TextField nameTf;
	@FXML private TextField phoneTf;
	@FXML private TableView<ReservationModel> tableView = new TableView<ReservationModel>();
	@FXML private TableColumn<ReservationModel, Integer> tableNo = new TableColumn<ReservationModel, Integer>();
	@FXML private TableColumn<ReservationModel, String> name = new TableColumn<ReservationModel, String>();
	@FXML private TableColumn<ReservationModel, String> phone = new TableColumn<ReservationModel, String>();
	@FXML private TableColumn<ReservationModel, Integer> covers = new TableColumn<ReservationModel, Integer>();
	@FXML private TableColumn<ReservationModel, String> date = new TableColumn<ReservationModel, String>();
	@FXML private TableColumn<ReservationModel, String> time = new TableColumn<ReservationModel, String>();
	@FXML private TableColumn<ReservationModel, String> arrival = new TableColumn<ReservationModel, String>();
	@FXML private TableColumn<ReservationModel, String> leave = new TableColumn<ReservationModel, String>();
	@FXML private TableColumn<ReservationModel, String> staffId = new TableColumn<ReservationModel, String>();
	private ObservableList<String> boxList;
	private Vector<Booking> currentBookings;
	private Booking bookingObj;
	private ObservableList<ReservationModel> data;
	private Reservation reservation;
	private BookingSystem bs;
	private Window w;
	private String booking;
	private Format dateForm;

	public ShowBookingDialog() {
		data = FXCollections.observableArrayList();
		w = Window.getInstance();
		bs = BookingSystem.getInstance();
		dateForm = new SimpleDateFormat("yyyy-MM-dd");
		booking = null;
	}

	@FXML
	void pickAction(ActionEvent event) throws IOException {

		String choice = bookingBox.getValue();
		if(choice == null) {
			Stage stage = (Stage) nameTf.getScene().getWindow();
			w.confirmDialogOk(new ConfirmDialogOk("Booking 유형을 선택하세요."), stage);
			return;
		}
		if(booking == null) {
			return;
		}

		data.clear();
		LocalDate local = Calender.getValue();
		Date date = null;
		if(local != null) {
			date = Date.valueOf(local);
			System.out.println(date);
			currentBookings = bs.getBookings(date);
		}
		setting();
	}

	@FXML
	void choiceBooking(ActionEvent event) {
		String choice = bookingBox.getValue();
		Calender.setValue(null);
		data.clear();
		tableView.refresh();
		setClear();

		if(choice.equals("Reservation")) {
			booking = "Reservation";
			name.setVisible(true);
			phone.setVisible(true);
			arrival.setVisible(true);
			boxList = FXCollections.observableArrayList("StaffID","Customer","TableNo");
			subjectBox.setItems(boxList);
			tableView.setPrefSize(1050, 314);
			tableView.setLayoutX(30);
			tableView.setLayoutY(90);
		}
		else if(choice.equals("WalkIn")) {
			booking = "WalkIn";
			nameTf.setVisible(false);
			phoneTf.setVisible(false);
			searchTf.setVisible(true);
			searchTf.setDisable(false);
			name.setVisible(false);
			phone.setVisible(false);
			arrival.setVisible(false);
			boxList = FXCollections.observableArrayList("StaffID","TableNo");
			subjectBox.setItems(boxList);
			tableView.setPrefSize(690, 314);
			tableView.setLayoutX(200);
			tableView.setLayoutY(90);
		}


	}

	@FXML
	void clickAction(MouseEvent event) {
		if(event.getClickCount() > 1) {
			Booking b = tableView.getSelectionModel().getSelectedItem().getBooking();
			// 창을 띄워서 수정할 것인지 : 바로 수정하고 저장을 누를것인지
		}
	}


	@FXML
	void undoAction(ActionEvent event) {
		Stage stage = (Stage) nameTf.getScene().getWindow();
		stage.close();
	}

	@FXML
	void selectAction(ActionEvent event) {
		String subject = subjectBox.getValue();

		setClear();

		if(subject == null) return;

		if(subject.equals("Customer")) {
			searchTf.setVisible(false);
			searchTf.setDisable(true);
			nameTf.setVisible(true);
			phoneTf.setVisible(true);
		}
		else {
			nameTf.setVisible(false);
			phoneTf.setVisible(false);
			searchTf.setVisible(true);
			searchTf.setDisable(false);
		}
	}

	@FXML
	void searchAction(ActionEvent event) throws IOException {
		data.clear();
		String object = searchTf.getText();
		String subject = subjectBox.getValue();
		Stage stage = (Stage) nameTf.getScene().getWindow();

		if(subject == null) {
			w.confirmDialogOk(new ConfirmDialogOk("검색 조건을 선택하세요 !"), stage);
			return;
		}
		if(object.isEmpty() && !subject.equals("Customer")) {
			w.confirmDialogOk(new ConfirmDialogOk("검색어를 입력하세요 !"), stage);
			return;
		}

		else if(subject.equals("StaffID"))
			currentBookings = bs.getSpecificBooking(booking, subject, object);
		else if(subject.equals("Customer")) {
			String name = nameTf.getText();
			String phone = phoneTf.getText();

			if(name.isEmpty() || phone.isEmpty()) {
				w.confirmDialogOk(new ConfirmDialogOk("정보를 모두 입력해주세요."), stage);
				return;
			}

			Customer c = CustomerMapper.getInstance().getCustomer(name, phone);

			if(c == null)
				w.confirmDialogOk(new ConfirmDialogOk("존재하지 않는 고객입니다."), stage);
			else
				currentBookings = bs.getSpecificBooking(booking, "customer_ID", Integer.toString(c.getOid()));
		}
		else if(subject.equals("TableNo"))
			currentBookings = bs.getSpecificBooking(booking, "table_ID", object);
		setting();
	}

	void setting() {
		if(booking.equals("Reservation")) setReservation();
		else if(booking.equals("WalkIn")) setWalkIn();

		tableView.setItems(data);
	}

	void setReservation() {
		ArrayList<ReservationModel> reser = new ArrayList<ReservationModel>();
		for(int i = 0; i < currentBookings.size(); i++) {
			bookingObj = currentBookings.get(i);
			if(bookingObj instanceof Reservation) {
				int tNo = bookingObj.getTableNumber();
				int covers = bookingObj.getCovers();
				String date = dateForm.format((bookingObj.getDate()));
				String time = bookingObj.getTime().toString();
				String leave = (bookingObj.getLeaveTime() == null ? "null" : bookingObj.getLeaveTime().toString());
				String staffId = bookingObj.getStaffId();
				Reservation r = (Reservation) bookingObj;
				String name = r.getCustomer().getName();
				String phone = r.getCustomer().getPhoneNumber();
				String arr = (r.getArrivalTime() == null ? "null" : r.getArrivalTime().toString());
				data.add(new ReservationModel(tNo,name,phone,covers,date,time,arr,leave,bookingObj,staffId));
			}
		}
	}

	void setWalkIn() {
		ArrayList<ReservationModel> reser = new ArrayList<ReservationModel>();
		for(int i = 0; i < currentBookings.size(); i++) {
			bookingObj = currentBookings.get(i);
			if(bookingObj instanceof WalkIn) {
				int tNo = bookingObj.getTableNumber();
				int covers = bookingObj.getCovers();
				String date = dateForm.format((bookingObj.getDate()));
				String time = bookingObj.getTime().toString();
				String leave = (bookingObj.getLeaveTime() == null ? "null" : bookingObj.getLeaveTime().toString());
				String staffId = bookingObj.getStaffId();
				data.add(new ReservationModel(tNo,covers,date,time,leave,bookingObj,staffId));
			}
		}
	}

	void setClear() {
		searchTf.clear();
		nameTf.clear();
		phoneTf.clear();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("Reservation","WalkIn");
		bookingBox.setItems(list);
		nameTf.setVisible(false);
		phoneTf.setVisible(false);

		name.setCellValueFactory(new PropertyValueFactory<ReservationModel, String>("Name"));
		tableNo.setCellValueFactory(new PropertyValueFactory<ReservationModel, Integer>("Tno"));
		phone.setCellValueFactory(new PropertyValueFactory<ReservationModel, String>("Phone"));
		covers.setCellValueFactory(new PropertyValueFactory<ReservationModel, Integer>("Covers"));
		date.setCellValueFactory(new PropertyValueFactory<ReservationModel, String>("Date"));
		time.setCellValueFactory(new PropertyValueFactory<ReservationModel, String>("Time"));
		arrival.setCellValueFactory(new PropertyValueFactory<ReservationModel, String>("Arrival"));
		leave.setCellValueFactory(new PropertyValueFactory<ReservationModel, String>("Leave"));
		staffId.setCellValueFactory(new PropertyValueFactory<ReservationModel, String>("StaffId"));
	}
}
