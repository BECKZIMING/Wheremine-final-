package mapping;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

//Class managing Database operations regarding assets
public class AssetHandler {
	
	final private String tableName = "Assets";
	
	private static Asset selectedAsset; // selection cache
	
	private SQLController sqlite;
	
	public AssetHandler() {
		sqlite = SQLController.getConnector();
	}
	
	//adds asset to database
	public String addAsset(Asset asset) {
		String result = (sqlite.insertData(asset.getTableName(), asset.getFields(), asset.getInputString()));
		
		return result;
	}

	// get expired warranty assets
	public HashMap<String, Asset> getExpiredWarrantyAssets() {
		HashMap<String, Asset> expiredAssets = new HashMap<>();
		ResultSet rs = sqlite.getExpiredWarrantyAssets();
		return convertResultSetToMap(expiredAssets, rs);
	}

	// get all assets by category
	public HashMap<String, Asset> getAllAssetsByCategory(String category) {
		HashMap<String, Asset> assets = new HashMap<>();
		ResultSet rs = sqlite.getAllAssetsByCategory(category);
		return convertResultSetToMap(assets, rs);
	}

	public HashMap<String, Asset> getAllAssetsByLocation(String location) {
		HashMap<String, Asset> assets = new HashMap<>();
		ResultSet rs = sqlite.getAllAssetsByLocation(location);
		return convertResultSetToMap(assets, rs);
	}

	private HashMap<String, Asset> convertResultSetToMap(HashMap<String, Asset> assets, ResultSet rs) {
		try {
			while(rs.next()) {
				Asset asset = new Asset(rs.getString("Name"), rs.getString("Category"), rs.getString("Location"));
				if(rs.getString("PurchaseDate")!= null)
					asset.setPurchDate(Date.valueOf(rs.getString("PurchaseDate")));
				if(rs.getString("WarrantyDate")!= null)
					asset.setWarrantyDate(Date.valueOf(rs.getString("WarrantyDate")));
				asset.setDescription(rs.getString("Description"));
				asset.setPurchValue(rs.getFloat("PurchaseValue"));
				assets.put(asset.getName(), asset);
			}
			rs.close();
			sqlite.closeStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return assets;
	}

	//search for asset based on name substring, return hashmap of all matches with Name as key
	public HashMap<String, Asset> searchAsset(String substring){
		HashMap<String, Asset> results = new HashMap<>();
		ResultSet rs = sqlite.searchData(tableName, "Name", "'%" + substring + "%'");

		return convertResultSetToMap(results, rs);
	}
	
	//delete asset from Database
	public String deleteAsset(String name) {
		return sqlite.deleteData("Assets", "Name", name);
	}
	//update asset in Database
	public String updateAsset(String updateField, String updateValue, String name) {
		return sqlite.updateData("Assets", updateField, updateValue, "Name", "'"+name+"'");
	}
	
	public static void selectAsset(Asset asset) { //add asset to selection cache
		selectedAsset = asset;
	}
	
	public static Asset getSelection() { // return asset in selection cache
		return selectedAsset;
	}
	
	public static void resetSelection() { // resets selection cache
		selectedAsset = null;
	}
}
