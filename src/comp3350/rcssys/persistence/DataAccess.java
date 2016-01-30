package comp3350.rcssys.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.Recipe;


public interface DataAccess {

	public void close();
	
	public void open( String string );
	
	public String deleteIngredient( Ingredient ingredient );
	
	public String deleteRecipe( Recipe currentRecipe );
	
	public ArrayList<Ingredient> getIngredient( Ingredient ingredient );
	
	public String getIngredientSequential( List<Ingredient> ingredient );
	
	public ArrayList<Recipe> getRecipe( Recipe currentRecipe );

	public String getRecipeSequential( List<Recipe> recipesResult );

	public String insertRecipe( Recipe currentRecipe );	

	public String insertIngredient( Ingredient ingredient );
	
	public String updateIngredient( Ingredient ingredient );
	
	public String updateRecipe( Recipe currentRecipe );
}
	