package comp3350.rcssys.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;	
import android.view.ViewGroup;
import android.widget.EditText;
import comp3350.rcssys.business.RecipesFilter;
import comp3350.rcssys.objects.Recipe;
import comp3350.rcssys.R;
import java.util.ArrayList;
import java.util.Collections;

public class SearchFragment extends Fragment {
	
	private static final String TOKEN_DELIMITERS = ",";
	
	private ViewHolder viewHolder;
	
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		prepareViewHolder();
		return inflater.inflate( R.layout.fragment_recipe_filter, container, false );
	}
	
	@Override
	public void onResume() {
		super.onResume();
		prepareViewHolder();
	}
	
	public ArrayList<Recipe> searchClick( View v ) {
		ArrayList<Recipe> recipes;
		RecipesFilter recipesFilter = new RecipesFilter();
		String title = viewHolder.title.getText().toString();
		recipesFilter.setTitleFilter( title );
		ArrayList<String> tokens = tokenize( viewHolder.ingredients.getText().toString() );
		
		for( String s : tokens ) {
			recipesFilter.createIngredientFilter( s );
		}
		
		tokens = tokenize( viewHolder.allergens.getText().toString() );
		
		for( String s : tokens ) {
			recipesFilter.createAllergyFilter( s );
		}
		
		tokens = tokenize( viewHolder.lifestyles.getText().toString() );
		
		for( String s : tokens ) {
			recipesFilter.createLifeStyleFilter( s );
		}
		
		recipes = recipesFilter.filterRecipes();
		
		return recipes;
	}
	
	public void filterClear( View v ) {
		viewHolder.title.getText().clear();
		viewHolder.ingredients.getText().clear();
		viewHolder.allergens.getText().clear();
		viewHolder.lifestyles.getText().clear();
	}
	
	private ArrayList<String> tokenize( String input ) {
		ArrayList<String> output = new ArrayList<String>();
		
		if( input != null && !input.isEmpty() ) {
			Collections.addAll( output, input.split( TOKEN_DELIMITERS ) );
		}
		
		return output;
	}
	
	private void prepareViewHolder() {
		viewHolder = new ViewHolder();
		viewHolder.title = ( EditText )getActivity().findViewById( R.id.fragment_filter_title );
		viewHolder.ingredients = ( EditText )getActivity().findViewById( R.id.fragment_filter_ingredients );
		viewHolder.allergens = ( EditText )getActivity().findViewById( R.id.fragment_filter_allergens );
		viewHolder.lifestyles = ( EditText )getActivity().findViewById( R.id.fragment_filter_lifestyles );
	}

	private class ViewHolder{
		public EditText title;
		public EditText ingredients;
		public EditText allergens;
		public EditText lifestyles;
	}
}

