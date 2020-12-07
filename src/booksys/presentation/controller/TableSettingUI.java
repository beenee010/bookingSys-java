package booksys.presentation.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import booksys.application.persistency.TableMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TableSettingUI implements Initializable {

	@FXML private Button okBtn;
	@FXML private ListView<String> tableView;
	ObservableList<String> oList;
	private Window w;
	private Vector numbers;
	private Vector places;
	private Vector oids;
	private Stage preStage;
	private Button preBtn;

	public TableSettingUI(Stage preStage, Button preBtn) {
		this.preStage = preStage;
		this.preBtn = preBtn;
		w = Window.getInstance();
	}

	@FXML
	void click(MouseEvent event) throws IOException {
		if(event.getClickCount() > 1) {
			String str = tableView.getSelectionModel().getSelectedItem();

			String[] split = str.split("\t\t");

			String oid = split[0];
			String tableNum = split[1];
			String tablePlaces = split[2];

			Stage stage = (Stage) tableView.getScene().getWindow();
			Window.getInstance().setTableDialog(
					stage, okBtn, oid, tableNum, tablePlaces);
		}

	}
	
    @FXML
    void addTable(ActionEvent event) throws IOException {
    	//AddTableDialog
    	Stage stage = (Stage) tableView.getScene().getWindow();
    	Window.getInstance().addTableDialog(stage);

    }

    @FXML
    void deleteTable(ActionEvent event) throws IOException {
    	//DeleteTableDialog
    	String str = tableView.getSelectionModel().getSelectedItem();
    	
		String[] split = str.split("\t\t");

		String oid = split[0];
		String tableNum = split[1];
		String tablePlaces = split[2];
		
		Stage stage = (Stage) tableView.getScene().getWindow();
		
		String text = "정말 " + tableNum + "번 테이블을 삭제 하시겠습니까?";
		ConfirmDialogChoice d = new ConfirmDialogChoice(text,"deleteTable",oid);
		Window.getInstance().deleteTableDialog(d, stage);
    }
	
	@FXML
	void ok(ActionEvent event) {
		Stage stage = (Stage) okBtn.getScene().getWindow();
		Window.getInstance().loginUI(stage);
	}
	
	@FXML
	void quit(ActionEvent event) {
		Stage stage = (Stage) okBtn.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	void refresh(ActionEvent event) {
		oids.clear();
		numbers.clear();
		places.clear();
		oList.removeAll(oList);
		setView();
	}


	void setView() {

		oids = TableMapper.getInstance().getTableOids();
		numbers = TableMapper.getInstance().getTableNumbers();
		places = TableMapper.getInstance().getTablecovers();

		ArrayList<String> list = new ArrayList<String>();

		for(int i = 0; i < numbers.size(); i++) {
			String str = Integer.toString((int) oids.get(i)) + "\t\t" + Integer.toString((int) numbers.get(i)) + "\t\t" + 
					Integer.toString((int)places.get(i));
			list.add(str);
		}
		oList = FXCollections.observableArrayList(list);
		tableView.setItems(oList);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setView();
	}

}
