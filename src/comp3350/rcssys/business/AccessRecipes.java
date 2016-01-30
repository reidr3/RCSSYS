package comp3350.rcssys.business;

import comp3350.rcssys.application.Main;
import comp3350.rcssys.application.Services;
import comp3350.rcssys.objects.Recipe;
import comp3350.rcssys.persistence.DataAccess;
import java.util.ArrayList;
import java.util.List;

public class AccessRecipes {
	
	private DataAccess dataAccess;
	private List<Recipe> recipes;
	private Recipe recipe;

	public AccessRecipes() {
		dataAccess = ( DataAccess )Services.getDataAccess( Main.dbName );
		recipes = null;
		recipe = null;
	}
	
	public String deleteRecipe( Recipe currentRecipe ) {
		String result = null;
		
		if( currentRecipe != null ) {
			result = dataAccess.deleteRecipe( currentRecipe );
		}
		
		return result;
	}
	
	public int getId() {
		ArrayList<Recipe> list = new ArrayList<Recipe>();
		String result;
		int max = -1;
		int currentId;
		
		result = getRecipes(list);
		
		if ( result == null ) {
			for ( int i = 0; i < list.size(); i++ ) {
				currentId = list.get( i ).getID();
				max = Math.max( max, currentId );
			}
			
			max++;
		}
		
		return max;
	}

    public String getRecipes( List<Recipe> recipes ) {
    	if( recipes == null ) {
    		recipes = new ArrayList<Recipe>();
    	}
    	
        recipes.clear();
        
        return dataAccess.getRecipeSequential( recipes );
    }

	public Recipe getRecipe( int recipeId ) {
		recipe = null;
		
		if ( recipeId >= -1 ) {		
			recipes = dataAccess.getRecipe( new Recipe( recipeId ) );
			
			if ( recipes.size() == 1 ) {
				recipe = ( Recipe ) recipes.get( 0 );
			}
		}
		
		return recipe;
	}
	
	public String insertRecipe( Recipe currentRecipe ) {
		String result = null;
		
		if( currentRecipe != null ) {
			if( dataAccess.getRecipe( currentRecipe ).size() < 1 ) {
				result = dataAccess.insertRecipe( currentRecipe );
			}
		}
		
		return result;
	}
	
	public ArrayList<Recipe> searchForRecipe( String recipeTitle ) {		
		ArrayList<Recipe> searchResults = new ArrayList<Recipe>();
		recipes = new ArrayList<Recipe>();
		dataAccess.getRecipeSequential( recipes );
		
		if( recipeTitle != null ) {
		
			for( Recipe recipe : recipes ) {
				
				if( recipe.getTitle().indexOf( recipeTitle ) != -1 ){				
					searchResults.add( recipe );
				}
			}
		}
		
		return searchResults;	
	}

	public String updateRecipe( Recipe currentRecipe ) {
		String result = null;
		
		if( currentRecipe != null ) {
			result = dataAccess.updateRecipe( currentRecipe );
		}
		
		return result;
	}
}
