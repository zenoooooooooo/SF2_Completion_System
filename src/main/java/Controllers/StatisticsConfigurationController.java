package Controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class StatisticsConfigurationController implements Initializable {
private Alert alert = new Alert(AlertType.NONE);
	
	private Properties props = new Properties();
	private static final String CONFIG_PATH = "src/main/resources/config.properties";
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@FXML
	private TextField statsCoordinateField;
	
	public void goBack(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/APConfiguration.fxml"));
		root = loader.load();
        
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void saveConfiguration(ActionEvent event) throws IOException {
        String statsCoordinates = statsCoordinateField.getText().trim();
        if (statsCoordinates.isBlank() || statsCoordinates == null || statsCoordinates.isEmpty()) {
        	alert.setAlertType(AlertType.WARNING);
        	alert.setHeaderText("No coordinates entered");
        	alert.setContentText("Please enter a valid coordinates in the input box, follow the instructions above.");
        	alert.showAndWait();
        } else {
        	props.setProperty("statisticsCoordinates", statsCoordinates);
        	alert.setAlertType(AlertType.INFORMATION);
    		alert.setHeaderText("Configuration Saved!");
        	alert.setContentText("Configuration settings have been saved successfully");
        	alert.showAndWait();
            try (OutputStream output = new FileOutputStream(CONFIG_PATH)) {
                props.store(output, "Warning! This is the configuration file. Do not delete it!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
            root = loader.load();
    		
    		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();

        }
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try (InputStream istream = new FileInputStream(CONFIG_PATH)) {
			props.load(istream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
