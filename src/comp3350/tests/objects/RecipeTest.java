package comp3350.tests.objects;

import java.util.ArrayList;

import junit.framework.TestCase;
import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.Recipe;

public class RecipeTest extends TestCase {
	private final int UNUSEDID = -2;
	private Recipe recipe;
	private Recipe recipe2;
	private ArrayList<Constituent> ingredients;
	private Constituent ingredient1;
	private Constituent ingredient2;
	private Constituent ingredient3;
	private Constituent ingredient4;
	private Constituent ingredient5;
	
	public RecipeTest( String arg0 ) {
		super( arg0 );
	}
	
	public void setUp() {	
		System.out.println( "Start " + this.getName() + "..." );
		ingredients = new ArrayList<Constituent>();
		ingredient1 = new Constituent( new Ingredient( "Name1" ), 1.0, "kg" );
		ingredient2 = new Constituent( new Ingredient( "Name2" ), 1.0, "lbs" );
		ingredient3 = new Constituent( new Ingredient( "Name3" ), 1.0, "tsp" );
		ingredient4 = new Constituent( new Ingredient( "Name4" ), 1.5, "tbsp" );
		ingredient5 = new Constituent( new Ingredient( "Name5" ), 2.0, "kg" );
		
		ingredients.add( ingredient1 );
		ingredients.add( ingredient2 );
		ingredients.add( ingredient3 );
		ingredients.add( ingredient4 );
		ingredients.add( ingredient5 );
		
		recipe = new Recipe ( UNUSEDID, "Super Ham Sandwhich", "Do stuff", "Tastes good", null );
		recipe2 = new Recipe( UNUSEDID, "New title", "New Directions", "New Description", ingredients );		
	}
	
	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
	}
	
	public void testRecipeNull() {
		recipe = new Recipe( UNUSEDID, null, null, null, null );
		
		assertEquals( "Missing directions", recipe.getDirections() );
		assertEquals( "Missing title", recipe.getTitle() );
		assertEquals( "Missing description", recipe.getDescription() );
		assertTrue( recipe.getIngredients().isEmpty() );
		
		recipe2 = new Recipe( UNUSEDID, null, null, null, null );
		assertTrue( compareRecipes() );
	}
	
	public void testNoTitle() {
		recipe = new Recipe( UNUSEDID, null );
		
		assertEquals( "Missing directions", recipe.getDirections() );
		assertEquals( "Missing title", recipe.getTitle() );
		assertEquals( "Missing description", recipe.getDescription() );
		assertTrue( recipe.getIngredients().isEmpty() );
		
		recipe2 = new Recipe( UNUSEDID, null );
		assertTrue( compareRecipes() );
	}
	
	public void testOnlyTitle() {
		recipe = new Recipe( UNUSEDID, "Test Name" );
		
		assertEquals( "Missing directions", recipe.getDirections() );
		assertEquals( "Missing description", recipe.getDescription() );
		assertTrue( recipe.getIngredients().isEmpty() );
		assertEquals("Test Name", recipe.getTitle() );
		
		recipe2 = new Recipe( UNUSEDID, "Test Name" );
		assertTrue( compareRecipes() );
		
		recipe2 = new Recipe( UNUSEDID, "Test Name 2" );
		assertTrue( compareRecipes() );
	}
	
	public void testRecipeNoIng() {
		checkRecipeBasics();
		assertEquals( 0, recipe.getIngredients().size() );
		endCheck();
	}
	
	public void testRecipeOneIng() {
		checkRecipeBasics();
		recipe.addIngredient( ingredient1 );
		assertEquals( ingredient1, recipe.getIngredients().get( 0 ) );

		recipe.removeIngredient( ingredient1 );
		assertEquals( 0, recipe.getIngredients().size() );		
		endCheck();
	}
	
	public void testRecipeMultipleIng() {		
		checkRecipeBasics();
		addAllIngredients();
		checkIngredients();
		endCheck();
		
		recipe = new Recipe( UNUSEDID, "Super Ham Sandwhich", "Do stuff", "Tastes good", ingredients );
		checkIngredients();
		endCheck();		
	}
	
	public void testRecipeTitle() {		
		assertEquals( "Super Ham Sandwhich", recipe.getTitle() );
		assertTrue( compareRecipes() );
		
		recipe.setTitle( "" );
		assertEquals( "", recipe.getTitle() );
		assertTrue( compareRecipes() );
		
		recipe.setTitle( null );
		assertEquals( "", recipe.getTitle() );
		assertTrue( compareRecipes() );
		
		recipe.setTitle( "New title" );
		assertTrue( compareRecipes() );	
	}
	
	public void testRecipeDirections() {
		
		recipe.setDirections( "New directions" );
		assertEquals( "New directions", recipe.getDirections() );
		
		recipe.setDirections( null );
		assertEquals( "New directions", recipe.getDirections() );
		
		recipe.setDirections( "" );
		assertEquals( "", recipe.getDirections() );
		
		recipe = new Recipe( UNUSEDID, "Super Ham Sandwhich", "These are directions", "These are not", null );
		assertEquals( "These are directions", recipe.getDirections() );
		
		recipe.setDirections( "" );
		assertEquals( "", recipe.getDirections() );		
	}
	
	public void testRecipeDescription() {		
		recipe.setDescription( "New description" );
		assertEquals( "New description", recipe.getDescription() );
		
		recipe.setDescription( null );
		assertEquals( "New description", recipe.getDescription() );
		
		recipe.setDescription( "" );
		assertEquals( "", recipe.getDescription() );
		
		recipe = new Recipe( UNUSEDID, "Super Ham Sandwhich", "These are directions", "These are not", null );
		assertEquals( "These are not", recipe.getDescription() );
		
		recipe.setDescription( "" );
		assertEquals( "", recipe.getDescription() );	
	}
	
	private void checkIngredients() {
		assertEquals( 5, recipe.getIngredients().size() );
		assertEquals( ingredient1, recipe.getIngredients().get( 0 ) );
		assertEquals( ingredient2, recipe.getIngredients().get( 1 ) );
		assertEquals( ingredient3, recipe.getIngredients().get( 2 ) );
		assertEquals( ingredient4, recipe.getIngredients().get( 3 ) );
		assertEquals( ingredient5, recipe.getIngredients().get( 4 ) );
		
		recipe.removeIngredient( ingredient1 );
		assertEquals( ingredient2, recipe.getIngredients().get( 0 ) );
		assertEquals( 4, recipe.getIngredients().size() );
		
		recipe.removeIngredient( ingredient2 );
		recipe.removeIngredient( ingredient3 );
		recipe.removeIngredient( ingredient4 );
		recipe.removeIngredient( ingredient5 );		
		assertEquals( 0, recipe.getIngredients().size() );		
	}
	
	private void addAllIngredients() {		
		recipe.addIngredient( ingredient1 );
		recipe.addIngredient( ingredient2 );
		recipe.addIngredient( ingredient3 );
		recipe.addIngredient( ingredient4 );
		recipe.addIngredient( ingredient5 );
	}
	
	private void endCheck() {	
		checkRecipeBasics();
		newRecipeInfo();
		checkUpdatedRecipeBasics();	
	}
	
	private void newRecipeInfo() {
		recipe.setTitle( "New title" );
		recipe.setDirections( "New Directions" );
		recipe.setDescription( "New Description" );
	}
	
	private void checkRecipeBasics() {
		assertEquals( "Super Ham Sandwhich", recipe.getTitle() );
		assertTrue( recipe.getIngredients().isEmpty() );
		assertEquals( "Do stuff", recipe.getDirections() );
		assertEquals( "Tastes good", recipe.getDescription() );
	}
	
	private void checkUpdatedRecipeBasics() {
		assertEquals( "New title", recipe.getTitle() );
		assertNotNull( recipe.getIngredients() );
		assertEquals( "New Directions", recipe.getDirections() );
		assertEquals( "New Description", recipe.getDescription() );
		assertTrue( compareRecipes() );	
	}
	
	private boolean compareRecipes() {
		boolean result = false;
		
		if( recipe.equals( recipe2 ) ) {
			result = true;
		}
		
		return result ;
	}
}
