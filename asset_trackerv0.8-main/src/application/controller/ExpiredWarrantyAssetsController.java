package application.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mapping.Asset;
import mapping.AssetHandler;

public class ExpiredWarrantyAssetsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private ListView<String> expiredAssetsList;

    @FXML
    private TextArea expiredAssetDetails;

    @FXML
    void selectExpiredItem(MouseEvent event) {
        int selectedIndex = expiredAssetsList.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            String selectedAssetKey = expiredAssetsList.getItems().get(selectedIndex);
            Asset selectedAsset = modifiedExpiredAssets.get(selectedAssetKey);
            expiredAssetDetails.setText(selectedAsset.toString());
        }
    }

    private HashMap<String, Asset> expiredAssets = new HashMap<>();
    private HashMap<String, Asset> modifiedExpiredAssets = new HashMap<>();

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'ExpiredWarrantyAssets.fxml'.";
        assert expiredAssetsList != null : "fx:id=\"expiredAssetsList\" was not injected: check your FXML file 'ExpiredWarrantyAssets.fxml'.";
        assert expiredAssetDetails != null : "fx:id=\"expiredAssetDetails\" was not injected: check your FXML file 'ExpiredWarrantyAssets.fxml'.";
        // load expired assets
        this.expiredAssets = new AssetHandler().getExpiredWarrantyAssets();
        // show the name and also the warranty date of the expired assets
        for (Map.Entry<String, Asset> entry : expiredAssets.entrySet()) {
            String newKey = entry.getKey() + " - " + entry.getValue().getWarrantyDate();
            modifiedExpiredAssets.put(newKey, entry.getValue());
        }
        expiredAssetsList.getItems().addAll(modifiedExpiredAssets.keySet());
    }
}
