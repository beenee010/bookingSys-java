package booksys.presentation.controller;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TimeDialog {

	@FXML private TextField tf;
	private CanvasUI c;

	public TimeDialog(CanvasUI c) {
		this.c = c;
	}

	@FXML
	void cancel(ActionEvent event) {
		Stage stage = (Stage) tf.getScene().getWindow();
		stage.close();
	}

	@FXML
	void ok(ActionEvent event) throws IOException {
		Time time = Time.valueOf(tf.getText());
		c.insertTimeCheck(time);
		Stage stage = (Stage) tf.getScene().getWindow();
		stage.close();
	}

}
