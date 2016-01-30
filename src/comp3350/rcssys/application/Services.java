package comp3350.rcssys.application;

import comp3350.rcssys.application.Main;
import comp3350.rcssys.persistence.DataAccess;
import comp3350.rcssys.persistence.DataAccessObject;

public class Services
{
	private static DataAccess dataAccessService = null;
	
	public static void closeDataAccess() {
		if ( dataAccessService != null ) {
			dataAccessService.close();
		}
		
		dataAccessService = null;
	}

	public static DataAccess createDataAccess( String dbName ) {
		if ( dataAccessService == null ) {
			dataAccessService = new DataAccessObject( dbName );
			dataAccessService.open( Main.getDBPathName() );
		}
		
		return dataAccessService;
	}

	public static DataAccess createDataAccess( DataAccess alternateDataAccessService )
	{
		if ( dataAccessService == null ) {
			dataAccessService = alternateDataAccessService;
			dataAccessService.open( Main.getDBPathName() );
		}
		
		return dataAccessService;
	}

	public static DataAccess getDataAccess( String dbName ) {
		if ( dataAccessService == null ) {
			System.out.println( "Connection to data access has not been established." );
			System.exit( 1 );
		}
		
		return dataAccessService;
	}

	
}

