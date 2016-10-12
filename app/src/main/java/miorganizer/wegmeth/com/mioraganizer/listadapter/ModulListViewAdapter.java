package miorganizer.wegmeth.com.mioraganizer.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import miorganizer.wegmeth.com.mioraganizer.R;
import miorganizer.wegmeth.com.mioraganizer.models.Module;

public abstract class ModulListViewAdapter extends ArrayAdapter<Module> {

    public ModulListViewAdapter(Context context, ArrayList<Module> module) {
        super(context, 0, module);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Module module = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.semester_modul_listentry, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.txtTitle);
        title.setText(module.getShortName());

        CheckBox prak = (CheckBox) convertView.findViewById(R.id.checkPraktikum);
        if (!module.isRequiredPrac()) {
            prak.setVisibility(View.INVISIBLE);
        }
        prak.setChecked(module.isPraktikum_done());

        CheckBox done = (CheckBox) convertView.findViewById(R.id.checkFinished);
        done.setChecked(module.isDone());

        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                doneChecked(isChecked, module);
            }
        });

        prak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prakChecked(isChecked,module);
            }
        });

        return convertView;
    }

    public abstract void prakChecked(boolean isChecked, Module mod);
    public abstract void doneChecked(boolean isChecked,Module mod );
}
