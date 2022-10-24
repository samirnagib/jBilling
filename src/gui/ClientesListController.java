package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.clientes;
import model.services.OwnerService;
import model.services.clientTypeServices;
import model.services.clientesService;

public class ClientesListController implements Initializable, DataChangeListener {
	
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
	private TableColumn<clientes, clientes> tableColumnEDIT;

	@FXML
	private TableColumn<clientes, clientes> tableColumnREMOVE;
	
	
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
		initEditButtons();
		initRemoveButtons(); 
	}
	
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<clientes, clientes>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(clientes obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
			
		});
	
	}


	private void removeEntity(clientes obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}


	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<clientes, clientes>() {
			private final Button button = new Button("Editar");
			@Override
			protected void updateItem(clientes obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ClientesForm.fxml", Utils.currentStage(event)));
			}
		});
	}


	public void updateFilteredTableView(String Filter) {
		
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<clientes> list = service.findClientByName(Filter);
		obsList = FXCollections.observableArrayList(list);
		tableViewClientes.setItems(obsList);
		initEditButtons();
		initRemoveButtons(); 
	}

	
	@FXML
	public void onBtNovoAction(ActionEvent event)  {
		Stage parentStage = Utils.currentStage(event);
		clientes obj = new clientes();
		createDialogForm(obj, "/gui/ClientesForm.fxml", parentStage);
		
	}
	private void createDialogForm(clientes obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			ClientesFormController controller = loader.getController();
			controller.setClient(obj);
			controller.setServices(new clientesService(), new clientTypeServices(), new OwnerService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com dos dados do Cliente:");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
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

	@Override
	public void onDataChanged() {
		updateTableView();
		
	}
	
	
	
	
	

}
