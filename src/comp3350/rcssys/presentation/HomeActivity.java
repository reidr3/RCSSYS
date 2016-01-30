package comp3350.rcssys.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import comp3350.rcssys.R;
import comp3350.rcssys.application.Main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        copyDatabaseToDevice();
        Main.startUp();
        setContentView( R.layout.activity_home );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Main.shutDown();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_home, menu );
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        return super.onOptionsItemSelected( item );
    }

    public void buttonRecipesOnClick( View v ) {
        Intent recipesIntent = new Intent( HomeActivity.this, RecipeListActivity.class );
        HomeActivity.this.startActivity( recipesIntent );
    }
    
    public void copyAssetsToDirectory( String[] assets, File directory ) throws IOException {
    	AssetManager assetManager = getAssets();

    	for (String asset : assets) {
    		String[] components = asset.split( "/" );
    		String copyPath = directory.toString() + "/" + components[components.length - 1];
    		char[] buffer = new char[1024];
    		int count;
    		File outFile = new File( copyPath );
    		
    		if ( !outFile.exists() ) {
    			InputStreamReader in = new InputStreamReader( assetManager.open( asset ) );
	    		FileWriter out = new FileWriter( outFile );
	    		count = in.read( buffer );
	    		
	    		while ( count != -1 ) {
	    			out.write( buffer, 0, count );
	        		count = in.read( buffer );
	    		}
	    		
	    		out.close();
	    		in.close();
    		}
    	}
	}
    
    public void openIngredients( View v ) {
    	Toast.makeText( this, "Not implemented", Toast.LENGTH_SHORT ).show();
    }
    
    private void copyDatabaseToDevice() {
    	final String DB_PATH = "db";
    	String[] assetNames;
    	Context context = getApplicationContext();
    	File dataDirectory = context.getDir( DB_PATH, Context.MODE_PRIVATE );
    	AssetManager assetManager = getAssets();
    	
    	try {
    		assetNames = assetManager.list(DB_PATH);
    		
    		for ( int i = 0; i < assetNames.length; i++ ) {
    			assetNames[i] = DB_PATH + "/" + assetNames[i];
    		}

    		copyAssetsToDirectory( assetNames, dataDirectory );
    		Main.setDBPathName( dataDirectory.toString() + "/" + Main.dbName );
    	} catch ( IOException ioe ) {
    		Messages.warning( this, "Unable to access application data: " + ioe.getMessage() );
    	}
    }
}
