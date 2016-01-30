package comp3350.rcssys.presentation.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import comp3350.rcssys.business.UnitConversions;
import comp3350.rcssys.objects.Constituent;
import comp3350.rcssys.R;
import java.util.ArrayList;

public class IngredientAdapter extends ArrayAdapter<Constituent> {
	
	private String[] conversionSettings;
	private boolean[] checked;
	private int mostRecentCheck;
	
	public IngredientAdapter( Context context, ArrayList<Constituent> data ) {
		super( context, R.layout.rowlayout_ingredient, data );
		checked = new boolean[data.size()];
		conversionSettings = new String[data.size()];
	}

	public View getView( final int position, View convertView, ViewGroup parent ) {
		LayoutInflater inflater = ( LayoutInflater ) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		ViewHolder viewHolder;
		
		if( convertView == null ) {
			convertView = inflater.inflate( R.layout.rowlayout_ingredient, parent, false );
			viewHolder = new ViewHolder();
			
			viewHolder.name = ( TextView )convertView.findViewById( R.id.rowlayout_ingredient_textView_name );
			viewHolder.quantity = ( TextView )convertView.findViewById( R.id.rowlayout_ingredient_textView_quantity );
			convertView.setTag( viewHolder );
		} else {
			viewHolder = ( ViewHolder )convertView.getTag();
		}
		
		Constituent ingredient = getItem( position );
		viewHolder.name.setText( ingredient.getName() );
		
		if( conversionSettings[position] != null && !conversionSettings[position].isEmpty() ) {
			try {
				Constituent convertedIngredient = UnitConversions.convertUnit( ingredient, conversionSettings[position] );
				viewHolder.quantity.setText( convertedIngredient.getAmount()+" "+convertedIngredient.getUnit() );
			} catch( IllegalArgumentException e ) {
				Toast.makeText( getContext(), "Cannot convert from " + ingredient.getUnit() + " to " + conversionSettings[position], Toast.LENGTH_SHORT ).show();
			}
		} else {
			viewHolder.quantity.setText( ingredient.getAmount() + " " + ingredient.getUnit() );
		}
		
		if( checked[position] ) {
			if(position == mostRecentCheck) {
				convertView.setBackgroundColor( getContext().getResources().getColor( R.color.flavor ) );
			} else {
				convertView.setBackgroundColor( getContext().getResources().getColor( R.color.flavor2 ) );
			}
		} else {
			convertView.setBackgroundColor( getContext().getResources().getColor( R.color.background ) );
		}
		
		
		return convertView;
	}
	
	public void setConversionSetting( int position, String unit ) {
		conversionSettings[position] = unit;
	}
	
	public void toggleCheck( int index ) {
		checked[index] = !checked[index];
		mostRecentCheck = index;
		notifyDataSetChanged();
		
		if( !checked[mostRecentCheck] ) {
			mostRecentCheck = -1;
		}
	}
	
	public int getMostRecentCheck() {
		return mostRecentCheck;
	}
	
	public boolean isMostRecent( int number ) {
		boolean output = false;
		
		if( mostRecentCheck == number ) {
			output = true;
		}
		
		return output;
	}
	
	public ArrayList<Integer> getChecked() {
		ArrayList<Integer> output = new ArrayList<Integer>();
		
		for( int i = 0; i < checked.length; i++ ) {
			if( checked[i] ) {
				output.add( i );
			}
		}
		
		return output;
	}
	
	private class ViewHolder {
		protected TextView name;
		protected TextView quantity;
	}
	
}
