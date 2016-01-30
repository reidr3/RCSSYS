package comp3350.rcssys.presentation.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import comp3350.rcssys.R;

public class EditableIngredient extends LinearLayout {
	
	public static final double UNSPECIFIED_QTY = -1.0;
	
	private ViewHolder viewHolder;
	private String[] units = {"kg","g","oz","lb","l","ml","c","tsp","tbsp"};
	
	public EditableIngredient( Context context, AttributeSet attrs ) {
		super( context,attrs );
		
		LayoutInflater inflater = ( LayoutInflater )context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		inflater.inflate( R.layout.editable_ingredient_entry, this, true );
		prepareViewHolder();
		prepareSpinner();
	}
	
	public EditableIngredient( Context context ) {
		this( context, null );
	}
	
	public EditableIngredient( Context context, String inName, double inQty, String inUnit ) {
		this( context );
		viewHolder.titleField.setText( inName );
		viewHolder.quantityField.setText( Double.toString( inQty ) );
		int unitIndex = 0;
		
		for( int i = 0; i < units.length; i++ ) {
			if( units[i].equals( inUnit ) ) {
				unitIndex = i;
			}
		}
		
		viewHolder.unitSpinner.setSelection( unitIndex );
	}
	
	public boolean isComplete() {
		boolean completion = false;
		
		if( !getName().isEmpty() && getQuantity() != UNSPECIFIED_QTY ) {
			completion = true;
		}
		
		return completion;
	}
	
	public String getName() {
		return viewHolder.titleField.getText().toString();
	}
	
	public double getQuantity() {
		double quantity = UNSPECIFIED_QTY;
		String numberString = viewHolder.quantityField.getText().toString();
		
		if( numberString != null && !numberString.isEmpty() ) {
			quantity = Double.parseDouble( numberString );
		}
		
		return quantity;
	}
	
	public String getUnit() {
		return viewHolder.unitSpinner.getSelectedItem().toString();
	}
	
	@Override
	public void setTag( Object tag ) {
		super.setTag( tag );
		viewHolder.button.setTag( tag );
	}
	
	private void prepareViewHolder() {
		viewHolder = new ViewHolder();
		viewHolder.titleField = ( EditText )findViewById( R.id.editable_ingredient_name );
		viewHolder.quantityField = ( EditText )findViewById( R.id.editable_ingredient_quantity );
		viewHolder.unitSpinner = ( Spinner )findViewById( R.id.editable_ingredient_unit );
		viewHolder.button = ( Button )findViewById( R.id.editable_ingredient_button_remove );
	}
	
	private void prepareSpinner() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( getContext(), R.layout.custom_spinner_item,units );
		viewHolder.unitSpinner.setAdapter( adapter );
	}
	
	private class ViewHolder {
		EditText titleField;
		EditText quantityField;
		Spinner unitSpinner;
		Button button;
	}

}
