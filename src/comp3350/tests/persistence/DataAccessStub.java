package comp3350.tests.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.rcssys.application.Main;
import comp3350.rcssys.objects.Allergy;
import comp3350.rcssys.objects.LifeStyle;
import comp3350.rcssys.objects.Recipe;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.persistence.DataAccess;

public class DataAccessStub implements DataAccess {
	private String dbName;
	private String dbType = "stub";

	private ArrayList<Recipe> recipes;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<Allergy> allergies;
	private ArrayList<LifeStyle> lifeStyles;

	public DataAccessStub( String dbName ) {
		this.dbName = dbName;
	}

	public DataAccessStub() {
		this( Main.dbName );
	}

	public void open( String dbName ) {
		Recipe thisRecipe;
		ArrayList<Constituent> constituents;
		ArrayList<Constituent> tempCons;
		Constituent thisConstituent = null;
		Ingredient thisIngredient = null;
		LifeStyle thisLifeStyle = null;
		Allergy thisAllergy = null;

		constituents = new ArrayList<Constituent>();
		ingredients = new ArrayList<Ingredient>();
		allergies = new ArrayList<Allergy>();
		lifeStyles = new ArrayList<LifeStyle>();
		
		thisAllergy = new Allergy( "Peanuts" );
		allergies.add( thisAllergy );
		thisAllergy = new Allergy( "Milk" );
		allergies.add( thisAllergy );		
		thisAllergy = new Allergy( "Gluten" );
		allergies.add( thisAllergy );	
		thisAllergy = new Allergy( "Lactose" );
		allergies.add ( thisAllergy );
		
		thisLifeStyle = new LifeStyle( "Vegan" );
		lifeStyles.add ( thisLifeStyle );	
		thisLifeStyle = new LifeStyle( "Vegatarian" );
		lifeStyles.add ( thisLifeStyle );	
		thisLifeStyle = new LifeStyle( "Gluten Free" );
		lifeStyles.add( thisLifeStyle );	
		thisLifeStyle = new LifeStyle( "Super Ham Sandwhich's only" );
		lifeStyles.add( thisLifeStyle );
		
		thisIngredient = new Ingredient( "Another thing" );
		ingredients.add( thisIngredient );
		thisConstituent = new Constituent( thisIngredient, 5.0, "kg" );
		constituents.add( thisConstituent );
		
		thisIngredient = new Ingredient( "Ham" );
		ingredients.add( thisIngredient );
		thisIngredient.addAllergy( allergies.get( 0 ) );
		thisIngredient.addLifeStyle( lifeStyles.get( 0 ) );
		thisConstituent = new Constituent( thisIngredient, 1.0, "tsp" );
		constituents.add( thisConstituent );
		
		thisIngredient = new Ingredient( "Mayo" );
		thisIngredient.addAllergy( allergies.get( 0 ) );
		thisIngredient.addAllergy( allergies.get( 1 ) );
		thisIngredient.addAllergy( allergies.get( 2 ) );
		ingredients.add( thisIngredient );
		thisConstituent = new Constituent( thisIngredient, 2.5, "kg" );
		constituents.add( thisConstituent );
		
		thisIngredient = new Ingredient( "Someother thing" );
		thisIngredient.addLifeStyle( lifeStyles.get( 0 ) );
		thisIngredient.addLifeStyle( lifeStyles.get( 1 ) );
		thisIngredient.addLifeStyle( lifeStyles.get( 2 ) );
		ingredients.add( thisIngredient );
		thisConstituent = new Constituent( thisIngredient, 1.0, "tbsp" );
		constituents.add( thisConstituent );


		recipes = new ArrayList<Recipe>();
		tempCons = new ArrayList<Constituent>();
		tempCons.add( constituents.get( 0 ) );
		tempCons.add( constituents.get( 1 ) );
		thisRecipe = new Recipe( 0, "Another recipe!", "Do stuff", "Tastes yummy", tempCons );
		recipes.add( thisRecipe );
		tempCons.clear();
		thisRecipe = new Recipe( 1, "More recipes!", "Do stuff", "Tastes yummy", null );
		recipes.add( thisRecipe );
		thisRecipe = new Recipe( 2, "Super Ham Sandwhich", null, null, constituents );
		recipes.add( thisRecipe );
		tempCons.clear();
		tempCons.add( constituents.get( 3 ) );
		thisRecipe = new Recipe( 3, "Super Ham Sandwhich #2", "Do stuff", "Tastes good", tempCons );
		recipes.add( thisRecipe );
		
		System.out.println( "Opened " + dbType +" database " + dbName );
	}

	public void close() {	
		System.out.println( "Closed " + dbType +" database " + dbName );
	}

	public String getRecipeSequential( List<Recipe> recipesResult ) {
		recipesResult.clear();
        recipesResult.addAll( recipes );
        
		return null;
	}

	public ArrayList<Recipe> getRecipe( Recipe currentRecipe ) {
		ArrayList<Recipe> newRecipes;
		int index;
		
		newRecipes = new ArrayList<Recipe>();
		index = recipes.indexOf( currentRecipe );
		
		if ( index >= 0 ) {
			newRecipes.add( recipes.get( index ) );
		}
		
		return newRecipes;
	}

	public String insertRecipe( Recipe currentRecipe ) {
		if(currentRecipe != null ) {
			recipes.add( currentRecipe );
		}
		
		return null;
	}

	public String updateRecipe( Recipe currentRecipe ) {
		int index;
		index = recipes.indexOf( currentRecipe );
		
		if ( index >= 0 ) {
			recipes.set( index, currentRecipe );
		}
		
		return null;
	}

	public String deleteRecipe( Recipe currentRecipe ) {
		int index;
		index = recipes.indexOf( currentRecipe );
		
		if ( index >= 0 ) {
			recipes.remove( index );
		}
		
		return null;
	}
	
	public String getIngredientSequential( List<Ingredient> ingredientResult ) {
		ingredientResult.clear();
		ingredientResult.addAll( ingredients );
	
		return null;
	}
	
	public ArrayList<Ingredient> getIngredient( Ingredient currentIngredient ) {
		ArrayList<Ingredient> newIngredients;
		int index;
	
		newIngredients = new ArrayList<Ingredient>();
		index = ingredients.indexOf( currentIngredient );
	
		if ( index >= 0 ) {
			newIngredients.add( ingredients.get( index ) );
		}
	
		return newIngredients;
	}
	
	public String insertIngredient( Ingredient currentIngredient ) {
		if( currentIngredient != null ) {
			ingredients.add( currentIngredient );
		}
	
		return null;
	}
	
	public String updateIngredient( Ingredient currentIngredient ) {
		int index;
		index = ingredients.indexOf( currentIngredient );
	
		if ( index >= 0 ) {
			ingredients.set( index, currentIngredient );
		}
	
		return null;
	}
	
	public String deleteIngredient( Ingredient currentIngredient ) {
		int index;
		index = ingredients.indexOf( currentIngredient );
	
		if ( index >= 0 ) {
			ingredients.remove( index );
		}
	
		return null;
	}
}
