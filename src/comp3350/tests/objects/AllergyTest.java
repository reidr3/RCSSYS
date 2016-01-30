package comp3350.tests.objects;

import junit.framework.TestCase;
import comp3350.rcssys.objects.Allergy;

public class AllergyTest extends TestCase {
	private Allergy allergy1;
	private Allergy allergy2;
	private Allergy allergy3;
	
	public AllergyTest ( String arg0 ) {
		super( arg0 );
	}
	
	public void setUp() {
		allergy1 = new Allergy();
		allergy2 = new Allergy( null );
		allergy3 = new Allergy( "Allergy" );
		System.out.println( "Start " + this.getName() + "..." );
	}
	
	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
	}
	
	public void testEmpty() {
		assertEquals( "Missing name", allergy1.getName() );
		allergy1.setName( "New name!" );
		assertEquals( "New name!", allergy1.getName() );
		allergy1.setName( null );
		assertEquals( "New name!", allergy1.getName() );
	}
	
	public void testNull() {
		assertEquals( "Missing name", allergy2.getName() );
		allergy2.setName( "New Name!" );
		assertEquals( "New Name!", allergy2.getName() );
		allergy2.setName( null );
		assertEquals( "New Name!", allergy2.getName() );
	}
	
	public void testReal() {
		assertEquals( "Allergy", allergy3.getName() );
		allergy3.setName( "New Name!" );
		assertEquals( "New Name!", allergy3.getName() );
		allergy3.setName( null );
		assertEquals( "New Name!", allergy3.getName() );
	}
}
