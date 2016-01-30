package comp3350.tests.persistence;

import java.util.ArrayList;

import junit.framework.TestCase;
import comp3350.rcssys.application.Main;
import comp3350.rcssys.application.Services;
import comp3350.rcssys.objects.*;
import comp3350.rcssys.persistence.DataAccess;

public class DataAccessTest extends TestCase {
	private static String dbName = Main.dbName;
	private static DataAccess dataAccess;
	private static ArrayList<Recipe> recipes;
	private static Recipe recipe;
	private static ArrayList<Ingredient> ingredients;
	private static Ingredient ingredient;
	private static final int MORERECIPESID = 0;
	private static final int ANOTHERRECIPEID = 1;
	private static final int SUPERHAMSANDWHICHID = 2;
	private static final int SUPERHAMSAN2ID = 3;
	
	public DataAccessTest( String arg0 ) {
		super( arg0 );
	}

	public void testDataAccess() {
		Services.closeDataAccess();

		System.out.println( "\nStarting Persistence test DataAccess (using stub)" );
		
		dataAccess = ( DataAccess )Services.createDataAccess( new DataAccessStub( dbName ) );
		
		dataAccessTest();

		System.out.println( "Finished Persistence test DataAccess (using stub)" );	
	}
	
	public static void dataAccessTest() {
		dataAccess = ( DataAccess )Services.getDataAccess( dbName );
		
		recipeTest();
		ingredientTest();

		Services.closeDataAccess();
	}
	
	private static void recipeTest() {
		ingredients = new ArrayList<Ingredient>();
		
		recipes = new ArrayList<Recipe>();
		dataAccess.getRecipeSequential( recipes );
		assertEquals( 4, recipes.size() );
		
		recipe = recipes.get( 1 );
		recipes.clear();
		recipes.addAll(dataAccess.getRecipe( recipe ) );
		assertEquals( 1, recipes.size() );
		
		recipes.clear();
		recipes.addAll( dataAccess.getRecipe( null ) );
		assertEquals( 0, recipes.size() );
		
		recipes.addAll( dataAccess.getRecipe( new Recipe( -1, "Not in there" ) ) );
		assertEquals( 0, recipes.size() );
		
		dataAccess.insertRecipe( null );
		dataAccess.getRecipeSequential( recipes );
		assertEquals( 4, recipes.size() );
		
		recipe = new Recipe( -2, "Lets add this" );
		dataAccess.insertRecipe( recipe );
		recipes.clear();
		dataAccess.getRecipeSequential( recipes );
		assertEquals( 5, recipes.size() );
		recipes.clear();
		recipes.addAll( dataAccess.getRecipe( recipe ) );
		assertEquals( "Lets add this", recipes.get( 0 ).getTitle() );
		
		dataAccess.deleteRecipe( recipe );
		recipes.clear();
		dataAccess.getRecipeSequential( recipes );
		assertEquals( 4, recipes.size() );
		
		//Suppose to be duplicate, trying to delete recipe that was already deleted
		dataAccess.deleteRecipe( recipe );
		recipes.clear();
		dataAccess.getRecipeSequential( recipes );
		assertEquals( 4, recipes.size() );
		
		dataAccess.deleteRecipe( null );
		assertEquals( 4, recipes.size() );
		
		recipes.clear();
		recipes.addAll( dataAccess.getRecipe( new Recipe( SUPERHAMSANDWHICHID ) ) );
		assertEquals( 4, recipes.get( 0 ).getIngredients().size() );
		
		recipes.clear();
		recipes.addAll( dataAccess.getRecipe( new Recipe( MORERECIPESID ) ) );
		assertEquals( 2, recipes.get( 0 ).getIngredients().size() );
		
		recipes.clear();
		recipes.addAll( dataAccess.getRecipe( new Recipe( ANOTHERRECIPEID ) ) );
		assertEquals( 0, recipes.get( 0 ).getIngredients().size() );
		
		recipes.clear();
		recipes.addAll( dataAccess.getRecipe( new Recipe( SUPERHAMSAN2ID ) ) );
		assertEquals( 1, recipes.get( 0 ).getIngredients().size() );	
		
		recipes.clear();
		recipes.addAll( dataAccess.getRecipe( new Recipe( SUPERHAMSANDWHICHID ) ) );
		assertEquals( 4, recipes.get( 0 ).getIngredients().size() );
		recipe = recipes.get( 0 );
		recipe.setTitle( "Test!" );
		dataAccess.updateRecipe( recipe );
		recipes.addAll( dataAccess.getRecipe( new Recipe( SUPERHAMSANDWHICHID ) ) );
		recipe = recipes.get( 0 );
		assertEquals( 4, recipe.getIngredients().size() );
		
		recipe.setTitle( "Super Ham Sandwhich" );
		dataAccess.updateRecipe( recipe );
		assertEquals( "Super Ham Sandwhich", recipe.getTitle() );
	}
	
	private static void ingredientTest() {
		ingredients = new ArrayList<Ingredient>();
		dataAccess.getIngredientSequential( ingredients );
		assertEquals( 4, ingredients.size() );
		
		ingredients = dataAccess.getIngredient( ingredients.get( 1 ) );
		assertEquals( 1, ingredients.size() );
		
		ingredients = dataAccess.getIngredient( null );
		assertEquals( 0, ingredients.size() );
		
		ingredients = dataAccess.getIngredient( new Ingredient( "Not in there" ) );
		assertEquals( 0, ingredients.size() );
		
		dataAccess.insertIngredient( null );
		dataAccess.getIngredientSequential( ingredients );
		assertEquals( 4, ingredients.size() );
		
		ingredient = new Ingredient( "Lets add this" );
		dataAccess.insertIngredient( ingredient );
		ingredients.clear();
		dataAccess.getIngredientSequential( ingredients );
		assertEquals( 5, ingredients.size() );
		ingredients = dataAccess.getIngredient( ingredient );
		assertEquals( "Lets add this", ingredients.get( 0 ).getName() );
		
		dataAccess.deleteIngredient( ingredient );
		ingredients.clear();
		dataAccess.getIngredientSequential( ingredients );
		assertEquals( 4, ingredients.size() );
		
		//This is suppose to be duplicate, trying to delete a ingredient
		//that is no longer in there
		dataAccess.deleteIngredient( ingredient );
		ingredients.clear();
		dataAccess.getIngredientSequential( ingredients );
		assertEquals( 4, ingredients.size() );
		
		dataAccess.deleteIngredient( null );
		ingredients.clear();
		dataAccess.getIngredientSequential( ingredients );
		assertEquals( 4, ingredients.size() );
		
		ingredients.clear();
		ingredients.addAll( dataAccess.getIngredient ( new Ingredient( "Mayo" ) ) );
		assertEquals( 1, ingredients.size() );
		assertEquals( 3, ingredients.get( 0 ).getAllergies().size() );
		assertEquals( 0, ingredients.get( 0 ).getLifeStyles().size() );
		
		ingredients = dataAccess.getIngredient( new Ingredient( "Someother thing" ) );
		assertEquals( 1, ingredients.size() );
		
		assertEquals( 3, ingredients.get( 0 ).getLifeStyles().size() );
		assertEquals( 0, ingredients.get( 0 ).getAllergies().size() );
		
		ingredients = dataAccess.getIngredient( new Ingredient( "Ham" ) );
		assertEquals( 1, ingredients.size() );
		
		assertEquals( 1, ingredients.get( 0 ).getAllergies().size() );
		assertEquals( 1, ingredients.get( 0 ).getLifeStyles().size() );

		
		ingredients = dataAccess.getIngredient( new Ingredient( "Another thing") );
		assertEquals( 1, ingredients.size() );
		assertEquals( 0, ingredients.get( 0 ).getAllergies().size() );
		assertEquals( 0, ingredients.get( 0 ).getLifeStyles().size() );

		ingredients.get( 0 ).addAllergy( new Allergy( "A" ) );
		dataAccess.updateIngredient( ingredients.get( 0 ) );
		ingredients = dataAccess.getIngredient( ingredients.get( 0 ) );
		assertEquals( 1, ingredients.size() );
		assertEquals( 1, ingredients.get( 0 ).getAllergies().size() );
		assertEquals( 0, ingredients.get( 0 ).getLifeStyles().size() );
		ingredients.get( 0 ).deleteAllergy( new Allergy( "A" ) );
		dataAccess.updateIngredient( ingredients.get( 0 ) );
		ingredients = dataAccess.getIngredient( ingredients.get ( 0 ) );
		assertEquals( 0, ingredients.get( 0 ).getAllergies().size() );
		
		ingredients.get( 0 ).addLifeStyle( new LifeStyle( "A" ) );
		dataAccess.updateIngredient( ingredients.get( 0 ) );
		ingredients = dataAccess.getIngredient( ingredients.get( 0 ) );
		assertEquals( 1, ingredients.size() );
		assertEquals( 0, ingredients.get( 0 ).getAllergies().size() );
		assertEquals( 1, ingredients.get( 0 ).getLifeStyles().size() );
		ingredients.get( 0 ).deleteLifeStyle( new LifeStyle( "A" ) );
		dataAccess.updateIngredient( ingredients.get( 0 ) );
		ingredients = dataAccess.getIngredient( ingredients.get ( 0 ) );
		assertEquals( 0, ingredients.get( 0 ).getLifeStyles().size() );
	}
}