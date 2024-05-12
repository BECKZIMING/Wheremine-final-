package mapping;

import java.sql.Date;

//Storage class that handles Asset data
public class Asset {
	
	final private String tableName = "Assets";
	final private String fields = "Name, Category, Location, PurchaseDate, Description, "
			+ "PurchaseValue, WarrantyDate";
	
	
	private String name;
	private String category;
	private String location;
	
	private Date purchDate;
	private String description;
	private float purchValue;
	private Date warrantyDate;
	
	
	public Asset(String name, String category, String location) {
		
		this.name = name;
		this.category = category;
		this.location = location;
		
		this.purchDate = null;
		this.warrantyDate = null;
		
		description = "None";
		purchValue = 0;
	}
	
	public void setPurchDate(Date purchDate) {
		this.purchDate = purchDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPurchValue(float purchValue) {
		this.purchValue = purchValue;
	}
	
	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getTableName() {
		
		return tableName;
	}

	public String getFields() {
		
		return fields;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getLocation() {
		return location;
	}
	
	public float getPurchValue() {
		return purchValue;
	}
	
	public Date getPurchDate() {
		return purchDate;
	}
	
	public Date getWarrantyDate() {
		return warrantyDate;
	}
	
	//returns all attributes as a concatenated string for Sqlite and testing operations
	public String getInputString() {
		String purch;
		String warranty;
		
		if(purchDate == null) {
			purch = "NULL";
		}
		else {
			purch = "'" + purchDate.toString() + "'";
		}
		if(warrantyDate == null){
			warranty = "NULL";
		}
		else {
			warranty = "'" + warrantyDate.toString() + "'";
		}
		
		return "'"+ name + "', '" + category +"', '" + location + "', "
				+ purch + ", '" + description + "', " + String.valueOf(purchValue)
				+ ", " + warranty;
	}

	public String toString() {
		return "Name:\n" + name + "\n\nCategory:\n" + category + "\n\nLocation:\n" + location
				+ "\n\nPurchase Date:\n" + purchDate + "\n\nDescription:\n" + description
				+ "\n\nPurchase Value:\n" + purchValue + "\n\nWarranty Date:\n" + warrantyDate;
	}
	
}

