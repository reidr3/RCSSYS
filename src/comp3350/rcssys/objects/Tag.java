package comp3350.rcssys.objects;

public abstract class Tag {
	private String name;
	
	public Tag() {
		this.name = "Missing name";
	}
	
	public Tag( String name ) {
		if (name != null ) {
			this.name = name;
		} else {
			this.name = "Missing name";
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		if( name != null ) {
			this.name = name;
		}
	}
	
	public abstract String getType();
	
	@Override
	public abstract boolean equals( Object object );
}