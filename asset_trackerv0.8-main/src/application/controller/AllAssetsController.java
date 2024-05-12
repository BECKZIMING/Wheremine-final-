package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mapping.Asset;
import mapping.AssetHandler;
import mapping.TagHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAssetsController {

    @FXML
    private AnchorPane pane;

    @FXML
    private ListView<String> allAssetsList;

    @FXML
    private TextArea allAssetDetails;

    @FXML
    private ComboBox<String> chooseCategoryComboBox;

    @FXML
    private ComboBox<String> chooseLocationComboBox;


    private HashMap<String, Asset> allAssets = new HashMap<>();

    // workaround to show the warranty date in the list
    private HashMap<String, Asset> modifiedAssets = new HashMap<>();

    @FXML
    void getAllAssetsByCategory(ActionEvent event) {

        chooseLocationComboBox.setValue("Choose Location");
        this.allAssets = new AssetHandler().getAllAssetsByCategory(chooseCategoryComboBox.getValue());
        // show the name, location and warranty date of the expired assets
        updateModifiedList(allAssets);

    }

    @FXML
    void getAllAssetsByLocation(ActionEvent event) {
        chooseCategoryComboBox.setValue("Choose Category");
        this.allAssets = new AssetHandler().getAllAssetsByLocation(chooseLocationComboBox.getValue());
        // show the name, location and warranty date of the expired assets
        allAssetsList.getItems().clear();
        updateModifiedList(allAssets);
    }

    private void updateModifiedList(Map<String, Asset> allAssets) {
        this.modifiedAssets.clear();
        for (Map.Entry<String, Asset> entry : allAssets.entrySet()) {
            String newKey = entry.getKey() + " - " + entry.getValue().getLocation() + " - " + entry.getValue().getWarrantyDate();
            this.modifiedAssets.put(newKey, entry.getValue());
        }
        // show the name, location and warranty date of the expired assets
        allAssetsList.getItems().clear();
        allAssetsList.getItems().addAll(modifiedAssets.keySet());
    }

    @FXML
    void selectItem(MouseEvent event) {
        int selectedIndex = allAssetsList.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            String selectedAssetKey = allAssetsList.getItems().get(selectedIndex);
            Asset selectedAsset = modifiedAssets.get(selectedAssetKey);
            allAssetDetails.setText(selectedAsset.toString());
        }
    }

    @FXML
    void initialize() {
        List<String> categories = TagHandler.getCategories();
        List<String> locations = TagHandler.getLocations();
        chooseCategoryComboBox.getItems().addAll(categories);
        chooseLocationComboBox.getItems().addAll(locations);
    }

}
