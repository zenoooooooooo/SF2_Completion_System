package Controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DateConfigurationController implements Initializable {

	private Parent root;
	private Stage stage;
	private Scene scene;
	
	private Alert alert = new Alert(AlertType.WARNING);
	
	private Properties props = new Properties();
	private static final String CONFIG_PATH = "src/main/resources/config.properties";
	
	@FXML
	private TextField dateCoordinateField;
	
	
	public void goBack(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
		root = loader.load();
        
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
    @FXML
    public void goToAPConfiguration(ActionEvent event) throws IOException {
        String dateCoordinates = dateCoordinateField.getText().trim();
        if (dateCoordinates.isBlank() || dateCoordinates == null || dateCoordinates.isEmpty()) {
        	alert.setHeaderText("No coordinates entered");
        	alert.setContentText("Please enter a valid coordinates in the input box, follow the instructions above.");
        	alert.showAndWait();
        } else {
        	props.setProperty("dateCoordinates", dateCoordinates);

            try (OutputStream output = new FileOutputStream(CONFIG_PATH)) {
                props.store(output, "Warning! This is the configuration file. Do not delete it!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/APConfiguration.fxml"));
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
