package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.clientes;
import model.services.clientesService;

public class ClientesListController implements Initializable {
	
	@FXML
	private Button btNovo;
	
	@FXML
	private Button btFilterName;
	
	@FXML
	private Button btResetFilter;
	
	@FXML
	private TableView<clientes> tableViewClientes;
	
	@FXML
	private TableColumn<clientes, Integer> tableColumnID;

	@FXML
	private TableColumn<clientes, String> tableColumnName;

	@FXML
	private TableColumn<clientes, String> tableColumnHostName;

	@FXML
	private TableColumn<clientes, String> tableColumnEDIT;

	@FXML
	private TableColumn<clientes, String> tableColumnREMOVE;
	
	
	private clientesService service;
	
	private ObservableList<clientes> obsList;
	
	public void setClienteServices(clientesService service) {
		this.service = service;
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("idClient"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
		tableColumnHostName.setCellValueFactory(new PropertyValueFactory<>("clientHostname"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewClientes.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<clientes> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewClientes.setItems(obsList);
	}
	
	
	public void updateFilteredTableView(String Filter) {
		
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<clientes> list = service.findClientByName(Filter);
		obsList = FXCollections.observableArrayList(list);
		tableViewClientes.setItems(obsList);
	}

	
	
	@FXML
	public void onBtNovoAction() {
		System.out.println("btNovo");
	}
	@FXML
	private void onbtFilterNameAction() {
		//System.out.println("btFilterName");
		String valor = Utils.showInputData("Entre com o nome do servidor");
		updateFilteredTableView(valor);
	}
	
	@FXML
	private void onbtResetFilterAction() {
		System.out.println("ResetFilter");
		updateTableView();
	}
	

}
