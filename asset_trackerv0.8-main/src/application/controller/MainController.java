package application.controller;

//@TODO: Add a redirect back to home once successful

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import mapping.Asset;
import mapping.AssetHandler;

//Controls Main.fxml for switching between pages
public class MainController { 
	
	@FXML HBox mainBox; 
	
	// Load Home.fxml when the controller is initialized
	 @FXML
    public void initialize() {
		 HashMap<String, Asset> expiredAssets = new AssetHandler().getExpiredWarrantyAssets();
		 if (!expiredAssets.isEmpty()) {
			 Alert alert = new Alert(Alert.AlertType.WARNING);
			 alert.setTitle("Warning");
			 alert.setHeaderText("There are assets with expired warranties.");
			 ButtonType okButton = new ButtonType("OK");
			 ButtonType showMeButton = new ButtonType("Show Me");
			 alert.getButtonTypes().setAll(okButton, showMeButton);

			 Optional<ButtonType> result = alert.showAndWait();
			 if (result.get() == okButton){
				 loadFXML("view/Home.fxml");
			 } else if (result.get() == showMeButton) {
				 loadFXML("view/ExpiredWarrantyAssets.fxml");
			 }
		 } else {
			 loadFXML("view/Home.fxml");
		 }

    }
	 
	//Replaces display with the Home.fxml page when button is clicked
	@FXML public void goToHome() { 
		loadFXML("view/Home.fxml");
	}

	//Replaces display with the AddNewCategory.fxml page when button is clicked
	@FXML public void goToAddCategory() { 
		loadFXML("view/AddNewCategory.fxml"); 
	}
	
	//Replaces display with the AddNewLocation.fxml page when button is clicked
	@FXML public void goToAddLocation() { 
		loadFXML("view/AddNewLocation.fxml"); 
	}
	//Replaces display with the AddAsset.fxml page when button is clicked
	@FXML public void goToAddAsset() { 
		loadFXML("view/AddNewAsset.fxml"); 
	}
	//Replaces display with SearchAsset.fxml page when button is clicked
	@FXML public void goToSearchAsset() { 
		loadFXML("view/SearchAsset.fxml"); 
	}
	//Replaces display with ExpiredWarrantyAssets.fxml page when button is clicked
	@FXML public void goToExpiredWarrantyAssets() {loadFXML("view/ExpiredWarrantyAssets.fxml");}
	//Replace display with AllAssets.fxml page when button is clicked
	@FXML void goToAllAssetsPage() {loadFXML("view/AllAssets.fxml");}



	//Loads the display on the right.
	protected void loadFXML(String fxmlPath) { 
		URL url = getClass().getClassLoader().getResource(fxmlPath); 
		AnchorPane panel;
		try {
			panel = (AnchorPane) FXMLLoader.load(url);
			if (mainBox.getChildren().size() > 1 ) //Removes right AnchorPane beforehand to avoid overlapping views
				mainBox.getChildren().remove(1); 
			
			mainBox.getChildren().add(panel);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}