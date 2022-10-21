package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.owner;

public class OwnerListController implements Initializable {

	@FXML
	private Button btNovo;
	
	@FXML
	private TableView<owner> tableViewOwner;
	
	@FXML
	private TableColumn<owner, Integer> tableColumnId;
	
	@FXML
	private TableColumn<owner, String> tableColumnNome;
	
	@FXML
	private TableColumn<owner, String> tableColumnEmail1;
	
	@FXML
	private TableColumn<owner, String> tableColumnEmail2;
	
	@FXML
	private TableColumn<owner, String> tableColumnProjeto;
	
	@FXML
	private TableColumn<owner, String> tableColumnAR;
	
	
	@FXML
	public void onBtNewAction() {
		System.out.println("Botao novo");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}


	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("ID"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("RESPONSAVEL"));
		tableColumnEmail1.setCellValueFactory(new PropertyValueFactory<>("EMAIL PRINCIPAL"));
		tableColumnEmail2.setCellValueFactory(new PropertyValueFactory<>("EMAIL ALTERNATIVO"));
		tableColumnProjeto.setCellValueFactory(new PropertyValueFactory<>("PROJETO"));
		tableColumnAR.setCellValueFactory(new PropertyValueFactory<>("AR"));
		
		Stage stage =  (Stage) Main.getMainScene().getWindow();
		tableViewOwner.prefHeightProperty().bind(stage.heightProperty());
		
	}

}
