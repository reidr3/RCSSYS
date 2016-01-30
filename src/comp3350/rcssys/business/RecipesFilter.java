package comp3350.rcssys.business;

import comp3350.rcssys.application.Main;
import comp3350.rcssys.application.Services;
import comp3350.rcssys.objects.Allergy;
import comp3350.rcssys.objects.LifeStyle;
import comp3350.rcssys.objects.Recipe;
import comp3350.rcssys.persistence.DataAccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class RecipesFilter {
	private  ArrayList<Recipe> recipes;
	private  ArrayList<String> filters;

	public RecipesFilter() {
		DataAccess dataAccess = ( DataAccess )Services.getDataAccess( Main.dbName );
		recipes = new ArrayList<Recipe>();
		dataAccess.open( "temp" );
		dataAccess.getRecipeSequential( recipes );
		filters = new ArrayList<String>();
	}

	public void clearFilters() {
		filters.clear();
	}

	public void createAllergyFilter( String allergy ) {
		filters.add( "Allergy: " + allergy );
	}

	public void createIngredientFilter( String ingredient ) {
		filters.add( "Ingredient: " + ingredient );
	}

	public void createLifeStyleFilter( String lifeStyle ) {
		filters.add( "Lifestyle: " + lifeStyle );
	}
	
	public void createOrder( String order ) {
		if( order.equals( "ascending" ) ) {
			createOrderAscending();
		} else if ( order.equals( "descending" ) ) {
			createOrderDescending();
		}
	}

	public ArrayList<Recipe> filterRecipes() {
		int i;
		int j;
		boolean somethingRemoved = false;
		String[] words;
		
		if( filters.size() > 0 ) {
			for( i = 0; i < filters.size(); i++ ) {
				j = 0;
				
				if( filters.get( i ).equals( "Alphabetical ascending" ) ) {
					SortAlphabetical( "ascending" );	
				} else if( filters.get( i ).equals( "Alphabetical descending" ) ) {
					SortAlphabetical( "descending" );		
				} else {
					words = filters.get( i ).split( "\\s+" );
					
					while( j < recipes.size() ) {
						if( words[0].equals( "Allergy:" ) ) {
							somethingRemoved = this.filterAllergies( j, words[1] );
						} else if ( words[0].equals( "Ingredient:" ) ) {
							somethingRemoved = this.containsIngredient( filters.get( i ).substring( words[0].length() + 1 ), j );
						} else if ( words[0].equals( "Lifestyle:" ) ) {
							somethingRemoved = this.belongsToLifeStyle( filters.get( i ).substring( words[0].length() + 1 ), j );
						} else if ( words[0].equals( "Title:" ) ) {
							somethingRemoved = this.filterTitle( filters.get( i ).substring( words[0].length() + 1 ), j ) ;
						}

						if( !somethingRemoved ) {
							j++;
						}
					}
				}
			}
		}

		return recipes;
	}
	
	public void setTitleFilter( String title ){
		if( title != null ){
			filters.add( "Title: " + title );
		}
	}
	
	private boolean belongsToLifeStyle ( String lifeStyle, int index ) {
		boolean removed = false;
		
		if( !recipes.get( index ).isLifeStyle( new LifeStyle( lifeStyle ) ) ) {
			recipes.remove( index );
			removed = true;
		}
		
		return removed;
	}

	private boolean containsAnyAllergen( int index ) {
		boolean removed = false;
		
		if( !recipes.get( index ).hasAllergen() ) {
			recipes.remove( index );
			removed = true;
		}
		
		return removed;
	}
	
	private boolean containsIngredient( String ingredient, int index ) {
		boolean removed = false;
		
		if( !recipes.get( index ).hasIngredient( ingredient ) ) {
			recipes.remove( index );
			removed = true;
		}
		
		return removed;
	}

	private boolean containsNoAllergen( int index ) {
		boolean removed = false;
		
		if( recipes.get( index ).hasAllergen() ) {
			recipes.remove( index );
			removed = true;
		}
		
		return removed;
	}

	private boolean containsThisAllergen( String allergy, int index ) {
		boolean removed = false;
		
		if( !recipes.get( index ).hasThisAllergy( new Allergy( allergy ) ) ) {
			recipes.remove( index );
			removed = true;
		}
		
		return removed;
	}
	
	private void createOrderAscending() {
		filters.add( "Alphabetical ascending" ) ;
	}

	private void createOrderDescending() {
		filters.add( "Alphabetical descending" );
	}
	
	private boolean filterAllergies( int index, String allergenTitle ) {
		boolean removed = false;
		String query = allergenTitle.toLowerCase( Locale.CANADA );
		
		if( query.equals( "any" ) ) {
			removed = this.containsAnyAllergen( index );
		} else if ( query.equals( "none" ) ) {
			removed = this.containsNoAllergen( index );
		} else {
			removed = this.containsThisAllergen( query, index );
		}
		
		return removed;
	}
	
	private boolean filterTitle( String titleQuery, int index) {
		boolean removed = false;
		
		if( titleQuery!=null && !titleQuery.isEmpty() && !recipes.get( index ).getTitle().contains( titleQuery ) ) {
			recipes.remove( index );
			removed = true;
		}
		
		return removed;
	}
	
	private void SortAlphabetical( String order ) {
		if ( order != null ) {
			if( order.equals( "ascending" ) ) {
				Collections.sort( recipes );
			} else if ( order.equals( "descending" ) ) {
				Collections.sort( recipes, Collections.reverseOrder() );
			}
		}	
	}
}
