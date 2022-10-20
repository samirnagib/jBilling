package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
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
import model.entities.clientType;
import model.services.clientTypeServices;

public class clientTypeListController implements Initializable {

	@FXML
	private Button btNovo;
	
	@FXML
	private TableView<clientType> tvClientType;
	
	@FXML
	private TableColumn<clientType, Integer> tbColumnidType;
	
	@FXML
	private TableColumn<clientType, String> tbColumntypeName;
	
	@FXML 
	private TableColumn<clientType, clientType> tbColumnEDIT;
	
	@FXML 
	private TableColumn<clientType, clientType> tbColumnREMOVE;
	
	
	private clientTypeServices service;
	private ObservableList<clientType> obsList;
	
	
	public void setClientTypeServices(clientTypeServices service) {
		this.service = service;
	}
	
	@FXML
	private void btNovoOnAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		clientType obj = new clientType();
		createDialogForm(obj, "/gui/ClientTypeForm.fxml", parentStage);
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		tbColumnidType.setCellValueFactory(new PropertyValueFactory<>("idType"));
		tbColumntypeName.setCellValueFactory(new PropertyValueFactory<>("typeName"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tvClientType.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<clientType> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tvClientType.setItems(obsList);
		initEditButtons();
		initRemoveButtons(); 
	}
	
	private void createDialogForm(clientType obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			ClientTypeFormController controller = loader.getController();
			controller.setClientType(obj);
			controller.setClientTypeService(new clientTypeServices());
			controller.subscribeDataChangeListener(this);
			
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os Tipos de servidor:");
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

	public void onDataChanged() {
		updateTableView();
		
	}
	
	private void initEditButtons() {
		tbColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tbColumnEDIT.setCellFactory(param -> new TableCell<clientType, clientType>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(clientType obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ClientTypeForm.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	
	private void initRemoveButtons() {
		tbColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tbColumnREMOVE.setCellFactory(param -> new TableCell<clientType, clientType>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(clientType obj, boolean empty) {
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
	
	private void removeEntity(clientType obj) {
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
	
	
	

}
