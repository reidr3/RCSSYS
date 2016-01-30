package comp3350.rcssys.objects;

import java.util.Locale;

public class LifeStyle extends Tag {
	private final String TYPE = "LifeStyle";
	
	public LifeStyle() {
		super();
	}
	
	public LifeStyle( String name ) {
		super( name );
	}
	
	@Override
	public boolean equals( Object object ) {
		boolean result = false;
		LifeStyle temp = null;
		
		if( object != null && object instanceof LifeStyle ) {
			temp = ( LifeStyle ) object;
			
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