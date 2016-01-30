package comp3350.rcssys.presentation;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import comp3350.rcssys.R;
import comp3350.rcssys.business.AccessRecipes;
import comp3350.rcssys.objects.Recipe;
import java.util.ArrayList;

public class RecipeListActivity extends FragmentActivity {
	public static final String EXTRA_RECIPE_TITLE = "recipeTitle";
	
	private ArrayList<Recipe> recipeList;
	private AccessRecipes recipeAccess;
	private RecipeListFragment recipeListFragment;
	private SearchFragment searchFragment;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_recipe_list );		
		recipeAccess = new AccessRecipes();
        recipeList = new ArrayList<Recipe>();
        recipeAccess.getRecipes( recipeList );
        recipeListFragment = new RecipeListFragment();
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, recipeListFragment ).commit();
	}
	
	@Override
    public void onResume() {
    	super.onResume();
    	refreshListView();
    }

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		int id = item.getItemId();
		
		if ( id == R.id.action_settings ) {
			return true;
		}
		
		return super.onOptionsItemSelected( item );
	}
	
	public void newRecipe( View v ) {
		recipeListFragment.newRecipe( v );
    }
	
	public void searchClick( View v ) {
		ArrayList<Recipe> results = searchFragment.searchClick( v );
		switchFragmentToRecipeDisplay();
		recipeListFragment.update( results );
	}
	
	public void goToSearch( View v ) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace( R.id.fragment_container, searchFragment );
		transaction.addToBackStack( null );
		transaction.commit();
	}
	
	public void filterCancel( View v ) {
		switchFragmentToRecipeDisplay();
	}
	
	public void filterClear( View v ) {
		searchFragment.filterClear( v );
	}
	
	public void refreshClick( View v ) {
		refreshListView();
	}
	
	private void switchFragmentToRecipeDisplay() {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace( R.id.fragment_container, recipeListFragment );
		transaction.addToBackStack( null );
		transaction.commit();
	}
	
	private void refreshListView() {
    	recipeAccess.getRecipes( recipeList );
    	recipeListFragment.update( recipeList );
    }
}
