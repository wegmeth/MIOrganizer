package miorganizer.wegmeth.com.mioraganizer.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import miorganizer.wegmeth.com.mioraganizer.R;
import miorganizer.wegmeth.com.mioraganizer.activities.GroupAddActivity;
import miorganizer.wegmeth.com.mioraganizer.listadapter.GroupListListAdapter;
import miorganizer.wegmeth.com.mioraganizer.helper.SQLHelper;
import miorganizer.wegmeth.com.mioraganizer.models.Group;


public class GroupFragment extends Fragment implements View.OnClickListener{

    public GroupFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_group, container, false);

        final ArrayList<Group> groups = new ArrayList<Group>();

        SQLHelper helper = new SQLHelper(getActivity().getApplicationContext());


        String[] projection = {
                Group._ID,
                Group.COLUMN_NOTES,
                Group.COLUMN_NAME
        };
        Cursor c = helper.getReadableDatabase().query(Group.TABLE_NAME, projection, "", null, null, null, null);
        c.moveToFirst();

        Group group;
        while (!c.isAfterLast()) {
            group = new Group();
            group.setId(c.getLong(c.getColumnIndexOrThrow(Group._ID)));
            group.setName(c.getString(c.getColumnIndexOrThrow(Group.COLUMN_NAME)));
            group.setNotes(c.getString(c.getColumnIndexOrThrow(Group.COLUMN_NOTES)));

            groups.add(group);
            c.moveToNext();
        }

        ListView listView = (ListView) fragment.findViewById(R.id.groupListView);

        listView.setAdapter(new GroupListListAdapter(fragment.getContext(), groups));
        listView.setClickable(true);

        Button btnAdd = (Button) fragment.findViewById(R.id.btnAddGroup);
        btnAdd.setClickable(true);
        btnAdd.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group g = groups.get(position);

                Intent intent = new Intent();
                intent.setClass(GroupFragment.this.getActivity(), GroupAddActivity.class);
                intent.putExtra("group_id", g.getId());
                startActivity(intent);

            }
        });

        return fragment;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), GroupAddActivity.class);
        startActivity(intent);
    }
}
