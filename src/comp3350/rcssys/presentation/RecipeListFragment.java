package comp3350.rcssys.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import comp3350.rcssys.R;
import comp3350.rcssys.business.AccessRecipes;
import comp3350.rcssys.objects.Recipe;
import java.util.ArrayList;

public class RecipeListFragment extends Fragment {
	
	public static final String RECIPE_IDENTIFIER = "recipeTitle";

	private ArrayList<Recipe> recipeList;
    private ArrayAdapter<Recipe> recipeArrayAdapter;
    private AccessRecipes recipeAccess;

    public RecipeListFragment() {
    	recipeList = new ArrayList<Recipe>();
    	recipeAccess = new AccessRecipes();
    }
    
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		View view = inflater.inflate( R.layout.fragment_recipe_list, container, false );
		
		recipeArrayAdapter = new ArrayAdapter<Recipe>( getActivity(), android.R.layout.simple_list_item_activated_2, android.R.id.text1, recipeList ) {
			@Override
            public View getView( int position, View convertView, ViewGroup parent ) {
                View view = super.getView( position, convertView, parent );
                TextView text1 = ( TextView )view.findViewById( android.R.id.text1 );
                TextView text2 = ( TextView )view.findViewById( android.R.id.text2 );

                text1.setText( recipeList.get( position ).getTitle() );
                text1.setHint( "New Recipe" );
                text1.setTextColor( getResources().getColor( R.color.text ) );
                text1.setPadding( 0, 5, 0, 0 );
                text2.setText( recipeList.get( position ).getDescription() );
                text2.setTextColor( getResources().getColor( R.color.text ) );

                return view;
            }
        };
        
        final ListView listView = ( ListView )view.findViewById( R.id.listRecipes );
        registerForContextMenu( listView );
        listView.setAdapter( recipeArrayAdapter );
        
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                openRecipe( position );
            }
        });
	            
		return view;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		refreshListView();
	}
	
	
	@Override
    public void onCreateContextMenu( ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo ) {
    	super.onCreateContextMenu( menu, v, menuInfo );
    	MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate( R.menu.context_menu_recipe_list,menu );
    }
	
	@Override
    public boolean onContextItemSelected( MenuItem item ) {
        AdapterView.AdapterContextMenuInfo info = ( AdapterView.AdapterContextMenuInfo )item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        int listItemIndex = info.position;
        
        switch ( menuItemIndex ) {
            case R.id.courses_contextmenu_open:
                openRecipe( listItemIndex );
                break;
            case R.id.courses_contextmenu_delete:
                deleteRecipe( listItemIndex );
                break;
        }
        
        return true;
    }
	
	public void update( ArrayList<Recipe> newRecipeList ) {
		recipeList.clear();
		
		for( Recipe recipe : newRecipeList ) {
			recipeList.add( recipe );
		}
		
		recipeArrayAdapter.notifyDataSetChanged();
	}
	
	public void openRecipe( int position ) {
    	Recipe recipe = recipeList.get( position );
    	Intent intent;
		intent = new Intent( getActivity(), RecipeDisplay.class );
    	intent.putExtra( RECIPE_IDENTIFIER, recipe.getID() );
    	startActivity( intent );
    }
	
	public void deleteRecipe( int position ) {
    	Recipe recipe = recipeList.get( position );
    	recipeAccess.deleteRecipe( recipe );
    	recipeList.remove( position );
    	refreshListView();
    }
	
	public void newRecipe( View v ) {	
    	Intent intent = new Intent( getActivity(),RecipeDisplay.class );
    	startActivity( intent );
    }
	
	private void refreshListView() {
    	recipeArrayAdapter.notifyDataSetChanged();
    }
	
}
