package comp3350.tests.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ObjectTests {
	public static TestSuite suite;
	
	public static Test suite() {
		suite = new TestSuite( "Object tests" );
		suite.addTestSuite( AllergyTest.class );
		suite.addTestSuite( ConstituentTest.class );
		suite.addTestSuite( IngredientTest.class );      
		suite.addTestSuite( LifeStyleTest.class);
		suite.addTestSuite( RecipeTest.class);
		
		return suite;
	}
}
