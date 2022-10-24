package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import model.services.BillTagsServices;
import model.services.OwnerService;
import model.services.clientTypeServices;
import model.services.clientesService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuBilltags;
	
	@FXML
	private MenuItem menuOwner;
	
	@FXML
	private MenuItem menuClientType;
	
	@FXML
	private MenuItem menuClients;
	
	@FXML
	private MenuItem menuInputBill;
	
	@FXML
	private MenuItem menuConnection;
	
	
	@FXML
	private void onMenuItemBillTagsAction() {
		loadView("/gui/BillTagsList.fxml", (BillTagsListController controller) -> {
			controller.setBillTagsServices(new BillTagsServices());
			controller.updateTableView();
		});
	}
	

	@FXML
	private void onMenuOwnerAction() {
		loadView("/gui/OwnerList.fxml", (OwnerListController controller) -> {
			controller.setOwnerService(new OwnerService());
			controller.updateTableView();
		});
	}

	@FXML
	private void onMenuClientTypeAction() {
		loadView("/gui/clientTypeList.fxml", (ClientTypeListController controller) -> {
			controller.setClientTypeServices(new clientTypeServices());
			controller.updateTableView();
		});
	}

	@FXML
	private void onMenuItemClientsAction() {
		loadView("/gui/ClientesList.fxml", (ClientesListController controller) -> {
			controller.setClienteServices(new clientesService());
			controller.updateTableView();
		});
	}

	@FXML
	private void onMenuItemInputBillAction() {
		loadView("/gui/FaturaList.fxml", x -> {});
	}
	
	@FXML
	private void onMenuItemConnectionAction() {
		loadView("/gui/ConnectionView.fxml", x -> {});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			T controller =  loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loadind view", e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
		
	}
	
}
