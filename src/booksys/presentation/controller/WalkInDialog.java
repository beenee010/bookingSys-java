/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation.controller ;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

import booksys.application.domain.BookingSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

class WalkInDialog implements Initializable
{
	@FXML private TextField timeTf;
	@FXML private ChoiceBox<Integer> tableNum;
	@FXML private TextField coversTf;

	protected boolean confirmed ;
	private CanvasUI c;

	public WalkInDialog(CanvasUI c) {
		this.c = c;
	}

	@FXML
	void cancel(ActionEvent event) {
		confirmed = false ;
		timeTf.getScene().getWindow().hide();
	}

	@FXML
	void ok(ActionEvent event) throws IOException {
		confirmed = true ;
		c.addWalkInCheck(this);
		Stage stage = (Stage) timeTf.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Enumeration enums = BookingSystem.getTableNumbers().elements() ;
		ArrayList<Integer> list = new ArrayList<Integer>();
		while(enums.hasMoreElements()) {
			list.add((Integer) enums.nextElement());
		}
		ObservableList<Integer> oList = FXCollections.observableArrayList(list);

		tableNum.setItems(oList);

	}

	int getTableNumber()
	{
		return tableNum.getValue() ;
	}

	int getCovers()
	{
		return Integer.parseInt(coversTf.getText()) ;
	}

	Time getTime()
	{
		return Time.valueOf(timeTf.getText()) ;
	}

	boolean isConfirmed()
	{
		return confirmed ;
	}

}
