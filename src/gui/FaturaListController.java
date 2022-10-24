package gui;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.entities.clientes;
import model.entities.fatura;

public class FaturaListController implements Initializable {

	@FXML
	private Button btNovo;
	
	@FXML
	private Button btProcurar;
	
	@FXML
	private RadioButton rbPesquisarData;
	
	@FXML
	private RadioButton rbPesquisarPeriodo;
	
	@FXML
	private TextField txtDtInicial;
	
	@FXML
	private TextField txtDtFinal;
	
	@FXML
	private TableView<fatura> tableViewFatura;
	
	@FXML
	private TableColumn<fatura, Integer> tableColumnSeq;
	
	@FXML
	private TableColumn<fatura, Date> tableColumnRef;
	
	@FXML
	private TableColumn<fatura, String> tableColumnCliente;
	
	@FXML
	private TableColumn<fatura, String> tableColumnTag;
	
	@FXML
	private TableColumn<fatura, String> tableColumnInstancia;
	
	@FXML
	private TableColumn<fatura, String> tableColumnSubClient;
	
	@FXML
	private TableColumn<fatura, Double> tableColumnTotal;
	
	@FXML
	private TableColumn<fatura, String> tableColumnAR;
	
	@FXML
	private TableColumn<fatura, fatura> tableColumnEDIT;

	@FXML
	private TableColumn<fatura, fatura> tableColumnREMOVE;
	
	
	
	@FXML
	private void btNovoOnAction() {
		System.out.println("novo");
	}
	
	@FXML
	private void btPesquisarOnAction() {
		System.out.println("Pesquisar");
	}
	
	@FXML
	private void rbPesquisarDataOnAction() {
		System.out.println("click por data");
	}
	
	@FXML
	private void rbPesquisarPeriodoOnAction() {
		System.out.println("click por periodo");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
