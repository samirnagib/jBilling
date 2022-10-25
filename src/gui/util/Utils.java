package gui.util;

import java.io.File;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage; 

public class Utils {
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
		
	}

	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		}catch (NumberFormatException e) {
			return null;
		}

	}
	public static boolean validaEmail(String emailAddress) {
		String regexPattern = "^(.+)@(\\S+)$";
		
		return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
	}
	
	
	public static Double tryParseToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%." + decimalPlaces + "f", item));
					}
				}
			};
			return cell;
		});
	}
	
	public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String formato) {
		SimpleDateFormat format = new SimpleDateFormat(formato);
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(format.format(item));
					}
				}
			};
			return cell;
		});
	}

	public static boolean getFileStatus() {
		String sFileParameter="config.properties";
		String sSubFolder = System.getProperty("file.separator")+"config"+System.getProperty("file.separator");
		String appPath = System.getProperty("user.dir");

		File fConfig = new File(appPath+sSubFolder+sFileParameter);
		if  ( fConfig.isFile() ) {
			return true;
		} else {
			return false;
		}
	}

	public static File loadFileConfig() {
			String sFileParameter="config.properties";
			String sSubFolder = System.getProperty("file.separator")+"config"+System.getProperty("file.separator");
			String appPath = System.getProperty("user.dir");

			File fConfig = new File(appPath+sSubFolder+sFileParameter);
			
			return fConfig;
			
	}
	
	public static String showInputData(String Title) {
		TextInputDialog td = new TextInputDialog("entre com o servidor");
		td.setHeaderText(Title);
		td.showAndWait();
		return td.getResult();
		
	}
	
	public static String convertData(String data) throws ParseException {
		Date dataRecebida;
		String dataSaida; 
		dataRecebida = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt-BR")).parse(data);
		dataSaida = new SimpleDateFormat("yyyy-MM-dd", new Locale("en-US")).format(dataRecebida);
		return dataSaida;
	}
	
	
}
