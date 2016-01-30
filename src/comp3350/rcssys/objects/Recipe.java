 package comp3350.rcssys.objects;

import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.objects.Ingredient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Recipe implements Comparable<Recipe>, Iterable<Constituent>{	
	public static final int EMPTY_ID = -1;
	private int id;
	private String title;
	private ArrayList<Constituent> ingredients;
	private String directions;
	private String description;

	public Recipe(int id) {
		this.id = id;
		this.title = "Missing title";
		this.directions = "Missing directions";
		this.description = "Missing description";
		this.ingredients = new ArrayList<Constituent>();
	}	

	public Recipe( int id, String newTitle ) {
		this( id );

		if( newTitle != null ) {
			this.setTitle( newTitle );
		}
	}

	public Recipe( int id, String newTitle, String newDirections, String newDescription ) {
		this( id, newTitle );

		if( newDirections != null ) {
			this.setDirections( newDirections );
		}

		if( newDescription != null ) {
			this.setDescription( newDescription );
		}
	}

	public Recipe( int id, String newTitle, String newDirections, String newDescription, ArrayList<Constituent> ingredients ) {
		this( id, newTitle, newDirections, newDescription );

		if( ingredients != null ) {
			this.ingredients.addAll(ingredients);
		}
	}

	public void addIngredient( Constituent newIngredient ) {
		if( newIngredient != null && !hasIngredient( newIngredient.getIngredient() ) ) {
			ingredients.add( newIngredient );
		}
	}

	public void addIngredient( String newIngredient, double amount, String unit ) {
		boolean isIn = !hasIngredient( new Ingredient( newIngredient ) );

		if( ingredients != null && !isIn ) {

			if( newIngredient != null && amount >= 0.0) {	
				ingredients.add( new Constituent( new Ingredient( newIngredient ), amount, unit ) );
			}

		} else if( newIngredient != null && amount >= 0.0 && !isIn ) {
			ingredients = new ArrayList<Constituent>();
			ingredients.add( new Constituent( new Ingredient( newIngredient ), amount, unit ) );
		}
	}
	
	public void clearIngredients(){
		ingredients.clear();
	}

	public int compareTo( Recipe otherRecipe ) {
		Recipe temp = null;
		int result = -1;
		
		if( otherRecipe != null) {
			temp = ( Recipe )otherRecipe;
			result = this.getTitle().compareTo( temp.getTitle() );
		}

		return result;
	}

	@Override
	public boolean equals( Object object ) {
		boolean result = false;
		Recipe tempRecipe;
		result = false;

		if( object != null ) {
			if ( object instanceof Recipe ) {
				tempRecipe = ( Recipe )object;

				if ( tempRecipe.getID() ==  this.id )  {		
					result = true;
				}
			}
		}

		return result;
	}
	
	public int getID(){
		return id;
	}

	public double getAmount( String ingName ) {
		return getConstituent( ingName ).getAmount();
	}

	public String getDescription() {
		return description;
	}

	public String getDirections() {
		return directions;
	}

	private Constituent getConstituent( String ingName ) {
		int i = 0;
		boolean found = false;
		Constituent returnConst = null;

		while( i < ingredients.size() && !found ) {
			if( ingName.equals( ingredients.get( i ).getName() ) ) {
				returnConst = ingredients.get( i );
				found = true;
			}

			i++;
		}

		return returnConst;
	}

	public ArrayList<Constituent> getIngredients() {
		return this.ingredients;
	}

	public String getTitle() {
		return title;
	}

	public boolean hasAllergen() {
		int i = 0;
		boolean foundOne = false;
		
		while( i < ingredients.size() && !foundOne ) {
			if( ingredients.get( i ).isAllergen() ) {
				foundOne = true;
			} else {
				i++;
			}
		}
		
		return foundOne;
	}
	
	public boolean hasThisAllergy( Allergy allergy ) {
		int i = 0;
		boolean doesContain = false;
		
		while( i < ingredients.size() && !doesContain ) {
			doesContain = ingredients.get( i ).hasThisAllergen( allergy );
			i++;
		}
		
		return doesContain;
	}
	
	public boolean hasIngredient( Ingredient testIng ) {
		boolean isIn = false;

		for( int i = 0; i < ingredients.size() && !isIn ; i++ ) {
			isIn = testIng.equals( ingredients.get( i ).getIngredient() );
		}

		return isIn;
	}
	
	public boolean hasIngredient( String ingName ) {
		boolean isIn = false;

		isIn = this.hasIngredient( new Ingredient( ingName ) );

		return isIn;
	}
	
	public boolean isLifeStyle( LifeStyle lifeStyle ) {
		boolean isLifeStyle = false;
		int i = 0;
		
		while( i < ingredients.size() && !isLifeStyle ) {
			isLifeStyle = ingredients.get( i ).isLifeStyle( lifeStyle );
			i++;
		}
		
		return isLifeStyle;
	}
	
	public Iterator<Constituent> iterator(){
		return new ConsIterator();
	}

	public void removeIngredient( String ingName ) {
		int i = 0;
		boolean found = false;

		while( i < ingredients.size() && !found ) {
			if( ingName.equals( ingredients.get( i ).getName() ) ) {
				ingredients.remove( i );
				found = true;
			}

			i++;
		}
	}

	public void removeIngredient( Ingredient ing ) {
		this.removeIngredient( ing.getName() );
	}

	public void removeIngredient(Constituent ing) {
		this.removeIngredient( ing.getIngredient() );
	}

	public void setDescription( String newDescription ) {
		if( newDescription != null ) {			
			this.description = newDescription;
		}
	}

	public void setDirections( String newDirections ) {
		if( newDirections != null) {
			this.directions = newDirections;
		}
	}

	public void setIngredientAmount( String origName, double newAmount ) {
		int i = 0;
		boolean found = false;

		while( i < ingredients.size() && !found ) {
			if( origName.equals( ingredients.get( i ).getName() ) ) {
				ingredients.get( i ).setAmount( newAmount );
				found = true;
			}

			i++;
		}
	}

	public void setIngredientName( String origName, String newName ) {
		int i = 0;
		boolean found = false;

		while( i < ingredients.size() && !found ) {
			if( origName.equals( ingredients.get( i ).getName() ) ) {
				ingredients.get( i ).setName( newName );
				found = true;
			}

			i++;
		}
	}

	public void setTitle( String newTitle ) {
		if( newTitle != null ) {
			this.title = newTitle;
		}
	}
	
	private class ConsIterator implements Iterator<Constituent> {
		int i = 0;
		
		public boolean hasNext(){
			boolean output = false;
			
			if( i < ingredients.size() ) {
				output = true;
			}
			
			return output;
		}
		
		public Constituent next(){
			Constituent output = null;
			
			if( hasNext() ){
				output = ingredients.get( i );
				i++;
			}else{
				throw new NoSuchElementException();
			}
			
			return output;
		}
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
}