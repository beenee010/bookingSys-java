package booksys.presentation.controller;

import java.io.IOException;

import booksys.application.persistency.TableMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTableDialog {
	private Window w;
    @FXML private TextField numberTf;
    @FXML private TextField placesTf;
    
    public AddTableDialog() {
    	w = Window.getInstance();
    }
    
    @FXML
    void add(ActionEvent event) throws IOException {
    	Stage stage = (Stage)numberTf.getScene().getWindow();
    	int tableNum = Integer.parseInt(numberTf.getText());
    	int places = Integer.parseInt(placesTf.getText());
    	
    	stage.close();
    	TableMapper.getInstance().createTable(tableNum, places);
    	ConfirmDialogOk d = new ConfirmDialogOk("추가가 완료되었습니다.");
    	Window.getInstance().confirmDialogOk(d, stage);
    }

    @FXML
    void quit(ActionEvent event) {
    	Stage stage = (Stage)numberTf.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void undo(ActionEvent event) {
    	Stage stage = (Stage)numberTf.getScene().getWindow();
    	stage.close();
    }
}
