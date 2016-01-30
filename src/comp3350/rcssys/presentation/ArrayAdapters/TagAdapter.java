package comp3350.rcssys.presentation.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import comp3350.rcssys.R;
import java.util.ArrayList;

public class TagAdapter extends ArrayAdapter<String> {
	public TagAdapter( Context context, ArrayList<String> data ) {
		super( context, R.layout.tag_list_item, data );
	}
	
	public View getView( final int position, View convertView, ViewGroup parent ) {
		LayoutInflater inflater = ( LayoutInflater )getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		ViewHolder viewHolder;
		
		if( convertView == null ) {
			convertView = inflater.inflate( R.layout.tag_list_item, parent, false );
			viewHolder = new ViewHolder();
			
			viewHolder.name = ( TextView )convertView.findViewById( R.id.tagItem_name );
			
			convertView.setTag( viewHolder );
		}else {
			viewHolder = ( ViewHolder )convertView.getTag();
		}
		viewHolder.name.setText( getItem( position ) );
		
		return convertView;
	}

	private class ViewHolder {
		protected TextView name;
	}
}
