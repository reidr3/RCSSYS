package comp3350.rcssys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import comp3350.rcssys.business.AccessRecipes;
import comp3350.rcssys.objects.Recipe;

public abstract class RecipeActivity extends Activity {
	private Recipe initialRecipe;
	
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		initialRecipe = null;
		loadRecipeFromIntent();
	}
	
	public Recipe getInitialRecipe() {
		loadRecipeFromIntent();
		
		return initialRecipe;
	}
	
	private void loadRecipeFromIntent(){
		Intent intent = getIntent();
		int recipeID = intent.getIntExtra( RecipeListFragment.RECIPE_IDENTIFIER,Recipe.EMPTY_ID );
		AccessRecipes accessRecipes = new AccessRecipes();
		initialRecipe = accessRecipes.getRecipe( recipeID );
	}
}
