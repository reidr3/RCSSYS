package comp3350.tests.integration;

import java.util.ArrayList;

import junit.framework.TestCase;
import comp3350.rcssys.application.Services;
import comp3350.rcssys.application.Main;
import comp3350.rcssys.business.AccessIngredients;
import comp3350.rcssys.business.AccessRecipes;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.Recipe;

public class BusinessPersistenceSeamTest extends TestCase {
	private final int ANOTHERRECIPEID = 0;
	private final int MORERECIPESID = 1;
	private final int UNUSEDID = -2;
	
	public BusinessPersistenceSeamTest( String arg0 ) {
		super( arg0 );
	}

	public void testAccessRecipes()	{
		AccessRecipes ar;
		Recipe recipe;
		String result;

		Services.closeDataAccess();

		System.out.println( "\nStarting Integration test of AccessRecipes to persistence" );

		Services.createDataAccess( Main.dbName );

		ar = new AccessRecipes();

		recipe = ar.getRecipe( ANOTHERRECIPEID );
		assertTrue( "Another recipe!".equals( recipe.getTitle() ) );

		result = ar.deleteRecipe( recipe );
		assertNull( result );
		assertNull( ar.getRecipe( ANOTHERRECIPEID ) );
		
		result = ar.insertRecipe( recipe );
		assertNull( result );
		recipe = ar.getRecipe( ANOTHERRECIPEID );
		assertTrue( "Another recipe!".equals( recipe.getTitle() ) );
		
		recipe = ar.getRecipe( UNUSEDID );
		assertNull( recipe );

		recipe = ar.searchForRecipe( "Super Ham Sandwhich #2" ).get( 0 );
		assertEquals( "Super Ham Sandwhich #2", recipe.getTitle() );
		
		assertEquals( 0, ar.searchForRecipe( "Still not here" ).size() );
		
		recipe = ar.getRecipe( MORERECIPESID );
		recipe.setDescription( "Swiping produces neat sentences" );
		ar.updateRecipe( recipe );
		assertEquals( recipe, ar.getRecipe( MORERECIPESID ) );
		
		recipe.setDescription( "Tastes yummy" );
		ar.updateRecipe( recipe );
		
		assertNull( ar.getRecipes( new ArrayList<Recipe>() ) );
		// Need to add some more tests to exercise all CRUD operations
		
		Services.closeDataAccess();

		System.out.println( "Finished Integration test of AccessRecipes to persistence" );
	}
	
	public void testAccessIngredients() {
		AccessIngredients ai;
		String result;
		Ingredient ingredient;
		Ingredient testIngredient;
		
		Services.closeDataAccess();

		System.out.println( "\nStarting Integration test of AccessIngredients to persistence" );
		
		Services.createDataAccess( Main.dbName );
		
		ai = new AccessIngredients();
		testIngredient = new Ingredient( "Not Ham" );
		result = ai.insertIngredient( testIngredient );
		assertNull( result );
		
		assertNull( ai.getIngredients( new ArrayList<Ingredient>() ) );
		
		ingredient = ai.getIngredient( "Not Ham" );
		assertEquals( "Not Ham", ingredient.getName() );
		
		result = ai.deleteIngredient( testIngredient );
		assertNull( result );
		
		result = ai.insertIngredient( testIngredient );
		assertNull( result );
		ingredient = ai.getIngredient( "Not Ham" );
		assertEquals( "Not Ham", ingredient.getName() );
		
		ingredient = ai.getIngredient( "Not real" );
		assertNull( ingredient );
		
		assertEquals( "Not Ham", ai.searchForIngredient( "Not Ham" ).get( 0 ).getName() );
		
		assertEquals( 0, ai.searchForIngredient( "Not realer" ).size() );
		
		result = ai.deleteIngredient( testIngredient );
		assertNull( result );
		
		Services.closeDataAccess();
		
		System.out.println( "Finished Integration test of AccessIngredients to persistence" );
		
	}
}