package comp3350.rcssys.application;

import comp3350.rcssys.application.Services;

public class Main {
	public static final String dbName = "sc";
	private static String dbPathName = "database/sc";

	public static void main( String[] args ) {
		startUp();
		shutDown();
		System.out.println( "All done" );
	}

	public static String getDBPathName() {
		String chosenDB;
		
		if ( dbPathName == null )
			chosenDB = dbName;
		else
			chosenDB = dbPathName;
		
		return chosenDB;
	}
	
	public static void setDBPathName( String pathName ) {
		System.out.println( "Setting DB path to: " + pathName );
		dbPathName = pathName;
	}
	
	public static void shutDown() {
		Services.closeDataAccess();
	}
	
	public static void startUp() {
		Services.createDataAccess( dbName );
	}
}
