package comp3350.tests.objects;

import comp3350.rcssys.objects.Allergy;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.LifeStyle;
import junit.framework.TestCase;

public class IngredientTest extends TestCase {
	Ingredient testIngredient = new Ingredient( "Ham" );
	Allergy allergy;
	LifeStyle lifeStyle;
	
	public IngredientTest( String arg0 ) {
		super( arg0 );
	}

	public void setUp() {
		System.out.println( "Start " + this.getName() + "..." );
		allergy = new Allergy( "Peanut" );
		lifeStyle = new LifeStyle ("Vegetarian" );
	}

	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
	}

	public void testIngredientNaming() {
		Ingredient testIng = new Ingredient();
		Ingredient testIng2 = new Ingredient( "Ham" );
		
		assertEquals( "No name", testIng.getName() );
		assertEquals( "Ham", testIng2.getName() );
		testIng.setName( "Jambon" );
		assertEquals( "Jambon", testIng.getName() );
	}
	
	public void testChangeLifestyle() {
		Ingredient testIng = new Ingredient();
		
		lifeStyle = new LifeStyle( "Vegetarian" );
		testIng.addLifeStyle( lifeStyle );
		assertEquals( "Vegetarian", testIng.getLifeStyles().get( 0 ).getName() );
		testIng.deleteLifeStyle( lifeStyle );
		assertEquals( 0, testIng.getLifeStyles().size() );
	}
	
	public void testChangeAllergies() {
		Ingredient testIng = new Ingredient();
		
		allergy = new Allergy( "peanuts" );
		testIng.addAllergy( allergy );
		assertEquals( "peanuts", testIng.getAllergies().get( 0 ).getName() );
		testIng.deleteAllergy( allergy );
		assertEquals( 0, testIng.getAllergies().size() );
	}
}
