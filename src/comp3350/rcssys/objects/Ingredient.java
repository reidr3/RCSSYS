package comp3350.rcssys.objects;

import java.util.ArrayList;
import java.util.Locale;

public class Ingredient {
	private String name;
	private ArrayList<Allergy> allergies;
	private ArrayList<LifeStyle> lifeStyles;
	
	public Ingredient() {
		this.setName( "No name" );
		
		allergies = new ArrayList<Allergy>();
		lifeStyles = new ArrayList<LifeStyle>();
	}

	public Ingredient( String name ) {
		this();

		if( name != null ) {
			this.setName( name );
		}
	}
	
	public Ingredient( String name, ArrayList<Allergy> allergies, ArrayList<LifeStyle> lifeStyles ) {
		this( name );
		
		if( allergies != null ) {
			this.allergies.addAll( allergies );
		}
		
		if( lifeStyles != null ) {
			this.lifeStyles.addAll( lifeStyles );
		}
	}

	public void addAllergy( Allergy allergy ) {
		if( !allergies.contains( allergy ) ) {
			allergies.add( allergy );
		}
	}
	
	public void addLifeStyle( LifeStyle lifeStyle ) {
		if( !lifeStyles.contains( lifeStyle ) ) {
			lifeStyles.add( lifeStyle );
		}
	}

	public boolean containsAllergen() {
		return allergies.size() > 0;
	}

	public boolean containsAllergy( Allergy allergy ) {
		boolean found = false;
		
		found = allergies.contains( allergy );
		
		return found;
	}

	public boolean containsLifeStyle( LifeStyle lifeStyle ) {
		boolean found = false;
		
		found = lifeStyles.contains( lifeStyle );
		
		return found;
	}

	public void deleteAllergy( Allergy allergy ) {
		allergies.remove( allergy );
	}

	public void deleteLifeStyle( LifeStyle lifeStyle ) {
		lifeStyles.remove( lifeStyle );
	}
	
	@Override
	public boolean equals( Object object ) {
		boolean result = false;
		Ingredient temp = null;
		
		if( object != null && object instanceof Ingredient ) {
			temp = ( Ingredient )object;
			
			if( this.name.toLowerCase( Locale.CANADA ).equals( temp.getName().toLowerCase( Locale.CANADA ) ) ) {
				result = true;
			}
		}
		
		return result;
	}

	public ArrayList<Allergy> getAllergies() {
		return allergies;
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<LifeStyle> getLifeStyles() {
		return lifeStyles;
	}

	public void setName( String name ) {
		if( name != null ) {
			this.name = name;
		}
	}
}