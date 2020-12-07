package booksys.presentation.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import booksys.application.persistency.TableMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetTableDialog implements Initializable{

    @FXML private TextField placesTf;
    @FXML private Label numLabel;
	private Window w;
	private Stage preStage;
	private Button preBtn;
	private int tableNum;
	private int places;
	private int oids;
	
	public SetTableDialog(Stage preStage, Button preBtn,String oid, String n, String p) {
		this.preStage = preStage;
		this.preBtn = preBtn;
		tableNum = Integer.parseInt(n);
		places = Integer.parseInt(p);
		oids = Integer.parseInt(oid);
		w = Window.getInstance();
	}
	

    @FXML
    void quit(ActionEvent event) {
    	Stage stage = (Stage) numLabel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void save(ActionEvent event) throws IOException {
    	int covers = Integer.parseInt(placesTf.getText());
    	TableMapper.getInstance().saveTable(oids, covers);
    	Stage stage = (Stage) numLabel.getScene().getWindow();
    	stage.close();
    	
    	ConfirmDialogOk d = new ConfirmDialogOk("작업을 완료했습니다 !");
    	w.confirmDialogOk(d,stage);
    }

    @FXML
    void undo(ActionEvent event) {
    	Stage stage = (Stage) numLabel.getScene().getWindow();
    	stage.close();
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String text = Integer.toString(tableNum);
		numLabel.setText(text);
	}
	
}
