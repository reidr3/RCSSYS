package comp3350.rcssys.presentation;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import comp3350.rcssys.R;
import comp3350.rcssys.business.AccessIngredients;
import comp3350.rcssys.business.AccessRecipes;
import comp3350.rcssys.business.UnitConversions;
import comp3350.rcssys.objects.Allergy;
import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.LifeStyle;
import comp3350.rcssys.objects.Recipe;
import comp3350.rcssys.presentation.ArrayAdapters.TagAdapter;
import java.util.ArrayList;

public class IngredientAddition extends RecipeActivity {
	private ArrayList<String> allergies;
	private ArrayList<String> lifeStyles;
	private TagAdapter tagList;
	private AccessIngredients ingredientAccess;
	private ViewHolder viewHolder;
	private OnFocusChangeListener focusChangeListener;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_ingredient_addition );
		ingredientAccess = new AccessIngredients();
		lifeStyles = new ArrayList<String>();
		allergies = new ArrayList<String>();
		prepareViewHolder();
		
		focusChangeListener = new OnFocusChangeListener() {
			@Override
			public void onFocusChange( View v, boolean hasFocus ) {
				Ingredient tempIngredient;
				ArrayList<Allergy> tempAllergies;
				ArrayList<LifeStyle> tempLifeStyles;
				
				if( !hasFocus ) {
					tempIngredient = ingredientAccess.getIngredient( viewHolder.name.getText().toString() );
					
					if( tempIngredient != null ) {
						tempAllergies = tempIngredient.getAllergies();
						
						for( int i = 0; i < tempAllergies.size(); i++ ) {
							if( !allergies.contains( tempAllergies.get( i ).getName() ) ) {
								allergies.add( tempAllergies.get(i).getName() );
							}
						}
						
						tempLifeStyles = tempIngredient.getLifeStyles();
						
						for( int i = 0; i < tempLifeStyles.size(); i++ ) {
							if( !lifeStyles.contains( tempLifeStyles.get( i ).getName() ) ) {
								lifeStyles.add( tempLifeStyles.get( i ).getName() );
							}
						}
					}
					
					refreshTags();
				}
			}
		};
		
		setListeners();
		populateFields();
		
	}
	
	public void deleteTag( View v ) {
		if( ( ( View )( ( View )v.getParent() ).getParent() ).getId() == R.id.ingredientAddition_allergyList ) {
			RelativeLayout tag = ( RelativeLayout )v.getParent();
			View child;
			TextView tagNameView;
			
			for( int i = 0; i < tag.getChildCount(); i++ ) {
				child = tag.getChildAt(i);
				
				if( child instanceof TextView ) {
					tagNameView = ( TextView )child;
					allergies.remove( tagNameView.getText().toString() );
				}
			}
			
			refreshTags();
		} else if( ( ( View )( ( View )v.getParent() ).getParent() ).getId() == R.id.ingredientAddition_lifeStyleList ) {
			RelativeLayout tag = ( RelativeLayout )v.getParent();
			View child;
			TextView tagNameView;
			
			for( int i = 0; i < tag.getChildCount(); i++ ) {
				child = tag.getChildAt(i);
				
				if( child instanceof TextView ) {
					tagNameView = ( TextView )child;
					lifeStyles.remove( tagNameView.getText().toString() );
				}
			}
			
			refreshTags();
		}
	}
	
	public void saveAllergy( View v ) {
		String allergy = viewHolder.newAllergy.getText().toString();
		
		if( allergy != null ) {
			if( !allergies.contains( allergy ) ) {
				allergies.add( allergy );
				refreshTags();
			}
		}
		
		viewHolder.newAllergy.setText( "" );
	}
	
	public void saveIngredient( View v ){
		AccessRecipes recipeAccess = new AccessRecipes();
		Constituent constituent = null;
		Ingredient ingredient;
		String warningResult = "";
		String ingredientWarning;
		String recipeValidation;
		Recipe recipe;
		
		ingredientWarning = validateIngredient();
		recipeValidation = validateRecipe();
		warningResult += ingredientWarning;
		warningResult += recipeValidation;
		
		if( ingredientWarning.isEmpty() ){
			String ingName = viewHolder.name.getText().toString();
			Double ingAmt = Double.parseDouble( viewHolder.amount.getText().toString() );
			String ingUnt = viewHolder.unitSpinner.getSelectedItem().toString();
			
			recipe = getInitialRecipe();
			ingredient = new Ingredient( ingName );
			
			for( int i = 0; i < allergies.size(); i++ ) {
				ingredient.addAllergy( new Allergy( allergies.get( i ) ) );
			}
			
			for( int i = 0; i < lifeStyles.size(); i++ ) {
				ingredient.addLifeStyle( new LifeStyle( lifeStyles.get( i ) ) );
			}
			
			if( ingredientAccess.getIngredient( ingName ) == null ) {				
				ingredientAccess.insertIngredient( ingredient );
			} else {
				ingredientAccess.updateIngredient( ingredient );
			}
			
			constituent = new Constituent( ingredient, ingAmt, ingUnt );
			recipe.addIngredient( constituent );
			recipeAccess.updateRecipe( recipe );
			finish();
		} else {
			Messages.warning( this, warningResult );
		}
	}
	
	public void saveLifeStyle( View v ) {
		String lifeStyle = viewHolder.newLifeStyle.getText().toString();
		
		if( lifeStyle != null ) {
			if( !lifeStyles.contains( lifeStyle ) ) {
				lifeStyles.add( lifeStyle );
				refreshTags();
			}
		}
		
		viewHolder.newLifeStyle.setText( "" );
	}
	
	public void setListeners() {
		viewHolder.name.setOnFocusChangeListener( focusChangeListener );
	}
	
	public String validateRecipe() {
		String returnMsg = "Error: initial recipe not found";
		
		if( getInitialRecipe() != null ) {
			returnMsg = "";
		}
		
		return returnMsg;
	}
	
	private void prepareViewHolder(){
		viewHolder = new ViewHolder();
		
		viewHolder.name = ( EditText )findViewById( R.id.ingredientAddition_name );
		viewHolder.amount = ( EditText )findViewById( R.id.ingredientAddition_amount );
		viewHolder.unitSpinner = ( Spinner )findViewById( R.id.ingredientAddition_unit );
		viewHolder.allergies = ( ListView )findViewById( R.id.ingredientAddition_allergyList );
		viewHolder.lifeStyles = ( ListView )findViewById( R.id.ingredientAddition_lifeStyleList );
		viewHolder.newAllergy = ( EditText )findViewById( R.id.ingredientAddition_newAllergy );
		viewHolder.newLifeStyle = ( EditText )findViewById( R.id.ingredientAddition_newLS );
		
		String[] units = UnitConversions.UNITS;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, R.layout.custom_spinner_item, units );
		viewHolder.unitSpinner.setAdapter( adapter );
	}
	
	private void populateFields(){
		viewHolder.unitSpinner.setSelection( 0 );
		refreshTags();
	}
	
	private void refreshTags() {
		tagList = new TagAdapter( this, allergies);
		viewHolder.allergies.setAdapter( tagList );
		
		tagList = new TagAdapter( this, lifeStyles);
		viewHolder.lifeStyles.setAdapter( tagList );
	}
	
	private String validateIngredient(){
		String warningMessage = "";
		String ingTitle;
		
		if( viewHolder.name != null && viewHolder.name.getText() != null ) {
			ingTitle = viewHolder.name.getText().toString();
			
			if( ingTitle == null || ingTitle.equals( "" ) ){
				warningMessage += "An ingredient is missing a name\n";
			}
		}
		
		if( viewHolder.amount != null && viewHolder.amount.getText() != null ) {			
			if( viewHolder.amount.getText().toString().equals( "" ) ){
				warningMessage += "An ingredient is missing a quantity\n";
			}
		}
		
		return warningMessage;
	}
	
	private class ViewHolder {
		public EditText name;
		public EditText amount;
		public ListView allergies;
		public ListView lifeStyles;
		public EditText newAllergy;
		public EditText newLifeStyle;
		public Spinner unitSpinner;
	}
}
