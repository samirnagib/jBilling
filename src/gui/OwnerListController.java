package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.owner;
import model.services.OwnerService;

public class OwnerListController implements Initializable {

	private OwnerService service;
	
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
	
	private ObservableList<owner> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("Botao novo");
	}
	
	public void setOwnerService(OwnerService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}


	private void initializeNodes() {
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idOwner"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("owName"));
		tableColumnEmail1.setCellValueFactory(new PropertyValueFactory<>("owEmail1"));
		tableColumnEmail2.setCellValueFactory(new PropertyValueFactory<>("owEmail2"));
		tableColumnProjeto.setCellValueFactory(new PropertyValueFactory<>("owProjetoArea"));
		tableColumnAR.setCellValueFactory(new PropertyValueFactory<>("owAR"));
		
		Stage stage =  (Stage) Main.getMainScene().getWindow();
		tableViewOwner.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<owner> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewOwner.setItems(obsList);
	}

}
