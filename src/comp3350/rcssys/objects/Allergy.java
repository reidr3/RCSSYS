package comp3350.rcssys.objects;

import java.util.Locale;

public class Allergy extends Tag {
	private final String TYPE = "Allergy";
	
	public Allergy() {
		super();
	}
	
	public Allergy( String name ) {
		super( name );
	}
	
	@Override
	public boolean equals( Object object ) {
		boolean result = false;
		Allergy temp = null;
		
		if( object != null && object instanceof Allergy ) {
			temp = ( Allergy )object;
			
			if ( this.getName().toLowerCase( Locale.CANADA ).equals( temp.getName().toLowerCase( Locale.CANADA ) ) ) {
				result = true;
			}
		}
		
		return result;
	}
	
	public String getType() {
		return TYPE;
	}
}
