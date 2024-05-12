package mapping;

import java.util.ArrayList;

//Facade class for limited external access to SQLController including only initialization, opening, and
//Closing DB connection.
public class DBinit {
    private SQLController sqlite = SQLController.getConnector();
    private TagHandler tagger = new TagHandler();
    public DBinit(){
    	
    }

    //If AssetTracker.db file does not exist, create the file and necessary tables, and connect to DB
    //otherwise, only open connection to DB
    public void dbInit() {
    	
    	if(!sqlite.dbExists()) {
    		System.out.println(sqlite.connectDB());
            System.out.println(sqlite.createTables("Categories",
            		"CategoryID INTEGER PRIMARY KEY, Name TEXT unique not null"));
            System.out.println(sqlite.createTables("Locations",
            		"LocationID INTEGER PRIMARY KEY, Name TEXT unique not null, Description TEXT")); 
            System.out.println(sqlite.createTables("Assets",
            		"AssetID INTEGER PRIMARY KEY, Name TEXT unique not null, Category TEXT not null, Location TEXT not null,"
            		+ " PurchaseDate DATE, Description TEXT, PurchaseValue FLOAT, WarrantyDate DATE"));
            
            TagHandler.initCache();
            
            //preload 2 categories and 2 locations
            tagger.addTag(new Category("Trinkets"));
            tagger.addTag(new Category("Baubles"));
            
            tagger.addTag(new Location("Closet"));
            tagger.addTag(new Location("Garage"));
            
    	}
    	else {
    		//initialize tag cache
    		System.out.println(sqlite.connectDB());
    		TagHandler.initCache();
    		TagHandler.importTags();
    		
    	}
      	
    }
    
    //Closes DB connection
    public void closeDB() {
    	System.out.println(sqlite.closeConnection());
    }

}



