package comp3350.tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BusinessTests {
	public static TestSuite suite;
	
	public static Test suite() {
		suite = new TestSuite( "Integration tests" );
		suite.addTestSuite( AccessIngredientsTest.class );
		suite.addTestSuite( AccessRecipesTest.class );
		suite.addTestSuite( RecipeFilterTest.class );      
		suite.addTestSuite( UnitConversionsTest.class);
		
		return suite;
	}
}
