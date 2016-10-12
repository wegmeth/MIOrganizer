package miorganizer.wegmeth.com.mioraganizer.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import miorganizer.wegmeth.com.mioraganizer.R;
import miorganizer.wegmeth.com.mioraganizer.models.Group;
import miorganizer.wegmeth.com.mioraganizer.models.Module;

public class GroupListListAdapter extends ArrayAdapter<Group> {

    List<Group> groups;

    public GroupListListAdapter(Context context, ArrayList<Group> group) {
        super(context, 0, group);
        groups = group;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_listentry, parent, false);
        }

        TextView text = (TextView)convertView.findViewById(R.id.txtGroupnameEntry);
        text.setText(groups.get(position).getName());

        return convertView;
    }

}