package booksys.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import booksys.application.domain.BookingSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;

public class ConfirmDialogOk implements Initializable{

	@FXML private Label label;
	private String text;
	protected boolean confirmed ;


	public ConfirmDialogOk(String text) {
		this.text = text;
	}

	@FXML
	void windowAction(WindowEvent event) {
		confirmed = false;
		label.getScene().getWindow().hide();
	}

	@FXML
	void ok(ActionEvent event) {
		confirmed = true ;
		label.getScene().getWindow().hide();
	}

	boolean isConfirmed()
	{
		return confirmed ;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label.setText(text);
	}

}
