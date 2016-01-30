package comp3350.tests.objects;

import junit.framework.TestCase;
import comp3350.rcssys.objects.LifeStyle;

public class LifeStyleTest extends TestCase {
	private LifeStyle lifeStyle1;
	private LifeStyle lifeStyle2;
	private LifeStyle lifeStyle3;
	
	public LifeStyleTest ( String arg0 ) {
		super( arg0 );
	}
	
	public void setUp() {
		lifeStyle1 = new LifeStyle();
		lifeStyle2 = new LifeStyle( null );
		lifeStyle3 = new LifeStyle( "lifeStyle" );
		System.out.println( "Start " + this.getName() + "..." );
	}
	
	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
	}
	
	public void testEmpty() {
		assertEquals( "Missing name", lifeStyle1.getName() );
		lifeStyle1.setName( "New name!" );
		assertEquals( "New name!", lifeStyle1.getName() );
		lifeStyle1.setName( null );
		assertEquals( "New name!", lifeStyle1.getName() );
	}
	
	public void testNull() {
		assertEquals( "Missing name", lifeStyle2.getName() );
		lifeStyle2.setName( "New Name!" );
		assertEquals( "New Name!", lifeStyle2.getName() );
		lifeStyle2.setName( null );
		assertEquals( "New Name!", lifeStyle2.getName() );
	}
	
	public void testReal() {
		assertEquals( "lifeStyle", lifeStyle3.getName() );
		lifeStyle3.setName( "New Name!" );
		assertEquals( "New Name!", lifeStyle3.getName() );
		lifeStyle3.setName( null );
		assertEquals( "New Name!", lifeStyle3.getName() );
	}
}