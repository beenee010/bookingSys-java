package booksys.presentation.controller;

import java.io.IOException;
import java.util.Vector;

import booksys.application.domain.BookingSystem;
import booksys.application.domain.Staff;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Window {

	final static int LEFT_MARGIN   = 50 ;
	final static int TOP_MARGIN    = 50 ;
	final static int BOTTOM_MARGIN = 50 ;
	final static int ROW_HEIGHT    = 30 ;
	final static int COL_WIDTH     = 60 ;
	final static int SLOTS = 12 ;     // Number of booking slots shown

	private static Window uniqueInstance;

	public static Window getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Window();
		}
		return uniqueInstance;
	}

	public Window() {}

	public void loginUI(Stage preStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//LoginUI.fxml"));
			LoginUI l = new LoginUI();
			loader.setController(l);

			Parent root = loader.load();

			preStage.setTitle("Login");
			preStage.setScene(new Scene(root));
			preStage.setResizable(false);
			preStage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public void canvas(Stage preStage, Staff s) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//StaffUI.fxml"));

			Vector tableNumbers = BookingSystem.getTableNumbers() ;
			Canvas canvas = new Canvas(LEFT_MARGIN + (SLOTS * COL_WIDTH),TOP_MARGIN + tableNumbers.size()*ROW_HEIGHT + BOTTOM_MARGIN);

			CanvasUI c = new CanvasUI(s, canvas);
			loader.setController(c);

			Parent root = loader.load();
			preStage.setTitle("Restaurant Booking System");
			preStage.setScene(new Scene(root,LEFT_MARGIN + (SLOTS * COL_WIDTH),TOP_MARGIN + tableNumbers.size()*ROW_HEIGHT + BOTTOM_MARGIN-15));
			preStage.setResizable(false);
			preStage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void adminLoginDialog(Stage preStage, Button preBtn, String what) throws IOException {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(preStage);
			dialog.setTitle("Admin Login");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//AdminLoginUI.fxml"));
			AdminLoginDialog d = new AdminLoginDialog(preStage, what);

			loader.setController(d);
			Parent parent = loader.load();

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void register(Stage preStage) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//RegisterUI.fxml"));
			RegisterDialog d = new RegisterDialog();

			loader.setController(d);
			Parent root = loader.load();

			preStage.setTitle("Register");
			preStage.setScene(new Scene(root));
			preStage.setResizable(false);
			preStage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void tableSetting(Stage preStage, Button preBtn) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//TableSettingUI.fxml"));
			TableSettingUI tUI = new TableSettingUI(preStage, preBtn);

			loader.setController(tUI);
			Parent root = loader.load();

			preStage.setTitle("Table Setting");
			preStage.setScene(new Scene(root));
			preStage.setResizable(false);
			preStage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void setTableDialog(Stage preStage, Button preBtn, String oid, String n, String p) throws IOException {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(preStage);
			dialog.setTitle("Set Table");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//SetTableUI.fxml"));

			SetTableDialog d = new SetTableDialog(preStage, preBtn,oid, n, p);

			loader.setController(d);

			Parent parent = loader.load();

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void addTableDialog(Stage preStage) throws IOException {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(preStage);
			dialog.setTitle("Add Table");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//AddTableUI.fxml"));

			AddTableDialog d = new AddTableDialog();
			loader.setController(d);

			Parent parent = loader.load();

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void deleteTableDialog(ConfirmDialogChoice d, Stage preStage) throws IOException {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(preStage);
			dialog.setTitle("Warning !!");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//ChoiceUI.fxml"));

			loader.setController(d);

			Parent parent = loader.load();

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void dateDialog(DateDialog d, Stage preStage) throws IOException {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(preStage);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//DateDialogUI.fxml"));

			loader.setController(d);
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Enter a Date");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void timeDialog(TimeDialog d, Stage preStage) throws IOException {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(preStage);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//TimeDialogUI.fxml"));

			loader.setController(d);
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Enter a Time");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void reservationDialog(ReservationDialog r) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//ReservationDialogUI.fxml"));

			loader.setController(r);
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Reservation Dialog");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void walkinDialog(WalkInDialog w) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//WalkInDialogUI.fxml"));

			loader.setController(w);
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("WalkIn Dialog");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void showBooking(ShowBookingDialog d) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//ShowBookingUI.fxml"));

			loader.setController(d);
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Show Booking");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void confirmDialogChoice(ConfirmDialogChoice d, Canvas c) throws IOException {
		try {
			Stage preStage = (Stage) c.getScene().getWindow();
			Stage dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(preStage);
			dialog.setTitle("Warning !!");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//AlertChoiceUI.fxml"));

			loader.setController(d);
			Parent parent = loader.load();

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void confirmDialogOk(ConfirmDialogOk d, Stage preStage) throws IOException {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(preStage);
			dialog.setTitle("Warning !!");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..//view//AlertConfirmUI.fxml"));

			loader.setController(d);
			Parent parent = loader.load();

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
