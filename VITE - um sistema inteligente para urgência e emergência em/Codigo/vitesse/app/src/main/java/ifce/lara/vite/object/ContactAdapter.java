package ifce.lara.vite.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ifce.lara.vite.R;

/**
 * Created by otonbraga on 29/08/16.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context,0 , contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        Contact contact = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name_device);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tv_phone_device);
        // Populate the data into the template view using the data object
        tvName.setText(contact.name);
        tvHome.setText(contact.phone);
        // Return the completed view to render on screen
        return convertView;
    }
}
