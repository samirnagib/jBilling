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
import model.entities.owner;
import model.services.OwnerService;

public class OwnerListController implements Initializable, DataChangeListener {

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
	
	@FXML
	private TableColumn<owner, owner> tableColumnEDIT;
	
	@FXML
	private TableColumn<owner, owner> tableColumnREMOVE;
	
	
	private ObservableList<owner> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		owner obj = new owner();
		createDialogForm(obj ,"/gui/OwnerForm.fxml", parentStage);
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
		initEditButtons();
		initRemoveButtons();
	}
	
	private void createDialogForm(owner obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			OwnerFormController controller = loader.getController();
			controller.setOwner(obj);
			controller.setOwnerService(new OwnerService());
			controller.subscribeDataChangeListerner(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com o Responsácel");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}
	
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<owner, owner>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(owner obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/OwnerForm.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<owner, owner>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(owner obj, boolean empty) {
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
	
	private void removeEntity(owner obj) {
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
