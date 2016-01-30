package comp3350.rcssys.objects;

import comp3350.rcssys.business.UnitConversions;
import java.util.ArrayList;
import java.util.Locale;

public class Constituent {
	private Ingredient contained;
	private double amount;
	private String unit;

	public Constituent( Ingredient ing, double amt, String unit ) {

		if( ing != null ) {
			contained = ing;
		} else {
			contained = new Ingredient( null );
		}

		if( amt >= 0.0 ){
			this.amount = amt;
		} else {
			this.amount = 0.0;
		}

		if( unit != null ) {
			this.setUnit( unit );
		} else {
			this.unit = "kg";
		}
	}
	
	public ArrayList<Allergy> getAllergies() {
		return contained.getAllergies();
	}
	
	public double getAmount() {
		return amount;
	}

	public Ingredient getIngredient() {
		return contained;
	}
	
	public ArrayList<LifeStyle> getLifeStyles() {
		return contained.getLifeStyles();
	}

	public String getName() {
		return contained.getName();
	}

	public String getUnit() {
		return unit;
	}

	public boolean hasThisAllergen( Allergy allergy ) {
		return contained.containsAllergy( allergy );
	}

	public boolean isAllergen() {
		return contained.containsAllergen();
	}

	public boolean isLifeStyle( LifeStyle lifeStyle ) {
		return contained.containsLifeStyle( lifeStyle );
	}

	public void setAmount( double amt ) {
		if( amt >= 0.0 ){
			amount = amt;
		} else {
			amount = 0.0;
		}
	}

	public void setName( String name ) {
		this.contained.setName( name );
	}

	public void setUnit( String unit ) {
		if( unit != null && UnitConversions.verifyUnit( unit.toLowerCase( Locale.CANADA ) ) == true ) {
			this.unit = unit.toLowerCase( Locale.CANADA );
		} else if ( this.unit == null) {
			this.unit = "kg";
		}
	}
}

