package comp3350.tests.business;

import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.business.UnitConversions;
import junit.framework.TestCase;
import java.util.ArrayList;

public class UnitConversionsTest extends TestCase {
	private ArrayList<Constituent> wholeNumsMass = new ArrayList<Constituent>();
	private ArrayList<Constituent> decimalNumsMass = new ArrayList<Constituent>();
	private ArrayList<Constituent> zeroesMass = new ArrayList<Constituent>();
	private ArrayList<Constituent> negativeNumsMass = new ArrayList<Constituent>();
	private ArrayList<Constituent> badUnitsMass = new ArrayList<Constituent>();

	private ArrayList<Constituent> wholeNumsMassSolns = new ArrayList<Constituent>();
	private ArrayList<Constituent> decimalNumsMassSolns = new ArrayList<Constituent>();
	private ArrayList<Constituent> zeroesMassSolns = new ArrayList<Constituent>();

	private ArrayList<Constituent> wholeNumsVolume = new ArrayList<Constituent>();
	private ArrayList<Constituent> decimalNumsVolume = new ArrayList<Constituent>();
	private ArrayList<Constituent> zeroesVolume = new ArrayList<Constituent>();
	private ArrayList<Constituent> negativeNumsVolume = new ArrayList<Constituent>();
	private ArrayList<Constituent> badUnitsVolume = new ArrayList<Constituent>();

	private ArrayList<Constituent> wholeNumsVolumeSolns = new ArrayList<Constituent>();
	private ArrayList<Constituent> decimalNumsVolumeSolns = new ArrayList<Constituent>();
	private ArrayList<Constituent> zeroesVolumeSolns = new ArrayList<Constituent>();

	public UnitConversionsTest( String arg0 ) {
		super( arg0 );
	}

	public void setUp() {
		System.out.println( "Start " + this.getName() + "..." );
	}

	public void tearDown() {
		System.out.println( "...finished " + this.getName() + ".\n" );
	}

	public void testWholeNumbers() {
		int i = 0;

		loadWholeNums();

		for( i = 0; i < wholeNumsMass.size(); i++ ) {
			Constituent output = UnitConversions.convertUnit( wholeNumsMass.get( i ), wholeNumsMassSolns.get( i ).getUnit() );

			assertEquals( output.getAmount(), wholeNumsMassSolns.get( i ).getAmount() );
			assertEquals( output.getUnit(), wholeNumsMassSolns.get( i ).getUnit() );
			assertEquals( output.getName(), wholeNumsMassSolns.get( i ).getName() );
		}

		for( i = 0; i < wholeNumsVolume.size(); i++ ) {
			Constituent ouptut = UnitConversions.convertUnit( wholeNumsVolume.get( i), wholeNumsVolumeSolns.get( i ).getUnit() );

			assertEquals( ouptut.getAmount(), wholeNumsVolumeSolns.get( i ).getAmount() );
			assertEquals( ouptut.getUnit(), wholeNumsVolumeSolns.get( i ).getUnit() );
			assertEquals( ouptut.getName(), wholeNumsVolumeSolns.get( i ).getName() );
		}

		wholeNumsMass.clear();
		wholeNumsVolume.clear();
		wholeNumsMassSolns.clear();
		wholeNumsVolumeSolns.clear();
	}

	@SuppressWarnings( "unchecked" )
	public void testSameUnit() {
		loadWholeNums();
		ArrayList<Constituent> wholeNumsMassCopy = ( ArrayList<Constituent> ) wholeNumsMass.clone();
		ArrayList<Constituent> wholeNumsVolCopy = ( ArrayList<Constituent> ) wholeNumsVolume.clone();
		int i;

		for( i = 0; i < wholeNumsMass.size(); i++ ) {
			UnitConversions.convertUnit( wholeNumsMass.get( i ), wholeNumsMassCopy.get( i ).getUnit() );

			assertEquals( wholeNumsMass.get( i ).getAmount(), wholeNumsMassCopy.get( i ).getAmount() );
			assertEquals( wholeNumsMass.get( i ).getUnit(), wholeNumsMassCopy.get( i ).getUnit() );
			assertEquals( wholeNumsMass.get( i ).getName(), wholeNumsMassCopy.get( i ).getName() );
		}

		for( i = 0; i < wholeNumsVolume.size(); i++ ) {
			UnitConversions.convertUnit(wholeNumsVolume.get( i ), wholeNumsVolCopy.get( i ).getUnit() );

			assertEquals( wholeNumsVolume.get( i ).getAmount(), wholeNumsVolCopy.get( i ).getAmount() );
			assertEquals( wholeNumsVolume.get( i ).getUnit(), wholeNumsVolCopy.get( i ).getUnit() );
			assertEquals( wholeNumsVolume.get( i ).getName(), wholeNumsVolCopy.get( i ).getName() );
		}

		wholeNumsMass.clear();
		wholeNumsVolume.clear();
		wholeNumsMassCopy.clear();
		wholeNumsVolCopy.clear();
	}

	public void testDecimalNumbers() {
		int i;

		loadDeciNums();

		for( i = 0; i < wholeNumsMass.size(); i++ ) {
			UnitConversions.convertUnit( wholeNumsMass.get( i ), wholeNumsMassSolns.get( i ).getUnit() );

			assertEquals( wholeNumsMass.get( i ).getAmount(), wholeNumsMassSolns.get( i ).getAmount() );
			assertEquals( wholeNumsMass.get( i ).getUnit(), wholeNumsMassSolns.get( i ).getUnit() );
			assertEquals( wholeNumsMass.get( i ).getName(), wholeNumsMassSolns.get( i ).getName() );
		}

		for( i = 0; i < wholeNumsVolume.size(); i++ ) {
			UnitConversions.convertUnit( wholeNumsVolume.get( i), wholeNumsVolumeSolns.get( i ).getUnit() );

			assertEquals( wholeNumsVolume.get( i ).getAmount(), wholeNumsVolumeSolns.get( i ).getAmount() );
			assertEquals( wholeNumsVolume.get( i ).getUnit(), wholeNumsVolumeSolns.get( i ).getUnit() );
			assertEquals( wholeNumsVolume.get( i ).getName(), wholeNumsVolumeSolns.get( i ).getName() );
		}

		decimalNumsMass.clear();
		decimalNumsVolume.clear();
		decimalNumsVolumeSolns.clear();
		decimalNumsVolumeSolns.clear();
	} 

	public void testNegativeNumbers() {
		int i;

		loadNegNums();
		
		for( i = 0; i < negativeNumsMass.size(); i++ ) {
			try {
				UnitConversions.convertUnit( negativeNumsMass.get( i ), "kg" );
						
			} catch( IllegalArgumentException n ) {
				fail( "Didn't change the negative number to 0" );
			}
		}
		
		for( i = 0; i < negativeNumsVolume.size(); i++ ) {
			try {
				UnitConversions.convertUnit( negativeNumsVolume.get( i ), "l" );
				
			} catch( IllegalArgumentException n ) {
				fail( "Didn't change the negative number to 0" );
			}
		}
		
		negativeNumsMass.clear();
		negativeNumsVolume.clear();
	}

	public void testBadUnits() {
		int i;
		
		loadBadUnits();
		
		for( i = 0; i < badUnitsMass.size(); i++ ) {
			try {
				UnitConversions.convertUnit( badUnitsMass.get( i ), "kg" );
			} catch ( IllegalArgumentException o ) {
				fail( "Didn't change it to kg" );
			}
		}
		
		for( i = 0; i < badUnitsMass.size(); i++ ) {
			try {
				UnitConversions.convertUnit( badUnitsVolume.get( i ), "kg" );				
			} catch ( IllegalArgumentException o ) {
				fail( "Changed to kg" );
			}
		}
		
		try {
			UnitConversions.convertUnit( new Constituent( new Ingredient( "Name" ), 1, "kg" ), "bad" );
		
			fail( "Didn't catch bad unit being passed." );
		} catch ( IllegalArgumentException o ) {}
		
		try {
			UnitConversions.convertUnit( new Constituent( new Ingredient( "Name" ), 1, "kg" ), null );
			
			fail( "Didn't catch bad unit being passed." );
		} catch ( IllegalArgumentException o ) {} 
		
		try {
			UnitConversions.convertUnit( new Constituent( new Ingredient( "Name" ), 1, "l" ), "bad" );
			
			fail( "Didn't catch bad unit being passed." );
		} catch ( IllegalArgumentException o ) {} 
		
		try {
			UnitConversions.convertUnit( new Constituent( new Ingredient( "Name" ), 1, "l" ), null );
			
			fail( "Didn't catch bad unit being passed." );
		} catch ( IllegalArgumentException o ) {} 
		
		badUnitsMass.clear();
		badUnitsVolume.clear();
	}

	public void testZero() {
		int i;
		
		loadZeroes();
		
		for( i = 0; i < zeroesMass.size(); i++ ) {
			Constituent output = UnitConversions.convertUnit( zeroesMass.get( i ), zeroesMassSolns.get( i ).getUnit() );

			assertEquals( output.getAmount(), zeroesMassSolns.get( i ).getAmount() );
			assertEquals( output.getUnit(), zeroesMassSolns.get( i ).getUnit() );
			assertEquals( output.getName(), zeroesMassSolns.get( i ).getName() );
		}
		
		for( i = 0; i < zeroesMass.size(); i++ ) {
			Constituent output = UnitConversions.convertUnit( zeroesVolume.get( i ), zeroesVolumeSolns.get( i ).getUnit() );

			assertEquals( output.getAmount(), zeroesVolumeSolns.get( i ).getAmount() );
			assertEquals( output.getUnit(), zeroesVolumeSolns.get( i ).getUnit() );
			assertEquals( output.getName(), zeroesVolumeSolns.get( i ).getName() );
		}
		
		zeroesMass.clear();
		zeroesVolume.clear();
		zeroesVolumeSolns.clear();
		zeroesMassSolns.clear();
	}

	private void loadWholeNums() {
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 1, "kg" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 3, "kg" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 15, "kg" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 1000, "g" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 5, "g" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 5, "g" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 2, "lb" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 10, "lb" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 80, "lb" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 64, "oz" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 64, "oz" ) );
			wholeNumsMass.add( new Constituent( new Ingredient( "Name" ), 64, "oz" ) );
	
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 1000.0, "g" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 6.61, "lb" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 529.11, "oz" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 1.0, "kg" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 0.01, "lb" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 0.18, "oz" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 32.0, "oz" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 4.54, "kg" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 36288.0, "g" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 1815.0, "g" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 4.0, "lb" ) );
			wholeNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 1.81, "kg" ) );
	
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 1, "l" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 3, "l" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 5, "l" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 7, "l" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 4000, "ml" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 12000, "ml" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 1500, "ml" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 3000, "ml" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 1, "c" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 4, "c" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 8, "c" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 12, "c" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 5, "tbsp" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 10, "tbsp" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 14, "tbsp" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 21, "tbsp" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 9, "tsp" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 11, "tsp" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 19, "tsp" ) );
			wholeNumsVolume.add( new Constituent( new Ingredient( "Name" ), 21, "tsp" ) );
	
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 1000.0, "ml" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 12.5, "c" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 1000.0, "tsp" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 467.0, "tbsp" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 4.0, "l" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 50.0, "c" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 300.0, "tsp" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 200.0, "tbsp" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 16.0, "tbsp" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 192.0, "tsp" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 1.92, "l" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 2880.0, "ml" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 15.0, "tsp" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 150.0, "ml" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0.21, "l" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 1.25, "c" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0.05, "l" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0.25, "c" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 95.0, "ml" ) );
			wholeNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 7.0, "tbsp" ) );
		
	}

	private void loadDeciNums() {
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 1.5, "kg" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 3.14, "kg" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 7.55, "kg" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 555.55, "g" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 123.32, "g" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 987.546, "g" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 7.4, "lb" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 36.6, "lb" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 19.5, "lb" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 17.9, "oz" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 87.45, "oz" ) );
			decimalNumsMass.add( new Constituent( new Ingredient( "Name" ), 40.2, "oz" ) );
	
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 1500.0, "g" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 6.92, "lb" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 266.32, "oz" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 0.56, "kg" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 0.27, "lb" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 34.83, "oz" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 118.4, "oz" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 16.6, "kg" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 8846.0, "g" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 1.12, "lb" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 2.48, "kg" ) );
			decimalNumsMassSolns.add( new Constituent( new Ingredient( "Name" ), 1140.0, "g" ) );
	
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 2.23, "l" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 3.57, "l" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 5.99, "l" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 7.04, "l" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 4000.95, "ml" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 1200.11, "ml" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 1500.45, "ml" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 3000.76, "ml" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 1.81, "c" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 4.32, "c" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 8.1, "c" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 12.9, "c" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 5.55, "tbsp" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 10.59, "tbsp" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 14.73, "tbsp" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 21.44, "tbsp" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 9.6, "tsp" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 11.11, "tsp" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 19.48, "tsp" ) );
			decimalNumsVolume.add( new Constituent( new Ingredient( "Name" ), 21.53, "tsp" ) );
	
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 2230.0, "ml" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 14.75, "c" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 1198.0, "tsp" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 470.0, "tbsp" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 4.0, "l" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 5.0, "c" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 300.0, "tsp" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 201.0, "tbsp" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 29.0, "tbsp" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 207.0, "tsp" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 1.94, "l" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 3096.0, "ml" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 17.0, "tsp" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0.75, "c" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0.22, "l" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 322.0, "ml" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 48.0, "ml" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0.06, "l" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0.5, "c" ) );
			decimalNumsVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 8.0, "tbsp" ) );
	}

	private void loadNegNums() {
			negativeNumsMass.add( new Constituent( new Ingredient( "Name" ), -10.9, "kg" ) );
			negativeNumsMass.add( new Constituent( new Ingredient( "Name" ), -8.7, "g" ) );
			negativeNumsMass.add( new Constituent( new Ingredient( "Name" ), -6.54, "oz" ) );
			negativeNumsMass.add( new Constituent( new Ingredient( "Name" ), -3.21, "lb" ) );
	
			negativeNumsVolume.add( new Constituent( new Ingredient( "Name" ), -1.2, "l" ) );
			negativeNumsVolume.add( new Constituent( new Ingredient( "Name" ), -2.4, "ml" ) );
			negativeNumsVolume.add( new Constituent( new Ingredient( "Name" ), -4.8, "c" ) );
			negativeNumsVolume.add( new Constituent( new Ingredient( "Name" ), -9.6, "tbsp" ) );
			negativeNumsVolume.add( new Constituent( new Ingredient( "Name" ), -19.2, "tsp" ) );

	}

	private void loadBadUnits() {
			badUnitsMass.add( new Constituent( new Ingredient( "Name" ), 5, "bad" ) );
			badUnitsMass.add( new Constituent( new Ingredient( "Name" ), 5, null ) );
			
			badUnitsVolume.add( new Constituent( new Ingredient( "Name" ), 111, "bad" ) );
			badUnitsVolume.add( new Constituent( new Ingredient( "Name" ), 111, null ) );
	}
	
	private void loadZeroes() {
			zeroesMass.add( new Constituent( new Ingredient( "Name" ), 0, "kg" ) );
			zeroesMass.add( new Constituent( new Ingredient( "Name" ) , 0, "g" ) );
			zeroesMass.add( new Constituent( new Ingredient( "Name" ) , 0, "lb" ) );
			zeroesMass.add( new Constituent( new Ingredient( "Name" ) , 0, "oz" ) );
			
			zeroesMassSolns.add( new Constituent( new Ingredient( "Name" ), 0, "g" ) );
			zeroesMassSolns.add( new Constituent( new Ingredient( "Name" ), 0, "lb" ) );
			zeroesMassSolns.add( new Constituent( new Ingredient( "Name" ), 0, "kg" ) );
			zeroesMassSolns.add( new Constituent( new Ingredient( "Name" ), 0, "oz" ) );
			
			zeroesVolume.add( new Constituent( new Ingredient( "Name" ), 0, "l" ) );
			zeroesVolume.add( new Constituent( new Ingredient( "Name" ), 0, "ml" ) );
			zeroesVolume.add( new Constituent( new Ingredient( "Name" ), 0, "c" ) );
			zeroesVolume.add( new Constituent( new Ingredient( "Name" ), 0, "tsp" ) );
			zeroesVolume.add( new Constituent( new Ingredient( "Name" ), 0, "tbsp" ) );
			
			zeroesVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0, "tbsp" ) );
			zeroesVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0, "c" ) );
			zeroesVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0, "tsp" ) );
			zeroesVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0, "ml" ) );
			zeroesVolumeSolns.add( new Constituent( new Ingredient( "Name" ), 0, "l" ) );
	}
}
