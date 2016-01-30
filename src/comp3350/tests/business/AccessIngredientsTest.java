package comp3350.tests.business;

import java.util.ArrayList;

import comp3350.rcssys.application.Services;
import comp3350.rcssys.business.AccessIngredients;
import comp3350.rcssys.objects.Ingredient;
import comp3350.tests.persistence.DataAccessStub;
import junit.framework.TestCase;

public class AccessIngredientsTest extends TestCase {
	
	private AccessIngredients ingredientAccess;
	private ArrayList<Ingredient> ingredients;
	private Ingredient ingredient;
	private String result;
	
	public AccessIngredientsTest( String arg0 ) {
		super( arg0 );
	}
	
	@Override
	public void setUp() {
		System.out.println( "Start " + this.getName() + "..." );
		Services.createDataAccess( new DataAccessStub( "stub" ) );
		ingredientAccess = new AccessIngredients();
		ingredients = new ArrayList<Ingredient>();
	}
	
	@Override
	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
		Services.closeDataAccess();
	}
	
	public void testNull() {
		assertNotNull( ingredientAccess );
		ingredient = ingredientAccess.getIngredient( null );
		assertNull( ingredient );
		
		ingredients = ingredientAccess.searchForIngredient( null );
		assertEquals( 0, ingredients.size() );
		
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( 4, ingredients.size() );
		result = ingredientAccess.insertIngredient( null );
		assertNull( result );
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( 4, ingredients.size() );
		
		result = ingredientAccess.deleteIngredient( null );
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( 4, ingredients.size() );
		
		result = ingredientAccess.updateIngredient( null );
		assertNull( result );
	}
	
	public void testBad() {
		assertNotNull( ingredientAccess );
		ingredient = ingredientAccess.getIngredient( "This isn't in there" );
		assertNull( ingredient );
		
		ingredients = ingredientAccess.searchForIngredient( "No ingredients with this in their name" );
		assertEquals( 0, ingredients.size() );
		
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( 4, ingredients.size() );
		ingredient = ingredientAccess.getIngredient( "Ham" );
		assertNotNull( ingredient );
		result = ingredientAccess.insertIngredient( ingredient );
		assertNull( result );
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( 4, ingredients.size() );
		
		ingredient = new Ingredient ( "This isn't in there" );
		result = ingredientAccess.deleteIngredient( ingredient );
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( 4, ingredients.size() );
		
		result = ingredientAccess.updateIngredient( ingredient );
		assertNull( result );
	}
	
	public void testReal() {
		assertNotNull( ingredientAccess );
		ingredient = ingredientAccess.getIngredient( "Ham" );
		assertEquals( ingredient.getName(), "Ham" );
		
		ingredients = ingredientAccess.searchForIngredient( "Mayo" );
		assertEquals( ingredients.size(), 1 );
		assertEquals( ingredients.get( 0 ).getName(), "Mayo" );
		ingredients = ingredientAccess.searchForIngredient( "o" );
		assertEquals( ingredients.size(), 3 );
		
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( ingredients.size(), 4 );
		
		ingredient = new Ingredient( "Lets add this" );
		result = ingredientAccess.insertIngredient( ingredient );
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( ingredients.size(), 5 );
		assertEquals( ingredients.get( 4 ).getName(), "Lets add this" );
		
		result = ingredientAccess.deleteIngredient( ingredient );
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( ingredients.size(), 4 );
		
		result = ingredientAccess.insertIngredient( ingredient );
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( ingredients.size(), 5 );
		
		ingredient.setName( "Does this work?" );
		result = ingredientAccess.updateIngredient( ingredient );;
		result = ingredientAccess.getIngredients( ingredients );
		assertEquals( ingredients.get( 4 ).getName(), "Does this work?" );
		
		ingredient = ingredientAccess.getIngredient( "Mayo" );
		assertEquals( 3, ingredient.getAllergies().size() );
		assertEquals( 0, ingredient.getLifeStyles().size() );
		
		ingredient = ingredientAccess.getIngredient( "Ham" );
		assertEquals( 1, ingredient.getAllergies().size() );
		assertEquals( 1, ingredient.getLifeStyles().size() );
		
		ingredient = ingredientAccess.getIngredient( "Someother thing" );
		assertEquals( 0, ingredient.getAllergies().size() );
		assertEquals( 3, ingredient.getLifeStyles().size() );
		
		ingredient = ingredientAccess.getIngredient( "Another thing" );
		assertEquals( 0, ingredient.getAllergies().size() );
		assertEquals( 0, ingredient.getLifeStyles().size() );
	}
}