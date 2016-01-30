package comp3350.rcssys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLWarning;

import comp3350.rcssys.objects.Allergy;
import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.objects.Ingredient;
import comp3350.rcssys.objects.LifeStyle;
import comp3350.rcssys.objects.Recipe;


public class DataAccessObject implements DataAccess {
	private Statement st1, st2;
	private Connection c1;
	private ResultSet rs2, rs3, rs4, rs5, rs6;

	private String dbName;
	private String dbType;
	private String cmdString;
	private String result;
	private static String EOF = "  ";
	
	private int updateCount;
	
	private ArrayList<Recipe> recipes;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<Allergy> allergies;
	private ArrayList<LifeStyle> lifeStyles;
	private Ingredient tempIng;

	public DataAccessObject( String dbName ) {
		this.dbName = dbName;
	}

	public void close() {
		try {
			cmdString = "shutdown compact";
			rs2 = st1.executeQuery( cmdString );
			c1.close();
		} catch ( Exception e ) {
			processSQLError( e );
		}

		System.out.println( "Closed " +dbType +" database " +dbName );
	}
	
	public void open( String string ) {
		String url;
		try {
			dbType = "HSQL";
			Class.forName( "org.hsqldb.jdbcDriver" ).newInstance();
			url = "jdbc:hsqldb:file:" + string; // stored on disk mode
			c1 = DriverManager.getConnection( url, "SA", "" );
			st1 = c1.createStatement();
			st2 = c1.createStatement();
		}
		catch ( Exception e ) {
			processSQLError( e );
		}
	
		System.out.println( "Opened " + dbType + " database " + string) ;
	}
	
	public String checkWarning( Statement st, int updateCount )	{
		String result;
		result = null;
		
		try	{
			SQLWarning warning = st.getWarnings();
			if ( warning != null ) {
				result = warning.getMessage();
			}
		}
		catch ( Exception e ) {
			result = processSQLError( e );
		}
		if ( updateCount != 1 ) {
			result = "Tuple not inserted correctly.";
		}
		
		return result;
	}
	
	public String deleteIngredient( Ingredient ingredient ) {
		String values;
		result = null;
			
		if( ingredient != null ) {
			try	{
				values = ingredient.getName();
				cmdString = "Delete from Ingredients where INGREDIENTNAME='" + values +"'";
				updateCount = st1.executeUpdate( cmdString );
				result = checkWarning( st1, updateCount );
				
			}
			catch ( Exception e ) {
				result = processSQLError( e );
			}
		}
		
		return result;
	}
	
	public String deleteRecipe( Recipe currentRecipe ) {
		result = null;
		
		if ( currentRecipe != null ) {
			try	{
				cmdString = "Delete from Recipes where RecipeId=" + currentRecipe.getID();
				updateCount = st1.executeUpdate( cmdString );
				result = checkWarning( st1, updateCount );
			}
			catch ( Exception e ) {
				result = processSQLError( e );
			}
		}
		
		return result;
	}
	
	public ArrayList<Ingredient> getIngredient( Ingredient ingredientResult ) {
		String myName;
		result = null;
		myName = EOF;
		ingredients = new ArrayList<Ingredient>();
		allergies = new ArrayList<Allergy>();
		lifeStyles = new ArrayList<LifeStyle>();
		
		if( ingredientResult != null ) {
			try {	
				cmdString = "Select * from Ingredients where IngredientName='" +ingredientResult.getName() + "'";
				rs4 = st1.executeQuery( cmdString );
				
				while ( rs4.next() ) {
					myName = rs4.getString( "IngredientName" );
					
					ingredientResult = new Ingredient( myName );
					addTags( ingredientResult );
					ingredients.add( ingredientResult );
				}
				rs4.close();
			}
			catch ( Exception e ) {
				processSQLError( e );
			}
		}
		
		return ingredients;
	}
	
	public String getIngredientSequential( List<Ingredient> ingredientResult ) {
		Ingredient ingredient = null;
		String myName;
		myName = EOF;
		result = null;
		allergies = new ArrayList<Allergy>();
		lifeStyles = new ArrayList<LifeStyle>();
		
		if( ingredientResult != null ) {
			try {
				cmdString = "Select * from Ingredients";
				rs5 = st1.executeQuery( cmdString );		
			} catch ( Exception e ) {
				processSQLError( e );
			}
			
			try	{
				while ( rs5.next() ) {
					myName = rs5.getString( "INGREDIENTNAME" );
					ingredient = new Ingredient( myName );
					addTags( ingredient );
					ingredientResult.add( ingredient );
					lifeStyles.clear();
					allergies.clear();
				}
				rs5.close();
			
			} catch ( Exception e )	{
				processSQLError( e );
			}
		}
		
		return result;
	}
	
	public ArrayList<Recipe> getRecipe( Recipe currentRecipe ) {
		recipes = new ArrayList<Recipe>();
		ArrayList<Constituent> constituents = new ArrayList<Constituent>();
		Constituent constituent = null;
		String ingName = null;
		String unit = null;
		int myId;
		double amount = 0.0;
		String myTitle;
		String myDirections;
		String myDescription;
		
		myId = -1;
		myTitle = EOF;
		myDirections = EOF;
		myDescription = EOF;
		
		if( currentRecipe != null ) {
			try {
				cmdString = "Select * from Recipes where RECIPEID='" + currentRecipe.getID() + "'";
				rs3 = st1.executeQuery( cmdString );
				
				while ( rs3.next() ) {
					myId = rs3.getInt( "RECIPEID" );
					myTitle = rs3.getString( "RECIPETITLE" );
					myDirections = rs3.getString( "Directions" );
					myDescription = rs3.getString( "Description" );
					
					cmdString = "Select * from Constituent where RECIPEID='" + currentRecipe.getID() + "'";
					rs4 = st1.executeQuery( cmdString );
					
					while( rs4.next() ) {
						ingName = rs4.getString( "INGREDIENTNAME" );
						unit = rs4.getString( "Unit" );
						amount = rs4.getDouble( "Amount" );
						tempIng = new Ingredient( ingName );
						addTags( tempIng );
						constituent = new Constituent( tempIng, amount, unit );
						constituents.add( constituent );
					}
					
					rs4.close();
					currentRecipe = new Recipe( myId, myTitle, myDirections, myDescription, constituents );
					recipes.add( currentRecipe );
					constituents.clear();
				}
				
				rs3.close();
			} catch ( Exception e )	{
				processSQLError( e );
			}
		}
		
		return recipes;
	}

	public String getRecipeSequential( List<Recipe> recipesResult ) {
		Recipe recipe = null;
		String myTitle;
		String myDirections;
		String myDescription;
		int myId;
		Constituent constituent;
		ArrayList<Constituent> constituents = new ArrayList<Constituent>();
		String ingName;
		String unit;
		double amount;
		
		myId = -1;
		myTitle = EOF;
		myDirections = EOF;
		myDescription = EOF;
		recipes = new ArrayList<Recipe>();
		result = null;
		
		try {
			cmdString = "Select * from Recipes";
			rs2 = st1.executeQuery( cmdString );
		}
		catch ( Exception e )
		{
			processSQLError( e );
		}
		
		try {
			while ( rs2.next() ) {
				myId = rs2.getInt( "RECIPEID" );
				myTitle = rs2.getString( "RECIPETITLE" );
				myDirections = rs2.getString( "DIRECTIONS" );
				myDescription = rs2.getString( "DESCRIPTION" );
				
				cmdString = "Select * from Constituent where RECIPEID='" + myId + "'";
				rs3 = st2.executeQuery( cmdString );
				
				while( rs3.next() ) {
					ingName = rs3.getString( "INGREDIENTNAME" );
					unit = rs3.getString( "Unit" );
					amount = rs3.getDouble( "Amount" );
					tempIng = new Ingredient( ingName );
					addTags( tempIng );
					constituent = new Constituent( tempIng, amount, unit );
					constituents.add( constituent );
				}
				
				rs3.close();
				recipe = new Recipe( myId, myTitle, myDirections, myDescription, constituents );
			    recipesResult.add( recipe );     
			    constituents.clear();
			}
			
			rs2.close();
		} catch ( Exception e )	{
			processSQLError( e );
		}
		
		return result;
	}
	
	public String insertIngredient( Ingredient ingredient ) {
		String values;
		result = null;
		
		if( ingredient != null ) {
			try	{
				values = "'" + ingredient.getName() + "'";
				cmdString = "Insert into Ingredients " +"Values(" + values +")";
				updateCount = st1.executeUpdate( cmdString );
				result = checkWarning( st1, updateCount );
				
				insertTags( ingredient );
			}
			catch ( Exception e ) {
				result = processSQLError( e );
			}		
		}
		
		return result;
	}
	
	public String insertRecipe( Recipe currentRecipe ) {
		String values;
		result = null;
		
		if( currentRecipe != null ) {
			try	{
				values = currentRecipe.getID()
						+ ", '" + currentRecipe.getTitle()
						+"', '"+currentRecipe.getDirections()
						+"', '"+currentRecipe.getDescription()
						+"'";
				cmdString = "Insert into Recipes " +" Values(" + values +")";
				updateCount = st1.executeUpdate( cmdString );
				result = checkWarning( st1, updateCount );
				
				insertConstituents( currentRecipe );
			}
			catch ( Exception e ){
				result = processSQLError( e );
			}
		} 		
		
		return result;
	}
	
	public String processSQLError( Exception e ) {
		String result = "*** SQL Error: "  + e.getMessage();
		e.printStackTrace();
		
		return result;
	}

	public String updateIngredient( Ingredient ingredient ) {
		result = null;
		
		if( ingredient != null ) {
			try	{
				insertTags( ingredient );
				result = checkWarning( st1, updateCount );
			}
			catch ( Exception e ) {
				result = processSQLError( e );
			}
		}
		
		return result;
	}
	
	public String updateRecipe( Recipe currentRecipe ) {
		String values;
		String where;
		result = null;
		
		try	{
			values = "RecipeTitle='" + currentRecipe.getTitle()
					+ "', Description='"+ currentRecipe.getDescription()
					+ "', Directions='"+ currentRecipe.getDirections()
					+ "'";
			where = "where RecipeID='" + currentRecipe.getID() + "'";
			cmdString = "Update Recipes " + " Set " + values + " " + where;
			updateCount = st1.executeUpdate( cmdString );
			result = checkWarning( st1, updateCount );
			
			insertConstituents( currentRecipe );
		}
		catch ( Exception e ) {
			
			result = processSQLError( e );
		}
		
		return result;
	}
	
	private String addTags( Ingredient ingredient ) {
		result = null;
		String tagName;
		Allergy tempAllergy;
		LifeStyle tempLife;
		
		try {
			cmdString = "Select * from isAllergy Where INGREDIENTNAME='" + ingredient.getName() + "'";
			rs6 = st1.executeQuery( cmdString );
			
			while( rs6.next() ) {
				tagName = rs6.getString( "AllergyName" );
				tempAllergy = new Allergy( tagName );
				ingredient.addAllergy( tempAllergy );
			}
			
			rs6.close();
			cmdString = "Select * from isLifeStyle Where INGREDIENTNAME='" + ingredient.getName() + "'";
			rs6 = st1.executeQuery( cmdString );
			
			while( rs6.next() ) {
				tagName = rs6.getString( "LifeStyleName" );
				tempLife = new LifeStyle( tagName );
				ingredient.addLifeStyle( tempLife );
			}
			
			rs6.close();
		} catch ( Exception e ) {
			result = processSQLError( e );
		}
		
		return result;
	}
	
	private void insertConstituents( Recipe currentRecipe ) {
		String values;
		ArrayList<Constituent> cons = new ArrayList<Constituent>();
		cons.addAll( currentRecipe.getIngredients() );
		result = null;
		
		try {
			cmdString = "Delete FROM Constituent Where RecipeID='" + currentRecipe.getID() + "'";
			updateCount = st1.executeUpdate( cmdString );
			
			for( int i = 0; i < cons.size(); i ++ ) {
				values = currentRecipe.getID()
						+ ", '" + cons.get( i ).getName()
						+ "', " + cons.get( i ).getAmount()
						+ ", '" + cons.get( i ).getUnit()
						+ "'";
					
					cmdString =  "Insert into Constituent " + "Values(" + values +")";
					updateCount = st1.executeUpdate( cmdString );
					
					insertTags( cons.get( i ).getIngredient() );
			}
		} catch ( Exception e ) {
			result = processSQLError( e );
		}
	}
	
	private String insertTags( Ingredient ingredient ) {
		allergies = new ArrayList<Allergy>();
		lifeStyles = new ArrayList<LifeStyle>();
		String ingName = ingredient.getName();
		
		try {
			cmdString = "Delete From isAllergy WHERE IngredientName = '" + ingName + "'";
			updateCount = st1.executeUpdate( cmdString );
			cmdString = "Delete From isLifeStyle WHERE IngredientName = '" + ingName + "'";
			updateCount = st1.executeUpdate( cmdString );
					
			allergies.addAll( ingredient.getAllergies() );
			
			for( int i = 0; i < allergies.size(); i ++ ) {
				cmdString = "Insert into isAllergy Values('" + allergies.get(i).getName() + "', '" + ingName + "')";
				updateCount = st1.executeUpdate( cmdString );
			}
			
			lifeStyles.addAll( ingredient.getLifeStyles() );
			
			for( int j = 0; j < lifeStyles.size(); j ++ ) {
				cmdString = "Insert into isLifeStyle Values('" + lifeStyles.get(j).getName() + "', '" + ingName + "')";
				updateCount = st1.executeUpdate( cmdString );
			}
			
		} catch ( Exception e ) {
			result = processSQLError( e );
		}
		
		return result;
	}
}
