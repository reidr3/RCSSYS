package comp3350.tests.objects;

import junit.framework.TestCase;
import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.objects.Ingredient;

public class ConstituentTest extends TestCase {
	Constituent constituent;

	public ConstituentTest ( String arg0 ) {
		super( arg0 );
	}
	
	public void setUp() {
		System.out.println( "Start " + this.getName() + "..." );
	}
	
	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
	}
	
	public void testNull() {
		constituent = new Constituent( null, 0.0, null );
		assertEquals( "kg", constituent.getUnit() );
		assertEquals( 0.0, constituent.getAmount() );
		assertEquals( "No name", constituent.getName( ) );
	}
	
	public void testBad() {
		constituent = new Constituent( null, -10.0, "not a unit" );
		assertEquals( 0.0, constituent.getAmount() );
		assertEquals( "kg", constituent.getUnit() );	
	}
	
	public void testReal() {
		constituent = new Constituent( new Ingredient( "New Name!" ), 1.0, "kg" );
		assertEquals( "New Name!", constituent.getName() );
		assertEquals( 1.0, constituent.getAmount( ) );
		
		assertEquals( "kg", constituent.getUnit() );
		constituent.setUnit( "g" );
		assertEquals( "g", constituent.getUnit() );
		constituent.setUnit( "oz" );
		assertEquals( "oz", constituent.getUnit() );
		constituent.setUnit( "lb" );
		assertEquals( "lb", constituent.getUnit() );
		
		constituent.setUnit( "KG" );
		assertEquals( "kg", constituent.getUnit() );
		constituent.setUnit( "G" );
		assertEquals( "g", constituent.getUnit() );
		constituent.setUnit( "OZ" );
		assertEquals( "oz", constituent.getUnit() );
		constituent.setUnit( "LB" );
		assertEquals( "lb", constituent.getUnit() );
		
		constituent.setUnit( "l" );
		assertEquals( "l", constituent.getUnit() );
		constituent.setUnit( "ml" );
		assertEquals( "ml", constituent.getUnit() );
		constituent.setUnit( "c" );
		assertEquals( "c", constituent.getUnit() );
		constituent.setUnit( "tsp" );
		assertEquals( "tsp", constituent.getUnit() );
		constituent.setUnit( "tbsp" );
		assertEquals( "tbsp", constituent.getUnit() );
		
		constituent.setUnit( "L" );
		assertEquals( "l", constituent.getUnit() );
		constituent.setUnit( "ML" );
		assertEquals( "ml", constituent.getUnit() );
		constituent.setUnit( "C" );
		assertEquals( "c", constituent.getUnit() );
		constituent.setUnit( "TSP" );
		assertEquals( "tsp", constituent.getUnit() );
		constituent.setUnit( "TBSP" );
		assertEquals( "tbsp", constituent.getUnit() );
	}
}
