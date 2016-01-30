package comp3350.rcssys.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import comp3350.rcssys.R;
import comp3350.rcssys.business.AccessIngredients;
import comp3350.rcssys.business.AccessRecipes;
import comp3350.rcssys.business.UnitConversions;
import comp3350.rcssys.objects.Allergy;
import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.LifeStyle;
import comp3350.rcssys.objects.Recipe;
import comp3350.rcssys.presentation.ArrayAdapters.IngredientAdapter;
import java.util.ArrayList;

public class RecipeDisplay extends RecipeActivity {
	private Recipe initialRecipe;
	private AccessRecipes recipeAccess;
	private ViewHolder viewHolder;
	private TextWatcher watcher;
	AlertDialog.Builder builder;
	private IngredientAdapter adapter;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_recipe_display );
		prepareViewHolder();
		prepareSpinner();
		recipeAccess = new AccessRecipes();
		
		watcher = new TextWatcher() {
			@Override
			public void beforeTextChanged( CharSequence s, int start, int count, int after ) {}

			@Override
			public void onTextChanged( CharSequence s, int start, int before,int count ) {
				viewHolder.saveButton.setVisibility( View.VISIBLE );
			}
			
			@Override
			public void afterTextChanged( Editable s ) {}
		};
		
		initialRecipe = getInitialRecipe();
		
		if( initialRecipe == null ) {
			initialRecipe = new Recipe( -1, "", "", "" );
			recipeAccess.insertRecipe( initialRecipe );
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		populateFields();
		addTextWatchers();
		findViewById( R.id.recipeDisplay ).requestFocus();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		removeTextWatchers();
	}
	
	public void addIng( View v ) {
		if( initialRecipe.getID() == -1 ) {
			tempSave();
		}
		
		Intent ingredientIntent = new Intent( this, IngredientAddition.class );
		ingredientIntent.putExtra( RecipeListFragment.RECIPE_IDENTIFIER, initialRecipe.getID() );
        startActivity( ingredientIntent );
	}
	
	private void addTextWatchers() {		
		viewHolder.title.addTextChangedListener( watcher );
		viewHolder.description.addTextChangedListener( watcher );
		viewHolder.directions.addTextChangedListener( watcher );
	}
	
	public void deleteIng( View v ) {		
		builder = new AlertDialog.Builder( this );
        builder.setMessage( "Are you sure you want to delete these ingredients?" );
        builder.setPositiveButton( "Confirm", new DialogInterface.OnClickListener() {
	       	public void onClick( DialogInterface dialog, int id ) {
	       		Toast.makeText( builder.getContext(), "Deleting ingredients...", Toast.LENGTH_SHORT ).show();
	       		
	       		AccessRecipes recipeAccess = new AccessRecipes();
	    		Recipe recipe = getInitialRecipe();
	    		int len = viewHolder.ingredients.getCount();
	    		SparseBooleanArray checked = viewHolder.ingredients.getCheckedItemPositions();
	    		IngredientAdapter thisIngs = ( IngredientAdapter )viewHolder.ingredients.getAdapter();
	
    			for ( int i = 0; i < len; i++ ) {
    				if ( checked.get( i ) ) {
    					recipe.removeIngredient( thisIngs.getItem( i ).getName() );
    				}
    			}
    			
    			viewHolder.ingredients.setAdapter( new IngredientAdapter( builder.getContext(),recipe.getIngredients() ) );
    			viewHolder.ingredients.setEmptyView( ( TextView )findViewById( R.id.recipeDisplay_emptyIngredientList ) );
    			recipeAccess.updateRecipe( recipe );
       	    }
        });
        builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int id ) {}
        });
        builder.show();
        viewHolder.conversionView.setVisibility(View.GONE);
	}
	
	@Override
	public void finish() {
		if( initialRecipe.getID() == -1 ) {
			recipeAccess.deleteRecipe( initialRecipe );
		}
		
		super.finish();
	}
	
	private void populateFields() {
		if( initialRecipe.getID() == -1 ) {
			initialRecipe = recipeAccess.getRecipe( -1 );
		} else {
			initialRecipe = recipeAccess.getRecipe( getInitialRecipe().getID() );
		}
		
		Recipe recipe = initialRecipe;
		
		if(  recipe != null ) {
			viewHolder.title.setText( recipe.getTitle() );
			viewHolder.description.setText( recipe.getDescription() );
			viewHolder.directions.setText( recipe.getDirections() );
			adapter = new IngredientAdapter( this,recipe.getIngredients() );
			viewHolder.ingredients.setEmptyView( ( TextView )findViewById( R.id.recipeDisplay_emptyIngredientList ) );
			viewHolder.ingredients.setChoiceMode( AbsListView.CHOICE_MODE_MULTIPLE );
			viewHolder.ingredients.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                    adapter.toggleCheck( position );
                    updateConvertText( position );
                }
            });
			viewHolder.ingredients.setAdapter( adapter );
		}
	}
	
	public void removeTextWatchers() {
		viewHolder.title.removeTextChangedListener( watcher );
		viewHolder.description.removeTextChangedListener( watcher );
		viewHolder.directions.removeTextChangedListener( watcher );
	}
	
	public void saveClick( View v ) {
		viewHolder.saveButton.setVisibility( View.INVISIBLE );
		
		AccessIngredients ingredientAccess = new AccessIngredients();
		ArrayList<Constituent> constituentList = new ArrayList<Constituent>();
		Constituent constituent;
		Ingredient tempIngredient;
		Ingredient ingredient;
		String title;
		String description;
		String directions;
		String warningResult = "";
		String ingredientName;
		Recipe recipe = initialRecipe;
		int constituentAmount = -1;
		
		title = viewHolder.title.getText().toString();
		description = viewHolder.description.getText().toString();
		directions = viewHolder.directions.getText().toString();
		
		if( title == null || title.isEmpty() ) {
			warningResult += "Please enter a title\n";
		}
		
		if( warningResult.isEmpty() ) {
			
			if( recipe != null ) {
				constituentList.addAll( recipe.getIngredients() );
				constituentAmount = constituentList.size();
			}
			
			
			for( int i = 0; i < constituentAmount; i++ ) {
				constituent = constituentList.get( i );
				tempIngredient = constituent.getIngredient();
				ingredientName = tempIngredient.getName();
				ingredient = ingredientAccess.getIngredient( ingredientName );
				
				if( ingredient == null ) {
					ingredient = new Ingredient( ingredientName );
					ArrayList<Allergy> tempAllergies = tempIngredient.getAllergies();
					ArrayList<LifeStyle> tempLifeStyles = tempIngredient.getLifeStyles();
					
					for( int j = 0; j < tempAllergies.size(); j++ ) {
						ingredient.addAllergy( tempAllergies.get( j ) );
					}
					
					for( int j = 0; j < tempLifeStyles.size(); j++ ) {
						ingredient.addLifeStyle( tempLifeStyles.get( j ) );
					}
					
					ingredientAccess.insertIngredient( ingredient );
				}
				
				ingredientAccess.updateIngredient( ingredient );
			}
			
			
		
			if( recipe.getID() == -1 ) {
				recipe = new Recipe( recipeAccess.getId(), title, directions, description, constituentList );
				recipeAccess.insertRecipe( recipe );
			} else {
				recipe.setTitle( title );
				recipe.setDescription( description );
				recipe.setDirections( directions );
				recipe.clearIngredients();
				
				for( int j = 0; j < constituentAmount; j++ ) {
					recipe.addIngredient( constituentList.get( j ) );
				}
				
				recipeAccess.updateRecipe( recipe );
			}
			
			finish();
		} else {
			Messages.warning( this, warningResult );
		}
	}
	
	private void tempSave() {
		String title;
		String description; 
		String directions;
		
		title = viewHolder.title.getText().toString();
		description = viewHolder.description.getText().toString();
		directions = viewHolder.directions.getText().toString();
		
		Recipe tempRecipe = new Recipe( -1, title, description, directions, initialRecipe.getIngredients() );
		recipeAccess.updateRecipe( tempRecipe );
	}
	
	private void updateConvertText(int position) {
		if( adapter.isMostRecent( position ) ) {
			String originalUnit = initialRecipe.getIngredients().get( position ).getUnit();
			String[] units = UnitConversions.getValidUnitsFor( originalUnit );
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>( this, R.layout.custom_spinner_item, units );
			viewHolder.convertToSpinner.setAdapter( spinnerAdapter );
			
			for( int i = 0; i < units.length; i++ ) {
				if( units[i].equals( originalUnit ) ) {
					viewHolder.convertToSpinner.setSelection( i );
				}
			}
			
			viewHolder.conversionView.setVisibility( View.VISIBLE );
			viewHolder.convertFromUnitText.setText(" " + originalUnit + " ");
		} else {
			viewHolder.conversionView.setVisibility( View.GONE );
		}
	}
	
	private void prepareSpinner() {
		String[] units = UnitConversions.UNITS;
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>( this, R.layout.custom_spinner_item, units );
		viewHolder.convertToSpinner.setAdapter( spinnerAdapter );
		viewHolder.convertToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				adapter.setConversionSetting(adapter.getMostRecentCheck(), selected);
				adapter.notifyDataSetChanged();
			}

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        
		    }
		});
	}
	
	private void prepareViewHolder() {
		viewHolder = new ViewHolder();
		viewHolder.title = ( EditText )findViewById( R.id.recipeDisplay_title );
		viewHolder.description = ( EditText )findViewById( R.id.recipeDisplay_description );
		viewHolder.directions = ( EditText )findViewById( R.id.recipeDisplay_directions );
		viewHolder.ingredients = ( ListView )findViewById( R.id.recipeDisplay_ingredientList );
		viewHolder.saveButton = ( Button )findViewById( R.id.recipeDisplay_save );		
		viewHolder.convertFromUnitText = ( TextView )findViewById( R.id.recipeDisplay_convertTextOriginalUnit );
		viewHolder.convertToSpinner = ( Spinner )findViewById( R.id.recipeDisplay_convertToSpinner );
		viewHolder.conversionView = ( LinearLayout )findViewById( R.id.recipeDisplay_conversionView );
	}
	
	private class ViewHolder {
		public ListView ingredients;
		public EditText title;
		public EditText description;
		public EditText directions;
		public Button saveButton;
		public TextView convertFromUnitText;
		public Spinner convertToSpinner;
		public LinearLayout conversionView;
	}
}
