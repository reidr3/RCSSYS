package comp3350.tests.business;

import junit.framework.TestCase;
import comp3350.rcssys.application.Main;
import comp3350.rcssys.application.Services;
import comp3350.rcssys.business.RecipesFilter;
import comp3350.rcssys.objects.Recipe;
import comp3350.rcssys.persistence.DataAccess;
import comp3350.tests.persistence.DataAccessStub;

import java.util.ArrayList;
import java.util.Collections;

public class RecipeFilterTest extends TestCase {
	private ArrayList<Recipe> toCompareTo = new ArrayList<Recipe>();
	private ArrayList<Recipe> toTest;
	private RecipesFilter testCase;
	DataAccess testDB;

	public RecipeFilterTest( String arg0 ) {
		super( arg0 );
	}

	public void setUp() {
		System.out.println( "Start " + this.getName() + "..." );
		Services.createDataAccess( new DataAccessStub( Main.dbName ) );
		testDB = Services.getDataAccess( Main.dbName );
		testDB.getRecipeSequential( toCompareTo );
		testCase = new RecipesFilter();
	}

	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
	}

	public void testNoFilters() {
		toTest = testCase.filterRecipes();
		assertEquals( toTest, toCompareTo );
	}

	public void testOneFilter() {
		testCase.createOrder( "ascending" );
		Collections.sort( toCompareTo );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo, toTest );
		
		testCase.clearFilters();
		testCase.createOrder( "descending" );
		Collections.sort( toCompareTo, Collections.reverseOrder() );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo, toTest );
		
		testCase = new RecipesFilter();
		testCase.clearFilters();
		testCase.createIngredientFilter( "Ham" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 3 ), toTest.get( 0 ) );
		
		testCase = new RecipesFilter();
		testCase.clearFilters();
		testCase.createAllergyFilter( "Peanuts" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 3 ), toTest.get( 0 ) );
		assertEquals( 2, toTest.size() );
		
		testCase = new RecipesFilter();
		testCase.clearFilters();
		testCase.createLifeStyleFilter( "Gluten Free" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 1 ), toTest.get( 0 ) );
		assertEquals( 2, toTest.size() );
		
		testCase = new RecipesFilter();
		testCase.clearFilters();
		testCase.createAllergyFilter( "Any" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 3 ), toTest.get( 0 ) );
		assertEquals( 2, toTest.size() );
		
		testCase = new RecipesFilter();
		testCase.clearFilters();
		testCase.createAllergyFilter( "None" );
		toTest = testCase.filterRecipes();
		assertEquals( 2, toTest.size() );
	}

	public void testMultiFilters() {
		testCase.createIngredientFilter( "Ham" );
		testCase.createAllergyFilter( "Milk" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 2 ), toTest.get( 0 ) );
		
		testCase.createLifeStyleFilter( "Vegatarian" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 2 ), toTest.get( 0 ) );
		
		testCase = new RecipesFilter();
		testCase.createAllergyFilter( "Milk" );
		testCase.createLifeStyleFilter( "Vegan" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 2 ), toTest.get( 0 ) );
		
		testCase.createIngredientFilter( "Another thing" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 2 ), toTest.get( 0 ) );
		
		testCase = new RecipesFilter();
		testCase.createLifeStyleFilter( "Vegatarian");
		testCase.createIngredientFilter( "Ham" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 2 ), toTest.get( 0 ) );
		
		testCase.createAllergyFilter( "Gluten" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 2 ), toTest.get( 0 ) );
	}

	public void testBadFilters() {
		testCase.createOrder( "bad" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo, toTest );
	}

	public void testBadAndGoodFilters() {
		Collections.sort( toCompareTo );
		testCase.createOrder( "ascending" );
		testCase.createOrder( "bad second" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo, toTest );
		
		testCase = new RecipesFilter();
		testCase.createOrder( "bad first" );
		testCase.createOrder( "ascending" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo, toTest );
		
		testCase = new RecipesFilter();
		testCase.createOrder( "bad first" );
		testCase.createIngredientFilter( "Ham");
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 0 ), toTest.get( 0 ) );
		assertEquals( 2, toTest.size() );

		testCase = new RecipesFilter();
		testCase.createAllergyFilter( "Milk");
		testCase.createOrder( "bad second" );
		toTest = testCase.filterRecipes();
		assertEquals( toCompareTo.get( 2 ), toTest.get( 0 ) );
	}
	
	public void filterOutEverything() {
		testCase.createIngredientFilter( "some ingredient not included" );
		toTest = testCase.filterRecipes();
		assertEquals( 0, toTest.size() );
		toTest.clear();
		testCase.clearFilters();
		
		testCase = new RecipesFilter();
		testCase.createAllergyFilter( "allergy not in list" );
		toTest = testCase.filterRecipes();
		assertEquals( 0, toTest.size() );
		toTest.clear();
		testCase.clearFilters();
		
		testCase = new RecipesFilter();
		testCase.createLifeStyleFilter( "non existent lifestyle" );
		toTest = testCase.filterRecipes();
		assertEquals( 0, toTest.size() );
		toTest.clear();
		testCase.clearFilters();
	}
}
