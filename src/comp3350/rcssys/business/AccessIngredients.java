package comp3350.rcssys.business;

import comp3350.rcssys.application.Main;
import comp3350.rcssys.application.Services;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.persistence.DataAccess;
import java.util.ArrayList;
import java.util.List;

public class AccessIngredients {
	
	private DataAccess dataAccess;
	private List<Ingredient> ingredients;
	private Ingredient ingredient;
	
	public AccessIngredients() {
		dataAccess = ( DataAccess )Services.getDataAccess( Main.dbName );
		ingredients = null;
		ingredient = null;
	}
	
	public String deleteIngredient( Ingredient currentIngredient ) {
		String result = null;
		
		if( currentIngredient != null ) {
			result = dataAccess.deleteIngredient( currentIngredient );
		}
		
		return result;
	}

    public String getIngredients( List<Ingredient> ingredients ) {
    	if( ingredients == null ) {
    		ingredients = new ArrayList<Ingredient>();
    	}
    	
        ingredients.clear();
        
        return dataAccess.getIngredientSequential( ingredients );
    }

	public Ingredient getIngredient( String ingredientName ) {
		ingredient = null;
		
		if ( ingredientName == null || ingredientName.trim().equals( "" ) ) {
			
		} else {			
			ingredients = dataAccess.getIngredient( new Ingredient( ingredientName ) );
			
			if ( ingredients.size() == 1 ) {				
				ingredient =  ingredients.get( 0 );
			}
		}
		
		return ingredient;
	}
	
	public String insertIngredient( Ingredient currentIngredient ) {
		String result = null;
		
		if( currentIngredient != null ) {
			
			if( dataAccess.getIngredient( currentIngredient ).size() < 1 ) {
				
				result = dataAccess.insertIngredient( currentIngredient );
			}
		}
			
		return result; 
	}
	
	public ArrayList<Ingredient> searchForIngredient( String ingredientTitle ) {		
		ArrayList<Ingredient> searchResults = new ArrayList<Ingredient>();
		ingredients = new ArrayList<Ingredient>();
		dataAccess.getIngredientSequential( ingredients );
		
		if( ingredientTitle != null ) {
			
			for( Ingredient ingredient:ingredients ) {
				
				if( ingredient.getName().indexOf( ingredientTitle )!=-1 ){				
					searchResults.add( ingredient );
				}
			}
		}
			
		return searchResults;	
	}

	

	public String updateIngredient( Ingredient currentIngredient ) {
		String result = null;
		
		if( currentIngredient != null ) {
			result = dataAccess.updateIngredient( currentIngredient ); 
		}
		
		return result;
	}

	
}