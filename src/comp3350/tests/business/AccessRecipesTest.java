package comp3350.tests.business;

import java.util.ArrayList;

import comp3350.rcssys.application.Services;
import comp3350.rcssys.business.AccessRecipes;
import comp3350.rcssys.objects.Recipe;
import comp3350.tests.persistence.DataAccessStub;
import junit.framework.TestCase;

public class AccessRecipesTest extends TestCase {
	
	private AccessRecipes recipeAccess;
	private ArrayList<Recipe> recipes;
	private Recipe recipe;
	private String result;
	private final int UNUSEDID = -2;
	private final int NEWID = -3;
	private final int SUPERHAMSANDWICHID = 2;
	
	public AccessRecipesTest( String arg0 ) {
		super( arg0 );
	}
	
	@Override
	public void setUp() {
		System.out.println( "Start " + this.getName() + "..." );
		Services.createDataAccess( new DataAccessStub( "stub" ) );
		recipeAccess = new AccessRecipes();
		recipes = new ArrayList<Recipe>();
	}
	
	@Override
	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
		Services.closeDataAccess();
	}
	
	public void testNull() {
		recipe = recipeAccess.getRecipe( UNUSEDID );
		assertNull( recipe );
		
		recipes = recipeAccess.searchForRecipe( null );
		assertEquals( 0, recipes.size() );
		
		result = recipeAccess.getRecipes( recipes );
		assertEquals( 4, recipes.size() );
		result = recipeAccess.insertRecipe( null );
		assertNull( result );
		result = recipeAccess.getRecipes( recipes );
		assertEquals( 4, recipes.size() );
		
		result = recipeAccess.deleteRecipe( null );
		result = recipeAccess.getRecipes( recipes );
		assertEquals( 4, recipes.size() );
		
		result = recipeAccess.updateRecipe( null );
		assertNull( result );
	}
	
	public void testBad() {
		recipe = recipeAccess.getRecipe( UNUSEDID );
		assertNull( recipe );
		
		recipes = recipeAccess.searchForRecipe( "No recipes with this in their name" );
		assertEquals( 0, recipes.size() );
		
		result = recipeAccess.getRecipes( recipes );
		assertEquals( 4, recipes.size() );
		recipe = recipeAccess.getRecipe( SUPERHAMSANDWICHID );
		assertEquals( recipe.getTitle(), "Super Ham Sandwhich" );
		result = recipeAccess.insertRecipe( recipe );
		assertNull( result );
		result = recipeAccess.getRecipes( recipes );
		assertEquals( 4, recipes.size() );
		
		recipe = new Recipe ( UNUSEDID, "This isn't in there" );
		result = recipeAccess.deleteRecipe( recipe );
		result = recipeAccess.getRecipes( recipes );
		assertEquals( 4, recipes.size() );
		
		result = recipeAccess.updateRecipe( recipe );
		assertNull( result );
	}
	
	public void testReal() {
		recipe = recipeAccess.getRecipe( SUPERHAMSANDWICHID );
		assertEquals( recipe.getTitle(), "Super Ham Sandwhich" );
		
		recipes = recipeAccess.searchForRecipe( "Super Ham Sandwhich #2" );
		assertEquals( recipes.size(), 1 );
		assertEquals( recipes.get( 0 ).getTitle(), "Super Ham Sandwhich #2" );
		recipes = recipeAccess.searchForRecipe( "e" );
		assertEquals( recipes.size(), 4 );
		
		result = recipeAccess.getRecipes( recipes );
		assertEquals( recipes.size(), 4 );
		
		recipe = new Recipe( NEWID, "Lets add this" );
		result = recipeAccess.insertRecipe( recipe );
		result = recipeAccess.getRecipes( recipes );
		assertEquals( recipes.size(), 5 );
		assertEquals( recipes.get( 4 ).getTitle(), "Lets add this" );
		
		result = recipeAccess.deleteRecipe( recipe );
		result = recipeAccess.getRecipes( recipes );
		assertEquals( recipes.size(), 4 );
		
		result = recipeAccess.insertRecipe( recipe );
		result = recipeAccess.getRecipes( recipes );
		assertEquals( recipes.size(), 5 );
		
		recipe.setTitle( "Does this work?" );
		result = recipeAccess.updateRecipe( recipe );
		result = recipeAccess.getRecipes( recipes );
		assertEquals( recipes.get( 4 ).getTitle(), "Does this work?" );
	}
}
