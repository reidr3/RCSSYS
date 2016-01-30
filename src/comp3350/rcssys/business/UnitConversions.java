package comp3350.rcssys.business;

import comp3350.rcssys.objects.Constituent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;


public class UnitConversions {
	
	public static final String[] UNITS = {"kg","g","oz","lb","l","ml","c","tsp","tbsp"};
	public static final String[] MASS_UNITS = {"kg","g","lb","oz"};
	public static final String[] VOLUME_UNITS = {"l","ml","c","tsp","tbsp"};
	
	private static DecimalFormat roundToTwo = new DecimalFormat( "#.##" );
	private static ArrayList<String> massUnits = new ArrayList<String> ();
	private static ArrayList<String> volumeUnits = new ArrayList<String> ();
	
	public static boolean checkForUnit( String unit ) {
		boolean found = false;

		for( int i = 0; i < massUnits.size() && !found; i++ ) {
			if( unit.equals( massUnits.get( i ) ) ) {
				found = true;
			}
		}

		return found;
	}
	
	public static Constituent convertUnit( Constituent constituent, String newUnit ) {
		double newAmount = 0;
		Constituent output = null;
		if( constituent == null || newUnit == null || constituent.getUnit() == null ) {
			throw new IllegalArgumentException( "Null value passed for conversion." );
		} else if( constituent.getAmount() < 0 ) {
			throw new IllegalArgumentException( "Negative constituent amounts not allowed." );
		} else {
			addMassUnits();
			addVolumeUnits();

			if ( massUnits.contains( newUnit.toLowerCase( Locale.CANADA ) ) && massUnits.contains( constituent.getUnit() ) ) {
				newAmount = convertWeight( constituent.getAmount(), constituent.getUnit(), newUnit.toLowerCase( Locale.CANADA ) );
			} else if ( volumeUnits.contains( newUnit.toLowerCase( Locale.CANADA ) ) && volumeUnits.contains( constituent.getUnit() ) ) {
				newAmount = convertVolume( constituent.getAmount(), constituent.getUnit(), newUnit.toLowerCase( Locale.CANADA ) );
			} else {
				throw new IllegalArgumentException( "Unit to be converted to is not supported." );
			}
			
			output = new Constituent(constituent.getIngredient(),newAmount,newUnit);
		}

		return output;
	}
	
	public static String[] getValidUnitsFor(String unit) {
		String[] output = null;
		
		if( isMassUnit( unit ) ) {
			output = MASS_UNITS;
		} else if( isVolumeUnit( unit ) ) {
			output = VOLUME_UNITS;
		}
		
		return output;
	}
	
	public static boolean isMassUnit( String unit ) {
		boolean output = false;
		String unitLower = unit.toLowerCase( Locale.CANADA );
		
		for( String s : MASS_UNITS ) {
			if( s.equals( unitLower ) ) {
				output = true;
			}
		}
		
		return output;
	}
	
	public static boolean isVolumeUnit( String unit ) {
		boolean output = false;
		String unitLower = unit.toLowerCase( Locale.CANADA );
		
		for( String s : VOLUME_UNITS ) {
			if( s.equals( unitLower ) ) {
				output = true;
			}
		}
		
		return output;
	}
	
	public static boolean verifyUnit(String unit) {
		boolean result = false;

		if( unit.equals("kg") ||
			unit.equals("g")  ||
			unit.equals("oz") ||
			unit.equals("lb") ||
			unit.equals("l")  ||
			unit.equals("ml") ||
			unit.equals("c")  ||
			unit.equals("tsp")||
			unit.equals("tbsp" ) ) {

			result = true;
		}

		return result;
	}
	
	private static void addMassUnits() {
		if(massUnits.size() == 0 ) {
			massUnits.add( "kg" );
			massUnits.add( "g" );
			massUnits.add( "lb" );
			massUnits.add( "oz" );
		}
	}

	private static void addVolumeUnits() {
		if(volumeUnits.size() == 0 ) {
			volumeUnits.add( "l" );
			volumeUnits.add( "ml" );
			volumeUnits.add( "c" );
			volumeUnits.add( "tsp" );
			volumeUnits.add( "tbsp" );
		}
	}

	private static double convertVolume( double amount,String origUnit, String newUnit ) {
		double litreToCommon = 1/5.0;
		double cupToCommon = 1/20.8333333;

		if ( !origUnit.equals( newUnit ) ) {
			if ( origUnit.equals( "l" ) ) {
				amount = amount * litreToCommon;
			} else if ( origUnit.equals( "c" ) ) {
				amount = amount * cupToCommon;
			} else if ( origUnit.equals( "ml" ) ) {
				amount = ( amount/1000.0 ) * litreToCommon;
			} else if ( origUnit.equals( "tsp" ) ) {
				amount = ( amount/48.0 ) * cupToCommon;
			} else if ( origUnit.equals ( "tbsp" ) ) {
				amount = ( amount/16.0 ) * cupToCommon;
			}
			
			if ( newUnit.equals( "l" ) ) { 
				amount = amount / litreToCommon;
			} else if ( newUnit.equals( "ml" ) ) {
				amount = Math.round( ( amount / litreToCommon ) * 1000.0 );
			} else if ( newUnit.equals( "c" ) ) {
				amount = Math.round( ( amount * 4 / cupToCommon ) ) * 0.25;
			} else if ( newUnit.equals( "tsp" ) ) {
				amount = Math.round( amount * 48 / cupToCommon );
			} else if ( newUnit.equals( "tbsp" ) ) {
				amount = Math.ceil( ( amount * 16.0 / cupToCommon ) );
			} 
		}

		return Double.valueOf( roundToTwo.format( amount ) );
	}

	private static double convertWeight( double amount, String origUnit, String newUnit ) {
		double kgToGram = 1000;
		double lbToOz = 16;
		double kgToLb = 2.20462;
		String [][] units = {{"kg", "g", "oz", "lb"}, {"lb", "oz", "kg", "g"}, 
				{"oz", "lb", "kg", "g"}, {"g", "kg", "lb", "oz"}};
		double [][] conversionFactors = {{1, kgToGram, kgToLb * lbToOz, kgToLb}, {1, lbToOz, 1/kgToLb, kgToGram/kgToLb},
				{1,  1/lbToOz, 1/( lbToOz * kgToLb ), kgToGram/( lbToOz * kgToLb )}, {1, 1/kgToGram, ( 1/kgToGram ) * kgToLb, ( 1/kgToGram ) * kgToLb * lbToOz}}; 
		int i = 0;
		int j = 0;

		while( !units[i][0].equals( origUnit ) ) {
			i++;
		}				
		while( !units[i][j].equals( newUnit ) ) {
			j++;
		}
		amount = amount * conversionFactors[i][j];

		if( newUnit.equals( "g" ) ) {
			amount = Math.ceil( amount );
		}

		return Double.valueOf( roundToTwo.format( amount ) );
	}
}