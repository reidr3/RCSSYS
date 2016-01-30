package comp3350.rcssys.presentation;

import java.util.ArrayList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import comp3350.rcssys.R;
import comp3350.rcssys.business.AccessIngredients;
import comp3350.rcssys.business.AccessRecipes;
import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.Recipe;
import comp3350.rcssys.presentation.views.EditableIngredient;

public class EditRecipe extends RecipeActivity {
	
	private static final String NEW_RECIPE_TITLE = "New Recipe";
	
	private ViewHolder viewHolder;
	private ArrayList<EditableIngredient> editableIngredientList;
	private int count;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_edit_recipe );
		editableIngredientList = new ArrayList<EditableIngredient>();
		count = 0;
		prepareViewHolder();
		populateFields();
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.edit_recipe, menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		int id = item.getItemId();
		if ( id == R.id.editRecipe_menu_save ) {
			saveRecipe();
		}
		return super.onOptionsItemSelected( item );
	}
	
	public void addIngredientClick( View view ){
		addEditableIngredient( null );
	}
	
	public void clickRemoveEditableIngredient( View v ) {
		Object tag = v.getTag();
		if( tag instanceof Integer ){
			int clickedId = ( Integer ) tag;
			for( EditableIngredient ing: editableIngredientList ){
				if( ing.getTag()instanceof Integer && ( Integer ) ing.getTag() == clickedId ){
					( ( ViewGroup ) ing.getParent() ).removeView( ing );
					editableIngredientList.remove( ing );
					break;
				}
			}
		}
	}
	
	public void clickTitle( View view ){
		if( !getTitle().equals( NEW_RECIPE_TITLE ) ){
			Toast.makeText( this, "Can't edit recipe titles", Toast.LENGTH_SHORT ).show();
		}
	}
	
	private void addEditableIngredient( Constituent cons ){
		EditableIngredient ei;
		if( cons != null ) {
			ei = new EditableIngredient( this,cons.getName(),cons.getAmount(),cons.getUnit() );
		}else {
			ei = new EditableIngredient( this );
		}
		ei.setTag( count );
		editableIngredientList.add( ei );
		viewHolder.ingredientLayout.addView( ei );
		count++;
	}
	
	private void populateFields() {
		Recipe recipe = getInitialRecipe();
		
		if( recipe!=null ) {
			String title = recipe.getTitle();
			viewHolder.title.setText( title );
			viewHolder.description.setText( recipe.getDescription() );
			viewHolder.directions.setText( recipe.getDirections() );
			for( Constituent cons: recipe ){
				addEditableIngredient( cons );
			}
			viewHolder.title.setFocusable( false );
		}else {
			setTitle(NEW_RECIPE_TITLE);
			viewHolder.title.setFocusable( true );
		}
	}
	
	private void prepareViewHolder() {
		viewHolder = new ViewHolder();
		viewHolder.ingredientLayout = ( LinearLayout )findViewById( R.id.editRecipe_layout_ingredientlistYOYO );
		viewHolder.title = ( EditText )findViewById( R.id.editRecipe_edittext_title );
		viewHolder.description = ( EditText )findViewById( R.id.editRecipe_edittext_description );
		viewHolder.directions = ( EditText )findViewById( R.id.editRecipe_edittext_directions );
	}
	
	private void saveRecipe(){
		AccessRecipes recipeAccess = new AccessRecipes();
		AccessIngredients ingredientAccess = new AccessIngredients();
		ArrayList<Constituent> constituentList = new ArrayList<Constituent>();
		Constituent constituent;
		Ingredient ingredient;
		String title, description, directions;
		String warningResult = "";
		String ingredientWarning;
		Recipe recipe = getInitialRecipe();
		title = viewHolder.title.getText().toString();
		description = viewHolder.description.getText().toString();
		directions = viewHolder.directions.getText().toString();
		
		if( title == null || title.isEmpty() ) {
			warningResult += "Please enter a title\n";
		}
		
		ingredientWarning = validateIngredients();
		warningResult += ingredientWarning;
		for( EditableIngredient ie : editableIngredientList ){
			if( ingredientWarning.isEmpty() ){
				ingredient = ingredientAccess.getIngredient( ie.getName() );
				if( ingredient == null ){
					ingredient = new Ingredient( ie.getName() );
					ingredientAccess.insertIngredient( ingredient );
				}
				constituent = new Constituent( ingredient,( double )ie.getQuantity(),ie.getUnit() );
				constituentList.add( constituent );
			}
		}
		
		if( warningResult.isEmpty() ){
			if( recipe == null ){
				recipe = new Recipe( recipeAccess.getId(), title, directions, description, constituentList );
				recipeAccess.insertRecipe( recipe );
			}else{
				recipe.setTitle( title );
				recipe.setDescription( description );
				recipe.setDirections( directions );
				recipe.clearIngredients();
				
				for( Constituent cons : constituentList ) {
					recipe.addIngredient( cons );
				}
				
				recipeAccess.updateRecipe( recipe );
			}
			
			finish();
		}else{
			Messages.warning( this, warningResult );
		}
	}
	
	private String validateIngredients(){
		String warningMessage = "";
		String ingTitle;
		double ingQty;
		boolean ingTitleFlag = false;
		boolean ingQtyFlag = false;
		
		for( EditableIngredient ie: editableIngredientList ) {
			ingTitle = ie.getName();
			ingQty = ie.getQuantity();
			if( ingTitle == null || ingTitle.isEmpty() && !ingTitleFlag ){
				warningMessage += "An ingredient is missing a name\n";
				ingTitleFlag = true;
			}
			if( ingQty == EditableIngredient.UNSPECIFIED_QTY && !ingQtyFlag ){
				warningMessage+= "An ingredient is missing a quantity\n";
				ingQtyFlag = true;
			}
		}
		return warningMessage;
	}
	
	private class ViewHolder {
		protected LinearLayout ingredientLayout;
		protected EditText title;
		protected EditText description;
		protected EditText directions;
	}
}
