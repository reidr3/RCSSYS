package comp3350.tests.acceptance;

import junit.framework.Assert;

import com.robotium.solo.Solo;

import comp3350.rcssys.presentation.HomeActivity;
import android.test.ActivityInstrumentationTestCase2;

public class RecipeAcceptanceTest extends ActivityInstrumentationTestCase2<HomeActivity> {
	private Solo solo;

	public RecipeAcceptanceTest() {
		super( HomeActivity.class );
	}

	public void setUp() throws Exception {
		solo = new Solo( getInstrumentation(), getActivity() );

		solo.waitForActivity( "HomeActivity" );
		solo.clickOnText( "Recipes" );
		solo.assertCurrentActivity( "Expected activity RecipeListActivity", "RecipeListActivity" );
	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	public void testViewRecipes() {
		Assert.assertTrue( solo.searchText( "Super Ham Sandwhich" ) );
		Assert.assertTrue( solo.searchText( "More Recipes!" ) );
		
		solo.clickOnText( "Super Ham Sandwhich" );
		solo.assertCurrentActivity( "Expected activity RecipeDisplay", "RecipeDisplay" );
		Assert.assertTrue( solo.searchText( "Super Ham Sandwhich" ) );
		Assert.assertTrue( solo.searchText( "Do stuff" ) );
		Assert.assertTrue( solo.searchText( "Tastes good" ) );
		
		solo.goBack();
		
		solo.assertCurrentActivity( "Expected activity RecipeListActivity", "RecipeListActivity" );
		
		solo.clickOnText( "More Recipes!" );
		solo.assertCurrentActivity( "Expected activity RecipeDisplay", "RecipeDisplay" );
		Assert.assertTrue( solo.searchText( "More Recipes!" ) );
		Assert.assertTrue( solo.searchText( "Do stuff" ) );
		
		solo.goBack();
		solo.goBack();
		
		solo.assertCurrentActivity( "Expected activity HomeActivity", "HomeActivity" );
	}
	
	public void testCreateRecipe() {
		solo.clickOnText( "New Recipe" );
		solo.waitForActivity( "EditRecipe" );
		solo.enterText( 0, "CreateTest1" );
		solo.enterText( 1, "Create Test Description 1" );
		solo.enterText( 2, "Create Test Directions 1" );
		solo.clickOnText( "Save" );
		
		solo.waitForActivity( "RecipeListActivity" );
		Assert.assertTrue( solo.searchText( "CreateTest1" ) );
		solo.clickOnText( "CreateTest1" );
		solo.waitForActivity( "RecipeDisplay" );
		Assert.assertTrue( solo.searchText( "Create Test Description 1" ) );
		Assert.assertTrue( solo.searchText( "Create Test Directions 1" ) );
		solo.goBack();
		
		solo.waitForActivity( "RecipeListActivity" );
		solo.clickOnText( "New Recipe" );
		solo.waitForActivity( "EditRecipe" );
		solo.enterText( 0, "Testing create 2" );
		solo.clickOnText( "Save" );

		solo.waitForActivity( "RecipeListActivity" );
		Assert.assertTrue( solo.searchText( "Testing create 2" ) );
	}
	
	public void testDeleteRecipe() {
		Assert.assertTrue( solo.searchText( "CreateTest1" ) );
		solo.clickLongOnText( "CreateTest1" );
		solo.clickOnText( "Delete" );
		Assert.assertFalse( solo.searchText( "CreateTest1" ) );
		
		Assert.assertTrue( solo.searchText( "Testing create 2" ) );
		solo.clickLongOnText( "Testing create 2" );
		solo.clickOnText( "Delete" );
		Assert.assertFalse( solo.searchText( "Testing create 2" ) );
	}
	
	public void testSearchRecipe() {
		assertTrue( solo.searchText( "Search" ) );
		solo.clickOnText( "Search" );
		
		solo.enterText( 0, "Another recipe!" );
		solo.clickOnText( "Search" );
		Assert.assertTrue( solo.searchText( "Another recipe!" ) );
		
		assertTrue( solo.searchText( "Refresh List" ) );
		solo.clickOnText( "Refresh List" );
		assertTrue( solo.searchText( "Super Ham Sandwhich" ) );
		assertTrue( solo.searchText( "Another recipe!" ) );
		
		solo.clickOnText( "Search" );
		solo.clickOnText( "Clear" );
		solo.enterText( 0, "Super Ham Sandwhich" );
		solo.clickOnText( "Search" );
		Assert.assertTrue( solo.searchText( "Super Ham Sandwhich" ) );
		Assert.assertTrue( solo.searchText( "Super Ham Sandwhich #2" ) );

		solo.clickOnText( "Search" );
		solo.clickOnText( "Clear" );
		solo.enterText( 0, "More" );
		solo.clickOnText( "Search" );
		Assert.assertTrue( solo.searchText( "More recipes!" ) );
		
		solo.clickOnText( "Search" );
		solo.clickOnText( "Clear" );
		solo.enterText( 1, "Ham" );
		solo.clickOnText( "Search" );
		Assert.assertTrue( solo.searchText( "Another recipe!" ) );
	}
	
	public void testEditRecipe() {
		Assert.assertTrue( solo.searchText( "Super Ham Sandwhich" ) );
		solo.clickOnText( "Super Ham Sandwhich" );

		solo.waitForActivity( "RecipeDisplay" );
		Assert.assertTrue( solo.searchText( "Super Ham Sandwhich" ));
		Assert.assertTrue( solo.searchText( "Ham" ) );
		Assert.assertTrue( solo.searchText( "1.0" ) );
		Assert.assertTrue( solo.searchText( "tsp" ) );
		
		Assert.assertTrue( solo.searchEditText( "Tastes good" ) );
		Assert.assertTrue( solo.searchEditText( "Do stuff" ) );
		
		solo.clearEditText( 0 );
		solo.enterText( 0, "New Title" );
		solo.clearEditText( 1 );
		solo.enterText( 1, "Something" );
		solo.clearEditText( 2 );
		solo.enterText( 2, "Stuff" );
		solo.clickOnText( "Save" );
		
		Assert.assertTrue( solo.searchText( "New Title" ) );
		solo.clickOnText( "New Title" );
		solo.waitForActivity( "Recipe Display" );
		
		Assert.assertTrue( solo.searchText( "New Title" ));
		Assert.assertTrue( solo.searchText( "Ham" ) );
		Assert.assertTrue( solo.searchText( "1.0" ) );
		Assert.assertTrue( solo.searchText( "tsp" ) );
		
		Assert.assertTrue( solo.searchEditText( "Something" ) );
		Assert.assertTrue( solo.searchEditText( "Stuff" ) );
		
		solo.clearEditText( 0 );
		solo.enterText( 0, "Super Ham Sandwhich" );
		solo.clearEditText( 1 );
		solo.enterText( 1, "Tastes good" );
		solo.clearEditText( 2 );
		solo.enterText( 2, "Do stuff" );
		solo.clickOnText( "Save" );
		
		solo.waitForActivity( "RecipeListDisplay" );
		
		Assert.assertTrue( solo.searchText( "Another recipe!" ) );
		solo.clickOnText( "Another recipe!" );
		solo.waitForActivity( "RecipeDisplay" );
		Assert.assertTrue( solo.searchText( "Another recipe!" ) );
		Assert.assertTrue( solo.searchText( "Another thing" ) );
		
		solo.clickOnButton( 0 );
		solo.assertCurrentActivity( "Expected activity IngredientAddition", "IngredientAddition" );
		solo.enterText( 0, "Pizza" );
		solo.enterText( 1, "7" );
		solo.clickOnText( "kg" );
		solo.clickOnText( "lb" );

		solo.enterText( 2, "Sunlight" );
		solo.clickOnButton( 0 );
		Assert.assertTrue( solo.searchText( "Sunlight" ) );
		solo.clickOnButton( 0 );
		Assert.assertFalse( solo.searchText( "Sunlight" ) );
		solo.enterText( 3, "Basement dweller" );
		solo.clickOnButton( 1 );
		Assert.assertTrue( solo.searchText( "Basement dweller" ) );
		solo.clickOnButton( 0 );
		Assert.assertFalse( solo.searchText( "Basement dweller" ) );

		solo.clickOnText( "Save" );
		
		solo.waitForActivity( "RecipeDisplay" );
		Assert.assertTrue( solo.searchText( "Pizza" ) );
		Assert.assertTrue( solo.searchText( "7" ) );
		Assert.assertTrue( solo.searchText( "lb" ) );
		
		solo.clickOnText( "Pizza" );
		solo.pressSpinnerItem( 0, -1 );
		solo.clickOnText( "Pizza" );
		Assert.assertTrue( solo.searchText( "3176.0" ) );
		Assert.assertTrue( solo.searchText( "g" ) );
		
		solo.clickOnText( "Pizza" );
		solo.clickOnButton( 1 );
		solo.waitForDialogToOpen();
		Assert.assertTrue( solo.searchText( "Confirm" ) );
		solo.clickOnText( "Confirm" );
		solo.waitForDialogToClose();
		
		Assert.assertFalse( solo.searchText( "Pizza" ) );
		Assert.assertFalse( solo.searchText( "7" ) );
		Assert.assertFalse( solo.searchText( "lb" ) );
		Assert.assertTrue( solo.searchText( "Ham" ) );
	}
	
	public void testInvalidRecipe() {
		solo.clickOnText( "Another recipe!" );
		solo.clickOnButton( 0 );
		solo.waitForActivity( "IngredientAddition" );
		solo.enterText( 0, "Ham" );
		solo.enterText( 1, "1" );
		solo.clickOnText( "kg" );
		solo.clickOnText( "lb" );
		solo.clickOnText( "Save" );
		
		Assert.assertFalse( solo.searchText( "lb" ) );
		
		solo.clickOnButton( 0 );
		solo.waitForActivity( "IngredientAddition" );
		solo.enterText( 0, "No Amount" );
		solo.clickOnText( "Save" );
		solo.waitForDialogToOpen();
		Assert.assertTrue( solo.searchText( "Warning" ) );
		solo.goBack();
		solo.goBack();

		solo.goBack();
		solo.goBack();
		solo.assertCurrentActivity( "Expected activity HomeActivity", "HomeActivity" );
	}
}
